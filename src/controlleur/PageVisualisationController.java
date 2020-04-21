/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleur;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modele.dao.BatimentDao;
import modele.dao.CageDao;
import modele.dao.DaoFactory;
import modele.dao.EnclosDao;
import modele.dao.PalmipedeDao;
import modele.dao.PonteDao;
import modele.dao.db.DbFactoryDao;
import modele.entite.Cage;
import modele.entite.Enclos;
import modele.entite.Ponte;
import modele.entiteAffichage.VisualisationTableau1;

/**
 * FXML Controller class
 *
 */
public class PageVisualisationController implements Initializable {

    @FXML private TableView<VisualisationTableau1> tableau1_Visualisation;
    @FXML private TableColumn<VisualisationTableau1, String> Batiment_collumn_Tableau1;
    @FXML private TableColumn<VisualisationTableau1, String> Enclos_collumn_Tableau1;
    @FXML private TableColumn<VisualisationTableau1, Integer> Oeufs_collumn_Tableau1;
    
    private ObservableList<VisualisationTableau1> listeVisualisationTableau1;
    
    
    private final BatimentDao monBatimentDAO;
    private final CageDao maCageDAO;
    private final EnclosDao monEnclosDAO;
    private final PalmipedeDao monPalmipedeDAO;
    private final PonteDao maPonteDAO;
    

    public PageVisualisationController() {
        
        DaoFactory myFactory = DbFactoryDao.getInstance();
        
        this.monBatimentDAO = myFactory.getBatimentDao();
        this.maCageDAO = myFactory.getCageDao();
        this.monEnclosDAO = myFactory.getEnclosDao();
        this.monPalmipedeDAO = myFactory.getPalmipedeDao();
        this.maPonteDAO = myFactory.getPonteDao();
        
        this.listeVisualisationTableau1 = FXCollections.observableArrayList();
    }
    
    

    //Switching to page1
    @FXML
    public void page1ButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/vue/PageFecondite.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(tableViewScene);
        window.show();
    }
    
    //Switching to page3
    @FXML
    public void page3ButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/vue/PageProbleme.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(tableViewScene);
        window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // ------------------------- Initialisation tableau 1 (tableau gauche) ----------------------------------------------
        
        Batiment_collumn_Tableau1.setCellValueFactory(new PropertyValueFactory< VisualisationTableau1, String>("nomBatiment"));
        Enclos_collumn_Tableau1.setCellValueFactory(new PropertyValueFactory< VisualisationTableau1, String>("nomEnclos"));
        Oeufs_collumn_Tableau1.setCellValueFactory(new PropertyValueFactory< VisualisationTableau1, Integer>("nbOeufs"));
        
        remplissageTableau1_Visualiation();
        
        tableau1_Visualisation.setItems(listeVisualisationTableau1);
        
        // ------------------------- Fin initialisation tableau 1 (tableau gauche) ----------------------------------------------
    }   
    
    public void remplissageTableau1_Visualiation(){
        listeVisualisationTableau1.clear();
        
        List<Enclos> listeEnclos = this.monEnclosDAO.findAll();
        
        
        for(Enclos unEnclos : listeEnclos){
            
            //Nom du batiment 
            String nomBatiment = unEnclos.getBatiment().getNomBatiment();
            
            //NomEnclos
            String nomEnclos = unEnclos.getNomEnclos();
            
            //Nombre d'oeufs dans un enclos
            int nbOeufs = 0;
            List<Cage> listeCages = this.maCageDAO.findByEnclos(unEnclos);
            
            for(Cage uneCage : listeCages){
                
                List<Ponte> listesPontes = this.maPonteDAO.findByCage(uneCage);
                for(Ponte unePonte : listesPontes){
                    if(unePonte.isOeufCollecte()==false){ //Oeuf non collecté
                        nbOeufs++;
                    }
                }
            }
            
            VisualisationTableau1 elementTableau1 = new VisualisationTableau1(nomBatiment, nomEnclos, nbOeufs);
            listeVisualisationTableau1.add(elementTableau1);
        }
        
    }
    
}
