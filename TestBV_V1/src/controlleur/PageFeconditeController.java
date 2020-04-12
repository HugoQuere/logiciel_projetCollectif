package controlleur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 *
 * @author Henri
 */
public class PageFeconditeController implements Initializable {
    
    //Starting examples
    @FXML private Label label;    
    @FXML private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    /*//configure the table
    @FXML private TableView<TESTpalmipède> tableView;
    @FXML private */
    
    //Switching to page2
    public void Page2ButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/vue/PageVisualisation.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);        
        //Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
    
    //Switching to page3 
     public void Page3ButtonPushed(ActionEvent event) throws IOException
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
        // TODO
    }    
    
}
