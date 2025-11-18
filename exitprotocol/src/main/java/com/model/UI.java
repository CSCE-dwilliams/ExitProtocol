package com.model;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * UI class handles all user interaction and display logic.
 * It delegates backend operations to EscapeManager and displays results.
 */
public class UI {
    private EscapeManager manager;
    private Scanner scanner;

    public UI() {
        manager = EscapeManager.getInstance();
        scanner = new Scanner(System.in);
    }

    public void mainLoop() {
        System.out.println("WELCOME TO EXIT PROTOCOL\n*********************\nAn Escape Room Bonanza Picture by The Clankers (Copyright 2006)\n");
        System.out.println("""
  ________   _______ _______   _____  _____   ____ _______ ____   _____ ____  _
 |  ____\\ \\ / /_   _|__   __| |  __ \\|  __ \\ / __ \\__   __/ __ \\ / ____/ __ \\| |
 | |__   \\ V /  | |    | |    | |__) | |__) | |  | | | | | |  | | |   | |  | | |
 |  __|   > <   | |    | |    |  ___/|  _  /| |  | | | | | |  | | |   | |  | | |
 | |____ / . \\ _| |_   | |    | |    | | \\ \\| |__| | | | | |__| | |___| |__| | |____
 |______/_/ \\_\\_____|  |_|    |_|    |_|  \\_\\\\____/  |_|  \\____/ \\_____\\____/|______|


""");
        System.out.println("------------------------------------------------");

        manager.initialize();
        signInLoop();

        System.out.println("Thank you for playing our game.\n[Insert quote something here]");
        scanner.close();
        System.exit(0);
    }

    public void signInLoop() {
        while (true) {
            System.out.println("Input:\n1. For Returning User\n2. For New User\n3. For Leaderboard\n4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 1) {
                handleLogin();
            } else if (choice == 2) {
                handleRegistration();
            } else if (choice == 3) {
                displayLeaderboard();
                continue;
            } else if (choice == 4) {
                return;
            } else {
                System.out.println("Enter a valid number");
                continue;
            }

            if (manager.getCurrentUser() != null) {
                userSessionLoop();
            }
        }
    }

    private void handleLogin() {
        System.out.println("Enter email associated with account:");
        String email = scanner.nextLine();
        System.out.println("Enter password for the account:");
        String password = scanner.nextLine();

        User user = manager.login(email, password);

        if (user == null) {
            System.out.println("Login failed. Invalid email or password.");
        } else {
            System.out.println("Login Success.");
            System.out.println("\n------\nUser accessed: \n" + user);
            System.out.println("-----------------------------\nWelcome " + user.getFirstName()
                    + "\n-----------------------------\n");
        }
    }

    private void handleRegistration() {
        System.out.println("Enter an associated email with the account:");
        String email = scanner.nextLine();

        while (manager.emailExists(email)) {
            System.out.println("Email is already in use");
            System.out.println("Enter a new email:");
            email = scanner.nextLine();
        }

        System.out.println("Enter a password for the account:");
        String password = scanner.nextLine();
        System.out.println("Enter a First Name for the account:");
        String firstName = scanner.nextLine();
        System.out.println("Enter a Last Name for the account:");
        String lastName = scanner.nextLine();
        System.out.println("Enter an avatar selection for the account:");
        int avatar = scanner.nextInt();
        scanner.nextLine(); // consume newline

        User newUser = manager.registerUser(firstName, lastName, email, password, avatar);

        if (newUser != null) {
            System.out.println("Account created successfully!");
            System.out.println("-----------------------------\nWelcome " + newUser.getFirstName()
                    + "\n-----------------------------\n");
        } else {
            System.out.println("Failed to create account.");
        }
    }

    private void userSessionLoop() {
        displayAndManageSessions();
        playGameLoop();
        manager.saveData();
    }

    private void displayAndManageSessions() {
        System.out.println("Press 1. To see all sessions\nPress 2. To create new session");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice == 1) {
            displayUserSessions();
        } else if (choice == 2) {
            createNewSession();
            displayUserSessions();
        }
    }

    private void displayUserSessions() {
        ArrayList<GameSession> sessions = manager.getUserSessions();

        if (sessions == null || sessions.isEmpty()) {
            System.out.println("No sessions found. Create a new session to get started.");
        } else {
            System.out.println("Here are your sessions\n______________________");
            for (GameSession s : sessions) {
                System.out.println(s);
                System.out.println("Percentage of progress: " + s.getPercent() + "% done");
                System.out.println("----------");
            }
        }
    }

    private void createNewSession() {
        System.out.println("Enter the details for your new game session.\nEnter Team Name: ");
        String teamName = scanner.nextLine();
        System.out.println("Enter Session Name:");
        String sessionName = scanner.nextLine();
        System.out.println("Enter a theme choice\n1: Mystery\n2: Horror\n3: Fantasy\n4: Historical");
        int themeChoice = scanner.nextInt();

        String themeName = switch (themeChoice) {
            case 1 -> "Mystery";
            case 2 -> "Horror";
            case 3 -> "Fantasy";
            case 4 -> "Historical";
            default -> "Mystery";
        };

        System.out.println("Enter difficulty selection[ 1: Easy | 2: Medium | 3: Hard]");
        int difficulty = scanner.nextInt();
        System.out.println("Enter the number of players [1-4]");
        int playerCount = scanner.nextInt();
        scanner.nextLine(); // consume newline

        GameSession session = manager.createGameSession(teamName, sessionName, themeName,
                                                       difficulty, playerCount);

        if (session != null) {
            System.out.println("Session created successfully!");
        } else {
            System.out.println("Failed to create session.");
        }
    }

    private void playGameLoop() {
        System.out.println("\nWhich session would you like to play?\nEnter Session Name:");
        String sessionChoice = scanner.nextLine().trim();
        GameSession session = manager.getSessionByName(sessionChoice);

        while (session == null) {
            System.out.println("Invalid session name: '" + sessionChoice + "'");
            System.out.println("Please type session name from the named sessions above:");
            sessionChoice = scanner.nextLine().trim();
            session = manager.getSessionByName(sessionChoice);
        }

        if (session.getChallengeIndex() > 0) {
            System.out.println("Would you like to see the challenges answered\n1.For yes\n2.For no");
            int challengeInt = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (challengeInt == 1) {
                for (int i = 0; i < session.getChallengeIndex(); i++) {
                    int challengeNumber = i + 1;
                    System.out.println("CHALLENGE No." + challengeNumber + " Completed");
                }
            }
        }

        System.out.println("Creating Game...\n");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GameSession updatedSession = manager.playGame(session);

        if (updatedSession != null) {
            System.out.println("[SESSION END:]\n");
            System.out.println(updatedSession + "\n");
        }
    }

    private void displayLeaderboard() {
        Leaderboard lb = manager.getLeaderboard();
        lb.displayLeaderBoard();
    }

    public static void main(String[] args) {
        (new UI()).mainLoop();
    }
}