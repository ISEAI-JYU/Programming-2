# External Libraries and Java Project Management Tools

### Learning Objectives

* Understand why build tools (such as Maven or Gradle) are needed in modern software development.
* Be familiar with the basic structure of a Maven project and the role of the `pom.xml` file.
* Be able to find and add external dependencies to a project.
* Understand the role of Java packages in organizing code and preventing naming conflicts.
* Be able to use `import` statements to access classes located in different packages.

Suppose you want to write a Java program that retrieves information from the web using an HTTP request. You find the following example online.

```java
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main {
    public static void main(String[] args) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/zen")
                .header("User-Agent", "Demo")
                .build();

        Response response = client.newCall(request).execute();
        IO.println(response.body().string());
        response.close();
    }
}
```

You try to compile the program, but receive an error message stating that the package `okhttp3` cannot be found.
The question is: where should this library come from? Even if you manage to download it as a `.jar` file, where should that file be placed? And what if the library itself depends on other libraries?

This brings us to the core of modern software development: your own code is often not enough. We frequently need code written by other developers as part of our applications.
Code published for others to use is called a *library*.
When you use a library written by someone else, your project *depends* on it. Such a dependency is called a *dependency*.
In the previous example, the dependency is the OkHttp library.

Dependency management means:

* obtaining the correct version of a library,
* adding it to the project's *classpath*,
* taking the library's own dependencies into account, and
* preventing version conflicts.

Managing libraries manually is tedious, which is why special tools have been developed to perform this work automatically.
These tools are called *build tools*.
The most common build tools in the Java ecosystem are **Maven** and **Gradle**.
A build tool automates the dependency-management tasks listed above.
It can also perform other tasks, such as running automated tests and packaging the finished application for distribution.

Next, we will introduce Maven in IntelliJ IDEA.
The same could just as easily be done using [Gradle](https://gradle.org/) or [Ant](https://ant.apache.org/).
Maven is perhaps slightly easier for beginners, so we will use it in these examples.

<details><summary>Analogy: Furniture Designer</summary>

Think of your role as a programmer as somewhat similar to that of an IKEA furniture designer.
You do not personally build each piece of furniture for the customer. Instead, you create detailed construction plans, assembly instructions (your code), and a parts list describing everything required for assembly.
The person who eventually purchases the furniture and assembles it can be compared to the Java Virtual Machine or another runtime environment: they open the package, follow your instructions, execute the steps in order, and bring the furniture to life.

But how do the plans and instructions on the designer's desk become a ready-to-sell package?
You cannot simply mail the assembly instructions and expect the customer to search hardware stores for the correct boards, bolts, nuts, and hinges.

Instead, as the designer, you deliver both the plans and the parts list to the factory and packaging department.
There, all necessary parts are automatically collected and packaged together with your instructions into a neatly packed box that can easily be transported and sold.

This is where **build tools** come into the picture.

Build tools such as **Maven**, **Gradle**, and the older **Apache Ant** act as your project's automated factory and packaging department.
Their responsibilities can be divided into three categories.

### 1. Dependency Management (Ordering the Nuts and Bolts)

As a programmer, you do not write everything from scratch yourself.
For example, you might use libraries for database connections or password encryption.
These reusable pieces of code are called *dependencies*.
A build tool reads the parts list you provide (for example, `pom.xml` or `build.gradle`), searches online repositories for the required libraries, downloads them automatically, and adds them to your project.
Dependency management is a central part of modern Java development and helps ensure that the correct library versions are used and that all required components are available.

### 2. Compilation and Testing (Quality Control)

Before the package is sealed, the build tool verifies that everything works.
It compiles your source code into a form the computer can execute and runs any automated tests.
In other words, it functions as a quality-control assembly line, checking that no parts are missing, that everything fits together correctly, and that the instructions make sense.

### 3. Packaging and Distribution (The Flat Cardboard Box)

Once all parts have been gathered and verified, the build tool packages everything into a single, easy-to-handle file, such as a **JAR** or **WAR** file (Java Archive).

Finally, the build tool can assist with deployment—that is, delivering the application to the environment where it will actually be used.
This may be a cloud service such as AWS or Azure, an application store such as Google Play or the Apple App Store, or an internal company server.
In the IKEA analogy, this is the stage where the factory loads the finished boxes onto trucks and delivers them to the warehouse shelves for customers to pick up.

</details>

***

## Your First Maven Project

Let's create our first Java project using Maven.

* Start by creating a new project.
* Name the project `MyFirstMavenProject`.
* In IDEA, select **Maven** as the Build System.
* Click **Create**.

If Maven is not available for some reason, install it through IDEA's plugin manager:
```text
File → Settings
      → Plugins
      → Marketplace
      → Search for "Maven"
      → Install
      → Restart IDEA
```

After a short wait, IDEA should generate a project containing several files and directories.
Let's take a closer look.
The project structure should look approximately like this:

```bob
src
 ├─ main --> java
 └─ test --> java
pom.xml
```

* The `src` directory contains both application code (`main/java`) and test code (`test/java`).
* The `pom.xml` file contains Maven's configuration, including dependencies, build settings, and other important information.
* IntelliJ will also automatically create a `.gitignore` file and a `.mvn` directory, which we will discuss later.

Let's look at the `pom.xml` file, which is the heart of a Maven project.
Opening the file reveals XML content that defines the structure, dependencies, and configuration of the project.
A simple "vanilla" Java project (one without external libraries) typically contains something similar to the following:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>MyFirstMaven</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>25</maven.compiler.source>
        <maven.compiler.target>25</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

</project>
```

At the beginning of the file we find standard XML syntax, followed by the elements `<groupId>`, `<artifactId>`, and `<version>`.
Together, these form the identity of the project and uniquely distinguish it from all other Maven projects.

* `groupId` acts as an organizational identifier. It is often written as a reversed internet domain name, such as `fi.jyu.programming`.
* `artifactId` is the project name.
* `version` specifies the project's version number.

Together these three values form the project's unique identifier.
At this stage these identifiers are not particularly important, but if you later publish the project to a repository such as Maven Central, they become essential.

The remaining lines define the Java version and the source-code character encoding used by the project.

Open `Main.java`, add the HTTP request example shown at the beginning of the chapter, and try to compile it.
The project still will not compile because the OkHttp library has not yet been added to the project's dependencies.
Dependencies are added to a Maven project by modifying the `pom.xml` file.
Locate the `<dependencies>` element in the file. If it does not yet exist, create both the opening and closing tags.

```xml
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp-jvm</artifactId>
    <version>5.3.2</version>
</dependency>
```

IDEA will still complain that the `okhttp-jvm` package cannot be found.
After adding dependencies, the Maven project must be refreshed. In the project view, right-click the project name, select **Maven**, and then **Sync Project**.
After this, IDEA downloads the required OkHttp dependency and any additional dependencies required by the library itself.

Also add `package org.example;`
to the beginning of your source file so that the code belongs to the correct package.
We will return to the meaning of packages shortly.

Save the file and compile the project.
Maven now downloads the OkHttp library from the Maven Central repository and links it to your project.
Compilation succeeds, and you should be able to see the results of the HTTP request in the console.

***

## Maven Central

You do not need to invent dependency XML by yourself.
One of the most popular repositories for Java libraries is [Maven Central](https://central.sonatype.com/), maintained by Sonatype.
Maven Central is a public repository from which Java libraries can be downloaded and added directly to a project.

You can search for libraries and their dependencies in Maven Central and copy the required XML directly into your `pom.xml` file.
Let's search for the OkHttp library we just used.

* Go to the Maven Central website <https://central.sonatype.com/>.
* Type `"okhttp"` into the search box and press Enter.
* The first search result points to an older library named `okhttp`.
* Instead, select the second result, which corresponds to the newer version.
* Under the *Snippets* section, you will find XML code that can *usually* be copied directly into `pom.xml`.
* Copy the XML and paste it inside the `<dependencies>` element.

For some libraries, however, the XML cannot be used exactly as provided.
You should always check the library documentation to ensure that the dependency definition is Maven-compatible.
The [OkHttp library](https://square.github.io/okhttp/#maven-and-jvm-projects) is one such case: we specifically need the Maven-compatible `okhttp-jvm` artifact.

In this situation, it is sufficient to change the value of the `<artifactId>` element to
`okhttp-jvm` 
and the dependency definition is ready to use.

Using classes provided by a dependency also requires adding the appropriate `import` statements.
For example, to use the `OkHttpClient` class from the OkHttp library, you must add 
`import okhttp3.OkHttpClient;`
to the beginning of the file.
Sometimes multiple classes are needed from the same package. In such cases, a wildcard import can be used
`import okhttp3.*;`
which imports all classes from the `okhttp3` package.

***

## Third-Party Dependencies

Java projects often make use of third-party libraries that provide ready-made functionality and save development time.
In Maven projects, the Maven Central repository and the user's *local repository* are available by default.
If you want to use a dependency that is not available in Maven Central, you can add additional repositories to the project by defining them in `pom.xml`:

```
<repositories>
    <repository>
        <id>repository-id</id>
        <url>repository-url</url>
        <!-- Other settings -->
    </repository>

    <!-- Other repositories -->
</repositories>
```

The repositories available to a project can also be viewed in IntelliJ IDEA:

```text
File > Settings
     > Build, Execution, Deployment
     > Build Tools
     > Maven
     > Repositories
```

<details><summary><i class="bi bi-stars jyu-gold"></i> Bonus: Where does Maven store libraries?</summary>

Maven installs all downloaded dependencies into a local directory.
After installation, those dependencies can be used by any Maven project on the same machine.
This local repository is typically located at `.m2/repository`
inside the user's home directory.
It is also possible to manually add packages to the local repository and then reference them from `pom.xml` like any other dependency.

Any JAR file can be installed into the local Maven repository using a command similar to:

```text
mvn install:install-file \
   -Dfile=<file-path> \
   -DgroupId=<group-id> \
   -DartifactId=<artifact-id> \
   -Dversion=<version-number> \
   -Dpackaging=jar \
   -DgeneratePom=true
```

This requires Maven command-line tools to be installed, so we will not use this approach on this course.
However, it is useful to know that such dependencies are then available only on the machine where the manual installation was performed.

A dependency can also be added directly to a project without placing it in the local repository.

This approach allows, for example, a dependency JAR to be stored inside the project directory itself, making it easier to include in version control.

The dependency is declared in `pom.xml` as usual, but we use the scope value `system` together with the `systemPath` setting to specify the path to the local file.

The variable `${project.basedir}` refers to the root directory of the project.

In the following example, the dependency is located in the project's `lib` directory.

```xml
<dependencies>
    <dependency>
        <groupId>organization-id</groupId>
        <artifactId>project-id</artifactId>
        <version>1.0</version>
        <scope>system</scope>
        <systemPath>${project.basedir}/lib/file.jar</systemPath>
    </dependency>
</dependencies>
```
</details>

***

## Packages in Java

As a Java application grows into a system consisting of many classes, placing all classes into a single directory is no longer sufficient.
We need a way to organize related classes into logical groups.
This is the purpose of *packages*.
A package is a named collection of classes.
It serves both as a logical grouping mechanism and as a technical *namespace* that prevents naming conflicts.

A class can declare the package to which it belongs at the beginning of the source file:

```java,ignore
package fi.jyu.programming;

class User {
    // ...
}
```

The `User` class now belongs to the package `fi.jyu.programming`.
Its fully qualified name is `fi.jyu.programming.User`
Classes in the same package can access one another without using fully qualified names.
They can also use one another without explicit `import` statements.

```java,ignore
package fi.jyu.programming;
class Main {
    static void main() {
        User user = new User();

        // ...

        // This also works, but is unnecessary
        // because Main and User belong to
        // the same package.

        fi.jyu.programming.User user2 = new User();
    }
}
```

Packages are directly connected to the project's directory structure.
Each part of a package name corresponds to one directory.
For example, the package `org.example`
corresponds to the directory structure `src/main/java/org/example/Main.java`.
This is not merely a recommendation.
The Java compiler requires that the file location matches the package declaration.

Packages are also used when working with external libraries.
When a library is added to a project, its classes reside inside the library's own packages.
Using those classes requires `import` statements.
For example, the class `OkHttpClient` belongs to the package `okhttp3`, so it can be imported as follows
`import okhttp3.OkHttpClient;`
An `import` statement does not copy a class into your project.
It merely tells the compiler where the class can be found.
Without an import, the class could only be referenced using its fully qualified name:

```
okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
```

Let's return to the `User` example.
Suppose the same project also contains another package named `com.example.library`, which also includes a `User` class.

```java,ignore
package com.example.library;

class User {
    // ...
}
```

Although the project now contains two classes named `User`, their fully qualified names differ, so no conflict occurs.
Sometimes both `User` classes may need to be used within the same source file.
In that case, fully qualified names can be used to distinguish between them.

```java,ignore
import fi.jyu.programming.User;
import com.example.library.User;

void main() {
    fi.jyu.programming.User user1 = new fi.jyu.programming.User();
    com.example.library.User user2 = new com.example.library.User();
}
```

Such situations are relatively uncommon in practice, but they highlight the importance of packages as namespaces.

Package names are usually based on a reversed internet domain name.
For example, a University of Jyväskylä project might use a package such as:

```text
fi.jyu.ohj2.myawesomeproject
```

This convention helps ensure that package names remain globally unique, which is particularly important when libraries are published for use by others.

For very small programs, packages are often unnecessary, and all classes may be placed in the same directory.
However, packages are a fundamental part of large Java applications.
They help keep code organized and prevent naming conflicts.
They also form the foundation of the standardized directory structures used by Java libraries and build tools such as Maven.
This structure ensures that development tools and runtime environments can locate classes correctly and use them properly.

In IntelliJ IDEA, packages can be defined conveniently when creating a Maven project.
When creating a new project:

* Open **Advanced Settings**.
* Enter the desired package name into the **GroupId** field.

IDEA will automatically create the correct directory structure and place `Main.java` into the package you specified.

***

## Exercises

<task>
<task-title>Exercise 6.8: Dependencies
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/6-8-dependencies/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part6/exercise8">Complete this exercise in TIM</a></task-link>
</task>