package fr.cda.java.AccesDonnees.dao;

import fr.cda.java.AccesDonnees.Connexion;
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
import java.util.List;

/**
 * AdresseDao
 *
 * <p>Description : …</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 15/01/2026
 */
public class AdresseDao implements DaoInterface<Adresse> {


    @Override
    public Adresse create(Adresse entite) {
        return null;
    }


    @Override
    public Adresse getById(int id) throws TreatedException, SQLException {
        Adresse adresse = null;
        PreparedStatement stmt = null;
        String query = "select *  from TABLE ( Adresse )  where id = ?";
        try {
            Connection con = Connexion.getConnection();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery(query);
            adresse = new Adresse(rs.getInt("Id_Adresse"),
                    rs.getString("numeroDeRue"),
                    rs.getString("nomDeRue"),
                    rs.getString("codePostal"),
                    rs.getString("ville"));
        } catch (SQLException e) {
            throw gestionDesErreurs.handleException(e, TypeBDD.MYSQL);
        } catch (FileNotFoundException e) {
            throw gestionDesErreurs.handleException(e);
        } catch (IOException e) {
            throw gestionDesErreurs.handleException(e);
        } catch (Exception e) {
            throw gestionDesErreurs.handleException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }

            if (adresse == null) {
                throw gestionDesErreurs.handleException(
                        new NotFoundException(LabelManager.get("aucunedonnee")));
            }
        }
        return adresse;
    }

    @Override
    public void update(Adresse entite) throws SQLException, TreatedException {
        PreparedStatement stmt = null;
        String query = "update adresse set  nomDeRue=? , numeroDeRue = ?, ville =, codePostal = where id = ?";
        try {
            Connection con = Connexion.getConnection();
            stmt = con.prepareStatement(query);
            // stmt.setInt(1, "id");
            ResultSet rs = stmt.executeQuery(query);
/*
            stmt.setString(1, entite.get);
            stmt.setString(2 , entite);
            stmt.setString(3, entite);
            stmt.setString(4, entite);
            stmt.setString(5, entite);
            stmt.setInt(5, entite);

            new Adresse(rs.getInt(entite),
                    rs.getString(entite),
                    rs.getString("nomDeRue"),
                    rs.getString("codePostal"),
                    rs.getString("ville"));
*/

            Adresse adresse = new Adresse(rs.getInt("Id_Adresse"),
                    rs.getString("numeroDeRue"),
                    rs.getString("nomDeRue"),
                    rs.getString("codePostal"),
                    rs.getString("ville"));
        } catch (SQLException e) {
            throw gestionDesErreurs.handleException(e, TypeBDD.MYSQL);
        } catch (FileNotFoundException e) {
            throw gestionDesErreurs.handleException(e);
        } catch (IOException e) {
            throw gestionDesErreurs.handleException(e);
        } catch (Exception e) {
            throw gestionDesErreurs.handleException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }

        }
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Adresse> findAll() {
        return List.of();
    }

    List<Adresse> getBySocieteId() {
        return List.of();
    }
}
