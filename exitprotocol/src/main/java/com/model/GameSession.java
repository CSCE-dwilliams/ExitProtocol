package com.model;

/**
 * Acts as both a save state for the users current position and score within a game. As well as the construction means 
 * for any given game. Contains the theme data and index which allow the GameList to properly construct the corresponding
 * template and run the game at the proper index. These are stored within keyed hashmap inside USER class.
 * @author The Clankers
 */
import java.util.UUID;

public class GameSession {
    private UUID sessionID;
    private String teamName;
    private String sessionName;
    private String gameTheme;
    private int difficulty;
    private int playerCount;
    private int challengeIndex;
    private int score;
    private int hintsUsed;
    private ChallengeProgress progress;
    private SessionState state;

    public GameSession(UUID userID, String teamName, String theme, int difficulty, int playerCount) {
        this.sessionID = UUID.randomUUID();
        this.teamName = teamName;
        this.difficulty = difficulty;
        this.playerCount = playerCount;
        this.gameTheme = theme;
        this.progress = new ChallengeProgress();
        this.state = SessionState.ACTIVE;
        this.challengeIndex = 0;
        this.hintsUsed = 0;

    }

    /**
     * Returns session ID
     * 
     * @return sessionID
     */
    public UUID getSessionID() {
        return this.sessionID;
    }

    /**
     * Returns the current team name
     * 
     * @return a string of team name
     */
    public String getTeamName() {
        return teamName;
    }

    public int getPercent() {
        GameList gameList = GameList.getInstance();
        gameList.loadGames();
        Game testGame = new Game(this);
        gameList.getGameData(testGame);
        testGame.assignChallenges();

        int totalChallenges = testGame.getChallenges().size();
        float percent = (((float) challengeIndex) / totalChallenges) * 100;
        return Math.round(percent);
    }

    public int getHintsUsed() {
        return this.hintsUsed;
    }

    public void setHintsUsed(int hintsUsed) {
        this.hintsUsed = hintsUsed;
    }

    public void addHintUsed() {
        this.hintsUsed += 1;
    }

    public String getSessionName() {
        return sessionName;
    }

    /**
     * Returns session theme
     * 
     * @return a string of a session theme
     */
    public String getSessionTheme() {
        return gameTheme;
    }

    /**
     * Returns a difficulty level
     * 
     * @return an integer of difficulty level
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Returns the amount of players in the game
     * 
     * @return returns an integer of player count
     */
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * Returns the current challenge index
     * 
     * @return an integer of challenge index
     */
    public int getChallengeIndex() {
        return challengeIndex;
    }

    public void setChallengeIndex(int index) {
        this.challengeIndex = index;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ChallengeProgress getProgress() {
        return progress;
    }

    public SessionState getState() {
        return state;
    }

    public void pause() {
        state = SessionState.PAUSED;
        // countdown.pause();
    }

    public void resume() {
        state = SessionState.ACTIVE;
        // countdown.resume();
    }

    public void complete() {
        state = SessionState.COMPLETED;
        // countdown.pause();

    }

    public void advancePuzzle() {
        challengeIndex++;
    }

    public void saveSession() {
    }

    /**
     * Returns a string representation of the game session
     * 
     * @return a string of the game session details
     */
    @Override
    public String toString() {
        return "Session Team Name: " + this.getTeamName() + "\nTheme: " + this.getSessionTheme()
                + "\nDifficulty: " + this.getDifficulty() + "\nPlayer Count: " + this.getPlayerCount()
                + "\nCurrent Score: " + this.getScore();

    }
}