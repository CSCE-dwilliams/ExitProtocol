package com.model;

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
    private int hintsUsed;
    private ChallengeProgress progress;
    private SessionState state;

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
        this.hintsUsed = 0;

    }
    public UUID getSessionID(){
        return this.sessionID;
    }
    public String getTeamName(){
        return teamName;
    }   
    public int getPercent(){
        GameList gameList = GameList.getInstance();
        gameList.loadGames();
        Game testGame = new Game(this);
        gameList.getGameData(testGame);
        testGame.assignChallenges();


        int totalChallenges = testGame.getChallenges().size();
        float percent = (((float)challengeIndex) / totalChallenges)*100;
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

    public String getSessionName(){ 
        return sessionName;
    }
    public String getSessionTheme(){
        return gameTheme;
    }
    public int getDifficulty(){
        return difficulty;
    }
    public int getPlayerCount(){
        return playerCount;
    }
    public int getChallengeIndex(){
        return challengeIndex;
    }
    public void setChallengeIndex(int index){
        this.challengeIndex = index;
    }
    public int getScore(){
        return score;
    }
    public void setScore(int score){
        this.score = score;
    }
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
    @Override
    public String toString(){
        return "Session Name: "+ this.getSessionName()+ "\nSession Team Name: " + this.getTeamName()+
        "\nSession Theme: "+ this.getSessionTheme() + "\nDifficulty: " + this.getDifficulty() + 
        "\nPlayer Count: " + this.getPlayerCount() + "\nCurrent Score: "+ this.getScore();
        
    }
}