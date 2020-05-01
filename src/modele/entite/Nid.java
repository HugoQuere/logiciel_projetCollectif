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
public class Nid {
    
    private int idNid;
    private int numNid;
    private Enclos enclos;
    private int zone;

    public Nid(int idNid, int numNid, Enclos enclos, int zone) {
        this.idNid = idNid;
        this.numNid = numNid;
        this.enclos = enclos;
        this.zone = zone;
    }

    public int getIdNid() {
        return idNid;
    }

    public void setIdNid(int idNid) {
        this.idNid = idNid;
    }

    public int getNumNid() {
        return numNid;
    }

    public void setNumNid(int numNid) {
        this.numNid = numNid;
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
