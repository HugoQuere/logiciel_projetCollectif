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
import modele.entite.Enclos;

/**
 *
 * @author hugo
 */
public interface EnclosDao {
    
    Enclos find(int idCage);
    
    void insert(Enclos uneCage) throws ErreurSauvegardeException;

    List<Enclos> findAll();

    void update(Enclos uneCage) throws ErreurMiseAjourException;

    void delete(Enclos uneCage) throws ErreurSuppressionException;
    
    
}
