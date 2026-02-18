# SceneBuilder

> [!Osaamistavoitteet]
> - Osaat käyttää SceneBuilder-työkalua JavaFX-käyttöliittymien luomiseen
> - Osaat yhdistää FXML-tiedoston ja kontrolleriluokan

SceneBuilder on visuaalinen työkalu, joka helpottaa JavaFX-käyttöliittymien
luomista. Se tarjoaa drag-and-drop-käyttöliittymän, jonka avulla voit luoda ja
muokata FXML-tiedostossa määriteltyä käyttöliittymää ilman FXML:n kirjoittamista
käsin. SceneBuilderin avulla voit helposti lisätä komponentteja, määrittää
niiden ominaisuuksia ja järjestää ne haluamallasi tavalla. Se on erityisen
hyödyllinen, jos et ole vielä tottunut kirjoittamaan FXML:ää suoraan tai haluat
nopeuttaa käyttöliittymän suunnitteluprosessia.

## Ensimmäinen komponentti

Avataan nyt projektimme SceneBuilderissä. Valitaan File <i class="bi
bi-chevron-right"></i> Open. Avataan tekemämme projektin alta `resources`-kansio
ja sieltä FXML-tiedosto nimeltä `main.fxml`. Nyt sama käyttöliittymä, jonka
näimme IDEAssa, pitäisi näkyä SceneBuilderissä.

TODO: Kuva.

Pohjassa on valmiina painike, eli `Button`-komponentti sekä tekstikenttä eli
`Label`-komponentti. Jos napsautat painiketta, näet oikealla
Properties-paneelissa kyseisen komponentin ominaisuuksia. Voit muuttaa
esimerkiksi painikkeen tekstiä, fonttia, väriä ja monia muita ominaisuuksia. 

## Käyttöliittymän hierarkkinen rakenne

Vasemmalla olevassa Document-paneelissa näet käyttöliittymän rakenteen, joka
muistuttaa hierarkiaa: `Button` ja `Label`-komponentit ovat `VBox`-komponentin
lapsia. `VBox` (Vertical Box, eli "pystysuuntainen laatikko") on
layout-komponentti, joka järjestää lapsikomponenttinsa pystysuoraan. Tämän
komponentin sisään voidaan siis laittaa muita komponentteja, ja se järjestää ne
automaattisesti pystysuoraan. 

JavaFX:ssä on paljon vastaavia valmiita `Pane`-luokasta periytyviä luokkia,
jotka auttavat järjestelemään käyttöliittymää kokonaisuuksiin sen sijaan, että
kaikki komponentit olisivat yhdessä läjässä suoraan ikkunan alaisuudessa. 

 * `HBox`-komponentti järjestää lapsensa vaakasuoraan,
 * `GridPane`-komponentti järjestää lapsensa ruudukkomaisesti,
 * `BorderPane`-komponentti järjestää lapsensa reunoille ja keskelle, ja niin
edelleen. 

Näiden komponenttien avulla voidaan luoda monimutkaisiakin
käyttöliittymiä, jotka skaalautuvat hyvin eri kokoisiksi ikkunoiksi.

## Syöttökenttä

Lisätään nyt käyttöliittymään tekstikenttä, johon käyttäjä voi kirjoittaa.
Valitaan vasemmalta Controls <i class="bi
bi-chevron-right"></i> TextField ja raahataan se VBox-komponentin sisään,
aiemman tekstikentän ja painikkeen väliin. Tuloksen pitäisi nyt näyttää tältä:

![alt text](images/scenebuilder-1.png)

Klikkaa nyt Save. Näet IDEA:ssa, että FXML-tiedostoon on ilmestynyt uusi
`<TextField>`-elementti `<Label>`- ja `<Button>`-elementtien väliin.

Käynnistä nyt ohjelma. Nyt sinulla pitäisi olla ikkunassa tekstikenttä, johon
voit kirjoittaa. Painikkeesta ei vielä tapahdu mitään, lisätään se seuraavaksi. 

Tehtävä 7.1: Tee `io.github.ohj-perus-jy:javafx-fxml-template`-archetypen
pohjalta JavaFX-projekti. Lisää SceneBuilderissa FXML-tiedostoon
TextField-komponentti. Käynnistä ohjelma, ja varmista, että näet ikkunassa
tekstikentän, johon voit kirjoittaa.

## FXML:n ja kontrolleriluokan yhdistäminen

Jotta voimme käsitellä käyttöliittymän tapahtumia, kuten painikkeen klikkaus,
meidän on luotava yhteys FXML-tiedoston ja kontrolleriluokan välille. Tämä
tapahtuu kahdessa vaiheessa: antamalla komponenteille tunnisteet
SceneBuilderissä ja määrittelemällä vastaavat muuttujat Java-koodissa.

**Tunnisteiden määrittäminen komponenteille**

 * Jokaisella komponentilla, jota haluamme ohjata koodista käsin, täytyy olla
   yksilöllinen tunniste, niin sanottu *fx:id*.
 * Avaa projekti SceneBuilderissä ja valitse haluamasi komponentti, esimerkiksi `TextField`.
 * Avaa oikeasta reunasta Code-paneeli.
 * Kirjoita fx:id-kenttään komponentin nimi, esimerkiksi `uusiTehtavaNimi`.
 * Toista sama painikkeelle ja anna sille fx:id `lisaaUusiTehtavaPainike`.
 * Tallenna, jotta muutokset päivittyvät FXML-tiedostoon.

**Muuttujien injektointi (@FXML)**

 * Palaa IDEAan. Jotta Java-koodi löytää nämä komponentit, ne on ilmoitettava
   MainController-luokassa käyttämällä @FXML-annotaatiota.
 * Lisää luokkaan seuraavat attribuutit:

```java,ignore
@FXML
private Button lisaaUusiTehtavaPainike;

@FXML
private TextField uusiTehtavaNimi;
```

> [!IMPORTANT]
> Muuttujan nimen on oltava täsmälleen sama kuin SceneBuilderissä määritellyn
> fx:id-arvon. Muuten JavaFX ei osaa yhdistää niitä.

 * Lisää puuttuvat import-lauseet `javafx.scene.control`-pakkauksesta, *ei*
`java.awt`-pakkauksesta.

**Miten injektointi toimii?** Kun ohjelma käynnistyy, `FXMLLoader`-luokka
(alustettu `App.java`-luokassa) lukee FXML-tiedoston ja huomaa siellä
määritellyt fx:id:t. Tämän jälkeen se:

 * luo instanssin kontrolleriluokasta (esim. `MainController`),
 * etsii kontrollerista `@FXML`-annotaatiolla merkityt kentät,
 * injektoi eli asettaa viittaukset käyttöliittymän komponentteihin näihin muuttujiin.

Käytännössä JavaFX tekee puolestasi työn, joka vastaisi koodia:
this.uusiTehtavaNimi = (TextField) findComponentById("uusiTehtavaNimi");.

Kokeile kääntää ja ajaa ohjelma uudestaan. Ohjelman pitäisi toimia kuten
ennenkin.

## Kontrollerin elinkaari ja initialize-metodi

Yksi yleisimmistä virheistä on yrittää käyttää käyttöliittymäkomponentteja
suoraan kontrollerin konstruktorissa. Tämä johtaa
`NullPointerException`-virheeseen, koska injektointi tapahtuu vasta
konstruktorin ajamisen jälkeen.

Kontrolleriluokan on hyvä toteuttaa `Initializable`-rajapinta, jolloin
initialize()-metodi kutsutaan automaattisesti, kun FXML-tiedosto on ladattu ja
kontrolleriluokka on alustettu. Tämä on oikea paikka määritellä
tapahtumankäsittelijöitä ja tehdä muita alustuksia, jotka vaativat pääsyä
FXML-komponentteihin.

Vaihtoehtoisesti voitaisiin määritellä `@FXML public void initialize()`-metodi.
Tekemässämme archetypessä kontrolleriluokka toteuttaa
`Initializable`-rajapinnan, joten käytämme `initialize()`-metodia.

Lisätään nyt `initialize()`-metodiin seuraava rivi, joka asettaa tekstikentän
fokukseen heti ohjelman käynnistyessä.

```java,ignore
uusiTehtavaNimi.requestFocus();
```

Testaa ohjelma uudestaan. 

## setOnAction

Lisätään seuraavaksi tapahtumankäsittelijä painikkeelle, joka 

 * lukee tekstikentän sisällön ja
 * tulostaa sen konsoliin.

Monien komponenttien, kuten painikkeiden, tekstikenttien ja valintaruutujen,
tapahtumia voidaan käsitellä `setOnAction`-metodilla. Tämä tapahtuma laukeaa,
kun käyttäjä vuorovaikuttaa komponentin kanssa tietyllä tavalla, kuten
klikkaamalla painiketta tai painamalla Enter-näppäintä tekstikentässä. Tällöin
komponentti tuottaa `ActionEvent`-tapahtuman. Painikkeen kohdalla tämä tarkoittaa
käytännössä sitä, että koodi ajetaan painiketta klikattaessa (tai kun painike
aktivoidaan näppäimistöllä). Suoritettava koodi voidaan määritellä
lambda-lausekkeena tai erillisenä metodina. 

Määritellään nyt `lisaaUusiTehtavaPainike`-painikkeelle tapahtumankäsittelijä
`initialize()`-metodissa lambda-lausekkeena:

```java,ignore
lisaaUusiTehtavaPainike.setOnAction(event -> {
    String teksti = uusiTehtavaNimi.getText(); // Haetaan tekstikentän sisältö
    IO.println("Tekstikentän sisältö: " + teksti); // Tulostetaan se konsoliin
});
```

<details><summary>Lisätietoa: Miksi emme lisää tapahtumankäsittelijää suoraan konstruktorissa?</summary>

Syy liittyy JavaFX:n alustuksen järjestykseen. Järjestys on tämä:

1. JavaFX luo kontrollerin konstruktorin (`new MainController()`).
2. `@FXML`-kentät ovat tässä vaiheessa vielä `null`.
3. `FXMLLoader` lukee FXML-tiedoston ja injektoi kenttiin oikeat komponentit
   `fx:id`-arvojen perusteella.
4. Lopuksi kutsutaan `initialize()`-metodi.

Jos siis kirjoitat konstruktoriin koodia, joka käyttää FXML-komponentteja, saat
helposti `NullPointerException`in.

```java,ignore
public MainController() {
    lisaaUusiTehtavaPainike.setOnAction(event -> {
        String teksti = uusiTehtavaNimi.getText();
        IO.println(teksti);
    });
}
```

Yllä olevassa esimerkissä sekä `lisaaUusiTehtavaPainike` että `uusiTehtavaNimi`
ovat konstruktorin aikana vielä `null`.

Siksi tapahtumankäsittelijät ja muut FXML-komponentteihin nojaavat alustukset
tehdään `initialize()`-metodissa.

</details>

Nyt kun painiketta klikataan, konsoliin tulostuu tekstikentän sisältö.

## Tekstikentän sisällön näyttäminen ikkunassa

Lisätään nyt tekstikenttään kirjoitettu sisältö näkyviin yläpuolella olevaan
`Label`-komponenttiin. Anna sille fx:id "tekemattomat", joka kuvastaisi
tehtäviä, jotka eivät ole vielä tehty. Tyhjennä Properties <i class="bi
bi-chevron-right"></i> Text, jotta se on aluksi tyhjä. 

Tallenna. Lisää kontrolleriluokkaan `@FXML private Label tekemattomat;`.

Muokkaa aiemmin tekemääsi tapahtumankäsittelijää: 

```java,ignore
lisaaUusiTehtavaPainike.setOnAction(event -> {
    String teksti = uusiTehtavaNimi.getText();
    // HIGHLIGHT_RED_BEGIN
    IO.println(teksti);
    // HIGHLIGHT_RED_END
    //HIGHLIGHT_GREEN_BEGIN
    tekemattomat.setText(tekemattomat.getText() + teksti + "\n");
    //HIGHLIGHT_GREEN_END
});
```

Nyt kirjoittamasi teksti näkyy ikkunassa, ja voit lisätä uusia rivejä
painikkeella. Jos suurennat ikkunaa, näet, että tekstirivejä lisätään aina
edellisen tekstin perään. 

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
