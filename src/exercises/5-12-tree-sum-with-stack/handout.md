Implement the calculation of the sum of the nodes in a binary tree containing integers **without recursion** using your own stack.
Use the following `Node` class:

```java,ignore
public class Node {
    int value;
    Node left;
    Node right;

    Node(int value) {
        this.value = value;
    }
}
```

The starting point is the following recursive definition:

```java,ignore
int sum(Node root) {
    if (root == null) return 0;
    return root.value + sum(root.left) + sum(root.right);
}
```

Model the recursion using a stack: each item on the stack represents the state of a recursive call.
For this purpose, you will need a `Frame` class (for example, containing `Node` and `visited`) to maintain the state information. The stack itself does not need to be implemented manually; you may use the `ArrayDeque` implementation as in the course material.

An example main program is included in the TIM exercise.