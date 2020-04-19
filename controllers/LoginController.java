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
 *
 * @author Tim
 */
public class LoginController implements Initializable {
    
    @FXML
    private Button login;
    @FXML
    private TextField userentry;
    @FXML private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Update our context's user list
        for (File file : new File(System.getProperty("user.dir") + "/data/Users/").listFiles()){
            Context.getInstance().getUsers().add(User.deserialize(file.getPath()));
        }

        //Login button clicked
        EventHandler<ActionEvent> addEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                //Check for username
                Boolean foundUser = false;
                for (User u : Context.getInstance().getUsers()){
                    if (u.getUsername().equals(userentry.getText())){
                        foundUser = true;
                        Context.getInstance().setLoggedIn(u);
                        break;
                    }
                }
                
                //change scene
                if (foundUser){
                    if (Context.getInstance().getLoggedIn().getIsAdmin()){
                        try {
                            Parent loader = FXMLLoader.load(getClass().getResource("AdminHub.fxml"));
                            Stage stage = (Stage) login.getScene().getWindow();
                            stage.getScene().setRoot(loader);
                            stage.sizeToScene();
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }
                    else{
                        try {
                            Parent loader = FXMLLoader.load(getClass().getResource("UserHub.fxml"));
                            Stage stage = (Stage) login.getScene().getWindow();
                            stage.getScene().setRoot(loader);
                            stage.sizeToScene();
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }
                }
                else
                    label.setText("Wrong username. Try again:");
                
            }
        };
        
        login.setOnAction(addEvent);
    }    
    
}
