/*
*
* Nom de la classe : VueMenuFin
* 
* Description : 
*   Interface graphique du menu de fin de partie.
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
import javamvc.modele.Niveaux;
import javamvc.controleur.ControleurHashi;
import javamvc.modele.Parametre;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class VueMenuFin extends Vue {

    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    private static MenuFin menuFin;
    private static ModeleHashi modele;
    
    // CONSTRUCTEURS ***************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Constructeur du menu de fin.
     * @param primaryStage Fenetre
     * @param m Modele qui vient d'être fini.
     * @param img Screenshot de fin.
     * @param victoire true en cas de victoire.
     */
    public VueMenuFin(Stage primaryStage, ModeleHashi m, Image img, boolean victoire) {
        
        super(primaryStage);
        
        VueMenuFin.modele = m;
        Image fond = img;
        ImageView imageView = new ImageView(fond);
        imageView.setFitWidth(Parametre.getLargeurFenetre()+10);
        imageView.setFitHeight(Parametre.getHauteurFenetre()+10);
        imageView.setTranslateX(-5);
        imageView.setTranslateY(-5);

        // Menu principal
        VueMenuFin.menuFin = new MenuFin(VueMenuFin.primaryStage);
        VueMenuFin.menuFin.setVisible(true);
       
        // Root  
        root.getChildren().addAll(imageView, menuFin);
        
        // Victoire ou défaite
        String vic = "RÉUSSITE";
        if (!victoire) vic = "ÉCHEC";
        RectangleHashi victoireRect = new RectangleHashi(vic,Parametre.getLargeurFenetre(),100);
        victoireRect.setOpacityRect(0);
        victoireRect.setAsTitle();
        menuFin.menuNiveaux.getChildren().add(0,victoireRect); 
        
        // NIVEAUX ********************************************************************* 
        //******************************************************************************
        
        for (int i = modele.getID() - 1; i <= modele.getID() && modele.getID()<Niveaux.getNiveaux().size()-4; i++) {
            final int k = i;
            ModeleHashi md = Niveaux.getNiveaux().get(k);
            String s = "NIVEAU " + (k);
            if (k == modele.getID() - 1) {
                s = "REJOUER";
            }
            String s2 = "Meilleur temps : " + md.scoreToString();
            if (md.getID()==1) s2 = s;
            Vue.MenuButton btnNiveau = new Vue.MenuButton(s, s2);
            btnNiveau.setOnMouseClicked((MouseEvent event) -> {
                ControleurHashi cont = new ControleurHashi(md, VueMenuFin.primaryStage);
                VueMenuFin.primaryStage.setTitle("Niveau " + (k));
                VueMenuFin.primaryStage.setScene(cont.getContVue().getScene());
                nullify();
            });
            if (md.getScore()>0 && !s.equals("REJOUER")) btnNiveau.addIcon(Parametre.ICON_OUI, true);
            else if (!s.equals("REJOUER")) btnNiveau.addIcon(Parametre.ICON_NON, true);
            else if (s.equals("REJOUER")) btnNiveau.addIcon(Parametre.ICON_RESTART, true);
            menuFin.menuNiveaux.getChildren().addAll(btnNiveau);           
        }
        
        // CHAPITRES DU SCENARIO ******************************************************* 
        //******************************************************************************
        
        for (int i = modele.getID() - 1; i >= modele.getID()-2 && modele.getID()-2>=Niveaux.getNiveaux().size()-5 && modele.isStory() ; i--) {
            final int k = i;
            ModeleHashi md = Niveaux.getNiveaux().get(k);
            String s = "CHAPITRE " + (Niveaux.getNiveaux().size()-k);
            if (k == Niveaux.getNiveaux().size()-4){
                s = "CHAPITRE FINAL";
            }
            if (k == modele.getID() - 1){
                s = "REJOUER";
                if (!(modele.getScore()>0)) i = i-1;
            }
            else if (k == Niveaux.getNiveaux().size()-5 || !(modele.getScore()>0)){
                break;
            }

            MenuButton btnNiveau = new MenuButton(s,  "Meilleur temps : " + md.scoreToString());
            btnNiveau.setOnMouseClicked((MouseEvent event) -> {                    
                VueMenuFin.primaryStage.setTitle("Cinématique");
                VueMenuFin.primaryStage.setScene(new VueCinematique(Niveaux.getNiveaux().size()-k,VueMenuFin.primaryStage,md).getScene());
                nullify();
            });
            if (md.getScore()>0 && !s.equals("REJOUER")) btnNiveau.addIcon(Parametre.ICON_OUI, true);
            else if (!s.equals("REJOUER")) btnNiveau.addIcon(Parametre.ICON_NON, true);
            else if (s.equals("REJOUER")) btnNiveau.addIcon(Parametre.ICON_RESTART, true);
            menuFin.menuNiveaux.getChildren().addAll(btnNiveau);
        }
        
        // MENU ALÉATOIRE ************************************************************** 
        //******************************************************************************
        
        if (modele.getID()>21){
            for (int i = 5; i < 10; i += 1) {
                final int k = i;
                MenuButton btnNiveau = new MenuButton("ALÉATOIRE " + k + "x" + k);
                
                btnNiveau.setOnMouseClicked((MouseEvent event) -> {          
                    nullify();
                    Runnable r = () -> {
                        VueChargement vc = new VueChargement(VueMenuFin.primaryStage, k);
                        Runnable r2 = () -> {
                            vc.chargement();
                        };
                        Platform.runLater(r2);
                    };
                    Platform.runLater(r);

                });
                btnNiveau.addIcon(Parametre.ICON_DICE, true);
                menuFin.menuNiveaux.getChildren().addAll(btnNiveau);
            }
        }
        
        menuFin.menuNiveaux.getChildren().add(menuFin.btnQuitter);        
        VueMenuFin.scene = new Scene(root);
        beginScene();
        
    }

    //////////////////////////////////////////////////////////////////////////////////
    // *****************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    
    private class MenuFin extends Parent {

        // ATTRIBUTS 
      
        VBox menuNiveaux = new VBox(30);
        Vue.MenuButton btnQuitter;
        Vue.MenuButton btnMenuPrincipal;

        // CONSTRUCTEUR 
        public MenuFin(Stage primaryStage) {

            menuNiveaux.setTranslateX(0);
            menuNiveaux.setTranslateY(Parametre.getHauteurFenetre() / 8);

            // Distance effet de transition          
            btnQuitter = new Vue.MenuButton("MENU PRINCIPAL");
            
            btnQuitter.setOnMouseClicked(event -> {
                VueMenuFin.primaryStage.setScene(new VueMenuPrincipal(VueMenuFin.primaryStage).getScene());
                nullify();
            });
            btnQuitter.addIcon(Parametre.ICON_MAIN, true);

          
            Rectangle bg = new Rectangle(Parametre.getLargeurFenetre(), Parametre.getHauteurFenetre());
            bg.setFill(Color.BLACK);
            bg.setOpacity(0.4);
            super.getChildren().add(bg);

            super.getChildren().addAll(menuNiveaux);

        }
    }
    
    /**
    * Méthode qui met à null les pointeurs
    */
    @Override
    protected void nullify(){
        VueMenuFin.menuFin = null;
        VueMenuFin.modele = null;
        System.gc();
        Runtime.getRuntime().gc();
    }
}
