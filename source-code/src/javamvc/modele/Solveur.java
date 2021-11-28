/*
*
* Nom de la classe : Solveur 
* 
* Description : 
*   Solveur et generateur de plateau aléatoire à solution unique. 
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


public class Solveur {

    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    private final ArrayList<Plateau> solutions = new ArrayList<>();
    private final ArrayList<Plateau> dejaVu = new ArrayList<>();
    private int backtrack = 0;
        
    // ACCESSEURS ******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * ACCESSEUR.
     * Liste des solutions trouvés.
     * @return liste des solutions
     */
    protected ArrayList<Plateau> getSolutions(){ return solutions; }

    // METHODES ********************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Verifie si la config de l'ile donnée en parametre est safe pour
     * la résolution.
     * @param p Plateau
     * @param x0 Coordonnée x de l'île à tester
     * @param y0 Coordonnée y de l'île à tester
     * @param a nombre de pont à tester au nord
     * @param b nombre de pont à tester au sud
     * @param c nombre de pont à tester à l'est
     * @param d nombre de pont à tester à l'ouest
     * @return true si la config est safe
     */
    private static boolean isSafe(Plateau p, int x0, int y0, int a ,int b,int c,int d) {
        Plateau plateau = p.clone();
        boolean res = chooseConfig(plateau, x0, y0, a,b,c,d);
        return res && !plateau.estAbsurde();
    }

    /**
     * Teste la config donnée en parametre.
     * @param plateau Plateau
     * @param x0 Coordonnée x de l'île à tester
     * @param y0 Coordonnée y de l'île à tester
     * @param a nombre de pont à tester au nord
     * @param b nombre de pont à tester au sud
     * @param c nombre de pont à tester à l'est
     * @param d nombre de pont à tester à l'ouest
     * @return true si la config est testée.
     */
    private static boolean chooseConfig(Plateau plateau, int x0, int y0, int a, int b, int c, int d) {
        int con = (int) (a * Math.pow(3, 3) + b * Math.pow(3, 3) + c * Math.pow(3, 3) + d * Math.pow(3, 3));
        if (con < 0 || con > 81) {
            return false;
        }
        int[][] voisins = plateau.voisinsOrdre(x0, y0);
        int x1 = voisins[0][0];
        int y1 = voisins[0][1];
        int x2 = voisins[1][0];
        int y2 = voisins[1][1];
        int x3 = voisins[2][0];
        int y3 = voisins[2][1];
        int x4 = voisins[3][0];
        int y4 = voisins[3][1];
        plateau.detruireTousLesPonts(x0, y0);
        if (con == 0) {
            return true;
        }
        for (int n = 0; n < a; n++) {
            plateau.construirePont(x0, y0, x1, y1);
        }
        for (int s = 0; s < b; s++) {
            plateau.construirePont(x0, y0, x2, y2);
        }
        for (int e = 0; e < c; e++) {
            plateau.construirePont(x0, y0, x3, y3);
        }
        for (int o = 0; o < d; o++) {
            plateau.construirePont(x0, y0, x4, y4);
        }
        return true;
    }

    /**
     * Sauvegarde la solution donnée en parametre dans la liste de 
     * solution (si elle n'est pas déjà présente).
     * @param c Plateau
     */
    private void saveSol(Plateau c) {
        for (int i = 0; i < this.solutions.size(); i++) {
            if (this.solutions.get(i).equals(c)) {
                return;
            }
        }
        this.solutions.add(c); 
    }

    /**
     * Retourne la liste des iles qui n'ont pas fait tous leur ponts
     * 
     * @param p1 Plateau.
     * @return 
     */
    private ArrayList<int[]> ilesCh(Plateau p1) {
        ArrayList<int[]> ilesCh = new ArrayList<>();
        for (int i = 0; i < p1.length(); i++) {
            for (int j = 0; j < p1.length(); j++) {
                if (p1.getCase(i, j) != null && p1.getCase(i, j).getNb() > 0 && !(p1.nbVoisinsLibres(i, j) == 0)) {
                    int[] t = new int[2];
                    t[0] = i;
                    t[1] = j;
                    ilesCh.add(t);
                }
            }
        }
        return ilesCh;
    }
    
    /** 
     * Algorithme de Backtracking pour l'IA de résolution. 
     * 
     * @param p0 Plateau
     * @return true quand on stoppe l'algorithme volontairement, false si on veut continuer jusqu'au bout.
     */
    private boolean solveHashi(final Plateau p0) {
        
        backtrack++;
        
        /* Si trop de backtrack on annule tout et on met la liste des solutions à 0 */
        /* En renvoie true : STOP -> trop long à résoudre */
        if (backtrack>6000){
            for (int i=0; i<this.solutions.size(); i++){
                this.solutions.remove(i);
            }
            return true;      
        }
        
        /* On construit les ponts évidents */
        p0.construireLesPontsEvidents();
        
        /* Si cette config de plateau a déjà été vue */
        /* on retourne false -> BACKTRACK */
        if (p0.isIn(dejaVu)){
            return false;
        }
         
        // A CE STADE CETTE CONFIG DE PLATEAU N'A JAMAIS ETE TESTEE
        
        /* Si cette config de plateau est absurde */
        /* on l'ajoute dans les configs qu'on a testées */
        /* on retourne false -> BACKTRACK */
        if (p0.estAbsurde()){
            dejaVu.add(p0.clone());
            return false;     
        }
        
        /* Si cette config de plateau est celle du plateau résolu */
        /* on l'ajoute dans les configs qu'on a testées */
        /* et on l'ajoute dans la liste des solutions */
        /* Si le nombre de solutions vaut maintenant 2 */
        /* on retourne true -> STOP : 2 solutions trouvées */
        /* Sinon (si le nombre de solution vaut maintenant 1) */
        /* on retourne false -> BACKTRACK pour trouver une autre solution */
        /* Si on en trouve pas, la solution est unique */
        if (p0.estFini()) {
            dejaVu.add(p0.clone());
            this.solutions.add(p0.clone());
            return this.solutions.size() == 2;
        }
        
        /* on cherche les coordonnées de l'ile qui n'a pas fait tous ses ponts */
        /* et qui n'est pas dans une config "test" en ce moment */
        int[] coor = p0.ilesChNotOnTest();     
        int x0 = coor[0];
        int y0 = coor[1];
        
        if (coor[0] != -1) {
            
            /* 
            *   On prends ses iles voisines 
            *   On stocke, pour chaque voisin, le minimum entre : 
            *    _ les ponts restants de l'ile  
            *    _ les ponts restants du voisin 
            */

            int[][] voisins = p0.voisinsOrdre(coor[0], coor[1]);           
            int [] minPontsRestants = new int [4];
            int pontsRestantsIle = p0.getCase(coor[0],coor[1]).getPR();
            for (int i = 0; i < 4; i++) {
                if (voisins[i][0] >= 0 && voisins[i][1] >= 0) {
                    int pontsRestantsVoisin = p0.getCase(voisins[i][0], voisins[i][1]).getPR();
                    minPontsRestants[i] = Math.min(pontsRestantsVoisin, pontsRestantsIle);
                } else {
                    minPontsRestants[i] = 0;
                }
            }
            
           /*
            *   On teste ne teste plus toutes les 81 configs possible de l'ile 
            *   [ 
            *       Version 1 de l'algo : 
            *            on testait les configs, même si c'était absurde,
            *            il fallait arriver à ce tour de boucle pour s'en 
            *            rendre compte (même si on en sortait immédiatement).
            *   ] 
            *   Version actuelle : on connait à l'avance le nombre exact de tours de boucle à faire
            *   grâce au tableau minPontsRestants[].
            *   Pour chaque voisin, on teste tous les ponts possibles en s'arrêtant au minimum
            *   du nombre de ponts restant entre l'ile et le voisin. 
            * 
            */
           
            /*
                On indique que l'ile est en test (on y touchera pas dans un autre backtrack).
                On garde une ancienne save du plateau. 
                
                On teste une config "test" pour l'ile (on construit un pont)
                
                Reccursion.
                On appelle appelle l'algo sur le plateau "test".
                Si l'appel de l'algo renvoie true, on renvoie true.
                (Donc au premier retour de true :
                    true -> true -> ... -> puis on sort de tous les algo imbriquées)
                
                Si l'appel de l'algo renvoie false.
                On ajoute le plateau "test" dans la liste des plateaux testés.
                On remet le plateau à son ancienne save.
            */
            for (int a = 0; a < minPontsRestants[0] + 1; a++) {
                for (int b = 0; b < minPontsRestants[1] + 1; b++) {
                    for (int c = 0; c < minPontsRestants[2] + 1; c++) {
                        for (int d = 0; d < minPontsRestants[3] + 1; d++) {
                            p0.getCase(x0, y0).setOnTest();
                            Plateau sauvegarde = p0.clone(); //on fait les copies des plateaux pour pouvoir reinitialiser en cas d'erreur
                            chooseConfig(p0,x0,y0,a,b,c,d);  // on construit un pont (test) 
                            if (solveHashi(p0)) {
                                return true;
                            }
                            dejaVu.add(p0.clone());
                            p0.setCases(sauvegarde.getCases());                            
                        }
                    }
                }
            }
        }
        
        /* Toutes les configs des appels imbriqués dans celle-ci ont été testées sans renvoyer true */
        /* on sort de cet appel là en renvoyant false, pour indiquer aux appels au-dessus qu'il faut 
        /* continuer les tests. */
        
        /* Mais si cet appel est le dernier, alors on n'a fini tous les tests possibles 
            (sinon on aurait renvoyer true si on avait dépassé le seuil de "trop de backtrack")  
           sans trouver deux solutions différentes (sinon on aurait renvoyé true au bout de deux
           solutions. 
        */
        return false;
    }

    /**
     * Applique l'algorithme de resolution sur le plateau donné en paramètre.
     * @param p Plateau.
     */
    public void solveHashiTotal(Plateau p) {
        backtrack = 0;
        solveHashi(p);
    }
    
    /**
     * Génère aléatoirement un plateau.
     * @param n taille du plateau à générer
     * @return Plateau généré aléatoirement.
     */
    public static Plateau genererPlateau(int n) {

        Plateau sol = new Plateau(n);

        while (sol.getSolutions().size() != 1) {            
            sol = new Plateau(n);

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    Random ran = new Random();
                    int il = ran.nextInt(n);
                    sol.getCases()[i][il] = new Case(8);
                    boolean b = (i + 1 < n && sol.getCase(i + 1, il) == null) && (i - 1 > 0 && sol.getCase(i - 1, il) == null) && (il + 1 < n && sol.getCase(i, il + 1) == null) && (il - 1 > 0 && sol.getCase(i, il - 1) == null);
                    b = b || ((i == n - 1) && (i - 1 > 0 && sol.getCase(i - 1, il) == null) && (il + 1 < n && sol.getCase(i, il + 1) == null) && (il - 1 > 0 && sol.getCase(i, il - 1) == null));
                    b = b || ((i == 0) && (i + 1 < n && sol.getCase(i + 1, il) == null) && (il + 1 < n && sol.getCase(i, il + 1) == null) && (il - 1 > 0 && sol.getCase(i, il - 1) == null));
                    b = b || ((il == 0) && (i + 1 < n && sol.getCase(i + 1, il) == null) && (i - 1 > 0 && sol.getCase(i - 1, il) == null) && (il + 1 < n && sol.getCase(i, il + 1) == null));
                    b = b || ((il == n - 1) && (i + 1 < n && sol.getCase(i + 1, il) == null) && (i - 1 > 0 && sol.getCase(i - 1, il) == null) && (il - 1 > 0 && sol.getCase(i, il - 1) == null));
                    if (!b) {
                        sol.detruireIle(i, il);
                    }
                }
            }

            sol.connecter();
            sol.detruireIleIsolee();
            sol.detruirePontAleatoire();
            sol = sol.cloneSansPonts();

            if (!sol.estVide()) {

                sol.resoudreSansSol();
                
                if (sol.getSolutions().size() > 1 ){
                    
                    for (int i = 0; i < 3; i++) {                        
                        sol.getSolutions().get(0).combiner(sol.getSolutions().get(1));
                        sol = sol.getSolutions().get(0);
                        sol = sol.cloneSansPonts();
                        sol.resoudreSansSol();
                        if (sol.getSolutions().size() < 2 ) {
                            break;
                        }
                    }
                }
            }
        }
        return sol.getSolutions().get(0);
    }
}
