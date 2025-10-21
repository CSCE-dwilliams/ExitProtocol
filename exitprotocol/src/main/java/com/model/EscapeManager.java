package com.model;

public class EscapeManager {
    private Leaderboard leaderboard;
    private Timer timer;
    // private Avatar avatar;
    // private Theme theme;
    // private GameSession gameSession;
    private User user;

    public EscapeManager() {
    }

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        UserList.signIn();
    }

    public void loadGame() {
    }

    public void endGame() {
    }

    public void pauseGame() {
    }

    public void unpauseGame() {
    }

    public void showLeaderboard() {
    }

    public void showNextQuestion() {
    }

    public void attemptQuestion() {
        // Either take in a value and test it against the puzzle answer or call other
        // methods
    }

    public void logOut() {
    }

    public void logIn() {

    }

    public void createAccount() {
    }

    public void addScore() {
    }
}
