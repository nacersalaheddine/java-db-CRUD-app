package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import beans.Entity;

public class DAOFactory {

    private static final String PROPERTIES_FILE       = "dao/dao.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_USERNAME        = "username";
    private static final String PROPERTY_PASSWORD        = "password";

    private String url;
    private String username;
    private String password;

    DAOFactory( String url, String username, String password ) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /*
     * 
     * Responsible method for loading connection details for the database
     * 
     */
    public static DAOFactory getInstance() throws Exception{
        Properties properties = new Properties();
        String url;
        String driver;
        String username;
        String password;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream( PROPERTIES_FILE );

        if ( fichierProperties == null ) {
           System.err.println("Can't find the properties\"" + PROPERTIES_FILE + "\" file.");
        }

        try {
            properties.load( fichierProperties );
            url = properties.getProperty( PROPERTY_URL );
            driver = properties.getProperty( PROPERTY_DRIVER );
            username = properties.getProperty( PROPERTY_USERNAME );
            password = properties.getProperty( PROPERTY_PASSWORD );
            System.out.println(url);
            System.out.println(driver);
            System.out.println(username);
            System.out.println(password);
        } catch ( IOException e ) {
            System.err.println( "Impossible de charger le fichier properties " + PROPERTIES_FILE+ e );
            throw e;
        }

        try {
            Class.forName( driver );
        } catch ( ClassNotFoundException e ) {
            System.err.println( "Le driver est introuvable dans le classpath."+ e );
            throw e;
        }

        DAOFactory instance = new DAOFactory( url, username, password );
        return instance;
    }

    /* Méthode chargée de fournir une connexion à la base de données */
     /* package */ 
     Connection getConnection() throws SQLException {

        return DriverManager.getConnection(url, username, password );
    }

    /*
     * Méthodes de récupération de l'implémentation des différents DAO (un seul
     * pour le moment)
     */
    public DaoInterface<Entity> getEntityDao() {
        return new EntityDaoImpl(this);
    }
}