Implement data deletion.
When deleting data, you must also handle any other objects that reference the deleted object.
For example, if you delete a `Category` object in the Expense Tracking application, the category must also be removed (set to `null` or `Optional.empty()`) from all `Event` objects that reference it.
It is also recommended to include a confirmation dialog when deleting data, for example by using the [`Alert`](https://code.makery.ch/blog/javafx-dialogs-official/) class, so that accidental clicks do not destroy data.