# Joukko- ja jonorakenteet

> [!WIP]
> - Otetaan pohjaa seuraavista lähteistä
>    - <https://docs.oracle.com/javase/tutorial/collections/>
>    - <https://dev.java/learn/api/collections-framework/>
>    - <https://www.cs.helsinki.fi/u/ahslaaks/kkkk.pdf>

> [!Osaamistavoitteet]
>
> - Tunnet Java-kielen yleisimmät valmiit tietorakenteet HashSet
> - Rajapintaa vasten ohjelmointi: Set
> - Lyhyesti käydään läpi vaikutukset suorituskykyyn
> - Ymmärrät ym. tietorakenteiden keskeisimmät operaatiot ja niiden aikakompleksisuudet
> - Ymmärrät, miksi `hashCode` tarvitaan (HashMap tapauksessa ainakin)

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



- `Stack`, `Queue`, `Deque`
   - Kokoelmatyypit, jotka mahdollistavat alkioiden lisäämistä ja ottamista pois eri järjestyksessä
   - `Stack`: LIFO
        - Huom: Javan historian takia `Stack` on luokka eikä sitä kannata käyttää ellei kyse ole monisäikeisestä ohjelmasta. Sen sijaan suositus on käyttää `Deque` rajapintaa
   - `Queue`: FIFO
        - `ArrayDeque`, `LinkedList`, `PriorityQueue`
   - `Deque`: LIFO ja FIFO
        - `ArrayDeque`, `LinkedList`, `PriorityQueue`