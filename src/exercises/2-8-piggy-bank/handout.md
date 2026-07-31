Implement a class called `PiggyBank`, whose purpose is to store money.

Attributes:

- `private double balance`: The current amount of money in the piggy bank.
- `private String owner`: The name of the piggy bank's owner.
- `private final String PASSWORD`: A password required for withdrawing money.

Constructor:
- Takes `owner` and `PASSWORD` as parameters.
- Sets the initial balance to `0.0`.

Methods:
- `public void deposit(double amount)`: Adds money only if `amount` is positive.
- `public double withdraw(double amount, String suppliedPassword)`: Checks whether `suppliedPassword` is correct. Checks whether there is enough money in the piggy bank. If both conditions are met, decreases the balance and returns the withdrawn amount. Otherwise, returns `0.0` and prints an error message.
- `public void printBalance()`: Prints `"Hello <owner>, the balance of your piggy bank is <balance> euros."`

Replace the parts enclosed in angle brackets with the appropriate attribute or parameter values.

Create a main program in which you create a `PiggyBank` object and test its functionality in different situations, such as depositing money, a successful withdrawal, and unsuccessful withdrawals (incorrect password, withdrawal amount exceeding the available balance).