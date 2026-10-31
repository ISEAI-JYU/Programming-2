Continue with the `Vehicle`, `Car` and `Airplane` classes from the previous exercise (copy them to this exercise).

Add an attribute `int mileage` to the `Car` class and an attribute `int flightHours` to the `Airplane` class. Add a new constructor to both classes that initializes these attributes: `Car(String brand, int mileage)` and `Airplane(String brand, int flightHours)`. Modify the existing one-parameter constructors so that these attributes are assigned the value `0`.

Modify the `move()` methods so that they increase these values (they still print the same texts as before). The `move()` method of the `Car` class should increase the `mileage` attribute by `10`, and the `move()` method of the `Airplane` class should increase the `flightHours` attribute by `1`.

Also override the `toString()` method in the `Vehicle` class so that it returns the text:
"Vehicle \<brand\> information: "
Then override this method in the `Car` and `Airplane` classes so that they first call the superclass's `toString()` and then **also** include the mileage or flight hours, respectively, in the form `Mileage: <mileage>` or `Flight hours: <flightHours>`.

A main program has been provided on the exercise page. With the classes implemented as described, it prints:

```text
Car LolWagen drives down the highway with its tires squealing.
Car Skouda drives down the highway with its tires squealing.
Airplane Hairbus takes off from the runway and flies above the clouds.
Airplane Beiong takes off from the runway and flies above the clouds.
Vehicle LolWagen information: Mileage: 125010
Vehicle Skouda information: Mileage: 10
Vehicle Hairbus information: Flight hours: 4671
Vehicle Beiong information: Flight hours: 1
```