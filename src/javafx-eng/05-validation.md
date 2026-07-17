# Data Validation

Data validation refers to checking the correctness of information entered by the user.
Validation ensures that user changes do not produce a data model that is invalid from the perspective of the application domain.

## Example

Suppose we have the following simple application:

```java,ignore
// FILE: MainController.java
public class MainController implements Initializable {
    @FXML
    private ListView<Pet> petsList;

    @FXML
    private TextField nameField;

    @FXML
    private TextField speciesField;

    @FXML
    private Button addButton;

    ObservableList<Pet> pets = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        petsList.setItems(pets);
        addButton.setOnAction(_ -> addPet());
    }

    private void addPet() {
        Pet pet = new Pet();
        pet.setName(nameField.getText());
        pet.setSpecies(speciesField.getText());
        pets.add(pet);

        nameField.clear();
        speciesField.clear();
    }
}

// FILE_END
// FILE: Pet.java

public class Pet {
    StringProperty name = new SimpleStringProperty();
    StringProperty species = new SimpleStringProperty();

    public StringProperty speciesProperty() { return species; }
    public String getSpecies() { return species.get(); }
    public void setSpecies(String species) { this.species.set(species); }

    public StringProperty nameProperty() { return name; }
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }

    @Override
    public String toString() {
        return getName() + " (" + getSpecies() + ")";
    }
}

// FILE_END
// FILE: main.fxml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.VBox?>

<VBox alignment="CENTER" spacing="20.0" xmlns="http://javafx.com/javafx/25" xmlns:fx="http://javafx.com/fxml/1" fx:controller="fi.jyu.ohj2.example.flashcard.MainController">
    <padding>
        <Insets bottom="20.0" left="20.0" right="20.0" top="20.0"/>
    </padding>
    <children>
        <ListView fx:id="petsList" prefHeight="200.0" prefWidth="200.0"/>
        <TextField fx:id="nameField" promptText="Name"/>
        <TextField fx:id="speciesField" promptText="Species"/>
        <Button fx:id="addButton" mnemonicParsing="false" text="Add"/>
    </children>
</VBox>
// FILE_END 
```

The application contains a list to which pets can be added, represented by the `Pet` class.

At the moment, the user can add pets without a name or a species, which is not a good idea.

We should prevent users from adding pets that have no name or no species.

## Simple Validation

The simplest form of validation can be implemented by checking the domain requirements before creating the model object.
For example, we could check inside the `addPet()` method whether the name and species fields contain text:

```java,ignore
private void addPet() {
    // TODO: Perform validation here.
    // Add the pet only if the information is valid.
    Pet pet = new Pet();
    pet.setName(name);
    pet.setSpecies(species);
    pets.add(pet);

    nameField.clear();
    speciesField.clear();
}
```

## Validation Method

Validation is often easy to implement as a separate method in the controller:

```java,ignore
/**
 * Checks whether the contents of the pet fields are valid.
 * If not, highlights the invalid fields.
 *
 * @return true if validation succeeds, otherwise false
 */
private boolean validatePet() {
    // Reset field styles
    nameField.setStyle("");
    speciesField.setStyle("");

    // Retrieve field contents
    String name = nameField.getText();
    String species = speciesField.getText();

    // Validation: Name must not be empty
    if (name.isBlank()) {
        // Highlight the invalid field
        nameField.setStyle("-fx-border-color: red; " + "-fx-background-color: #ffcccc;");
        return false;
    }

    // Validation: Species must not be empty
    if (species.isBlank()) {
        speciesField.setStyle("-fx-border-color: red; " + "-fx-background-color: #ffcccc;");
        return false;
    }

    // true = validation succeeded
    return true;
}

private void addPet() {
    // Validate before adding
    if (!validatePet()) {
        return;
    }

    Pet pet = new Pet();

    pet.setName(nameField.getText());
    pet.setSpecies(speciesField.getText());

    pets.add(pet);

    nameField.clear();
    speciesField.clear();
}
```

## Moving Validation into the Data Model

From the perspective of testing and separation of responsibilities, it is often clearer if validation is part of the data model itself.
In that case, validation can be implemented directly within the model class.
In this example, `validateErrors` is part of the `Pet` class.
At the same time, let's create an enumeration type `ValidationError` that represents possible error situations.

```java,ignore
public enum ValidationError {
    NAME_EMPTY,
    SPECIES_EMPTY
}
public class Pet {
    // ...

    // Actual validation method
    public ValidationError validateErrors() {
        if (getName().isBlank()) {
            return ValidationError.NAME_EMPTY;
        }
        if (getSpecies().isBlank()) {
            return ValidationError.SPECIES_EMPTY;
        }
        return null;
    }
}
```

Adding a pet still happens inside `addPet()`, but now validation of the model object is performed directly through the model class:

```java,ignore
private void addPet() {
    nameField.setStyle("");
    speciesField.setStyle("");

    // Create the model object and assign values
    Pet pet = new Pet();

    pet.setName(nameField.getText());
    pet.setSpecies(speciesField.getText());

    // Check whether the model object is valid
    ValidationError errorResult = pet.validateErrors();

    // If validation fails, display
    // an appropriate error indication
    if (errorResult != null) {
        if (errorResult == ValidationError.NAME_EMPTY) {
            nameField.setStyle("-fx-border-color: red; " + "-fx-background-color: #ffcccc;");
        }
        if (errorResult == ValidationError.SPECIES_EMPTY) {
            speciesField.setStyle("-fx-border-color: red; " + "-fx-background-color: #ffcccc;");
        }
        return;
    }

    // Add the pet only if it is valid
    pets.add(pet);

    nameField.clear();
    speciesField.clear();
}
```

Instead of relying on `null` checks, it is often much clearer to use the `Optional` type, which explicitly represents a situation where an error may or may not exist.

```java,ignore
public Optional<ValidationError> validateErrors() {
    if (getName().isBlank()) {
        return Optional.of(ValidationError.NAME_EMPTY);
    }
    if (getSpecies().isBlank()) {
        return Optional.of(ValidationError.SPECIES_EMPTY);
    }
    return Optional.empty();
}
```

Whereas `null` is an invisible contract that requires separate documentation (which, unfortunately, is often never written), the `Optional` type makes the intent explicit.

The validation code then becomes cleaner:

```java,ignore
private void addPet() {
    // ...

    Optional<ValidationError> errorResult = pet.validateErrors();
    // If a validation error exists,
    // display the corresponding feedback

    if (errorResult.isPresent()) {
        ValidationError error = errorResult.get();
        if (error == ValidationError.NAME_EMPTY) {
            nameField.setStyle("-fx-border-color: red; " + "-fx-background-color: #ffcccc;");
        }
        if (error == ValidationError.SPECIES_EMPTY) {
            speciesField.setStyle("-fx-border-color: red; " + "-fx-background-color: #ffcccc;");
        }
        return;
    }
    // ...
}
```
