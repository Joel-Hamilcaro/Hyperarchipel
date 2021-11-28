/*
*
* Nom de la classe : ModeleHashi 
* 
* Description : 
*   Modèle général du jeu. Stocke l'état courant du plateau, sa solution, 
*   les fonctions liés aux jokers, l'enregistremeent des scores ...
*
* Version : 1.0
*
* Date : Mai 2019
*
* Auteur : PI4 / HASHIWOKAKERO1
*
 */
package javamvc.modele;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModeleHashi {

    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    private final Plateau solution; // Solution stockée    
    private Plateau plateau; // Plateau de jeu courant
    private int score = -1; // Meilleur temps enregistré 
    private static int cpt = 0; // Nombre de plateaux
    private final int id; // Identifiant du plateau
    private final int[] joker3 = {-1, -1}; // Coordonnée de l'ile joker
    private boolean perdu = false; //
    private boolean classique = true; // Pas un chapitre du mode histoire

    // CONSTRUCTEURS ***************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * construteur ModeleHashi
     *
     * @param base : un Plateau déjà créé et de solution connue.
     */
    public ModeleHashi(Plateau base) { /* Avec un plateau pré-créé */
        this.solution = base.cloneSansPonts();   // Plateau vierge
        this.solution.construireAvecSol(base);   // Resolution du plateau
        this.plateau = this.solution.cloneSansPonts();
        cpt++;
        id = cpt;
    }

    /**
     * constructeur ModeleHashi
     *
     * @param n un Plateau aléatoire de coté n
     */
    public ModeleHashi(int n) { // Plateau aléatoire de coté n
        Plateau sol = Solveur.genererPlateau(n);
        this.solution = sol;
        this.plateau = sol.cloneSansPonts();
        id = cpt + n;
    }

    // ACCESSEURS ******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * ACCESSEUR.
     * @return plateau.
     */
    public Plateau getPlateau() {
        return this.plateau;
    }

    /**
     * ACCESSEUR.
     * @return solution.
     */
    public Plateau getSolution() {
        return this.solution;
    }

    /**
     * ACCESSEUR.
     * @return coordonnée de l'ile choisie par le joker 3.
     */
    public int[] getJoker3() {
        return this.joker3;
    }

    /**
     * ACCESSEUR.
     * @return score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * ACCESSEUR.
     * @return ID du modèle
     */
    public int getID() {
        return id;
    }

    /**
     * ACCESSEUR.
     * @return true si le modèle est en mode histoire.
     */
    public boolean isStory() {
        return !classique;
    }

    /**
     * ACCESSEUR.
     * @return true si la partie est perdue.
     */
    public boolean isPerdu() {
        return perdu;
    }

    /**
     * ACCESSEUR.
     * @return le numero du chapitre de l'histoire associé au modèle (-1 si pas en mode histoire)
     */
    public int getChapitre() {
        if (this.id < Niveaux.getNiveaux().size() - 3 || this.id > Niveaux.getNiveaux().size()) {
            return -1;
        } else {
            return Niveaux.getNiveaux().size() - this.id + 1;
        }
    }
    
    // MUTATEURS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Met le modèle en mode histoire.
     */
    public void setStory() {
        classique = false;
    }

    /**
     * Déclare la partie perdue.
     */
    public void setPerdu() {
        perdu = true;
    }   

    
    // METHODES ********************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Representation textuelle du score
     * @return String de la forme --:--:--
     */
    public String scoreToString() {
        if (score == -1) {
            return "--:--:--";
        } else {
            return String.format("%02d", score / 3600) + ":" + String.format("%02d", score / 60 % 60) + ":" + String.format("%02d", score % 60) + "";
        }
    }
    
    /**
     * fonction estFini
     *
     * @return true si le plateau est résolu,
     */
    public boolean estFini() {
        return plateau.equals(solution);
    }

    /**
     * Supprime tous les ponts du plateau et réinitialise les jokers.
     */
    public void reinitialiser() {
        this.plateau = this.solution.cloneSansPonts();
        joker3[0] = -1;
        joker3[1] = -1;
        this.perdu = false;
    }

    /**
     * construit un pont correct (conforme à la solution).
     *
     * @return true si le pont à été construit, false si on ne peut pas
     * construire un tel pont.
     */
    public boolean joker1() {
        for (int i = 0; i < plateau.length(); i++) {
            for (int j = 0; j < plateau.length(); j++) {
                Case ile = plateau.getCase(i, j);
                if (ile != null && ile.getPR() > 0) {
                    int[][] voisins = plateau.voisinsLibres(i, j);
                    for (int k = 0; k < plateau.nbVoisinsLibres(i, j); k++) {
                        if (!plateau.sontLies(i, j, voisins[k][0], voisins[k][1])
                                && solution.sontLies(i, j, voisins[k][0], voisins[k][1])) {
                            plateau.construirePont(i, j, voisins[k][0], voisins[k][1]);
                            return true;
                        }
                        if ((plateau.sontLies(i, j, voisins[k][0], voisins[k][1])
                                && !plateau.sontDoublementLiees(i, j, voisins[k][0], voisins[k][1]))
                                && solution.sontDoublementLiees(i, j, voisins[k][0], voisins[k][1])) {
                            plateau.construirePont(i, j, voisins[k][0], voisins[k][1]);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Supprime un pont incorrect (non conforme à la solution).
     *
     * @return true si le pont a été détruit, false si on ne peut pas détruire
     * un tel pont.
     */
    public boolean joker2() {
        for (int i = 0; i < plateau.length(); i++) {
            for (int j = 0; j < plateau.length(); j++) {
                Case ile = plateau.getCase(i, j);
                if (ile != null) {
                    int[][] voisins = plateau.voisinsLies(i, j);
                    for (int k = 0; k < plateau.nbVoisinsLies(i, j); k++) {
                        if (plateau.sontLies(i, j, voisins[k][0], voisins[k][1])
                                && !solution.sontLies(i, j, voisins[k][0], voisins[k][1])) {
                            plateau.detruirePont(i, j, voisins[k][0], voisins[k][1]);
                            return true;
                        }
                        if ((plateau.sontDoublementLiees(i, j, voisins[k][0], voisins[k][1])
                                && !solution.sontDoublementLiees(i, j, voisins[k][0], voisins[k][1]))) {
                            plateau.detruirePont(i, j, voisins[k][0], voisins[k][1]);
                            plateau.construirePont(i, j, voisins[k][0], voisins[k][1]);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Selectionne et stocke une ile qui n'est pas correctement connecté
     *
     * @return true si l'ile a été stocké, false si il n'existe pas de telle
     * ile.
     */
    public boolean joker3() {
        ArrayList<Integer[]> choixIle = new ArrayList<>();
        boolean trouve = false;
        for (int i = 0; i < plateau.length(); i++) {
            for (int j = 0; j < plateau.length(); j++) {
                Case ile = plateau.getCase(i, j);

                if (ile != null && ile.getNb() > 0
                        && !ile.equals(solution.getCase(i, j))) {
                    Integer[] t = new Integer[2];
                    t[0] = i;
                    t[1] = j;
                    choixIle.add(t);
                    trouve = true;
                }
            }
        }
        if (trouve) {
            Random r = new Random();
            int index = r.nextInt(choixIle.size());
            joker3[0] = choixIle.get(index)[0];
            joker3[1] = choixIle.get(index)[1];
            return true;
        }
        return false;
    }

    /**
     * enregistre le temps du chronometre si il est meilleur que l'ancien temps.
     *
     * @param temps : temps à enregistrer.
     */
    public void setScore(int temps) {
        if ((score == -1 && temps > 0) || (temps < score && temps > 0)) {
            score = temps;
            sauvegarder();
        }
    }

    /**
     * Initialise le score du modèle.
     *
     * @param temps le temps
     */
    public void initScore(int temps) {
        score = temps;
    }

    /**
     * Ecriture du score dans le fichier save.
     */
    @SuppressWarnings("null")
    public void sauvegarder() {
        File f = new File(Parametre.FILE_SCORE);
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(f);
        } catch (FileNotFoundException ex) {
            System.out.println("Fichier " + Parametre.FILE_SCORE + " introuvable.");
        }
        f.setReadable(true);
        f.setWritable(true);
        ArrayList<String> lignes = new ArrayList<>();
        Path fichier = Paths.get(Parametre.FILE_SCORE);
        int i = 1;
        while (sc2.hasNextLine()) {
            if (id == i) {
                lignes.add(Integer.toString(score));
                sc2.nextLine();
            } else if (id != i) {
                lignes.add(sc2.nextLine());
            }
            i++;
        }

        while (id >= i) {
            if (id != i) {
                lignes.add("-1");
            } else if (id == i) {
                lignes.add(Integer.toString(score));
            }
            i++;
        }
        try {
            Files.write(fichier, lignes, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(ModeleHashi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
