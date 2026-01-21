import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fr.cda.java.gestionErreurs.Exceptions.MandatoryDataException;
import fr.cda.java.gestionErreurs.Exceptions.RegexException;
import fr.cda.java.gestionErreurs.Exceptions.UniciteException;
import fr.cda.java.model.gestion.Client;
import fr.cda.java.model.gestion.Prospect;
import fr.cda.java.model.gestion.Adresse;
import fr.cda.java.utilitaire.Interet;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * La raison sociale devra être saisie La raison sociale devra être unique. Il ne pourra pas y avoir
 * 2 sociétés qui aient le même nom, que ce soit un client ou un prospect Le téléphone devra être
 * renseigné et être vérifié par un Regex L’adresse mail devra être renseignée et être vérifiée par
 * un Regex Les commentaires ne seront pas obligatoires
 */
@TestInstance(Lifecycle.PER_CLASS)
class SocieteTest {

    Adresse adresse = new Adresse(1, "4", "rue de Berne", "68000", "COLMAR");
    Client instance = new Client( "raisonSociale", adresse,
            "0633710842", "nordine.sefroun@laposte.net", "commentaire",
            10000, 250);


    @BeforeEach
    void setUp() {
    }

    @ParameterizedTest(name = "Cas OK")
    @DisplayName("✅ Test de succès pour setRaisonSociale")
    @ValueSource(strings = {"testOk"})
    void setRaisonSocialeOk(String input) {
        Adresse adresse = new Adresse(1, "4", "rue de Berne", "68000", "COLMAR");
        Client instanceClient = new Client("raison Sociale", adresse,
                "0633710842", "nordine.sefroun@laposte.net", "commentaire",
                220, 14);

        //on en profite pour tester que le test d'unicité ne saute pas quand on met la même raison sociale.
        assertDoesNotThrow(() -> {
            instanceClient.setRaisonSociale(input);
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
    }


    @ParameterizedTest(name = "Cas OK")
    @DisplayName("✅ Test de succès pour setTelephone")
    @ValueSource(strings = {"0389791040"})
    void setTelephoneOk(String input) {

        assertDoesNotThrow(() -> {
            instance.setTelephone(input);
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
    }

    @ParameterizedTest(name = "Cas OK")
    @DisplayName("✅ Test de succès pour setAdresseMail")
    @ValueSource(strings = {"nordine.sefroun@laposte.net"})
    void setAdresseMailOk(String input) {

        assertDoesNotThrow(() -> {
            instance.setAdresseMail(input);
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
    }

    @ParameterizedTest(name = "Cas OK")
    @DisplayName("✅ Test de succès pour setCommentaire")
    @ValueSource(strings = {"commentaire", ""})
    void setCommentaireOk(String input) {

        assertDoesNotThrow(() -> {
            instance.setCommentaire(input);
        }, "Le setter ne devrait pas lever d'exception pour une entrée valide : " + input);
    }


    @Test
    @DisplayName("❌ Test d'échec pour setAdresseMail")
    void setAdresseMailKo() {
        assertThrows(RegexException.class, () -> {
            instance.setAdresseMail("tatatitatata");
        }, "Le champ adresse mail ne respecte pas le format requis"
                + "tatatitatata");

        assertThrows(MandatoryDataException.class, () -> {
            instance.setAdresseMail("");
        }, "Tous les champs sont obligatoires  : le champs adresse mail n'est pas renseigné");
    }

    @ParameterizedTest(name = "Cas KO")
    @DisplayName("❌ Test d'échec pour setTelephone")
    @ValueSource(strings = {"03546413", "0000002200", ""})
    void setTelephoneKo(String input) {

        if (input.isEmpty()) {

            assertThrows(MandatoryDataException.class, () -> {
                instance.setTelephone(input);
            }, "Tous les champs sont obligatoires  : le champs téléphone n'est pas renseigné");
        } else {
            assertThrows(RegexException.class, () -> {
                instance.setTelephone(input);
            }, "Le champ téléphone ne respecte pas le format requis"
                    + input);
        }
    }


    @Test
    @DisplayName("❌ Test d'échec pour setRaisonSociale")
    void setRaisonSocialeKo() {

        Client client1 = new Client("raisonSociale", adresse,
                "0633710842", "nordine.sefroun@laposte.net", "commentaire",
                220, 14);
        Client client2 = new Client("raisonSociale2", adresse,
                "0633710842", "nordine.sefroun@laposte.net", "commentaire",
                220, 14);
        Prospect prospect1 = new Prospect("raisonSociale3", adresse,
                "0633710842", "nordine.sefroun@laposte.net", "commentaire", LocalDate.now(),
                Interet.OUI);
        Prospect prospect2 = new Prospect("raisonSociale4", adresse,
                "0633710842", "nordine.sefroun@laposte.net", "commentaire", LocalDate.now(),
                Interet.OUI);

        // il faut vérifier, si 2 client on le même nom avec un id différent.
        assertThrows(UniciteException.class, () -> {
            client2.setRaisonSociale("raisonSociale");
        }, "Les raisons sociales sont uniques pour tous les types de sociétés. (raisonSociale)");
        // un prospect a le meme nom qu'un client.
        assertThrows(UniciteException.class, () -> {
            client2.setRaisonSociale("raisonSociale3");
        }, "Les raisons sociales sont uniques pour tous les types de sociétés. (raisonSociale3)");
        // un prospect a le meme nom qu'un client.
        assertThrows(UniciteException.class, () -> {
            prospect1.setRaisonSociale("raisonSociale2");
        }, "Les raisons sociales sont uniques pour tous les types de sociétés. (raisonSociale2)");
        // 2 prospects ont le même nom avec un id différent.
        assertThrows(UniciteException.class, () -> {
            prospect2.setRaisonSociale("raisonSociale3");
        }, "Les raisons sociales sont uniques pour tous les types de sociétés. (raisonSociale3)");
        // 1 client et un prospect avec le même id.
        assertThrows(UniciteException.class, () -> {
            prospect2.setRaisonSociale("raisonSociale");
        }, "Les raisons sociales sont uniques pour tous les types de sociétés. (raisonSociale)");
        assertThrows(MandatoryDataException.class, () -> {
            instance.setRaisonSociale("");
        }, "Tous les champs sont obligatoires  : le champs raison sociale n'est pas renseigné");
    }

}