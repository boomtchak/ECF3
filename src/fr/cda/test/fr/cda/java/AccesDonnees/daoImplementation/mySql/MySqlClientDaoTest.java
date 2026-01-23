package fr.cda.java.AccesDonnees.daoImplementation.mySql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.model.gestion.Adresse;
import fr.cda.java.model.gestion.Client;
import fr.cda.java.utilitaire.Severite;
import fr.cda.java.utilitaire.TypeErreur;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * TU MySqlClientDao - Version simplifiée et auto-nettoyante.
 * Gère les suppressions en cascade manuelle pour éviter les doublons.
 */
class MySqlClientDaoTest {

    private final MySqlClientDao clientDao = new MySqlClientDao();
    private final MySqlAdresseDao adresseDao = new MySqlAdresseDao();

    private List<Integer> createdClientIds;
    private List<Integer> createdAdresseIds;

    @BeforeEach
    void setUp() {
        createdClientIds = new ArrayList<>();
        createdAdresseIds = new ArrayList<>();
    }

    /**
     * Nettoyage chirurgical après chaque test.
     * ORDRE CRITIQUE : Supprimer les clients d'abord (dépendants) puis les adresses.
     */
    @AfterEach
    void tearDown() {
        // 1. On nettoie les clients
        for (int id : createdClientIds) {
            try { clientDao.delete(id); } catch (TreatedException ignored) {}
        }
        // 2. On nettoie les adresses orphelines créées par le test
        for (int id : createdAdresseIds) {
            try { adresseDao.delete(id); } catch (TreatedException ignored) {}
        }
    }

    /**
     * Méthode utilitaire interne pour préparer le terrain.
     */
    private Adresse preparerAdresseUnique() throws TreatedException {
        Adresse adr = new Adresse(0, "1", "Rue " + System.nanoTime(), "00000", "City");
        Adresse cree = adresseDao.create(adr);
        createdAdresseIds.add(cree.getIdentifiant());
        return cree;
    }

    // --- CASES OK ---

    @Test
    @DisplayName("✅ OK : Création Client complet")
    void testCreateClientSuccess() throws TreatedException {
        // Setup
        Adresse adr = preparerAdresseUnique();
        String raisonUnique = "Societe_" + System.currentTimeMillis();
        Client client = new Client(0, raisonUnique, adr, "0102030405", "test@test.com", "Comment", 1000L, 10);

        // Action
        Client cree = clientDao.create(client);
        createdClientIds.add(cree.getIdentifiant());

        // Vérification
        assertTrue(cree.getIdentifiant() > 0);
        Client trouve = clientDao.getById(cree.getIdentifiant());
        assertEquals(raisonUnique, trouve.getRaisonSociale());
        assertNotNull(trouve.getIdentifiant());
    }

    // --- CASES KO ---

    @Test
    @DisplayName("❌ KO : Doublon Raison Sociale (Code 1062)")
    void testCreateClientDuplicate() throws TreatedException {
        // 1. Premier client
        Adresse adr = preparerAdresseUnique();
        String raison = "UniqueCorp_" + System.nanoTime();
        Client c1 = clientDao.create(new Client(0, raison, adr, "0389794080", "a@a.com", "C", 20000L, 70));
        createdClientIds.add(c1.getIdentifiant());

        // 2. Tentative de doublon
        Client c2 = new Client(0, raison, adr, "0389794080", "b@b.com", "C", 20000L, 505);

        TreatedException ex = assertThrows(TreatedException.class, () -> clientDao.create(c2));

        assertEquals(Severite.FAIBLE, ex.getSeverite());
        assertEquals(TypeErreur.DB_MODEL, ex.getTypeErreur());
    }

    @Test
    @DisplayName("❌ KO : Champ Obligatoire manquant (Code 1048)")
    void testCreateClientMissingField() throws TreatedException {
        Adresse adr = preparerAdresseUnique();
        // Raison Sociale à NULL
        Client clientInvalide = new Client(0, null, adr, "0389794080", "a@a.com", "C", 1000L, 400);

        TreatedException ex = assertThrows(TreatedException.class, () -> clientDao.create(clientInvalide));

        // Le service doit mapper 1048 en MOYENNE
        assertEquals(Severite.MOYENNE, ex.getSeverite());
    }

    @Test
    @DisplayName("❌ KO : ID inexistant")
    void testGetClientNotFound() {
        assertThrows(TreatedException.class, () -> clientDao.getById(-1));
    }
}