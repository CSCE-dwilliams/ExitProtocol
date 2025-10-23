package com.model;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class UserList {
    private static UserList userList;
    private ArrayList<User> users;

    private UserList() {
    }

    /**
     * Demo Test Session methods
     */

    // public static void main(String[] args) {
    // System.out.println("\nTesting game\n\n");
    // runGame();
    // }

    public static void signIn() {
        UserList userList = UserList.getInstance();
        userList.loadUsers();

        while (true) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("Input:\n1. For Returning User\n2. For New User");
            int choice = userInput.nextInt();
            User playerUser;

            if (choice == 1) {
                playerUser = returnUser();
                System.out.println("\n------\nUser accessed: " + playerUser);
            } else if (choice == 2) {
                playerUser = newUser();
                User test = userList.getUser(playerUser.getEmail(), playerUser.getPassword());
            } else {
                System.out.println("Enter a valid number");
                continue;
            }

            System.out.println("Welcome " + playerUser.getFirstName() + "\n-----------------------------\n");
            System.out.println("Here are your sessions\n______________________");

            for (GameSession s : playerUser.getAllSessions()) {
                System.out.println(s);
                System.out.println("----------");
            }

            System.out.println("Which session would you like to play?\nEnter Session Name:");
            userInput.nextLine();
            String sessionChoice = userInput.nextLine();
           
           
            GameSession sessionCurrent = playerUser.chooseSession(sessionChoice);
            if (sessionCurrent != null) {
                System.out.println("Your session selection is:\n" + sessionCurrent);
            } else {
                System.out.println("Session name did not match, try again");
                continue;
            }
            System.out.println("Creating Game...\n");

            Game startGame = new Game(sessionCurrent);
            GameList gameList = GameList.getInstance();
            gameList.loadGames();
            gameList.getGameData(startGame);
            startGame.challengeStart(sessionCurrent.getChallengeIndex());

            DataWriter.saveUsers();
            
        }
    }

    public static void addSession(User addUser) {
        Scanner u = new Scanner(System.in);
        System.out.println("Enter the details for your new game session.\nEnter Team Name: ");
        String teamName = u.nextLine();
        System.out.println("Enter Session Name:");
        String sessionName = u.nextLine();
        System.out.println("Enter a theme choice\n1: Medieval\n2: Horror\n3: Fantasy\n4: Historical");
        int themeChoice = u.nextInt();
        String themeName = "";
        switch (themeChoice) {
            case 1:
                themeName = "Mystery";
                break;
            case 2:
                themeName = "Horror";
                break;
            case 3:
                themeName = "Fantasy";
                break;
            case 4:
                themeName = "Historical";
                break;
        }
        System.out.println("Enter difficulty selection[ 1: Easy | 2: Medium | 3: Hard]");
        int difficulty = u.nextInt();
        System.out.println("Enter the number of players [1-4]");
        int playerCount = u.nextInt();
        addUser.createAndAddSession(teamName, sessionName, themeName, difficulty, playerCount);

    }

    public static User returnUser() {
        while (true) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("Enter email associated with account:");
            String emailAttempt = userInput.nextLine();
            if (userList.testEmail(emailAttempt.toLowerCase())) {
                System.out.println("Enter password for the account:");
                String passAttempt = userInput.nextLine();
                if (userList.testPassword(emailAttempt, passAttempt)) {
                    System.out.println("Login Success.");
                    return userList.getUser(emailAttempt, passAttempt);
                } else {
                    System.out.println("Password not correct.");
                    continue;
                }
            } else {
                System.out.println("Email address not associated with account, try again");
                continue;
            }
        }
    }

    public static User newUser() {
        while (true) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("Enter an associated email with the account:");
            String email = userInput.nextLine();
            System.out.println("Enter a password for the account:");
            String password = userInput.nextLine();
            System.out.println("Enter a First Name for the account:");
            String firstName = userInput.nextLine();
            System.out.println("Enter a Last Name for the account:");
            String lastName = userInput.nextLine();
            System.out.println("Enter an avatar selection for the account:");
            int avatar = userInput.nextInt();
            User add = new User(firstName, lastName, email, password, avatar, UUID.randomUUID());
            userList.createAccount(firstName, lastName, email, password, avatar, UUID.randomUUID());
            return add;
        }
    }

    public static UserList getInstance() {
        if (userList == null)
            userList = new UserList();
        return userList;
    }

    public void loadUsers() {
        users = DataLoader.getUsers();
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public boolean testEmail(String email) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().toLowerCase().equals(email.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean testPassword(String email, String pass) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)
                    && users.get(i).getPassword().equals(pass)) {
                return true;
            }
        }
        return false;
    }

    public User getUser(String email, String password) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)
                    && users.get(i).getPassword().equals(password)) {
                return users.get(i);
            }
        }
        return null;
    }

    public void createAccount(
            String firstName,
            String lastName,
            String email,
            String password,
            int avatar,
            UUID id) {
        User newUser = new User(firstName, lastName, email, password, avatar, id);
        getUsers().add(newUser);
        DataWriter.saveUsers();
    }
}
