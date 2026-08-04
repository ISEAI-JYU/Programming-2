Create a class `Container` that uses generics and acts as a simple container for a single object of any type.

Add an attribute `content`, which can store an object of any type. Also add a string attribute `owner`. Create a constructor that receives these values as parameters.

Also add the getter methods `getOwner`, `getContent`, and `getType`, where the last one returns the type of the container's content as a string. Override the `toString` method so that it returns all of this information in a single string.

A main program is provided for testing the functionality of the class.

<details><summary>Hint</summary>

You can obtain the type of an object as a string using the method 
`object.getClass().getSimpleName()`.

</details>