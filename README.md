## 3. zadanie
# Aplikácia (projekt) 
### Databázové systémy (predmet)
#### Richard Mocák

Slovenská technická univerzita v Bratislave,

Fakulta informatiky a informačných technológií

## Zadanie projektu
Filmový portál umožňuje registráciu použivateľov. 
Použivateľ može na portáli prehľadávať filmy. Môže vytvoriť
zoznam filmov, kde si ukladá filmy ktoré ho zaujímajú a chce sa 
k nim vrátiť neskôr. Použivateľ môže udržiavať viacero zoznamov 
filmov.
Ak si pozrie film, označí ho za videný.
Použivateľ môže nájsť nový film pomocou prehľadávania filmov podľa 
premiéry, roku vydania, hodnotenia, jazyku a
tiež podľa samotného názvu. Okrem základných informácií, sa
môže dozvedieť o všetkých detialoch ako je dĺžka, žáner, 
popis a tiež je
zobrazené kompletné personálne obsadenie vo filme od hercov
až po scénaristov. Každá osoba z filmovej produkcie má vytvorený
svoj profíl aj s kompletnou usporiadanou filmografiou.
Ak je film práve teraz premietaný v kinách, tak je pri filme zahrnuté
informácia o premietaniach filmu vo vybraných kinách a
cena za premietanie. Každé kino má svoj profil a program.

## Scenáre použitia
V aplikácií figurujú maximálne 2 aktéri : **Používateľ** a  **Admin**. Pre jednoduchšiu implementáciu, sú v aplikácii všetky informácie zlúčené a prístupné pre každého používateľa aplikácie. Scenáre ale boli tvorené z oddeleného hľadiska. Aby sa dosiahlo požadovaná funkcionalita v hlavných scenároch, ktoré sú očíslované nižšie tak boli použité viaceré podscenáre, ktoré sú opísané pod hlavnými scenármi. Pri každom Hlavnom scenári sú spomenuté použité podscenáre.

### Hlavné scenáre:
1. Ako používateľ, chcem mať prehľad o najnovších filmoch, 
z ktorých si môžem vyberať podľa jazyka vo filme a hodnotenia aby som sa neskôr dozvedel viac informacií.
- Aplikácia nájde a zobrazí v tabuľke názov, hodnotenie a jazyk najnovších filmov zoradených podľa premiéry a stránkovaných po 100 záznamov.
    

2. Ako používateľ, chcem vedieť detailné informácie o filme ako je popis, dĺžka v minútach, žáner, premiéra, herecké obsadenie, 
premietanie v kine a ďalšie.
- Použivateľ klikne na vybraný film.
- Aplikácia zobrazí všetky základné údaje o filme, zoznam personálného obsadenia v tabuľke a údaje o premietaní v kinách taktiež v tabuľke. 
    
    (Boli použité tieto podscenáre : **Zobraz Personálne obsadenie filmu**, **Zobraz premietanie filmu v kinách**)


3. Ako používateľ, chcem poznať najúspešnejši osoby z filmovej branže za zvolený rok.
- Používateľ zadá do vstupného poľa zvolený rok. Klikne na tlačidlo.
- Aplikácia nájde a vypíše 15 najúspešnjších tvorcov za zvolený rok. Sú usporiadaní podľa počtu filmov, v ktorých účinkovali a potom abecedne podľa prezviska.

4. Ako admin, chcem odstrániť z portálu záznamy o zvolenom filme.
- Admin, klikne na tlačidlo Zmazať záznam.
- Aplikácia zmaže všetky záznamy o filme v databáze a vráti sa do Administrácie.

5. Ako admin, chcem upraviť základné informácie o filme.
- Admin dvojklikne na zvolený film v tabuľke.
- Aplikácia otvorí scénu s vstupnými poliami, kde figurujú detailné informácie o zvolenom filme. Aplikácia tiež inicializuje možnosti pre výber Žánru a Jazyku vo filme podľa databázy.
- Admin vloží nové alebo pozmení základné informácie o filme. 
- Admin klikne na tlačidlo Uložiť zmeny.
- Aplikácia uloží nové hodnoty a vráti použivateľa do Administrácie.
    
    (Aby mohol Admin vybrať zvolený film, bol použitý aj podscenár **Vyhľadávanie filmu podľa názvu**. V podscenároch **Nájdi všetky Žánre** a **Nájdi všetky Jazyky** sa nájdu možnosti pre výber. )


6. Ako admin, chcem pridať novy záznam o filme spolu s hereckým obsadením.
- Admin klikne na tlačidlo Pridať nový záznam
- Aplikácia otvorí scénu s vstupnými poliami pre základné údaje o filme a s modulom pre načítavanie personálneho obsadenia vo filme. Aplikácie tiež načíta možnosti pre výber Žánru, Jazyka filmu a Obsadenia pri vytváaraní personálneho obsadenia.
- Admin vyplní základné údaje alebo vyberie z vypísaných možností.
- Admin vyhľadá osobu, vyplní údaje o obsadení vo filme a pridá do personálneho obsadenia.
- Admin klikne na tlačidlo Uložiť film.
- Aplikácia v transakcii uloží záznam o filme a záznam o každom obsadení osoby vo filme. Potom vráti Admina do Administrácie.

    (Bol použitý podscenár **Vyhľadaj osobu podľa priezviska** na vyhľadávanie osôb v systéme. Ešte boli použité aj podscenáre **Nájdi všetky Žánre**, **Nájdi všetky Jazyky** a **Nájdi všetky Obsadenia** aby zobrazil možnosti pre výber Adminovi.)


### Podscenáre :

**Zobraz Personálne obsadenie filmu** - Aplikácia nájde všetky osoby zahrnuté pri tvorbe filmu a ich obsadenie vo filme. Vypíše ich Meno, Priezvisko, Pozíciu a Názov postavy do tabuľky.

**Zobraz premietanie filmu v kinách** - Aplikácia nájde všetky premietania filmu v kinách. Vypíše do tabuľky názov kina, začiatok premietania a cenu vstupného pre dospelých a pre študentov.

**Vyhľadávanie filmu podľa názvu** - Aplikácia nájde všetky filmy v systéme, ktoré sa začínajú s vloženým reťazecom znakov. V tabuľke zobrazí usporiadané podľa abecedy informácie ako názov filmu, hodnotenie a jazyk. Tabuľka je stránkovaná po 100 záznamov.

**Vyhľadaj osobu podľa priezviska** - Aplikácia nájde všetky osoby v systéme, ktorých priezvisko sa začína (alebo zhoduje) s vloženým reťazecom znakov. Do tabuľky sú je vložené meno, priezvisko a vek. Záznamy nie sú strákované.

**Nájdi všetky Jazyky** - Aplikácia nájde zoznam všetkých Jazykov figurujúcich v systéme.

**Nájdi všetky Žánre** - Aplikácia nájde zoznam všetkých Žánrov figurujúcich v systéme.

**Nájdi všetky Obsadenia** - Aplikácia nájde zoznam všetkých Obsadení figurujúcich v systéme.


## Logický a fyzický model



## Implementácia 

### Špecifikácia aplikácie a databázy
Aplikáciou som sa rozhodol naprogramovať v JAVE s cieľovou verziou 1.8 a pre prácu s grafickým rozhraním som využil základný balík JAVAFX 2.0, ktorý je súčasťou JavaDevelompmentKit -u. Jedná sa o PostgreSQL databázu *filmovy_portal*, s ktorou lokálne komunikujem pomocou JDBC ovládača, ktorý je pripojený k aplikácii ako JAR súbor.

### Štruktúra zdrojových súborov a aplikácie
Keďže som sa rozhodol čo najviac využiť vymoženosti JAVAFX aplikácie, tak som rozdelil jednotlivé funkcionality do osobitných balíkov a osobitných tried a snažil som sa dodržať princípy architúry so vzorom MVC (Model View Controller).

Všetky zdrojové súborý sa nachádzajú v jednom hlavnom balíku. Logika a reakcie na správanie používateľa sú implementované v *Controller* triedach, ktoré sú uložené priamo v hlavnom balíku. Triedy, ktoré používam na uchovávanie údajov počas behu programu a predstavujú entity sú umiestnené v balíku **model**. Pre prácu s databázou bol zriadený balík **dbmanagment**, kde sú uložené triedy, ktoré komunikujú s databázou alebo spracúvajú dáta vrátené z databázy. Samotné grafické rozhranie je vďaka JAVAFX uložené v **fxml** súboroch, ktoré sa nachádzajú v *resources* priečinku. 

**Controller** triedy sú prepojené s grafickým rozhraním (všetky vstupné textové polia, nadpisy, výber možností, výber dátumu, tabuľky, stĺpce ...) vďaka @FXML anotáciam, ktoré su vymoženosťou JAVAFX aplikácií.
 
 Okrem komunikovania s grafickým rozhraním, komunikuje *Controller* aj s databázou vďaka **DBConnector** objektu, ktorý je vytvorený pri spustení aplikácie a agreguje ho každý *Controller*. Kvôli práci s databázou musí *Controller* dáta aj ukladať aby k nim mohol pristupovať počas behu programu a aby o nich mal používateľ prehľad. Preto Controller vytvára objekty z entity tried, ktoré sú uložené v balíku **model**. Tieto triedy často obsahujú iba základne atribúty, ktoré su skoro zhodné s atribútmi entít (tabuliek) v databáze. Niekedy sú tieto atribúty implementované ako **Property** atribúty (StringProperty, IntegerProperty, ...). Property atribúty umožňujú obyčajnú prácu s hodnotami ako obyčajné typy String, Integer, Double,... ale navyše umožňujú ľahšie sledovanie zmien a prepojenie medzi atribútmi entity triedy a stĺpcami pri práci s tabuľkami. Toto využíva Controller pri vypisovaní do grafického prostredia a pri pristupovaní k údajom počas behu programu. 

### Práca s databázou
S databázou sa pracuje v triedach z balíka **dbmanagment**. Najdôležitejšia je trieda **DBConnector**. Jej atribút **source** typu *PGSimpleDataSource* slúži na získavanie pripojenia k databáze. V konštruktore triedy sú pre *source* nastavené do atribútov informácie potrebné k prístupu do databázy. Jedná sa o password, userName, databaseName, serverName. Až po tejto inicializácií je možné získať pripojenie do databázy. 

V jej metódach je implementovaná celá komunikácia s databázou:

 ##### 1. select(String selectStatement, Parser parser)
 Slúži  na získavanie dát z databázy pomocou SQL SELECT príkazov, ktoré sú obsahom *selectStatement* ako vstupný parameter. Zo **source** sa získa pripojenie a vytvorí sa konekcia. Vykoná sa príkaz a databáza vráti ResultSet obsahujúci výsledné riadky. Tu sa dostaneme k druhému parametru *Parser*. 
 
 *Parser* je rozhranie z balíka *dbmanagment* a jeho jedinou metódou, ktorú je potrebné implementovať je processRow(ResultSet rs). Preto keď databáza vráti ResultSet, tak pre každý riadok výsledku pošleme tento ResultSet do metódy processRow(), ktorá vždy vytiahne potrebné stĺpce z ResultSet-u a vráti požadovaný objekt, ktorý je potrebný pre logku aplikácie ako návratovú hodnotu. Tieto objekty pre logiku aplikácie sú po vrátení z parsera pridávane do **ObservableList result** a po spracvaní všetkých riadkov vrátené ako návratová hodnota samotnej metódy **select**.
  
 Toto umožňuje všeobecnejšiu prácu so Select príkazmi. Stačí len napísať SQL select príkaz a vytvoriť triedu (aj anonymnú), ktorá implementuje rozhranie *Parser* a poslať ju ako parameter do metódy *select*. 
 
 Pri realizovaní navrhnutých scenárov som práve využil tento mechanizmus s anonýmnýmii triedami. Pri vykonávaní *SELECT* príkazov som posielal do metódy *select()* práve anonýmnú triedu *Parser*, ktorá implementovala metódu *parseRow()* a vrátila vždy potrebnú entitu ako návratovu hodnotu. Niektoré selekty sa však využívali častejšie a preto som do balíka **dbmanegment** priamo vytvoril triedy, ktoré implementovali rozhranie *Parser* a tým som obmedzil opakovanie kódu. 
 
 Po skončení komunikácie s satabázou sa uzavrie pripojenie.
 
 
##### 2. execute(String statement)
V tejto metóde sa ako parameter posiela ľubovoľný SQL skript. Na začiatku sa zase získa pripojenie na databázu z atribútu **source**. Po pripojení sa spustí SQL skript ako statement. Nečaká sa na žiadny ResultSet. 
  
Táto metóda slúži na jednoduchšie typy SQL príkazov, ako napríklad *UPDATE* alebo *DELETE*, ktoré nepotrebujú spracovať výsledky po vykonaní skriptu.
  
Takto ma táto metóda zase veľmi všeobecné využitie.

Po skončení komunikácie s databázou sa uzavrie pripojenie.


##### 3. insertTransaction(Movie movie, ObservableList personsInMovie)
Oproti predchádzajúcim metódam, má *insertTransaction()* veľmi špecifické a jednoznačné využitie. Slúži na vloženie nového záznamu o filme do databázy a potom naviazanie cudzieho kľúča daného filmu pri vložení záznamov o osobách vystupujúcich vo filme do databázy. 
 
Na začiatku sa zase získa pripojenie zo **source**. 
Následne sa nastaví **autoCommit** na **false**, čo zabezpečí atomickosť vykonania týchto mnohých INSERT príkazov za sebou. Najprv sa vykoná vloženie údajov o filme do príslušnej tabuľky v databáze. Potom sa pre každú osobu vo filme v liste *personsInMovie* vytvorí nový záznam v tabuľke. Na konci sa úspešné vykonanie INSERT príkazov potvrdí metódou **commit()**.

Po skončení komunikácie s databázou sa uzavrie pripojenie.

### Implementácia scenárov
Všetky scenáre sa mi podarilo implementovať do 4 Scén grafického rozhrania (Podľa dokumentácie JAVAFX -> Scéna je kontajner, ktorý obsahuje všetko čo sa nachádza na obrazovke - textové polia, tlačidlá, nadpisy, tabuľky, .... Táto scéna sa môže vložiť do Stage(Okna). Jeden Stage ukazuje vždy len 1 aktuálnu scénu ale môže tieto scény vymienať. )

Pre každú scénu je vytvorený jeden FXML súbor, v ktorom je uložené grafické rozhranie a je spojený s **Controller** triedou. Prejdem teraz každú scénu, kde pod obrázkom grafického rozhrania rozoberiem všetky scenáre implementované v danej scéne. Tak popíšem všetky scenáre, ktoré sú vždy implementované v prislúchajúcej *Controller* triede. Prvá scéna sa spustí v triede Main a v metóde *start()*. Ďalšie scény sa už podľa logiky aplácie prepínaju v samotných *Controller* triedach.

#### 1. Scéna - Domov
- controller : UserController
- fxml súbor : user_pane.fxml
 
 OBRAZOK
 
 Ešte pred tým, než sa zobrazí Scéna a samotné okno sa vykoná inicializácia v metóde **init()**. Táto metóda sa vyskytuje v každej Controller triede a slúži na to aby sa inicializovali nejaké objekty, aby sa načítali dáta z databázy a aby sa vypísali do grafického prostredia pred tým než sa Scéna zobrazí používateľovi.
 
 ##### hlavný scenár č.1
 Na ľavej strane je vidieť tabuľku *Najnovšie filmy*. Táto tabuľka implemementuje 1. hlavný scenár, kde použivateľ dostáva prehľad o najnovších filmoch s informáciou o názve, jazyku a hodnotení. Získanie a zobrazenie dát do tabuľky su implementované v metóde **initNewestMovieTable()**. Najprv sa napíše *SELECT* príkaz, následne sa vytvorí Anonýmná trieda Parser, ktorá spracuje výstup z databázy a cez referrenciu na objekt **dbconnector** sa vykoná *select()*. Návratený zoznam *ObservableList moviesObs*, v ktorom sú vytvorené objekty z triedy **ThumbnailMovie** je zobrazený do tabuľky. 

 Takto vyzerá SELECT príkaz, ktorým sa získavajú najnovšie filmy.
 
        SELECT f.id, f.nazov AS nazov, f.hodnotenie_imdb AS hodnotenie, 
        k.skratka AS jazyk FROM film f 
        JOIN krajina_povodu k ON k.id = f.krajina_povodu_id
        ORDER BY premiera DESC
        LIMIT **limit**  OFFSET ** page*limit **;

V tomto scenári sú tieto najnovšie filmy strankované. Pomocou tlačidiel *Predchádzajúce* a *Ďalšie* sa premenná **page** zvyšuje o jedna alebo zníži o jedna. A pomocou OFFSET sa získajú filmy z ďalšej stránky. Pri prvej stránke je hodnota *page* = 0 a tak je OFFSET nulový a **limit** je po celý čas nastavený na 100 záznamov. Po druhej stránke je *page* rovný 1 a OFFSET = 100 a dostávame skutočne záznamy z druhej stránky.

##### hlavný scenár č. 2
Na pravej strane scény sa nachádza sekcia, v ktorej sú vypísané detailné informácie o zvolenom filme a implementujú 2. hlavný scenár. Ak použivateľ klikne na film v tabuľke s Najnovšími filmami, tak sa zavolá metóda **showMovieDetail(int movieId)** a pošle sa do nej ID zvoleného filmu z tabuľky. V metóde sa príjme parameter, ktorý predstavuje primárny kľúč v databáze a podľa, tohto kľúča sa vykoná SELECT príkaz, ktorý si vyžiada všetky detailné informácie o filme. Vytvorí sa objekt **Movie movie** a z neho sá nastavie textové výpisy. 

Okrem detailnych informácií o filme sa v pravej polovici Scény, objavujú aj informácie o **Personálnom obsadení** a o **Premietaní v kinách**. Tieto implementujú podscenáre *Zobraz Personálne obsadenie filmu* a *Zobraz premietanie filmu v kinách* a tiež sú implementované v metóde *showMovieDetail()*. 

Pre získanie personálneho obsadenia sa napíše SELECT príkaz do datábazy a použije sa **PersonInMovieParser** pre spracovanie výsledku z databázy. Zavolá sa **dbconnector.select()** a spracuje sa výsledok. Parser vytvára objekty entity **PersonInMovie** a takto vyzerá SELECT príkaz.

        SELECT ovf.id, o.meno, ovf.osoba_id, ovf.obsadenie_id,  o.priezvisko, 
        ob.nazov, ovf.meno_postavy FROM osoba_vofilme ovf 
        JOIN osoba o ON o.id = ovf.osoba_id 
        JOIN obsadenie ob ON ob.id = ovf.obsadenie_id
        WHERE ovf.film_id = **movie_id** ;
        
Vyberajú sa tu údaje z dvoch tabuliek a PersonInMovieParser spracuje všetky riadky a vytvorí objekty, ktoré sa dostanu do ObservableList a následne sa vypíšu dáta do tabuľky pre personálne obsadenie. Vypisuje sa meno, priezvisko, pozícia a rola osoby vystupujúcej vo filme.

Premietanie filmu v kinách sa získavá obdobným spôsobom. Implementácie je tiež súčasťou metódy *showMovieDetail*. Napíše sa SELECT príkaz a anonýmna trieda Parser spracuje dáta na vytvorené objekty, ktorú sa postupne pridávajú do Listu. Po spracovaní výsledku z databázy sa zapíšu údaje do tabuľky. Vypiše sa názov kina, začiatok premietania a cena (dospelý a študent osobitne).

##### hlavný scenár č. 3 
Na ľavej strane pod tabuľkou sa nachádza sekcia, kde sa nachádzajú informácie o najúspešnejších tvorcoch vo filmovej sfére. Implementujem 3. hlavný scenár, kde chce použivateľ poznať 15 najúspešnejších tvorcov za zvolený rok. Používateľ musí zadať do textového poľa platný rok a po stlačení tlačidla sa vykoná metóda *showTopCreators()*, kde sa spracuje rok z textového poľa a zavolá sa metóda **initTopCreatorsTable(int year)**. V metóde sa zavolá zase *dbconnector.select()*, kde sa posiela tento SELECT príkaz :

        SELECT o.meno, o.priezvisko, count(ovf.film_id) AS pocet 
        FROM osoba_vofilme ovf
        JOIN (SELECT id FROM film f 
        WHERE EXTRACT(YEAR FROM f.premiera) = **year** ) tmp ON tmp.id = ovf.film_id
        JOIN osoba o ON o.id = ovf.osoba_id
        GROUP BY o.id
        ORDER BY count(DISTINCT ovf.film_id) DESC, priezvisko ASC
        LIMIT 15;
 
 V SELEKT príkaze sa pracuje s troma tabuľkami a využíva sa tu GROUP BY a agregačná funkcia COUNT(). Záznamy sa usporiadajú najprv podľa počtu filmmov, v ktorých účinkovali a potom podľa priezviska abecedne. 
 
 Parser spracuje dáta z databázy na List objektov typu **TopCreator** a tento list je potom vypísaný do tabuľky. Vypisuje sa meno, priezvisko a počet filmov tvorcu.
 
 
 ##### Zvyšné tlačidlá 
 Po kliknutí na tlačidlo **Refresh** sa zavolá metóda *init()*, ktorá nanovo načíta údaje do tabuľky Najnovšie filmy a zabezpečí, že používateľ vidí najnovšie dáta.
 
 Po kliknutí na tlačidlo **Administrácia** aplikácia prejde do 2. scény.
 
 
#### 2. Scéna - Administrácia
- controller : AdminController
- fxml súbor : admin_pane.fxml

OBRAZOK

Pred zobrazením scény sa zase načítajú dáta z databázy a zavolá sa zase metóda **init()**. Metóda init() inicializuje tabuľku Najnovšie filmy, z ktorej získavame údaje rovnakým spôsobom ako pri Scéne č.1. Okrem načítavaní dát sa priraďujú stĺpce k príslušným hodnotám v obidvoch tabuľkách. Pre obidve tabuľky sa ešte pridá akcia, ktorá sa spustí ak bolo dvojkliknuté na riadok. Toto zabezpečí prechod na 4. Scénu - Úprava filmu. 

##### Podscenár Vyhľadávanie filmu podľa názvu
Aby mohol admin upraviť / vymazať film, bol implementovaný podscenár, kde hľadáme filmy, ktorých názov začína alebo sa zhoduje so zadaným reťazcom znakov. Implementáciu nájdeme na pravej časti scény. Admin zadá názov filmu do textového poľa a klikne na tlačidlo **Hľadaj film**. Zavolá sa metóda **showMoviesByTitle()**, ktorá vezme String z textového poľa. Kvôli pohodlnejšiemu vyhľadávaniu bol vytvorený index na názov filmu ale v tvare uppercase (iba veľké písmená). Preto sa hľadaný String prevedie na uppercase a vloží do pripraveného SELECT príkazu :
        
        SELECT f.id, f.nazov, f.hodnotenie_imdb, 
        (SELECT k.skratka FROM krajina_povodu k WHERE f.krajina_povodu_id = k.id) 
        AS jazyk  FROM film f
        WHERE upper(f.nazov) LIKE ' **upperTitle**% '
        LIMIT **searchLimit** OFFSET ** searchPage*searchLimit **

Podobne ako pri stránkovaní tabuľky v Domovskej Scéne aj tu je použitá metóda LIMIT - OFFSET. Za vložený reťazec sa pridá %, čo pri použití LIKE zabezpečí, že sa znak percenta môže nahradiť ľubovoľným reťazcom znakov. Preto dostávame výsledky aj kde je hľadaný reťazec len začiatočnou čaasťou názvu. Výsledky sa zapíšu do tabuľky pre výsledky vyhľadávania.

##### Tlačidlo Pridaj nový film
Po kliknutí na modré tlačidlo *Pridaj nový film* sa načíta nová Scéna č. 3 pre pridanie nového filmu. 




#### 3. Scéna - Pridaj nový film
- controller : NewMovieController
- fxml súbor : add_movie.fxml

OBRAZOK

Rovnako ako pri predošlých scénach sa pred zobrazením scény zavolá metóda init(). Teraz sa inicializujú comboBoxy, z ktorých admin vyberá jednu možnosť ale ostatné možnosti sú potiahnuté z databázy a vložené do comboBoxu. Keďže máme 3 comboBoxy tak táto inicializácia možností prebehne pre výber Jazyka, aj pre výber Žánru a ešte pre výber Pozície pri dopĺňaní osôb do personálneho obsadenia. Zodpovedajú tomu metódy **getGenresDB()**, **getLanguagesDB()** a **getPositionsDB()**. 
Nastaví sa akcia pre reakciu na dvojkliknutie na riadok tabuľky pre vyhľadávanie osôb.

##### hlavný scenár č. 6
Hlavný scenár č. 6 hovorí o tom, že Admin môže pridať nový záznam o filme spolu s detailnými informáciami o filme, a tiež že môže vytvoriť personálne obsadenie vo filme. 

Admin musí najprv vyplniť všetky detailné údaje ako je názov, rok vydania, vybrať žáner, ... Potom nasleduje pridávanie personálného obsadenia. 

Najprv admin vyberie pozíciu, ktorú chce pridať a ak sa jedná o herca / herečku vyplní aj rolu, ktorú vo filme predstavujú. 
 
##### Podscenár Vyhľadaj osobu podľa priezviska
Aby mohla byť osoba pridaná do personálneho obsadenia filmu musíme poznať o nej informácie. Admin zadá vstupný reťazec do textového poľa na pravej strane scény. Po kliknutí na tlačidlo **Hľadaj** sa spustí vyhľadávanie. Zavolá sa metóda **lookForLastName()**, ktorá vezme reťazec a podobne ako pri podscenári, kde sa vyhľadávali filmy podľa titulu, tak aj teraz prevedieme reťazec na uppercase. Tento reťazec doplníme do SQL príkazu, ktorý zase využíva LIKE operátor podobne ako pri vyhľadávaní titulu. Spracuje sa výstup z dtabázy a výsledok sa vypíše do tabuľky. Tento výsledok nie je stránkovaný, preto niekedy trvá dlhšie kým načíta vetky záznamy do tabuľky. 

Ak použivateľ našiel žiadanú osobu tak dvojklinknutím spustí dynamické pridanie osoby do fronty, ktoré prebehne v metóde **addNewPersonInMovie(Person person, String role, Position position)**. Osoba *person* sa získa z tabuľky po vyhľadaní, *String role* sa získa zo vstupu a *Position position * sa vezme ako vybratá možnosť z comboBoxu pre výber pozície. Vytvorí sa objekt **PersonInMovie** a pridá sa do zoznamu **personsInMovie**, ktorého prvky sú nanovo zobrazené do tabuľky Aktuálneho obsadenia. 
   
 Po pridaní personálneho obsadenia klikne admin na tlačidlo **Uložiť** a spustí sa proces vkladania dát. Na tento proces sa použije transakcia. Najprv sa vytvorí objekt **Movie movie** s hodnotami načítanými zo vstupu od Admina. Zavolá sa metóda **dbconnector.insertTransaction(Movie movie, ObservableList personsInMovie)**, kde vložím novovytvorený *movie*  a pridáme aj list *personsInMovie*, ktorý dynamicky udržujeme.
 
 Detailnejšie info o tejto transkacii je napísané vyššie v sekcii *Práca s databázou*. 
 
 ##### Ostatné tlačidlá
 
 **Spať** ponúka návrat do predošlej scény (Admnistrácia) bez uloženia údajov. Dáta na novonačítanej scéne môžu byť zastarané a je potreba ich obnoviť cez *Refresh*.
 
 **Vymaž označený záznam** umožňuje dynamicky odstrániť prvok z neuloženého personálneho obsadenia v zozname *personsInMovie*. 
 
        
  
  
#### 4. Scéna - Uprav film
- controller : EditMovieController extends NewMovieController 
- fxml súbor : edit_movie.fxml

OBRAZOK

Táto scéna vyzerá veľmi podobne ako Scéna s pridávaním nového filmu a je to preto, že aj časť samotného grafického rozhrania a controller sú rovnaké. *EditController* dedí od *NewMovieController* a tým mu umožňuje pracovať s vstupnými textovými políčkami. 


Ako vždy aj v tomto prípade sa zavolá **init()** pred zobrazením scény. V tomto prípade sa zase vykonajú zaužívané inicializačné úlohy pre získanie možností do comboBoxov pre výber Žánru a výber Jazyka. Vďaka dedeniu môžme zavolať už raz implementované **genGenresDB()** a **genLanguagesDB()** bez duplicity kódu.
 
Ešte stále pred zobrazením scény sa zavolá **initValues(int movieID)** a prebehne zase SELECT príkaz do databázy, ktorý získa detailne infrmácie o filme podľa jeho primárneho kľúča *movieID*. Tieto aktuálne informácie sú vložené do textových poličiek. 

Po tomto sa zobrazí scéna. 

##### hlavný scenár č.5 
V tomto scenári Admin môže upraviť údaje o filme. Upraví informácie o filme v textových políčkach. Keď klikne na tlačidlo **Uložiť zmeny** zavolá sa metóda **saveMovie()**. V tejto metóde sa vyplní UPDATE príkaz s informáciami so vstupnýc políčok a bude meniť hodnoty iba ak sa jedná o záznam s rovnakým **movieID**. Takto vyzerá :

        UPDATE film SET nazov= '**title**', 
        hodnotenie_imdb=**rating**,
        dlzka_min=**minutes**,
        rok_vydania=**year**,
        popis='**description**',
        krajina_povodu_id=**language_id**,
        zaner_id=**genre_id**,
        premiera='**premiera.toString()**'
        WHERE id= **movie.getId()** ;
        
 Tento príkaz spustíme vďaka **dbconnector.execute(String statement)**. 
 Potom sa vrátime na 2. scénu (Administrácia).
  
 ##### hlavný scenár č.4
 Ak chce Admin odstrániť záznamy o filme z celej databázy tak musí kliknuť na tlačítko **Zmazať film**. 
 
 Pri implementovaní databázy sa nastavili všetky cudzie kľuče na tento film aby pri mazaní došlo k ON DELETE CASCADE. Toto zabezpečí, že ak vymažeme tento záznam z tabuľky *film* a v tabuľke *osoba_vofilme* máme informácie o personálnom obsadení do filmu, ktorý sa aktuálne vymazuje, tak sa všetky tieto riadky s referenciou na mazaný film vymažú tiež. 
 
 Preto stačí iba jeden obyčajný DELETE príkaz : 
 
        DELETE FROM film WHERE id=**movie.getId()**;
        
 Po vykonaní sa v databáze už určite nenáchadza ani stopa po tomto filme. Ak sa príkaz vykoná správne tak je admin vrátený späť do 2. scény (Administrácie).
 
 
 Tlačidlo **Späť** vráti Admina späť do 2. scény (Administrácie) bez uloženia zmien. 


## Zhodnotenie