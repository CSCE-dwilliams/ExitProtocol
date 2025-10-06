package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants{
    public static void main(String[] args){
        System.out.println("test");
        ArrayList<User> users = getUsers();

        for(User user: users){
            System.out.println(user);
            System.out.println();
        }
    }

    public static ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<User>();

        try{
            FileReader reader = new FileReader(USER_FILE_NAME);
            JSONArray peopleJSON = (JSONArray)new JSONParser().parse(reader);

            for(int i =0; i < peopleJSON.size();i++){
                JSONObject personJSON = (JSONObject)peopleJSON.get(i);
                UUID id = UUID.fromString((String)personJSON.get(USER_ID));
                String firstName = (String)personJSON.get(USER_FIRST_NAME);
                String lastName = (String)personJSON.get(USER_LAST_NAME);
                String email = (String)personJSON.get(USER_EMAIL);
                String passWord = (String)personJSON.get(USER_PASSWORD);
                String teamName = (String)personJSON.get(USER_TEAM_NAME);
                int avatar = ((Long)personJSON.get(USER_AVATAR)).intValue();
                int score = ((Long)personJSON.get(USER_SCORE)).intValue();

                users.add(new User(firstName,lastName,email,passWord,teamName,avatar,score));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return users;
    }
    public void getUser(String userName, String passWord){

    }   
    public void setAvatar(String imgFile){

    }
    // public ArrayList<Game> getGames(){
          
    // }
}