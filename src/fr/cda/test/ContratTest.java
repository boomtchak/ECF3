import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fr.cda.java.gestionErreurs.Exceptions.MandatoryDataException;
import fr.cda.java.model.gestion.Client;
import fr.cda.java.model.gestion.Contrat;
import fr.cda.java.model.gestion.Adresse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * L’identifiant du client est obligatoire
 * Le nom du contrat devra être saisi
 * Le montant du contrat devra être saisi
 */
@TestInstance(Lifecycle.PER_CLASS)
class ContratTest {

    Adresse adresse = new Adresse(1,"4", "rue de Berne", "68000", "COLMAR");
    Client client = new Client("ContratTest", adresse,
            "0633710842", "nordine.sefroun@laposte.net", "commentaire",
            220, 14);
    Contrat instance = new Contrat(client.getIdentifiant(), "contrat de test", 400);


    @ParameterizedTest(name = "Cas OK")
    @DisplayName("✅ Test de succès pour setIdentifiant")
    @ValueSource(ints = {1})
    void setIdentifiantOk(int input) {

        assertDoesNotThrow(() -> {
            instance.setIdentifiant(input);
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
    }

    @ParameterizedTest(name = "Cas OK")
    @DisplayName("✅ Test de succès pour setIdentifiantClient")
    @ValueSource(ints = {1})
    void setIdentifiantClientOk(int input) {

        assertDoesNotThrow(() -> {
            instance.setIdentifiantClient(input);
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
    }

    @ParameterizedTest(name = "Cas OK")
    @DisplayName("✅ Test de succès pour setNomContrat")
    @ValueSource(strings = {"nomContrat"})
    void setNomContratOk(String input) {

        assertDoesNotThrow(() -> {
            instance.setNomContrat(input);
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
    }

    @ParameterizedTest(name = "Cas OK")
    @DisplayName("✅ Test de succès pour setMontantContrat")
    @ValueSource(doubles = {55})
    void setMontantContratOk(double input) {

        assertDoesNotThrow(() -> {
            instance.setMontantContrat(input);
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
    }

    @Test
    @DisplayName("❌ Test d'échec pour setMontantContrat")
    void setMontantContratKo() {

        assertThrows(MandatoryDataException.class, () -> {
            instance.setMontantContrat(0);
        }, "Tous les champs sont obligatoires  : le champs montant du contrat n'est pas renseigné");
    }

    @Test
    @DisplayName("❌ Test d'échec pour setNomContrat")
    void setNomContratKo() {

        assertThrows(MandatoryDataException.class, () -> {
            instance.setNomContrat("");
        }, "Tous les champs sont obligatoires  : le champs nom du contrat n'est pas renseigné");
    }

    @Test
    @DisplayName("❌ Test d'échec pour setIdentifiantClient")
    void setIdentifiantClientKo() {

        assertThrows(MandatoryDataException.class, () -> {
            instance.setIdentifiantClient(0);
        }, "Tous les champs sont obligatoires  : le champs nom de la rue n'est pas renseigné");
    }
}