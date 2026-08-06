Download the dataset: [words.txt](https://raw.githubusercontent.com/ohj-perus-jy/ohj2/refs/heads/main/src/exercises/6-9-sanat/sanat.txt)

Save the file in your project's working directory as `words.txt`.

The file contains one word per line. It intentionally contains empty lines,
leading/trailing whitespace, duplicate words, and different letter cases
(e.g. Java, java, JAVA).

Example from the beginning of the dataset:

```text
  Java
python

JAVA
CSharp
  java
```

Create a program that:

- Reads all lines.
- Removes extra whitespace from the words and converts them to lowercase.
  Hint: `String.trim()` and `String.toLowerCase()`.
- Removes empty lines.
- Removes duplicates. (Hint: the `distinct()` method in the Stream API, or a
  `Set` collection.)
- Sorts the words alphabetically. (Hint: the `sorted()` method in the Stream API,
  or the `Collections.sort()` method on a `List`.)
- Writes a new file `output/words-clean.txt` containing the cleaned word list
  (one word per line).
- Also writes a file `output/report.txt` containing:
  - the number of original lines
  - the number of unique words after removing empty lines and cleaning the words
  - the longest word (if there are several, any one of them is acceptable)

Hint: If you solve the task using the Stream API, you can use the
`Stream.max(Comparator)` method to find the longest word.

Hint: First create `List<String> cleaned = ...`, and then use
`Files.write(...)` to write the results to the two output files.

`report.txt` should look like this:

```text
Original lines: 1074
Cleaned words: 59
Longest word: binarytree
```