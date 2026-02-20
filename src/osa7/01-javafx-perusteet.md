# JavaFX perusteet

> [!Osaamistavoitteet]
> - Ymmärrät JavaFX-sovelluksen rakenteen

Olemme tähän saakka tehneet komentorivisovelluksia ja lähinnä tulostaneet
tekstiä ruudulle. Graafinen käyttöliittymä (GUI) on kuitenkin monille ohjelmille
olennainen osa. Graafisen käyttöliittymän avulla käyttäjä näkee painikkeita,
valikoita ja kuvia sen sijaan, että hänen pitäisi opetella kirjoittamaan
komentoja oikeassa muodossa. 

Java-kielelle on useita kirjastoja graafisten käyttöliittymien toteuttamiseen,
mutta JavaFX on niistä ehkäpä nykyaikaisin ja monipuolisin. 

JavaFX käsittelee käyttöliittymää puumaisena rakenteena. Jokainen ikkunan osa
(painike, teksti, ryhmittelyelementti) on "solmu" (*Node*), joka kuuluu johonkin
suurempaan kokonaisuuteen. Tämä tekee monimutkaistenkin näkymien hallinnasta
loogista.

Ulkoasu ja logiikka erotetaan JavaFX:ssä toisistaan. Ulkoasun määritellään
käyttämällä **FXML**-kieltä, mikä on XML-pohjainen tiedostomuoto. Toiminnallisen
logiikka kirjoitetaan tavallisena Java-koodina. Tämä muistuttaa tapaa, jolla
web-kehityksessä erotetaan HTML (rakenne) ja JavaScript (toiminta).

JavaFX:ssä on oma toteutus CSS:stä (Cascading Style Sheets), joka tukee osaa CSS
2.1:n ominaisuuksista, ja joitain CSS 3:n ominaisuuksia. Tämän avulla
käyttöliittymäelementtien tyylittelyjä voidaan tietyssä määrin toteuttaa
web-kehityksestä tutulla tavalla. CSS-tuki on kuitenkin kohtalaisen rajallista,
eikä esimerkiksi float-, position- tai flexbox-ominaisuuksia tueta. Joihinkin
ominaisuuksiin löytyy joko JavaFX:n omat vastineensa, kuten flexboxin
tapauksessa VBox/HBox. Myös kehittäjäyhteisö tuottaa jatkuvasti avoimen
lähdekoodin kirjastoja, jotka tuovat joitain CSS:stä tuttuja ominaisuuksia
JavaFX:ään.

## Tutoriaali: TODO-sovellus

Osien 7 ja 8 aikana rakennamme yksinkertaisen TODO-sovelluksen. Tähän osioon
kuuluu tehtäviä, jossa opit tekemään saman sovelluksen omatoimisesti. Nämä osat
antavat sinulle tarvittavan ymmärryksen JavaFX:stä, jotta voit luoda oman
harjoitustyön osien 7-9 aikana. 

Osassa 7 teemme sovellukseen seuraavat ominaisuudet:

 * Käyttäjä voi lisätä uuden tehtävän
 * Käyttäjä näkee listan kaikista tehtävistä
 * Käyttäjä voi merkitä tehtävän tehdyksi
 * Käyttäjä voi poistaa tehtävän
 * Käyttäjä voi palauttaa tehdyn tehtävän takaisin tekemättömäksi
 * Tehtävät tallennetaan tiedostoon, jotta ne säilyvät sovelluksen sulkemisen jälkeen
 * Tehtävät haetaan tiedostosta sovelluksen käynnistyessä

Kuten aiemminkin, tämänkin osan tehtävistä täytyy tehdä vähintään 50%.
Erityisesti osissa 7 ja 8 kuitenkin suosittelemme tekemään kaikki tehtävät
jotta harjoitustyön tekeminen olisi helpompaa. Bonustehtävät jäävät kuitenkin
edelleen vapaavalintaisiksi. 

## Ensimmäinen JavaFX-sovellus

Tehdään nyt IDEAssa uusi JavaFX-projekti. Valitaan File <i class="bi
bi-chevron-right"></i> New <i class="bi bi-chevron-right"></i> Project. 

Maven-projekteja voidaan luoda tyhjänä, kuten aiemmin teimme, tai käyttäen
valmista pohjaa. Tällaista valmista pohjaa kutsutaan Maven-slangissa
*archetypeksi*. Pohja saattaa sisältää esimerkiksi valmiin rakenteen
projektille, koodia ja valmiiksi määritellyt riippuvuudet. Archetypeja on hyvin
monenlaisia erilaisiin käyttötarkoituksiin. Olemme julkaisseet tämän kurssin
tarpeisiin sopivan archetypen, joka sisältää käytännössä tyhjän
JavaFX-Maven-projektin, jossa on kaikki valmiina aloittamista varten. 

Valitaan vasemmalta Maven Archetype. Laita sovelluksen nimeksi Todo. Katso, että
olet valinnut oikean kansion, johon haluat projektin luoda. 

Valitse nyt Catalog-kohdassa Maven Central. Kirjoita Archetype-riville:
`io.github.ohj-perus-jy:javafx-fxml-template`. 

Nyt Version-kohtaan pitäisi nyt tulla automaattisesti viimeisin versio, joka on
kirjoitushetkellä 1.0.1. **Jos** Version-kenttä ei täyty automaattisesti,
kirjoita siihen manuaalisesti "1.0.1". Tämän jälkeen myös Additional Properties
-kentään ilmestyy automaattisesti "javaVersion: 25". 

Vaihda vielä Advaced Settingsin alta groupIID ja artifactID omiksesi. GroupId
voisi olla esimerkiksi `fi.jyu.ohj2.todo` ja ArtifactId `Todo`. Version-kohtaan
voit laittaa vaikkapa "0.1".

Hetken miettimisen jälkeen ruudun alareunassa Run-ikkunassa pitäisi lukea "BUILD
SUCCESS". 

IDEA ei luo oletusarvoisesti projektille ajokonfiguraatiota. Toisin sanoen, et
voi painaa suoraan Play-painiketta. Luo ajokonfiguraatio etsimällä
`src`-kansiosta `Main.java`-tiedosto. Klikkaa sitä hiiren oikealla ja valitse
Run.

Tämä käynnistää sovelluksen, jossa sinun pitäisi nyt nähdä yksi klikattava
painike. Lisäksi tämä luo ajokonfiguraation, ja voit painaa Play-painiketta
suoraan jatkossa.

## JavaFX-sovelluksen rakenne

JavaFX-sovellus koostuu yleensä kolmesta pääkomponentista: pääluokka, ulkoasu ja
kontrolleriluokka. 

**Pääluokka** on Java-luokka, joka toimii sovelluksen käynnistyspisteenä.
Esimerkissämme se on `App.java`, jota kutsutaan `Main.java`-tiedostossa olevasta
perinteisestä `main()`- pääohjelmasta. Pääluokka perii `Application`-luokan ja
määrittelee, miten sovellus luo ja näyttää ikkunan. Pääluokka on vastuussa
sovelluksen elinkaaren hallinnasta.

**Ulkoasu** määritellään niin kutsutussa FXML-tiedostossa, joka on XML-pohjainen
kuvaus käyttöliittymästä. Esimerkissämme se löytyy `resources`-kansiosta nimeltä
`main.fxml`. FXML-tiedosto määrittelee tekstimuodossa, millaisia komponentteja
ikkunassa on ja miten ne on järjestetty. FXML-tiedosto ei ole siis Java-koodia,
vaan erillinen tiedosto, joka kuvaa käyttöliittymän rakennetta. Pienessä
projektissa FXML-tiedostoja on yleensä vain yksi, mutta suuremmissa projekteissa
niitä voi olla useita. Jos esimerkiksi sovelluksella on useita eri näkymiä,
kuten päävalikko, asetukset ja itse pääkäyttöliittymä, jokaiselle näkymälle
voidaan luoda oma FXML-tiedosto.

**Kontrolleriluokka** on Java-luokka, joka sisältää logiikan käyttöliittymän
komponenttien käsittelyyn. Esimerkkiprojektissamme tämän nimi on
`MainController.java`. Kontrolleriluokassa määritellään miten sovellus reagoi
käyttäjän syötteisiin ja tapahtumiin. Kontrolleriluokka sisältää metodeja, jotka
on sidottu FXML-tiedoston komponentteihin, kuten painikkeisiin ja
tekstikenttiin. Nämä metodit määrittelevät, mitä tapahtuu, kun käyttäjä
vuorovaikuttaa käyttöliittymässä olevien komponenttien kanssa.
Kontrolleriluokkia on yleensä yksi per FXML-tiedosto, ja ne toimivat ikään kuin
välittäjinä FXML:n ja pääluokan välillä.

## TODO.... Tarvittaneenko tässä??? Stage, Scene ja Node

Stage on ikkunan pääkomponentti, joka sisältää kaikki muut komponentit. 

Scene on ikkunan sisältö, joka koostuu erilaisista graafisista elementeistä,
kuten painikkeista, tekstikentistä ja kuvista. Nämä elementit järjestetään
layout-pohjiin, kuten VBox tai HBox, jotka määrittävät niiden asettelun
ikkunassa.

Node on JavaFX:n peruskomponentti, joka toimii pohjana kaikille
graafisilleelementeille. Esimerkiksi Button, Label ja TextField ovat kaikki
Node-luokan aliluokkia.
