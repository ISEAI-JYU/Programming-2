# Towards Object-Oriented Programming


## Learning goals
- You take a step from the procedural programming mindset of data and functions toward the object-oriented programming mindset of state and methods.
- You understand the concept of an object: data and functionality combined into a single package.
- You understand how modeling concepts as objects can make building programs easier.

So far, we have mostly created programs in which data is stored in variables and processed using functions. 
This style of programming is called *procedural programming*.
In this course, we will become familiar with another programming paradigm: *object-oriented programming*.

Object-oriented programming is a very broad topic, and in this course we will cover its theory selectively, focusing especially on the needs of this study module. 
<!-- A deeper understanding of the theory can be gained, for example, in the course Object-Oriented Design and Programming. -->

## Basic Idea

The idea of object-oriented programming is to create structures called objects that contain both data and the functionality used to manipulate that data. Objects may be similar to one another, but each object has its own *state*, which can change during program execution. The state of an object is stored in its own variables, called *attributes*.

An object can also have its own subroutines, called *methods*. A method is a subroutine that belongs to an object and can inspect and modify the state of the object that owns it. 
Before objects can be created, a *class* must first be defined. A class describes the structure of an object.

Objects are meant to be responsible for the functionality within their own area of responsibility. Cooperation and communication between objects through method calls are central concepts in object-oriented programming. In some programming languages, this may be referred to as message passing.

A minimal program utilizing objects could look like this:

```java
class Cat {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }
}

void main() {
    Cat cat1 = new Cat();
    cat1.setName("Miuku");

    Cat cat2 = new Cat();
    cat2.setName("Katti");

    IO.println(cat1.getName());
    IO.println(cat2.getName());
}
```

In the example, a class called `Cat` is first defined, and objects are then created from it in the main program. Each object contains a string attribute called `name` and two methods. 
The name of an object can be changed by calling its `setName` method and retrieved by calling its `getName` method. Both objects have their own state—that is, their own name. The state of one object does not affect the state of another.

From this simple example, we can see how data can be grouped inside objects. In this case, the object's only attribute, `name`, is accessed directly through methods. In practice, this kind of direct manipulation is uncommon in production code, but we will return to this topic later.

It is hardly worth creating separate objects for a single variable, but the benefits of object-oriented programming quickly become apparent when the amount of data increases. 
Consider a situation where we want to store information about competitors participating in a contest. There may be many competitors, and for each one we need to store at least a name, a competitor number, and a score.
Using objects, we can keep a single competitor's data and the functionality related to modifying that data within the same structure, making the information easier to manage.

```java
class Competitor {
    private String name;
    private int number;
    private int score;

    public Competitor(String name, int number, int score) {
        this.name = name;
        this.number = number;
        this.score = score;
    }

    // ...

    public void printInformation() {
        IO.println(String.format("%d: %s, %d points", number, name, score)); 
    }
}

void main() {
    Competitor[] competitors = {
        new Competitor("A", 2, 20),
        new Competitor("B", 4, 15),
        new Competitor("C", 6, 10)
    };

    for (Competitor competitor : competitors) {
        competitor.printInformation();
    }
}
```

Creating an equivalent program without object-oriented programming requires some mental gymnastics. One possible approach would be to use three separate arrays: one containing names, another containing competitor numbers, and a third containing scores. However, you would then need to ensure that each competitor's information is always stored at the same index in all arrays, which is cumbersome in practice and prone to errors.

Objects make it possible to package data and functionality together in a clearer and more organized manner.
Organizing related data and functionality within the same structure makes program code easier to understand and extend. However, this is not the only advantage of object-oriented programming. We can also hide some of a class's implementation details for internal use only, allowing programmers who use the class to access only a carefully defined public interface without needing to understand its internal workings.
In Part 3, we will also explore polymorphism, inheritance, and interfaces, where the benefits of object-oriented programming become even more apparent.

## Main Program in Java

Java is a programming language that is particularly oriented toward object-oriented programming. Historically, even the main program had to be placed inside a class. In newer versions of Java, this has changed; writing simple programs has been made easier so that the main program no longer needs to be wrapped inside a class.
You can read more about these changes from [Java 21 documentation](https://openjdk.org/jeps/445) and 
[Java 25 documentation](https://openjdk.org/jeps/512) published in autumn 2025.

Today, the straightforward main program used in these materials is sufficient:

```java
void main() {
    IO.println("Hello World!");
}
```

Previously, a minimal Java program might have looked like this:

```java
public class HelloWorld {
    public static void main(String[] args) {
        IO.println("Hello World!");
    }
}
```

The older-style example introduces a few new concepts that we will examine more closely in this section. First, a class is defined using the `class` keyword. In the example, the class is named `HelloWorld`. The main program, `main`, is placed inside this class.
In the older style, the main program must also include the `static` modifier, which we will examine in more detail shortly.

Because the simpler style of writing a main program is a relatively new feature, the majority of examples found online and in books still use the original style, where the main program is embedded inside a class. This is useful to keep in mind when searching for information.