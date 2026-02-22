# Tehtävien lukeminen ja kirjoittaminen tiedostoon

Tekemämme TODO-ohjelma on vielä melko väliaikainen, koska kaikki tehtävät
katoavat, kun suljet ohjelman. Ratkaistaan tämä tallentamalla tehtävät
tiedostoon. 

Käytetään JSON-muotoista tiedostoa tallentamiseen. Tiedoston rakenne voisi olla
esimerkiksi seuraavanlainen.

```json
{
  "tehtavat": [
    {
      "teksti": "Osta maitoa",
      "tehty": false
    },
    {
      "teksti": "Vie roskat",
      "tehty": true
    }
  ]
}
```

Lisätään riippuvuus Jackson-kirjastoon [osan
6.5](../osa6/05-tiedostojen-kasittely.md#jackson) ohjeen mukaisesti. 

Tarvitsemme luokan, joka kuvaa yksittäistä tehtävää. Luodaan `Tehtava`-luokka,
jossa on kaksi attribuuttia: `teksti` ja `tehty`. Tehdään niille myös getterit
ja setterit, jotta Jackson osaa käsitellä niitä. 

Aivan aluksi meidän pitäisi saada tuotettua lista tehtävistä. Luodaan
tehtävälista (`List<Tehtava>`) aivan `initialize()`-metodin loppuun. Tämä takaa,
että kaikki muutokset listaan on tehty. 

```java,ignore
public void initialize(...) {
    // ...
    
    // HIGHLIGHT_GREEN_BEGIN
    List<Tehtava> kaikkiTehtavat = new ArrayList<>();
    // HIGHLIGHT_GREEN_END
}
```

Tehtävät ovat nyt siis tallennettuina `tehdyt`- ja `tekemattomat`-VBoxeihin.
VBoxien lapsikomponenttien muuntaminen `Tehtava`-olioiksi vaatii hieman jumppaa.
VBoxin lapsikomponentit ovat tyyppiä `Node` &ndash; JavaFX:ssä kaikki
visuaaliset komponentit, myös `CheckBox`, periytyvät `Node`-luokasta. Vaikka
meidän tapauksessamme kaikki VBoximme lapset ovatkin `CheckBox`-komponentteja,
on hyvä silti tarkistaa olion todellinen tyyppi. Tämä tarkistus voidaan tehdä
`instanceof`-operaattorilla, jonka perään voidaan kirjoittaa muuttujan nimi
(tässä `c`), johon tarkistettu objekti sijoitetaan uuden tyypin kera.

```java,ignore
tekemattomat.getChildren().forEach(node -> {
    // Periaatteessa kaikki lapsikomponentit pitäisi 
    // olla CheckBoxeja, mutta varmuuden vuoksi tarkistetaan 
    // tämä kuitenkin. 
    if (!(node instanceof CheckBox c)) {
        return;
    }
    // Jos pääsemme tänne, niin node on CheckBox, 
    // ja voimme turvallisesti käyttää c-muuttujaa
    // CheckBox-tyyppisenä.

    String tekstiC = c.getText();
    boolean tehtyC = c.isSelected();
    Tehtava tehtava = new Tehtava(tekstiC, tehtyC);
    kaikkiTehtavat.add(tehtava);
});
```

Kokeillaan nyt pyytää Jacksonia kirjoittamaan tehtävät JSON-tiedostoon. Lisää
tämä aivan `initialize()`-metodin loppuun.

```java,ignore
public void initialize(...) {
    // ...
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(Path.of("tehtavat.json"), kaikkiTehtavat);
}
```

Katso IDEAssa, että projektikansioon ilmestyy `tehtavat.json`-tiedosto, kun
lisäät uuden tehtävän. 

Luonnollisesti myös tehdyt tehtävät tulee tallentaa. Jotta koodia ei tarvitse
toistaa, tehdään yllä olevasta koodista uusi metodi, `teeTehtavalista(VBox vbox)`, joka palauttaa VBox-parametrin lapsikomponenttien perusteella listan
`Tehtava`-olioita. 

Kutsutaan tätä sitten sekä `tekemattomat`- että `tehdyt`-VBoxeille.

```java,ignore

List<Tehtava> kaikkiTehtavat = new ArrayList<>();
// HIGHLIGHT_GREEN_BEGIN
kaikkiTehtavat.addAll(teeTehtavalista(tekemattomat));
kaikkiTehtavat.addAll(teeTehtavalista(tehdyt));
// HIGHLIGHT_GREEN_END
// HIGHLIGHT_RED_BEGIN
tekemattomat.getChildren().forEach(node -> {
    //...
});
// HIGHLIGHT_RED_END
ObjectMapper mapper = new ObjectMapper();
mapper.writeValue(Path.of("tehtavat.json"), kaikkiTehtavat);
```

> [!HUOMAUTUS]
> Tässä välissä on hyvä lisätä `.gitignore`-tiedostoon rivi `tehtavat.json`,
> koska tuota tiedostoa ei haluta versionhallintaan. Tallennuksen jälkeen tee
> komennot 
> 
> `git add .gitignore` ja \
> `git commit -m "Lisätty tehtavat.json .gitignoreen"`.
> 
> Jos lisäsit jo `tehtavat.json`-tiedoston versionhallintaan, poista se ensin
> komennolla `git rm --cached tehtavat.json`, tee commit, ja muuta vasta sen
> jälkeen `.gitignore`-tiedostoa

Nyt tallennus kyllä toimii, kun tehtävä lisätään. Tila täytyy kuitenkin
tallentaa myös silloin, kun tehtävä merkitään tehdyksi tai palautetaan
tekemättömäksi. Tämä kyllä onnistuu, jos kirjoitetaan sama tallennuskoodi
uudestaan `CheckBox`-komponentin `setOnAction`-tapahtumankäsittelijään,
mutta koodin toistaminen ei ole hyvä ratkaisu. 

Mietitäänpä siis hetki. Tallentaminen on selkeästi oma kokonaisuutensa, joka ei
liity suoraan siihen, miten tehtävät luodaan, näytetään tai siirretään. Tehdään
siitä oma metodi. 

```java,ignore
private void tallenna() {
    List<Tehtava> kaikkiTehtavat = new ArrayList<>();
    kaikkiTehtavat.addAll(teeTehtavalista(tekemattomat));
    kaikkiTehtavat.addAll(teeTehtavalista(tehdyt));
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(Path.of("tehtavat.json"), kaikkiTehtavat);
}
```

Nyt voimme kutsua tallennusta uuden `CheckBox`-komponentin
tapahtumankäsittelijässä, eikä koodia tarvitse toistaa. Siirretään samassa
rytäkässä myös uuden `CheckBox`-komponentin luominen erilliseen metodiin, jotta
`initialize()`-metodi pysyisi vähän selkeämpänä. 

Uuden `CheckBox`-komponentin luominen on selkeästi
oma kokonaisuutensa, joten erotetaan se omaksi metodikseen. 

```java,ignore
private CheckBox luoCheckBox(String teksti) {
    CheckBox checkbox = new CheckBox(teksti);
    checkbox.setOnAction(cbevent -> {
      // ... 
      tallenna();
    });
    return checkbox;
}
```

Jos katsot nyt ajon aikana `tehtavat.json`-tiedostoa, näet, että se päivittyy,
kun tehtävä lisätään, merkitään tehdyksi tai tehdään tekemättömäksi. Näiden
keskinäinen järjestys ei säily, mutta emme murehdi siitä tässä vaiheessa.
  
Nyt olemme saaneet tehtävät tallennettua, ne pitäisi myös lukea ohjelman
käynnistyessä. Tehdään sitä varten metodi `lueTehtavat()`. Käytetään tiedoston
lukemiseen tapaa, jonka opimme [osassa
6.5](../osa6/05-tiedostojen-kasittely.md#jackson), eli käytetään
`ObjectMapper`-luokan `readValue()`-metodia. Kääritään koko komeus `try-catch`-lohkon sisään, jotta mahdolliset poikkeukset saadaan kiinni.

```java,ignore
private void lueTehtavat() {
    ObjectMapper mapper = new ObjectMapper();
    Path path = Path.of("tehtavat.json");
    try {
        // 
        List<Tehtava> kaikkiTehtavat = mapper.readValue(path.toFile(),
                new TypeReference<java.util.List<Tehtava>>() {});
        kaikkiTehtavat.forEach(tehtava -> {
            CheckBox checkbox = luoCheckBox(tehtava.getTeksti());
            if (tehtava.isTehty()) {
                tehdyt.getChildren().add(checkbox);
            } else {
                tekemattomat.getChildren().add(checkbox);
            }
        });
    } catch (JacksonException je) {
        IO.println("JSONin lukeminen epäonnistui: " + je.getMessage());
    }
}
```

Lisätään kutsu `initialize()`-metodiin.

```java,ignore
public void initialize(...) {
    lueTehtavat();
    // ...
```

Lukemisessa, tai oikeastaan checkboxien luomisessa, on pieni ongelma.
`luoCheckBox()`-metodi ei ota huomioon sitä, onko tehtävä tehty vai ei, vaan luo
aina tekemättömän tehtävän. Korjataan tämä lisäämällä metodille toinen
parametri.

```java,ignore
private CheckBox luoCheckBox(String teksti, boolean valittu) {
    CheckBox checkbox = new CheckBox(teksti);
    if (valittu) checkbox.setSelected(true);
    // ...
}
```

Nyt voimme lisätä metodin kutsuun mukaan checkboxin oikean tilan. 

```java,ignore
// ...
kaikkiTehtavat.forEach(tehtava -> {
    // HIGHLIGHT_GREEN_BEGIN
    CheckBox checkbox;
    // HIGHLIGHT_GREEN_END
    if (tehtava.isTehty()) {
    // HIGHLIGHT_GREEN_BEGIN
        checkbox = luoCheckBox(tehtava.getTeksti(), true);
    // HIGHLIGHT_GREEN_END
        tehdyt.getChildren().add(checkbox);
    } else {
    // HIGHLIGHT_GREEN_BEGIN
        checkbox = luoCheckBox(tehtava.getTeksti(), false);
    // HIGHLIGHT_GREEN_END
        tekemattomat.getChildren().add(checkbox);
    }
});
// ...
```

<task>
  <task-title>Tehtävä 7.5: TODO-ohjelma, vaihe 5. <points>1 p.</points> </task-title>
  <handout>

{{#include ../exercises/7-5-todo-5/handout.md}}

  </handout>
  <task-link><a href="https://tim.jyu.fi/view/kurssit/tie/tiep111/tehtavat/osa7/tehtava5">Tee tehtävä TIMissä</a></task-link>
</task>
