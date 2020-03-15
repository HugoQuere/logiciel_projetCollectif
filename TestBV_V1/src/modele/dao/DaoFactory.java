/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

/**
 *
 * @author hugo
 */
public interface DaoFactory {
    
    public BatimentDao getBatimentDao();
    
    public CageDao getCageDao();
    
    public EnclosDao getEnclosDao();
    
    public PalmipedeDao getPalmipedeDao();
    
    public PonteDao getPonteDao();
    
}
