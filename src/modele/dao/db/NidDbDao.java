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
import modele.dao.EnclosDao;
import modele.dao.PonteDao;
import modele.dao.exception.ErreurMiseAjourException;
import modele.dao.exception.ErreurSauvegardeException;
import modele.dao.exception.ErreurSuppressionException;
import modele.entite.Nid;
import modele.entite.Enclos;
import modele.dao.NidDao;

/**
 *
 * @author hugo
 */
public class NidDbDao extends DbDao implements NidDao{
    
    private final EnclosDao enclosDao;
    
    public NidDbDao(Properties props) {
        super(props);
        this.enclosDao = DbFactoryDao.getInstance().getEnclosDao();
    }

    @Override
    public Nid find(int idNid) {
        
        Nid leNid = null;
        try {
            String sql = "select idNid, numNid, idEnclos, zone from NID where idNid=" + idNid;
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int numNid = rs.getInt("numNid");
                Enclos lEnclos = this.enclosDao.find(rs.getInt("idEnclos"));
                int zone = rs.getInt("zone");
                leNid = new Nid(idNid, numNid, lEnclos, zone);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Erreur SQL " + ex.getMessage());
        }
        return leNid;
        
    }

    @Override
    public void insert(Nid unNid) throws ErreurSauvegardeException {
        
        Connection con = null;
        try {
            String sql = "insert into NID (idEnclos) values (?)";
            con = this.getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, unNid.getEnclos().getIdEnclos());
            int result = pstmt.executeUpdate();
            if (result == 1) {
                // ne pas oublier de récupérer l'id !
                String sqlId = "select max(idNid) as maxId from NID";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sqlId);
                if (rs.next()) {
                    unNid.setNumNid(rs.getInt("maxId"));
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
            throw new ErreurSauvegardeException("Le nid n'a pas pu être enregistré");
        }
        
    }

    @Override
    public List<Nid> findAll() {
        
        List<Nid> lesNids = new ArrayList<>();
        try {
            String sql = "select idNid, numNid, idEnclos, zone from NID where idEnclos=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            List<Enclos> lesEnclos = this.enclosDao.findAll();
            for (Enclos unEnclos : lesEnclos) {
                pstmt.clearParameters();
                pstmt.setInt(1, unEnclos.getIdEnclos());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int idNid = rs.getInt("idNid");
                    int numNid = rs.getInt("numNid");
                    int zone = rs.getInt("zone");
                    Nid unNid = new Nid(idNid, numNid,unEnclos, zone);
                    lesNids.add(unNid);
                }
                rs.close();
            }
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Erreur SQL " + ex.getMessage());
        }
        return lesNids;
        
    }
    
    @Override
    public List<Nid> findByEnclos(Enclos unEnclos) {
        
        List<Nid> lesNids = new ArrayList<>();
        try {
            String sql = "select idNid, numNid, idEnclos, zone from NID where idEnclos=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            
            pstmt.clearParameters();
            pstmt.setInt(1, unEnclos.getIdEnclos());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int idNid = rs.getInt("idNid");
                int numNid = rs.getInt("numNid");
                int zone = rs.getInt("zone");
                Nid unNid = new Nid(idNid, numNid, unEnclos, zone);
                lesNids.add(unNid);
            }
            rs.close();
                
                
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Erreur SQL " + ex.getMessage());
        }
        return lesNids;
        
    }

    @Override
    public void update(Nid unNid) throws ErreurMiseAjourException {
        
        try {
            String sql = "update NID set idEnclos=? where idNid=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, unNid.getEnclos().getIdEnclos());
            pstmt.setInt(2, unNid.getIdNid());
            int result = pstmt.executeUpdate();
            if (result == 0) {
                pstmt.close();
                con.close();
                throw new ErreurMiseAjourException("Le nid n'a pas pu être mis à jour");
            }
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurMiseAjourException("Le nid n'a pas pu être mis à jour");
        }
        
    }

    @Override
    public void delete(Nid unNid) throws ErreurSuppressionException {
        
        PonteDao daoPontes = DbFactoryDao.getInstance().getPonteDao();
        try {
            //Attention si on supprime un nid, il faut aussi supprimer les pontes qu'il y a eu dedans
            daoPontes.deleteByNid(unNid);
            String sql = "delete from NID where idNid=" + unNid.getIdNid();
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(sql);
            if (result == 0) {
                stmt.close();
                con.close();
                throw new ErreurSuppressionException("Le nid " + unNid.getIdNid() + " n'a pas pu être supprimé");
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurSuppressionException("Le nid " + unNid.getIdNid() + " n'a pas pu être supprimé");
        }
        
    }
      

    @Override
    public void deleteByEnclos(Enclos unEnclos) throws ErreurSuppressionException {
        
        PonteDao daoPontes = DbFactoryDao.getInstance().getPonteDao();
        try {
            //Attention si on supprime un nid, il faut aussi supprimer les pontes qu'il y a eu dedans
            List<Nid> listeNids = this.findByEnclos(unEnclos);
            if(listeNids.size()>0){
                for(Nid unNid : listeNids){
                    daoPontes.deleteByNid(unNid);
                }

                String sql = "delete from NID where idEnclos=" + unEnclos.getIdEnclos();
                Connection con = this.getConnection();
                Statement stmt = con.createStatement();
                int result = stmt.executeUpdate(sql);
                if (result == 0) {
                    stmt.close();
                    con.close();
                    throw new ErreurSuppressionException("Les nids de l'enclos" + unEnclos + " n'ont pas pu être supprimés");
                }
                stmt.close();
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurSuppressionException("Les nids de l'enclos" + unEnclos + " n'ont pas pu être supprimés");
        }
        
    }
    
}
