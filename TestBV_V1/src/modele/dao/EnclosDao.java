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
    
    Enclos find(int idEnclos);
    
    void insert(Enclos unEnclos) throws ErreurSauvegardeException;

    List<Enclos> findAll();

    void update(Enclos unEnclos) throws ErreurMiseAjourException;

    void delete(Enclos unEnclos) throws ErreurSuppressionException;
    
    
}
