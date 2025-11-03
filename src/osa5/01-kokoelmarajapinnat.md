# Kokoelmarajapinnat

> [!Osaamistavoitteet]
>
> - List, Set, Map. Oikean kokoelman valinta käyttötarkoituksen mukaan. 
> - Tunnet Java-kielen kokoelmarajapinnat ja niitä toteuttavia tietorakenteita: List, Set, Map
> - Collections-luokka ja Collection-rajapinta


- Otetaan pohjaa seuraavista lähteistä
    - <https://docs.oracle.com/javase/tutorial/collections/>
    - <https://dev.java/learn/api/collections-framework/>

- Motivointi kokoelmien käyttöön
  - Oikeassa maailmassa asioita voi koostaa erilaisiin kokoelmiin => pelikortit pakassa, puhelinluettelo ("numero -> nimi"), jne.
  - Kokoelma ohjelmointikielessä = olio, joka koostaa useita alkioita samaan yksikköön => mallintaa erilaisia tapoja säilyttää, hakea ja käsitellä dataa
  - Vertaus taulukkoon: kokoelmiin liittyy monesti myös se, että ne voivat olla dynaamisia, eli alkioita voi lisätä ja poistaa ohjelman suorituksen aikana (vrt. taulukko, jonka koko pysyy samana)
     - Taulukko on kuitenkin myös eräänlainen kokoelma
     - Huom: kokoelman ei tarvitse olla dynaaminen
- Kokoelmat Javassa
   - Jokaisella kokoelmalla on rajapinta => määrittää, miten kokoelman tulisi toimia, antaa jotain takeita alkioista
   - Rajapinnalle on yksi tai useampi toteutus => varsinainen toteutus
- Kokoelman valinta
   - Valinta tarkoittaa sekä kokoelmarajapinnan valinta sekä sen vastaavan toteutuksen valinta
   - Kumpikin tärkeä => rajapinta vaikuttaa siihen, miten ohjelman koodi kirjoitetaan (kokoelmarajapinta antaa takeita); toteutus vaikuttaa siihen, kuinka paljon aikaa tai muistia eri operaatiot vie ja tai mitä "lisäominaisuuksia" toteutus tarjoaa (esim. samanaikaisuus, alkioiden järjestys)
    - <https://dev.java/learn/api/collections-framework/intro/>: tarkempia ohjeita valintaa, voi tiivistää

- `Collection`
    - Kokoelma alkioita, joita voi käydä läpi järjestelmällisesti
    - Yleinen rajapinta mille tahansa kokoelmalle
    - Perii `Iterable`-rajapintaa, eli alkiot voi käydä läpi `for each` -silmukalla
    - Mitä kokoelmalla voi tehdä:
        - Lisätä ja poistaa alkioita
        - Selvittää, löytyykö kokoelmasta alkioita
        - Selvittää, kuinka monta alkiota on kokoelmassa ja onko kokoelma tyhjä
        - Tyhjentää kokoelma
        - Käydä jokainen alkio läpi iteroimalla tai Streamilla

    - **Esimerkki:** (ajatus) Toteutetaan esim. kokoelma kokonaislukutaulukosta ja sille pakolliset metodit `contains`, `containsAll`, `equals`, `hashCode`, `isEmpty`, `iterator`, `size`, `toArray` (?, ehkä)

TODO: Alla olevat ehkä räjäytetään omiin alalukuihinsa?

- `List`
    - Kokoelma, jossa alkiot pysyvät niiden suhteellisessa lisäysjärjestyksessä
    - Ensimmäinen alkio listassa on aina ensimmäiseksi lisätty, sitten on toiseksi lisätty jne.
    - Alkiot iteroidaan läpi aina **samassa** järjestyksessä
    - Yleisiä toteutuksia listalle
        - `ArrayList` - lista, jossa alkiot tallenetaan taulukkoon
           - kun taulukosta loppuu tila, luodaan uusi taulukko
           - kaikki operaatiot toteutettu taulukko-operaatioina => pääosin O(n)
        - `LinkedList` - lista, jossa jokainen alkio sisältää arvon ja viitteen seuraavaan ja edelliseen alkioon
           - Uusien alkioiden lisäys aina vakioaikainen
           - Alkion haku indeksin perusteella on lineaarinen

- `Set`
    - Kokoelma, jossa jokainen alkion arvo voi esiintyä vain kerran, ts. ei duplikaatteja
    - Alkion lisääminen joukkoon voi epäonnistua
    - Yleisiä toteutuksia
        - `HashSet` - hajautustauluun perustuva joukko
           - Alkioden järjestys ei ole kiinteä
           - Alkioiden lisäys O(1), alkion olemassaolon tarkistus O(1)
    - Lisäksi seuraavia rajapintoja
        - `SortedSet` - `Set`, jossa alkiot ovat aina järjestetty
            - Valmis toteutus Javassa: `TreeSet`
        - `NavigableSet` - `SortedSet`, mutta mahdollista etsiä suurin/pienin arvoja

- `Map`
    - Kokoelma avain-arvoparien säilyttämiseen ja hakemiseen
    - Jokaista avaimen arvoa vastaa nolla tai yksi toinen arvo
    - Esimerkkejä: hetu => henkilö, nimi => arvosana
    - Avaimet ovat uniikkeja, eli samassa Map-oliossa ei voi olla kaksi samaa avainta
    - Yleisiä toteutuksia
       - `HashMap` - hajautustaulu
         - Avain-arvoparit säilytään taulukossa, yksittäisen avain-arvoparin indeksi lasketaan avaimen hajautusarvosta `hashCode`
         - Uusien arvojen lisääminen nopeaa O(1), haku nopeaa O(1), mutta riippuu hajautusfunktiosta
         - Avain-arvoparien järjestys ei ole kiinteä
       - `LinkedHashMap` - hajautustaulu, mutta avain-arvoparien järjestys pysyy kiinteänä
    - Lisäksi seuraavia rajapintoja
       - `SortedMap` - hajautustaulu, mutta avaimet aina järjestetty halutussa järjestyksessä
          - Valmis toteutus Javassa: `TreeMap`
       - `NavigableMap` - hajautustaulu, mutta voidaan hakea suurinta/pienintä avaimen arvoa

- `Stack`, `Queue`, `Deque`
   - Kokoelmatyypit, jotka mahdollistavat alkioiden lisäämistä ja ottamista pois eri järjestyksessä
   - `Stack`: LIFO
        - Huom: Javan historian takia `Stack` on luokka eikä sitä kannata käyttää ellei kyse ole monisäikeisestä ohjelmasta. Sen sijaan suositus on käyttää `Deque` rajapintaa
   - `Queue`: FIFO
        - `ArrayDeque`, `LinkedList`, `PriorityQueue`
   - `Deque`: LIFO ja FIFO
        - `ArrayDeque`, `LinkedList`, `PriorityQueue`