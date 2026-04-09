# Rithmomachie — Plateau, disposition initiale et pyramides

## 1. Plateau

### 1.1 Dimensions

Le jeu se déroule sur un plateau rectangulaire de :

* 16 colonnes
* 8 lignes

### 1.2 Repérage

Les cases du plateau sont repérées par des coordonnées :

* les colonnes sont numérotées de 1 à 16, de gauche à droite
* les lignes sont numérotées de 1 à 8, du haut vers le bas

Ainsi :

* la case (1,1) est en haut à gauche
* la case (16,8) est en bas à droite

### 1.3 Orientation des joueurs

* Le joueur noir (joueur 1) occupe les colonnes 1 à 4
* Le joueur blanc (joueur 2) occupe les colonnes 13 à 16

### 1.4 Apparence des cases

Les cases du plateau alternent visuellement (claires et foncées ou hachurées).

Cette alternance n’a pas d’effet sur les règles du jeu.

---
## 2. Pièces
Les valeurs peuvent apparaître plusieurs fois dans le jeu ; chaque occurrence correspond à une pièce distincte, même si elle partage la même valeur.

### 2.1 Types de pièces

Chaque pièce a :

* type : triangle, cercle, carré ou pyramide
* valeur : entier
* joueur : noir ou blanc
* coordonnée : (colonne, ligne) (placée dans la section 4 et en 2.2.1 et 2.2.2)
* composants (pour pyramides uniquement) : liste des pièces empilées avec leur type et valeur


### 2.2 Liste des pièces par joueur
#### 2.2.1 Joueur noir

| Type | Valeur | Coordonnée | Composants |
|------------|--------|------------|------------|
| Carré | 289 | (1,1) | - |
| Carré | 153 | (2,1) | - |
| Triangle | 81 | (3,1) | - |
| Carré | 169 | (1,2) | - |
| Pyramide | 91 | (2,2) | [(cercle,1),(cercle,4),(triangle,9),(triangle,16),(carré,25),(carré,36)] |
| Triangle | 72 | (3,2) | - |
| Triangle | 49 | (2,3) | - |
| Cercle | 64 | (3,3) | - |
| Cercle | 8 | (4,3) | - |
| Triangle | 42 | (2,4) | - |
| Cercle | 36 | (3,4) | - |
| Cercle | 6 | (4,4) | - |
| Triangle | 20 | (2,5) | - |
| Cercle | 16 | (3,5) | - |
| Cercle | 4 | (4,5) | - |
| Triangle | 25 | (2,6) | - |
| Cercle | 4 | (3,6) | - |
| Cercle | 2 | (4,6) | - |
| Carré | 81 | (1,7) | - |
| Carré | 45 | (2,7) | - |
| Triangle | 6 | (3,7) | - |
| Carré | 25 | (1,8) | - |
| Carré | 15 | (2,8) | - |
| Triangle | 9 | (3,8) | - |

Soit un total de 29 pièces (23 + 6 constitutifs de la pyramide)

#### 2.2.2 Joueur blanc

| Type | Valeur | Coordonnée | Composants |
|------------|--------|------------|------------|
| Triangle | 16 | (14,1) | - |
| Carré | 28 | (15,1) | - |
| Carré | 49 | (16,1) | - |
| Triangle | 12 | (14,2) | - |
| Carré | 66 | (15,2) | - |
| Carré | 121 | (16,2) | - |
| Cercle | 3 | (13,3) | - |
| Cercle | 9 | (14,3) | - |
| Triangle | 36 | (15,3) | - |
| Cercle | 5 | (13,4) | - |
| Cercle | 25 | (14,4) | - |
| Triangle | 30 | (15,4) | - |
| Cercle | 7 | (13,5) | - |
| Cercle | 49 | (14,5) | - |
| Triangle | 56 | (15,5) | - |
| Cercle | 9 | (13,6) | - |
| Cercle | 81 | (14,6) | - |
| Triangle | 64 | (15,6) | - |
| Triangle | 90 | (14,7) | - |
| Carré | 120 | (15,7) | - |
| Carré | 225 | (16,7) | - |
| Triangle | 100 | (14,8) | - |
| Pyramide | 190 | (15,8) | [(cercle,16),(triangle,25),(triangle,36),(carré,49),(carré,64)] |
| Carré | 361 | (16,8) | - |

Soit un total de 28 pièces (23 + 5 constitutifs de la pyramide)

### 2.3 Types de pièces simples

#### 2.3.1 Triangles

* Représentés par un triangle
* Portent une valeur numérique

#### 2.3.2 Cercles

* Représentés par un cercle
* Portent une valeur numérique

#### 2.3.3 Carrés

* Représentés par un carré
* Portent une valeur numérique

#### 2.3.4 Inventaire des pièces de chaque couleur
##### 2.3.4.1 Pièces Noires
* 8 cercles : 2, 4, 6, 8, 4, 16, 36, 64
* 8 triangles : 6, 9, 20, 25, 42, 49, 72, 81
* 7 carrés : 15, 25, 45, 81, 153, 169, 289
* 1 pyramide 91 composée de divers pions en plus de ceux listés ci-dessus (cf. 3.2.1)
* Soit 23 pièces indépendantes plus une pyramide composée de 6 composants.

##### 2.3.4.2 Pièces Blanches
* 8 cercles : 3, 5, 7, 9, 9, 25, 49, 81
* 8 triangles : 12, 16, 30, 36, 56, 64, 90, 100
* 7 carrés : 28, 49, 66, 120, 121, 225, 361
* 1 pyramide 190 composée de divers pions en plus de ceux listés ci-dessus (cf. 3.2.2)

* Soit 23 pièces indépendantes plus une pyramide composée de 5 composants.

---

## 3. Pyramides

### 3.1 Nature des pyramides

Une pyramide est une pièce unique occupant une case, contenant une liste de composants.
Les composants ne deviennent des pièces indépendantes que lorsqu’ils sont libérés par capture.

Les pyramides sont des pièces composites constituées de plusieurs pièces empilées.

Elles appartiennent chacune à un joueur.

---

### 3.2 Composition des pyramides

#### 3.2.1 Pyramide noire

La pyramide noire est composée au départ des composants suivants :

* cercle 1
* cercle 4
* triangle 9
* triangle 16
* carré 25
* carré 36

#### 3.2.2 Pyramide blanche

La pyramide blanche est composée au départ des composants suivants :

* cercle 16
* triangle 25
* triangle 36
* carré 49
* carré 64

---

### 3.3 Valeur des pyramides

La valeur d’une pyramide est toujours égale à la somme de ses composants.

* la pyramide noire a une valeur de départ de 91
* la pyramide blanche a une valeur de départ de 190

En cours de jeu, les pièces des pyramides peuvent être capturées (voir règles sur la capture). Par exemple, la pyramide noire pourrait perdre son cercle 1 et n'avoir plus qu'une valeur de 90. 

Les composants définissent la valeur de la pyramide ainsi que ses modes de déplacements possibles.

---

### 3.5 Multiplicité des pièces
En plus des pièces décrites en 2.3.4 et 2.3.5, il existe donc les pièces constitutives des pyramides (cf. 3.2.1 et 3.2.2).
Au final, les noirs ont donc
* 3 cercles de valeur 4, deux indépendants et un constitutif de la pyramide.
* 2 triangles de valeur 9 (un indépendant un constitutif de la pyramide)
* 2 triangles de valeur 16 (un indépendant un constitutif de la pyramide)
* 2 carrés de valeur 25 (un indépendant un constitutif de la pyramide)

Les blancs ont 
* 2 cercles de valeur 9, tous deux indépendants.
* 2 triangles de valeur 36 (un indépendant un constitutif de la pyramide)
* 2 carrés de valeur 49 (un indépendant un constitutif de la pyramide)

## 4. Disposition initiale
Cette section présente la disposition initiale sous une forme visuelle simplifiée.

### 4.1 Placement des pièces
Soit un tableau composé de 16 colonnes (1 à 16) et de 8 lignes (1 à 8).
Une case est représentée par un couple de nombre, selon la forme suivante (colonne, ligne).
Les colonnes des tableaux correspondent à la première coordonnée, les lignes à la seconde.
Par exemple, au départ, la case (3, 2) est occupée par un triangle noir de valeur 72

Légende des schémas ci-dessous :
* &#x2B24; : cercle
* &#x25B2; : triangle
* &#x25A0; : carré
* &#x25BC; : pyramide

#### 4.1.1 Disposition des pions noirs

|   | 1            | 2           | 3           | 4          |
|---|--------------|-------------|-------------|------------|
| 1 | &#x25A0; 289 | &#x25A0; 153 | &#x25B2; 81 |            |
| 2 | &#x25A0; 169 | &#x25BC; 91 | &#x25B2; 72 |            |
| 3 |              | &#x25B2; 49 | &#x2B24; 64 | &#x2B24; 8 |
| 4 |              | &#x25B2; 42 | &#x2B24; 36 | &#x2B24; 6 |
| 5 |              | &#x25B2; 20 | &#x2B24; 16 | &#x2B24; 4 |
| 6 |              | &#x25B2; 25 | &#x2B24; 4  | &#x2B24; 2 |
| 7 | &#x25A0; 81  | &#x25A0; 45 | &#x25B2; 6  |            |
| 8 | &#x25A0; 25  | &#x25A0; 15 | &#x25B2; 9  |            |


#### 4.1.2 Disposition des pions Blancs

|   | 13         | 14           | 15           | 16           |
|---|------------|--------------|--------------|--------------|
| 1 |            | &#x25B2; 16  | &#x25A0; 28  | &#x25A0; 49  |
| 2 |            | &#x25B2; 12  | &#x25A0; 66  | &#x25A0; 121 |
| 3 | &#x2B24; 3 | &#x2B24;  9  | &#x25B2; 36  |              |
| 4 | &#x2B24; 5 | &#x2B24; 25  | &#x25B2; 30  |              |
| 5 | &#x2B24; 7 | &#x2B24; 49  | &#x25B2; 56  |              |
| 6 | &#x2B24; 9 | &#x2B24; 81  | &#x25B2; 64  |              |
| 7 |            | &#x25B2; 90  | &#x25A0; 120 | &#x25A0; 225 |
| 8 |            | &#x25B2; 100 | &#x25BC; 190 | &#x25A0; 361 |


---

## 5. Remarques

* Toutes les valeurs numériques font partie intégrante du jeu et interviennent dans les règles de capture et de victoire.
* Les pyramides constituent des pièces particulières dont le comportement dépend de leur composition interne.
* Les règles de victoire, de déplacement et de capture ne sont pas décrites dans ce document et feront l’objet de sections séparées.
