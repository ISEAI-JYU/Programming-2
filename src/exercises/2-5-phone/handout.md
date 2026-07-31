Create a class called `Phone` with the attributes `brand` (a string) and `batteryLevel` (an integer representing the battery charge as a percentage between 0 and 100).

Add the following methods to the class:

- `sendMessage(String person, String message)`: prints a message in the format:
  `"Sending a message to <person>: <message>"`
  Sending a message reduces the battery level by 5 percentage points.

- `call(String person, int minutes)`: prints a message in the format:
  `"Calling <person>, duration: <minutes> minutes"`
  Making a call reduces the battery level by 1 percentage point per minute.

- `charge(int percentage)`: increases the battery level by the given amount, but the battery level cannot exceed 100%.

- `printInfo()`: prints the phone's brand and battery level in the format:
  `"The battery level of the <brand> phone is <battery>%."`

Replace the parts enclosed in angle brackets with the appropriate attribute or parameter values.

The battery level cannot drop below 0%.

If the battery level is 0%, messages cannot be sent and calls cannot be made. Print:
`"Battery empty. Cannot send message."`
or
`"Battery empty. Cannot make a call."`

Test your application by creating a `Phone` object, sending a message, making a call, charging the battery, and printing the phone's information.

<details><summary>Optional additional exercise: if battery runs out</summary>

Modify the `sendMessage` and `call` methods so that if the battery level is not sufficient to send the entire message or complete the entire call, the message is sent or the call is made only for as long as the battery lasts.

If the battery runs out during a message or call, print how much of the message was successfully sent or how long the call lasted before the battery was depleted. For example:
- `"Battery empty. You managed to send 60% of the message."`
- `"The battery ran out after 3 minutes."`

</details>