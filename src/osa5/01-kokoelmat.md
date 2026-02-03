# Kokoelmat

> [!VAROITUS]
> Tämä osio julkaistaan 9. helmikuuta 2026.
> {{#include ../ei-julkaistu.md}}

> [!Osaamistavoitteet]
>
> - Ymmärrät, mitä kokoelma tarkoittaa ja miksi niitä käytetään ohjelmoinnissa.
> - Ymmärrät Javan kokoelmakehyksen (*Collections Framework*)
>   perusrakenteen.
> - Tunnet Javan `Collection`-rajapinnan metodit ja osaat käyttää ne ohjelmassa.

Tiedon koostaminen kokonaisuuksiin on hyvin yleinen osa ohjelmointia.
Esimerkiksi osassa 2 ja 3 käsitelty olio-ohjelmointi tarjoaa tavan koostaa
samaan kohteeseen liittyviä tietoja attribuutteina ja määrittää tapoja käsitellä
tätä tietoa. Kun taas samantyyppisiä arvoja, kuten päivän lämpötiloja tai
luennolla olevia opiskelijoita, olemme koostaneet taulukoihin tai
listohin.

Taulukon ja listan erot ovat pintapuolisesti pienet. Kumpaankin voi tallentaa
samantyyppisiä arvoja ja arvoja voidaan hakea indeksin perusteella, mutta
taulukkoihin ei voi lisätä uusia alkioita, kun taas listaan voi. Oikeassa
maailmassa on kuitenkin tapauksia, jossa arvojen käsittelyyn liittyy jokin
erikoisrajoite. Javassa **kokoelma** (engl. *collection*) on olio, joka koostaa
useita samantyyppisiä arvoja samaan yksikköön ja mallintaa tapoja, jolla näitä
arvoja voi käsitellä. Tietorakennetta valitessaan ohjelmoija valitsee paitsi
tavan, jolla tiedot tallennetaan, myös sen, miten tietoja voi käsitellä.

Alla muutamia esimerkkejä erilaisista kokoelmista ja niiden erityispiirteistä.

 - Soittaessasi asiakaspalveluun uudet soittajat lisätään käsittelyjonon
   loppuun, ja soittajia palvellaan aina jonon alusta lähtien. Rakennetta, jossa
   uudet alkiot lisätään loppuun ja poistetaan alusta, kutsutaan *jonoksi*
   (engl. *queue*).
 - Sisuun viedään arvosanat aina pareina (opiskelijanumero, arvosana) **ja**
   jokaista opiskelijaa tulee vastata korkeintaan yksi arvosana samassa
   toteutuksessa. Rakennetta, jossa jokaista yksikäsitteistä avainta
   (esimerkiksi opiskelijanumero) vastaa täsmälleen yksi arvo (esimerkiksi
    arvosana), kutsutaan *hakurakenteeksi* (engl. *map*).
 - Discord-viestipalvelussa käyttäjällä voi olla useita ystäviä, mutta samaa
   käyttäjää **ei** voi lisätä ystäväkseen kahdesti. Rakennetta, johon ei voi
   lisätä samaa arvoa kahdesti, kutsutaan *joukoksi* (engl. *set*).

Vaikka yllä olevat tapaukset voi kyllä periaatteessa toteuttamaan jo
oppimillamme taulukko- ja listarakenteilla, lisäehtojen toteuttaminen vaatisi
ylimääräistä teknistä työtä. Ohjelman suunnittelua ja toteutusta usein
helpottaa, jos tietorakenne noudattelee kohdealueen rakenteita, logiikkaa ja
tarpeita. 

Javassa useita erilaisia toteutuksia kokoelmille, joilla on erilaisia
ominaisuuksia ja käyttötarkoituksia. Tutustumme kokoelmatyyppeihin ja niiden
tavallisimpiin toteutuksiin tarkemmin tässä osassa. 

## Kokoelmakehys (Java Collections Framework)

Java tarjoaa valtavan joukon valmiita kokoelmia sekä rajapintoja uusien kokoelmien
toteuttamiseksi osana [Javan
kokoelmakehystä](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/doc-files/coll-overview.html). 

Javan kokoelmaviitekehys perustuu kahteen pääosaan: 

- *kokoelmarajapintoihin*, jotka määrittävät, mitä toimintoja kokoelmalla voi
  tehdä (esim. `List`, `Set`), sekä
- *konkreettisiin toteutusluokkiin*, jotka toteuttavat rajapinnan tai 
  rajapinnat jollakin tavalla (esim. `ArrayList`, `HashSet`, `HashMap`).

Esimerkiksi `List` on kokoelmarajapinta, joka määrittää listalle kuuluvia
metodeja, mutta ei sitä, miten ne varsinaisesti toteutetaan. Puolestaan
`ArrayList` on eräs luokka, joka toteuttaa `List`-rajapinnan käyttämällä
taulukkoja. Muita valmiita listan toteutuksia käsitellään [osassa 5.2](02-listarakenteet.md).

Javan kokoelmakehyksen oleellisin rajapinta on `Collection` ([Java
Doc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Collection.html)),
joka on yleinen, korkean tason rajapinta. Esimerkiksi edellä mainittu
`ArrayList` voidaan sijoittaa `Collection`-tyyppiseen muuttujaan:

```java
//-void main() {
Collection<String> marjat = new ArrayList<>(List.of("mansikka", "mustikka", "puolukka", "lakka"));
IO.println(marjat);
//-}
```

`Collection` ei tee oletuksia sen sisältämien alkioiden järjestyksestä tai
sisällöstä. Varsin lukuisa joukko Javan valmiita kokoelmia toteuttavat
`Collection`-rajapinnan. Tutustumme tässä osassa eräisiin kokoelmiin ja niiden
toteutuksiin tarkemmin, mutta ensin katsotaan, mitä `Collection`-rajapinnan
toteuttavalla kokoelmalla voi tehdä.

## Lisääminen ja poistaminen

Alkioiden lisääminen onnistuu `add` ja `remove`-metodeilla. 

```java
//-void main() {
Collection<String> marjat = new ArrayList<>(List.of("mansikka", "mustikka", "puolukka", "lakka"));
marjat.add("kirsikka");
IO.println(marjat);

marjat.remove("mansikka");
IO.println(marjat);
//-}
```

Huomaa, että `add`-metodi ei takaa, mihin kohtaan kokoelmaa uusi alkio lisätään,
eikä `remove`-metodi poista alkioita indeksin perusteella.
`Collection`-rajapinta ei edellytä, että sen toteuttava luokka säilyttäisi
alkioitaan missään tietyssä järjestyksessä.

## Tietyn alkion löytyminen kokoelmasta

`Collection`-rajapinta määrittelee myös `contains`-metodin, jolla voi tarkistaa,
löytyykö kokoelmasta jokin alkio.

```java
//-void main() {
Collection<String> marjat = new ArrayList<>(List.of("mustikka", "puolukka", "lakka", "kirsikka"));
IO.println("Löytyykö mustikka: " + marjat.contains("mustikka"));

// Mansikka poistettiin yllä, eli ei löydy
IO.println("Löytyykö mansikka: " + marjat.contains("mansikka"));
//-}
```

## Alkioiden määrä ja tyhjyys

`Collection`-rajapinta määrittelee myös `size`- ja `isEmpty`-metodit, joilla voi
selvittää, kuinka monta alkiota kokoelmassa on ja onko kokoelma tyhjä.

```java
//-void main() {
Collection<String> marjat = new ArrayList<>(List.of("mustikka", "puolukka", "lakka", "kirsikka"));
IO.println("Marjoja on: " + marjat.size());
IO.println("Onko marjakokoelma tyhjä: " + marjat.isEmpty());  
//-}
```

## Alkioiden läpikäynti

`Collection`-rajapinta perii `Iterable`-rajapinnan, joka määrittelee, että
kokoelman alkioita voi käydä läpi `for each`-silmukalla.

```java
//-void main() {
Collection<String> marjat = new ArrayList<>(List.of("mustikka", "puolukka", "lakka", "kirsikka"));
for (String marja : marjat) {
    IO.println("Marja: " + marja);
}
//-}
```

Kuten mainitsimme, `Collection`-rajapinta ei tee oletuksia sen sisältämien
alkioiden järjestyksestä. Tämän vuoksi läpikäynti ei onnistu indeksin
perusteella esimerkiksi `for`-silmukalla. Kokoelman alkioiden järjestys riippuu
aina konkreettisen kokoelman valinnasta. Esimerkiksi `ArrayList` säilyttää
alkioiden järjestyksen (ts. läpikäynti tapahtuu siinä järjestyksessä, jossa
alkiot on lisätty), mutta jotkin toteutukset eivät. Esimerkiksi `HashSet`
sijoittaa alkiot sisäiseen rakenteeseensa niiden niin kutsutun hajautusarvon
perusteella, joten läpikäynti tapahtuu hajautusarvojen mukaisessa
järjestyksessä, joka voi vaikuttaa satunnaiselta.

Sivuhuomautuksena mainittakoon, että `Collection` todellakin *perii*
`Iterable`-rajapinnan. Emme käsitelleet rajapinnan perintää aiemmin, mutta idea
toimii rajapinnoissa samalla tavalla kuin luokissa: `Collection`-rajapinnan
toteuttavan kokoelman tulee toteuttaa myös kaikki `Iterable`-rajapinnan
määrittelemät metodit.

## Mieti mitä näille tehdään

- `Collection`
    - Kokoelma alkioita, joita voi käydä läpi järjestelmällisesti
    - Yleinen rajapinta mille tahansa kokoelmalle
    - Perii `Iterable`-rajapintaa, eli alkiot voi käydä läpi `for each`
      -silmukalla
    - Esimerkki kokoelmasta: `ArrayList`
    - Mitä kokoelmalla voi tehdä (esimerkkejä `ArrayList`in lautta):
        - Lisätä ja poistaa alkioita
        - Selvittää, löytyykö kokoelmasta alkioita
        - Selvittää, kuinka monta alkiota on kokoelmassa ja onko kokoelma tyhjä
        - Tyhjentää kokoelma
        - Käydä jokainen alkio läpi iteroimalla tai Streamilla


> [!WIP] 
> 
> Ajatuksia tehtäville:
>
> - Ota joku vanha tuttu tehtävä ja toteuta se kokoelmalle (esim. Keskiarvon
>   laskeminen)
> - Tutustu `Collections`-luokkaan, tee esim. valmiista kokoelmasta sellaisen,
>   että sitä ei voi muokata (`Collections.unmodifibleCollection`); laske
>   kokoelmassa annettun luvun frekvenssi/maksimi/minimi.
> - Tehtävien tarkistimessa voi olla erilaisia kokoelmia => testataan, että
>   opiskelijan koodi toimii erilaisilla kokoelmilla
> - Tee luokka `NotNullCollection<T> implements Collection<T>` joka tallentaa
>   attribuutiksi `Collection`in, välittää kaikki kutsut attribuuttiin (eli
>   luokka on ikään kuin käärijäluokka), mutta kokoelmaan ei saa lisätä
>   `null`-arvoja. Eli pitää ylikirjoittaa `add` yms.

## Miksi ja milloin käyttäisin kokoelmia?

- Listat ja taulukot toimivat pitkälle
- Monissa tapauksissa taulukolla pärjää todella pitkälle => taulukko on myös
  eräänlainen kokoelma

- Valinta tarkoittaa sekä kokoelmarajapinnan valinta sekä sen vastaavan
  toteutuksen valinta
- Kumpikin tärkeä => rajapinta vaikuttaa siihen, miten ohjelman koodi
  kirjoitetaan (kokoelmarajapinta antaa takeita); toteutus vaikuttaa siihen,
  kuinka paljon aikaa tai muistia eri operaatiot vie ja tai mitä
  "lisäominaisuuksia" toteutus tarjoaa (esim. samanaikaisuus, alkioiden
  järjestys)
- <https://dev.java/learn/api/collections-framework/intro/>: tarkempia ohjeita
  valintaa, voi tiivistää
- Javassa jos yleispätevissä algoritmeissa voi käyttää mielellään
  `Collection`-tyyppiä, sillä algoritmia voi sitten käyttää kaikilla kokoelmilla
- Javassa `Collection` tarjoaa jo jotain toimintoja, joita perustaulukko ei
  tarjoa (esim. olemassaolon tarkistus, poistaminen)
- Seuraavissa luvuissa esitellään erilaisia kokoelmatyyppejä
   - Jokaisen yhteydessä esitetään sen olennaiset toiminnot ja vaatimukset sekä
     toteutukset ja niiden mahdolliset hyödyt ja käyttötarkoitukset 

> [!WIP] 
> 
> Ehkä ei tehtäviä varsinaisesti, mutta jotain esimerkkejä voisi heittää?
>
> Jos tehtäviä, niin yllä olevat käyvät hyvin myös tähän kohtaan.