package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The user list class is a singleton that manages the user objects
 * within the system. The methods provided are for loading, saving,
 * retrieving, and updating users, as well as for validating email
 * and password credentials.
 */
public class UserList {
    private static UserList userList;
    private ArrayList<User> users;

    /*
     * -----------------------------------
     * [BASE FUNCTIONALITY METHODS]
     * -----------------------------------
     */
    /**
     * Private constrcutor for singleton pattern.
     */
    private UserList() {
        users = DataLoader.getUsers(); // added to ensure user is not null
    }

    /**
     * Returns the singleton list of users, if none exist
     *
     * @return list of users
     */
    public static UserList getInstance() {
        if (userList == null)
            userList = new UserList();
        return userList;
    }

    /**
     * Loads all users...
     */
    public void loadUsers() {
        users = DataLoader.getUsers();
    }

    /**
     * Saves all users...
     */
    public void saveUsers() {
        DataWriter.saveUsers();
    }

    /**
     * Returns the list of all User objects currently loaded.
     *
     * @return an ArrayList of user objects
     */
    public ArrayList<User> getUsers() {
        return this.users;
    }

    /**
     * Updates an existing users credentials
     *
     * @param u user object to be updated
     */
    public void updateUser(User u) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getID().equals(u.getID())) {
                users.set(i, u);
            }
        }
    }

    /*
     * -----------------------------------
     * USER ACCESS/SIGN IN, NEW USER OPTIONS
     * -----------------------------------
     */

    /**
     * Test if a user with the given email exist in the system
     *
     * @param email email address to search for
     * @return if a user with that email exist
     */
    public boolean emailExists(String email) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean gameExists(User user, String theme) {
        return user.getSession(theme) == null;
    }

    public boolean themeMatch(String theme) {
        return (theme.equalsIgnoreCase("historical") ||
                theme.equalsIgnoreCase("mystery") ||
                theme.equalsIgnoreCase("medieval"));
    }

    public boolean difficultyCorrect(int difficulty) {
        return (difficulty > 0 && difficulty <= 3);
    }

    public boolean playerCountCorrect(int playerCount) {
        return (playerCount > 0 && playerCount <= 4);
    }

    public boolean gameDataMatch(String theme, int difficulty, int playerCount) {
        return (difficultyCorrect(difficulty) && themeMatch(theme) && playerCountCorrect(playerCount));
    }

    /**
     * Authenticates and gets back a user object based on email and password
     *
     * @param email    users email address
     * @param password users password
     * @return user if the credentials match
     */
    public User getUser(String email, String password) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)
                    && users.get(i).getPassword().equals(password)) {
                return users.get(i);
            }
        }
        return null;
    }

    /**
     * Creates account based on user credentials and adds it to the list
     *
     * @param firstName first name provided by user
     * @param lastName  last name provided by user
     * @param email     email provided by user
     * @param password  password provided by user
     * @param avatar    an integer representing the users avatar choice
     * @param id        unique id of the user
     * @return the newly created User, or null if email already exists
     */
    public User createAccount(
            String firstName,
            String lastName,
            String email,
            String password,
            int avatar,
            UUID id) {
        if (emailExists(email)) {
            return null;
        }
        User newUser = new User(firstName, lastName, email, password, avatar, id);
        getUsers().add(newUser);
        DataWriter.saveUsers();
        return newUser;
    }

    public boolean createAcc(
            String firstName,
            String lastName,
            String email,
            String password,
            int avatar,
            UUID id) {
        if (emailExists(email))
            return false;
        users.add(new User(firstName, lastName, email, password, avatar, id));
        return true;
    }

    /**
     * Gets all user sessions
     *
     * @param u the user
     * @return list of game sessions, or null if none exist
     */
    public ArrayList<GameSession> getUserSessions(User u) {
        ArrayList<GameSession> userSessions = u.getAllSessions();
        if (!userSessions.isEmpty()) {
            return userSessions;
        }
        return null;
    }

    /**
     * Creates a new game session for a user
     *
     * @param user        the user creating the session
     * @param teamName    the team name
     * @param sessionName the session name
     * @param themeName   the theme name
     * @param difficulty  the difficulty level
     * @param playerCount the number of players
     * @return the newly created GameSession
     */
    public GameSession createGameSession(User user, String teamName,
            String theme, int difficulty, int playerCount) {
        if (theme.equalsIgnoreCase("Historical")) {
            theme = "Historical";
        } else if (theme.equalsIgnoreCase("Medieval")) {
            theme = "Medieval";
        } else if (theme.equalsIgnoreCase("Mystery")) {
            theme = "Mystery";
        }
        GameSession session = new GameSession(UUID.randomUUID(), teamName, theme, difficulty,
                playerCount);
        user.storeGameSession(session);

        updateUser(user);
        saveUsers();

        return session;
    }

    /**
     * Retrieves a specific game session for a user by session name
     *
     * @param u           the user
     * @param sessionName the name of the session
     * @return the GameSession if found, null otherwise
     */
    public GameSession getSessionByName(User u, String sessionName) {
        return u.chooseSession(sessionName);
    }

    /**
     * Initiates and runs a game session
     *
     * @param user    the user playing the game
     * @param session the game session to play
     * @return the updated GameSession with score and progress
     */
    public GameSession playGameSession(User user, GameSession session) {
        Game gameObject = new Game(session);
        GameList gameList = GameList.getInstance();
        gameList.loadGames();
        gameList.getGameData(gameObject);

        gameObject.challengeStart(session.getChallengeIndex());
        gameObject.runGame();

        int sessionScore = gameObject.getScore();
        int sessionIndex = gameObject.getIndex();
        session.setScore(sessionScore);
        session.setChallengeIndex(sessionIndex);

        user.storeGameSession(session);
        updateUser(user);
        saveUsers();

        return session;
    }
}
