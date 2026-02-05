# Rekursio

> [!VAROITUS]
> Tämä osio julkaistaan 9. helmikuuta 2026.
> {{#include ../ei-julkaistu.md}}

> [!Osaamistavoitteet]
>
> - Ymmärrät miten rekursio toimii
> - Ymmärrät, miten rekursiota voidaan mallintaa pinon avulla
> - Rekursio, perus- ja induktiotapaukset, rekursiivinen tietorakenne (?). Hajota ja hallitse -periaate. Pinon käyttö rekursiossa.
> - Mahdollisesti jotakin dynaamisesta ohjelmoinnista (?)

## Yleisempi tapa ratkaista rekursiivisia ongelmia:
Käytä induktiota selvittääksesi, onko ongelmalla optimaalinen alistruktuuri, (engl. *optimal substructure*), jossa ahne valintaominaisuus(engl. *greedy choice property*)(eli aina valitsemalla lokaalin optimin päädytään globaaliin optimiin). Jos tosi --> Käytä ahnetta algoritmia (engl. *greedy algorithm*). Jos taasen ongelmassa on päällekkäisiä osaongelmia käytä hajoita ja hallitse-menetelmiä tai dynaamista ohjelmointia (Memoisaatiota molemmissa)(hajoita ja hallitse dynaamisen osajoukko). Muutoin käytä suoraviivaista menetelmää ratkaisujen läpikäymiseen.

(Tällä kurssilla käydään kuitenkin asiat x ja y)

# Rekursio
Rekursion voidaan ajatella olevan yksi ohjelmoijan työkaluista. Vaikka teoreettisesti ei ole olemassa ongelmia, jotka voidaan ratkaista vain rekursiolla, on ongelmia, jotka ovat valmiiksi rekursiivisessa muodossa, kuten puurakenteet. Tällöin rekursio voi olla helpoin tapa saattaa ratkaisu luettavaan muotoon, josta katsotaan seuraavaksi esimerkki:

```java
public class Solmu {
    int arvo;
    Solmu vasen;
    Solmu oikea;

    Solmu(int arvo) {
        this.arvo = arvo;
    }
}

public static int korkeus(Solmu juuri) {
    if (juuri == null) {
        return 0;
    }

    return 1 + Math.max(korkeus(juuri.vasen), korkeus(juuri.oikea));
}

void main() {
    //Muodostetaan binääripuu
    Solmu juuri = new Solmu(1);
    juuri.vasen = new Solmu(2);
    juuri.oikea = new Solmu(3);
    juuri.vasen.vasen = new Solmu(4);

    IO.println(korkeus(juuri));
}
```

Tarkastellaan seuraavaksi esimerkkiä, jossa lasketaan binääripuun korkeus iteratiivisesti, eli käyttämättä rekursiota:


```java
//-public class Solmu {
//-    int arvo;
//-    Solmu vasen;
//-    Solmu oikea;
//-
//-    Solmu(int arvo) {
//-        this.arvo = arvo;
//-    }
//-}

public static int puunKorkeusIteratiivisesti(Solmu juuri) {
    if (juuri == null) return 0;

    Queue<Solmu> jono = new LinkedList<>();
    jono.add(juuri);
    int korkeus = 0;

    while (!jono.isEmpty()) {
        int tasonKoko = jono.size();
        korkeus++;

        for (int i = 0; i < tasonKoko; i++) {
            Solmu nykyinen = jono.poll();
            if (nykyinen.vasen != null) jono.add(nykyinen.vasen);
            if (nykyinen.oikea != null) jono.add(nykyinen.oikea);
        }
    }

    return korkeus;
}
//-
//-void main() {
//-    //Muodostetaan binääripuu
//-    Solmu juuri = new Solmu(1);
//-    juuri.vasen = new Solmu(2);
//-    juuri.oikea = new Solmu(3);
//-    juuri.vasen.vasen = new Solmu(4);
//-
//-    IO.println(puunKorkeusIteratiivisesti(juuri));
//-}
```

Vaikka ongelma voitiin ratkaista iteratiivisesti, on iteratiivinen ratkaisu huomattavasti vaikeampi ymmärtää nopealla vilkaisulla.

## Mitä rekursio tarkoittaa yleisellä tasolla

Rekursiivinen ongelmanratkaisu voidaan jakaa kahteen vaiheeseen:

1) Perustapaus:
- Jos ongelma on riittävän helppo, ratkaise se ja palauta vastaus.

2) Rekursiivinen tapaus:
- Muunna ongelmaa hiukan helpommaksi ja välitä se seuraavalle ratkaisijalle

(Hiukan erilainen sanoitus)
1. Voinko ratkaista tämän nyt?
2. Jos en, miten teen ongelmasta helpomman ja lähetän sen eteenpäin?

- Ongelman määrittely itseään pienempien aliongelmien avulla
- Rekursiivisen funktion rakenne
    - Funktion kutsuminen itseään
    - Parametrien muuttuminen kutsujen välillä

- Esimerkkitehtäviä:
   - Faktoriaali
   - Fibonacci
   - Puun tai listan läpikäynti

## Rekursio pinon avulla
Kutsupino (call stack)
- Miten funktiokutsut tallentuvat pinoon
- Paikallisten muuttujien elinkaari

Rekursion eteneminen pinossa
- Kutsuvaihe (push)
- Paluuvaihe (pop)

Rekursion ja iteratiivisen ratkaisun vertailu
- Rekursio vs. silmukat

Muistin käyttö

```java
void lahtolaskenta(int n) {
    if (n == 0) return;
    IO.println(n);
    lahtolaskenta(n - 1);
}

void main() {
    lahtolaskenta(5);
}
```

- Milloin rekursio pysähtyy
- Tyypilliset virheet (puuttuva tai väärä perustapaus)
    - Ääretön rekursio --> Liian suuret kutsusyvyydet
    - Virheellinen perustapaus

Induktiotapaus (rekursiivinen askel)
- Ongelman pienentäminen
- Oikean etenemissuunnan valinta

## Rekursiiviset tietorakenteet
"Itsensä sisältävää" tietorakennetta voidaan kutsua rekursiiviseksi tietorakenteeksi. Esimerkkejä tällaisista ovat linkitetty lista, puut ja graafit.

- Rekursiiviset algoritmit tietorakenteille
    - Haku
    - Läpikäynti (DFS, preorder, inorder, postorder)

### Hajota ja hallitse
Sopii erityisen hyvin, jos ongelma jakaantuu riippumattomiin aliongelmiin.
- Periaatteen idea
    - Ongelman jakaminen osiin
    - Osaongelmien yhdistäminen
- Rekursion rooli hajota ja hallitse -menetelmässä
- Esimerkkejä algoritmeista
    - Merge sort
    - Quick sort
    - Puolitushaku (engl. *binary search*)

## Pinon käyttö rekursiossa
Rekursiossa pinoa hallinnoi ohjelmointikieli. Iteratiivisessa ratkaisussa sinä itse huolehdit pinon käytöstä.

- Implisiittinen pino (kutsupino)
- Eksplisiittinen pino
    - Rekursion simulointi itse toteutetulla pinolla

## Dynaaminen ohjelmointi?
Dynaaminen ohjelmointi on sekä matemaattinen optimointimetodi ja algoritminen paradigma. 
- Yhteys rekursioon
    - Rekursiivinen määrittely + muistin käyttö
- Päällekkäiset aliongelmat
- Muistitekniikat
    - Memoisaatio
    - Taulukointi (bottom-up)
- Esimerkkejä
    - Fibonacci optimoituna
    - Kapsäkkiongelma (engl. *knapsack problem*) (koliket)

# Ahne algoritmi

Ahne algoritmi on mikä tahansa algoritmi, joka noudattaa heuristiikkaa, jossa jokaisessa tilanteessa valitaan lokaali optimi. Katsotaan seuraavaksi esimerkki ahneesta algoritmista eurovaluutalle

```java
void main() {
    int[] tulos = ahneMenetelma(new int[]{1,2,5,10,20,50}, 4);
    IO.println(Arrays.toString(tulos));
}

// Oletetaan, että yksiköt ovat jo nousevassa järjestyksessä
private int[] ahneMenetelma(int[] valuutat, int tavoite) {
    List<Integer> tulos = new ArrayList<>();
    int jaljella = tavoite;

    for (int i = valuutat.length - 1; i >= 0; i--) {
        int valuutta = valuutat[i];

        int maara = jaljella / valuutta; //Otetaan niin monta kuin mahdollista
        for (int j = 0; j < maara; j++) {
            tulos.add(valuutta);
        }
        jaljella -= maara * valuutta;
    }
    // Tavoite ei mahdollinen
    if(jaljella != 0) return new int[0];
    return tulos.stream().mapToInt(Integer::intValue).toArray();
}
```
Useimmat nykyiset rahayksiköt ovat tarkoituksella suunniteltu siten (kuten euro), että ahne algoritmi antaa optimaalisen tuloksen. Esimerkiksi, jos meillä olisi yksiköt `1,3,4` ahne algoritmi antaa tavoitteelle 6 tuloksen `4+1+1`, eikä globaalia optimia `3+3`:

```java
void main() {
    int[] tulos = ahneMenetelma(new int[]{1,3,4}, 6);
    IO.println(Arrays.toString(tulos));
}

//-private int[] ahneMenetelma(int[] valuutat, int tavoite) {
//-    List<Integer> tulos = new ArrayList<>();
//-    int jaljella = tavoite;

//-    for (int i = valuutat.length - 1; i >= 0; i--) {
//-        int valuutta = valuutat[i];

//-        int maara = jaljella / valuutta; //Otetaan niin monta kuin mahdollista
//-        for (int j = 0; j < maara; j++) {
//-            tulos.add(valuutta);
//-        }
//-        jaljella -= maara * valuutta;
//-    }
//-    // Tavoite ei mahdollinen
//-    if(jaljella != 0) return new int[0];
//-    return tulos.stream().mapToInt(Integer::intValue).toArray();
//-}
```