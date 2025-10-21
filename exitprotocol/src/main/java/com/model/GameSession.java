package com.model;

import java.util.HashMap;
import java.util.UUID;

public class GameSession{
    private UUID sessionID;
    private UUID gameTemplateID; // consider later
    private int currentChallengeIndex;
    private String teamName;
    private int score;
    private ChallengeProgress progress;
    private SessionState state;
    private String sessionName;
    private Game sessionGame;


    public GameSession(Game aGame, UUID userID,String teamName, String sessionName){
        this.sessionGame = aGame;
        this.sessionID = UUID.randomUUID();
        this.progress = new ChallengeProgress();
        this.state = SessionState.ACTIVE;
        this.currentChallengeIndex = 0;
        this.state = SessionState.ACTIVE;
        this.teamName = teamName;
        this.sessionName = sessionName;
    }
    public UUID getSessionID(){
        return this.sessionID;
    }
    public String getTeamName(){
        return teamName;
    }
    public int getScore(){
        return score;
    }
    public int getCurrentChallengeIndex(){
        return currentChallengeIndex;
    }
    public ChallengeProgress getProgress(){
        return progress;
    }
    public String getSessionName(){ 
        return sessionName;
    }
    public Game getSessiongame(){
        return sessionGame;
    }
    public SessionState getState(){ return state;}
    public void pause(){state = SessionState.PAUSED;}
    public void resume(){state = SessionState.ACTIVE;}
    public void complete(){ state = SessionState.COMPLETED;}
    
    public void advancePuzzle(){currentChallengeIndex++;}

    public void saveSession(){
        
    }
    @Override
    public String toString(){
        return sessionID.toString();
    }
}