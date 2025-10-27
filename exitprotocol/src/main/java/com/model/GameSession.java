package com.model;
/**
 * Acts as both a save state for the users current position and score within a game. As well as the construction means 
 * for any given game. Contains the theme data and index which allow the GameList to properly construct the corresponding
 * template and run the game at the proper index. These are stored within keyed hashmap inside USER class.
 * @author The Clankers
 */
import java.util.HashMap;
import java.util.UUID;

public class GameSession{
    private UUID sessionID;
    private String teamName;
    private String sessionName;
    private String gameTheme;
    private int difficulty;
    private int playerCount;
    private int challengeIndex;
    private int score;
    private ChallengeProgress progress;
    private SessionState state;


     /**
      * Creates a Game Session that takes in user parameters
      * @param userID assigns unique user ID to each constructed User object
      * @param teamName assigns team name to the game session
      * @param sessionName assigns a name to the game session
      * @param theme - is used to map to Game generation through game template 
      * @param difficulty - used to set the difficulty of the game session
      * @param playerCount - used to set the number of players in the game session
      */
    public GameSession(UUID userID,String teamName, String sessionName, String theme, int difficulty, int playerCount){
        this.sessionID = UUID.randomUUID();
        this.teamName = teamName;
        this.sessionName = sessionName;
        this.difficulty = difficulty;
        this.playerCount = playerCount;
        this.gameTheme = theme;
        this.progress = new ChallengeProgress();
        this.state = SessionState.ACTIVE;
        this.challengeIndex = 0;

    }
    /**
     * Returns session ID
     * @return sessionID
     */
    public UUID getSessionID(){
        return this.sessionID;
    }
    /**
     * Returns the current team name
     * @return a string of team name
     */
    public String getTeamName(){
        return teamName;
    }   
    /**
     * Returns session name
     * @return a string of a session name
     */
    public String getSessionName(){ 
        return sessionName;
    }
    /**
     * Returns session theme
     * @return a string of a session theme
     */
    public String getSessionTheme(){
        return gameTheme;
    }
    /**
     * Returns a difficulty level
     * @return an integer of difficulty level
     */
    public int getDifficulty(){
        return difficulty;
    }
    /**
     * Returns the amount of players in the game
     * @return returns an integer of player count
     */
    public int getPlayerCount(){
        return playerCount;
    }
    /**
     * Returns the current challenge index
     * @return an integer of challenge index
     */
    public int getChallengeIndex(){
        return challengeIndex;
    }
    /**
     * Returns the score of the game
     * @return the score as an integer
     */
    public int getScore(){
        return score;
    }
    /**
     * Returns the progress of the challenge
     * @return the challenge progress
     */
    public ChallengeProgress getProgress(){
        return progress;
    }

    public SessionState getState(){ return state;}
    public void pause(){
        state = SessionState.PAUSED;
        // countdown.pause();
    }
    public void resume(){
        state = SessionState.ACTIVE;
        // countdown.resume();
    }
    public void complete(){
        state = SessionState.COMPLETED;
        // countdown.pause();

    }
    
    public void advancePuzzle(){challengeIndex++;}

    public void saveSession(){
        
    }
    /**
     * Returns a string representation of the game session
     * @return a string of the game session details
     */
    @Override
    public String toString(){
        return "Session Name: "+ this.getSessionName()+ "\nSession Team Name: " + this.getTeamName()+
        "\nSession Theme: "+ this.getSessionTheme() + "\nDifficulty: " + this.getDifficulty() + 
        "\nPlayer Count: " + this.getPlayerCount();
        
    }
}