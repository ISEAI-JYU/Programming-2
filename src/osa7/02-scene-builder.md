# SceneBuilder

SceneBuilder on visuaalinen työkalu, joka helpottaa
JavaFX-käyttöliittymienluomista. Se tarjoaa drag-and-drop-käyttöliittymän, jonka
avulla voitluoda ja muokata FXML-tiedostoja ilman, että sinun tarvitsee
kirjoittaakoodia FXML:ää käsin. SceneBuilderin avulla voit helposti lisätä
komponentteja, määrittää niiden ominaisuuksia ja järjestää ne haluamallasi
tavalla. Se on erityisen hyödyllinen, jos et ole vielä tottunut kirjoittamaan
FXML:ää suoraan tai haluat nopeuttaa käyttöliittymän suunnitteluprosessia.

## Ensimmäinen komponentti

Avataan nyt SceneBuilder. File -> Open. Avataan tekemämme projektin alta
`resources`-kansio ja sieltä FXML-tiedosto nimeltä `main.fxml`. 

Lisätään ensimmäienn tekestikenttä. 

Pohjalla on anchorpane, johon voi lisätä komponentteja ja määrittää itse niiden
sijainnin. 

Valitaan Controls -> TextField ja raahataan se ikkunaan. Tulee AnchorPanein
sisälle. Klikkaa Save. Näet IDEA:ssa, että FXML-tiedostoon on ilmestynyt uusi
elementti anchorpane-elementin sisälle children-elementtiin.

Käynnistä ohjelma. Voit kirjoittaa tekstikenttään. 

Lisätään painike jolla tekstin voi tallentaa. Controls -> Button. Vaihda
tekstiksi "Lisää" oikealta ylhtäältä. 

## FXML:n ja kontrolleriluokan yhdistäminen

Syöttökentälle Code -> ID -> fx:id "uusiTehtavaNimi". Painikkeelle fx:id
"lisaaUusiTehtavaPainike". 

Mennään IDEA -> MainController.java. Nämä ID:t on nyt määriteltävä
kontrolleriluokassa muuttujina, jotta niihin voidaan viitata koodissa.

Lisätään kontrolleriluokkaan seuraavat muuttujat:

```java,ignore
@FXML
private Button lisaaUusiTehtavaPainike;

@FXML
private TextField uusiTehtavaNimi;
```

Lisää puuttuvat import-lauseet `javafx.scene.control`-pakkauksesta.

Laitetaan `public class MainController implements Initializable`, jotta voidaan
toteuttaa `initialize()`-metodi, joka suoritetaan, kun FXML-tiedosto on ladattu
ja kontrolleriluokka on alustettu. Laitetaan `initialize()`-metodiin seuraava koodi:

```java,ignore
uusiTehtavaNimi.requestFocus();
```

Miksi ei muodostajaa? 

```java,ignore
    public MainController() {
        uusiTehtavaNimi.requestFocus();
    }
``` 

aiheuttaisi NullPointerExceptionin, koska FXML-tiedoston lataus ja
kontrolleriluokan alustus tapahtuvat erikseen, ja FXML-tiedoston lataus
tapahtuu ennen kuin kontrolleriluokan kentät on alustettu. Toisin sanoen FXML
hoitaa kontrolleriluokan kenttien "injektoinnin". 

Kokeillaan nyt että saamme kirjoittamamme tekstin ohjelmallisesti näkyviin. 

Laitetaan `initialize()`-metodiin seuraava koodi:

```java,ignore
lisaaUusiTehtavaPainike.setOnAction(event -> {
    String teksti = uusiTehtavaNimi.getText();
    System.out.println("Tekstikentän sisältö: " + teksti);
});
```

Nyt kun painiketta klikataan, konsoliin tulostuu tekstikentän sisältö.

## Tekstikentän sisällön näyttäminen ikkunassa

Komponenttien tapahtumien käsittely.

Laitetaan nyt tekstikentän sisältö näkyviin ikkunaan. Lisätään FXML-tiedostoon
Label-komponentti. Anna sille fx:id "todoLista". Tyhjennä Properties -> Text,
jotta se on tyhjä. Lisää kontrolleriin `@FXML private Label todoLista;`. 

Komponentille voidaan määritellä tapahtumankäsittelijöitä, jotka määrittävät,
mitä tapahtuu, kun käyttäjä vuorovaikuttaa komponentin kanssa. Esimerkiksi
painikkeelle voidaan määrittää `setOnAction`-tapahtumankäsittelijä, joka
suoritetaan, kun painiketta klikataan. 

Laita `MainController`-luokkaan `implements Initializable` ja toteuta
`initialize()`-metodi, joka suoritetaan, kun    FXML-tiedosto on ladattu ja
kontrolleriluokka on alustettu. `initialize()`-metodiin voidaan lisätä koodi,
joka määrittää tapahtumankäsittelijöitä komponentteille.

Lisätään tapahtumankäsittelijä `initialize()`-metodiin: 

```java,ignore
lisaaUusiTehtavaPainike.setOnAction(event -> {
    String teksti = uusiTehtavaNimi.getText();
    todoLista.setText(todoLista.getText() + teksti + "\n");
});
```

## VBOX ja komponenttien luominen dynaamisesti 

Laitetaan AnchorPanen sisään VBOX,. Korvataan Label VBOXilla. 

Checkboxeja

Niiden luominen dynaamisesti. 

## Toinen VBOX käsitellyille tehtäville

Siirretään käsitellyt tehtävät omaan VBOXiin. 

Palauttaminen käsitellystä tilasta käsittelemättömään tilaan.

## Tehtävien lukeminen ja kirjoittaminen tiedostoon

Kirjoittaminen JSON-tiedostoon ja lukeminen sieltä.

## Refaktorointia

## AnchorPane vs Gridpane

AnchorPane ei osaa skaalatua, joten vaihdetaan se GridPaneen, ja laitetaan sen
sisään asiat. Vaihda buttonin row ja column indexit Layout-kohdassa. 

Skaalauden säätäminen niin että ikkunan pienentäminen ja suurentaminen ei
totallisesti hajota layoutia. 
