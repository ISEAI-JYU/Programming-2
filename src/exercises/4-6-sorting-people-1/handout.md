The exercise is based on a `Person` class in its own file and a `sortPeople` method in `main.java`. However, the method does not work because it uses Java's built-in `Collections.sort` method, and the `Person` class does not provide the required support for it.

Modify the `Person` class so that lists of type `List<Person>` can be sorted alphabetically by a person's name using the `Collections.sort` method.

For example, the list

```java,noplayground
List<Person> people = Arrays.asList(
    new Person("Joukahainen"),
    new Person("Ilmatar"),
    new Person("Kyllikki"),
    new Person("Kokko")
);
```

should be in the following order after calling
`Collections.sort(people);`:

1. Ilmatar
2. Joukahainen
3. Kokko
4. Kyllikki