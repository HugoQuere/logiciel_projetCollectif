/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javafx.collections.ObservableList;
import modele.dao.BatimentDao;
import modele.dao.EnclosDao;
import modele.dao.exception.ErreurMiseAjourException;
import modele.dao.exception.ErreurSauvegardeException;
import modele.dao.exception.ErreurSuppressionException;
import modele.entite.Batiment;

/**
 *
 * @author hugo
 */
public class BatimentDbDao extends DbDao implements BatimentDao{
    
    public BatimentDbDao(Properties props) {
        super(props);
    }

    @Override
    public Batiment find(int idBatiment) {
        
        Batiment result = null;
        try {
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select idBatiment, nomBatiment from BATIMENT where idBatiment=" + idBatiment;
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                result = new Batiment(idBatiment, rs.getString("nomBatiment"));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Exception SQL " + ex.getMessage());
        }
        return result;
        
    }

    @Override
    public void insert(Batiment unBatiment) throws ErreurSauvegardeException {
        
        //Sécurité pour vérifier que le nom du batiment n'existe pas déja dans la liste des batiments
        List<Batiment> listeBatiment  = this.findAll();
        for(Batiment batiment : listeBatiment){
            if(batiment.getNomBatiment().equals(unBatiment.getNomBatiment())){
                throw new ErreurSauvegardeException("Nom de batiment déja existant " + unBatiment);
            }
        }
        
        Connection con = null;
        try {
            con = this.getConnection();
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            String sql = "insert into BATIMENT (nomBatiment) values ('" + unBatiment.getNomBatiment() + "')";
            int nbInsert = stmt.executeUpdate(sql);
            if (nbInsert == 1) {
                sql = "select MAX(idBatiment) from BATIMENT";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    unBatiment.setIdBatiment(rs.getInt(1));
                }
                rs.close();
            }
            stmt.close();
            con.commit();

        } catch (SQLException ex) {
            System.out.println("Exception SQL " + ex.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex1) {
            }
            throw new ErreurSauvegardeException("Erreur lors de la sauvegarde de du batiment " + unBatiment);
        }
        
    }

    @Override
    public List<Batiment> findAll() {
        
        List<Batiment> lesBatiments = new ArrayList<>();
        
        try {
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select idBatiment, nomBatiment from BATIMENT";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                lesBatiments.add(new Batiment(rs.getInt("idBatiment"), rs.getString("nomBatiment") ));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Exception SQL " + ex.getMessage());
        }
        
        return lesBatiments;
    }
    
    
   
    
   

    @Override
    public void update(Batiment unBatiment) throws ErreurMiseAjourException {
        
        try {
            String sql = "update BATIMENT set nomBatiment=? where idBatiment=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, unBatiment.getNomBatiment());
            pstmt.setInt(2, unBatiment.getIdBatiment());
            int result = pstmt.executeUpdate();
            if (result == 0) {
                pstmt.close();
                con.close();
                throw new ErreurMiseAjourException("Le batiment " + unBatiment.getNomBatiment() + " n'a pas pu être mis à jour");
            }
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurMiseAjourException("Le batiment " + unBatiment.getNomBatiment() + " n'a pas pu être mis à jour");
        }
        
    }

    @Override
    public void delete(Batiment unBatiment) throws ErreurSuppressionException {
        
        EnclosDao daoEnclos = DbFactoryDao.getInstance().getEnclosDao();
        try {
            // attention, si on supprime un batiment ,il faut supprimer ces enclos
            daoEnclos.deleteByBatiment(unBatiment);
            
            String sql = "delete from BATIMENT where idBatiment=" + unBatiment.getIdBatiment();
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(sql);
            if (result == 0) {
                stmt.close();
                con.close();
                throw new ErreurSuppressionException("Le batiment " + unBatiment + " n'a pas pu être supprimé");
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurSuppressionException("Le batiment " + unBatiment + " n'a pas pu être supprimé");
        }
    }
    
}
