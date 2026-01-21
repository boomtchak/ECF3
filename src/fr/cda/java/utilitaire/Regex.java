package fr.cda.java.utilitaire;

/**
 * Librairie de constantes centralisant les expressions régulières utilisées dans l'application.
 */
public class Regex {

    /**
     * Validation générique d'email (format 99%).
     */
    public static String EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    /**
     * Code Postal Français (Métropole, 5 chiffres stricts).
     */
    public static String POSTAL_CODE_FR_SIMPLE = "^\\d{5}$";

    /**
     * Code Postal Français (Étendu : inclut 2A, 2B, DOM-TOM).
     */
    public static String POSTAL_CODE_FR_EXTENDED = "^((0[1-9])|([1-8]\\d)|(9[0-5])|(9[78]\\d)|(2[ABab]))\\d{3}$";

    /**
     * Téléphone Français (10 chiffres, accepte 01-07/09, avec séparateurs optionnels).
     */
    public static String PHONE_FR_SIMPLE = "^0[1-79]([\\s.-]?\\d{2}){4}$";

    /**
     * Format E.164 international simplifié (ex: +33123456789).
     */
    public static String PHONE_INTERNATIONAL_E164 = "^\\+\\d{1,15}$";

    /**
     * évite les codes postaux fantaisistes (ex: 00000, 99999). vérifie que le département existe
     * (de 01 à 98, incluant Monaco et DOM-TOM).
     */
    public static String CODE_POSTAL_EXISTANT = "/^(?:(?:0[1-9]|[1-8]\\d|9[0-5])\\d{3}|97[1-8]\\d{2}|98\\d{2})$/";

    /**
     *
     * je cherche sur le net les regex. Pour MySQL : on capture ce qui est après le point dans les
     * guillemets de la clé
     */
    public static final String MYSQL_FIELD_EXTRACTOR = "for key '.*\\.(.*)'";

    /**
     * je cherche sur le net les regex. Pour MongoDB : on capture le nom de l'index avant le suffixe
     * de direction (_1)
     */
    public static final String MONGO_FIELD_EXTRACTOR = "index: (.*?)_";
}