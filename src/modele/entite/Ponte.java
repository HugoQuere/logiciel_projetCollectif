/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.sql.Date;
import java.sql.Time;


/**
 *
 * @author hugo
 */
public class Ponte {
    
    private int idPonte;
    private Palmipede palmipede;
    private Nid nid;
    private Date datePonte;
    private Time heureDebutPonte;
    private Time heureFinPonte;
    private boolean presenceOeuf;
    private boolean oeufCollecte;

    public Ponte(int idPonte, Palmipede palmipede, Nid nid, Date datePonte, Time heureDebutPonte, Time heureFinPonte, boolean presenceOeuf, boolean oeufCollecte) {
        this.idPonte = idPonte;
        this.palmipede = palmipede;
        this.nid = nid;
        this.datePonte = datePonte;
        this.heureDebutPonte = heureDebutPonte;
        this.heureFinPonte = heureFinPonte;
        this.presenceOeuf = presenceOeuf;
        this.oeufCollecte = oeufCollecte;
    }

    public int getIdPonte() {
        return idPonte;
    }

    public void setIdPonte(int idPonte) {
        this.idPonte = idPonte;
    }

    public Palmipede getPalmipede() {
        return palmipede;
    }

    public void setPalmipede(Palmipede palmipede) {
        this.palmipede = palmipede;
    }

    public Nid getNid() {
        return nid;
    }

    public void setNid(Nid nid) {
        this.nid = nid;
    }

    public Date getDatePonte() {
        return datePonte;
    }

    public void setDatePonte(Date datePonte) {
        this.datePonte = datePonte;
    }

    public Time getHeureDebutPonte() {
        return heureDebutPonte;
    }

    public void setHeureDebutPonte(Time heureDebutPonte) {
        this.heureDebutPonte = heureDebutPonte;
    }

    public Time getHeureFinPonte() {
        return heureFinPonte;
    }

    public void setHeureFinPonte(Time heureFinPonte) {
        this.heureFinPonte = heureFinPonte;
    }

    public boolean isPresenceOeuf() {
        return presenceOeuf;
    }

    public void setPresenceOeuf(boolean presenceOeuf) {
        this.presenceOeuf = presenceOeuf;
    }

    public boolean isOeufCollecte() {
        return oeufCollecte;
    }

    public void setOeufCollecte(boolean oeufCollecte) {
        this.oeufCollecte = oeufCollecte;
    }

    
    
}
