package fr.cda.java.AccesDonnees.daoImplementation.mySql;

import fr.cda.java.AccesDonnees.Connexion;
import fr.cda.java.AccesDonnees.DaoInterface;
import fr.cda.java.gestionErreurs.Exceptions.NotFoundException;
import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.gestionErreurs.Logger.AppLogger;
import fr.cda.java.model.gestion.Adresse;
import fr.cda.java.utilitaire.LabelManager;
import fr.cda.java.utilitaire.TypeBDD;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public class MySqlAdresseDao implements DaoInterface<Adresse> {


    @Override
    public Adresse create(Adresse entite) throws TreatedException {

        String query = "INSERT INTO Adresse ( numeroDeRue, nomDeRue, codePostal, ville) VALUES"
                + "(?,?, ?, ?)";
        try (PreparedStatement stmt = Connexion.getConnection()
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entite.getNumeroDeRue());
            stmt.setString(2, entite.getNomDeRue());
            stmt.setString(3, entite.getCodePostal());
            stmt.setString(4, entite.getVille());
            int rs = stmt.executeUpdate();
            try (ResultSet rs4 = stmt.getGeneratedKeys()) {
                if (rs4.next()) {
                    entite.setIdentifiant(rs4.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw gestionDesErreurs.handleException(e, TypeBDD.MYSQL);
            if(AppLogger)

        } catch (FileNotFoundException e) {
            throw gestionDesErreurs.handleException(e);
        } catch (IOException e) {
            throw gestionDesErreurs.handleException(e);
        } catch (Exception e) {
            throw gestionDesErreurs.handleException(e);
        }
        return entite;
    }


    @Override
    public void update(Adresse entite) throws TreatedException {
        String query = "update adresse set  nomDeRue=? , numeroDeRue = ?, ville =?, codePostal = ? where id = ?";
        try (PreparedStatement stmt = Connexion.getConnection().prepareStatement(query)) {
            stmt.setString(1, entite.getNomDeRue());
            stmt.setString(2, entite.getNumeroDeRue());
            stmt.setString(3, entite.getVille());
            stmt.setString(4, entite.getCodePostal());
            stmt.setInt(5, entite.getIdentifiant());
            int rs = stmt.executeUpdate();

        } catch (SQLException e) {
            throw gestionDesErreurs.handleException(e, TypeBDD.MYSQL);
        } catch (FileNotFoundException e) {
            throw gestionDesErreurs.handleException(e);
        } catch (IOException e) {
            throw gestionDesErreurs.handleException(e);
        } catch (Exception e) {
            throw gestionDesErreurs.handleException(e);
        }
    }

    @Override
    public Adresse getById(int id) throws TreatedException {
        Adresse adresse = null;
        String query = "select *  from Adresse where Id_Adresse = ?";
        try (PreparedStatement stmt = Connexion.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                adresse = new Adresse(rs.getInt("Id_Adresse"),
                        rs.getString("numeroDeRue"),
                        rs.getString("nomDeRue"),
                        rs.getString("codePostal"),
                        rs.getString("ville"));
            }
        } catch (SQLException e) {
            throw gestionDesErreurs.handleException(e, TypeBDD.MYSQL);
        } catch (FileNotFoundException e) {
            throw gestionDesErreurs.handleException(e);
        } catch (IOException e) {
            throw gestionDesErreurs.handleException(e);
        } catch (Exception e) {
            throw gestionDesErreurs.handleException(e);
        }
        if (adresse == null) {
            throw gestionDesErreurs.handleException(
                    new NotFoundException(LabelManager.get("aucuneDonnee")));
        }
        return adresse;
    }

    @Override
    public List<Adresse> findAll() throws TreatedException {
        String query = "select *  from adresse";
        List<Adresse> liste = new ArrayList<>();
        try (Statement stmt = Connexion.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Adresse adresse = new Adresse(rs.getInt("Id_Adresse"),
                        rs.getString("numeroDeRue"),
                        rs.getString("nomDeRue"),
                        rs.getString("codePostal"),
                        rs.getString("ville"));
                liste.add(adresse);
            }
        } catch (SQLException e) {
            throw gestionDesErreurs.handleException(e, TypeBDD.MYSQL);
        } catch (FileNotFoundException e) {
            throw gestionDesErreurs.handleException(e);
        } catch (IOException e) {
            throw gestionDesErreurs.handleException(e);
        } catch (Exception e) {
            throw gestionDesErreurs.handleException(e);
        }
        return liste;
    }

    @Override
    public void delete(int id) throws TreatedException {
        String query = "delete from adresse where Id_Adresse = ?";
        try (PreparedStatement stmt = Connexion.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw gestionDesErreurs.handleException(e, TypeBDD.MYSQL);
        } catch (FileNotFoundException e) {
            throw gestionDesErreurs.handleException(e);
        } catch (IOException e) {
            throw gestionDesErreurs.handleException(e);
        } catch (Exception e) {
            throw gestionDesErreurs.handleException(e);
        }
    }

    @Override
    public boolean nameExist(String raisonSociale) {
        return false;
    }
}
