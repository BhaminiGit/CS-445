package cs445.a4;

/**
 * This abstract data type represents the backend for a streaming radio service.
 * It stores the songs, stations, and users in the system, as well as the
 * ratings that users assign to songs.
 */
public interface StreamingRadio {

    /**
     * The abstract methods below are declared as void methods with no
     * parameters. You need to expand each declaration to specify a return type
     * and parameters, as necessary. You also need to include a detailed comment
     * for each abstract method describing its effect, its return value, any
     * corner cases that the client may need to consider, any exceptions the
     * method may throw (including a description of the circumstances under
     * which this will happen), and so on. You should include enough details
     * that a client could use this data structure without ever being surprised
     * or not knowing what will happen, even though they haven't read the
     * implementation.
     */

 	
    /**
     * Adds a new song to the system. 
     * If the song was successfully added, method will return true.
     * If the song already exists in the system  the method will return false.
     * If Song is null, the method will throw a NullPointerException.
     *  
     * @param newSong song that is going to be added
     * @return true if the song was successfully added
     * 		   false if the song was not successfully added(if there was a dupe)
     * @throws NullPointerEsception if the song was null
     */
	 public boolean addSong(Song newSong) 
     throws NullPointerException; 
	 


    /**
     * Removes an existing song from the system.
     * If the song isn't in the system, then the method will throw an IllegalArgumentException
     * If the song is null, the method will throw a NullPointerException
     * If the system is empty the method will return null
     * If the song was successfully removed from the system, the method will return the song that was removed
     * 
     * 
     *  @param aSong song that is going to be removed
     * @return the song that was removed
     * 		   or if the the system is empty, null
     * @throws NullPointerEsception if the song was null
     * @throws IllegalArguementException if aSong isn't in the system
     */
    public Song removeSong(Song aSong)
    		throws IllegalArgumentException, NullPointerException;
    
    
        
    
    /**
     * Adds an existing song to the playlist for an existing radio station.
     * If song was added successfully, then the method will return true
     * If theStation doesn't exist and/or song doesn't exist in the system, the method throws an IllegalArgumentException
     * If theStation and/or song is null, the method will throw a NullPointerException
     * If song already exists in theStation, then the method will return false
     * 
     * @param rStation station that you want to add song to 
     * @param theSong the song you want to add
     * @return true if song was added to station (things went well)
     * 		   false if song wan't added to station
     * @throws NullPointerException if either or both of the parameters are null
     * @throws IllegalArgumentException if the song or the station doesn't already exist
     * 
     */
    public boolean addToStation(Station theStation, Song theSong)
    		throws IllegalArgumentException, NullPointerException;

    
    
    
    /**
     * Removes an existing song to the playlist from an existing radio station.
     * If song was removed successfully, then the method will return the song that was removed from the station
     * If rStation doesn't exist in the system, then the method returns false
     * If theSong is not in rStation, then the method will return null.
     * If rStation and/or song is null, the method will throw a null pointer exception
     * If there are no songs in the station, the method will throw an EmptyStationException
     * 
     * @param rStation station that you want to remove song from
     * @param song the song you want to remove
     * @return true if song was removed from the station (things went well)
     * 		   false if song wan't added to station
     * @throws NullPointerrException if either or both of the parameters are null
     * @throws EmptyStationException if the station is empty
     * 
     */
    public Song removeFromStation(Station rStation, Song theSong)
    		throws IllegalArgumentException, NullPointerException;

    
    
    
    /**
     * Sets a user's rating for a song, as a number of stars from 1 to 5.
     * If the song or the user doesn't exist, the method throws an IllegalArgument exception
     * If the rating isn't from 1-5 inclusive, the method throws an Illegal Argument exception
     * If the user has already rated this song, the method will return false
     * If either of the arguments are null, the method will throw a NullPointerExcepiton
     * If all the conditions are correct, the user's rating for the song will be recorded
     * If the song is properly rated, the method will return true
     * 
     * @param user the user whose song rating that needs to be made
     * @param song the song that's going to be rated by the user
     * @param rating the number of stars for the song given by the user
     * @return true if the song was properly rated, or false if the song wasn't properly rated
     * @throws NullPointerException if any of the arguements are null
     * @throws IllegalArguement if the song/user doesn't exist, or if the rating isn't a number between 1-5 inclusive
     */
    public boolean rateSong(User user,Song song, int rating)
    		throws IllegalArgumentException, NullPointerException;

    
    
    
    
    /**
     * Clears a user's rating on a song. If this user has rated this song and
     * the rating has not already been cleared, then the rating is cleared and
     * the state will appear as if the rating was never made. If there is no
     * such rating on record (either because this user has not rated this song,
     * or because the rating has already been cleared), then this method will
     * throw an IllegalArgumentException.
     *
     * @param theUser user whose rating should be cleared
     * @param theSong song from which the user's rating should be cleared
     * @throws IllegalArgumentException if the user does not currently have a
     * rating on record for the song
     * @throws NullPointerException if either the user or the song is null
     */
    public void clearRating(User theUser, Song theSong)
    throws IllegalArgumentException, NullPointerException;
    
    

    /**
     * Predicts the rating a user will assign to a song that they have not yet
     * rated, as a number of stars from 1 to 5.
     * If the user has already rated the song, the method will return -1
     * If the user hasn't rated a song before, the method returns 3
     * If the song or the user doesnt exist, the method returns -1
     * If the song or the user is null, the method will throw a NullPointerException
     * 
     * @param theUser user who's rating the method will predict for theSong
     * @param theSong song the method will be predicting the rating for
     * @throws NullPointerException if either the user or the song is null
     * @returns the predicted rating, or -1 if something went wrong
     */
    public int predictRating(User theUser, Song theSong)
    		throws NullPointerException;
    
    
    

    /**
     * Suggests a song for a user that they are predicted to like.
     * If the user hasn't rated a song before, the method will return a random song
     * If theUser is null, the method will throw a NullPointerException
     * If the theUser does not exist, the method will throw an IllegalArguementException
     * 
     *@param theUser user for whom the method is going to suggest a song
     *@return the suggested song
     *@throws NullPointerException if theUser is null
     *@throws IllegalArguementException if the user does not exist1
     * 
     */
    public Song suggestSong(User theUser)
    		throws IllegalArgumentException, NullPointerException;

}

