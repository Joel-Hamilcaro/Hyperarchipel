/*
*
* Nom de la classe : Parametre 
* 
* Description : 
*   Enregistrement et modification des paramètres du jeu.
*   Stocke les chemins d'accès vers les ressources externe (images, sons ...)
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

public abstract class Parametre {

    // PARAMETRES STATIQUES ***********************************************************
    
    private static boolean audioMute = false;
    private static boolean pleinEcran = false;
    private static boolean isLow = false;
    private static boolean doubleClic = true;
    private static boolean anime = true;
    private static int hauteurFenetre = 720;
    private static int largeurFenetre = 720;
    
    // CONSTANTES ET RESSOURCES ******************************************************
    
    /**
     * Constantes du menu pause
     */
    public static final int POSITION_X_MENU_PAUSE_OUVERT = 0;
    /**
     * Constantes du menu pause
     */
    public static final int POSITION_X_MENU_PAUSE_FERME = -1000;
    
    /**
    * Liste des niveaux
    */
    public static final String FILE_NIVEAU = "/res/txt/ListeNiveaux.txt"; 
    
    /**
    * Fichier de sauvegarde des scores
    */
    public static final String FILE_SCORE = "save/Score.bin";
    
    /**
    * Fichier de sauvegarde des parametres
    */
    public static final String FILE_PARAM = "save/Settings.bin";
    
    /**
    * Archipel du menu principal
    */
    public static   String FILE_WALLPAPER = "/res/img/archipel.png";
    
    /**
     * Fond d'écran titre
     */
    public static   String FILE_WALLPAPER0 = "/res/img/wallpaper0.jpg";
    
    /**
     * Fond d'écran narration
     */
    public static   String FILE_WALLPAPER1 = "/res/img/wallpaper1.png";
    
    /**
     * Fond d'écran chargement
     */
    public static   String FILE_WALLPAPER2 = "/res/img/wallpaper2.png";
    
    /**
     * Fond d'écran flashback
     */
    public static   String FILE_WALLPAPER3 = "/res/img/wallpaper3.jpg";
    
    /**
     * Fond vagues animée
     */
    public static   String FILE_WAVE = "/res/img/VAGUES-V2.gif";
    
    /**
     * Image d'ile
     */
    public static   String FILE_IMAGEILE1 = "/res/img/ile1.png";
    
    /**
     * Image d'ile
     */
    public static   String FILE_IMAGEILE2 = "/res/img/ile2.png";
    
    /**
     * Image d'ile
     */
    public static   String FILE_IMAGEILE3 = "/res/img/ile3.png";
    
    /**
     * image du Baio
     */
    public static   String FILE_BAIO = "/res/img/baio.png";
    
    /**
     * Musiques et fonds sonores
     */
    public static final String FILE_MUSIC_0 = "/res/msc/mscIntro.wav";
    
    /**
     * Musiques et fonds sonores
     */
    public static final String FILE_MUSIC_1 = "/res/msc/mscBoss.wav";
    
    /**
     * Musiques et fonds sonores
     */
    public static final String FILE_MUSIC_2 = "/res/msc/mscVagues.wav";
    
    /**
     * Musiques et fonds sonores
     */
    public static final String FILE_MUSIC_3 = "/res/msc/mscCine.wav";
    
    /**
     * Musiques et fonds sonores
     */
    public static final String FILE_MUSIC_4 = "/res/msc/mscMenu.wav";
    
    /**
     * Icones
     */ 
    public static   String FILE_IMAGEMENUA = "/res/img/menu.png";
    
    /**
     * Icones
     */
    public static   String ICON_ABOUT = "/res/img/about.png";
    
    /**
     * Icones
     */
    public static   String ICON_ALEA = "/res/img/alea.png";
    
    /**
     * Icones
     */
    public static   String ICON_BACK = "/res/img/back.png";
    
    /**
     * Icones
     */
    public static   String ICON_NEXT = "/res/img/next.png";
    
    /**
     * Icones
     */
    public static   String ICON_SON = "/res/img/son1.png";
    
    /**
     * Icones
     */
    public static   String ICON_MUTE = "/res/img/mute.png";
    
    /**
     * Icones
     */
    public static   String ICON_ANIM = "/res/img/vent.png";
    
    /**
     * Icones
     */
    public static   String ICON_DICE = "/res/img/dice.png";
    
    /**
     * Icones
     */
    public static   String ICON_JOKER = "/res/img/joker.png";
    
    /**
     * Icones
     */
    public static   String ICON_MAIN = "/res/img/main.png";
    
    /**
     * Icones
     */
    public static   String ICON_RESTART = "/res/img/restart.png";
    
    /**
     * Icones
     */
    public static   String ICON_SETTINGS = "/res/img/settings.png";
    
    /**
     * Icones
     */
    public static   String ICON_STORY = "/res/img/story.png";
    
    /**
     * Icones
     */
    public static   String ICON_SURVOL = "/res/img/survol.png";
    
    /**
     * Icones
     */
    public static   String ICON_SURVOL2 = "/res/img/mouse.png";
    
    /**
     * Icones
     */
    public static   String ICON_ANIM2 = "/res/img/nuage.png";
    
    /**
     * Icones
     */
    public static   String ICON_TUTO = "/res/img/tuto.png";
    
    /**
     * Icones
     */
    public static   String ICON_STAIRS = "/res/img/stairs.png";
    
    /**
     * Icones
     */
    public static   String ICON_EXIT = "/res/img/exit.png";
    
    /**
     * Icones
     */
    public static   String ICON_LEVEL = "/res/img/level.png";
    
    /**
     * Icones
     */
    public static   String ICON_J1 = "/res/img/j1.png";
    
    /**
     * Icones
     */
    public static   String ICON_J2 = "/res/img/j2.png";
    
    /**
     * Icones
     */
    public static   String ICON_J3 = "/res/img/j3.png";
    
    /**
     * Icones
     */
    public static   String ICON_OUI = "/res/img/oui.png";
    
    /**
     * Icones
     */
    public static   String ICON_NON = "/res/img/non.png";
     
    // METHODES *******************************************************************************
    
    /**
     * Active et desactive le mode muet (sans sons).
     * @param b true pour activer le mode muet.
     */
    public static void setAudioMute(boolean b) {
        audioMute = b;
        writeSettings(b,0);
    }
    
    /**
     * Active et desactive le mode muet (sans sons).
     * @param b true pour activer le mode muet.
     */
    public static void setPleinEcran(boolean b) {
        pleinEcran = b;
        writeSettings(b,4);
    }
    
    /**
     * Active le clic de confirmation (désactive le survol).
     * @param b true pour activer le clic de confirmation.
     */
    public static void setDoubleClic(boolean b) {
        doubleClic = b;
        writeSettings(b,2);
    }
    
    /**
     * Active l'animation des vagues pendant le jeu.
     * @param b true pour activer l'animation.
     */
    @SuppressWarnings("null")
    public static void setAnime(boolean b) {
        anime = b;
        writeSettings(b,1);
    }
    
    public static void setLow(boolean b){
        Parametre.isLow = b;
        writeSettings(b,3);
        if (b){
            FILE_WALLPAPER = "/res/imgLow/archipel.png";
            FILE_WALLPAPER0 = "/res/imgLow/wallpaper0.jpg";
            FILE_WALLPAPER1 = "/res/imgLow/wallpaper1.png";
            FILE_WALLPAPER2 = "/res/imgLow/wallpaper2.png";
            FILE_WALLPAPER3 = "/res/imgLow/wallpaper3.jpg";
            FILE_WAVE = "/res/imgLow/VAGUES-V2.gif";
            FILE_IMAGEILE1 = "/res/imgLow/ile1.png";
            FILE_IMAGEILE2 = "/res/imgLow/ile2.png";
            FILE_IMAGEILE3 = "/res/imgLow/ile3.png";
            FILE_BAIO = "/res/imgLow/baio.png";
            FILE_IMAGEMENUA = "/res/imgLow/menu.png";
            ICON_ABOUT = "/res/imgLow/about.png";
            ICON_ALEA = "/res/imgLow/alea.png";
            ICON_BACK = "/res/imgLow/back.png";
            ICON_NEXT = "/res/imgLow/next.png";
            ICON_SON = "/res/imgLow/son1.png";
            ICON_MUTE = "/res/imgLow/mute.png";
            ICON_ANIM = "/res/imgLow/vent.png";
            ICON_DICE = "/res/imgLow/dice.png";
            ICON_JOKER = "/res/imgLow/joker.png";
            ICON_MAIN = "/res/imgLow/main.png";
            ICON_RESTART = "/res/imgLow/restart.png";
            ICON_SETTINGS = "/res/imgLow/settings.png";
            ICON_STORY = "/res/imgLow/story.png";
            ICON_SURVOL = "/res/imgLow/survol.png";
            ICON_SURVOL2 = "/res/imgLow/mouse.png";
            ICON_ANIM2 = "/res/imgLow/nuage.png";
            ICON_TUTO = "/res/imgLow/tuto.png";
            ICON_STAIRS = "/res/imgLow/stairs.png";
            ICON_EXIT = "/res/imgLow/exit.png";
            ICON_LEVEL = "/res/imgLow/level.png";
            ICON_J1 = "/res/imgLow/j1.png";
            ICON_J2 = "/res/imgLow/j2.png";
            ICON_J3 = "/res/imgLow/j3.png";
            ICON_OUI = "/res/imgLow/oui.png";
            ICON_NON = "/res/imgLow/non.png";
        }
        else {
            FILE_WALLPAPER = "/res/img/archipel.png";
            FILE_WALLPAPER0 = "/res/img/wallpaper0.jpg";
            FILE_WALLPAPER1 = "/res/img/wallpaper1.png";
            FILE_WALLPAPER2 = "/res/img/wallpaper2.png";
            FILE_WALLPAPER3 = "/res/img/wallpaper3.jpg";
            FILE_WAVE = "/res/img/VAGUES-V2.gif";
            FILE_IMAGEILE1 = "/res/img/ile1.png";
            FILE_IMAGEILE2 = "/res/img/ile2.png";
            FILE_IMAGEILE3 = "/res/img/ile3.png";
            FILE_BAIO = "/res/img/baio.png";
            FILE_IMAGEMENUA = "/res/img/menu.png";
            ICON_ABOUT = "/res/img/about.png";
            ICON_ALEA = "/res/img/alea.png";
            ICON_BACK = "/res/img/back.png";
            ICON_NEXT = "/res/img/next.png";
            ICON_SON = "/res/img/son1.png";
            ICON_MUTE = "/res/img/mute.png";
            ICON_ANIM = "/res/img/vent.png";
            ICON_DICE = "/res/img/dice.png";
            ICON_JOKER = "/res/img/joker.png";
            ICON_MAIN = "/res/img/main.png";
            ICON_RESTART = "/res/img/restart.png";
            ICON_SETTINGS = "/res/img/settings.png";
            ICON_STORY = "/res/img/story.png";
            ICON_SURVOL = "/res/img/survol.png";
            ICON_SURVOL2 = "/res/img/mouse.png";
            ICON_ANIM2 = "/res/img/nuage.png";
            ICON_TUTO = "/res/img/tuto.png";
            ICON_STAIRS = "/res/img/stairs.png";
            ICON_EXIT = "/res/img/exit.png";
            ICON_LEVEL = "/res/img/level.png";
            ICON_J1 = "/res/img/j1.png";
            ICON_J2 = "/res/img/j2.png";
            ICON_J3 = "/res/img/j3.png";
            ICON_OUI = "/res/img/oui.png";
            ICON_NON = "/res/img/non.png";
        }
    }
  
    
    /**
     * Choisi aléatoirement l'image d'une ile.
     * @return chemin d'accès du fichier de l'image. 
     */
    public static String randomFileIle() {
        String[] t = new String[3];
        t[0] = Parametre.FILE_IMAGEILE1;
        t[1] = Parametre.FILE_IMAGEILE2;
        t[2] = Parametre.FILE_IMAGEILE3;
        return t[new Random().nextInt(3)];
    }
    
    /**
     * Modifie la largeur de la fenêtre
     * @param x largeur de la fenêtre
     */
    public static void setLargeurFenetre(int x){
        largeurFenetre = x;
    }
    
    /**
     * Modifie la hauteur de la fenêtre.
     * @param x hauteur de la fenêtre.
     */
    public static void setHauteurFenetre(int x){
        hauteurFenetre = x;
    }
    // ACCESSEURS ***************************************************************************
    
    /**
     * Accesseur.
     * Mode muet
     * @return true si le mode muet est activé.
     */
    public static boolean isAudioMute() {
        return audioMute;
    }
    
    /**
     * Accesseur.
     * Mode plein écran
     * @return true si le mode plein écran est activé.
     */
    public static boolean isPleinEcran() {
        return pleinEcran;
    }
    
     /**
     * Accesseur.
     * Mode low graphics
     * @return true si le mode low graphics est activé.
     */
    public static boolean isLow() {
        return isLow;
    }
    
    /**
     * Accesseur.
     * Double-clic
     * @return true si le double clic est activé.
     */
    public static boolean isDoubleClic() {
        return doubleClic;
    }
    
    /**sc2
     * Accesseur.
     * Animation
     * @return true si l'animation est activée.
     */
    public static boolean isAnime() {
        return anime;
    }
    
    /**
     * Accesseur.
     * Largeur de la fenêtre
     * @return largeur
     */
    public static int getLargeurFenetre(){
        return largeurFenetre;
    }
    
    /**
     * Accesseur.
     * Hauteur de la fenêtre.
     * @return hauteur.
     */
    public static int getHauteurFenetre(){
        return hauteurFenetre;
    }
    
     /**
     * Sauvegarde des paramètres dans le fichier Settings.bin.
     */
    @SuppressWarnings("null")
    public static boolean readSettings() {
        File f = new File(Parametre.FILE_PARAM);
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(f);
        } catch (FileNotFoundException ex) {
            System.out.println("Fichier " + Parametre.FILE_PARAM + " introuvable.");
        }
        f.setReadable(true);
        for (int i=0; i<5; i++){
            String str = sc2.next();
            
            if (i==0 && str.equals("0")){
                setAudioMute(false);
            }
            if (i==0 && str.equals("1")){
                setAudioMute(true);
            }
            if (i==1 && str.equals("0")){
                Parametre.setAnime(false);
            }
            if (i==1 && str.equals("1")){
                Parametre.setAnime(true);
            }
            if (i==2 && str.equals("0")){
                Parametre.setDoubleClic(false);
            }
            if (i==2 && str.equals("1")){
                Parametre.setDoubleClic(true);
            }
            if (i==3 && str.equals("0")){
                Parametre.setLow(false);
            }
            if (i==3 && str.equals("1")){
                Parametre.setLow(true);
            }
            if (i==4 && str.equals("0")){
                Parametre.setPleinEcran(false);
                sc2.close();
                return false;
            }
            if (i==4 && str.equals("1")){
                Parametre.setPleinEcran(true);
                sc2.close();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Active l'animation des vagues pendant le jeu.
     * @param b true pour activer l'animation.
     */
    @SuppressWarnings("null")
    public static void writeSettings(boolean b, int param_id) {
        File f = new File(Parametre.FILE_PARAM);
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(f);
        } catch (FileNotFoundException ex) {
            System.out.println("Fichier " + Parametre.FILE_PARAM + " introuvable.");
        }
        f.setReadable(true);
        f.setWritable(true);
        ArrayList<String> lignes = new ArrayList<>();
        Path fichier = Paths.get(Parametre.FILE_PARAM);
        int i = 0;
        while (sc2.hasNextLine()) {
            if (i==param_id) {
                if (b) lignes.add("1");
                else lignes.add("0");
                sc2.nextLine();
            } else if (i != param_id) {
                lignes.add(sc2.nextLine());
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