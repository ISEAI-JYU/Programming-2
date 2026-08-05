Implement your own simple hash table.

Use an array as the main data structure of the hash table. To handle collisions, you may use a list, for example, so that elements that end up at the same index are stored in a list located at that index. Elements must not be lost when collisions occur.

The hash table capacity may have a default value of 10, or it may be provided as a constructor parameter. The capacity does not need to change at any point during program execution, so you do not need to consider or implement load factor handling.

Java's `hashCode` method can return a negative value, so it is a good idea to use the absolute value to avoid negative indices.

Add a `get` method that retrieves an element from the hash table based on its key.
Also add `add` and `remove` methods for inserting and removing elements.