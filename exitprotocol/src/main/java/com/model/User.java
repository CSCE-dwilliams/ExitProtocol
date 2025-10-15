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
    private String teamName;
    private int avatar;
    private Integer score;
    private HashMap<UUID,GameSession> sessions = new HashMap<>();


    public User(String firstName, String lastName, String email,
            String password, String teamName, int avatar, int score, UUID id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.teamName = teamName;
        this.avatar = avatar;
        this.score = score;
        this.id = id;
    }

    public void storeGameSession(GameSession session){
        if (session == null) return;
        sessions.put(session.getSessionID(),session);
    }

    public GameSession createAndAddSession(Game game){
        GameSession s = new GameSession(game, this.id);
        storeGameSession(s);
        return s;
    }

    public GameSession getSession(UUID sessionID){
        return sessions.get(sessionID);
    }

    public ArrayList<GameSession> getAllSessions(){
        return new ArrayList<>(sessions.values());
    }

    public void removeSession(UUID sessionID){
        sessions.remove(sessionID);
    }

    public void checkCredentials() {

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

    public String getTeamName() {
        return teamName;
    }

    public int getAvatar() {
        return avatar;
    }

    public UUID getSessionData() {
        UUID sessionID = new UUID(5, 5);
        return sessionID;
    }

    public UUID getUUID() {
        return this.id;
    }

    public Integer getScore() {
        return 0;

    }

    public boolean isMatch(String userName, String passWord) {
        return true;
    }


    @Override
    public String toString() {
        return "First Name: " + firstName +
                "\nLast Name: " + lastName +
                "\nEmail: " + email +
                "\nTeam Name: " + teamName +
                "\nAvatar Selection No.:" + avatar +
                "\nScore: " + score;
    }

}