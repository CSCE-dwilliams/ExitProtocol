package com.model;

import java.util.HashMap;
import java.util.UUID;

public class GameSession{
    private UUID sessionID;
    private UUID gameTemplateID; // consider later
    private HashMap<UUID, Progress> playerProgress;
    private int currentChallengeIndex;
    private SessionState state;


    public GameSession(Game aGame, UUID userID){
        this.sessionID = UUID.randomUUID();
        this.playerProgress = new HashMap<>();
        this.playerProgress.put(userID, new Progress());
        this.currentChallengeIndex = 0;
        this.state = SessionState.ACTIVE;
    }

    public UUID getSessionID(){
        return this.sessionID;
    }

    public Progress getProgress(UUID userID){
        return playerProgress.get(userID);
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
        return "This sessions ID: " + sessionID + "\n This sessions current state: " + state + "\n This Session associated user:";
    }
}