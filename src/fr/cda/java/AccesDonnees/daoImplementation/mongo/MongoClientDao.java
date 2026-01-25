package fr.cda.java.AccesDonnees.daoImplementation.mongo;

import fr.cda.java.AccesDonnees.DaoInterface;
import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.model.gestion.Client;
import java.util.List;

/**
 * MySqlClientDao
 *
 * <p>Description : …</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 15/01/2026
 */
public class MongoClientDao implements DaoInterface<Client> {

    @Override
    public Client create(Client entite) {
        return null;
    }

    @Override
    public Client findById(int id) {
        return null;
    }

    @Override
    public void save(Client entite) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Client> findAll() {
        return List.of();
    }

    @Override
    public boolean nameExist(String raisonSociale) throws TreatedException {
        return false;
    }
}
