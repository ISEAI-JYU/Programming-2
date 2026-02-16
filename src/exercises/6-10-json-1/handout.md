 1. Tee uusi Maven-projekti, joka käyttää Jackson-kirjastoa JSON-tiedostojen
   käsittelyyn. 
 2. Lisää `pom.xml`-tiedostoosi tarvittava riippuvuus.
 3. Lataa [henkilot.json](https://raw.githubusercontent.com/ohj-perus-jy/ohj2/refs/heads/main/src/exercises/6-10-json-1/henkilot.json) ja tallenna se projektiisi samaan kansioon kuin
      missä koodisi on. 
 4. `Henkilo`-luokka tai vastaava record, jolla on kentät `String nimi`, `int
    ika`, `String kaupunki`.
 5. Lue tiedosto `henkilot.json` ja muuta se listaksi `Henkilo`-olioita.
 6. Suodata mukaan vain vähintään 18-vuotiaat.
 7. Tulosta heidän nimensä, ikänsä ja kaupunkinsa.

Muista varmistaa, että tallennat `henkilot.json`-tiedoston samaan kansioon kuin
missä koodisi sijaitsee. Tarkista sitten ajokonfiguraatiostasi, että
työskentelyhakemisto on sama kuin koodisi kansio, jotta tiedosto löytyy.

Saat henkilöiden tiedot tarvittaessa auki tästä: 

<details>
  <summary>henkilot.json</summary>

[
  { "nimi": "Maija Laine", "ika": 25, "kaupunki": "Jyväskylä" },
  { "nimi": "Matti Virtanen", "ika": 30, "kaupunki": "Tampere" },
  { "nimi": "Liisa Niemi", "ika": 17, "kaupunki": "Helsinki" },
  { "nimi": "Pekka Korhonen", "ika": 41, "kaupunki": "Oulu" },
  { "nimi": "Aino Salmi", "ika": 22, "kaupunki": "Turku" },
  { "nimi": "Jari Heikkinen", "ika": 19, "kaupunki": "Kuopio" },
  { "nimi": "Sari Lehto", "ika": 16, "kaupunki": "Lahti" },
  { "nimi": "Oskari Mäkinen", "ika": 28, "kaupunki": "Espoo" },
  { "nimi": "Emilia Ranta", "ika": 33, "kaupunki": "Vantaa" },
  { "nimi": "Teemu Koski", "ika": 45, "kaupunki": "Pori" },
  { "nimi": "Noora Aalto", "ika": 18, "kaupunki": "Joensuu" },
  { "nimi": "Kalle Hämäläinen", "ika": 52, "kaupunki": "Rovaniemi" }
]
</details>