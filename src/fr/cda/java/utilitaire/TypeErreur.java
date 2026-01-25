package fr.cda.java.utilitaire;

/**
 * Ici, on définit si on a une erreur Technique avec "une erreur interne s'est produite, contactez
 * un admin ou une erreur fonctionnelle ou on pointe du doigt l'action de l'utilisateur qui pose
 * probleme.
 */
public enum TypeErreur {

    // technique Database
    DB_TECH,
    // technique crash appli
    APP_TECH,
    // fonctionnelle Model
    MODEL,
    // fonctionnelle Metier
    DB_MODEL;


}
