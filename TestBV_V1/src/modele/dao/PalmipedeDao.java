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
import modele.entite.Palmipede;

/**
 *
 * @author hugo
 */
public interface PalmipedeDao {
    
    Palmipede find(int idCage);
    
    void insert(Palmipede uneCage) throws ErreurSauvegardeException;

    List<Palmipede> findAll();

    void update(Palmipede uneCage) throws ErreurMiseAjourException;

    void delete(Palmipede uneCage) throws ErreurSuppressionException;
    
}
