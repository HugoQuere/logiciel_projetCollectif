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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import modele.dao.EnclosDao;
import modele.dao.PalmipedeDao;
import modele.dao.PonteDao;
import modele.dao.exception.ErreurMiseAjourException;
import modele.dao.exception.ErreurSauvegardeException;
import modele.dao.exception.ErreurSuppressionException;
import modele.entite.Enclos;
import modele.entite.Palmipede;

/**
 *
 * @author hugo
 */
public class PalmipedeDbDao extends DbDao implements PalmipedeDao{

    private final EnclosDao enclosDao;
    
    public PalmipedeDbDao(Properties props) {
        super(props);
        this.enclosDao = DbFactoryDao.getInstance().getEnclosDao();
    }

    @Override
    public Palmipede find(int idPalmipede) {
        
        Palmipede lePalmipede = null;
        try {
            String sql = "select idPalmipede, rfid, dateEntree, dateSortie, idEnclos from PALMIPEDE where idPalmipede=" + idPalmipede;
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int rfid = rs.getInt("rfid");
                Enclos lEnclos = this.enclosDao.find(rs.getInt("idEnclos"));
                Date dateEntree = rs.getDate("dateEntree");
                Date dateSortie = rs.getDate("dateSortie");
                lePalmipede = new Palmipede(idPalmipede, rfid, dateEntree, dateSortie, lEnclos);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Erreur SQL " + ex.getMessage());
        }
        return lePalmipede;
        
    }

    @Override
    public void insert(Palmipede unPalmipede) throws ErreurSauvegardeException {
        
        Connection con = null;
        try {
            String sql = "insert into PALMIPEDE (rfid, dateEntree, dateSortie, idEnclos) values (?,?,?,?)";
            con = this.getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, unPalmipede.getNumRFID());
            pstmt.setDate(2, (java.sql.Date) unPalmipede.getDateEntree());
            pstmt.setDate(3, (java.sql.Date) unPalmipede.getDateSortie());
            pstmt.setInt(4, unPalmipede.getEnclos().getIdEnclos());
            int result = pstmt.executeUpdate();
            if (result == 1) {
                // ne pas oublier de récupérer l'id !
                String sqlId = "select max(idPalmipede) as maxId from PALMIPEDE";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sqlId);
                if (rs.next()) {
                    unPalmipede.setIdPalmipede(rs.getInt("maxId"));
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
            throw new ErreurSauvegardeException("Le palmipede portant le num rfid:  " + unPalmipede.getNumRFID() + " n'a pas pu être enregistré");
        }
        
    }

    @Override
    public List<Palmipede> findAll() {
        
        List<Palmipede> lesPalmipedes = new ArrayList<>();
        try {
            String sql = "select idPalmipede, rfid, dateEntree, dateSortie, idEnclos from PALMIPEDE where idEnclos=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            List<Enclos> lesEnclos = this.enclosDao.findAll();
            for (Enclos unEnclos : lesEnclos) {
                pstmt.clearParameters();
                pstmt.setInt(1, unEnclos.getIdEnclos());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int idPalmipede = rs.getInt("idPalmipede");
                    int rfid = rs.getInt("rfid");
                    Date dateEntree = rs.getDate("dateEntree");
                    Date dateSortie = rs.getDate("dateSortie");
                    Palmipede unPalmipede = new Palmipede(idPalmipede, rfid, dateEntree, dateSortie, unEnclos);
                    lesPalmipedes.add(unPalmipede);
                }
                rs.close();
            }
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Erreur SQL " + ex.getMessage());
        }
        return lesPalmipedes;
        
    }
    
    
    @Override
    public List<Palmipede> findByDateSortie(LocalDate dateSortieMax){
        
        List<Palmipede> lesPalmipedes = new ArrayList<>();
        try {
            String sql = "select idPalmipede, rfid, dateEntree, dateSortie, idEnclos "
                        + "from PALMIPEDE "
                        + "where idEnclos=? and (dateSortie IS NULL or dateSortie>?)";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            Date dateSortieMaxConvertie = Date.valueOf(dateSortieMax);
            List<Enclos> lesEnclos = this.enclosDao.findAll();
            for (Enclos unEnclos : lesEnclos) {
                pstmt.clearParameters();
                pstmt.setInt(1, unEnclos.getIdEnclos());
                pstmt.setDate(2, dateSortieMaxConvertie);
                
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int idPalmipede = rs.getInt("idPalmipede");
                    int rfid = rs.getInt("rfid");
                    Date dateEntree = rs.getDate("dateEntree");
                    Date dateSortie = rs.getDate("dateSortie");
                    Palmipede unPalmipede = new Palmipede(idPalmipede, rfid, dateEntree, dateSortie, unEnclos);
                    lesPalmipedes.add(unPalmipede);
                }
                rs.close();
            }
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Erreur SQL " + ex.getMessage());
        }
        return lesPalmipedes;
        
    }

    
    @Override
    public List<Palmipede> findByEnclos(Enclos unEnclos) {
        
        List<Palmipede> lesPalmipedes = new ArrayList<>();
        try {
            String sql = "select idPalmipede, rfid, dateEntree, dateSortie, idEnclos from PALMIPEDE where idEnclos=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            
            pstmt.clearParameters();
            pstmt.setInt(1, unEnclos.getIdEnclos());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int idPalmipede = rs.getInt("idPalmipede");
                int rfid = rs.getInt("rfid");
                Date dateEntree = rs.getDate("dateEntree");
                Date dateSortie = rs.getDate("dateSortie");
                Palmipede unPalmipede = new Palmipede(idPalmipede, rfid, dateEntree, dateSortie, unEnclos);
                lesPalmipedes.add(unPalmipede);
            }
            rs.close();
                
            
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Erreur SQL " + ex.getMessage());
        }
        return lesPalmipedes;
        
    }
    
    
    @Override
    public void update(Palmipede unPalmipede) throws ErreurMiseAjourException {
        
        try {
            String sql = "update PALMIPEDE set rfid=?, dateEntree=?, dateSortie=?, idEnclos=? where idPalmipede=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, unPalmipede.getNumRFID());
            pstmt.setDate(2, unPalmipede.getDateEntree());
            pstmt.setDate(3, unPalmipede.getDateSortie());
            pstmt.setInt(4, unPalmipede.getEnclos().getIdEnclos());
            pstmt.setInt(5, unPalmipede.getIdPalmipede());
            int result = pstmt.executeUpdate();
            if (result == 0) {
                pstmt.close();
                con.close();
                throw new ErreurMiseAjourException("Le palmipede portant le numéro RFID; " + unPalmipede.getNumRFID() + " n'a pas pu être mis à jour");
            }
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurMiseAjourException("Le palmipede portant le numéro RFID; " + unPalmipede.getNumRFID() + " n'a pas pu être mis à jour");
        }
        
    }

    @Override
    public void delete(Palmipede unPalmipede) throws ErreurSuppressionException {
        
        PonteDao daoPontes = DbFactoryDao.getInstance().getPonteDao();
        try {
            //Attention si on supprime un palmipede, il faut aussi supprimer ces pontes
            daoPontes.deleteByPalmipede(unPalmipede);
            String sql = "delete from PALMIPEDE where idPalmipede=" + unPalmipede.getIdPalmipede();
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(sql);
            if (result == 0) {
                stmt.close();
                con.close();
                throw new ErreurSuppressionException("Le palmipede " + unPalmipede + " n'a pas pu être supprimé");
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurSuppressionException("Le palmipede " + unPalmipede + " n'a pas pu être supprimé");
        }
    }

    @Override
    public void deleteByEnclos(Enclos unEnclos) throws ErreurSuppressionException {
        
        
        
        PonteDao daoPontes = DbFactoryDao.getInstance().getPonteDao();
        try {
            //Attention si on supprime un enclos, il faut aussi supprimer toutes les pontes de ces palmipédes
            List<Palmipede> listePalmipede = this.findByEnclos(unEnclos);
            if(listePalmipede.size()>0){
                for(Palmipede unPalmipede : listePalmipede){
                    daoPontes.deleteByPalmipede(unPalmipede);
                }

                String sql = "delete from PALMIPEDE where idEnclos=" + unEnclos.getIdEnclos();
                Connection con = this.getConnection();
                Statement stmt = con.createStatement();
                int result = stmt.executeUpdate(sql);
                if (result == 0) {
                    stmt.close();
                    con.close();
                    throw new ErreurSuppressionException("Les palmipedes de l'enclos: " + unEnclos + " n'ont pas pu être supprimés");
                }
                stmt.close();
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurSuppressionException("Les palmipedes de l'enclos: " + unEnclos + " n'ont pas pu être supprimés");
        }
            
        
    }

    
    
    
}
