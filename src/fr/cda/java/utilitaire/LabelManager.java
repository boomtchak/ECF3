package fr.cda.java.utilitaire;

import fr.cda.java.gestionErreurs.Logger.AppLogger;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Properties;

/**
 * LabelManager
 *
 * <p>Description : preparation d'une architecture i18n /p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 19/01/2026
 */
public class LabelManager {


    // L'instance unique des propriétés
    private static final Properties properties = new Properties();
    private static String propertiesUrl = AppContext.langue.getUrl();

    static {
        chargerLesDonnees();
    }

    /**
     *
     * 2. On extrait la logique dans une méthode réutilisable
     */
    private static void chargerLesDonnees() {
        properties.clear();
        try (InputStream input = LabelManager.class.getClassLoader()
                .getResourceAsStream(propertiesUrl)) {
            if (input == null) {
                AppLogger.LOGGER.severe("Désolé, impossible de trouver le fichier ");
            } else {
                properties.load(input); // Chargement sécurisé
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * Méthode d'accès au labels
     */
    public static String get(String key, Object... args) {

        String message = get(key);

        return MessageFormat.format(message, args);
    }

    /**
     * Méthode d'accès au labels
     *
     */
    public static String get(String key) {

        String message = properties.getProperty(key, "!" + key + "!");
        return properties.getProperty(key, "!" + key + "!");
    }

    /**
     * modifie la langue de l'appli, ce qui va changer les variables globales associées./
     * @param langue
     */
    public static void changerLangue(Langue langue) {
        AppContext.langue = langue;

        propertiesUrl = langue.getUrl();
        chargerLesDonnees();

    }
}
