package fi.jyu.ohj2.esimerkit.viitteidenkorjaaminen;

import javafx.beans.binding.Bindings;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static final Kategoria TYHJA_KATEGORIA = new Kategoria("");

    private final ObservableList<Kategoria> kategoriat = FXCollections.observableArrayList(k-> 
        new Observable[] {
            k.nimiProperty(),
            k.poistettuProperty()
        }
    );

    private final ObservableList<Tehtava> tehtavat = FXCollections.observableArrayList();

    private Optional<Tehtava> valittuTehtava;
    private Optional<Kategoria> valittuKategoria;

    @FXML
    private ListView<Kategoria> kategoriatListView;

    @FXML
    private TableView<Tehtava> tehtavatTableView;

    @FXML
    private Button muokkaaKategoriaButton;

    @FXML
    private Button muokkaaTehtavaButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Path tehtavatPolku = Path.of("tehtavat.json");
        Path kategoriatPolku = Path.of("kategoriat.json");

        ObjectMapper mapper = new ObjectMapper();
        try {
            Kategoria[] k = mapper.readValue(kategoriatPolku.toFile(), Kategoria[].class);
            Tehtava[] t = mapper.readValue(tehtavatPolku.toFile(), Tehtava[].class);

            kategoriat.setAll(k);
            for (Tehtava tehtava : t) {
                Kategoria jsonistaLuettuKategoria = tehtava.getKategoria();
                tehtava.setKategoria(asetaKategoriaViite(jsonistaLuettuKategoria.getNimi()));
            }
            tehtavat.setAll(t);

        } catch (Exception e) {
            e.printStackTrace();
        }

        TableColumn<Tehtava, String> otsikkoColumn = new TableColumn<>("Otsikko");
        otsikkoColumn.setCellValueFactory(cellData -> cellData.getValue().otsikkoProperty());
        TableColumn<Tehtava, String> kategoriaColumn = new TableColumn<>("Kategoria");
        kategoriaColumn.setCellValueFactory(cellData ->
                Bindings.selectString(cellData.getValue().kategoriaProperty(), "nimi"));

        tehtavatTableView.setRowFactory(tv -> {
            TableRow<Tehtava> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    valittuTehtava = Optional.of(row.getItem());
                    muokkaaTehtavaButton.setDisable(false);
                } else {
                    valittuTehtava = Optional.empty();
                    muokkaaTehtavaButton.setDisable(true);
                }
            });
            return row;
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
        kategoriatListView.getSelectionModel().selectedItemProperty().addListener((obs, vanha, uusi) -> {
            valittuKategoria = Optional.ofNullable(uusi);
            muokkaaKategoriaButton.setDisable(uusi == null);
        });
        kategoriatListView.setItems(kategoriat);
        muokkaaTehtavaButton.setDisable(true);
    }

    @FXML
    void kasittelePoistaKategoria() {
        Kategoria valittu = kategoriatListView.getSelectionModel().getSelectedItem();
        if (valittu == null || valittu.isPoistettu()) {
            return;
        }

        valittu.setPoistettu(true);
        for (Tehtava tehtava : tehtavat) {
            if (tehtava.getKategoria() == valittu) {
                tehtava.setKategoria(TYHJA_KATEGORIA);
            }
        }
    }

    @FXML
    void kasitteleMuokkaaKategoria(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("muokkaaKategoria.fxml"));
            loader.setControllerFactory(_ -> new MuokkaaKategoriaController(valittuKategoria.get(), kategoriat));

            Parent root = loader.load();
            Stage muokkaaKategoriaDialogi = new Stage();
            muokkaaKategoriaDialogi.setScene(new Scene(root));
            muokkaaKategoriaDialogi.setTitle("Muokkaa kategoriaa");
            muokkaaKategoriaDialogi.initModality(Modality.APPLICATION_MODAL);
            muokkaaKategoriaDialogi.showAndWait();
            MuokkaaKategoriaController controller = loader.getController();
            if (controller.getKategoria().isPresent()) {
                Kategoria muokattuKategoria = controller.getKategoria().get();
                valittuKategoria.get().setNimi(muokattuKategoria.getNimi());
            }

        } catch (IOException ioe) {
            IO.println("Kategorian muokkaaminen epäonnistui: " + ioe.getMessage());
        }
    }

    @FXML
    void kasitteleMuokkaaTehtava(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("muokkaaTehtava.fxml"));
            loader.setControllerFactory(_ -> new MuokkaaTehtavaController(valittuTehtava.get(), kategoriat));

            Parent root = loader.load();
            Stage muokkaaTehtavaDialogi = new Stage();
            muokkaaTehtavaDialogi.setScene(new Scene(root));
            muokkaaTehtavaDialogi.setTitle("Muokkaa tehtävää");
            muokkaaTehtavaDialogi.initModality(Modality.APPLICATION_MODAL);
            muokkaaTehtavaDialogi.showAndWait();

            MuokkaaTehtavaController controller = loader.getController();
            if (controller.getTehtava().isPresent()) {
                valittuTehtava.get().setOtsikko(controller.getTehtava().get().getOtsikko());
                valittuTehtava.get().setKategoria(controller.getTehtava().get().getKategoria());
            }

        } catch (IOException ioe) {
            IO.println("Tehtävän muokkaaminen epäonnistui: " + ioe.getMessage());
        }
    }

    /**
     * Hakee ja palauttaa kategoriat-listalta Kategoria-olion, jolla on sama
     * nimi kuin JSONista luetulla Kategoria-oliolla, ja palauttaa sen. Jos
     * kategoriat-listalla ei ole Kategoria-oliota, jolla on sama nimi, tai jos
     * se on merkitty poistetuksi, palautetaan TYHJA_KATEGORIA.
     * 
     * @param nimi JSONista luetun Kategoria-olion nimi
     * @return kategoriat-listalta löytyvät Kategoria-olio. Jos ei löydy, tai se
     *         on merkitty poistetuksi, palautetaan TYHJA_KATEGORIA.
     */
    private Kategoria asetaKategoriaViite(String nimi) {

        for (Kategoria ehdokas : kategoriat) {
            if (ehdokas.getNimi().equals(nimi)) {
                if (!ehdokas.isPoistettu()) {
                    return ehdokas;
                }
                return TYHJA_KATEGORIA;
            }
        }
        return TYHJA_KATEGORIA;
        /*
         * return kategoriat.stream()
         * .filter(ehdokas -> ehdokas.getNimi().equals(kategoria.getNimi()))
         * .findFirst()
         * .filter(ehdokas -> !ehdokas.isPoistettu())
         * .orElse(TYHJA_KATEGORIA);
         */
    }
}
