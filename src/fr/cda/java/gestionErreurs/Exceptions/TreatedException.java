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

    /**
     * @return severite description
     */
    public Severite getSeverite() {
        return severite;
    }

    /**
     * @param severite description
     */
    public void setSeverite(Severite severite) {

        this.severite = severite;
    }

    /**
     * @return message description
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * @param message description
     */
    public void setMessage(String message) {

        if (message == null) {
            message = "";
        }

        this.message = message;
    }

    /**
     * @return typeErreur description
     */
    public TypeErreur getTypeErreur() {
        return typeErreur;
    }

    /**
     * @param typeErreur description
     */
    public void setTypeErreur(TypeErreur typeErreur) {

        this.typeErreur = typeErreur;
    }

    private Severite severite;
    TypeErreur typeErreur;

    public TreatedException(String message, Severite severite, TypeErreur typeErreur) {
        this.message = message;
        this.severite = severite;
        this.typeErreur = typeErreur;
    }

}
