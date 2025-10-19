package com.model;

import java.util.HashMap;
import java.util.UUID;

public class GameSession{
    private UUID sessionID;
    private UUID gameTemplateID; // consider later
    private int currentChallengeIndex;
    private Progress progress;
    private SessionState state;


    public GameSession(Game aGame, UUID userID){
        this.sessionID = UUID.randomUUID();
        this.progress = new Progress();
        this.state = SessionState.ACTIVE;
        this.currentChallengeIndex = 0;
        this.state = SessionState.ACTIVE;
    }

    public UUID getSessionID(){
        return this.sessionID;
    }

    public Progress getProgress(){
        return progress;
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
        return "This sessions ID: " + sessionID + "\nThis sessions current state: " + state + "\nThis Session associated user:";
    }
}