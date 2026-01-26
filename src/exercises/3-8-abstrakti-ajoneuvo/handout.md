Muuta Tehtävän 3.6 `Ajoneuvo`-luokka ja sen `liiku()`-metodi abstrakteiksi. Jätä
`toString()`-metodi edelleen tavalliseksi (ei-abstraktiksi) metodiksi.

Muuta `Ajoneuvo` -luokka ja tiedosto `Moottoriajoneuvo`ksi ja tee tarvittavat muutokset. Tehdään luokkaan lisäksi seuraavia muutoksia:

1. Lisää `Moottoriajoneuvo` luokkaan metodi `kaynnista(String avaintunniste)`, joka tarkistaa ensin, onko moottori käynnissä, sen jälkeen onko avaintunniste oikein ja käynnistää moottorin, jos molemmat menevät läpi. Pitäisikö metodin ylikirjoitus sallia lapsiluokissa vai ei?
2. Lisää attribuutit `boolean kaynnissa` ja `String avaintunniste`. Mieti myös mikä on näiden näkyvyys ja pitäisikö käyttää avainsanaa `final`?
3. Luo mahdollisuus tehdä moottoriajoneuvoja, joihin tarvitaan `avaintunniste`, mutta jätä mahdollisuus tehdä moottoriajoneuvoja, joihin ei tarvita avaintunnistetta

Tee seuraavia muutoksia luokkiin `Auto` ja `Lentokone`

1. Päivitä konstruktori(t)
2. Tarkista `liiku()` metodeissa, että onko moottori käynnissä. Jos ei, tulostetaan tilanne, eikä tehdä sen jälkeen mitään.

Ota avuksesi TIM:issä oleva pääohjelma ja tee tarvittavia muutoksia omaan koodiisi.

