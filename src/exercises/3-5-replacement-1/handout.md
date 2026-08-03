Create a class `Vehicle` with an attribute `String brand` and a constructor that initializes this value. Also add a method `move()`, which does nothing.

Create the classes `Car` and `Airplane` that inherit from `Vehicle`. Add a `move()` method to both `Car` and `Airplane` that overrides the `move()` method from the `Vehicle` class.
A `Car` object should print "
Car \<brand\> drives down the highway with its tires squealing."

and an `Airplane` object should print:
"Airplane \<brand\> takes off from the runway and flies above the clouds.".

Create a main program in which you create two `Vehicle` variables (not `Car` or `Airplane` variables) and assign a `Car` object to one and an `Airplane` object to the other.
Call the `move()` method on both objects.