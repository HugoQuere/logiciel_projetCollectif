/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.util.List;
import modele.dao.exception.ErreurMiseAjourException;
import modele.dao.exception.ErreurSauvegardeException;
import modele.dao.exception.ErreurSuppressionException;
import modele.entite.Nid;
import modele.entite.Enclos;

/**
 *
 * @author hugo
 */
public interface NidDao {
    
    Nid find(int idNid);
    
    void insert(Nid unNid) throws ErreurSauvegardeException;

    List<Nid> findAll();
    
    List<Nid> findByEnclos(Enclos unEnclos);

    void update(Nid unNid) throws ErreurMiseAjourException;

    void delete(Nid unNid) throws ErreurSuppressionException;
    
    void deleteByEnclos(Enclos unEnclos) throws ErreurSuppressionException;
    
}
