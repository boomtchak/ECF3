package fr.cda.java.AccesDonnees.dao;

import fr.cda.java.model.gestion.Contrat;
import java.util.List;

/**
 * ContratDao
 *
 * <p>Description : …</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 15/01/2026
 */
public class ContratDao implements DaoInterface<Contrat> {



    @Override
    public Contrat create(Contrat entite) {



        return null;
    }

    @Override
    public Contrat getById(int id) {
        return null;
    }

    @Override
    public void update(Contrat entite) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Contrat> findAll() {
        return List.of();
    }

    List<Contrat> getByClientId(int clientId){
        return List.of();
    }
}
