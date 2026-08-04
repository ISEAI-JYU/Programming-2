Add two methods to the `LargeContainer` class from the previous exercise.

1. The class method (*static*) `sumNumbers` takes a `LargeContainer` object as a parameter. The container contains numbers, meaning objects of type `Number` **or any of its subclasses**. The method returns the sum of all numbers in the container.

2. The instance method `transferAll` takes another `LargeContainer` object as a parameter and transfers the contents of the container on which the method is called into the other container. The other container must be of a type that can contain objects of this container's type.

A main program is provided for testing the functionality of the class.

<details><summary>Hint</summary>

You will need type bounds in this exercise.

1. All objects of type `Number` have a `doubleValue()` method, which returns their value as a `double`.

2. Note that the container types do not have to be exactly the same. A `LargeContainer<Number>` can contain `Integer` objects because `Integer` is a subclass of `Number`.

</details>