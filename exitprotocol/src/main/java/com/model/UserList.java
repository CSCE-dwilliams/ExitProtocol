package com.model;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

/**
 * The user list class is a singleton that manages the user objects
 * within the system. The methods provided are for loading, saving,
 * retrieving, and updating users, as well as for validating email
 * and password credentials.
 */
public class UserList {
    private static UserList userList;
    private ArrayList<User> users;

    /*
     * 
     *[BASE FUNCTIONALITY METHODS]
     * 
     */
    /**
     * Private constrcutor for singleton pattern.
     */
    private UserList() {
        users = new ArrayList<>(); // added to ensure user is not null
    }

    /**
     * Returns the singleton list of users, if none exist
     * 
     * @return list of users
     */
    public static UserList getInstance() {
        if (userList == null)
            userList = new UserList();
        return userList;
    }

    /**
     * Loads all users...
     */
    public void loadUsers() {
        users = DataLoader.getUsers();
    }

    /**
     * Saves all users...
     */
    public void saveUsers() {
        DataWriter.saveUsers();
    }

    /**
     * Returns the list of all User objects currently loaded.
     * 
     * @return an ArrayList of user objects
     */
    public ArrayList<User> getUsers() {
        return this.users;
    }

    /**
     * Updates an existing users credentials
     * 
     * @param u user object to be updated
     */
    public void updateUser(User u) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getID().equals(u.getID())) {
                users.set(i, u);
            }
        }
    }


    /*
     * 
     * USER ACCESS/SIGN IN, NEW USER OPTIONS
     * 
     */

    /*
     * this one cool for now
     */
    public User returningUser(String email, String pass) {
        if (!userList.testEmailSignIn(email.toLowerCase())) {
            System.out.println("Email address not associated with account, try again");
            return null;
        } else if (userList.getUser(email, pass) == null) {
            System.out.println("Password incorrect for email");
            return null;
        } else {
            return userList.getUser(email, pass);
        }
    }

    // uses below 2 methods

    /**
     * Test if a user with the given email exist in the system
     * 
     * @param email email address to serach for
     * @return if a user with that email exist
     */
    public boolean testEmailSignIn(String email) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a user object based on email and password
     * 
     * @param email    users email address
     * @param password users password
     * @return user if the credentials match
     */
    public User getUser(String email, String password) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)
                    && users.get(i).getPassword().equals(password)) {
                return users.get(i);
            }
        }
        return null;
    }

    // FE implementation:
    public static User returnUser() {
        while (true) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("Enter email associated with account:");
            String emailAttempt = userInput.nextLine();
            if (userList.testEmailSignIn(emailAttempt.toLowerCase())) {

                System.out.println("Enter password for the account:");
                String passAttempt = userInput.nextLine();

                User testUser = userList.getUser(emailAttempt, passAttempt);

                if (testUser != null) {
                    System.out.println("Login Success.");
                    return testUser;
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




    /**
     * Creates account based on user credentials and adds it to the list
     * 
     * @param firstName first name provided by user
     * @param lastName  last name provided by user
     * @param email     email provided by user
     * @param password  password provided by user
     * @param avatar    an integer representing the users avatar choice
     * @param id        unique id of the user
     */
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

    // this is front end implementation above method:
    public static User newUser() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter an associated email with the account:");
        String email = userInput.nextLine();
        while (userList.testEmailSignIn(email)) {
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

//Gets back all user sessions
    public ArrayList<GameSession> seeSessions(User u) {
        DataLoader.getUsers();
        ArrayList<GameSession> userSessions = u.getAllSessions();
        if (!userSessions.isEmpty()) {
            return userSessions;
        } else {
            System.out.println("User has no in-progress sessions");
            return null;
        }
    }

    // FE Implementation:
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
                        System.out.println("Percentage of progress: " + s.getPercent() + "% done");
                        System.out.println("----------");
                    }
                    break;
                } else {
                    System.out.println("No sessions found, press 2 to create a new session");
                }
            } else if (choice == 2) {
                addSession(accessUser, u);
                DataLoader.getUsers();
                System.out.println("Here are your sessions\n______________________");
                for (GameSession s : accessUser.getAllSessions()) {
                    System.out.println(s);
                    System.out.println("Percentage of progress: " + s.getPercent() + "% done");
                    System.out.println("----------");
                }
                break;
            } else {
                System.out.println("Enter a valid number choice");
                continue;
            }
        }
    }


    // wont need userlist implementation as User.createAndAddSession essentially does this
    //might need to address issues of loading and writing to data
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


//Might migrate these internal methods to GameList for clarity, but these have otherwise have not been
//restructured or organized    
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
        if (sessionCurrent.getChallengeIndex() > 0) {
            System.out.println("Would you like to see the challenges answered\n1.For yes\n2.For no");
            int challengeInt = u.nextInt();
            if (challengeInt == 1) {
                for (int i = 0; i < sessionCurrent.getChallengeIndex(); i++) {
                    int challengeNumber = i + 1;
                    System.out.println("CHALLENGE No." + challengeNumber + " Completed");
                    // insert hints used here
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
        // this loads in game sets of questions,answers, etc into respective template
        // objects
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
        System.out.println(sessionCurrent + "\n");
        // signInOptions();

    }

    // This is FE container for all above methods -> can be stored in UI or elsewhere fr
    public static void signInOptions(UserList userList) {
        // calls dataloader method
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

}
