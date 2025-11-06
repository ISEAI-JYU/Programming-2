# Listarakenteet

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

- **Esimerkki:** (ajatus) Toteutetaan esim. kokoelma kokonaislukutaulukosta ja sille pakolliset metodit `contains`, `containsAll`, `equals`, `hashCode`, `isEmpty`, `iterator`, `size`, `toArray` (?, ehkä)