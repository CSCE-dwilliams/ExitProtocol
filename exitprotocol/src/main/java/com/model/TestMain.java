package com.model;

import java.util.UUID;

public class TestMain {

    public static void main(String[] args) {

        UserList userList = UserList.getInstance();
        userList.loadUsers();

        GameList gameList = GameList.getInstance();
        SessionManager sessionList = SessionManager.getInstance();

        User testUser = userList.getUser("jodoe@email.sc.edu", "1234");
        Game createGame = new Game("null", 0, 0, "null", UUID.randomUUID());
        gameList.loadGames();
        gameList.addgame(createGame);
        sessionList.createSession(createGame, testUser.getUUID());

        // dummy testing

        System.out.println(sessionList.getSession(testUser.getUUID()));

    }
}