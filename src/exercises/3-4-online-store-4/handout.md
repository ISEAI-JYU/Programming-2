In the classes `Product`, `Electronics`, and `Phone`, override the `toString()` method. In each implementation, first call the superclass's `toString()` method and then append information from the class's own attributes to the resulting string, **each piece of information on its own line**:

* `Product`: the same text that `printDetails()` prints, e.g. `HighPower Computer: 899.0 €`
* `Electronics`: adds `Original device warranty: 24 months`
* `Phone`: adds `Operating system: AiOS` and `Connection type: 5G` (or `4G` for a phone without 5G)

A main program has been provided on the exercise page, which you can use to test your classes. With the classes implemented as described, it prints the following.

<details><summary>Output of the main program</summary>

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
  * Constant `MAX_RANGE` (an `int`, set in the constructor and never changed afterwards), which represents the maximum distance in kilometers that the electric vehicle can travel on a single charge.
  * `private double batteryHealth` (as a percentage; between 0 and 100). A new vehicle has a battery health of 100.

* Constructor
  * `ElectricVehicle(String name, double price, int warrantyMonths, int maxRange)`, which passes the first three values to the superclass constructor and sets `MAX_RANGE`.

* Methods
  * `void charge()`, which prints `Charging vehicle NAME...` (NAME is the product's name) and decreases the battery health by 0.1 percentage points each time the vehicle is charged (100.0 → 99.9 → 99.8 …). The battery health must never go below 0.
  * `toString()`, which first calls the superclass method `toString()`, and then appends on their own lines the battery health as a percentage and the vehicle's range in kilometers, both with two decimals (`Battery health: 99.50%` and `Range: 401.98 km`). The range is calculated using the formula:
    (batteryHealth / 100 * MAX_RANGE)