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
import modele.entite.Cage;
import modele.entite.Palmipede;
import modele.entite.Ponte;

/**
 *
 * @author hugo
 */
public interface PonteDao {
    
    Ponte find(int idPonte);
    
    void insert(Ponte unePonte) throws ErreurSauvegardeException;

    List<Ponte> findAll();
    
    List<Ponte> findByPalmipede(Palmipede unPalmipede) throws ErreurSuppressionException;
    
    List<Ponte> findByCage(Cage uneCage) throws ErreurSuppressionException;

    void update(Ponte unePonte) throws ErreurMiseAjourException;

    void delete(Ponte unePonte) throws ErreurSuppressionException;
    
    void deleteByPalmipede(Palmipede unPalmipede) throws ErreurSuppressionException;
    
    void deleteByCage(Cage uneCage) throws ErreurSuppressionException;
    
}
