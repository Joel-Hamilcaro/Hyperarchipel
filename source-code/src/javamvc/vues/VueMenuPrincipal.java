/*
*
* Nom de la classe : VueMenuFin
* 
* Description : 
*   Interface graphique du menu principal
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
import java.util.Random;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class VueMenuPrincipal extends Vue {

    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    private static MenuPrincipal menuPrincipal;
    private static final Niveaux NIVEAUX = new Niveaux();
    private ImageView helico;
    private ImageView iv;
    private TranslateTransition tt;
    
    

    /**
     * Constructeur du menu principal
     * @param primaryStage Fenetre
     */
    public VueMenuPrincipal(Stage primaryStage) {
        
        // VUE 
        super(primaryStage, "Menu Principal");
        
        
        
        this.music(4);                    


        // IMAGE DE FOND
        this.iv = new ImageView(loadImage(Parametre.FILE_WAVE));
        iv.setFitHeight(Parametre.getHauteurFenetre());
        iv.setFitWidth(Parametre.getLargeurFenetre());
        if (Parametre.isAnime()) {
            VueMenuPrincipal.root.getChildren().add(0,iv);
        }
        this.addImageView(Parametre.FILE_WALLPAPER);

        // BAIO
        Image fond3 = new Image(getClass().getResource(Parametre.FILE_BAIO).toString());
        helico = new ImageView(fond3);
        helico.setOpacity(1);
        RotateTransition rt = new RotateTransition(Duration.millis(0.1), helico);
        rt.setToAngle(-180);
        rt.play();
        helico.setFitHeight(Parametre.getHauteurFenetre() / 4);
        helico.setFitWidth(Parametre.getHauteurFenetre() / 4 / 1.5);

        helico.setTranslateX(-1000);
        helico.setTranslateY(Parametre.getHauteurFenetre() / 2 - Parametre.getHauteurFenetre() / 8);
        root.getChildren().add(helico);

        baladerBaio(true);

        // MENU PRINCIPAL
        VueMenuPrincipal.menuPrincipal = new MenuPrincipal();
        VueMenuPrincipal.menuPrincipal.setVisible(true);
        root.getChildren().addAll(menuPrincipal);

        // NIVEAUX ********************************************************************* 
        //******************************************************************************
        
        for (int i = 0; i < Niveaux.getNiveaux().size() - 4; i++) {
            final int k = i;
            ModeleHashi md = Niveaux.getNiveaux().get(k);
            String s = "NIVEAU " + (k);
            String s2 = "Meilleur temps : " + md.scoreToString();
            if (i == 0) {
                s = "TUTORIEL";
                s2 = "TUTORIEL";
            }

            MenuButton btnNiveau = new MenuButton(s, s2);
            btnNiveau.setOnMouseClicked((MouseEvent event) -> {
                ControleurHashi cont = new ControleurHashi(md, VueMenuPrincipal.primaryStage);
                if (k > 0) {
                    VueMenuPrincipal.primaryStage.setTitle("Niveau " + (k));
                } else {
                    VueMenuPrincipal.primaryStage.setTitle("Tutoriel");
                }
                //this.tt.stop();
                //this.tt = null;
                //this.helico = null;

                VueMenuPrincipal.primaryStage.setScene(cont.getContVue().getScene());
                nullify();
            });
            if (k == 0) {
                btnNiveau.addIcon(Parametre.ICON_TUTO, true);
            } else if (md.getScore() > 0) {
                btnNiveau.addIcon(Parametre.ICON_OUI, true);
            } else {
                btnNiveau.addIcon(Parametre.ICON_NON, true);
            }

            if (i < 6) {
                menuPrincipal.menuNiveaux.getChildren().addAll(btnNiveau);
            } else if (i < 11) {
                menuPrincipal.menuNiveaux2.getChildren().addAll(btnNiveau);
            } else if (i < 17) {
                menuPrincipal.menuNiveaux3.getChildren().addAll(btnNiveau);
            } else {
                menuPrincipal.menuNiveaux4.getChildren().addAll(btnNiveau);
            }
        }

        menuPrincipal.menuNiveaux.getChildren().addAll(menuPrincipal.btnSuivant);
        menuPrincipal.menuNiveaux2.getChildren().addAll(menuPrincipal.btnSuivant2);

        // CHAPITRES DU SCENARIO ******************************************************* 
        //******************************************************************************
        
        for (int i = Niveaux.getNiveaux().size() - 1; i > Niveaux.getNiveaux().size() - 5; i--) {
            final int k = i;
            ModeleHashi md = Niveaux.getNiveaux().get(k);
            String s = "CHAPITRE " + (Niveaux.getNiveaux().size() - k);
            if (k == Niveaux.getNiveaux().size()-4){
                s = "CHAPITRE FINAL";
            }
            MenuButton btnNiveau = new MenuButton(s, "Meilleur temps : " + md.scoreToString());
            btnNiveau.setOnMouseClicked((MouseEvent event) -> {

                //this.tt.stop();
                //this.tt = null;
                //this.helico = null;
                VueMenuPrincipal.primaryStage.setTitle("Cinématique");
                VueMenuPrincipal.primaryStage.setScene(new VueCinematique(Niveaux.getNiveaux().size() - k, VueMenuPrincipal.primaryStage, md).getScene());
                nullify();
            });
            if (md.getScore() > 0) {
                btnNiveau.addIcon(Parametre.ICON_OUI, true);
            } else {
                btnNiveau.addIcon(Parametre.ICON_NON, true);
            }

            menuPrincipal.menuChapitres.getChildren().addAll(btnNiveau);

            if (md.getScore() <= -1) {
                break;
            }
        }

        // MENU ALÉATOIRE ************************************************************** 
        //******************************************************************************
        
        for (int i = 5; i < 10; i += 1) {
            final int k = i;
            MenuButton btnNiveau = new MenuButton("ALÉATOIRE " + k + "x" + k);

            btnNiveau.setOnMouseClicked((MouseEvent event) -> {
                //this.tt.stop();
                //this.tt = null;
                //this.helico = null;
                nullify();
                Runnable r;
                r = () -> {
                    VueChargement vc = new VueChargement(VueMenuPrincipal.primaryStage, k);
                    Runnable r2 = () -> {
                        vc.chargement();
                    };
                    Platform.runLater(r2);
                };
                Platform.runLater(r);

            });
            btnNiveau.addIcon(Parametre.ICON_DICE, true);
            menuPrincipal.menuAleatoire.getChildren().addAll(btnNiveau);
        }
        
        // SCENE
        VueMenuPrincipal.scene = new Scene(root,Color.rgb(11,38,59));
        this.beginScene();
    }
    
    /**
     * Balade le Baio dans le menu.
     * @param first true si c'est le premier déplacement.
     * (implique une variante dans le déplacement).
     */
    public void baladerBaio(boolean first) {

        Random r = new Random();
        int x = -500 + r.nextInt(Parametre.getLargeurFenetre() + 500);
        int y = -500 + r.nextInt(Parametre.getHauteurFenetre() + 500);

        int x0 = (int) helico.getTranslateX();
        int y0 = (int) helico.getTranslateY();

        if (first) {
            x = Parametre.getLargeurFenetre() + 1000;
            y = (int) helico.getTranslateY();
        }
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
        this.tt = new TranslateTransition(Duration.seconds(duree), helico);
        tt.setToX(x);
        tt.setToY(y);

        RotateTransition rt = new RotateTransition(Duration.seconds(duree2), helico);
        rt.setToAngle(-angle);
        rt.play();
        tt.play();
        tt.setOnFinished(evt -> {

            this.baladerBaio(false);

        });
    }
    
    //////////////////////////////////////////////////////////////////////////////////
    // *****************************************************************************//
    //******************************************************************************//
    //*************************** CLASSE INTERNE ***********************************//
    //******************************************************************************//
    //******************************************************************************//
    //////////////////////////////////////////////////////////////////////////////////
    
    private class MenuPrincipal extends Parent {

        // ATTRIBUTS 
        VBox menuParent = new VBox(30);
        VBox menuOptions = new VBox(30);
        VBox menuNiveaux = new VBox(20);
        VBox menuNiveaux2 = new VBox(30);
        VBox menuNiveaux3 = new VBox(30);
        VBox menuNiveaux4 = new VBox(30);
        VBox menuChapitres = new VBox(30);
        VBox menuAleatoire = new VBox(30);
        VBox menuParametre = new VBox(30);
        MenuButton btnSuivant;
        MenuButton btnSuivant2;
        MenuButton btnSuivant3;

        // CONSTRUCTEUR 
        private MenuPrincipal() {
            int posX = Parametre.getLargeurFenetre() / 2 - Parametre.getLargeurFenetre() / 4;

            // Position du menu
            menuParent.setTranslateX(posX);
            menuParent.setTranslateY(Parametre.getHauteurFenetre() / 8);

            menuOptions.setTranslateX(Parametre.getLargeurFenetre() / 2);
            menuOptions.setTranslateY(Parametre.getHauteurFenetre() / 2);

            menuNiveaux.setTranslateX(Parametre.getLargeurFenetre() / 2);
            menuNiveaux.setTranslateY(Parametre.getHauteurFenetre() / 8);

            menuNiveaux2.setTranslateX(Parametre.getLargeurFenetre() / 2);
            menuNiveaux2.setTranslateY(Parametre.getHauteurFenetre() / 8);

            menuNiveaux3.setTranslateX(Parametre.getLargeurFenetre() / 2);
            menuNiveaux3.setTranslateY(Parametre.getHauteurFenetre() / 8);

            menuNiveaux4.setTranslateX(Parametre.getLargeurFenetre() / 2);
            menuNiveaux4.setTranslateY(Parametre.getHauteurFenetre() / 8);

            menuChapitres.setTranslateX(Parametre.getLargeurFenetre() / 2);
            menuChapitres.setTranslateY(Parametre.getHauteurFenetre() / 8);

            menuAleatoire.setTranslateX(Parametre.getLargeurFenetre() / 2);
            menuAleatoire.setTranslateY(Parametre.getHauteurFenetre() / 8);

            menuParametre.setTranslateX(Parametre.getLargeurFenetre() / 2);
            menuParametre.setTranslateY(Parametre.getHauteurFenetre() / 8);

            // Distance de l'effet de transition
            final int deplacement = 3000;
            menuOptions.setTranslateX(deplacement);
            menuNiveaux.setTranslateX(deplacement);
            menuNiveaux2.setTranslateX(deplacement);
            menuNiveaux3.setTranslateX(deplacement);
            menuNiveaux4.setTranslateX(deplacement);
            menuChapitres.setTranslateX(deplacement);
            menuAleatoire.setTranslateX(deplacement);
            menuParametre.setTranslateX(deplacement);

            // BOUTON NIVEAUX
            MenuButton btnJouer = new MenuButton("NIVEAUX");
            btnJouer.setOnMouseClicked(event -> {
                if (getChildren().contains(menuNiveaux)) {
                    return;
                }
                getChildren().add(menuNiveaux);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menuParent);
                tt.setToX(menuParent.getTranslateX() - deplacement);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menuNiveaux);
                tt1.setToX(menuParent.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menuParent);
                });
            });

            btnJouer.addIcon(Parametre.ICON_STAIRS, true);

            // BOUTON HISTOIRE
            MenuButton btnStory = new MenuButton("MODE HISTOIRE");
            btnStory.setOnMouseClicked(event -> {
                if (getChildren().contains(menuChapitres)) {
                    return;
                }
                getChildren().add(menuChapitres);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menuParent);
                tt.setToX(menuParent.getTranslateX() - deplacement);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menuChapitres);
                tt1.setToX(menuParent.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menuParent);
                });
            });
            btnStory.addIcon(Parametre.ICON_STORY, true);

            // BOUTON ALÉATOIRE
            MenuButton btnAleatoire = new MenuButton("ALÉATOIRE");
            btnAleatoire.setOnMouseClicked(event -> {
                if (getChildren().contains(menuAleatoire)) {
                    return;
                }
                getChildren().add(menuAleatoire);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menuParent);
                tt.setToX(menuParent.getTranslateX() - deplacement);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menuAleatoire);
                tt1.setToX(menuParent.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menuParent);
                });
            });
            btnAleatoire.addIcon(Parametre.ICON_ALEA, true);

            // BOUTON QUITTER
            MenuButton btnQuitter = new MenuButton("QUITTER");
            btnQuitter.setOnMouseClicked(event -> {
                System.exit(0);
            });
            btnQuitter.addIcon(Parametre.ICON_EXIT, true);

            // BOUTON RETOUR DU MENU NIVEAU
            MenuButton btnRetour0 = new MenuButton("RETOUR");
            btnRetour0.setOnMouseClicked(event -> {
                if (getChildren().contains(menuParent)) {
                    return;
                }
                getChildren().add(menuParent);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menuNiveaux);
                tt.setToX(menuNiveaux.getTranslateX() + deplacement);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menuParent);
                tt1.setToX(menuNiveaux.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menuNiveaux);
                });
            });
            btnRetour0.addIcon(Parametre.ICON_BACK, true);

            // BOUTON SUIVANT DU MENU NIVEAU (PAGE 1 -> 2)
            btnSuivant = new MenuButton("SUIVANT");
            btnSuivant.setOnMouseClicked(event -> {
                if (getChildren().contains(menuNiveaux2)) {
                    return;
                }
                getChildren().add(menuNiveaux2);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menuNiveaux);
                tt.setToX(menuNiveaux.getTranslateX() - deplacement);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menuNiveaux2);
                tt1.setToX(menuNiveaux.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menuNiveaux);
                });
            });
            btnSuivant.addIcon(Parametre.ICON_NEXT, true);

            // BOUTON SUIVANT DU MENU NIVEAU (PAGE 2 -> 3)
            btnSuivant2 = new MenuButton("SUIVANT");
            btnSuivant2.setOnMouseClicked(event -> {
                if (getChildren().contains(menuNiveaux3)) {
                    return;
                }
                getChildren().add(menuNiveaux3);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menuNiveaux2);
                tt.setToX(menuNiveaux2.getTranslateX() - deplacement);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menuNiveaux3);
                tt1.setToX(menuNiveaux2.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menuNiveaux2);
                });
            });
            btnSuivant2.addIcon(Parametre.ICON_NEXT, true);

            // BOUTON SUIVANT DU MENU NIVEAU (PAGE 3 -> 4)
            btnSuivant3 = new MenuButton("SUIVANT");
            btnSuivant3.setOnMouseClicked(event -> {
                if (getChildren().contains(menuNiveaux4)) {
                    return;
                }
                getChildren().add(menuNiveaux4);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menuNiveaux3);
                tt.setToX(menuNiveaux3.getTranslateX() - deplacement);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menuNiveaux4);
                tt1.setToX(menuNiveaux3.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menuNiveaux3);
                });
            });
            btnSuivant3.addIcon(Parametre.ICON_NEXT, true);

            // BOUTON PRECEDENT DU MENU NIVEAU (PAGE 2 -> 1)
            MenuButton btnPrecedent = new MenuButton("PRECEDENT");
            btnPrecedent.setOnMouseClicked(event -> {
                if (getChildren().contains(menuNiveaux)) {
                    return;
                }
                getChildren().add(menuNiveaux);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menuNiveaux2);
                tt.setToX(menuNiveaux2.getTranslateX() + deplacement);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menuNiveaux);
                tt1.setToX(menuNiveaux2.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menuNiveaux2);
                });
            });
            btnPrecedent.addIcon(Parametre.ICON_BACK, true);

            // BOUTON PRECEDENT DU MENU NIVEAU (PAGE 3 -> 2)
            MenuButton btnPrecedent2 = new MenuButton("PRECEDENT");
            btnPrecedent2.setOnMouseClicked(event -> {
                if (getChildren().contains(menuNiveaux2)) {
                    return;
                }
                getChildren().add(menuNiveaux2);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menuNiveaux3);
                tt.setToX(menuNiveaux3.getTranslateX() + deplacement);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menuNiveaux2);
                tt1.setToX(menuNiveaux3.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menuNiveaux3);
                });
            });
            btnPrecedent2.addIcon(Parametre.ICON_BACK, true);

            // BOUTON PRECEDENT DU MENU NIVEAU (PAGE 4 -> 3)
            MenuButton btnPrecedent3 = new MenuButton("PRECEDENT");
            btnPrecedent3.setOnMouseClicked(event -> {
                if (getChildren().contains(menuNiveaux3)) {
                    return;
                }
                getChildren().add(menuNiveaux3);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menuNiveaux4);
                tt.setToX(menuNiveaux4.getTranslateX() + deplacement);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menuNiveaux3);
                tt1.setToX(menuNiveaux4.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menuNiveaux4);
                });
            });
            btnPrecedent3.addIcon(Parametre.ICON_BACK, true);

            // BOUTON RETOUR DU MENU ALÉATOIRE
            MenuButton btnRetour1 = new MenuButton("RETOUR");
            btnRetour1.setOnMouseClicked(event -> {
                if (getChildren().contains(menuParent)) {
                    return;
                }
                getChildren().add(menuParent);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menuAleatoire);
                tt.setToX(menuAleatoire.getTranslateX() + deplacement);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menuParent);
                tt1.setToX(menuAleatoire.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menuAleatoire);
                });
            });
            btnRetour1.addIcon(Parametre.ICON_BACK, true);

            // BOUTON RETOUR DU MENU CHAPITRES
            MenuButton btnRetour2 = new MenuButton("RETOUR");
            btnRetour2.setOnMouseClicked(event -> {
                if (getChildren().contains(menuParent)) {
                    return;
                }
                getChildren().add(menuParent);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menuChapitres);
                tt.setToX(menuChapitres.getTranslateX() + deplacement);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menuParent);
                tt1.setToX(menuChapitres.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menuChapitres);
                });
            });
            btnRetour2.addIcon(Parametre.ICON_BACK, true);

            // BOUTON CREDITS            
            MenuButton btnCredits = new MenuButton("CRÉDITS");
            btnCredits.setOnMouseClicked(event -> {
                VueMenuPrincipal.primaryStage.setScene(new VueCredits(VueMenuPrincipal.primaryStage).getScene());
                nullify();
            });
            this.getChildren().add(btnCredits);
            btnCredits.addIcon(Parametre.ICON_ABOUT, true);

            // BOUTON PARAMETRE            
            MenuButton btnParametres = new MenuButton("PARAMÈTRES");
            btnParametres.setOnMouseClicked(event -> {
                if (getChildren().contains(menuParametre)) {
                    return;
                }
                getChildren().add(menuParametre);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menuParent);
                tt.setToX(menuParent.getTranslateX() - deplacement);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menuParametre);
                tt1.setToX(menuParent.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menuParent);
                });
            });
            this.getChildren().add(btnParametres);
            btnParametres.addIcon(Parametre.ICON_SETTINGS, true);

            // BOUTON RETOUR DES PARAMETRES            
            MenuButton retour = new MenuButton("RETOUR", "RETOUR", Parametre.getLargeurFenetre() / 2, 50);
            retour.setOnMouseClicked(event -> {
                if (getChildren().contains(menuParent)) {
                    return;
                }
                getChildren().add(menuParent);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menuParametre);
                tt.setToX(menuParametre.getTranslateX() + deplacement);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menuParent);
                tt1.setToX(menuParametre.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menuParametre);
                });
            });
            menuParametre.getChildren().add(retour);
            retour.addIcon(Parametre.ICON_BACK, true);

            // BOUTON SON
            String s1;
            if (Parametre.isAudioMute()) {
                s1 = "Activer le son";
                
            } else {
                s1 = "Désactiver le son";
                
            }

            MenuButton btnSon = new MenuButton(s1, s1, Parametre.getLargeurFenetre() / 2, 50, true);
            
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
            menuParametre.getChildren().add(btnSon);

            // BOUTON ANIM
            String s2;
            if (!Parametre.isAnime()) {
                s2 = "Activer l'animation";
            } else {
                s2 = "Désactiver l'animation";
            }

            MenuButton btnAnim = new MenuButton(s2, s2, Parametre.getLargeurFenetre() / 2, 50, true);
            btnAnim.setOnMouseClicked(event -> {
                Parametre.setAnime(!Parametre.isAnime());
                if (!Parametre.isAnime()) {
                    btnAnim.setText("Activer l'animation", true);
                    btnAnim.removeIcon();
                    VueMenuPrincipal.root.getChildren().remove(iv);
                    btnAnim.addIcon(Parametre.ICON_ANIM2, true);

                } else {
                    btnAnim.setText("Désactiver l'animation", true);
                    btnAnim.removeIcon();
                    VueMenuPrincipal.root.getChildren().add(0,iv);
                    btnAnim.addIcon(Parametre.ICON_ANIM, true);
                }
            });
            if (!Parametre.isAnime()) {
                btnAnim.addIcon(Parametre.ICON_ANIM2, true);
            } else {
                btnAnim.addIcon(Parametre.ICON_ANIM, true);
            }

            menuParametre.getChildren().add(btnAnim);

            // BOUTON GAMEPLAY
            String s3;
            if (Parametre.isDoubleClic()) {
                s3 = "Activer le survol";
            } else {
                s3 = "Désactiver le survol";
            }

            MenuButton btnClic = new MenuButton(s3, s3, Parametre.getLargeurFenetre() / 2, 50, true);
            btnClic.setOnMouseClicked(event -> {
                Parametre.setDoubleClic(!Parametre.isDoubleClic());
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
            menuParametre.getChildren().add(btnClic);

            // BOUTON LOW GRAPHICS
            String sLg;
            if (!Parametre.isLow()) {
                sLg = "Désactiver la HD";
            } else {
                sLg = "Activer la HD";
            }
            String sLr = "Redémarrage nécessaire";
            MenuButton btnLow = new MenuButton(sLg, sLr, Parametre.getLargeurFenetre() / 2, 50, true);
            btnLow.setOnMouseClicked(event -> {
                Parametre.setLow(!Parametre.isLow());
                if (!Parametre.isLow()) {
                    btnLow.setText("Désactiver la HD", sLr,true);
                    btnLow.removeIcon();
                    btnLow.addIcon(Parametre.ICON_SETTINGS, true);
                } else {
                    btnLow.setText("Activer la HD", sLr,true);
                    btnLow.removeIcon();
                    btnLow.addIcon(Parametre.ICON_SETTINGS, true);
                }
            });
            if (Parametre.isLow()) {
                btnLow.addIcon(Parametre.ICON_SETTINGS, true);
            } else {
                btnLow.addIcon(Parametre.ICON_SETTINGS, true);
            }
            menuParametre.getChildren().add(btnLow);
            
            // BOUTON PLEIN ECRAN
            String sPl;
            if (Parametre.isPleinEcran()) {
                sPl = "Désactiver le plein écran";
            } else {
                sPl = "Activer le plein écran";
            }
            String sPr = "Redémarrage nécessaire";
            MenuButton btnFullScreen = new MenuButton(sPl, sPr, Parametre.getLargeurFenetre() / 2, 50, true);
            btnFullScreen.setOnMouseClicked(event -> {
                Parametre.setPleinEcran(!Parametre.isPleinEcran());
                if (Parametre.isPleinEcran()) {
                    btnFullScreen.setText("Désactiver le plein écran", sPr,true);
                    btnFullScreen.removeIcon();
                    btnFullScreen.addIcon(Parametre.ICON_SETTINGS, true);
                } else {
                    btnFullScreen.setText("Activer le plein écran", sPr,true);
                    btnFullScreen.removeIcon();
                    btnFullScreen.addIcon(Parametre.ICON_SETTINGS, true);
                }
            });
            if (Parametre.isPleinEcran()) {
                btnFullScreen.addIcon(Parametre.ICON_SETTINGS, true);
            } else {
                btnFullScreen.addIcon(Parametre.ICON_SETTINGS, true);
            }
            menuParametre.getChildren().add(btnFullScreen);
            
            
            
            
            // AJOUTS DU MENU PARENT
            super.getChildren().addAll(menuParent);

            // AJOUT DES BOUTONS DU MENU PARENT
            menuParent.getChildren().addAll(btnJouer, btnStory, btnAleatoire, btnParametres, btnCredits, btnQuitter);

            // AJOUT DU BOUTON RETOUR DU MENU NIVEAUX
            menuNiveaux.getChildren().addAll(btnRetour0);

            // AJOUT DU BOUTON PRECEDENT DU MENU NIVEAUX (Page 2-> 1)
            menuNiveaux2.getChildren().addAll(btnPrecedent);

            // AJOUT DU BOUTON PRECEDENT DU MENU NIVEAUX (Page 3-> 2)
            menuNiveaux3.getChildren().addAll(btnPrecedent2);

            // AJOUT DU BOUTON PRECEDENT DU MENU NIVEAUX (Page 2 -> 1)
            menuNiveaux4.getChildren().addAll(btnPrecedent3);

            // AJOUT DU BOUTON RETOUR DU MENU CHAPITRE
            menuChapitres.getChildren().addAll(btnRetour2);

            // AJOUT DU BOUTON RETOUR DU MENU ALÉATOIRE
            menuAleatoire.getChildren().addAll(btnRetour1);
        }
    }
    
     /**
    * Méthode qui met à null les pointeurs
    */
    @Override
    protected void nullify(){
        if (this.tt != null) this.tt.stop();
        this.tt = null;
        this.helico = null;
        VueMenuPrincipal.menuPrincipal = null;
        this.iv = null ;
        System.gc();
        Runtime.getRuntime().gc();
    }
}
