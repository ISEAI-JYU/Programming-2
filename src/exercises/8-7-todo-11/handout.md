Return the project refactored in Exercise 8.5.
Recap of the steps in this part:

- Add unit tests to the project.
- Separate task saving and loading into a dedicated class that implements the `TaskRepository` interface.
- Create a mock class in the test package that implements the `TaskRepository` interface but stores data only in memory.
- Test file saving and loading.

When the phase is complete, run `git add` for the modified files and create a `git commit`.
Return the `TaskRepository` interface as well as the `JsonTaskRepository`, `MockTaskRepository`, and `TaskCollectionTest` classes. No other classes or FXML files need to be returned.