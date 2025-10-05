package com.model;

import java.util.UUID;

public class User{

    private String firstName;
    private String lastName;
    private UUID id;
    private String email;
    private String password;
    private String teamName;
    private int avatar;
    private Integer score;
    

    public User(String firstName, String lastName, String email, 
                String password, String teamName, int avatar, int score){
        
    }
    public void checkCredentials(){

    }
    public UUID getSessionData(){
        UUID sessionID = new UUID(5,5);
        return sessionID;
    }
    public Integer getScore(){
        return 0;

    }
    public boolean isMatch(String userName, String passWord){
        return true;
    }
    public void addUser(String userName, String passWord){

    }
    @Override
    public String toString(){
        return "";
    }



}