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
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import modele.dao.PalmipedeDao;
import modele.dao.PonteDao;
import modele.dao.exception.ErreurMiseAjourException;
import modele.dao.exception.ErreurSauvegardeException;
import modele.dao.exception.ErreurSuppressionException;
import modele.entite.Nid;
import modele.entite.Palmipede;
import modele.entite.Ponte;
import modele.dao.NidDao;

/**
 *
 * @author hugo
 */
public class PonteDbDao extends DbDao implements PonteDao{

    private final NidDao nidDao;
    private final PalmipedeDao palmipedeDao;
    
    public PonteDbDao(Properties props) {
        super(props);
        this.nidDao = DbFactoryDao.getInstance().getNidDao();
        this.palmipedeDao = DbFactoryDao.getInstance().getPalmipedeDao();
    }

    @Override
    public Ponte find(int idPonte) {
        
        Ponte laPonte = null;
        try {
            String sql = "select idPonte, idPalmipede, idNid, datePonte, heureDebutPonte, heureFinPonte, precenseOeuf, oeufCollecte from PONTE where idPonte=" + idPonte;
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Palmipede lePalmipede = this.palmipedeDao.find(rs.getInt("idPalmipede"));
                Nid leNid = this.nidDao.find(rs.getInt("idNid"));
                Date datePonte = rs.getDate("datePonte");
                Time heureDebutPonte = rs.getTime("heureDebutPonte");
                Time heureFinPonte = rs.getTime("heureFinPonte");
                boolean precenseOeuf = rs.getBoolean("precenseOeuf");
                boolean oeufCollecte = rs.getBoolean("OeufCollecte");

                laPonte = new Ponte(idPonte, lePalmipede, leNid, datePonte, heureDebutPonte, heureFinPonte, precenseOeuf, oeufCollecte);
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
            String sql = "insert into PONTE (idPalmipede, idNid, datePonte, precenseOeuf, oeufCollecte) values (?,?,?,?,?)";
            con = this.getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, unePonte.getPalmipede().getIdPalmipede());
            pstmt.setInt(2, unePonte.getNid().getIdNid());
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
            String sql = "select idPonte, idPalmipede, idNid, datePonte, heureDebutPonte, heureFinPonte, precenseOeuf, OeufCollecte from PONTE where idPalmipede=? and idNid=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            List<Nid> lesNids = this.nidDao.findAll();
            List<Palmipede> lesPalmipedes = this.palmipedeDao.findAll();
            for (Palmipede unPalmipede : lesPalmipedes) {
                for (Nid unNid : lesNids){
                    pstmt.clearParameters();
                    pstmt.setInt(1, unPalmipede.getIdPalmipede());
                    pstmt.setInt(2, unNid.getIdNid());
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        int idPonte = rs.getInt("idPonte");
                        Date datePonte = rs.getDate("datePonte");
                        Time heureDebutPonte = rs.getTime("heureDebutPonte");
                        Time heureFinPonte = rs.getTime("heureFinPonte");
                        boolean precenseOeuf = rs.getBoolean("precenseOeuf");
                        boolean oeufCollecte = rs.getBoolean("OeufCollecte");
                      
                        Ponte unePonte = new Ponte(idPonte, unPalmipede, unNid, datePonte, heureDebutPonte, heureFinPonte, precenseOeuf, oeufCollecte);
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
    public List<Ponte> findByPeriod(LocalDate dateDebut, LocalDate dateFin){
        List<Ponte> lesPontes = new ArrayList<>();
        try {
            String sql = "select idPonte, idPalmipede, idNid, datePonte, heureDebutPonte, heureFinPonte, precenseOeuf, OeufCollecte "
                        + "from PONTE "
                        + "where idNid=? and datePonte>=? and datePonte<=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            
            Date dateDebutConvert = Date.valueOf(dateDebut);
            Date dateFinConvert = Date.valueOf(dateFin);
            
            List<Nid> lesNids = this.nidDao.findAll();
            for (Nid unNid : lesNids){
                pstmt.clearParameters();
                pstmt.setInt(1, unNid.getIdNid());
                pstmt.setDate(2, dateDebutConvert);
                pstmt.setDate(3, dateFinConvert);
                
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int idPonte = rs.getInt("idPonte");
                    Palmipede unPalmipede = this.palmipedeDao.find(rs.getInt("idPalmipede"));
                    Date datePonte = rs.getDate("datePonte");
                    Time heureDebutPonte = rs.getTime("heureDebutPonte");
                    Time heureFinPonte = rs.getTime("heureFinPonte");
                    boolean precenseOeuf = rs.getBoolean("precenseOeuf");
                    boolean oeufCollecte = rs.getBoolean("OeufCollecte");

                    Ponte unePonte = new Ponte(idPonte, unPalmipede, unNid, datePonte, heureDebutPonte, heureFinPonte, precenseOeuf, oeufCollecte);
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
    public List<Ponte> findByPalmipede(Palmipede unPalmipede) {
        
        List<Ponte> lesPontes = new ArrayList<>();
        try {
            String sql = "select idPonte, idPalmipede, idNid, datePonte, heureDebutPonte, heureFinPonte, precenseOeuf, OeufCollecte from PONTE where idPalmipede=? and idNid=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            List<Nid> lesNids = this.nidDao.findAll();
            for (Nid unNid : lesNids){
                pstmt.clearParameters();
                pstmt.setInt(1, unPalmipede.getIdPalmipede());
                pstmt.setInt(2, unNid.getIdNid());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int idPonte = rs.getInt("idPonte");
                    Date datePonte = rs.getDate("datePonte");
                    Time heureDebutPonte = rs.getTime("heureDebutPonte");
                    Time heureFinPonte = rs.getTime("heureFinPonte");
                    boolean precenseOeuf = rs.getBoolean("precenseOeuf");
                    boolean oeufCollecte = rs.getBoolean("OeufCollecte");

                    Ponte unePonte = new Ponte(idPonte, unPalmipede, unNid, datePonte, heureDebutPonte, heureFinPonte, precenseOeuf, oeufCollecte);
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
    public List<Ponte> findByPalmipedeAndPeriod(Palmipede unPalmipede, LocalDate dateDebut, LocalDate dateFin){
        
        List<Ponte> lesPontes = new ArrayList<>();
        try {
            String sql = "select idPonte, idPalmipede, idNid, datePonte, heureDebutPonte, heureFinPonte, precenseOeuf, OeufCollecte "
                        + "from PONTE "
                        + "where idPalmipede=? and idNid=? and datePonte>=? and datePonte<=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            
            Date dateDebutConvert = Date.valueOf(dateDebut);
            Date dateFinConvert = Date.valueOf(dateFin);
            
            List<Nid> lesNids = this.nidDao.findAll();
            for (Nid unNid : lesNids){
                pstmt.clearParameters();
                pstmt.setInt(1, unPalmipede.getIdPalmipede());
                pstmt.setInt(2, unNid.getIdNid());
                pstmt.setDate(3, dateDebutConvert);
                pstmt.setDate(4, dateFinConvert);
                
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int idPonte = rs.getInt("idPonte");
                    Date datePonte = rs.getDate("datePonte");
                    Time heureDebutPonte = rs.getTime("heureDebutPonte");
                    Time heureFinPonte = rs.getTime("heureFinPonte");
                    boolean precenseOeuf = rs.getBoolean("precenseOeuf");
                    boolean oeufCollecte = rs.getBoolean("OeufCollecte");

                    Ponte unePonte = new Ponte(idPonte, unPalmipede, unNid, datePonte, heureDebutPonte, heureFinPonte, precenseOeuf, oeufCollecte);
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
    public List<Ponte> findByNid(Nid unNid){
        
        List<Ponte> lesPontes = new ArrayList<>();
        try {
            String sql = "select idPonte, idPalmipede, idNid, datePonte, heureDebutPonte, heureFinPonte, precenseOeuf, OeufCollecte from PONTE where idPalmipede=? and idNid=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            List<Palmipede> lesPalmipedes = this.palmipedeDao.findAll();
            for (Palmipede unPalmipede : lesPalmipedes) {
                pstmt.clearParameters();
                pstmt.setInt(1, unPalmipede.getIdPalmipede());
                pstmt.setInt(2, unNid.getIdNid());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int idPonte = rs.getInt("idPonte");
                    Date datePonte = rs.getDate("datePonte");
                    Time heureDebutPonte = rs.getTime("heureDebutPonte");
                    Time heureFinPonte = rs.getTime("heureFinPonte");
                    boolean precenseOeuf = rs.getBoolean("precenseOeuf");
                    boolean oeufCollecte = rs.getBoolean("OeufCollecte");

                    Ponte unePonte = new Ponte(idPonte, unPalmipede, unNid, datePonte, heureDebutPonte, heureFinPonte, precenseOeuf, oeufCollecte);
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
            String sql = "update PONTE set idPalmipede=?, idNid=?, datePonte=?, precenseOeuf=?, oeufCollecte=? where idPonte=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, unePonte.getPalmipede().getIdPalmipede());
            pstmt.setInt(2, unePonte.getNid().getIdNid());
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
    public void deleteByNid(Nid unNid) throws ErreurSuppressionException {
        
        List<Ponte> listePontes = this.findByNid(unNid);
        if(listePontes.size()>0){
            try {
                String sql = "delete from PONTE where idNid=" + unNid.getIdNid();
                Connection con = this.getConnection();
                Statement stmt = con.createStatement();
                int result = stmt.executeUpdate(sql);
                if (result == 0) {
                    stmt.close();
                    con.close();
                    throw new ErreurSuppressionException("Les pontes du nid" + unNid + " n'ont pas pu être supprimés");
                }
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ErreurSuppressionException("Les pontes du nid" + unNid + " n'ont pas pu être supprimés");
            }
        }
    }
    
    
}
