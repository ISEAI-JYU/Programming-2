# JavaFX perusteet

## Ensimmäinen JavaFX-sovellus

catalog: Maven central -> hakuun jfx -> io.github.sosuisen:jfx-sss-fxml

Vaihda java ja javafx-versiot 25. 

Vaihda advaced settingsin alta groupIID ja artifactID omiksesi. Käytämme tässä
groupid:tä `fi.jyu.ohj2.todo` ja artifactid:tä `Todo`.

Launcher -> run -> luo ajokonfiguraation. 

Klikkaa run, tulee tyhjä ruutu

## JavaFX-sovelluksen rakenne

JavaFX-sovellus koostuu yleensä kolmesta pääkomponentista: pääluokka, ulkoasu ja
kontrolleriluokka. 

Pääluokka on Java-luokka, joka toimii sovelluksen käynnistyspisteenä.
Esimerkissämme se on `App.java`, jota kutsutaan `Launcer.java`-tiedostossa
olevasta perinteisestä `main()`- pääohjelmasta. Se perii `Application`-luokan ja
sisältää main-metodin, joka käynnistää sovelluksen. Pääluokka määrittelee, miten
sovellus luo ja näyttää ikkunan, ja se on vastuussa sovelluksen elinkaaren
hallinnasta.

Ulkoasu määritellään FXML-tiedostossa, joka on XML-pohjainen kuvaus
käyttöliittymästä. FXML-tiedosto määrittelee, millaisia komponentteja ikkunassa
on ja miten ne on järjestetty. 

Kontrolleriluokka on Java-luokka, joka sisältää logiikan
käyttöliittymänkomponenttien käsittelyyn. Esimerkkiprojektissamme tämän nimi on
`MainController.java`. Kontrolleriluokka on yhteydessä FXML-tiedostoon, ja se
määrittelee, miten sovellus reagoi käyttäjän syötteisiin ja tapahtumiin.
Kontrolleriluokka sisältää metodeja, jotka on sidottu FXML-tiedoston
komponentteihin, kuten painikkeisiin ja tekstikenttiin, ja nämä metodit
määrittelevät, mitä tapahtuu, kun käyttäjä vuorovaikuttaa käyttöliittymässä
olevien komponenttien kanssa. Käytännössä kontrolleriluokkia on yleensä yksi per
FXML-tiedosto, ja ne toimivat ikään kuin "välittäjinä" FXML:n ja pääluokan
välillä.

## Stage, Scene ja Node

Stage on ikkunan pääkomponentti, joka sisältää kaikki muut komponentit. 

Scene on ikkunan sisältö, joka koostuu erilaisista graafisista elementeistä,
kuten painikkeista, tekstikentistä ja kuvista. Nämä elementit järjestetään
layout-pohjiin, kuten VBox tai HBox, jotka määrittävät niiden asettelun
ikkunassa.

Node on JavaFX:n peruskomponentti, joka toimii pohjana kaikille
graafisilleelementeille. Esimerkiksi Button, Label ja TextField ovat kaikki
Node-luokan aliluokkia.

