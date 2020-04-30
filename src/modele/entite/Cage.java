/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

/**
 *
 * @author hugo
 */
public class Cage {
    
    private int idCage;
    private int numCage;
    private Enclos enclos;
    private int zone;

    public Cage(int idCage, int numCage, Enclos enclos, int zone) {
        this.idCage = idCage;
        this.numCage = numCage;
        this.enclos = enclos;
        this.zone = zone;
    }

    public int getIdCage() {
        return idCage;
    }

    public void setIdCage(int idCage) {
        this.idCage = idCage;
    }

    public int getNumCage() {
        return numCage;
    }

    public void setNumCage(int numCage) {
        this.numCage = numCage;
    }

    public Enclos getEnclos() {
        return enclos;
    }

    public void setEnclos(Enclos enclos) {
        this.enclos = enclos;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    
   
    
}
