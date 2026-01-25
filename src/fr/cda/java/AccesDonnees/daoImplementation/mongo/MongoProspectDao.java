package fr.cda.java.AccesDonnees.daoImplementation.mongo;

import fr.cda.java.AccesDonnees.DaoInterface;
import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import java.util.List;

/**
 * MongoProspectDao
 *
 * <p>Description : …</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 16/01/2026
 */
public class MongoProspectDao implements DaoInterface {

    @Override
    public Object create(Object entite) {
        return null;
    }

    @Override
    public Object findById(int id) {
        return null;
    }

    @Override
    public void save(Object entite) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List findAll() {
        return List.of();
    }


    @Override
    public boolean nameExist(String raisonSociale) throws TreatedException {
        return false;
    }
}
