# Project Assignment, Phase 1

In this chapter, you will begin implementing your own project assignment.
The project is completed incrementally during Parts 9–12, and no later than the end of Part 12 the finished project must be submitted and approved by a course instructor during remote or in-person guidance.
Read the [project requirements](../project-assignment.md) carefully before you begin.

Parts 9–12 contain guidance intended to help you make progress with the project. Likewise, Parts 9–12 contain exercises whose purpose is to support the project's gradual development.
As before, you must complete at least 50% of the exercises.

We recommend implementing the project using the phased approach described in these chapters.

## Project Topic

Start by choosing a project topic and familiarizing yourself with the project requirements.
You can find ready-made project topics in the project [assignment instructions](../project-assignment.md).

After selecting a topic, report it through the task below.

<task>
<task-title>Exercise 9.1: Assignment, topic
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/9-1-assignment/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part9/exercise1">Complete this exercise in TIM</a></task-link>
</task>

## Initializing the Project

Once you have selected a topic, you can create a new JavaFX project.
We recommend using the course's JavaFX project template introduced in [Part 7.1](../part7/01-javafx-fundamentals.md#the-first-javafx-application).

Tips:

* First create a separate empty directory for your project in a location that you can easily find on your computer.
* When creating the project in the IDE, choose that directory as the project location (`Location` setting).
* Give your project a unique identifier (`GroupId`). A suitable format is
`fi.jyu.ohj2.yourname.topic`
where `yourname` is your university username and `topic` is the project topic.

Once the project has been created, try running it and verify that the application starts correctly.

## Initializing a Git Repository

After creating the project, immediately create a Git repository inside the project directory using the command
`git init`.
However, do not create the first commit just yet. First, let's prepare the project directory slightly.

Projects using Git typically include `.gitignore` and `README.md` files.
The purpose of the `.gitignore` file was briefly discussed earlier [in the course](../part7/03-version-control.md): files and directories listed in it are not included in commits unless explicitly forced.
Examples include IDE-generated `out` and `target` directories that contain compiled code.
It is especially important to remember to add files containing sensitive information, such as personal data, passwords, or API keys, so that they do not accidentally end up in a remote repository.

Make sure your project contains a `.gitignore` file.
If you are using the course JavaFX template, one is already included.

Pay attention to the spelling
`.gitignore` begins with a period and is written entirely in lowercase.
`README.md` is written in uppercase letters and does not begin with a period.

The `README.md`, or "Read Me" file, is intended both for presenting the project and for providing important development instructions.
Remote repository services usually display this file on the project's front page, making it a good place to describe the project for non-technical users as well.
You can create a `README.md` file directly in the IDE by right-clicking the project in the project explorer, selecting **New** → **File**, and naming the file
`README.md`

<video src="images/intellij-readme-md.mp4" controls></video>

The `README.md` file is typically written using [Markdown](https://www.markdownguide.org/basic-syntax/).
At this stage, the README can be very simple.
Include at least the project name and a short description consisting of a few sentences
If you are using one of the predefined topics, you may copy the project description from the [project assignment instructions](../project-assignment.md#project-assignment).

Once you have created the `README.md` and `.gitignore` files, make your first commit.
Finally, create a remote repository and upload your local repository there using the [instructions](../part8/06-remote-version-control.md) for remote Git usage.

<task>
<task-title>Exercise 9.1: Assignment, Git Remote Repository
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/9-2-assignment/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part9/exercise2">Complete this exercise in TIM</a></task-link>
</task>

## Implementing the Data Model

Before diving deeper into the user interface, it is worth thinking carefully about the application's data model and behavior.
Begin development by implementing the classes that are essential to the data model.
In the predefined project topics, the classes, their attributes, and their relationships are already described using UML diagrams.

Tips:

* Implement data-model attributes using JavaFX `Property` types from the beginning. This will make it easier to connect the model and view later.

* Place data-model classes in a dedicated `model` package, separate from other classes.

* Start thinking about which public methods the class should provide to other classes. Although getter and setter methods are needed for JSON serialization, you can already consider methods that improve [encapsulation](../part2/03-encapsulation.md#encapsulation-and-cohesion). For example, in the Todo example application, the controller never adds a `Task` object directly into the task list of the collection. Instead, adding a task is the responsibility of the collection through its `addTask()` method.

  Do not spend too much time thinking about class behavior at this stage; not every future use case can be predicted. Additional helper methods can always be added later when implementing controller classes.

* We strongly recommend testing cooperation between model classes by using them directly from the application's `main` method (or from a helper method called by `main`).
  You do not need a user interface yet. Create and use objects directly from the main program.
  Verify that your model supports the application's most important operations, such as:
  adding data, retrieving data, modifying data and deleting data.
  The debugger can help verify that the model state is correct.

  If you wish, you may even write unit tests for the model's basic functionality. You can use the Todo application's model tests from [Part 8.5](../part8/05-unit-testing.md#testing-the-todo-application) as an example.

* There is no need to think about saving or loading data yet.

Once you have implemented an initial version of the data model in Java and the code contains no errors, it is a good time to save your progress in Git.

Create a new commit (`git add` + `git commit`) and push it to the remote repository (`git push`).

## Preliminary User Interface Plan

Once you have an understanding of the application's data model and requirements, it is a good time to begin planning the basic layout and behavior of the user interface.

Create a new directory in your project called `plan`
In the IDE, this can be done by right-clicking the project and selecting:
New → Directory
Create a file named `user_interface.md`
Write preliminary information about the required user-interface views into the file.

<details>
<summary>You can use the following template for the user interface design document</summary>

```markdown
# User Interface Plan

## View 1

![Rough layout of the view as an image (wireframe.cc, Draw.io, Paint, or a hand-drawn sketch on paper)](view1.jpg)

**Key functionality**

- What the user sees in the interface
- How the user reaches this view
  (application startup, button click, etc.)
- What the user can do in the interface:
  what can be clicked and what each button does

**Key components**

- Which JavaFX components might be needed
- This is mainly a place to record links to JavaFX classes
  and libraries so they can be easily found later
- This section is optional and intended only to make
  finding documentation easier

## View 2

![Rough layout of the view as an image (wireframe.cc, Draw.io, Paint, or a hand-drawn sketch on paper)](view2.jpg)

**Key functionality**

- What the user sees in the interface
- How the user reaches this view
  (application startup, button click, etc.)
- What the user can do in the interface:
  what can be clicked and what each button does

**Key components**

- Which JavaFX components might be needed
- This is mainly a place to record links to JavaFX classes
  and libraries so they can be easily found later
- This section is optional and intended only to make
  finding documentation easier
```

</details>

Draw preliminary sketches of every view.
At this stage, the exact appearance does not need to be finalized. The purpose is to focus on what the user sees and what the user can do.
You may create sketches using online diagram tools such as 
[wireframe.cc](https://wireframe.cc/),
[DrawIO](https://app.diagrams.net) or
[Figma](https://www.figma.com/)
or any other drawing application.
You may also draw on paper and take a photo or scan it.

The detailed visual design of the interface will be developed in Part 10.
If you wish, you may also build the views immediately using SceneBuilder.
In that case, take screenshots of the views.
Do not spend too much time perfecting the views at this stage; the goal is simply to develop a rough understanding of the planned user interface.

Save the images in the `plan` directory and reference them in the `user_interface.md` document.

In the plan, describe what is displayed in each view and how users can interact with it.
Doing so helps ensure that you remember all required create, read, update, and delete operations for your data model.

Once the user-interface plan is complete, create another commit and push the changes to the remote repository.

<task>
<task-title>Exercise 9.3: User Interface Design Plan
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/9-3-assignment/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part9/exercise3">Complete this exercise in TIM</a></task-link>
</task>

<task>
<task-title>Exercise 9.4: Presenting the Phase to the Instructor
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/9-4-assignment/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part9/exercise4">Complete this exercise in TIM</a></task-link>
</task>
