# Variables and data types

Programs use information stored as data. In high-level programming languages
like Java and Python we use human-readable names to refer to the data stored
in memory. This kind of name is referred to as *variable*. 
Programmer only needs to remember the name of variable; operating system and
the internal logic of computer handles the real information where the data
is really stored in computer.

Before we can use variable, it must be *defined* 

```java,ignore
type nameOfVariable;
```

The type of the variable is defined before the name of the variable and it 
tells what is the ”data type” of the variable. For example integer, floating number 
or boolean value.
The name of the variable is programmers how the variable is references.
Name can contain numbers and underscores.
The name of the variable can't start with number and cannot be a reserved keyword
in Java.

After we have defined the variable we can *assign* values to it:

```java,ignore
nameOfVariable = expression;
``` 

The righthand side of the equal signs is the expression to be stored to the variable.
If the variable already contained some expression, it's replaced by the new expression.

```java
//-void main() {
double intrestRate; // Definition of variable , double = decimal number
double capital; // Definition of variables

intrestRate = 0.05; // Assignment of value to variable
capital = 150.0; // Assignment of value to variable
//- IO.println("intrestRate = " + intrestRate);
//- IO.println("capital = " + capital);
//- }
```

Variable can't be used before it's assigned a value at least once.
(What happens if you try to?)
For this purpose 