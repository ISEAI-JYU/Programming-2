# Functional Interfaces and Lambda Expressions

### Learning Objectives

* Understand the concept of a functional interface.
* Be able to use lambda expressions and method references to implement functional interfaces.
* Be familiar with Java's most common built-in functional interfaces (for example, `Function` and `Consumer`).
* Be able to define alternative orderings for objects using the `Comparator` interface and lambda expressions.

A *functional interface* is an interface that contains only one required method.
Its purpose is to represent a single operation or capability.

For example, the following interface is a functional interface:

```java,ignore
/**
 * Interface representing a function that takes
 * a number as a parameter and returns another number.
 */
public interface NumberFunction {
    int calculate(int number);
}
```

The `Adjustable` interface introduced as an example in [Chapter 4.1](../part4/01-interfaces.md#smart-home-adjustable-devices) is also a functional interface because it contains only one method: `setValue`.

Java provides a simplified way to create objects that implement functional interfaces.
This allows us to write code where functions can be handled almost like data.

***

## Creating Objects from Functional Interfaces

If we want to create an object that implements the `NumberFunction` interface, we can define a class that implements the interface and override its `calculate()` method.

```java
// FILE: main.java
public class MultiplyByTwo implements NumberFunction {
    @Override
    public int calculate(int number) {
        return number * 2;
    }
}

void main() {
    NumberFunction multiplyByTwo = new MultiplyByTwo();
    IO.println(multiplyByTwo.calculate(3));
}

// FILE_END
// FILE: NumberFunction.java
public interface NumberFunction {
    int calculate(int number);
}

// FILE_END
```

In this example, we had to write quite a lot of code—a new class and a method override—just to create a small object.
However, `NumberFunction` is a functional interface because it has only one required method.
In Java, it is possible to use an existing method *without* creating a separate class and treat that method as if it were an object implementing the interface.
This is called a *method reference*.

```java
// FILE: main.java
int multiplyByTwo(int number) {
    return number * 2;
}

void main() {
    // Use an existing method as the implementation of the interface.
    NumberFunction function = this::multiplyByTwo;
    IO.println(function.calculate(3));
}

// FILE_END
// FILE: NumberFunction.java
public interface NumberFunction {
    int calculate(int number);
}

// FILE_END
```

Notice the syntax `this::multiplyByTwo`
This does not call the method.
Instead, it creates a reference to the method.
Java automatically creates an object implementing the interface because the method's parameters and return type match the interface's single method.
If we print the value of the variable `function`, we do not get an integer. Instead, we get information about an object.

```java
//-public interface NumberFunction {
//-    int calculate(int number);
//-}
//-
//-int multiplyByTwo(int number) {
//-    return number * 2;
//-}
//-
//-void main() {

NumberFunction function = this::multiplyByTwo;
IO.println(function);
//-}
```

Method references use the `::` operator to refer to either an object method or a class method.
In other words `this::multiplyByTwo`
means that the method reference refers to the `multiplyByTwo` method of the current object.
Instead of `this`, we may also use an object reference or, in the case of static methods, a class name.

```java
// FILE: main.java
class Program {
    public static int multiplyByTwoStatic(int number) {
        IO.println("I am a class method!");
        return number * 2;
    }

    public int multiplyByTwoNonStatic(int number) {
        IO.println("I am an object method!");
        return number * 2;
    }

    void main() {
        // Static method reference
        NumberFunction function = Program::multiplyByTwoStatic;
        // Instance method reference
        NumberFunction function2 = this:: multiplyByTwoNonStatic;
        IO.println(function.calculate(2));
        IO.println(function2.calculate(2));
    }
}

// FILE_END
// FILE: NumberFunction.java

/**
 * Interface representing a function
 * that takes a number as input and
 * returns another number.
 */
public interface NumberFunction {
    int calculate(int number);
}

// FILE_END
```

<details>
<summary><i class="bi bi-stars jyu-gold"></i> Bonus: How the function reference works?</summary>

You may wonder how a method can suddenly "become" an object.
In reality, this is a technical trick performed by Java.
Before method references existed, the same thing was commonly accomplished using *anonymous classes*.

The compiler transforms `this::multiplyByTwo`
into something roughly equivalent to:

```java,ignore
int multiplyByTwo(int number) {
    return number * 2;
}

NumberFunction function = new NumberFunction() {
    @Override
    public int calculate(int number) {
        return multiplyByTwo(number);
    }
};
```

A lambda expression is therefore actually an object implementing the desired interface.
For this reason, lambda expressions can only be used with interfaces that contain exactly one abstract method—that is, functional interfaces.

Although anonymous classes are used less frequently nowadays, they are still useful when the implemented interface is not functional.
</details>

***

## Lambda Expressions

Java makes it possible to define the implementation of a function directly at the point where it is needed, without creating a separate method.
Such a function written as an expression is called a *lambda expression*.

The basic structure is:

```java,ignore
(type parameter) -> {
    // function body
    return result;
}
```

If there are multiple parameters, they are separated by commas.

```java,ignore
(type1 parameter1,
 type2 parameter2) -> {
    // function body
    return result;
}
```

A lambda expression does not have its own name.
Because of this, lambda expressions are also known as *anonymous functions*.
The following example prints elements from a list using a lambda expression.

```java
// FILE: main.java
void main() {
    // Pass an anonymous function directly to forEach()
    List<String> berries = List.of("strawberry", "blueberry", "lingonberry", "strawberry");

    // Print only strawberries
    berries.forEach(berry -> {
        if (berry.equals("strawberry")) {
            IO.println(berry);
        }
    });
}

// FILE_END
```

This could of course also be written using an ordinary method call:

```java
void main() {
    List<String> berries = List.of("strawberry", "blueberry", "lingonberry");
    printStrawberries(berries);
}

void printStrawberries(List<String> berries) {
    for (String berry : berries) {
        if (berry.equals("strawberry")) {
            IO.println(berry);
        }
    }
}
```

The key idea is this:
A lambda expression is not merely a piece of code. It is an object that implements a functional interface.
Each parameter must correspond to the parameter types of the functional interface's method.
Let's return to our `NumberFunction` example.
Suppose we want a function that multiplies its input by two.
Such a lambda expression takes an integer parameter and returns an integer.

```java,ignore
(int number) -> {
    return number * 2;
}
```

Because this lambda expression implements `NumberFunction`, we can assign it to a variable of that type:

```java,ignore
NumberFunction function = (int number) -> {
        return number * 2;
    };
```

We can now call the `calculate()` method because the lambda expression implements the interface.

```java
// FILE: main.java
void main() {
    NumberFunction function = (int number) -> {
            return number * 2;
        };
    IO.println(function.calculate(1));
    IO.println(function.calculate(2));
    IO.println(function.calculate(3));
    IO.println(function.calculate(4));
}
// FILE_END
// FILE: NumberFunction.java

/**
 * Interface representing a function
 * that takes a number as input and
 * returns another number.
 */
public interface NumberFunction {
    int calculate(int number);
}

// FILE_END
```

We did not need a separate class—or even a separate method!

The greatest advantage of lambda expressions is their conciseness.
Java can infer many things automatically, allowing the code to be shortened even further.
First, parameter types can often be inferred from the parameter types of the functional interface method.
As a result, the types can usually be omitted.
In our previous example, we can leave out the `int` type because the method `NumberFunction.calculate()` already specifies that its parameter must be an integer.

```java
//-public interface NumberFunction {
//-    int calculate(int number);
//-}
//-
//-void main() {
NumberFunction function = (number) -> {
        return number * 2;
    };
//-IO.println(function.calculate(1));
//-IO.println(function.calculate(2));
//-IO.println(function.calculate(3));
//-IO.println(function.calculate(4));
//-}
```

Second, if the body of a lambda expression contains only a single statement, the braces and the `return` keyword may be omitted.

```java
//-public interface NumberFunction {
//-    int calculate(int number);
//-}
//-
//-void main() {

NumberFunction function = (number) -> number * 2;
//-IO.println(function.calculate(1));
//-IO.println(function.calculate(2));
//-IO.println(function.calculate(3));
//-IO.println(function.calculate(4));
//-}
```

Finally, if a lambda expression has exactly one parameter, the parentheses around the parameter may also be omitted.

```java
//-public interface NumberFunction {
//-    int calculate(int number);
//-}
//-
//-void main() {

NumberFunction function = number -> number * 2;
//-IO.println(function.calculate(1));
//-IO.println(function.calculate(2));
//-IO.println(function.calculate(3));
//-IO.println(function.calculate(4));
//-}
```

It is also common to use shorter parameter names in lambda expressions because the meaning of the parameters is already documented by the functional interface.

```java
//-public interface NumberFunction {
//-    int calculate(int number);
//-}
//-
//-void main() {

NumberFunction multiplyByTwo = x -> x * 2;
//-IO.println(multiplyByTwo.calculate(1));
//-IO.println(multiplyByTwo.calculate(2));
//-IO.println(multiplyByTwo.calculate(3));
//-IO.println(multiplyByTwo.calculate(4));
//-}
```

***

## Functions as Parameters

Because a lambda expression is an object, we can pass it to methods as an argument.
This allows us to write functions at a higher level of abstraction.
For example, we could write a method that evaluates and sums two functions.

```java
//-public interface NumberFunction {
//-    int calculate(int number);
//-}
//-

/**
 * Evaluates two functions for a given value and returns the sum.
 */
int sumFunctions(NumberFunction f1, NumberFunction f2, int x) {
    return f1.calculate(x) + f2.calculate(x);
}

void main() {
    // Pass two different functions: x * 2 and x * x

    int result = sumFunctions(x -> x * 2, x -> x * x, 3);
    IO.println("Result: " + result); // (3 * 2) + (3 * 3) = 6 + 9 = 15
}
```

***

## Built-In Functional Interfaces

Java provides a collection of general-purpose functional interfaces in the package 
`java.util.function` (see [JavaDoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/package-summary.html#class-summary))

**`Function<T, R>`**
([JavaDoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/Function.html))
represents a function that takes one parameter of type `T` and returns a value of type `R`.
For example, our earlier example can be simplified by using the built-in `Function` interface instead of `NumberFunction`.

```java
//-void main() {
Function<Integer, Integer> multiplyByTwo = x -> x * 2;
Function<Integer, Integer> square = x -> x * x;
IO.println(multiplyByTwo.apply(1));
IO.println(square.apply(2));
//-}
```

The `Function` interface also provides the helper methods `andThen` and `compose`, which allow functions to be chained together.

```java
//-void main() {
Function<Integer, Integer> multiplyByTwo = x -> x * 2;
Function<Integer, Integer> square = x -> x * x;

// Computes: (x²) * 2
Function<Integer, Integer> squareThenMultiply = multiplyByTwo.compose(square);
// Computes: (x * 2)²
Function<Integer, Integer> multiplyThenSquare = multiplyByTwo.andThen(square);

IO.println(squareThenMultiply.apply(2));
IO.println(multiplyThenSquare.apply(2));
//-}
```

In the same way **`BiFunction<T, U, R>`** 
([JavaDoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/BiFunction.html))
represents a function that takes two parameters and returns a value.

**`Consumer<T>`**
([JavaDoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/Consumer.html))
and **`BiConsumer<T, U>`** 
([JavaDoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/BiConsumer.html))
represent functions that accept parameters but do not return a value (their return type is `void`).
Many collection classes include a `forEach` method that accepts a `Consumer`.

```java
//-void main() {
List<String> berries = List.of("strawberry", "blueberry", "lingonberry");

// IO.println matches Consumer<T>
// because it accepts one parameter and returns nothing.
berries.forEach(IO::println);

Map<String, Integer> grades = new HashMap<>(
        Map.of(
            "Denis",        1,
            "Antti-Jussi",  3,
            "Sami",         5,
            "Karri",        5,
            "Ville",        1)
    );

// BiConsumer takes two parameters:  key and value
grades.forEach( (name, grade) -> IO.println(name + " => " + grade));
//-}
```

***

## The Comparator Interface

Let's return to the `Comparable<T>` interface introduced in [Chapter 4.2](../part4/02-comparable.md).
Using `Comparable`, we defined a "natural ordering" for objects.
Sometimes, however, we want to sort the same objects differently in different situations—for example, ordering people either by name or by age.

Java's `Comparator` interface 
([JavaDoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Comparator.html))
provides a way to define *alternative* orderings.
The interface contains only one required method, `compare`, meaning that it is itself a functional interface.
The `compare` method follows exactly the same principle as `Comparable.compareTo`:

| Case                     | Meaning  | Interpretation                   |
| ------------------------ | -------- | -------------------------------- |
| `cmp.compare(a, b) < 0`  | `a < b`  | `a` comes before `b`             |
| `cmp.compare(a, b) == 0` | `a == b` | `a` and `b` are considered equal |
| `cmp.compare(a, b) > 0`  | `a > b`  | `a` comes after `b`              |

Because `Comparator` is functional, we can define comparisons very concisely using lambda expressions.

```java
//-void main() {
List<String> names = Arrays.asList("Ville", "Aino", "Matti");

// Sort by length (shortest first)
names.sort((s1, s2) -> Integer.compare(s1.length(), s2.length()));

IO.println(names); // [Aino, Ville, Matti]
//-}
```

Let's return once more to the [collectible card example](../part4/02-comparable.md#implementing-the-comparable-interface-yourself) from Chapter 4.2.
We will slightly extend the `CollectibleCard` class by adding the attribute `series`, which describes which card series the card belongs to (for example, Animals, Vehicles, and so on).

```java
class CollectibleCard implements Comparable<CollectibleCard> {
    private String name;
    private String series;
    private int identifier;

    public CollectibleCard(String name, String series, int identifier) {
        this.name = name;
        this.series = series;
        this.identifier = identifier;
    }

//-
//-    @Override
//-    public int compareTo(CollectibleCard other) {
//-        int seriesComparison = this.series.compareTo(other.series);
//-        if (seriesComparison != 0) {
//-            return seriesComparison;
//-        }
//-        return Integer.compare(this.identifier, other.identifier);
//-    }
//-
//-    public String getName() {
//-        return name;
//-    }
//-
//-    public String getSeries() {
//-        return series;
//-    }
//-
//-    @Override
//-    public String toString() {
//-        return "Card: " + name + " (Series: " + series + ", #" + identifier + ")";
//-    }
//-}
//-
//-void main() {
//-    List<CollectibleCard> cards = Arrays.asList(
//-            new CollectibleCard("Brilliant Dragon", "Animals", 3),
//-            new CollectibleCard("Speedy Vespa Jet", "Vehicles", 1),
//-            new CollectibleCard("Beginner Amoeba","Animals", 1),
//-            new CollectibleCard("Magnificent Seahorse","Animals", 2),
//-            new CollectibleCard("Fast Flash", "Vehicles", 2));
//-
//-    IO.println("Before sorting:");
//-    for (CollectibleCard card : cards) {
//-        IO.println(card);
//-    }
//-
//-    Collections.sort(cards);
//-
//-    IO.println();
//-
//-    IO.println("After sorting:" );
//-
//-    for (CollectibleCard card : cards) {
//-        IO.println(card);
//-    }
//-}
```

At the moment, the collectible cards have a natural ordering defined through the `compareTo()` method.
Suppose, however, that we want to offer an alternative way of sorting cards based on the series name.
To achieve this, we can use the version of `sort` that accepts a `Comparator`.

```java
// FILE: main.java
void main() {
    List<CollectibleCard> cards = Arrays.asList(
        new CollectibleCard("Brilliant Dragon", "Animals", 3),
        new CollectibleCard("Speedy Vespa Jet", "Vehicles", 1),
        new CollectibleCard("Beginner Amoeba", "Animals", 1),
        new CollectibleCard("Magnificent Seahorse", "Animals", 2),
        new CollectibleCard("Fast Flash", "Vehicles", 2));

    IO.println("Before sorting:");
    cards.forEach(IO::println);

    // Collections.sort provides a version
    // that accepts a Comparator.
    Comparator<CollectibleCard> bySeries =
        (card1, card2) -> card1.getSeries() .compareTo( card2.getSeries());
    Collections.sort(cards, bySeries);

    IO.println();

    IO.println("After sorting:");
    cards.forEach(IO::println);
}

// FILE_END
// FILE: CollectibleCard.java
class CollectibleCard implements Comparable<CollectibleCard> {
    private String name;
    private String series;
    private int identifier;

    public CollectibleCard(String name, String series, int identifier) {
        this.name = name;
        this.series = series;
        this.identifier = identifier;
    }

    public String getSeries() {
        return series;
    }

    @Override
    public int compareTo(CollectibleCard other) {
        int seriesComparison = this.series.compareTo(other.series);

        if (seriesComparison != 0) {
            return seriesComparison;
        }

        return Integer.compare(this.identifier, other.identifier);
    }

    @Override
    public String toString() {
        return "Card: " + name + " (Series: " + series + ", #" + identifier + ")"; 
    }
}

// FILE_END
```

Notice that sorting is now performed according to the comparator `bySeries`, which is defined as a lambda expression.

Because the comparator is defined outside the `CollectibleCard` class, we also added the getter method `getSeries`.
The `Comparator` class also provides several helper methods for combining and creating comparators.

***

### Comparator.comparing()

The method `Comparator.comparing` takes a lambda expression that extracts a value from an object.
The comparison is then performed using the natural ordering of that extracted value.
For example, our `bySeries` comparator can be written more concisely as follows:


```java
// FILE: main.java
//-void main() {
//-    List<CollectibleCard> cards = Arrays.asList(
//-        new CollectibleCard("Brilliant Dragon", "Animals", 3),
//-        new CollectibleCard("Speedy Vespa Jet", "Vehicles", 1),
//-        new CollectibleCard("Beginner Amoeba", "Animals", 1),
//-        new CollectibleCard("Magnificent Seahorse", "Animals", 2),
//-        new CollectibleCard("Fast Flash", "Vehicles", 2));
//-
//-    IO.println("Before sorting:");
//-    cards.forEach(IO::println);
//-
//-    // Collections.sort provides a version
//-    // that accepts a Comparator.
    Comparator<CollectibleCard> bySeries =
        Comparator.comparing(CollectibleCard::getSeries);
//-    Collections.sort(cards, bySeries);
//-
//-    IO.println();
//-
//-    IO.println("After sorting:");
//-    cards.forEach(IO::println);
//-}
//-class CollectibleCard implements Comparable<CollectibleCard> {
//-    private String name;
//-    private String series;
//-    private int identifier;
//-
//-    public CollectibleCard(String name, String series, int identifier) {
//-        this.name = name;
//-        this.series = series;
//-        this.identifier = identifier;
//-    }
//-
//-    public String getSeries() {
//-        return series;
//-    }
//-
//-    @Override
//-    public int compareTo(CollectibleCard other) {
//-        int seriesComparison = this.series.compareTo(other.series);
//-
//-        if (seriesComparison != 0) {
//-            return seriesComparison;
//-        }
//-
//-        return Integer.compare(this.identifier, other.identifier);
//-    }
//-
//-    @Override
//-    public String toString() {
//-        return "Card: " + name + " (Series: " + series + ", #" + identifier + ")"; 
//-    }
//-}
// FILE_END
```

***

The method `Comparator.thenComparing()` combines two comparators into one.
Objects are first compared using the first comparator.
If the first comparison yields `0`, the second comparator is used.
For example, the `compareTo()` method of `CollectibleCard` can be implemented in a more object-oriented style as follows:

```java,ignore
@Override
public int compareTo(CollectibleCard other) {
    // Compare cards by series.
    Comparator<CollectibleCard> bySeries = Comparator.comparing(c -> c.series);
    // Compare cards by identifier.
    Comparator<CollectibleCard> byIdentifier = Comparator.comparing(c -> c.identifier);

    // First compare by series,
    // then by identifier.
    Comparator<CollectibleCard> comparison = bySeries.thenComparing(byIdentifier);
    return comparison.compare(this, other);
}
```

***


`Comparator.naturalOrder()` returns a comparator that sorts objects according to their *natural ordering*.
In other words, it allows us to treat the `compareTo` implementation of a `Comparable` object as a comparator object.
For example, a comparator corresponding to the normal alphabetical ordering of strings can be created as follows:

```java
void main() {
    List<String> strings = new ArrayList<>(List.of("Denis", "Antti-Jussi", "Karri", "Rauli", "Sami"));
    Comparator<String> alphabeticalOrder = Comparator.naturalOrder();
    Collections.sort(strings, alphabeticalOrder);
    IO.println(strings);
}
```

***

`Comparator.reversed()` creates a new comparator with the opposite ordering.
This makes it easy, for example, to sort strings in reverse alphabetical order.

```java
void main() {
    List<String> strings = new ArrayList<>(List.of("Denis", "Antti-Jussi", "Karri", "Rauli", "Sami"));
    Comparator<String> alphabeticalOrder = Comparator.naturalOrder();
    Comparator<String> reverseAlphabeticalOrder = alphabeticalOrder.reversed();
    Collections.sort(strings, reverseAlphabeticalOrder);
    IO.println(strings);
}
```

***

By default, many comparators do not handle `null` references.

This can lead to exceptions and unexpected behavior.

For example, even the natural ordering of Java `String` objects cannot handle a `null` value.

```java
//-void main() {
String[] strings = {"Programming 1", null, "Programming 2" };
Arrays.sort(strings);
IO.println(Arrays.toString(strings));
//-}
```
```
java.lang.NullPointerException: Cannot invoke "java.lang.Comparable.compareTo(Object)" because "a[runHi]" is null
```

`Comparator.nullsFirst()` and `Comparator.nullsLast()` help in such situations.
They take an existing comparator and return a new comparator that can safely handle `null` references.
As their names suggest 
`nullsFirst()` places `null` values before all other values and 
`nullsLast()` places `null` values after all other values.

```java
//-void main() {
String[] strings = {"Programming 1", null, "Programming 2" };
Comparator<String> alphabeticalOrder = Comparator.naturalOrder();

Comparator<String> nullsFirst = Comparator.nullsFirst(alphabeticalOrder);
Arrays.sort(strings, nullsFirst);
IO.println(Arrays.toString(strings));

Comparator<String> nullsLast = Comparator.nullsLast(alphabeticalOrder);
Arrays.sort(strings, nullsLast);
IO.println(Arrays.toString(strings));
//-}
```

By combining different comparator methods, it is possible to create very sophisticated comparison logic without writing explicit conditional statements.

```java
//-void main() {
List<String> names = Arrays.asList("Ville", "Bob", "Aino", "Matti", null);
// Build a more complex comparator:
//
// 1. Put null values last.
// 2. Sort by length.
// 3. If lengths are equal,
//    use alphabetical ordering.
Comparator<String> comparator = Comparator.nullsLast( 
    Comparator.comparingInt(String::length)
        .thenComparing(Comparator.naturalOrder())
    );

names.sort(comparator);
IO.println(names);
//-}
```

```text
{{#include ../exercises/6-2-comparison-rarity/handout.md}}
```

***

## Unused Parameters

In user-interface programming, it is common to encounter situations where a method definition requires a parameter even though the implementation does not actually need it.
A typical example is an event listener.
The interface requires an event object to be accepted as a parameter, even though the event data itself may not be used.

```java,ignore
button.setOnAction(event -> {
    IO.println("Button clicked!");
});
```

An IDE may warn that the parameter `event` is never used.
Such warnings are normally helpful, but in this situation it is not a mistake.

Since the parameter cannot simply be omitted, Java allows it to be written as `_`.
Starting from Java 22, this is an official language feature called an *unnamed variable*.

```java,ignore
button.setOnAction(_ -> {
    // This event listener does not
    // need the event information,
    // so the parameter can be
    // discarded using "_".
    IO.println("Button clicked!");
});
```

This explicitly communicates that the parameter value is not needed.
A parameter declared using `_` cannot be referenced later in the code, and any "parameter is never used" warning disappears.

A similar convention exists in several other programming languages, such as C#.
In Java, however, it is no longer merely a convention but an official language feature.