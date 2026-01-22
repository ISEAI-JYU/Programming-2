Laajenna luokkahierarkiaa edelleen. Lisää `SahkoAuto`-luokka, joka perii
`Elektroniikka`-luokan. 

Lisää luokkaan 

 * attribuutit
     * vakio `TOIMINTASADE_MAX`, joka ilmaisee maksimietäisyyden kilometreinä, jonka
sähköauto voi kulkea yhdellä latauksella. 
     * `private double akunKunto` (prosentteina; väliltä 0-100)
 * metodit
     * `lataa()`, joka heikentää akun kuntoa 0.1%:lla jokaisella latauskerralla.
     * `tulostaAutonTiedot()`, joka kutsuu ensin yliluokan
       `tulostaPerustiedot()`, sitten kutsuu yliluokan `testaaLaite()`, jonka
       jälkeen laskee nykyisen toimintasäteen (kaava: akunkunto / 100 *
       TOIMINTASADE_MAX), tulostaa akun kunnon prosentteina ja sitten
       toimintasäteen kilometreinä.
--- 