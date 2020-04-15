package controlleur;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
import modele.entite.Batiment;
import modele.entite.Enclos;
import modele.entite.Palmipede;
import modele.entite.PalmipedeTableau;
import modele.entite.Ponte;



/**
 *
 */
public class PageFeconditeController implements Initializable {
    
    
    @FXML private TableView<PalmipedeTableau> tableauFecondite;
    @FXML private TableColumn<PalmipedeTableau, Integer> RFID_Collumn;
    @FXML private TableColumn<PalmipedeTableau, String> Batiment_Collumn;
    @FXML private TableColumn<PalmipedeTableau, String> Enclos_Collumn;
    @FXML private TableColumn<PalmipedeTableau, Integer> NbPontes_Collumn;
    @FXML private TableColumn<PalmipedeTableau, Date> DateArrive_Collumn;
    @FXML private TableColumn<PalmipedeTableau, Date> DateSortie_Collumn;
    
    @FXML private DatePicker DatePickerDebut;
    @FXML private DatePicker DatePickerFin;
    
    @FXML private Button refreshBouton;
    @FXML private Label refreshLabel;
    
    
    
    private ObservableList<PalmipedeTableau> listePalmipedesTableau;
    
    private final BatimentDao monBatimentDAO;
    private final CageDao maCageDAO;
    private final EnclosDao monEnclosDAO;
    private final PalmipedeDao monPalmipedeDAO;
    private final PonteDao maPonteDAO;
    

    public PageFeconditeController() {
        
        DaoFactory myFactory = DbFactoryDao.getInstance();
        
        this.monBatimentDAO = myFactory.getBatimentDao();
        this.maCageDAO = myFactory.getCageDao();
        this.monEnclosDAO = myFactory.getEnclosDao();
        this.monPalmipedeDAO = myFactory.getPalmipedeDao();
        this.maPonteDAO = myFactory.getPonteDao();
        
        this.listePalmipedesTableau = FXCollections.observableArrayList();
    }
        
    
    
    
    
    
    
    
    //Switching to page Visualisation
    @FXML
    public void PageVisualisationButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/vue/PageVisualisation.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);        
        //Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
    
    //Switching to page Probleme
    @FXML
     public void PageProblemeButtonPushed(ActionEvent event) throws IOException
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
        
        // ------------------------- Configuration des dates ----------------------------------------------
        configurationDefaultDate();
        
        miseAJourHeureLabelRefresh();
        // ------------------------- Fin Configuration des dates ------------------------------------------
        
        
        
        // ------------------------- Configuration du tableau de fécondité ----------------------------------------------
        RFID_Collumn.setCellValueFactory(new PropertyValueFactory<PalmipedeTableau, Integer>("numRFID"));
        Batiment_Collumn.setCellValueFactory(new PropertyValueFactory<PalmipedeTableau, String>("nomBatiment"));
        Enclos_Collumn.setCellValueFactory(new PropertyValueFactory<PalmipedeTableau, String>("nomEnclos"));        
        NbPontes_Collumn.setCellValueFactory(new PropertyValueFactory<PalmipedeTableau, Integer>("nbPontes"));
        DateArrive_Collumn.setCellValueFactory(new PropertyValueFactory<PalmipedeTableau, Date>("dateEntree"));
        DateSortie_Collumn.setCellValueFactory(new PropertyValueFactory<PalmipedeTableau, Date>("dateSortie"));
           
        
        remplissageTableauPalmipedeInternes();
        
        tableauFecondite.setItems(listePalmipedesTableau);
        // ------------------------- Fin Configuration du tableau de fécondité ------------------------------------------
        
    }
    
    
    private void remplissageTableauPalmipedeInternes(){
        
        listePalmipedesTableau.clear(); //Vidage du tableau
        
        List<Palmipede> listePalmipede= this.monPalmipedeDAO.findByDateSortie(DatePickerFin.getValue());
        for(Palmipede unPalmipede : listePalmipede){
            
            int numRFID = unPalmipede.getNumRFID();
            
            String nomBatiment = unPalmipede.getEnclos().getBatiment().getNomBatiment();
            String nomEnclos = unPalmipede.getEnclos().getNomEnclos();
            
            int nbPontes = 0;
            List<Ponte> listePontes = this.maPonteDAO.findByPalmipedeAndPeriod(unPalmipede, DatePickerDebut.getValue(), DatePickerFin.getValue() );
            for(Ponte unePonte : listePontes){
                if(unePonte.isPresenceOeuf()){
                    nbPontes++;
                }
            }
            
            Date dateEntree = unPalmipede.getDateEntree();
            Date dateSortie = unPalmipede.getDateSortie();
            
            PalmipedeTableau unPalmipedeTableau = new PalmipedeTableau(numRFID, nomBatiment, nomEnclos, nbPontes, dateEntree, dateSortie);
            listePalmipedesTableau.add(unPalmipedeTableau);
        }
    }

    @FXML
    private void changementPeriodeDatePicker(ActionEvent event) {
        
        LocalDate dateDebut = DatePickerDebut.getValue();
        LocalDate dateFin = DatePickerFin.getValue();
        
        if(dateDebut.isBefore(dateFin)){ //Si on n'a bien respecté le "sens" des dates
            remplissageTableauPalmipedeInternes(); //Comme la liste est une liste observable, cela met automatiquement à jour le tableau
        }else { //Si on a pas respecté le "sens" des dates, retour à la valeur par défaut
            configurationDefaultDate();
        }
        
        miseAJourHeureLabelRefresh();
    }
    
    private void configurationDefaultDate(){
        LocalDate dateDebut = (LocalDate.now()).minusDays(7); //Par défaut, intervalle de 7 jours à partir du jour actuel
        DatePickerDebut.setValue(dateDebut);
        
        LocalDate dateFin = LocalDate.now();
        DatePickerFin.setValue(dateFin);
    }

    @FXML
    private void refreshButtonPushed(ActionEvent event) {
        
        miseAJourHeureLabelRefresh();
        
        remplissageTableauPalmipedeInternes(); //Relance la récupération des données de la table
    }
    
    private void miseAJourHeureLabelRefresh(){
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        refreshLabel.setText("Mis à jour à: "+ format.format(calendar.getTime()));
    }

}
