import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fr.cda.java.Exceptions.MandatoryDataException;
import fr.cda.java.Exceptions.donneeException;
import fr.cda.java.model.gestion.Client;
import fr.cda.java.model.util.Adresse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * La raison sociale est unique et distinct pour toutes les sociétés
 * Le chiffre d’affaires devra être supérieur à 200 Le nombre d’employés devra être renseigné et
 * être strictement supérieur à zéro
 */
@TestInstance(Lifecycle.PER_CLASS)
class ClientTest {

    Adresse adresse = new Adresse("4", "rue de Berne", "68000", "COLMAR");
    Client instance = new Client("ClientTest", adresse,
            "0633710842", "nordine.sefroun@laposte.net", "commentaire",
            220, 14);


    @ParameterizedTest(name = "Cas OK")
    @DisplayName("✅ Test de succès pour setChiffreAffaire")
    @ValueSource(longs = {230})
    void setChiffreAffaireOk(long input) {

        assertDoesNotThrow(() -> {
            instance.setChiffreAffaire( input);
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
    }

    @ParameterizedTest(name = "Cas OK")
    @DisplayName("✅ Test de succès pour setNombreEmployes")
    @ValueSource(ints = {15})
    void setNombreEmployesOk(int input) {

        assertDoesNotThrow(() -> {
            instance.setNombreEmployes(input);
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
    }

    @ParameterizedTest(name = "Cas KO")
    @DisplayName("❌ Test d'échec pour setNombreEmployes")
    @ValueSource(ints = {0})
    void setNombreEmployesKo(int input) {

        assertThrows(MandatoryDataException.class, () -> {
            instance.setNombreEmployes(input);
        }, "Tous les champs sont obligatoires  : le champs nombre d'employés n'est pas renseigné");
    }

    @ParameterizedTest(name = "Cas KO")
    @DisplayName("❌ Test d'échec pour setChiffreAffaire")
    @ValueSource(longs = {190})
    void setChiffreAffaireKo(long input) {

        // WHEN / THEN
        assertThrows(donneeException.class, () -> {
            instance.setChiffreAffaire(input);
        }, "le chiffre d'affaire doit être supérieur à 200"
                );
    }
}