/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleur;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.dao.BatimentDao;
import modele.dao.CageDao;
import modele.dao.DaoFactory;
import modele.dao.EnclosDao;
import modele.dao.PalmipedeDao;
import modele.dao.PonteDao;
import modele.dao.db.DbFactoryDao;
import modele.dao.exception.ErreurMiseAjourException;
import modele.dao.exception.ErreurSauvegardeException;
import modele.dao.exception.ErreurSuppressionException;
import modele.entite.Batiment;
import modele.entite.Cage;
import modele.entite.Enclos;
import modele.entite.Palmipede;
import modele.entite.Ponte;

/**
 *
 */
public class TestControlleur {
    
    private final BatimentDao monBatimentDAO;
    private final CageDao maCageDAO;
    private final EnclosDao monEnclosDAO;
    private final PalmipedeDao monPalmipedeDAO;
    private final PonteDao maPonteDAO;

    public TestControlleur() {
        
        DaoFactory myFactory = DbFactoryDao.getInstance();
        
        this.monBatimentDAO = myFactory.getBatimentDao();
        this.maCageDAO = myFactory.getCageDao();
        this.monEnclosDAO = myFactory.getEnclosDao();
        this.monPalmipedeDAO = myFactory.getPalmipedeDao();
        this.maPonteDAO = myFactory.getPonteDao();
        
        //testFind();
        //testInsert();
        //testFindAll();
        //testUpdate();
        //testSuppression();
        
        //pontesfindByPalmipedeAndPeriod();
        testFindByPalmipedeAndPeriod();
    }
    
    private void testFind(){
        
        System.out.println("Nom Batiment : "+ this.monBatimentDAO.find(2).getNomBatiment() );
        System.out.println("Id Cage : "+this.maCageDAO.find(1).getIdCage()+" , dans l'enclos: "+this.maCageDAO.find(1).getEnclos().getNomEnclos());
        System.out.println("Nom Enclos : "+this.monEnclosDAO.find(3).getNomEnclos()+" , dans le batiment: "+this.monEnclosDAO.find(3).getBatiment().getNomBatiment());
        System.out.println("RFID palmipede : "+this.monPalmipedeDAO.find(2).getNumRFID()+" , entree le: "+ this.monPalmipedeDAO.find(2).getDateEntree().toString() + ", vit dans l'enclos: "+ this.monPalmipedeDAO.find(2).getEnclos().getNomEnclos());
        System.out.println("Date ponte : "+this.maPonteDAO.find(4).getDatePonte().toString()+" , pondu par: "+ this.maPonteDAO.find(4).getPalmipede().getIdPalmipede() + ", dans la cage: "+ this.maPonteDAO.find(4).getCage().getIdCage());
        
        
    }
    
    private void testInsert(){
        
        try {

            Batiment unBatiment = new Batiment(0, "BatimentTestInsert");
            this.monBatimentDAO.insert(unBatiment);
            
            Enclos unEnclos = new Enclos(0, "EnclosTestInsert", unBatiment);
            this.monEnclosDAO.insert(unEnclos);
            
            Cage uneCage = new Cage(0, 1, unEnclos, 1);
            this.maCageDAO.insert(uneCage);
            
            
            long millis=System.currentTimeMillis();  
            Date dateEntree =new java.sql.Date(millis);  
            Date dateSortie =new java.sql.Date(millis); 
            Palmipede unPalmipede = new Palmipede(0, 999, dateEntree, dateSortie, unEnclos);
            this.monPalmipedeDAO.insert(unPalmipede);
            
            Date datePonte =new java.sql.Date(millis); 
            Ponte unePonte = new Ponte(0, unPalmipede, uneCage, datePonte, true, true);
            this.maPonteDAO.insert(unePonte);
            
        } catch (ErreurSauvegardeException ex) {
            Logger.getLogger(TestControlleur.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
            System.out.println("Id Cage : "+uneCage.getIdCage()+" , dans l'enclos: "+uneCage.getEnclos().getNomEnclos());
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
            System.out.println("Date ponte : "+unePonte.getDatePonte().toString()+" , pondu par: "+ unePonte.getPalmipede().getIdPalmipede() + ", dans la cage: "+ unePonte.getCage().getIdCage());
        }
        if(mesPontes.size()==0){
            System.out.println("Liste de cages vides\n");
        }
    }
    
    
    private void testUpdate(){
        
        try {
            Batiment unBatiment = this.monBatimentDAO.find(2);
            unBatiment.setNomBatiment("TestUpdate");
            this.monBatimentDAO.update(unBatiment);
            
            
            Enclos unEnclosUpdate = new Enclos(0, "EnclosTestInsert", unBatiment);
            this.monEnclosDAO.insert(unEnclosUpdate);
            Cage uneCage = this.maCageDAO.find(1);
            uneCage.setEnclos(unEnclosUpdate);
            this.maCageDAO.update(uneCage);
            
            Enclos unEnclos = this.monEnclosDAO.find(3);
            unEnclos.setNomEnclos("TestUpdate");
            this.monEnclosDAO.update(unEnclos);
            
            Palmipede unPalmipede = this.monPalmipedeDAO.find(2);
            unPalmipede.setEnclos(unEnclos);
            this.monPalmipedeDAO.update(unPalmipede);
            
            Ponte unePonte = this.maPonteDAO.find(4);
            unePonte.setCage(uneCage);
            this.maPonteDAO.update(unePonte);
            
        } catch (ErreurMiseAjourException ex) {
            Logger.getLogger(TestControlleur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ErreurSauvegardeException ex) {
            Logger.getLogger(TestControlleur.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private void testSuppression(){
        //Devrait théoriquement tout supprimé de toutes les tables
        
        List<Batiment> listeBatiment = this.monBatimentDAO.findAll();
        for(Batiment unBatiment : listeBatiment){
            try {
                this.monBatimentDAO.delete(unBatiment);
            } catch (ErreurSuppressionException ex) {
                Logger.getLogger(TestControlleur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*
        List<Palmipede> listePalmipede = this.monPalmipedeDAO.findAll();
        for(Palmipede unPalmipede : listePalmipede){
            try {
                this.monPalmipedeDAO.delete(unPalmipede);
            } catch (ErreurSuppressionException ex) {
                Logger.getLogger(Controlleur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        
    }
    
    private void pontesfindByPalmipedeAndPeriod(){
        
        Date uneDate = new Date(0);
        Batiment unBatiment = new Batiment(1, "batiment");
        Enclos unEnclos = new Enclos(1, "blabla", unBatiment);
        Palmipede unPalmipede = new Palmipede(2, 0, uneDate, uneDate, unEnclos);
        
        
        LocalDate dateDebut = LocalDate.now().minusDays(7);
        LocalDate dateFin = LocalDate.now();
        List<Ponte> listePontes = this.maPonteDAO.findByPalmipedeAndPeriod(unPalmipede, dateDebut, dateFin );
        
        for(int i=0; i<listePontes.size(); i++){
            Ponte unePonte = listePontes.get(i);
            System.out.println("Date ponte : "+unePonte.getDatePonte().toString()+" , pondu par: "+ unePonte.getPalmipede().getIdPalmipede() + ", dans la cage: "+ unePonte.getCage().getIdCage());
        }
        if(listePontes.size()==0){
            System.out.println("Liste de pontes vides\n");
        }
    }
    
    private void testFindByPalmipedeAndPeriod(){
        
        List<Palmipede> mesPalmipedes = this.monPalmipedeDAO.findAll();
        for(int i=0; i<mesPalmipedes.size(); i++){
            Palmipede unPalmipede = mesPalmipedes.get(i);
            System.out.println("RFID palmipede : "+unPalmipede.getNumRFID()+" , entree le: "+ unPalmipede.getDateEntree().toString() + ", vit dans l'enclos: "+ unPalmipede.getEnclos().getNomEnclos());
        }
        if(mesPalmipedes.size()==0){
            System.out.println("Liste de cages vides\n");
        }
        
        
        
        LocalDate date = LocalDate.now();
        List<Palmipede> listePalmipedes = this.monPalmipedeDAO.findByDateSortie(date);
        
        for(int i=0; i<listePalmipedes.size(); i++){
            Palmipede unPalmipede = listePalmipedes.get(i);
            System.out.println("RFID palmipede : "+unPalmipede.getNumRFID()+" , entree le: "+ unPalmipede.getDateEntree().toString() + ", vit dans l'enclos: "+ unPalmipede.getEnclos().getNomEnclos());
        }
        if(listePalmipedes.size()==0){
            System.out.println("Liste  palmipede vides\n");
        }
    }
}
