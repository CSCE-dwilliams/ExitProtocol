package com.model;

public class Driver{

    private UserList userList = UserList.getInstance();

    public Driver(){
        
    }
    public static void signInStart(){
        UserList userList = UserList.getInstance();
        userList.signInOptions(userList);

    }



}