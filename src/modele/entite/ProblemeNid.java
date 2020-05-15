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
public class ProblemeNid extends Probleme{
    
    private int idNid;
    
    public ProblemeNid(int idProbleme, int idNid,int idCategorie, String commentaire, Date dateCreation, Date dateResolution) {
        super(idProbleme, idCategorie, commentaire, dateCreation, dateResolution);
        this.idNid = idNid;
    }

    public int getIdNid() {
        return idNid;
    }

    public void setIdNid(int idNid) {
        this.idNid = idNid;
    }
    
}
