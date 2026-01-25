package fr.cda.java.AccesDonnees.factory;

import fr.cda.java.AccesDonnees.DaoInterface;
import fr.cda.java.AccesDonnees.daoImplementation.mySql.MySqlAdresseDao;
import fr.cda.java.AccesDonnees.daoImplementation.mySql.MySqlClientDao;
import fr.cda.java.AccesDonnees.daoImplementation.mySql.MySqlContratDao;
import fr.cda.java.AccesDonnees.daoImplementation.mySql.MySqlProspectDao;
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
public class MySqlDaoFactory extends AbstractDaoFactory {

    @Override
    public DaoInterface getClientDao() throws TreatedException {
        return getInstance(MySqlClientDao.class);
    }

    @Override
    public DaoInterface getProspectDao() throws TreatedException {
        return getInstance(MySqlProspectDao.class);
    }

    @Override
    public DaoInterface getContratDao() throws TreatedException {
        return getInstance(MySqlContratDao.class);
    }

    @Override
    public DaoInterface getAdresseDao() throws TreatedException {
        return getInstance(MySqlAdresseDao.class);
    }
}
