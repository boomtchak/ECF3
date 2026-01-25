package fr.cda.java.AccesDonnees;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *  Permet de tester la connexion à la base (idéal dans un manuel du nouvel arrivant.doc)
 *
 *  Ceci est hors ECF, j'y ait trouvé une utilité pour mes besoins futurs
 *
 *  TODO : integrer l
 *
 */
class ConnexionTest {


    /**
     * Appel à vide.
      */
    public static void main(String[] args) {
        new ConnexionTest().okConnection();
    }

    /**
     * Soit ca fonctionne, soit ca nous indique le probleme.
     */
    void okConnection(){
        try{
            Connection connection = Connexion.getConnection();
            assert ( connection != null && !connection.isClosed());
            System.out.println("SUCCÈS : Connexion établie et active.");

        } catch (FileNotFoundException exception) {
            System.out.println("Attention, ton fichier n'est pas la ou tu l'as promis!");
        } catch (IOException IOException) {
            System.out.println("Erreur lors de la lecture des properties");
        } catch (SQLException exception) {
            System.out.println("Erreur lors de la connection à la base");
        } catch (Exception exception) {
            System.out.println("Erreur inattendue ");
        }
    }



}