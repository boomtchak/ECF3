package fr.cda.java.model.gestion;

import fr.cda.java.Exceptions.MandatoryDataException;
import fr.cda.java.Exceptions.UniciteException;
import fr.cda.java.model.liste.Clients;
import fr.cda.java.model.liste.Prospects;
import fr.cda.java.model.util.Adresse;
import fr.cda.java.model.util.Interet;
import java.time.LocalDate;
import org.json.JSONObject;

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

    public Prospect(String raisonSociale, Adresse adresse, String telephone,
            String adresseMail, String commentaire, LocalDate dateProspection, Interet interet) {
        this.setAdresse(adresse);
        this.setTelephone(telephone);
        this.setAdresseMail(adresseMail);
        this.setCommentaire(commentaire);
        this.setDateProspection(dateProspection);
        this.setInteret(interet);
        this.setRaisonSociale(raisonSociale);
    }

    public Prospect(JSONObject json) {
        String dateStr = json.getString("dateProspection");
        LocalDate date = LocalDate.parse(dateStr); // format ISO yyyy-MM-dd
        this(json.getString("raisonSociale"),
                new Adresse(json.getJSONObject("adresse")),
                json.getString("telephone"),
                json.getString("adresseMail"),
                json.getString("commentaire"),
                date,
                Interet.valueOf(json.getString("interet")));

    }

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
        if(dateProspection == null){
            throw  new MandatoryDataException("date de prospection");
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
        /**
         * si la raison sociale existe déjà, on s'assure qu'il s'agit pas de l'objet en cours de traitement
         */
        if (Clients.getInstance().getListeSocietes().containsKey(raisonSociale)
                || (Prospects.getInstance().getListeSocietes().containsKey(raisonSociale)
                && Prospects.getInstance().getListeSocietes().get(raisonSociale).getIdentifiant()
                != this.getIdentifiant())) {
            throw new UniciteException(raisonSociale);
        }
        super.setRaisonSociale(raisonSociale);
    }
}
