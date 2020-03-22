/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleur;

import java.util.List;
import modele.dao.BatimentDao;
import modele.dao.CageDao;
import modele.dao.DaoFactory;
import modele.dao.EnclosDao;
import modele.dao.PalmipedeDao;
import modele.dao.PonteDao;
import modele.dao.db.DbFactoryDao;
import modele.entite.Batiment;
import modele.entite.Cage;
import modele.entite.Enclos;
import modele.entite.Palmipede;
import modele.entite.Ponte;

/**
 *
 * @author hugo
 */
public class Controlleur {
    
    private final BatimentDao monBatimentDAO;
    private final CageDao maCageDAO;
    private final EnclosDao monEnclosDAO;
    private final PalmipedeDao monPalmipedeDAO;
    private final PonteDao maPonteDAO;

    public Controlleur() {
        
        DaoFactory myFactory = DbFactoryDao.getInstance();
        
        this.monBatimentDAO = myFactory.getBatimentDao();
        this.maCageDAO = myFactory.getCageDao();
        this.monEnclosDAO = myFactory.getEnclosDao();
        this.monPalmipedeDAO = myFactory.getPalmipedeDao();
        this.maPonteDAO = myFactory.getPonteDao();
        
        testFindAll();
    }
    
    private void testFindAll(){
        
        List<Batiment> mesBatiments=this.monBatimentDAO.findAll();
        for(int i=0; i<mesBatiments.size(); i++){
            Batiment unBatiment = mesBatiments.get(i);
            System.out.println("Nom Batiment : "+unBatiment.getNomBatiment());
        }
        if(mesBatiments.size()==0){
            System.out.println("Liste de batiment vide\n");
        }
        
        
        
        List<Enclos> mesEnclos = this.monEnclosDAO.findAll();
        for(int i=0; i<mesEnclos.size(); i++){
            Enclos unEnclos = mesEnclos.get(i);
            System.out.println("Nom Enclos : "+unEnclos.getNomEnclos()+" , dans le batiment: "+unEnclos.getBatiment().getNomBatiment());
        }
        if(mesEnclos.size()==0){
            System.out.println("Liste d'enclos vide\n");
        }
        
        
        
        List<Cage> mesCages = this.maCageDAO.findAll();
        for(int i=0; i<mesCages.size(); i++){
            Cage uneCage = mesCages.get(i);
            System.out.println("Id Cage : "+uneCage.getIdBox()+" , dans le batiment: "+uneCage.getEnclos().getNomEnclos());
        }
        if(mesCages.size()==0){
            System.out.println("Liste de cages vides\n");
        }
        
        
        List<Palmipede> mesPalmipedes = this.monPalmipedeDAO.findAll();
        for(int i=0; i<mesPalmipedes.size(); i++){
            Palmipede unPalmipede = mesPalmipedes.get(i);
            System.out.println("RFID palmipede : "+unPalmipede.getNumRFID()+" , entree le: "+ unPalmipede.getDateEntree().toString() + ", vit dans l'enclos: "+ unPalmipede.getEnclos().getNomEnclos());
        }
        if(mesPalmipedes.size()==0){
            System.out.println("Liste de cages vides\n");
        }
        
        List<Ponte> mesPontes = this.maPonteDAO.findAll();
        for(int i=0; i<mesPontes.size(); i++){
            Ponte unePonte = mesPontes.get(i);
            System.out.println("Date ponte : "+unePonte.getDatePonte().toString()+" , pondu par: "+ unePonte.getPalmipede().getIdPalmipede() + ", dans la cage: "+ unePonte.getCage().getIdBox());
        }
        if(mesPontes.size()==0){
            System.out.println("Liste de cages vides\n");
        }
    }
    
}
