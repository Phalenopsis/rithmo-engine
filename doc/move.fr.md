# Rithmomachie — Les Mouvements

Une fois le plateau en place, les joueurs déplacent leurs pièces à tour de rôle. Le joueur **Noir** commence toujours la partie. Contrairement aux échecs, on ne capture pas une pièce en se plaçant dessus : le mouvement sert uniquement à se positionner pour "piéger" l'adversaire.

---

## 1. Principes de base du déplacement

* **Chacun son tour :** On ne déplace qu'une seule pièce par tour.
* **Case libre :** On ne peut se déplacer que vers une case **vide**.

### 1.1 Types de déplacements
#### 1.1.1 Déplacements réguliers
Lors des déplacements réguliers, les pièces doivent glisser et les cases entre la position initiale et la position finale doivent être libres.

#### 1.1.2 Déplacements irréguliers
Les déplacements irréguliers permettent de sauter directement de la case de départ à la case d'arrivée.
Les déplacements irréguliers suivent un schéma spécifique indépendant (voir ci-dessous la section 2).
---

## 2. Comment se déplacent les formes ?

Chaque forme possède sa propre "signature" de mouvement. C'est la **forme** qui décide de la distance, et non le nombre inscrit dessus.

### 2.1 Le Cercle : Le pas prudent
Le cercle est la pièce la plus simple. Il se déplace d'**une seule case** à la fois.
* **Direction :** Uniquement en diagonale.
* **Interdiction :** Il ne peut jamais se déplacer horizontalement ou verticalement.
* Pas de déplacement irrégulier

Le 0 représente la position initiale d'un cercle, les r ses destinations possibles en mouvement régulier

|   |1|2| 3 | 4 | 5 |6|7|
|---|-|-|---|---|---|-|-|
| 1 | | |   |   |   | | |
| 2 | | |   |   |   | | |
| 3 | | | r |   | r | | |
| 4 | | |   | O |   | | |
| 5 | | | r |   | r | | |
| 6 | | |   |   |   | | |
| 7 | | |   |   |   | | |

### 2.2 Le Triangle
Le triangle est plus vif. Il se déplace exactement de **deux cases**.
* **Direction :** Uniquement **horizontalement ou verticalement**.
* **Condition :** La case par laquelle il passe (la case intermédiaire) doit être vide.

Le T représente la position initiale d'un triangle.
Les r ses destinations possibles en mouvement régulier.
Les i ses destinations possibles en mouvement irrégulier.


|   |1| 2 | 3 | 4 | 5 | 6 |7|
|---|-|---|---|---|---|---|-|
| 1 | |   |   |   |   |   | |
| 2 | |   | i | r | i |   | |
| 3 | | i |   |   |   | i | |
| 4 | | r |   | T |   | r | |
| 5 | | i |   |   |   | i | |
| 6 | |   | i | r | i |   | |
| 7 | |   |   |   |   |   | |

### 2.3 Le Carré : La longue portée
Le carré est la pièce la plus puissante en termes de distance. Il se déplace exactement de **trois cases**.
* **Direction :** Uniquement en ligne droite (horizontalement ou verticalement).
* **Condition :** Les deux cases intermédiaires doivent être vides.

Le C représente la position initiale d'un carré.
Les r ses destinations possibles en mouvement régulier.
Les i ses destinations possibles en mouvement irrégulier.

|   | 1 | 2 | 3 | 4 | 5 | 6 | 7 |
|---|---|--|---|---|---|--|---|
| 1 |   |  | i | r | i |  |   |
| 2 |   |  |   |   |   |  |   |
| 3 | i |  |   |   |   |  | i |
| 4 | r |  |   | C |   |  | r |
| 5 | i |  |   |   |   |  | i |
| 6 |   |  |   |   |   |  |   |
| 7 |   |  | i | r | i |  |   |
---

## 3. Le cas particulier : La Pyramide

La pyramide est la pièce "maîtresse". Comme elle est composée de plusieurs formes empilées (cercles, triangles et carrés), elle cumule leurs pouvoirs (réguliers et irréguliers).

* **Polyvalence :** À son tour, la pyramide peut choisir de se déplacer soit comme un cercle (1 case diagonale), soit comme un triangle (2 cases orthogonales), soit comme un carré (3 cases orthogonales).
* **Affaiblissement :** Si, au cours du jeu, la pyramide perd tous ses composants d'une certaine forme (par exemple, si elle n'a plus de triangles à l'intérieur), elle perd le droit de se déplacer selon les règles de cette forme.

Le P représente la position initiale d'une pyramide constituée d'au moins un cercle, un triangle et un carré.
Les r ses destinations possibles en mouvement régulier.
Les i ses destinations possibles en mouvement irrégulier.

|   | 1 | 2 | 3 | 4 | 5 | 6 | 7 |
|---|---|---|---|---|---|---|---|
| 1 |   |   | i | r | i |   |   |
| 2 |   |   | i | r | i |   |   |
| 3 | i | i | r |   | r | i | i |
| 4 | r | r |   | P |   | r | r |
| 5 | i | i | r |   | r | i | i |
| 6 |   |   | i | r | i |   |   |
| 7 |   |   | i | r | i |   |   |
---


## 4. Conseil stratégique
Puisque le but est de capturer les pièces adverses par des calculs mathématiques (égalité, addition, etc.), le mouvement est votre outil principal pour aligner vos nombres face à ceux de l'adversaire ou pour encercler ses pièces les plus précieuses.

> **Note :** Un bon joueur de Rithmomachie ne déplace pas ses pièces au hasard, il prépare ses "pièges" numériques plusieurs coups à l'avance !