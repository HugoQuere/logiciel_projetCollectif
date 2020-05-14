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
public class CategorieProbleme {
    
    private int idCategorie;
    private String typeProbleme;

    public CategorieProbleme(int idCategorie, String typeProbleme) {
        this.idCategorie = idCategorie;
        this.typeProbleme = typeProbleme;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public String getTypeProbleme() {
        return typeProbleme;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public void setTypeProbleme(String typeProbleme) {
        this.typeProbleme = typeProbleme;
    }
    
    
}
