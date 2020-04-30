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
    
    private int idBox;
    private int numBox;
    private Enclos enclos;

    public Cage(int idBox, int numBox, Enclos enclos) {
        this.idBox = idBox;
        this.numBox = numBox;
        this.enclos = enclos;
    }

    public int getIdBox() {
        return idBox;
    }

    public void setIdBox(int idBox) {
        this.idBox = idBox;
    }

    public int getNumBox() {
        return numBox;
    }

    public void setNumBox(int numBox) {
        this.numBox = numBox;
    }

    public Enclos getEnclos() {
        return enclos;
    }

    public void setEnclos(Enclos enclos) {
        this.enclos = enclos;
    }

    
    
}
