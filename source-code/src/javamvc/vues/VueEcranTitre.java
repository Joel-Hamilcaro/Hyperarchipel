/*
*
* Nom de la classe : VueEcranTitre
* 
* Description : 
*   Interface graphique de l'ecran titre.
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
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VueEcranTitre extends Vue {

    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    private final DialogueTitre dialoguePane;
    private FadeTransition ft, ft2, ft3, ft4,ft5,ft6;
    private TranslateTransition tt0, tt;
    
    // CONSTRUCTEUR ****************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Constructeur de VueEcranTitre
     * @param primaryStage Fenetre
     */
    public VueEcranTitre(Stage primaryStage) {
        super(primaryStage,"Ecran principal");
        this.music(0);
        addImageView(Parametre.FILE_WALLPAPER0);
        Dialogue.dialogueLettreParLettre(Dialogue.titre());
        this.dialoguePane = new DialogueTitre();
        dialoguePane.initTransition();
        root.getChildren().add(dialoguePane);
        dialoguePane.actualiseDialogue();
        VueEcranTitre.scene = new Scene(root);
        VueEcranTitre.root.setOnMouseClicked(evt -> {
            if (ft!=null) ft.stop();
            if (ft2!=null) ft2.stop();
            if (ft3!=null)ft3.stop();
            if (ft4!=null)ft4.stop();
            if (ft5!=null)ft5.stop();
            if (ft6!=null)ft6.stop();
            if (tt!=null)tt.stop();
            if (tt0!=null)tt0.stop();
            VueHashi.primaryStage.setScene(new VueMenuPrincipal(VueHashi.primaryStage).getScene());
            nullify();
        });
        scene.setOnKeyPressed((KeyEvent t) -> {
            KeyCode key = t.getCode();
            if (key == KeyCode.ENTER){
                if (ft!=null) ft.stop();
                if (ft2!=null) ft2.stop();
                if (ft3!=null)ft3.stop();
                if (ft4!=null)ft4.stop();
                if (ft5!=null)ft5.stop();
                if (ft6!=null)ft6.stop();
                if (tt!=null)tt.stop();
                if (tt0!=null)tt0.stop();
                VueEcranTitre.primaryStage.setScene(new VueMenuPrincipal(VueHashi.primaryStage).getScene());
            }
        });
    }
    
    // METHODE *********************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Initialise l'écran titre
     */
    protected void initEcranTitre(){
        
        this.addFondCouleur(Color.BLACK);
        Image fond = new Image(getClass().getResource(Parametre.FILE_WALLPAPER).toString());
        ImageView imageView = new ImageView(fond);
        imageView.setOpacity(0);
        imageView.setFitWidth(Parametre.getLargeurFenetre());
        imageView.setFitHeight(Parametre.getHauteurFenetre());
        root.getChildren().add(imageView);
        
        Image fond2 = new Image(getClass().getResource(Parametre.FILE_WAVE).toString());
        ImageView imageView2 = new ImageView(fond2);
        imageView2.setOpacity(0);
        imageView2.setFitWidth(Parametre.getLargeurFenetre());
        imageView2.setFitHeight(Parametre.getHauteurFenetre());
        root.getChildren().add(imageView2);
        
        Image fond3 = new Image(getClass().getResource(Parametre.FILE_BAIO).toString());
        ImageView imageView3= new ImageView(fond3);
        imageView3.setOpacity(0);
        RotateTransition rt = new RotateTransition(Duration.millis(0.1), imageView3);
        rt.setToAngle(-180);
        rt.play();
        imageView3.setFitHeight(Parametre.getHauteurFenetre()/4);
        imageView3.setFitWidth(Parametre.getHauteurFenetre()/4/1.5);

        imageView3.setTranslateX(Parametre.getLargeurFenetre()/2-Parametre.getLargeurFenetre()/8/1.5);
        imageView3.setTranslateY(Parametre.getHauteurFenetre()/2-Parametre.getHauteurFenetre()/8);
        root.getChildren().add(imageView3);
       
        String titre = "HYPERARCHIPEL";
        RectangleHashi imageView4 = new RectangleHashi(titre,Parametre.getLargeurFenetre()-100,85);
        imageView4.setOpacityRect(0);
        imageView4.setAsTitle();
        imageView4.setOpacity(0);
        imageView4.setTranslateX(50);
        imageView4.setTranslateY(100);
        root.getChildren().add(imageView4);
        
        String soustitre = "Une adaptation du jeu de logique\nHASHIWOKAKERO\n橋 を か け ろ";
        RectangleHashi imageView5 = new RectangleHashi(soustitre,Parametre.getLargeurFenetre()-200,40);
        imageView5.setOpacityRect(0);
        imageView5.setAsTitle();
        imageView5.setStrokeWidth(1);
        imageView5.setOpacity(0);
        imageView5.setTranslateX(100);
        imageView5.setTranslateY(Parametre.getHauteurFenetre()/4*3);
        root.getChildren().add(imageView5);
        
        ft2 = new FadeTransition(Duration.seconds(27),imageView);
        ft2.setFromValue(0);
        ft2.setToValue(1);
        
        ft3 = new FadeTransition(Duration.seconds(27),imageView2);
        ft3.setFromValue(0);
        ft3.setToValue(0.5);
        
        ft4 = new FadeTransition(Duration.seconds(27),imageView3);
        ft4.setFromValue(0);
        ft4.setToValue(1);
        
        ft5 = new FadeTransition(Duration.seconds(27),imageView4);
        ft5.setFromValue(0);
        ft5.setToValue(1);
        ft5.setAutoReverse(true);
        ft5.setCycleCount(1);
        
        ft6 = new FadeTransition(Duration.seconds(35),imageView5);
        ft6.setFromValue(0);
        ft6.setToValue(1);
        
        ft2.play();
        ft3.play();
        ft4.play();
        ft5.play();
        
        tt0 = new TranslateTransition(Duration.seconds(1),imageView3);
        
        tt0.setToX(imageView3.getTranslateX()-5);
        
        tt = new TranslateTransition(Duration.seconds(1),imageView3);
        tt.setToX(Parametre.getLargeurFenetre()+1000);
        
        ft5.setOnFinished(evt -> { 
            ft6.play();
        });
        
        ft6.setOnFinished(evt -> { 
            tt0.play();
        });
        
        
        tt0.setOnFinished(evt -> { 
            tt.play(); 
        });
        tt.setOnFinished(evt -> { 
            VueEcranTitre.primaryStage.setScene(new VueMenuPrincipal(VueHashi.primaryStage).getScene()); 
            nullify();
        });
        
    }
    
    /**
    * Méthode qui met à null les pointeurs
    */
    @Override
    protected void nullify(){
        ft = null; ft2 = null; ft3 =  null; 
        ft4 =  null; ft5 =  null; ft6 = null; 
        tt0 = null; tt = null;
        System.gc();
        Runtime.getRuntime().gc();
    }
    
    //////////////////////////////////////////////////////////////////////////////////
    // *****************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    
    private class DialogueTitre extends DialoguePane {

        private DialogueTitre() {
            super();
            texte = new Text();
            texte.setFont(Font.font(21.5));
            texte.setFill(Color.GOLD);
            texte.setStroke(Color.BLACK);
            texte.setStrokeWidth(0.5);
            texte.setTextAlignment(TextAlignment.CENTER);
            this.getChildren().add(texte);
        }

        @Override
        protected void initTransition() {
            ft = new FadeTransition(Duration.millis(43), this); /* 43 pour synchroniser à la musique */
            ft.setFromValue(1);
            ft.setToValue(1);
            ft.setAutoReverse(true);  
        }

        @Override
        protected void actualiseDialogue() {
            this.texte.setText(Dialogue.getDialogue().next());
            ft.play();
            ft.setOnFinished(evt -> {
                if (Dialogue.getDialogue().hasNext()) {
                    actualiseDialogue();
                } else {
                    initEcranTitre();
                }
            });
        }
    }
}