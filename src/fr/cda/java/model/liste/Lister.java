package fr.cda.java.model.liste;

import java.util.TreeMap;

public interface Lister<T> {

    TreeMap listeClients = new TreeMap();
    // c'est le point central pour la "persistance"
    int compteurIdentifiant = 1;
    int idInutilise = 0;


    /**
     * ajoute un nouveau client et lui attribut un id.
     * @param entite
     */
    void ajouter(T entite);

    /***
     * remplace un dans la liste en prenant en compte
     * @param index
     * @param entite
     */
    void replace(String index,T entite);

    /**
     * supprime une entité de la liste
     * @param entite
     */
    void supprimer(T entite);


    /**
     *
     * @return  la liste complète*
     */
    TreeMap<String, T> getListeSocietes();


    /**
     *
     * @return le compteur .
     */
    int getCompteurIdentifiant();

}
