package fr.cda.java.AccesDonnees.Services;

import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.utilitaire.LabelManager;
import fr.cda.java.utilitaire.Regex;
import fr.cda.java.utilitaire.Severite;
import fr.cda.java.utilitaire.TypeBDD;
import fr.cda.java.utilitaire.TypeErreur;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TeatedExceptionServic
 *
 * <p>Description :  3 situation :
 * l'utilisateur a fait une erreur de saisie métier -> erreur metier  + log leger pour les stats
 * l'utilisateur a faire une erreur de saisie technique -> erreur database + log leger pour les
 * stats la jvm a crash ou que sais je -> log severe + message a l'utilisateur si l'app est encore
 * debout la base de donnée est down -> log severe + proposition a l'utilisateur de changer de
 * sgbd.
 * </p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 19/01/2026
 */
public class TreatedExceptionService {


    /**
     * pour les erreurs sql, on sait pas le type d'erreur, on doit trouver la base de donnée par
     * l'appelant.
     *
     * @param ex
     * @return
     */
    public TreatedException handleException(SQLException ex, TypeBDD typeBDD) {
        TreatedException retour = null;

        if (typeBDD != null) {

            String field = getFieldFromMessage(ex.getMessage(), typeBDD);

            if (typeBDD.equals(TypeBDD.MYSQL)) {
                switch (ex.getErrorCode()) {
                    case 1062:
                        retour = new TreatedException(LabelManager.get("unicite"),
                                Severite.FAIBLE,
                                TypeErreur.DB_MODEL);
                        break;
                    case 1406:
                        retour = new TreatedException(LabelManager.get("longueur", field),
                                Severite.FAIBLE,
                                TypeErreur.DB_MODEL);
                        break;
                    case 1451:
                        retour = new TreatedException(LabelManager.get("IntegriteContrat"),
                                Severite.FAIBLE,
                                TypeErreur.DB_MODEL);
                        break;
                    case 1264:
                        retour = new TreatedException(LabelManager.get("ValeurNonAutorisee", field),
                                Severite.FAIBLE,
                                TypeErreur.DB_MODEL);
                        break;
                    case 1048:
                        retour = new TreatedException(LabelManager.get("champsObligatoire", field),
                                Severite.FAIBLE,
                                TypeErreur.DB_MODEL);
                        break;
                    default:
                        retour = new TreatedException(LabelManager.get("inconnu"),
                                Severite.FAIBLE,
                                TypeErreur.DB_MODEL);
                        break;
                }
            } else if (typeBDD.equals(TypeBDD.MONGO)) {
                switch (ex.getErrorCode()) {
                    case 11000:
                        retour = new TreatedException(LabelManager.get("unicite"),
                                Severite.FAIBLE,
                                TypeErreur.DB_MODEL);
                        break;
                    case 121:

                        if (ex.getMessage().contains("comparison failed")) {
                            retour = new TreatedException(
                                    LabelManager.get("ValeurNonAutorisee", field),
                                    Severite.FAIBLE,
                                    TypeErreur.DB_MODEL);
                        } else if (ex.getMessage().contains("maxLength failed")) {
                            retour = new TreatedException(LabelManager.get("Longueur", field),
                                    Severite.FAIBLE,
                                    TypeErreur.DB_MODEL);
                        } else if (ex.getMessage().contains("required")) {
                            retour = new TreatedException(
                                    LabelManager.get("champsObligatoire", field),
                                    Severite.FAIBLE,
                                    TypeErreur.DB_MODEL);
                        }
                        break;
                    default:
                        retour = new TreatedException(LabelManager.get("inconnu"),
                                Severite.FAIBLE,
                                TypeErreur.DB_MODEL);
                }

            }


        }
        return retour;

    }

    /**
     * Pour la plupart des exception, on doit confirmer le type d'erreur, pour savoir comment agir.
     *
     * @param ex
     * @return l'exception traitée
     */
    public TreatedException handleException(Exception ex) {
        switch (ex) {

            case FileNotFoundException s -> {
                return new TreatedException(LabelManager.get("fichierIntrouvable"),
                        Severite.ELEVEE, TypeErreur.APP_TECH);
            }
            case IOException s -> {
                return new TreatedException(
                        LabelManager.get(""),
                        Severite.ELEVEE, TypeErreur.APP_TECH);
            }
            case ReflectiveOperationException s -> {
                return new TreatedException(
                        LabelManager.get("erreurInterne"),
                        Severite.ELEVEE, TypeErreur.APP_TECH);
            }
            default -> {
                return new TreatedException(LabelManager.get("inconnue"), Severite.ELEVEE,
                        TypeErreur.APP_TECH);
            }
        }
    }

    /**
     * Méthode utilitaire de récupération des champs (regex magique)
     */
    private String getFieldFromMessage(String message, TypeBDD typeBDD) {
        String field = LabelManager.get("dbInfoInexistante");
        // ca peut être null, les batchs sortent de notre périmètre.
        Pattern pattern = typeBDD.equals(TypeBDD.MYSQL) ? java.util.regex.Pattern.compile(
                Regex.MYSQL_FIELD_EXTRACTOR) :
                typeBDD.equals(TypeBDD.MONGO) ? java.util.regex.Pattern.compile(
                        Regex.MONGO_FIELD_EXTRACTOR) : null;
        if (pattern != null) {

            if (message != null) {
                Matcher matcher = pattern.matcher(message);

                if (matcher.find()) {
                    field = matcher.group(1);
                }
            }
        }
        return field;
    }
}
