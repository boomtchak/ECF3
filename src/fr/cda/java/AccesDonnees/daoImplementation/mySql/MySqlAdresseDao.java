package fr.cda.java.AccesDonnees.daoImplementation.mySql;

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
public class MySqlAdresseDao implements DaoInterface<Adresse> {


    @Override
    public Adresse create(Adresse entite) {
        return null;
    }


    @Override
    public Adresse getById(int id) throws TreatedException {
        Adresse adresse = null;
        String query = "select *  from TABLE ( Adresse )  where id = ?";
        try (PreparedStatement stmt = Connexion.getConnection().prepareStatement(query)) {
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
        }
        if (adresse == null) {
            throw gestionDesErreurs.handleException(
                    new NotFoundException(LabelManager.get("aucunedonnee")));
        }
        return adresse;
    }

    @Override
    public void update(Adresse entite) throws  TreatedException {
        String query = "update adresse set  nomDeRue=? , numeroDeRue = ?, ville =, codePostal = where id = ?";
        try (PreparedStatement stmt = Connexion.getConnection().prepareStatement(query)) {
            // stmt.setInt(1, "id");
            ResultSet rs = stmt.executeQuery(query);

            stmt.setString(1, entite.getNomDeRue());
            stmt.setString(2, entite.getNumeroDeRue());
            stmt.setString(3, entite.getVille());
            stmt.setString(4, entite.getCodePostal());
            stmt.setInt(5, entite.getIdentifiant());

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
    public void delete(int id) throws TreatedException {
        String query = "delete from adresse where id = ?";
        try (PreparedStatement stmt = Connexion.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery(query);
            stmt.setInt(1, id);
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
    public List<Adresse> findAll() {
        String query = "delete from adresse where id = ?";
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
    }

    List<Adresse> getBySocieteId() {
        return List.of();
    }
}
