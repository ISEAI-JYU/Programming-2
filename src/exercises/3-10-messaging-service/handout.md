Continue with the `CommunicationChannel`, `Email` and `TextMessage` classes from the previous exercise (copy them to this exercise).

Create a `MessagingService` class (with a parameterless constructor) to which different `CommunicationChannel` objects can be added using the method
`void addChannel(CommunicationChannel channel)`
Also add a method
`void sendToAll(String message)`
which sends the message through all added channels, one after the other, by calling each channel's `send(String message)` method. Each `MessagingService` object keeps its own list of channels.

A main program has been provided on the exercise page, which you can use to test your class.

Optional extra challenges (no points awarded, but they are included in the model solution):

1. Extend the `CommunicationChannel` class so that a channel can also have a list of recipients. Add this as a second constructor; keep the existing single-recipient constructors `Email(String address)` and `TextMessage(String number)`, because the main program uses them. As a result, the `sendInternally` methods must also be modified.
2. Add a character limit (e.g. 80 characters) to the `TextMessage` class. If a message exceeds this limit, it should be split into multiple parts according to the character limit. The whole message must still be delivered.