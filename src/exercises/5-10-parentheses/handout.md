Write a function that checks the correctness of the brackets contained in a string.
The function must determine whether all brackets are properly closed in the correct order and whether every opening bracket has a matching closing bracket.

Supported bracket types are:

- Parentheses `( )`
- Square brackets `[ ]`
- Curly braces `{ }`

Logic and Rules:

- **Nesting:** Brackets may be nested (e.g. `([])`), but they must not cross. For example, `([)]` is invalid because the brackets cross each other.

- **Order:** A bracket must always be opened before it is closed.

- **Other characters**, such as letters or numbers, should be ignored.

- An **empty string** is considered valid and contains 0 pairs.

Return Value:

- If the brackets are valid, return the number of bracket pairs found (an integer).
- If the bracket structure is invalid (even a single missing pair or incorrect ordering), return `-1`.

Examples:

| String    | Result   | Explanation                                                    |
|-----------|----------|----------------------------------------------------------------|
| `""`      | `0`      | Empty input is valid, 0 pairs.                                 |
| `"()"`    | `1`      | One valid pair.                                                |
| `"(())"`  | `2`      | Two nested pairs.                                              |
| `"([])"`  | `2`      | Two nested pairs.                                              |
| `"()[]"`  | `2`      | Two adjacent pairs.                                            |
| `"a(b)c"` | `1`      | Letters are ignored, one pair.                                 |
| `"("`     | `-1`     | Missing closing bracket.                                       |
| `"(()"`   | `-1`     | One closing bracket is missing.                                |
| `"()}"`   | `-1`     | Extra closing bracket.                                         |
| `")("`    | `-1`     | Incorrect order (missing opening bracket at the beginning).    |
| `"([)]"`  | `-1`     | Brackets cross (invalid nesting).                              |

The function should be implemented so that **if new bracket types are added in the future**, no changes are required in the core validation logic.
For example, to support the string `a<(b)>c`, it should be sufficient to add support for angle brackets `< >`, while keeping the rest of the logic unchanged (in other words, no additional `if` statements should be needed).