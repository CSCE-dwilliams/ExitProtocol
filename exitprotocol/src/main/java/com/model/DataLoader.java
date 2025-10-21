package com.model;

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

    public static ArrayList<GameTemplate> getGames() {
        ArrayList<GameTemplate> games = new ArrayList<GameTemplate>();

        try {
            FileReader reader = new FileReader(GAMES_FILE_NAME);
            JSONArray peopleJSON = (JSONArray) new JSONParser().parse(reader);

            for (int i = 0; i < peopleJSON.size(); i++) {
                JSONObject personJSON = (JSONObject) peopleJSON.get(i);
                String theme = (String) personJSON.get("theme");
                String intro = (String) personJSON.get("description");
                
                GameTemplate newGame = new GameTemplate(theme, intro);
                JSONArray questionsArray = (JSONArray) personJSON.get("challenges");

                for(int j =0; j < questionsArray.size(); j++) {
                    JSONObject questionObj = (JSONObject) questionsArray.get(j);
                    JSONArray phraseArray = (JSONArray) questionObj.get("phrasechallenges");

                    for(int h =0; h < phraseArray.size(); h++) {
                        JSONObject phraseObj = (JSONObject) phraseArray.get(h);

                        String question = (String) phraseObj.get("question");
                        String answer = (String) phraseObj.get("answer");

                        JSONArray hintArray = (JSONArray) phraseObj.get("hints");
                        
                        // for(int k =0; k < hintArray.size();k++){
                        //     String hint = (String) hintArray.get(k);
                        //     newGame.hintSet.get(h).add(hint);
                        // }
                        
                        String clue = (String) phraseObj.get("clue");
                        newGame.addClues(clue);
                        newGame.addQuestions(question);
                        newGame.answerSet.add(answer);
                    }

                }
                games.add(newGame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return games;
    }

}