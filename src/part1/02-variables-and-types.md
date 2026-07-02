# Variables and Data Types

## Learning objectives

* You remember what variables and constants are.
* You remember what strings and lists are.
* You know Java's primitive data types.
* You know how strings, arrays, and lists are used in Java.

Programs process data stored in memory. In high-level programming languages, such as Java, human-readable names are used to refer to data stored in memory. Such a name that refers to data in memory is called a **variable**.

The programmer only needs to remember the name; the operating system and the computer's internal logic take care of the actual location of the data in memory.

Before a variable can be used, it must be **declared**:

```java,ignore
type variableName;
```

The variable's type is written before its name and indicates what kind of data the variable can contain, such as an integer, a decimal number, or a boolean value.

The variable name is an identifier chosen by the programmer and used to refer to the variable.

A name may contain letters and underscores. However, a variable name cannot be a reserved Java keyword, nor may it begin with a number.

After a variable has been declared, values can be **assigned** to it:

```java,ignore
variableName = expression;
```

The value of the expression on the right-hand side of the equals sign is stored in the variable named on the left-hand side.

If the variable previously contained another value, it is replaced by the new one.

```java
//-void main() {
    double interestRate; // Variable declaration, double = decimal number
    double principal;    // Variable declaration

    interestRate = 0.05; // Assign a value to the variable
    principal = 150.0;   // Assign a value to the variable
//- IO.println("interestRate = " + interestRate);
//- IO.println("principal = " + principal);
//-}
```

A variable cannot be used until a value has been assigned to it at least once.

For this purpose, Java allows a combined declaration and assignment statement:

```java,ignore
double principal = 0.05;

// Equivalent to:
double principal;
principal = 0.05;
```

A variable can also be part of an expression, allowing its value to be used as part of a larger computation.

```java
//-void main() {
    double interestRate = 0.05;
    double principal = 150.0;

    double principalWithInterest = (1 + interestRate) * principal;
//- IO.println("interestRate = " + interestRate);
//- IO.println("principal = " + principal);
//- IO.println("principalWithInterest = " + principalWithInterest);
//-}
```

In programming, an assignment is a **statement**, meaning a single executable instruction.

For example, the statement

```java,ignore
double principal = 150.0;
```

can be understood as:

> "Store the number 150.0 in memory at a location that will be referred to from now on as `principal`."

A variable's value remains unchanged until another statement modifies it.

Java data types can be divided into two main categories:
**primitive data types** and **reference data types**

All information in a computer is stored in memory as binary numbers (sequences of zeros and ones). Data types differ in how much memory they occupy, what kind of data they represent, and what rules govern their use.

Primitive types contain simple values such as integers and boolean values, whereas reference types contain more complex structures such as objects, arrays, and strings.

## Primitive Data Types

Java provides eight built-in primitive data types. These can be roughly divided into four categories: integers, floating-point numbers, characters, and boolean values.

### Integers

There are four integer types, which differ in their range and memory consumption. The most commonly used integer type is `int`.

| Type | Size (bytes/bits) | Approximate Range |
|--------|--------|--------|
| byte | 1 byte (8 bits) | -128 ... 127 |
| short | 2 bytes (16 bits) | -32,768 ... 32,767 |
| int | 4 bytes (32 bits) | approx. -2 billion ... 2 billion |
| long | 8 bytes (64 bits) | approx. ±9 × 10^18 |

<details><summary><b><i class="bi bi-info-circle"></i> Note:</b> Type of variable doesn't change automatically</summary>

In many dynamic programming languages such as Python and JavaScript, integers may not have a maximum value. Additional memory is allocated as needed, or less significant digits are rounded away.
**This is not the case in Java.**
If the result of a calculation exceeds the range of the variable type, an **overflow** occurs and the value wraps around within the allowed range:

```java
//-void main() {
    int largeNumber = 2000000000;
    IO.println("largeNumber = " + largeNumber);

    largeNumber += 1000000000;
    IO.println("largeNumber = " + largeNumber);
//-}
```
Therefore, the possible value range must be considered when writing programs.
If there's a change that overflow happens you need to change the data type.
Java also provides 
[`BigInteger`](https://docs.oracle.com/en/java/javase/25/docs/api//java.base/java/math/BigInteger.html)
type for working with extremely large integers, but it is not covered in this course.
</details>

### Floating-Point Numbers

Floating-point types are used to represent decimal numbers. The most common one is `double`.

| Type   | Size              | Precision                     |
| ------ | ----------------- | ----------------------------- |
| float  | 4 bytes (32 bits) | approx. 7 significant digits  |
| double | 8 bytes (64 bits) | approx. 15 significant digits |

<details><summary><b><i class="bi bi-info-circle"></i> Note:</b> Floating-point might not be accurate</summary>


Java uses the IEEE 754 standard for floating-point arithmetic. Although these types are designed to represent decimal values, they differ from ordinary mathematical decimal numbers in several important ways:

```java
void main() {

    // Rounding errors may occur
    double roundingError = 0.1 + 0.2;
    IO.println("roundingError = " + roundingError);

    // Division by zero is defined
    double negativeInfinity = -1.0 / 0.0;
    IO.println("negativeInfinity = " + negativeInfinity);

    double positiveInfinity = 1.0 / 0.0;
    IO.println("positiveInfinity = " + positiveInfinity);

    // 0/0 does not throw an exception
    double nan = 0.0 / 0.0;
    IO.println("nan = " + nan);
}
```

Floating-point numbers therefore have special values:
`Infinity`,
`-Infinity` and
`NaN` = **N**ot **A** **N**umber.
In addition, calculations involving floating-point values may contain small inaccuracies due to their binary representation and rounding behavior.

Java also provides the 
[`BigDecimal`](https://docs.oracle.com/en/java/javase/25/docs/api//java.base/java/math/BigDecimal.html)
type for applications requiring highly accurate arithmetic, but it is not covered in this course.
</details>

### Characters

A single character is stored in a `char` variable, which uses 2 bytes of memory.

### Boolean Values

Java provides the `boolean` type for truth values. It has only two possible values: `true` or `false`.

## Reference Data Types

Unlike primitive types, a reference-type variable does not contain the actual data itself. Instead, it contains a small fixed-size value called a **reference**.
Through this reference, a program can access the actual data, whose size or contents might not be known until runtime.

In practice, almost every Java type other than the primitive types is a reference type.
For example `String` is a reference type and likewise arrays and lists.
Starting from Chapter 2, we will explore object-oriented programming. In Java, all objects are reference types.

All reference-type variables can be assigned the special value `null`.
A **null reference** indicates that the variable does not currently refer to any data.
Attempting to read or modify a variable whose value is `null` usually causes a runtime error:

```java,ignore
String text = null;
String upperCaseText = text.toUpperCase();
IO.println(upperCaseText);
```
```
java.lang.NullPointerException: Cannot invoke "String.toUpperCase()" because "<local1>" is null
	at main.main(main.java:3)
```

Since this error occurs only during execution, it is generally the programmer's responsibility to ensure that variables and method parameters do not contain null references.
Such checks can be implemented using conditional statements, which are introduced in the next section.



<details><summary><i class="bi bi-stars jyu-gold"></i> Optional information: Why do both primitive and reference types exist?
</summary>

There are several reasons why Java distinguishes between these two categories of data types.

The first reason relates to performance and memory management.
If all variables were value-based (like primitive types), programs would consume a great deal of unnecessary memory and run more slowly, especially when handling large data structures.
For example, imagine a variable representing a `book` containing 1,000 pages of text. Every time we wanted to process the `book` variable, the entire contents of the `book` would need to be copied in memory. This would be highly inefficient.
Reference types solve this problem by allowing variables to refer to an object located elsewhere in memory without copying the entire object every time it is used. 

The second reason is shared state.
Often, multiple parts of a program need to access and modify the same data. For example, it makes sense for a bank-account object to be shared by deposit, withdrawal, and balance-checking operations.
In a purely value-based system, the entire bank-account object would need to be copied every time money was withdrawn, transferred, or the balance checked. This could easily result in inconsistent copies containing different states of the same account. 

The third reason is dynamic size.
Reference types enable dynamically growing and shrinking data structures such as linked lists, stacks, and queues.
These are difficult to implement efficiently as value types because the size of a value-type variable is fixed at compile time. 

The fourth reason is object-oriented programming.
References enable polymorphism in Java. Since a variable contains only a reference, it can point to any object that is compatible with the declared type.

```java,ignore
Animal pet = new Dog();
pet = new Cat();
```

If these were pure value types, a variable of type `Animal` would require a fixed amount of memory.
If a `Cat` object required more memory than had been allocated for an `Animal`, the program would fail.
Because variables contain references rather than the actual object data, the size of the variable remains constant regardless of the size of the referenced object.

</details>

## Literals

A **literal** is a fixed value written directly into source code.
Each data type has its own syntax for literals.

* **Characters** (`char`):
Character literals are enclosed in single quotation marks: `'A'`, `'*'`
,`'x'`.
Special characters begin with a backslash:
`'\n'` (new line),
`'\u03A9'`  (Greek capital omega Ω) and
`'\t'` (tab character)

*  **Integers** (`byte`, `short`, `int`, `long`):
Integer literals are written directly as numbers:
`42`,
`-7` and
`0`.
A `long` literal must end with either an uppercase or lowercase `L` or `l`:
`12345678901L`.

*  **Floating-Point Numbers** (`float`, `double`):
Floating-point literals are written using a decimal point:
`3.14`,
`-0.001` and
`2.0`.
Scientific notation may also be used:
`1.5e3` (1.5 × 10³ = 1500) and
`2.0E-4` (2.0 × 10⁻⁴ = 0.0002).
By default, decimal-number literals are of type `double`.
To create a `float` literal, append `F` or `f`. For example `3.14f`.

* **Boolean Values** (`boolean`):
Boolean literals are written using the keywords:
`true` and `false`.

```java
//-void main() {
    char character = 'A';
    //- IO.println("character = " + character);

    int number = 123;
    //- IO.println("number = " + number);
    long largeNumber = 12345678901L;
    //- IO.println("largeNumber = " + largeNumber);

    double decimalNumber = -2.0;
    //- IO.println("decimalNumber = " + decimalNumber);
    float smallDecimal = 2.0f;
    //- IO.println("smallDecimal = " + smallDecimal);
    double scientificNotation = 1.5e-2;
    //- IO.println("scientificNotation = " + scientificNotation);

    boolean truthValue = true;
    //- IO.println("truthValue = " + truthValue);
//-}
```

## Wrapper Classes

Each primitive type in Java has a corresponding **wrapper class**.
A wrapper class wraps a primitive value inside an object, allowing primitive values to be treated as objects.
For example: for primitive type `int` the wrapper class is `Integer`.
Wrapper classes provide useful methods and constants such as `toString()` and `MAX_VALUE`.
The following table shows the relationship between primitive types and wrapper classes:

| Primitive Type | Wrapper Class |
| -------------- | ------------- |
| byte           | Byte          |
| short          | Short         |
| int            | Integer       |
| long           | Long          |
| float          | Float         |
| double         | Double        |
| char           | Character     |
| boolean        | Boolean       |

The following example demonstrates the use of wrapper-class constants, methods and
overflowing of range
```java
void main() {
    byte oneByte = Byte.MAX_VALUE;
    short twoBytes = Short.MAX_VALUE;
    IO.println(oneByte);
    IO.println(twoBytes);
    IO.println(Short.toString(twoBytes).charAt(0));

    int maximum = Integer.MAX_VALUE;
    IO.println( maximum + " is the largest value that can be stored in an int variable");
    int overflow = Integer.MAX_VALUE + 1;
    IO.println("Overflowing the range:");
    IO.println(overflow);
}
```

### Strings

In Java, strings are not considered primitive data types.
However, Java provides special syntax for creating strings. A string can be created by enclosing characters in double quotation marks :

```java
//-void main() {
    String text = "I am studying programming!";
//-    IO.println("text = " + text);
//-}
```

Strings in Java are **immutable**.
If you perform an operation on a string, you receive a new string as the result. 
The original string remains unchanged.
For example:

```java
//-void main() {

    String immutable = "This string is immutable.";
    IO.println("immutable = " + immutable);

    immutable.concat(" Or is it?");
    IO.println("immutable = " + immutable);
//-}
```

The *new* string returned by `concat()` is not stored anywhere, so it is discarded and
the original string remains unmodified.
To modify the value stored in a string variable, you must assign the new string back to the variable:

```java
//-void main() {
String immutable = "This string is immutable.";
//-IO.Printlnt("immutable = " + immutable);

// HIGHLIGHT_GREEN_BEGIN
immutable = immutable.concat(" Or is it?");
// HIGHLIGHT_GREEN_END
//- IO.println("immutable = " + immutable);
//-}
```

String contains numerous useful methods. Some of the most commonly used ones are listed below.
<!-- The full documentation of can be found [here](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/String.html). -->

| Method | Description |
|----------|-------------|
| `string.charAt(index)` | Returns the character at the specified index. |
| `string.length()` | Returns the number of characters in the string. |
| `string.trim()` | Returns a copy of the string with leading and trailing whitespace removed. |
| `string.replace(target, replacement)` | Returns a copy of the string where occurrences of `target` have been replaced with `replacement`. |
| `string.split(pattern)` | Splits the string at occurrences of `pattern` and returns an array containing the resulting substrings. Note that `pattern` is a [regular expression](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/regex/Pattern.html#sum). |
| `string.contains(search)` | Returns `true` if the string contains `search`. |
| `string.indexOf(search)` | Returns the index of the first occurrence of `search`. |
| `string.substring(start, end)` | Returns a substring beginning at `start` and ending at `end`. |
| `String.join(separator, strings)` | Returns a string formed by joining the strings in the collection using `separator`. |

Complete documentation for all methods can be found in the JavaDocs documentation (see [Class `String`](https://docs.oracle.com/en/java/javase/25/docs/api//java.base/java/lang/String.html)).

The following example demonstrates the use of some of these methods:
```java
void main() {
    String text = "I am studying programming using Java.";
    IO.println("text = " + text);
    IO.println("First character: " + text.charAt(0));
    IO.println("Length of string: " + text.length());

    IO.println(); // Prints additional newline

    // Concatenation using +
    text = text + " Hello world!";
    IO.println("text (after addition) = " + text);

    // Replacing text
    text = text.replace("Java", "JAVA");
    IO.println("text (after replacement) = " + text);
    IO.println();

    // Split the string at periods
    // HOX! In split method the characters \^$.|?*+()[]{}
    // So for example if you would like to split at period, you need to write
    // text.split("\\.") and not text.split(".").
    // The last one is so called regular expression
    // which would split text at every character.
    String[] sentences = text.split("\\.");
    IO.println("sentences = " + Arrays.toString(sentences));

    IO.println();

    // Find position of a substring
    int programmingIndex = text.indexOf("programming");
    IO.println("programmingIndex = " + programmingIndex);

    // Extract a substring using the index
    String substring = text.substring(programmingIndex, programmingIndex + 11);
    IO.println("substring = " + substring);

    IO.println();

    // Operation "String + expression" converts the expression value into a string
    String anotherString = "1/2 = " + (1.0 / 2);
    IO.println("anotherString = \"" + anotherString + "\"");
}
```

### Parsing Numbers from Strings

A string can be converted into a numeric value using the wrapper classes parsing methods.
For example: `Integer.parseInt()` converts a string to an integer and
`Double.parseDouble()` converts a string to a floating-point number.

```java
void main() {
    String integerString = "42";
    int integerValue = Integer.parseInt(integerString);
    IO.println("integerValue = " + integerValue);

    String decimalString = "3.14";
    double decimalValue = Double.parseDouble(decimalString);
    IO.println("decimalValue = " + decimalValue);
}
```

### StringBuilder

Use the `StringBuilder` class whenever you need a mutable string.
`StringBuilder` provides methods for modifying text without creating a new string object after every operation, resulting in better memory efficiency.

Some useful methods are listed below.

| Method             | Description                                       |
| ------------------ | ------------------------------------------------- |
| `sb.charAt(index)` | Returns the character at the specified index.     |
| `sb.length()`      | Returns the number of characters in the sequence. |
| `sb.append(value)` | Appends a value to the end of the sequence.       |
| `sb.toString()`    | Returns a String copy of the contents.            |

A complete description of all methods can be found in the JavaDocs documentation (see 
[Class
`StringBuilder`](https://docs.oracle.com/en/java/javase/25/docs/api//java.base/java/lang/StringBuilder.html)).


Example how to use `StringBuilder` methods
```java
void main() {

    StringBuilder mutable = new StringBuilder("This is mutable");
    IO.println("mutable = " + mutable);
    IO.println("mutable.length() = " + mutable.length());

    IO.println();

    mutable.append(" string.");
    IO.println("mutable = " + mutable);
    IO.println("mutable.length() = " + mutable.length());

    IO.println();

    String immutableCopy = mutable.toString();
    IO.println("immutableCopy = " + immutableCopy);
}
```

## Arrays

Arrays are used to store a collection of elements of the same type in a single variable.
This makes data organization simpler and more efficient.

A new array can be declared and created in Java as follows:

```java,ignore
Type[] name = new Type[size];
```

Here, `new Type[size]` creates an array containing `size` elements of type `Type`.
After creating the array, values can be assigned using assignment statements:

```java
//-void main() {
int[] grades = new int[4];
grades[0] = 4;
grades[1] = 2;
grades[2] = 2;
grades[3] = 5;
//-IO.println( "grades = " + Arrays.toString(grades));
//-}
```

In the expression `\[number]`, the number refers to the element's position, or **index**, within the array.
Java uses **zero-based indexing**: so first element is at index `0`, second one is at index `1` and so on.
The index of the last element is always `array.length - 1`.

If the values are already known when the array is created, initialization can be written as:

```java
//-void main() {
int[] grades = new int[] {4, 2, 2, 5};
//-    IO.println( "grades = " + Arrays.toString(grades));
//-}
```

The `new Type[]` portion may be omitted when declaration and initialization occur on the same line:

```java
//-void main() {
int[] grades = {4, 2, 2, 5};
//-IO.println( "grades = " + Arrays.toString(grades));
//-}
```

In Java, the size of an array cannot be changed after it has been created.
Attempting to access or assign an element outside the valid index range causes a runtime error:

```java,ignore
int[] grades = new int[]{4, 2, 2, 5};
grades[5] = 3;
```
```
java.lang.ArrayIndexOutOfBoundsException: Index 5 out of bounds for length 4
	at main.main(main.java:3)
```

The length of an array can always be checked using the `length` attribute.
An array can be printed using `Arrays.toString()`
(see
[JavaDocs](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Arrays.html#toString(java.lang.Object[]))
)
```java
//-void main() {
    int[] grades = {4, 2, 2, 5};
    IO.println( "Array length: " + grades.length);
    IO.println( "Array contents: " + Arrays.toString(grades));
//-}
```