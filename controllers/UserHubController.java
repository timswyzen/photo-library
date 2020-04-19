/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photos;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tim
 */
public class UserHubController implements Initializable {

    //tableview var initialization
    @FXML private TableView<Album> albumTable;
    @FXML private TableColumn<Album, String> titleCol;
    @FXML private TableColumn<Album, String> sizeCol;
    @FXML private TableColumn<Album, String> startCol;
    @FXML private TableColumn<Album, String> endCol;
    ArrayList<Album> myAlbums = Context.getInstance().getLoggedIn().getAlbums();
    
    //Other FXML initializations
    @FXML private Button openBtn;
    @FXML private Button renameBtn;
    @FXML private Button deleteBtn;
    @FXML private TextField newAlbumNameField;
    @FXML private Button createAlbumBtn;
    @FXML private Button logoutBtn;
    @FXML private Button quitBtn;
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private TextField tagsSearchField;
    @FXML private Button searchBtn;
    
    /*
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Alert a = new Alert(AlertType.NONE);
        
        //Populate table
        titleCol.setCellValueFactory(new PropertyValueFactory("name"));
        sizeCol.setCellValueFactory(new PropertyValueFactory("size"));
        startCol.setCellValueFactory(new PropertyValueFactory("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory("endDate"));
        
        albumTable.getItems().addAll(myAlbums);
        albumTable.getSelectionModel().selectFirst();
        
        //Open button
        EventHandler<ActionEvent> openEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                //Select item
                Album selected = albumTable.getSelectionModel().getSelectedItem();
                Context.getInstance().setSelectedAlbum(selected);
                
                //Go to album viewer
                try {
                    Parent loader = FXMLLoader.load(getClass().getResource("AlbumViewer.fxml"));
                    Stage stage = (Stage) openBtn.getScene().getWindow();
                    stage.getScene().setRoot(loader);
                    stage.sizeToScene();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        };
        openBtn.setOnAction(openEvent);
        
        //Rename button
        EventHandler<ActionEvent> renameEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                //Select album
                Album selected = albumTable.getSelectionModel().getSelectedItem();
                Context.getInstance().setSelectedAlbum(selected);
                //Go to renamer
                try {
                    Parent loader = FXMLLoader.load(getClass().getResource("AlbumRenamer.fxml"));
                    Stage stage = (Stage) renameBtn.getScene().getWindow();
                    stage.getScene().setRoot(loader);
                    stage.sizeToScene();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        };
        renameBtn.setOnAction(renameEvent);
        
        
        //Delete button
        EventHandler<ActionEvent> deleteEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                //delete from user and table
                Album toDelete = albumTable.getSelectionModel().getSelectedItem();
                Context.getInstance().getLoggedIn().removeAlbum(toDelete);
                albumTable.getItems().remove(toDelete);
                
                //delete serialization
                File file = new File(System.getProperty("user.dir") + "/data/Albums/" + toDelete.getName() + ".ser");
                file.delete();
            }
        };
        deleteBtn.setOnAction(deleteEvent);
        
        //Creating Album
        EventHandler<ActionEvent> newAlbumEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                //Make sure album doesn't exist
                File checker = new File(System.getProperty("user.dir") + "/data/Albums/" + newAlbumNameField.getText() + ".ser");
                if (checker.exists()){
                    
                    a.setAlertType(AlertType.ERROR);
                    a.setContentText("Album name already exists.");
                    a.show();
                }
                else{
                    //Create and serialize album
                    Album newAlbum = new Album(newAlbumNameField.getText());
                    Context.getInstance().getLoggedIn().addAlbum(newAlbum);
                    newAlbum.serialize();
                    albumTable.getItems().add(newAlbum);
                }
            }
        };
        createAlbumBtn.setOnAction(newAlbumEvent);
        
        //SEARCHING
        EventHandler<ActionEvent> searchEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                //Pass fields
                Context.getInstance().setSearchStart(startDate.getValue());
                Context.getInstance().setSearchEnd(endDate.getValue());
                Context.getInstance().setSearchTags(tagsSearchField.getText());
                //Go to search results menu
                try {
                    Parent loader = FXMLLoader.load(getClass().getResource("SearchResults.fxml"));
                    Stage stage = (Stage) searchBtn.getScene().getWindow();
                    stage.getScene().setRoot(loader);
                    stage.sizeToScene();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        };
        searchBtn.setOnAction(searchEvent);
        
        //Logging out
        EventHandler<ActionEvent> logoutEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                //Save EVERYTHING
                for (Album album : Context.getInstance().getLoggedIn().getAlbums()){
                   for (Photo photo : album.getPhotos())
                       photo.serialize();
                    album.serialize();
                }
                Context.getInstance().getLoggedIn().serialize();
                
                //Reset logged in; go to login menu
                Context.getInstance().setLoggedIn(null);
                try {
                    Parent loader = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    Stage stage = (Stage) renameBtn.getScene().getWindow();
                    stage.getScene().setRoot(loader);
                    stage.sizeToScene();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        };
        logoutBtn.setOnAction(logoutEvent);
        
        EventHandler<ActionEvent> quitEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                //Save EVERYTHING
                for (Album album : Context.getInstance().getLoggedIn().getAlbums()){
                   for (Photo photo : album.getPhotos())
                       photo.serialize();
                    album.serialize();
                }
                //gtfo
                Context.getInstance().getLoggedIn().serialize();
                Platform.exit();
                System.exit(0);
            }
        };
        quitBtn.setOnAction(quitEvent);
        
    }    
    
}
