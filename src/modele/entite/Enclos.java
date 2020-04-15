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
public class Enclos {
    
    private int idEnclos;
    private String nomEnclos;
    private Batiment batiment;

    public Enclos(int idEnclos, String nomEnclos, Batiment batiment) {
        this.idEnclos = idEnclos;
        this.nomEnclos = nomEnclos;
        this.batiment = batiment;
    }

    public int getIdEnclos() {
        return idEnclos;
    }

    public void setIdEnclos(int idEnclos) {
        this.idEnclos = idEnclos;
    }

    public String getNomEnclos() {
        return nomEnclos;
    }

    public void setNomEnclos(String nomEnclos) {
        this.nomEnclos = nomEnclos;
    }

    public Batiment getBatiment() {
        return batiment;
    }

    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }

    
    
}
