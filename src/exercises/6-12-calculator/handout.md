Create a simple calculator program that repeatedly asks the user for two numbers and an arithmetic operation, and then prints the result.

The program should work approximately as follows:

```text
Enter an expression in the format <number> <operator> <number>.
Type "exit" to close the program.

> 1 + 1
2.0
> 10 - 1
9.0
> 0.5 * 100
50.0
> 10 / 2
5.0
> 10
Enter an expression in the format <number> <operator> <number>.
> cat
Enter an expression in the format <number> <operator> <number>.
> exit
Program closing.
```

The program must handle invalid user input so that it does not crash due to incorrect input.

Implement the basic arithmetic operations: addition (`+`), subtraction (`-`), multiplication (`*`), and division (`/`).
In addition, invent and implement at least two additional operations of your own choice.

**Do not use conditional statements to select the arithmetic operation itself.** However, you may use conditional statements and `try/catch` blocks to validate user input.

<details closed><summary>Hint 1</summary>

You may implement the operations as lambda expressions. Use either `BiFunction<Double, Double, Double>`
([JavaDoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/BiFunction.html))
or `DoubleBinaryOperator`
([JavaDoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/DoubleBinaryOperator.html))
as the type for the lambda expressions.

</details>

<details closed><summary>Hint 2</summary>

You may use the `Scanner` class to read the user's input:

```java,ignore
Scanner reader = new Scanner(userInput);
double number1 = reader.nextDouble();
String operation = reader.next();
double number2 = reader.nextDouble();
```

You may need to add the necessary exception handling.

</details>