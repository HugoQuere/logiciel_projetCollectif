/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class PageVisualisationController implements Initializable {

    //Switching to page1
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
        // TODO
    }    
    
}
