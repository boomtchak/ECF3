package fr.cda.java.model.gestion;

import fr.cda.java.Exceptions.MandatoryDataException;
import org.json.JSONObject;
import org.junit.platform.commons.util.StringUtils;

/**
 * Contrat
 *
 * <p>Description : établie une fois la confiance d'un client acquise</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 05/11/2025
 */
public class Contrat {

    public static int compteurIdentifiant = 1;
    private int identifiant;
    private int identifiantClient;
    private String nomContrat;
    private double montantContrat;

    public Contrat(int identifiantClient, String nomContrat,
            double montantContrat) {
        this.setIdentifiant(compteurIdentifiant);
        this.setIdentifiantClient(identifiantClient);
        this.setNomContrat(nomContrat);
        this.setMontantContrat(montantContrat);
        compteurIdentifiant++;
    }

    
    public Contrat(JSONObject json) {
        this.setIdentifiant(json.getInt("identifiant"));
        this.setIdentifiantClient(json.getInt("identifiantClient"));
        this.setNomContrat(json.getString("nomContrat"));
        this.setMontantContrat(json.getDouble("montantContrat"));
        compteurIdentifiant++;
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
     * @return identifiantClient description
     */
    public int getIdentifiantClient() {
        return identifiantClient;
    }

    /**
     * @param identifiantClient description
     */
    public void setIdentifiantClient(int identifiantClient) {
        if (identifiantClient == 0) {
            throw new MandatoryDataException("identifiant du client");
        }
        this.identifiantClient = identifiantClient;
    }

    /**
     * @return nomContrat description
     */
    public String getNomContrat() {
        return nomContrat;
    }

    /**
     * @param nomContrat description
     */
    public void setNomContrat(String nomContrat) {

        if (StringUtils.isBlank(nomContrat)) {
            throw new MandatoryDataException("nom du contrat");
        }

        this.nomContrat = nomContrat;

    }

    /**
     * @return montantContrat description
     */
    public double getMontantContrat() {
        return montantContrat;
    }

    /**
     * @param montantContrat description
     */
    public void setMontantContrat(double montantContrat) {
        if (montantContrat == 0) {
            throw new MandatoryDataException("montant du contrat");
        }

        this.montantContrat = montantContrat;
    }
}
