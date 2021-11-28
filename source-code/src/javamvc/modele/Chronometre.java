/*
*
* Nom de la classe : Chronometre 
* 
* Description : 
*   Chronometre du jeu en cours de partie.
*
* Version : 1.0
*
* Date : Mai 2019
*
* Auteur : PI4 / HASHIWOKAKERO1
*
*/

package javamvc.modele;


import javamvc.controleur.ControleurHashi;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;

public class Chronometre {

    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    private int secondes;
    private int minutes;
    private final Timer timer;
    private final TimerTask task;
    private final ControleurHashi controleur;
    private boolean ecoule = false;
    private boolean stopped = true;

    // CONSTRUCTEURS ***************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * constructeur de Chronometre
     *
     * @param controleur un ControleurHashi
     * @param reverse un boolean , false-chronometre croissant, true chronomètre descroissant.
     * @param min : minnutes de départ dans le cas décroissant.
     * @param sec: secondes de départ dans le cas décroissant.
     *
     */
    public Chronometre(ControleurHashi controleur, boolean reverse, int min, int sec) {
        
        if (!reverse){
            minutes = 0;
            secondes = 0;
        }
        else {
            minutes = min;
            secondes = sec;
        }
        this.controleur = controleur;
        this.timer = new Timer();
        this.task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (!reverse) {
                        incSecondes();
                    } else {
                        decSecondes();
                    }
                    if (controleur!=null) controleur.actualiseChrono();
                });
            }
        };
    }

    /**
     * Constructeur de Chronometre.
     *
     * @param controleur un ControleurHashi
     * @param reverse un boolean , false--chronometre croissant ,
     * true--chronomatre descroissant.
     * Par défaut : minute de départ dans le cas décroissant vaut 1 
     *
     */
    public Chronometre(ControleurHashi controleur, boolean reverse){
        this(controleur,reverse,1,0);
    }
    
    /**
     * constructeur de Chronometre
     *
     * @param controleur un ControleurHashi
     * Par défaut : minute de départ dans le cas décroissant vaut 1 
     */
    public Chronometre(ControleurHashi controleur){
        this(controleur,false,1,0);
    }
    
    // ACCESSEURS ******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************

    /**
     * Accesseurs
     * @return minutes du chronomètre
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Accesseurs
     * @return secondes du chronomètre
     */
    public int getSecondes() {
        return secondes;
    }

    /**
     * Accesseurs
     * @return true si le temps est ecoule (concerne les chronomètres décroissants)
     */
    public boolean isEcoule() {
        return ecoule;
    }

    /**
     * Accesseurs.
     * @return true si le chronomètre est stoppé.
     */
    public boolean isStop() {
        return stopped;
    }

    // METHODES ********************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
    * Démarre le chronomètre.
    */
    public void start() {
        stopped = false;
        timer.scheduleAtFixedRate(task, 0, 1000);
    }
    /**
    * Arrètre le chronomètre.
    */
    public void stop() {
        timer.cancel();
        stopped = true;
    }
    /**
     * Réinitialise le chronomètre.
     */
    public void reinitialiser() {
        this.secondes = 0;
        this.minutes = 0;
    }
    
    
    /**
     * @return un String qui représente le chronomètre
     */
    @Override
    public String toString() {
        if ((minutes == 0 && secondes == 0) || (minutes < 0)) {
            return String.format("%02d", 0) + ":" + String.format("%02d", 0);
        }
        return String.format("%02d", minutes) + ":" + String.format("%02d", secondes);
    }
    
    /**
     * incrémente le temps du chronomètre
     */
    public void incSecondes() {
        if (secondes == 59) {
            secondes = 0;
            minutes++;
        } else {
            secondes++;
        }
    }
    
    /**
     * Décrémente le temps du chronomètre
     */
    public void decSecondes() {
        if ((minutes == 0 && secondes == 0) || (minutes < 0)) {
            stop();
            this.ecoule = true;
        } else {
            if (secondes == 0) {
                secondes = 59;
                minutes--;
            } else {
                secondes--;
            }
        }
    }
    
    /**
     * Rajoute 5 minutes ou enlève 5 minutes du chronomètre.
     *
     * @param b : true-- ajoute 5 minutes, false-- réduit de 5 minutres
     */
    public void penalite(boolean b) {
        if (b) {
            minutes += 5;
        } else {
            minutes -= 5;
        }
    }

    
}
