# Kokoelmat

> [!WIP]
> - Otetaan pohjaa seuraavista lähteistä
>    - <https://docs.oracle.com/javase/tutorial/collections/>
>    - <https://dev.java/learn/api/collections-framework/>
>    - <https://www.cs.helsinki.fi/u/ahslaaks/kkkk.pdf>

> [!Osaamistavoitteet]
>
> - Ymmärrät, mitä kokoelma tarkoittaa ja miksi niitä käytetään ohjelmoinnissa.
> - Ymmärrät Javan kokoelmaviitekehyksen (*Collections Framework*) perusrakenteen.
> - Tunnet Javan `Collection`-rajapinnan metodit ja osaat käyttää ne ohjelmassa.


- Tähän mennessä on käytetty taulukkoja ja listoja samankaltaisen tiedon koostamiseen
  - Sen sijaan että tehdään 5 muuttujaa voidaan tehdä yksi muuttuja, jossa jokaista arvoa vastaa paikka (indeksi)
  - Taulukoilla on kiinteä pituus, mutta listaan pystytään lisäämään ja sieltä voidaan poistaa alkioita
  - Taulukoilla ja listoilla voidaan käytännössä kirjoittaa mitä tahansa ohjelmia
- Oikeassa maailmassa asioita voidaan kuitenkin kostaa eri tavoin ja eri vaatimuksilla
    - esim. Uno-korttipelissä uudet kortit otetaan pakan päältä ja laitetaan takaisin pakan alle (viimeisin pakkaan lisätty kortti ei oteta pois ensimmäisena)
    - Sisuun vietävät arvosanat esitetään aina pareina (opiskelijanumero, arvosana) ja jokaisella opiskelijalla tulee olla täsmälleen yksi arvosana samalle toteutukselle
    - Voit lisätä Discordiin ystäviä, mutta et voi lisätä samaa käyttäjää ystäväksi kahdesti
- Ohjelmat pyritään usein suunnittelemaan ja esittämään kohdealueen rakenteilla ja termeillä
    - Listalla ja taulukolla onnistuu, mutta koodin intentiota voi olla vaikeaa ymmärtää kohdalueen kannalta tai joutuu tekemään apumetodeja

- Kokoelma ohjelmointikielessä = olio, joka koostaa useita alkioita samaan yksikköön ja mallintaa tapoja, jolla alkioita voi käsitellä
  - Abstrahoi datan tallentamisen yksityiskohdat ja tarjoaa korkean tason toimintoja alkioiden käsittelyyn

- Esimerkkejä yleisistä kokoelmien tyypeistä
  - Uno-korttipelin kortit => pino
  - Sisuun vietävät arvosanat => hakurakenne eli sanakirja
  - Discordin ystävät => joukkorakenne

## Kokoelmat Javassa

- Javan kokoelmaviitekehys
   - Jokaisella kokoelmalla on rajapinta => määrittää, miten kokoelman tulisi toimia, antaa jotain takeita alkioista
   - Rajapinnalle on yksi tai useampi toteutus => varsinainen toteutus
   - Esimerkki: `List` on rajapinta listamaiselle tietorakenteelle, `ArrayList` on eräs toteutus

- `Collection`
    - Kokoelma alkioita, joita voi käydä läpi järjestelmällisesti
    - Yleinen rajapinta mille tahansa kokoelmalle
    - Perii `Iterable`-rajapintaa, eli alkiot voi käydä läpi `for each` -silmukalla
    - Esimerkki kokoelmasta: `ArrayList`
    - Mitä kokoelmalla voi tehdä (esimerkkejä `ArrayList`in lautta):
        - Lisätä ja poistaa alkioita
        - Selvittää, löytyykö kokoelmasta alkioita
        - Selvittää, kuinka monta alkiota on kokoelmassa ja onko kokoelma tyhjä
        - Tyhjentää kokoelma
        - Käydä jokainen alkio läpi iteroimalla tai Streamilla


> [!WIP]
> Ajatuksia tehtäville:
>
> - Ota joku vanha tuttu tehtävä ja toteuta se kokoelmalle (esim. Keskiarvon laskeminen)
> - Tutustu `Collections`-luokkaan, tee esim. valmiista kokoelmasta sellaisen, että sitä ei voi muokata (`Collections.unmodifibleCollection`); laske kokoelmassa annettun luvun frekvenssi/maksimi/minimi.
> - Tehtävien tarkistimessa voi olla erilaisia kokoelmia => testataan, että opiskelijan koodi toimii erilaisilla kokoelmilla
> - Tee luokka `NotNullCollection<T> implements Collection<T>` joka tallentaa attribuutiksi `Collection`in, välittää kaikki kutsut attribuuttiin (eli luokka on ikään kuin käärijäluokka), mutta kokoelmaan ei saa lisätä `null`-arvoja. Eli pitää ylikirjoittaa `add` yms.

## Miksi ja milloin käyttäisin kokoelmia?

- Listat ja taulukot toimivat pitkälle
- Monissa tapauksissa taulukolla pärjää todella pitkälle => taulukko on myös eräänlainen kokoelma

- Valinta tarkoittaa sekä kokoelmarajapinnan valinta sekä sen vastaavan toteutuksen valinta
- Kumpikin tärkeä => rajapinta vaikuttaa siihen, miten ohjelman koodi kirjoitetaan (kokoelmarajapinta antaa takeita); toteutus vaikuttaa siihen, kuinka paljon aikaa tai muistia eri operaatiot vie ja tai mitä "lisäominaisuuksia" toteutus tarjoaa (esim. samanaikaisuus, alkioiden järjestys)
- <https://dev.java/learn/api/collections-framework/intro/>: tarkempia ohjeita valintaa, voi tiivistää
- Javassa jos yleispätevissä algoritmeissa voi käyttää mielellään `Collection`-tyyppiä, sillä algoritmia voi sitten käyttää kaikilla kokoelmilla
- Javassa `Collection` tarjoaa jo jotain toimintoja, joita perustaulukko ei tarjoa (esim. olemassaolon tarkistus, poistaminen)
- Seuraavissa luvuissa esitellään erilaisia kokoelmatyyppejä
   - Jokaisen yhteydessä esitetään sen olennaiset toiminnot ja vaatimukset sekä toteutukset ja niiden mahdolliset hyödyt ja käyttötarkoitukset 

> [!WIP]
> Ehkä ei tehtäviä varsinaisesti, mutta jotain esimerkkejä voisi heittää?
>
> Jos tehtäviä, niin yllä olevat käyvät hyvin myös tähän kohtaan.