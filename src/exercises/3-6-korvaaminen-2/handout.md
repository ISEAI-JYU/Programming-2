Lisää `Auto`-luokkaan attribuutti `int ajokilometrit`. 
Lisää `Lentokone`-luokkaan attribuutti `int lentotunnit`.
Aseta kummatkin attribuutit konstruktorissa arvoihin 0.

Muuta `liiku()`-metodeja siten, että ne kasvattavat näitä arvoja. 
`Auto`-luokan `liiku()`-metodi kasvattaa `ajokilometrit`-attribuuttia 10:llä ja
`Lentokone`-luokan `liiku()`-metodi kasvattaa `lentotunnit`-attribuuttia 1:llä.

Lisää vielä `Ajoneuvo`-luokkaan metodi `naytaTiedot()`, joka tulostaa ajoneuvon
merkin. Ylikirjoita tämä metodi `Auto`- ja `Lentokone`-luokissa siten, että ne
tulostavat **myös** ajokilometrit tai lentotunnit.