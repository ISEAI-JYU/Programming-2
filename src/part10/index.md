# Project Assignment, Phase 2

Continue working on your project assignment by creating an interactive prototype for the application's user interface.
The prototype does not yet need to contain actual functionality, but it should provide an idea of what the application will look like.
It should also give a sense of how the user will interact with the application.

## Creating the Views

Create each view of your project assignment in SceneBuilder.

Add an `fx:id` to every component that you will likely want to reference later in code.
Do not forget possible instruction texts or warning messages.
Initially, these may contain placeholder text.

## Event Handlers

Add event handlers to every component that will later contain functionality, such as navigation between views, adding objects, deleting objects, and so on.
Typically, such components are buttons, drop-down menus, or similar controls.

Event-handler stubs can also be created directly in SceneBuilder.
Open the **Code** section and enter an event-handler name in the **On Action** field, for example
`handleLoginButton`.

## Naming Components and Event Handlers

It is common practice to append the component type to the end of an `fx:id`.
This makes coding easier because you can type, for example, `button`, and the IDE can suggest the correct component automatically.

| Component | Abbreviation / Suffix | Example (fx:id)               |
| --------- | --------------------- | ----------------------------- |
| Button    | btn or Butto          | `saveBtn`, `cancelButton`        |
| TextField | txt or Field          | `emailField`, `statusTxt`         |
| Label     | lbl or Label          | `notificationLabel`, `errorLbl`   |
| ComboBox  | combo                 | `countryCombo`                  |
| TableView | table                 | `userTable`, `taskTable`          |
| CheckBox  | cb or check           | `filterCheck`                   |

For event handlers, it is customary to use the prefixes `handle` or `process`.
For example, `handleNewPurchaseTransaction` could be the event handler associated with the `newPurchaseTransactionButton` button.

## Naming Controller Classes

It is a good idea to assign a controller class to each view already in SceneBuilder, even if the class does not yet exist.
The name is entered in:
`Controller → Controller class`.
The name should be chosen so that it matches the view name followed by the word `Controller`.
For example, for the view
`EnterTask.fxml`
an appropriate controller class would be:
`EnterTaskController`

> [!IMPORTANT]
>
> The controller class name must be entered together with its package name, for example
> `fi.jyu.ohj2.anlakane.todo.EnterTaskController`
> If you enter only the class name, such as
> `EnterTaskController`
> the IDE will not be able to find the class.

## Creating Controller Classes

Create a separate controller class for each view.
Tip: You can obtain a controller skeleton directly from SceneBuilder and copy-paste it into your project.
Select:
`View → Show Sample Controller Skeleton`
Fill in the appropriate types in place of the `?` placeholders.

## Navigating Between Views

Users must be able to move between views.
Write the necessary code in the event handlers so that navigation between views is possible.
All other interactive elements, such as buttons, should also perform some action, even if that action is simply printing a message to the console.
This gives you a good foundation upon which actual functionality can later be added.