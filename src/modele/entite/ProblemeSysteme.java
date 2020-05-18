/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.sql.Date;

/**
 *
 * @author d-dja
 */
public class ProblemeSysteme extends Probleme{
    
    public ProblemeSysteme(int idProbleme, int idCategorie, String commentaire, Date dateCreation, Date dateResolution) {
        super(idProbleme, idCategorie, commentaire, dateCreation, dateResolution);
    }
    
}
