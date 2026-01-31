# Listarakenteet

> [!VAROITUS]
> Tämä osio julkaistaan 9. helmikuuta 2026.
> {{#include ../ei-julkaistu.md}}

> [!WIP]
> - Otetaan pohjaa seuraavista lähteistä
>    - <https://docs.oracle.com/javase/tutorial/collections/>
>    - <https://dev.java/learn/api/collections-framework/>
>    - <https://www.cs.helsinki.fi/u/ahslaaks/kkkk.pdf>

> [!Osaamistavoitteet]
>
> TODO: Pitäisikö olla sen sijaan ohjattu tehtävä?
> TAI: Voisi tehdä Full Stack Moocin tavoin ohjatusti ja sitten
>      tehtävänä on tehdä LinkedList tai HashMap.
> Vrt. myös [HY](https://java-programming.mooc.fi/part-12/2-arraylist-and-hashtable)
> 

- Kokoelma, jossa alkiot pysyvät siinä järjestyksessä, jossa niitä on lisätty kokoelmaan

## Listan perusominaisuudet ja soveltuvuuskohteet

- Javassa
  - `List`-rajapinta
  - Yleisin käytetty toteutus: `ArrayList`

- Ensimmäinen alkio listassa on aina ensimmäiseksi lisätty, sitten on toiseksi lisätty jne.

    ```java
    //-void main() {
    List<String> marjat = new ArrayList<>();
    marjat.add("mustikka");
    marjat.add("puolukka");
    marjat.add("mansikka");
    IO.println(marjat);
    //-}
    ```

- Alkioiden suhteellinen järjestys pysyy vaikka alkioita poistaisi listan jostain kohtaa

    ```java
    //-void main() {
    //-List<String> marjat = new ArrayList<>();
    //-marjat.add("mustikka");
    //-marjat.add("puolukka");
    //-marjat.add("mansikka");
    marjat.remove("puolukka");
    IO.println(marjat);
    //-}
    ```

- Jokaisella alkiolla on siten *paikka* eli indeksi, jonka perusteella alkioita voi hakea

    ```java
    //-void main() {
    //-List<String> marjat = new ArrayList<>();
    //-marjat.add("mustikka");
    //-marjat.add("puolukka");
    //-marjat.add("mansikka");
    //-marjat.remove("puolukka");
    IO.println("Listassa on " + marjat.size() + " alkiota");
    for (int i = 0; i < marjat.size(); i++) {
       IO.println("Paikassa " + i + " on alkio " + marjat.get(i));
    }
    //-}
    ```

- Alkiot käydään läpi aina samassa järjestyksessä

    ```java
    //-void main() {
    //-List<String> marjat = new ArrayList<>();
    //-marjat.add("mustikka");
    //-marjat.add("puolukka");
    //-marjat.add("mansikka");
    //-marjat.remove("puolukka");
    IO.println("Ensimmäinen iteraatio: ");
    for (String marja : marjat) {
       IO.println(marja);
    }

    marjat.add("lakka");

    IO.println("Toinen iteraatio: ");
    for (String marja : marjat) {
       IO.println(marja);
    }
    //-}
    ```

- Listat soveltuu tilanteisiin, jossa jokaiselle alkiolle halutaan kiinteä paikka ja kiinteä järjestys
  - Tilanteet, jossa järjestystä halutaan muokata tai säilyttää (esim. järjestäminen, haku paikan perusteella)
  - Samaa alkio saa esiintyä listassa useaan kertaan

## Listat ja taulukot

- Yllä olevan kuvauksen perusteella listoilla ja taulukoilla on paljon yhtymäkohtia
- Kokeillaan tehdä oma yksinkertainen listarakenne käyttämällä taulukoita

> [!WIP]
> Tehdään esimerkkien kautta
> - Tehdään `Lista<T>`-luokka ja sille attribuutiksi taulukko
> - Katsotaan, mitkä ovat olennaisimmat listan toiminnan kannalta olevat metodit ja toteutetaan ne järjestyksessä
>     - `get(index)` -> suoraan
>     - `size()` -> suoraan
>     - `isEmpty()` -> tehtävänä
>     - `set(index, element)` -> suoraan
>     - `contains(object)` -> tehtävänä
>     - `indexOf(object)` -> tehtävänä
>     - `iterator()` -> suoraan tai tehtävänä

### Dynaamisuus listoissa

- Javassa `List` ei periaatteessa tarvitse sallia uusien alkioiden lisäämisen
- Kuitenkin on yleinen olettamus, että listarakenteet ovat dynaamisia, eli alkioita voidaan lisätä ja poistaa
- Miten dynaamisuus voitaisiin toteuttaa taulukkojen kanssa, kun taulukkojen kokoa ei voi muuttaa
  - `add`: taulukkoa ei voi kasvattaa, mutta voidaan aina tehdä uusi taulukko ja kopioida alkioita siihen
  - `remove`: voidaan tehdä uusi taulukko TAI tehdään apumuuttuja, joka kertoo, mihin indeksiin asti taulukko on täytetty

> [!WIP]
> Tehdään esimerkkien kautta
> - Toteutetaan järjestyksessä
>     - `add(e)` -> yllä olevan kuvauksen kautta
>     - `remove(e)` -> yllä olevan kuvauksen kautta
>     - `remove(index)` -> tehtävänä
>     - `size()`, `isEmpty()` ja muiden aiempien metodien päivitys -> tehtävänä

- Lopuksi huomioita
  - Toteutus ei vielä lopullinen, sillä `List`-rajapinta paljon laajempi

> [!WIP]
> Mahdollinen ajatus bonus- tai guru-tason tehtäväksi: toteuta itse tehtyyn listaan
> loput `Collection<T>`-rajapinnan pakolliset metodit, jotta itse tehtyä listaa voi käyttää kokoelmana.
>  - `contains`, `containsAll`, `equals`, `hashCode`, `isEmpty`, `iterator`, `size`, `toArray`

## Listan toteutuksia

- Yleisiä toteutuksia listalle
   - `ArrayList` - lista, jossa alkiot tallenetaan taulukkoon
      - kun taulukosta loppuu tila, luodaan uusi taulukko
      - kaikki operaatiot toteutettu taulukko-operaatioina
      - Erityishuomiot operaatioista
        - Uuden alkion lisääminen listan loppuun on keskimäärin O(1) mutta pahimmillaan O(n)
        - Lisääminen alkuun aina O(n), listan väliin keskimäärin O(n)
        - Alkion hakeminen indeksin perusteella O(1)
        - Plussaa: alkiot sijaitsevat tietokoneen muistissa aina lähekkäin, jolloin käyttöjärjestelmä pystyy optimoimaan muistin käyttöä
   - `LinkedList` - lista, jossa jokainen alkio sisältää arvon ja viitteen seuraavaan ja edelliseen alkioon
      - Alkiot muodostavat ikään kuin "ketjun", jota pitkin voi liikkua
      - Erityishuomiot operaatioista
        - Lisääminen loppuun ja alkuun on nopeaa O(1)
        - Poistaminen lopusta ja alusta on myös nopeaa O(1) -> soveltuu jonoksi, josta oma luku
   - Muuttumattomat listat `List.of`-metodilla