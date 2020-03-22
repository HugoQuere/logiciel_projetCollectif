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
import modele.entite.Enclos;

/**
 *
 * @author hugo
 */
public interface CageDao {
    
    Cage find(int idCage);
    
    void insert(Cage uneCage) throws ErreurSauvegardeException;

    List<Cage> findAll();
    
    List<Cage> findByEnclos(Enclos unEnclos);

    void update(Cage uneCage) throws ErreurMiseAjourException;

    void delete(Cage uneCage) throws ErreurSuppressionException;
    
    void deleteByEnclos(Enclos unEnclos) throws ErreurSuppressionException;
    
}
