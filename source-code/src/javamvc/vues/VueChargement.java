/*
*
* Nom de la classe : VueChargement
* 
* Description : 
*   Interface graphique de l'écran de chargement lors de la génération aléatoire
*   d'un plateau.
*
* Version : 1.0
*
* Date : Mai 2019
*
* Auteur : PI4 / HASHIWOKAKERO1
*
*/

package javamvc.vues;

import javamvc.modele.ModeleHashi;
import javamvc.controleur.ControleurHashi;
import javamvc.modele.Parametre;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class VueChargement extends Vue {
    
    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    private final int taillePlateau;

    // CONSTRUCTEUR ****************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Contructeur de la vue du chargement pendant la génération d'un plateau
     * @param primaryStage Fenetre
     * @param taillePlateau Taille du plateau aléatoire à générer.
     */
    public VueChargement(Stage primaryStage, int taillePlateau) {
        super(primaryStage,"Chargement en cours");
        this.taillePlateau = taillePlateau;
        addImageView(Parametre.FILE_WALLPAPER2);
        VueChargement.scene = new Scene(root);
        VueChargement.primaryStage.setScene(this.getScene());
        VueChargement.primaryStage.show();
    }

    // METHODES ********************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Lance l'algorithme de génération d'un plateau aléatoire à solution unique.
     */
    protected void chargement() {
        Runnable r = () -> {
            ModeleHashi md = new ModeleHashi(taillePlateau);
            Runnable r2 = () -> {
                ControleurHashi cont = new ControleurHashi(md, VueChargement.primaryStage);
                nullify();
            };      
            Platform.runLater(r2);
        };
        Platform.runLater(r);
        VueChargement.primaryStage.setTitle("Niveau aléatoire " + taillePlateau + "x" + taillePlateau);
    }
    
     /**
    * Méthode qui met à null les pointeurs
    */
    @Override
    protected void nullify(){
        System.gc();
        Runtime.getRuntime().gc();
    }
    
}
