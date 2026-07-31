Modify the example `Person` class so that every created person is *automatically* assigned a unique identification number, which is an integer.
The first person created should receive the number 1, the next person should receive the number 2, and so on.

The number must not be assigned from outside the object, for example by calling one of its methods.

Also implement a method called `getNumber` that returns the identification number of a particular object as an integer. No other methods need to be added.

<details><summary>Tip</summary>

You will need static class members in this exercise.

Before you begin, consider the following questions:

- When does an object receive its number?
- How does an object know what its number should be?
- What information is shared among all objects, and what information is specific to each object?

</details>