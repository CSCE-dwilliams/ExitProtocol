package com.model;

import java.util.UUID;
import java.util.ArrayList;

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
    private ArrayList<Item> earnedItems; // Persistent items across questions
    private static EscapeManager escapeManager;

    private EscapeManager() {
        userList = UserList.getInstance();
        userList.loadUsers();
        gameList = GameList.getInstance();
        earnedItems = new ArrayList<>();
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

    public void correctAnswer() {
        currentGame.addScore(100);
    }

    public void hintPenalty() {
        currentGame.decScore(5);
    }

    public void wrongAnswerPenalty() {
        currentGame.decScore(10);
    }

    public boolean emailAlreadyExists(String email) {
        return userList.emailExists(email);
    }

    public boolean selectExistingGame(String theme) {
        if (!userList.gameExists(currentUser, theme)) {
            return false;
        }
        GameSession session = currentUser.getSession(theme);
        Game game = new Game(session);
        gameList.loadGames();
        gameList.setGameData(game);
        currentSession = session;
        currentGame = game;

        // Restore earned items based on current progress
        restoreEarnedItems();

        return true;
    }

    private void restoreEarnedItems() {
        earnedItems.clear();
        if (currentGame != null) {
            // Get all challenges completed so far
            int currentIndex = currentGame.getPlayerIndex();
            ArrayList<Challenge> allChallenges = currentGame.getChallenges();

            // Scan all completed challenges (0 to currentIndex-1) for items
            for (int i = 0; i < currentIndex && i < allChallenges.size(); i++) {
                Challenge challenge = allChallenges.get(i);
                ArrayList<Item> challengeItems = challenge.getItems();

                for (Item item : challengeItems) {
                    if (!earnedItems.contains(item)) {
                        earnedItems.add(item);
                    }
                }
            }
        }
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

        // Clear earned items for new game
        earnedItems.clear();

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

    public void saveGame() {
        userList.updateUser(currentUser);
        userList.saveUsers();
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

    public ArrayList<Item> getEarnedItems() {
        return earnedItems;
    }

    public void addEarnedItem(Item item) {
        earnedItems.add(item);
    }

    public void clearEarnedItems() {
        earnedItems.clear();
    }

    public void addItemsFromCurrentChallenge() {
        if (currentGame != null) {
            // Get all challenges completed up to and including current challenge
            int currentIndex = currentGame.getPlayerIndex();
            ArrayList<Challenge> allChallenges = currentGame.getChallenges();

            // Scan all completed challenges (0 to currentIndex) for items
            for (int i = 0; i <= currentIndex && i < allChallenges.size(); i++) {
                Challenge challenge = allChallenges.get(i);
                ArrayList<Item> challengeItems = challenge.getItems();

                for (Item item : challengeItems) {
                    if (!earnedItems.contains(item)) {
                        addEarnedItem(item);
                    }
                }
            }
        }
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
