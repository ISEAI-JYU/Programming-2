# Hakurakenteet

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
> - Tunnet Java-kielen yleisimmät valmiit tietorakenteet HashMap
> - Rajapintaa vasten ohjelmointi: Map
> - Lyhyesti käydään läpi vaikutukset suorituskykyyn
> - Ymmärrät ym. tietorakenteiden keskeisimmät operaatiot ja niiden aikakompleksisuudet
> - Ymmärrät, miksi `hashCode` tarvitaan (HashMap tapauksessa ainakin)

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