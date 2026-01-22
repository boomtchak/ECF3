package fr.cda.java.AccesDonnees.daoImplementation.mySql;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.model.gestion.Adresse;
import fr.cda.java.model.gestion.Prospect;
import fr.cda.java.utilitaire.Interet;
import fr.cda.java.utilitaire.Severite;
import fr.cda.java.utilitaire.TypeErreur;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MySqlProspectDaoTest {

    private final MySqlProspectDao dao = new MySqlProspectDao();
    private final MySqlAdresseDao adresseDao = new MySqlAdresseDao();
    private static int idGenere;
    private static Adresse adresseCommune;

    @Test
    @Order(0)
    @DisplayName("Setup : Création Adresse pré-requise")
    void setupAdresse() throws TreatedException {
        adresseCommune = (Adresse) adresseDao.create(new Adresse(0, "5", "Av Prospect", "33000", "Bordeaux"));
    }


    @Test
    @Order(1)
    @DisplayName("CRUD OK : Création de 2 prospects")
    void testCreateOk() {
        assertDoesNotThrow(() -> {
            Prospect p1 = new Prospect(0, "Prospect A", adresseCommune, "0600000001", "p1@test.com",
                    "Interessé", LocalDate.now(), Interet.OUI);
            Prospect p2 = new Prospect(0, "Prospect B", adresseCommune, "0600000002", "p2@test.com",
                    "Pas interessé", LocalDate.now(), Interet.NON);

            Prospect created1 = (Prospect) dao.create(p1);
            dao.create(p2);

            assertTrue(created1.getIdentifiant() > 0);
            idGenere = created1.getIdentifiant();
        });
    }

    @Test
    @Order(2)
    @DisplayName("CRUD OK : Mise à jour Prospect")
    void testUpdateOk() {
        assertDoesNotThrow(() -> {
            Prospect p = (Prospect) dao.getById(idGenere);
            p.setRaisonSociale("Prospect A Modifié");
            p.setInteret(Interet.NON);

            dao.update(p);

            Prospect verif = (Prospect) dao.getById(idGenere);
            assertEquals("Prospect A Modifié", verif.getRaisonSociale());
            assertEquals(Interet.NON, verif.getInteret());
        });
    }

    @Test
    @Order(3)
    @DisplayName("CRUD OK : Récupération Liste")
    void testFindAllOk() {
        assertDoesNotThrow(() -> {
            List<Prospect> liste = dao.findAll();
            assertFalse(liste.isEmpty());
        });
    }

    @Test
    @Order(4)
    @DisplayName("CRUD OK : Suppression")
    void testDeleteOk() {
        assertDoesNotThrow(() -> dao.delete(idGenere));
    }

    // --- FLUX ERREUR ---

    @Test
    @DisplayName("Case 1062 : Unicité Raison Sociale (Sévérité FAIBLE)")
    void testMappingCode1062() {
        assertDoesNotThrow(() -> {
            // Création initiale
            Prospect prospect = dao.create(new Prospect(0, "Unique Corp test", adresseCommune, "000", "u@u.com", "Com", LocalDate.now(), Interet.OUI));

            // Tentative de doublon
            TreatedException ex = assertThrows(TreatedException.class, () -> {
                dao.create(new Prospect(0, "Unique Corp test", adresseCommune, "111", "u2@u.com", "Com", LocalDate.now(), Interet.NON));
            });
            dao.delete(prospect.getIdentifiant());
            assertEquals(Severite.FAIBLE, ex.getSeverite());
            assertEquals(TypeErreur.DB_MODEL, ex.getTypeErreur());
        });
    }

    @Test
    @DisplayName(" Case 1406 : Donnée trop longue (Sévérité FAIBLE)")
    void testMappingCode1406() {
        TreatedException ex = assertThrows(TreatedException.class, () -> {
            String nomLong = "P".repeat(200); // Trop long
            dao.create(new Prospect(0, nomLong, adresseCommune, "000", "l@l.com", "Com", LocalDate.now(), Interet.OUI));
        });
        assertEquals(Severite.FAIBLE, ex.getSeverite());
    }

    @Test
    @DisplayName("Case 1048 : Champ Obligatoire (Sévérité MOYENNE)")
    void testMappingCode1048() {
        TreatedException ex = assertThrows(TreatedException.class, () -> {
            // Raison Sociale null
            dao.create(new Prospect(0, null, adresseCommune, "000", "n@n.com", "Com", LocalDate.now(), Interet.OUI));
        });
        assertEquals(Severite.MOYENNE, ex.getSeverite());
    }
}