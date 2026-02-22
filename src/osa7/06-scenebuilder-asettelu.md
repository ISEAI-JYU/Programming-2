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


<task>
  <task-title>Tehtävä 7.6: TODO-ohjelma, vaihe 6. <points>1 p.</points> </task-title>
  <handout>

{{#include ../exercises/7-6-todo-6/handout.md}}

  </handout>
  <task-link><a href="https://tim.jyu.fi/view/kurssit/tie/tiep111/tehtavat/osa7/tehtava6">Tee tehtävä TIMissä</a></task-link>
</task>