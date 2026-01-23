package fr.cda.java.gestionErreurs.Logger;

import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.utilitaire.Severite;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.FileHandler;
import java.util.logging.Level;
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
     * Log une exception et la retourne pour permettre le throw immédiat. Usage : throw
     * AppLogger.log(te);
     */
    public static TreatedException log(TreatedException te,) {
        Level level = mapSeveriteToLevel(te.getSeverite());

        // Formatage du message pour le log
        String logMessage = String.format("[%s] [%s] [%s] Sévérité: %s | Message: %s",
                LocalDateTime.now(),
                te.getTypeErreur(),
                extraireOrigine(te),
                te.getSeverite(),
                te.getMessage());

        // Log effectif
        LOGGER.log(level, logMessage);
        return te; // On retourne l'objet pour chaîner le throw
    }

    private static Level mapSeveriteToLevel(Severite severite) {
        if (severite == null) {
            return Level.SEVERE;
        }
        return switch (severite) {
            case BASIQUE -> Level.FINE;
            case FAIBLE -> Level.INFO;
            case MOYENNE -> Level.WARNING;
            case ELEVEE -> Level.SEVERE;
            case URGENT -> Level.SEVERE;
        };
    }

    /**
     * Analyse la stack trace pour trouver la classe et la méthode d'origine.
     */
    private static String extraireOrigine(TreatedException te) {
        StackTraceElement[] stack = te.getStackTrace();
        if (stack.length > 0) {
            for (StackTraceElement element : stack) {
                String className = element.getClassName();
                if (!className.contains("gestionErreurs") && !className.contains("AppLogger")) {
                    String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
                    return simpleClassName + "." + element.getMethodName();
                }
            }
            return stack[0].getMethodName();
        }
        return "OrigineInconnue";
    }

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

