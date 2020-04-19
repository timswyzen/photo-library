/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 *
 * @author Tim
 */
public class Photos extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        
        //Quit nicely
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
           @Override
           public void handle(WindowEvent t) {
               if (Context.getInstance().getLoggedIn() != null){
                    for (Album album : Context.getInstance().getLoggedIn().getAlbums()){
                        for (Photo photo : album.getPhotos())
                            photo.serialize();
                         album.serialize();
                     }

                    Context.getInstance().getLoggedIn().serialize();
               }
                Platform.exit();
                System.exit(0);
           }
        });
        
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Test data setup
        /*Photo newP = new Photo("C:/Users/Tim/Documents/NetBeansProjects/Photos/blue.jpg");
        Photo otherP = new Photo("C:/Users/Tim/Documents/NetBeansProjects/Photos/vs emote.png");
        Album newA = new Album("clues");
        newA.addPhoto(newP);
        newA.addPhoto(otherP);
        Album otherA = new Album("deleteme");
        User tim1 = new User("tim1", false);
        User tim2 = new User("tim2", false);
        tim1.addAlbum(newA);
        tim1.addAlbum(otherA);
        tim2.serialize();
        otherA.serialize();
        newA.serialize();
        newP.serialize();
        otherP.serialize();
        tim1.serialize();*/
        
        //Launcher
        launch(args);
    }
    
    
}
