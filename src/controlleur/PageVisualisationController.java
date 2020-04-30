/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleur;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modele.dao.BatimentDao;
import modele.dao.CageDao;
import modele.dao.DaoFactory;
import modele.dao.EnclosDao;
import modele.dao.PalmipedeDao;
import modele.dao.PonteDao;
import modele.dao.db.DbFactoryDao;
import modele.entite.Batiment;
import modele.entite.Cage;
import modele.entite.Enclos;
import modele.entite.Ponte;
import modele.entiteAffichage.VisualisationTableau1;
import modele.entiteAffichage.VisualisationTableau2;

/**
 * FXML Controller class
 *
 */
public class PageVisualisationController implements Initializable {

    //Tableau 1
    @FXML private TableView<VisualisationTableau1> tableau1_Visualisation;
    @FXML private TableColumn<VisualisationTableau1, String> tableau1_Collumn_Batiment;
    @FXML private TableColumn<VisualisationTableau1, String> tableau1_Collumn_Enclos;
    @FXML private TableColumn<VisualisationTableau1, Integer> tableau1_Collumn_Oeufs;
    
    private ObservableList<VisualisationTableau1> listeVisualisationTableau1;
    
    
    
    
    //Tableau 2
    @FXML private TableView<VisualisationTableau2> tableau2_Visualisation;
    @FXML private TableColumn<VisualisationTableau2, Integer> tableau2_Collumn_Cage;
    @FXML private TableColumn<VisualisationTableau2, Integer> tableau2_Collumn_nbOeufs;
    private ObservableList<VisualisationTableau2> listeVisualisationTableau2;
    
    
    
    
    //Combo box
    @FXML private ComboBox<String> batimentComboBox;
    @FXML private ComboBox<String> enclosComboBox;
    private ObservableList<String> listeStringBatiment;
    private ObservableList<String> listeStringEnclosParBatiment;
    
    
    
    
    //Accés base de données
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
        
        //Liste tableau 1
        this.listeVisualisationTableau1 = FXCollections.observableArrayList();
        
        //Liste tableau 2
        this.listeVisualisationTableau2 = FXCollections.observableArrayList();
        
        //Liste combobox
        this.listeStringBatiment = FXCollections.observableArrayList();
        this.listeStringEnclosParBatiment = FXCollections.observableArrayList();
                
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
        
        // ------------------------- Initialisation ComboBox  ----------------------------------------------
        
        remplissageListeBatiment();
        batimentComboBox.setItems(listeStringBatiment);
        batimentComboBox.setValue(listeStringBatiment.get(0)); //Mise par défaut à la premiére valeur de la combo box
        
        remplissageListeEnclosParBatiment(listeStringBatiment.get(0)); //Remplissage avec le batiment par défaut
        enclosComboBox.setItems(listeStringEnclosParBatiment);
        enclosComboBox.setValue(listeStringEnclosParBatiment.get(0)); //Mise par défaut à la premiére valeur de la combo box
        
        // ------------------------- Fin initialisation ComboBox  ----------------------------------------------
        
        
        
        
        
        
        // ------------------------- Initialisation tableau 1 (tableau gauche) ----------------------------------------------
        
        tableau1_Collumn_Batiment.setCellValueFactory(new PropertyValueFactory< VisualisationTableau1, String>("nomBatiment"));
        tableau1_Collumn_Enclos.setCellValueFactory(new PropertyValueFactory< VisualisationTableau1, String>("nomEnclos"));
        tableau1_Collumn_Oeufs.setCellValueFactory(new PropertyValueFactory< VisualisationTableau1, Integer>("nbOeufs"));
        
        remplissageTableau1_Visualisation();
        
        tableau1_Visualisation.setItems(listeVisualisationTableau1);
        
        // ------------------------- Fin initialisation tableau 1 (tableau gauche) ----------------------------------------------
        
        
        
        
        
        // ------------------------- Initialisation tableau 2 (tableau droite) ----------------------------------------------
        
        tableau2_Collumn_Cage.setCellValueFactory(new PropertyValueFactory< VisualisationTableau2, Integer>("numCage"));
        tableau2_Collumn_nbOeufs.setCellValueFactory(new PropertyValueFactory< VisualisationTableau2, Integer>("nbOeufs"));
        
        remplissageTableau2_Visualisation();
        
        tableau2_Visualisation.setItems(listeVisualisationTableau2);
        
        // ------------------------- Fin initialisation tableau 2 (tableau droite) ----------------------------------------------
        
    }   
    

    @FXML
    private void actionComboBoxBatiment(ActionEvent event) {
        
       String batimentSelectionne = batimentComboBox.getValue();
       
       remplissageListeEnclosParBatiment(batimentSelectionne); //Remplissage de la combobox enclos avec le batiment sélectionné
       
       remplissageTableau2_Visualisation(); //remplissage du deuxieme tableau en fonction des combobox
        
    }

    @FXML
    private void actionComboBoxEnclos(ActionEvent event) {
        remplissageTableau2_Visualisation(); //remplissage du deuxieme tableau en fonction des combobox
    }
    
    
    
    @FXML
    private void mouseClick_Tableau1(MouseEvent event) {
        TableViewSelectionModel<VisualisationTableau1> selectionModel = tableau1_Visualisation.getSelectionModel();
        
        //ObservableList<Integer> selectedIndices = selectionModel.getSelectedIndices(); //get selected indices
        //int elementSelectionne = selectedIndices.get(0);
        //System.out.println("Element selectionne : " + elementSelectionne);
        
        ObservableList<VisualisationTableau1> selectedItems = selectionModel.getSelectedItems(); //get selected element (un seul element par defaut
        VisualisationTableau1 element = selectedItems.get(0);
        
        for (int i=0; i<listeStringBatiment.size(); i++){
            String unNomBatiment = listeStringBatiment.get(i);
            if(unNomBatiment.equals(element.getNomBatiment())){ //ne peut survenir qu'une seule fois
                batimentComboBox.setValue(listeStringBatiment.get(i)); //Mise à la valeur sélectionné (combo box batiment)
            }
        }
        
        remplissageListeEnclosParBatiment(element.getNomBatiment());
        for (int i=0; i<listeStringEnclosParBatiment.size(); i++){
            String unNomEnclos = listeStringEnclosParBatiment.get(i);
            if(unNomEnclos.equals(element.getNomEnclos())){ //ne peut survenir qu'une seule fois
                enclosComboBox.setValue(listeStringEnclosParBatiment.get(i)); //Mise à la valeur sélectionné (combo box enclos)
            }
        }
        
        
    }
    
    
    
    
    
    
    
    
    
    public void remplissageTableau1_Visualisation(){
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
                    if(unePonte.isOeufCollecte()==false && unePonte.isPresenceOeuf()==true){ //Oeuf non collecté
                        nbOeufs++;
                    }
                }
            }
            
            VisualisationTableau1 elementTableau1 = new VisualisationTableau1(nomBatiment, nomEnclos, nbOeufs);
            listeVisualisationTableau1.add(elementTableau1);
        }
        
    }
    
    
    
    public void remplissageTableau2_Visualisation(){
        listeVisualisationTableau2.clear();
        
        String enclosSelectionne = enclosComboBox.getValue();
        
        List<Enclos> listeEnclos = this.monEnclosDAO.findAll();
        for(Enclos unEnclos : listeEnclos){
            if(unEnclos.getNomEnclos().equals(enclosSelectionne)){ //enclos correspondant au paramétre (nom unique)
                
                List<Cage> listeCages = this.maCageDAO.findByEnclos(unEnclos);
                
                for(Cage uneCage : listeCages){
                    
                    int numCage = uneCage.getNumBox();
                    
                    int nbOeufsNonRecolte=0;
                    List<Ponte> listePontes = this.maPonteDAO.findByCage(uneCage);
                    for(Ponte unePonte : listePontes){
                        if(unePonte.isOeufCollecte()==false && unePonte.isPresenceOeuf()==true){
                            nbOeufsNonRecolte++;
                        }
                    }
                    
                    VisualisationTableau2 unElement = new VisualisationTableau2(numCage, nbOeufsNonRecolte);
                    listeVisualisationTableau2.add(unElement);
                    
                }
                break;
            }
        }
        
        //Ne semble pas petre fonctionnel
        tableau2_Visualisation.getSortOrder().clear();
        tableau2_Visualisation.getSortOrder().add(tableau2_Collumn_Cage); //Trie du tableau par rapport au numéro de la cage
        tableau2_Visualisation.sort(); //Trie les cages dans l'ordre
        
    }
    
    
    
    
    public void remplissageListeBatiment(){
        
        listeStringBatiment.clear();
        
        List<Batiment> listeDesBatiment = this.monBatimentDAO.findAll();
        for(Batiment unBatiment : listeDesBatiment){
            String nomBatiment = unBatiment.getNomBatiment();
            this.listeStringBatiment.add(nomBatiment);
        }
        
    }
    
    public void remplissageListeEnclosParBatiment(String nomBatiment){
        
        listeStringEnclosParBatiment.clear();
        
        List<Batiment> listeBatiment = this.monBatimentDAO.findAll();
        for(Batiment unBatiment : listeBatiment){
            if(unBatiment.getNomBatiment().equals(nomBatiment)){ //batiment correspondant au paramétre (nom unique)
                List <Enclos> listeEnclos = this.monEnclosDAO.findByBatiment(unBatiment);
                for(Enclos unEnclos : listeEnclos){
                    this.listeStringEnclosParBatiment.add(unEnclos.getNomEnclos());
                }
            }
        }
        
        if( listeStringEnclosParBatiment.size()==0 ){
            enclosComboBox.setDisable(true); //Désactivation de la combo box 
        }
        else{
            enclosComboBox.setDisable(false);
            enclosComboBox.setValue(listeStringEnclosParBatiment.get(0)); //Mise par défaut à la premiére valeur de la combo box
        }
        
        
    }

    
}
