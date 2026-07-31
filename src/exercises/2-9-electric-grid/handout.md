In this exercise, you will build a system that monitors the electricity consumption of buildings and prevents fuses from blowing. The exercise consists of several stages.

<details>
<summary>Stage 1: Electrical Device</summary>

Create a class called `ElectricalDevice`.

Devices have two immutable properties: a name and a current consumption in amperes.

Add the attributes:

- `private final String NAME`
- `private final double CURRENT`

Assume that the current consumption is always a positive number.

Create a constructor that initializes these values.

Add `private boolean connected`, which indicates whether the device is turned on.

Implement the methods `connect()` and `disconnect()`, which modify the state of the `connected` variable.
Also implement a `getCurrent()` method that returns the device's current consumption, and a corresponding `getName()` method.

Test your class in a main program by creating a few devices and turning them on and off.

</details>

<details>
<summary>Stage 2: Electrical Panel and Object List</summary>

Create a class called `ElectricalPanel`. The purpose of this class is to manage electrical devices.

Add the attribute `final double FUSE_RATING`. A fuse rating might be, for example, 16 amperes or 35 amperes.

Also add `private boolean fuseActive`, which indicates whether the fuse is intact (`true`) or blown (`false`). Initially, the fuse is active.

Create `List<ElectricalDevice> connectedDevices` (use an `ArrayList`).

Implement the method `double calculateCurrentConsumption()`, which iterates through the list and calculates the sum of the devices' `CURRENT` values.

</details>

<details>
<summary>Stage 3: Supervisory Logic and State Management</summary>

The panel must decide whether a device can be turned on.

Implement the method `boolean connect(ElectricalDevice device)`.
The method should check whether the current consumption plus the new device's current consumption is less than or equal to the fuse rating. If so, the device is added to the list and `device.connect()` is called.
Otherwise, the fuse blows: set `fuseActive = false`, turn off all devices in the list by calling `disconnect()`, and clear the list of connected devices.

Also implement the method `void disconnect(ElectricalDevice device)`, which removes the device from the list and calls `device.disconnect()`.

</details>

<details>
<summary>Stage 4: Global Monitoring</summary>

The power company wants to monitor the status of all electrical panels.

Add `static double totalNationalConsumption` to the `ElectricalPanel` class.

Update this variable whenever any device in any panel is turned on, disconnected from a panel, or when a fuse blows.

Add a static method `printNationalStatus()`, which prints the total electricity consumption.

</details>

You can test your program using the provided main program in TIM, or you may write your own test program.