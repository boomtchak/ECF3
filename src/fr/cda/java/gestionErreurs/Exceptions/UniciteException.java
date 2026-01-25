package fr.cda.java.gestionErreurs.Exceptions;

/**
 * Gère le non respect de l'unicité de la raison sociale.
 */
public class UniciteException extends RuntimeException {

    public UniciteException(String raisonSociale) {
        super( String.format("Les raisons sociales sont uniques pour tous les types de sociétés. (%s)", raisonSociale));
    }
}
