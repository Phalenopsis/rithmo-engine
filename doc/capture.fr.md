# Rithmomachie — Règles de capture

## 1. Principes généraux

* Les captures doivent être associées à un déplacement.
* Une capture peut être effectuée avant et/ou après déplacement de la pièce active.
* Quand la capture précède le déplacement, la pièce preneuse doit être mise à la place de la pièce prise.
* En revanche, quand la capture est consécutive à un déplacement, la case de la pièce prise reste libre.
* Dans certaines conditions, une pièce peut faire plusieurs captures dans un même tour.
* Si les prises multiples ont lieu avant le déplacement, le preneur choisit la place de l'un des pions pris.
* Les captures **ne sont pas obligatoires**.
* Un déplacement irrégulier ne peut pas provoquer de capture arithmétique (Rencontre, Embûche, Assaut, Puissance ou Progression). L'Emprisonnement constitue une exception : il dépend uniquement de l'état global du plateau et peut donc résulter de n'importe quel déplacement légal.
---

## 2. Types de capture

Il existe quatre types principaux de capture :

### 2.1 Rencontre


* une pièce vient se placer à un pas régulier d'un pion adverse de même valeur

#### Exemple :
* rb25 : rond blanc de valeur 25
* cn25 : carré noir de valeur 25

|   | 1 | 2    | 3 | 4    |
|---|---|------|---|------|
| 1 |   | rb25 |   |      |
| 2 |   |      |   |      |
| 3 |   |      |   | cn25 |
| 4 |   |      |   |      |

Si blanc joue son pion 25 en (3,2), il peut prendre le carré noir 25.

Il est à noter que si blanc ne veut pas prendre le carré, noir ne pourra pas prendre le rond blanc avant son déplacement car la case (3,2) n'est pas à un pas de déplacement régulier du carré.

---

### 2.2 Embûche

Quand un nombre est égal soit à la somme, soit à la différence, soit au produit ou au quotient de deux nombres adverses, il peut être pris à condition que les deux preneurs soient à un pas régulier du pion à prendre.

#### Exemple :
* rn8 : rond noir de valeur 8
* rn4 : rond noir de valeur 4
* tb12: triangle blanc de valeur 12

|   | 1   | 2   | 3    | 4 |
|---|-----|-----|------|---|
| 1 |     | rn8 |      |   |
| 2 |     |     | tb12 |   |
| 3 |     |     |      |   |
| 4 | rn4 |     |      |   |

Si noir joue son pion 4 en (2,3), il peut prendre le triangle blanc 12 :
* 8 + 4 = 12
* le triangle est à une case en diagonale (pas régulier du rond) du pion rond 8 
* le triangle sera à une case en diagonale du pion rond 4

---

### 2.3 Assaut

Une pièce peut capturer une pièce adverse si :

* on compte le nombre de cases vides entre les deux pièces (horizontalement, verticalement ou diagonalement)
* seulement s'il n'y a pas de pièces entre l'attaquant et le défenseur
* la valeur de la pièce attaquante **multipliée ou divisée par ce nombre de cases** est égale à la valeur de la pièce cible

#### Exemple
* rn36 : rond noir de valeur 36
* rn2 : rond noir de valeur 2
* tn6 : triangle noir de valeur 6
* tb12: triangle blanc de valeur 12

|   | 1   | 2   | 3 | 4    | 5 | 6 | 7    | 8 |
|---|-----|-----|---|------|---|---|------|---|
| 1 |     |     |   | tb12 |   |   |      |   |
| 2 |     |     |   |      |   |   | rn36 |   |
| 3 |     | rn2 |   |      |   |   |      |   |
| 4 | tn6 |     |   |      |   |   |      |   |

* Le triangle blanc 12 peut être pris via différents assauts :
* * si noir joue rn36 en (8,1) car
* * * il y a 3 cases libres entre les deux pièces 
* * * 36/3 = 12.
* * si noir joue rn2 en (1, 2) ou en (3,4) car il laisse le champ libre à son triangle noir :
* * * il y a 2 cases libres entre les deux triangles
* * * 6 * 2 = 12.


---
### 2.4 Puissance

Quand un nombre est placé à un pas régulier d'un nombre adverse et est égal soit à sa puissance soit à sa racine, il peut prendre la pièce adverse.


#### Exemple :
* rb3 : rond blanc de valeur 3
* cn81 : carré noir de valeur 81


|   | 1   | 2    | 3    | 4 |
|---|-----|------|------|---|
| 1 |     | rb3  |      |   |
| 2 |     |      |  |   |
| 3 |     | cn81 |      |   |
| 4 |  |      |      |   |

Si blanc joue son pion 3 en (1,2) ou en (3,2), il peut prendre le carré blanc 81 :
* il y aura un pas régulier entre les deux
* 3 est la racine quatrième de 81

---

### 2.5 Progression

Un nombre peut être pris s'il est intégré dans une progression (arithmétique, géométrique ou harmonique) avec deux nombres adverses à condition qu'il soit à un pas régulier de déplacement de ces deux nombres.

#### Exemple :
* rb25 : rond blanc de valeur 25
* cn15 : carré noir de valeur 15
* tn20 : triangle noir de valeur 20


|   | 1    | 2 | 3    | 4    |
|---|------|---|------|------|
| 1 | rb25 |   |      | cn15 |
| 2 |      |   |      |      |
| 3 |      |   | tn20 |      |
| 4 |      |   |      |      |

Si noir joue son pion 20 en (1,3), il peut prendre le rond blanc 25 :
* le triangle noir sera à un pas de déplacement du rond blanc
* le carré noir est déjà à un pas de déplacement du rond blanc
* 15, 20, 25 forment une progression arithmétique

### 2.6 Emprisonnement

L'emprisonnement (*Obsidio*) est le seul mode de capture qui ne repose sur aucune relation arithmétique entre les pièces.

Une pièce peut être capturée lorsqu'elle ne dispose plus d'aucun mouvement régulier légal.

#### Conditions de capture

Pour qu'une pièce soit considérée comme emprisonnée :

* tous ses mouvements réguliers théoriques doivent être impossibles ;
* chaque axe de déplacement doit être bloqué soit par le bord du plateau, soit par une autre pièce ;
* au moins un de ces blocages doit être assuré directement par une pièce ennemie.

Une pièce entièrement entourée par ses propres alliés n'est donc pas capturable : elle est immobilisée mais non assiégée.

#### Notion de bouclier allié

Lorsqu'un axe de déplacement est bloqué par plusieurs pièces, seul le premier obstacle rencontré est pris en compte.

* Si le premier obstacle est un allié, cet axe est considéré comme protégé.
* Si le premier obstacle est un ennemi, cet axe est considéré comme assiégé.

Une pièce ennemie située derrière un allié n'intervient pas dans l'évaluation de l'emprisonnement.

#### Blocus mixte

Une pièce peut être capturée même si certains de ses axes sont bloqués par des alliés.

Il suffit :

* qu'aucun mouvement régulier légal ne reste disponible ;
* et qu'au moins un axe soit directement bloqué par un ennemi.

Un emprisonnement peut donc résulter d'un blocus combinant alliés et ennemis.

#### Indépendance du mouvement ayant provoqué le blocus

Contrairement aux captures arithmétiques, l'emprisonnement découle uniquement de l'état global du plateau.

La pièce ayant fermé le blocus peut avoir utilisé :

* un mouvement régulier ;
* un mouvement irrégulier (saut) ;
* une capture préalable ayant modifié la configuration du plateau.

Ces éléments n'ont aucune influence sur la validité de l'emprisonnement.

#### Attribution des acteurs

Chaque bloqueur ennemi direct participant au siège est considéré comme un acteur potentiel de la capture.

Lorsqu'une pièce est emprisonnée :

* chaque bloqueur ennemi direct génère une opportunité de capture distincte ;
* les autres bloqueurs ennemis sont considérés comme ses soutiens.

Ainsi, une même cible peut donner lieu à plusieurs options de capture selon l'acteur choisi.

#### Capture avant déplacement

Lorsqu'un emprisonnement est résolu avant le déplacement de la pièce active :

* la pièce emprisonnée est retirée du plateau ;
* l'acteur choisi est immédiatement placé sur la case libérée ;
* le joueur doit ensuite effectuer son déplacement normal depuis cette nouvelle position.

Il est donc possible qu'un joueur choisisse une capture qui conduise ensuite à une absence de mouvement légal.

#### Capture après déplacement

Un déplacement ou une capture arithmétique peut modifier la configuration du plateau et provoquer un nouvel emprisonnement.

Dans ce cas :

* la capture est proposée au joueur après son déplacement ;
* elle reste facultative ;
* la pièce emprisonnée est simplement retirée du plateau ;
* aucune pièce ne vient occuper sa case.

#### Exemple :
* rn2 : rond noir de valeur 2
* cn15 : carré noir de valeur 15
* tb30 : triangle blanc de valeur 30
* rn36 : rond noir de valeur 36
* rn4 : rond noir de valeur 4
* tb16 : triangle blanc de valeur 16
* tn72 : triangle noir de valeur 72


|   | 1    | 2    | 3    | 4    |
|---|------|------|------|------|
| 1 |      | rn2  |      |      |
| 2 | cn15 | tb30 |      | rn36 |
| 3 | rn4  |      | tb16 |      |
| 4 |      |      |      | tn72 |


* Si noir déplace son triangle 72 en (2,4), il peut prendre le triangle blanc 30 car celui-ci ne peut plus se déplacer de manière régulière/
* Si noir déplace son rond 4 en (2, 4), il peut prendre à la fois les deux triangles blancs, celui de valeur 30 par emprisonnement et celui de valeur 16 par puissance.
* Rien à voir avec l'emprisonnement, mais si c'est au tour de blanc de jouer, il pourrait décider de prendre le rond noir 4 et de placer son triangle 16 à sa place, puis jouer son déplacement.

---

## 3. Cas particulier des pyramides

* Une pyramide peut participer aux captures en utilisant **les valeurs de chacun de ses composants** ou sa valeur propre. 
* Les composants d’une pyramide peuvent être capturés individuellement selon les règles normales. 
* Une pyramide peut être capturée entièrement en utilisant sa valeur propre. Ses composants deviennent alors des pions capturés comme s'ils l'avaient été individuellement.

## La pose
* Les pions sont réversibles, c'est à dire qu'un joueur peut remettre en jeu une pièce capturée en changeant sa couleur.
* Il peut réintroduire une pièce capturée quand il le veut dans la colonne 1 pour les noirs et dans la colonne 16 pour les blancs.
* Cette pose peut donner lieu à une prise (mais ce n'est pas obligatoire).
* L'action d'introduire un pion compte pour un coup, qu'il y a prise ou non.
* Les pyramides et leurs composants sont les seules pièces à ne pas être réversibles. Elles ne peuvent donc pas être remises en jeu.
---

