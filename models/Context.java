/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photos;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Tim
 */
public class Context {
    private final static Context instance = new Context();
    
    /**
     * Get instance of Context
     * @return Context instance
     */
    public static Context getInstance() {
        return instance;
    }
    
    private ArrayList<User> users = new ArrayList<User>();
    
    /**
     * Fetches ALL users
     * @return ArrayList of users
     */
    public ArrayList<User> getUsers() {
        return users;
    }
    
    /**
     * Removes a user FROM CONTEXT (not from disk)
     * @param user User to remove
     */
    public void removeUser(User user) {
        users.remove( user );
    }
    
    /**
     * Adds a user TO CONTEXT (not to disk)
     * @param user User to add
     */
    public void addUser(User user) {
        users.add(user);
    }
    
    private User loggedUser;
    
    /**
     * Gets currently logged in user
     * @return logged in user
     */
    public User getLoggedIn() {
        return loggedUser;
    }
    
    /**
     * Sets the logged in user
     * @param user User to be logged into
     */
    public void setLoggedIn(User user) {
        loggedUser = user;
    }
    
    private Album selectedAlbum;

    /**
     * Gets currently selected Album for viewing or renaming
     * @return Album that's being read or written to
     */
    public Album getSelectedAlbum() {
        return selectedAlbum;
    }

    /**
     * Selects an album for viewing or renaming
     * @param selectedAlbum Album to read or write to
     */
    public void setSelectedAlbum(Album selectedAlbum) {
        this.selectedAlbum = selectedAlbum;
    }
    
    private LocalDate searchStart;
    
    /**
     * GEts start date for search
     * @return Start date for search
     */
    public LocalDate getSearchStart() {
        return searchStart;
    }
    
    /**
     * Sets start date for search
     * @param date Start date for search
     */
    public void setSearchStart( LocalDate date ){
        this.searchStart = date;
    }
    
    private LocalDate searchEnd;
    
    /**
     * Gets end date for search
     * @return End date for search
     */
    public LocalDate getSearchEnd() {
        return searchEnd;
    }
    
    /**
     * Sets end date for search
     * @param date End date for search
     */
    public void setSearchEnd( LocalDate date ){
        this.searchEnd = date;
    }
    
    private String searchTags;

    /**
     * Gets tags being searched by
     * @return Hopefully formatted string of tags
     */
    public String getSearchTags() {
        return searchTags;
    }

    /**
     * Sets the tags being searched by
     * @param searchTags Should be formatted as per project description
     */
    public void setSearchTags(String searchTags) {
        this.searchTags = searchTags;
    }
    
    
    
}
