/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entiteAffichage;

import java.sql.Date;

/**
 * Classe uniquement utilisé pour le tableau de fécondité
 */
public class PalmipedeTableau { 

    private int numRFID;
    private String nomBatiment;
    private String nomEnclos;
    private int nbPontes;
    private Date dateEntree;
    private Date dateSortie;

    public PalmipedeTableau(int numRFID, String nomBatiment, String nomEnclos, int nbPontes, Date dateEntree, Date dateSortie) {
        this.numRFID = numRFID;
        this.nomBatiment = nomBatiment;
        this.nomEnclos = nomEnclos;
        this.nbPontes = nbPontes;
        this.dateEntree = dateEntree;
        this.dateSortie = dateSortie;
    }

    public int getNumRFID() {
        return numRFID;
    }

    public void setNumRFID(int numRFID) {
        this.numRFID = numRFID;
    }

    public String getNomBatiment() {
        return nomBatiment;
    }

    public void setNomBatiment(String nomBatiment) {
        this.nomBatiment = nomBatiment;
    }

    public String getNomEnclos() {
        return nomEnclos;
    }

    public void setNomEnclos(String nomEnclos) {
        this.nomEnclos = nomEnclos;
    }

    public int getNbPontes() {
        return nbPontes;
    }

    public void setNbPontes(int nbPontes) {
        this.nbPontes = nbPontes;
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
