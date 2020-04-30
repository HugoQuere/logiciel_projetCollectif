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
import javafx.event.EventDispatcher;
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
    
    
    //Accés base de données
    private final BatimentDao monBatimentDAO;
    private final CageDao maCageDAO;
    private final EnclosDao monEnclosDAO;
    private final PalmipedeDao monPalmipedeDAO;
    private final PonteDao maPonteDAO;
    
    
    //Copie à un instant X de la base de données (au démarrage de la page ainsi qu'à l'appui du bouton refresh ?)
    //Nécessaire de faire une copie local de la base de données car le nombre d'accés à la base de données était trop conséquent
    private ObservableList<Batiment> listeBatiment;
    private ObservableList<Enclos> listeEnclos;
    private ObservableList<Cage> listeCages;
    private ObservableList<Ponte> listePontes;
    
   
    
    
    
    

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
        
        //Copie locale de la base de données
        this.listeBatiment = FXCollections.observableArrayList();
        this.listeEnclos = FXCollections.observableArrayList();
        this.listeCages = FXCollections.observableArrayList();
        this.listePontes = FXCollections.observableArrayList();
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
        
        // ------------------------- Remplissage base de données temporaire  ----------------------------------------------
        //Remplissage liste batiment
        List<Batiment> listeB = this.monBatimentDAO.findAll();
        this.listeBatiment.setAll(listeB);
        
        //Remplissage liste enclos
        List<Enclos> listeE = this.monEnclosDAO.findAll();
        this.listeEnclos.setAll(listeE);
        
        //Remplissage liste cage
        List<Cage> listeC = this.maCageDAO.findAll();
        this.listeCages.setAll(listeC);
        
        //Remplissage liste ponte
        List<Ponte> listeP = this.maPonteDAO.findByPeriod(LocalDate.now().minusDays(7), LocalDate.now());
        this.listePontes.setAll(listeP);
        System.out.println("NbPontes dans la liste: "+ listePontes.size());
        // ------------------------- Fin Remplissage base de données temporaire  ----------------------------------------------
        
        
        
        
        
        // ------------------------- Initialisation ComboBox  ----------------------------------------------
        
        remplissageComboBoxBatiment();
        this.batimentComboBox.setVisibleRowCount(10); //Affichage au maximum de 10 batiment avant de passer en mode liste déroulante
        this.batimentComboBox.getSelectionModel().selectFirst(); //Mise par défaut à la premiére valeur de la combo box
        
        remplissageComboBoxEnclos(listeBatiment.get(0)); //Remplissage avec le batiment par défaut
        this.enclosComboBox.setVisibleRowCount(10); //Affichage au maximum de 10 enclos avant de passer en mode liste déroulante
        this.enclosComboBox.getSelectionModel().selectFirst();//Mise par défaut à la premiére valeur de la combo box
        
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
    

    /**
     * @brief Fonction appelé lors d'une action sur la combo box batiment (c'est à dire un changement de la valeur sélectionné)
     * @param event 
     */
    @FXML private void actionComboBoxBatiment(ActionEvent event) {
        
       int numElementSelectionne = this.batimentComboBox.getSelectionModel().getSelectedIndex(); //on récupére l'index de l'élément sélectionné
       
        remplissageComboBoxEnclos(this.listeBatiment.get(numElementSelectionne)); //Remplissage de la combobox enclos avec le batiment sélectionné
                                                                                //Comme la liste de string de la combo box est une copie de la liste de batiment, on peut directement récupérer le batiment
        this.enclosComboBox.getSelectionModel().selectFirst();//Mise par défaut à la premiére valeur de la combo box
    }

    /**
     * Fonction appelé lors d'une action sur la combo box enclos (c'est à dire un changement de la valeur sélectionné)
     * @param event 
     */
    @FXML private void actionComboBoxEnclos(ActionEvent event) {
        remplissageTableau2_Visualisation(); //remplissage du deuxieme tableau en fonction des combobox
    }
    
    
    /**
     * Fonction appelé lors d'un click sur une ligne du tableau 1
     * @param event 
     */
    @FXML private void mouseClick_Tableau1(MouseEvent event) {
        TableViewSelectionModel<VisualisationTableau1> selectionModel = tableau1_Visualisation.getSelectionModel();
        
        ObservableList<Integer> selectedIndices = selectionModel.getSelectedIndices(); //get selected indices
        int elementSelectionne = selectedIndices.get(0); //Correspond au numéro de l'enclos dans la liste des enclos
        //System.out.println("Element selectionne : " + elementSelectionne);
        
        Enclos unEnclos = this.listeEnclos.get(elementSelectionne);
        Batiment batimentDeEnclosSelectionne = unEnclos.getBatiment();
        batimentComboBox.setValue(unEnclos.getBatiment().getNomBatiment());
        
        remplissageComboBoxEnclos(batimentDeEnclosSelectionne);
        enclosComboBox.setValue(unEnclos.getNomEnclos());
     
    }
    
    
    
    
    
    
    
    
    /**
     * Fonction qui rempli le tableau 1 (gauche)
     */
    private void remplissageTableau1_Visualisation(){
        listeVisualisationTableau1.clear();
        
        for(Enclos unEnclos : listeEnclos){
            
            //Nom du batiment 
            String nomBatiment = unEnclos.getBatiment().getNomBatiment();
            
            //NomEnclos
            String nomEnclos = unEnclos.getNomEnclos();
            
            //Nombre d'oeufs dans un enclos
            int nbOeufs = 0;
            
            
            List<Cage> listeCagesDeLEnclos = new ArrayList<Cage>();
            for(Cage uneCage : this.listeCages){
                if(uneCage.getEnclos().getIdEnclos() == unEnclos.getIdEnclos()){
                    listeCagesDeLEnclos.add(uneCage);
                }
            }
            
            for(Cage uneCage : listeCagesDeLEnclos){
                
                List<Ponte> listesPontesDeLaCage = new ArrayList<Ponte>();
                for(Ponte unePonte : this.listePontes){
                    if(unePonte.getCage().getIdCage() == uneCage.getIdCage()){
                        listesPontesDeLaCage.add(unePonte);
                    }
                }        
                
                for(Ponte unePonte : listesPontesDeLaCage){
                    if(unePonte.isOeufCollecte()==false && unePonte.isPresenceOeuf()==true){ //Oeuf non collecté
                        nbOeufs++;
                    }
                }
            }
            
            VisualisationTableau1 elementTableau1 = new VisualisationTableau1(nomBatiment, nomEnclos, nbOeufs);
            listeVisualisationTableau1.add(elementTableau1);
        }
        
    }
    
    
    /**
     * Fonction qui rempli le tableau 2 (droite)
     */
    private void remplissageTableau2_Visualisation(){
        listeVisualisationTableau2.clear();
        
        int numBatimentSelectionne = this.batimentComboBox.getSelectionModel().getSelectedIndex(); //on récupére l'index du batiment sélectionné
        int numEnclosSelectionne = this.enclosComboBox.getSelectionModel().getSelectedIndex(); //on récupére l'index de l'enclos sélectionné
        //System.out.println("numBatimentSelectionne : "+ numBatimentSelectionne);
        //System.out.println("numEnclosSelectionne : "+ numEnclosSelectionne);
        
        Enclos enclosSelectionne = null;
        
        Batiment unBatiment = this.listeBatiment.get(numBatimentSelectionne);
        int nbEnclosDansListe = 0;
        for(Enclos unEnclos : this.listeEnclos){
            if(unEnclos.getBatiment().getIdBatiment()==unBatiment.getIdBatiment()){ //Enclos dans le batiment
                if(nbEnclosDansListe==numEnclosSelectionne){
                    enclosSelectionne = unEnclos;
                    break;
                }
                nbEnclosDansListe++;   
            }
        }
        
        
        
        List<Cage> listeCagesDeLEnclos = new ArrayList<>();
        if(enclosSelectionne!=null){ //Sécurité, ne doit jamais arrivé
            for(Cage uneCage : this.listeCages){ //Remplissage de la liste de cages
                if(uneCage.getEnclos().getIdEnclos() == enclosSelectionne.getIdEnclos()){
                    listeCagesDeLEnclos.add(uneCage);
                }
            }
        }

        for(Cage uneCage : listeCagesDeLEnclos){

            int numCage = uneCage.getNumCage();

            int nbOeufsNonRecolte=0;
            
            List<Ponte> listesPontesDeLaCage = new ArrayList<Ponte>();
            for(Ponte unePonte : this.listePontes){
                if(unePonte.getCage().getIdCage() == uneCage.getIdCage()){
                    listesPontesDeLaCage.add(unePonte);
                }
            }        

            for(Ponte unePonte : listesPontesDeLaCage){
                if(unePonte.isOeufCollecte()==false && unePonte.isPresenceOeuf()==true){
                    nbOeufsNonRecolte++;
                }
            }
            
            
            VisualisationTableau2 unElement = new VisualisationTableau2(numCage, nbOeufsNonRecolte);
            listeVisualisationTableau2.add(unElement);

        }
    }
    
    
    
    /**
     * @brief Fonction qui permet de remplir la combo boc avec l'ensemble des batiments dans la base de données
     */
    public void remplissageComboBoxBatiment(){
        
        //Note: Il faut désactiver le gestionnaire d'événements car on déclenche un événment lorsque l'on vide un combobox
        EventDispatcher eventDispatch = this.batimentComboBox.getEventDispatcher();
        this.batimentComboBox.setEventDispatcher(null);
        this.batimentComboBox.getItems().clear(); //Vidage de la combobox
        this.batimentComboBox.setEventDispatcher(eventDispatch);
        
        for(Batiment unBatiment : this.listeBatiment){
            String nomBatiment = unBatiment.getNomBatiment();
            this.batimentComboBox.getItems().add(nomBatiment); //Ajout du nom du batiment dans la liste de la combobox
        }
    }
    
    /**
     * @brief Fonction qui permet de remplir la combo box avec les enclos d'un batiment
     * @param unBatiment, batiment dont on veut afficher les enclos
     */
    public void remplissageComboBoxEnclos(Batiment unBatiment){
        
        //Note: Il faut désactiver le gestionnaire d'événements car on déclenche un événment lorsque l'on vide un combobox
        EventDispatcher eventDispatch = this.enclosComboBox.getEventDispatcher();
        this.enclosComboBox.setEventDispatcher(null);
        this.enclosComboBox.getItems().clear(); //Vidage de la combobox
        this.enclosComboBox.setEventDispatcher(eventDispatch);
        
        for(Enclos unEnclos : this.listeEnclos){
            if(unEnclos.getBatiment().getIdBatiment()==unBatiment.getIdBatiment()){ //Enclos dans le batiment
                this.enclosComboBox.getItems().add(unEnclos.getNomEnclos());
                //System.out.println("Ajout d'un enclos dans la combobox"); //Ligne de débugage
            }
        }
        
        if( this.enclosComboBox.getItems().isEmpty() ){ //On grise la combobox lorqu'il n'y a pas d'enclos dans le batiment
            enclosComboBox.setDisable(true); //Désactivation de la combo box 
        }
        else{
            enclosComboBox.setDisable(false);
        }
        
        
    }

    
}
