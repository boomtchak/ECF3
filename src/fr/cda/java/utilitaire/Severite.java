package fr.cda.java.utilitaire;

import java.awt.Color;

public enum Severite {

    // Batman a choisi des nuances RGB précises pour un rendu UI moderne
    BASIQUE(1, "Basique", new Color(46, 204, 113)),  // Vert Émeraude (Tout va bien)
    FAIBLE(2, "Faible", new Color(52, 152, 219)),    // Bleu (Information)
    MOYENNE(3, "Moyenne", new Color(241, 196, 15)),  // Jaune (Attention)
    ELEVEE(4, "Elevée", new Color(230, 126, 34)),    // Orange (Risque)
    URGENT(5, "Urgent", new Color(231, 76, 60));     // Rouge (Action immédiate !)

    private final int niveau;
    private final String libelle;
    private final Color couleur;


    // Constructeur
    Severite(int niveau, String libelle, Color couleur) {
        this.niveau = niveau;
        this.libelle = libelle;
        this.couleur = couleur;
    }

    // Getters
    public int getNiveau() {
        return niveau;
    }

    public String getLibelle() {
        return libelle;
    }

    public Color getCouleur() {
        return couleur;
    }
}