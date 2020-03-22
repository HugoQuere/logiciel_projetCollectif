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
import modele.entite.Ponte;

/**
 *
 * @author hugo
 */
public interface PonteDao {
    
    Ponte find(int idPonte);
    
    void insert(Ponte unePonte) throws ErreurSauvegardeException;

    List<Ponte> findAll();

    void update(Ponte unePonte) throws ErreurMiseAjourException;

    void delete(Ponte unePonte) throws ErreurSuppressionException;
    
}
