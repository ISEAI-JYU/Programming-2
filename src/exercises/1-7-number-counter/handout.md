Create a program that asks the user for input and prints how many times each digit (0-9) appears in the input.

For example, if the user enters:
`12223`
the program should print:

```
1: 1 pcs
2: 3 pcs
3: 1 pcs
```

Similarly, if the input is:
`10002244412`
the program should print:

```
0: 3 pcs
1: 2 pcs
2: 3 pcs
4: 3 pcs
```

After printing the results, the program asks the user for new input.
If the user enters an empty input, the program terminates.

<details>
<summary>Tip</summary>

A single character can be converted to an integer by combining
[`Character.toString`](https://docs.oracle.com/en/java/javase/25/docs/api//java.base/java/lang/Character.html#toString()) and [`Integer.parseInt`](https://docs.oracle.com/en/java/javase/25/docs/api//java.base/java/lang/Integer.html#parseInt(java.lang.String)):

```java,ignore
int digitAsNumber = Integer.parseInt(Character.toString(a));
```

Note that `Integer.parseInt` assumes that the given string is actually a number. If it contains anything other than numeric characters, the function throws an exception.
You can check whether a single character is a digit by using the `Character.isDigit` method.

</details>