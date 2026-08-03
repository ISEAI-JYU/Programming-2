1. Create an abstract class `CommunicationChannel`. It has an attribute `String recipient`, which is initialized in the constructor. Add an abstract method `sendInternally(String message)` that does not return a value.

2. Also create a method `String getRecipient()`, which returns the recipient.

3. Create a concrete method `send(String message)` that immediately exits (`return`) if the message is empty or `null`. Otherwise, the method should call the abstract method `sendInternally(String message)`.

4. Create the classes `Email` and `TextMessage` that inherit from `CommunicationChannel`. In both classes, override the abstract method `sendInternally(String message)`, which prints a message to the console in the format 
"Sending \<channel\> to \<address/number\>: \<message\>".
For example 
"Sending email to matti@maikalainen.com: Hi, what's up?"
or
"Sending text message to 0401234567: Welcome to the course!".