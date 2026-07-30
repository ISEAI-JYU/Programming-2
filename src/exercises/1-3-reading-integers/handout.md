Create a program that reads non-negative integers from the user, one line at a time, in a loop using the `IO.readln()` method, until the user enters an empty string.
Store these numbers in a list.

Then write the methods `sum`, `average`, `smallest`, and `largest`, which calculate the smallest number in the list, the largest number in the list, the sum of the numbers, and the average of the numbers. Print the values returned by these methods to the user.
Add appropriate handling for empty lists to the methods and document them.

You may assume that the user enters only non-negative integers as input. If a method receives an empty list as input, it must return the value `-1`.

The use of built-in methods from the `Collections` class, such as `Collections.min()` and `Collections.max()`, is prohibited.

<details><summary>Hint</summary>

You can convert a string to an integer using the
[`Integer.parseInt(number)`](https://docs.oracle.com/en/java/javase/25/docs//api/java.base/java/lang/Integer.html#parseInt(java.lang.String)) method.

</details>