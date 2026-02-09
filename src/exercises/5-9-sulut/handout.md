Kirjoita aliohjelma, joka tarkistaa merkkijonon sisältämien sulkujen
oikeellisuuden. Aliohjelman tulee tunnistaa, sulkeutuvatko kaikki sulut oikeassa
järjestyksessä ja onko jokaisella alkavalla sululla vastaava lopettava pari.

Tuetut sulkutyypit ovat:

 * Kaarisulut `( )`
 * Hakasulut `[ ]`
 * Aaltosulut `{ }`

Toimintalogiikka ja säännöt:

 * Sisäkkäisyys: Sulut voivat olla sisäkkäin (esim. ([])), mutta ne eivät saa
   mennä ristiin. Esimerkiksi `([)]` on virheellinen, koska sulut menevät
   ristiin.
 * Järjestys: Sulun on aina alettava ennen kuin se sulkeutuu.
 * Muut merkit: Merkkijono voi sisältää muitakin merkkejä (esim. kirjaimia tai numeroita), mutta aliohjelman tulee jättää ne huomiotta.
 * Tyhjä merkkijono: Tyhjä merkkijono katsotaan oikeelliseksi, ja siinä on 0 paria.

Paluuarvo:

 * Jos sulutus on kunnossa: Palauta löydettyjen sulkuparien lukumäärä (kokonaisluku).
 * Jos sulutus on virheellinen (yksikin pari puuttuu tai järjestys on väärä): Palauta luku -1.

Esimerkit:

| Merkkijono | Tulos | Selite                                             |
| ---------- | ----- | -------------------------------------------------- |
| ""         | 0     | Tyhjä syöte on validi, 0 paria.                    |
| "()"       | 1     | Yksi ehjä pari.                                    |
| "(())"     | 2     | Kaksi sisäkkäistä paria.                           |
| "([{}])"   | 3     | Kolme sisäkkäistä paria.                           |
| "a(b)c"    | 1     | Kirjaimet sivuutetaan, yksi pari.                  |
| "("        | -1    | Sulkeva pari puuttuu.                              |
| "(()"      | -1    | Yksi sulkeva pari puuttuu.                         |
| "()}"      | -1    | Ylimääräinen sulkeva sulku.                        |
| ")("       | -1    | Väärä järjestys (alkava sulku puuttuu alussa).     |
| "([)]"     | -1    | Sulut menevät ristiin (virheellinen sisäkkäisyys). |
