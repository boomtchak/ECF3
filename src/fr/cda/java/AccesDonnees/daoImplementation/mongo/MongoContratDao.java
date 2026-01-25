package fr.cda.java.AccesDonnees.daoImplementation.mongo;

import fr.cda.java.AccesDonnees.DaoInterface;
import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
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
public class MongoContratDao implements DaoInterface<Contrat> {



    @Override
    public Contrat create(Contrat entite) {



        return null;
    }

    @Override
    public Contrat findById(int id) {
        return null;
    }

    @Override
    public void save(Contrat entite) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Contrat> findAll() {
        return List.of();
    }

    @Override
    public boolean nameExist(String raisonSociale) throws TreatedException {
        return false;
    }

    List<Contrat> getByClientId(int clientId){
        return List.of();
    }
}
