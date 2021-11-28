/*
*
* Nom de la classe : VueCinematique 
* 
* Description : 
*   Interface graphique pour la narration.
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
import javamvc.modele.Dialogue;
import javamvc.controleur.ControleurHashi;
import javamvc.modele.Parametre;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import static javamvc.vues.Vue.root;

public class VueCinematique extends Vue {

    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    private final DialoguePane dialoguePane;
    private final ModeleHashi modele;
    private final int chapitre;

    // CONSTRUCTEURS ***************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    /**
     * Constructeur de la vue de la narration de chapitre.
     *
     * @param chapitre Numéro du chapitre
     * @param primaryStage Fenetre
     * @param modele Modèle de la partie.
     */
    public VueCinematique(int chapitre, Stage primaryStage, ModeleHashi modele) {
        super(primaryStage, "Cinématique");
        this.chapitre = chapitre;
        this.modele = modele;
        Dialogue.initDialogue(chapitre, true);
        this.dialoguePane = new DialogueCutScene();
        this.dialoguePane.setTranslateY(0);
        this.addFondCouleur(Color.BLACK);
        addImageView(Parametre.FILE_WALLPAPER1, 0.5);
        if (chapitre==1){
            this.addFondCouleur(Color.BLACK);
        }
        if (chapitre>=2){
            this.addFondCouleur(Color.BLACK);
            this.addImageView(Parametre.FILE_WALLPAPER3);
            this.addFondCouleur(Color.BLACK);
        }
        if (chapitre==4){
            this.addImageView(Parametre.FILE_WALLPAPER3);
            this.addFondCouleur(Color.BLACK);
        }
        root.getChildren().add(dialoguePane);
        dialoguePane.initTransition();
        dialoguePane.actualiseDialogue();        
        scene = new Scene(root);
        this.beginScene();
    }

    //////////////////////////////////////////////////////////////////////////////////
    // *****************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    
    private class DialogueCutScene extends DialoguePane {
        
        private FadeTransition ft;
        private FadeTransition ft2;
        
        private DialogueCutScene() {
            super();
            this.setOnMouseClicked(event -> {
                if (ft.getStatus() == Animation.Status.RUNNING) {
                    return;
                }
                if (Dialogue.getDialogue().hasNext()) {
                    actualiseDialogue();
                } else {
                    root.getChildren().remove(this);
                    modele.setStory();
                    ControleurHashi cont = new ControleurHashi(modele, VueHashi.primaryStage);
                    VueCinematique.primaryStage.setTitle("Histoire");
                    VueCinematique.primaryStage.setScene(cont.getContVue().getScene());
                    nullify();
                }
            });
        }

        @Override
        protected void initTransition() {
            ft = new FadeTransition(Duration.millis(125), this);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setAutoReverse(true);
            ft2 = new FadeTransition(Duration.millis(125), this);
            ft2.setFromValue(0);
            ft2.setToValue(1);
        }

        @Override
        protected void actualiseDialogue() {
            if (chapitre==1){
                if (Dialogue.getDialogue().nextIndex()==0){
                    stopMusic();
                }
                if (Dialogue.getDialogue().nextIndex()==1){
                    music(3);
                    root.getChildren().remove(root.getChildren().size()-2);
                }
            }
            if (chapitre==2){
                if (Dialogue.getDialogue().nextIndex()==0){
                    stopMusic();
                    texte.setFill(Color.ANTIQUEWHITE);
                    texte.setStroke(Color.ANTIQUEWHITE);
                }
                if (Dialogue.getDialogue().nextIndex()==1){
                    root.getChildren().remove(root.getChildren().size()-2);
                }
                if (Dialogue.getDialogue().nextIndex()==3){
                    music(3);
                    root.getChildren().remove(root.getChildren().size()-2);
                    texte.setFill(Color.WHITE);
                    texte.setStroke(Color.WHITE);
                }
                if (Dialogue.getDialogue().nextIndex()==8){
                    root.getChildren().remove(root.getChildren().size()-2);
                }
            }
            if (chapitre==3){
                if (Dialogue.getDialogue().nextIndex()==0){
                    stopMusic();
                    texte.setFill(Color.ANTIQUEWHITE);
                    texte.setStroke(Color.ANTIQUEWHITE);
                }
                if (Dialogue.getDialogue().nextIndex()==1){
                    root.getChildren().remove(root.getChildren().size()-2);
                }
                if (Dialogue.getDialogue().nextIndex()==11){
                    root.getChildren().remove(root.getChildren().size()-2);
                    texte.setFill(Color.WHITE);
                    texte.setStroke(Color.WHITE);
                }
                if (Dialogue.getDialogue().nextIndex()==12){
                    music(3);
                    root.getChildren().remove(root.getChildren().size()-2);
                }
            }
            if (chapitre==4){
                if (Dialogue.getDialogue().nextIndex()==0){
                    stopMusic();
                    texte.setFill(Color.ANTIQUEWHITE);
                    texte.setStroke(Color.ANTIQUEWHITE);
                }
                if (Dialogue.getDialogue().nextIndex()==1){
                    root.getChildren().remove(root.getChildren().size()-2);
                }
                if (Dialogue.getDialogue().nextIndex()==8){
                    root.getChildren().remove(root.getChildren().size()-2);
                }
                if (Dialogue.getDialogue().nextIndex()==9){
                    root.getChildren().remove(root.getChildren().size()-2);
                }
                if (Dialogue.getDialogue().nextIndex()==11){
                    music(3);
                    root.getChildren().remove(root.getChildren().size()-2);
                    texte.setFill(Color.WHITE);
                    texte.setStroke(Color.WHITE);
                }
                if (Dialogue.getDialogue().nextIndex()==15){
                    root.getChildren().remove(root.getChildren().size()-2);
                }
                if (Dialogue.getDialogue().nextIndex()==26){
                    root.getChildren().remove(root.getChildren().size()-2);
                }
                
            }
            ft.play();
            ft.setOnFinished(evt -> {
                if (Dialogue.getDialogue().hasNext()) {
                    this.texte.setText("_____________________________________________________________\n\n"
                            + Dialogue.getDialogue().next()
                            + "\n_____________________________________________________________\n"
                            + "");
                    ft2.play();
                }
            });
        }
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
