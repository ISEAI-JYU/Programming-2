# Unit Testing

The basic idea of unit testing is simple: test small parts of a program, such as individual classes or methods, separately from the rest of the system. The goal is to ensure that each part works correctly within its own responsibility. When a test remains small and narrowly focused, locating errors is much easier than trying to test the entire application at once.

Unit tests are useful because they reveal errors quickly during development. They also act as a safety net: if we modify code later, running the unit tests quickly shows whether previously working functionality has broken. Third, they force the programmer to think about what a class interface should look like and what the class is actually supposed to do.

Consider the `TaskCollection` class, for example. We can write a test that adds one task to the collection and verifies that the list now contains one item. We can write another test that attempts to add an empty title and verifies that no task is added. A third test could remove a selected task and verify that the collection size decreases correctly. Each of these tests verifies one clear piece of behavior.

## JUnit

In the Java ecosystem, unit tests are commonly written using the **JUnit** library. JUnit provides ready-made tools for writing test methods and verifying expected results.

At the time of writing, the current JUnit version is 6.1.2, also known as JUnit Jupiter. Add the `junit-jupiter` dependency to `pom.xml`:

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>6.1.2</version>
    <scope>test</scope>
</dependency>
```

Let's create a simple example using JUnit.
Create a new Maven project. Add a method `average` to the main class. The method computes the average value of a list, except that if it encounters a number greater than or equal to a stop value, all following numbers are ignored.

```java
public static double average(List<Integer> numbers, int stopNumber) {
    if (numbers.isEmpty()) {
        throw new IllegalArgumentException("The list must not be empty");
    }
    int sum = 0;
    int count = 0;
    for (int number : numbers) {
        if (number >= stopNumber) {
            break;
        }

        sum += number;
        count++;
    }

    return (double)sum / count;
}
```

Let's write a unit test for this method.
Tests are traditionally placed in the `src/test/java` directory. When creating a new directory under `src` in a Maven project, IDEA automatically suggests creating the `src/test/java` structure.
Create the test directory and inside it a class called `AverageTest`.
If you copy the code below, update the first import statement to match the name of your own main class.

```java,ignore
import fi.jyu.ohj2.Main;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AverageTest {

    @Test
    void averageCalculatesCorrectly() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        double result = Main.average(numbers, 10);
        assertEquals(3.0, result, "The average should be 3.0");
    }

    @Test
    void averageStopsCorrectly() {
        List<Integer> numbers = List.of(1, 2, 3, 10, 4, 5);
        double result = Main.average(numbers, 10);
        assertEquals(2.0, result, "The average should be 2.0 because 10 and all following values are ignored");
    }
}
```

Tests can be run using the green arrow beside the `AverageTest` class.
JUnit runs the tests and displays the results in IDEA's test view.
If all tests pass, a green indicator is shown.
If a test fails, a red indicator appears together with an error message explaining what went wrong.

One important test is still missing: what happens if there are no valid numbers at all?
Let's decide that the method should throw an `IllegalArgumentException`.
We can verify this using another test.

JUnit allows exception testing through `assertThrows`. It accepts the expected exception class and a lambda expression containing the code being tested.

```java
@Test
void averageThrowsExceptionWhenNoNumbersAreIncluded() {
    List<Integer> numbers = List.of(10, 20, 30, 40, 50, 60);
    IllegalArgumentException exception =
            assertThrows( IllegalArgumentException.class, () -> {
                        Main.average(numbers, 10);
                    }
            );
    assertEquals("No numbers were included in the average", exception.getMessage());
}
```

The test now fails with:

```text
org.opentest4j.AssertionFailedError: Expected java.lang.IllegalArgumentException to be thrown, but nothing was thrown.
```

When using `double` values, division by zero does not throw an exception. Instead, it produces the `NaN` value.
Let's fix the method:

```java,ignore
public static double average(List<Integer> numbers, int stopNumber) {
    // ... previous code unchanged ...

    // HIGHLIGHT_GREEN_BEGIN
    if (count == 0) {
        throw new IllegalArgumentException("No numbers were included in the average");
    }
    // HIGHLIGHT_GREEN_END
    return (double)sum / count;
}
```

Now all tests pass!

## Testing the Todo Application

When testing the Todo application, everything does not need to go through the user interface.
What matters is testing the application's business logic, meaning how `TaskCollection` behaves in different situations.
Instead of clicking buttons and opening windows, we call model methods directly and verify that the results match our expectations.

Examples of testable behavior include:

* `addTask("Go shopping")` adds a task to the list.
* `removeTask(task)` removes the given task.
* `addTask("   ")` does not add an empty task.

For this reason, we can create a JUnit test class such as the following in `src/test/java`:

```java,ignore
import fi.jyu.ohj2.name.todo.model.Task;
import fi.jyu.ohj2.name.todo.model.TaskCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskCollectionTest {

    @Test
    void addTask_addsTaskToList() {
        TaskCollection collection = new TaskCollection("testtasks.json");
        collection.addTask("Go shopping");
        assertEquals(1, collection.getTasks().size());
        assertEquals("Go shopping", collection.getTasks().getFirst().getTitle());
    }

    @Test
    void removeTask_removesTaskFromList() {
        TaskCollection collection = new TaskCollection("testtasks.json");
        collection.addTask("Go shopping");
        Task task = collection.getTasks().getFirst();
        collection.removeTask(task);
        assertEquals(0, collection.getTasks().size());
    }

    @Test
    void addTask_doesNotAddEmptyTask() {
        TaskCollection collection = new TaskCollection("testtasks.json");
        collection.addTask("   ");
        assertEquals(0, collection.getTasks().size());
    }
}
```

We also added a new constructor to `TaskCollection` that accepts a file name as a parameter.
This allows tests to use a separate test file without interfering with real data.
Add the following constructor to `TaskCollection`:

```java,ignore
public TaskCollection(String path) {
    filePath = Path.of(path);
    tasks.addListener((ListChangeListener<Task>) change -> {
        save();
    });
}
```

The idea behind these tests is straightforward:

1. Prepare the initial state.
2. Call the method being tested.
3. Verify that the collection changed correctly.

This is exactly the kind of business-logic testing that the MVC architecture enables.

Add the line `testtasks.json`
to `.gitignore` so that test files are not added to version control.
If the file was already committed, remove it with:
`git rm --cached testtasks.json`
and create a new commit.

## Unit Testing and MVC Architecture

Now that we have moved to an MVC architecture and created the `TaskCollection` class, we have completely separated the user interface from the application's logic and data.
This provides a crucial software-engineering benefit.
If we attempted to test our code through the user interface by simulating button presses, testing would be slow, vulnerable to random failures, and would require JavaFX to be launched.
Since the collection now has a clear programming interface (`addTask`, `removeTask`, and so on), we can build unit tests that exercise the collection directly and verify behavior in milliseconds without opening any windows.

Unfortunately, there is still one practical issue.
The current `TaskCollection` class also performs file operations.
This means that testing is not yet entirely straightforward.

## I/O Is Problematic in Testing

Consider what happens if we start testing our new `TaskCollection` class.
Suppose a test creates ten new tasks and verifies that the count is correct.
Because we attached a listener that calls `save`, those test tasks are actually written to disk, for example into `tasks.json`.

Writing to disk is generally undesirable in tests.
In a production environment, tests may be executed hundreds of times in succession, and disk I/O makes them much slower.
Furthermore, if tests fail or terminate unexpectedly, they can leave files in a messy state containing partially written or outdated data.

## Separating Persistence Behind an Abstraction: The Repository Pattern

The solution is to separate persistence into its own component.
`TaskCollection` should no longer read and write `tasks.json` directly, but instead delegate persistence to a separate class.
This improves unit testing because tests are no longer directly tied to real files or file systems.
This approach is commonly known as the *Repository pattern*.

The Repository pattern hides loading and saving data behind a dedicated interface or class.
The rest of the application no longer deals directly with files, databases, or other storage mechanisms, but instead relies on methods provided by a repository interface such as `TaskRepository`.

Let's see how this is implemented in practice in our Todo application.

**1. Creating the TaskRepository Interface**.
Methods related to loading and saving data are typically placed in a package called `persistence`. Let's do the same.

```java,ignore
package fi.jyu.ohj2.name.todo.persistence;

import fi.jyu.ohj2.name.todo.model.Task;
import java.util.List;

public interface TaskRepository {
    List<Task> load() throws RepositoryException;
    void save(List<Task> tasks) throws RepositoryException;
}
```

At the same time, let's create a separate `RepositoryException` class to represent loading and saving failures. Since tasks may eventually be loaded from sources other than files, a more general exception type is useful.
Place this class in the `persistence` package as well.

```java,ignore
package fi.jyu.ohj2.name.todo.persistence;

public class RepositoryException extends Exception {

    public RepositoryException(String message) {
        super(message);
    }
}
```

**2. Moving JSON Persistence into Its Own Implementation**
Next, we separate the JSON-specific loading and saving code from the model into a dedicated implementation called `JsonTaskRepository`, also located in the `persistence` package.
Copy the loading and saving code from `TaskCollection` into a new class that implements the `TaskRepository` interface.

```java,ignore
package fi.jyu.ohj2.name.todo.persistence;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.JacksonException;
import fi.jyu.ohj2.name.todo.model.Task;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JsonTaskRepository implements TaskRepository {
    private final Path saveFile;
    private final ObjectMapper mapper = new ObjectMapper();

    public JsonTaskRepository(Path saveFile) {
        this.saveFile = saveFile;
    }

    @Override
    public List<Task> load() throws JacksonException {
        if (Files.notExists(saveFile)) {
            return List.of();
        }
        return mapper.readValue(saveFile.toFile(), new TypeReference<>() {});
    }

    @Override
    public void save(List<Task> tasks) throws JacksonException {
        mapper.writeValue(saveFile.toFile(), tasks);
    }
}
```

**3. Updating TaskCollection to Work with Any Persistence Mechanism**.
Next, modify `TaskCollection` so that it accepts any implementation of the repository interface.
Instead of creating a repository internally, it will receive one through its constructor.
This approach is known as *dependency injection*.
Dependency injection means that a class does not create its own dependencies. Instead, they are supplied from outside.
In particular, `TaskCollection` will no longer create a `JsonTaskRepository` object itself. Instead, it receives one as a constructor parameter.
The benefit will become even clearer when we begin writing tests.

```java,ignore
public class TaskCollection {
    // The persistence mechanism is now hidden behind an interface.
    private final TaskRepository repository;

    // The desired persistence implementation
    // is injected through the constructor.
    public TaskCollection(TaskRepository repository) {
        this.repository = repository;

        this.tasks.addListener(
                (ListChangeListener<Task>) change -> {
                    save();
                }
        );
    }

    public void load() {
        try {
            List<Task> allTasks = repository.load();
            tasks.addAll(allTasks);
        } catch (RepositoryException e) {
            IO.println(e.getMessage());
        }
    }

    public void save() {
        try {
            repository.save(tasks);
        } catch (RepositoryException e) {
            IO.println(e.getMessage());
        }
    }

    // ... addTask(), removeTask(), etc. like before
}
```

Also update the initialization of `TaskCollection` inside `MainController`:

```java,ignore
private TaskCollection taskCollection = new TaskCollection(new JsonTaskRepository(Path.of("tasks.json")));
```

In our Todo application, the Repository pattern supports the MVC architecture because it keeps responsibilities separate even within the model layer.
`TaskCollection` belongs to the model, but it no longer needs to know the technical details of persistence.
It can simply ask the repository to load or save tasks while focusing on business logic.
As a result, the controller and view remain independent of file handling.

The real strength of the interface is that `TaskCollection` no longer needs to know *how* or *where* data is stored. It could be a JSON file, a database, or even an in-memory collection used for testing.

## Mock and Fake Classes

Testing often makes use of so-called *mock* or *fake* objects when the class being tested collaborates with another object.
The idea is to replace a real dependency with a lightweight substitute whose behavior is easier to control.
This allows the test to focus on the class being tested without involving files, databases, networks, or other heavyweight systems.

Consider a simple example where a `FrostMonitor` class obtains temperature readings through a `TemperatureSensor` interface.
If we want to test `FrostMonitor`, we may not want to use a real sensor. The sensor may not even exist in the test environment, or its value may constantly change.
Instead, we can create a fake sensor that always returns `21.5`.
This makes the test completely predictable.

For example:

```java,ignore
public interface TemperatureSensor {
    double measureTemperature();
}
public class FrostMonitor {
    private final TemperatureSensor sensor;

    public FrostMonitor(TemperatureSensor sensor) {
        this.sensor = sensor;
    }

    public boolean isFreezing() {
        return sensor.measureTemperature() < 0;
    }
}
```

In the real application, the sensor might read values from a physical device.
In a test, however, we can use a simple fake implementation:

```java
public class FakeTemperatureSensor implements TemperatureSensor {
    @Override
    public double measureTemperature() {
        return 21.5;
    }
}
```

Now we know exactly what value the sensor returns every time.

Such replacement objects are especially useful whenever a real dependency is slow, difficult to control, or produces side effects.
Next, we'll apply exactly the same idea to the Todo application by creating a fake repository that pretends to store data but actually keeps everything in memory for the duration of the test.

## Testing with a Mock Repository

Inside the test environment (`src/test/java/...`), we can create a mock class that pretends to save data to a file but actually stores everything in a normal Java list in memory.

```java,ignore
public class MockTaskRepository implements TaskRepository {

    // Stored in memory instead of a file for tests
    private List<Task> savedTasks = new ArrayList<>();

    @Override
    public List<Task> load() {
        return savedTasks;
    }

    @Override
    public void save(List<Task> tasks) {
        savedTasks.clear();
        // Create copies of all tasks.
        // This allows us to verify that
        // saved data matches the data
        // stored inside the collection.
        for (Task task : tasks) {
            Task copy = new Task();
            copy.setTitle(task.getTitle());
            copy.setPriority(task.getPriority());
            copy.setDescription(task.getDescription());
            copy.setCompleted(task.getCompleted());
            savedTasks.add(copy);
        }
    }

    public List<Task> getSavedTasks() {
        return this.savedTasks;
    }
}
```

Now we can test the model safely using JUnit:

```java,ignore
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskCollectionTest {

    @Test
    void addTask_addsTaskAndSavesIt() {
        // 1. Arrange: Inject a FAKE repository
        MockTaskRepository mockRepo = new MockTaskRepository();
        TaskCollection model = new TaskCollection(mockRepo);

        // 2. Act
        model.addTask("Go shopping");

        // 3. Assert
        assertEquals(1, model.getTasks().size(), "The list should contain 1 task.");
        assertEquals("Go shopping", model.getTasks().getFirst().getTitle(), "The title should match.");

        // 4. Assert 2: Verify that saving occurred
        assertEquals(1, mockRepo.getSavedTasks().size(), "Data should have been saved through the repository!");
    }

    @Test
    void addTask_doesNotAddEmptyTitle() {
        MockTaskRepository mockRepo = new MockTaskRepository();
        TaskCollection model = new TaskCollection(mockRepo);

        model.addTask("   ");

        assertEquals(0, model.getTasks().size(), "Empty tasks must not be added.");
    }
}
```

We can actually go even further and verify that tasks are saved whenever either the collection or a task changes.
Let's create dedicated tests specifically for saving behavior:

```java
@Test
void taskCollectionSavesAfterAddAndRemove() {
    // Act and Assert steps can be repeated
    MockTaskRepository repo = new MockTaskRepository();
    TaskCollection collection = new TaskCollection(repo);
    // Act 1: Add task
    collection.addTask("Go shopping");
    // Assert 1
    assertEquals(1, repo.getSavedTasks().size(), "Tasks should be saved when a new task is added");

    // Act 2
    Task task = collection.getTasks().getFirst();
    collection.removeTask(task);
    // Assert 2
    assertEquals(0, repo.getSavedTasks().size(), "Tasks should be saved when a task is removed");
}
@Test
void taskCollectionSavesPropertyChanges() {
    MockTaskRepository repo = new MockTaskRepository();
    TaskCollection collection = new TaskCollection(repo);

    collection.addTask("Go shopping");
    Task task = collection.getTasks().getFirst();
    task.setCompleted(true);

    Task savedTask = repo.getSavedTasks().getFirst();

    assertEquals(task.getCompleted(), savedTask.getCompleted(), "Completed status should be saved when modified");

    task.setTitle("Go to sleep");
    savedTask = repo.getSavedTasks().getFirst();
    assertEquals(task.getTitle(), savedTask.getTitle(), "Title should be saved when modified");

    task.setPriority(Priority.HIGH);
    savedTask = repo.getSavedTasks().getFirst();
    assertEquals(task.getPriority(), savedTask.getPriority(), "Priority should be saved when modified");

    task.setDescription("Sleeping is nice");
    savedTask = repo.getSavedTasks().getFirst();
    assertEquals(task.getDescription(), savedTask.getDescription(), "Description should be saved when modified");
}
```

Without our MVC architecture, we would be trying to call controller logic directly from `Main.java` and struggling to count the number of checkboxes inside `VBox` containers, all while trying not to corrupt the real `tasks.json` database!
Now we can focus entirely on testing the model layer, which after a small amount of setup becomes fast, reliable, and easy.

## Summary of I/O Abstractions

The greatest benefit of a well-designed architecture usually becomes visible first in testing.
The relationship can be viewed as:
```bob
UI (Controller)
        ↓
Business Logic (TaskCollection)
        ↓
Data Provider (TaskRepository)
```

Automated UI testing is difficult.
Automated testing of the real data provider (writing to disk) is typically slow and fragile.
However, isolated business logic, the actual heart of the application, can be executed as pure logic code in fractions of a second by using interface-based mock objects to replace surrounding complex systems during testing.
