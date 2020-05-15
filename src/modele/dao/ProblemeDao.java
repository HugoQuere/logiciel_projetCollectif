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
import modele.entite.Probleme;

/**
 *
 * @author d-dja
 */
public interface ProblemeDao {
    Probleme find(int idProbleme);
    
    void insert(Probleme unProbleme) throws ErreurSauvegardeException;

    List<Probleme> findAll();
    
    void update(Probleme unProbleme) throws ErreurMiseAjourException;

    void delete(Probleme unProbleme) throws ErreurSuppressionException;
}
