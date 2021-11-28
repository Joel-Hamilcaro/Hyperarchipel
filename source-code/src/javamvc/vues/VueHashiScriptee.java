/*
*
* Nom de la classe : VueHashiScriptee
* 
* Description : 
*   Vue Abstraite liées aux parties scriptées du jeu : tutoriel, scénario.
*   
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
import javamvc.controleur.ControleurHashi;
import javamvc.modele.Parametre;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public abstract class VueHashiScriptee extends VueHashi {
    
    // CONSTRUCTEURS ***************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
       
    /**
     * Constructeur de VueHashiScriptee.
     * @param c Controleur.
     */
    public VueHashiScriptee(ControleurHashi c) {
        super(c);
    }
   
    //////////////////////////////////////////////////////////////////////////////////
    // *****************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    
    protected class ObjetMobile extends StackPane {

        private ImagePattern ip;

        protected ObjetMobile() {
            this(false);
        }

        protected ObjetMobile(boolean tuto) {
            super();
            if (tuto) {
                String s = Parametre.ICON_SURVOL2;
                Circle c0 = cercleMobile();
                Image im = new Image(s);
                this.ip = new ImagePattern(im);
                c0.setFill(ip);
                super.getChildren().addAll(c0);
            } else {
                String s = Parametre.FILE_BAIO;
                Rectangle c0 = rectangleMobile();
                Image im = new Image(s);
                this.ip = new ImagePattern(im);
                c0.setFill(ip);
                super.getChildren().addAll(c0);
            }
        }

        protected Rectangle rectangleMobile() {
            Rectangle c0 = new Rectangle((Math.min(largeurCase / 2.0, hauteurCase / 2.0) - 3)/1.5,(Math.min(largeurCase / 2.0, hauteurCase / 2.0) - 3));
            c0.setFill(Color.LIGHTGREEN);
            return c0;
        }
        
        protected Circle cercleMobile() {
            Circle c0 = new Circle(Math.min(largeurCase / 4.0, hauteurCase / 4.0) - 3);
            c0.setFill(Color.LIGHTGREEN);
            return c0;
        }
    }
    
    //////////////////////////////////////////////////////////////////////////////////
    // *****************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    
    protected class DialogueHashi extends DialoguePane {

        protected FadeTransition ft;
        protected FadeTransition ft2;
        private double opacity;

        protected DialogueHashi() {
            this(Parametre.getHauteurFenetre(), 1);
        }

        protected DialogueHashi(int hauteur) {
            this(hauteur, 1);
        }

        protected DialogueHashi(int hauteur, double opacity) {
            super(Parametre.getLargeurFenetre(), hauteur);
            this.opacity = opacity;
            initTransition();
            this.setOnMouseClicked(event -> {
                if (ft.getStatus() == Animation.Status.RUNNING) {
                    return;
                }
                if (Dialogue.getDialogue().hasNext()) {
                    actualiseDialogue();
                } else {
                    root.getChildren().remove(this);
                    if ((VueHashiScriptee.this instanceof VueHashiScenario)){
                        if (!cont.getContMod().estFini() && !cont.getContMod().isPerdu() ) {
                            ((VueHashiScenario)VueHashiScriptee.this).quitterBaio();
                        } else if (cont.getContMod().isPerdu()) {
                            ((VueHashiScenario)VueHashiScriptee.this).inonder();
                        } else {
                            cont.termine();
                        }
                    }
                }
            });
           
        }

        @Override
        public void initTransition() {
            ft = new FadeTransition(Duration.millis(125), this);
            ft.setFromValue(opacity);
            ft.setToValue(0);
            ft.setAutoReverse(true);
            ft2 = new FadeTransition(Duration.millis(125), this);
            ft2.setFromValue(0);
            ft2.setToValue(opacity);
        }

        @Override
        public void actualiseDialogue() {

            ft.play();
            ft.setOnFinished(evt -> {
                if (Dialogue.getDialogue().hasNext()) {
                    if ((VueHashiScriptee.this instanceof VueHashiScenario)) {
                        this.texte.setText(Dialogue.getDialogue().next());
                    } else {
                        this.texte.setText("_____________________________________________________________\n\n"
                                + Dialogue.getDialogue().next()
                                + "\n_____________________________________________________________\n"
                                + "");
                    }
                    ft2.play();
                }
            });
        }
    }   
}
