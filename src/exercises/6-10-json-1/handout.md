- Create a new Maven project that uses the Jackson library for processing JSON files.
- Add the required dependency to your `pom.xml` file.
- Download [`people.json`](https://raw.githubusercontent.com/ohj-perus-jy/ohj2/refs/heads/main/src/exercises/6-10-json-1/henkilot.json) and save it in the same folder as your code.
- Create a `Person` class or an equivalent record with the fields `String name`, `int age`, and `String city`.
- Read the file `people.json` and convert it into a list of `Person` objects.
- Filter the list so that only people aged 18 or older are included.
- Print their name, age, and city.

Remember to save `people.json` in the same folder as your code. Then check your run configuration and make sure that the working directory is the same as the folder containing your code so that the file can be found.

You can access the contents of `people.json` here if necessary:

<details>
<summary>people.json</summary>

```json
[
  { "name": "Maija Laine", "age": 25, "city": "Jyväskylä" },
  { "name": "Matti Virtanen", "age": 30, "city": "Tampere" },
  { "name": "Liisa Niemi", "age": 17, "city": "Helsinki" },
  { "name": "Pekka Korhonen", "age": 41, "city": "Oulu" },
  { "name": "Aino Salmi", "age": 22, "city": "Turku" },
  { "name": "Jari Heikkinen", "age": 19, "city": "Kuopio" },
  { "name": "Sari Lehto", "age": 16, "city": "Lahti" },
  { "name": "Oskari Mäkinen", "age": 28, "city": "Espoo" },
  { "name": "Emilia Ranta", "age": 33, "city": "Vantaa" },
  { "name": "Teemu Koski", "age": 45, "city": "Pori" },
  { "name": "Noora Aalto", "age": 18, "city": "Joensuu" },
  { "name": "Kalle Hämäläinen", "age": 52, "city": "Rovaniemi" }
]
```

</details>