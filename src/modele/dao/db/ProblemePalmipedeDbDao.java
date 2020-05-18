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
import modele.dao.ProblemeDao;
import modele.dao.ProblemePalmipedeDao;
import modele.dao.exception.ErreurMiseAjourException;
import modele.dao.exception.ErreurSauvegardeException;
import modele.dao.exception.ErreurSuppressionException;
import modele.entite.Probleme;
import modele.entite.ProblemePalmipede;

/**
 *
 * @author d-dja
 */
public class ProblemePalmipedeDbDao extends DbDao implements ProblemePalmipedeDao{

    public ProblemePalmipedeDbDao(Properties props) {
        super(props);
    }

    @Override
    public Probleme find(int idProbleme) {
        Probleme result = null;
        try {
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select * from PROBLEMEPALMIPEDE where idProblemePalmipede=" + idProbleme;
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                result = new ProblemePalmipede(idProbleme, rs.getInt("idPalmipede"), rs.getInt("idCategorieProbleme"), rs.getString("commentaire"), rs.getDate("dateCreation"), rs.getDate("dateResolution"));
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
    public void insert(Probleme unProbleme) throws ErreurSauvegardeException {
        //Sécurité pour vérifier que le probleme n'existe pas déja dans la liste des probleme
        List<Probleme> listeProbleme  = this.findAll();
        for(Probleme probleme : listeProbleme){
            if(probleme.getCommentaire().equals(unProbleme.getCommentaire())){
                throw new ErreurSauvegardeException("Probleme déja existant " + unProbleme);
            }
        }
        
        Connection con = null;
        try {
            con = this.getConnection();
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            String sql = "insert into PROBLEMEPALMIPEDE (idPalmipede, idCategorieProbleme,commentaire,dateCreation,dateResolution) values ("+
                    ((ProblemePalmipede)unProbleme).getIdPalmipede()+","+unProbleme.getIdCategorie()+",'" +unProbleme.getCommentaire()+ "','"+unProbleme.getDateCreation()+"','"+unProbleme.getDateResolution()+"')";
            int nbInsert = stmt.executeUpdate(sql);
            if (nbInsert == 1) {
                sql = "select MAX(idProblemePalmipede) from PROBLEMEPALMIPEDE";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    unProbleme.setIdProbleme(rs.getInt(1));
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
              System.out.println("Exception SQL " + ex1.getMessage());
            }
            throw new ErreurSauvegardeException("Erreur lors de la sauvegarde d'un probleme " + unProbleme);
        }
    }

    @Override
    public List<Probleme> findAll() {
        List<Probleme> lesProblemes = new ArrayList<>();
        
        try {
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select * from PROBLEMEPALMIPEDE";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                lesProblemes.add(new ProblemePalmipede(rs.getInt("idProblemePalmipede"), rs.getInt("idPalmipede"), rs.getInt("idCategorieProbleme"), rs.getString("commentaire"), rs.getDate("dateCreation"), rs.getDate("dateResolution") ));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Exception SQL " + ex.getMessage());
        }
        
        return lesProblemes;
    }

    @Override
    public void update(Probleme unProbleme) throws ErreurMiseAjourException {
        try {
            String sql = "update PROBLEMEPALMIPEDE set idPalmipede=?,idCategorieProbleme=?,commentaire=?,dateCreation=?,dateResolution=? where idProblemePalmipede=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, ((ProblemePalmipede)unProbleme).getIdPalmipede());
            pstmt.setInt(2, unProbleme.getIdCategorie());
            pstmt.setString(3, unProbleme.getCommentaire());
            pstmt.setDate(4, unProbleme.getDateCreation());
            pstmt.setDate(5, unProbleme.getDateResolution());
            pstmt.setInt(6, unProbleme.getIdProbleme());
            int result = pstmt.executeUpdate();
            if (result == 0) {
                pstmt.close();
                con.close();
                throw new ErreurMiseAjourException("Le Probleme  " + unProbleme.getCommentaire()+ " n'a pas pu être mis à jour");
            }
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurMiseAjourException("Le Probleme  " + unProbleme.getCommentaire()+ " n'a pas pu être mis à jour");
        }
    }

    @Override
    public void delete(Probleme unProbleme) throws ErreurSuppressionException {
        try {            
            String sql = "delete from PROBLEMEPALMIPEDE where idProblemePalmipede=" + unProbleme.getIdProbleme();
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(sql);
            if (result == 0) {
                stmt.close();
                con.close();
                throw new ErreurSuppressionException("Le probleme " + unProbleme + " n'a pas pu être supprimé");
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurSuppressionException("Le probleme " + unProbleme + " n'a pas pu être supprimé");
        }
    }
    
}
