/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entiteAffichage;

/**
 *
 * @author hugo
 */
public class VisualisationTableau1 {
    String nomBatiment;
    String nomEnclos;
    int nbOeufs;

    public VisualisationTableau1(String nomBatiment, String nomEnclos, int nbOeufs) {
        this.nomBatiment = nomBatiment;
        this.nomEnclos = nomEnclos;
        this.nbOeufs = nbOeufs;
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

    public int getNbOeufs() {
        return nbOeufs;
    }

    public void setNbOeufs(int nbOeufs) {
        this.nbOeufs = nbOeufs;
    }
    
    
}
