package fr.cda.java.utilitaire;


/**
 * GEstion du type de société géré actuellement pour l'affichage et l'enregistrement coté vue.
 */
public enum TypeSociete {
    // Les différentes valeurs possibles
    CLIENT("client"),
    PROSPECT("prospect");


    // Un champ pour stocker le nom "propre"
    private final String affichage;

    // Le constructeur (toujours privé pour une enum)
    TypeSociete(String affichage) {
        this.affichage = affichage;
    }


    /**
     * @return affichage description
     */
    public String getAffichage() {
        return affichage;
    }

    // Elle sera appelée automatiquement pour afficher le texte.
    @Override
    public String toString() {
        return this.affichage;
    }
}
