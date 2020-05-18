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
import modele.dao.CategorieProblemeDao;
import modele.dao.exception.ErreurMiseAjourException;
import modele.dao.exception.ErreurSauvegardeException;
import modele.dao.exception.ErreurSuppressionException;
import modele.entite.CategorieProbleme;

/**
 *
 * @author d-dja
 */
public class CategorieProblemeDbDao extends DbDao implements CategorieProblemeDao {

    public CategorieProblemeDbDao(Properties props) {
        super(props);
    }

    @Override
    public CategorieProbleme find(int idCategorieProbleme) {
        CategorieProbleme result = null;
        try {
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select idCategorieProbleme, typeProbleme from CATEGORIEPROBLEME where idCategorieProbleme=" + idCategorieProbleme;
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                result = new CategorieProbleme(idCategorieProbleme, rs.getString("typeProbleme"));
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
    public void insert(CategorieProbleme uneCategorie) throws ErreurSauvegardeException {
        //Sécurité pour vérifier que le type de probleme n'existe pas déja dans la liste des batiments
        List<CategorieProbleme> listeCategorie  = this.findAll();
        for(CategorieProbleme categorie : listeCategorie){
            if(categorie.getTypeProbleme().equals(uneCategorie.getTypeProbleme())){
                throw new ErreurSauvegardeException("Type de probleme déja existant " + uneCategorie);
            }
        }
        
        Connection con = null;
        try {
            con = this.getConnection();
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            String sql = "insert into CATEGORIEPROBLEME (idCategorieProbleme, typeProbleme) values ("+uneCategorie.getIdCategorie()+",'" + uneCategorie.getTypeProbleme()+ "')";
            int nbInsert = stmt.executeUpdate(sql);
            if (nbInsert == 1) {
                sql = "select MAX(idCategorieProbleme) from CATEGORIEPROBLEME";
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
            throw new ErreurSauvegardeException("Erreur lors de la sauvegarde d'un type de probleme " + uneCategorie);
        }
    }

    @Override
    public List<CategorieProbleme> findAll() {
        List<CategorieProbleme> lesCategoriesProbleme = new ArrayList<>();
        
        try {
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select idCategorieProbleme, typeProbleme from CATEGORIEPROBLEME";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                lesCategoriesProbleme.add(new CategorieProbleme(rs.getInt("idCategorieProbleme"), rs.getString("typeProbleme") ));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Exception SQL " + ex.getMessage());
        }
        
        return lesCategoriesProbleme;
    }

    @Override
    public void update(CategorieProbleme uneCategorie) throws ErreurMiseAjourException {
        try {
            String sql = "update CATEGORIEPROBLEME set typeProbleme=? where idCategorieProbleme=?";
            Connection con = this.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, uneCategorie.getTypeProbleme());
            pstmt.setInt(2, uneCategorie.getIdCategorie());
            int result = pstmt.executeUpdate();
            if (result == 0) {
                pstmt.close();
                con.close();
                throw new ErreurMiseAjourException("Le type de probleme  " + uneCategorie.getTypeProbleme()+ " n'a pas pu être mis à jour");
            }
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurMiseAjourException("Le type de probleme " + uneCategorie.getTypeProbleme()+ " n'a pas pu être mis à jour");
        }
    }

    @Override
    public void delete(CategorieProbleme uneCategorie) throws ErreurSuppressionException {
        // TODO : Code Erreur
        /*CodeErreurDao daoCodeErreur = DbFactoryDao.getInstance().getCodeErreurDao();
        try {
            // attention, si on supprime un type d'erreur ,il faut supprimer ses code erreur
            daoCodeErreur.deleteByCategorie(uneCategorie);
            
            String sql = "delete from CATEGORIEPROBLEME where idCategorieProbleme=" + uneCategorie.getIdCategorie();
            Connection con = this.getConnection();
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(sql);
            if (result == 0) {
                stmt.close();
                con.close();
                throw new ErreurSuppressionException("Le type de probleme " + uneCategorie + " n'a pas pu être supprimé");
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErreurSuppressionException("Le type de probleme " + uneCategorie + " n'a pas pu être supprimé");
        }*/
    }
    
}
