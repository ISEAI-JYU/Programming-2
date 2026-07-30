Create a function
`int missingNumber(int[] array)`
The function is given an array as a parameter that contains the numbers
1 through N in random order, but one of the numbers is missing.
The function must return the missing number.
The function must not cause any side effects.

Examples:

- `missingNumber(new int[] { 1, 6, 3, 4, 5 })` returns 2
- `missingNumber(new int[] { 8, 2, 4, 1, 3, 5, 6 })` returns 7
- `missingNumber(new int[] { })` returns 1
- `missingNumber(new int[] { 2 })` returns 1

You can use the helper function below to generate a random array:

```java,ignore
int[] generateInput(int maxNumber) {
    Random r = new Random();
    List<Integer> numbers = new ArrayList<>(IntStream.range(1, r.nextInt(2, maxNumber + 1)).boxed().toList());
    Collections.shuffle(numbers);
    numbers.remove(r.nextInt(numbers.size()));
    return numbers.stream().mapToInt(Integer::intValue).toArray();
}
```

You can use the method as follows:
 
```java,ignore
// Creates an random array of size 1-10, but with one number missing
int[] input = generateInput(10);

// Determine which number is missing
int missing = missingNumber(input);
```