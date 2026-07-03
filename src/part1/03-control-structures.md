# Control Structures and Basic Data Structures

> [!important]
> - Conditional statements (`if`, `switch`)
> - Loop statements (`for`, `while`, `do-while`) and list-like data structures
> - Understanding that in Java, strings are compared using the `equals()` method rather than the `==` operator

Programming is rarely just about executing lines of code sequentially. To make programs useful, they must be able to make decisions, repeat actions, and manage data in a sensible way. In this section, we will cover Java's control flow mechanisms, iteration structures, and two ways of storing data: traditional arrays and flexible lists.

## Comparison Operators

Before we can teach a program to make choices ("if this, then that"), we need to understand how a computer sees the world. Computer logic is binary: statements are either true (`true`) or false (`false`).
Comparison operators can be thought of as questions that return a Boolean value. Below are the most common comparison operators in Java.

| Operator | Meaning                  | Example (when x = 5, y = 3) | Result  |
| -------- | ------------------------ | --------------------------- | ------- |
| `==`     | Equal to                 | `x == y`                    | `false` |
| `!=`     | Not equal to             | `x != y`                    | `true`  |
| `>`      | Greater than             | `x > y`                     | `true`  |
| `<`      | Less than                | `x < 4`                     | `false` |
| `>=`     | Greater than or equal to | `x >= 5`                    | `true`  |
| `<=`     | Less than or equal to    | `y <= 3`                    | `true`  |

Often, decisions depend on more than one condition. For example:

> "I will go outside IF it is not raining AND I have free time."

For this purpose, we use logical operators to combine conditions.

* `&&` (**AND**): The expression is true only if both conditions are true.
* `||` (**OR**): The expression is true if at least one of the conditions is true.
* `!` (**NOT**): Reverses a Boolean value (true becomes false, false becomes true).

> [!Warning]
> Do not confuse the assignment operator `=` with the comparison operator `==`.
> * `if (x = 5)` attempts to assign the value `5` to `x` (error)
> * `if (x == 5)` checks whether the value of `x` is `5` (correct)

## Comparing Reference-Type Variables

Unlike primitive types (`int`, `double`, etc.), the `==` operator in Java compares references when used with reference types rather than the contents of the objects themselves. 
For this reason, the `equals()` method should be used when comparing the contents of strings and other reference-type variables.

```java
void main() {
    String string1 = "Slush";
    String string2 = new String("Slush"); // Force creation of a new String object

    // INCORRECT: Compares references -> prints false
    IO.println(string1 == string2);

    // CORRECT: Compares contents -> prints true
    IO.println(string1.equals(string2));
}
```

### Comparing Against `null`

Checking for a `null` reference can, however, be done with the `==` operator, because in this case we specifically want to compare references.

```java
void main() {
    String string = null;
    IO.println(string == null);
}
```

Methods often use a `null` reference to represent the absence of a value. For example, `IO.readln()` may return `null` if input could not be read (see 
[JavaDoc documentation](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/IO.html#readln())). 
This may occur if the user terminates the program unexpectedly. In such situations, it can be sensible to verify that a variable is not `null` before using it.

```java,ignore
void main() {
    String input = IO.readln("Enter input");

    if (input == null) {
        // Do something if no input was provided
        // For example: exit the program
        return;
    }

    // Here we know that input contains at least some string
    IO.println("Text in uppercase: " + input.toUpperCase());
}
```

## Conditional Statements

Conditional statements control the flow of a program. Constructing a conditional statement always requires one or more Boolean expressions.

### The `if` Statement

The basic conditional statement is `if`. The code block inside it is executed only if the Boolean expression inside the parentheses evaluates to `true`.
Often, we also need alternative branches, in which case we use `else if` and `else` structures.

The syntax for the `if` statements is the following:
```java, ignore
if (points >= 90) {
    IO.println("Grade: 5");
} else if (points >= 50) {
    IO.println("Grade: Pass");
} else {
    // Executed if none of the above conditions were met
    IO.println("Grade: Fail");
}
```

### The `switch` Statement

When you want to compare the value of a single variable against several specific values (for example, a menu selection), a `switch` statement may be clearer than a long `if-else` chain.

```java,ignore
int choice = 2;

switch (choice) {
    case 1:
        IO.println("You selected option 1");
        break; // Important: stops execution within this switch block
    case 2:
        IO.println("You selected option 2");
        break;
    default:
        IO.println("Unknown selection");
}
```

In addition to the traditional `switch` statement, modern versions of Java (Java 14+) support a more 
concise arrow syntax using `->` operator.

```java
//-void main() {
int choice = 2;

switch (choice) {
    case 1 -> IO.println("You selected option 1");
    case 2 -> IO.println("You selected option 2");
    case 6,7 -> IO.println("Six or Seven");
    default -> IO.println("Unknown selection");
}
//-}
```



### The Ternary Operator

In simple either-or situations where you want to assign a value to a variable based on a condition, you can use the ternary operator (`?:`).

Syntax: `(condition) ? valueIfTrue : valueIfFalse;`

Example:
```java,ignore
void main() {
    int number1 = 5;
    int number2 = 8;

    // Read as:
    // If number1 is greater than number2,
    // assign number1 to larger, otherwise assign number2
    int larger = (number1 > number2) ? number1 : number2;

    IO.println("The larger number is: " + larger);
}
```

## Loops

Loops are needed when you want to perform actions repeatedly. Java provides four main ways to create loops:
`for`, `for-each`, `while` and `do-while`.

### `for`

Use a `for` loop when you know in advance how many repetitions are needed or when you need an index (position number) during the iteration.
The structure is as follows:

```
for (initialization; condition; update) {
    // loop body
}
```

Below is an example of calculating a sum with a `for` loop.
```java
//-void main() {
int[] numbers = {1, 2, 3, 4};
int sum = 0;

// Traverse the array using indices 0, 1, 2, and 3
for (int i = 0; i < numbers.length; i++) {
    sum += numbers[i];
}

IO.println("The sum is: " + sum);
//-}
```

The initialization, condition, and update expressions can technically be left empty, but the semicolons must remain in place.
An infinite loop can be created by leaving the condition empty, although this is rarely desirable.

### `for-each`

A `for-each` loop is often the most readable and safest way to iterate through an entire data structure. If you do not need the index and do not intend to modify the size of the structure, use a `for-each` loop.

The `for-each` loop has some limitations: You do not know the index of the current element and
you cannot modify the structure by adding or removing elements.

```java
void main() {
    int[] numbers = {1, 2, 3, 4};
    int sum = 0;

    // "For each number in the numbers array..."
    for (int number : numbers) {
        sum += number;
    }

    IO.println(sum);
}
```

### `while`

A `while` loop is a good choice when you do not know beforehand how many times an action must be repeated. The loop continues as long as the condition remains true.
Typical examples include reading a file line by line or running a game loop.

```java
void main() {
    String input = "";

    IO.println("Welcome to the game! (Type 'quit' to exit)");

    // Note the "!" (NOT operator) and .equals() for strings
    // The loop continues as long as
    // input is NOT missing (i.e., not null)
    // AND input is NOT "quit"
    while (input != null && !input.equals("quit")) {
        input = IO.readln("> "); // Stops to wait user input

        IO.println("Echo: " + input);
    }

    IO.println("Game ended.");
}
```

### `do-while`

This loop works like a `while` loop, but with one important difference: the loop body is always executed at least once because the condition is checked only at the end.

`do-while` is the only loop whose closing line ends with a semicolon.

The following pseudocode example repeatedly generates a new location for an apple if it is too close to the player.

```java,ignore
void main() {
    Vector2D playerPosition = new Vector2D(0, 0);
    Vector2D applePosition = new Vector2D(0, 0);

    do {
        // Generate a new position for the apple
        applePosition.x = Math.random() * 10;
        applePosition.y = Math.random() * 10;
        // If the apple is too close to the player, generate a new position
    } while (applePosition.distanceTo(playerPosition) < 2.0);
}
```

### Controlling Loop Execution

When necessary, loop execution can be controlled using the following statements:

* `break`: Terminates the loop and continues execution with the code following the loop.
* `continue`: Ends the current iteration and proceeds directly to the loop's update expression and condition check.

In the example below, the numbers 1, 2, 3, 4, 6, and 7 are printed. The number 5 is skipped, and the loop terminates when it reaches 8.

```java
//- void main() {
for (int i = 1; i <= 10; i++) {
    if (i == 5) {
        continue; // Move to the next iteration
    }

    if (i == 8) {
        break; // Stop the loop
    }

    IO.println(i);
}
//-}
```

## Combining Control Structures

A loop structure can contain other structures, such as conditional statements and additional loops.
When using nested structures, it is important to understand variable scope:
a variable defined inside an inner structure is not visible outside that structure and
a variable defined in an outer structure is visible inside nested structures.

For example, a variable defined inside an `if` statement cannot be used outside the `if` block.

```java,ignore
void main() {
    int number = 10;

    if (number > 5) {
        int number2 = number + 1; // OK: number is defined in an outer scope
        number2 *= 5;
    }

    IO.println(number2); // ERROR: number2 is defined inside the if-block
}
```

```
error: cannot find symbol
    IO.println(number2);
               ^
  symbol:   variable number2
```

In situations like this, one solution is to move the variable declaration into the outer scope.

```java
void main() {
    int number = 10;

    // Define and initialize the variable in the outer scope
    int number2 = 0;

    if (number > 5) {
        // OK: both number and number2 are defined in the outer scope
        number2 = number + 1;
        number2 *= 5;
    }

    // OK: number2 is defined in the same scope as this statement
    IO.println(number2);
}
```

### Nested Loops

Nested loops mean writing one or more loop structures inside another loop.
For each iteration of the outer loop, a certain number of additional iterations are performed by the inner loop.

A common use case for nested loops is processing [multidimensional arrays `T[][]`](02-variables-and-types.md#multidimensional-arrays).

```java
//-void main() {
int[][] table2D = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

for (int rowIndex = 0; rowIndex < table2D.length; rowIndex++) {
    IO.println("Row " + rowIndex + ":");
    for (int columnIndex = 0; columnIndex < table2D[rowIndex].length; columnIndex++) {
        int element = table2D[rowIndex][columnIndex];
        IO.println("  Column " + columnIndex + ": " + element);
    }
}
//-}
```

Remember that `int[][]` means an array whose elements are themselves arrays of integers. Therefore, in the example above:

* `table2D.length` returns the number of arrays contained in `table2D`, that is, the number of rows.
* `table2D[rowIndex]` returns the `int[]` array stored at the specified index, containing all elements of that row.
* `table2D[rowIndex][columnIndex]` returns the element at index `columnIndex` within the row `table2D[rowIndex]`.
