# Versiohallinta

Tässä vaiheessa on hyvä hetki aloittaa versionhallinta. Käytämme
Git-versionhallintaa, joka on laajasti käytetty työkalu ohjelmistokehityksessä.
Jos et ole aiemmin käyttänyt Gitiä, lue aluksi
Ohjelmointi 1 -kurssin materiaalin
[Git-osio](https://ohjelmointi1.it.jyu.fi/git.html). Emme tässä vaiheessa
tarvitse vielä etävarastoa, joten voit ohittaa GitLab-etävarastoa käsittelevän
kohdan. 

Lyhyesti: Gitin avulla voit seurata koodiin tehtyjä muutoksia, tehdä koodista
varmuuskopion etävarastoon, työskennellä tiimin kanssa saman koodin parissa ja
paljon muuta. 

Osien 7 ja 8 aikana teet jokaisesta tehtävästä oman Git-commitin, joka kuvaa
tehtävän aikana tehtyjä muutoksia. 

Gitin käyttämiseen on monenlaisia käyttöliittymiä &ndash; myös IDEAssa on
omansa. Käytämme tässä kuitenkin komentoriviä, koska se on suhteellisen
universaali tapa käyttää Gitiä kaikissa ympäristöissä samalla tavalla. 

Aloitetaan versionhallinta luomalla Git-varasto projektille. Avataan komentorivi
ja navigoidaan projektin juurikansioon. Juurikansio on se kansio, jossa on
`src`-kansio ja `pom.xml`-tiedosto. Tyhjä Git-varasto alustetaan komennolla `git
init`.

TODO: Tähän Asciinema?

```bash
cd /polku/projektiin
git init
```

Saat ilmoituksen, että tyhjä Git-varasto on luotu. Projektin polku `...Path...`
on tietenkin erilainen omalla koneellasi.

```
Initialized empty Git repository in C:/...Path.../Todo/.git/
```

Ennen kuin teemme ensimmäisen commitin, meidän on kerrottava Gitille, mitä
tiedostoja haluamme seurata. Voimme tässä vaiheessa tehdä sen seuraavalla
komennolla:

```bash 
git add .
```

Tämä lisää kaikki nykyisessä kansiossa ja sen alikansioissa olevat tiedostot
seurantaan. 

Kirjoita nyt: 

```bash
git status
```

Saat listan tiedostoista, jotka on lisätty seurantaan. Pohjaprojektin mukana
tuli `.gitignore`-tiedosto, mikä pitäisi näkyä listassa ensimmäisenä. Tämä
tiedosto kertoo Gitille, mitä tiedostoja **ei** haluta seurata. Näin
varmistetaan, että esimerkiksi käännettyt `.class`-tiedostot tai IDEAn omat
asetustiedostot eivät päädy versionhallintaan. `.gitignore`-tiedostoa voi ja
kannattaa muokata tarpeen mukaan, jos halutaan jättää pois seurannasta muita
tiedostoja.

Nyt voimme tehdä ensimmäisen commitin, joka on kuin "snapshot" projektista
tietyssä vaiheessa. Commitin yhteydessä kirjoitetaan kuvaava viesti, joka
kertoo, mitä muutoksia on tehty. Yleensä ensimmäiselle commitille kirjoitetaan
viesti, kuten "Initial commit" tai "Projektin aloitus". 

```bash
git commit -m "Initial commit"
```

Tästä eteenpäin jokaisen tehtävän yhteydessä teet uuden commitin, jossa kuvaat
tehtävän aikana tekemiäsi muutoksia. Voit aivan hyvin tehdä useammankin
commitin, jos haluat. 

<task>
  <task-title>Tehtävä 7.2: TODO-ohjelma, vaihe 2. <points>1 p.</points> </task-title>
  <handout>

{{#include ../exercises/7-2-todo-2/handout.md}}

  </handout>
  <task-link><a href="https://tim.jyu.fi/view/kurssit/tie/tiep111/tehtavat/osa7/tehtava2">Tee tehtävä TIMissä</a></task-link>
</task>