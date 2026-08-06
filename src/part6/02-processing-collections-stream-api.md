# Processing Collections: Stream API

### Learning Objectives

* Understand the difference between declarative and imperative programming when processing collections
* Be familiar with the core concepts of the Stream API (intermediate and terminal operations)
* Know how to use streams to filter, transform, and sort collections
* Be able to use the `Optional` type when dealing with potentially missing values
* Be familiar with primitive streams such as `IntStream` and `DoubleStream`

So far, we have used loops to process collections. For example, if we want to calculate the sum of all even elements in a list, we would typically write something like this:

```java
//-void main() {
List<Integer> numbers = List.of(508, 18, 17, -148, 67, 42, -41);
int sum = 0;
for (int number : numbers) {
    if (number % 2 == 0) {
        sum += number;
    }
}
IO.println("Sum: " + sum);
//-}
```

This programming style is called *imperative* programming.
In imperative programming, we describe step by step *what the computer must do* in order to reach a result that we already know we want.

In data processing, however, it is often clearer to describe *what kind of result we want* rather than specifying the exact execution steps.
This approach is known as *declarative* programming.
Java's Stream API provides tools for this by utilizing lambda expressions.
Using streams, we can replace the loop shown above with a single line:

```java
//-void main() {


List<Integer> numbers = List.of(508, 18, 17, -148, 67, 42, -41);
int sum = numbers.stream().filter(i -> i % 2 == 0).mapToInt(Integer::intValue).sum();
IO.println("Sum: " + sum);
//-}
```

***

## Basic Stream Operation

Let's take a closer look at the previous example.
The statement consists of four separate parts:

```java,ignore
numbers                         //    Collection to be processed
  .stream()                     // 1. Convert to a stream
  .filter(i -> i % 2 == 0)      // 2. Filter
  .mapToInt(Integer::intValue)  // 3. Convert to primitive type
  .sum();                       // 4. Compute the result
```

Let's examine each step.

### 1. Converting a Collection into a Stream

Every Java collection provides a `stream` method, which returns a `Stream<T>` object—that is, a stream
([JavaDoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/stream/Stream.html)).
A stream can be thought of as a conveyor belt or machine that takes a collection and produces one element at a time as a *data flow*.

```bob
\   42   /
 \  67  /
  \-148/
   \17/           data flow -->
+---\/---+         
|        |      .---.       .---.  
| Stream |-----( 18  )-----( 508 )----->
|        |      `---'       `---' 
+--------+
```

### 2. The `filter` Function

The stream method `filter` executes the supplied lambda expression for every element.
If the lambda returns `true`, the element continues through the stream.
If it returns `false`, the element is removed from the stream.

In other words, `filter` acts as a *filter* that either lets an element pass through or filters it out based on a lambda expression.

```bob
\   42   /
 \  67  /
  \-148/                data flow -->
   \  /                                            
+---\/---+             +--------+          
|        |    .---.    |        |      .---.       .---.  
| Stream |---( -148)---| filter |-----( 18  )-----( 508 )----->
|        |    `---'    |"i%2==0"|      `---'       `---' 
+--------+             +--------+ true -> forward

                  false    :     
                    |      :     
                    V      v     
                  out    .---.   
                        ( 17  ) 
                         `---'
```

### 3. The `map` Function

One of the most important tools in the Stream API is *mapping*, or transformation.
These methods usually begin with the word `map`.
They take one element at a time and transform it into something else.

For example, `mapToInt` transforms each element into an `int` using a supplied function.
In this example, we use the method reference 
`Integer::intValue`
which converts an `Integer` object into a primitive integer.

```bob
\   42   /
 \  67  /
  \-148/                data flow -->
   \  /                                            
+---\/---+             +--------+             +-------------------+      int
|        |    .---.    |        |    .---.    |                   |     .---.  
| Stream |---( -148)---| filter |---( 18  )---|   mapToInt        |----( 508 )----->
|        |    `---'    |"i%2==0"|    `---'    |"Integer::intValue"|     `---'
+--------+             +--------+             +-------------------+
```

We need this step because a generic `Stream<T>` cannot contain primitive types such as `int`.
After calling `mapToInt`, the stream becomes an `IntStream`.
([JavaDoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/stream/IntStream.html)).
`IntStream` is optimized for integer processing and provides convenient statistical methods such as `sum()`.

### 4. Computing a Value

At the end of a stream pipeline, we always call some kind of **terminal operation**.

A terminal operation receives the elements that reach the end of the data flow and returns the result in a desired form (for example, a sum or a list).
In this example, we use `sum()`, which adds the numbers together and returns a single result.

```bob
\   42   /
 \  67  /
  \-148/                data flow -->
   \  /                                            
+---\/---+   +--------+   +-------------------+      \+------+   
|        |   |        |   |                   |    508\      |  
| Stream |---| filter |---|   mapToInt        |--- 18  \ sum |---> 420
|        |   |"i%2==0"|   |"Integer::intValue"|  "-148"/     |
+--------+   +--------+   +-------------------+    42 /+-----+ 
                                                     /
```

***

## Using Streams

Everything that can be done with streams could also be written using ordinary loops.
However, by combining different Stream API operations, we often get concise solutions to problems that would otherwise require several lines of imperative code.

### Creating Streams

The most common way to create a stream is directly from a collection.
Every class implementing the `Collection` interface provides a `stream` method.

```java
//-void main() {
List<String> fruits = List.of("apple", "pear", "orange");
Map<String, Integer> populations = Map.of(
  "Helsinki", 695526,
  "Tampere", 263526,
  "Jyväskylä", 149967
);

Set<String> carBrands = Set.of("BMW", "Audi", "Hyundai", "Volvo");

Stream<String> fruitsStream = fruits.stream();
Stream<Map.Entry<String, Integer>> populationsStream = populations.entrySet().stream();
Stream<String> carBrandsStream = carBrands.stream();

//-
//-IO.println("Fruits without the letter p: " + fruitsStream.filter(f -> !f.contains("p")).toList());
//-IO.println("City with the largest population: " + populationsStream.max(Comparator.comparing(Map.Entry::getValue)).get().getKey());
//-IO.println("Car brand names combined: " + carBrandsStream.reduce("", (previous, next) -> previous + next));
//-}
```

Streams can also be created from arrays using `Arrays.stream`.

```java
//-void main() {
int[] grades = {5, 1, 2, 3, 4, 5, 2, 5, 5, 4};
String[] teachers = {"Denis", "Antti-Jussi", "Sami", "Karri"};

IntStream gradesStream = Arrays.stream(grades);
Stream<String> teachersStream = Arrays.stream(teachers);
//-
//-IO.println("Average grade: " + gradesStream.average().getAsDouble());
//-IO.println("Teacher with the longest first name: " + teachersStream.max(Comparator.comparing(String::length)).get());
//-}
```

It is worth mentioning that Java provides special stream types for primitive data types
  `IntStream`,
  `DoubleStream`,
  `LongStream`, and so on.
These specialized streams provide statistical methods such as
`max`,
`min`,
`average`,
`sum`.
For collections, primitive values are wrapped in wrapper classes, resulting in stream types such as 
`Stream<Integer>`, `Stream<Double>` or `Stream<Long>`
The `Stream` class provides methods such as `mapToInt`, `mapToDouble()`
for converting a stream into its primitive counterpart.

We can also create streams that produce infinitely many values.
For example, `Stream.generate` repeatedly calls a supplied function.
In such cases, it is important to use limiting operations such as `limit`.

```java
//-void main() {
Stream<String> hashes = Stream.generate(() -> "#");
List<String> tenHashes = hashes.limit(10).toList();
IO.println(tenHashes);
//-}
```

***

## Intermediate Stream Operations

All stream methods that return another `Stream` object are called *intermediate operations*.
They are used to modify and filter the elements flowing through the stream.

Imagine that we are maintaining purchase records for a store.
We want to determine the average price of purchases made in September.

```java,ignore
public class PurchaseEvent {
  private double price;
  private LocalDate date;
}
```

Rather than writing loops and `if` statements, we can build the required result step by step using stream operations.
Let's begin by including only purchases made in September.
The `filter` method removes elements from the stream according to a boolean condition.

```java
// FILE: main.java
void main() {
  List<PurchaseEvent> purchaseEvents = List.of(
    new PurchaseEvent(100.0, LocalDate.of(2025, Month.JANUARY, 2)),
    new PurchaseEvent(21.5, LocalDate.of(2025, Month.JULY, 3)),
    new PurchaseEvent(12.0, LocalDate.of(2025, Month.SEPTEMBER, 1)),
    new PurchaseEvent(5.25, LocalDate.of(2025, Month.SEPTEMBER, 12)),
    new PurchaseEvent(245.0, LocalDate.of(2025, Month.SEPTEMBER, 21)),
    new PurchaseEvent(342.0, LocalDate.of(2025, Month.OCTOBER, 2))
  );

  Stream<PurchaseEvent> septemberOnly =
          purchaseEvents.stream()
                        .filter(p -> p.getDate().getMonth() == Month.SEPTEMBER);

  septemberOnly.forEach(IO::println);
}
// FILE_END
// FILE: PurchaseEvent.java
import java.time.LocalDate;

public class PurchaseEvent {
  private double price;
  private LocalDate date;

//-
//-  public PurchaseEvent(double price, LocalDate date) {
//-    this.price = price;
//-    this.date = date;
//-  }
//-
//-  public LocalDate getDate() {
//-    return date;
//-  }
//-
//-  public double getPrice() {
//-    return price;
//-  }
//-
//-  @Override
//-  public String toString() {
//-    return date.toString() + ", " + price + " €";
//-  }
}
// FILE_END
```

Now that we have only September purchases, we want to calculate their average price.
An average can only be calculated from numbers, whereas the stream currently contains `PurchaseEvent` objects.
We can use the stream method `map`, which transforms each element according to a given mapping function.
In our case, we only need to retrieve the `price` attribute from each `PurchaseEvent`.

```java
// FILE: main.java
//-void main() {
//-  List<PurchaseEvent> purchaseEvents = List.of(
//-    new PurchaseEvent(100.0, LocalDate.of(2025, Month.JANUARY, 2)),
//-    new PurchaseEvent(21.5, LocalDate.of(2025, Month.JULY, 3)),
//-    new PurchaseEvent(12.0, LocalDate.of(2025, Month.SEPTEMBER, 1)),
//-    new PurchaseEvent(5.25, LocalDate.of(2025, Month.SEPTEMBER, 12)),
//-    new PurchaseEvent(245.0, LocalDate.of(2025, Month.SEPTEMBER, 21)),
//-    new PurchaseEvent(342.0, LocalDate.of(2025, Month.OCTOBER, 2))
//-  );
//-

  Stream<Double> septemberPrices =
          purchaseEvents.stream()
                        .filter(p -> p.getDate().getMonth() == Month.SEPTEMBER)
                        .map(p -> p.getPrice());

  septemberPrices.forEach(IO::println);
//-}
// FILE_END
// FILE: PurchaseEvent.java
import java.time.LocalDate;

public class PurchaseEvent {
  private double price;
  private LocalDate date;

//-
//-  public PurchaseEvent(double price, LocalDate date) {
//-    this.price = price;
//-    this.date = date;
//-  }
//-
//-  public LocalDate getDate() {
//-    return date;
//-  }
//-
//-  public double getPrice() {
//-    return price;
//-  }
//-
//-  @Override
//-  public String toString() {
//-    return date.toString() + ", " + price + " €";
//-  }
}
// FILE_END
```


To make the calculation of averages easier, we can convert the values into primitive `double` values using `mapToDouble`.

```java
// FILE: main.java
//-void main() {
//-  List<PurchaseEvent> purchaseEvents = List.of(
//-    new PurchaseEvent(100.0, LocalDate.of(2025, Month.JANUARY, 2)),
//-    new PurchaseEvent(21.5, LocalDate.of(2025, Month.JULY, 3)),
//-    new PurchaseEvent(12.0, LocalDate.of(2025, Month.SEPTEMBER, 1)),
//-    new PurchaseEvent(5.25, LocalDate.of(2025, Month.SEPTEMBER, 12)),
//-    new PurchaseEvent(245.0, LocalDate.of(2025, Month.SEPTEMBER, 21)),
//-    new PurchaseEvent(342.0, LocalDate.of(2025, Month.OCTOBER, 2))
//-  );
//-
  DoubleStream septemberPrices =
          purchaseEvents.stream()
                        .filter(p -> p.getDate().getMonth() == Month.SEPTEMBER)
                        .map(p -> p.getPrice())
                        .mapToDouble(d -> d.doubleValue());

  septemberPrices.forEach(IO::println);

//-}
// FILE_END
// FILE: PurchaseEvent.java
import java.time.LocalDate;

public class PurchaseEvent {
  private double price;
  private LocalDate date;

//-
//-  public PurchaseEvent(double price, LocalDate date) {
//-    this.price = price;
//-    this.date = date;
//-  }
//-
//-  public LocalDate getDate() {
//-    return date;
//-  }
//-
//-  public double getPrice() {
//-    return price;
//-  }
//-
//-  @Override
//-  public String toString() {
//-    return date.toString() + ", " + price + " €";
//-  }
}
// FILE_END
```

`DoubleStream` already provides a built-in `average` method that calculates the average value of all elements in the stream.

```java
// FILE: main.java
//-void main() {
//-  List<PurchaseEvent> purchaseEvents = List.of(
//-    new PurchaseEvent(100.0, LocalDate.of(2025, Month.JANUARY, 2)),
//-    new PurchaseEvent(21.5, LocalDate.of(2025, Month.JULY, 3)),
//-    new PurchaseEvent(12.0, LocalDate.of(2025, Month.SEPTEMBER, 1)),
//-    new PurchaseEvent(5.25, LocalDate.of(2025, Month.SEPTEMBER, 12)),
//-    new PurchaseEvent(245.0, LocalDate.of(2025, Month.SEPTEMBER, 21)),
//-    new PurchaseEvent(342.0, LocalDate.of(2025, Month.OCTOBER, 2))
//-  );
//-
  OptionalDouble septemberAverage =
          purchaseEvents.stream()
                        .filter(p -> p.getDate().getMonth() == Month.SEPTEMBER)
                        .map(p -> p.getPrice())
                        .mapToDouble(d -> d.doubleValue())
                        .average();

  IO.println(septemberAverage);

//-}
// FILE_END
// FILE: PurchaseEvent.java
import java.time.LocalDate;

public class PurchaseEvent {
  private double price;
  private LocalDate date;

//-
//-  public PurchaseEvent(double price, LocalDate date) {
//-    this.price = price;
//-    this.date = date;
//-  }
//-
//-  public LocalDate getDate() {
//-    return date;
//-  }
//-
//-  public double getPrice() {
//-    return price;
//-  }
//-
//-  @Override
//-  public String toString() {
//-    return date.toString() + ", " + price + " €";
//-  }
}
// FILE_END
```

Notice that `average` does not return a `double` directly.
Instead, it returns an `OptionalDouble`.
This is because the stream may be empty—for example, there might not be any September purchases at all—and in that situation an average cannot be calculated.
We will return to this topic shortly.

***

## Terminal Stream Operations

All stream methods that return something other than a new stream are called *terminal operations*.
Terminal operations typically process all elements remaining in the stream and produce either a value or a side effect.

One common terminal operation is collecting the elements into a collection.
For example, `toList` gathers all stream elements into a list and `toArray` gathers them into an array.

```java
//-void main() {
List<Integer> grades = List.of(1, 4, 5, -1, 0, 15, 2, 4, 5);

List<Integer> validGrades = grades.stream()
                                  .filter(i -> 1 <= i && i <= 5)
                                  .sorted()
                                  .toList();

int[] validGradesArray = grades.stream()
                               .filter(i -> 1 <= i && i <= 5)
                               .mapToInt(i -> i.intValue())
                               .sorted()
                               .toArray();

IO.println(validGrades);
IO.println(Arrays.toString(validGradesArray));
//-}
```

Notice that after a terminal operation, the stream is considered *consumed*.
A consumed stream cannot normally be used again.
Trying to reuse it causes an error.

```java
//-void main() {
List<Integer> grades = List.of(1, 4, 5, -1, 0, 15, 2, 4, 5);

Stream<Integer> gradesStream = grades.stream()
                                     .filter(i -> 1 <= i && i <= 5)
                                     .sorted();

// toList() consumes the stream
List<Integer> gradesList = gradesStream.toList();

// ERROR: attempting to reuse a consumed stream
long gradeCount = gradesStream.count();
//-}
```

```
java.lang.IllegalStateException: stream has already been operated upon or closed
```

Like collections, streams also provide a `forEach` method that can execute arbitrary code for every element.

```java
//-void main() {
IntStream.range(0, 10)      // Integers 0–9
  .filter(i -> i % 2 == 1)  // Keep only odd numbers
  .forEach(IO::println);    // Print each number
//-}
```

Streams also include helper functions for common tasks.
For example, the methods `min` and `max` return the smallest and largest element in a stream.
Both methods take a `Comparator` as an argument.

```java
//-void main() {
List<String> teachers = List.of("Denis", "Antti-Jussi", "Sami", "Karri");

Optional<String> longestName = teachers.stream().max(Comparator.comparing(String::length));
Optional<String> shortestName = teachers.stream().min(Comparator.comparing(String::length));

IO.println("Longest: " + longestName);
IO.println("Shortest: " + shortestName);
//-}
```

Notice that `max`, `min`, and many other terminal operations do not return values directly.
Instead, they return an `Optional<T>` object.
([JavaDoc](https://docs.oracle.com/en/java/javase/25/docs//api/java.base/java/util/Optional.html)).
This type represents a value that may or may not exist.
For example, if a stream contains no elements, or if a terminal operation is otherwise unable to produce a result, it returns `Optional.empty` to indicate that the computation failed.

```java
//-void main() {

List<String> teachers = List.of("Denis", "Antti-Jussi", "Sami", "Karri");

Optional<String> longestName =
    teachers.stream()
            .filter(s -> s.startsWith("V"))
            .max(Comparator.comparing(String::length));

IO.println("Longest: " + longestName);
//-}
```

Before the returned value can be used, we should first check whether the `Optional<T>` actually contains a value.
This can be done, for example, using the `isPresent` method.
Once we know that a value exists, it can be retrieved with `get`.

```java
//-void main() {
List<String> teachers = List.of("Denis", "Antti-Jussi", "Sami", "Karri");
Optional<String> longestName = teachers.stream()
            .max(Comparator.comparing(String::length));

if (longestName.isPresent()) {
    String name = longestName.get();

    IO.println("Longest: " + name);
} else {
    IO.println("No names matched the criteria");
}
//-}
```

It is worth mentioning that `Optional<T>` provides many additional helper methods that often eliminate the need for explicit `if` statements.


Let's return briefly to streams.
Streams are very convenient for searching values in collections.
The method `findFirst` returns the first element that reaches the end of the data flow.
For example, suppose we want to find the first purchase event in an inventory application that occurred in September and had a price greater than €100.

```java
// FILE: main.java
//-void main() {
List<PurchaseEvent> purchaseEvents = List.of(
  new PurchaseEvent(100.0, LocalDate.of(2025, Month.JANUARY, 2)),
  new PurchaseEvent(21.5, LocalDate.of(2025, Month.JULY, 3)),
  new PurchaseEvent(12.0, LocalDate.of(2025, Month.SEPTEMBER, 1)),
  new PurchaseEvent(5.25, LocalDate.of(2025, Month.SEPTEMBER, 12)),
  new PurchaseEvent(245.0, LocalDate.of(2025, Month.SEPTEMBER, 21)),
  new PurchaseEvent(342.0, LocalDate.of(2025, Month.OCTOBER, 2))
);

Optional<PurchaseEvent> event =
                  purchaseEvents.stream()
                      .filter(p -> p.getDate().getMonth() == Month.SEPTEMBER)
                      .filter(p -> p.getPrice() > 100.0)
                      .findFirst();

if (event.isPresent()) {
  IO.println(event.get());
} else {
  IO.println("Event not found");
}

//-}

// FILE_END
// FILE: PurchaseEvent.java
import java.time.LocalDate;

public class PurchaseEvent {

  private double price;
  private LocalDate date;

//-
//-  public PurchaseEvent(double price, LocalDate date) {
//-    this.price = price;
//-    this.date = date;
//-  }
//-
//-  public LocalDate getDate() {
//-    return date;
//-  }
//-
//-  public double getPrice() {
//-    return price;
//-  }
//-
//-  @Override
//-  public String toString() {
//-    return date.toString() + ", " + price + " €";
//-  }
}

// FILE_END
```

Finally, streams also provide a number of statistical operations.
For example, the `count` method mentioned earlier returns the number of elements in the stream as a `long`.
In addition, the primitive stream types `IntStream`, `DoubleStream`, and `LongStream` provide statistical methods such as:

* `sum` – calculates the sum of all numbers
* `min` / `max` – finds the smallest or largest value
* `average` – calculates the arithmetic mean
* `summaryStatistics` – calculates the sum, minimum, maximum, and average simultaneously

```java
//-void main() {
IntStream numbers = new Random().ints(20, 0, 100);
IO.println(numbers.summaryStatistics());
//-}
```

## Exercises 

<task>
<task-title>Exercise 6.3: Music Playlist
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/6-3-music-playlist/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part6/exercise3">Complete this exercise in TIM</a></task-link>
</task>

<task>
<task-title>Exercise 6.4: Average with threshold values
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/6-4-rainfall/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part6/exercise4">Complete this exercise in TIM</a></task-link>
</task>