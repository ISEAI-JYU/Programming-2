Extend the [`CollectibleCard` class](https://humppabyte.github.io/Programming-2/part6/01-functional-interfaces-and-lambda-expressions.html#the-comparator-interface)
by adding an attribute `String rarity`. The rarity of a collectible card can be one of the following values (from least rare to most rare):
`C`, `U`, `R`, `RR`, `RRR`, `SR`, `AR`, `SAR`, `UR`.

Write a comparator that sorts the cards in a list according to their rarity. You can use the following card collection to test your code:

<details closed><summary>Example list of collectible cards</summary>

```java,ignore
List<CollectibleCard> cards = new ArrayList<>(List.of(
    new CollectibleCard("Lost Semicolon", "Code Jungle", 101, "C"),
    new CollectibleCard("Infinite Loop", "Code Jungle", 102, "U"),
    new CollectibleCard("Bug Hunter", "Code Jungle", 103, "R"),
    new CollectibleCard("Spaghetti Code Monster", "Code Jungle", 104, "RR"),
    new CollectibleCard("Overclocked Processor", "Code Jungle", 105, "SR"),
    new CollectibleCard("Holy Stack Overflow", "Code Jungle", 106, "RRR"),
    new CollectibleCard("Null Pointer Ninja", "Code Jungle", 107, "U"),
    new CollectibleCard("Blue Screen of Death", "Code Jungle", 108, "AR"),

    new CollectibleCard("Student Card", "Campus Saga", 201, "C"),
    new CollectibleCard("Sleepy Lecturer", "Campus Saga", 202, "C"),
    new CollectibleCard("Overall Party", "Campus Saga", 203, "U"),
    new CollectibleCard("Overachiever", "Campus Saga", 204, "R"),
    new CollectibleCard("Free Bucket", "Campus Saga", 205, "SAR"),
    new CollectibleCard("Late Submission", "Campus Saga", 206, "RR"),
    new CollectibleCard("Academic Quarter", "Campus Saga", 207, "SR"),
    new CollectibleCard("Thesis Anxiety", "Campus Saga", 208, "AR"),
    new CollectibleCard("Semma Pancake", "Campus Saga", 209, "UR"),

    new CollectibleCard("Angry Moose", "Finnish Myths", 301, "C"),
    new CollectibleCard("Eternal November", "Finnish Myths", 302, "RR"),
    new CollectibleCard("Sauna Gollum", "Finnish Myths", 303, "SR"),
    new CollectibleCard("Salmiakki Rain", "Finnish Myths", 304, "U"),
    new CollectibleCard("Väinämöinen's Kantele", "Finnish Myths", 305, "SAR"),
    new CollectibleCard("Sisu", "Finnish Myths", 306, "RRR"),
    new CollectibleCard("Laser Sauna Ladle", "Finnish Myths", 307, "UR"),
    new CollectibleCard("Market Square Police Officer", "Finnish Myths", 308, "R")
));
```

</details>

Write a `main()` program that sorts and prints the collectible cards by rarity (most common cards first, rarest cards last).
Cards whose rarity is `null` or some value other than those listed above must be placed at the beginning of the list.