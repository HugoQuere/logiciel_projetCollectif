package controlleur;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
import modele.dao.DaoFactory;
import modele.dao.ProblemeDao;
import modele.dao.db.DbFactoryDao;
import modele.entite.Probleme;

/**
 * FXML Controller class
 *
 */
public class PageProblemeController implements Initializable {
    //Nid
    @FXML
    private TableView<Probleme> tableauProblemeNid;
    @FXML
    private TableColumn<Probleme, Integer> IdCollumn_Nid;
    @FXML
    private TableColumn<Probleme, String> CommentaireCollumn_Nid;
    @FXML
    private TableColumn<Probleme, Date> DateCreationCollumn_Nid;
    @FXML
    private TableColumn<Probleme, Date> DateResolutionCollumn_Nid;
    
    private ObservableList<Probleme> listeProblemeNidTableau;
    
    private final ProblemeDao monProblemeNidDao;
    
    //Palmipede
    @FXML
    private TableView<Probleme> tableauProblemePalmipede;
    @FXML
    private TableColumn<Probleme, Integer> IdCollumn_Palmipede;
    @FXML
    private TableColumn<Probleme, String> CommentaireCollumn_Palmipede;
    @FXML
    private TableColumn<Probleme, Date> DateCreationCollumn_Palmipede;
    @FXML
    private TableColumn<Probleme, Date> DateResolutionCollumn_Palmipede;
    
    private ObservableList<Probleme> listeProblemePalmipedeTableau;
    
    private final ProblemeDao monProblemePalmipedeDao;
    
    //Systeme
    @FXML
    private TableView<Probleme> tableauProblemeSysteme;
    @FXML
    private TableColumn<Probleme, Integer> IdCollumn_Systeme;
    @FXML
    private TableColumn<Probleme, String> CommentaireCollumn_Systeme;
    @FXML
    private TableColumn<Probleme, Date> DateCreationCollumn_Systeme;
    @FXML
    private TableColumn<Probleme, Date> DateResolutionCollumn_Systeme;
    
    private ObservableList<Probleme> listeProblemeSystemeTableau;
    
    private final ProblemeDao monProblemeSystemeDao;

    public PageProblemeController() {
        
        DaoFactory myFactory = DbFactoryDao.getInstance();
        monProblemeNidDao = myFactory.getProblemeNidDao();
        monProblemePalmipedeDao = myFactory.getProblemePalmipedeDao();
        monProblemeSystemeDao = myFactory.getProblemeSystemeDao();
        
        this.listeProblemeNidTableau = FXCollections.observableArrayList();
        this.listeProblemePalmipedeTableau = FXCollections.observableArrayList();
        this.listeProblemeSystemeTableau = FXCollections.observableArrayList();
    }
    

    @FXML
    public void Page1ButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/vue/PagePontes.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);        
        //Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(tableViewScene);
        window.show();
    }
    
    @FXML
    public void Page2ButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/vue/PageVisualisation.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);        
        //Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(tableViewScene);
        window.show();
    }
         
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //NID
        IdCollumn_Nid.setCellValueFactory(new PropertyValueFactory<Probleme, Integer>("idProbleme"));
        CommentaireCollumn_Nid.setCellValueFactory(new PropertyValueFactory<Probleme, String>("commentaire"));
        DateCreationCollumn_Nid.setCellValueFactory(new PropertyValueFactory<Probleme, Date>("dateCreation"));
        DateResolutionCollumn_Nid.setCellValueFactory(new PropertyValueFactory<Probleme, Date>("dateResolution"));
        
        listeProblemeNidTableau.addAll(monProblemeNidDao.findAll());
        
        tableauProblemeNid.setItems(listeProblemeNidTableau);
        
        //Palmipede
        IdCollumn_Palmipede.setCellValueFactory(new PropertyValueFactory<Probleme, Integer>("idProbleme"));
        CommentaireCollumn_Palmipede.setCellValueFactory(new PropertyValueFactory<Probleme, String>("commentaire"));
        DateCreationCollumn_Palmipede.setCellValueFactory(new PropertyValueFactory<Probleme, Date>("dateCreation"));
        DateResolutionCollumn_Palmipede.setCellValueFactory(new PropertyValueFactory<Probleme, Date>("dateResolution"));
        
        listeProblemePalmipedeTableau.addAll(monProblemePalmipedeDao.findAll());
        
        tableauProblemePalmipede.setItems(listeProblemePalmipedeTableau);
        
        //Systeme
        IdCollumn_Systeme.setCellValueFactory(new PropertyValueFactory<Probleme, Integer>("idProbleme"));
        CommentaireCollumn_Systeme.setCellValueFactory(new PropertyValueFactory<Probleme, String>("commentaire"));
        DateCreationCollumn_Systeme.setCellValueFactory(new PropertyValueFactory<Probleme, Date>("dateCreation"));
        DateResolutionCollumn_Systeme.setCellValueFactory(new PropertyValueFactory<Probleme, Date>("dateResolution"));
        
        listeProblemeSystemeTableau.addAll(monProblemeSystemeDao.findAll());
        
        tableauProblemeSysteme.setItems(listeProblemeSystemeTableau);
    }    
    
}
