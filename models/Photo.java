/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photos;
import java.io.*;
import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author Tim
 */
public class Photo implements java.io.Serializable{
    private String path;
    private Date date;
    private ArrayList<Tag> tags;
    private String caption;
    private String name;
    
    /**
     * Tagless constructor. Autogenerates date and name.
     * @param path Absolute path of image
     */
    public Photo(String path) {
        this.path = path;
        File f = new File(path);
        this.date = new Date(f.lastModified());
        this.name = path.substring(path.lastIndexOf('/') + 1);
    }
    
    /**
     * Constructor with tags. Autogenerates date and name.
     * @param path Absolute path of image
     * @param tags Tags to add, in an ArrayList of Tags
     */
    public Photo(String path, ArrayList<Tag> tags) {
        this.path = path;
        File f = new File(path);
        this.date = new Date(f.lastModified());
        this.tags = tags;
        this.name = path.substring(path.lastIndexOf('/') + 1);
    }
    
    /**
     * Serializes the object into a .ser file
     */
    public void serialize(){
        String path = System.getProperty("user.dir") + "/data/Photos/" + name + ".ser";
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
     * Turns a serialized Photo file into a Photo object
     * @param path Path of serialized file
     * @return new Photo object
     */
    public static Photo deserialize( String path ){
        Photo des = null;
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            des = (Photo) in.readObject();
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
     * gets photos' caption
     * @return photo's caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     * sets photo's caption
     * @param caption caption to set
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * Name getter
     * @return Photo name
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter
     * @param name new name 
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * Path getter
     * @return Photo path
     */
    public String getPath() {
        return path;
    }

    /**
     * Path setter
     * @param path New path 
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Date getter
     * @return Photo date (last modified)
     */
    public Date getDate() {
        return date;
    }

    /**
     * Date setter
     * @param date New date 
     */
    public void setDate(Date date) {
        this.date = date;
    }
    
    /**
     * Adds a tag to the Photo's tag ArrayList
     * @param newTag Tag object to add
     */
    public void addTag(Tag newTag){
        tags.add(newTag);
    }
    
    /**
     * Removes a tag by Tag object
     * @param tag Tag to remove 
     */
    public void removeTag(Tag tag){
        tags.remove(tag);
    }
    
    
}
