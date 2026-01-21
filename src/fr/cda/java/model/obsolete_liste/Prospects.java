package fr.cda.java.model.obsolete_liste;

import fr.cda.java.model.gestion.Prospect;
import java.util.TreeMap;

/**
 * Prospects
 *
 * <p>Description :  Description : liste des prospects utilisée pour conserver les prospects
 * existants. </p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 05/11/2025
 */
public class Prospects implements Lister<Prospect> {

    static Prospects prospects;
    private int compteurIdentifiant = 1;
    private TreeMap<String, Prospect> listeProspects = new TreeMap<String, Prospect>();


    public static Prospects getInstance() {
        if (prospects == null) {
            prospects = new Prospects();
        }
        return prospects;
    }

    public void ajouter(Prospect prospect) {
        prospect.setIdentifiant(compteurIdentifiant);
        listeProspects.put(prospect.getRaisonSociale(), prospect);
        compteurIdentifiant++;
    }

    public void replace(String index, Prospect prospect) {
        // si on a modifié la raison sociale on a cassé l'index.
        if (prospect.getRaisonSociale() != index) {
            listeProspects.remove(index);
            listeProspects.put(prospect.getRaisonSociale(), prospect);
        }
        listeProspects.replace(prospect.getRaisonSociale(), prospect);
    }

    @Override
    public void supprimer(Prospect entite) {

    }

    /**
     * @return listeClients description
     */
    public TreeMap<String, Prospect> getListeSocietes() {
        return listeProspects;
    }

    /**
     * @return compteurIdentifiant description
     */
    public int getCompteurIdentifiant() {
        if (idInutilise != 0) {
            return idInutilise;
        }
        return compteurIdentifiant;
    }

}