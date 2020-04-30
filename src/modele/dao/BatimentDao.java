/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.util.List;
import javafx.collections.ObservableList;
import modele.dao.exception.ErreurMiseAjourException;
import modele.dao.exception.ErreurSauvegardeException;
import modele.dao.exception.ErreurSuppressionException;
import modele.entite.Batiment;

/**
 *
 * @author hugo
 */
public interface BatimentDao {
    Batiment find(int idBatiment);
    
    void insert(Batiment unBatiment) throws ErreurSauvegardeException;

    List<Batiment> findAll();
    
    void update(Batiment unBatiment) throws ErreurMiseAjourException;

    void delete(Batiment unBatiment) throws ErreurSuppressionException;
}
