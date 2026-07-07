# Collections

## Learning Objectives

- Understand what a collection is and why collections are used in programming.
- Understand the basic structure of the Java *Collections Framework*.
- Be familiar with the methods provided by Java's `Collection` interface and know how to use them in programs.

At the heart of programming is often the task of organizing data into meaningful structures.
Earlier, we used arrays and lists to store collections of similar values, such as temperatures or students. We have also learned object-oriented programming, which allows us to bundle data and related functionality together.

However, a simple list of values is not always enough.
Although arrays and lists are useful, real-world problems often require more specific rules regarding how data is added, removed, or retrieved.

In Java, the term **collection** refers to an object whose purpose is to manage a group of other values or objects.
A collection is not merely a container; it is a data structure that defines the rules for handling data.
Choosing the correct data structure is an important skill for a programmer because it affects both program efficiency and code readability.

Here are some examples of situations where an array or a list is no longer the optimal solution:

* **Queue**: When you call a customer service center, incoming calls are placed in a queue. New callers join the end of the queue, and the customer service representative always serves the caller at the front. This "first in, first out" structure is called a queue. A queue is optimized for these kinds of insertions and removals, unlike a regular list. A *stack*, on the other hand, works in the opposite way: the last item added is the first one removed, much like a stack of plates.

* **Set**: In a service such as Discord or in a contact list, it usually does not make sense to add the same person as a friend twice. A structure that ensures each element appears only once (unique values) is called a set.

* **Map**: In a student information system, each student number corresponds to a particular grade. This is not simply a list; it involves *keys* (student numbers) and associated *values* (grades). A structure that allows data to be retrieved using a unique key is called a map or associative array.

In principle, we could implement all of this behavior using ordinary lists and a large amount of additional code, such as `if` statements to detect duplicates or loops to search for values.
The Java Collections Framework provides ready-made, optimized, and easy-to-use tools for these situations.

***

## The Java Collections Framework

Java provides a large collection of ready-made data structures as well as interfaces for creating new ones through the [Java Collections Framework](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/doc-files/coll-overview.html).

The Collections Framework is built around two main concepts:

* *Collection interfaces*, which define what operations can be performed on a collection (for example, `List` and `Set`).
* *Concrete implementation classes*, which implement one or more interfaces in a particular way (for example, `ArrayList`, `HashSet`, and `HashMap`).

For example, `List` is a collection interface. It defines the methods that a list should provide, but it does not specify how those methods are implemented.
`ArrayList`, on the other hand, is a class that implements the `List` interface using an array internally.
Other list implementations will be discussed later in [Chapter 5.2](./02-list-data-structures.md).

The most important interface in the Collections Framework is [`Collection`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Collection.html), which serves as a high-level general-purpose interface.
The `List` interface inherits from `Collection`, so an `ArrayList` can be stored in a variable of type `Collection`.

```java
//-void main() {
Collection<String> berries = new ArrayList<>(
    List.of("strawberry", "blueberry", "lingonberry", "cloudberry")
);

IO.println(berries);
//-}
```

The `Collection` interface makes no assumptions about the order of elements or the specific contents of the collection.
A large number of Java's built-in collections implement the `Collection` interface.
Next, let's look at the operations available through this interface.

***

## Adding and Removing Elements

Elements are added using the `add()` method and removed using the `remove()` method.
Both methods return `true` if the collection was modified.

```java
//-void main() {
Collection<String> berries = new ArrayList<>(
    List.of("strawberry", "blueberry", "lingonberry", "cloudberry")
);

berries.add("cherry");
IO.println(berries);

berries.remove("strawberry");
IO.println(berries);
//-}
```

Adding an element does not always succeed in every collection implementation.
For example, a `Set` does not allow duplicate elements, so `add()` may return `false`.
Likewise, `remove()` removes the first element found using the `equals()` method and returns `false` if no matching element exists.

The `Collection` interface does not include the concept of indexes.
Therefore, `remove()` removes elements based on value rather than position, such as "the third element."
If you want to add or remove elements using indexes, you need the `List` interface instead.

In addition to removing individual elements, an entire collection can be emptied using the `clear()` method.

```java
//-void main() {
Collection<String> berries = new ArrayList<>(
    List.of("strawberry", "blueberry", "lingonberry", "cloudberry")
);

berries.clear();
IO.println(berries);
//-}
```

***

## Checking Whether an Element Exists

The `Collection` interface also defines the `contains()` method, which allows you to check whether a collection contains a particular element.
The check is based on the `equals()` method. Therefore, when working with your own classes, `equals()` should be implemented appropriately. We will return to this topic in [Chapter 5.2](./02-list-data-structures.md).

```java
//-void main() {
Collection<String> berries = new ArrayList<>(
    List.of("blueberry", "lingonberry", "cloudberry", "cherry")
);

IO.println("Contains blueberry: " + berries.contains("blueberry"));

// Strawberry was removed earlier, so it should not be found.
IO.println("Contains strawberry: " + berries.contains("strawberry"));
//-}
```

***

## Number of Elements and Emptiness

The `Collection` interface defines the methods `size` and `isEmpty`, which allow you to determine how many elements are stored in a collection and whether the collection is empty.
The `isEmpty()` method is often a clearer way to express that you are only interested in whether the collection contains any elements.

```java
//-void main() {
Collection<String> berries = new ArrayList<>(
    List.of("blueberry", "lingonberry", "cloudberry", "cherry")
);

IO.println("Number of berries: " + berries.size());
IO.println("Is the berry collection empty: " + berries.isEmpty());
//-}
```

***

## Iterating Through Elements

The `Collection` interface inherits from the `Iterable` interface.
Because of this, the elements of a collection can be traversed using a for-each loop.

```java
//-void main() {
Collection<String> berries = new ArrayList<>(
    List.of("blueberry", "lingonberry", "cloudberry", "cherry")
);

for (String berry : berries) {
    IO.println("Berry: " + berry);
}
//-}
```

The `Collection` interface does not make any guarantees about the ordering of elements.
As a result, iteration is not based on indexes, and the order of traversal always depends on the specific collection implementation being used.
For example, `ArrayList` preserves insertion order, whereas `HashSet` does not guarantee any particular ordering.

As a side note, `Collection` truly does *inherit* the `Iterable` interface.
We did not discuss interface inheritance earlier, but the idea works in interfaces just as it does with classes: every implementation of `Collection` must also satisfy the requirements defined by `Iterable`.
