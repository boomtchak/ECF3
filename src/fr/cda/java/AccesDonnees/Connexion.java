package fr.cda.java.AccesDonnees;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DriverManager
 *
 * <p>Description : …</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 15/01/2026
 */
public class Connexion{

    private static Connexion instance;

    private Connection connection;

    /**
     * constructeur privé, tout ce qu'on peut appeler, c'est getConnexion. il possede une seule instance, contenant une connection.
     */
    private Connexion() throws IOException, SQLException {

        // on lit les valeurs du fichier, pas à pas, le but c'est de bien comprendre
        // copier coller du cours
        Properties dataProperties =  getDatabaseProperties();
        String url =  dataProperties.getProperty("url");
        String login =           dataProperties.getProperty("login");
        String password =         dataProperties.getProperty("password");


       // ici on crée l'object instance
        connection = DriverManager.getConnection(url, login, password);

    }

    /**
     *  Si on a déjà instancié connexion
     *  connection existe et vaut quelque chose,
     * on renvoie la connection, sinon il va d'abord la crée en passant par le constructeur.
     */
    public static Connection getConnection() throws SQLException,IOException  {

    if (instance == null ||  instance.connection == null ||  instance.connection.isClosed()) {
        instance = new Connexion();
    }
        return instance.connection;
}

    /**
     * // on lit les valeurs du fichier, pas à pas, le but c'est de bien comprendre
     *
     * @return l'url attendue par le DriverManager
     */
    private Properties getDatabaseProperties() throws IOException {
        File fichier = new File("dataProperties/database.properties");
        FileInputStream input = new FileInputStream(fichier);
        Properties dataProperties = new Properties();
        dataProperties.load(input);
        return dataProperties;
}


}
