Implement a class called `Book` that keeps track of individual books while also maintaining global library statistics.
Create the following variables for the class:

Instance variables:
- `String title`: The title of the book.
- `String author`: The author of the book.
- `boolean isBorrowed`: Indicates whether the book is currently on loan.

Class variables (`static`):
- `static int totalBooks`: The total number of books that have been created.
- `static int borrowedBooks`: The number of books that are currently on loan.

The constructor should take the title and author as parameters. Whenever a new book is created, the `totalBooks` variable should be increased by one.

Create the instance methods `borrow()` and `returnBook()`. These methods change the book's `isBorrowed` status and update the static `borrowedBooks` counter.

Create a static method `printStatistics()` that prints the library statistics in the following format:
"Library contains X books, of which Y are currently on loan."

A main program is provided that you can use to test your implementation.