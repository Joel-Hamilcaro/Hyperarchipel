
import javamvc.modele.Parametre;
import javamvc.vues.VueEcranTitre;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import java.awt.Dimension;
import javafx.stage.StageStyle;

public class MainApplication extends Application {
    
    /** 
     * Active le mode plein écran
     *
     * @param primaryStage Fenêtre
     */
    public static void pleinEcran(Stage primaryStage) {
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) dimension.getHeight();
        int width = (int) dimension.getWidth();
        Parametre.setHauteurFenetre(height);
        Parametre.setLargeurFenetre(width);
        primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);
    }

    /**
     * Active le mode écran vertical
     *
     * @param primaryStage Fenêtre
     */
    public static void mobileEcran(Stage primaryStage) {
        Parametre.setHauteurFenetre((int) (480 * 1.5));
        Parametre.setLargeurFenetre((int) (320 * 1.5));
    }

    /**
     * Fonction appelée automatiquement au lancement de l'application
     *
     * @param primaryStage Fenêtre
     * @throws Exception Exception au lancement de l'application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        if (Parametre.readSettings()) pleinEcran(primaryStage) ;
        primaryStage.setScene(new VueEcranTitre(primaryStage).getScene());
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /**
     * Fermeture de l'application
     */
    @Override
    public void stop() {
        System.exit(0);
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Fonction principale qui appelle automatiquement start()
     *
     * @param args arguments de la fonction main (pas utilisé ici)
     */
    public static void main(String[] args) {
        launch(args);
    }

    ////////////////////////////////////////////////////////////////////////////
}
