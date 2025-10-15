package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class SessionManager {

    private static SessionManager instance;
    private HashMap<UUID, GameSession> sessions;

    private SessionManager() {
        sessions = new HashMap<>();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public GameSession createSession(Game newGame, UUID userID) {
        GameSession s = new GameSession(newGame, userID);
        sessions.put(s.getSessionID(), s);
        return s;
    }

    public GameSession getSession(UUID sessionID) {
        return sessions.get(sessionID);
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

    public ArrayList<GameSession> getAllSessions() {
        return new ArrayList<>(sessions.values());
    }

}