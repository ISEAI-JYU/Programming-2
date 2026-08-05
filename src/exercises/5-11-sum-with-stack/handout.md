Calculate the sum `1 + 2 + ... + n` **without recursion** using your own stack.

The starting point is the following recursive definition:

```java,ignore
int sum(int n) {
    if (n == 0) return 0;
    return n + sum(n - 1);
}
```

Write a method `sumIteratively(int n)` that returns the same result.
Model the recursion using a stack: store on the stack the numbers that are "waiting" during the return phase. Use the `ArrayDeque` implementation for the stack.
You do not yet need a structure such as a `Frame` object for this exercise.