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
    
    private int numNid;
    private int nbOeufs;

    public VisualisationTableau2(int numNid, int nbOeufs) {
        this.numNid = numNid;
        this.nbOeufs = nbOeufs;
    }

    public int getNumNid() {
        return numNid;
    }

    public void setNumNid(int numNid) {
        this.numNid = numNid;
    }

    public int getNbOeufs() {
        return nbOeufs;
    }

    public void setNbOeufs(int nbOeufs) {
        this.nbOeufs = nbOeufs;
    }

    
    
}
