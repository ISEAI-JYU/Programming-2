Create a `MessagingService` class to which different `CommunicationChannel` objects can be added using the method
`addChannel(CommunicationChannel channel)`
Also add a method
`sendToAll(String message)`
which sends the message through all channels at once.

Optional extra challenges (no points awarded, but they are included in the model solution):

1. Modify the `CommunicationChannel` class so that it accepts a list of recipients instead of just one recipient. As a result, the `sendInternally` methods must also be modified.
2. Add a character limit (e.g. 80 characters) to the `TextMessage` class. If a message exceeds this limit, it should be split into multiple parts according to the character limit.