# Exception Handling

### Learning Objectives

* Understand what an exception is and how it differs from the normal flow of program execution.
* Know the difference between checked and unchecked exceptions.
* Be able to handle exceptions using the `try-catch-finally` structure.
* Be able to throw exceptions (`throw`) and declare them in method signatures (`throws`).
* Understand the benefits of exceptions for code readability and error handling.
* Be able to create custom exception classes.

An *exception* (short for *exceptional event*) is a situation that occurs during program execution and interrupts the normal flow of instructions.
Exceptions may occur, for example, in the following situations:

* The user provides invalid input (for example, text when a number is expected).
* The program attempts to read a file that does not exist.
* A network or database connection is lost during execution.
* A division by zero occurs.
* A list or array index outside the valid range is accessed.
* A method is called through a reference that is `null`.

Java provides a built-in exception handling mechanism that allows such error situations to be handled in a controlled manner.
Using this mechanism, a programmer can define how a program should react to different kinds of errors without causing the entire application to crash.

***

## Throwing and Catching Exceptions

Exception handling in Java is based on three main concepts:

* *Throwing exceptions*: When an error occurs, an exception object is created either by the program itself or by the JVM. The JVM then interrupts the normal execution flow and begins searching the call stack for a suitable handler.
* *Catching exceptions*: The programmer can define code blocks that handle specific exceptions using a `try-catch` structure.
* *Final cleanup*: A `finally` block is executed regardless of whether an exception occurred, typically to release resources or restore program state.

We will return to concrete examples shortly, but let us first examine how the mechanism works at a higher level.

When an error occurs during the execution of a method, the method creates an exception object describing the error and passes it to the runtime system.
The exception object contains information such as the type of the exception and the state of the program at the moment the error occurred.
Creating and passing this exception object to the runtime system is called *throwing an exception*.

Once an exception has been thrown, the runtime system begins searching the call stack for a suitable *exception handler* in the reverse order of method calls.
A handler is considered suitable if it can handle the type of exception that was thrown.
If a suitable handler is found, the exception is delivered to it. At that point, the exception is said to be *caught*.

If the runtime system examines the entire call stack without finding a suitable handler, the thread in which the error occurred terminates.
If that thread happens to be the program's main thread, the entire program crashes.

The following diagram roughly illustrates the process of throwing and catching an exception.
Assume that `main()` calls `a()`, which calls `b()`, which in turn calls `c()`.

```bob
                         +-----.-----------------------+ 
"Throws exception" ------|"c()"| "Method where error"  |
                         |-----' "occurred"            |----.
                         +-----------------------------+    | "Search for"
                                        ^                   | "a suitable"
                                        | calls             | "handler"
                         +-----.-----------------------+    |
"Throws exception" ------|"b()"| "Method without an"   |<---'
       "forward"         |-----' "exception handler"   |----.
                         +-----------------------------+    | "Search for"
                                        ^                   | "a suitable"
                                        | calls             | "handler"
                         +-----.-----------------------+    |
 "Catches exception" ----|"a()"| "Method containing"   |<---'
                         |-----' "exception handler"   |
                         +-----------------------------+
                                        ^
                                        | calls
                         +-----------------------------+
                         |          "main()"           |
                         +-----------------------------+                      .
```

The following happens:

* Method `c()` throws an exception, perhaps while handling a network connection, but `c()` does not contain a suitable handler.
* The runtime system examines the next method on the call stack, which is `b()`.
* Method `b()` also lacks a suitable handler, so the search continues with `a()`.
* Method `a()` contains a suitable handler that catches the exception.
* Program execution continues after the handler in `a()` has completed.

***

## Checked and Unchecked Exceptions

Java divides exceptions into two categories: *checked* and *unchecked* exceptions.
The distinction comes from the fact that handling of checked exceptions is verified by the compiler, whereas handling of unchecked exceptions is not.

A *checked exception* represents a problem arising from the environment or program input that can often be handled in a controlled way.
Typical examples include file operations, network communication, and database access.
For example, opening a file may fail because the file does not exist or because the program lacks permission to read it, even though the code itself is perfectly correct.
Examples of checked exceptions include:

* `IOException`, which represents input/output problems such as failed file access.
* `SQLException`, which relates to database operations.

Checked exceptions inherit from the class `Exception`.

When the compiler encounters code that may throw a checked exception, it essentially tells the programmer:
"*I see that you are doing something potentially risky (such as reading a file). I will not compile your program until you show that you have considered the possible problems.*"

In this situation, the programmer must either:

* handle the exception using a `try-catch` structure (similar to method `a()` in the previous diagram), or
* declare with `throws` that the exception may be passed to the caller (similar to methods `b()` and `c()` in the diagram).

If neither is done, the code will not compile.
This requirement is known as the *catch or specify* requirement.

An *unchecked exception* is an exception that is not checked at compile time and therefore does not need to be handled or declared.
Such exceptions can still occur during program execution.
Common unchecked exceptions include:

* `NullPointerException`, caused by attempting to use a reference whose value is `null`.
* `IllegalArgumentException`, caused by providing an invalid argument to a method.
* `ArrayIndexOutOfBoundsException`, caused by accessing an array index outside the valid range.
* `ArithmeticException`, caused by arithmetic errors such as division by zero.

Unchecked exceptions inherit from `RuntimeException`.

Unchecked exceptions typically represent programming mistakes.
Handling them with `try-catch` is not required and is usually not recommended.
For example, a `NullPointerException` is often a clear indication of a bug in the program.
Although a `NullPointerException` can be caught with `try-catch`, doing so usually only hides the bug rather than fixing it.

***

## Syntax

A `try-catch` structure looks like this:

```java,ignore
try {
    // Code block where an exception may occur
} catch (ExceptionType exception) {
    // Code block that handles the exception
}
```

A `throws` declaration is placed in a method signature:

```java,ignore
void method() throws ExceptionType {
    // Method implementation that may throw an exception
}
```

Multiple exceptions can be handled separately:

```java,ignore
try {
    // Code block where an exception may occur
} catch (ExceptionType1 e1) {
    // Handles ExceptionType1
} catch (ExceptionType2 e2) {
    // Handles ExceptionType2
}
```

If a method can throw multiple checked exceptions, they can be declared in the `throws` clause separated by commas:

```java,ignore
void method() throws ExceptionType1, ExceptionType2 {
    // Method implementation that may throw multiple exceptions
}
```

***

## finally

A `finally` block is used in situations where resources opened during the `try` block must be released, or the execution environment must reliably be restored regardless of whether the operation succeeds or fails.
Typical examples include closing files, network connections, or other resources.

When `try-catch-finally` is used, execution proceeds as follows:

* The `try` block is executed.
* If an exception occurs, the appropriate `catch` block is executed.
* Finally, the `finally` block is always executed.

The following example reads a file using the `Scanner` class.
We will examine the `Scanner` class in more detail in [Chapter 6.5](05-file-handling.md), but briefly: a `Scanner` object can be used to read text from a file one character or one line at a time.
When using a traditional `try-catch` structure, a `Scanner` object does not automatically close itself if an exception occurs, so it must be closed in a `finally` block.

```java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

void readFile(String path) {
    Scanner reader = null;

    try {
        reader = new Scanner(new File(path));
        while (reader.hasNextLine()) {
            IO.println(reader.nextLine());
        }
    } catch (FileNotFoundException e) {
        IO.println("File not found: " + path);
    } finally {
        if (reader != null) {
            reader.close();
        }
        IO.println("File reading operation finished.");
    }
}
//-void main() {
//-    readFile("./main.java");
//-}
```

In the example above, `finally` closes the reader even if file reading is interrupted by an exception.
If the object is not closed, the *file handle* (the operating-system resource reserved for reading the file) remains open.
Operating systems place strict limits on the number of files that can be open simultaneously by a process or by the system as a whole.
If a program runs in a loop or as a server and repeatedly opens files without closing them, the system's *file descriptor table* eventually fills up.
When this limit is reached, the program fails with an error such as 
`IOException: Too many open files`
and can no longer open new files.
The `finally` block ensures that the file reader is closed even if reading fails.

In modern Java, resource management is often handled using the *try-with-resources*
([JavaDoc](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html))
construct instead of `try-catch-finally`.
Using that approach, classes such as `Scanner` can automatically close themselves.
We will see examples of this later after we have learned about the `Closeable` interface, which allows resources to be declared as closable.

***

## Example of a Checked Exception

Suppose we want to read the contents of a file.
Let's use the modern Java method `Files.readString`.
Notice that when using this method, a `finally` block is not required because the method reads the entire file at once and automatically handles closing the file.
However, it is important to remember that not all methods in the `Files` class behave this way. For example, `Files.lines` 
([JavaDoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/nio/file/Files.html#lines(java.nio.file.Path)))
requires different handling.

The `Files.readString` method requires a `Path` object as its argument, so we will also use `Path.of`, which provides a convenient way to create a `Path` from a string.

```java
import java.nio.file.Files;
import java.nio.file.Path;

void main() {
    String content = readFile("data.txt");
    IO.println(content);
}

String readFile(String path) {
    return Files.readString(Path.of(path));
}
```

Our program fails because `Files.readString` *may* throw an `IOException`.
This is a checked exception.
Let's add a `throws IOException` declaration to the `readFile` method.

```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

void main() {
    String content = readFile("data.txt");
    IO.println(content);
}

String readFile(String path) throws IOException {
    return Files.readString(Path.of(path));
}
```

The program still does not compile correctly because the `throws` declaration merely passes the exception to the caller (similar to methods `b()` and `c()` in the earlier diagram).
Since `main()` does not contain a suitable handler, the exception ultimately causes the program to fail.
Let's handle the exception in `main()` using a `try-catch` block.

```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

void main() {
    try {
        String content = readFile("data.txt");
        IO.println(content);
    } catch (IOException e) {
        IO.println("Failed to read file: " + e.getMessage());
    }
}

String readFile(String path) throws IOException {
    return Files.readString(Path.of(path));
}
```

Now the program works and prints an error message even if the file `data.txt` does not exist.

***

## Example of an Unchecked Exception

Suppose we have a class `Person` with a method `getName()` that returns a person's name.
Let's create a list of people.

```java
import java.util.List;

public class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

void main() {
    List<Person> people = new ArrayList<>();
    people.add(new Person("Antti-Jussi"));
    people.add(new Person("Denis"));
}
```

Next, let's look at how `null` references can cause `NullPointerException`s.
Suppose that for some reason a `null` value ends up in the list.
This could happen, for example, if the data were read from an external source and one of the records was incomplete but was still added to the list.

```java
//- public class Person {
//-     private String name;
//-
//-     public Person(String name) {
//-         this.name = name;
//-     }
//-
//-     public String getName() {
//-         return name;
//-     }
//- }

void main() {
    List<Person> people = new ArrayList<>();
    people.add(new Person("Antti-Jussi"));
    people.add(new Person("Denis"));
    processList(people);

    for (Person p : people) {
        IO.println(p.getName());
    }
}

void processList(List<Person> people) {
    // For some reason a null reference
    // ends up in the list.
    people.add(null);
}
```

From the point of view of `main`, this problem is not obvious at all—it is simply doing its own job.
The real issue is that `processList` causes a side effect by modifying the contents of the list in a way that violates `main`'s assumption that the list contains only `Person` objects.
The problem becomes visible when `main` later attempts to call `getName()` on a `null` reference.

Of course, this situation could be handled using a `try-catch` structure or by explicitly checking whether `p` is `null` before printing.
However, that approach is rather unpleasant and does not address the root cause of the problem.

***

## Multiple Exception Objects

The same `try` block may throw multiple types of exceptions.
In that case, each exception type can have its own `catch` block.

In the example below, `Integer.parseInt` may throw a `NumberFormatException` if the input is not a valid number, and the division may throw an `ArithmeticException` if the divisor is zero.

```java
void main() {
    calculate("42", 2);   // succeeds
    calculate("abc", 2);  // NumberFormatException
    calculate("42", 0);   // ArithmeticException
}

void calculate(String input, int divisor) {
    try {
        int value = Integer.parseInt(input);
        int result = value / divisor;
        IO.println("Result: " + result);
    } catch (NumberFormatException e) {
        IO.println("Invalid number: " + input);
    } catch (ArithmeticException e) {
        IO.println("Division by zero is not allowed.");
    }
}
```

When exceptions represent different kinds of problems, they should generally be handled differently as well.

***

## Creating Your Own Exception Classes

Sometimes the built-in exception classes do not describe a problem precisely enough.
In such cases, you can create your own exception class.

The usual rule is:

* Extend `Exception` if you want a checked exception.
* Extend `RuntimeException` if you want an unchecked exception.

Here is an example of a custom checked exception:

```java
class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super(message);
    }
}

void main() {
    try {
        registerUser("abc");
        IO.println("User registered.");
    } catch (InvalidPasswordException e) {
        IO.println("Registration failed: " + e.getMessage());
    }
}

void registerUser(String password) throws InvalidPasswordException {
    if (password.length() < 8) {
        throw new InvalidPasswordException("Password must contain at least 8 characters.");
    }

    if (!password.matches(".*[A-Z].*")) { // regular expression checking for an uppercase letter
        throw new InvalidPasswordException("Password must contain at least one uppercase letter.");
    }

    // ... additional checks ...
}
```

The advantage of custom exceptions is that the error becomes semantically more precise.
The name of the exception immediately reveals which rule was violated.
Of course, password validation could also be implemented with ordinary `if` statements alone, but exceptions have the advantage that they force the error condition to be handled rather than accidentally ignored.

***

## The Benefits of Exception Handling

Exceptions significantly improve code readability and maintainability.
Before Java, error handling was often implemented using special return values.
For example, C programs commonly returned values such as `-1` or `NULL` to indicate failure.
This approach was error-prone because programmers could easily forget to check the return values.
Error handling also tended to become mixed together with the program's main logic, resulting in so-called "spaghetti code."

```text
openFile;
IF (success) {
    determineSize;
    IF (sizeKnown) {
        allocateMemory;
        IF (memoryAvailable) {
            readData;
            // ... etc ...
        } ELSE returnError -2;
    } ELSE returnError -3;
} ELSE returnError -5;
```

With exceptions, the program's *happy path* becomes much easier to read, and error handling is separated into dedicated blocks.

```java
try {
    openFile();
    determineSize();
    allocateMemory();
    readData();
} catch (FileError e) {
    handleError();
} catch (MemoryError e) {
    handleError();
}
```

Sometimes an error occurs deep inside a chain of method calls (for example method1 $\rightarrow$ method2 $\rightarrow$ method3 $\rightarrow$ readFile).
Without exceptions, every intermediate method would need to inspect return values and forward errors even if it has no idea how to handle them.
With exceptions, intermediate methods can essentially *duck* the exception.
The error automatically bubbles upward through the call stack until it reaches a method that is interested in handling it.

Because exceptions are objects, they form inheritance hierarchies.
This enables flexible error handling.
You can catch a very specific exception (such as `FileNotFoundException`) if you know how to fix that particular problem.
You can also catch a superclass (such as `IOException`) to handle an entire family of related problems.
A dangerous temptation is to catch a very general superclass such as `Exception`.
This catches nearly everything, but in reality you often do not know how to handle all possible problems.
For this reason, constructs such as
`catch (Exception e)`
should generally be avoided unless you genuinely intend to handle every possible exception—including unexpected programming bugs.
Handling exceptions at too general a level can hide programming errors and make debugging considerably more difficult.

Different programming languages take different approaches to exception handling.

For example:

* In [Python](https://docs.python.org/3/library/exceptions.html), all exceptions are effectively unchecked, and programmers are not required to declare them in advance.
* [C++](https://en.cppreference.com/w/cpp/error/exception.html) supports exceptions, but their use is generally less common than in Java.
* [Rust](https://doc.rust-lang.org/std/result/) does not have exceptions at all; instead, error situations are handled through the `Result` type, which forces programmers to explicitly deal with possible failures.

*Parts of this chapter are based on the [Java documentation](https://dev.java/learn/exceptions/) on exceptions.*