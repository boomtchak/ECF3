package fr.cda.java.utilitaire;

/**
 * Gestion des langues et du fichier associé.
 */
public enum Langue {

    FR("i18n/fr.properties"),
    US("i18n/us.properties");


   private String url;

    Langue(String url) {
        this.url = url;
    }

    /**
     * @return url description
     */
    public String getUrl() {
        return url;
    }


}
