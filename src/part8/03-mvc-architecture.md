# MVC Architecture

Now that we have created the `Task` model with properties and can display a list of tasks using a `TableView`, it is time to think about the architecture of the entire application.

Application architecture refers to the overall organization of a program's different parts and responsibilities. Architecture consists of decisions that are very difficult to undo, change, or refactor later. A good architecture makes code easier to understand, extend, and test.

In very small projects, architecture does not usually require much thought. In this project, however, architecture has already become important, particularly so that task management, persistence, and the user interface do not all accumulate in a single class.

Fortunately, architecture rarely needs to be invented from scratch. There are many well-established architectural solutions. One such solution is **MVC** (*Model-View-Controller*).

In the MVC architecture, an application is divided into three parts: the model, the view, and the controller. The model manages data and data processing, the view displays the user interface, and the controller forwards user actions to the model and updates the view. In this project, MVC helps clarify the structure so that `MainController` is no longer responsible for everything; task management logic and persistence can be moved into their own model class.

In practice, applications rarely consist of only one model, one view, and one controller. A single program may have multiple models for different kinds of data, multiple views for different screens or use cases, and multiple controllers responsible for their own areas of the user interface. MVC therefore describes the principle of separating responsibilities, rather than requiring an application to be built from exactly one Model, one View, and one Controller class.

In this chapter, we identify the responsibilities of the different parts of our application and move the remaining task-management and persistence functionality out of the controller and into a dedicated model class.

## MVC Layers and Responsibilities

Let's take a closer look at the responsibilities of each MVC layer and how those layers are implemented in this project.

### View

* **Responsibility:** What the application looks like.
* **Implementation in the Todo application:** FXML files that define the user-interface structure.
* **Restrictions:** Contains no application logic whatsoever (for example, it does not know how tasks are saved to disk).

### Model

* **Responsibility:** What data the application contains and how that data is processed. This is often referred to as application logic, business logic, or domain logic.
* **Implementation in the Todo application:** We have already created the `Task` class to represent an individual task. In this chapter, we will also create a `TaskCollection` class that stores the state of the task list and provides methods for adding, removing, and saving tasks.
* **Restrictions:** Knows nothing about JavaFX views (`TableView`, `TextField`, etc.) and instead relies on observable structures to communicate changes to interested parties.

### Controller

* **Responsibility:** Acting as an interpreter between the view and the model.
* **Implementation in the Todo application:** `MainController` reacts to user actions such as button clicks, calls methods in the model (`TaskCollection`), and binds the view (`TableView`) to the model using observable data structures.

## MVC Encourages the Single Responsibility Principle

*Single Responsibility* is a software-design principle stating that every class or part of a program should have one clear responsibility—that is, one primary reason to change. The idea is that a class should not contain functionality that changes for different reasons. The Single Responsibility Principle is one of the [five SOLID principles](https://en.wikipedia.org/wiki/SOLID), which we will revisit later.
A "reason to change" means a requirement or need that forces modifications to a class's implementation.

For example, in our Todo application, task persistence might change because we decide to replace JSON files with a database. The user interface might change because we want to display tasks differently or because we decide to provide a command-line or web version of the application.
If the same class handled both persistence and the user interface, these unrelated reasons for change would become entangled.

MVC supports the Single Responsibility Principle because concerns that change for different reasons are separated into different layers.
In this project, the principle appears as follows:

* `Task` (and later `TaskCollection`) belong to the model because they represent application data and its rules.
* `MainController` does not save files directly but delegates that work to the model.
* The FXML view contains the structure of the interface, not application logic.

If `MainController` simultaneously handled button clicks, input validation, persistence, and file loading, the class would have many different reasons to change. This would make it harder to test, maintain, and safely extend.

## Refactoring the Application Packages

In a JavaFX application, MVC separation is easiest to see through packages and directory structure.
Currently, our application classes are organized as follows:

```bob
fi.jyu.ohj2.name.todo
├── data
│   └── Task
├── App
├── Main
└── MainController
```

Let's refactor the package structure so that MVC responsibilities become more visible:

```bob
fi.jyu.ohj2.name.todo
├── model
│   └── Task
├── controller
│   └── MainController
├── App
└── Main
```

Start by renaming the existing `data` subpackage to `model`.
(A subpackage is simply a package located inside another package, such as `data` inside `fi.jyu.ohj2.name.todo`.)
Open IDEA's Project view and right-click the `data` package.
Select **Rename** and change the package name from `data` to `model`, then click **Refactor**.

<video src="images/intellij-refactor-rename.mp4" controls></video>

Next, create a new subpackage called `controller`
(see [Part 6.4](../part6/04-external-libraries-and-java-project-management-tools.md#packages-in-java))
and move the `MainController` class into it.

<video src="images/intellij-refactor-new-package.mp4" controls></video>

Notice that IDEA automatically updates package declarations inside classes as well as references within FXML files.

## TaskCollection

We will now move the heart of the application—the task list and loading/saving operations—out of the controller and into its own class.
This functionality clearly belongs to application data and data processing, so in MVC it belongs in the model layer.

Create a class `TaskCollection` in the `model` package and move the task-management functionality there: the `tasks` list, its initialization, the `load()` method, and the `save()` method.
We also follow the principle of encapsulation: make the `tasks` list private and provide helper methods such as `getTasks()`, `addTask()`, and `removeTask()` for accessing and modifying it.
At the same time, let's perform a few additional refactorings: move the file location and the `ObjectMapper` into fields, since both are used by loading and saving.

```java,ignore
package fi.jyu.ohj2.name.todo.model;

//-import javafx.beans.Observable;
//-import javafx.collections.FXCollections;
//-import javafx.collections.ListChangeListener;
//-import javafx.collections.ObservableList;
//-import tools.jackson.core.JacksonException;
//-import tools.jackson.core.type.TypeReference;
//-import tools.jackson.databind.ObjectMapper;
//-
//-import java.nio.file.Files;
//-import java.nio.file.Path;
//-import java.util.List;

// imports omitted for brevity

public class TaskCollection {
    private final ObservableList<Task> tasks = FXCollections.observableArrayList(
                task -> new Observable[]{task.completedProperty()}
            );

    private final Path filePath = Path.of("tasks.json");
    private final ObjectMapper mapper = new ObjectMapper();

    public TaskCollection() {
        tasks.addListener((ListChangeListener<Task>) change -> {
            save();
        });
    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }

    public void save() {
        mapper.writeValue(filePath, tasks);
    }

    public void load() {
        if (Files.notExists(filePath)) {
            return;
        }
        try {
            List<Task> allTasks = mapper.readValue(filePath, new TypeReference<>() {});
            tasks.addAll(allTasks);
        } catch (JacksonException je) {
            IO.println("Failed to read JSON: " + je.getMessage());
        }
    }

    public void addTask(String text) {
        if (text == null || text.isBlank()) {
            return;
        }
        text = text.trim();
        tasks.add(new Task(text, false));
    }

    public void removeTask(Task task) {
        if (task == null) {
            return;
        }

        tasks.remove(task);
    }
}
```

Notice how all the rules ("task title cannot be empty", "update the file when a task is added or modified") now live inside the model class.

## The Controller's New Role

Finally, let's update `MainController`.
The controller now has a much clearer role as a "clerk" between the model and the view.
It no longer carries the burden of collection-management logic. Instead, it simply communicates between the user interface and `TaskCollection`.
`TaskCollection` acts as the application's top-level model. It owns the task list, handles loading and saving, and provides methods for adding and removing tasks.

Although this refactoring may seem like little more than moving code around, it is really about separating responsibilities according to the MVC model.
Once task management, persistence, and validation exist in a dedicated model class, they can be developed and tested independently of the user interface, while the controller remains simpler.

```java
package fi.jyu.ohj2.name.todo.controller;

//-import fi.jyu.ohj2.nimi.todo.model.Tehtava;
//-import fi.jyu.ohj2.nimi.todo.model.Tehtavakokoelma;
//-import javafx.collections.transformation.SortedList;
//-import javafx.fxml.FXML;
//-import javafx.fxml.Initializable;
//-import javafx.scene.control.Button;
//-import javafx.scene.control.TableColumn;
//-import javafx.scene.control.TableView;
//-import javafx.scene.control.TextField;
//-import javafx.scene.control.cell.CheckBoxTableCell;
//-
//-import java.net.URL;
//-import java.util.Comparator;
//-import java.util.ResourceBundle;
// imports omitted for brevity

public class MainController implements Initializable {
    @FXML
    private Button addNewTaskButton;

    @FXML
    private TextField newTaskName;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private Button deleteSelectedButton;

    // HIGHLIGHT_YELLOW_BEGIN
    private TaskCollection taskCollection = new TaskCollection();
    // HIGHLIGHT_YELLOW_END

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // HIGHLIGHT_YELLOW_BEGIN
        SortedList<Task> sortedTasks = taskCollection.getTasks().sorted(Comparator.comparing(Task::getCompleted));
        // HIGHLIGHT_YELLOW_END
        taskTable.setItems(sortedTasks);
        taskTable.setEditable(true);

        TableColumn<Task, Boolean> completedColumn = new TableColumn<>("Completed");
        completedColumn.setCellValueFactory(cd -> cd.getValue().completedProperty());
        completedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(completedColumn));
        taskTable.getColumns().add(completedColumn);

        TableColumn<Task, String> textColumn = new TableColumn<>("Task");
        textColumn.setCellValueFactory(cd -> cd.getValue().textProperty());
        taskTable.getColumns().add(textColumn);

        // HIGHLIGHT_YELLOW_BEGIN
        taskCollection.load();
        // HIGHLIGHT_YELLOW_END
        newTaskName.setOnAction(event -> addTask());
        addNewTaskButton.setOnAction(event -> addTask());
        deleteSelectedButton.setOnAction(event -> deleteSelected());
    }

    private void addTask() {
        // HIGHLIGHT_YELLOW_BEGIN
        taskCollection.addTask(newTaskName.getText());
        // HIGHLIGHT_YELLOW_END
        newTaskName.clear();
        newTaskName.requestFocus();
    }

    private void deleteSelected() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        // HIGHLIGHT_YELLOW_BEGIN
        taskCollection.removeTask(selectedTask);
        // HIGHLIGHT_YELLOW_END
    }

    // HIGHLIGHT_RED_BEGIN
    private void save() {
        //- ObjectMapper mapper = new ObjectMapper();
        //- mapper.writeValue(Path.of("tasks.json"), tasks);
    }

    private void load() {
        //- Path path = Path.of("tasks.json");
        //- if (Files.notExists(path)) {
        //-     return;
        //- }
        //- try {
        //-     ObjectMapper mapper = new ObjectMapper();
        //-     List<Task> allTasks =
        //-         mapper.readValue(path.toFile(), new TypeReference<>() {});
        //-     tasks.addAll(allTasks);
        //- } catch (JacksonException je) {
        //-     IO.println("Failed to read JSON: " + je.getMessage());
        //- }
    }
    // HIGHLIGHT_RED_END
}
```

<task>
<task-title>Exercise 8.3: Todo Application, Part 9
<points>1 p.</points> </task-title>
<handout>

{{#include ../exercises/8-3-todo-9/handout.md}}

</handout>
<task-link><a href="https://tim.jyu.fi/view/kurssit/it/iseai/26-27/programming2/exercises/part8/exercise3">Complete this exercise in TIM</a></task-link>
</task>