package com.model;

import java.util.UUID;

public class TestMain {

    public static void main(String[] args) {

        UserList userList = UserList.getInstance();
        userList.loadUsers();

        GameList gameList = GameList.getInstance();

        User testUser = userList.getUser("jodoe@email.sc.edu", "1234");
        Game createGame = new Game("null", 0, 0, "null", UUID.randomUUID());
        gameList.loadGames();
        gameList.addgame(createGame);

        GameSession session = testUser.createAndAddSession(createGame);
        
        // dummy testing
        //loop through and display all sessions  
        System.out.println(testUser.getSession(session.getSessionID()));

    }
}