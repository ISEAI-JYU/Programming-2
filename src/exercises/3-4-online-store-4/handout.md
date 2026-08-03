
In the classes `Product`, `Electronics`, and `Phone`, override the `toString()` method. In each implementation, first call the superclass's `toString()` method and then append information from the class's own attributes to the resulting string.

A main program has been provided on the exercise page, which you can use to test your classes. 

<details><summary>Example of what the program might print </summary>

```text
HighPower Computer: 899.0 €
Original device warranty: 24 months

Aifoun42: 888.0 €
Original device warranty: 37 months
Operating system: AiOS
Connection type: 5G

----------------------------

--- NEW STORE PRODUCT ---
Light Bulb: 67000.0 €
Original device warranty: 73 months
Battery health: 100.00%
Range: 404.00 km

--- ACTIVATION AND CHARGING ---

Charging vehicle Light Bulb...
Charging vehicle Light Bulb...
Charging vehicle Light Bulb...
Charging vehicle Light Bulb...
Charging vehicle Light Bulb...

--- STATUS AFTER CHARGING ---

Light Bulb: 67000.0 €
Original device warranty: 73 months
Battery health: 99.50%
Range: 401.98 km
```

</details>

<br />

Further extend the class hierarchy. Add a class `ElectricVehicle` that inherits from the `Electronics` class.
Add the following to the class:

* Attributes
  * Constant `MAX_RANGE`, which represents the maximum distance in kilometers that the electric vehicle can travel on a single charge.
  * `private double batteryHealth` (as a percentage; between 0 and 100).

* Methods
  * `charge()`, which decreases the battery health by 0.1% each time the vehicle is charged.
  * `toString()`, which first calls the superclass method `toString()`, and then appends the battery health as a percentage and the vehicle's range in kilometers. The range is calculated using the formula:
    (batteryHealth / 100 * MAX_RANGE)