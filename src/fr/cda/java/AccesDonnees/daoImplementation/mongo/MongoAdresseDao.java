package fr.cda.java.AccesDonnees.daoImplementation.mongo;

import fr.cda.java.AccesDonnees.Connexion;
import fr.cda.java.AccesDonnees.DaoInterface;
import fr.cda.java.gestionErreurs.Exceptions.NotFoundException;
import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.model.gestion.Adresse;
import fr.cda.java.utilitaire.LabelManager;
import fr.cda.java.utilitaire.TypeBDD;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * MySqlAdresseDao
 *
 * <p>Description : …</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 15/01/2026
 */
public class MongoAdresseDao implements DaoInterface<Adresse> {

    @Override
    public Adresse create(Adresse entite) throws TreatedException {
        return null;
    }

    @Override
    public Adresse getById(int id) throws TreatedException {
        return null;
    }

    @Override
    public void update(Adresse entite) throws TreatedException {

    }

    @Override
    public void delete(int id) throws TreatedException {

    }

    @Override
    public List<Adresse> findAll() throws TreatedException {
        return List.of();
    }

    @Override
    public boolean nameExist(String raisonSociale) throws TreatedException {
        return false;
    }
}
