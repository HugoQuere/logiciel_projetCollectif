/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

/**
 *
 * @author d-dja
 */
public class CodeErreur {
    private int idCodeErreur;
    private String description;
    private int idCategorie;

    public CodeErreur(int idCodeErreur, String description, int idCategorie) {
        this.idCodeErreur = idCodeErreur;
        this.description = description;
        this.idCategorie = idCategorie;
    }

    public int getIdCodeErreur() {
        return idCodeErreur;
    }

    public String getDescription() {
        return description;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCodeErreur(int idCodeErreur) {
        this.idCodeErreur = idCodeErreur;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }
    
    
}
