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
import modele.entite.CategorieProbleme;

/**
 *
 * @author d-dja
 */
public interface CategorieProblemeDao {
    CategorieProbleme find(int idCategorieProbleme);
    
    void insert(CategorieProbleme uneCategorie) throws ErreurSauvegardeException;

    List<CategorieProbleme> findAll();
    
    void update(CategorieProbleme uneCategorie) throws ErreurMiseAjourException;

    void delete(CategorieProbleme uneCategorie) throws ErreurSuppressionException;
}
