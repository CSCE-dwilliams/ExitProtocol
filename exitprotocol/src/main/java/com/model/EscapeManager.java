package com.model;

public class EscapeManager {
    private Leaderboard leaderboard;
    // private Timer timer;
    // private Avatar avatar;
    // private Theme theme;
    // private GameSession gameSession;
    private User currentUser;
    private UserList userList;
    private GameList gameList;
    private static EscapeManager escapeManager;

    private EscapeManager() {
        userList = UserList.getInstance();
        gameList = GameList.getInstance();
    }

    public static EscapeManager getInstance() {
        if (escapeManager == null) {
            escapeManager = new EscapeManager();
        }
        return escapeManager;
    }

    public boolean signIn(String email, String password) {
        User signUser = userList.getUser(email, password);
        if (signUser == null)
            return false;
        currentUser = signUser;
        return true;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // public ArrayList<Game> getPlayerGames() {

    // }

    public void logIn() {
        Driver.signInStart();
    }

    public void logOut() {
        userList.saveUsers();
    }
}
