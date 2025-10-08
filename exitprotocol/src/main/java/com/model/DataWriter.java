package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {
    // public ArrayList<Challenge> setTheme(String theme, int difficulty, int playerCount) {}
    public void setAvatar(String imgfile) {}
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

    public static JSONObject getUserJSON(User user) {
        JSONObject o = new JSONObject();
        o.put("firstname", user.getFirstName());
        o.put("lastname", user.getLastName());
        o.put("id", user.getUUID().toString());
        o.put("email", user.getEmail());
        o.put("password", user.getPassword());
        o.put("teamname", user.getTeamName());
        o.put("avatar", user.getAvatar());
        o.put("score", user.getScore());

        return o;
    }

    public static void main(String[] args) {
        // Create a test user
        User testUser = new User("Six", "Seven", "john.doe@example.com", "password123", "TestTeam", 1, 1, UUID.randomUUID());

        // Create DataWriter instance and save the user
        DataWriter dataWriter = new DataWriter();

        UserList userList = UserList.getInstance();
        userList.loadUsers();

        //demoing account creation rq, need to also consider if we want createAcc to take these params or a user obj
        userList.createAccount("demo","demo","demo@email.com","demo1","teamdem",1,1, UUID.randomUUID());
        ArrayList<User> users = userList.getUsers();

        users.add(testUser);
        saveUsers();

        System.out.println("Test user saved successfully!");
        System.out.println("User JSON: " + getUserJSON(testUser).toJSONString());
    }

    public void clearDataWriter() {}
    public void saveGameState() {}
    public void saveGame() {}
}
