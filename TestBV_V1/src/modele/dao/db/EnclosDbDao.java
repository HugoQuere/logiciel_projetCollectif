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
import modele.dao.exception.ErreurMiseAjourException;
import modele.dao.exception.ErreurSauvegardeException;
import modele.dao.exception.ErreurSuppressionException;
import modele.entite.Batiment;
import modele.entite.Enclos;

/**
 *
 * @author hugo
 */
public class EnclosDbDao extends DbDao implements EnclosDao{

    private final BatimentDao batimentDao;
    
    public EnclosDbDao(Properties props) {
        super(props);
        this.batimentDao = DbFactoryDao.getInstance().getBatimentDao();
    }

    @Override
    public Enclos find(int idCage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Enclos uneCage) throws ErreurSauvegardeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Enclos> findAll() {
        
        List<Enclos> lesEnclos = new ArrayList<>();
        try {
            String sql = "select idEnclos, nomEnclos, idBatiment from ENCLOS where idBatiment=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            List<Batiment> lesBatiments = this.batimentDao.findAll();
            for (Batiment unBatiment : lesBatiments) {
                pstmt.clearParameters();
                pstmt.setInt(1, unBatiment.getIdBatiment());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int idEnclos = rs.getInt("idEnclos");
                    String nomEnclos = rs.getString("nomEnclos");
                    Enclos unEnclos = new Enclos(idEnclos, nomEnclos, unBatiment);
                    lesEnclos.add(unEnclos);
                }
                rs.close();
            }
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Erreur SQL " + ex.getMessage());
        }
        return lesEnclos;
    }

    @Override
    public void update(Enclos uneCage) throws ErreurMiseAjourException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Enclos uneCage) throws ErreurSuppressionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
