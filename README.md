# HYPERARCHIPEL

<img alt="Java" src="https://img.shields.io/badge/Java-%23ED8B00.svg?&style=flat-square&logo=java&logoColor=white"/><img alt="JavaFX" src="https://img.shields.io/badge/JavaFX-50EAFF.svg?&style=flat-square&logo=java&logoColor=black"/><img alt="Gimp" src="https://img.shields.io/badge/Gimp-5C5543?style=flat-square&logo=gimp&logoColor=white" />   

**Groupe EQU1PE - @PI4HashiWokakero1**   
**Membres : [Ninoh](https://github.com/ninohdasilva) , [Joël](https://github.com/Joel-Hamilcaro/) , [Jie](https://github.com/jie-tu) , José**

## Contexte

Ce projet a été réalisé par quatre étudiants en 2ème année de Licence (Licence MIASHS / Double Licence Math-Info) dans le cadre de l'UE "Projet Informatique".

Notre groupe de projet devait développer, un jeu de Hashiwokakero en langage Java.

> Le Hashiwokakero est apparu dans le magazine Puzzle Communication Nikoli en 1990. Il s’agit d’un jeu de logique que l’on retrouve parfois sous le nom de "Bridge" ou "Hashi" en anglais.

Nos objectifs étaient les suivants :
1. Créer un jeu vidéo accessible. Une fois téléchargé, permettre au joueur de lancer le jeu en un clic/une seule commande, de naviguer dans les menus du jeu de manière intuitive, sauvegarder sa progression automatiquement.
2. Programmer un algorithme de résolution d’une grille et proposer des fonctions d'aide au joueur induisant des pénalités sur son score.
3. Proposer une liste de grilles jouables de différents niveaux de difficultés.
4. Générer par intelligence artificielle des nouvelles grilles jouables, de tailles personnalisables, tout en garantissant mathématiquement que la grille générée ait bien une solution unique.
5. Personnaliser le jeu en y ajoutant un mode histoire pour ne pas se limiter à un classique jeu de type puzzle.

## Univers du jeu

Pendant la première moitié du XXVème siècle, la géologue Lisa Banks étudiait de nouveaux phénomènes climatiques étranges qui touchaient la région du Pineda. Bien que le secteur continental était épargné, la plus grosse partie de la région, constituée d'îles, était affectée par ces événements d'ampleur inégalée. En 2428, Banks nomma cette zone l' « HYPERARCHIPEL ».

## Bande-annonce

[![IMAGE ALT TEXT](wallpapers/wallpaperPlay.png)](https://www.youtube.com/watch?v=0K2NKLjKqSA)

## Règles du jeu

Le Hashiwokakero se joue sur une grille rectangulaire sans grandeur standard. On y retrouve des nombres de 1
à 8 inclusivement. Ils sont généralement encerclés et nommés îles. Le but du jeu est de relier toutes les îles en un seul groupe en créant une série de ponts (simples ou doubles) entre les îles.  
- Tout pont débute et finit sur une île.  
- Aucun pont ne peut en croiser un autre.  
- Tous les ponts sont en ligne droite.  
- Le nombre de ponts qui passent sur une île est le nombre indiqué sur l’île.  
- Étant données deux îles, il est possible de se rendre de l’une à l’autre en empruntant une série de ponts.

> Les règles supplémentaires (jokers/pénalités ...) sont détaillées dans le mode `NIVEAUX -> TUTORIEL`

## Modes de jeu

- Niveaux :

Le mode de jeu classique. Les niveaux sont des plateaux à résoudre. Les niveaux sont ordonnés par tailles croissantes de plateau. Un tutoriel expliquant les règles y est inclus.

- Histoire :

Pour découvrir tout l'univers d'Hyperarchipel.

- Aléatoire :  

Pour jouer à l'infini sur des nouveaux plateaux générés aléatoirement par intelligence artificielle.

- Paramètres :

Pour régler les différents paramètres du jeu.


## Code source

- Le code source du projet se trouve dans le dossier `source-code`

## Téléchargement

### Windows

- Télécharger et extraire l'archive [hyperarchipel-windows.zip](https://drive.google.com/uc?id=1o1fr-HFbSLJTfWb_ccqZZfhnlCPkLnva&export=download)
- Exécuter le fichier `Hyperarchipel.exe` se trouvant dans le dossier `hyperarchipel-windows`

### Linux

- Télécharger et extraire l'archive [hyperarchipel-linux.tar.xz](https://drive.google.com/file/d/1vqYwTUeHAELWWcUsIGdNQPhfYgfCb0dK/view?usp=sharing)
- Ouvrir un terminal avec le dossier `hyperarchipel-linux` comme répertoire de travail
- Taper la commande :

```
./jdk-16/bin/java -jar --module-path javafx-sdk-16/lib --add-modules=javafx.controls,javafx.fxml Hyperarchipel.jar
```

### Fonds d'écran

- Des fonds d'écran se trouvent dans le dossier `wallpapers`
