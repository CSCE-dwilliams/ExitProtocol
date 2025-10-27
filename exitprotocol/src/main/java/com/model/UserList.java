package com.model;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class UserList {
    private static UserList userList;
    private ArrayList<User> users;

    private UserList() {
    }

    public static UserList getInstance() {
        if (userList == null)
            userList = new UserList();
        return userList;
    }

    /*
     * points this Instances user ArrayList to the DataLoaders, loaded and generated User arraylist
     */
    public void loadUsers() {
        users = DataLoader.getUsers();
    }
    public void saveUsers(){
        DataWriter.saveUsers();
    }
    public ArrayList<User> getUsers() {
        return this.users;
    }

    public boolean emailAlreadyExists(String email) {

        boolean exists = false;
        for (User u : this.users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                exists = true;
            }
        }
        return exists;
    }

    public boolean testEmailSignIn(String email) {
        for (User u : users) {
            if (u.getEmail().toLowerCase().equals(email.toLowerCase())) {
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

    public void updateUser(User u ){
        for(int i =0; i < users.size();i++){
            if(users.get(i).getID().equals(u.getID())){
                users.set(i,u);
            }
        }
    }
    
    /*
     *
     *
     * DEMOING USER ACCESS AND SESSION CREATION
     *
     *
     */


    public static void signInOptions(UserList userList) {
        userList.loadUsers();

        while (true) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("Input:\n1. For Returning User\n2. For New User\n3. For Leaderboard");
            int choice = userInput.nextInt();
            User playerUser;

            if (choice == 1) {
                playerUser = returnUser();
                System.out.println("\n------\nUser accessed: \n" + playerUser);
            } else if (choice == 2) {
                playerUser = newUser();
                User test = userList.getUser(playerUser.getEmail(), playerUser.getPassword());
            } else if (choice == 3) {
                Leaderboard lb = new Leaderboard();
                lb.displayLeaderBoard();
                continue;
            } else {
                System.out.println("Enter a valid number");
                continue;
            }

            System.out.println("-----------------------------\nWelcome " + playerUser.getFirstName()
                    + "\n-----------------------------\n");

            seeAllSessions(playerUser, userInput);
            startGame(playerUser, userInput);
            userList.saveUsers();
            userList.loadUsers();
            userList.signInOptions(userList);

        }
    }


    public static void startGame(User accessUser, Scanner u) {
        GameSession sessionCurrent = null;
        DataLoader.getUsers();
        u.nextLine();
        System.out.println("\nWhich session would you like to play?\nEnter Session Name:");
        String sessionChoice = u.nextLine().trim();
        sessionCurrent = accessUser.chooseSession(sessionChoice);

        while (sessionCurrent == null) {
            System.out.println("Invalid session name: '" + sessionChoice + "'");
            System.out.println("Please type session name from the named sessions above:");
            sessionChoice = u.nextLine().trim();
            sessionCurrent = accessUser.chooseSession(sessionChoice);
        }
        if(sessionCurrent.getChallengeIndex()>0){
        System.out.println("Would you like to see the challenges answered\n1.For yes\n2.For no");
        int challengeInt = u.nextInt();
        if(challengeInt ==1){
            for(int i =0; i < sessionCurrent.getChallengeIndex();i++){
                int challengeNumber = i+1;
                System.out.println("CHALLENGE No."+ challengeNumber + " Completed");
                //insert hints used here
            }
        }
    }
        System.out.println("Creating Game...\n");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Game gameObject = new Game(sessionCurrent);
        GameList gameList = GameList.getInstance();
        //this loads in game sets of questions,answers, etc into respective template objects
        gameList.loadGames();

        gameList.getGameData(gameObject);

        gameObject.challengeStart(sessionCurrent.getChallengeIndex());
        gameObject.runGame();
        
        
        int sessionScore = gameObject.getScore();
        int sessionIndex = gameObject.getIndex();
        sessionCurrent.setScore(sessionScore);
        sessionCurrent.setChallengeIndex(sessionIndex);

        accessUser.storeGameSession(sessionCurrent);
        userList.updateUser(accessUser);

        DataWriter.saveUsers();
        
        System.out.println("[SESSION END:]\n");
        System.out.println(sessionCurrent+"\n");
        // signInOptions();

    }

    public static void seeAllSessions(User accessUser, Scanner u) {
        u.nextLine();
        System.out.println("Press 1. To see all sessions\nPress 2. To create new session");
        while (true) {
            int choice = u.nextInt();
            if (choice == 1) {
                if (!accessUser.getAllSessions().isEmpty()) {
                    System.out.println("Here are your sessions\n______________________");
                    for (GameSession s : accessUser.getAllSessions()) {
                        System.out.println(s);
                        System.out.println("Percentage of progress: "+ s.getPercent() + "% done");
                        System.out.println("----------");
                    }
                    break;
                } else {
                    System.out.println("No sessions found, press 2 to create a new session");
                }
            } else if (choice == 2) {
                addSession(accessUser, u);
                System.out.println("Here are your sessions\n______________________");
                for (GameSession s : accessUser.getAllSessions()) {
                    System.out.println(s);
                    System.out.println("Percentage of progress: "+ s.getPercent() + "% done");
                    System.out.println("----------");
                }
                DataWriter.saveUsers();
                break;
            } else {
                System.out.println("Enter a valid number choice");
                continue;
            }
        }
    }

    public static void addSession(User addUser, Scanner u) {
        u.nextLine();
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
        DataWriter.saveUsers();

    }

    public static User returnUser() {
        while (true) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("Enter email associated with account:");
            String emailAttempt = userInput.nextLine();
            if (userList.testEmailSignIn(emailAttempt.toLowerCase())) {
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
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter an associated email with the account:");
        String email = userInput.nextLine();
        while (userList.emailAlreadyExists(email)) {
            System.out.println("Email is already in use");
            System.out.println("Enter a new email:");
            email = userInput.nextLine();
        }
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
        DataWriter.saveUsers();
        return add;
    }

}
