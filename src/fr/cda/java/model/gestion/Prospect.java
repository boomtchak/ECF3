package fr.cda.java.model.gestion;

import fr.cda.java.gestionErreurs.Exceptions.MandatoryDataException;
import fr.cda.java.utilitaire.Interet;
import java.time.LocalDate;

/**
 * Prospect
 *
 * <p>Description : société futur client potentiel.</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 05/11/2025
 */
public class Prospect extends Societe {

    private LocalDate dateProspection;
    private Interet interet;


    public Prospect(int identifiant, String raisonSociale, Adresse adresse, String telephone,
            String adresseMail, String commentaire, LocalDate dateProspection, Interet interet) {

        // l'id n'existe que quand il revient de la base
        this(identifiant, raisonSociale, telephone, adresseMail, commentaire, dateProspection,
                interet);
        this.setAdresse(adresse);
    }

    public Prospect(int identifiant, String raisonSociale, String telephone,
            String adresseMail, String commentaire, LocalDate dateProspection, Interet interet) {
        this.setIdentifiant(identifiant);
        this.setTelephone(telephone);
        this.setAdresseMail(adresseMail);
        this.setCommentaire(commentaire);
        this.setDateProspection(dateProspection);
        this.setInteret(interet);
        this.setRaisonSociale(raisonSociale);
    }
/* obsolete , conservé pour un bouton back up dans une v3.
    public Prospect(JSONObject json) {
        String dateStr = json.getString("dateProspection");
        LocalDate date = LocalDate.parse(dateStr); // format ISO yyyy-MM-dd
        this(json.getInt().getString("raisonSociale"),
                new Adresse(json.getJSONObject("adresse")),
                json.getString("telephone"),
                json.getString("adresseMail"),
                json.getString("commentaire"),
                date,
                Interet.valueOf(json.getString("interet")));

    } */

    /**
     * @return dateProspection description
     */
    public LocalDate getDateProspection() {
        return dateProspection;
    }

    /**
     * @param dateProspection description
     */
    public void setDateProspection(LocalDate dateProspection) {
        if (dateProspection == null) {
            throw new MandatoryDataException("date de prospection");
        }
        this.dateProspection = dateProspection;
    }

    /**
     * @return interet description
     */
    public Interet getInteret() {
        return interet;
    }

    /**
     * @param interet description
     */
    public void setInteret(Interet interet) {

        if (interet == null) {
            interet = Interet.INCONNU;
        }

        this.interet = interet;
    }

    @Override
    public void setRaisonSociale(String raisonSociale) {
        super.setRaisonSociale(raisonSociale);
    }
}
