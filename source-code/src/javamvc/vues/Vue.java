/*
*
* Nom de la classe : Vue 
* 
* Description : 
*   Vue abstraite. Fonctions de base liées aux vues : lecture des images, sons,
*   classes internes liées au design commun à toutes les vues (style des 
*   boutons ... etc)
*
* Version : 1.0
*
* Date : Mai 2019
*
* Auteur : PI4 / HASHIWOKAKERO1
*
 */
package javamvc.vues;

import javamvc.modele.Parametre;
import java.io.IOException;
import java.net.URL;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public abstract class Vue {

    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    /**
     * Root de la scene (première couche du fond de l'interface graphique)
     */
    protected static Pane root;
    /**
     * Scene JavaFX
     */
    protected static Scene scene;
    /**
     * Audio en cours de lecture
     */
    protected static Clip clip;
    /**
     * ID de l'audio en cours de lecture
     */
    protected static int idMusic;
    /**
     * Fenêtre ouverte (Stage JavaFX)
     */
    protected static Stage primaryStage;
    
    // CONSTRUCTEURS ***************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Constructeur de vue
     *
     * @param primaryStage Fenêtre ouverte (Stage JavaFX)
     */
    public Vue(Stage primaryStage) {
        Vue.primaryStage = primaryStage;
        root = new Pane();
        root.setPrefSize(Parametre.getLargeurFenetre(), Parametre.getHauteurFenetre());        
    }

    /**
     * Constructeur de vue
     *
     * @param primaryStage Fenêtre ouverte (Stage JavaFX)
     * @param title Titre de la fenètre
     */
    protected Vue(Stage primaryStage, String title) {
        this(primaryStage);
        Vue.primaryStage.setTitle(title);
    }

    // ACCESSEURS ******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Accesseur. Scene en cours.
     *
     * @return Scene JavaFX
     */
    public Scene getScene() {
        return Vue.scene;
    }

    /**
     * Accesseur. Audio en cours.
     *
     * @return Clip audio
     */
    protected Clip getClip() {
        return clip;
    }

    // METHODES ********************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    /**
     * Ajoute un fond coloré au root (première couche de fond)
     *
     * @param color couleur choisie
     */
    protected void addFondCouleur(Color color) {
        Rectangle rect = new Rectangle(Parametre.getLargeurFenetre(), Parametre.getHauteurFenetre());
        rect.setFill(color);
        rect.setOpacity(1);
        root.getChildren().add(rect);
    }

    /**
     * Ajoute une image de fond au root (premiere couche de fond)
     *
     * @param chemin Chemin du fichier
     * @param opacity Opacité de l'image
     */
    protected void addImageView(String chemin, double opacity) {
  
        chemin = getClass().getResource(chemin).toString();
        Image fond = new Image(chemin);

        ImageView imageView = new ImageView(fond);
        imageView.setOpacity(opacity);
        imageView.setFitWidth(Parametre.getLargeurFenetre());
        imageView.setFitHeight(Parametre.getHauteurFenetre());
        root.getChildren().add(imageView);
        
    }

    /**
     * Charge et retourne une image
     *
     * @param chemin Chemin d'acces du fichier image
     * @return Image
     */
    protected final Image loadImage(String chemin) {
        return new Image(getClass().getResource(chemin).toString());
    }

    /**
     * Ajoute une image de fond au root (premiere couche de fond)
     *
     * @param chemin Chemin du fichier
     */
    protected void addImageView(String chemin) {
        addImageView(chemin, 1);
    }

    /**
     * Fondu du noir vers la scene.
     */
    protected void beginScene() {
        Rectangle rect = new Rectangle(Parametre.getLargeurFenetre(), Parametre.getHauteurFenetre());
        rect.setFill(Color.BLACK);
        rect.setOpacity(1);
        root.getChildren().add(rect);
        FadeTransition ft = new FadeTransition(Duration.millis(500), rect);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setAutoReverse(true);
        ft.play();
        ft.setOnFinished(evt -> {
            root.getChildren().remove(rect);
        });
    }

    /**
     * Lecture d'une musique
     *
     * @param n ID de l musique
     */
    protected final void music(int n) {
        stopMusic();
        try {
            if (Vue.clip != null) {
                if (!Parametre.isAudioMute()) {
                    Vue.clip.start();
                }
            } else {
                URL s = null;
                switch (n) {
                    case 0:
                        s = getClass().getResource(Parametre.FILE_MUSIC_0);
                        Vue.idMusic = 0;
                        break;
                    case 1:
                        s = getClass().getResource(Parametre.FILE_MUSIC_1);
                        Vue.idMusic = 1;
                        break;
                    case 2:
                        s = getClass().getResource(Parametre.FILE_MUSIC_2);
                        Vue.idMusic = 2;
                        break;
                    case 3:
                        s = getClass().getResource(Parametre.FILE_MUSIC_3);
                        Vue.idMusic = 3;
                        break;
                    case 4:
                        s = getClass().getResource(Parametre.FILE_MUSIC_4);
                        Vue.idMusic = 4;
                        break;

                }
                Clip clipLoaded = AudioSystem.getClip();
                clipLoaded.open(AudioSystem.getAudioInputStream(s));
                Vue.clip = clipLoaded;
                clip.setLoopPoints(0, -1);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                if (!Parametre.isAudioMute()) {
                    clip.start();
                } else {
                    clip.stop();
                }
            }
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    /**
     * Arret de la musique.
     */
    protected final void stopMusic() {
        try {
            if (Vue.clip != null) {

                Vue.clip.stop();
                Vue.clip.close();
                Vue.clip = null;
                Vue.idMusic = -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Change les parametres du sons (muet / son).
     */
    protected static void switchMute() {
        if (!Parametre.isAudioMute()) {
            Parametre.setAudioMute(true);
            try {
                if (Vue.clip != null) {
                    Vue.clip.stop();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Parametre.setAudioMute(false);
            try {
                if (Vue.clip != null) {
                    Vue.clip.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Calcul d'angle de rotation pour les objet mobiles de la vue
     *
     * @param opp coté opposé
     * @param adj coté adjacent
     * @return angle
     */
    protected static int calculAngle(double opp, double adj) {
        double tan = opp / adj;
        return (int) (Math.atan(tan) * (180 / Math.PI));
    }
    
    /**
    * Méthode qui met à null les pointeurs
    */
    protected abstract void nullify();
    
    //////////////////////////////////////////////////////////////////////////////////
    // *****************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    protected static class MenuButton extends StackPane {

        private Text text;
        private Text textBis;
        private double largeur;
        private double hauteur;
        private Rectangle icone;

        /**
         * Constructeur de MenuButton.
         *
         * @param name Texte du bouton
         */
        protected MenuButton(String name) {
            this(name, name);
            textBis.setFont(Font.font(30)); // Taille texte
        }

        /**
         * Constructeur de MenuButton.
         *
         * @param name Texte du bouton
         * @param bis Texte alternatif (quand le bouton est en surbrillance)
         */
        protected MenuButton(String name, String bis) {
            this(name, bis, Parametre.getLargeurFenetre() / 2, 50);
        }

        /**
         * Constructeur de MenuButton.
         *
         * @param name Texte du bouton
         * @param bis Texte alternatif (quand le bouton est en surbrillance)
         * @param largeur Largeur du bouton
         * @param hauteur Hauteur du bouton
         */
        protected MenuButton(String name, String bis, double largeur, double hauteur) {
            name = "\t" + name;
            bis = "\t" + bis;
            this.largeur = largeur;
            this.hauteur = hauteur;
            text = new Text(name); // Texte
            text.setFont(Font.font(hauteur * 0.6)); // Taille texte
            text.setFill(Color.WHITE); // Couleur texte
            textBis = new Text(bis); // Texte
            textBis.setFont(Font.font(hauteur * 0.4)); // Taille texte
            textBis.setFill(Color.WHITE); // Couleur texte                      
            Rectangle bg = new Rectangle(largeur, hauteur); // Forme du fond du bouton 
            bg.setOpacity(0.6);
            bg.setFill(Color.BLACK);
            bg.setEffect(new GaussianBlur(hauteur * 0.6)); // Effet de flou du rectangle
            setAlignment(Pos.CENTER); // Position du texte // 2 3
            setRotate(0); // Inclinaison des boutons
            super.getChildren().addAll(bg, text);
            setOnMouseEntered(event -> {
                bg.setTranslateX(0); // Decalage au survol du rectangle
                bg.setFill(Color.WHITE); // Changement de couleur du rectangle au survol
                super.getChildren().remove(text);
                super.getChildren().add(textBis);
                textBis.setFill(Color.BLACK); // Changement de couleur du texte au survol
            });
            setOnMouseExited(event -> { // Retour a la normal au dé-survol
                bg.setTranslateX(0);
                super.getChildren().remove(textBis);
                setAlignment(Pos.CENTER); // Position du texte // 2 3
                setRotate(0); // Inclinaison des boutons
                super.getChildren().add(text);
                text.setTranslateX(0);
                bg.setFill(Color.BLACK);
                text.setFill(Color.WHITE);
            });
            DropShadow effet = new DropShadow(10, Color.WHITE);
            effet.setInput(new Glow());
            setOnMousePressed(event -> setEffect(effet));
            setOnMouseReleased(event -> setEffect(null));
        }

        /**
         * Constructeur de MenuButton.
         *
         * @param name Texte du bouton
         * @param bis Texte alternatif (quand le bouton est en surbrillance)
         * @param largeur Largeur du bouton
         * @param hauteur Hauteur du bouton
         * @param petit true si la police du texte doit être reduit.
         */
        protected MenuButton(String name, String bis, double largeur, double hauteur, boolean petit) {
            this(name, bis, largeur, hauteur);
            if (petit) {
                text.setFont(Font.font(hauteur * 0.4));
            }
        }

        /**
         * MUTATEUR. Change le texte du bouton
         *
         * @param s Texte
         * @param petit true si la police du texte doit être réduite
         */
        protected void setText(String s, boolean petit) {
            setText(s);
            if (petit) {
                text.setFont(Font.font(hauteur * 0.4));
            }
        }
        
        /**
         * MUTATEUR. Change le texte du bouton
         *
         * @param s Texte
         */
        protected void setText(String s) {
            s = "\t" + s;
            super.getChildren().remove(text);
            super.getChildren().remove(textBis);
            text = new Text(s); // Texte
            text.setFont(Font.font(hauteur * 0.6)); // Taille texte
            text.setFill(Color.WHITE); // Couleur texte
            textBis = new Text(s); // Texte
            textBis.setFont(Font.font(hauteur * 0.4)); // Taille texte
            textBis.setFill(Color.BLACK); // Couleur texte
            if (!super.getChildren().contains(textBis)) {
                super.getChildren().add(textBis);
            }
        }
        
        protected void setText(String s, String bis, boolean petit) {
            s = "\t" + s;
            bis = "\t" + bis;
            super.getChildren().remove(text);
            super.getChildren().remove(textBis);
            text = new Text(s); // Texte
            text.setFont(Font.font(hauteur * 0.6)); // Taille texte
            text.setFill(Color.WHITE); // Couleur texte
            textBis = new Text(bis); // Texte
            textBis.setFont(Font.font(hauteur * 0.4)); // Taille texte
            textBis.setFill(Color.BLACK); // Couleur texte
            if (!super.getChildren().contains(textBis)) {
                super.getChildren().add(textBis);
            }
            if (petit) {
                text.setFont(Font.font(hauteur * 0.4));
                textBis.setFont(Font.font(hauteur * 0.4));
            }
        }

        /**
         * Ajoute une icone au bouton.
         *
         * @param chemin Chemin d'accès du fichier image
         * @param gauche Position de l'icone : true si à gauche.
         */
        protected void addIcon(String chemin, boolean gauche) {
            Rectangle r = dessineIcone(chemin);
            if (gauche) {
                r.setTranslateX(-largeur / 2 + largeur / 8); // 2 3
            } else {
                r.setTranslateX(150);
            }
            this.icone = r;
            this.getChildren().addAll(r);
        }

        /**
         * Retire l'icone au bouton.
         *
         */
        protected void removeIcon() {
            this.getChildren().remove(this.icone);
            this.icone = null;
        }

        /**
         * Dessine l'icone du bouton.
         *
         * @param chemin Chemin d'accès du fichier image
         */
        private Rectangle dessineIcone(String chemin) {
            Rectangle r = new Rectangle(this.hauteur / 3 * 2, this.hauteur / 3 * 2);
            r.setLayoutY(10.0);
            Image im = new Image(chemin);
            r.setFill(new ImagePattern(im));
            return r;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    // *****************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    protected static class RectangleHashi extends StackPane {

        private Rectangle bg;
        private Text text;

        /**
         * Constructeur de RectangleHashi.
         *
         * @param largeur Largeur du rectangle
         * @param hauteur Hauteur du rectangle
         */
        protected RectangleHashi(double largeur, double hauteur) {
            this("", largeur, hauteur);
        }

        /**
         * Constructeur de RectangleHashi.
         *
         * @param name Texte du rectangle
         * @param largeur Largeur du rectangle
         * @param hauteur Hauteur du rectangle
         */
        protected RectangleHashi(String name, double largeur, double hauteur) {
            text = new Text(name); // Texte
            text.setFont(Font.font(hauteur * 0.8)); // Taille texte
            text.setFill(Color.WHITE); // Couleur texte

            this.bg = new Rectangle(largeur, hauteur); // Forme du fond du bouton 
            bg.setOpacity(0.6);
            bg.setFill(Color.BLACK);
            bg.setEffect(new GaussianBlur(hauteur * 0.8)); // Effet de flou du rectangle
            super.getChildren().addAll(bg, text);
        }

        /**
         * Change l'opacité du rectangle.
         *
         * @param x Opacité
         */
        protected void setOpacityRect(double x) {
            this.bg.setOpacity(x);
        }

        /**
         * Définit le texte du rectangle dans le style d'un texte titre.
         */
        protected void setAsTitle() {
            text.setFill(Color.BLACK);
            text.setStroke(Color.WHITE);
            text.setStrokeWidth(2);
            text.setTextAlignment(TextAlignment.CENTER);
        }

        /**
         * Change l'épaisseur du contour du texte.
         *
         * @param x Epaisseur.
         */
        protected void setStrokeWidth(double x) {
            text.setStrokeWidth(x);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    // *****************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    protected abstract class DialoguePane extends StackPane {

        /**
         * Texte affiché à l'écran.
         */
        protected Text texte;

        /**
         * Contructeur du DialoguePane.
         *
         * @param largeur Largeur du rectangle contenant le texte
         * @param hauteur Hauteur du rectangle contenant le texte
         */
        protected DialoguePane(int largeur, int hauteur) {
            super();
            texte = new Text();
            texte.setFont(Font.font(20));
            texte.setFill(Color.WHITESMOKE);
            texte.setStroke(Color.WHEAT);
            texte.setStrokeWidth(0.5);
            texte.setTextAlignment(TextAlignment.CENTER);
            RectangleHashi c0 = new RectangleHashi(largeur, hauteur);
            c0.getChildren().add(texte);
            this.getChildren().add(c0);
        }

        /**
         * Constructeur du DialoguePane par défaut aux dimensions de l'écran.
         */
        protected DialoguePane() {
            this(Parametre.getLargeurFenetre(), Parametre.getHauteurFenetre());
        }

        /**
         * Méthode qui définit la transition entre deux boîtes de
         * dialogue.
         */
        protected abstract void initTransition();

        /**
         * Méthode qui actualise les boîtes de dialogue.
         */
        protected abstract void actualiseDialogue();

    }

}
