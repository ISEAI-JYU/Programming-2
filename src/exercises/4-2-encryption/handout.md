Spies send messages to each other, but the encryption method changes daily so that the enemy cannot figure out the logic. We need an interface that allows us to switch the encryption algorithm on the fly.

1. Create an interface `Encryptor`. Define the following two methods in the interface:

```java,ignore
String encrypt(String message);
String decrypt(String encryptedMessage);
```

2. Implement three different classes: `Reverser`, `Hacker`, and `NextLetter`, which implement the `Encryptor` interface using the following logic:

    *  Reverser (Mirror Writing)
        Reverses a word or message.
        Example: "Agent" &rarr; "tnegA"
        Hint: You can use the `reverse()` method of the `StringBuilder` class or a loop that processes the string from end to beginning.

    * Hacker ("Leet Speak")
        Replaces certain letters with numbers or symbols.
        "Agent" &rarr; "@g3nt"

```
'a' -> '@'
'e' -> '3'
'i' -> '!'
'o' -> '0'
```

   * NextLetter (Caesar Shift)
Moves each letter one position forward in the alphabet.
Example: abc -> bcd.
Hint: In Java, `char` is a numeric type, so you can use:
`character + 1`.

Examples:

```text
'a' -> 'b'
'b' -> 'c'
'k' -> 'l'
```
and so on.

In this exercise, you do not need to worry about wrapping around the alphabet for letters such as `z`, `å`, `ä`, or `ö`, unless you want to.
You also do not need to worry about encryption and decryption always restoring the exact original message. For example, when using the `Hacker` encryptor, if the original message actually contains the character `@`, the `decrypt()` method will convert it to `a`. This is acceptable for this exercise, although a real encryption system should ensure that no information is accidentally lost or altered.

A main program is provided in TIM for testing your class structure.