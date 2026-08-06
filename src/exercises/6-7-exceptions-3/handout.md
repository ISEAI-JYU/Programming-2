Create a program that checks whether the age entered by the user is sufficient for a certain activity, such as obtaining a driver's license.

Create a method `isOldEnough` that takes an age (`int`) as a parameter and returns `true` if the age is sufficient. If the age is below 18, throw an `AgeException`, which is your own checked exception class. Provide an appropriate exception message, for example
"Age is not sufficient."
As a hint, the method declaration can be:

```java,ignore
static boolean isOldEnough(int age) throws AgeException
```

If the age is negative, throw an `AgeException` with the message:
"Age cannot be negative."

Remove the `if` statement and modify the `main` method so that it compiles and prints the correct output.