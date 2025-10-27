package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 * This class gathers the atrributes from the user. 
 * The attributes being first name, last name id, email, password, and avatar. 
 * All of these attributes will be written and saved to a json file
 * @author Clankers
 */
public class DataWriter extends DataConstants {
    
    /**
     * Saves all user data from the UserList singleton instance into a json file.
     * The json file location is defined by the USER_FILE_NAME constant in the DataConstants.
     * Each User and their associated GameSessions are serialized into json format
     * and written to disk.
     */
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

    /**
     * Method used for saving game session data
     */
    public static void saveSessions() {

    }

    /**
     * Converts a User object and all its associated GameSessions
     * into a json object representation suitable for saving to disk.
     *
     * @param user the User object to convert
     * @return a json oject containing all user data, including session details
     */
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
        
            sessionYo.put("theme",userSession.getSessionTheme());
            sessionYo.put("difficulty", userSession.getDifficulty());
            sessionYo.put("playercount", userSession.getPlayerCount());

            sessionYo.put("currentChallengeIndex", userSession.getChallengeIndex());
            sessionYo.put("state", userSession.getState().toString());
            sessionYo.put("sessionName", userSession.getSessionName());
            jsonSessions.add(sessionYo);

            o.put("sessions", jsonSessions);
        }

        return o;
    }

     /**
     * Converts a Game object into a json object representation.
     * @param game the Game object to convert
     * @return a json object containing game data
     */
    public static JSONObject getGameJSON(Game game) {
        JSONObject o = new JSONObject();
        o.put("difficulty", game);
        return o;
    }

}
