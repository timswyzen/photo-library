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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tim
 */
public class AdminHubController implements Initializable {

    @FXML Button deleteBtn;
    @FXML ListView<User> userList;
    @FXML Button userCreateBtn;
    @FXML Button logOutBtn;
    @FXML TextField newUsername;
    ObservableList<User> users = FXCollections.observableArrayList( Context.getInstance().getUsers() );
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Set up list
        userList.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getUsername() == null) {
                    setText(null);
                } else {
                    setText(item.getUsername());
                }
            }
        });
        userList.setItems(users);
        
        
        //Delete button
        EventHandler<ActionEvent> deleteEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                //delete from user and table
                User toDelete = userList.getSelectionModel().getSelectedItem();
                Context.getInstance().removeUser(toDelete);
                userList.getItems().remove(toDelete);
                
                //delete serialization
                File file = new File(System.getProperty("user.dir") + "/data/Users/" + toDelete.getUsername() + ".ser");
                file.delete();
            }
        };
        deleteBtn.setOnAction(deleteEvent);
        
        //Create button
        EventHandler<ActionEvent> createUserEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                User newUser = new User( newUsername.getText(), false );
                userList.getItems().add(newUser);
                newUser.serialize();
            }
        };
        userCreateBtn.setOnAction(createUserEvent);
        
        //Logging out
        EventHandler<ActionEvent> logoutEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                //Save EVERYTHING
                for (User user: Context.getInstance().getUsers()){
                    user.serialize();
                }
                
                //Reset logged in; go to login menu
                Context.getInstance().setLoggedIn(null);
                try {
                    Parent loader = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    Stage stage = (Stage) logOutBtn.getScene().getWindow();
                    stage.getScene().setRoot(loader);
                    stage.sizeToScene();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        };
        logOutBtn.setOnAction(logoutEvent);
    }    
    
}
