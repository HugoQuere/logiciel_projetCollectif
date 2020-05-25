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
import modele.dao.DaoFactory;
import modele.dao.EnclosDao;
import modele.dao.PalmipedeDao;
import modele.dao.PonteDao;
import modele.dao.db.DbFactoryDao;
import modele.entite.Batiment;
import modele.entite.Nid;
import modele.entite.Enclos;
import modele.entite.Ponte;
import modele.entiteAffichage.VisualisationTableau1;
import modele.entiteAffichage.VisualisationTableau2;
import modele.dao.NidDao;

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
    //Zone 1
    @FXML private TableView<VisualisationTableau2> tableau2_zone1_Visualisation;
    @FXML private TableColumn<VisualisationTableau2, Integer> tableau2_zone1_Collumn_Nids;
    @FXML private TableColumn<VisualisationTableau2, Integer> tableau2_zone1_Collumn_nbOeufs;
    private ObservableList<VisualisationTableau2> listeVisualisationTableau2_zone1;
    //Zone 2
    @FXML private TableView<VisualisationTableau2> tableau2_zone2_Visualisation;
    @FXML private TableColumn<VisualisationTableau2, Integer> tableau2_zone2_Collumn_Nids;
    @FXML private TableColumn<VisualisationTableau2, Integer> tableau2_zone2_Collumn_nbOeufs;
    private ObservableList<VisualisationTableau2> listeVisualisationTableau2_zone2;
    //Zone 3
    @FXML private TableView<VisualisationTableau2> tableau2_zone3_Visualisation;
    @FXML private TableColumn<VisualisationTableau2, Integer> tableau2_zone3_Collumn_Nids;
    @FXML private TableColumn<VisualisationTableau2, Integer> tableau2_zone3_Collumn_nbOeufs;
    private ObservableList<VisualisationTableau2> listeVisualisationTableau2_zone3;
    //Zone 4
    @FXML private TableView<VisualisationTableau2> tableau2_zone4_Visualisation;
    @FXML private TableColumn<VisualisationTableau2, Integer> tableau2_zone4_Collumn_Nids;
    @FXML private TableColumn<VisualisationTableau2, Integer> tableau2_zone4_Collumn_nbOeufs;
    private ObservableList<VisualisationTableau2> listeVisualisationTableau2_zone4;
    //Zone 5
    @FXML private TableView<VisualisationTableau2> tableau2_zone5_Visualisation;
    @FXML private TableColumn<VisualisationTableau2, Integer> tableau2_zone5_Collumn_Nids;
    @FXML private TableColumn<VisualisationTableau2, Integer> tableau2_zone5_Collumn_nbOeufs;
    private ObservableList<VisualisationTableau2> listeVisualisationTableau2_zone5;
    //Zone 6
    @FXML private TableView<VisualisationTableau2> tableau2_zone6_Visualisation;
    @FXML private TableColumn<VisualisationTableau2, Integer> tableau2_zone6_Collumn_Nids;
    @FXML private TableColumn<VisualisationTableau2, Integer> tableau2_zone6_Collumn_nbOeufs;
    private ObservableList<VisualisationTableau2> listeVisualisationTableau2_zone6;
    
    
    
    
    
    //Combo box
    @FXML private ComboBox<String> batimentComboBox;
    @FXML private ComboBox<String> enclosComboBox;
    
    
    //Accés base de données
    private final BatimentDao monBatimentDAO;
    private final NidDao monNidDAO;
    private final EnclosDao monEnclosDAO;
    private final PalmipedeDao monPalmipedeDAO;
    private final PonteDao maPonteDAO;
    
    
    //Copie à un instant X de la base de données (au démarrage de la page ainsi qu'à l'appui du bouton refresh ?)
    //Nécessaire de faire une copie local de la base de données car le nombre d'accés à la base de données était trop conséquent
    private ObservableList<Batiment> listeBatiment;
    private ObservableList<Enclos> listeEnclos;
    private ObservableList<Nid> listeNids;
    private ObservableList<Ponte> listePontes;
    
    
    

    public PageVisualisationController() {
        
        DaoFactory myFactory = DbFactoryDao.getInstance();
        
        this.monBatimentDAO = myFactory.getBatimentDao();
        this.monNidDAO = myFactory.getNidDao();
        this.monEnclosDAO = myFactory.getEnclosDao();
        this.monPalmipedeDAO = myFactory.getPalmipedeDao();
        this.maPonteDAO = myFactory.getPonteDao();
        
        //Liste tableau 1
        this.listeVisualisationTableau1 = FXCollections.observableArrayList();
        
        //Liste tableau 2
        this.listeVisualisationTableau2_zone1 = FXCollections.observableArrayList();
        this.listeVisualisationTableau2_zone2 = FXCollections.observableArrayList();
        this.listeVisualisationTableau2_zone3 = FXCollections.observableArrayList();
        this.listeVisualisationTableau2_zone4 = FXCollections.observableArrayList();
        this.listeVisualisationTableau2_zone5 = FXCollections.observableArrayList();
        this.listeVisualisationTableau2_zone6 = FXCollections.observableArrayList();
        
        //Copie locale de la base de données
        this.listeBatiment = FXCollections.observableArrayList();
        this.listeEnclos = FXCollections.observableArrayList();
        this.listeNids = FXCollections.observableArrayList();
        this.listePontes = FXCollections.observableArrayList();
    }
    
        //Switching to page1
    public void PagePontesButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/vue/PagePontes.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(tableViewScene);
        window.show();
    }
    
    //Switching to page3
    @FXML
    public void PageProblemesButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/vue/PageProbleme.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(tableViewScene);
        window.show();
    }
    
    @FXML
    private void PageSystemeButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/vue/PageSysteme.fxml"));
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
        
        //Remplissage liste nids
        List<Nid> listeC = this.monNidDAO.findAll();
        this.listeNids.setAll(listeC);
        
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
        
        //Zone 1
        tableau2_zone1_Collumn_Nids.setCellValueFactory(new PropertyValueFactory< VisualisationTableau2, Integer>("numNid"));
        tableau2_zone1_Collumn_nbOeufs.setCellValueFactory(new PropertyValueFactory< VisualisationTableau2, Integer>("nbOeufs"));
        tableau2_zone1_Visualisation.setItems(listeVisualisationTableau2_zone1);
        //Zone 2
        tableau2_zone2_Collumn_Nids.setCellValueFactory(new PropertyValueFactory< VisualisationTableau2, Integer>("numNid"));
        tableau2_zone2_Collumn_nbOeufs.setCellValueFactory(new PropertyValueFactory< VisualisationTableau2, Integer>("nbOeufs"));
        tableau2_zone2_Visualisation.setItems(listeVisualisationTableau2_zone2);
        //Zone 3
        tableau2_zone3_Collumn_Nids.setCellValueFactory(new PropertyValueFactory< VisualisationTableau2, Integer>("numNid"));
        tableau2_zone3_Collumn_nbOeufs.setCellValueFactory(new PropertyValueFactory< VisualisationTableau2, Integer>("nbOeufs"));
        tableau2_zone3_Visualisation.setItems(listeVisualisationTableau2_zone3);
        //Zone 4
        tableau2_zone4_Collumn_Nids.setCellValueFactory(new PropertyValueFactory< VisualisationTableau2, Integer>("numNid"));
        tableau2_zone4_Collumn_nbOeufs.setCellValueFactory(new PropertyValueFactory< VisualisationTableau2, Integer>("nbOeufs"));
        tableau2_zone4_Visualisation.setItems(listeVisualisationTableau2_zone4);
        //Zone 5
        tableau2_zone5_Collumn_Nids.setCellValueFactory(new PropertyValueFactory< VisualisationTableau2, Integer>("numNid"));
        tableau2_zone5_Collumn_nbOeufs.setCellValueFactory(new PropertyValueFactory< VisualisationTableau2, Integer>("nbOeufs"));
        tableau2_zone5_Visualisation.setItems(listeVisualisationTableau2_zone5);
        //Zone 6
        tableau2_zone6_Collumn_Nids.setCellValueFactory(new PropertyValueFactory< VisualisationTableau2, Integer>("numNid"));
        tableau2_zone6_Collumn_nbOeufs.setCellValueFactory(new PropertyValueFactory< VisualisationTableau2, Integer>("nbOeufs"));
        tableau2_zone6_Visualisation.setItems(listeVisualisationTableau2_zone6);
        
        remplissageTableau2_Visualisation();
        
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
            
            
            List<Nid> listeNidsDeLEnclos = new ArrayList<Nid>();
            for(Nid unNid : this.listeNids){
                if(unNid.getEnclos().getIdEnclos() == unEnclos.getIdEnclos()){
                    listeNidsDeLEnclos.add(unNid);
                }
            }
            
            for(Nid unNid : listeNidsDeLEnclos){
                
                List<Ponte> listesPontesDuNid = new ArrayList<Ponte>();
                for(Ponte unePonte : this.listePontes){
                    if(unePonte.getNid().getIdNid() == unNid.getIdNid()){
                        listesPontesDuNid.add(unePonte);
                    }
                }        
                
                for(Ponte unePonte : listesPontesDuNid){
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
        listeVisualisationTableau2_zone1.clear();
        listeVisualisationTableau2_zone2.clear();
        listeVisualisationTableau2_zone3.clear();
        listeVisualisationTableau2_zone4.clear();
        listeVisualisationTableau2_zone5.clear();
        listeVisualisationTableau2_zone6.clear();
        
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
        
        
        
        List<Nid> listeNidsDeLEnclos = new ArrayList<>();
        if(enclosSelectionne!=null){ //Sécurité, ne doit jamais arrivé
            for(Nid unNid : this.listeNids){ //Remplissage de la liste de nids
                if(unNid.getEnclos().getIdEnclos() == enclosSelectionne.getIdEnclos()){
                    listeNidsDeLEnclos.add(unNid);
                }
            }
        }

        for(Nid unNid : listeNidsDeLEnclos){

            int numNid = unNid.getNumNid();

            int nbOeufsNonRecolte=0;
            
            List<Ponte> listesPontesDuNid = new ArrayList<Ponte>();
            for(Ponte unePonte : this.listePontes){
                if(unePonte.getNid().getIdNid() == unNid.getIdNid()){
                    listesPontesDuNid.add(unePonte);
                }
            }        

            for(Ponte unePonte : listesPontesDuNid){
                if(unePonte.isOeufCollecte()==false && unePonte.isPresenceOeuf()==true){
                    nbOeufsNonRecolte++;
                }
            }
            
            VisualisationTableau2 unElement = new VisualisationTableau2(numNid, nbOeufsNonRecolte);
            
            int zone = unNid.getZone();
            switch(zone){
                case 1:
                    listeVisualisationTableau2_zone1.add(unElement);
                    break;
                case 2:
                    listeVisualisationTableau2_zone2.add(unElement);
                    break;
                case 3:
                    listeVisualisationTableau2_zone3.add(unElement);
                    break;
                case 4:
                    listeVisualisationTableau2_zone4.add(unElement);
                    break;
                case 5:
                    listeVisualisationTableau2_zone5.add(unElement);
                    break;
                case 6:
                    listeVisualisationTableau2_zone6.add(unElement);
                    break;    
            }

        }
        
        whichTableau2Affiche();
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

    /**
     * Fonction qui permet d'activer ou de désactiver les tableaux contenant les nids
     */
    public void whichTableau2Affiche(){
        //Zone 1
        if(listeVisualisationTableau2_zone1.isEmpty()){
            tableau2_zone1_Visualisation.setVisible(false);
        }else{
            tableau2_zone1_Visualisation.setVisible(true);
        }
        
        //Zone 2
        if(listeVisualisationTableau2_zone2.isEmpty()){
            tableau2_zone2_Visualisation.setVisible(false);
        }else{
            tableau2_zone2_Visualisation.setVisible(true);
        }
        
        //Zone 3
        if(listeVisualisationTableau2_zone3.isEmpty()){
            tableau2_zone3_Visualisation.setVisible(false);
        }else{
            tableau2_zone3_Visualisation.setVisible(true);
        }
        
        //Zone 4
        if(listeVisualisationTableau2_zone4.isEmpty()){
            tableau2_zone4_Visualisation.setVisible(false);
        }else{
            tableau2_zone4_Visualisation.setVisible(true);
        }
        
        //Zone 5
        if(listeVisualisationTableau2_zone5.isEmpty()){
            tableau2_zone5_Visualisation.setVisible(false);
        }else{
            tableau2_zone5_Visualisation.setVisible(true);
        }
        
        //Zone 6
        if(listeVisualisationTableau2_zone6.isEmpty()){
            tableau2_zone6_Visualisation.setVisible(false);
        }else{
            tableau2_zone6_Visualisation.setVisible(true);
        }
    }
}
