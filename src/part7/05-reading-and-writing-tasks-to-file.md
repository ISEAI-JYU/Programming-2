# Reading and Writing Tasks to a File

Our application is starting to be functionally complete.
Let's implement the final two features:

* Tasks are saved to a file so that they persist after the application is closed.
* Tasks are loaded from a file when the application starts.

In [Part 6.5](../part6/05-file-handling.md), we learned how to save objects to files using the JSON file format.
Let's first design the file format.
At the moment, a single task can be modeled using two attributes: the task's *text* as a string and a boolean value indicating whether the *task is completed*.
In other words, a single task can be represented as a JSON object:

```json
{
    "text": "Study programming",
    "completed": true
}
```

Since there are multiple tasks, we store them all in a single list, giving the JSON file the following structure:

```json
[
    {
        "text": "Study programming",
        "completed": true
    },
    {
        "text": "Go to school",
        "completed": false
    }
]
```

## Preparing for Saving

First, add the Jackson dependency following the instructions from 
[Part 6.5](../part6/05-file-handling.md#processing-json-files-with-the-jackson-library).

Next, create a class `Task` that models a single task.

Add the attributes `text` and `completed` as well as the required getter methods.

Also create a constructor that initializes the attribute values:

```java,ignore
public class Task {
    @SuppressWarnings("FieldMayBeFinal")
    private String text;
    @SuppressWarnings("FieldMayBeFinal")
    private boolean completed;

    @SuppressWarnings("unused")
    public Task() { /* Leave empty or provide a default implementation */ }

    public Task(String text, boolean completed) { /* Add implementation */ }

    public boolean getCompleted() { /* Add implementation */ }

    public String getText() { /* Add implementation */ }
}
```

Implement the missing functionality yourself.

We intentionally omit setter methods for now because the class is currently used only for loading and saving tasks.
However, we do not mark the fields as `final`, allowing Jackson to assign values to them.
We also add a no-argument constructor, which Jackson uses when constructing objects from JSON.

<details><summary><i class="bi bi-stars jyu-gold"></i>Optional information: Task is a record </summary>

If the fields are not intended to be modified (that is, all fields are `final`), the class can be written more compactly using Java's record syntax:

```java,ignore
record Task(String text, boolean completed) {}
```

Records were discussed in more detail in [Part 6.5](../part6/05-file-handling.md#record-classes-for-modeling-json-data).

In the next chapter, task objects will be used directly in the user interface, at which point we will add the necessary setter methods.
For this reason, we implement tasks as a normal class.
</details>

## Saving Tasks

Let's start with saving tasks.
First, we need to produce a list of tasks as `Task` objects.
At the moment, tasks are represented directly by `CheckBox` components.
Therefore, we must convert the checkboxes stored inside a `VBox` into a list of tasks.
Converting lists is easy using streams (see [Part 6.2](../part6/02-processing-collections-stream-api.md)).
Add the following code to the end of `addTask` to retrieve all incomplete tasks:

```java,ignore
void addTask() {
    // ... beginning of method hidden
//-    String text = newTaskName.getText();
//-    if (text == null || text.isBlank()) {
//-        newTaskName.requestFocus();
//-        return;
//-    }
//-    text = text.trim();
//-    CheckBox task = new CheckBox(text);
//-    task.setOnAction(event -> {
//-        if (task.isSelected()) {
//-            pendingTasks.getChildren().remove(task);
//-            completedTasks.getChildren().add(task);
//-        } else {
//-            completedTasks.getChildren().remove(task);
//-            pendingTasks.getChildren().add(task);
//-        }
//-    });
//-    pendingTasks.getChildren().add(task);
//-    newTaskName.clear();
//-    newTaskName.requestFocus();

    List<Task> pendingTasksList = pendingTasks.getChildren().stream()
            .map(n -> (CheckBox)n)
            .map(cb -> new Task(cb.getText(), cb.isSelected()))
            .toList();
}
```

We use a stream consisting of two `map()` transformations and a `toList()` collector.
Notice that the child components of a `VBox` are of type `Node`.
In JavaFX, all visual components, including `CheckBox`, inherit from `Node`.
In this case, we know that the completed-task and pending-task containers contain only checkboxes.
For that reason, the first `map` safely converts everything to `CheckBox`.

The second `map` converts the `CheckBox` objects into `Task` objects.
The `getText` method returns the text displayed inside the checkbox
(see [JavaDoc](https://download.java.net/java/GA/javafx25/docs/api/javafx.controls/javafx/scene/control/Labeled.html#getText()))
, and `isSelected`
(ks. [JavaDoc](https://download.java.net/java/GA/javafx25/docs/api/javafx.controls/javafx/scene/control/CheckBox.html#setSelected(boolean)))
returns whether the checkbox is selected—that is, whether the task is completed.
Finally, `toList` collects everything into a list.

<details><summary>Pending tasks lists with traditional loop</summary>

Of course, the same list could also be built using a traditional loop:

```java
List<Task> pendingTasksList = new ArrayList<>();

for (Node node : pendingTasks.getChildren()) {
    CheckBox c = (CheckBox)node;
    String textC = c.getText();
    boolean completedC = c.isSelected();
    Task task = new Task(textC, completedC);
    pendingTasksList.add(task);
}
```
</details>

<details><summary><i class="bi bi-stars jyu-gold"></i>Bonus: What if the container contains
other components than CheckBox-components?
</summary>


Here we omitted type checking because we know the container contains only checkboxes.
If the container contained various types of components and we wanted only checkboxes, we could use `instanceof`:

```java,ignore
List<Task> pendingTasksList = new ArrayList<>();

for (Node node : pendingTasks.getChildren()) {
    // In principle all child components should be
    // CheckBox components, but let's verify that.
    if (!(node instanceof CheckBox c)) {
        continue;
    }
    // If we get here, node is a CheckBox and
    // c can safely be used as a CheckBox.
    String textC = c.getText();
    boolean completedC = c.isSelected();
    Task task = new Task(textC, completedC);
    pendingTasksList.add(task);
}
```

Equivalent filtering can also be implemented using streams and `mapMulti`
([JavaDoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/stream/Stream.html#mapMulti(java.util.function.BiConsumer)))

```java,inogre
pendingTasks.getChildren().stream()
            .<CheckBox>mapMulti((n, consumer) -> {
                // Check whether the component is a CheckBox
                if (n instanceof CheckBox c) {
                    // If it is, c contains the converted component.
                    // consumer.accept adds it to the stream.
                    consumer.accept(c);
                }
            })
            .map(cb -> new Task(cb.getText(), cb.isSelected()))
            .toList();
```

</details>

Let's now save at least the pending tasks to a file using Jackson.
Add an `ObjectMapper` and call `writeValue()`:

```java,ignore
private void addTask() {
    // ... beginning of method hidden

//-    String text = newTaskName.getText();
//-    if (text == null || text.isBlank()) {
//-        newTaskName.requestFocus();
//-        return;
//-    }
//-    text = text.trim();
//-    CheckBox task = new CheckBox(text);
//-    task.setOnAction(event -> {
//-        if (task.isSelected()) {
//-            pendingTasks.getChildren().remove(task);
//-            completedTasks.getChildren().add(task);
//-        } else {
//-            completedTasks.getChildren().remove(task);
//-            pendingTasks.getChildren().add(task);
//-        }
//-    });
//-    pendingTasks.getChildren().add(task);
//-    newTaskName.clear();
//-    newTaskName.requestFocus();

    List<Task> pendingTasksList = pendingTasks.getChildren().stream()
            .map(n -> (CheckBox)n)
            .map(cb -> new Task(cb.getText(), cb.isSelected()))
            .toList();
    // HIGHLIGHT_GREEN_BEGIN
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(Path.of("tasks.json").toFile(), pendingTasksList);
    // HIGHLIGHT_GREEN_END
}
```
Try running the application and adding a few tasks.
Every time a task is added, the tasks are saved to `tasks.json`.

> [!NOTE]
> You should add the following line to `.gitignore`:
> `tasks.json`
> since this file should not be tracked by version control.
> 
> After modifying `.gitignore`, commit the change:
> 
> ```bash
> git add .gitignore
> git commit -m "Added tasks.json to .gitignore"
> ```
> If you already added `tasks.json` to version control, first remove it with
> `git rm --cached tasks.json`, and make a new commit, and only after that 
> change `.gitingore`-file.

Naturally, completed tasks must also be saved.
To avoid code duplication, let's create a helper method `getTasks(VBox container)` that converts the container's child components into a list of `Task` objects.
After that, we combine both task lists:

```java,ignore
private List<Task> getTasks(VBox container) {
    return container.getChildren().stream()
            .map(n -> (CheckBox)n)
            .map(cb -> new Task(cb.getText(), cb.isSelected()))
            .toList();
}

private void addTask() {

    // ... beginning of method hidden

    // HIGHLIGHT_RED_BEGIN
    List<Task> pendingTasksList = pendingTasks.getChildren().stream()
            .map(n -> (CheckBox)n)
            .map(cb -> new Task(cb.getText(), cb.isSelected()))
            .toList();
    // HIGHLIGHT_RED_END

    // HIGHLIGHT_GREEN_BEGIN
    List<Task> tasks = new ArrayList<>();
    tasks.addAll(getTasks(pendingTasks));
    tasks.addAll(getTasks(completedTasks));
    // HIGHLIGHT_GREEN_END
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(Path.of("tasks.json").toFile(), tasks);
}
```

Try running the application and inspect how `tasks.json` changes.
Now both pending and completed tasks are stored whenever a new task is added.

However, changing a task's status does not yet update the file.
We could duplicate the save logic inside the checkbox event handler, but that would not be a good solution.
Instead, let's refactor saving into its own method:

```java,ignore
private void save() {
    List<Task> allTasks = new ArrayList<>();
    allTasks.addAll(getTasks(pendingTasks));
    allTasks.addAll(getTasks(completedTasks));
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(Path.of("tasks.json").toFile(), allTasks);
}
```

Now we can simply call `save()` from both places:

```java,ignore
private void addTask() {
    // method beginning hidden...
//-  Platform.runLater(newTaskName::requestFocus);
//-  String text = newTaskName.getText();    // Retrieve text field contents
//-  if (text == null || text.isBlank()) {
//-          return;
//-      }
//-  text = text.trim();
    CheckBox task = new CheckBox(text);
    task.setOnAction(event -> {
        // event-handler beginning hidden...
//-         if (task.isSelected()) {
//-             // Task selected --> move to completed tasks
//-             pendingTasks.getChildren().remove(task);
//-             completedTasks.getChildren().add(task);
//-         } else {
//-             completedTasks.getChildren().remove(task);
//-             pendingTasks.getChildren().add(task);
//-         }
        // HIGHLIGHT_GREEN_BEGIN
        save();
        // HIGHLIGHT_GREEN_END
    });
//-    pendingTasks.getChildren().add(task);
//-    newTaskName.clear();
    // HIGHLIGHT_RED_BEGIN
    List<Task> tasks = new ArrayList<>();
    tasks.addAll(getTasks(pendingTasks));
    tasks.addAll(getTasks(completedTasks));
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(Path.of("tasks.json").toFile(), tasks);
    // HIGHLIGHT_RED_END
    // HIGHLIGHT_GREEN_BEGIN
    save();
    // HIGHLIGHT_GREEN_END
}
```

At this point, `addTask()` is also becoming a bit too large.

Creating a checkbox is clearly its own responsibility, so let's move that into a new method.

```java,ignore
private CheckBox createCheckBox(String text) {

    CheckBox task = new CheckBox(text);
    // method body hidden...
//-    task.setOnAction(event -> {
//-        if (task.isSelected()) {
//-            pendingTasks.getChildren().remove(task);
//-            completedTasks.getChildren().add(task);
//-        } else {
//-            completedTasks.getChildren().remove(task);
//-            pendingTasks.getChildren().add(task);
//-        }
//-        save();
//-    });
    return task;
}
private void addTask() {
//-    Platform.runLater(newTaskName::requestFocus);
//-    String text = newTaskName.getText();    // Retrieve text field contents
//-    if (text == null || text.isBlank()) {
//-        return;
//-    }
//-    text = text.trim();
    // HIGHLIGHT_RED_BEGIN
    CheckBox task = new CheckBox(text);
    task.setOnAction(event -> {
        if (task.isSelected()) {
            pendingTasks.getChildren().remove(task);
            completedTasks.getChildren().add(task);
        } else {
            completedTasks.getChildren().remove(task);
            pendingTasks.getChildren().add(task);
        }
        save();
    });
    // HIGHLIGHT_RED_END

    // HIGHLIGHT_YELLOW_BEGIN
    pendingTasks.getChildren().add(createCheckBox(text));
    // HIGHLIGHT_YELLOW_END
//-  newTaskName.clear();
//-  save();
}
```

## Loading Tasks

Now that tasks can be saved, they also need to be loaded when the application starts.
Let's immediately create a `load()` method.
We'll use the same approach we learned in [Part 6.5](../part6/05-file-handling.md#processing-json-files-with-the-jackson-library) and call `ObjectMapper.readValue()`.
We use `try-catch` to handle expctions:

```java,ignore
private void load() {
    Path path = Path.of("tasks.json");
    if (Files.notExists(path)) {
        return;
    }
    try {
        ObjectMapper mapper = new ObjectMapper();
        List<Task> allTasks = mapper.readValue( path.toFile(), new TypeReference<>() {});
        allTasks.forEach(task -> {
            CheckBox checkBox = createCheckBox(task.getText());
            if (task.getCompleted()) {
                completedTasks.getChildren().add(checkBox);
            } else {
                pendingTasks.getChildren().add(checkBox);
            }
        });

    } catch (JacksonException je) {
        IO.println("Failed to read JSON: " + je.getMessage()
        );
    }
}
```

Add a call to the beginning of `initialize()`:

```java
public void initialize(URL url, ResourceBundle resourceBundle) {
    load();
    // method ending hidden...
//-    newTaskName.setOnAction(event -> addTask());
//-    addNewTaskButton.setOnAction(event -> addTask());
}
```

Try running the application.
Tasks should now remain available even after the application is closed and reopened.

<video src="images/todo-app-save-load-buggy.mp4" controls></video>

However, there is one small problem.
Completed tasks are loaded into the completed-task list, but their checkboxes are no longer checked.
This happens because `createCheckBox` does not yet know whether the task is completed.

Let's add an additional parameter:

```java,ignore
private CheckBox createCheckBox(String text, boolean selected) {
    CheckBox task = new CheckBox(text);
    // HIGHLIGHT_GREEN_BEGIN
    task.setSelected(selected);
    // HIGHLIGHT_GREEN_END
    // rest of method hidden...
}
```

Now loading can restore the correct state:

```java
private void load() {
    // beginning of method hidden...
        allTasks.forEach(task -> {
            // HIGHLIGHT_YELLOW_BEGIN
            CheckBox checkBox = createCheckBox(task.getText(), task.getCompleted());
            // HIGHLIGHT_YELLOW_END

    // rest of method hidden...
}
```

At the same time, update the call inside `addTask()`.
New tasks are always incomplete, so pass `false`:

```java
private void addTask() {
    // beginning of method hidden...
//-    String text = newTaskName.getText();
//-    if (text == null || text.isBlank()) {
//-        newTaskName.requestFocus();
//-        return;
//-    }
//-    text = text.trim();
    // HIGHLIGHT_YELLOW_BEGIN
    pendingTasks.getChildren().add(createCheckBox(text, false));
    // HIGHLIGHT_YELLOW_END
    // rest of method hidden...
}
```

Save and run the application again.
Now completed tasks are correctly restored as selected when the application starts.