package com.model;

import java.util.Scanner;

public class Driver {

    private UserList userList = UserList.getInstance();

    public Driver() {

    }

    public static void signInStart() {
        UserList userList = UserList.getInstance();

        System.out.println("Enter email");
        Scanner u = new Scanner(System.in);
        String email = u.nextLine();
        System.out.println("Enter pass");
        String pass = u.nextLine();
        User currentUser = userList.getUser(email, pass);
        while (currentUser != null) {
            int difficulty = 2;
            int playerCount = 2;
            System.out.println(currentUser);
            System.out.println("[All sessions]:\n---------------\n");

            for (GameSession s : currentUser.getAllSessions()) {
                System.out.println(s);
            }

            userList.createGameSession(currentUser, "The Clankers22", "Mystery", difficulty, playerCount);
            System.out.println("\n\n[DEMOING SESSION OVVERRIDE]\n\n");
            for (GameSession s : currentUser.getAllSessions()) {
                System.out.println(s);
            }
            userList.createGameSession(currentUser, "The Clankers", "Medieval", difficulty, playerCount);
            System.out.println("\n\n[DEMOING SESSION ADDITION]\n\n");

            for (GameSession s : currentUser.getAllSessions()) {
                System.out.println(s);
            }
            userList.saveUsers();

            break;

            // what now needs to be arranged:
            // only can have ONE session per game theme
            // means max total number of sessions can be FOUR
        }

        // userList.signInOptions(userList);

    }

}