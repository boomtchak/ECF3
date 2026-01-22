package fr.cda.java.AccesDonnees.daoImplementation.mySql;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.model.gestion.Adresse;
import fr.cda.java.model.gestion.Client;
import fr.cda.java.model.gestion.Contrat;
import fr.cda.java.utilitaire.Severite;
import fr.cda.java.utilitaire.TypeErreur;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * Classe de test pour MySqlClientDao.
 */
@TestInstance(Lifecycle.PER_CLASS)
class MySqlClientDaoTest {


    private final MySqlClientDao clientDao = new MySqlClientDao();
    private final MySqlAdresseDao adresseDao = new MySqlAdresseDao();
    private final MySqlContratDao contratDao = new MySqlContratDao();

    /**
     * Méthode utilitaire pour générer une adresse valide en base.
     */
    private Adresse creerAdresseTechnique() throws TreatedException {
        Adresse adr = new Adresse(0, "10", "Rue Test Client", "75000", "Paris");
        return (Adresse) adresseDao.create(adr);
    }

    /**
     * Création d'un client avec son adresse, puis récupération.
     */
    @Test
    @DisplayName("Test Succès : Création et Récupération Client")
    void testCreateAndFind_Success() {
        assertDoesNotThrow(() -> {
            // 1. Pré-requis : Création de l'adresse
            Adresse adresse = creerAdresseTechnique();

            // 2. Action : Création du client lié à cette adresse
            // Note : L'ID est à 0 avant insertion
            Client client = new Client(0, "Stark IndustriesTest", adresse, "0102030405",
                    "tony@stark.com", "CEO", 1000000000L, 500);
            Client cree = clientDao.create(client);

            // 3. Vérifications
            assertTrue(cree.getIdentifiant() > 0, "L'ID Client doit être généré par l'auto-incrément.");

            Client trouve = clientDao.getById(cree.getIdentifiant());
            assertNotNull(trouve, "Le client doit être retrouvé en base.");
            assertEquals("Stark IndustriesTest", trouve.getRaisonSociale(), "La raison sociale doit correspondre.");

            // Vérification de la persistance de la clé étrangère
            assertTrue(trouve.getIdAdresse() > 0, "Le lien avec l'adresse doit être maintenu.");
            clientDao.delete(client.getIdentifiant());
        });
    }

    /**
     * Teste le mappage de l'erreur SQL 1062 (doublons).
     */
    @Test
    @DisplayName("Test Unicité (Code 1062) - Doit retourner Sévérité FAIBLE")
    void testCreate_Doublon_1062() {
        assertDoesNotThrow(() -> {
            Adresse adresse = creerAdresseTechnique();

            // Premier client (Référence)
            Client c1 = new Client(0, "Wayne Corp test", adresse, "0101010101", "bruce@wayne.com", "Bat", 10000L, 1);
            clientDao.create(c1);

            // Deuxième client avec la MÊME raison sociale (Violation contrainte UNIQUE)
            Client c2 = new Client(0, "Wayne Corp test", adresse, "0202020202", "fake@wayne.com", "Copy", 10000L, 1);

            TreatedException exception = assertThrows(TreatedException.class, () -> {
                clientDao.create(c2);
            }, "Une TreatedException est attendue pour un doublon de Raison Sociale.");
            clientDao.delete(c1.getIdentifiant());
            //clientDao.delete(c2.getIdentifiant());
            assertEquals(Severite.FAIBLE, exception.getSeverite());
            assertEquals(TypeErreur.DB_MODEL, exception.getTypeErreur());
        });
    }

    /**
     * Teste le mappage de l'erreur SQL 1406 (longueur).
     */
    @Test
    @DisplayName("Test Longueur (Code 1406) - Doit retourner Sévérité FAIBLE")
    void testCreate_DonneeTropLongue_1406() {
        assertDoesNotThrow(() -> {
            Adresse adresse = creerAdresseTechnique();
            String nomTropLong = "A".repeat(255); // Dépassement supposé du VARCHAR(50)

            Client client = new Client(0, nomTropLong, adresse, "0101010101", "a@a.com", "Test", 300L, 1);

            TreatedException exception = assertThrows(TreatedException.class, () -> {
                clientDao.create(client);
            });

            assertEquals(Severite.FAIBLE, exception.getSeverite());
            assertEquals(TypeErreur.DB_MODEL, exception.getTypeErreur());
        });
    }


    /**
     * Teste le mappage de l'erreur SQL 1451 (Foreign Key Constraint Fail).
     * Cas métier : Tentative de suppression d'un client possédant des contrats actifs.
     * Résultat attendu : Sévérité FAIBLE.
     */
    @Test
    @DisplayName("Test Intégrité (Code 1451) - Suppression Client avec Contrat")
    void testDelete_ContrainteIntegrite_1451() {
        assertDoesNotThrow(() -> {
            // 1. Setup : Chaîne de dépendance Adresse -> Client -> Contrat
            Adresse adresse = creerAdresseTechnique();
            Client client = new Client(0, "Oscorp test", adresse, "0606060606", "norman@oscorp.com", "Green", 5000L, 10);
            client = clientDao.create(client);

            // Création d'un contrat lié au client
            Contrat contrat = new Contrat(0, "Armure", 50000.0);
            contrat.setIdentifiantClient(client.getIdentifiant());
            contratDao.create(contrat);

            // 2. Action : Tentative de suppression du Client
            int idClient = client.getIdentifiant();
            TreatedException exception = assertThrows(TreatedException.class, () -> {
                clientDao.delete(idClient);
            }, "La suppression doit être bloquée par la présence du contrat.");

            contratDao.delete(contrat.getIdentifiant());
            clientDao.delete(client.getIdentifiant());
            assertEquals(Severite.FAIBLE, exception.getSeverite());
            assertEquals(TypeErreur.DB_MODEL, exception.getTypeErreur());
        });
    }

    /**
     * Teste le comportement par défaut en cas d'erreur non identifiée ou d'absence de résultat gérée.
     */
    @Test
    @DisplayName("Test Erreur Inconnue (Default) - ID Inexistant")
    void test_ErreurInconnue_Default() {
        TreatedException exception = assertThrows(TreatedException.class, () -> {
            clientDao.getById(-99999);
        });

        assertEquals(Severite.ELEVEE, exception.getSeverite());
        assertEquals(TypeErreur.APP_TECH, exception.getTypeErreur());
    }
}