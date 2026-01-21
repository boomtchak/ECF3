package fr.cda.java.model.gestion;

import fr.cda.java.gestionErreurs.Exceptions.MandatoryDataException;
import fr.cda.java.gestionErreurs.Exceptions.RegexException;
import fr.cda.java.utilitaire.Regex;
import org.junit.platform.commons.util.StringUtils;

/**
 * Societe
 *
 * <p>Description : tous les types de société correspondent à cette définition de classe.</p>
 *
 * TODO : rendre abstraite.
 * @author CDA-09
 * @version 1.0
 * @since 05/11/2025
 */
public abstract class Societe {


    private int identifiant;
    private String raisonSociale;
    private Adresse adresse;
    private String telephone;
    private String adresseMail;
    private String commentaire;


    /**
     * @param identifiant description
     */
    public void setIdentifiant(int identifiant) {

        this.identifiant = identifiant;
    }

    /**
     * @return identifiant description
     */
    public int getIdentifiant() {
        return identifiant;
    }


    /**
     * @return raisonSociale description
     */
    public String getRaisonSociale() {
        return raisonSociale;
    }

    /**
     * @param raisonSociale description
     */
    public void setRaisonSociale(String raisonSociale) {

        if (StringUtils.isBlank(raisonSociale)) {
            throw new MandatoryDataException("raison sociale");
        }

        this.raisonSociale = raisonSociale;
    }

    /**
     * @return adresse description
     */
    public Adresse getAdresse() {
        return adresse;
    }

    /**
     * @param adresse description
     */
    public void setAdresse(Adresse adresse) {
if(adresse == null){
    throw new MandatoryDataException("adresse");
}
        this.adresse = adresse;
    }

    /**
     * @return telephone description
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone description
     */
    public void setTelephone(String telephone) {
        if (StringUtils.isBlank(telephone)) {
            throw new MandatoryDataException("téléphone");
        }
        if (!telephone.matches(Regex.PHONE_FR_SIMPLE)) {
            throw new RegexException("téléphone");
        }

        this.telephone = telephone;
    }

    /**
     * @return adresseMail description
     */
    public String getAdresseMail() {
        return adresseMail;
    }

    /**
     * @param adresseMail description
     */
    public void setAdresseMail(String adresseMail) {

        if (StringUtils.isBlank(adresseMail)) {
            throw new MandatoryDataException("adresse mail");
        }
        if (!adresseMail.matches(Regex.EMAIL)) {
            throw new RegexException("adresse mail");
        }

        this.adresseMail = adresseMail;
    }

    /**
     * @return commentaire description
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     * @param commentaire description
     */
    public void setCommentaire(String commentaire) {

        if (StringUtils.isBlank(commentaire)) {
            commentaire = "";
        }

        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return raisonSociale;
    }

    public String toPrint() {
        final StringBuilder sb = new StringBuilder("Societe{");
        sb.append("identifiant=").append(identifiant);
        sb.append(", raisonSociale='").append(raisonSociale).append('\'');
        sb.append(", adresse=").append(adresse);
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append(", adresseMail='").append(adresseMail).append('\'');
        sb.append(", commentaire='").append(commentaire).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
