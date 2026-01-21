import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fr.cda.java.gestionErreurs.Exceptions.MandatoryDataException;
import fr.cda.java.gestionErreurs.Exceptions.RegexException;
import fr.cda.java.model.gestion.Adresse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tous les champs de l’adresse devront être renseignés Le code postal sera vérifié par un Regex (il
 * devra comporter 5 chiffres)
 */
@TestInstance(Lifecycle.PER_CLASS)
class AdresseTest {

    Adresse instance = new Adresse(1, "4", "rue de Berne", "68000", "COLMAR");


    @ParameterizedTest(name = "Cas OK")
    @DisplayName("✅ Test de succès pour setNumeroDeRue")
    @ValueSource(strings = {"12"})
    void setNumeroDeRueOk(String input) {

        assertDoesNotThrow(() -> {
            instance.setNumeroDeRue(input);
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
    }

    @ParameterizedTest(name = "Cas OK")
    @DisplayName("✅ Test de succès pour setNomDeRue")
    @ValueSource(strings = {"rue des rues"})
    void setNomDeRueOk(String input) {

        assertDoesNotThrow(() -> {
            instance.setNomDeRue(input);
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
    }

    @ParameterizedTest(name = "Cas OK")
    @DisplayName("✅ Test de succès pour setCodePostal")
    @ValueSource(strings = {"67800"})
    void setCodePostalOk(String input) {

        assertDoesNotThrow(() -> {
            instance.setCodePostal(input);
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
    }

    @ParameterizedTest(name = "Cas OK")
    @DisplayName("✅ Test de succès pour setVille")
    @ValueSource(strings = {"LAVILLE"})
    void setVilleOk(String input) {

        assertDoesNotThrow(() -> {
            instance.setVille(input);
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
    }

    @Test
    @DisplayName("❌ Test d'échec pour setVille")
    void setVilleKo() {

        assertThrows(MandatoryDataException.class, () -> {
            instance.setVille("");
        }, "Tous les champs sont obligatoires  : le champs ville n'est pas renseigné");
    }

    @ParameterizedTest(name = "Cas KO")
    @DisplayName("❌ Test d'échec pour setCodePostal")
    @ValueSource(strings = {"999999", "123456"})
    void setCodePostalKo(String input) {

        assertThrows(RegexException.class, () -> {
            instance.setCodePostal(input);
        }, "Le champ code postal ne respecte pas le format requis");

        assertThrows(MandatoryDataException.class, () -> {
            instance.setCodePostal(null);
        }, "Tous les champs sont obligatoires  : le champs code postal n'est pas renseigné");
    }

    @Test
    @DisplayName("❌ Test d'échec pour setNomDeRue")
    void setNomDeRueKo() {

        assertThrows(MandatoryDataException.class, () -> {
            instance.setVille("");
        }, "Tous les champs sont obligatoires  : le champs nom de la rue n'est pas renseigné");
    }

    @Test
    @DisplayName("❌ Test d'échec pour setNumeroDeRue")
    void setNumeroDeRueKo() {

        assertThrows(MandatoryDataException.class, () -> {
            instance.setVille("");
        }, "Tous les champs sont obligatoires  : le champs numero de la rue n'est pas renseigné");
    }

}