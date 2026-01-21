package fr.cda.java.utilitaire;

import fr.cda.java.AccesDonnees.factory.AbstractDaoFactory;
import fr.cda.java.AccesDonnees.factory.MongoDaoFactory;
import fr.cda.java.AccesDonnees.factory.MySqlDaoFactory;

/**
 * TypeBDD
 *
 * <p>Description : …</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 20/01/2026
 */
public enum TypeBDD {
    // technique Database
    MYSQL(new MySqlDaoFactory()),
    // technique crash appli
    MONGO(new MongoDaoFactory()),
    // fonctionnelle Model
    JSON(null);

    AbstractDaoFactory daoFactory;


    TypeBDD(AbstractDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * @return mySqlDaoFactory description
     */
    public AbstractDaoFactory getDaoFactory() {
        return this.daoFactory;
    }
}
