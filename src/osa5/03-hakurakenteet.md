# Hakurakenteet

> [!VAROITUS]
> Tämä osio julkaistaan 9. helmikuuta 2026.
> {{#include ../ei-julkaistu.md}}

> [!Osaamistavoitteet]
>
> - Tunnet Java-kielen yleisimmät valmiit tietorakenteet: `Map` ja sen toteutukset `HashMap`, `LinkedHashMap` ja `TreeMap`.
> - Osaat käyttää ym. tietorakenteita.
> - Ymmärrät ym. tietorakenteiden keskeisimmät operaatiot ja niiden aikakompleksisuudet. 
> - Ymmärrät, miksi oliot tarvitsevat `hashCode`-metodin.

Kuvitellaan tilanne, jossa ylläpidämme laajaa opiskelijarekisteriä. Haluamme
tallentaa kunkin opiskelijan numeron ja nimen siten, että voimme 
opiskelijanumeron perusteella löytää helposti opiskelijan nimen. 

Jos käyttäisimme tähän listaa, joutuisimme tallentamaan tiedot joko 
eri listoihin tai luomaan erillisen olion, joka sisältää kaikki tiedot. Kun 
haluaisimme hakea tietyn henkilön tiedot, joutuisimme käymään listan alkioita 
läpi, kunnes oikea henkilö löytyy. Tämä ei ole hirveän tehokasta, jos 
opiskelijoita on hyvin suuri määrä. 

Hakurakenteet tarjoavat tähän ratkaisun mahdollistamalla suoran haun jonkin 
tunnuksen perusteella parhaimmassa tapauksessa ilman koko listan läpikäymistä. 
Tunnuksena voisi toimia esimerkiksi opiskelijan numero. Hakurakenne toimii kuin 
sanakirja, josta voimme katsoa suoraan oikean kohdan sen sijaan, että lukisimme 
koko kirjan kannesta kanteen löytääksemme tietyn sanan. Hakurakenteita 
kutsutaankin useissa ohjelmointikielissä nimellä *dictionary*. Javassa ne 
tunnetaan nimellä *map*.

```java
//-void main() {
// Luodaan avain-arvo-pareja.
Map<String, String> opiskelijat = Map.of(
  "123", "Joni",
  "555", "Maija",
  "789", "Mikko"
);

// Voimme pyytää avaimen avulla opiskelijan tietoja suoraan käymättä
// koko tietorakennetta läpi.
IO.println("Opiskelija, jonka numero on 123: " + opiskelijat.get("123"));
//-}
```

Hakurakenteet ovat tietorakenteita, joihin tallennetaan tietoa
avain-arvo-pareina (engl. *key-value pair*), joista käytetään myös 
nimitystä `Entry`. Toisin kuin listoissa, joissa tietoa hallinnoidaan 
numeerisen indeksin avulla, hakurakenteeseen tallennettuun arvoon voidaan päästä 
käsiksi sen avaimen perusteella. Avaimet ovat aina uniikkeja; yksi avain voi 
esiintyä hakurakenteessa vain kerran ja se osoittaa vain yhteen arvoon 
kerrallaan. Arvot sen sijaan eivät ole uniikkeja, eli eri avaimet voivat johtaa 
samaan arvoon. 

> [!HUOMAUTUS]
> Javassa sekä avaimet että arvot ovat on aina olioita. Ne eivät 
> voi olla primitiivisiä tietotyypejä, kuten `int` tai `double`, vaan tähän 
> tarkoitukseen on käytettävä vastaavia kääreluokkia, kuten `Integer` tai `Double`. 

## Map

`Map` on Javan kokoelmaviitekehyksen toinen keskeinen rajapinta
`Collection`-rajapinnan rinnalla. `Map` määrittelee yleiset säännöt kaikille
hakurakenteille tarjoamalla tärkeimmät metodit avain-arvo-parien tallentamiseen
ja käsittelyyn. `Collection`-rajapinnan tapaan `Map`-rajapinta ei ota kantaa 
alkioiden järjestykseen tai sisältöön eikä edellytä sen toteuttavien luokkien
käyttävän mitään tiettyä tietorakennetta tiedon tallentamiseen.

Toisin kuin `Collection`, `Map` ei toteuta `Iterable`-rajapintaa, minkä 
vuoksi hakurakenteita ei voida iteroida esimerkiksi `for each` -silmukan avulla,
vaan alkioiden läpikäynti on tehtävä hieman hankalammin avainten tai arvojen 
kautta.

Tutustutaan nyt `Map`-rajapinnan tärkeimpiin metodeihin.

## Alkion lisääminen ja poistaminen

Alkioiden lisääminen onnistuu `put`, `putIfAbsent`. Metodit ottavat ensimmäisenä
parametrina avaimen ja toisen sinä vastaavan arvon. Jos avain on jo valmiiksi
tietorakenteessa, metodit palauttavat sitä vastaavan alkuperäisen arvon ennen 
ylikirjoittamista. Jos avainta ei ole valmiiksi tietorakenteessa, metodit 
palauttavat `null`.

```java
//-void main() {
Map<String, Integer> arvosanat = new HashMap<>();

// Lisää tai korvaa arvon.
arvosanat.put("Maija", 3);
arvosanat.put("Maija", 5); // Palauttaa 3 ja korvaa alkuperäisen.

// Lisää arvon vain, jos avainta ei ole vielä tietorakenteessa.
arvosanat.putIfAbsent("Joni", 1);
arvosanat.putIfAbsent("Joni", 0); // Palauttaa 1, mutta ei korvaa alkuperäistä.

IO.println(arvosanat);
//-}
```

Poistaminen onnistuu kokoelmien tapaan `remove`-metodilla, jolle annetaan
parametrina poistettava avain. Metodi palauttaa lisäämisen tapaan poistettavaa
avainta vastaavan arvon, jos sellainen tietorakenteessa on. Muussa tapauksessa
se palauttaa myös `null`;

```java
//-void main() {
Map<String, Integer> arvosanat = new HashMap<>();
arvosanat.put("Maija", 5);
arvosanat.put("Joni", 5);

// Poistaa avain-arvo-parin, jonka avain on "Joni".
arvosanat.remove("Joni");

IO.println(arvosanat);
//-}
```

## Alkion löytäminen avaimen avulla

Avainta vastaavan arvon hakemiseen voidaan käyttää `get` ja 
`getOrDefault`-metodeja. Jos avainta ei löydy, `get` palauttaa `null`, mutta 
`getOrDefault`-metodille voi itse määrittää tässä tilanteessa palautettavan 
oletusarvon.

```java
//-void main() {
Map<String, Integer> arvosanat = new HashMap<>();
arvosanat.put("Maija", 5);

// Arvo on 5.
IO.println("Maijan arvosana: " + arvosanat.get("Maija")); 

// Avainta ei ole, joten arvo on null.
IO.println("Jonin arvosana: " + arvosanat.get("Joni")); 

// Käytetään oletusarvoa 0.
IO.println("Jonin arvosana: " + arvosanat.getOrDefault("Joni", 0)); 
//-}
```

Voimme myös tarkistaa, sisältääkö rakenne tietyn avaimen tai arvon `containsKey`
ja `containsValue` -metodeilla, jotka palauttavat totuusarvon `true` tai `false`.

```java
//-void main() {
Map<String, Integer> arvosanat = new HashMap<>();
arvosanat.put("Maija", 5);

IO.println(arvosanat.containsKey("Maija")); // true
IO.println(arvosanat.containsKey("Matti")); // false

IO.println(arvosanat.containsValue(5)); // true
IO.println(arvosanat.containsKey(0)); // false
//-}
```

## Alkioiden määrä

`Map`-rajapinta määrittelee `Collection`-rajapinnan tapaan myös `size` ja
`isEmpty` -metodit, joilla voimme tarkastella alkioiden lukumäärää tai sitä,
onko se tyhjä.

```java
//-void main() {
Map<String, Integer> arvosanat = new HashMap<>();
arvosanat.put("Maija", 5);

IO.println("Alkioita: " + arvosanat.size()); // 1
IO.println("Tyhjä: " + arvosanat.isEmpty()); // false
//-}
```

## Alkioiden läpikäynti

`Map` ei toteuta `Iterable`-rajapintaa eikä ole siten suoraan iteroitavissa
`for each` -silmukalla, mutta se tarjoaa metodit `keySet`, `values` ja 
`entrySet`, jotka palauttavat avaimet, arvot tai näiden parit kokoelmina.

Huomaa, että `entrySet` palauttaa kokoelman `Map.Entry<K,V>`-tyypin olioista, 
jossa `K` ja `V` vastaavat tietorakenteen avaimen ja arvon tyyppejä.
`Map.Entry` on avain-arvo-pari, joka sisältää metodit `getKey` ja `getValue`. 

```java
//-void main() {
Map<String, Integer> arvosanat = new HashMap<>();
arvosanat.put("Maija", 3);
arvosanat.put("Matti", 4);

// Käydään läpi kaikki avaimet. Voimme avaimen perusteella hakea 
// myös sitä vastaavan arvon tulostettavaksi.
for (String avain : arvosanat.keySet()) {
  IO.println(avain + " : " + arvosanat.get(avain));
}

// Käydään läpi kaikki arvot. Arvon avulla emme pääse käsiksi 
// avaimeen, joten emme voi sitä tulostaa.
for (Integer arvo : arvosanat.values()) { 
  IO.println(arvo);
}

// Käydään läpi kaikki avain-arvo-parit.
for (Map.Entry<String, Integer> pari : arvosanat.entrySet()) { 
  IO.println(pari.getKey() + " : " + pari.getValue());
}
//-}
```

## Hajautustaulu

Hajautustaulu on tietorakenne, jonka toiminta perustuu olioiden
`equals`- ja `hashCode`-metodeihin. Kun hajautustauluun lisätään
avain-arvo-pari, rakenne kutsuu avaimen `hashCode`-metodia. Käytettyä metodia
kutsutaan *hajautusfunktioksi* ja sen palauttama luku eli *hajautusarvo* 
muutetaan hajautustaulun indeksiksi, johon alkio sijoitetaan. Hajautustaulun 
indeksien lukumäärää kutsutaan kapasiteetiksi (engl. *capacity*).
Hajautusarvolle sopiva indeksi voidaan laskea esimerkiksi hajautusarvon ja
hajautustaulun kapasiteetin jakojäännöksellä, mikä pitää huolen, että laskettu 
indeksi ei koskaan ole taulukon rajojen ulkopuolella.

```
indeksi = hajautusarvo % kapasiteetti
```

Hajautustaulussa on rajallinen määrä indeksejä, joten useampi alkio voi päätyä 
samaan indeksiin. Tätä kutsutaan törmäykseksi (engl. *collision*).
Törmäystilanteiden käsittelemiseen on kaksi yleistä tapaa; ensimmäinen on myös 
Javan `HashMap`-luokan käyttämä ketjutus (engl. *chaining*), jossa 
hajautustaulun indeksissä on lista, johon siihen indeksiin sijoittuvat alkiot 
lisätään. Toinen tapa on avoin hajautus (engl. *open addressing*), jossa 
hajautustaulun indeksit sisältävät listan sijaan itse alkioita ja törmäävä alkio 
lisätään seuraavaan vapaaseen indeksiin.

Törmäysten määrä vaikuttaa suoraan tietorakenteen suorituskykyyn. Parhaimmillaan
hajautustaulun lisäys-, poisto- ja hakuoperaatiot ovat vakioaikaisia eli
aikavaativuudeltaan *O(1)*, sillä oikea indeksi saadaan suoraan yksinkertaisella 
laskutoimituksella. Huonoimmassa tapauksessa kaikki alkiot päätyvät samaan 
indeksiin, jolloin oikean alkion löytämiseksi joudutaan käymään koko
tietorakenne läpi ja aikavaativuus laskee tasolle *O(n)*.

Alkiota hakiessa tarkistetaan ensimmäiseksi sen hajautusarvon mukainen indeksi. 
Ketjutusmenetelmää käytettäessä alkiota lähdetään etsimään indeksistä löytyvästä 
listasta, kun taas avointa hajautusta käytettäessä lähdetään käymään 
hajautustaulun indeksejä läpi, kunnes löytyy oikea alkio tai alkio, joka ei 
kuulu samaan indeksiin. Ideaalitilanteessa jokainen alkio päätyy omaan 
indeksiinsä ilman törmäyksiä, jolloin haku on välitöntä, sillä haun ei tarvitse 
käydä tietorakennetta läpi löytääkseen oikean alkion.

Hajautustaulun suorituskyky paranee merkittävästi, jos sille määritetään 
käyttötarkoitukseen sopiva kapasiteetti jo luontivaiheessa. Jos kapasiteetti 
on liian pieni suhteessa alkioiden määrään, taulukon täyttöaste 
(engl. *load factor*) nousee liian korkeaksi, mikä johtaa törmäysten 
lisääntymiseen.

Hajautustaulu toimii kuin varasto, jossa on monta säilytyslaatikkoa, joihin
voidaan laittaa kuinka monta esinettä tahansa. Kapasiteetti kuvastaa
säilytyslaatikoiden lukumäärää ja laatikot ovat hajautustaulun *indeksejä*. 
Laatikot ovat tapa järjestellä esineitä niin, että löydämme haluamamme esineen 
varastosta helpommin. Yksi laatikko voi olla vaatteita varten, toinen työkaluja, 
ja kolmanteen laitetaan kaikki muut. Voimme yksinkertaistetusti ajatella, että 
esimerkiksi kaikki työkalut saisivat saavat hajautusfunktion tuloksena saman 
indeksin ja päätyvät samaan laatikkoon. Etsiessämme vasaraa voimme heti katsoa 
työkalujen laatikosta, mutta jos se sisältää suuren määrän esineitä, oikean 
työkalun löytämiseen voi silti mennä aikaa.

## HashMap

`HashMap` on yleisimmin käytetty `Map`-rajapinnan toteuttava luokka. 
`HashMap`-luokan toteutus perustuu edellä mainittuun hajautustauluun ja se 
tarjoaa parhaan keskimääräisen suorituskyvyn perusoperaatioille. Alkioiden 
hakeminen, lisääminen ja poistaminen avaimen perusteella onnistuu parhaimmillaan
vakioajassa *O(1)*. Kaikkien alkioiden läpikäyminen on kuitenkin sisäisen
tietorakenteen vuoksi yleensä hitaampaa kuin listoissa. `HashMap`-luokan 
suorituskykyerot tulevat paremmin esille, kun alkioita on hyvin suuri määrä. 
Jos alkioita on vähän, eroa esimerkiksi listaan ei juurikaan huomaa.

`HashMap` ei takaa alkioiden järjestystä; alkioita läpi käydessä ne voivat olla 
missä järjestyksessä tahansa, ja järjestys voi muuttua, kun rakenteeseen 
lisätään uusia alkioita.

## LinkedHashMap

`LinkedHashMap` on `HashMap`-luokasta periytyvä luokka, joka ylläpitää 
sisäisesti hajautustaulun lisäksi linkitettyä listaa kaikista lisätyistä 
alkioista. Tämä mahdollistaa alkioiden **lisäysjärjestyksen** säilyttämisen, 
mutta lisätty tietorakenne vie hieman enemmän muistia.

```java
//-void main() {
Map<String, Integer> hashmap = new HashMap<>();
hashmap.put("Joni Virtanen", 20);
hashmap.put("Maija Meikäläinen", 10);
hashmap.put("Matti Korhonen", 5);

// Lisäysjärjestys ei säily.
for (String key : hashmap.keySet()) {
    IO.println(key + " : " + hashmap.get(key));
}

IO.println();

Map<String, Integer> linked = new LinkedHashMap<>();
linked.put("Joni Virtanen", 20);
linked.put("Maija Meikäläinen", 10);
linked.put("Matti Korhonen", 5);

// Lisäysjärjestys säilyy.
for (String key : linked.keySet()) {
    IO.println(key + " : " + linked.get(key));
}
//-}
```

## TreeMap

`TreeMap` eroaa edellisistä siten, että se käyttää hajautustaulun sijaan
puurakennetta sisäisenä tietorakenteenaan. Se toteuttaa `SortedMap`- ja
`NavigableMap`-rajapinnat. `SortedMap` takaa, että avaimet ovat aina
**luonnollisessa järjestyksessä**, ja `NavigableMap` lisää tähän mahdollisuuden 
etsiä esimerkiksi lähintä avainta tietyn arvon ylä- tai alapuolelta. Muista
tässä osassa mainituista hakurakenteista poiketen `TreeMap` ei salli
`null`-arvoa avaimena, sillä sitä ei voitaisi vertailla muihin avaimiin
niiden järjestyksen selvittämiseksi.

`TreeMap` on puurakenteen vuoksi operaatioiltaan hitaampi kuin `HashMap`, mutta 
se mahdollistaa alkioiden järjestämisen. `TreeMap`-luokan operaatioiden
aikavaativuus on *O(log n)*.

```java
//-void main() {
Map<String, Integer> tree = new TreeMap<>();
tree.put("Olli", 100);
tree.put("Heikki", 200);
tree.put("Anna", 300);

// Tulostaa avaimen mukaisesti suuruusjärjestyksessä.
for (String key : tree.keySet()) {
    IO.println(key + " : " + tree.get(key));
}
//-}
```

`NavigableMap`-rajapinta tarjoaa myös useita metodeja, joilla voidaan hakea
avaimia tai pareja eri tavoin avainten järjestykseen perustuen. 

```java
//-void main() {
NavigableMap<String, Integer> tree = new TreeMap<>();
tree.put("B", 2);
tree.put("H", 3);
tree.put("A", 1);
tree.put("Q", 1);

// Tulostetaan pienin ja suurin avain.
IO.println("Pienin avain: " + tree.firstKey());
IO.println("Suurin avain: " + tree.lastKey()); 

// Tulostetaan annettua avainta lähin pienempi ja suurempi avain.
IO.println(tree.lowerKey("B")); 
IO.println(tree.higherKey("B"));

// Palauttaa koko tietorakenteen käänteisessä järjestyksessä.
IO.println(tree.descendingMap()); 
//-}
```

Lisäksi `TreeMap` mahdollistaa alijoukkojen muodostamisen `subMap`-metodilla.

```java
//-void main() {
NavigableMap<String, Integer> tree = new TreeMap<>();
tree.put("B", 2);
tree.put("H", 3);
tree.put("A", 1);

// Muodostetaan uusi hakurakenne alkioista, jotka ovat A-C välillä.
// Parametrien true-arvot kertovat, että myös A ja C otetaan mukaan.
Map<String, Integer> alipuu = tree.subMap("A", true, "C", true);
IO.println(alipuu);
//-}
```

---

<task>
  <task-title>Tehtävä 5.5: Sanat<points>1 p.</points> </task-title>
  <handout>

{{#include ../exercises/5-5-sanat/handout.md}}

  </handout>
  <task-link><a href="https://tim.jyu.fi/view/kurssit/tie/tiep111/tehtavat/osa5/tehtava5">Tee tehtävä TIMissä</a></task-link>
</task>

<task>
  <task-title><i class="bi bi-stars jyu-gold"></i> Bonus: Tehtävä 5.6: Varaukset<points>1 p.</points> </task-title>
  <handout>

{{#include ../exercises/5-6-varaukset/handout.md}}

  </handout>
  <task-link><a href="https://tim.jyu.fi/view/kurssit/tie/tiep111/tehtavat/osa5/tehtava6">Tee tehtävä TIMissä</a></task-link>
</task>

<task>
  <task-title><i class="bi bi-stars jyu-gold"></i> Bonus: Tehtävä 5.7: Hajautustaulu<points>1 p.</points> </task-title>
  <handout>

{{#include ../exercises/5-7-hajautustaulu/handout.md}}

  </handout>
  <task-link><a href="https://tim.jyu.fi/view/kurssit/tie/tiep111/tehtavat/osa5/tehtava7">Tee tehtävä TIMissä</a></task-link>
</task>
