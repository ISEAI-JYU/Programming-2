Toteuta oma yksinkertainen hajautustaulu.

Käytä hajautustaulun päätietorakenteena taulukkoa. Voit käyttää törmäysten
käsittelyyn esimerkiksi listaa, eli samaan indeksiin osuvat alkiot laitetaan
siinä indeksissä sijaitsevaan listaan alkioista. Alkioita ei saa kadota
törmäysten yhteydessä.

Hajautustaulun kapasiteetilla voi olla oletusarvo tai se voi ottaa arvon vastaan
muodostajassa. Taulua ei tarvitse luoda uudelleen missään vaiheessa, eli sen
käyttöastetta ei tarvitse huomioida tai toteuttaa.

Javan `hashCode` voi palauttaa negatiivisen arvon, joten siitä kannattaa ottaa 
itseisarvo ennen indeksin laskemista.

Lisää metodi `hae`, joka hakee alkion hajautustaulusta sen avaimen perusteella.
Lisää myös metodit `lisaa` ja `poista` alkioiden lisäämistä ja poistamista
varten.
