package fr.cda.java.gestionErreurs.Exceptions;

/**
 * Gestion des erreurs liés  à l'affichage des données.
 */
public class AffichageException extends RuntimeException {

    public AffichageException(String message) {
        super(message);
    }
}
