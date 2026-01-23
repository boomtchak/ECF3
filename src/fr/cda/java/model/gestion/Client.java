package fr.cda.java.model.gestion;

import fr.cda.java.gestionErreurs.Exceptions.MandatoryDataException;
import fr.cda.java.gestionErreurs.Exceptions.donneeException;
import java.util.HashMap;
import java.util.Map;

/**
 * Client
 *
 * <p>Description : un client est une société avec qui des contrats ont été signés.</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 05/11/2025
 */
public class Client extends Societe {

    long chiffreAffaire;
    int nombreEmployes;
    Map<Integer, Contrat> listeContrats = new HashMap<>();

    /**
     * Construit un client à partir de la vue.
     *
     * @param raisonSociale
     * @param adresse
     * @param telephone
     * @param adresseMail
     * @param commentaire
     * @param chiffreAffaire
     * @param nombreEmployes
     */
    public Client(int identifiant, String raisonSociale, Adresse adresse, String telephone,
            String adresseMail,
            String commentaire, long chiffreAffaire, int nombreEmployes) {

        // l'id n'existe que quand il revient de la base
        this(identifiant, raisonSociale, telephone, adresseMail, commentaire, chiffreAffaire,
                nombreEmployes);
        this.setAdresse(adresse);
    }

    /**
     * Construit venant de la base de donnée.
     *
     * @param raisonSociale
     * @param telephone
     * @param adresseMail
     * @param commentaire
     * @param chiffreAffaire
     * @param nombreEmployes
     */
    public Client(int identifiant, String raisonSociale, String telephone, String adresseMail,
            String commentaire, long chiffreAffaire, int nombreEmployes) {

        this.setIdentifiant(identifiant);
        this.setTelephone(telephone);
        this.setAdresseMail(adresseMail);
        this.setCommentaire(commentaire);
        this.setChiffreAffaire(chiffreAffaire);
        this.setNombreEmployes(nombreEmployes);
        this.setRaisonSociale(raisonSociale);
    }


    /*  /**
     * C'est plus safe pour anticiper les données corrompues en json de passer par ici et utiliser
     * les setter.
     *
     * @param json
     *//*
    public Client(JSONObject json) {

        this(json.getString("raisonSociale"),
                new Adresse(json.getJSONObject("adresse")),
                json.getString("telephone"),
                json.getString("adresseMail"),
                json.getString("commentaire"),
                json.getLong("chiffreAffaire"),
                json.getInt("nombreEmployes"));
    }
    */

    /**
     * @return listeContrats description
     */
    public Map<Integer, Contrat> getListeContrats() {
        return listeContrats;
    }

    /**
     * @param listeContrats description
     */
    public void setListeContrats(Map<Integer, Contrat> listeContrats) {

        this.listeContrats = listeContrats;
    }

    /**
     * @return chiffreAffaire description
     */
    public long getChiffreAffaire() {
        return chiffreAffaire;
    }

    /**
     * @param chiffreAffaire description
     */
    public void setChiffreAffaire(long chiffreAffaire) {
        if (chiffreAffaire < 200) {
            throw new donneeException("le chiffre d'affaire doit être supérieur à 200");
        }

        this.chiffreAffaire = chiffreAffaire;
    }

    /**
     * @return nombreEmployes description
     */
    public int getNombreEmployes() {
        return nombreEmployes;
    }

    /**
     * @param nombreEmployes description
     */
    public void setNombreEmployes(int nombreEmployes) {
        if (nombreEmployes == 0) {
            throw new MandatoryDataException("nombre d'employés");
        }
        this.nombreEmployes = nombreEmployes;
    }

    @Override
    public void setRaisonSociale(String raisonSociale) {
        super.setRaisonSociale(raisonSociale);
    }

}
