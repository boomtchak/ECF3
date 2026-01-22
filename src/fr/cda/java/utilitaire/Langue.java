package fr.cda.java.utilitaire;

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
