package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class SessionList {


    //think about what we want this class to hold,
    //it should load in the sessions from a json like the userlist does,
    //like the userlist it should be able to write to and read from this list
    //initializing it builds and populates the 
    private static SessionList instance;
    //gonna use sessionID to get 
    private HashMap<UUID, GameSession> sessions;

    private SessionList() {
        sessions = new HashMap<>();
    }

    public static SessionList getInstance() {
        if (instance == null) {
            instance = new SessionList();
        }
        return instance;
    }

    public GameSession createSession(Game newGame, UUID userID) {
        GameSession s = new GameSession(newGame, userID);
        sessions.put(userID, s);
        return s;
    }

    public GameSession getSession(UUID userId) {

        return null;
    }
    
    public void removeSession(UUID sessionID) {
        sessions.remove(sessionID);
    }

    public void pauseSession(UUID sessionID) {
        GameSession s = sessions.get(sessionID);
        if (s != null)
            s.pause();
    }

    public void resumeSession(UUID sessionID) {
        GameSession s = sessions.get(sessionID);
        if (s != null)
            s.resume();
    }

    public ArrayList<GameSession> getSessions() {
        return new ArrayList<>(sessions.values());
    }

}