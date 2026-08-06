Create a program that asks the user for two decimal numbers and an arithmetic
operation, and then prints the result as follows:

```text
Number 1 > 12.0
Number 2 > 3.0
Operation (+, -, *, /) > +
12.0 + 3.0 = 15.0
```

At this stage you do not need to handle invalid input. You may assume that the
numbers are always entered as numeric values. The supported operations are
addition (+), subtraction (-), multiplication (*) and division (/). You may
assume that only these operations are used as input.

**Do not use loops or conditional statement.** Instead, implement the arithmetic
operations as lambda expressions and store them in a lookup structure using the
operation symbol as the key.

The program should terminate after displaying the result.

<details><summary>Hint 1</summary>

You may use `BiFunction<Double, Double, Double>`
([See JavaDoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/BiFunction.html))
or `DoubleBinaryOperator`
([JavaDoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/DoubleBinaryOperator.html))
as the type for the lambda expressions.

</details>

<details><summary>Hint 2</summary>

You may use `Map<String, BiFunction<Double, Double, Double>>`
or `Map<String, DoubleBinaryOperator>` as the type of the lookup structure.

You may either choose a specific implementation for the lookup structure or
initialize an immutable lookup structure using the `Map.of` method:

```java,ignore
Map<String, BiFunction<Double, Double, Double>> operations = Map.of(
    "+", ...,
    "-", ...,
    "*", ...,
    "/", ...
);
```

For each `...`, simply provide an appropriate lambda expression.

</details>