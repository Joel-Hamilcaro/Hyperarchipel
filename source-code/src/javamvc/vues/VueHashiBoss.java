/*
*
* Nom de la classe : VueHashiBoss
* 
* Description : 
*   Interfac graphique du chapitre final du scénario
*
* Version : 1.0
*
* Date : Mai 2019
*
* Auteur : PI4 / HASHIWOKAKERO1
*
*/
package javamvc.vues;


import javamvc.controleur.ControleurHashi;
import javamvc.modele.Parametre;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class VueHashiBoss extends VueHashiScenario {
    
    // CONSTRUCTEURS ***************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Constructeur de VueHashiBoss (Chapitre final)
     * @param c Controleur
     */
    public VueHashiBoss(ControleurHashi c){
        super(c);
        this.stopMusic();
        this.music(1);
        
        Rectangle bg = new Rectangle(Parametre.getLargeurFenetre(),Parametre.getHauteurFenetre());
        bg.setOpacity(0.5);
        Rectangle bg2 = new Rectangle(Parametre.getLargeurFenetre(),Parametre.getHauteurFenetre());
        bg2.setOpacity(0.3);
        bg.setFill(Color.ORANGERED);
        bg2.setFill(Color.BLACK);
        VueHashiBoss.root.getChildren().add(root.getChildren().size()-4,bg);
        VueHashiBoss.root.getChildren().add(root.getChildren().size()-4,bg2);
    }
    
    // METHODES ********************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
   
    
    @Override
    public void inonder(){        
        ivf.setFitHeight(Parametre.getHauteurFenetre()*2);
        ivf.setFitWidth(Parametre.getLargeurFenetre()*2);
        ivf.setTranslateX(-Parametre.getHauteurFenetre()/2);
        ivf.setTranslateY(-Parametre.getLargeurFenetre()/2);
        ivf.setOpacity(0);
        int x = (int)baio.getTranslateX();
        int y = (int)baio.getTranslateY();
        int x0 = (int)baio.getTranslateX();
        int y0 = (int)baio.getTranslateY();
        int angle=0;
        if (x0<x && x-x0>Math.abs(y-y0)) angle = 180; 
        else if (x0>x && x0-x>Math.abs(y-y0)) angle = 0;
        else if (y0>y && y0-y>Math.abs(x-x0)) angle = 90;
        else if (y>y0 && y-y0>Math.abs(x-x0)) angle = 270;
        TranslateTransition tt = new TranslateTransition(Duration.seconds(3), baio);
        tt.setToX(x);
        tt.setToY(y);
        RotateTransition rt = new RotateTransition(Duration.seconds(1),baio);
        rt.setToAngle(angle);
        rt.play();
        tt.play();
        FadeTransition ft = new FadeTransition(Duration.millis(1000), ivf);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setAutoReverse(true);
        FadeTransition ft2 = new FadeTransition(Duration.millis(1000), iv);  
        ft2.setFromValue(0);
        ft2.setToValue(1);
        ft2.setAutoReverse(true);
        tt.play();
        tt.setOnFinished( evt -> {
            
            VueHashiBoss.root.getChildren().add(ivf);
                Rectangle bg = new Rectangle(Parametre.getLargeurFenetre(), Parametre.getHauteurFenetre());
                bg.setOpacity(0);
                bg.setFill(Color.ORANGERED);
                VueHashiBoss.root.getChildren().add(bg);
                FadeTransition ft3 = new FadeTransition(Duration.millis(1000), bg);
                ft3.setFromValue(0);
                ft3.setToValue(1);
                ft3.setAutoReverse(true);
                ft3.play(); 
            
            ft.play();
        });
        ft.setOnFinished(evt -> {
            VueHashiBoss.root.getChildren().remove(iv);
            iv.setOpacity(0);
            VueHashiBoss.root.getChildren().add(iv);
                Rectangle bg = new Rectangle(Parametre.getLargeurFenetre(), Parametre.getHauteurFenetre());
                bg.setOpacity(0);
                bg.setFill(Color.ORANGERED);
                VueHashiBoss.root.getChildren().add(bg);
                FadeTransition ft3 = new FadeTransition(Duration.millis(1000), bg);
                ft3.setFromValue(0);
                ft3.setToValue(1);
                ft3.setAutoReverse(true);
                ft3.play();
            
            ft2.play();
            
        });
        ft2.setOnFinished(evt -> {
             VueHashiBoss.root.getChildren().remove(ivf);
             this.plateauGraphique.getChildren().remove(baio);
             this.cont.termine();
             nullify();
        });
    }
    
    
}
