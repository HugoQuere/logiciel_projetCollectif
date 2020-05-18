/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.sql.Date;

/**
 *
 * @author d-dja
 */
public abstract class Probleme {
    private int idProbleme;
    private int idCategorie;
    private String commentaire;
    private Date dateCreation;
    private Date dateResolution;

    public Probleme(int idProbleme, int idCategorie, String commentaire, Date dateCreation, Date dateResolution) {
        this.idProbleme = idProbleme;
        this.idCategorie = idCategorie;
        this.commentaire = commentaire;
        this.dateCreation = dateCreation;
        this.dateResolution = dateResolution;
    }

    public int getIdProbleme() {
        return idProbleme;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public Date getDateResolution() {
        return dateResolution;
    }

    public void setIdProbleme(int idProbleme) {
        this.idProbleme = idProbleme;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setDateResolution(Date dateResolution) {
        this.dateResolution = dateResolution;
    }
    
    
}
