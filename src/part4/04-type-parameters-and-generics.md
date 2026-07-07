# Type Parameters and Generics

## Learning Objectives

* Be able to use type parameters to implement reusable, generic classes and methods.

In your previous programming courses, you learned that parameters help reduce repetition by generalizing program behavior for different values.
The idea behind parameters is to separate the logic of a computation from the actual values being processed. When we write a method, the computation only needs to be defined once.
For example, if we wanted to determine the index where a number first appears without using parameters, we might write code like this:

```java
//-void main() {
int[] array1 = {2, 3, 4};
// Search for the number 3
for (int i = 0; i < array1.length; i++) {
    if (array1[i] == 3) {
        IO.println("The number 3 first appears at index " + i);
        break;
    }
}

int[] array2 = {-20, 10, 2, 1};
// Search for the number 2
for (int i = 0; i < array2.length; i++) {
    if (array2[i] == 2) {
        IO.println("The number 2 first appears at index " + i);
        break;
    }
}
//-}
```

This works, but the code contains unnecessary duplication.
Let's create a function that takes the array as a *parameter*.

```java
int findIndex(int[] array, int target) {
    for (int i = 0; i < array.length; i++)
        if (array[i] == target) return i;
    return -1;
}

void main() {
    IO.println("The number 3 is at index " + findIndex(new int[] {2, 3, 4}, 3));
    IO.println("The number 2 is at index " + findIndex(new int[] {-20, 10, 2, 1}, 2));
}
```

You can think of this as using parameters at the **value level**: the method remains general, while the values vary.

However, we quickly discover that the same `findIndex()` method does not work for other numeric types such as `long` or `float`, nor for completely different types such as `String`.
For those cases, we would need separate methods.

```java
int findIndexLong(long[] array, long target) {
    for (int i = 0; i < array.length; i++)
        if (array[i] == target) return i;

    return -1;
}

int findIndexFloat(float[] array, float target) {
    for (int i = 0; i < array.length; i++)
        if (array[i] == target) return i;

    return -1;
}

int findIndexString(String[] array, String target) {
    for (int i = 0; i < array.length; i++)
        if (array[i].equals(target)) return i;

    return -1;
}

void main() {
    long[] longArray = {-10L, 5L, 1L};
    float[] floatArray = {-10.0f, 2.0f};
    String[] stringArray = {"Dog", "Cat", "Bird"};
    IO.println("The number -10 is at index " + findIndexLong(longArray, -10L));
    IO.println("The number 2.0 is at index " + findIndexFloat(floatArray, 2.0f));
    IO.println("The string \"Cat\" is at index " + findIndexString(stringArray, "Cat"));
}
```

Because Java is a statically typed language, we cannot write a single method that automatically works for all these types.
Without a better solution, we easily end up with a collection of nearly identical methods:
`findIndexInt`,
`findIndexDouble`,
`findIndexString` and so on.
The code is practically the same; only the types differ.

The fundamental idea behind all these methods is really this:

```java,ignore
int findIndex(TYPE[] array, TYPE target) {
    for (int i = 0; i < array.length; i++)
        if (array[i] == target) return i;

    return -1;
}
```

In Java, writing this kind of code is possible through *type parameters*.

***

## Type Parameters

A type parameter is a parameter whose value is itself a data type.
The purpose of type parameters is to reduce duplication in situations where the same code works for different types while preserving the benefits of static typing.
Type parameters can also eliminate unnecessary type casts in many situations.

Type parameters can be defined for methods in addition to ordinary parameters.
As a special feature, they can also be defined for classes.
Together, method and class type parameters enable *generic programming*, that is, programming algorithms and data structures that are independent of a specific type.

***

## Generic Methods

A method that defines one or more type parameters is called a *generic method*.
In a generic method, the data type is not fixed when the method is defined. Instead, the type is represented by a symbol whose meaning is determined when the method is called.
A method's type parameter is written inside angle brackets before the return type.
By convention, single uppercase letters are often used. The most common is `T`, which stands for *Type*.

```java
// Method "print" with one type parameter T
// and one ordinary parameter "value"
<T> void print(T value) {
    IO.println("Hello, I am '" + value + "' and I am an object of class '" + value.getClass() + "'!");
}

void main() {
    print(1.0);
    print(1);
    print("cat");
}
```

A generic method may be static, non-static, or a constructor.
The type parameter can appear in the return type, method parameters, or both.

A method may have one or more type parameters.
Multiple type parameters are written as a comma-separated list inside angle brackets, and each type parameter may represent an independent type.

For example we cahn have two type parameters `T1` and `T2`:

```java
<T1, T2> String combine(T1 value1, T2 value2) {
    return value1.toString() + ", " + value2.toString();
}

void main() {
    IO.println(combine(1, 2)); // T1 = Integer, T2 = Integer
    IO.println(combine(true, 1.0)); // T1 = Boolean, T2 = Double
}
```

The convention for naming type parameters is usually to use a single uppercase letter that reflects the role of the parameter:
`T` (**T**ype), 
`E` (**E**lement),
`K` (**K**ey),
`V` (**V**alue) and 
`N` (**N**umber).
Occasionally numbers are appended, such as `T1`, `T2`, and `T3`.

Notice that in the examples above, type parameters are defined but are not explicitly supplied when calling the method.
They could be:

```java
//-<T> void print(T value) {
//-    IO.println("Hello, I am '" + value + "' and I am an object of class '" + value.getClass() + "'!");
//-}
//-

void main() {
    this.<Double>print(1.0); // same as print(1.0)
    this.<String>print("cat"); // same as print("cat")
}
```

Normally, the type parameter is not provided explicitly because the compiler can infer it automatically.
For example, in the call: `print(1.0);`
the compiler sees that `1.0` is of type `double` and infers that `T` should be `Double`.
It is nevertheless important to remember that a type parameter value does exist behind the scenes even when it is not written explicitly.


Let's see how our earlier searching problem can be solved using a generic method.

```java
<T> int findIndex(T[] array, T target) {
    for (int i = 0; i < array.length; i++)
        if (array[i].equals(target)) return i;
    return -1;
}

void main() {
    Integer[] integers = {2, 3, 4};
    Double[] doubles = {-10.0, 2.0, 0.0, 5.5 };
    String[] animals = {"dog", "cat", "cheetah", "cat", "cheetah" };

    IO.println(findIndex(integers, 3));
    IO.println(findIndex(doubles, 5.4));
    IO.println(findIndex(animals, "cheetah"));
}
```

Notice that we had to make a couple of important changes.

First, the comparison is now performed using
`array[i].equals(target)`.
This is because the type parameter `T` can represent any reference type, and reference values cannot generally be compared using the `==` operator.

Second, in the `main` method we must use the wrapper classes `Integer`, `Double`, and `Long` instead of the primitive types `int`, `double`, and `long`.
This is because only reference types can be used as type parameters in Java.
This restriction stems from the way Java implements generics internally.
It is worth mentioning that Java is continually evolving, and it is quite possible that this limitation will disappear in the future.

<details><summary><i class="bi bi-stars jyu-gold"></i>Optional additional information: Why can't the type parameters be primitive types?</summary>

Java uses a mechanism called *type erasure*.
This means that when Java code is compiled into bytecode, type parameters are removed and replaced with their upper bound.
The upper bound is the type that the generic parameter is guaranteed to represent.
For example, if a type parameter is defined as:
`<T extends Number>`
then its upper bound is `Number`.
After compilation, all references to `T` are treated as if the type were `Number`.
If no restriction is specified, the upper bound automatically becomes `Object`.
For example type parameter
`T`
is treated internally as if `T` were `Object`.

In practice, this means that generics are not a runtime feature in Java. They are a compile-time type-checking mechanism.
Type information is removed so that generic code remains compatible with older, non-generic Java code.

Because primitive types do not inherit from `Object`, they cannot be used as type parameters.
This is why generic structures always use [wrapper classes](../part1/02-variables-and-types.md#wrapper-classes)(`Integer`, `Double`, `Boolean`)
instead of primitive types.

The same limitation also affects arrays.
Java does not allow generic arrays to be created.
For example:
`new T[10]`
is not permitted because the actual type parameter is not available at runtime due to type erasure.
In practice, this means that generic code almost always uses collections such as `ArrayList` instead of arrays.
</details>

***

## Generic Classes and Generic Interfaces

Generics are not limited to methods.
The real power of type parameters becomes evident when they are defined for classes or interfaces.
In fact, we have already used type parameters throughout the course in classes such as `ArrayList<T>`.
The list itself is generic, while the type of elements stored in it is specified later.

A generic class is particularly useful when a class stores values of a specific type and multiple methods operate on that same type parameter.
For example, a `Pair<T, U>` class might store two values whose types should remain fixed throughout the object's lifetime.

```java,ignore
public class Pair<T, U> {
    private T first;
    private U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(U second) {
        this.second = second;
    }
}
```

Using this class, we can create objects whose values may be of any types without writing separate classes for every possible combination.
For example:

```java
//- public class Pair<T, U> {
//-     private T first;
//-     private U second;
//-
//-     public Pair(T first, U second) {
//-         this.first = first;
//-         this.second = second;
//-     }
//-
//-     public T getFirst() {
//-         return first;
//-     }
//-
//-     public U getSecond() {
//-         return second;
//-     }
//-
//-     public void setFirst(T first) {
//-         this.first = first;
//-     }
//-
//-     public void setSecond(U second) {
//-         this.second = second;
//-     }
//- }
void main() {
    Pair<String, Integer> nameAndAge = new Pair<>("Matti", 30);
    IO.println("Name: " + nameAndAge.getFirst() + ", Age: " + nameAndAge.getSecond());

    Pair<Double, Double> coordinates = new Pair<>(60.192059, 24.945831);
    IO.println("Latitude: " + coordinates.getFirst() + ", Longitude: " + coordinates.getSecond());
}
```

If we attempted to implement the same functionality using `Object` attributes and tried to compensate with generic methods, type safety would easily be lost and mandatory type casts would introduce potential runtime errors.

```java,ignore
public class Pair {
    private final Object first;
    private final Object second;

    public Pair(Object first, Object second) {
        this.first = first;
        this.second = second;
    }

    public <T> T getFirst() {
        return (T) first;
        // type cast, no compile-time verification
    }
}
```

In the example above, the supposedly generic method does not actually make the class type-safe because the object's state is still stored as `Object`, and type conversions occur only at runtime.
The core idea of a generic class is that the type becomes attached to the class fields and their usage at compile time.

It is important to note that the choice between a generic method and a generic class does not depend on whether the method is static.
Instead, it depends on whether the type belongs to the class's permanent structure or merely to a single operation.
A method inside a generic class may still itself be generic, provided it uses its own type parameter and does not conflict with the class's type parameter.

<details><summary><i class="bi bi-stars jyu-gold"></i>Optional additional information:
Java cannot always infer the type unambiguously in every situation.
</summary>

Earlier we mentioned that Java can often infer a generic method's type parameter automatically.
This feature is known as *type inference*.
In practice, the compiler examines the method arguments and their types, then determines which type parameter satisfies the method definition.

For example, in the call `findIndex(integers, 3)`
the compiler sees that the array type is `Integer[]` and the search value is an `Integer`.
Based on this information, it infers that the type parameter `T` must be `Integer`, and there is no need to specify it explicitly.

Java also allows explicit generic method invocation, where the type parameter is provided manually:

`Finder.<Integer>findIndex(integers, 3);`

Although automatic type inference is sufficient in most practical situations, there are cases where the compiler cannot determine a type unambiguously or where making the type explicit improves readability or debugging.

One such situation occurs when arguments have different but compatible types and it is unclear which type should be chosen as the type parameter.

```java,ignore
static <T> T choose(T a, T b) {
    return a;
}

// choose(1, 1.0);
// COMPILATION ERROR:
// T cannot be inferred
Number n = <Number>choose(1, 1.0);
// OK: type supplied explicitly
```

In this example, the arguments have different types (`Integer` and `Double`).
Both inherit from `Number`, but the compiler cannot automatically determine whether one of those types or their common superclass should be chosen.
By supplying the type parameter explicitly, we tell the compiler that we want to use the method with the type `Number`.
</details>

***

## Generics and Polymorphism

Generics and polymorphism (more precisely, subtype polymorphism) are two different mechanisms that complement one another.
Although both increase code flexibility, they solve different problems and operate at different stages of program execution.

1. Polymorphism (subtyping) is a runtime mechanism. We studied it in [Chapter 3](../part3/02-polymorphism.md). It allows objects to be handled through a superclass or interface, with the correct method implementation chosen at runtime.
2. Generics (parametric polymorphism) are a compile-time mechanism. Their purpose is to ensure type safety and reduce duplication while allowing the same code to work with different types without losing type information.
3. Plain polymorphism (without type safety)
Before generics were introduced (Java 1.4 and earlier), collections relied entirely on polymorphism and the `Object` class.

```java
//-void main(){
// Raw list (raw type) - No longer recommended
List list = new ArrayList();

list.add("text");
list.add(123); // Allowed because Integer is an Object

for (Object o : list) {
    // Calls each object's own toString()
    IO.println(o.toString());
}
//-}
```

Here polymorphism itself works, but the code is not type-safe.
The compiler cannot prevent us (but it will kindly warn us) from inserting incorrect types into the list, which often leads to errors only when we later attempt to cast values back to their original types.

<!-- ======================================================================= -->
Generics introduce constraints that the compiler can verify.

```java
//-import java.util.*;
//-void main(){
List<String> words = new ArrayList<>();
words.add("cat");
words.add("dog");
words.add(123); // COMPILATION ERROR!
//-}
```

In this example, generics prevent incorrect usage before the program is even run.
Here we are not really utilizing polymorphism between our own classes; instead, we rely on the compiler's strict enforcement that the list contains only strings.

The most powerful approach is to combine both generics and polymorphism: generics restrict the allowed types to a particular family (for example, `Number`), while polymorphism handles the individual behavior of members of that family.

```java
//-void main(){


// The list can contain any kind of number (Integer, Double, Long, ...)
List<Number> numbers = new ArrayList<>();
numbers.add(1);     // Integer is a Number
numbers.add(2.5);   // Double is a Number

for (Number n : numbers) {
    // Generics guarantee that 'n' is at least a Number.
    // Polymorphism (the implementation provided by Number subclasses)
    // handles the actual values.
    IO.println(n.doubleValue());
}
//-}
```

***

## Type Bounds

Type parameters can be restricted so that only certain kinds of types may be used as arguments.
This is done using the `extends` keyword when defining the type parameter.
Bounds may be classes or interfaces, and they define the upper limit of the types that the type parameter may represent.
Notice that `extends` is used here for both classes and interfaces, even though interfaces do not actually inherit from classes.

```java
// Type parameter T can only be a subtype of Number
<T extends Number> void printNumber(T number) {
    IO.println("Number: " + number.doubleValue());
}

void main() {
    printNumber(10); // OK: Integer is a Number
    printNumber(3.14); // OK: Double is a Number
    // printNumber("cat"); // COMPILATION ERROR: // String is not a Number
}
```

Multiple bounds can be specified using the `&` operator.
The type parameter must satisfy all specified requirements.
The following method requires `T` to be both a subtype of `Number` and an implementation of `Comparable<T>`.

```java
// T must be both Number and Comparable
<T extends Number & Comparable<T>> void compare(T a, T b) {
    if (a.compareTo(b) < 0) {
        IO.println(a + " is smaller than " + b);
    } else if (a.compareTo(b) > 0) {
        IO.println(a + " is larger than " + b);
    } else {
        IO.println( a + " is equal to " + b);
    }
}

void main() {
    compare(10, 20); // OK: Integer is Number and Comparable
    compare(3.14, 2.71); // OK: Double is Number and Comparable
    // compare("cat", "dog"); // COMPILATION ERROR: String is not a Number
}
```

Type restrictions can also be expressed using the *wildcard* symbol (`?`), which represents an unknown type.
Wildcards make it possible to define upper and lower bounds for generic types.
They are commonly used with collections when we want to express that a collection can be read from or written to using a certain family of types, even though the exact element type is not known beforehand.
Consider the following examples.

```java,ignore
// Accepts a list containing any subtype of Number.
// Values can be read as Numbers,
// but nothing can be added because
// the exact type is unknown.
void printNumbers(List<? extends Number> numbers) {
    for (Number n : numbers) {
        IO.println(n);  // Note, that we don't know the exact type.
    }
    // numbers.add(10); // COMPILATION ERROR, because we don't know the exact type
}
/*
 * Accepts a list whose element type is Number or one of its supertypes
 * (for example Object).
 * It is therefore safe to add Number values and subclasses of Number.
 */
void addNumbers( List<? super Number> list) {
    list.add(10); // OK: Integer is a Number
    list.add(3.14); // OK: Double is a Number
    // list.add("cat"); // COMPILATION ERROR: String isn't a Number
    // Integer first = list.getFirst(); // COMPILATION ERROR: exact type unknown
    // We can't presume it to be Integer
}
```

We will not discuss wildcards in more detail in this chapter, but you are encouraged to explore them independently in the [Java documentation](https://docs.oracle.com/javase/tutorial/java/generics/wildcards.html).

***

## Invariance of Generic Types

Although `Integer` is a subtype of `Number`, `List<Integer>` is not a subtype of `List<Number>`.
These are completely separate types and there is no inheritance relationship between them.
This property is known as *invariance*.
Generic types are invariant by default for safety reasons.
The following example illustrates the idea.

```java
//-void main(){
List<Integer> integers = new ArrayList<>();
integers.add(1);

// If generics were NOT invariant, we could do this:
List<Number> numbers = integers; // This is exactly what Java prevents.

// Both variables would now refer to the same list in memory.
// Since numbers has type List<Number>, we could add a floating-point value:
numbers.add(3.14);

// But integers still assumes it contains only Integer values!
Integer i = integers.get(1); // BOOM! Runtime error: ClassCastException
//-}
```

If we could treat a list of integers as a general list of numbers, we could accidentally insert floating-point values into it.
Later, when the original code tries to read the list as integers, the program would fail.
Interestingly, Java arrays behave differently.
Arrays are *covariant*, meaning that an `Integer[]` array can be treated as a `Number[]` array.
However, type safety is then checked only at runtime.
If an element of the wrong type is stored, Java throws an `ArrayStoreException`.

```java
//-void main(){
// This is allowed in Java:
Integer[] integers = {1, 2};
Number[] numbers = integers; // OK with arrays!

// But this fails at runtime:
numbers[0] = 3.14; // ArrayStoreException!
//-}
```

With arrays, Java accepts the risk and reports the error only when the program is running.
One of the major goals of generics was to solve this problem by moving such errors to compile time.

If we want to utilize polymorphism between generic collections, we must use wildcards.
For example, if we wish to treat a `List<Integer>` like a list of numbers, we can use `List<? extends Number>`.

```java

//-void main(){
List<Integer> integers = Arrays.asList(1,2,4,8,16);
// Now this is allowed,
// but the list becomes effectively
// read-only for safety reasons.
List<? extends Number> numbers = integers;

for (Number n : numbers) {
    IO.println(n);  // Works
}
// numbers.set(0,5); // Compile error
//-}
```

***

<!-- ## Exercises

```text
Exercise 4.8 – Find the Largest
Exercise 4.9 – Container
Exercise 4.10 – Large Container
Exercise 4.11 – Type Bounds 1
Exercise 4.12 – Type Bounds 2
```

***

## Summary

Type parameters and generics allow us to write reusable, type-safe code without duplicating implementations for different data types.

A generic method introduces type parameters for an individual operation, while a generic class associates type parameters with the object's state and behavior.

Generics provide compile-time type checking, reducing the need for explicit casts and helping prevent runtime errors.

Important concepts introduced in this chapter include:

* Generic methods
* Generic classes
* Type inference
* Type erasure
* Wrapper classes
* Type bounds using `extends`
* Multiple bounds using `&`
* Wildcards (`?`)
* Upper bounds (`? extends T`)
* Lower bounds (`? super T`)
* Generic type invariance

Generics and polymorphism solve different problems, but together they provide a powerful way to build flexible and type-safe software. -->
