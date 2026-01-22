package fr.cda.java.AccesDonnees.daoImplementation.mySql;

import fr.cda.java.AccesDonnees.Connexion;
import fr.cda.java.AccesDonnees.DaoInterface;
import fr.cda.java.gestionErreurs.Exceptions.NotFoundException;
import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.model.gestion.Client;
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
 * MySqlClientDao
 *
 * <p>Description : …</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 15/01/2026
 */
public class MySqlClientDao implements DaoInterface<Client> {

    @Override
    public Client create(Client entite) throws TreatedException {

        // TODO configurer le saut de ligne formatage intellij je n'ai pas trouvé de solution.
        String query =
                "INSERT INTO "
                        + "Client (raisonSocialeClient, telephoneClient, adresseMailClient"
                        + ", commentaire, chiffreAffaire, nombreEmployes, Id_Adresse) VALUES"
                        + "(?, ?,?, "
                        + "?, ?, ?, ?)";
        try (PreparedStatement stmt = Connexion.getConnection()
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entite.getRaisonSociale());
            stmt.setString(2, entite.getTelephone());
            stmt.setString(3, entite.getAdresseMail());
            stmt.setString(4, entite.getCommentaire());
            stmt.setLong(5, entite.getChiffreAffaire());
            stmt.setInt(6, entite.getNombreEmployes());
            stmt.setInt(7, entite.getAdresse().getIdentifiant());
            stmt.executeUpdate();
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
    public void update(Client entite) throws TreatedException {
        String query =
                "update Client set raisonSocialeClient =?, telephoneClient=?, adresseMailClient=?"
                        + ", commentaire=?, chiffreAffaire=?, nombreEmployes=?, Id_Adresse=?";
        try (PreparedStatement stmt = Connexion.getConnection().prepareStatement(query)) {

            stmt.setString(1, entite.getRaisonSociale());
            stmt.setString(2, entite.getTelephone());
            stmt.setString(3, entite.getAdresseMail());
            stmt.setString(4, entite.getCommentaire());
            stmt.setLong(5, entite.getChiffreAffaire());
            stmt.setInt(6, entite.getNombreEmployes());
            stmt.setInt(7, entite.getAdresse().getIdentifiant());
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
    public Client getById(int id) throws TreatedException {
        Client client = null;
        String query = "select *  from  Client  where Id_Client =?";
        try (PreparedStatement stmt = Connexion.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                client = new Client(
                        id,
                        rs.getString("raisonSocialeClient"),
                        rs.getString("telephoneClient"),
                        rs.getString("adresseMailClient"),
                        rs.getString("commentaire"),
                        rs.getLong("chiffreAffaire"),
                        rs.getInt("nombreEmployes"));
                client.setIdAdresse(rs.getInt("Id_Adresse"));
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
        if (client == null) {
            throw gestionDesErreurs.handleException(
                    new NotFoundException(LabelManager.get("aucuneDonnee")));
        }
        return client;
    }

    @Override
    public List<Client> findAll() throws TreatedException {
        String query = "select *  from Client";
        List<Client> liste = new ArrayList<>();
        try (Statement stmt = Connexion.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Client client = new Client(
                        rs.getInt("Id_Client"),
                        rs.getString("raisonSocialeClient"),
                        rs.getString("telephoneClient"),
                        rs.getString("adresseMailClient"),
                        rs.getString("commentaire"),
                        rs.getLong("chiffreAffaire"),
                        rs.getInt("nombreEmployes"));
                client.setIdAdresse(rs.getInt("Id_Adresse"));
                liste.add(client);
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
        String query = "delete from Client where Id_Client = ?";
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

        boolean retour = false;
        // On demande à MySQL de renvoyer "1" si ça existe. Rien de plus.
        String query = "SELECT 1 FROM Client WHERE raisonSocialeClient = ?";

        try (PreparedStatement stmt = Connexion.getConnection().prepareStatement(query)) {

            stmt.setString(1, raisonSociale);

            try (ResultSet rs = stmt.executeQuery()) {
                // Renvoie true si une ligne a été trouvée.
                retour = rs.next();
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
        return retour;
    }

}
