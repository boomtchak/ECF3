package fr.cda.java.AccesDonnees.daoImplementation.mySql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.model.gestion.Adresse;
import fr.cda.java.utilitaire.TypeErreur;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * TU MySqlAdresseDao - Version simplifiée et auto-nettoyante.
 * Pas d'habillage inutile, focus sur le résultat et la propreté de la DB.
 */
class MySqlAdresseDaoTest {

    private final MySqlAdresseDao dao = new MySqlAdresseDao();
    private List<Integer> createdIds;

    @BeforeEach
    void setUp() {
        createdIds = new ArrayList<>();
    }

    /**
     * Nettoyage automatique après chaque test.
     * On supprime uniquement ce que le test a créé pour éviter les doublons au prochain run.
     */
    @AfterEach
    void tearDown() {
        for (int id : createdIds) {
            try {
                dao.delete(id);
            } catch (TreatedException e) {
                // On ignore l'échec du nettoyage pour ne pas masquer l'erreur du test principal
            }
        }
    }

    // --- CASES OK (NOMINAL) ---

    @Test
    @DisplayName("✅ OK : Création et lecture")
    void testCreateSuccess() throws TreatedException {
        // Préparation : on utilise un timestamp pour garantir l'unicité des données de test
        String uniqueRue = "Rue " + System.currentTimeMillis();
        Adresse adr = new Adresse(0, "10", uniqueRue, "75000", "Paris");

        // Action : Si ça throw, JUnit fera échouer le test (pas besoin de assertDoesNotThrow)
        Adresse cree = dao.create(adr);

        // Enregistrement pour le cleanup
        createdIds.add(cree.getIdentifiant());

        // Vérification
        assertTrue(cree.getIdentifiant() > 0);
        Adresse trouve = dao.findById(cree.getIdentifiant());
        assertNotNull(trouve);
        assertEquals(uniqueRue, trouve.getNomDeRue());
    }

    // --- CASES KO (ERROR MAPPING) ---


    @Test
    @DisplayName("❌ KO : Donnée trop longue (Code 1406)")
    void testDataTooLong() {
        String tropLong = "A".repeat(300); // Dépasse le VARCHAR habituel
        Adresse adr = new Adresse(0, "1", tropLong, "00000", "City");

        TreatedException ex = assertThrows(TreatedException.class, () -> dao.create(adr));

        assertEquals(TypeErreur.DB_MODEL, ex.getTypeErreur());
    }

    @Test
    @DisplayName("❌ KO : ID inexistant")
    void testGetNotFound() {
        // On cherche un ID qui n'existe pas
        assertThrows(TreatedException.class, () -> dao.findById(-999));
    }
}