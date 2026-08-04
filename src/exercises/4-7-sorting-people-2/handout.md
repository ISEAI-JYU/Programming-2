Continuation of the previous exercise. The `Person` class has now been updated so that a person's name is divided into a last name and first name(s).

Modify the updated `Person` class so that `List<Person>` collections can be sorted using the `Collections.sort` method in alphabetical order by last name and first name(s), with the primary sorting criterion being the last name.

For example, the list

```java
List<Person> people = Arrays.asList(
        new Person("Pacius", "Fredrik"),
        new Person("Mozart", "Wolfgang Amadeus"),
        new Person("Mozart", "Leopold"),
        new Person("Chopin", "Frédéric")
);
```

should be in the following order after calling
`Collections.sort(people);`:

1. Chopin Frédéric
2. Mozart Leopold
3. Mozart Wolfgang Amadeus
4. Pacius Fredrik