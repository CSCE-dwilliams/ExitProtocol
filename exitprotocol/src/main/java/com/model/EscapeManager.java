package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * EscapeManager is the main facade/controller for the Exit Protocol application.
 * It coordinates between UserList, GameList, and other domain classes to provide
 * high-level operations with minimal logic. All business logic remains in domain classes.
 */
public class EscapeManager {
    private UserList userList;
    private GameList gameList;
    private User currentUser;
    private static EscapeManager escapeManager;

    private EscapeManager() {
        userList = UserList.getInstance();
        gameList = GameList.getInstance();
    }

    public static EscapeManager getInstance(){
        if(escapeManager == null){
            escapeManager = new EscapeManager();
        }
        return escapeManager;
    }

    /**
     * Initialize the system by loading data
     */
    public void initialize() {
        userList.loadUsers();
        gameList.loadGames();
    }

    /**
     * Authenticate a user with email and password
     *
     * @param email user's email
     * @param password user's password
     * @return authenticated User or null if authentication fails
     */
    public User login(String email, String password) {
        currentUser = userList.authenticateUser(email, password);
        return currentUser;
    }

    /**
     * Register a new user account
     *
     * @param firstName user's first name
     * @param lastName user's last name
     * @param email user's email
     * @param password user's password
     * @param avatar user's avatar selection
     * @return the newly created User or null if email already exists
     */
    public User registerUser(String firstName, String lastName, String email,
                            String password, int avatar) {
        User newUser = userList.createAccount(firstName, lastName, email, password,
                                             avatar, UUID.randomUUID());
        if (newUser != null) {
            currentUser = newUser;
        }
        return newUser;
    }

    /**
     * Check if an email already exists in the system
     *
     * @param email the email to check
     * @return true if email exists, false otherwise
     */
    public boolean emailExists(String email) {
        return userList.emailExists(email);
    }

    /**
     * Get the currently logged in user
     *
     * @return current User or null if not logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Logout the current user
     */
    public void logout() {
        currentUser = null;
    }

    /**
     * Get all game sessions for the current user
     *
     * @return list of GameSessions or null if none exist
     */
    public ArrayList<GameSession> getUserSessions() {
        if (currentUser == null) return null;
        return userList.getUserSessions(currentUser);
    }

    /**
     * Create a new game session for the current user
     *
     * @param teamName the team name
     * @param sessionName the session name
     * @param themeName the theme name
     * @param difficulty the difficulty level
     * @param playerCount the number of players
     * @return the newly created GameSession or null if no user is logged in
     */
    public GameSession createGameSession(String teamName, String sessionName,
                                        String themeName, int difficulty, int playerCount) {
        if (currentUser == null) return null;
        return userList.createGameSession(currentUser, teamName, sessionName,
                                         themeName, difficulty, playerCount);
    }

    /**
     * Get a specific game session by name for the current user
     *
     * @param sessionName the name of the session
     * @return the GameSession or null if not found
     */
    public GameSession getSessionByName(String sessionName) {
        if (currentUser == null) return null;
        return userList.getSessionByName(currentUser, sessionName);
    }

    /**
     * Start and play a game session
     *
     * @param session the GameSession to play
     * @return the updated GameSession with score and progress
     */
    public GameSession playGame(GameSession session) {
        if (currentUser == null || session == null) return null;
        return userList.playGameSession(currentUser, session);
    }

    /**
     * Get the leaderboard
     *
     * @return Leaderboard instance
     */
    public Leaderboard getLeaderboard() {
        return new Leaderboard();
    }

    /**
     * Save all data
     */
    public void saveData() {
        userList.saveUsers();
    }
}
