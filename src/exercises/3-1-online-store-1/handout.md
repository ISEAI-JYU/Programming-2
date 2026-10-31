Create a class called `Product`. Give it the attributes `name` (a string) and
`price` (a decimal number). Initialize the attribute values in the constructor.
Also create a method `void printDetails()` that prints the product's name and
price in the format "Name: Price €".

Create the classes `Clothing` and `Food` that inherit from the `Product`
class. The constructors of the derived classes do not need to do anything
other than call the superclass constructor with the correct values.

A main program has been provided on the exercise page. It creates two different
clothing items and two different food products and prints their details by
calling the `void printDetails()` method, so with the classes implemented as
described it prints:

```text
Talvitakki Dulce & Käppänä: 120.0 €
Lee Boomer: 67.69 €
Ruisleipä Reissurähjä: 2.5 €
Falio Sininen: 0.99 €
```

You may add your own products to the main program if you wish.