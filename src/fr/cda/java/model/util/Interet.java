package fr.cda.java.model.util;

/**
 * permet de définir l'interet d'un prospect, et le cas ou ce n'est pas renseigné.
 */
public enum Interet {
    // Les différentes valeurs possibles
    OUI("oui"),
    NON("non"),
    INCONNU("");


    /**
     * Un champ pour stocker le nom "propre"
     *
     */
    private final String affichage;

    /**
     *
     * Le constructeur (toujours privé pour une enum)
     *
     * @param privé
     * @return
     */
    Interet(String affichage) {
        this.affichage = affichage;
    }

    /**
     * $ Elle sera appelée automatiquement pour afficher le texte.
     *
     */
    @Override
    public String toString() {
        return this.affichage;
    }


}
