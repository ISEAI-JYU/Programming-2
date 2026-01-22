Jatketaan edellistä tehtävää. Peri `Tuote`-luokasta myös luokka `Elektroniikka`.

Lisää erityispiirteitä kuhunkin aliluokkaan:

 * `Vaate`: attribuutti `String koko` (esim. "M", "L", jne.), metodi
   `sovita(String sovittajanKoko)`, joka tulostaa, onko vaate sopiva
   sovittajalle.
 * `Elektroniikka`: attribuutti `int takuuKuukausina` (esim. 24), metodi
   `int takuutaJaljella(int kuukausiaKulunut)` palauttaa montako kuukautta
   takuuta on jäljellä (tai 0, jos takuu on umpeutunut).
 * `Ruoka`: attribuutti `String parastaEnnen` (esim. "2026-01-31"), ja metodi
   `syo()`, joka tulostaa "Nautit ruoan, jonka viimeinen käyttöpäivä on
   YYYY-MM-DD." (korvaa YYYY-MM-DD `parastaEnnen`-arvolla).

Huomaa, että perivien luokkien konstruktoreissa tulee nyt kutsua yläluokan
konstruktoria oikeilla arvoilla, sekä asettaa omat attribuutit.

Tehtäväsivulla on valmiiksi annettuna pääohjelma. Käytä sitä luokkiesi
testaamiseen. Se ei saa tuottaa käännös- tai ajonaikaisia virheitä. Voit
kuitenkin halutessasi lisätä pääohjelmaan omaa koodiasi. 
