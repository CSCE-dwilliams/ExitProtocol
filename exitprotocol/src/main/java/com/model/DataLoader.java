package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {
    public static void main(String[] args) {

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
                UUID id = UUID.fromString((String) personJSON.get(USER_ID));
                String firstName = (String) personJSON.get(USER_FIRST_NAME);
                String lastName = (String) personJSON.get(USER_LAST_NAME);
                String email = (String) personJSON.get(USER_EMAIL);
                String passWord = (String) personJSON.get(USER_PASSWORD);
                // String teamName = (String) personJSON.get(USER_TEAM_NAME);
                int avatar = ((Long) personJSON.get(USER_AVATAR)).intValue();
                // int score = ((Long) personJSON.get(USER_SCORE)).intValue();
                User addUser = new User(firstName, lastName, email, passWord, avatar, id);
                if(personJSON.containsKey("sessions")){
                    JSONArray sessionsArray = (JSONArray) personJSON.get(SESSIONS);
                    for(int j =0; j < sessionsArray.size();j++){
                        JSONObject sessionDat = (JSONObject) sessionsArray.get(j);
                        int difficulty =  ((Long) sessionDat.get("difficulty")).intValue();
                        int playerCount = ((Long) sessionDat.get("playercount")).intValue();
                        String sessionName = (String) sessionDat.get("sessionName");
                        String theme = (String) sessionDat.get("theme");
                        UUID sessionID = UUID.fromString((String) personJSON.get("id"));
                        String state = (String) sessionDat.get("state");
                        int challengeIndex = ((Long) sessionDat.get("currentChallengeIndex")).intValue();
                        String teamname = (String) sessionDat.get("teamname");
                        GameSession addSession =  new GameSession(sessionID, teamname, sessionName, theme, difficulty, playerCount);
                        addUser.storeGameSession(addSession);
                    }
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

            //think through helper logic
            //pass to it 
            for (int i = 0; i < peopleJSON.size(); i++) {
                JSONObject personJSON = (JSONObject) peopleJSON.get(i);
                String theme = (String) personJSON.get("theme");
                String intro = (String) personJSON.get("intro");
                
                GameTemplate newGame = new GameTemplate(theme, intro);
                JSONArray challengeArray = (JSONArray) personJSON.get("challenges");

                for(int j =0; j < challengeArray.size(); j++) {
                    JSONObject questionObj = (JSONObject) challengeArray.get(j);
                    JSONArray phraseArray = (JSONArray) questionObj.get("phrasechallenges");

                    for(int h =0; h < phraseArray.size(); h++) {
                        JSONObject phraseObj = (JSONObject) phraseArray.get(h);

                        String question = (String) phraseObj.get("question");
                        String answer = (String) phraseObj.get("answer");

                        JSONArray hintArray = (JSONArray) phraseObj.get("hints");
                        for(int l =0; l < hintArray.size();l++){
                            
                            String hint = hintArray.get(l).toString();
                            newGame.addHints(l, hint);
                        }
                        
                        String clue = (String) phraseObj.get("clue");
                        newGame.addClues(clue);
                        newGame.addQuestions(question);
                        newGame.addAnswers(answer);
                    }

                }
                games.add(newGame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return games;
    }

    private static GameTemplate parseGame(JSONObject personJSON){
        String theme = (String) personJSON.get("theme");
        String intro = (String) personJSON.get("description");

        GameTemplate newGame = new GameTemplate(theme, intro);

        JSONArray questionsArray = (JSONArray) personJSON.get("challenges");

        for(Object obj : questionsArray){

        }


        return newGame;
    }

    private static void parseQuestion(JSONObject questionObj, GameTemplate game){
        JSONArray phraseArray = (JSONArray) questionObj.get("phrasechallenges");

        for(Object obj : phraseArray){
            JSONObject phraseObj = (JSONObject) obj;

        }

    }
}