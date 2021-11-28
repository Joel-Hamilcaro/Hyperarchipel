/*
*
* Nom de la classe : VueHashiTuto
* 
* Description : 
*   Interface graphique du tutoriel
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
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class VueHashiTuto extends VueHashiScriptee {

    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    private int etapeTuto = 0;
    private ObjetMobile flecheTuto;
    private final ArrayList<IleGraphique> ilesTuto = new ArrayList<>();

    // CONSTRUCTEURS ***************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    /**
     * Constructeur VueHashiTuto (Tutoriel)
     *
     * @param c Controleur.
     */
    public VueHashiTuto(ControleurHashi c) {
        super(c);
        scene.setOnKeyPressed((KeyEvent t) -> {
            KeyCode key = t.getCode();
            if (key == KeyCode.ESCAPE) {
                cont.getContMod().reinitialiser();
                cont.getContChrono().stop();
                if (animationEnCours != null) {
                    animationEnCours.stop();
                }
                VueHashiTuto.primaryStage.setScene(new VueMenuPrincipal(cont.getPrimaryStage()).getScene());
                nullify();
            }
        });
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
            this.plateauGraphique.getChildren().add(sp);
            ilesTuto.add(sp);
        }
    }

    public void initDialogueTuto(int k) {
        enPause = true;
        Dialogue.initDialogue(k, true);
        while (Dialogue.getDialogue().hasPrevious()) {
            Dialogue.getDialogue().previous();
        }
        DialogueHashi dh = new DialogueHashi(Parametre.getHauteurFenetre());
        dh.setOnMouseClicked(evt -> {
            if (etapeTuto == 48) {
                return;
            }
            if (dh.ft.getStatus() == Animation.Status.RUNNING) {
                return;
            }
            if (Dialogue.getDialogue().hasNext()) {
                scriptTuto(++etapeTuto);
                dh.actualiseDialogue();

            } else {
                scriptTuto(++etapeTuto);
                root.getChildren().remove(dh);
            }

        });
        dh.setTranslateY(0);
        root.getChildren().add(dh);
        dh.actualiseDialogue();
    }

    /**
     * Programme les étapes du tutoriel.
     *
     * @param etape etape du tutoriel
     */
    public void scriptTuto(int etape) {
        switch (etape) {

            default:

                for (int i = 0; i < ilesTuto.size(); i++) {
                    ilesTuto.get(i).setOnMouseClicked(MouseEvent -> {
                    });
                }
                for (int i = 0; i < ilesTuto.size(); i++) {
                    ilesTuto.get(i).setOnMouseEntered(MouseEvent -> {
                    });
                }
                return;

            case 13:

                initFlecheTuto(1);
                IleGraphique ig1 = ilesTuto.get(1);
                ig1.indiquer();
                ig1.setOnMouseClicked(MouseEvent -> {

                    if (nbCliques < 1) {
                        selec[nbCliques] = ig1;
                        ig1.c.setOpacity(0.5);
                        ig1.estClique = true;
                        nbCliques++;
                        ig1.desindiquer();
                        initDialogueTuto(8);
                    }
                });
                break;

            case 15:
                
                initFlecheTuto(6);
                IleGraphique ig6 = ilesTuto.get(6);
                ig6.indiquer();
                ig6.setOnMouseClicked(MouseEvent -> {

                    if (nbCliques == 1) {
                        selec[nbCliques] = ig6;
                        ig6.c.setOpacity(0.5);
                        ig6.estClique = true;
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
                            initFlecheTuto(-1);
                            ig6.desindiquer();
                            initDialogueTuto(9);
                        }
                    }
                });
                break;

            case 17:
                
                initFlecheTuto(1);
                IleGraphique ig11 = ilesTuto.get(1);
                ig11.indiquer();
                ig11.setOnMouseClicked(MouseEvent -> {

                    if (nbCliques < 1) {
                        selec[nbCliques] = ig11;
                        ig11.c.setOpacity(0.5);
                        ig11.estClique = true;
                        nbCliques++;
                        ig11.desindiquer();
                        scriptTuto(++etapeTuto);
                    }
                });
                break;

            case 18:
                
                initFlecheTuto(6);
                IleGraphique ig66 = ilesTuto.get(6);
                ig66.indiquer();
                ig66.setOnMouseClicked(MouseEvent -> {

                    if (nbCliques == 1) {
                        selec[nbCliques] = ig66;
                        ig66.c.setOpacity(0.5);
                        ig66.estClique = true;
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
                            initFlecheTuto(-1);
                            ig66.desindiquer();
                            initDialogueTuto(10);
                        }
                    }
                });
                break;

            case 20:
                
                initFlecheTuto(1);
                IleGraphique ig111 = ilesTuto.get(1);
                ig111.indiquer();
                ig111.setOnMouseClicked(MouseEvent -> {

                    if (nbCliques < 1) {
                        selec[nbCliques] = ig111;
                        ig111.c.setOpacity(0.5);
                        ig111.estClique = true;
                        nbCliques++;
                        ig111.desindiquer();
                        scriptTuto(++etapeTuto);
                    }
                });
                break;

            case 21:

                initFlecheTuto(6);
                IleGraphique ig666 = ilesTuto.get(6);
                ig666.indiquer();
                ig666.setOnMouseClicked(MouseEvent -> {

                    if (nbCliques == 1) {
                        selec[nbCliques] = ig666;
                        ig666.c.setOpacity(0.5);
                        ig666.estClique = true;
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
                            initFlecheTuto(-1);
                            ig666.desindiquer();
                            initDialogueTuto(11);
                        }
                    }
                });
                break;
                
            case 23:

                initFlecheTuto(1);
                IleGraphique ig1111 = ilesTuto.get(1);
                ig1111.indiquer();
                ig1111.setOnMouseClicked(MouseEvent -> {

                    if (nbCliques < 1) {
                        selec[nbCliques] = ig1111;
                        ig1111.c.setOpacity(0.5);
                        ig1111.estClique = true;
                        nbCliques++;
                        ig1111.desindiquer();
                        scriptTuto(++etapeTuto);
                    }
                });
                break;
                
            case 24:
                
                for (int i = 0; i < ilesTuto.size(); i++) {
                    ilesTuto.get(i).setOnMouseClicked(MouseEvent -> {
                    });
                }
                initFlecheTuto(6);
                IleGraphique ig6666 = ilesTuto.get(6);
                ig6666.indiquer();
                ig6666.setOnMouseEntered(MouseEvent -> {

                    if (nbCliques == 1) {
                        selec[nbCliques] = ig6666;
                        ig6666.c.setOpacity(0.5);
                        ig6666.estClique = true;
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
                            ig6666.desindiquer();
                            initDialogueTuto(12);
                            initFlecheTuto(-1);

                        }
                    }
                });
                break;
                
            case 30:

                int i = 0;
                JokerGraphique sp = new JokerGraphique(i + 1);
                sp.setLayoutX(Parametre.getLargeurFenetre() - 50 - 12.5 - Parametre.getLargeurFenetre() / 3 + Parametre.getLargeurFenetre() / 3 / 3 * (i));
                sp.setLayoutY(12.5);
                if (true || !this.cont.getContMod().isStory()) {
                    this.infos.getChildren().add(sp);
                }
                sp.setOnMouseClicked(evt -> {
                    if (cont.getContMod().joker1()) {
                        sp.utilisable = false;
                        infos.getChildren().remove(sp);
                        cont.actualiseVue();
                        cont.getContChrono().penalite(!cont.getContMod().isStory());
                        initDialogueTuto(13);
                    }
                });
                break;

            case 37:

                int i2 = 1;
                JokerGraphique sp2 = new JokerGraphique(i2 + 1);
                sp2.setLayoutX(Parametre.getLargeurFenetre() - 50 - 12.5 - Parametre.getLargeurFenetre() / 3 + Parametre.getLargeurFenetre() / 3 / 3 * (i2));
                sp2.setLayoutY(12.5);
                if (true || !this.cont.getContMod().isStory()) {
                    this.infos.getChildren().add(sp2);
                }
                sp2.setOnMouseClicked(evt -> {
                    if (cont.getContMod().joker2()) {
                        sp2.utilisable = false;
                        infos.getChildren().remove(sp2);
                        cont.actualiseVue();
                        cont.getContChrono().penalite(!cont.getContMod().isStory());
                        initDialogueTuto(14);
                    }
                });
                break;

            case 44:

                int i3 = 2;
                JokerGraphique sp3 = new JokerGraphique(i3 + 1);
                sp3.setLayoutX(Parametre.getLargeurFenetre() - 50 - 12.5 - Parametre.getLargeurFenetre() / 3 + Parametre.getLargeurFenetre() / 3 / 3 * (i3));
                sp3.setLayoutY(12.5);
                if (true || !this.cont.getContMod().isStory()) {
                    this.infos.getChildren().add(sp3);
                }
                sp3.setOnMouseClicked(evt -> {
                    if (cont.getContMod().joker3()) {
                        sp3.utilisable = false;
                        infos.getChildren().remove(sp3);
                        cont.actualiseVue();
                        cont.getContChrono().penalite(!cont.getContMod().isStory());
                        initDialogueTuto(15);
                    }
                });
                break;

            case 48:

                for (int k1 = 0; k1 < ilesTuto.size(); k1++) {
                    final int l = k1;
                    ilesTuto.get(k1).setDefault();
                }
                enPause = false;
                this.initMenuPause();
                break;
        }
    }

    /**
     * Initialise la fleche du tutoriel (qui indique où cliquer)
     * @param i id de l'ile du tutoriel.
     */
    public void initFlecheTuto(int i) {
        if (i == -1) {
            this.plateauGraphique.getChildren().remove(flecheTuto);
            return;
        }
        this.plateauGraphique.getChildren().remove(flecheTuto);
        flecheTuto = new ObjetMobile(true);
        double adj = Math.cos(Math.PI / 4) * rayon;
        double opp = Math.sin(Math.PI / 4) * rayon;

        flecheTuto.setTranslateX(ilesTuto.get(i).getLayoutX() + rayon);
        flecheTuto.setTranslateY(ilesTuto.get(i).getLayoutY() + 2 * rayon + decalageY);
        this.plateauGraphique.getChildren().add(flecheTuto);
    }
    
    /**
    * Méthode qui met à null les pointeurs
    */
    @Override
    protected void nullify(){
        this.flecheTuto = null;
        super.nullify();
    }
    
}
