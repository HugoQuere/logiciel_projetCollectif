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
public class PagePontesTableau { 

    private int numRFID;
    private String nomBatiment;
    private String nomEnclos;
    private int nbPontes;
    private float nbPontesParJour;
    private int tempsPrecensePontes;

    public PagePontesTableau(int numRFID, String nomBatiment, String nomEnclos, int nbPontes, float nbPontesParJour, int tempsPrecensePontes) {
        this.numRFID = numRFID;
        this.nomBatiment = nomBatiment;
        this.nomEnclos = nomEnclos;
        this.nbPontes = nbPontes;
        this.nbPontesParJour = nbPontesParJour;
        this.tempsPrecensePontes = tempsPrecensePontes;
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

    public float getNbPontesParJour() {
        return nbPontesParJour;
    }

    public void setNbPontesParJour(float nbPontesParJour) {
        this.nbPontesParJour = nbPontesParJour;
    }

    public int getTempsPrecensePontes() {
        return tempsPrecensePontes;
    }

    public void setTempsPrecensePontes(int tempsPrecensePontes) {
        this.tempsPrecensePontes = tempsPrecensePontes;
    }

    
    
}
