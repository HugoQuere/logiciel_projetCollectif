/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import modele.dao.BatimentDao;
import modele.dao.DaoFactory;
import modele.dao.EnclosDao;
import modele.dao.PalmipedeDao;
import modele.dao.PonteDao;
import modele.dao.NidDao;

/**
 *
 * @author hugo
 */
public class DbFactoryDao implements DaoFactory{

    private static DbFactoryDao instance;
    private Properties props;
    
    private DbFactoryDao(){
        FileInputStream in = null;
        try{
            in = new FileInputStream(System.getProperty("dbFileConfig"));
            this.props = new Properties();
            this.props.load(in);
        }
        catch(FileNotFoundException ex){
            System.err.println("Impossible d'ouvrir le fichier de configuration");
        }
        catch(IOException ex){
            System.err.println("Impossible de lire le fichier de configuration");
        }
        finally{
            try{
                in.close();
            }
            catch(IOException ex){
            }
        }
    }
    
    
  
    public static DbFactoryDao getInstance(){
        if(instance == null){
            instance = new DbFactoryDao();
        }
        return instance;
    }
    
    
    
    @Override
    public BatimentDao getBatimentDao() {
        return new BatimentDbDao(props);
    }

    @Override
    public NidDao getNidDao() {
        return new NidDbDao(props);
    }

    @Override
    public EnclosDao getEnclosDao() {
        return new EnclosDbDao(props);
    }

    @Override
    public PalmipedeDao getPalmipedeDao() {
        return new PalmipedeDbDao(props);
    }

    @Override
    public PonteDao getPonteDao() {
        return new PonteDbDao(props);
    }
    
}
