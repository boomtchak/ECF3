package fr.cda.java.AccesDonnees.dao;

import fr.cda.java.model.gestion.Client;
import java.util.List;

/**
 * ClientDao
 *
 * <p>Description : …</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 15/01/2026
 */
public class ClientDao implements DaoInterface<Client> {

    @Override
    public Client create(Client entite) {
        return null;
    }

    @Override
    public Client getById(int id) {
        return null;
    }

    @Override
    public void update(Client entite) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Client> findAll() {
        return List.of();
    }
}
