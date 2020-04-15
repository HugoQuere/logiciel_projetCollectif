/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import modele.dao.CageDao;
import modele.dao.PalmipedeDao;
import modele.dao.PonteDao;
import modele.dao.exception.ErreurMiseAjourException;
import modele.dao.exception.ErreurSauvegardeException;
import modele.dao.exception.ErreurSuppressionException;
import modele.entite.Cage;
import modele.entite.Palmipede;
import modele.entite.Ponte;

/**
 *
 * @author hugo
 */
public class PonteDbDao extends DbDao implements PonteDao{

    private final CageDao cageDao;
    private final PalmipedeDao palmipedeDao;
    
    public PonteDbDao(Properties props) {
        super(props);
        this.cageDao = DbFactoryDao.getInstance().getCageDao();
        this.palmipedeDao = DbFactoryDao.getInstance().getPalmipedeDao();
    }

    @Override
    public Ponte find(int idPonte) {
        
        Ponte laPonte = null;
        try {
            String sql = "select idPonte, idPalmipede, idCage, datePonte, precenseOeuf, oeufCollecte from PONTE where idPonte=" + idPonte;
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Palmipede lePalmipede = this.palmipedeDao.find(rs.getInt("idPalmipede"));
                Cage laCage = this.cageDao.find(rs.getInt("idCage"));
                Date datePonte = rs.getDate("datePonte");
                boolean precenseOeuf = rs.getBoolean("precenseOeuf");
                boolean oeufCollecte = rs.getBoolean("OeufCollecte");

                laPonte = new Ponte(idPonte, lePalmipede, laCage, datePonte, precenseOeuf, oeufCollecte);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Erreur SQL " + ex.getMessage());
        }
        return laPonte;
        
    }

    @Override
    public void insert(Ponte unePonte) throws ErreurSauvegardeException {
        
        Connection con = null;
        try {
            String sql = "insert into PONTE (idPalmipede, idCage, datePonte, precenseOeuf, oeufCollecte) values (?,?,?,?,?)";
            con = this.getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, unePonte.getPalmipede().getIdPalmipede());
            pstmt.setInt(2, unePonte.getCage().getIdBox());
            pstmt.setDate(3, (java.sql.Date) unePonte.getDatePonte());
            pstmt.setBoolean(4, unePonte.isPresenceOeuf());
            pstmt.setBoolean(5, unePonte.isOeufCollecte());
            int result = pstmt.executeUpdate();
            if (result == 1) {
                // ne pas oublier de récupérer l'id !
                String sqlId = "select max(idPonte) as maxId from PONTE";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sqlId);
                if (rs.next()) {
                    unePonte.setIdPonte(rs.getInt("maxId"));
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
            throw new ErreurSauvegardeException("La ponte du palmipede: " + unePonte.getPalmipede().getIdPalmipede() + " n'a pas pu être enregistré");
        }
        
    }

    @Override
    public List<Ponte> findAll() {
        
        List<Ponte> lesPontes = new ArrayList<>();
        try {
            String sql = "select idPonte, idPalmipede, idCage, datePonte, precenseOeuf, OeufCollecte from PONTE where idPalmipede=? and idCage=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            List<Cage> lesCages = this.cageDao.findAll();
            List<Palmipede> lesPalmipedes = this.palmipedeDao.findAll();
            for (Palmipede unPalmipede : lesPalmipedes) {
                for (Cage uneCage : lesCages){
                    pstmt.clearParameters();
                    pstmt.setInt(1, unPalmipede.getIdPalmipede());
                    pstmt.setInt(2, uneCage.getIdBox());
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        int idPonte = rs.getInt("idPonte");
                        Date datePonte = rs.getDate("datePonte");
                        boolean precenseOeuf = rs.getBoolean("precenseOeuf");
                        boolean oeufCollecte = rs.getBoolean("OeufCollecte");
                      
                        Ponte unePonte = new Ponte(idPonte, unPalmipede, uneCage, datePonte, precenseOeuf, oeufCollecte);
                        lesPontes.add(unePonte);
                    }
                    rs.close();
                }
            }
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Erreur SQL " + ex.getMessage());
        }
        return lesPontes;
        
    }
    
    @Override
    public List<Ponte> findByPalmipede(Palmipede unPalmipede) throws ErreurSuppressionException {
        
        List<Ponte> lesPontes = new ArrayList<>();
        try {
            String sql = "select idPonte, idPalmipede, idCage, datePonte, precenseOeuf, OeufCollecte from PONTE where idPalmipede=? and idCage=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            List<Cage> lesCages = this.cageDao.findAll();
            for (Cage uneCage : lesCages){
                pstmt.clearParameters();
                pstmt.setInt(1, unPalmipede.getIdPalmipede());
                pstmt.setInt(2, uneCage.getIdBox());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int idPonte = rs.getInt("idPonte");
                    Date datePonte = rs.getDate("datePonte");
                    boolean precenseOeuf = rs.getBoolean("precenseOeuf");
                    boolean oeufCollecte = rs.getBoolean("OeufCollecte");

                    Ponte unePonte = new Ponte(idPonte, unPalmipede, uneCage, datePonte, precenseOeuf, oeufCollecte);
                    lesPontes.add(unePonte);
                }
                rs.close();
            }
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Erreur SQL " + ex.getMessage());
        }
        return lesPontes;
        
    }

    @Override
    public List<Ponte> findByCage(Cage uneCage) throws ErreurSuppressionException {
        
        List<Ponte> lesPontes = new ArrayList<>();
        try {
            String sql = "select idPonte, idPalmipede, idCage, datePonte, precenseOeuf, OeufCollecte from PONTE where idPalmipede=? and idCage=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            List<Palmipede> lesPalmipedes = this.palmipedeDao.findAll();
            for (Palmipede unPalmipede : lesPalmipedes) {
                pstmt.clearParameters();
                pstmt.setInt(1, unPalmipede.getIdPalmipede());
                pstmt.setInt(2, uneCage.getIdBox());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int idPonte = rs.getInt("idPonte");
                    Date datePonte = rs.getDate("datePonte");
                    boolean precenseOeuf = rs.getBoolean("precenseOeuf");
                    boolean oeufCollecte = rs.getBoolean("OeufCollecte");

                    Ponte unePonte = new Ponte(idPonte, unPalmipede, uneCage, datePonte, precenseOeuf, oeufCollecte);
                    lesPontes.add(unePonte);
                }
                rs.close();
            }
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Erreur SQL " + ex.getMessage());
        }
        return lesPontes;
        
    }
    

    @Override
    public void update(Ponte unePonte) throws ErreurMiseAjourException {
        
        try {
            String sql = "update PONTE set idPalmipede=?, idCage=?, datePonte=?, precenseOeuf=?, oeufCollecte=? where idPonte=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, unePonte.getPalmipede().getIdPalmipede());
            pstmt.setInt(2, unePonte.getCage().getIdBox());
            pstmt.setDate(3, unePonte.getDatePonte());
            pstmt.setBoolean(4, unePonte.isPresenceOeuf());
            pstmt.setBoolean(5, unePonte.isOeufCollecte());
            pstmt.setInt(6, unePonte.getIdPonte());
            int result = pstmt.executeUpdate();
            if (result == 0) {
                pstmt.close();
                con.close();
                throw new ErreurMiseAjourException("La ponte " + unePonte.getIdPonte() + " n'a pas pu être mis à jour");
            }
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurMiseAjourException("La ponte " + unePonte.getIdPonte() + " n'a pas pu être mis à jour");
        }
        
    }

    @Override
    public void delete(Ponte unePonte) throws ErreurSuppressionException {
        
        try {
            String sql = "delete from PONTE where idPonte=" + unePonte.getIdPonte();
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(sql);
            if (result == 0) {
                stmt.close();
                con.close();
                throw new ErreurSuppressionException("La ponte avec l'id: " + unePonte.getIdPonte() + " n'a pas pu être supprimé");
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurSuppressionException("La ponte avec l'id: " + unePonte.getIdPonte() + " n'a pas pu être supprimé");
        }
        
    }

    @Override
    public void deleteByPalmipede(Palmipede unPalmipede) throws ErreurSuppressionException {
        
        List<Ponte> listePontes = this.findByPalmipede(unPalmipede);
        if(listePontes.size()>0){
            try {
                String sql = "delete from PONTE where idPalmipede=" + unPalmipede.getIdPalmipede();
                Connection con = this.getConnection();
                Statement stmt = con.createStatement();
                int result = stmt.executeUpdate(sql);
                if (result == 0) {
                    stmt.close();
                    con.close();
                    throw new ErreurSuppressionException("Les pontes du palmipéde" + unPalmipede + " n'ont pas pu être supprimés");
                }
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ErreurSuppressionException("Les pontes du palmipéde" + unPalmipede + " n'ont pas pu être supprimés");
            }
        }
    }

    @Override
    public void deleteByCage(Cage uneCage) throws ErreurSuppressionException {
        
        List<Ponte> listePontes = this.findByCage(uneCage);
        if(listePontes.size()>0){
            try {
                String sql = "delete from PONTE where idCage=" + uneCage.getIdBox();
                Connection con = this.getConnection();
                Statement stmt = con.createStatement();
                int result = stmt.executeUpdate(sql);
                if (result == 0) {
                    stmt.close();
                    con.close();
                    throw new ErreurSuppressionException("Les pontes de la cage" + uneCage + " n'ont pas pu être supprimés");
                }
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ErreurSuppressionException("Les pontes de la cage" + uneCage + " n'ont pas pu être supprimés");
            }
        }
    }
    
    
}
