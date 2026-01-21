package fr.cda.java.utilitaire;

import fr.cda.java.model.obsolete_liste.Clients;
import fr.cda.java.model.obsolete_liste.Lister;
import fr.cda.java.model.obsolete_liste.Prospects;

/**
 * GEstion du type de société géré actuellement pour l'affichage et l'enregistrement coté vue.
 */
public enum TypeSociete {
    // Les différentes valeurs possibles
    CLIENT("client", Clients.getInstance()),
    PROSPECT("prospect", Prospects.getInstance());


    // Un champ pour stocker le nom "propre"
    private final String affichage;
    // Un champ pour stocker la liste de sociétés
    private final Lister liste;

    // Le constructeur (toujours privé pour une enum)
    TypeSociete(String affichage, Lister liste) {
        this.affichage = affichage;
        this.liste = liste;
    }

    /**
     * @return liste description
     */
    public Lister getListe() {
        return liste;
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
