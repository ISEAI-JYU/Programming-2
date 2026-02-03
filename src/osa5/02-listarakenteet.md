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

`List`-rajapinta kuvaa kokoelmaa, jossa alkiot ovat tietyssä järjestyksessä ja
niihin voidaan viitata indeksin avulla. Tämä vastaa monella tapaa taulukkoa,
mutta tarjoaa huomattavasti joustavamman rajapinnan. Sivuhuomautus: Oikeampaa
olisi sanoa, että kyseessä on `List<E>`-rajapinta, jossa `E` on listan alkioiden
(*element*) tyyppi, mutta tässä yhteydessä jätämme geneerisyyden mainitsematta,
jotta kirjoitusasu pysyy yksinkertaisena. 

Kuvitellaan, että ohjelma tallentaa opiskelijoiden nimet siinä järjestyksessä
kuin he ovat ilmoittautuneet kurssille. Tässä tilanteessa järjestyksellä on
merkitystä, ja sama nimi voi esiintyä useammin kuin kerran, koska kahdella eri
henkilöllä voi olla sama nimi. 

```java
//-void main() {
List<String> opiskelijat = new ArrayList<>();
opiskelijat.add("Aino");
opiskelijat.add("Ville");
opiskelijat.add("Aino");

System.out.println(opiskelijat.get(1)); // Ville
//-}
```

Listan käyttäminen tuntuu luontevalta, koska ajattelet tietoa nimenomaan jonona:
ensimmäinen, toinen, kolmas. `ArrayList` on tässä yleisin valinta, koska se
mahdollistaa nopean pääsyn alkioihin indeksin avulla. Tässä vaiheessa kurssia
voit ajatella, että `List` tarkoittaa käytännössä `ArrayList`‑luokkaa, ellei ole
erityistä syytä käyttää muuta toteutusta.

Listan alkioiden suhteellinen järjestys säilyy, vaikka välistä poistetaan
alkioita. Kun yksi alkio poistetaan, sitä seuraavat alkiot siirtyvät
automaattisesti yhden askeleen eteenpäin.

```java
//-void main() {
List<String> opiskelijat = new ArrayList<>();
opiskelijat.add("Aino");
opiskelijat.add("Ville");
opiskelijat.add("Aino");
opiskelijat.remove("Ville");
IO.println(opiskelijat);
//-}
```

Jokaisella alkiolla on listassa aina tietty paikka eli indeksi, ja tämän vuoksi
lista käydään läpi aina samassa järjestyksessä. Tämä tekee listasta sopivan
tilanteisiin, joissa järjestyksellä on merkitystä, järjestystä halutaan muokata
tai säilyttää, tai sama alkio saa esiintyä useita kertoja.

## Oma listarakenne

Seuraavaksi rakennetaan itse yksinkertainen listarakenne taulukon päälle. Tämän
tarkoituksena on havainnollistaa, miten `ArrayList` toimii sisäisesti
perusperiaatteiden tasolla. Toteutus ei ole täydellinen eikä kata koko
`List`‑rajapintaa, mutta riittää ymmärtämään keskeiset ideat.

Kuten muistamme, listoilla ja taulukoilla on paljon yhteistä. Molemmissa alkiot
ovat järjestyksessä ja niihin viitataan indeksin avulla. Suurin ero on siinä,
että taulukon koko on kiinteä, kun taas lista on dynaaminen ja voi kasvaa tai
pienentyä tarpeen mukaan. Käytämme tässä taulukkoa alkioiden tallentamiseen, ja
lisäämme rakenteeseen logiikan, joka mahdollistaa alkioiden lisäämisen ja poistamisen
dynaamisesti.

Aloitetaan luomalla oma luokka `Lista<T>`, joka sisältää taulukon alkioiden
tallentamista varten. Alla oleva koodi on annettu valmiiksi.

```java,ignore
public class Lista<T> {
  private T[] alkiot;
  private int koko;

  @SuppressWarnings("unchecked")
    public Lista(int kapasiteetti) {
      this.alkiot = (T[]) new Object[kapasiteetti];
    this.koko = 0;
  }
}
```


Tässä välissä on tarpeen selittää, miksi `@SuppressWarnings("unchecked")`
tarvitaan. Javassa ei voi suoraan luoda geneeristä `T`-tyyppistä taulukkoa.
Tyyppiparametri `T` on olemassa vain käännösaikana. Ajonaikaisesti Java ei
tiedä, mikä `T` oikeasti on. Tätä kutsutaan tyyppien häviämiseksi (type
erasure). Taulukot sen sijaan ovat ajonaikaisesti tyyppitietoisia; kun luot
taulukon, JVM tietää tarkasti, minkä tyyppisiä alkioita siihen saa tallentaa.
Tästä syntyy ristiriita: Java ei pysty luomaan taulukkoa tyypistä `T`, koska
`T`:n todellinen tyyppi ei ole ajonaikaisesti tiedossa. Siksi tämä ei ole
sallittua:

```java,ignore
new T[10]; // käännösvirhe 
```

Ainoa tapa kiertää rajoitus on luoda taulukko yleisimmästä mahdollisesta
viitetyypistä eli `Object`:sta ja pakottaa se tyyppimuunnoksella geneeriseksi.

```java,ignore
(T[]) new Object[kapasiteetti];
```

Kääntäjä tietää, että tämä muunnos ei ole täysin turvallinen. Se ei pysty
varmistamaan, että taulukkoon ei koskaan päädy väärän tyyppisiä alkioita. Tästä
syystä kääntäjä antaa varoituksen. Varoituksen ohittava annotaatio
`@SuppressWarnings("unchecked")` ei tee koodista turvallisempaa eikä
vaarallisempaa. Se ainoastaan kertoo kääntäjälle, että tiedostat tämän
rajoituksen ja hyväksyt sen. Ilman annotaatiota ohjelma toimii täsmälleen
samalla tavalla, mutta kääntäjä tulostaa varoituksen.

<details><summary>Lisätietoa: Missä tilanteessa tyyppimuunnos voisi aiheuttaa ongelmia?</summary>

Oletetaan, että luot `Lista<String>`-olion. Tällöin `T` on `String`. Sisäisesti
taustalla luotu taulukko alkioiden säilyttämistä varten on kuitenkin `Object[]`.
Niin kauan kuin listaa käytetään oikein, ongelmaa ei synny. Mutta Java sallii
tämän: 

```java,ignore
Lista<String> nimet = new Lista<>(10); // Lista, jossa T on String, pituus 10
Object o = nimet;
Lista<Integer> luvut = (Lista<Integer>) o; // unchecked cast
```

Kääntäjä varoittaa, mutta sallii koodin. Nyt molemmat viitteet osoittavat samaan
listaan.

Seuraavaksi lisätään listaan alkioita käyttäen `luvut`-viitettä:

```java,ignore
luvut.add(42); // oletetaan että add-metodi on toteutettu
```

Tämä onnistuu ajonaikaisesti, koska taulukko on oikeasti `Object[]` ja `Integer` on
`Object`. JVM ei havaitse mitään virhettä tässä vaiheessa. Ongelmia syntyy, kun
yrität hakea alkiota listasta:

```java,ignore
String s = nimet.get(0); // ClassCastException
```

Tässä vaiheessa JVM yrittää muuntaa alkion `String`-tyyppiseksi, koska lista on
`Lista<String>`. Koska alkio on kuitenkin `Integer`, syntyy `ClassCastException`.
Tämä on se tarkka syy, miksi kääntäjä varoittaa unchecked-muunnoksesta. Se ei
pysty todistamaan, että listan sisäinen tyyppisopimus säilyy ehjänä kaikissa
tilanteissa. On tärkeää huomata, että ongelma ei johdu listan käytöstä sinänsä,
vaan siitä, että geneerisen luokan tyyppitietoa kierretään eksplisiittisellä
tyyppimuunnoksella. Valmiit Javan kokoelmat ovat samassa tilanteessa, mutta
niiden rajapinnat ja toteutukset on suunniteltu niin, että tällaisia rikkomuksia
ei käytännössä tapahdu normaalissa käytössä.

</details>

Lisätään seuraavaksi listaan metodit `get` ja `size`, joilla pääsee käsiksi
listan alkioihin ja saadaan selville listan koko.

```java,ignore
public T get(int index) {
  if (index < 0 || index >= koko) {
    throw new IndexOutOfBoundsException("Indeksi " + index + " ei ole välillä 0.." + (koko - 1));
  }
  return alkiot[index];
}

public int size() {
  return koko;
}
``` 

Käsittelemme poikkeuksia tarkemmin vasta myöhemmin, mutta tässä yhteydessä on
tarpeen mainita, mitä `throw new...`-rivi tarkoittaa. Listan indeksit alkavat
nollasta ja jatkuvat aina `koko - 1`:een asti. Jos yrität hakea alkiota
indeksillä, joka on pienempi kuin nolla tai suurempi tai yhtä suuri kuin `koko`,
kyseinen indeksi on listan ulkopuolella. Tämä on yleinen käytäntö Javan
kokoelmissa, ja varmasti sinulle myös tuttu aivan ohjelmoinnin alkeista asti.
Nyt pääsemme itse heittämään kyseisen poikkeuksen!

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