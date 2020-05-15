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
public class ProblemePalmipede extends Probleme{
    private int idPalmipede;

    public ProblemePalmipede(int idProbleme, int idPalmipede,int idCategorie, String commentaire, Date dateCreation, Date dateResolution) {
        super(idProbleme, idCategorie, commentaire, dateCreation, dateResolution);
        this.idPalmipede = idPalmipede;
    }

    public int getIdPalmipede() {
        return idPalmipede;
    }

    public void setIdPalmipede(int idPalmipede) {
        this.idPalmipede = idPalmipede;
    }
    
}
