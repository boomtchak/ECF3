import fr.cda.java.Logger.AppLogger;
import fr.cda.java.dao.Dao;
import fr.cda.java.vue.Acceuil;
import java.awt.Dimension;
import javax.swing.JOptionPane;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

/**
 * Lanceur de l'application. gère les erreurs techniques.
 */
void main() {
    AppLogger.initFileLogger();

    Acceuil dialog = new Acceuil();
    try {
        Dao.charger();

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

    } finally {
        try {
            //quoi qu'il arrive on tente la save.
            Dao.sauvegarder();

        } catch (IOException e) {
            // si il y a un plantage ici, on sait que la sauvegarde est en cause.
            AppLogger.LOGGER.severe("la sauvegarde a rencontré un problème non anticipé");
            JOptionPane.showMessageDialog(dialog,
                    "la sauvegarde a rencontré suite à un problème en cours de résolution. Merci de votre compréhension.");
        }
    }
}
