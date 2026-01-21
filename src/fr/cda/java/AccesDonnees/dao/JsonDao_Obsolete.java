package fr.cda.java.AccesDonnees.dao;

import fr.cda.java.gestionErreurs.Logger.AppLogger;
import fr.cda.java.model.gestion.Client;
import fr.cda.java.model.gestion.Contrat;
import fr.cda.java.model.gestion.Prospect;
import fr.cda.java.model.obsolete_liste.Clients;
import fr.cda.java.model.obsolete_liste.Prospects;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * ProspectDao
 *
 * <p>Description : une simple classe bouchon,
 * qui permet de charger les données à l'ouverture de l'appli (voir init) depuis les fichiers et de
 * les sauvegarder à la fermeture de l'application dans les fichiers. Pas de sauvegarde en temps
 * reel.</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 07/11/2025
 */
public class JsonDao_Obsolete {

    private static final String FICHIER_DB = "data/db.json";
    private static final String REPERTOIRE_DATA = "data";

    /**
     * je ne sais pas si lors de l'integration j'aurais pas des soucis de dossiers existant.
     */
    private static void verifierRepertoire() {
        File dataDir = new File(REPERTOIRE_DATA);
        if (!dataDir.exists()) {
            // Crée le répertoire s'il n'existe pas
            dataDir.mkdirs();
        }
    }

    /**
     * ca va sauvegarder tous les clients, avec tous leur contrats, et tous les prospects ensuite.
     *
     * @throws IOException
     */
    public static void sauvegarder() throws IOException {
        verifierRepertoire();
        HashMap<String, JSONArray> listeComplete = new HashMap<String, JSONArray>();
        JSONArray jsonArrayC = new JSONArray(Clients.getInstance().getListeSocietes().values());
        JSONArray jsonArrayP = new JSONArray(Prospects.getInstance().getListeSocietes().values());

        listeComplete.put("clients", jsonArrayC);
        listeComplete.put("prospects", jsonArrayP);

        JSONObject son = new JSONObject(listeComplete);
        FileWriter writer = new FileWriter(FICHIER_DB);
        // Convertit l'objet Java (la liste) en chaîne JSON et l'écrit dans le fichier
        writer.write(son.toString(2));
    }

    /**
     * Utilise des constructeurs json pour recréer les données en cache.
     */
    public static void charger() throws IOException {
        verifierRepertoire();
        FileReader reader = new FileReader("data/db.json");
        // 1. Lire le contenu du fichier pour obtenir le JSONObject racine
        JSONTokener tokener = new JSONTokener(reader);
        JSONObject json = new JSONObject(tokener);
        if (!json.has("clients")) {
            throw new RuntimeException("JSON ne contient pas de clé 'clients'");
        }
        JSONArray listeClients = json.getJSONArray("clients");
        if (!listeClients.isEmpty()) {
            for (int i = 0; i < listeClients.length(); i++) {
                JSONObject clientJson = listeClients.getJSONObject(i);
                Client client = new Client(clientJson);
                JSONArray listeContratClients = clientJson.getJSONArray("listeContrats");
                Map<Integer, Contrat> listeContrats = new HashMap<>();
                if (!listeContratClients.isEmpty()) {
                    for (int j = 0; j < listeContratClients.length(); j++) {
                        JSONObject contratJson = listeContratClients.getJSONObject(j);
                        Contrat contrat = new Contrat(contratJson);
                        listeContrats.put(contrat.getIdentifiant(), contrat);
                    }
                }
                client.setListeContrats(listeContrats);
                Clients.getInstance().ajouter(client);
            }
        }
        if (!json.has("prospects")) {
            throw new RuntimeException("JSON ne contient pas de clé 'prospects'");
        }
        JSONArray listeProspects = json.getJSONArray("prospects");
        if (!listeProspects.isEmpty()) {
            for (int i = 0; i < listeProspects.length(); i++) {
                JSONObject prospectJson = listeProspects.getJSONObject(i);
                Prospect prospect = new Prospect(prospectJson);
                Prospects.getInstance().ajouter(prospect);
            }
        }

        AppLogger.LOGGER.info("le chargement s'est bien passé");

    }

}
