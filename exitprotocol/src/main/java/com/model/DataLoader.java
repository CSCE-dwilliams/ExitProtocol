package com.model;

import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {
    public static void main(String[] args) {
        // Platform.runLater(() -> {
        // Stage stage = new Stage();
        // Timer.timerStart(stage);
        // });

        System.out.println("test");
        ArrayList<User> users = getUsers();

        for (User user : users) {
            System.out.println(user);
            System.out.println();
        }
    }

    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();

        try {
            FileReader reader = new FileReader(USER_FILE_NAME);
            JSONArray peopleJSON = (JSONArray) new JSONParser().parse(reader);

            for (int i = 0; i < peopleJSON.size(); i++) {
                JSONObject personJSON = (JSONObject) peopleJSON.get(i);
                JSONArray sessionsArray = (JSONArray) personJSON.get(SESSIONS);
                UUID id = UUID.fromString((String) personJSON.get(USER_ID));
                String firstName = (String) personJSON.get(USER_FIRST_NAME);
                String lastName = (String) personJSON.get(USER_LAST_NAME);
                String email = (String) personJSON.get(USER_EMAIL);
                String passWord = (String) personJSON.get(USER_PASSWORD);
                String teamName = (String) personJSON.get(USER_TEAM_NAME);
                int avatar = ((Long) personJSON.get(USER_AVATAR)).intValue();
                int score = ((Long) personJSON.get(USER_SCORE)).intValue();
                User addUser = new User(firstName, lastName, email, passWord, avatar, id);
                for (int j = 0; i < sessionsArray.size(); i++) {

                }
                users.add(addUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public static ArrayList<Game> getGames() {
        ArrayList<Game> games = new ArrayList<Game>();
        try {
            FileReader reader = new FileReader(GAMES_FILE_NAME);
            JSONArray peopleJSON = (JSONArray) new JSONParser().parse(reader);

            for (int i = 0; i < peopleJSON.size(); i++) {
                JSONObject personJSON = (JSONObject) peopleJSON.get(i);
                JSONArray sessionsArray = (JSONArray) personJSON.get(SESSIONS);
                UUID id = UUID.fromString((String) personJSON.get(USER_ID));
                String firstName = (String) personJSON.get(USER_FIRST_NAME);
                String lastName = (String) personJSON.get(USER_LAST_NAME);
                String email = (String) personJSON.get(USER_EMAIL);
                String passWord = (String) personJSON.get(USER_PASSWORD);
                String teamName = (String) personJSON.get(USER_TEAM_NAME);
                int avatar = ((Long) personJSON.get(USER_AVATAR)).intValue();
                int score = ((Long) personJSON.get(USER_SCORE)).intValue();
                User addUser = new User(firstName, lastName, email, passWord, avatar, id);
                for (int j = 0; i < sessionsArray.size(); i++) {

                }
                //games.add(addUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return games;
    }

}