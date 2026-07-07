# The `Comparable` Interface and Natural Ordering

The [`Comparable`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Comparable.html) interface defines the method `compareTo()`, which allows objects of a class to define a *natural ordering* relative to other objects of the same type.

The interface contains a single method, `compareTo()`, which returns an integer indicating the ordering relationship between two objects.
The return value is interpreted as follows:

| Case                              | Meaning              | Interpretation                      |
| --------------------------------- | -------------------- | ----------------------------------- |
| `objectA.compareTo(objectB) < 0`  | `objectA < objectB`  | `objectA` is smaller than `objectB` |
| `objectA.compareTo(objectB) == 0` | `objectA == objectB` | `objectA` is equal to `objectB`     |
| `objectA.compareTo(objectB) > 0`  | `objectA > objectB`  | `objectA` is greater than `objectB` |

For example, the `Integer` type implements the `Comparable` interface for integer objects. Therefore, two integer objects can be compared using the `compareTo()` method.

```java
void main() {
    Integer number1 = 5;
    Integer number2 = 18;

    int result = number1.compareTo(number2);

    // Prints a negative value (< 0), because 5 < 18
    IO.println( "number1.compareTo(number2): " + result);
}
```

Strings already have a natural ordering defined as alphabetical order.

```java
// Helper function that prints the ordering between two strings.
void describeOrder(String word1, String word2) {
    int result = word1.compareTo(word2);
    if (result < 0) {
        IO.println( "The string '" + word1 + "' comes before the string '" + word2 + "'");
    } else if (result > 0) {
        IO.println("The string '" + word1 + "' comes after the string '" + word2 + "'");
    }
    else {
        IO.println("'" + word1 + "' is equal to '" + word2 + "'");
    }
}

void main() {
    String word1 = "apple";
    String word2 = "orange";
    String word3 = "banana";

    describeOrder(word1, word2);
    describeOrder(word1, word3);
    describeOrder(word2, word3);
}
```

Similarly, the natural ordering for `Integer` values is ascending numerical order.

```java
//-int describeOrder(Integer number1, Integer number2) {
//-    int result = number1.compareTo(number2);
//-    if (result < 0) {
//-        IO.println( number1 + " is smaller than " + number2);
//-    } else if (result > 0) {
//-        IO.println(number1 + " is greater than " + number2);
//-    } else {
//-        IO.println(number1 + " is equal to " + number2);
//-    }
//-
//-    return result;
//-}
//-

void main() {
    Integer number1 = 5;
    Integer number2 = 18;
    Integer number3 = 5;
    describeOrder(number1, number2);
    describeOrder(number1, number3);
    describeOrder(number2, number3);
}
```

One of the major benefits of the `Comparable` interface is that it allows us to write programs and use algorithms that require comparisons in a completely general way for any objects that implement `Comparable`.
Classes implementing this interface can also be sorted using Java's built-in collection sorting implementations, such as the [`Collections.sort`](<https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Collections.html#sort(java.util.List)>) method.
As a result, we do not need to implement sorting algorithms ourselves.

```java
void main() {
    List<Integer> numbers = Arrays.asList(18, 5, 42);
    List<String> fruits = Arrays.asList("apple", "pear", "orange");
    IO.println(numbers); // [18, 5, 42]
    IO.println(fruits);  // [apple, pear, orange]

    // Sort lists according to the natural order of their elements.

    Collections.sort(fruits);
    Collections.sort(numbers);

    IO.println(numbers); // [5, 18, 42]
    IO.println(fruits);  // [apple, orange, pear]
}
```

<details closed><summary>Extra: <code>Collections</code> class</summary>

In addition to the `sort` method mentioned above, the [`Collections`](https://docs.oracle.com/en/java/javase/25/docs/api//java.base/java/util/Collections.html) class provides many useful utility methods for Java collections.
A *collection* is Java's general term for data structures that contain elements, such as arrays, lists, and maps.

We will cover collections in more detail in [Part 5](../part5/index.md).
However, you may already explore the `Collections` class if you wish. Be aware that its documentation contains syntax that will not be introduced until later in the course.

Many methods in the `Collections` class rely on collection elements implementing interfaces such as `Comparable`.
By combining Java's collection interfaces and element interfaces, it becomes possible to write highly general algorithms that work regardless of whether the parameter is a list of numbers or, for example, an array of students.

</details>

***

## Implementing the `Comparable` Interface Yourself

Let's try implementing the `Comparable` interface for one of our own classes.

Suppose we have a class called `CollectibleCard`, representing cards used in a collectible card game.
Initially, our collectible card contains only a name and a unique identification number starting from one.

```java
// FILE: CollectibleCard.java
class CollectibleCard {
    private String name;
    private int idNumber;

    public CollectibleCard(String name, int idNumber) {
        this.name = name;
        this.idNumber = idNumber;
    }

    @Override
    public String toString() {
        return "Card: " + name + " (#" + idNumber + ")";
    }
}
// FILE_END
// FILE: main.java
void main() {
    List<CollectibleCard> cards = List.of(
        new CollectibleCard("Brilliant Dragon", 3),
        new CollectibleCard("Beginner Amoeba", 1),
        new CollectibleCard("Magnificent Seahorse", 2)
    );

    for (CollectibleCard card : cards) {
        IO.println(card);
    }
}
// FILE_END
```

If we now try to sort `CollectibleCard` objects using `Collections.sort()`, we receive a compile-time error because the class does not implement the `Comparable` interface.

```java
// FILE: main.java
void main() {
    List<CollectibleCard> cards =
        Arrays.asList(
            new CollectibleCard("Brilliant Dragon", 3),
            new CollectibleCard("Beginner Amoeba", 1),
            new CollectibleCard("Magnificent Seahorse", 2)
        );

    IO.println("Before sorting:");
    for (CollectibleCard card : cards) {
        IO.println(card);
    }

    Collections.sort(cards);

    IO.println();
    IO.println("After sorting:");
    for (CollectibleCard card : cards) {
        IO.println(card);
    }
}
// FILE_END
// FILE: CollectibleCard.java
class CollectibleCard {

    private String name;
    private int idNumber;

    public CollectibleCard(
            String name,
            int idNumber) {
        this.name = name;
        this.idNumber = idNumber;
    }

    @Override
    public String toString() {
        return "Card: " + name + " (#" + idNumber + ")";
    }
}
// FILE_END
```

```text
main.java:14: error: no suitable method found for sort(List<CollectibleCard>)
    Collections.sort(cards);
```

The error message is rather cryptic.
In simple terms, the error occurs because `Collections.sort()` cannot guess what the natural ordering of `CollectibleCard` objects should be.
Should cards be ordered alphabetically by name? Or by increasing identifier number?
To answer that question, we must implement the `Comparable` interface for the `CollectibleCard` class.

When implementing the `Comparable` interface for our collectible cards, we immediately have to consider what the natural ordering of our cards should be.
For example, alphabetical ordering by name might be useful. On the other hand, since the cards have unique numerical identifiers starting from one, ordering by identifier might feel more natural and just as useful.
When choosing a natural ordering, it is important to consider both the application domain and the needs of the users. What ordering would other programmers using the class—or the application's end users—expect as the default ordering for collectible cards?

For the purposes of this example, let's decide that ordering by the unique identifier is the most reasonable natural ordering.
With that decision made, we can implement the `Comparable` interface so that cards are ordered according to their identifier number.
To do this, we need to add the interface implementation to the class definition and implement the `compareTo` method.

We will use the return-value table presented earlier in the [chapter](#the-comparable-interface-and-natural-ordering).

```java
// FILE: CollectibleCard.java
class CollectibleCard implements Comparable<CollectibleCard> {
    private String name;
    private int idNumber;

    public CollectibleCard(String name, int idNumber) {
        this.name = name;
        this.idNumber = idNumber;
    }

    @Override
    public int compareTo(CollectibleCard other) {
        if (idNumber > other.idNumber) {
            return 1;
        }
        if (idNumber < other.idNumber) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Card: " + name + " (#" + idNumber + ")";
    }
}
// FILE_END
// FILE: main.java
void main() {
    List<CollectibleCard> cards =
        Arrays.asList(new CollectibleCard( "Brilliant Dragon", 3),
            new CollectibleCard("Beginner Amoeba", 1),
            new CollectibleCard("Magnificent Seahorse", 2)
        );

    IO.println("Before sorting:");
    for (CollectibleCard card : cards) {
        IO.println(card);
    }

    Collections.sort(cards);

    IO.println();
    IO.println("After sorting:");
    for (CollectibleCard card : cards) {
        IO.println(card);
    }
}
// FILE_END
```

`Comparable` is a so-called *generic interface*, meaning that the interface itself does not specify what type of objects are being compared.
We will cover generic programming in more detail in [Part 4.4](./04-type-parameters-and-generics.md).
Because of this, when implementing the `Comparable` interface, we must explicitly specify the type for which the natural ordering is being defined.
In this case, we are defining an ordering for collectible cards, which is why we write:
`implements Comparable<CollectibleCard>`.

***

## Using Existing Comparison Methods

In the previous example, we implemented `compareTo()` directly according to the definition of the `Comparable` interface.
However, Java's built-in types often already provide comparison methods that can be used when implementing `Comparable`.

For example, Java provides the method [`Integer.compare()`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Integer.html#compare(int,int)) for comparing `int` values.
This allows us to simplify the implementation of `compareTo()` in `CollectibleCard` into a single-line method.

```java
class CollectibleCard implements Comparable<CollectibleCard> {
    private String name;
    private int idNumber;

    public CollectibleCard(String name, int idNumber) {
        this.name = name;
        this.idNumber = idNumber;
    }

    @Override
    public int compareTo(CollectibleCard other) {
        return Integer.compare(idNumber, other.idNumber);
    }

    @Override
    public String toString() {
        return "Card: " + name + " (#" + idNumber + ")";
    }
}

//-
//-void main() {
//-    List<CollectibleCard> cards = Arrays.asList(
//-        new CollectibleCard("Brilliant Dragon", 3),
//-        new CollectibleCard("Beginner Amoeba", 1),
//-        new CollectibleCard("Magnificent Seahorse", 2)
//-    );
//-
//-    IO.println("Before sorting:");
//-    IO.println(cards);
//-
//-    Collections.sort(cards);
//-
//-    IO.println("After sorting:");
//-    IO.println(cards);
//-}
```

When implementing the `Comparable` interface for your own classes, it is generally a good idea to use existing comparison methods whenever possible.
For example, `Integer.compare()` correctly handles all special cases related to integers.
Similarly, `Double.compare()` correctly handles all floating-point special values, such as infinity and *Not a Number* (`NaN`).

***

## Comparing Multiple Attributes

A natural ordering may also be based on multiple attributes.
For example, we might decide that cards should be ordered alphabetically by name first, and by identifier number only when the names are the same.
To achieve this, we modify `compareTo()` so that it first compares the `name` attributes using the `String` class's own `compareTo()` method.
If the names are equal (that is, if `compareTo()` returns `0`), then we compare the identifier numbers.

```java
// FILE: CollectibleCard.java
class CollectibleCard implements Comparable<CollectibleCard> {
    private String name;
    private int idNumber;

    public CollectibleCard(String name, int idNumber) {
        this.name = name;
        this.idNumber = idNumber;
    }

    @Override
    public int compareTo(CollectibleCard other) {
        int nameComparison = this.name.compareTo(other.name);
        if (nameComparison != 0) {
            return nameComparison;
        }

        return Integer.compare(this.idNumber, other.idNumber);
    }

    @Override
    public String toString() {
        return "Card: " + name + " (#" + idNumber + ")";
    }
}
// FILE_END
// FILE: main.java
void main() {
    List<CollectibleCard> cards = Arrays.asList( 
            new CollectibleCard("Brilliant Dragon", 3),
            new CollectibleCard("Beginner Amoeba", 1),
            new CollectibleCard("Magnificent Seahorse", 2)
        );

    IO.println("Before sorting:");
    for (CollectibleCard card : cards) {
        IO.println(card);
    }

    Collections.sort(cards);

    IO.println();
    IO.println("After sorting:");

    for (CollectibleCard card : cards) {
        IO.println(card);
    }
}
// FILE_END
```

***