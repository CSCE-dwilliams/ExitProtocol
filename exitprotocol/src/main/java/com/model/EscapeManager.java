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
    private Game currentGame;
    private GameSession currentSession;
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

    public boolean selectExistingGame(String theme) {
        if (!userList.gameExists(currentUser, theme)) {
            return false;
        }
        Game game = new Game(currentUser.getSession(theme));
        currentGame = game;
        return true;
    }

    public boolean createGame(String teamName, String theme, int difficulty, int playercount) {
        if (!userList.gameDataMatch(theme, difficulty, playercount)) {
            return false;
        }
        GameSession session = userList.createGameSession(currentUser, teamName, theme, difficulty, playercount);
        Game game = new Game(session);

        currentSession = session;
        currentGame = game;

        return true;
    }

    public boolean startGame() {
        userList.playGameSession(currentUser, currentSession);
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
