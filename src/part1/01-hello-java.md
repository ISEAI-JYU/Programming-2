# Hello, Java!

In the programming 2 we are using Java programming language.
Java is a high-level, general-purpose, object-oriented programming language.
Which is meant to write platform independent code.
It is one of the most popular programming language. (for example 
[TIOBE index](https://www.tiobe.com/tiobe-index/), 
[StackOverflow 2025 developer survey](https://survey.stackoverflow.co/2025/technology), 
[Most popular languages in GitHub](https://madnight.github.io/githut)).
Syntax of Java is one of "C-type" languages like C++ and C#. We will also learn 
C++ later in the course.

## Basics of Java

Let's start with the traditional ”Hello, World” program in Java:

```java
/* 1 */ void main() {
/* 2 */     IO.println("Hello, World!");
/* 3 */ }
```

Let's go through this program line by line:

1. Java programs execution starts with entry-point procedure called `main`. 
`void` means that this subroutine doesn't return any value. 
Beacuse the paranthesis `()` 
after the `main` means that this subroutine doesn't take any parameters.
The body of the subroutine starts with `{`

2. We print string to the console using `IO.println`-method.
Unlike in python, in java statements usally end with `;`. Just as here also.

3. The end of subroutine is indicated by `}`. The execution of program ends automatically,
when `main`-program has finished.

Already we see few key differences in syntax compared to python.
The body of subprogram is shown curly bracket not by intendation levels.
Also you need to remember the `;` after statements.

Even though this program is very simple it's a whole Java-program.
In the early parts of the course we are working mostly with CLI (command-line interface).
At the later parts of course we change the focus to working with GUI (graphical user interface)

## Coding practices in Java

Usually different programming languages have different best-practises 
involving syntax and the semnatics of programs. 

<!-- Like naming the variables. -->

Here are the most important ones to keep in mind.

- The curly bracket starting the body of program `{` is usually put at the same row
as the definition of subprogram. This is also true for control structure where 
their body is started with `{`, like `if`, `for`, `while` and `do-while`.

- Variables and names of subprograms are named using camelCasing, meaning the first
character is lowercase letter and the next words are started with uppercase letters.
For example `thisIsNameOfFunction`.

- The names of files and classes are named using PascalCasing, meaning the first
character is uppercase letter and the following words are also started with
uppercase letter: `HelloWorld.java`, `public class Student`, and so on.
This is also true for the interfaces, comperators and other things we will later 
in the course.

<!-- 
TODO
-->