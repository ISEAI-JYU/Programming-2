Create a class `Reservations` that stores reservations. There can be **only one** reservation for a given date, and the name of the person making the reservation must also be stored.

In this exercise, you may represent dates as strings in the format `YYYY-MM-DD` (year-month-day). You may also assume that all dates are always in the correct format.

Implement the following methods:

- `addReservation` takes a date and the reservation holder's name as string parameters and adds the reservation to the data structure. If a reservation already exists for that date, the new reservation must not replace it. The method returns `true` if the reservation is added, otherwise `false`.

- `removeReservation` takes a date as a parameter and removes the reservation for that date. The method returns `true` if the reservation is removed from the data structure, otherwise `false`.

- `printReservations` takes a start date and an end date as parameters and prints all reservations that fall within that range, **sorted by reservation date**.

You can test the functionality of the class using the provided main program.

<details><summary>Hint</summary>

In this case, it is not advisable to sort the data structure manually. One of the classes implementing the `Map` interface keeps its elements automatically sorted.

</details>