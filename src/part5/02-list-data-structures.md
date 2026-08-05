# List Structures

The `List` interface describes a collection whose elements are stored in a specific order and can be accessed using an index.
In many ways, this resembles an array, but it provides a significantly more flexible interface.
As a side note, it would be more precise to say that we are dealing with the `List<E>` interface, where `E` represents the type of the list's elements. However, for the sake of readability, we will ignore generics in this discussion.

Imagine a program that stores student names in the order in which students enrolled in a course.
In this situation, order matters, and the same name may appear multiple times because two different people can share the same name.

```java
//-void main() {
List<String> students = new ArrayList<>();
students.add("Aino");
students.add("Ville");
students.add("Aino");

IO.println(students.get(1)); // Ville
//-}
```

Using a list feels natural because the data is viewed as a sequence:
first, second, third.
`ArrayList` is the most common choice because it provides fast access to elements through indexes.
At this stage of the course, you can generally think of `List` as meaning `ArrayList`, unless there is a specific reason to use a different implementation.

The relative order of elements is preserved even when elements are removed. When one element is removed, all subsequent elements automatically shift one position toward the beginning of the list.

```java
//-void main() {
List<String> students = new ArrayList<>();
students.add("Aino");
students.add("Ville");
students.add("Aino");
students.remove("Ville");
IO.println(students);
//-}
```

Every element in a list always has a particular position, called its index. Because of this, a list is always traversed in a predictable order.
This makes lists suitable for situations where 
order matters,
order should be modified or preserved, or duplicate elements are allowed.

***

## Our Own List Implementation

Next, we will build a simple dynamic list structure on top of an array.
The goal is to illustrate the basic principles behind how `ArrayList` works internally.
The implementation will not be complete and will not cover the entire `List` interface, but it is sufficient for understanding the core ideas.

We begin by creating our own class `List<T>`, which contains an array for storing elements.
The following code is provided:

```java,ignore
public class List<T> {
    private T[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public List(int capacity) {
        this.elements = (T[]) new Object[capacity];
        this.size = 0;
    }
}
```

As a result, the `elements` array is initialized with `capacity` number of `null` values.
At this point, it is necessary to explain why
`@SuppressWarnings("unchecked")`
is required.
Java does not allow us to directly create a generic array of type `T`.
A type parameter such as `T` exists only during compilation. At runtime, Java does not know what `T` actually represents. This phenomenon is known as type erasure.
Arrays, however, are type-aware at runtime. When an array is created, the JVM knows exactly what type of elements may be stored in it.
This creates a conflict:
Java cannot create an array of type `T` because the real type of `T` is not available at runtime.

Therefore, the following is not allowed:

```java,ingore
new T[10]; // Compilation error
```

The only way around this limitation is to create an array of the most general reference type, `Object`, and cast it into a generic array.

```java,ignore
(T[]) new Object[capacity];
```

The compiler knows that this conversion is not completely safe.
It cannot prove that elements of the wrong type will never end up in the array.
The annotation
`@SuppressWarnings("unchecked")`
does not make the code safer or less safe. It merely tells the compiler that you are aware of this limitation and choose to accept it.
Without the annotation, the program behaves exactly the same way, but the compiler displays a warning.

<details><summary><i class="bi bi-stars jyu-gold"></i> Optional information: In which cases type cast might cause issues?</summary>

Suppose we create a `List<String>` object.
In this case, `T` is `String`.
Internally, however, the array used to store elements is actually an `Object[]`.
As long as the list is used correctly, there is no problem.
However, Java allows the following:

```java,ignore
List<String> names = new List<>(10); // List, where T is string and length 10
Object o = names;
List<Integer> numbers = (List<Integer>) o; // unchecked cast
```
The compiler warns about this code but still allows it.
Both references now point to the same list.

Next, we add an element through the `numbers` reference:

```java,ingore
numbers.add(42); // assuming add() has been implemented
```

This succeeds at runtime because the underlying array is actually an `Object[]`, and an `Integer` is an `Object`.
The JVM does not detect any problem at this stage.
The problem appears when we later retrieve an element:

```java,ingore
String s = names.get(0); // ClassCastException
```
At this point, the JVM attempts to convert the value into a `String` because the list is a `List<String>`.
However, the stored element is actually an `Integer`, so a `ClassCastException` occurs.
This is the exact reason why the compiler warns about unchecked conversions.
It cannot guarantee that the internal type contract of the list will remain valid in all circumstances.
It is important to note that the problem does not come from normal use of the list itself. The problem is caused by intentionally bypassing generic type safety through explicit casts.
The standard Java collections face the same limitation internally, but their interfaces and implementations are designed so that such violations rarely occur during normal use.

</details>

***

## Basic List Operations

The first operation we will implement is `add(element)`, which adds an element to the end of the list.
For now, we will ignore the situation where the array becomes full.
If there is still space available in the array, adding an element is simple:
place the element in the next available position
and increase the size by one.
If the array is already full, we simply return immediately without doing anything. We will improve this behavior later.

Because we cannot yet retrieve values from the list, we cannot programmatically verify that insertion worked correctly.
However, we can use a debugger.
Call the `add` method from the main program and place a breakpoint afterward.

```java,ignore
void main() {
    List<String> list = new List<>(10);
    list.add("Aino");
    list.add("Ville");
    list.add("Matti");
    // Place a breakpoint at the closing brace
}
```

Start IntelliJ IDEA's debugger.
Open the *Threads and Variables* view.
You should now be able to inspect the `list` object and its `elements` array.
The added names should appear at the beginning of the array.
By default, IntelliJ IDEA hides the trailing `null` values in arrays. To display them, right-click the array, select "Customize Data View", and disable "Hide Null Elements".

<task>
<task-title>Exercise 5.1: List, Part 1
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/5-1-list-1/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part5/exercise1">Complete this exercise in TIM</a></task-link>
</task>

***

Next, let's implement a method `get(int index)` that returns the element at a specified index, and a method `set(int index, T element)` that replaces the element at a particular index.

```java,ignore
public T get(int index) {
  if (index < 0 || index >= size) {
    throw new IndexOutOfBoundsException("Index " + index + " is not in the range 0.." + (size - 1));
    }
    return elements[index];
}

public void set(int index, T element) {
  if (index < 0 || index >= size) {
    throw new IndexOutOfBoundsException("Index " + index + " is not in the range 0.." + (size - 1));
    }
    elements[index] = element;
}
```

We will discuss exceptions in more detail later, but it is useful to briefly explain what the line
`throw new ...`
means.
List indexes start at zero and continue up to `size - 1`.
If an attempt is made to access an index that is less than zero or greater than or equal to `size`, the index lies outside the bounds of the list.
This is standard behavior in Java collections and should already be familiar from basic programming courses.

***


<!-- ======================================================================= -->
## Dynamic Behavior

Although the `List` interface in Java does not strictly require dynamic resizing, in practice lists are expected to support adding and removing elements dynamically.
This means that the size of a list can change while the program is running.
This is a significant difference compared to arrays, whose size is fixed once they have been created.

Implementing dynamic behavior on top of an array requires a bit of additional logic.
If there is still room in the array—that is, if `size` is smaller than the array length—adding a new element is straightforward: place the element in the next available position and increment the size by one.
If the array is full, the usual approach is to create a new array, typically twice as large, copy all elements from the old array into the new one, and then add the new element to the new array.
This process ensures that the list can continue growing whenever necessary.

<task>
<task-title>Exercise 5.2: Dynamic List, Part 1
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/5-2-dynamic-list-1/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part5/exercise2">Complete this exercise in TIM</a></task-link>
</task>



Removing elements is slightly more complicated, because removing an element leaves an empty slot in the array.
One possibility would be to leave the position as `null`.
However, this would cause problems because the ordering of elements and their indexes would no longer match the logical structure of the list.

Another option would be to copy all elements except the removed one into a new array.
This would be quite inefficient because every removal would require copying the entire array.
A more common approach is to shift all elements after the removed element one position toward the beginning of the array.
This keeps the list compact and avoids allocating a new array.

<task>
<task-title>Exercise 5.3: Dynamic List, Part 2
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/5-3-dynamic-list-2/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part5/exercise3">Complete this exercise in TIM</a></task-link>
</task>

Elements can also be removed based on value.
In that case, the first element equal to the specified value is located and removed.

For the method
`remove(T element)`
to work correctly, equality must be checked using the `equals()` method.
This ensures that comparisons work correctly even when the list contains generic types.
Let's now take a closer look at how `equals()` works.

***

## The `equals` Method

At this point it is useful to take a short detour into the `equals()` and `hashCode()` methods, because understanding them is essential when working with collections.
Let's begin with `equals()`.

In Java, the `==` operator performs reliable value comparison only for primitive types.
For example, it works perfectly well when comparing numbers.
For object references, however, `==` compares the references themselves rather than the contents of the objects.
For example:
`Integer a = new Integer(42);` and
`Integer b = new Integer(42);`
Here, `a == b` evaluates to `false`, even though the contents of both objects are identical.

The purpose of the `equals()` method is to compare the *contents* of objects—that is, whether they should be considered the same from the application's point of view.

All classes inherit an `equals()` method from the `Object` class.
The default implementation in `Object` behaves similarly to `==`, meaning it compares references.
Because of this, meaningful content comparison often requires a custom implementation of `equals()`.

The `equals()` method has a well-defined contract:
* Reflexive: `x.equals(x)` must always be `true`.
* Symmetric: if `x.equals(y)` is `true`, then `y.equals(x)` must also be `true`.
* Transitive: if `x.equals(y)` and `y.equals(z)` are both `true`, then `x.equals(z)` must also be `true`.
* Consistent: repeated calls with unchanged data must return the same result.
* `x.equals(null)` must always be `false`.

A typical implementation follows these steps:

1. If both references point to the same object, return `true`.
2. If the other object is `null` or has a different type, return `false`.
3. Cast the object to the appropriate type and compare the fields that determine object equality.

Consider the following `Student` class.
Two students are considered equal only if both their names and student numbers are equal.

```java
public class Student {
    private String name;
    private String studentNumber;

    public Student(String name, String studentNumber) {
        this.name = name;
        this.studentNumber = studentNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if (other == null || getClass() != other.getClass()) 
            return false;

        // This cast is now safe
        Student otherStudent = (Student) other;
        return this.studentNumber.equals(otherStudent.studentNumber)
            && this.name.equals(otherStudent.name);
    }
}
```

If either `studentNumber` or `name` may be `null`, it is safer to use `Objects.equals(a, b)`, which performs *null-safe* comparisons.

```java
import java.util.Objects;

@Override
public boolean equals(Object other) {

    // ...

  Student otherStudent = (Student) other;
  return Objects.equals(this.studentNumber, otherStudent.studentNumber)
      && Objects.equals(this.name, otherStudent.name);
}
```

***

## The `hashCode` Method

Whenever a class overrides `equals`, it is absolutely essential to override `hashCode` as well.
These two methods work together, and forgetting one of them can lead to subtle and difficult-to-find bugs.
To understand why, we need to briefly examine how hash-based data structures such as `HashSet` and `HashMap` operate.

### How Hashing Works

Imagine a library containing thousands of books.
If you wanted to locate a particular book, you would not walk through every shelf one book at a time.
Instead, you would first go to the correct section of the library (for example 800-899).
In Java, the `hashCode` method serves a similar purpose.

* bucketing: `hashCode` compresses an object's data into a single integer called a *hash value*. The collection uses this value to determine which bucket should contain the object.
* Speed: When searching for an object, Java calculates its hash value and jumps directly to the correct bucket.
* Verification: Java then scans only the objects in that bucket and uses `equals` to determine whether the desired object is actually present.

Java collections rely on the following rule:
If `equals` says two objects are equal, their hash codes must also be equal.

* Required direction:
`x.equals(y) == true`
$\Rightarrow$
`x.hashCode() == y.hashCode()`

* The reverse is not required.
Two different objects may have the same hash code.
This situation is called a *collision*.
Collisions merely mean that multiple objects are stored in the same bucket; `equals()` is still used to distinguish them.

### Implementation

The easiest and safest way to implement `hashCode` is to use Java's helper method `Objects.hash`.
It is extremely important that `hashCode` uses exactly the same fields that are compared in `equals`.
If `equals` compares both `name` and `studentNumber`, then `hashCode` must also use both fields.

```java,ignore
@Override
public int hashCode() {
    // Create a hash value from the same fields used in equals()
    return Objects.hash(name, studentNumber);
}
```

<details><summary><i class="bi bi-stars jyu-gold"></i>Optional information: Don't change keys!</summary>

There is one important danger when working with hash-based structures: modifying objects after they have been inserted.

Suppose an object is added to a `HashSet`.
If one of the fields used by `hashCode()` is later modified, the object's hash code changes.
However, the object remains physically stored in the bucket corresponding to its original hash code.
When Java later tries to find the object using the updated value, it searches in a different bucket and fails to locate it.

A useful rule of thumb is:

> If an object is used as a key in a `HashMap` or as an element in a `HashSet`, try to keep it immutable.

As a side note, Java's modern `record` types automatically generate correct implementations of both `equals()` and `hashCode()`, taking all record fields into account.

</details>

Although you do not need to implement `equals()` or `hashCode()` yourself when building a dynamic list, understanding their purpose is crucial because collections such as `HashSet` and `HashMap` depend heavily on them internally.

***

## Removing an Element by Value

As we learned earlier, removing an element by value requires that the element type provides a meaningful implementation of `equals`.
Once this requirement is satisfied, removal can be implemented reliably by traversing the list and comparing each element to the target using `equals`.

If the list contains multiple equal elements, this implementation removes only the first one found.
Removing all matching elements would require additional traversals.

<task>
<task-title>Exercise 5.4: Dynamic List, Part 3
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/5-4-dynamic-list-3/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part5/exercise4">Complete this exercise in TIM</a></task-link>
</task>