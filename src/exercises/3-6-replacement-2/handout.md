Add an attribute `int mileage` to the `Car` class. Add an attribute `int flightHours` to the `Airplane` class. Add a new constructor to both classes that initializes these attributes. Modify the existing constructor so that these attributes are assigned the value `0`.

Modify the `move()` methods so that they increase these values. The `move()` method of the `Car` class should increase the `mileage` attribute by `10`, and the `move()` method of the `Airplane` class should increase the `flightHours` attribute by `1`.

Also override the `toString()` method in the `Vehicle` class so that it returns the text:
"Vehicle \<brand\> information: "
Then override this method in the `Car` and `Airplane` classes so that they **also** include the mileage or flight hours, respectively.