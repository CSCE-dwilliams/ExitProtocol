package com.model;

import java.util.Scanner;

public class Driver {

    private UserList userList = UserList.getInstance();

    public Driver() {

    }

    public static void signInStart() {
        UserList userList = UserList.getInstance();
        EscapeManager manager = EscapeManager.getInstance();
        System.out.println("Enter email");
        Scanner u = new Scanner(System.in);
        String email = u.nextLine();
        System.out.println("Enter pass");
        String pass = u.nextLine();
        // current user is stored in manager
        while (manager.signIn(email, pass)) {
            User currentUser = manager.getCurrentUser();

            int difficulty = 2;
            int playerCount = 2;
            System.out.println(currentUser);
            System.out.println("[All sessions]:\n---------------\n\n");

            for (GameSession s : currentUser.getAllSessions()) {
                System.out.println(s);
            }

            // Lets play a historical game
            System.out.println("-----------------------");
            manager.createGame("theClank", "Historical", difficulty, playerCount);
            manager.createGame("theClank", "Mystery", difficulty, playerCount);
            manager.createGame("theClank", "Medieval", difficulty, playerCount);

            for (GameSession s : currentUser.getAllSessions()) {
                System.out.println(s);
            }

            manager.startGame();
            break;

        }

        // userList.signInOptions(userList);

    }

}