Create an abstract class `Message` with an attribute `String message`, which is initialized in the constructor. Set the visibility of the `message` attribute to be as restrictive as possible. The class also contains an abstract method `void send()`.

Create the classes `Email` and `TextMessage` that inherit from `Message`. Both classes should have a constructor that calls the superclass constructor. Implement the `send()` method in both classes.
The `send()` method of the `Email` class should print
"Sending email: \<message\>" and
the `send()` method of the `TextMessage` class should print
"Sending text message: \<message\>".