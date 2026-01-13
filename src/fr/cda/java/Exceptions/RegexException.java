package fr.cda.java.Exceptions;

/**
 * Gères les erreurs dues au non respect d'une regex.
 */
public class RegexException extends RuntimeException {

    public RegexException(String champs) {
        super(new StringBuilder("Le champ ").append(champs).append(" ne respecte pas le format requis").toString());
    }
}
