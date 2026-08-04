1. Create an interface named `Converter`.
  Define a single method in the interface:
  `String convert(String input);`
  Remember that methods in an interface do not have a body (no curly braces `{}`).

2. Create the classes `Lowercase`, `Uppercase`, and `Capitalize`, which implement the `Converter` interface.
    - The `convert` method of the `Lowercase` class converts the given string to lowercase.
      `convert("Hello World")` -> `"hello world"`.
    - The `convert` method of the `Uppercase` class converts the given string to uppercase.
      `convert("Hello World"` -> `"HELLO WORLD"`.
    - The `convert` method of the `Capitalize` class converts the given string so that only the first letter is uppercase and all other letters are lowercase.
    `convert("HELLO WORLD")` -> `"Hello world"`

3. Test your program using the provided main program.