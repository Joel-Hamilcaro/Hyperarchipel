/*
*
* Nom de la classe : VueHashi
* 
* Description : 
*   Interface graphique du jeu
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
import javamvc.modele.Chronometre;
import javamvc.modele.Case;
import javamvc.modele.Niveaux;
import javamvc.controleur.ControleurHashi;
import javamvc.modele.Parametre;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class VueHashi extends Vue {

    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Controleur de la partie.
     */
    protected final ControleurHashi cont;
    
    /**
     * Partie supérieure de l'interface graphique contenant
     * le bouton du menu pause, le chronomètre et les boutons jokers.
     */
    protected final Group infos;
    
    /**
     * Partie de l'interface graphique contenant le plateau.
     */
    protected final Group plateauGraphique;
    
    /**
     * Menu pause.
     */
    protected static VBox menuPause;
    
    /**
     * Menu parametre.
     */
    protected static VBox menuParametre;
    
    /**
     * Chronomètre affiché à l'écran.
     */
    private StackPane chronoGraphique;
    
    /**
     * Animation de fond des vagues à l'écran.
     */
    protected ImageView iv;
    
    /**
     * Adresse de l'image de fond qui a été chargée.
     * (Evite aux classes filles de recharger l'image).
     */
    protected final Image im;
    
    /**
     * Stocke les adresses des Lignes qui représentent les ponts.
     */
    protected final Line[][][] ponts; 
    
    /**
     * Stocke les adresses des iles graphiques.
     */
    protected final IleGraphique[][] ilesGraphique; 
    
    /**
     * Stocke l'adresse des iles selectionnées.
     */
    protected final IleGraphique[] selec = new IleGraphique[2];
    
    /**
     * Nombre d'iles selectionnées.
     */
    protected int nbCliques = 0;
    
    /**
     * Stocke l'adresse de l'animation en cours, s'il y en a une.
     */
    protected static TranslateTransition animationEnCours;
    
    /**
     * Vérifie si l'on est dans le menu pause.
     */
    protected boolean enPause = false;
    
    /**
     * Vérifie si l'on est dans le menu parametre.
     */
    protected boolean enParametre = false;
    
    /**
     * Largeur d'une case.
     */
    protected final int largeurCase;
    
    /**
     * Hauteur d'une case.
     */
    protected final int hauteurCase;
    
    /**
     * Coordonnée horizontale de déplacement des menus pause et parametre.
     */
    protected final int deplacement = 3000;
    
    /**
    * Rayon d'une ile graphique.
    */
    protected final double rayon;
    
    /**
    * Distance entre l'extrémité gauche de la case virtuelle et l'extrémité 
    * gauche de l'ile graphique de la case.
    */
    protected final double decalageX;
    
    /**
    * Distance entre l'extrémité du haut de la case virtuelle et l'extrémité 
    * du haut de l'ile graphique de la case.
    */
    protected final double decalageY;
    
    /**
    * Distance entre l'extremité maximale de l'ile graphique (en X et Y)
    * et l'extremité d'un pont double. 
    * En prenant une distance de 5px par rapport à la position d'un pont simple.
    * Par Pythagore : rayon² = 5² + decalage2².
    */
    protected final double decalage2;
    
    
    // CONSTRUCTEUR ****************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Constructeur de VueHashi
     * @param c Controleur
     */
    public VueHashi(ControleurHashi c) {
        super(c.getPrimaryStage());
        this.cont = c;
        if (!c.isStory()) this.music(2);
        ponts = new Line[cont.length()][cont.length()][2];
        ilesGraphique = new IleGraphique[cont.length()][cont.length()];
        this.im = loadImage(Parametre.FILE_WAVE);
        this.iv = new ImageView(im);
        iv.setFitHeight(Parametre.getHauteurFenetre());
        iv.setFitWidth(Parametre.getLargeurFenetre());
        if (Parametre.isAnime()) {
            VueHashi.root.getChildren().add(iv);
        }
        infos = new Group();
        infos.setLayoutX(25);
        infos.setLayoutY(0);
        VueHashi.root.getChildren().add(infos);
        this.plateauGraphique = new Group();
        this.plateauGraphique.setLayoutX(25);
        this.plateauGraphique.setLayoutY(50);
        this.hauteurCase = cont.calculeHauteurCase();
        this.largeurCase = cont.calculeLargeurCase();
        this.rayon = Math.min(largeurCase / 2.0, hauteurCase / 2.0);
        this.decalageY = Math.abs(largeurCase - Math.max(hauteurCase, largeurCase)) / 2.0;
        this.decalageX = Math.abs(hauteurCase - Math.max(hauteurCase, largeurCase)) / 2.0;
        this.decalage2 = rayon - Math.sqrt(rayon * rayon - 25);
        VueHashi.root.getChildren().add(plateauGraphique);
        VueHashi.scene = new Scene(root, Parametre.getLargeurFenetre(), Parametre.getHauteurFenetre(), Color.rgb(11,38,59));
        beginScene();    
    }

    // ACCESSEURS ******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    public StackPane getChronoGraphique() {
        return this.chronoGraphique;
    }

    public Group getInfos() {
        return this.infos;
    }

    public VBox getMenuParametre() {
        return VueHashi.menuParametre;
    }
    
    public VBox getMenuPause() {
        return VueHashi.menuPause;
    }

    public Group getPlateauGraphique() {
        return this.plateauGraphique;
    }

    public Pane getRoot() {
        return VueHashi.root;
    }

    
    // MUTATEUR ********************************************************************
    //******************************************************************************
    //******************************************************************************
    
    public void setEnPause(boolean b) {
        enPause = b;
    }

    // METHODES : ACTUALISATION DE L'AFFICHAGE *************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Dessine le chronometre 
     */
    public void dessineChrono() {
        StackPane sp = new ChronoGraphique();
        chronoGraphique = sp;
        sp.setLayoutX((Parametre.getLargeurFenetre() - 50 - 12.5 - Parametre.getLargeurFenetre() / 3 + Parametre.getLargeurFenetre() / 40 - 15) / 4);
        sp.setLayoutY(12.5);
        this.infos.getChildren().add(sp);
    }

    /**
     * Dessine les boutons jokers.
     */
    public void dessineJoker() { // Configuration de la zone des boutons de joker
        for (int i = 0; i < 3; i++) {
            StackPane sp = new JokerGraphique(i + 1);
            sp.setLayoutX(Parametre.getLargeurFenetre() - 50 - 12.5 - Parametre.getLargeurFenetre() / 3 + Parametre.getLargeurFenetre() / 3 / 3 * (i));
            sp.setLayoutY(12.5);
            if (true || !this.cont.getContMod().isStory()) {
                this.infos.getChildren().add(sp);
            }
        }
    }
    
    /**
     * Supprime la barre d'infos.
     */
    public void nettoyer() {
        VueHashi.root.getChildren().remove(infos);
    }
    
    
    
    /**
     * Dessine les iles graphiques 
     * @param i coordonnée X
     * @param j coordonnée Y
     */
    public void initDessin(int i, int j) {
        Case il = cont.getContMod().getPlateau().getCase(i, j);
        if (il != null && il.getNb() != -1) {
            IleGraphique sp = new IleGraphique(i, j);
            ilesGraphique[i][j] = sp;
            sp.setLayoutX(j * (largeurCase) + decalageX);
            sp.setLayoutY(i * (hauteurCase) + decalageY);
            this.plateauGraphique.getChildren().add(sp);
        }
    }
    
    /**
     * Verifie si la case graphique diffère de la case du modèle, 
     * et supprime les ponts à supprimer (ceux qui ont été supprimés
     * du modèle). Mais ne dessine pas encore les ponts à ajouter.
     * @param i coordonnée I du plateau (du haut vers le bas)
     * @param j coordonnée J du plateau (de gauche à droite)
     * @return true si la case du modèle est différent de celle de la vue
     */
    protected boolean changementEtat(int i, int j) {
        boolean b = false;
        Case il = cont.getContMod().getPlateau().getCase(i, j);
        int x = cont.getContMod().getJoker3()[0];
        int y = cont.getContMod().getJoker3()[1];
        /* On actualise toujours l'ile Joker 3 */
        if (x != -1 && y != -1) {
            if (!cont.getContMod().getSolution().getCase(x, y).equals(cont.getContMod().getPlateau().getCase(x, y))) {
                ilesGraphique[x][y].circleJoker.setOpacity(0.5);
            } else {
                ilesGraphique[x][y].circleJoker.setOpacity(0);
            }
        }
        /* Si pas de ponts traverse la case dans le modèle */
        if (il == null) {
            /* Si un pont graphique est dessiné */
            if (this.ponts[i][j][0] != null) {
                this.plateauGraphique.getChildren().remove(this.ponts[i][j][0]);
                this.ponts[i][j][0] = null;
                b = true;
            }
            /* Si un deuxième pont graphique est dessiné */
            if (this.ponts[i][j][1] != null) {
                this.plateauGraphique.getChildren().remove(this.ponts[i][j][1]);
                this.ponts[i][j][1] = null;
                b = true;
            }
            return b;
        }
        /* Sinon on supprime */     
        if (this.ponts[i][j][0] != null) {
            this.plateauGraphique.getChildren().remove(this.ponts[i][j][0]);
            this.ponts[i][j][0] = null;
            b = true;
        }
        if (this.ponts[i][j][1] != null) {
            this.plateauGraphique.getChildren().remove(this.ponts[i][j][1]);
            this.ponts[i][j][1] = null;
            b = true;
        }
        if (this.ponts[i][j][0] == null && this.ponts[i][j][1] == null) {
            b = true;
        }
        return b;
    }    

    /**
     * Dessine la case.
     * @param i coordonnée I du plateau (du haut vers le bas)
     * @param j coordonnée J du plateau (de gauche à droite)
     */
    public void dessineCase(int i, int j) {
        /* POUR LES TESTS DEVELOPPEURS */
        /*
        Text t = new Text("i"+i+"j"+j);
        t.setFill(Color.WHITE);
        t.setTranslateX(j*largeurCase);
        t.setTranslateY(i*hauteurCase);
        this.plateauGraphique.getChildren().add(t);
        */
        Case il = cont.getContMod().getPlateau().getCase(i, j);
        if (changementEtat(i, j)) {
            int f = 0;
            if (il != null) {
                switch (il.getDirection()) {
                    case "X ": {
                        if (cont.getContMod().getPlateau().getCases()[i][j].getPR() == 0) {
                            PontGraphique l1 = new PontGraphique("X1", i, j);
                            this.plateauGraphique.getChildren().add(l1);
                            this.ponts[i][j][0] = l1;
                            PontGraphique l2 = new PontGraphique("X2", i, j);
                            this.plateauGraphique.getChildren().add(l2);
                            this.ponts[i][j][1] = l2;
                        }
                        break;
                    }
                    case "| ": {
                        PontGraphique l = new PontGraphique("v", i, j);
                        this.plateauGraphique.getChildren().add(f, l);
                        this.ponts[i][j][0] = l;
                        break;
                    }
                    case "- ": {
                        PontGraphique l = new PontGraphique("h", i, j);
                        this.plateauGraphique.getChildren().add(f, l);
                        this.ponts[i][j][0] = l;
                        break;
                    }
                    case "= ": {
                        PontGraphique l1 = new PontGraphique("h1", i, j);
                        PontGraphique l2 = new PontGraphique("h2", i, j);
                        this.plateauGraphique.getChildren().add(f, l1);
                        this.plateauGraphique.getChildren().add(f, l2);
                        this.ponts[i][j][0] = l1;
                        this.ponts[i][j][1] = l2;
                        break;
                    }
                    case "$ ": {
                        PontGraphique l1 = new PontGraphique("v1", i, j);
                        PontGraphique l2 = new PontGraphique("v2", i, j);
                        this.plateauGraphique.getChildren().add(f, l1);
                        this.plateauGraphique.getChildren().add(f, l2);
                        this.ponts[i][j][0] = l1;
                        this.ponts[i][j][1] = l2;
                        break;
                    }
                    default:
                        break;
                }
            }
        }
    }
    
    // AUTRES METHODES *************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Construit le menu pause.
     */
    public void initMenuPause() {
        VueHashi.menuPause = new VBoxPause();
        VueHashi.menuParametre = new VBoxParametre();
    }
    
    /**
     * Active ou désactive l'animation.
     */
    public void switchAnimation() {
        if (Parametre.isAnime()) {
            VueHashi.root.getChildren().remove(iv);
            Parametre.setAnime(false);
        } else {
            VueHashi.root.getChildren().add(0, iv);
            Parametre.setAnime(true);
        }
    }

    /**
     * Active ou désactive le survol de la souris.
     */
    public void switchGameplay() {
        Parametre.setDoubleClic(!Parametre.isDoubleClic());
    }

    /**
     * Capture d'ecran en fin de partie.
     * @return Screenshot.
     */
    public WritableImage imagePlateau() {
        WritableImage plateauImg = root.snapshot(null, null);
        return plateauImg;
    }

   

     /**
    * Méthode qui met à null les pointeurs
    */
    @Override
    protected void nullify(){
        this.chronoGraphique = null;
        this.iv = null;
        System.gc();
        Runtime.getRuntime().gc();
    }
    
    //////////////////////////////////////////////////////////////////////////////////
    //******************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    
    
    protected class IleGraphique extends StackPane {

        private final Case ile;
        protected int x, y;
        protected boolean estClique;
        protected Circle c;
        private Circle circleJoker;
        protected ImagePattern ip;
        protected ArrayList<Integer[]> zoneDeplacement; 
        protected ArrayList<Integer[]> zoneScriptee;
        protected IleGraphique(int i, int j) {
            super(); 
            x = i;
            y = j;
            Text text = new Text(Integer.toString(cont.getContMod().getPlateau().getCase(i, j).getNb()));
            text.setFont(Font.font(rayon * 0.8));
            InnerShadow is = new InnerShadow();
            is.setOffsetX(4.0f);
            is.setOffsetY(4.0f);
            text.setEffect(is);
            text.setFill(Color.GREEN);
            text.setStroke(Color.DARKBLUE);
            text.setStrokeWidth(1);
            this.ile = cont.getContMod().getPlateau().getCase(i, j);
            Circle c0 = cercleIle();
            Random r = new Random();
            int k = r.nextInt(2); 
            Image im = new Image(Parametre.randomFileIle());
            this.ip = new ImagePattern(im);
            Circle c1 = cercleIle();
            c1.setFill(Color.RED);
            c1.setOpacity(0);
            c0.setFill(ip);
            super.getChildren().addAll(c0, c1);
            super.getChildren().add(text);
            this.c = c0;
            this.circleJoker = c1;
            this.setDefault();
        }

        protected void setDefault() {
            this.setOnMouseClicked(MouseEvent -> {
                if (enPause) {
                    return;
                }
                if (nbCliques < 1) {
                    selec[nbCliques] = this;
                    c.setOpacity(0.5);
                    estClique = true;
                    nbCliques++;
                } else if (nbCliques == 1) {
                    selec[nbCliques] = this;
                    c.setOpacity(0.5);
                    estClique = true;
                    nbCliques++;
                    if (nbCliques == 2) {
                        nbCliques++;
                        cont.getContMod().getPlateau().construirePont(selec[0].x, selec[0].y, selec[1].x, selec[1].y, true);
                        nbCliques = 0;
                        selec[0].c.setOpacity(1);
                        selec[0].c.setFill(ilesGraphique[selec[0].x][selec[0].y].ip);
                        selec[1].c.setOpacity(1);
                        selec[1].c.setFill(ilesGraphique[selec[1].x][selec[1].y].ip);
                        cont.actualiseVue();
                    }
                }
            });
            this.setOnMouseEntered(MouseEvent -> {
                if (enPause) {
                    return;
                }
                if (Parametre.isDoubleClic()) {
                    return;
                }
                if (nbCliques == 1) {
                    selec[nbCliques] = this;
                    c.setOpacity(0.5);
                    estClique = true;
                    nbCliques++;
                    if (nbCliques == 2) {
                        nbCliques++;
                        cont.getContMod().getPlateau().construirePont(selec[0].x, selec[0].y, selec[1].x, selec[1].y, true);
                        nbCliques = 0;
                        selec[0].c.setOpacity(1);
                        selec[0].c.setFill(ilesGraphique[selec[0].x][selec[0].y].ip);
                        selec[1].c.setOpacity(1);
                        selec[1].c.setFill(ilesGraphique[selec[1].x][selec[1].y].ip);
                        cont.actualiseVue();
                    }
                }
            });
        }
        
        protected Circle cercleIle() {
            Circle c0 = new Circle(Math.min(largeurCase / 2.0, hauteurCase / 2.0) - 1.5);
            c0.setFill(Color.LIGHTGREEN);
            c0.setStroke(Color.rgb(166, 128, 100));
            c0.setStrokeWidth(3);
            c0.setEffect(new GaussianBlur(1.5));
            return c0;
        }
        
        protected void indiquer(){
            this.c.setStroke(Color.RED);
            this.c.setStrokeWidth(6);
        }
        
        protected void desindiquer(){
            c.setStroke(new Color(244.0 / 255, 193.0 / 255, 40.0 / 255, 1));
            c.setStrokeWidth(3);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    //******************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    
    protected class ChronoGraphique extends StackPane {

        protected ChronoGraphique() {
            super();
            Chronometre chronometre = cont.getContChrono();
            String s = "Temps : " + String.format("%02d", chronometre.getMinutes()) + ":" + String.format("%02d", chronometre.getSecondes());
            RectangleHashi c0 = rectangleChrono(s);
            super.getChildren().add(c0);
        }

        protected final RectangleHashi rectangleChrono(String name) {
            RectangleHashi c = new RectangleHashi(name, Parametre.getLargeurFenetre() / 3, 25);
            return c;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    //******************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    
    protected class PontGraphique extends Line {

        String dir;
        int i, j;

        protected PontGraphique(String d, int i, int j) {
            this.dir = d;
            this.i = i;
            this.j = j;
            this.setStroke(Color.rgb(166, 128, 100));
            
            switch (dir) {
                case "X1":
                    double adj = Math.cos(Math.PI/4)*rayon;
                    double opp = Math.sin(Math.PI/4)*rayon;
                    this.setStartX(j * largeurCase + rayon - adj + decalageX); // haut gauche X
                    this.setStartY(i * hauteurCase + rayon - opp + decalageY); // haut gauche Y
                    this.setEndX(this.getStartX()+2*adj); // bas droite X
                    this.setEndY(this.getStartY()+2*opp);  // bas droite Y
                    this.setStroke(Color.BLACK);
                    break;
                case "X2":
                    double adj2 = Math.cos(Math.PI/4)*rayon;
                    double opp2 = Math.sin(Math.PI/4)*rayon;
                    this.setStartX(j * largeurCase + rayon - adj2 + decalageX); // bas gauche X
                    this.setStartY(i * hauteurCase + rayon + opp2 + decalageY); // bas gauche Y
                    this.setEndX(this.getStartX()+2*adj2); // haut droite X
                    this.setEndY(this.getStartY()-2*opp2); // haut droite Y
                    this.setStroke(Color.BLACK);
                    break;
                case "v":
                    this.setStartX(j * largeurCase + largeurCase / 2.0);
                    this.setStartY(i * hauteurCase - decalageY + 2.5);
                    this.setEndX(j * largeurCase + largeurCase / 2.0);
                    this.setEndY((i + 1) * hauteurCase + decalageY - 2.5);
                    break;
                case "v1":
                    this.setStartX(j * largeurCase + largeurCase / 2.0 - 5);
                    this.setStartY(i * hauteurCase - decalageY + 2.5 - decalage2);
                    this.setEndX(j * largeurCase + largeurCase / 2.0 - 5);
                    this.setEndY((i + 1) * hauteurCase + decalageY - 2.5 + decalage2);
                    break;
                case "v2":
                    this.setStartX(j * largeurCase + largeurCase / 2.0 + 5);
                    this.setStartY(i * hauteurCase - decalageY + 2.5 - decalage2);
                    this.setEndX(j * largeurCase + largeurCase / 2.0 + 5);
                    this.setEndY((i + 1) * hauteurCase + decalageY - 2.5 + decalage2);
                    break;
                case "h":
                    this.setStartX(j * largeurCase - decalageX + 2.5);
                    this.setStartY(i * hauteurCase + hauteurCase / 2.0);
                    this.setEndX((j + 1) * largeurCase + decalageX - 2.5);
                    this.setEndY(i * hauteurCase + hauteurCase / 2.0);
                    break;
                case "h1":
                    this.setStartX(j * largeurCase - decalageX + 2.5 - decalage2);
                    this.setStartY(i * hauteurCase + hauteurCase / 2.0 - 5);
                    this.setEndX((j + 1) * largeurCase + decalageX - 2.5 + decalage2);
                    this.setEndY(i * hauteurCase + hauteurCase / 2.0 - 5);
                    break;
                case "h2":
                    this.setStartX(j * largeurCase - decalageX + 2.5 - decalage2);
                    this.setStartY(i * hauteurCase + hauteurCase / 2.0 + 5);
                    this.setEndX((j + 1) * largeurCase + decalageX - 2.5 + decalage2);
                    this.setEndY(i * hauteurCase + hauteurCase / 2.0 + 5);
                    break;
                default:
                    break;
            }

            if (!dir.equals("X1") && !dir.equals("X2")) {
                this.setStrokeWidth(7);
                this.setOnMouseClicked(event -> {
                    if (enPause) {
                        return;
                    }
                    MouseButton mb = event.getButton();
                    int[][] r = this.getCoordIlesRattachées();
                    if (mb == MouseButton.PRIMARY) {
                        cont.getContMod().getPlateau().construirePont(r[0][0], r[0][1], r[1][0], r[1][1], true);
                        cont.actualiseVue();
                    }
                    if (mb == MouseButton.SECONDARY) {
                        if (ponts[i][j][0] != null && ponts[i][j][1] != null) {
                            cont.getContMod().getPlateau().detruirePont(r[0][0], r[0][1], r[1][0], r[1][1]);
                            cont.getContMod().getPlateau().construirePont(r[0][0], r[0][1], r[1][0], r[1][1]);
                            cont.actualiseVue();
                        } else {
                            cont.getContMod().getPlateau().detruirePont(r[0][0], r[0][1], r[1][0], r[1][1]);
                            cont.actualiseVue();
                        }
                    }
                });
            } else {
                this.setOpacity(1); 
                this.setStrokeWidth(0.25); 
                this.setEffect(new GaussianBlur(1.5));
            }
        }

        protected int[][] getCoordIlesRattachées() {
            int[][] r = new int[2][2];
            if ("h".equals(this.dir) || "h1".equals(this.dir) || "h2".equals(this.dir)) {
                int j1 = this.j;
                int j2 = this.j;
                while (cont.getContMod().getPlateau().getCase(i, j1).getNb() == -1) {
                    j1--;
                }
                while (cont.getContMod().getPlateau().getCase(i, j2).getNb() == -1) {
                    j2++;
                }
                r[0][0] = i;
                r[0][1] = j1;
                r[1][0] = i;
                r[1][1] = j2;
            } else if ("v".equals(this.dir) || "v1".equals(this.dir) || "v2".equals(this.dir)) {
                int i1 = this.i;
                int i2 = this.i;
                while (cont.getContMod().getPlateau().getCase(i1, j).getNb() == -1) {
                    i1--;
                }
                while (cont.getContMod().getPlateau().getCase(i2, j).getNb() == -1) {
                    i2++;
                }
                r[0][0] = i1;
                r[0][1] = j;
                r[1][0] = i2;
                r[1][1] = j;
            }
            return r;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    //******************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    
    protected class JokerGraphique extends StackPane {

        protected boolean utilisable = true;

        protected JokerGraphique(int typeJoker) {
            super();
            MenuButton c = boutonJoker(typeJoker);

            super.getChildren().add(c);

            this.setOnMouseClicked(MouseEvent -> {
                if (enPause) {
                    return;
                }

                boolean b = !cont.getContMod().isStory();

                if (utilisable) {
                    cont.actualiseVue();
                    switch (typeJoker) {
                        case 1:
                            if (cont.getContMod().joker1()) {
                                utilisable = false;
                                infos.getChildren().remove(this);
                                cont.actualiseVue();
                                cont.getContChrono().penalite(b);
                            }
                            break;
                        case 2:
                            cont.getContMod().joker2();
                            utilisable = false;
                            infos.getChildren().remove(this);
                            cont.actualiseVue();
                            cont.getContChrono().penalite(b);

                            break;
                        case 3:
                            if (cont.getContMod().joker3()) {
                                utilisable = false;
                                infos.getChildren().remove(this);
                                cont.getContChrono().penalite(b);
                                int x = cont.getContMod().getJoker3()[0];
                                int y = cont.getContMod().getJoker3()[1];
                                ////////////////////////////////////////////////////////////////////////////////////
                                //cont.getContMod().getPlateau().construireAvecSol(cont.getContMod().getSolution());
                                ////////////////////////////////////////////////////////////////////////////////////
                                cont.actualiseVue();
                            }
                            break;
                    }
                }
            });
        }

        protected final MenuButton boutonJoker(int k) {
            String name = "";
            switch (k) {
                case 1:
                    name = Parametre.ICON_J1;
                    break;
                case 2:
                    name = Parametre.ICON_J2;
                    break;
                case 3:
                    name = Parametre.ICON_J3;
                    break;
            }
            MenuButton c = new MenuButton("Joker " + k, "Joker " + k, Parametre.getLargeurFenetre() / 9, 30, true);
            c.addIcon(name, true);
            return c;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    //******************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    
    protected final class VBoxPause extends VBox {

        private final Rectangle boutonMenu;

        protected VBoxPause() {
            this.setTranslateX(deplacement);
            this.boutonMenu = dessineBoutonMenu();
            this.boutonMenu.setLayoutX(Parametre.getLargeurFenetre() / 20 - 40);
            this.setLayoutY(50);
            infos.getChildren().add(this.boutonMenu);
            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.25), this.boutonMenu);
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.25), this);
            TranslateTransition tt2 = new TranslateTransition(Duration.millis(250), this.boutonMenu);
            TranslateTransition tt3 = new TranslateTransition(Duration.millis(250), this);
            this.setSpacing(50.0);

            // BOUTON RETOUR A LA PARTIE
            MenuButton resume = new MenuButton("Retour à la partie", "Retour à la partie", Parametre.getLargeurFenetre() - 50, 100, true);
            resume.setOnMouseClicked(event -> {
                if (rotateTransition.getStatus() != Animation.Status.RUNNING) {

                    rotateTransition.setByAngle(180f);
                    rotateTransition.setCycleCount(1);
                    rotateTransition.setAutoReverse(true);
                    rotateTransition.play();
                    if (!enPause) {
                        if (plateauGraphique.getChildren().contains(this)) {
                            return;
                        }
                        plateauGraphique.getChildren().add(this);
                        translateTransition.setFromX(Parametre.POSITION_X_MENU_PAUSE_FERME);
                        translateTransition.setToX(Parametre.POSITION_X_MENU_PAUSE_OUVERT);
                        translateTransition.setCycleCount(1);
                        translateTransition.setAutoReverse(true);
                        translateTransition.play();
                        tt2.setFromX(Parametre.getLargeurFenetre() / 20 - 40);
                        tt2.setToX(Parametre.getLargeurFenetre() / 20 + Parametre.getLargeurFenetre() / 2.2 + 5);
                        tt2.setCycleCount(1);
                        tt2.setAutoReverse(true);
                        tt2.play();
                        enPause = true;
                    } else {
                        tt3.setFromX(Parametre.POSITION_X_MENU_PAUSE_OUVERT);
                        tt3.setToX(Parametre.POSITION_X_MENU_PAUSE_FERME);
                        tt3.setCycleCount(1);
                        tt3.setAutoReverse(true);
                        tt3.play();
                        tt2.setFromX(Parametre.getLargeurFenetre() / 20 + Parametre.getLargeurFenetre() / 2.2 + 5);
                        tt2.setToX(Parametre.getLargeurFenetre() / 20 - 40);
                        tt2.setCycleCount(1);
                        tt2.setAutoReverse(true);
                        tt2.play();
                        tt3.setOnFinished(evt -> {
                            plateauGraphique.getChildren().remove(this);
                        });
                        enPause = false;
                    }
                }
            });
            resume.addIcon(Parametre.ICON_BACK, true);
            this.getChildren().add(resume);

            MenuButton retry = new MenuButton("Recommencer le niveau", "Recommencer le niveau", Parametre.getLargeurFenetre() - 50, 100, true);

            retry.setOnMouseClicked(event -> {
                if (animationEnCours != null) {
                    animationEnCours.stop();
                    animationEnCours = null;
                }
                cont.getContChrono().stop();
                ModeleHashi md = cont.getContMod();
                md.reinitialiser();
                if (md.isStory()) {
                    VueHashi.primaryStage.setTitle("Cinématique");
                    VueHashi.primaryStage.setScene(new VueCinematique(Niveaux.getNiveaux().size() - md.getID() + 1, VueHashi.primaryStage, md).getScene());
                    nullify();
                } else {
                    ControleurHashi cont = new ControleurHashi(md, VueHashi.primaryStage);
                    VueHashi.primaryStage.setScene(cont.getContVue().getScene());
                    nullify();
                }
            });
            retry.addIcon(Parametre.ICON_RESTART, true);
            this.getChildren().add(retry);

            // BOUTON TUTORIEL
            MenuButton btnParam = new MenuButton("Paramètres", "Paramètres", Parametre.getLargeurFenetre() - 50, 100, true);
            btnParam.setOnMouseClicked(event -> {
                enParametre = true;
                if (plateauGraphique.getChildren().contains(menuParametre)) {
                    return;
                }
                plateauGraphique.getChildren().add(menuParametre);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), this);
                tt.setToX(this.getTranslateX() - deplacement);
                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menuParametre);
                tt1.setToX(this.getTranslateX());
                tt.play();
                tt1.play();
                tt.setOnFinished(evt -> {
                    plateauGraphique.getChildren().remove(this);
                });
            });
            btnParam.addIcon(Parametre.ICON_SETTINGS, true);
            this.getChildren().add(btnParam);

            // BOUTON QUITTER
            MenuButton btnQuitter = new MenuButton("Retour au menu", "Retour au menu", Parametre.getLargeurFenetre() - 50, 100, true);
            btnQuitter.setOnMouseClicked(event -> {
                cont.getContMod().reinitialiser();
                cont.getContChrono().stop();
                if (animationEnCours != null) {
                    animationEnCours.stop();
                }
                VueHashi.primaryStage.setScene(new VueMenuPrincipal(cont.getPrimaryStage()).getScene());
                nullify();
            });
            btnQuitter.addIcon(Parametre.ICON_MAIN, true);
            this.getChildren().add(btnQuitter);

            // BOUTON MENU PAUSE
            this.boutonMenu.setOnMouseClicked(event -> {
                if (rotateTransition.getStatus() != Animation.Status.RUNNING) {
                    rotateTransition.setByAngle(180f);
                    rotateTransition.setCycleCount(1);
                    rotateTransition.setAutoReverse(true);
                    rotateTransition.play();
                    if (!enPause) {
                        if (plateauGraphique.getChildren().contains(this)) {
                            return;
                        }
                        plateauGraphique.getChildren().add(this);
                        translateTransition.setFromX(Parametre.POSITION_X_MENU_PAUSE_FERME);
                        translateTransition.setToX(Parametre.POSITION_X_MENU_PAUSE_OUVERT);
                        translateTransition.setCycleCount(1);
                        translateTransition.setAutoReverse(true);
                        translateTransition.play();
                        tt2.setFromX(Parametre.getLargeurFenetre() / 20 - 40);
                        tt2.setToX(Parametre.getLargeurFenetre() / 20 + Parametre.getLargeurFenetre() / 2.2 + 5);
                        tt2.setCycleCount(1);
                        tt2.setAutoReverse(true);
                        tt2.play();
                        enPause = true;
                    } else {
                        tt3.setFromX(Parametre.POSITION_X_MENU_PAUSE_OUVERT);
                        tt3.setToX(Parametre.POSITION_X_MENU_PAUSE_FERME);
                        tt3.setCycleCount(1);
                        tt3.setAutoReverse(true);
                        tt3.play();
                        tt2.setFromX(Parametre.getLargeurFenetre() / 20 + Parametre.getLargeurFenetre() / 2.2 + 5);
                        tt2.setToX(Parametre.getLargeurFenetre() / 20 - 40);
                        tt2.setCycleCount(1);
                        tt2.setAutoReverse(true);
                        tt2.play();
                        if (!enParametre) {
                            tt3.setOnFinished(evt -> {
                                plateauGraphique.getChildren().remove(this);
                            });
                        } else {

                            TranslateTransition tt4 = new TranslateTransition(Duration.seconds(0.5), menuParametre);
                            tt4.setToX(plateauGraphique.getTranslateX() - deplacement);

                            tt4.play();

                            tt3.setOnFinished(evt2 -> {
                                plateauGraphique.getChildren().remove(menuParametre);
                                enParametre = false;
                            });

                        }
                        enPause = false;
                    }
                }
            });
        }

        protected Rectangle dessineBoutonMenu() {
            Rectangle r = new Rectangle(Parametre.getLargeurFenetre() / 20, 30.0);
            r.setLayoutY(10.0);
            Image im = new Image(Parametre.FILE_IMAGEMENUA);
            r.setFill(new ImagePattern(im));
            return r;
        }

    }

    //////////////////////////////////////////////////////////////////////////////////
    //******************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    
    protected class VBoxParametre extends VBox {

        protected VBoxParametre() {
            this.setTranslateX(deplacement);
            this.setLayoutY(50);
            TranslateTransition tt3 = new TranslateTransition(Duration.millis(250), this);
            this.setSpacing(50.0);

            // BOUTON RETOUR A LA PARTIE
            MenuButton retour = new MenuButton("Retour", "Retour", Parametre.getLargeurFenetre() - 50, 100, true);
            retour.setOnMouseClicked(event -> {
                if (plateauGraphique.getChildren().contains(menuPause)) {
                    return;
                }
                plateauGraphique.getChildren().add(menuPause);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), this);
                tt.setToX(this.getTranslateX() - deplacement);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menuPause);
                tt1.setToX(this.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    plateauGraphique.getChildren().remove(this);
                    enParametre = false;
                });
            });
            retour.addIcon(Parametre.ICON_BACK, true);
            this.getChildren().add(retour);

            // BOUTON SON
            String s;
            if (Parametre.isAudioMute()) {
                s = "Activer le son";
            } else {
                s = "Désactiver le son";
            }
            MenuButton btnSon = new MenuButton(s, s, Parametre.getLargeurFenetre() - 50, 100, true);

            btnSon.setOnMouseClicked(event -> {
                Vue.switchMute();
                if (Parametre.isAudioMute()) {
                    btnSon.setText("Activer le son", true);
                    btnSon.removeIcon();
                    btnSon.addIcon(Parametre.ICON_MUTE, true);
                } else {
                    btnSon.setText("Désactiver le son", true);
                    btnSon.removeIcon();
                    btnSon.addIcon(Parametre.ICON_SON, true);
                }
            });
            if (Parametre.isAudioMute()) {
                btnSon.addIcon(Parametre.ICON_MUTE, true);
            } else {
                btnSon.addIcon(Parametre.ICON_SON, true);
            }

            this.getChildren().add(btnSon);

            // BOUTON ANIM
            String s2;
            if (!Parametre.isAnime()) {
                s2 = "Activer l'animation";
            } else {
                s2 = "Désactiver l'animation";
            }

            MenuButton btnAnim = new MenuButton(s2, s2, Parametre.getLargeurFenetre() - 50, 100, true);
            btnAnim.setOnMouseClicked(event -> {
                switchAnimation();
                if (!Parametre.isAnime()) {
                    btnAnim.setText("Activer l'animation", true);
                    btnAnim.removeIcon();
                    btnAnim.addIcon(Parametre.ICON_ANIM2, true);

                } else {
                    btnAnim.setText("Désactiver l'animation", true);
                    btnAnim.removeIcon();
                    btnAnim.addIcon(Parametre.ICON_ANIM, true);
                }
            });
            if (!Parametre.isAnime()) {
                btnAnim.addIcon(Parametre.ICON_ANIM2, true);
            } else {
                btnAnim.addIcon(Parametre.ICON_ANIM, true);
            }
            this.getChildren().add(btnAnim);

            // BOUTON GAMEPLAY
            String s3;
            if (Parametre.isDoubleClic()) {
                s3 = "Activer le survol";
            } else {
                s3 = "Désactiver le survol";
            }

            MenuButton btnClic = new MenuButton(s3, s3, Parametre.getLargeurFenetre() - 50, 100, true);
            btnClic.setOnMouseClicked(event -> {
                switchGameplay();
                if (Parametre.isDoubleClic()) {
                    btnClic.setText("Activer le survol", true);
                    btnClic.removeIcon();
                    btnClic.addIcon(Parametre.ICON_SURVOL, true);
                } else {
                    btnClic.setText("Désactiver le survol", true);
                    btnClic.removeIcon();
                    btnClic.addIcon(Parametre.ICON_SURVOL2, true);
                }
            });
            if (Parametre.isDoubleClic()) {
                btnClic.addIcon(Parametre.ICON_SURVOL, true);
            } else {
                btnClic.addIcon(Parametre.ICON_SURVOL2, true);
            }
            this.getChildren().add(btnClic);
        }
    }

}
