package fr.cda.java.AccesDonnees.daoImplementation.mySql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.model.gestion.Adresse;
import fr.cda.java.model.gestion.Prospect;
import fr.cda.java.utilitaire.Interet;
import fr.cda.java.utilitaire.TypeErreur;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * TU MySqlProspectDao - Cycle de vie complet (CRUD).
 */
class MySqlProspectDaoTest {

    private final MySqlProspectDao prospectDao = new MySqlProspectDao();
    private final MySqlAdresseDao adresseDao = new MySqlAdresseDao();

    private List<Integer> createdProspectIds;
    private List<Integer> createdAdresseIds;

    @BeforeEach
    void setUp() {
        createdProspectIds = new ArrayList<>();
        createdAdresseIds = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        for (int id : createdProspectIds) {
            try { prospectDao.delete(id); } catch (TreatedException ignored) {}
        }
        for (int id : createdAdresseIds) {
            try { adresseDao.delete(id); } catch (TreatedException ignored) {}
        }
    }

    private Adresse preparerAdresseUnique() throws TreatedException {
        Adresse adr = new Adresse(0, "42", "Boulevard du Test " + System.nanoTime(), "69000", "Lyon");
        Adresse cree = adresseDao.create(adr);
        createdAdresseIds.add(cree.getIdentifiant());
        return cree;
    }

    @Test
    @DisplayName("✅ OK : Création Prospect")
    void testCreateProspectSuccess() throws TreatedException {
        Adresse adr = preparerAdresseUnique();
        String raison = "Creation_" + System.currentTimeMillis();
        Prospect p = new Prospect(0, raison, adr, "0600000000", "p@test.fr", "Note", LocalDate.now(), Interet.NON);

        Prospect cree = prospectDao.create(p);
        createdProspectIds.add(cree.getIdentifiant());

        assertNotNull(cree.getIdentifiant());
        assertEquals(raison, prospectDao.findById(cree.getIdentifiant()).getRaisonSociale());
    }

    @Test
    @DisplayName("✅ OK : Liste des prospects (findAll)")
    void testGetAllProspects() throws TreatedException {
        // On s'assure d'avoir au moins 2 éléments
        testCreateProspectSuccess();
        testCreateProspectSuccess();

        List<Prospect> liste = prospectDao.findAll();

        // La liste ne doit pas être vide et contenir au moins nos 2 créations
        assertTrue(liste.size() >= 2);
    }

    @Test
    @DisplayName("✅ OK : Mise à jour (update)")
    void testSaveProspect() throws TreatedException {
        // 1. Initialisation
        Adresse adr = preparerAdresseUnique();
        Prospect p = prospectDao.create(new Prospect(0, "Initial", adr, "0389794080", "old@mail.com", "Note", LocalDate.now(), Interet.NON));
        createdProspectIds.add(p.getIdentifiant());

        // 2. Modification locale
        String nouvelleRaison = "MiseAJour_" + System.nanoTime();
        p.setRaisonSociale(nouvelleRaison);
        p.setInteret(Interet.OUI);

        // 3. Persistance
        prospectDao.save(p);

        // 4. Vérification
        Prospect maj = prospectDao.findById(p.getIdentifiant());
        assertEquals(nouvelleRaison, maj.getRaisonSociale());
        assertEquals(Interet.OUI, maj.getInteret());
    }

    @Test
    @DisplayName("✅ OK : Suppression (delete)")
    void testDeleteProspect() throws TreatedException {
        // 1. Création
        Adresse adr = preparerAdresseUnique();
        Prospect p = prospectDao.create(new Prospect(0, "ASupprimer", adr, "0102030405", "del@mail.com", "Note", LocalDate.now(), Interet.NON));
        int id = p.getIdentifiant();

        // 2. Action
        prospectDao.delete(id);
        // On retire de la liste de cleanup car déjà supprimé
        createdProspectIds.remove(Integer.valueOf(id));

        // 3. Vérification : getById doit lever une exception
        assertThrows(TreatedException.class, () -> prospectDao.findById(id));
    }

    @Test
    @DisplayName("❌ KO : Doublon Raison Sociale")
    void testCreateProspectDuplicate() throws TreatedException {
        String raison = "Unique_" + System.nanoTime();
        Adresse adr = preparerAdresseUnique();

        prospectDao.create(new Prospect(0, raison, adr, "0389897805", "a@a.com", "C", LocalDate.now(), Interet.NON));
        Prospect p2 = new Prospect(0, raison, adr, "0389897805", "b@b.com", "C", LocalDate.now(), Interet.NON);

        TreatedException ex = assertThrows(TreatedException.class, () -> prospectDao.create(p2));
        assertEquals(TypeErreur.DB_MODEL, ex.getTypeErreur());
    }
}