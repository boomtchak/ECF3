import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import fr.cda.java.model.gestion.Adresse;
import fr.cda.java.model.gestion.Prospect;
import fr.cda.java.utilitaire.Interet;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 *
 */
@TestInstance(Lifecycle.PER_CLASS)
class ProspectTest {

    Adresse adresse = new Adresse(1, "4", "rue de Berne", "68000", "COLMAR");
    Prospect instance = new Prospect(1, "ProspectTest", adresse,
            "0633710842", "nordine.sefroun@laposte.net", "commentaire", LocalDate.now(),
            Interet.OUI);


    @Test
    @DisplayName("✅ Test de succès pour setDateProspection")
    void setDateProspectionOk() {
        java.time.LocalDate input = LocalDate.now();
        assertDoesNotThrow(() -> {
            instance.setDateProspection(LocalDate.now());
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
        input = null;
        assertDoesNotThrow(() -> {
            instance.setDateProspection(LocalDate.now());
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
    }

    @ParameterizedTest
    @DisplayName("✅ Test de succès pour setInteret")
    @ValueSource(strings = {"OUI", "NON", "INCONNU"})
    void setInteretOk(String interet) {
        Interet input = Interet.valueOf(interet);
        assertDoesNotThrow(() -> {
            instance.setInteret(input);
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
    }


}