/*
*
* Nom de la classe : Case 
* 
* Description : 
*   Définit les cases non vides du plateau, autrement dit, les iles et les ponts.
*
* Version : 1.0
*
* Date : Mai 2019
*
* Auteur : PI4 / HASHIWOKAKERO1
*
*/
package javamvc.modele;

public class Case {
    
    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    private int nb;
    private int nord;
    private int sud;
    private int est;
    private int ouest;
    private boolean onTest = false;

    // CONSTRUCTEURS ***************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Constructeur de case.
     *
     * @param val une case de val -1 est traversée par un pont et une case de
     * val N (de 1 à 8) est une ile portant le numéro N.
     */
    public Case(int val) {
        this(val, 0, 0, 0, 0);
    }

    /**
     * Constructeur de case.
     *
     * @param val une case de val -1 est traversée par un pont et une case de
     * val N (de 1 à 8) est une ile portant le numéro N.
     * @param n nombre de ponts au nord.
     * @param s nombre de ponts au sud.
     * @param e nombre de ponts à l'est.
     * @param o nombre de ponts à l'ouest.
     */
    public Case(int val, int n, int s, int e, int o) {
        if (val < -1 || val > 8 || val == 0 || n < 0 || n > 2 || s < 0 || s > 2 || e < 0 || e > 2 || o < 0 || o > 2) {
            return;
        }
        this.nb = val;
        this.nord = n;
        this.sud = s;
        this.est = e;
        this.ouest = o;
    }

    /**
     * Constructeur de case. Créer une nouvelle case, équivalente à celle donnée
     * en paramètre
     *
     * @param i Case à cloner
     */
    public Case(Case i) {
        this.nb = i.getNb();
        this.nord = i.getNord();
        this.sud = i.getSud();
        this.est = i.getEst();
        this.ouest = i.getOuest();
    }

    // ACCESSSEURS *****************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    /**
     * Accesseur. Valeur de la case.
     *
     * @return int correspondant à la valeur de la case. Vaut -1 si traversée
     * par un pont. Vaut le numero de l'ile (1 à 8) sinon.
     */
    public int getNb() {
        return this.nb;
    }

    /**
     * Accesseur. Nombre de ponts au nord.
     *
     * @return int correspondant au nombre de ponts au nord.
     */
    public int getNord() {
        return this.nord;
    }

    /**
     * Accesseur. Nombre de ponts au sud.
     *
     * @return int correspondant au nombre de ponts au sud.
     */
    public int getSud() {
        return this.sud;
    }

    /**
     * Accesseur. Nombre de ponts à l'est.
     *
     * @return int correspondant au nombre de ponts à l'est.
     */
    public int getEst() {
        return this.est;
    }

    /**
     * Accesseur. Nombre de ponts à l'ouest.
     *
     * @return int correspondant au nombre de ponts à l'ouest.
     */
    public int getOuest() {
        return this.ouest;
    }

    /**
     * Nombre de ponts restant à construire pour l'ile considérée.
     *
     * @return int correspondant au numéro de l'ile - nombre de ponts. -1 si la
     * case est traversé par un pont.
     */
    public int getPR() {
        if (this.nb == -1) {
            return -1;
        }
        return this.nb - (this.nord + this.sud + this.ouest + this.est);
    }

    /**
     * Nombre de ponts total.
     *
     * @return int correspondant à la somme des nombres de ponts aux 4
     * directions
     */
    public int getPonts() {
        return this.getEst() + this.getOuest() + this.getNord() + this.getSud();
    }

    /**
     * Direction du pont qui traverse la case considérée.
     *
     * @return String symbolisant la direction du pont : Si la case est une ile,
     * elle renvoie la chaîne "X " par défaut.
     */
    public String getDirection() {
        if (this.nb == -1) {
            if (this.nord == 1 && this.sud == 1) {
                return "| ";
            }
            if (this.nord == 2 && this.sud == 2) {
                return "$ ";
            }
            if (this.ouest == 1 && this.est == 1) {
                return "- ";
            }
            if (this.ouest == 2 && this.est == 2) {
                return "= ";
            }
        }
        return "X ";
    }

    /**
     * Accesseur. Attribut necéssaire au solveur. Vérifie si l'instance la
     * configuration de l'instance est un test.
     *
     * @return true si la case est en phase de test.
     */
    public boolean isOnTest() {
        return onTest;
    }

    // MUTATEURS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Mutateur. Modifie le nombre de pont au nord
     *
     * @param n nombre de pont au nord
     */
    public void setNord(int n) {
        if (n < 0 || n > 2) {
            return;
        }
        this.nord = n;
    }

    /**
     * Mutateur. Modifie le nombre de pont au sud
     *
     * @param s nombre de pont au sud
     */
    public void setSud(int s) {
        if (s < 0 || s > 2) {
            return;
        }
        this.sud = s;
    }

    /**
     * Mutateur. Modifie le nombre de pont à l'est.
     *
     * @param e nombre de pont à l'est.
     */
    public void setEst(int e) {
        if (e < 0 || e > 2) {
            return;
        }
        this.est = e;
    }

    /**
     * Mutateur. Modifie le nombre de pont à l'ouest.
     *
     * @param o nombre de pont à l'ouest.
     */
    public void setOuest(int o) {
        if (o < 0 || o > 2) {
            return;
        }
        this.ouest = o;
    }

    /**
     * Mutateur. Modifie la valeur de la case
     *
     * @param nb valeur de la case
     */
    public void setNb(int nb) {
        if (nb < -1 || nb == 0 || nb > 8) {
            return;
        }
        this.nb = nb;
    }

    /**
     * Mutateur. Pour le solveur. Indique que la case est en test.
     */
    public void setOnTest() {
        this.onTest = true;
    }

    /**
     * Mutateur. Pour le solveur. Indique si la case est en test ou non
     *
     * @param b true si la case est en test.
     */
    public void setTest(boolean b) {
        this.onTest = b;
    }

    // METHODES ********************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Verifie si la case est equivalente à une autre.
     *
     * @param il Case à comparer avec l'instance
     * @return true si elle sont équivalentes. (même numero, même nombre de
     * ponts dans chaque direction)
     */
    public boolean equals(Case il) {
        return (this.nb == il.nb && this.nb == il.getNb() && this.nord == il.getNord() && this.sud == il.getSud() && this.est == il.getEst() && this.ouest == il.getOuest());
    }

    /**
     * Représentation textuelle de l'instance
     *
     * @return String de la forme : Valeur de la case - Nord - Sud - Est - Ouest
     */
    @Override
    public String toString() {
        return "nb " + nb + " h " + nord + " b " + sud + " d " + est + " g " + ouest;
    }

    /**
     * Rajoute un pont au nord. Remet à zéro si il y en a déjà deux.
     */
    public void incNord() {
        if (this.nord < 2) {
            this.nord++;
        } else {
            this.nord = 0;
        }
    }

    /**
     * Rajoute un pont au sud. Remet à zéro si il y en a déjà deux.
     */
    public void incSud() {
        if (this.sud < 2) {
            this.sud++;
        } else {
            this.sud = 0;
        }
    }

    /**
     * Rajoute un pont à l'est. Remet à zéro si il y en a déjà deux.
     */
    public void incEst() {
        if (this.est < 2) {
            this.est++;
        } else {
            this.est = 0;
        }
    }

    /**
     * Rajoute un pont à l'ouest. Remet à zéro si il y en a déjà deux.
     */
    public void incOuest() {
        if (this.ouest < 2) {
            this.ouest++;
        } else {
            this.ouest = 0;
        }
    }

    /**
     * Supprime tous les ponts aux 4 directions.
     */
    public void nettoyage() {
        this.setNord(0);
        this.setSud(0);
        this.setEst(0);
        this.setOuest(0);
    }

}
