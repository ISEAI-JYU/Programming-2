# Derived Observable Values

In a user interface, we often want to display not only the actual data but also values calculated from that data, such as sums, averages, combinations, or similar derived values.

For example, a person's full name can be calculated by combining the first name and last name:

```java
String fullName = person.getFirstName() + person.getLastName();
```

However, a value calculated this way does not automatically update in the user interface if the first name or last name changes.

## Example

Consider the following application for entering player information:

```java,ignore
// FILE: MainController.java
// Imports hidden
//- import javafx.collections.FXCollections;
//- import javafx.collections.ObservableList;
//- import javafx.fxml.FXML;
//- import javafx.fxml.Initializable;
//- import javafx.scene.control.Button;
//- import javafx.scene.control.Label;
//- import javafx.scene.control.TableColumn;
//- import javafx.scene.control.TableView;
//- import javafx.scene.control.cell.TextFieldTableCell;
//- import javafx.util.converter.NumberStringConverter;
//- 
//- import java.net.URL;
//- import java.util.ResourceBundle;
public class MainController implements Initializable {

    @FXML
    private TableColumn<Player, String> nameColumn;

    @FXML
    private TableColumn<Player, Number> birthYearColumn;

    @FXML
    private TableColumn<Player, Number> ageColumn;

    @FXML
    private TableView<Player> playersTable;

    @FXML
    private Label playerCountLabel;

    @FXML
    private Button addPlayerButton;

    private ObservableList<Player> players = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        birthYearColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter("####")));
        birthYearColumn.setCellValueFactory(cellData -> cellData.getValue().birthYearProperty());

        // This does not work!
        //
        // setCellValueFactory requires an ObservableValue,
        // but age is an int value.
        //
        // ageColumn.setCellValueFactory(cellData -> LocalDate.now().getYear() -  cellData.getValue().getBirthYear()
        // );

        addPlayerButton.setOnAction(event -> {
            Player newPlayer = new Player();
            newPlayer.setName("New Player");
            newPlayer.setBirthYear(2000);
            players.add(newPlayer);
        });

        playersTable.setItems(players);
    }
}

// FILE_END
// FILE: Player.java
// Imports hidden
//- import javafx.beans.property.IntegerProperty;
//- import javafx.beans.property.SimpleIntegerProperty;
//- import javafx.beans.property.SimpleStringProperty;
//- import javafx.beans.property.StringProperty;
public class Player {
    StringProperty name = new SimpleStringProperty();
    IntegerProperty birthYear = new SimpleIntegerProperty();

    public StringProperty nameProperty() { return name; }

    public String getName() { return name.get(); }

    public void setName(String name) { this.name.set(name); }

    public IntegerProperty birthYearProperty() { return birthYear; }

    public int getBirthYear() { return birthYear.get(); }

    public void setBirthYear(int birthYear) { this.birthYear.set(birthYear); }
}
// FILE_END
// FILE: main.fxml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TableColumn?>
<?import javafx.scene.control.TableView?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>

<VBox alignment="CENTER" spacing="20.0" xmlns="http://javafx.com/javafx/25" xmlns:fx="http://javafx.com/fxml/1" fx:controller="fi.ohj2.examples.bindingstest.MainController">
    <padding>
        <Insets bottom="20.0" left="20.0" right="20.0" top="20.0"/>
    </padding>
    <children>
        <TableView fx:id="playersTable" editable="true" prefHeight="200.0" prefWidth="200.0">
            <columns>
                <TableColumn fx:id="nameColumn" prefWidth="200.0" text="Name"/>
                <TableColumn fx:id="birthYearColumn" prefWidth="50.0" text="Birth Year"/>
                <TableColumn fx:id="ageColumn" editable="false" prefWidth="50.0" text="Age"/>
            </columns>
            <columnResizePolicy>
                <TableView fx:constant="CONSTRAINED_RESIZE_POLICY"/>
            </columnResizePolicy>
        </TableView>
        <HBox VBox.vgrow="NEVER">
            <children>
                <Label text="Players: "/>
                <Label fx:id="playerCountLabel" text="0"/>
            </children>
        </HBox>
        <Button fx:id="addPlayerButton" mnemonicParsing="false" text="Add Player"/>
    </children>
</VBox>
// FILE_END
```

In this example we can see two problems:

* Adding a new player does not update the player count.
* Editing a player's birth year does not update the player's age.

<video src="images/bindings-1.mp4" controls></video>

## Converting an Observable Value into Another Value

All `ObservableValue` types, such as `StringProperty`, `IntegerProperty`, `FloatProperty`, and so on, provide a helper method called `map()` (See 
[JavaDoc](https://download.java.net/java/GA/javafx25/docs/api/javafx.base/javafx/beans/value/ObservableValue.html#map(java.util.function.Function)))
that allows calculations to be performed on the value.

For example, a player's age can be calculated from the birth year as follows:

```java,ignore
ObservableValue<Number> age = player.birthYearProperty().map(year -> LocalDate.now().getYear() - year.intValue());
```

The value contained in the `age` variable is then recalculated using
`LocalDate.now().getYear() - year.intValue()`
whenever the player's birth year changes.
Because `ObservableValue` is itself observable, it can be used directly in a `setCellValueFactory` call:

```java,ignore
public void initialize(URL url, ResourceBundle resourceBundle) {
    nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

    birthYearColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter("####")));
    birthYearColumn.setCellValueFactory(cellData -> cellData.getValue().birthYearProperty());

    // HIGHLIGHT_GREEN_BEGIN
    ageColumn.setCellValueFactory(cellData ->
        cellData.getValue().birthYearProperty().map(
            birthYear -> LocalDate.now().getYear() - birthYear.intValue()));
    // HIGHLIGHT_GREEN_END

    addPlayerButton.setOnAction(event -> {
        Player newPlayer = new Player();
        newPlayer.setName("New Player");
        newPlayer.setBirthYear(2000);
        players.add(newPlayer);
    });

    playersTable.setItems(players);
}
```

## Converting a Function into an Observable Value

The number of players can be obtained by calling: `players.size()`.
However, the `size()` method does not return an observable value, and an `ObservableList` does not provide the `map()` method discussed above.
Nevertheless, any function can be converted into an observable value using the `Bindings`
(See [JavaDoc](https://download.java.net/java/GA/javafx25/docs/api/javafx.base/javafx/beans/binding/Bindings.html#))
class and its `createXBinding` helper methods.
Here, `X` refers to the type of observable value, for example `Integer`, `Long`, `String`, or `Object`.
Since `size()` returns an integer, we use `Bindings.createIntegerBinding()`
(See 
[JavaDoc](https://download.java.net/java/GA/javafx25/docs/api/javafx.base/javafx/beans/binding/Bindings.html#createIntegerBinding(java.util.concurrent.Callable,javafx.beans.Observable...)))
:

```java,ignore
IntegerBinding playerCount = Bindings.createIntegerBinding(() -> players.size(), players);
```

`Bindings.createIntegerBinding()` takes at least two parameters:
a lambda expression used to calculate the observable value and
one or more `Observable` values whose changes trigger recalculation.
In this example, the first parameter specifies that `playerCount` is always calculated using:
`players.size()`.
The second parameter, `players`, specifies that the value must be updated whenever the contents of the `players` list change.

The `Bindings.createXBinding` method returns a binding object, which can be used like any other observable value.
In this case, we can bind the text of `playerCountLabel` to it:

```java
public void initialize(URL url, ResourceBundle resourceBundle) {
    nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

    birthYearColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter("####")));
    birthYearColumn.setCellValueFactory(cellData -> cellData.getValue().birthYearProperty());

    ageColumn.setCellValueFactory(cellData -> cellData.getValue().birthYearProperty().map(birthYear -> LocalDate.now().getYear() - birthYear.intValue()));

    addPlayerButton.setOnAction(event -> {
        Player newPlayer = new Player();
        newPlayer.setName("New Player");
        newPlayer.setBirthYear(2000);
        players.add(newPlayer);
    });

    // HIGHLIGHT_GREEN_BEGIN
    IntegerBinding playerCount = Bindings.createIntegerBinding(() -> players.size(), players);
    playerCountLabel.textProperty().bind(playerCount.asString());
    // HIGHLIGHT_GREEN_END

    playersTable.setItems(players);
}
```

Using the `bind()` method of a `Property`, we can bind one value to another observable value.

In this case, the text of `playerCountLabel` is bound to the number of players.

As a result, whenever the `players` list changes:

* `playerCount` detects the change and recalculates its value using `players.size()`.
* `playerCount.asString()` detects the change in `playerCount` and updates its own value by calling `playerCount.toString()`.
* `playerCountLabel.textProperty()` detects the change in `playerCount.asString()` and updates its displayed text accordingly.

After these changes, both the player count and each player's age update automatically.

<video src="images/bindings-2.mp4" controls></video>