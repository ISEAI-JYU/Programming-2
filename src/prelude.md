# Prelude

## How did we get here and where are we going?

In Programming 1 we used Python and learned the basics of programming: variables, conditionals, loops, functions, and working with data.
Here is a fun fact: by around week three of that course, you had already learned everything you *technically* need to write any computer program.
A language with variables, conditionals, and loops can — given enough time and memory — compute anything that can be computed at all.
This property is called *Turing completeness*, named after the famous computer scientist Alan Turing.

Knowing that, you might reasonably wonder:

- You just said we have everything we need. Why learn more?
- Why do I need to write programs by "hand" like a caveman when we have AI tools that write the code for us?
- Really, *every* program there is? Did that Turing guy know what he was talking about?

These are good questions, and this course is, in a sense, a semester-long answer to them. Let us start with short versions.

### Why learn more, if we can already compute anything?

Turing completeness is a statement about what *can be computed* — it says nothing about whether the program is fast, correct, understandable, or something a team of people can keep developing for the next ten years.
Any program could, in principle, be written with week-three Python. It could also, in principle, be written in raw machine code. Nobody does, because *being possible* and *being practical* are very different things.

Software engineering, and especially programming, is not just writing instructions that the machine then magically runs.
It is the craft of making programs that are **reliable**, **efficient**, and **easy to maintain and extend** — programs built by teams, read by other people, and modified long after the original author has moved on.
That is what this course is about, and it is a much deeper skill than making the computer do something once.

### Why learn to program in the age of AI?

AI tools are genuinely useful, and you are encouraged to explore them. But they amplify skill; they do not replace it.
To use generated code responsibly, you must be able to *read* it, *judge* whether it is correct and well designed, *debug* it when it is not, and *specify* precisely what you want in the first place.
Every one of those abilities comes from the kind of understanding you build by programming yourself.

This is also why the course works the way it does: your grade is based on oral exams where you explain *your own code*.
A program you cannot explain is a program you do not control — and as a future software engineering and AI professional, you will be the person others trust to know the difference.

You can follow the patterns shown to you in the [Introduction to It](https://tim.jyu.fi/view/kurssit/it/iseai/26-27/introduction-to-it/13-llm-engineering/lesson#five-useful-llm-patterns)

### Was Turing right?

Yes — with the caveats above. The theory holds; the practice is where the work is. Which brings us to what changes in this course.

## From Python to Java

Programming has climbed a long ladder of abstraction.
In the beginning, code was written directly as processor instructions — raw bits.
Then came assembly languages, then high-level languages that let us think in terms of variables and functions instead of registers and memory addresses.
Python sits comfortably near the top of this ladder: it is forgiving, concise, and quick to start with, which makes it an excellent first language.

But as programs grow from scripts into applications, some of that ease starts to cost us.
In Python, many mistakes — like passing the wrong type of value to a function — are only discovered when the program is already running.
In a 50-line script you notice immediately; in a 5,000-line application, the error may hide for weeks.

In this course we switch to **Java**, a statically typed language. In Java, you declare what type of data each variable holds, and the compiler checks your program *before* it runs, catching a whole class of mistakes at your desk instead of in production.
The structure Java imposes can feel strict after Python, but that strictness is exactly what keeps large programs manageable.

The second big change is the programming *paradigm*.
So far you have mostly written procedural code: data in variables, processed by functions.
In this course we learn **object-oriented programming**, where data and the functionality that operates on it are bundled together into objects.
We will dig into what that means — and why it helps — right at the start of the course, in [Towards Object-Oriented Programming](./part2/01-towards-object-oriented-programming.md).

One caveat before we dive in: object-oriented programming is *a* paradigm, not *the* paradigm.
Programmers have invented several ways of structuring programs — procedural, object-oriented, functional, declarative — and each shines in different situations. Modern languages mix them freely: Python supports several styles, and Java has adopted functional features too (we will meet lambdas and streams later in the course).
We focus on OOP because it is a battle-tested way to structure the kind of applications we build here, and because its ideas are everywhere in industry — but treat it as a powerful tool in your toolbox, not as the absolute truth.

## Where we are going

The course builds up in layers:

1. **The basics of Java** — the familiar concepts from Programming 1, in a new language.
2. **Object-oriented programming** — classes, objects, encapsulation, inheritance, polymorphism, and interfaces.
3. **Data structures and useful techniques** — collections, recursion, streams, exception handling, and files.
4. **Graphical user interfaces** — building desktop applications with JavaFX, plus version control and unit testing.
5. **The project** — a pair project where you design and build a complete application of your own.

By the end, you will have moved from writing programs that *work* to designing programs that *last*. Let's get started.
