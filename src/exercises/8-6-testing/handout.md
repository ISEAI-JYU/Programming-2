Write unit tests for the `TaskCollection` class. Test at least the following:

- When a task is added with a title that contains leading and trailing spaces, the spaces are removed before the task is stored in the list.
- When two tasks with the same title are added to the collection, both of them are actually stored in the list.
- Continuing from the previous case: when one of those tasks is marked as completed, only that specific task is marked as completed, not the other one.
- When two different tasks are added to the collection one after another, both are stored in the list in the correct order.

Return the `TaskCollectionTest` class.