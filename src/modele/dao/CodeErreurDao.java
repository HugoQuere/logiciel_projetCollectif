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
import modele.entite.CodeErreur;

/**
 *
 * @author d-dja
 */
public interface CodeErreurDao {
    CodeErreur find(int idCodeErreur);
    
    void insert(CodeErreur unCodeErreur) throws ErreurSauvegardeException;

    List<CodeErreur> findAll();
    
    void update(CodeErreur unCodeErreur) throws ErreurMiseAjourException;

    void delete(CodeErreur unCodeErreur) throws ErreurSuppressionException;
}
