# Sets and Queue Structures

### Learning Objectives

* Be familiar with Java's most common built-in data structures: `Set`, `Queue`, `Deque`, and their implementations `HashSet` and `ArrayDeque`.
* Know how to use these data structures.
* Understand their most important operations and their time complexities.
* Understand why objects need a `hashCode()` method.

***

## Set Structures

Sets are collections in which all elements are unique.
If an attempt is made to add a duplicate element to a set, the state of the set does not change in any way.
This property makes sets useful whenever we want to ensure that the same piece of data appears at most once in a data structure.

For example, we could use a set to store the email addresses of recipients on a mailing list.
This guarantees that a single email address can appear at most once in the collection, preventing multiple copies of the same message from being sent to the same address.
Sets are also commonly used to model mathematical sets and their operations.

***

## Set

The Java Collections Framework includes the `Set` interface, which defines the basic rules for all set data structures.
`Set` inherits from the `Collection` interface, which means that all sets provide the functionality promised by `Collection`.
As a result, we can iterate through sets just as easily as we iterate through lists.

The most commonly used implementation of `Set` in Java is `HashSet`, which, as its name suggests, is based on a hash table.
Internally, `HashSet` uses a `HashMap` to store its elements as keys.
Because of this hash-table implementation, inserting, removing, and searching for elements are all very fast constant-time operations on average.
However, a hash table is a more complex structure than a simple array, so iterating through a `HashSet` is somewhat slower than iterating through an `ArrayList`, especially when the number of elements becomes very large.

Like the `Collection` and `Map` interfaces, neither `Set` nor its implementation `HashSet` guarantee any particular ordering of elements.
Other set implementations exist for situations where maintaining order is important.

Let's now look at how `HashSet` can be used through the `Set` interface.

***

### Adding and Removing Elements

Adding and removing elements in a `HashSet` works in much the same way as with other collections.
Elements are added using the `add` method and removed using the `remove` method.
Both methods return `true` if the set was *modified* by the operation—that is, if an element was actually added or removed.

Insertion and removal in a `HashSet` are average-case constant-time operations $O(1)$.

```java
//-void main() {
Set<String> names = new HashSet<>();

// Add elements.
names.add("Matti");
names.add("Maija");

// Returns false because the element is already in the set.
IO.println(names.add("Matti"));

// Remove an element.
IO.println(names.remove("Maija"));
IO.println(names.remove("Maija")); // Returns false because it is no longer present.
//-}
```

***

### Finding an Element

Searching for an element in a set is easy using the `contains()` method.
The method returns a boolean value indicating whether the element exists in the set.

Because `HashSet` is based on a hash table, it does not need to scan the entire data structure when searching.
Searching is therefore also an average-case constant-time operation.

```java
//-void main() {
Set<String> names = new HashSet<>();
names.add("Matti");
names.add("Maija");

if (names.contains("Matti")) {
    IO.println("Matti was found in the set!");
}
//-}
```

***

### Number of Elements and Iteration

Because `Set` inherits from `Collection`, we can use familiar methods such as `size()` and `isEmpty()` to examine the number of elements and determine whether the set is empty.
Thanks to the `Iterable` interface, we can also iterate through sets using a for-each loop.

Traversing all elements in a set has time complexity $O(n)$.

```java
//-void main() {
Set<String> names = new HashSet<>();
names.add("Matti");
names.add("Maija");

for (String name : names) {
    IO.println(name);
}

IO.println();
IO.println("The set contains " + names.size() + " elements.");
IO.println("Is empty: " + names.isEmpty());
//-}
```

***

## Element Ordering

`HashSet` does not preserve any ordering of elements.
However, just as with maps, there are set implementations that preserve either insertion order or natural ordering.

`LinkedHashSet` inherits from `HashSet` and, similarly to `LinkedHashMap`, maintains an internal linked list to preserve insertion order.
The primary operations still have average-case time complexity $O(1)$,
although some additional memory is required for maintaining the linked list.

```java
//-void main() {
LinkedHashSet<String> linked = new LinkedHashSet<>();
linked.add("Joni Virtanen");
linked.add("Maija Meikäläinen");
linked.add("Matti Korhonen");

// LinkedHashSet preserves insertion order.
IO.println(linked);
//-}
```

`TreeSet` implements the `SortedSet` and `NavigableSet` interfaces and stores elements in their natural ordering, similarly to `TreeMap`.
Unlike `HashSet`, it is not based on a hash table but instead on a balanced tree structure.
As a result, its fundamental operations are slightly slower.
Insertion, removal, and searching have average-case complexity
$O(\log n)$.

```java
//-void main() {
TreeSet<String> tree = new TreeSet<>();

tree.add("Joni Virtanen");
tree.add("Maija Meikäläinen");
tree.add("Matti Korhonen");

// TreeSet automatically sorts elements into natural order.
IO.println(tree);
//-}
```

The `SortedSet` and `NavigableSet` interfaces also provide useful methods for managing ordered collections.
These operations are very similar to those provided by the `SortedMap` and `NavigableMap` interfaces.

```java
//-void main() {
NavigableSet<Integer> tree = new TreeSet<>();
tree.add(1);
tree.add(2);
tree.add(5);
tree.add(4);
tree.add(3);

// Elements are sorted.
IO.println(tree);

// Print smallest and largest elements.
IO.println("Smallest element: " + tree.first()); // 1
IO.println("Largest element: " + tree.last()); // 5

// Print nearest lower and higher elements.
IO.println(tree.lower(3));  // 2
IO.println(tree.higher(3)); // 4

// Return the set in reverse order.
IO.println(tree.descendingSet());
//-}
```

`NavigableSet` also supports creating subsets using the `subSet()` method.

```java
//-void main() {
NavigableSet<Integer> tree = new TreeSet<>();
tree.add(1);
tree.add(2);
tree.add(5);
tree.add(4);
tree.add(3);

// Create a new set containing
// elements between 3 and 5.
//
// The true values indicate that
// 3 and 5 are included.
Set<Integer> subset = tree.subSet(3, true, 5, true);
IO.println(subset);
//-}
```

***

## Queues and Stacks

Queues and stacks are linear data structures similar to lists.
These structures are particularly useful when we are mainly interested in elements located at the beginning or end of a collection.
In practice, queues and stacks are specialized forms of lists where insertion, removal, and retrieval operations are restricted to the ends of the structure.
The concepts themselves are extremely important in computer science and are used frequently in real-world systems.

In addition to ordinary queues, there are double-ended queues, which combine the functionality of queues and stacks by allowing insertion and removal operations at both ends.
In Java, a double-ended queue also provides many additional useful operations and is used to implement both queues and stacks.

***

## Stack

A *stack* is a data structure that follows the *last in, first out* (**LIFO**) principle.
Elements can only be added to the top of the stack, and removals always occur from the top.

A common example is the *Undo* feature of a word processor.
Each modification is pushed onto the top of the stack, and each undo operation removes the most recent modification.


> [!NOTE]
> Unlike many other languages, Java does **not** provide a `Stack` interface.
> Historically, Java includes a `Stack` class, but its use is no longer recommended.
> Modern Java uses a double-ended queue—typically `ArrayDeque` through the `Deque` interface—to implement stacks.

The fundamental stack operations are
`push`, `pop` and  `peek`.
The `Deque` interface provides these methods directly.

```java
//-void main() {
Deque<String> stack = new ArrayDeque<>();
// Push elements. Doesn't retur anything
stack.push("A");
stack.push("B");
stack.push("C");

// Notice that the most recently added element appears first.
IO.println(stack);

// Returns "C" but does not remove it.
// Returns null if empty.
IO.println(stack.peek());

// Returns "C" and removes it.
// Returns null if empty.
IO.println(stack.pop());

IO.println(stack);

//-}
```
## Queue

A *queue* follows the *first in, first out* (**FIFO**) principle.
The first element added to the queue is also the first one removed.

Queues behave very much as their name suggests.
Network routers provide a good example of queue usage: packets waiting to be transmitted are stored in a queue and sent onward in the same order in which they arrived.

The `Queue` interface in Java defines the common functionality of queue data structures.
It extends the `Collection` interface and provides methods for adding elements to the end of the queue and removing or examining elements at the front.

For each of these three basic operations, the `Queue` interface defines two versions that handle errors differently.
The methods `add`, `remove`, and `element` throw an exception when an error occurs.
The methods `offer`, `poll`, and `peek`, on the other hand, return a special value instead.
Since exceptions will be discussed later in this course, we will focus for now on the methods `offer`, `poll`, and `peek`, which work just as well.

The `ArrayDeque` class implements the `Queue` interface and therefore provides all of the required methods.

We will use it to implement a queue.

```java
//-void main() {
Queue<String> queue = new ArrayDeque<>();

// Add elements to the end of the queue.
// 'offer' returns true if adding succeeds and false otherwise.
queue.offer("A");
queue.offer("B");

IO.println(queue); // [A, B]

// Examine the first element without
// removing it.
// 'peek' returns null if the queue
// contains no elements.
IO.println(queue.peek()); // "A"

// Remove elements from the front of the queue.
// 'poll' returns null if the queue contains no elements.
IO.println(queue.poll()); // "A"
IO.println(queue.poll()); // "B"
IO.println(queue.poll()); // null

//-}
```

***

## Double-Ended Queue

A double-ended queue is known as a *deque*, which is short for *double-ended queue*.
We could use such a structure to model, for example, a queue where particularly important items may occasionally be inserted at the front.

The `Deque` interface defines all functionality required by double-ended queues.
In practice, this means it provides all methods needed for both queues and stacks, as well as additional useful operations such as iterating through elements in reverse order.
Note that `Deque` also contains all queue- and stack-related methods discussed earlier.

The most commonly used implementation of `Deque` is `ArrayDeque`, which, as its name suggests, uses an array as its internal data structure.
`Deque` implements the `Collection` interface, which means familiar methods such as `size`, `isEmpty`, and `contains` are also available.

***

### Adding and Removing Elements

Like `Queue`, `Deque` defines two styles of methods:
methods that throw exceptions when an operation fails and
methods that return `null` or `false` when an operation fails.

Elements can be added:
to the front using `addFirst()` or `offerFirst()`,
and to the back using `addLast()` or `offerLast()`.
For now we will use `offerFirst()` and `offerLast()` because they avoid exceptions.

```java
//-void main() {
Deque<Integer> numbers = new ArrayDeque<>();

// 'offerFirst' inserts at the front.  // 'offerLast' inserts at the back.
// Both return false if insertion fails.
numbers.offerFirst(2);
IO.println(numbers); // [2]
numbers.offerLast(3);
IO.println(numbers); // [2, 3]
numbers.offerFirst(1);
IO.println(numbers); // [1, 2, 3]
//-}
```

Removal works similarly from either end.
The methods `removeFirst` and `removeLast` remove elements or throw an exception if the deque is empty.
The methods `pollFirst` and `pollLast` instead return `null` when no element can be removed, making them safer to use at this stage.

We can also examine elements without removing them using `peekFirst` and `peekLast`.

```java
//-void main() {
Deque<Integer> numbers = new ArrayDeque<>();
numbers.offerFirst(3);
numbers.offerFirst(2);
numbers.offerFirst(1);

IO.println(numbers); // [1, 2, 3]

// Examine first and last values.
IO.println(numbers.peekFirst()); // 1
IO.println(numbers.peekLast());  // 3

// Remove first and last values.
// Removal returns the removed element
// if successful, otherwise null.
numbers.pollFirst();
numbers.pollLast();

IO.println(numbers); // [2]
//-}
```

Both insertion and removal at either end occur in constant time $O(1)$.
This is possible because `ArrayDeque` is implemented as a circular array whose head and tail positions are tracked using indexes.
Elements do not need to be shifted when inserting or removing from either end.

`ArrayDeque` also provides the `remove()` method inherited from the `Collection` interface.
This method removes a specific element regardless of its position, behaving much like removal in a list by scanning through the structure until the element is found.

***

### Iterating Through Elements

`ArrayDeque` implements the `Iterable` interface, so it can be traversed using a for-each loop.
In addition, the method `descendingIterator` returns an iterator that allows elements to be visited in reverse order.
The time complexity of iterating through all elements is $O(n)$.
which is the same as for an ordinary list.

```java
//-void main() {
Deque<Integer> numbers = new ArrayDeque<>();
numbers.offerFirst(3);
numbers.offerFirst(2);
numbers.offerFirst(1);

// Normal traversal.
for (Integer number : numbers) {
    IO.println(number);
}

// Reverse traversal.
Iterator<Integer> it = numbers.descendingIterator();
while (it.hasNext()) {
    IO.println(it.next());
}
//-}
```

***

## Priority Queue

A *priority queue* is a special type of queue implemented in Java by the `PriorityQueue` class.
An ordinary queue preserves insertion order.
A priority queue instead ensures that the *smallest* element according to natural ordering is always returned first whenever we request the next element.

```java
//-void main() {
PriorityQueue<Integer> numbers = new PriorityQueue<>();
numbers.offer(2);
numbers.offer(3);
numbers.offer(1);
numbers.offer(5);
numbers.offer(4);

// Notice that printing the queue
// does not necessarily display
// the elements in sorted order.
IO.println(numbers);

// A priority queue always returns
// the "most important" element,
// which here means the smallest one.
IO.println(numbers.poll()); // 1
IO.println(numbers.poll()); // 2
IO.println(numbers.poll()); // 3
IO.println(numbers.poll()); // 4
IO.println(numbers.poll()); // 5

//-}
```

The time complexity of `PriorityQueue` differs from ordinary queue structures because it is internally based on a *heap*.
Insertion and removal both have time complexity $O(\log n)$.


Requesting the smallest element, however, is very efficient and occurs in constant time $O(1)$.

***

## Exercises 

<task>
<task-title>Exercise 5.8: Sets
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/5-8-sets/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part5/exercise8">Complete this exercise in TIM</a></task-link>
</task>

<task>
<task-title>Exercise 5.9: Task List
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/5-9-task-list/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part5/exercise9">Complete this exercise in TIM</a></task-link>
</task>

<task>
<task-title><i class="bi bi-stars jyu-gold"></i> Exercise 5.10: Parentheses
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/5-10-parentheses/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part5/exercise10">Complete this exercise in TIM</a></task-link>
</task>
