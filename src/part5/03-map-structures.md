# Map Data Structures

### Learning Objectives

* Be familiar with Java's most common built-in map data structures: `Map` and its implementations `HashMap`, `LinkedHashMap`, and `TreeMap`.
* Know how to use these data structures.
* Understand their most important operations and recognize their time complexities.
* Understand why objects need a `hashCode()` method.

Imagine that we maintain a large student registry.
We want to store each student's ID number and name so that we can easily retrieve the student's name based on the student number.

If we used a list for this purpose, we would either need multiple lists or a separate object containing all student information.
Whenever we wanted to find a particular student, we would need to traverse the list until the correct student was located. This becomes increasingly inefficient as the number of students grows.

Map data structures provide a solution by allowing direct lookup based on a unique identifier, often without traversing the entire collection.
In this case, the identifier could be the student number.
A map works somewhat like a dictionary: instead of reading an entire book from beginning to end to find a specific word, we can look directly at the correct entry.
In many programming languages, map structures are therefore called *dictionaries*. 
In Java, they are known as *maps*.

```java
//-void main() {
// Create key–value pairs.
Map<String, String> students = Map.of(
    "123", "Joni",
    "555", "Maija",
    "789", "Mikko"
);

// We can retrieve student information
// directly using the key.
IO.println("Student with ID 123: " + students.get("123"));
//-}
```

Map data structures store information as *key–value pairs*, also known as *entries*.
Whereas a list uses a unique integer index as the route to an element, a map uses a unique object of any type as the route to a value.
Keys are always unique. A key may appear in a map only once and can refer to only one value at a time.
Values, however, do not need to be unique.

> [!NOTE]
> In Java, both keys and values are always objects.
> They cannot be primitive types such as `int` or `double`; instead, wrapper classes such as `Integer` and `Double` must be used.

***

## The `Map` Interface

`Map` is the second major interface of the Java Collections Framework alongside the `Collection` interface.
It defines the general rules for all map data structures and provides the most important operations for storing and manipulating key–value pairs.
Like `Collection`, the `Map` interface makes no assumptions about element ordering or contents and does not require its implementations to use any particular internal data structure.

Unlike `Collection`, `Map` does not implement the `Iterable` interface.
As a result, maps cannot be iterated directly using a for-each loop. Instead, iteration must be performed through keys, values, or entries.

In the examples below we use the concrete class `HashMap`, but all methods shown are defined in the `Map` interface and therefore work with any `Map` implementation.

***

## Adding and Removing Elements

Elements can be added using the methods `put` and `putIfAbsent`.
Both methods take a key and its corresponding value as parameters.
If the key already exists in the map, the methods return the previous value associated with the key before replacement. If the key does not already exist, they return `null`.

```java
//-void main() {
Map<String, Integer> grades = new HashMap<>();

// Add or replace a value.
grades.put("Maija", 3);
grades.put("Maija", 5); // Returns 3 and replaces it.

// Add only if absent.
grades.putIfAbsent("Joni", 1);
grades.putIfAbsent("Joni", 0); // Returns 1 but does not replace it.
IO.println(grades);
//-}
```

Removal works using the `remove` method, just as with collections.
The key to be removed is provided as a parameter.
Like insertion methods, `remove` returns the value associated with the key if it exists. Otherwise it returns `null`.

```java
//-void main() {
Map<String, Integer> grades = new HashMap<>();
grades.put("Maija", 5);
grades.put("Joni", 5);

// Remove the key–value pair whose key is "Joni".
grades.remove("Joni");

IO.println(grades);
//-}
```

***

## Retrieving a Value Using a Key

Values can be retrieved using the methods `get()` and `getOrDefault()`.
If the key does not exist, `get()` returns `null`.
The `getOrDefault()` method allows you to define your own default value.

```java
//-void main() {
Map<String, Integer> grades = new HashMap<>();
grades.put("Maija", 5);

// Value is 5.
IO.println("Maija's grade: " + grades.get("Maija"));

// Key does not exist, so value is null.
IO.println("Joni's grade: " + grades.get("Joni"));

// Use default value 0.
IO.println("Joni's grade: " + grades.getOrDefault("Joni", 0));
//-}
```

We can also check whether a map contains a particular key or value using the methods `containsKey()` and `containsValue()`, which return either `true` or `false`.

```java
//-void main() {
Map<String, Integer> grades = new HashMap<>();
grades.put("Maija", 5);

IO.println(grades.containsKey("Maija")); // true
IO.println(grades.containsKey("Matti")); // false

IO.println(grades.containsValue(5)); // true
IO.println(grades.containsKey(0)); // false
//-}
```

***

## Number of Elements

Like the `Collection` interface, `Map` defines the methods `size()` and `isEmpty()`.
These allow us to determine the number of entries stored in the map and whether the map is empty.

```java
//-void main() {
Map<String, Integer> grades = new HashMap<>();
grades.put("Maija", 5);
IO.println("Entries: " + grades.size()); // 1
IO.println("Empty: " + grades.isEmpty()); // false
//-}
```

***

## Iterating Through Entries

`Map` does not implement `Iterable` and therefore cannot be iterated over directly with a for-each loop.
Instead, it provides the methods `keySet`, `values`, and `entrySet`, which return the keys, values, or key–value pairs as collections.

Note that `entrySet` returns a collection of `Map.Entry<K,V>` objects, where `K` and `V` correspond to the key and value types of the map.
`Map.Entry` represents a key–value pair and provides the methods `getKey` and `getValue`.

```java
//-void main() {
Map<String, Integer> grades = new HashMap<>();
grades.put("Maija", 3);
grades.put("Matti", 4);

// Iterate through all keys.
for (String key : grades.keySet()) {
    IO.println(key + " : " + grades.get(key));
}

// Iterate through all values.
for (Integer value : grades.values()) {
    IO.println(value);
}

// Iterate through all key–value pairs.
for (Map.Entry<String, Integer> pair : grades.entrySet()) {
    IO.println(pair.getKey() + " : " + pair.getValue());
}
//-}
```

<!-- > [!NOTE]
> As we have learned statically typed programming languages brings compile-time safety 
> and preservation of types.
> But it is not always nice to write the type out fully like in the above example
> `Map.Entry<String, Integer> pair>`.
> For these kind of cases java has a reserved type name `var`, which
> locally infers the types of variables, so the above example could be written.
>  ```java
> //-void main() {
> Map<String, Integer> grades = new HashMap<>();
> grades.put("Maija", 3);
> grades.put("Matti", 4);
> 
> // Iterate through all keys.
> for (var key : grades.keySet()) {
>     IO.println(key + " : " + grades.get(key));
> }
> 
> // Iterate through all values.
> for (var value : grades.values()) {
>     IO.println(value);
> }
> 
> // Iterate through all key–value pairs.
> for (var pair : grades.entrySet()) {
>     IO.println(pair.getKey() + " : " + pair.getValue());
> }
> //-}
> ``` -->

***

## Hash Tables

Before examining specific `Map` implementations, let's first look at the general principle behind a *hash table*.
A hash table is the underlying data structure used by several Java map implementations, including `HashMap` and `LinkedHashMap`.

A hash table is based on an array used to store key–value pairs.
The key idea is that information does not have to be found by searching sequentially. Instead, the correct location is calculated directly from the key.

When an entry is inserted into a hash table:

1. Hash value: A key is converted into an integer using the `hashCode()` method.
2. Index: Because the hash value may be very large or even negative, it must be transformed into a valid array index.

This is typically done using the remainder operation:

```text
index = abs(hashValue % capacity)
```

The resulting index is always within the range:

```text
[0 .. capacity - 1]
```

Because there are vastly more possible hash values than array positions, multiple keys may end up at the same index.
This is called a collision.
Two main strategies are used to handle collisions:

* Chaining: This is used by Java's `HashMap`. Each index contains a bucket, typically implemented as a list. Entries that map to the same index are stored in the same bucket.
* Open Addressing: Each index can contain only one element. If a position is occupied, another available position is searched for.

When retrieving an element, the same index calculation is performed.
The remaining search process depends on the collision-resolution method being used.

The number of collisions directly affects performance.
Insertion, removal, and lookup are extremely fast in a hash table.

Their average-case time complexity is:
$O(1)$
because the correct index can usually be found directly through a simple calculation.
In the worst case, all elements end up in the same bucket.
Finding the correct element then requires traversing the entire structure, resulting in a time complexity of:
$O(n)$.
where $n$ is the number of stored elements.

> [!NOTE]
> Time complexities are covered in much greater detail in the **Algorithms 1** course; here we will only touch on them briefly.
> For the purposes of this course, it is sufficient to understand that $O(1)$ means that an operation runs in constant time, meaning that its running time does not depend on the size of the data structure.
> $O(n)$ means that the running time grows proportionally to the size of the data structure. For example, if a data structure contains 1,000 elements, an $O(n)$ operation would require approximately 1,000 times more work than an $O(1)$ operation.

In order for a hash table to remain fast ($O(1)$), collisions must be minimized.
This is influenced by the *load factor*. If the capacity of the underlying array is too small relative to the number of stored elements, the table becomes crowded and collisions become more frequent.
When the load factor exceeds a certain threshold, the hash table is resized—typically by doubling its capacity—and all existing elements are reinserted into a new, larger table.
This resizing operation is relatively expensive, which is why choosing a reasonable initial capacity is important.

A hash table can be compared to a storage facility containing many storage bins into which any number of items can be placed.
The capacity corresponds to the number of storage bins, and the bins themselves correspond to the *indexes* of the hash table.
The bins provide a way to organize items so that finding a particular item becomes easier.
One bin might be designated for clothing, another for tools, and a third for everything else.
As a simplified analogy, we can imagine that all tools produce the same result from the hash function and therefore end up in the same storage bin.
When searching for a hammer, we can immediately look in the tools bin. However, if that bin contains a large number of items, finding the correct tool may still take some time.

## HashMap

`HashMap` is the most commonly used implementation of the `Map` interface.
Its implementation is based on the hash table described above, and it provides the best average-case performance for the basic map operations.
Looking up, inserting, and removing elements by key can, in the best case, be performed in constant time
$O(1)$.
Iterating through all elements is slightly slower than with a simple list because of the more complex internal structure.
The performance differences become most noticeable when the number of elements is very large. With small collections, the difference compared to a list is usually insignificant.

`HashMap` does not guarantee any ordering of elements.
When iterating through a map, entries may appear in any order, and that order may change when new elements are added.

***

## LinkedHashMap

`LinkedHashMap` is a class that extends `HashMap`.
In addition to the underlying hash table, it internally maintains a linked list containing all inserted entries.
This allows it to preserve **insertion order**, although the additional structure requires slightly more memory.

```java
//-void main() {
Map<String, Integer> hashMap = new HashMap<>();
hashMap.put("Joni Virtanen", 20);
hashMap.put("Maija Meikäläinen", 10);
hashMap.put("Matti Korhonen", 5);

// Insertion order is not guaranteed.
for (String key : hashMap.keySet()) {
    IO.println(key + " : " + hashMap.get(key));
}

IO.println();

Map<String, Integer> linked = new LinkedHashMap<>();

linked.put("Joni Virtanen", 20);
linked.put("Maija Meikäläinen", 10);
linked.put("Matti Korhonen", 5);

// Insertion order is preserved.
for (String key : linked.keySet()) {
    IO.println(key + " : " + linked.get(key));
}

//-}
```

`LinkedHashMap` is well suited for situations such as storing recently viewed products in an online store.
A product can be retrieved quickly using its product identifier as a key, while the products remain in the same order in which the user viewed them.
This makes the structure useful when both fast lookup and a user-friendly ordering are required.

***

## TreeMap

`TreeMap` differs from the previous implementations in that it uses a tree structure rather than a hash table as its underlying data structure.
It implements the interfaces `SortedMap` and `NavigableMap`.
`SortedMap` guarantees that keys are always maintained in **natural sorted order**, and `NavigableMap` extends this functionality by providing operations for finding keys immediately above or below a given key.
Unlike the other map implementations discussed in this chapter, `TreeMap` does not allow `null` as a key because `null` values cannot be compared with other keys to determine their order.

Because of its tree structure, `TreeMap` is slower than `HashMap`, but it provides automatic ordering of keys.
The time complexity of its main operations is $O(log n)$.

```java
//-void main() {
Map<String, Integer> tree = new TreeMap<>();
tree.put("Olli", 100);
tree.put("Heikki", 200);
tree.put("Anna", 300);

// Prints entries ordered by key.
for (String key : tree.keySet()) {
    IO.println(key + " : " + tree.get(key));
}
//-}
```

The `NavigableMap` interface also provides several methods that use the ordering of keys.

```java
//-void main() {
NavigableMap<String, Integer> tree = new TreeMap<>();
tree.put("B", 2);
tree.put("H", 3);
tree.put("A", 1);
tree.put("Q", 1);

// Print smallest and largest keys.
IO.println("Smallest key: " + tree.firstKey());
IO.println("Largest key: " + tree.lastKey());

// Print nearest lower and higher keys.
IO.println(tree.lowerKey("B"));
IO.println(tree.higherKey("B"));

// Return the entire map in reverse order.
IO.println(tree.descendingMap());
//-}
```

In addition, `TreeMap` allows portions of the tree to be extracted using the `subMap()` method.

```java
//-void main() {
NavigableMap<String, Integer> tree = new TreeMap<>();
tree.put("B", 2);
tree.put("H", 3);
tree.put("A", 1);

// Create a new map containing
// entries from A through C.
// The true values indicate that
// A and C themselves are included.
Map<String, Integer> subtree = tree.subMap("A", true, "C", true);
IO.println(subtree);
//-}
```

A good example of a use case for `TreeMap` is storing events that are indexed by timestamps.
Suppose a system collects log entries where each event has a precise timestamp and an associated description.
In this situation, a structure such as
`TreeMap<LocalDateTime, Event>`
is a very good choice.
Its main advantage is automatic ordering of keys.
New events are inserted directly into their correct chronological position, allowing the data to be processed in time order without any additional sorting.

`TreeMap` also supports fast searches within a specific time interval through methods such as:
subMap, headMap and tailMap.
It can also locate nearby values efficiently.
For example:
 `floorKey` finds the greatest key less than or equal to a given key.
`ceilingKey` finds the smallest key greater than or equal to a given key.
All of these operations have a time complexity of $O(\log n)$
which is significantly more efficient than scanning an unordered structure.
`TreeMap` is therefore especially useful when range queries and ordered searches are important.

The following table summarizes the strengths and weaknesses of different structures in scenarios where ordering matters.

| Structure         | Advantage                                        | Disadvantage                                                                           |
| ----------------- | ------------------------------------------------ | -------------------------------------------------------------------------------------- |
| **HashMap**       | Average lookup by key is faster (`O(1)`).        | Ordering is lost. Range queries require traversing or separately sorting all elements. |
| **LinkedHashMap** | Preserves insertion order.                       | Does not guarantee chronological order if data is inserted out of order.               |
| **PriorityQueue** | Fast access to extreme values (minimum/maximum). | Does not support efficient lookup by arbitrary key or efficient range searches.        |

The appropriate choice therefore depends on the requirements of the application.
`HashMap` is usually the best choice when the primary goal is the fastest possible key-based lookup and element ordering is irrelevant.
`LinkedHashMap` is useful when insertion order must be preserved.
`TreeMap` is somewhat slower, but it is the correct choice when keys must remain continuously sorted and ordering-based queries are required.

---

## Exercises

<task>
<task-title>Exercise 5.5: Words
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/5-5-words/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part5/exercise5">Complete this exercise in TIM</a></task-link>
</task>

<task>
<task-title> Exercise 5.6: Reservations
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/5-6-reservations/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part5/exercise6">Complete this exercise in TIM</a></task-link>
</task>

<task>
<task-title>Exercise 5.7: Hash Table
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/5-7-hash-table/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part5/exercise7">Complete this exercise in TIM</a></task-link>
</task>
