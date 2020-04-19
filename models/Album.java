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
import java.util.Date;

/**
 *
 * @author Tim
 */
public class Album implements java.io.Serializable{
    private ArrayList<Photo> photos = new ArrayList<Photo>();
    private String name;
    private int size;
    private Date startDate;
    private Date endDate;
    
    /**
     * Creates album - autogenerates size and dates
     * @param name Name of album
     */
    public Album(String name){
        this.name = name;
        photos = new ArrayList<Photo>();
        this.size = 0;
        this.startDate = null;
        this.endDate = null;
    }
    
    /**
     * Automatically updates the album's start date, end date, and size
     */
    private void updateStartEnd(){
        Date tempStart = photos.get(0).getDate();
        Date tempEnd = photos.get(0).getDate();
        int i = 0;
        for( Photo photo : photos ){
            if ( photo.getDate().before(tempStart) )
                tempStart = photo.getDate();
            if ( photo.getDate().after(tempEnd) )
                tempEnd = photo.getDate();
            i++;
        }
        this.startDate = tempStart;
        this.endDate = tempEnd;
        this.size = i; //just to add a lil efficiency, we don't have to loop again for the length func
    }
    
    /**
     * Serializes the object into a .ser file
     */
    public void serialize(){
        String path = System.getProperty("user.dir") + "/data/Albums/" + name + ".ser";
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
     * Turns a serialized Album file into an Album object
     * @param path Path of Album file
     * @return new Album object
     */
    public static Album deserialize( String path ){
        Album des = null;
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            des = (Album) in.readObject();
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
     * Adds a photo to the Album; regenerates metadata
     * @param newPhoto Photo object to add
     */
    public void addPhoto(Photo newPhoto){
        photos.add(newPhoto);
        updateStartEnd();
    }
    
    /**
     * Removes a photo from the Album; regenerates metadata
     * @param photo Photo to remove
     */
    public void removePhoto(Photo photo){
        photos.remove(photo);
        updateStartEnd();
    }

    /**
     * Photo list getter
     * @return ArrayList of photos in album
     */
    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    /**
     * Sets ALL photos in album
     * @param photos new ArrayList to point to
     */
    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
        updateStartEnd();
    }

    /**
     * Name getter
     * @return name of album
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
     * Size getter
     * @return Size of album (in number of pictures)
     */
    public int getSize() {
        return size;
    }

    /**
     * Size setter. Do not use - internal function.
     * @param size Size to set to
     */
    private void setSize(int size) {
        this.size = size;
    }

    /**
     * Start date getter
     * @return Start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Start date setter
     * @param startDate New start date. Internal functino - do not use.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * End date getter
     * @return End date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * End date setter
     * @param endDate New end date. Internal function - do not use.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    
    
    
}
