/*
*
* Nom de la classe : VueCredits
* 
* Description : 
*   Interface graphique des crédits du jeu.
*
* Version : 1.0
*
* Date : Mai 2019
*
* Auteur : PI4 / HASHIWOKAKERO1
*
*/

package javamvc.vues;

import javamvc.modele.Dialogue;
import javamvc.modele.Parametre;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VueCredits extends Vue {
    
    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    private final DialogueCredits dialoguePaneHaut;
    private final DialogueCredits dialoguePaneBas;
    private final double hauteurDPH, hauteurDPB, cacheD, cacheF, milieuD, milieuF;
    private TranslateTransition tt;
    private TranslateTransition tt2;
    private TranslateTransition tt3;
    private TranslateTransition tt4;
    private TranslateTransition tt5;
    private TranslateTransition tt6; 
    private Image fond3 ;
    private ImageView helico;

    // CONSTRUCTEUR ****************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Constructeur de VueCredits 
     * @param primaryStage Fenetre
     */
    public VueCredits(Stage primaryStage) {
        super(primaryStage, "Crédits");
        this.stopMusic();
        this.music(3);
        Dialogue.initDialogue(6, true); //6 signifie crédits dans notre convention
        this.cacheD = -1000;
        this.cacheF = Parametre.getLargeurFenetre() + 1000;
        this.milieuD = -12.5 ;
        this.milieuF = this.milieuD + 25;
        this.hauteurDPH = Parametre.getHauteurFenetre() / 2 - 50;
        this.hauteurDPB = Parametre.getHauteurFenetre() / 2 ;
        this.dialoguePaneHaut = new DialogueCredits();
        this.dialoguePaneBas = new DialogueCredits();
        this.dialoguePaneHaut.setTranslateX(cacheD);
        this.dialoguePaneHaut.setTranslateY(hauteurDPH);
        this.dialoguePaneBas.setTranslateX(cacheF);
        this.dialoguePaneBas.setTranslateY(hauteurDPB);
        this.addFondCouleur(Color.BLACK);
        root.getChildren().add(dialoguePaneHaut);
        root.getChildren().add(dialoguePaneBas);
        dialoguePaneHaut.initTransition();
        dialoguePaneHaut.actualiseDialogue();
        VueCredits.scene = new Scene(root);
        VueCredits.root.setOnMouseClicked(evt -> {
            tt.stop();
            tt6.stop();
            tt2.stop();
            tt3.stop();
            tt4.stop();
            tt5.stop();
            VueHashi.primaryStage.setTitle("Menu principal");
            VueHashi.primaryStage.setScene(new VueMenuPrincipal(VueHashi.primaryStage).getScene());
            nullify();
        });
    }

    //////////////////////////////////////////////////////////////////////////////////
    // *****************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    
    
    private class DialogueCredits extends DialoguePane {

        protected DialogueCredits() {
            super(Parametre.getLargeurFenetre(),100);
        }
        @Override
        protected void initTransition() {
            tt = new TranslateTransition(Duration.millis(300), dialoguePaneHaut);
            tt.setToX(milieuD);
            tt2 = new TranslateTransition(Duration.millis(3480), dialoguePaneHaut);
            tt2.setToX(milieuF);
            tt3 = new TranslateTransition(Duration.millis(300), dialoguePaneHaut);
            tt3.setToX(cacheF);
            tt6 = new TranslateTransition(Duration.millis(300), dialoguePaneBas);
            tt6.setToX(milieuF);
            tt5 = new TranslateTransition(Duration.millis(3480), dialoguePaneBas);
            tt5.setToX(milieuD);
            tt4 = new TranslateTransition(Duration.millis(300), dialoguePaneBas);
            tt4.setToX(cacheD);
        }
        @Override
        protected void actualiseDialogue() {
            dialoguePaneHaut.texte.setText(Dialogue.getDialogue().next());
            dialoguePaneBas.texte.setText(Dialogue.getDialogue().next());
            tt.play();
            tt6.play();
            tt.setOnFinished(evt -> {
                tt2.play();
            });
            tt6.setOnFinished(evt -> {
                tt5.play();
            });
            tt2.setOnFinished(evt -> {
                tt3.play();
            });
            tt5.setOnFinished(evt -> {
                tt4.play();
            });
            tt4.setOnFinished(evt -> {
                if (Dialogue.getDialogue().hasNext()) {
                    dialoguePaneHaut.setTranslateX(cacheD);
                    dialoguePaneHaut.setTranslateY(hauteurDPH);
                    dialoguePaneBas.setTranslateX(cacheF);
                    dialoguePaneBas.setTranslateY(hauteurDPB);
                    actualiseDialogue();
                } else {
                    VueHashi.primaryStage.setScene(new VueMenuPrincipal(VueHashi.primaryStage).getScene());
                    nullify();
                }
            });
        }
    }
    
     /**
    * Méthode qui met à null les pointeurs
    */
    @Override
    protected void nullify(){
        this.fond3 = null;
        this.helico = null;
        this.tt = null;
        this.tt2 = null;
        this.tt3 = null;
        this.tt4 = null;
        this.tt5 = null;
        this.tt6 = null;
        System.gc();
        Runtime.getRuntime().gc();
    }
    
}
