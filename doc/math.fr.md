# Notions mathématique
## Progression
### Progression arithmétique
La progression arithmétique (PA) est suite de nombres dans laquelle chaque nombre a une différence commune. Il s’agit de l’un des types de séquences les plus simples dans le monde des mathématiques, car chaque terme consécutif augmente de la même quantité.
#### Notation mathématique :
a <sub>n</sub>  = a<sub>1</sub> + (n−1) × d

où
* a est le nombre de départ de la suite => **premier terme** souvent noté a<sub>1</sub>
* n représente la position du terme dans la suite => **rang**
* d est la différence entre deux nombres consécutifs de cette suite => **raison** (différence constante)

#### Exemple:
Si :

* a=2
* d=3

Alors la suite est :
2, 5, 8, 11, …

Et :

a<sub>4</sub>= 2 + (4−1) × 3 = 11


### Progression géométrique

La progression géométrique (PG) est une suite de nombres dans laquelle chaque terme est obtenu en multipliant le terme précédent par une constante appelée raison. Contrairement à la progression arithmétique, où l’on ajoute une valeur fixe, ici on multiplie toujours par le même nombre.

Notation mathématique :

a <sub>n</sub> = a<sub>1</sub> × q<sup>(n−1)</sup>

où

* a est le nombre de départ de la suite => premier terme souvent noté a<sub>1</sub>
* n représente la position du terme dans la suite => rang
* q est le facteur multiplicatif entre deux termes consécutifs => raison

#### Exemple:

Si :

* a = 2
* q = 3

Alors la suite est :
2, 6, 18, 54, …

Et :

a<sub>4</sub> = 2 × 3<sup>(4−1)</sup> = 2 × 27 = 54

### Progression harmonique

La progression harmonique (PH) est une suite de nombres dont les inverses forment une progression arithmétique. Autrement dit, si l’on prend l’inverse de chaque terme, on obtient une suite arithmétique.

#### Notation mathématique :

1 / a<sub>n</sub> = 1 / (a<sub>1</sub> + (n−1) × d)

où 

* a<sub>1</sub> est le premier terme de la suite
* 1 / a<sub>1</sub> est le premier terme de la suite des inverses
* n représente la position du terme dans la suite => **rang**
* d est la différence constante entre deux inverses consécutifs

---

#### Exemple:

Considérons la suite :

2, 3, 6

Cette suite ne semble pas régulière au premier regard, ce qui est typique des progressions harmoniques.

Prenons les inverses :

1/2, 1/3, 1/6

Calculons les différences :

* 1/3 − 1/2 = −1/6
* 1/6 − 1/3 = −1/6

Les différences sont constantes.

Donc la suite des inverses est une **progression arithmétique**, ce qui signifie que :

2, 3, 6 est une **progression harmonique**


La progression harmonique est souvent vue via la proportion a:b:c telle que
* (c - b) / (b - a) = c / a

Exemple avec  a = 2, b = 3, c = 6 :
* (c - b) / (b - a) = (6 - 3) / (3 - 2) = 3 / 1
*  c / a = 6 / 2 = 3 / 1 
---

#### Mise en forme avec la notation :

On peut écrire la suite des inverses comme une suite arithmétique :

* a = 2
* 1/a = 1/2
* d = −1/6

Donc :

a<sub>n</sub> = 1 / (1/2 + (n−1) × (−1/6))

Vérification pour n = 3 :

a<sub>3</sub> = 1 / (1/2 + 2 × (−1/6))  
= 1 / (1/2 − 1/3)  
= 1 / (1/6)  
= 6

---

#### Remarque

Contrairement aux progressions arithmétiques ou géométriques, une progression harmonique :

* n’a pas forcément une forme “évidente”
* peut sembler irrégulière (comme 2, 3, 6)
* mais devient régulière lorsqu’on considère les inverses

C’est cette propriété qui la rend particulièrement intéressante, notamment dans les mathématiques anciennes et la rithmomachie.