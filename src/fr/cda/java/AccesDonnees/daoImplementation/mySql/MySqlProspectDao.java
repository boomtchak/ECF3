package fr.cda.java.AccesDonnees.daoImplementation.mySql;

import fr.cda.java.AccesDonnees.Connexion;
import fr.cda.java.AccesDonnees.DaoInterface;
import fr.cda.java.gestionErreurs.Exceptions.NotFoundException;
import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.model.gestion.Prospect;
import fr.cda.java.utilitaire.Interet;
import fr.cda.java.utilitaire.LabelManager;
import fr.cda.java.utilitaire.TypeBDD;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * MongoProspectDao
 *
 * <p>Description : …</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 16/01/2026
 */
public class MySqlProspectDao implements DaoInterface<Prospect> {

    @Override
    public Prospect create(Prospect entite) throws TreatedException {

        // TODO configurer le saut de ligne formatage intellij je n'ai pas trouvé de solution.
        String query =
                "INSERT INTO "
                        + "Prospect (raisonSocialeProspect, telephoneProspect, adresseMailProspect"
                        + ", commentaire, dateProspection, interet,  Id_Adresse) VALUES"
                        + "(?, ?,?, "
                        + "?, ?, ?, ?)";
        try (PreparedStatement stmt = Connexion.getConnection()
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entite.getRaisonSociale());
            stmt.setString(2, entite.getTelephone());
            stmt.setString(3, entite.getAdresseMail());
            stmt.setString(4, entite.getCommentaire());
            stmt.setDate(5, Date.valueOf(entite.getDateProspection()));
            stmt.setBoolean(6, entite.getInteret().equals(Interet.OUI));
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
    public void update(Prospect entite) throws TreatedException {
        String query =
                "update Prospect set raisonSocialeProspect =?, telephoneProspect=?, adresseMailProspect=?"
                        + ", commentaire=?,dateProspection=?, interet = ?,Id_Adresse=?";
        try (PreparedStatement stmt = Connexion.getConnection().prepareStatement(query)) {

            stmt.setString(1, entite.getRaisonSociale());
            stmt.setString(2, entite.getTelephone());
            stmt.setString(3, entite.getAdresseMail());
            stmt.setString(4, entite.getCommentaire());
            stmt.setDate(5, Date.valueOf(entite.getDateProspection()));
            stmt.setBoolean(6, entite.getInteret().equals(Interet.OUI));
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
    public Prospect getById(int id) throws TreatedException {
        Prospect prospect = null;
        String query = "select *  from Prospect   where Id_Prospect = ?";
        try (PreparedStatement stmt = Connexion.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                prospect = new Prospect(
                        id,
                        rs.getString("raisonSocialeProspect"),
                        rs.getString("telephoneProspect"),
                        rs.getString("adresseMailProspect"),
                        rs.getString("commentaire"),
                        rs.getDate("dateProspection").toLocalDate(),
                        rs.getBoolean("interet") ? Interet.OUI : Interet.NON);

                prospect.setIdAdresse(rs.getInt("Id_Adresse"));
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
        if (prospect == null) {
            throw gestionDesErreurs.handleException(
                    new NotFoundException(LabelManager.get("aucuneDonnee")));
        }
        return prospect;
    }

    @Override
    public List<Prospect> findAll() throws TreatedException {
        String query = "select *  from Prospect";
        List<Prospect> liste = new ArrayList<>();
        try (Statement stmt = Connexion.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Prospect prospect = new Prospect(
                        rs.getInt("Id_Prospect"),
                        rs.getString("raisonSocialeProspect"),
                        rs.getString("telephoneProspect"),
                        rs.getString("adresseMailProspect"),
                        rs.getString("commentaire"),
                        rs.getDate("dateProspection").toLocalDate(),
                        rs.getBoolean("interet") ? Interet.OUI : Interet.NON);
                prospect.setIdAdresse(rs.getInt("Id_Adresse"));
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
        String query = "delete from Prospect where Id_Prospect = ?";
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
        String query = "SELECT 1 FROM Prospect WHERE raisonSocialeProspect = ?";

        try (Connection con = Connexion.getConnection();
                PreparedStatement stmt = con.prepareStatement(query)) {

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
