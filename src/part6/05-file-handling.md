# File Handling

### Learning Objectives

* Be able to read and write text files using Java's `Files` class.
* Be able to use the `Scanner` class to read and parse data from files.
* Be able to process files line by line using the `Stream` interface.
* Know the basics of the JSON file format.
* Be able to use the Jackson library to read and write JSON data.
* Understand how Java records are suitable for modeling data.

File handling always follows the same basic pattern: open a resource, read or write data in a particular format, and close the resource. Java provides several built-in alternatives for this. The choice depends on whether you only need to read data line by line or whether you need to split and parse lines into values (such as numbers), whether you want to process large files efficiently, and what format the data is stored in.

## Adding Your Own File to a Project

Suppose we have the following text file:

```text
name,age
Maija,25
Matti,30
```

The file contains information about people. The first line contains column names, while the following lines contain person data. The values are separated by commas. This file format is called a **CSV file** (*comma-separated values*), and it is a very common way of storing tabular data in a text file.

Save such a file as `data.csv` in the root directory of the project.
To allow IDEA to access the file when running the program, define the project's root directory as the working directory. This can be done via run <i class="bi bi-chevron-right"></i> Edit Configurations.
Select the class containing the `main` method. In the Working directory field, make sure the directory points to the root of the source code, usually ending in `src` or `src/main/java`.

Now we can use the `data.csv` file in our program.

***

## Processing Files with the Files API

The `Files` class (more specifically, the API in the `java.nio.file` package) provides a straightforward way to read an entire file at once when the file size is reasonably small.
You can, for example, read the entire file into memory as a list of lines using `Files.readAllLines` or as a single string using `Files.readString`.
If the file contains numbers, dates, or other structured data, those values must still be processed separately.

Let's implement the earlier example using `Files.readAllLines`. This method reads the entire file into memory as a list of strings, where each string corresponds to one line in the file. We can then iterate through the list and split each line into columns.

```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {
    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("data.csv"));
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split(",");
                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                IO.println("Name: " + name + ", Age: " + age);
            }
        } catch (IOException e) {
            IO.println("File not found or cannot be read: " + e.getMessage());
        } finally {
            // Nothing needs to be closed manually because the Files API handles it for us
        }
    }
}
```

Using the same approach—processing the entire file at once—you can also write data to a file.
When the entire contents are available as a single string, you can use `Files.writeString`.
Before writing, you must ensure that the destination directory exists.

```java,ignore
Path path = Path.of("data", "result.txt");
Files.createDirectories(path.getParent());
```

```java
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriteFileWriteString {
    public static void main(String[] args) {
        Path path = Path.of("data", "result.txt");

        try {
            Files.createDirectories(path.getParent()); // Make sure the data folder exists

            String content = "Hello!\nThis is a new file.\n";
            Files.writeString(path, content, StandardCharsets.UTF_8);

            IO.println("Written to: " + path.toAbsolutePath());

        } catch (IOException e) {
            IO.println("Writing failed: " + e.getMessage());
        }
    }
}
```

When the data naturally exists as lines, it is often convenient to use `Files.write()`.

```java
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class WriteFileLineByLine {
    public static void main(String[] args) {
        Path path = Path.of("data", "data.csv");

        List<String> lines = List.of(
                "name,age",
                "Maija,25",
                "Matti,30"
        );

        try {
            Files.createDirectories(path.getParent());
            Files.write(path, lines, StandardCharsets.UTF_8);

            IO.println("Written to: " + path.toAbsolutePath());

        } catch (IOException e) {
            IO.println("Writing failed: " + e.getMessage());
        }
    }
}
```

It is also possible to append text to the end of an existing file. This requires a few extra arguments.

```java,ignore
// ...

Path path = Path.of("data", "result.txt");

String line = "A new line appended to the end of the file.\n";

Files.writeString(
        path,
        line,
        StandardCharsets.UTF_8,         // UTF-8 encoding
        StandardOpenOption.CREATE,      // Create a file if it doesn't exists
        StandardOpenOption.APPEND       // Append to the end of a file
);

// ...
```

***

## Reading with a Scanner

`Scanner` is well suited for situations where you want to read text piece by piece: for example, one line at a time, up to the next whitespace, or even the next number.
You can think of a `Scanner` object as a reading head or cursor that advances whenever methods such as `nextLine` or `next` are called.
When there is nothing left to read, `hasNext` returns `false`.

`Scanner` can also read numbers and other primitive types directly (`nextInt`, `nextDouble`, etc.), reducing the amount of manual parsing required.

The following example reads a file using a `Scanner`. Reading is performed line by line, and each line is split into columns.

```java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    public static void main(String[] args) {
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File("data.csv"));

            // Skip header row
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Read lines until the end of file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                IO.println("Name: " + name + ", Age: " + age);
            }

        } catch (FileNotFoundException e) {
            IO.println("File not found: " + e.getMessage());
        } finally {
            // Close the scanner
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
```

Data is not always this nicely formatted.
A file might contain numbers separated by spaces, commas, semicolons, or line breaks.

Suppose the file `measurements.txt` contains:

```text
12  8,  5
-3; 10  7
error  2  1.5  3
```

You want to read all numeric values regardless of how they are separated.
This is somewhat awkward with the `Files` API but is easy with `Scanner`.
Using `useDelimiter`, you can define which characters act as separators.
For example `useDelimiter("[\\s,;]+")`
defines spaces, commas, semicolons, and line breaks as delimiters.
Everything between delimiters forms a **token**, which can be read using `next`.

```java
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
public class ReadNumbersWithScanner {
    public static void main(String[] args) throws IOException {
        double sum = 0.0;
        int count = 0;

        Scanner sc = new Scanner(new File("measurements.txt"));
        try {
            sc.useLocale(Locale.US);  
            sc.useDelimiter("[\\s,;]+");

            while (sc.hasNext()) {
                if (sc.hasNextDouble()) {
                    sum += sc.nextDouble();
                    count++;
                } else {
                    sc.next();  // Skip token which isn't a valid dumber
                }
            }
        } finally {
            sc.close();
        }

        IO.println("Numbers: " + count);
        IO.println("Sum: " + sum);
        IO.println("Average: " + (count == 0 ? 0 : sum / count));
    }
}
```

A `Scanner` can only read files; it cannot write to them.

***

## Streams

In addition to collections (see [Chapter 6.2](./02-processing-collections-stream-api.md)), files and other external resources can also be processed using the `Stream` interface.
Streams are particularly useful when multiple sequential operations are performed on data, such as transformations (`map`), filtering (`filter`), and collecting (`toList`, `collect`).
The processing is described as a chain that clearly communicates what happens to the data step by step.

Reading a file as a stream typically begins with `Files.lines(path)`.
Unlike `readAllLines`, which loads the entire file into memory, `Files.lines` reads lines lazily as the stream is consumed.
This makes it suitable even for large files.
Since the file remains open while reading, the stream must eventually be closed.

Let's start with a simple example that repeats the earlier pattern, but now uses `Files.lines` and stream processing.
We use the previously learned `map` operation to transform each row into an array. As the terminal operation, we use `forEach`, which executes the given lambda expression for each row. Here we parse the rows in the same way as in the earlier examples and finally print the names and ages.

```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReaderStream {
    static void main() {
        try {
            Files.lines(Paths.get("data.csv"))
                    .skip(1) // Skip the header row
                    .map(line -> line.split(",")) // Split the row into columns
                    .forEach(parts -> {
                        String name = parts[0]; // First column is the name
                        int age = Integer.parseInt(parts[1]); // Second column is the age, parse as int
                        IO.println("Name: " + name + ", Age: " + age);
                    });
        } catch (IOException e) {
            IO.println("File not found or cannot be read: " + e.getMessage());
        }
    }
}
```

Let's extend the example a little. Filter out people younger than 18 and finally print the names in alphabetical order.

```java
//-import java.io.IOException;
//-import java.nio.file.Files;
//-import java.nio.file.Paths;
//-import java.util.List;
//-
//-public class FileReaderStream {
//-    static void main() {
//-        try {

List<String> names = Files.lines(Paths.get("data.csv"))
        .skip(1) // Skip the header row
        .map(line -> line.split(",")) // Split the row into columns
// HIGHLIGHT_GREEN_BEGIN
        .filter(parts -> Integer.parseInt(parts[1]) >= 18) // Filter out people under 18
        .map(parts -> parts[0]) // Keep only the name
        .sorted() // Sort names alphabetically
        .toList(); // Collect results into a list

names.forEach(IO::println); // Print the names
// HIGHLIGHT_GREEN_END

//-        } catch (IOException e) {
//-            IO.println("File not found or cannot be read: " + e.getMessage());
//-        }
//-    }
//-}
```

Stream-based processing is very convenient when the processing is relatively simple and progresses linearly. However, it is worth noting that stream-based processing can significantly complicate debugging.

<details><summary><i class="bi bi-stars jyu-gold"></i> Optional information: More about challenges of stream-based processing</summary>

* Single-use nature: A `Stream` object can only be used once, after which it must be closed. If you want to process the same data again, you must create a new stream.
* Debugging: Chaining hides intermediate results. If a `map` or `filter` step throws an exception, the stack trace tells you which lambda was being executed, but not necessarily which row or intermediate value caused the problem without additional logging or the use of the 
[`peek`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#peek-java.util.function.Consumer-)
method.
* Error handling: Unchecked exceptions inside lambda expressions (for example, `NumberFormatException` in a call to `Integer.parseInt`) must be handled separately because lambda expressions do not allow checked exceptions to be thrown directly. This can make error handling more complicated than in a traditional loop.
* Laziness can be surprising: A stream does nothing until a terminal operation (`forEach`, `toList`, `collect`, `count`, etc.) is executed. This can be surprising because the code appears to read a file, yet nothing happens if the terminal operation is missing. Likewise, exceptions are not thrown when the file is opened but only when the stream is consumed.

</details>

***

## BufferedReader and BufferedWriter

[BufferedReader](https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html)
and 
[BufferedWriter](https://docs.oracle.com/javase/8/docs/api/java/io/BufferedWriter.html)
are the traditional tools for processing text files when you want to read and write line by line and maintain precise control over the processing loop.
They buffer I/O operations, meaning they do not perform a system call for every individual character but instead read and write larger chunks of data. This improves performance, especially when working with large files, and makes processing more predictable.
We leave studying these classes as an optional, self-directed exercise.

***

## JSON Files

JSON (*JavaScript Object Notation*) is a popular data exchange format used extensively in web development.
JSON files are text files containing key-value pairs and may contain complex data structures such as arrays and objects.

JSON can contain the following types of values:

* String (`"Maija"`)
* Number (`25`)
* Boolean (`true` / `false`)
* Null (`null`)
* Array (`[ ... ]`)
* Object (`{ ... }`)

For example, the file `people.json` could look like this:

```json
[
  {
    "name": "Maija",
    "age": 25,
    "city": "Jyväskylä"
  },
  {
    "name": "Matti",
    "age": 30,
    "city": "Tampere"
  }
]
```

Compared to CSV, the advantage of JSON is that fields can be nested.
For example, `"city"` could itself be an object containing `"previous_cities"` and `"current_city"`.
Rows are therefore not tied to a single tabular structure.
The disadvantage is that the structure is usually somewhat heavier to read visually compared to CSV. There is also more syntax involved, making it somewhat more difficult to process manually without the help of a library.

***

## Processing JSON Files with the Jackson Library

JSON can of course be processed manually by splitting strings, but in practice this approach is error-prone.
Therefore, JSON is usually processed using a dedicated library.
One of the most common Java libraries for this purpose is **Jackson**.

Add the following dependency to your `pom.xml` file:

```xml
<dependency>
    <groupId>tools.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>3.0.4</version>
</dependency>
```

After adding the dependency, refresh the Maven project.
The following example reads the file `people.json` into a list of `Person` objects.
We will explain the code in more detail afterward.

```java
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.util.List;

public class ReadJson {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        Path path = Path.of("data", "people.json");

        try {
            List<Person> people = mapper.readValue(
                    path.toFile(),
                    new TypeReference<List<Person>>() {}
            );

            people.forEach(p ->
                    IO.println(p.name() + " (" + p.age() + "), " + p.city())
            );

        } catch (JacksonException je) {
            IO.println("Failed to read JSON: " + je.getMessage());
        }
    }
}
```

**Reading JSON from a file:** Reading begins by creating an `ObjectMapper` object, which is the Jackson library's main tool for converting JSON into Java objects and vice versa.
The `readValue` method takes a JSON file and information about the type into which the JSON should be converted.

In order for the conversion to be possible, we must model the JSON data using Java objects.
Let's create a Java class `Person` with fields `name`, `age`, and `city`, matching the fields in the JSON file.
We also create matching getters and setters as well as a no-argument constructor. A class of this form is required by Jackson so that it can create objects from JSON.

```java,ignore
public class Person {
    private String name;
    private int age;
    private String city;

    public Person() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
```

Reading the file may fail, so the call to `readValue()` should be wrapped in a `try-catch` structure.
The Jackson library throws a `JacksonException`, which is a subclass of `IOException`.

**Writing JSON to a file** is slightly easier than reading it.
Writing is performed with the `writeValue` method, which accepts a file and an object to be stored, and converts the object into JSON format.
In this situation, two different things may fail:
1. Creating the directory may fail; `createDirectories` throws an `IOException`.
2. JSON processing may fail if the file cannot be written, the JSON is invalid, or the type conversion fails; `writeValue` throws a `JacksonException`.

Both exceptions should be handled separately.

The following example writes a list of people to the file `data/people-new.json`:

```java
import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.JacksonException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class WriteJson {
    static void main() {
        ObjectMapper mapper = new ObjectMapper();

        List<Person> people = List.of(
                new Person("Aino", 22, "Turku"),
                new Person("Pekka", 41, "Oulu")
        );

        Path path = Path.of("data", "people-new.json");

        try {
            Files.createDirectories(path.getParent());
            mapper.writeValue(path.toFile(), people);
            IO.println("JSON written to: " + path.toAbsolutePath());
        } catch (IOException e) {
            IO.println("Failed to create directory: " + e.getMessage());
        } catch (JacksonException je) {
            IO.println("JSON processing failed: " + je.getMessage());
        }
    }
}
```

***

## Record Classes for Modeling JSON Data

Let's briefly introduce Java's *record* feature.
A Java record is a special kind of class designed for modeling small, straightforward data structures such as structured JSON data.
When you define a record, Java automatically generates a number of common routines for you.
You automatically get:

* a constructor that accepts all fields as arguments,
* accessors corresponding to the fields, whose names are simply the field names themselves (for example, `name` instead of `getName`),
* implementations of `equals` and `hashCode`,
* a useful `toString` implementation.

The *components* (attributes) of a record are effectively `final` fields, meaning that records are largely immutable objects.

For this reason, records are a natural match for JSON libraries.
A JSON object often corresponds directly to a single "bundle of data" that can be modeled with a record without any additional boilerplate code.
Whereas a traditional class typically requires fields, constructors, and getters to be written separately, a record expresses the same idea in a single line.

It is worth noting that methods and validation logic can still be added to a record.
However, the primary purpose of a record is to remain small and focused on representing data.
If a class begins to accumulate complex behavior or large amounts of mutable state, a regular class is usually a better choice.

Let's define a data type using a record:

```java
public record Person(String name, int age, String city) {}
```

In this way, the relatively long `Person` class can be replaced by a single line.
In this particular case, the record provides essentially the same functionality as the traditional class defined earlier.

***

## Exercises

<task>
<task-title>Exercise 6.9: Words
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/6-9-words/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part6/exercise9">Complete this exercise in TIM</a></task-link>
</task>

<task>
<task-title><i class="bi bi-stars jyu-gold"></i> Exercise 6.10: Load people from a JSON file 
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/6-10-json-1/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part6/exercise10">Complete this exercise in TIM</a></task-link>
</task>


<task>
<task-title>Exercise 6.11: CSV->JSON
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/6-11-csv-json/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part6/exercise11">Complete this exercise in TIM</a></task-link>
</task>


<task>
<task-title>Exercise 6.12: Better Calculator
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/6-12-calculator/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part6/exercise12">Complete this exercise in TIM</a></task-link>
</task>