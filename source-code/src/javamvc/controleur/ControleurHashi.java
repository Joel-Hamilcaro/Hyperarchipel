/*
*
* Nom de la classe : ControleurHashi 
* 
* Description : 
*   Modifie le modèle en fonction des action de l'utilisateur
*   Initialise et actualise la vue en fonction des actions effectué / du temps écoulé.
*
* Version : 1.0
*
* Date : Mai 2019
*
* Auteur : PI4 / HASHIWOKAKERO1
*
*/

package javamvc.controleur;

import javamvc.modele.Parametre;
import javamvc.modele.Niveaux;
import javamvc.modele.ModeleHashi;
import javamvc.modele.Chronometre;
import javamvc.vues.VueHashiTuto;
import javamvc.vues.VueCredits;
import javamvc.vues.VueHashiBoss;
import javamvc.vues.VueHashiScenario;
import javamvc.vues.VueHashi;
import javamvc.vues.VueMenuFin;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ControleurHashi {

    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    private static ModeleHashi modele;
    private static VueHashi vue;
    private static Chronometre chronometre;
    private static Stage primaryStage;

    // CONSTRUCTEUR ****************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Contructeur du controleur
     * @param mod ModèleHashi controlé
     * @param primaryStage Fenêtre
     */
    public ControleurHashi(ModeleHashi mod, Stage primaryStage) {
        ControleurHashi.primaryStage = primaryStage;
        ControleurHashi.modele = mod;
        ControleurHashi.chronometre = 
                new Chronometre(this, modele.isStory());
        int chapitre = modele.getChapitre();
        if (isStory()){
            if (isBoss()) ControleurHashi.vue = new VueHashiBoss(this);
            else ControleurHashi.vue = new VueHashiScenario(this);
            ((VueHashiScenario)ControleurHashi.vue).initBaio();
            ((VueHashiScenario)ControleurHashi.vue).moveBaioRegulier(100);
            ((VueHashiScenario)ControleurHashi.vue).initDialogueHashi(chapitre);            
        }
        else{
            if (isTuto()) {
                ControleurHashi.vue = new VueHashiTuto(this);
                this.initVue();
                Parametre.setDoubleClic(true);
                ((VueHashiTuto)ControleurHashi.vue).initDialogueTuto(7);
            }
            else {
                ControleurHashi.vue = new VueHashi(this);
                ControleurHashi.vue.dessineJoker();
                ControleurHashi.vue.initMenuPause();
                this.initVue();
            }
            ControleurHashi.chronometre.start();
        }
        primaryStage.setScene(vue.getScene());  
    }

    // ACCESSEURS ******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Modele controlé par l'instance.
     * @return ModeleHashi
     */
    public ModeleHashi getContMod() {
        return ControleurHashi.modele;
    }

    /**
     * Vue controlée par l'instance.
     * @return VueHashi
     */
    public VueHashi getContVue() {
        return ControleurHashi.vue;
    }

    /**
     * Chronomètre de l'instance.
     * @return Chronometre
     */
    public Chronometre getContChrono() {
        return ControleurHashi.chronometre;
    }

    /**
     * Fenêtre (Stage JavaFX) ouverte.
     * @return Stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    /**
     * Vérifie si le modèle est celui du tutoriel.
     * @return true si et seulement si l'id du modele vaut 1
     */
    public final boolean isTuto(){
        return modele.getID()==1;
    }
    
    /**
     * Vérifie le modèle est celui d'un chapitre du scénario.
     * @return true si et seulement si le chapitre du modèle est strictement supérieur à 0
     */
    public final boolean isStory(){
        return modele.getChapitre()>0;
    }
    
    /**
     * Vérifie si le modèle est celui du chapitre final du scénario.
     * @return true si et seuelement si le chapitre du modèle vaut 4
     */
    public final boolean isBoss(){
        return modele.getChapitre()==4;
    }

    // METHODES DE CALCULS *********************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Longueur du plateau
     * @return Longueur du plateau.
     */
    public final int length() {
        return modele.getPlateau().length();
    }

    /**
     * Calcule la hauteur d'une case graphique en fonction des parametres.
     * @return hauteur d'une case graphique.
     */
    public int calculeHauteurCase() {
        return (Parametre.getHauteurFenetre() - 50 - 25) / (this.length());
    } 
    
    /**
     * Calcule la largeur d'une case graphique en fonction des parametres.
     * @return largeur d'une case graphique.
     */
    public int calculeLargeurCase() {
        return (Parametre.getLargeurFenetre() - 50) / (this.length());
    }

    // METHODES DE SYNCHRONISATION MODELE-VUE **************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Initialise l'affichage graphique du plateau. 
     * Dessine les îles et initialise la population en mode histoire.
     */
    public final void initVue() {
        for (int i = 0; i < ControleurHashi.modele.getPlateau().length(); i++) {
            for (int j = 0; j < ControleurHashi.modele.getPlateau().length(); j++) {                
                ControleurHashi.vue.initDessin(i, j);
            }
        }
        if (isStory()){
           ((VueHashiScenario)vue).movePopulation();
        }
        
    }
    
    /**
     * Si la partie n'est pas finie : Actualise l'affichage graphique du temps.
     * Sinon : termine la partie.
     */
    public void actualiseChrono() {
        ControleurHashi.vue.getInfos().getChildren().remove(vue.getChronoGraphique());
        if (ControleurHashi.modele.estFini()) {
            ControleurHashi.chronometre.stop();
            ControleurHashi.vue.nettoyer();
            if (!ControleurHashi.modele.isStory()) {
                this.termine();
            }
        } else if (ControleurHashi.chronometre.isEcoule()) {
            ControleurHashi.chronometre.stop();
            ControleurHashi.modele.setPerdu();
            ControleurHashi.vue.setEnPause(true);
            ControleurHashi.vue.getRoot().getChildren().remove(vue.getInfos());
            ControleurHashi.vue.getPlateauGraphique().getChildren().remove(vue.getMenuPause());
            ControleurHashi.vue.getPlateauGraphique().getChildren().remove(vue.getMenuParametre());
        } else {
            ControleurHashi.vue.dessineChrono();

        }
    }
    
    /**
     * Actualise l'affichage graphique du plateau.
     */
    public void actualiseVue() {
        for (int i = 0; i < ControleurHashi.modele.getPlateau().length(); i++) {
            for (int j = 0; j < ControleurHashi.modele.getPlateau().length(); j++) {
                ControleurHashi.vue.dessineCase(i, j);
            }
        }
        if (isStory()){
           ((VueHashiScenario)vue).movePopulation();
        }
        
    }   

    /**
     * Termine la partie. 
     * Enregistre le score, réinitialise le modèle, et initialise le menu de fin.
     * Variante pour le chapitre final : initialise les crédits.
     * 
     */
    public void termine() {
        Image img = ControleurHashi.vue.imagePlateau();
        boolean victoire = true;
        if (!ControleurHashi.modele.isStory()) {
            ControleurHashi.modele.setScore(ControleurHashi.chronometre.getMinutes() * 60 + ControleurHashi.chronometre.getSecondes() - 1);
        } else if (modele.estFini()) {
            int score = ControleurHashi.chronometre.getMinutes()*60 + ControleurHashi.chronometre.getSecondes();
            ControleurHashi.modele.setScore(60-score-1);
        } else {
            victoire = false;
        }
        if (modele.getID() == Niveaux.getNiveaux().size() - 3 && modele.estFini()) {
            ControleurHashi.modele.reinitialiser();
            primaryStage.setScene(new VueCredits(primaryStage).getScene());
            return;
        }
        ControleurHashi.modele.reinitialiser();
        primaryStage.setScene(new VueMenuFin(primaryStage, ControleurHashi.modele, img,victoire).getScene());
    }
}
