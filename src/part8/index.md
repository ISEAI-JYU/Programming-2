# JavaFX Part 2: MVC

### Learning Objectives

* Be able to separate data, application logic, and the user interface in a JavaFX project.
* Be able to use types based on JavaFX's `Observable` interface so that the user interface updates automatically when data changes.
* Be able to display task data in a `TableView` component and utilize data binding.
* Be able to create a task-editing view that includes validation and priority information.
* Be able to write unit tests for the Todo application's model and business logic.
* Submit the second phase of the tutorial to TIM.

In Part 7, we created a working Todo application in which tasks were modeled largely using user-interface components (`CheckBox`) and stored in a JSON file.

The solution was a good starting point, but in the long term it makes the application rigid.
For example, if we wanted to add new features to tasks, such as a longer description or a priority level, we would need to introduce new components such as a `TaskCheckBox` component.
Furthermore, if we wanted to edit task data through a form, we would have to create a separate `TaskForm` component.
At that point, it would become unclear which representation is the "real" source of truth: should data always be copied from `TaskCheckBox` objects to `TaskForm` objects, or the other way around?

Following object-oriented design principles, *the core functionality of the problem domain and its presentation to the user are two separate responsibilities*.
The task data and task management functionality of the Todo application should be modeled as their own separate entity.
The sole responsibility of the user interface should be to present the domain data.
Checkboxes are not tasks themselves—they are merely one way of displaying tasks.

In this part, we will continue developing the Todo application from Part 7.
Our focus now shifts to separating the user interface from the application's core functionality.
Finally, we will see how this separation makes it possible to verify the correctness of the application's business logic through automated tests.

JavaFX provides tools that help keep the user interface and application logic *loosely coupled*.

In this part we will:

* Move all task data into the `Task` class and model tasks using an `ObservableList`.
* Use a `TableView` to display task data.
* Organize the application's classes according to the MVC architecture into three separate areas of responsibility.
* Add a task-editing window in which the description, priority, and due date can be modified.
* Verify the correctness of the solution using unit tests.
* Publish the finished project using a GitLab or GitHub remote repository service.
