/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import modele.entite.ProblemeNid;

/**
 *
 * @author d-dja
 */
public interface ProblemeNidDao extends ProblemeDao{
    ProblemeNid findByNid(int idProbleme);
}
