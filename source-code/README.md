# Développement et maintenance du projet

## Dernières mises à jour

```
- NEW : Nouveau paramètre 'mode Low graphics'
- NEW : Sauvegarde des paramètres au démarrage
- NEW : Transparence sur le contour des îles
- NEW : Plateformes circulaires en bois sur lesquels s'attachent les ponts
- FIX : Problèmes des filepaths depuis les nouvelles version de Java/JavaFX > 11
- FIX : Des fuites de mémoires sur les audioclip et le chronomètre.
```

## Maintenance

Pour mettre à jour le projet
- Installer/Lancer Netbeans :
- Créer un nouveau projet Netbeans et y inclure les fichiers de ce dossier `source-code/`
- Pour rendre le projet compatibles avec les dernières version de Java et JavaFX :  

  Suivre ce tutoriel : https://www.youtube.com/watch?v=h_3AfQhjziw

### Outils de débogage avec Netbeans

  - Cliquer sur "Degub project" (icône à droite du "run")
        - Permet de voir les stream en cours sur le nouvel onglet
  - Cliquer sur "Profile project" (à droite de "Debug project") et choisir le mode "Telemetry" (pour voir l'évolution du Heap Space)
  - Sinon choisir le mode "Objects" (pour voir les pointeurs non libérés)

# Création du lanceur

## Générer les `.jar` pour partager le jeu

  - Pour générer les `.jar` pour Windows et Linux il faut compiler le projet depuis Netbeans, respectivement sur Windows et Linux.
  - Dans le dossier `dist` se trouve le fichier `.jar` (e.g. `Hyperarchipel.jar`).
  - Créer un nouveau dossier (ce dossier sera le dossier du jeu partagé au client).
  - Dans ce dossier, y inclure :
      - un dossier contenant tout le jdk java (open source, téléchargeable depuis le site jdk.java.net)
        - Vérifier que le jdk est correct : il est de la forme
        ```  
        nom_du_dossier_jdk ─┬─ bin ─── ...
                            ├─ conf ─── ...
                            ├─ include ─── ...
                            ├─ jmods ─── ...
                            ├─ legal ─── ...
                            ├─ lib ─── ...
                            └─ release
        ```
      - un dossier contenant tout le jfx (open source, téléchargeable depuis le site officiel de JavaFX)
        - Vérifier que le jfx est correct : il est de la forme
        ```  
        nom_du_dossier_jfx ─┬─ bin ─── ...
                            ├─ legal ─── ...
                            └─ lib ─── ...
        ```
        (Remarque : le sous-dossier `bin` est absent sur Linux)
      - Le fichier du jeu `.jar`
      - Le dossier `save`
    - Le dossier est prêt à être partagé. Il sera de la forme :
       ```  
        dossier_final─┬─ nom_du_dossier_jdk ─── ...
                      ├─ nom_du_dossier_jfx ─── ...
                      ├─ save ─── ...
                      └─ Hyperarchipel.jar
        ```  

## Lancer le projet (Windows prompt et Linux terminal)

Pour lancer le projet depuis ce dossier prêt (voir section précédente)

- Ouvrir un terminal dans le dossier

- Sur Windows faire :
```
nom_du_dossier_jdk\bin\java -jar --module-path nom_du_dossier_jfx\lib\ --add-modules javafx.controls Hyperarchipel.jar
```
- Sur Linux faire :
```
nom_du_dossier_jdk/bin/java -jar --module-path nom_du_dossier_jfx/lib/ --add-modules=javafx.controls,javafx.fxml Hyperarchipel.jar
```

## Créer le .exe pour Windows

- Installer launch4j et le lancer
- Créer un nouveau dossier pour le lanceur `dossier_lanceur`
- Dans ce dossier copier et coller : `nom_du_dossier_jdk` et `nom_du_dossier_jfx` et `save`
- Onglet Basic :
    - Output File : `chemin\vers\dossier_lanceur\Hyperarchipel.exe`
    - Jar : `Hyperarchipel.jar`
    - Cocher : "Don't wrap the jar, launch only"
    - Process Priority : High
- Onglet JRE :
    - Bundle JRE path : `.\nom_du_dossier_jdk`
    - JVM options : `--module-path  .\nom_du_dossier_jfx\lib --add-modules=javafx.controls,javafx.fxml`
- Cliquer sur `Build wrapper`
- Tester en cliquant sur `Test wrapper`
- Il suffit maintenant de partager le dossier `dossier_lanceur` et de lancer le jeu en cliquant sur le `Hyperarchipel.exe`
