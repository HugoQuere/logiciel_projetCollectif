/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.time.LocalDate;
import java.util.List;
import modele.dao.exception.ErreurMiseAjourException;
import modele.dao.exception.ErreurSauvegardeException;
import modele.dao.exception.ErreurSuppressionException;
import modele.entite.Enclos;
import modele.entite.Palmipede;

/**
 *
 * @author hugo
 */
public interface PalmipedeDao {
    
    Palmipede find(int idPalmipede);
    
    void insert(Palmipede unPalmipede) throws ErreurSauvegardeException;

    List<Palmipede> findAll();
    
    List<Palmipede> findByDateSortie(LocalDate dateSortieMax);
    
    List<Palmipede> findByEnclos(Enclos unEnclos);

    void update(Palmipede unPalmipede) throws ErreurMiseAjourException;

    void delete(Palmipede unPalmipede) throws ErreurSuppressionException;
    
    void deleteByEnclos(Enclos unEnclos) throws ErreurSuppressionException;
    
}
