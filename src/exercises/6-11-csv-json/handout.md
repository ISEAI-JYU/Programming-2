Create a program that reads the file 
[`people.csv`](https://raw.githubusercontent.com/ohj-perus-jy/ohj2/refs/heads/main/src/exercises/6-11-csv-json/henkilot.csv)
(format: `name,age,city`) and writes a similar JSON file `people.json` as the one provided in the previous exercise. If a row is invalid (for example, the age is not a number), skip the row and continue processing.

The output file should look like this. It does not matter if the indentation or line breaks are not exactly the same.

```json
[
  {
    "name": "Maija Laine",
    "age": 25,
    "city": "Jyväskylä"
  },
  {
    "name": "Matti Virtanen",
    "age": 30,
    "city": "Tampere"
  },
  ...
]
```