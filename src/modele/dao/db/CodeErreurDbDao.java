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
import modele.dao.CodeErreurDao;
import modele.dao.exception.ErreurMiseAjourException;
import modele.dao.exception.ErreurSauvegardeException;
import modele.dao.exception.ErreurSuppressionException;
import modele.entite.CodeErreur;

/**
 *
 * @author d-dja
 */
public class CodeErreurDbDao extends DbDao implements CodeErreurDao {

    public CodeErreurDbDao(Properties props) {
        super(props);
    }

    @Override
    public CodeErreur find(int idCodeErreur) {
        CodeErreur result = null;
        try {
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select idCodeErreur, description, idCategorieProbleme from CODEERREUR where idCodeErreur=" + idCodeErreur;
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                result = new CodeErreur(idCodeErreur, rs.getString("description"),rs.getInt("idCategorieProbleme"));
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
    public void insert(CodeErreur unCodeErreur) throws ErreurSauvegardeException {
        //Sécurité pour vérifier que le code erreur n'existe pas déja dans la liste des code erreur
        List<CodeErreur> listeCodeErreur  = this.findAll();
        for(CodeErreur codeErreur : listeCodeErreur){
            if(codeErreur.getDescription().equals(unCodeErreur.getDescription())){
                throw new ErreurSauvegardeException("Code erreur déja existant " + unCodeErreur);
            }
        }
        
        Connection con = null;
        try {
            con = this.getConnection();
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            String sql = "insert into CODEERREUR (idCodeErreur, description, idCategorieProbleme) values ("+unCodeErreur.getIdCodeErreur()+",'" + unCodeErreur.getDescription()+ "',"+unCodeErreur.getIdCategorie()+")";
            int nbInsert = stmt.executeUpdate(sql);
            if (nbInsert == 1) {
                sql = "select MAX(idCodeErreur) from CODEERREUR";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    //
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
            throw new ErreurSauvegardeException("Erreur lors de la sauvegarde d'un code erreur " + unCodeErreur);
        }
    }

    @Override
    public List<CodeErreur> findAll() {
        List<CodeErreur> lesCodeErreur = new ArrayList<>();
        
        try {
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select * from CODEERREUR";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                lesCodeErreur.add(new CodeErreur(rs.getInt("idCodeErreur"), rs.getString("description"),rs.getInt("idCategorieProbleme") ));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Exception SQL " + ex.getMessage());
        }
        
        return lesCodeErreur;
    }

    @Override
    public void update(CodeErreur unCodeErreur) throws ErreurMiseAjourException {
        try {
            String sql = "update CODEERREUR set description=?,idCategorieProbleme=? where idCodeErreur=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, unCodeErreur.getDescription());
            pstmt.setInt(2, unCodeErreur.getIdCategorie());
            pstmt.setInt(3, unCodeErreur.getIdCodeErreur());
            int result = pstmt.executeUpdate();
            if (result == 0) {
                pstmt.close();
                con.close();
                throw new ErreurMiseAjourException("Le code erreur  " + unCodeErreur.getDescription()+ " n'a pas pu être mis à jour");
            }
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurMiseAjourException("Le code erreur " + unCodeErreur.getDescription()+ " n'a pas pu être mis à jour");
        }
    }

    @Override
    public void delete(CodeErreur unCodeErreur) throws ErreurSuppressionException {
        try {
            
            String sql = "delete from CODEERREUR where idCodeErreur=" + unCodeErreur.getIdCodeErreur();
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(sql);
            if (result == 0) {
                stmt.close();
                con.close();
                throw new ErreurSuppressionException("Le code erreur " + unCodeErreur + " n'a pas pu être supprimé");
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurSuppressionException("Le code erreur " + unCodeErreur + " n'a pas pu être supprimé");
        }
    }
    
}
