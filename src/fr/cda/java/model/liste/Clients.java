package fr.cda.java.model.liste;

import fr.cda.java.model.gestion.Client;
import java.util.TreeMap;

/**
 * Clients
 *
 * <p>Description : liste des clients utilisées pour conserver les clients existants. </p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 05/11/2025
 */
public class Clients implements Lister<Client> {

    static Clients clients;
    private TreeMap<String, Client> listeClients = new TreeMap<String, Client>();
    // c'est le point central pour la "persistance"
    private int compteurIdentifiant = 1;
    private int idInutilise;

    /**
     * Singletoon.
     *
     * @return
     */
    public static Clients getInstance() {
        if (clients == null) {
            clients = new Clients();
        }
        return clients;
    }


    public void ajouter(Client client) {

        // Si on a fait une suppression on  un trou dans le compteur d'id. a long terme c'est pas bon.
        if (idInutilise != 0) {

            client.setIdentifiant(idInutilise);
            idInutilise = 0;
        } else {
            client.setIdentifiant(compteurIdentifiant);
            compteurIdentifiant++;
        }
        listeClients.put(client.getRaisonSociale(), client);
    }


    public void replace(String index, Client client) {

        // si on a modifié la raison sociale on a cassé l'index.
        if(client.getRaisonSociale() !=index ){
            listeClients.remove(index);
            listeClients.put(client.getRaisonSociale(), client);
        }
        listeClients.replace(client.getRaisonSociale(), client);
    }

    public void supprimer(Client client) {

        idInutilise = client.getIdentifiant();
        listeClients.remove(client.getRaisonSociale());
    }


    /**
     * @return listeClients description
     */
    public TreeMap<String, Client> getListeSocietes() {
        return listeClients;
    }

    /**
     * @return compteurIdentifiant description
     */
    public int getCompteurIdentifiant() {
        if(idInutilise != 0){
            return idInutilise;
        }
        return compteurIdentifiant;
    }
}