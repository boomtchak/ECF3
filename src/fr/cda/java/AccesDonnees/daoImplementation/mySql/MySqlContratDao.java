package fr.cda.java.AccesDonnees.daoImplementation.mySql;

import fr.cda.java.AccesDonnees.Connexion;
import fr.cda.java.AccesDonnees.DaoInterface;
import fr.cda.java.gestionErreurs.Exceptions.NotFoundException;
import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.model.gestion.Contrat;
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
 * ContratDao
 *
 * <p>Description : …</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 15/01/2026
 */
public class MySqlContratDao implements DaoInterface<Contrat> {

    @Override
    public Contrat create(Contrat entite) throws TreatedException {

        String query = "INSERT INTO Contrat ( montantContrat, nomContrat, Id_Client) VALUES"
                + "(?,?, ?, ?)";
        try (PreparedStatement stmt = Connexion.getConnection()
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDouble(1, entite.getMontantContrat());
            stmt.setString(2, entite.getNomContrat());
            stmt.setInt(3, entite.getIdentifiantClient());
            int rs = stmt.executeUpdate();
            try (ResultSet rs4 = stmt.getGeneratedKeys()) {
                if (rs4.next()) {
                    entite.setIdentifiant(rs4.getInt(1));
                }
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
        return entite;
    }


    @Override
    public void update(Contrat entite) throws TreatedException {
        String query = "update contrat set  montantContrat=? , nomContrat = ?  where id = ?";
        try (PreparedStatement stmt = Connexion.getConnection().prepareStatement(query)) {
            stmt.setDouble(1, entite.getMontantContrat());
            stmt.setString(2, entite.getNomContrat());
            stmt.setInt(3, entite.getIdentifiant());
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
    public Contrat getById(int id) throws TreatedException {
        Contrat contrat = null;
        String query = "select *  from Contrat  where Id_Contrat = ?";
        try (PreparedStatement stmt = Connexion.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                contrat = new Contrat(rs.getInt("Id_Client"),
                        rs.getString("nomContrat"),
                        rs.getDouble("montantContrat"));
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
        if (contrat == null) {
            throw gestionDesErreurs.handleException(
                    new NotFoundException(LabelManager.get("aucuneDonnee")));
        }
        return contrat;
    }

    @Override
    public List<Contrat> getByParentId(int idClient) throws TreatedException {
        Contrat contrat = null;
        String query = "select *  from Contrat  where Id_Client = ?";
        List<Contrat> liste = new ArrayList<>();
        try (Statement stmt = Connexion.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                contrat = new Contrat(rs.getInt("Id_Client"),
                        rs.getString("nomContrat"),
                        rs.getDouble("montantContrat"));
                liste.add(contrat);
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
        if (liste.isEmpty()) {
            throw gestionDesErreurs.handleException(
                    new NotFoundException(LabelManager.get("aucuneDonnee")));
        }
        return liste;
    }

    @Override
    public List<Contrat> findAll() throws TreatedException {
        String query = "select *  from contrat";
        List<Contrat> liste = new ArrayList<>();
        try (Statement stmt = Connexion.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Contrat contrat = new Contrat(rs.getInt("Id_Client"),
                        rs.getString("nomContrat"),
                        rs.getDouble("montantContrat"));
                liste.add(contrat);
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
        String query = "delete from contrat where Id_Contrat =?";
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
    public boolean nameExist(String raisonSociale) throws TreatedException {
        return false;
    }
}
