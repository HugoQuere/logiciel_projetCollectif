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
    
    List<Ponte> findByPeriod(LocalDate dateDebut, LocalDate dateFin);
    
    List<Ponte> findByPalmipede(Palmipede unPalmipede);
    
    List<Ponte> findByPalmipedeAndPeriod(Palmipede unPalmipede, LocalDate dateDebut, LocalDate dateFin);
    
    List<Ponte> findByCage(Cage uneCage);

    void update(Ponte unePonte) throws ErreurMiseAjourException;

    void delete(Ponte unePonte) throws ErreurSuppressionException;
    
    void deleteByPalmipede(Palmipede unPalmipede) throws ErreurSuppressionException;
    
    void deleteByCage(Cage uneCage) throws ErreurSuppressionException;
    
}
