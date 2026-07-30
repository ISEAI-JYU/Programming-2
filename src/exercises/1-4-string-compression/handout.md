Create a method called `compressString` that takes a `String` as a parameter
and returns a new compressed `String` in such a way that consecutive identical
characters are represented by the character followed by its count.

For example:

- `compressString("aaaabbbccd")` would return `"a4b3c2d1"`,
- `compressString("00666663332222222")` would return `"02653327"`,
- `compressString("Niiiiiin")` would return `"N1i6n1"`,
- `compressString("nnNNnnnN")` would return `"n2N2n3N1"`,
- `compressString("ohjelmointi")` would return `"o1h1j1e1l1m1o1i1n1t1i1"`.

Also write an appropriate documentation comment for the method and add
at least one usage example to the `main()` method.

<details>
<summary>Tip 1</summary>

Use a [`StringBuilder`](https://humppabyte.github.io/Programming-2/part1/02-variables-and-types.html#stringbuilder) and its methods to construct the resulting string.

</details>

<details>
<summary>Tip 2 </summary>

Try solving the problem manually on paper first for a short string
(e.g., `"aabb"`). What information needs to be tracked (variables)?

</details>

<details>
<summary>Tip 3</summary>

It may be easier to start from the second character and always compare
it to the previous one:

```bob
  0   1   2   3 = string.length
+---+---+---+---+
| a | a | b | b |
+---+---+---+---+
  ^   ^
  |   |
i - 1 |
      i = 1
```

</details>