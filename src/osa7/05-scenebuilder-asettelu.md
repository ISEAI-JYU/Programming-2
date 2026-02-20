# SceneBuilder: Komponenttien asettelu


## Käyttöliittymän siistimistä

## Fiksumpi skaalautuminen

USE_COMPUTED_SIZE

USE_PREF_SIZE

Vai tartteeko jo aikaisemmin?

## AnchorPane vs Gridpane

DZ: Ei ehkä tarvita, koska pohjassa on nyt VBox?

AnchorPane ei osaa skaalatua, joten vaihdetaan se GridPaneen, ja laitetaan sen
sisään asiat. Vaihda buttonin row ja column indexit Layout-kohdassa. 

Skaalauden säätäminen niin että ikkunan pienentäminen ja suurentaminen ei
totallisesti hajota layoutia. 
