package controlleur;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import modele.dao.DaoFactory;
import modele.dao.EnclosDao;
import modele.dao.PalmipedeDao;
import modele.dao.PonteDao;
import modele.dao.db.DbFactoryDao;
import modele.entite.Batiment;
import modele.entite.Enclos;
import modele.entite.Palmipede;
import modele.entiteAffichage.PagePontesTableau;
import modele.entite.Ponte;
import modele.dao.NidDao;



/**
 *
 */
public class PagePontesController implements Initializable {
    
    
    @FXML private TableView<PagePontesTableau> tableauPontes;
    @FXML private TableColumn<PagePontesTableau, Integer> RFID_Collumn;
    @FXML private TableColumn<PagePontesTableau, String> Batiment_Collumn;
    @FXML private TableColumn<PagePontesTableau, String> Enclos_Collumn;
    @FXML private TableColumn<PagePontesTableau, Integer> NbPontes_Collumn;
    @FXML private TableColumn<PagePontesTableau, Integer> NbPontesParJour_Collumn;
    @FXML private TableColumn<PagePontesTableau, Time> TempsPrecensePontes_Collumn;

    
    @FXML private DatePicker DatePickerDebut;
    @FXML private DatePicker DatePickerFin;
    
    @FXML private Button refreshBouton;
    @FXML private Label refreshLabel;
    
    
    
    private ObservableList<PagePontesTableau> listePalmipedesTableau;
    
    private final BatimentDao monBatimentDAO;
    private final NidDao monNidDAO;
    private final EnclosDao monEnclosDAO;
    private final PalmipedeDao monPalmipedeDAO;
    private final PonteDao maPonteDAO;
    

    
    public PagePontesController() {
        
        DaoFactory myFactory = DbFactoryDao.getInstance();
        
        this.monBatimentDAO = myFactory.getBatimentDao();
        this.monNidDAO = myFactory.getNidDao();
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
        RFID_Collumn.setCellValueFactory(new PropertyValueFactory<PagePontesTableau, Integer>("numRFID"));
        Batiment_Collumn.setCellValueFactory(new PropertyValueFactory<PagePontesTableau, String>("nomBatiment"));
        Enclos_Collumn.setCellValueFactory(new PropertyValueFactory<PagePontesTableau, String>("nomEnclos"));        
        NbPontes_Collumn.setCellValueFactory(new PropertyValueFactory<PagePontesTableau, Integer>("nbPontes"));
        NbPontesParJour_Collumn.setCellValueFactory(new PropertyValueFactory<PagePontesTableau, Integer>("nbPontesParJour"));
        TempsPrecensePontes_Collumn.setCellValueFactory(new PropertyValueFactory<PagePontesTableau, Time>("tempsPrecensePontes"));
           
        
        remplissageTableauPalmipedeInternes();
        
        tableauPontes.setItems(listePalmipedesTableau);
        // ------------------------- Fin Configuration du tableau de fécondité ------------------------------------------
        
    }
    
    
    private void remplissageTableauPalmipedeInternes(){
        
        listePalmipedesTableau.clear(); //Vidage du tableau
        
        List<Palmipede> listePalmipede= this.monPalmipedeDAO.findByDate(DatePickerDebut.getValue(), DatePickerFin.getValue());
        for(Palmipede unPalmipede : listePalmipede){
            
            int numRFID = unPalmipede.getNumRFID();
            
            String nomBatiment = unPalmipede.getEnclos().getBatiment().getNomBatiment();
            String nomEnclos = unPalmipede.getEnclos().getNomEnclos();
            
            int nbPontes = 0;
            long tempsPonteTotal = 0;
            List<Ponte> listePontes = this.maPonteDAO.findByPalmipedeAndPeriod(unPalmipede, DatePickerDebut.getValue(), DatePickerFin.getValue() );
            for(Ponte unePonte : listePontes){
                if(unePonte.isPresenceOeuf()){
                    nbPontes++;
                }
                
                tempsPonteTotal = tempsPonteTotal + Math.abs( unePonte.getHeureFinPonte().getTime() - unePonte.getHeureDebutPonte().getTime() );
            }
            
            int nbJourDePontes = Math.abs( DatePickerFin.getValue().getDayOfYear() - DatePickerDebut.getValue().getDayOfYear());
            float nbPontesParJour = (float) nbPontes / (float) nbJourDePontes; //Nombre de pontes sur la période / le nombre de jour de la période
            
            
            
            //Calcul temps moyen pontes
            long tempsPonteMoyen=0;
            if(nbPontes!=0){
                tempsPonteMoyen = tempsPonteTotal/nbPontes;
            }
            Time TimeTempsPonteMoyen = new Time(0);
            
            //Nécessitait de passer par cette structure pour permettre de gérer les fuseaux horaires (heure d'été/heure d'hiver)
            SimpleDateFormat isoFormat = new SimpleDateFormat("HH:mm:ss");
            isoFormat.setTimeZone(TimeZone.getTimeZone("CET"));
            try {
                Date date = isoFormat.parse("00:00:00");
                TimeTempsPonteMoyen = new Time( date.getTime() + tempsPonteMoyen);
            } catch (ParseException ex) {
                Logger.getLogger(PagePontesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            PagePontesTableau unPalmipedeTableau = new PagePontesTableau(numRFID, nomBatiment, nomEnclos, nbPontes, nbPontesParJour, TimeTempsPonteMoyen);
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
