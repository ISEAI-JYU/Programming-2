package fi.jyu.ohj2.esimerkit.viitteidenkorjaaminen;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tools.jackson.databind.ObjectMapper;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    ObservableList<Kategoria> kategoriat = FXCollections
            .observableArrayList(k -> new Observable[] { 
                k.poistettuProperty(), k.nimiProperty() });

    ObservableList<Tehtava> tehtavat = FXCollections.observableArrayList(
            t -> new Observable[] { t.kategoriaProperty(), t.otsikkoProperty(), t.getKategoria().poistettuProperty() }
    );

    @FXML
    private ListView<Kategoria> kategoriatListView;

    @FXML
    private Button poistaKategoriaButton;

    @FXML
    private TableView<Tehtava> tehtavatTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Path tehtavatPolku = Path.of("tehtavat.json");
        Path kategoriatPolku = Path.of("kategoriat.json");

        ObjectMapper mapper = new ObjectMapper();
        try {
            Kategoria[] k = mapper.readValue(kategoriatPolku.toFile(), Kategoria[].class);
            Tehtava[] t = mapper.readValue(tehtavatPolku.toFile(), Tehtava[].class);
            
            this.kategoriat.setAll(k);

            // Asetetaan vielä tehtävien kategoria-viitteet oikeiksi kategoriat-olioiksi,
            // jotta suodatus ja muotoilu toimii
            for (Tehtava tehtava : t) {
                Kategoria oikeaKategoria = this.kategoriat.stream()
                        .filter(kategoria -> kategoria.getNimi().equals(tehtava.getKategoria().getNimi()))
                        .findFirst()
                        .orElse(tehtava.getKategoria());
                tehtava.setKategoria(oikeaKategoria);
            }
            this.tehtavat.setAll(t);

        } catch (Exception e) {
            e.printStackTrace();
        }

        TableColumn<Tehtava, String> otsikkoColumn = new TableColumn<>("Otsikko");
        otsikkoColumn.setCellValueFactory(cellData -> cellData.getValue().otsikkoProperty());
        TableColumn<Tehtava, String> kategoriaColumn = new TableColumn<>("Kategoria");
        kategoriaColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriaProperty().asString());
        kategoriaColumn.setCellFactory(cell -> new TableCell<Tehtava, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                IO.println("updateItem: " + item + ", empty: " + empty);
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    Tehtava tehtava = getTableRow().getItem();
                    if (tehtava != null && tehtava.getKategoria().isPoistettu()) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
        tehtavatTableView.getColumns().addAll(otsikkoColumn, kategoriaColumn);
        tehtavatTableView.setItems(tehtavat);

        kategoriatListView.setCellFactory(cell -> new ListCell<Kategoria>() {
            @Override
            protected void updateItem(Kategoria item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.getNimi());
                    if (item.isPoistettu()) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
        kategoriatListView.setItems(kategoriat);
    }

    @FXML
    void kasittelePoistaKategoria(ActionEvent event) {
        Kategoria valittu = kategoriatListView.getSelectionModel().getSelectedItem();
        if (valittu != null) {
            valittu.setPoistettu(true);
            // Päivitetään tehtävien kategoria-viitteet, jotta muotoilu päivittyy
            for (Tehtava tehtava : tehtavat) {
                if (tehtava.getKategoria().getNimi().equals(valittu.getNimi())) {
                    tehtava.setKategoria(valittu);
                }
            }
            // Päivitetään näkymät
            // kategoriatListView.refresh();
            // tehtavatTableView.refresh();
        }
    }
}
