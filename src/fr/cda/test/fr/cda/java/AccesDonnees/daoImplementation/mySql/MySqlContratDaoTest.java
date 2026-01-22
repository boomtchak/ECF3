package fr.cda.java.AccesDonnees.daoImplementation.mySql;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.model.gestion.Adresse;
import fr.cda.java.model.gestion.Client;
import fr.cda.java.model.gestion.Contrat;
import fr.cda.java.utilitaire.Severite;
import fr.cda.java.utilitaire.TypeErreur;
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
class MySqlContratDaoTest {

    private final MySqlContratDao dao = new MySqlContratDao();
    private final MySqlClientDao clientDao = new MySqlClientDao();
    private final MySqlAdresseDao adresseDao = new MySqlAdresseDao();

    private static int idGenere;
    private static Client clientParent;

    @Test
    @Order(0)
    @DisplayName("Setup : Création Client pré-requis")
    void setupClient() throws TreatedException {
        Adresse adr = (Adresse) adresseDao.create(new Adresse(0, "1", "Rue Contrat", "31000", "Toulouse"));
        clientParent = new Client(0, "Client Contrat Corp test", adr, "0500000000", "c@c.com", "Com", 1000L, 5);
        clientParent = clientDao.create(clientParent);
    }

    // --- FLUX NOMINAL ---

    @Test
    @Order(1)
    @DisplayName("CRUD OK : Création de 2 contrats")
    void testCreateOk() {
        assertDoesNotThrow(() -> {
            Contrat c1 = new Contrat(0, "Contrat Maintenance", 5000.0);
            c1.setIdentifiantClient(clientParent.getIdentifiant());

            Contrat c2 = new Contrat(0, "Contrat Audit", 2500.0);
            c2.setIdentifiantClient(clientParent.getIdentifiant());

            Contrat created1 = dao.create(c1);
            dao.create(c2);

            assertTrue(created1.getIdentifiant() > 0);
            idGenere = created1.getIdentifiant();
        });
    }

    @Test
    @Order(2)
    @DisplayName("CRUD OK : Mise à jour")
    void testUpdateOk() {
        assertDoesNotThrow(() -> {
            Contrat c = dao.getById(idGenere);
            c.setNomContrat("Contrat Renouvelé");
            dao.update(c);

            Contrat verif = dao.getById(idGenere);
            assertEquals("Contrat Renouvelé", verif.getNomContrat());
        });
    }

    @Test
    @Order(3)
    @DisplayName("CRUD OK : Récupération par Parent ID")
    void testGetByParentIdOk() {
        assertDoesNotThrow(() -> {
            List<Contrat> contrats = dao.getByParentId(clientParent.getIdentifiant());
            assertFalse(contrats.isEmpty());
            assertTrue(contrats.size() >= 2);
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
    @DisplayName("Case 1048 : Champ Obligatoire (Sévérité MOYENNE)")
    void testMappingCode1048() {
        TreatedException ex = assertThrows(TreatedException.class, () -> {
            // ID Client manquant ou Nom Contrat null
            Contrat c = new Contrat(0, null, 100.0);
            // On ne set pas l'id client (donc 0 ou null selon mapping) -> Erreur FK ou Not Null
            dao.create(c);
        });
        // Note: Selon la contrainte (FK ou Not Null), le code peut varier (1452 ou 1048)
        // Ici on suppose le Not Null sur le nom
        assertTrue(ex.getTypeErreur() == TypeErreur.DB_MODEL);
    }

    @Test
    @DisplayName(" Case 1264 : Valeur Non Autorisée (Sévérité FAIBLE)")
    void testMappingCode1264() {
        TreatedException ex = assertThrows(TreatedException.class, () -> {
            // Montant hors limite (trop grand pour DECIMAL(10,2) par exemple)
            Contrat c = new Contrat(0, "Contrat Cher", 99999999999.0);
            c.setIdentifiantClient(clientParent.getIdentifiant());
            dao.create(c);
        });
        assertEquals(Severite.FAIBLE, ex.getSeverite());
    }
}