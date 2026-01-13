package fr.cda.java.model.util;

import fr.cda.java.Exceptions.MandatoryDataException;
import fr.cda.java.Exceptions.RegexException;
import org.json.JSONObject;
import org.junit.platform.commons.util.StringUtils;

/**
 * Adresse
 *
 * <p>Description : classe 1 1 avec les societes, se gere sans fichier ni liste.</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 05/11/2025
 */
public class Adresse {

    private static int compteurIdentifiant = 1;
    private static int identifiant;
    private String numeroDeRue;
    private String nomDeRue;
    private String codePostal;
    private String ville;

    public Adresse(String numeroDeRue, String nomDeRue, String codePostal,
            String ville) {
        this.setIdentifiant(compteurIdentifiant);

        this.setNumeroDeRue(numeroDeRue);
        this.setNomDeRue(nomDeRue);
        this.setCodePostal(codePostal);
        this.setVille(ville);
        compteurIdentifiant++;
    }

    public Adresse(JSONObject json) {
        this(json.getString("numeroDeRue") , json.getString("nomDeRue"),
                json.getString("codePostal"), json.getString("ville"));
    }

    /**
     * @return identifiant description
     */
    public int getIdentifiant() {
        return identifiant;
    }

    /**
     * @param identifiant description
     */
    public void setIdentifiant(int identifiant) {

        this.identifiant = identifiant;
    }

    /**
     * @return numeroDeRue description
     */
    public String getNumeroDeRue() {
        return numeroDeRue;
    }

    /**
     * @param numeroDeRue description
     */
    public void setNumeroDeRue(String numeroDeRue) {

        if (StringUtils.isBlank(numeroDeRue)) {
            throw new MandatoryDataException("numero de la rue");
        }

        this.numeroDeRue = numeroDeRue;
    }

    /**
     * @return nomDeRue description
     */
    public String getNomDeRue() {
        return nomDeRue;
    }

    /**
     * @param nomDeRue description
     */
    public void setNomDeRue(String nomDeRue) {

        if (StringUtils.isBlank(nomDeRue)) {
            throw new MandatoryDataException("nom de la rue");
        }

        this.nomDeRue = nomDeRue;
    }

    /**
     * @return codePostal description
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * @param codePostal description
     */
    public void setCodePostal(String codePostal) {

        if (StringUtils.isBlank(codePostal)) {
            throw new MandatoryDataException("code postal");
        }
        if (!codePostal.matches(Regex.POSTAL_CODE_FR_SIMPLE)) {
            throw new RegexException("code postal");
        }

        this.codePostal = codePostal;
    }

    /**
     * @return ville description
     */
    public String getVille() {
        return ville;
    }

    /**
     * @param ville description
     */
    public void setVille(String ville) {

        if (StringUtils.isBlank(ville)) {
            throw new MandatoryDataException("ville");
        }

        this.ville = ville;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(numeroDeRue)
                .append(", ")
                .append(nomDeRue)
                .append(" ")
                .append(codePostal)
                .append(" ")
                .append(ville);
        return sb.toString();
    }

}
