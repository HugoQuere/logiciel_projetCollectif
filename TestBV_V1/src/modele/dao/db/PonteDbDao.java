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
import java.util.Date;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            for (Palmipede unPalmide : lesPalmipedes) {
                for (Cage uneCage : lesCages){
                    pstmt.clearParameters();
                    pstmt.setInt(1, unPalmide.getIdPalmipede());
                    pstmt.setInt(2, uneCage.getIdBox());
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        int idPonte = rs.getInt("idPonte");
                        Date datePonte = rs.getDate("datePonte");
                        boolean precenseOeuf = rs.getBoolean("precenseOeuf");
                        boolean oeufCollecte = rs.getBoolean("OeufCollecte");
                      
                        Ponte unePonte = new Ponte(idPonte, unPalmide, uneCage, datePonte, precenseOeuf, oeufCollecte);
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
    public void update(Ponte unePonte) throws ErreurMiseAjourException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Ponte unePonte) throws ErreurSuppressionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
