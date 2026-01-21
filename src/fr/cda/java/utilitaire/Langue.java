package fr.cda.java.utilitaire;

public enum Langue {

    FR("dataProperties/fr.properties"),
    US("dataProperties/us.properties");


    String url = "dataProperties/fr.properties";

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
