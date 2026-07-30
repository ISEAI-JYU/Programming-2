Create a function called `isPasswordStrong` that checks the strength of a password (`String`) passed as a parameter.
The password must be at least 8 characters long, contain one digit, and include at least one uppercase and one lowercase letter.

The function returns `true` if the password provided as a string meets the above requirements. Otherwise, it returns `false`.

Also write an appropriate documentation comment for the method and add at least one usage example to the `main()` method.

<details>
<summary>Hint</summary>

Take a look at the `Character` class method [`isDigit`](https://docs.oracle.com/en/java/javase/25/docs//api/java.base/java/lang/Character.html#isDigit(char)).

For example, by calling `Character.isDigit(character)`, the return value is `true` if the character is a digit and `false` if it is not.

The methods `isUpperCase` and `isLowerCase`, which can also be found in the same class, work according to the same principle.

</details>