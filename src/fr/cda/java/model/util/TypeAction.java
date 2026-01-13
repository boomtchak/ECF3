package fr.cda.java.model.util;

/**
 * GEstion du crud, libellé, boutons et visibilité / disablance des champs.
 */
public enum TypeAction {
    // Les différentes valeurs possibles
    AFFICHER("Affichage d'un"),
    CREATE("Création d'un "),
    UPDATE("Modification d'un "),
    DELETE("Suppression d'un ");


    // Un champ pour stocker le nom "propre"
    private final String affichage;

    // Le constructeur (toujours privé pour une enum)
    TypeAction(String affichage) {
        this.affichage = affichage;
    }

    // Elle sera appelée automatiquement pour afficher le texte.
    @Override
    public String toString() {
        return this.affichage;
    }
}
