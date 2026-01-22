import fr.cda.java.gestionErreurs.Logger.AppLogger;
import fr.cda.java.vue.Acceuil;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

/**
 * Lanceur de l'application. gère les erreurs techniques.
 */
void main() {
    AppLogger.initFileLogger();
    UIManager.put("Button.disabledText", Color.BLUE); // Ou une couleur plus lisible que le gris
    Acceuil dialog = new Acceuil();
    try {
        // on lit les valeurs du fichier, pas à pas, le but c'est de bien comprendre
        //  c'est ici que la langue est choisie
        File fichier = new File("dataProperties/fr.properties");
        FileInputStream input = new FileInputStream(fichier);
        Properties dataProperties = new Properties();
        dataProperties.load(input);
        //JsonDao_Obsolete.charger();

    } catch (IOException e) {
        AppLogger.LOGGER.severe("Le chargement a rencontré un problème IO voir les droits");
        JOptionPane.showMessageDialog(dialog,
                "la chargement a rencontré suite à un problème en cours de résolution. Merci de votre compréhension.");

    } catch (Exception e) {
        AppLogger.LOGGER.severe("le chargement a rencontré un problème non anticipé");
        JOptionPane.showMessageDialog(dialog,
                "la chargement a rencontré suite à un problème en cours de résolution. Merci de votre compréhension.");
    }

    try {

        Dimension perfectSize = dialog.getSize();

        // 2. On force la fenêtre à ne JAMAIS être plus petite que cette taille
        dialog.setMinimumSize(perfectSize);

        // 3. (Optionnel) Vous pouvez maintenant cacher votre panneau par défaut
        // La fenêtre ne rétrécira pas.
        dialog.pack();

        dialog.getPaneauSociete().setVisible(false);
        dialog.setVisible(true);
    } catch (Exception e) {
        System.exit(0);
        // ici il s'agit d'un plantage de l'appli.
        AppLogger.LOGGER.severe("l'application a rencontré un problème non anticipé");
        JOptionPane.showMessageDialog(dialog,
                "l'application' a rencontré problème en cours de résolution. Merci de votre compréhension.");

    }
}
