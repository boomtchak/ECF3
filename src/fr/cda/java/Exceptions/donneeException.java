package fr.cda.java.Exceptions;

/**
 * Gère les erreurs liées aux données qui ne respectent pas leurs regle métier.
 */
public class donneeException extends RuntimeException {

    public donneeException(String message) {
        super(message);
    }
}
