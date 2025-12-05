package com.model;

import java.util.UUID;

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
    private Challenge currentChallenge;
    private GameSession currentSession;
    private static EscapeManager escapeManager;

    private EscapeManager() {
        userList = UserList.getInstance();
        userList.loadUsers();
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

    public boolean emailAlreadyExists(String email) {
        return userList.emailExists(email);
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
        gameList.loadGames();
        gameList.setGameData(game);
        currentSession = session;
        currentGame = game;

        return true;
    }

    public GameSession getCurrentSession() {
        return currentSession;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public boolean startGame() {
        userList.playGameSession(currentUser, currentSession);
        return true;
    }

    public void createAccount(String firstName, String lastName, String email, String password, int avatar) {
        userList.createAccount(firstName, lastName, email, password, avatar, UUID.randomUUID());
        currentUser = userList.getUser(email, password);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean questionCorrect(String answer) {
        return currentGame.attemptQuestion(answer);
    }

    public void nextQuestion() {
        currentGame.setPlayerIndex(currentGame.getPlayerIndex() + 1);
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
