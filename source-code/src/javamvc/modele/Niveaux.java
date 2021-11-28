/*
*
* Nom de la classe : Niveaux 
* 
* Description : 
*   Classe dédiée à la construction des niveaux à partir du 
*   fichier texte où ils sont stockés.
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public final class Niveaux {

    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    private static final ArrayList<ModeleHashi> NIVEAUX = new ArrayList<ModeleHashi>();
    private final String debut = "{";
    private final String fin = "}";

    // CONSTRUCTEURS ***************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Constructeur de Niveaux à partir du fichier ressource.
     */
    public Niveaux() {
        InputStream in = getClass().getResourceAsStream(Parametre.FILE_NIVEAU);
        Scanner sc = new Scanner(in);
        Scanner sc2 = null;
        Case[][] sol = null;
        File f = new File(Parametre.FILE_SCORE);
        try {
            sc2 = new Scanner(f);
        } catch (FileNotFoundException ex) {
            File dir = new File("save");
            dir.mkdirs();
            try {
                f.createNewFile();
                sc2 = new Scanner(f);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        while (sc.hasNext()) {
            String s = sc.next();
            switch (s) {
                case debut:
                    int taille = sc.nextInt();
                    sol = new Case[taille][taille];
                    break;
                case fin:
                    ModeleHashi m = new ModeleHashi(new Plateau(sol));
                    if (sc2.hasNextInt()) {
                        m.initScore(sc2.nextInt());
                    }
                    NIVEAUX.add(m);
                    break;
                default:
                    sol[Integer.valueOf(s)][sc.nextInt()] = new Case(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
                    break;
            }
        }
        sc.close();
        sc2.close();
        
    }
    
    // ACCESSEUR *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * ACCEUSSEUR.
     * @return liste des niveaux
     */
    public static ArrayList<ModeleHashi> getNiveaux() {
        return Niveaux.NIVEAUX;
    }

}
