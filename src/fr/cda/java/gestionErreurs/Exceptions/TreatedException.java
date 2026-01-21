package fr.cda.java.gestionErreurs.Exceptions;

import fr.cda.java.utilitaire.Severite;
import fr.cda.java.utilitaire.TypeErreur;

/**
 * TreatedException
 *
 * <p>Description :  lorsqu'elle est formatée, loggée, et prete a être affichée, l'erreur deviens
 * une treated Exception</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 19/01/2026
 */
public class TreatedException extends Throwable {

    private String message;
    private Severite severite;
    TypeErreur typeErreur;

    public TreatedException(String message, Severite severite, TypeErreur typeErreur) {
        this.message = message;
        this.severite = severite;
        this.typeErreur = typeErreur;
    }

}
