package fr.cda.java.AccesDonnees.factory;

import fr.cda.java.AccesDonnees.DaoInterface;
import fr.cda.java.AccesDonnees.daoImplementation.mongo.MongoAdresseDao;
import fr.cda.java.AccesDonnees.daoImplementation.mongo.MongoClientDao;
import fr.cda.java.AccesDonnees.daoImplementation.mongo.MongoContratDao;
import fr.cda.java.AccesDonnees.daoImplementation.mongo.MongoProspectDao;
import fr.cda.java.gestionErreurs.Exceptions.TreatedException;

/**
 * MySqlDaoFactory
 *
 * <p>Description : …</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 21/01/2026
 */
public class MongoDaoFactory extends AbstractDaoFactory {

    @Override
    public DaoInterface getClientDao() throws TreatedException {
        return getInstance(MongoClientDao.class);
    }

    @Override
    public DaoInterface getProspectDao() throws TreatedException {
        return getInstance(MongoProspectDao.class);
    }

    @Override
    public DaoInterface getContratDao() throws TreatedException {
        return getInstance(MongoContratDao.class);
    }

    @Override
    public DaoInterface getAdresseDao() throws TreatedException {
        return getInstance(MongoAdresseDao.class);
    }
}
