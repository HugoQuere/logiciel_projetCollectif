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
import modele.dao.BatimentDao;
import modele.dao.CageDao;
import modele.dao.EnclosDao;
import modele.dao.PonteDao;
import modele.dao.exception.ErreurMiseAjourException;
import modele.dao.exception.ErreurSauvegardeException;
import modele.dao.exception.ErreurSuppressionException;
import modele.entite.Cage;
import modele.entite.Enclos;

/**
 *
 * @author hugo
 */
public class CageDbDao extends DbDao implements CageDao{
    
    private final EnclosDao enclosDao;
    
    public CageDbDao(Properties props) {
        super(props);
        this.enclosDao = DbFactoryDao.getInstance().getEnclosDao();
    }

    @Override
    public Cage find(int idCage) {
        
        Cage laCage = null;
        try {
            String sql = "select idCage, idEnclos from Cage where idCage=" + idCage;
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Enclos lEnclos = this.enclosDao.find(rs.getInt("idEnclos"));
                laCage = new Cage(idCage, lEnclos);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Erreur SQL " + ex.getMessage());
        }
        return laCage;
        
    }

    @Override
    public void insert(Cage uneCage) throws ErreurSauvegardeException {
        
        Connection con = null;
        try {
            String sql = "insert into CAGE (idEnclos) values (?)";
            con = this.getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, uneCage.getEnclos().getIdEnclos());
            int result = pstmt.executeUpdate();
            if (result == 1) {
                // ne pas oublier de récupérer l'id !
                String sqlId = "select max(idCage) as maxId from CAGE";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sqlId);
                if (rs.next()) {
                    uneCage.setNumBox(rs.getInt("maxId"));
                }
                rs.close();
                stmt.close();
            }
            pstmt.close();
            con.commit();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
            }
            throw new ErreurSauvegardeException("La cage n'a pas pu être enregistré");
        }
        
    }

    @Override
    public List<Cage> findAll() {
        
        List<Cage> lesCages = new ArrayList<>();
        try {
            String sql = "select idCage, idEnclos from CAGE where idEnclos=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            List<Enclos> lesEnclos = this.enclosDao.findAll();
            for (Enclos unEnclos : lesEnclos) {
                pstmt.clearParameters();
                pstmt.setInt(1, unEnclos.getIdEnclos());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int idCage = rs.getInt("idCage");
                    Cage uneCage = new Cage(idCage, unEnclos);
                    lesCages.add(uneCage);
                }
                rs.close();
            }
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Erreur SQL " + ex.getMessage());
        }
        return lesCages;
        
    }
    
    @Override
    public List<Cage> findByEnclos(Enclos unEnclos) {
        
        List<Cage> lesCages = new ArrayList<>();
        try {
            String sql = "select idCage, idEnclos from CAGE where idEnclos=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            
            pstmt.clearParameters();
            pstmt.setInt(1, unEnclos.getIdEnclos());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int idCage = rs.getInt("idCage");
                Cage uneCage = new Cage(idCage, unEnclos);
                lesCages.add(uneCage);
            }
            rs.close();
                
                
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Erreur SQL " + ex.getMessage());
        }
        return lesCages;
        
    }

    @Override
    public void update(Cage uneCage) throws ErreurMiseAjourException {
        
        try {
            String sql = "update CAGE set idEnclos=? where idCage=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, uneCage.getEnclos().getIdEnclos());
            pstmt.setInt(2, uneCage.getIdBox());
            int result = pstmt.executeUpdate();
            if (result == 0) {
                pstmt.close();
                con.close();
                throw new ErreurMiseAjourException("La cage n'a pas pu être mis à jour");
            }
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurMiseAjourException("La cage n'a pas pu être mis à jour");
        }
        
    }

    @Override
    public void delete(Cage uneCage) throws ErreurSuppressionException {
        
        PonteDao daoPontes = DbFactoryDao.getInstance().getPonteDao();
        try {
            //Attention si on supprime une cage, il faut aussi supprimer les pontes qu'il y a eu dedans
            daoPontes.deleteByCage(uneCage);
            String sql = "delete from CAGE where idCage=" + uneCage.getIdBox();
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(sql);
            if (result == 0) {
                stmt.close();
                con.close();
                throw new ErreurSuppressionException("La cage " + uneCage.getIdBox() + " n'a pas pu être supprimé");
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurSuppressionException("La cage " + uneCage.getIdBox() + " n'a pas pu être supprimé");
        }
        
    }
      

    @Override
    public void deleteByEnclos(Enclos unEnclos) throws ErreurSuppressionException {
        
        PonteDao daoPontes = DbFactoryDao.getInstance().getPonteDao();
        try {
            //Attention si on supprime une cage, il faut aussi supprimer les pontes qu'il y a eu dedans
            List<Cage> listeCages = this.findByEnclos(unEnclos);
            if(listeCages.size()>0){
                for(Cage uneCage : listeCages){
                    daoPontes.deleteByCage(uneCage);
                }

                String sql = "delete from CAGE where idEnclos=" + unEnclos.getIdEnclos();
                Connection con = this.getConnection();
                Statement stmt = con.createStatement();
                int result = stmt.executeUpdate(sql);
                if (result == 0) {
                    stmt.close();
                    con.close();
                    throw new ErreurSuppressionException("Les cages de l'enclos" + unEnclos + " n'ont pas pu être supprimés");
                }
                stmt.close();
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurSuppressionException("Les cages de l'enclos" + unEnclos + " n'ont pas pu être supprimés");
        }
        
    }
    
}
