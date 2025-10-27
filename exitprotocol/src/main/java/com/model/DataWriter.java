package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {
    // public ArrayList<Challenge> setTheme(String theme, int difficulty, int
    // playerCount) {}

    public static void saveUsers() {
        UserList userList = UserList.getInstance();
        ArrayList<User> users = userList.getUsers();
        JSONArray jsonUsers = new JSONArray();

        for (User u : users) {
            jsonUsers.add(getUserJSON(u));
        }

        try (FileWriter writer = new FileWriter(USER_FILE_NAME)) {
            writer.write(jsonUsers.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveSessions() {

    }

    public static JSONObject getUserJSON(User user) {
        JSONObject o = new JSONObject();
        o.put("firstname", user.getFirstName());
        o.put("lastname", user.getLastName());
        o.put("id", user.getUUID().toString());
        o.put("email", user.getEmail());
        o.put("password", user.getPassword());
        o.put("avatar", user.getAvatar());
        
        ArrayList<String> sessionKeys = user.getSessionIDS();

        JSONArray jsonSessions = new JSONArray();
        o.put("sessions", jsonSessions);

        for (int i = 0; i < sessionKeys.size(); i++) {

            GameSession userSession = user.getSession(UUID.fromString(sessionKeys.get(i)));
            JSONObject sessionYo = new JSONObject();

            sessionYo.put("id", userSession.getSessionID().toString());
            sessionYo.put("teamname", userSession.getTeamName());
            sessionYo.put("score", userSession.getScore());
            // sessionYo.put("progress") idk jsonObject man
            sessionYo.put("theme",userSession.getSessionTheme());
            sessionYo.put("difficulty", userSession.getDifficulty());
            sessionYo.put("playercount", userSession.getPlayerCount());

            sessionYo.put("currentChallengeIndex", userSession.getChallengeIndex());
            sessionYo.put("state", userSession.getState().toString());
            sessionYo.put("sessionName", userSession.getSessionName());
            sessionYo.put("hintsUsed", userSession.getHintsUsed());
            jsonSessions.add(sessionYo);

            o.put("sessions", jsonSessions);
        }

        return o;
    }

    public static JSONObject getGameJSON(Game game) {
        JSONObject o = new JSONObject();
        // game params theme,difficul,playercount,teamname,id
        o.put("difficulty", game);
        return o;
    }

}
