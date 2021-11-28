/*
*
* Nom de la classe : VueHashiScenario
* 
* Description : 
*   Interface graphique des chapitre du scénario
*
* Version : 1.0
*
* Date : Mai 2019
*
* Auteur : PI4 / HASHIWOKAKERO1
*
 */
package javamvc.vues;

import javamvc.modele.Case;
import javamvc.modele.Dialogue;
import javamvc.controleur.ControleurHashi;
import javamvc.modele.Parametre;

import java.util.Random;
import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class VueHashiScenario extends VueHashiScriptee {

    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    /**
     * Coordonnées X de l'ile où se pose le Baio.
     */
    private int heliportX;

    /**
     * Coordonnées X de l'ile où se pose le Baio.
     */
    private int heliportY;

    /**
     * Animation de l'inondation à l'écran en cas d'échec.
     */
    protected final ImageView ivf;

    /**
     * Archipel avant l'arriver du baio jusqu'au plateau.
     */
    private ImageView ivCinematique;

    /**
     * Vagues avant l'arriver du baio jusqu'au plateau.
     */
    private ImageView ivVagueCinematique;

    /**
     * Baio.
     */
    protected ObjetMobile baio;

    /**
     * Population.
     */
    private final ArrayList<Habitant> habitants = new ArrayList<>();

    /**
     * Coordonnée maximum du plateau (utiles pour restreindre les animations
     * aléatoire).
     */
    private final int max;

    // CONSTRUCTEURS ***************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    /**
     * Constructeur de VueHashiScenario (chapitres du mode histoire)
     *
     * @param c Controleur
     */
    public VueHashiScenario(ControleurHashi c) {
        super(c);
        this.ivVagueCinematique = new ImageView(im);
        ivVagueCinematique.setFitHeight(Parametre.getHauteurFenetre());
        ivVagueCinematique.setFitWidth(Parametre.getLargeurFenetre());
        this.ivVagueCinematique.setRotate(180);
        if (Parametre.isAnime()) {
            VueHashi.root.getChildren().add(1, this.ivVagueCinematique);
        }
        this.ivf = new ImageView(im);
        Image fond = loadImage(Parametre.FILE_WALLPAPER);
        ivCinematique = new ImageView(fond);
        ivCinematique.setFitWidth(Parametre.getLargeurFenetre());
        ivCinematique.setFitHeight(Parametre.getHauteurFenetre());
        ivCinematique.setOpacity(1);
        VueHashi.root.getChildren().add(this.ivCinematique);
        this.max = cont.getContMod().getPlateau().length() - 1;
    }

    // METHODES ********************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    @Override
    public void initDessin(int i, int j) {
        Case il = cont.getContMod().getPlateau().getCase(i, j);

        if (il != null && il.getNb() != -1) {
            IleGraphique sp = new IleGraphique(i, j);
            ilesGraphique[i][j] = sp;
            sp.setLayoutX(j * (largeurCase) + decalageX);
            sp.setLayoutY(i * (hauteurCase) + decalageY);
            if (this.plateauGraphique.getChildren().size() >= 0) {
                this.plateauGraphique.getChildren().add(0, sp);
            } else {
                this.plateauGraphique.getChildren().add(sp);
            }
            heliportX = (int) (j * (largeurCase) + decalageX);
            heliportY = (int) (i * (hauteurCase) + decalageY);
            for (int k = 0; k < il.getNb(); k++) {
                Habitant h = new Habitant(i, j);
                h.setTranslateX((int) (j * (largeurCase) + decalageX));
                h.setTranslateY((int) (i * (hauteurCase) + decalageY));
                h.setOpacity(0);
                habitants.add(h);
                this.getPlateauGraphique().getChildren().add(h);
            }
        }
    }

    @Override
    public void dessineCase(int i, int j) {
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

    /**
     * Initialise le Baio (à l'exterieur gauche du plateau)
     */
    public void initBaio() {
        baio = new ObjetMobile();
        baio.setTranslateX(-50);
        baio.setTranslateY(max * hauteurCase);
        RotateTransition rt = new RotateTransition(Duration.millis(1), baio);
        rt.setToAngle(180);
        rt.play();
        this.plateauGraphique.getChildren().add(baio);
    }

    /**
     * Mouvement rectiligne du baio vers la coordonnée donnée en paramètre.
     *
     * @param x Coordonnée.
     */
    public void moveBaioRegulier(int x) {
        if (!root.getChildren().contains(ivCinematique)) {
            root.getChildren().add(root.getChildren().size() - 2, ivCinematique);
        }
        final int k = x;
        if (baio == null) {
            return;
        }
        Random r = new Random();
        int y = max * hauteurCase;
        int x0 = (int) baio.getTranslateX();
        int y0 = (int) baio.getTranslateY();
        int angle = 0;
        if (x == x0) {
            x++;
        }
        int alpha = calculAngle(Math.abs(y - y0), Math.abs(x - x0));
        if (x0 <= x && y0 <= y) {
            angle = 180 - alpha;
        } else if (x0 <= x && y0 > y) {
            angle = -180 + alpha;
        } else if (x0 > x && y0 <= y) {
            angle = alpha;
        } else if (x0 > x && y0 > y) {
            angle = -alpha;
        }
        VueHashiScenario.animationEnCours = new TranslateTransition(Duration.millis(1000), baio);
        animationEnCours.setToX(x);
        animationEnCours.setToY(y);
        RotateTransition rt = new RotateTransition(Duration.millis(1), baio);
        rt.setToAngle(-angle);
        rt.play();
        animationEnCours.play();
        animationEnCours.setOnFinished(evt -> {
            if (!this.cont.getContChrono().isStop()) {
                return;
            }
            if (this.cont.getContMod().estFini()) {
                this.poseBaio();
            } else if (this.cont.getContMod().isPerdu()) {
                this.initDialogueFin(false);
            } else if (k + 10 < Parametre.getLargeurFenetre() - 10) {
                this.moveBaioRegulier(k + 10);
            }
        });
    }

    /**
     * Déplace le Baio à l'exterieur droit de l'écran et initialise la vue du
     * plateau.
     */
    public void quitterBaio() {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(1), baio);
        int x = Parametre.getLargeurFenetre() + 10;
        int y = (int) baio.getTranslateY();
        int x0 = (int) baio.getTranslateX();
        int y0 = (int) baio.getTranslateY();
        tt.setToX(x);
        tt.setToY(y);
        int angle = 0;
        if (x == x0) {
            x++;
        }
        int alpha = calculAngle(Math.abs(y - y0), Math.abs(x - x0));
        if (x0 <= x && y0 <= y) {
            angle = 180 - alpha;
        } else if (x0 <= x && y0 > y) {
            angle = -180 + alpha;
        } else if (x0 > x && y0 <= y) {
            angle = alpha;
        } else if (x0 > x && y0 > y) {
            angle = -alpha;
        }
        RotateTransition rt = new RotateTransition(Duration.seconds(0.5), baio);
        rt.setToAngle(-angle);
        rt.play();
        tt.play();
        VueHashiScenario.animationEnCours.stop();
        tt.setOnFinished(evt -> {
            VueHashiScenario.animationEnCours = null;
            this.plateauGraphique.getChildren().remove(baio);
            this.baio = null;
            cont.initVue();
            if (root.getChildren().contains(ivCinematique)) {
                root.getChildren().remove(ivCinematique);

            }
            if (root.getChildren().contains(this.ivVagueCinematique)) {
                root.getChildren().remove(ivVagueCinematique);
            }
            ivCinematique = null;
            ivVagueCinematique = null;
            enPause = true;
            this.initBaio();
            this.volStationnaireBaio(true);
        });
    }

    /**
     * Déplace le baio par dessus l'ile.
     *
     * @param first true si c'est le premier déplacement (implique une variante
     * dans le mouvement)
     */
    public void volStationnaireBaio(boolean first) {
        if (baio == null) {
            return;
        }
        Random r = new Random();
        int x = heliportX - 10 + r.nextInt(50);
        int y = heliportY - 10 + r.nextInt(50);
        if (first) {
            x = heliportX;
            y = heliportY;
        }
        int x0 = (int) baio.getTranslateX();
        int y0 = (int) baio.getTranslateY();
        int angle = 0;
        if (x == x0) {
            x++;
        }
        int alpha = calculAngle(Math.abs(y - y0), Math.abs(x - x0));
        if (x0 <= x && y0 <= y) {
            angle = 180 - alpha;
        } else if (x0 <= x && y0 > y) {
            angle = -180 + alpha;
        } else if (x0 > x && y0 <= y) {
            angle = alpha;
        } else if (x0 > x && y0 > y) {
            angle = -alpha;
        }
        double duree = 3, duree2 = 1;
        if (first) {
            duree = 1;
            duree2 = 0.0001;
        }
        VueHashiScenario.animationEnCours = new TranslateTransition(Duration.seconds(duree), baio);
        animationEnCours.setToX(x);
        animationEnCours.setToY(y);
        RotateTransition rt = new RotateTransition(Duration.seconds(duree2), baio);
        rt.setToAngle(-angle);
        rt.play();
        animationEnCours.play();
        animationEnCours.setOnFinished(evt -> {
            if (cont.getContChrono().isStop() && enPause && !this.cont.getContMod().isPerdu()) { // ce cas n'arrive qu'au debut du chapitre         
                cont.getContChrono().start();
                enPause = false;
                this.initMenuPause();
            }
            if (this.cont.getContMod().estFini()) {
                enPause = true;
                this.sauverPopulation();
            } else if (this.cont.getContMod().isPerdu()) {
                this.initDialogueFin(false);
            } else {
                this.volStationnaireBaio(false);
            }
        });
    }

    /**
     * Pose le Baio.
     */
    public void poseBaio() {
        int x = (int) (heliportX);
        int y = (int) (heliportY);
        TranslateTransition tt = new TranslateTransition(Duration.seconds(2), baio);
        tt.setToX(x);
        tt.setToY(y);
        int x0 = (int) baio.getTranslateX();
        int y0 = (int) baio.getTranslateY();
        int angle = 0;
        if (x == x0) {
            x++;
        }
        int alpha = calculAngle(Math.abs(y - y0), Math.abs(x - x0));
        if (x0 <= x && y0 <= y) {
            angle = 180 - alpha;
        } else if (x0 <= x && y0 > y) {
            angle = -180 + alpha;
        } else if (x0 > x && y0 <= y) {
            angle = alpha;
        } else if (x0 > x && y0 > y) {
            angle = -alpha;
        }
        RotateTransition rt = new RotateTransition(Duration.seconds(1.5), baio);
        rt.setToAngle(-angle);
        ScaleTransition st = new ScaleTransition(Duration.millis(5000), baio);
        st.setToY(0.5);
        st.setToX(0.5);
        st.setAutoReverse(true);
        rt.play();
        tt.play();
        tt.setOnFinished(evt -> {
            st.play();
        });
        st.setOnFinished(evt -> {
            faireMonterHabitant(0);
        });
    }

    /**
     * Initialise le dialogue de fin.
     *
     * @param estFini true si reussite (le dialogue varie selon l'échec ou la
     * reussite).
     */
    public void initDialogueFin(boolean estFini) {
        if (estFini && this.cont.getContMod().getChapitre()==4){
            Dialogue.initDialogue(16, estFini);
        }
        else {
            Dialogue.initDialogue(5, estFini);
        }
        while (Dialogue.getDialogue().hasPrevious()) {
            Dialogue.getDialogue().previous();
        }
        DialogueHashi dh = new DialogueHashi();
        dh.setTranslateY(0);
        root.getChildren().add(dh);
        dh.actualiseDialogue();
    }

    /**
     * Initialise le dialogue de départ (après la narration)
     *
     * @param chapitre Chapitre du scénario.
     */
    public void initDialogueHashi(int chapitre) {
        Dialogue.initDialogue(chapitre, false);
        while (Dialogue.getDialogue().hasPrevious()) {
            Dialogue.getDialogue().previous();
        }
        DialogueHashi dh = new DialogueHashi();
        dh.setTranslateY(0);
        root.getChildren().add(dh);
        dh.actualiseDialogue();
    }

    /**
     * Initialise l'animation de l'inondation.
     */
    public void inonder() {
        ivf.setFitHeight(Parametre.getHauteurFenetre() * 2);
        ivf.setFitWidth(Parametre.getLargeurFenetre() * 5);
        ivf.setTranslateX(-Parametre.getHauteurFenetre() * 2);
        ivf.setTranslateY(-Parametre.getLargeurFenetre() / 2);
        ivf.setOpacity(0);
        VueHashiScenario.root.getChildren().add(ivf);

        int x = (int) (Parametre.getLargeurFenetre() / 2 - baio.getWidth());
        int y = -200;
        int x0 = (int) baio.getTranslateX();
        int y0 = (int) baio.getTranslateY();
        int angle = 0;
        if (x == x0) {
            x++;
        }
        int alpha = calculAngle(Math.abs(y - y0), Math.abs(x - x0));
        if (x0 <= x && y0 <= y) {
            angle = 180 - alpha;
        } else if (x0 <= x && y0 > y) {
            angle = -180 + alpha;
        } else if (x0 > x && y0 <= y) {
            angle = alpha;
        } else if (x0 > x && y0 > y) {
            angle = -alpha;
        }
        TranslateTransition tt = new TranslateTransition(Duration.seconds(1), baio);
        tt.setToX(x);
        tt.setToY(y);
        RotateTransition rt = new RotateTransition(Duration.seconds(1), baio);
        rt.setToAngle(-angle);

        FadeTransition ft = new FadeTransition(Duration.millis(1500), ivf);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setAutoReverse(true);

        TranslateTransition ttI = new TranslateTransition(Duration.millis(7000), ivf);
        ttI.setToX(-Parametre.getLargeurFenetre() / 2);

        FadeTransition ft2 = new FadeTransition(Duration.millis(5000), iv);
        ft2.setFromValue(0);
        ft2.setToValue(1);
        ft2.setAutoReverse(true);

        rt.setOnFinished(evt -> {
            tt.play();
            ttI.play();

        });

        tt.setOnFinished(evt -> {
            this.plateauGraphique.getChildren().remove(baio);
            ft.play();

        });

        ft.setOnFinished(evt -> {
            VueHashiScenario.root.getChildren().remove(iv);
            iv.setOpacity(0);
            VueHashiScenario.root.getChildren().add(iv);
            ft2.play();
        });

        ft2.setOnFinished(evt -> {
            VueHashiScenario.root.getChildren().remove(ivf);
            this.cont.termine();
        });

        rt.play();
    }

    /**
     * Animation de la population pendant la partie.
     */
    public void movePopulation() {
        for (Habitant h : habitants) {
            h.moveHabitant();
        }
    }

    /**
     * Animation de la population qui rejoint l'ile en fin de partie.
     */
    private void sauverPopulation() {
        sauverHabitant(0);
    }

    /**
     * Animation d'un habitant qui rejoint l'ile en fin de partie.
     */
    private void sauverHabitant(int n) {
        double duree = 0.1;
        if (n == habitants.size()) {
            poseBaio();
            return;
        }
        FadeTransition ft = new FadeTransition(Duration.millis(duree), habitants.get(n));
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(evt -> {
            habitants.get(n).tt.stop();
            habitants.get(n).tt0.stop();
            poserHabitant(n);
        });
        ft.play();
    }

    /**
     * Animation de la population qui rejoint la zone d'aterissage du Baio.
     */
    private void poserHabitant(int n) {
        double duree = 0.1;
        if (n == habitants.size()) {
            return;
        }
        TranslateTransition tt = new TranslateTransition(Duration.millis(duree), habitants.get(n));
        tt.setToX(baio.getTranslateX() + habitants.get(n).r.nextInt(20));
        tt.setToY(baio.getTranslateY() + habitants.get(n).r.nextInt(20));
        tt.setToX(heliportX + rayon - rayon / 1.5 + habitants.get(n).r.nextInt((int) (rayon / 0.75)));
        tt.setToY(heliportY + rayon - rayon / 1.5 + habitants.get(n).r.nextInt((int) (rayon / 0.75)));
        FadeTransition ft = new FadeTransition(Duration.millis(1), habitants.get(n));
        ft.setFromValue(0);
        ft.setToValue(1);
        tt.setOnFinished(evt -> {
            ft.play();
        });
        ft.setOnFinished(evt -> {
            sauverHabitant(n + 1);
            TranslateTransition tt2 = new TranslateTransition(Duration.millis(500), habitants.get(n));
            tt2.setToX(heliportX + rayon / 2 - rayon / 8 + habitants.get(n).r.nextInt((int) (rayon / 4)));
            tt2.setToY(heliportY + rayon / 2 - rayon / 8 + habitants.get(n).r.nextInt((int) (rayon / 4)));
            tt2.play();
        });
        tt.play();
    }

    /**
     * Animation de la population qui rejoint le Baio.
     */
    private void faireMonterHabitant(int n) {
        if (n == habitants.size()) {
            this.initDialogueFin(true);
            return;
        }
        FadeTransition ft = new FadeTransition(Duration.millis(1), habitants.get(n));
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(evt -> {
            faireMonterHabitant(n + 1);
        });
        ft.play();
    }

    //////////////////////////////////////////////////////////////////////////////////
    // *****************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    private class Habitant extends StackPane {

        private final Circle circle;
        private int i, j;
        private final Random r;
        private TranslateTransition tt, tt0;

        private Habitant(int i, int j) {
            this.i = i;
            this.j = j;
            this.r = new Random();
            this.circle = new Circle(1.5, Color.BLACK);
            this.getChildren().add(circle);
        }

        private void moveHabitant() {
            double duree = 0.07;
            int[][] tv = cont.getContMod().getPlateau().voisins3(i, j);
            int k = 0;
            for (int[] tv1 : tv) {
                if (tv1[0] != -2) {
                    k++;
                }
            }
            if (k == 0) {
                return;
            }
            int dv = r.nextInt(k);
            int i2 = tv[dv][0];
            int j2 = tv[dv][1];
            int r2 = 0;
            if (this.tt != null) {
                this.tt.stop();
            }
            if (this.tt0 != null) {
                this.tt0.stop();
            }
            if (j2 > j) {
                if (cont.getContMod().getPlateau().sontDoublementLiees(i, j, i2, j2)) {
                    r2 = 1;
                }
                tt0 = new TranslateTransition(Duration.seconds(duree), this);
                tt0.setToX(ponts[i][j + 1][r2].getStartX());
                tt0.setToY(ponts[i][j + 1][r2].getStartY() - this.circle.getRadius());
                tt = new TranslateTransition(Duration.seconds(duree), this);
                tt.setToX(ponts[i2][j2 - 1][r2].getEndX());
                tt.setToY(ponts[i2][j2 - 1][r2].getEndY() - this.circle.getRadius());
                tt0.setOnFinished(evt -> {
                    this.setOpacity(1);
                    tt.play();
                });
                tt.setOnFinished(evt -> {
                    this.setOpacity(0);
                    this.i = i2;
                    this.j = j2;
                    moveHabitant();
                });
                tt0.play();
            } else if (j > j2) {
                tt0 = new TranslateTransition(Duration.seconds(duree), this);
                tt0.setToX(ponts[i][j - 1][r2].getEndX());
                tt0.setToY(ponts[i][j - 1][r2].getEndY() - this.circle.getRadius());
                tt = new TranslateTransition(Duration.seconds(duree), this);
                tt.setToX(ponts[i2][j2 + 1][r2].getStartX());
                tt.setToY(ponts[i2][j2 + 1][r2].getStartY() - this.circle.getRadius());
                tt0.setOnFinished(evt -> {

                    this.setOpacity(1);
                    tt.play();
                });
                tt.setOnFinished(evt -> {
                    this.setOpacity(0);
                    this.i = i2;
                    this.j = j2;
                    moveHabitant();
                });
                tt0.play();
            } else if (i < i2) {
                if (cont.getContMod().getPlateau().sontDoublementLiees(i, j, i2, j2)) {
                    r2 = 1;
                }
                tt0 = new TranslateTransition(Duration.seconds(duree), this);
                tt0.setToX(ponts[i + 1][j][r2].getStartX() - this.circle.getRadius());
                tt0.setToY(ponts[i + 1][j][r2].getStartY());
                tt = new TranslateTransition(Duration.seconds(duree), this);
                tt.setToX(ponts[i2 - 1][j2][r2].getEndX() - this.circle.getRadius());
                tt.setToY(ponts[i2 - 1][j2][r2].getEndY());
                tt.setOnFinished(evt -> {
                    this.setOpacity(0);
                    this.i = i2;
                    this.j = j2;
                    moveHabitant();
                });
                tt0.setOnFinished(evt -> {
                    this.setOpacity(1);
                    tt.play();
                });
                tt0.play();
            } else if (i > i2) {
                tt0 = new TranslateTransition(Duration.seconds(duree), this);
                tt0.setToX(ponts[i - 1][j][r2].getEndX() - this.circle.getRadius());
                tt0.setToY(ponts[i - 1][j][r2].getEndY());
                tt = new TranslateTransition(Duration.seconds(duree), this);
                tt.setToX(ponts[i2 + 1][j2][r2].getStartX() - this.circle.getRadius());
                tt.setToY(ponts[i2 + 1][j2][r2].getStartY());

                tt0.setOnFinished(evt -> {
                    this.setOpacity(1);
                    tt.play();
                });
                tt.setOnFinished(evt -> {
                    this.setOpacity(0);
                    this.i = i2;
                    this.j = j2;
                    moveHabitant();
                });
                tt0.play();
            }
        }
    }
    
    /**
    * Méthode qui met à null les pointeurs
    */
    @Override
    protected void nullify(){
        while (habitants.size()>0){
            if (habitants.get(0).tt != null) habitants.get(0).tt.stop();
            if (habitants.get(0).tt0 != null) habitants.get(0).tt0.stop();
            habitants.get(0).tt = null;
            habitants.get(0).tt0 = null;
            habitants.remove(0);
        }
        this.baio = null;
        this.ivCinematique = null;
        this.ivVagueCinematique = null;                
        super.nullify();
    }
}
