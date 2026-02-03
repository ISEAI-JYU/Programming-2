# Kokoelmat

> [!VAROITUS]
> Tämä osio julkaistaan 9. helmikuuta 2026.
> {{#include ../ei-julkaistu.md}}

> [!Osaamistavoitteet]
>
> - Ymmärrät, mitä kokoelma tarkoittaa ja miksi niitä käytetään ohjelmoinnissa.
> - Ymmärrät Javan kokoelmaviitekehyksen (*Collections Framework*)
>   perusrakenteen.
> - Tunnet Javan `Collection`-rajapinnan metodit ja osaat käyttää ne ohjelmassa.

Tiedon koostaminen kokonaisuuksiin on hyvin yleinen osa ohjelmointia.
Esimerkiksi osassa 2 ja 3 käsitelty olio-ohjelmointi tarjoaa tavan koostaa
samaan kohteeseen liittyviä tietoja attribuutteina ja määrittää tapoja käsitellä
tätä tietoa. Kun taas samantyyppisiä arvoja, kuten päivän lämpötiloja tai
luennolla olevia opiskelijoita, olemme koostaneet taulukoihin (`T[]`) tai
listohin (`ArrayList<T>`).

Taulukon ja listan erot ovat pintapuolisesti pienet. Kumpaankin voi tallentaa
samantyyppisiä arvoja ja arvoja voidaan hakea indeksin perusteella, mutta
taulukkoihin ei voi lisätä uusia alkioita, kun taas listaan voi. Oikeassa
maailmassa on kuitenkin tapauksia, jossa tiedon käyttämiseen liittyy jokin
erikoisrajoite.

 - Uno-korttipelissä on korttipakka, jossa on kortteja, *mutta* kortteja saa
   ottaa vain pakan päältä ja lisätä pakan alle.
 - Sisuun viedään arvosanat aina pareina `(opiskelijanumero, arvosana)` **ja**
   jokaista opiskelijaa tulee vastata korkeintaan yksi arvosana samassa
   toteutuksessa.
 - Discord-viestipalvelussa käyttäjällä voi olla useita ystäviä, **mutta** samaa
   käyttäjää ei voi lisätä ystäväkseen kahdesti.

Yllä olevat tapaukset pystytään toteuttamaan taulukko- ja listarakenteilla,
mutta lisäehtojen toteuttaminen vaatisi ylimääräistä teknistä työtä. Ohjelman
suunnittelu ja toteutus olisi mahdollisesti helpompi, jos se pystyttäisiin
tekemään kohdealueen rakenteilla ja termeillä. Siten tarvitaan mahdollisesti
listoja ja taulukoita yleisempi koostava tietorakenne.

**Kokoelma** (engl. *collection*) on olio, joka koostaa useita samantyyppisiä
arvoja samaan yksikköön ja mallintaa tapoja, jolla näitä arvoja voi käsitellä.
Toisin sanoen kokoelma on eräänlainen yleistys tietorakenteelle, joka voi
sisältää useita samantyyppisiä tietorakenteita. Java-kokoelmat perustuvat
rajapintoihin (interface), eivät konkreettisiin luokkiin. Tietorakennetta
valitessaan ohjelmoija valitsee paitsi tavan, jolla tiedot tallennetaan, myös
sen, miten tietoja voi käsitellä.

On olemassa erilaisia kokoelmakategorioita. 

- Lista on kokoelma, jossa alkiot pysyvät kiinteässä järjestyksessä ja joita 
  pystyy osoittamaan indeksillä.
- Jonorakenne (engl. *queue*) on kokoelma, jossa uudet alkiot lisätään
  kokoelmaan loppuun ja alkioita poistetaan aina kokoelman alusta.
  Esimerkiksi Uno-korttipelin korttipakkaa voisi mallintaa jonorakenteella. 
- Hakurakenne (engl. *map*) on kokoelma avain-arvopareja, jossa jokaista avainta voi vastata
  täsmälleen yksi arvo. Esimerkiksi Sisu-järjestelmän arvosanat voisi
  mallintaa hakurakenteella, jossa avaimina ovat opiskelijanumerot ja arvoina
  arvosanat.
- Joukko (engl. *set*) on kokoelma, johon ei voi lisätä samaa arvoa kahdesti.
  Esimerkiksi Discordin ystävälista voisi mallintaa joukolla, koska samaa
  käyttäjää ei voi lisätä ystäväksi kahdesti.

Jokaiselle näistä kategorioista on Javassa useita erilaisia toteutuksia, joilla
on erilaisia ominaisuuksia ja käyttötarkoituksia. Tutustumme kokoelmatyyppeihin
ja niiden tavallisimpiin toteutuksiin tarkemmin tässä osassa. 

## Kokoelmat Javassa

Java tarjoaa joukon valmiita kokoelmia sekä rajapintoja uusien kokoelmien
toteuttamiseksi osana [Javan
kokoelmaviitekehystä](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/doc-files/coll-overview.html). 

Javassa jokainen kokoelma koostuu

- *kokoelmarajapinnasta*, joka määrittää, mitkä toiminnot ovat määritelty kokoelmalle, ja
- yhdestä tai useasta *kokoelmaluokasta*, joka on yhden tai useamman kokoelman toteutus.

Esimerkiksi `List<T>` on rajapinta, joka määrittää listalle kuuluvia
metodeja, mutta ei sitä, miten ne varsinaisesti toteutetaan.
Puolestaan `ArrayList<T>` on eräs luokka, joka toteuttaa `List<T>`-rajapinnan käyttämällä
taulukkoja. Muita valmiita listan toteutuksia käsitellään [luvussa 5.2](02-listarakenteet.md).

Javan kokoelmaviitekehyksen oleellisin rajapinta on `Collection<T>` ([Java Doc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Collection.html)),
joka on yleinen rajapinta mille tahansa kokoelmalle. Esimerkiksi edellä mainittu
`ArrayList<T>` voidaan sijoittaa `Collection<T>`-tyyppiseen muuttujaan:

```java
//-void main() {
Collection<String> marjat = new ArrayList<>(List.of("mansikka", "mustikka", "puolukka", "lakka"));
IO.println(marjat);
//-}
```

Javassa `Collection<T>` on hyvin yleinen rajapinta, joka ei tee oletuksia sen
sisältämien alkioiden järjestyksestä tai sisällöstä. Tarkastellaan, mitä
yleisellä kokoelmalla pystyy tekemään.

## Lisääminen ja poistaminen

Kokoelmilla on `add` ja `remove`-metodit, jolla kokoelmiin voi lisätä
ja sieltä voi poistaa alkioita.

```java
//-void main() {
Collection<String> marjat = new ArrayList<>(List.of("mansikka", "mustikka", "puolukka", "lakka"));
marjat.add("kirsikka");
IO.println(marjat);

marjat.remove("mansikka");
IO.println(marjat);
//-}
```

**Huomaa**, että kokoelmaan ei voi lisätä alkioita tiettyyn indeksiin eikä
alkiota voi poistaa indeksin perusteella. Tämä johtuu siitä, että kokoelmalta 
ei edellytetä, että se säilyttäisi alkioitaan missään tietyssä järjestyksessä.

## Tietyn alkion löytyminen kokoelmasta

Kokoelmilla on `contains`-metodi, jolla voi tarkistaa, löytyykö kokoelmasta
jokin alkio.

```java
//-void main() {
Collection<String> marjat = new ArrayList<>(List.of("mustikka", "puolukka", "lakka", "kirsikka"));
IO.println("Löytyykö mustikka: " + marjat.contains("mustikka"));

// Mansikka poistettiin yllä, eli ei löydy
IO.println("Löytyykö mansikka: " + marjat.contains("mansikka"));
//-}
```


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