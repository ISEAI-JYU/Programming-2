Palauta tässä osan 7.5 perusteella edistetty projekti.

Kertaus tämän osan vaiheista:

- Tallenna tehtävät JSON-tiedostoon aina, kun käyttäjä lisää tehtävän tai
  muuttaa tehtävän tilaa.
- Lue tehtävät JSON-tiedostosta ohjelman käynnistyessä (jos tiedosto on
  olemassa). JSON-tiedoston tulisi näyttää suunnilleen seuraavalta (pois lukien
  luettavuutta varten lisätyt rivinvaihdot ja sisennykset):

    ```json
    [
      {
        "tehtava": "Osta maitoa",
        "tehty": false
      },
      {
        "tehtava": "Vie roskat",
        "tehty": true
      }
    ]
    ```


Kun vaihe on valmis,  muista tehdä `git add` muuttuneille tiedostoille ja `git
commit`. Palauta projektisi tiedostot.