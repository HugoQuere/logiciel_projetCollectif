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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import modele.dao.EnclosDao;
import modele.dao.PalmipedeDao;
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
    public Palmipede find(int idCage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Palmipede uneCage) throws ErreurSauvegardeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public void update(Palmipede uneCage) throws ErreurMiseAjourException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Palmipede uneCage) throws ErreurSuppressionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
