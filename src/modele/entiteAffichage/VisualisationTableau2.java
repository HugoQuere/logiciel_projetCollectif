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
public class VisualisationTableau2 {
    
    private int numCage;
    private int nbOeufs;

    public VisualisationTableau2(int numCage, int nbOeufs) {
        this.numCage = numCage;
        this.nbOeufs = nbOeufs;
    }

    public int getNumCage() {
        return numCage;
    }

    public void setNumCage(int numCage) {
        this.numCage = numCage;
    }

    public int getNbOeufs() {
        return nbOeufs;
    }

    public void setNbOeufs(int nbOeufs) {
        this.nbOeufs = nbOeufs;
    }
    
}
