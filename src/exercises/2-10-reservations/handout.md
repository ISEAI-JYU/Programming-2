Modify the example program containing the `Building`, `Room`, and `Reservation` classes so that the program does not allow overlapping reservations to be added to the same room. If the room already has a reservation that overlaps with a new reservation, the new reservation must not be created.

Also add validation checks that prevent invalid reservations from being created. The reservation duration must be at least 1 hour, and the start time must be between 0 and 23.

In this exercise, error situations may be handled by printing an error message.

Before starting the exercise, it is worth taking a moment to consider which responsibilities belong to which objects.

You can test your program using the provided main program.