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
* composants (pour pyramides uniquement) : liste des pièces empilées avec leur type et valeur

#### 2.1.1 Triangles

* Représentés par un triangle
* Portent une valeur numérique

#### 2.1.2 Cercles

* Représentés par un cercle
* Portent une valeur numérique

#### 2.1.3 Carrés

* Représentés par un carré
* Portent une valeur numérique

## 3. Pyramides

### 3.1 Nature des pyramides

Une pyramide est une pièce unique occupant une case, contenant une liste de composants.
Les composants ne deviennent des pièces indépendantes que lorsqu’ils sont libérés par capture.

Les pyramides sont des pièces composites constituées de plusieurs pièces empilées.

Elles appartiennent chacune à un joueur.

---

### 3.2 Composition des pyramides

#### 3.2.1 Pyramide de 91
La pyramide est composée au départ des composants suivants :

* cercle 1
* cercle 4
* triangle 9
* triangle 16
* carré 25
* carré 36

#### 3.2.2 Pyramide de 190
La pyramide est composée au départ des composants suivants :

* cercle 16
* triangle 25
* triangle 36
* carré 49
* carré 64

---

### 3.3 Valeur des pyramides

La valeur d’une pyramide est toujours égale à la somme de ses composants.
En cours de jeu, les pièces des pyramides peuvent être capturées (voir règles sur la capture). Par exemple, la pyramide noire pourrait perdre son cercle 1 et n'avoir plus qu'une valeur de 90. 

Les composants définissent la valeur de la pyramide ainsi que ses modes de déplacements possibles.

### 3.4 Distribution des pyramides

#### 3.4.1 Selon la variante "L'Escale à Jeux"
* **Pyramide Noire (91)**
* **Pyramide Blanche (190)**

#### 3.4.2 Selon la variante "Boissière 1556"
Dans le traité historique de Claude de Boissière, la pyramide parfaite de valeur 91 appartient au joueur **Blanc**, tandis que la grande pyramide de valeur 190 appartient au joueur **Noir**.
* **Pyramide Blanche (91)** 
* **Pyramide Noire (190)** 
---

## 4. Disposition initiale
Cette section présente la disposition initiale sous une forme visuelle simplifiée.

### 4.1 Placement des pièces pour le plateau Escale à Jeux
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

### 4.2 Placement des pièces pour le plateau Boissière (1556)

Dans cette configuration historique documentée par Claude de Boissière, les couleurs des pions de base (cercles pairs et impairs) sont inversées, et les positions des pyramides sont asymétriques.

Légende des schémas ci-dessous :
* &#x2B24; : cercle
* &#x25B2; : triangle
* &#x25A0; : carré
* &#x25BC; : pyramide

#### 4.2.1 Disposition des pions noirs (Boissière)

|   | 1            | 2            | 3            | 4          |
|---|--------------|--------------|--------------|------------|
| 1 | &#x25A0; 361 | &#x25BC; 91  | &#x25B2; 100 |            |
| 2 | &#x25A0; 225 | &#x25A0; 120 | &#x25B2; 90  |            |
| 3 |              | &#x25B2; 64  | &#x2B24; 81  | &#x2B24; 9 |
| 4 |              | &#x25B2; 56  | &#x2B24; 49  | &#x2B24; 7 |
| 5 |              | &#x25B2; 30  | &#x2B24; 25  | &#x2B24; 5 |
| 6 |              | &#x25B2; 36  | &#x2B24; 9   | &#x2B24; 3 |
| 7 | &#x25A0; 121 | &#x25A0; 66  | &#x25B2; 12  |            |
| 8 | &#x25A0; 49  | &#x25A0; 28  | &#x25B2; 16  |            |


#### 4.2.2 Disposition des pions Blancs (Boissière)

|   | 13          | 14           | 15           | 16           |
|---|-------------|--------------|--------------|--------------|
| 1 |             | &#x25B2; 9   | &#x25A0; 15  | &#x25A0; 25  |
| 2 |             | &#x25B2; 6   | &#x25A0; 45  | &#x25A0; 81  |
| 3 | &#x2B24; 2  | &#x2B24; 4   | &#x25B2; 25  |              |
| 4 | &#x2B24; 4  | &#x2B24; 16  | &#x25B2; 20  |              |
| 5 | &#x2B24; 6  | &#x2B24; 36  | &#x25B2; 42  |              |
| 6 | &#x2B24; 8  | &#x2B24; 64  | &#x25B2; 49  |              |
| 7 |             | &#x25B2; 72  | &#x25BC; 190 | &#x25A0; 169 |
| 8 |             | &#x25B2; 81  | &#x25A0; 153 | &#x25A0; 289 |
---

## 5. Remarques

* Toutes les valeurs numériques font partie intégrante du jeu et interviennent dans les règles de capture et de victoire.
* Les pyramides constituent des pièces particulières dont le comportement dépend de leur composition interne.
* Les règles de victoire, de déplacement et de capture ne sont pas décrites dans ce document et feront l’objet de sections séparées.
