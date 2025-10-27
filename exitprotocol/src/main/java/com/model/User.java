package com.model;
/**
 * The user class store all user information and game sessions.
 * @author The Clankers
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class User {

    private String firstName;
    private String lastName;
    private UUID id;
    private String email;
    private String password;
    private int avatar;
    private Integer score;//remove later
    //sessionID to session object 
    private HashMap<UUID, GameSession> sessions = new HashMap<>();
    /**
     * Constructor for User
     * @param firstName of user
     * @param lastName of user
     * @param email of user
     * @param password of user
     * @param avatar selection of user
     * @param id unique identifier for user
     */

    public User(String firstName, String lastName, String email,
            String password, int avatar, UUID id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.id = id;
    }
    /**
     * Returns the unique identifier for user
     * @return UUID of user
     */
    public UUID getID() {
        return id;
    }
    /**
     * Stores a game session in the user's session list
     * @param session is the game session to be stored
     */
    public void storeGameSession(GameSession session) {
        if (session == null)
            return;
        sessions.put(session.getSessionID(), session);
    }

    /**
     * Returns a list of session IDs associated with the user
     * @return an array list of session IDs
     */
    public ArrayList<String> getSessionIDS(){
        ArrayList<String> sessionKeys = new ArrayList<>();
        for(UUID id: sessions.keySet()){
            sessionKeys.add(id.toString());
        }
        return sessionKeys;

    }
    /**
     * Stores a game session based on the session named by the user
     * @param sessionName is the name of the session to be chosen
     * @return
     */
    public GameSession chooseSession(String sessionName){
        for(GameSession s: sessions.values()){
            if(s.getSessionName().equalsIgnoreCase(sessionName)){
                return s;
            }
        }
        return null;
    }
    /**
     * Creates and adds a new game session to the user's session list
     * @param teamName is the name of the team
     * @param sessionName is the name of the session
     * @param theme is the theme of the game
     * @param difficulty is the difficulty level of the game
     * @param playerCount is the number of players in the game
     * @return the newly created game session
     */
    public GameSession createAndAddSession(String teamName, String sessionName, String theme, int difficulty, int playerCount) {
        GameSession s = new GameSession(this.id, teamName, sessionName,theme, difficulty,playerCount);
        storeGameSession(s);
        return s;
    }
    /**
     * Retrieves a game session based on the session ID
     * @param sessionID how the session is identified by a unique identifier
     * @return the game session with it's specific session ID
     */
    public GameSession getSession(UUID sessionID) {
        return sessions.get(sessionID);
    }
    /**
     * Retrieves all game sessions associated with the user
     * @return an array list of all game sessions
     */
    public ArrayList<GameSession> getAllSessions() {
        return new ArrayList<>(sessions.values());
    }
    /**
     * Removes a game session based on the session ID
     * @param sessionID the game session with is specific session ID to be removed
     */
    public void removeSession(UUID sessionID) {
        sessions.remove(sessionID);
    }
    /**
     * Returns first name of user
     * @return a string of first name
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Returns last name of user
     * @return a string of last name
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Returns email of user
     * @return a string of email
     */
    public String getEmail() {
        return email;
    }
    /**
     * Returns password of user
     * @return a string of password
     */
    public String getPassword() {
        return password;
    }
    /**
     * Returns avatar selection of user
     * @return integer of avatar selection
     */
    public int getAvatar() {
        return avatar;
    }
    /**
     * Returns UUID of user
     * @return UUID of user
     */
    public UUID getUUID() {
        return this.id;
    }
    /**
     * Returns score of user
     * @return integer of score
     */
    public Integer getScore() {
        return 0;

    }
    /**
     * Returns a string of the users information
     * @return a string containing user information
     */
    @Override
    public String toString() {
        return "First Name: " + firstName +
                "\nLast Name: " + lastName +
                "\nEmail: " + email +
                "\nAvatar Selection No.:" + avatar +
                "\nScore: " + score;
    }

}