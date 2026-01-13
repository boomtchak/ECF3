package fr.cda.java.Logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;


/**
 * Animaux
 *
 * <p>Description : Gestion des logs dans un fichier et pas dans la console.</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 30/10/2025
 */
public class AppLogger {

    private static final String REPERTOIRE_DATA = "Logs";
    /**
     *
     * Logger principal de l'application
     */
    public static final Logger LOGGER = Logger.getLogger(AppLogger.class.getName());

    /**
     *
     * Gestionnaire de fichier pour le log
     */
    private static FileHandler fileHandler = null;

    /**
     * je ne sais pas si lors de l'integration j'aurais pas des soucis de dossiers existant.
     */
    private static void verifierRepertoire() {
        File dataDir = new File(REPERTOIRE_DATA);
        if (!dataDir.exists()) {
            // Crée le répertoire s'il n'existe pas
            dataDir.mkdirs();
        }
    }

    /**
     *
     * Initialisation du logger
     */
    public static void initFileLogger() {
        verifierRepertoire();
        try {
            // Création du FileHandler
            fileHandler = new FileHandler("Logs/application.log", true);

            // Empêche l'affichage du log dans la console
            LOGGER.setUseParentHandlers(false);

            // Ajoute le gestionnaire de fichier au logger
            LOGGER.addHandler(fileHandler);

            // Attache un formateur personnalisé au gestionnaire
            fileHandler.setFormatter(new AppLogFormatter());

        } catch (IOException e) {
            System.err.println("Erreur lors de l'initialisation du logger : " + e.getMessage());
        }
    }
}

