/*
*
* Nom de la classe : Plateau 
* 
* Description : 
*   Plateau du jeu. Contient des iles et des ponts.
*   
*
* Version : 1.0
*
* Date : Mai 2019
*
* Auteur : PI4 / HASHIWOKAKERO1
*
*/

package javamvc.modele;

import java.util.ArrayList;
import java.util.Random;

public class Plateau {

    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    private Case[][] cases;
    private ArrayList<Plateau> solutions = new ArrayList<>();

    // CONSTRUCTEURS ***************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * constructeur Plateau 
     * 
     * @param length longueur et largeur du Plateau 
     */
    public Plateau(int length) {
        this.cases = new Case[length][length];
    }
    
    /**
     * constructeur Plateau 
     * 
     * @param iles: un tableau de Case
     */
    public Plateau(Case[][] iles) {
        this.cases = iles;
    }
    
    // ACCESSEURS ******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * ACCESSEURS
     * 
     * @return  la liste des solutions pour les Plateau aleatoire (pour ferifier l'unicité)
     */
    public ArrayList<Plateau> getSolutions(){
        return solutions;
    }
    
    /**
     * ACCESSEURS
     *
     * @return longueur du plateau.
     */
    public int length() {
        return cases.length;
    }

    /**
     * ACCESSEURS
     * 
     * @param i Coordonnée x de la case (de haut en bas).
     * @param j Coordonnée y de la case (de gauche à droite).
     * @return case de coordonnée i,j.
     */
    public Case getCase(int i, int j) {
        return cases[i][j];
    }

    /**
     * ACCESSEURS
     * 
     * @return tableau des cases du plateau.
     */
    public Case[][] getCases() {
        return cases;
    }
    
    /**
     * Iles connectées par le pont qui traverse la case (i,j).
     * @param i:coordonnée x d'une ile0.
     * @param j:coordonnée y d'une ile0.
     * @return les deux iles qui sont connecte apartient un pont.
     */
    public int[][] getIles(int i, int j) {
        int[][] iles = new int[2][2];
        if (this.getCase(i, j).getNb() < 0) {
            if (this.getCase(i, j).getDirection().equals("- ") || this.getCase(i, j).getDirection().equals("= ")) {
                boolean b = false;
                int k = j;
                while (!b) {
                    j--;
                    if (this.getCase(i, j).getNb() > 0) {
                        iles[0][0] = i;
                        iles[0][1] = j;
                        b = true;
                    }
                }
                b = false;
                while (!b) {
                    k++;
                    if (this.getCase(i, k).getNb() > 0) {
                        iles[1][0] = i;
                        iles[1][1] = k;
                        b = true;
                    }
                }
            }
            if (this.getCase(i, j).getDirection().equals("| ") || this.getCase(i, j).getDirection().equals("$ ")) {
                boolean b = false;
                int k = i;
                while (!b) {
                    i--;
                    if (this.getCase(i, j).getNb() > 0) {
                        iles[0][0] = i;
                        iles[0][1] = j;
                        b = true;
                    }
                }
                b = false;
                while (!b) {
                    k++;
                    if (this.getCase(k, j).getNb() > 0) {
                        iles[1][0] = k;
                        iles[1][1] = j;
                        b = true;
                    }
                }
            }
        }
        return iles;
    } 
    
    // MUTATEURS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
     /**
     * Change le tableau de cases du plateau.
     * @param iles Tableau de cases
     */
    public void setCases(Case[][] iles) {
        this.cases = iles;
    }
    
    
    // METHODES GENERALES **********************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * fonction equals 
     * @param plateau: un plateau 
     * @return true si deux Plateaux sont identiques.
     */
    public boolean equals(Plateau plateau) {
        if (this.length() != plateau.length()) {
            return false;
        }
        for (int i = 0; i < plateau.length(); i++) {
            for (int j = 0; j < plateau.length(); j++) {
                if (plateau.getCase(i, j) != null && plateau.getCase(i, j).getNb() > 0
                        && this.getCase(i, j) != null && this.getCase(i, j).getNb() > 0) {
                    if (!plateau.getCase(i, j).equals(this.getCase(i, j))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    

    
    /**
     * fonction toString : représente le plateau en mode textuel.
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.length(); i++) {
            for (int j = 0; j < this.length(); j++) {
                if (cases[i][j] != null) {
                    if (cases[i][j].getNb() == -1) {
                        s = s + "" + cases[i][j].getDirection();
                    } else {
                        s = s + cases[i][j].getNb() + " ";
                    }
                } else {
                    s = s + "  ";
                }
            }
            s = s + "\n";
        }
        return s;
    }
    
    /**
     * fonction toMatrix : la matrice qui code un plateau dans les normes de Niveaux.java. 
     * @return la matrice du plateau.
     */
    public String toMatrix() {
        String s = "" + length() + "\n";
        for (int i = 0; i < this.length(); i++) {
            for (int j = 0; j < this.length(); j++) {
                if (cases[i][j] != null && cases[i][j].getNb() > 0) {
                    s = s + "" + i + " " + j + " " + cases[i][j].getNb() + " " + cases[i][j].getNord() + " " + cases[i][j].getSud() + " " + cases[i][j].getEst() + " " + cases[i][j].getOuest() + "\n";
                }
            }
        }
        return "{\n" + s + "}";
    }

    
   /**
    * fonction Clone : clone un Plateau.
    * @return un plateau clone.
    */
    @Override
    @SuppressWarnings({"CloneDoesntCallSuperClone", "CloneDeclaresCloneNotSupported"})
    public Plateau clone() {
        Case[][] tabnet = new Case[this.length()][this.length()];
        for (int i = 0; i < this.length(); i++) {
            for (int j = 0; j < this.length(); j++) {
                if (getCase(i, j) != null) {
                    tabnet[i][j] = new Case(getCase(i, j).getNb(), getCase(i, j).getNord(), getCase(i, j).getSud(), getCase(i, j).getEst(), getCase(i, j).getOuest());
                    tabnet[i][j].setTest(getCase(i, j).isOnTest());
                }
            }
        }
        return new Plateau(tabnet);
    }
    
    /**
     * fonction cloneSansPonts : un clone sans ponts du plateau.
     * @return un clone du plateau sans les ponts.
     */
    public final Plateau cloneSansPonts() {
        Case[][] tabnet = new Case[this.length()][this.length()];
        for (int i = 0; i < this.length(); i++) {
            for (int j = 0; j < this.length(); j++) {
                if (getCase(i, j) != null && getCase(i, j).getNb() != -1) {
                    tabnet[i][j] = new Case(getCase(i, j).getNb());
                }
            }
        }
        return new Plateau(tabnet);
    }
    
    
    /**
     * Détruit tous les ponts
     * @param i: coordonnée x d'une ile
     * @param j : coordonnée y d'une ile
     */
    public void detruireTousLesPonts(int i, int j) {
        int[][] voisins = voisinsLibres(i, j);
        for (int k = 0; k < nbVoisinsLibres(i, j); k++) {
            this.detruirePont(i, j, voisins[k][0], voisins[k][1]);
        }
    }
    
    
    /**
     * Vérifie si le plateau est résolu.
     * @return true si le plateau est résolu.
     */
    public boolean estFini() {
        if (this.partieIsolee()) {
            return false;
        } //partie isolee
        for (int i = 0; i < this.length(); i++) {
            for (int j = 0; j < this.length(); j++) {
                if (this.getCase(i, j) != null && this.getCase(i, j).getNb() >= 0) {
                    if (this.getCase(i, j).getPonts() != this.getCase(i, j).getNb()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
 
    
    
    // METHODES D'ACTIONS DE JEU : CONSTRUCTION ET DESCTRUCTIONS DE PONTS **********
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * fonction nullAMoinsUN pour construire un pont
     * @param x coordonnée x d'ile
     * @param y coordonnée y d'ile
     * @param b un boolean  present les direction , true-vertical, false--horizontal
     */
    public void nullAMoinsUn(int x, int y, boolean b) {
        if (getCase(x, y) == null) {
            if (b) {
                cases[x][y] = new Case(-1, 1, 1, 0, 0);
            } else {
                cases[x][y] = new Case(-1, 0, 0, 1, 1);
            }
        }
    }
    
    /**
     * fonction position change les positions des deux iles pour construire les ponts correctement.
     * @param x0:coordonnée x d'une ile0
     * @param y0:coordonnée y d'une ile0
     * @param x1:coordonnée x d'une ile1
     * @param y1:coordonnée y d'une ile1
     * @return un tableau de int qui représente deux iles. 
     */
    public int[] position(int x0, int y0, int x1, int y1) {
        int[] tab = new int[4];
        tab[0] = x0;
        tab[1] = y0;
        tab[2] = x1;
        tab[3] = y1;
        if (x0 > x1 || y0 > y1) {
            tab[0] = x1;
            tab[1] = y1;
            tab[2] = x0;
            tab[3] = y0;
        }
        return tab;
    }
    
    /**
     * fonction construirePont connecte deux iles sans la fonctionalité détruire
     * @param x0:coordonnée x d'une ile0
     * @param y0:coordonnée y d'une ile0
     * @param x1:coordonnée x d'une ile1
     * @param y1:coordonnée y d'une ile1
     */
    public void construirePont(int x0, int y0, int x1, int y1) {
        this.construirePont(x0, y0, x1, y1, false);
    }
    
    /**
     * fonction construirePont connecte deux iles avec choix de la fonctionalite détruire
     * @param x0:coordonnée x d'une ile0
     * @param y0:coordonnée y d'une ile0
     * @param x1:coordonnée x d'une ile1
     * @param y1:coordonnée y d'une ile1
     * @param detruireAuto un boolean :true--peut detruire , false--ne pas détruire automatiquemannt les ponts
     */
    public void construirePont(int x0, int y0, int x1, int y1, boolean detruireAuto) {
        if (x0 < 0 || y0 < 0 || x1 < 0 || y1 < 0) {
            return;
        }
        int[] tab = position(x0, y0, x1, y1);
        x0 = tab[0];
        y0 = tab[1];
        x1 = tab[2];
        y1 = tab[3];
        Case i1 = cases[x0][y0];
        Case i2 = cases[x1][y1];

        if (sontVoisinsLibres(x0, y0, x1, y1)) {
            if (x0 < x1) {
                i1.incSud();
                i2.incNord();
                for (int i = x0 + 1; i < x1; i++) {
                    if (cases[i][y0] == null) {
                        this.nullAMoinsUn(i, y0, true);
                    } else {
                        cases[i][y0].incSud();
                        cases[i][y0].incNord();
                    }
                }
            }
            if (y0 < y1) {
                i1.incEst();
                i2.incOuest();
                for (int j = y0 + 1; j < y1; j++) {
                    if (cases[x0][j] == null) {
                        this.nullAMoinsUn(x0, j, false);
                    } else {
                        cases[x0][j].incOuest();
                        cases[x0][j].incEst();
                    }
                }
            }
            return;
        }
        if (detruireAuto) {
            if (cases[x0][y0].getPR() == 0 || cases[x1][y1].getPR() == 0 || sontDoublementLiees(x0, y0, x1, y1)) {
                detruirePont(x0, y0, x1, y1);
            }
        }
    }
    
    /**
     * fonction detruirePont qui détruit les ponts
     * @param x0:coordonnée x d'une ile0
     * @param y0:coordonnée y d'une ile0
     * @param x1:coordonnée x d'une ile1
     * @param y1:coordonnée y d'une ile1 
     */
    public void detruirePont(int x0, int y0, int x1, int y1) {
        int[] tab = position(x0, y0, x1, y1);
        x0 = tab[0];
        y0 = tab[1];
        x1 = tab[2];
        y1 = tab[3];
        Case i1 = cases[x0][y0];
        Case i2 = cases[x1][y1];
        if (sontLies(x0, y0, x1, y1)) {
            if (x0 < x1) {
                i1.setSud(0);
                i2.setNord(0);
                for (int i = x0 + 1; i < x1; i++) {
                    cases[i][y0] = null;
                }
            } else if (y0 < y1) {
                i1.setEst(0);
                i2.setOuest(0);
                for (int j = y0 + 1; j < y1; j++) {
                    cases[x0][j] = null;
                }
            }
        }
    }
    
        
    /**
     * Construit tous les ponts conformément à la solution donnée en paramètre
     * @param sol : le plateau de solution
     */
    public void construireAvecSol(Plateau sol) {
        for (int i = 0; i < this.length(); i++) {
            for (int j = 0; j < this.length(); j++) {
                if (this.getCase(i, j) != null && this.getCase(i, j).getNb() > 0) {
                    int[][] voisins = this.voisinsOrdre(i, j);
                    // Nord
                    int x1 = voisins[0][0];
                    int y1 = voisins[0][1];
                    // Sud
                    int x2 = voisins[1][0];
                    int y2 = voisins[1][1];
                    // Est
                    int x3 = voisins[2][0];
                    int y3 = voisins[2][1];
                    // Ouest
                    int x4 = voisins[3][0];
                    int y4 = voisins[3][1];

                    for (int k = 0; k < (sol.getCase(i, j).getNord()); k++) {
                        this.construirePont(i, j, x1, y1);
                    }
                    for (int k = 0; k < (sol.getCase(i, j).getSud()); k++) {
                        this.construirePont(i, j, x2, y2);
                    }
                    for (int k = 0; k < (sol.getCase(i, j).getEst()); k++) {
                        this.construirePont(i, j, x3, y3);
                    }
                    for (int k = 0; k < (sol.getCase(i, j).getOuest()); k++) {
                        this.construirePont(i, j, x4, y4);
                    }
                }
            }
        }
    }
    
    // METHODES ASSOCIEES AU VOISINAGE D'UNE ILE DONNEE SUR LE PLATEAU *************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * sontLies si les iles sont connectés (simple ou double). 
     * @param x0:coordonnée x d'une ile0
     * @param y0:coordonnée y d'une ile0
     * @param x1:coordonnée x d'une ile1
     * @param y1:coordonnée y d'une ile1
     * @return true si les iles sont connectés (simple ou double). 
     */
    public boolean sontLies(int x0, int y0, int x1, int y1) {
        int[] tab = position(x0, y0, x1, y1);
        x0 = tab[0];
        y0 = tab[1];
        x1 = tab[2];
        y1 = tab[3];

        if (getCase(x0, y0) == null || getCase(x1, y1) == null) {
            return false; // ile 1 ou ile 2 null
        }
        if (getCase(x0, y0).getNb() == -1 || getCase(x1, y1).getNb() == -1) {
            return false; // ile 1 ou ile 2 null
        }
        if (x0 == x1 && y0 == y1) {
            return false; // ile 1 = ile 2
        }
        if (x0 != x1 && y0 != y1) {
            return false; // ni sur la meme ligne ni sur la meme colonne
        }
        if (x0 == x1) { // cas même ligne
            for (int i = y0 + 1; i < y1; i++) { // Parcours horizontal
                if (getCase(x0, i) == null) {
                    return false; // pas de ponts              
                }
                if (getCase(x0, i) != null && getCase(x0, i).getNb() > 0) {
                    return false; // autre ile fait obstacle
                }
                if (getCase(x0, i) != null && getCase(x0, i).getNb() == -1) {
                    if (!getCase(x0, i).getDirection().equals("- ") && !getCase(x0, i).getDirection().equals("= ")) {
                        return false; // croisement ou déja double pont.
                    }
                }
            }
        }

        if (y0 == y1) { // cas même colonne
            for (int i = x0 + 1; i < x1; i++) { // Parcours vertical
                if (getCase(i, y0) == null) {
                    return false; // pas de ponts
                }
                if (getCase(i, y0) != null && getCase(i, y0).getNb() > 0) {
                    return false; // autre ile fait obstacle
                }
                if (getCase(i, y0) != null && getCase(i, y0).getNb() == -1) {
                    if (!getCase(i, y0).getDirection().equals("| ") && !getCase(i, y0).getDirection().equals("$ ")) {
                        return false; // croisement ou déja double pont.
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * doublementLiees si les iles sont connectés par un pont double. 
     * @param x0:coordonnée x d'une ile0
     * @param y0:coordonnée y d'une ile0
     * @param x1:coordonnée x d'une ile1
     * @param y1:coordonnée y d'une ile1
     * @return true si les iles sont connectés (simple ou double). 
     */
    public boolean sontDoublementLiees(int x0, int y0, int x1, int y1) {
        int[] tab = position(x0, y0, x1, y1);
        x0 = tab[0];
        y0 = tab[1];
        x1 = tab[2];
        y1 = tab[3];
        if (getCase(x0, y0) == null || getCase(x1, y1) == null) {
            return false; // ile 1 ou ile 2 null
        }
        if (getCase(x0, y0).getNb() == -1 || getCase(x1, y1).getNb() == -1) {
            return false; // ile 1 ou ile 2 pont
        }
        if (x0 == x1 && y0 == y1) {
            return false; // ile 1 = ile 2
        }
        if (x0 != x1 && y0 != y1) {
            return false; // ni sur la meme ligne ni sur la meme colonne
        }
        if (x0 == x1) { // cas même ligne
            for (int i = y0 + 1; i < y1; i++) { // Parcours horizontal
                if (getCase(x0, i) == null) {
                    return false; // pas de ponts
                }
                if (getCase(x0, i) != null && getCase(x0, i).getNb() > 0) {
                    return false; // autre ile fait obstacle
                }
                if (getCase(x0, i) != null && getCase(x0, i).getNb() == -1) {
                    if (!getCase(x0, i).getDirection().equals("= ")) {
                        return false; // croisement ou déja double pont.
                    }
                }
            }
        }
        if (y0 == y1) { // cas même colonne
            for (int i = x0 + 1; i < x1; i++) { // Parcours vertical
                if (getCase(i, y0) == null) {
                    return false; // pas de ponts
                }
                if (getCase(i, y0) != null && getCase(i, y0).getNb() > 0) {
                    return false; // autre ile fait obstacle
                }
                if (getCase(i, y0) != null && getCase(i, y0).getNb() == -1) {
                    if (!getCase(i, y0).getDirection().equals("$ ")) {
                        return false; // croisement ou déja double pont.
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * voisinsLies : les voisins connectées avec cette ile.
     * Fonction anciennement appelé liaisons.
     * @param i:coordonnée x d'une ile
     * @param j:coordonnée y d'une ile
     * @return un tableau qui represente les voisins connectées avec cette ile.
     * Tableau de la forme :
     * { {x1,y1}, {x2,y2} , {x3,y3} , {x4,y4} }
     * le tableau est toujours de taille 4 et les cases finales valent {-2,-2}
     * si il n'y a plus de voisins connectés avec l'ile.
     */
    public int[][] voisinsLies(int i, int j) {
        int res = 0;
        int l = 0;
        int[][] voisins = new int[4][2];
        while (l < cases.length) {
            if (sontLies(i, j, i, l)) {
                voisins[res][0] = i;
                voisins[res][1] = l;
                res++;
            }
            if (sontLies(i, j, l, j)) {
                voisins[res][0] = l;
                voisins[res][1] = j;
                res++;
            }
            l++;
        }
        while (res < 4) {
            voisins[res][0] = -2;
            voisins[res][1] = -2;
            res++;
        }
        return voisins;
    }
    
    /**
     * nbVoisinsLies : nombre de voisins connectés avec cette ile. 
     * @param i:coordonnée x d'une ile
     * @param j:coordonnée y d'une ile
     * @return nombre de voisins connecté avec cette ile. 
     */
    public int nbVoisinsLies(int i, int j) {
        int[][] voisins = voisinsLies(i, j);
        int res = 0;
        for (int[] voisin : voisins) {
            if (voisin[0] >= 0) {
                res++;
            }
        }
        return res;
    }
    
    /**
     * Tableau des coordonnées des voisins avec lesquels 
     * on peut encore tracer des ponts avec l'ile de 
     * coordonnées indiqués en parametre.
     *
     * @param i:coordonnée x d'une ile
     * @param j:coordonnée y d'une ile
     * @return Tableau de la forme :
     * { {x1,y1}, {x2,y2} , {x3,y3} , {x4,y4} }
     * le tableau est toujours de taille 4 et les cases finales valent {-2,-2}
     * si il n'y a plus de voisins libres.
     */ 
    public int[][] voisinsLibres(int i, int j) {
        int res = 0;
        int l = 0;
        int[][] voisins = new int[4][2];
        while (l < cases.length) {
            if (sontVoisinsLibres(i, j, i, l)) {
                voisins[res][0] = i;
                voisins[res][1] = l;
                res++;
            }
            if (sontVoisinsLibres(i, j, l, j)) {
                voisins[res][0] = l;
                voisins[res][1] = j;
                res++;
            }
            l++;
        }
        while (res < 4) {
            voisins[res][0] = -2;
            voisins[res][1] = -2;
            res++;
        }
        return voisins;
    }
    
    /**
     * fonction sontVoisinsLibres : on peut encore tracer des ponts.
     * @param x0:coordonnée x d'une ile0
     * @param y0:coordonnée y d'une ile0
     * @param x1:coordonnée x d'une ile1
     * @param y1:coordonnée y d'une ile1
     * @return true si on peut encore tracer des ponts entre les deux iles.
     */
    public boolean sontVoisinsLibres(int x0, int y0, int x1, int y1) {
        int[] tab = position(x0, y0, x1, y1);
        x0 = tab[0];
        y0 = tab[1];
        x1 = tab[2];
        y1 = tab[3];
        if (getCase(x0, y0) == null || getCase(x1, y1) == null) {
            return false; // ile 1 ou ile 2 null
        }
        if (x0 == x1 && y0 == y1) {
            return false; // ile 1 = ile 2
        }
        if (getCase(x1, y1).getPR() <= 0 || getCase(x0, y0).getPR() <= 0) {
            return false; // pas de ponts restants
        }
        if (x0 != x1 && y0 != y1) {
            return false; // ni sur la meme ligne ni sur la meme colonne
        }
        if (x0 == x1) { // cas même ligne
            for (int i = y0 + 1; i < y1; i++) { // Parcours horizontal
                if (getCase(x0, i) != null && getCase(x0, i).getNb() > 0) {
                    return false; // autre ile fait obstacle
                }
                if (getCase(x0, i) != null && getCase(x0, i).getNb() == -1) {
                    if (!getCase(x0, i).getDirection().equals("- ")) {
                        return false; // croisement ou déja double pont.
                    }
                }
            }
        }
        if (y0 == y1) { // cas même colonne
            for (int i = x0 + 1; i < x1; i++) { // Parcours vertical
                if (getCase(i, y0) != null && getCase(i, y0).getNb() > 0) {
                    return false; // autre ile fait obstacle
                }
                if (getCase(i, y0) != null && getCase(i, y0).getNb() == -1) {
                    if (!getCase(i, y0).getDirection().equals("| ")) {
                        return false; // croisement ou déja double pont.
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * nBvoisinsLibre : nombre de voisins vers lesquels on peut encore tracer des ponts.
     * @param i:coordonnée x d'une ile
     * @param j:coordonnée y d'une ile
     * @return nombre de voisins que on peut encore tracer des ponts entre elles
     */
    public int nbVoisinsLibres(int i, int j) {
        int[][] voisins = voisinsLibres(i, j);
        int res = 0;
        for (int[] voisin : voisins) {
            if (voisin[0] >= 0) {
                res++;
            }
        }
        return res;
    }

    /**
     * voisinsAbsolus : voisins de l'ile (connectés ou libres).
     * Anciennement "voisins2", récemment rennommer.
     * @param i:coordonnée x d'une ile
     * @param j:coordonnée y d'une ile
     * @return un tableau de tableau int qui present les voisins sont lies ou
     * sont voisins
     * Tableau de la forme :
     * { {x1,y1}, {x2,y2} , {x3,y3} , {x4,y4} }
     * le tableau est toujours de taille 4 et les cases finales valent {-2,-2}
     * si il n'y a plus de voisins connectés ou libres.
     */
    
    public int[][] voisinsAbsolus(int i, int j) {
        int res = 0;
        int l = 0;
        int[][] voisins = new int[4][2];
        while (l < this.length()) {
            if (sontVoisinsAbsolus(i, j, i, l) && res < 4) {
                voisins[res][0] = i;
                voisins[res][1] = l;
                res++;
            }
            if (sontVoisinsAbsolus(i, j, l, j) && res < 4) {
                voisins[res][0] = l;
                voisins[res][1] = j;
                res++;
            }
            l++;
        }
        while (res < 4) {
            voisins[res][0] = -2;
            voisins[res][1] = -2;
            res++;
        }
        return voisins;
    }
    
    /**
     * fonction estvoisin2 : voisins (connectés ou libres).
     * @param x0:coordonnée x d'une ile0
     * @param y0:coordonnée y d'une ile0
     * @param x1:coordonnée x d'une ile1
     * @param y1:coordonnée y d'une ile1
     * @return un boolean qui verifier les deux iles sont connectés ou libres.
     */
    
    public boolean sontVoisinsAbsolus(int x0, int y0, int x1, int y1) {
        return (sontLies(x0, y0, x1, y1) || sontVoisinsLibres(x0, y0, x1, y1));
    }
    
    /**
     * fonction nBvoisns2 : voisins (connectés ou libres).
     * @param i:coordonnée x d'une ile
     * @param j:coordonnée y d'une ile
     * @return le nombre de voisins (connectés ou libres).
     */
    public int nbVoisinsAbsolus(int i, int j) {
        int[][] voisins = voisinsAbsolus(i, j);
        int res = 0;
        for (int[] voisin : voisins) {
            if (voisin[0] >= 0) {
                res++;
            }
        }
        return res;
    }
    
    /**
     * fonction voisins3 : voisins vraiment connectés avec l'ile.
     * @param i:coordonnée x d'une ile
     * @param j:coordonnée y d'une ile
     * @return les voisins qui sont vraiments connectées (par un ou deux ponts)
     */
    public int[][] voisins3(int i, int j) {
        int res = 0;
        int l = 0;
        int[][] voisins = new int[4][2];
        while (l < this.length()) {
            if (this.estVoisin3(i, j, i, l) && res < 4) {
                voisins[res][0] = i;
                voisins[res][1] = l;
                res++;
            }
            if (this.estVoisin3(i, j, l, j) && res < 4) {
                voisins[res][0] = l;
                voisins[res][1] = j;
                res++;
            }
            l++;
        }
        while (res < 4) {
            voisins[res][0] = -2;
            voisins[res][1] = -2;
            res++;
        }
        return voisins;
    }
    
    /**
     * fonction estvoisin3 : voisins vraiment connectés.
     * @param x0:coordonnée x d'une ile0
     * @param y0:coordonnée y d'une ile0
     * @param x1:coordonnée x d'une ile1
     * @param y1:coordonnée y d'une ile1
     * @return un boolean qui verifier les deux iles sont connectées.
     */
    public boolean estVoisin3(int x0, int y0, int x1, int y1) {
        return this.sontLies(x0, y0, x1, y1);
    }
    /**
     * fonction nBvoisins3 : voisins vraiment connectés avec l'ile.
     * @param i:coordonnée x d'une ile
     * @param j:coordonnée y d'une ile
     * @return le nombre de voisins3
     */
    public int nBvoisins3(int i, int j) {
        int[][] voisins = this.voisins3(i, j);
        int res = 0;
        for (int[] voisin : voisins) {
            if (voisin[0] >= 0) {
                res++;
            }
        }
        return res;
    }
    
    /**
     * Liste les coordonnées des voisins dans l'ordre N,S,E,O.
     * @param i:coordonnée x d'une ile0
     * @param j:coordonnée y d'une ile0
     * @return Une tableau de coordonnées des voisins. 
     * S'il n'y a pas de voisin dans la direction, alors on
     * marque {-2,-2}.
     */
    public int[][] voisinsOrdre(int i, int j) {
        int[][] iles = new int[5][2];
        int count = 0;
        for (int k = 0; k < 5; k++) {
            iles[k][0] = -2;
            iles[k][1] = -2;
        }

        if (getCase(i, j) != null && getCase(i, j).getNb() > 0) {
            for (int n = i - 1; n >= 0; n--) {
                if (getCase(n, j) != null && getCase(n, j).getNb() > 0) {
                    count++;
                    iles[0][0] = n;
                    iles[0][1] = j;
                    break;
                }

            }
            for (int s = i + 1; s < this.length(); s++) {
                if (getCase(s, j) != null && getCase(s, j).getNb() > 0) {
                    count++;
                    iles[1][0] = s;
                    iles[1][1] = j;
                    break;
                }

            }
            for (int e = j + 1; e < this.length(); e++) {
                if (getCase(i, e) != null && getCase(i, e).getNb() > 0) {
                    count++;
                    iles[2][0] = i;
                    iles[2][1] = e;
                    break;
                }

            }
            for (int o = j - 1; o >= 0; o--) {
                if (getCase(i, o) != null && getCase(i, o).getNb() > 0) {
                    count++;
                    iles[3][0] = i;
                    iles[3][1] = o;
                    break;
                }

            }
        }
        iles[4][0] = count;
        return iles;
    }
   
    
    // METHODES LIEES A LA GENERATION ALEATOIRE DU PLATEAU *************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * fonction detruireIle : Detruit une ile du plateau et renvoie le
     * tableau de case obtenu.
     * @param i:coordonnée x d'une ile
     * @param j:coordonnée y d'une ile
     * @return un tableau de cases apres avoir détruit l'ile (i,j)
     */
    public Case[][] detruireIle(int i, int j) {
        cases[i][j] = null;
        return cases;
    }
    
    /**
     * Détruit les iles isolées pour générer le plateau.
     */
    public void detruireIleIsolee() {
        for (int i = 0; i < length(); i++) {
            for (int j = 0; j < length(); j++) {
                if (getCase(i, j) != null && getCase(i, j).getNb() >= 0) {
                    if (getCase(i, j).getPonts() == 0) {
                        detruireIle(i, j);
                    }
                }

            }
        }
    }

    /**
     * Connecte les iles aleatoirement pour générer le plateau.
     */
    public void connecter() {
        boolean b = true;
        int x = 0;
        int y = 0;
        while (b) {
            Random r = new Random();
            x = r.nextInt(length());
            y = r.nextInt(length());
            if (getCase(x, y) != null && getCase(x, y).getNb() > 0) {
                b = false;
            }
        }
        connecterRecursif(x, y);
    }

    /**
     * Connexions aléatoirement et recursive entre les iles du plateau 
     * @param i:coordonnée x d'une ile
     * @param j:coordonnée y d'une ile
     */
    public void connecterRecursif(int i, int j) {
        int[][] voisins = this.voisinsLibres(i, j);
        int nBvoisins = this.nbVoisinsLibres(i, j);
        for (int n = 0; n < nBvoisins; n++) {
            construirePont(i, j, voisins[n][0], voisins[n][1]);
            construirePont(i, j, voisins[n][0], voisins[n][1]);
            if (sontLies(i, j, voisins[n][0], voisins[n][1])) {
                connecterRecursif(voisins[n][0], voisins[n][1]);
            }
        }
    }

    /**
     * Définit automatiquement le numéro des iles en fonction des ponts (pour la
     * génération).
     */
    public void definirNb() {
        for (int i = 0; i < length(); i++) {
            for (int j = 0; j < length(); j++) {
                if (getCase(i, j) != null && getCase(i, j).getNb() > 0) {
                    getCase(i, j).setNb(getCase(i, j).getPonts());
                }
            }
        }
    }
    /**
     * Détruit les ponts aleatoirement pour générer un plateau aléatoire.
     */
    public void detruirePontAleatoire() {
        for (int i = 0; i < length(); i++) {
            for (int j = 0; j < length(); j++) {
                if (getCase(i, j) != null && getCase(i, j).getNb() >= 0) {
                    for (int k = 0; k < 10; k++) {
                        if (nbVoisinsAbsolus(i, j) > 1) {
                            int[][] v = voisinsAbsolus(i, j);
                            Random ran = new Random();
                            int r = ran.nextInt(2);
                            int s = ran.nextInt(nbVoisinsAbsolus(i, j));
                            if (r == 0 && !(sontLies(i, j, v[s][0], v[s][1]) && !sontDoublementLiees(i, j, v[s][0], v[s][1]))) {
                                construirePont(i, j, v[s][0], v[s][1], true);
                                construirePont(i, j, v[s][0], v[s][1], true);
                            }
                        }
                    }
                }
            }
        }
        definirNb();
        detruireIleIsolee();
    }
    
    /**
     * Vérifie s'il y a des iles isolées 
     * (pas possible de former un chemin avec les autres iles).
     * @return true s'il y a des iles isolées.
     */
    public boolean partieIsolee() {
        Plateau copie = this.clone();
        boolean b = true;
        int x = 0;
        int y = 0;
        while (b) {
            Random r = new Random();
            x = r.nextInt(copie.length());
            y = r.nextInt(copie.length());
            if (copie.getCase(x, y) != null && copie.getCase(x, y).getNb() > 0) {
                b = false;
            }
        }
        copie.suprimerRecurcif(x, y);
        return !copie.estVide();
    }
    
    /**
     * fonction suprimerRecurcif : supprime les iles connectées entre elles
     * recurcivement, s'il y a des iles qui restent, alors il existe des
     * iles isolées. 
     * @param i: coordonnée x d'une ile
     * @param j: coordonnée y d'une ile
     */
    public void suprimerRecurcif(int i, int j) {
        int[][] voisins = this.voisins3(i, j);
        int nBvoisins = this.nBvoisins3(i, j);

        this.detruireIle(i, j);
        for (int n = 0; n < nBvoisins; n++) {
            this.suprimerRecurcif(voisins[n][0], voisins[n][1]);
        }
    }
    
    /**
     * Vérifie si le plateau est vide (arrive parfois lors de la génération
     * aléatoire).
     * @return true si le plateau est vide.
     */
    public boolean estVide() {
        for (int i = 0; i < this.length(); i++) {
            for (int j = 0; j < this.length(); j++) {
                if (this.getCase(i, j) != null && this.getCase(i, j).getNb() > 0) {
                    return false;

                }
            }
        }
        return true;
    }
    
    
    
    /**
     * Fusionne deux plateau: utile pour la génération
     * aléatoire lorsque le plateau a plusieurs solutions : 
     * on combine les plateaux pour que 
     * les ponts qui diffèrent soient toujours construits puis
     * on réadapte le numéro des iles.
     * @param p le plateau à combiner avec l'instance. 
     */
    public void combiner(Plateau p) {
        for (int i = 0; i < this.cases.length; i++) {
            for (int j = 0; j < this.cases.length; j++) {
                if (this.getCase(i, j) != null) {
                    if (this.getCase(i, j).getNb() < 0) {
                        int[][] iles = getIles(i, j);
                        int x0 = iles[0][0];
                        int y0 = iles[0][1];
                        int x1 = iles[1][0];
                        int y1 = iles[1][1];
                        if (p.getCase(i, j) != null) {
                            if (this.getCase(i, j).getEst() != p.getCase(i, j).getEst()
                                    || this.getCase(i, j).getSud() != p.getCase(i, j).getSud()
                                    || this.getCase(i, j).getOuest() != p.getCase(i, j).getOuest()
                                    || this.getCase(i, j).getNord() != p.getCase(i, j).getNord()) {
                                this.getCase(x0, y0).setNb(8);
                                this.getCase(x1, y1).setNb(8);
                                construirePont(x0, y0, x1, y1);
                                construirePont(x0, y0, x1, y1);
                            }
                        } else {
                            this.getCase(x0, y0).setNb(8);
                            this.getCase(x1, y1).setNb(8);
                            construirePont(x0, y0, x1, y1);
                            construirePont(x0, y0, x1, y1);
                        }
                    }
                }
            }
        }
        definirNb();
    }
    
    // METHODES LIES A LA RESOLUTION DU PLATEAU PAR LE SOLVEUR *********************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Un tableau de coordonnées {x,y} pour une ile qui n'a pas fait tous ses ponts
     * @return un tableau int pour Case qui n'a pas fait tous ses ponts
     */
    public int[] ilesCh() {
        int[] t = {-1, -1};
        for (int i = 0; i < length(); i++) {
            for (int j = 0; j < length(); j++) {
                if (getCase(i, j) != null && getCase(i, j).getPR() > 0) {
                    t[0] = i;
                    t[1] = j;
                    return t;
                }
            }
        }
        return t;
    }
    
    /**
     *  Construit les ponts Evidents pour résoudre le plateau.
     */
    public void construireLesPontsEvidents() {
        boolean retry = true;
        while (retry) {
            retry = false;
            /*
			 * A un moment donné, il n'y aura 
			 * plus aucun pont évident à 
			 * construire. On ne rentre jamais
			 * dans les "if" des "case", le booleen retry 
			 * reste à "false" -> sortie du while
             */
            for (int i = 0; i < length(); i++) {
                for (int j = 0; j < length(); j++) {
                    if (getCase(i, j) != null && getCase(i, j).getNb() > 0) {
                        int pr = getCase(i, j).getPR();
                        int[][] voisins = voisinsLibres(i, j);
                        int nBvoisins = nbVoisinsLibres(i, j);
                        switch (pr) { // Voir Annexe (Cahier des charges) pour comprendre les case
                            case 1:
                                if (nBvoisins == 1) {
                                    construirePont(i, j, voisins[0][0], voisins[0][1]);
                                    retry = true;
                                }
                                break;
                            case 2:
                                if (nBvoisins == 1) {
                                    construirePont(i, j, voisins[0][0], voisins[0][1]);
                                    construirePont(i, j, voisins[0][0], voisins[0][1]);
                                    retry = true;
                                }
                                break;
                            case 3:
                                if (nBvoisins == 2) {
                                    construirePont(i, j, voisins[0][0], voisins[0][1]);
                                    construirePont(i, j, voisins[1][0], voisins[1][1]);
                                    retry = true;
                                }
                                break;
                            case 4:
                                if (nBvoisins == 2) {
                                    construirePont(i, j, voisins[0][0], voisins[0][1]);
                                    construirePont(i, j, voisins[0][0], voisins[0][1]);
                                    construirePont(i, j, voisins[1][0], voisins[1][1]);
                                    construirePont(i, j, voisins[1][0], voisins[1][1]);
                                    retry = true;
                                }
                                break;
                            case 5:
                                if (nBvoisins == 3) {
                                    construirePont(i, j, voisins[0][0], voisins[0][1]);
                                    construirePont(i, j, voisins[1][0], voisins[1][1]);
                                    construirePont(i, j, voisins[2][0], voisins[2][1]);
                                    retry = true;
                                }
                                break;
                            case 6:
                                if (nBvoisins == 3) {
                                    construirePont(i, j, voisins[0][0], voisins[0][1]);
                                    construirePont(i, j, voisins[0][0], voisins[0][1]);
                                    construirePont(i, j, voisins[1][0], voisins[1][1]);
                                    construirePont(i, j, voisins[1][0], voisins[1][1]);
                                    construirePont(i, j, voisins[2][0], voisins[2][1]);
                                    construirePont(i, j, voisins[2][0], voisins[2][1]);
                                    retry = true;
                                }
                                break;
                            case 7:
                                if (nBvoisins == 4) {
                                    construirePont(i, j, voisins[0][0], voisins[0][1]);
                                    construirePont(i, j, voisins[1][0], voisins[1][1]);
                                    construirePont(i, j, voisins[2][0], voisins[2][1]);
                                    construirePont(i, j, voisins[3][0], voisins[3][1]);
                                    retry = true;
                                }
                                break;
                            case 8:
                                if (nBvoisins == 4) {
                                    construirePont(i, j, voisins[0][0], voisins[0][1]);
                                    construirePont(i, j, voisins[1][0], voisins[1][1]);
                                    construirePont(i, j, voisins[2][0], voisins[2][1]);
                                    construirePont(i, j, voisins[3][0], voisins[3][1]);
                                    construirePont(i, j, voisins[0][0], voisins[0][1]);
                                    construirePont(i, j, voisins[1][0], voisins[1][1]);
                                    construirePont(i, j, voisins[2][0], voisins[2][1]);
                                    construirePont(i, j, voisins[3][0], voisins[3][1]);
                                    retry = true;
                                }
                                break;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 
     * Vérifie si il y a une absurdité par rapport à la recherche de
     * d'une solution (qu'on ne connait pas encore).
     * @return true si Les iles ont fait tous leurs ponts 
     * qu'elles ne sont pas toutes connectées, ou, forcementIsolee(),
     * ou, croisementLocal(), ou, incoherence().
     */
    public boolean estAbsurde() {

        if (incoherence()) {
            return true;
        }
        int[] ileCh = ilesCh();
        if (ileCh[0] == -1 && !estFini()) {
            return true;
        }
        if (ileCh[0] != -1 && this.nbVoisinsLibres(ileCh[0], ileCh[1]) <= 0) {
            return true;
        }
        return forcementIsolee();
    }
    
    /**
     * Verifie si il y a une incoherence dans le plateau.
     * @return true si il y a un croisement local, ou, 
     * une ile a un nombre de pont plus grand que son numéro.
     */
    public boolean incoherence() {
        for (int i = 0; i < length(); i++) {
            for (int j = 0; j < length(); j++) {
                if (cases[i][j] != null && cases[i][j].getNb() > 0 && cases[i][j].getNb() < cases[i][j].getPonts()) {
                    return true;
                }
                if (croisementLocal(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Verifie si des ponts se croisent.
     * @param i: coordonnée x d'une ile
     * @param j : coordonnée y d'une ile
     * @return true si des ponts se croisent à la case i,j.
     */
    public boolean croisementLocal(int i, int j) {

        if (i < 0 || i > length() - 1 || j < 0 || j > length() - 1 || cases[i][j] == null) {
            return false;
        }
        Case ile = cases[i][j];

        Case iN = null;
        Case iS = null;
        Case iO = null;
        Case iE = null;

        if (i - 1 >= 0 && i - 1 <= length() - 1) {
            iN = cases[i - 1][j];
        }
        if (i + 1 >= 0 && i + 1 <= length() - 1) {
            iS = cases[i + 1][j];
        }
        if (j - 1 >= 0 && j - 1 <= length() - 1) {
            iO = cases[i][j - 1];
        }
        if (j + 1 >= 0 && j + 1 <= length() - 1) {
            iE = cases[i][j + 1];
        }

        if (ile.getDirection().equals("= ") || ile.getDirection().equals("- ")) {
            if (iN != null && (iN.getDirection().equals("$ ") || iN.getDirection().equals("| "))) {
                return true;
            }
            if (iS != null && (iS.getDirection().equals("$ ") || iS.getDirection().equals("| "))) {
                return true;
            }
        }

        if (ile.getDirection().equals("| ") || ile.getDirection().equals("$ ")) {
            if (iO != null && (iO.getDirection().equals("= ") || iO.getDirection().equals("- "))) {
                return true;
            }
            if (iE != null && (iE.getDirection().equals("= ") || iE.getDirection().equals("- "))) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * Verifie si l'état du plateau implique forcément que des iles
     * seront isolées des autres.
     * @return  true si une ile n'a pas fait tous ses ponts alors 
     * qu'elle n'a plus de voisins libres, ou, un 1 est connecté avec un 1,
     * ou,un 2 fait un double pont avec un 2.
     */
    public boolean forcementIsolee() {
        for (int i = 0; i < length(); i++) {
            for (int j = 0; j < length(); j++) {
                if (cases[i][j] != null && cases[i][j].getNb() > 0) {

                    if (this.nbVoisinsLibres(i, j) == 0 && this.getCase(i, j).getPR() > 0) {
                        return true;
                    }

                    if (cases[i][j].getNb() == 1) {
                        int[][] voisins = this.voisinsAbsolus(i, j);
                        for (int[] voisin : voisins) {
                            if (voisin[0] != -2) {
                                if (this.getCase(voisin[0], voisin[1]).getNb() == 1) {
                                    if (this.sontLies(i, j, voisin[0], voisin[1])) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    if (cases[i][j].getNb() == 2) {
                        int[][] voisins = this.voisinsAbsolus(i, j);
                        for (int[] voisin : voisins) {
                            if (voisin[0] != -2) {
                                if (this.getCase(voisin[0], voisin[1]).getNb() == 2) {
                                    if (this.sontDoublementLiees(i, j, voisin[0], voisin[1])) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Verifie si le plateau est dans la liste donnée en paramètre.
     * @param p1: une liste de plateau
     * @return true si le plateau est dans la liste.
     */
    public boolean isIn(ArrayList<Plateau> p1) {
        for (int i = 0; i < p1.size(); i++) {
            if (p1.get(i).equals(this)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Coordonnées de l'ile en test.
     * @return tableau de coordonnées
     */
    public int[] ilesChNotOnTest() {
        int[] t = {-1, -1};
        for (int i = 0; i < length(); i++) {
            for (int j = 0; j < length(); j++) {
                if (getCase(i, j) != null && getCase(i, j).getPR() > 0 && !getCase(i, j).isOnTest()) {
                    t[0] = i;
                    t[1] = j;
                    return t;
                }
            }
        }
        return t;
    }
    
    /**
     * Résoud la plateau grace au solveur.
     * @return  un boolean si on a reussi de resoudre le plateau sans le plateau solution,
     * et que la solution est unique.
     */
    public boolean resoudreSansSol() {
        Solveur s = new Solveur();
        s.solveHashiTotal(this);
        this.solutions = s.getSolutions();
        if (solutions.size() == 1) {
            this.cases = solutions.get(0).cases;
            return true;
        } 
        return false;
    }
}
