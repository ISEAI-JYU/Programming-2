Create a function `double average(int[] numbers, int minimum, int maximum)`. The function calculates the average of the numbers given in the array according to the following rules:

- If an element is less than or equal to `minimum`, the element is discarded and is not included in the average.
- If an element is greater than or equal to `maximum`, that element and all elements *after it* are discarded.

Example:

```java
IO.println(average(new int[] { -5, 1, -4, 0, 98 }, -7, 99));
IO.println(average(new int[] { 11, 4, 2, 6, 99, 12, 0, -3 }, 3, 99));
IO.println(average(new int[] { 99, 1, 2, 3 }, 0, 99));
```

```text
18.0
7.0
0.0
```

The first call returns `18.0` because the entire dataset falls between the minimum and maximum bounds.
The second call returns `7.0` because only the values `11`, `4`, and `6` are included in the average: the value `2` is smaller than the minimum, and all values starting from `99` are discarded.

If the average cannot be calculated, the function returns the value of the `minimum` parameter.

**Do not use loops.** Implement the function using streams.

<details closed><summary>Hint</summary>

Study the `IntStream` type
([JavaDoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/stream/IntStream.html))
and its methods. You may find at least the following useful:

- `filter()`: removes elements from the stream
- `takeWhile()`: takes elements from the stream as long as the condition is true; as soon as the condition becomes false, stream processing stops there (like a "tap" being turned off)
- `average()`: calculates the average

Note that `average()` returns an `OptionalDouble` object
([JavaDoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/OptionalDouble.html)).
The object contains an `orElse()` method that allows you to return either the calculated value or an alternative default value.

</details>