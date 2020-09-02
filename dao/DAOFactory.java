package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import beans.Entity;

public class DAOFactory {

    private static final String PROPERTIES_FILE       = "dao/dao_sqlite.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_USERNAME        = "username";
    private static final String PROPERTY_PASSWORD        = "password";

    private String url;
    private String username;
    private String password;
    DAOFactory( String url) {
        this.url = url;
    }
    DAOFactory( String url, String username, String password ) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /*
     * 
     *  Method responsible for loading connection details for the database
     * 
     */
    public static DAOFactory getInstance() throws Exception{
        Properties properties = new Properties();
        String url;
        String driver;
        String username;
        String password;
        DAOFactory instance;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream peropertiesFile = classLoader.getResourceAsStream( PROPERTIES_FILE );

        if ( peropertiesFile == null ) {
           System.err.println("Can't find the properties\"" + PROPERTIES_FILE + "\" file.");
        }

        try {
            properties.load( peropertiesFile );
            url = properties.getProperty( PROPERTY_URL );
            driver = properties.getProperty( PROPERTY_DRIVER );
            username = properties.getProperty( PROPERTY_USERNAME );
            password = properties.getProperty( PROPERTY_PASSWORD );
        } catch ( IOException e ) {
            System.err.println( "Error loading the properties file " + PROPERTIES_FILE+ e );
            throw e;
        }
       

        try {
            Class.forName( driver );
        } catch ( ClassNotFoundException e ) {
            System.err.println( "Cant find the driver in classpath."+ e );
            throw e;
        }

        if (PROPERTIES_FILE.equals("dao_mysql.properties")){
             instance = new DAOFactory( url, username, password );
        }else{
             instance = new DAOFactory(url);
        }

        return instance;
    }

    /* Create connection to database */
     /* package */ 
     Connection getConnection() throws SQLException {

        return DriverManager.getConnection(url, username, password );
    }

    /*
     * Dao connector
     */
    public DaoInterface<Entity> getEntityDao() {
        return new EntityDaoImpl(this);
    }
}