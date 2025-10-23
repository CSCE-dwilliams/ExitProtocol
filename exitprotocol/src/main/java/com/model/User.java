package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class User {

    private String firstName;
    private String lastName;
    private UUID id;
    private String email;
    private String password;
    private int avatar;
    private Integer score;// remove later
    // sessionID to session object
    private HashMap<UUID, GameSession> sessions = new HashMap<>();

    public User(String firstName, String lastName, String email,
            String password, int avatar, UUID id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.id = id;
    }

    public UUID getID() {
        return id;
    }

    public void storeGameSession(GameSession session) {
        if (session == null)
            return;
        sessions.put(session.getSessionID(), session);
    }

    public ArrayList<String> getSessionIDS() {
        ArrayList<String> sessionKeys = new ArrayList<>();
        for (UUID id : sessions.keySet()) {
            sessionKeys.add(id.toString());
        }
        return sessionKeys;

    }

    public GameSession chooseSession(String sessionName) {
        for (GameSession s : sessions.values()) {
            if (s.getSessionName().equalsIgnoreCase(sessionName)) {
                return s;
            }
        }
        return null;
    }

    public GameSession createAndAddSession(String teamName, String sessionName, String theme, int difficulty,
            int playerCount) {
        GameSession s = new GameSession(this.id, teamName, sessionName, theme, difficulty, playerCount);
        storeGameSession(s);
        return s;
    }

    public GameSession getSession(UUID sessionID) {
        return sessions.get(sessionID);
    }

    public ArrayList<GameSession> getAllSessions() {
        return new ArrayList<>(sessions.values());
    }

    public void removeSession(UUID sessionID) {
        sessions.remove(sessionID);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAvatar() {
        return avatar;
    }

    public UUID getUUID() {
        return this.id;
    }

    public void setScore(Integer newScore) {
        score = newScore;
    }

    public Integer getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "First Name: " + firstName +
                "\nLast Name: " + lastName +
                "\nEmail: " + email +
                "\nAvatar Selection No.:" + avatar;
    }

}