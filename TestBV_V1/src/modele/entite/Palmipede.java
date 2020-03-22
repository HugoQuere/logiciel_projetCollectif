/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.sql.Date;


/**
 *
 * @author hugo
 */
public class Palmipede {
    
    private int idPalmipede;
    private Enclos enclos;
    private int numRFID;
    private Date dateEntree;
    private Date dateSortie;

    public Palmipede(int idPalmipede, int numRFID, Date dateEntree, Date dateSortie, Enclos enclos) {
        this.idPalmipede = idPalmipede;
        this.enclos = enclos;
        this.numRFID = numRFID;
        this.dateEntree = dateEntree;
        this.dateSortie = dateSortie;
    }

    public int getIdPalmipede() {
        return idPalmipede;
    }

    public void setIdPalmipede(int idPalmipede) {
        this.idPalmipede = idPalmipede;
    }

    public Enclos getEnclos() {
        return enclos;
    }

    public void setEnclos(Enclos enclos) {
        this.enclos = enclos;
    }

    public int getNumRFID() {
        return numRFID;
    }

    public void setNumRFID(int numRFID) {
        this.numRFID = numRFID;
    }

    public Date getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(Date dateEntree) {
        this.dateEntree = dateEntree;
    }

    public Date getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    
    
}
