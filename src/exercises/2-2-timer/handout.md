Create a class called `Timer` with the integer attributes `minutes` and `seconds`.

Add the methods `addMinutes` and `addSeconds` to the class. These methods take the number of minutes and seconds to be added to the timer as parameters. Also add a method called `toString` that returns the timer's minutes and seconds as a string.

The number of minutes can be any non-negative value, but the number of seconds must always be between 0 and 59. If the seconds exceed this range, they must be converted into minutes.
You can convert seconds into minutes and remaining seconds as follows:

```java
int seconds = 75; // Example of value in parameter

// This gives the amount of minutes to be added
int minutesToAdd = (this.seconds + seconds) / 60;

int remainingSeconds = (this.seconds + seconds) % 60;
```

You can test the functionality of the class using the provided main program.