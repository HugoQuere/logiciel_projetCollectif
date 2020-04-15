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
    private Enclos enclos;

    public Cage(int idBox, Enclos enclos) {
        this.idBox = idBox;
        this.enclos = enclos;
    }

    public int getIdBox() {
        return idBox;
    }

    public void setNumBox(int idBox) {
        this.idBox = idBox;
    }

    public Enclos getEnclos() {
        return enclos;
    }

    public void setEnclos(Enclos enclos) {
        this.enclos = enclos;
    }
  
    
    
}
