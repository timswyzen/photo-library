/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photos;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tim
 */
public class AlbumRenamerController implements Initializable {

    @FXML TextField userentry;
    @FXML Button doneBtn;
    @FXML Label label;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         EventHandler<ActionEvent> addEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                //get album selected
                Album toChange = Context.getInstance().getSelectedAlbum();
                
                
                //Serialization stuff
                File file = new File(System.getProperty("user.dir") + "/data/Albums/" + toChange.getName() + ".ser");
                File dest = new File(System.getProperty("user.dir") + "/data/Albums/" + userentry.getText() + ".ser");
                if (!file.renameTo(dest))
                    label.setText("Bad input.");
                else{

                    toChange.setName(userentry.getText());

                    //change scene
                    try {
                        Parent loader = FXMLLoader.load(getClass().getResource("UserHub.fxml"));
                        Stage stage = (Stage) doneBtn.getScene().getWindow();
                        stage.getScene().setRoot(loader);
                        stage.sizeToScene();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
                
            }
        };
        
        doneBtn.setOnAction(addEvent);
    }    
    
}
