/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author hugo
 */
public abstract class DbDao {
    
    private static Properties myProperties = null;

    public DbDao(Properties props) {
        if (myProperties == null) {
            myProperties = props;
            try {
                Class.forName(props.getProperty("db.driver"));
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                throw new RuntimeException("driver non trouvé");
            }
        }
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(myProperties.getProperty("db.url"),
                myProperties.getProperty("db.username"), myProperties.getProperty("db.password"));
    }

}
