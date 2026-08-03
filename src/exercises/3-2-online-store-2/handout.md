Let's continue from the previous exercise. Also create a class `Electronics`
that inherits from the `Product` class. Add the following special features to
each subclass:

- Clothing: attribute `String size` (e.g., "M", "L", etc.), and a method
  `void tryOn(String customerSize)` that prints whether the clothing item is
  suitable for the person trying it on.

- Electronics: attribute `int warrantyMonths` (e.g., 24), and a method
  `int warrantyRemaining(int monthsElapsed)` that returns how many months of
  warranty are left (or 0 if the warranty has expired).

- Food: attribute `String bestBefore` (e.g., "31.01.2026"), and a method
  `void eat()` that prints:
  "You consume food whose expiration date is DD.MM.YYYY."
  (replace DD.MM.YYYY with the value of `bestBefore`).

Note that the constructors of the subclasses must now call the superclass
constructor with the correct values and also initialize their own attributes.

A main program has been provided for you on the exercise page. Use it to test
your classes. It must not produce any compilation or runtime errors. However,
you may add your own code to the main program if you wish.