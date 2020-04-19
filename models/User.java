/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photos;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Tim
 */
public class User implements java.io.Serializable{
    //TODO: We should eventually be loading this data in from the disk.
    //TODO: Perhaps subclasses for normal user and admin? but may not be necessary
    private String username;
    private Boolean isAdmin;
    private ArrayList<Album> albums = new ArrayList<Album>();
    
    /**
     * Create new user
     * @param username User's username
     * @param isAdmin Will the user see an admin control panel?
     */
    public User( String username, Boolean isAdmin ){
        this.username = username;
        this.isAdmin = isAdmin;
    }
    
    /**
     * Turns a serialized file into a User object
     * @param path Path of the serialized file
     * @return Deserialized user
     */
    public static User deserialize( String path ){
        User des = null;
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            des = (User) in.readObject();
            in.close();
            fileIn.close();
         } catch (IOException i) {
            i.printStackTrace();
            return null;
         } catch (ClassNotFoundException c) {
            System.out.println("User class not found");
            c.printStackTrace();
            return null;
         }
        return des;
    }
    
    /**
     * Serializes the object into a .ser file
     */
    public void serialize(){
        String path = System.getProperty("user.dir") + "/data/Users/" + username + ".ser";
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
         } catch (IOException i) {
            i.printStackTrace();
         }
    }

    /**
     * Username getter
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Username setter
     * @param username New username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * isAdmin getter
     * @return True if the user is an admin
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * Sets admin permissions
     * @param isAdmin True if the user should be an admin
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Gets albums assigned to the user
     * @return ArrayList of albums
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }
    
    /**
     * Adds an album to the user's album ArrayList
     * @param album Album to add
     */
    public void addAlbum(Album album){
        albums.add(album);
    }
    
    /**
     * Removes an album from the user's ArrayList by object
     * @param album Album to remove
     */
    public void removeAlbum(Album album){
        albums.remove(album);
    }

    /**
     * Sets a user's entire album ArrayList
     * @param albums ArrayList of albums
     */
    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }
    
    
}
