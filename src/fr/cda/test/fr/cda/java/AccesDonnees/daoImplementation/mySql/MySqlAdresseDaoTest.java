package fr.cda.java.AccesDonnees.daoImplementation.mySql;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.model.gestion.Adresse;
import fr.cda.java.model.gestion.Client;
import fr.cda.java.utilitaire.Severite;
import fr.cda.java.utilitaire.TypeErreur;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * Classe de test pour MySqlAdresseDao.
 */
@TestInstance(Lifecycle.PER_CLASS)
class MySqlAdresseDaoTest {

    // Instanciation directe pour isoler le test de l'AppContext
    private final MySqlAdresseDao adresseDao = new MySqlAdresseDao();
    private final MySqlClientDao clientDao = new MySqlClientDao();


    /**
     * Teste le mappage de l'erreur SQL 1406 (longueur).
     */
    @Test
    @DisplayName("Test Longueur (Code 1406) - Doit retourner Sévérité FAIBLE")
    void testCreate_DonneeTropLongue_1406() {
        assertDoesNotThrow(() -> {
            String rueTropLongue = "A".repeat(255);
            Adresse adresseInvalide = new Adresse(0, "1", rueTropLongue,
                    "68000", "COLMAR");

            TreatedException exception = assertThrows(TreatedException.class, () -> {
                adresseDao.create(adresseInvalide);
            });

            assertEquals(Severite.FAIBLE, exception.getSeverite());
            assertEquals(TypeErreur.DB_MODEL, exception.getTypeErreur());
        });
    }

    /**
     * Teste le mappage de l'erreur SQL 1451 (integrité). Cas : On crée un client lié à l'adresse,
     * puis on tente de supprimer l'adresse.
     */
    @Test
    @DisplayName("Test Intégrité (Code 1451) - Suppression Adresse liée à un Client")
    void testDelete_ContrainteIntegrite_1451() {
        assertDoesNotThrow(() -> {
            // 1. Setup : Créer une adresse
            Adresse adr = new Adresse(0, "5", "Rue Liée", "33000", "Bordeaux");
            adr = (Adresse) adresseDao.create(adr);

            // 2. Setup : Créer un client qui utilise cette adresse (FK)
            Client client = new Client(0, "Entreprise Test3", adr, "0102030405", "test@test.com",
                    "Dev", 2000L, 5);
            clientDao.create(client);
            // 3. Action : Tentative de suppression de l'adresse
            int idAdresse = adr.getIdentifiant();
            TreatedException exception = assertThrows(TreatedException.class, () -> {
                adresseDao.delete(idAdresse);
            }, "L'adresse est liée à un client, la suppression doit échouer.");
            clientDao.delete(client.getIdentifiant());

            assertEquals(Severite.FAIBLE, exception.getSeverite());
            assertEquals(TypeErreur.DB_MODEL, exception.getTypeErreur());
        });
    }

    /**
     * Teste le comportement par défaut.
     */
    @Test
    @DisplayName("Test Erreur Syntaxe")
    void test_ErreurInconnue_Default() {
        TreatedException exception = assertThrows(TreatedException.class, () -> {
            adresseDao.getById(-1);
        });

        assertEquals(Severite.ELEVEE, exception.getSeverite());
        assertEquals(TypeErreur.DB_TECH, exception.getTypeErreur());
    }
}