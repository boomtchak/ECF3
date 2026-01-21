package fr.cda.java.AccesDonnees.dao;

import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.gestionErreurs.TreatedExceptionService;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe qui permet a chaques dao crée d'être safe. Peut aussi permettre à un appel a la même
 * interface de gerer plusieurs types d'objet selon Attention, pour contrat on a besoin du
 * getByClientId !! pour  adresse on a besoin du getBySocieteId !!
 *
 */
public interface DaoInterface<T> {


    static final TreatedExceptionService gestionDesErreurs = new TreatedExceptionService();

    /**
     * Crée un objet et retourne sa nouvelle version (avec l'id)
     *
     * @param entite
     * @return l'objet maj
     */
    T create(T entite);

    /**
     * Récupère un objet selon son id
     *
     * @param id
     * @return
     */
    T getById(int id) throws TreatedException, SQLException;

    /**
     * Met à jour un objet.rien à renvoyer.
     *
     * @param objet
     */
    void update(T entite) throws SQLException, TreatedException;

    /**
     *
     * supprime un objet selon son id.
     *
     * @param id
     */
    void delete(int id);

    /**
     * retourne la liste complete.
     *
     * @return la liste complete.
     */
    List<T> findAll();
}
