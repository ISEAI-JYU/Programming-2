Tee aliohjelma, joka ottaa merkkijonon vastaan ja tarkistaa, onko merkkijonossa
jokaisella sululla pari. Sulut voivat olla (), [] ja {} ja ne voivat olla 
sisäkkäisiä. Muut merkit voi jättää huomiotta.

Jos kaikilla suluilla on pari, palauta parien lukumäärä.
Jos yhdeltäkin sululta puuttuu pari, palauta -1. 

Esimerkit:

| Merkkijono | Tulos  |
|------------|--------|
| ""         | 0      |
| "()"       | 1      |
| "(())"     | 2      |
| "([{}])"   | 3      |
| "("        | -1     |
| "(()"      | -1     |
| "()}"      | -1     |
