package fr.cda.java.AccesDonnees.daoImplementation.mySql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.model.gestion.Adresse;
import fr.cda.java.model.gestion.Client;
import fr.cda.java.model.gestion.Contrat;
import fr.cda.java.utilitaire.Severite;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * TU MySqlContratDao - Version auto-nettoyante.
 * Gère la hiérarchie Contrat -> Client -> Adresse.
 */
class MySqlContratDaoTest {

    private final MySqlContratDao contratDao = new MySqlContratDao();
    private final MySqlClientDao clientDao = new MySqlClientDao();
    private final MySqlAdresseDao adresseDao = new MySqlAdresseDao();

    private List<Integer> createdContratIds;
    private List<Integer> createdClientIds;
    private List<Integer> createdAdresseIds;

    @BeforeEach
    void setUp() {
        createdContratIds = new ArrayList<>();
        createdClientIds = new ArrayList<>();
        createdAdresseIds = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        // ORDRE DE SUPPRESSION : Du plus dépendant au plus indépendant
        for (int id : createdContratIds) {
            try { contratDao.delete(id); } catch (TreatedException ignored) {}
        }
        for (int id : createdClientIds) {
            try { clientDao.delete(id); } catch (TreatedException ignored) {}
        }
        for (int id : createdAdresseIds) {
            try { adresseDao.delete(id); } catch (TreatedException ignored) {}
        }
    }

    /**
     * Prépare un client complet pour pouvoir y rattacher un contrat.
     */
    private Client preparerClientParent() throws TreatedException {
        // 1. Adresse
        Adresse adr = new Adresse(0, "10", "Rue du Test " + System.nanoTime(), "75000", "Paris");
        Adresse adrCree = adresseDao.Create(adr);
        createdAdresseIds.add(adrCree.getIdentifiant());

        // 2. Client
        Client client = new Client(0, "ClientTest_" + System.nanoTime(), adrCree, "0102030405", "test@contrat.com", "RAS", 500L, 5);
        Client clientCree = clientDao.Create(client);
        createdClientIds.add(clientCree.getIdentifiant());

        return clientCree;
    }

    @Test
    @DisplayName("✅ OK : Création Contrat")
    void testCreateContratSuccess() throws TreatedException {
        Client parent = preparerClientParent();
        String nomContratUnique = "CNTR_" + System.currentTimeMillis();
        Contrat contrat = new Contrat(parent.getIdentifiant(), nomContratUnique,1500.50);

        Contrat cree = contratDao.Create(contrat);
        createdContratIds.add(cree.getIdentifiant());

        assertTrue(cree.getIdentifiant() > 0);
        assertEquals(nomContratUnique, contratDao.findById(cree.getIdentifiant()).getNomContrat());
    }

    @Test
    @DisplayName("❌ KO : Champ Obligatoire manquant (1048)")
    void testCreateContratMissingField() throws TreatedException {
        Client parent = preparerClientParent();
        String nomContratUnique = "CNTR_" + System.currentTimeMillis();
        Contrat invalide = new Contrat(parent.getIdentifiant(), nomContratUnique,1500.50);

        TreatedException ex = assertThrows(TreatedException.class, () -> contratDao.Create(invalide));
        assertEquals(Severite.MOYENNE, ex.getSeverite());
    }
}