/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import modele.dao.BatimentDao;
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
    public Batiment find(int idCage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Batiment uneCage) throws ErreurSauvegardeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public void update(Batiment uneCage) throws ErreurMiseAjourException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Batiment uneCage) throws ErreurSuppressionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
