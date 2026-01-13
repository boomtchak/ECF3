package fr.cda.java.Exceptions;

/**
 * Gère l'absence d'une donnée obligatoire dans un formulaire.
 */
public class MandatoryDataException extends RuntimeException {


    public MandatoryDataException(String champs) {


        super(new StringBuilder("Tous les champs sont obligatoires  : ").append("le champs ").append(champs).append(" n'est pas renseigné").toString()) ;
    }



}
