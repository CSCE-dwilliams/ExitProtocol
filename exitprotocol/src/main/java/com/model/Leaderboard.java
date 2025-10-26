package com.model;
import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The {@code Leaderboard} class will manage and display a list of users
 * ranked by total scores. It interacts with {@link UserList}, {@code DataLoader}, and {@link DataWriter} to 
 * load, update, and persist user data. The scores are computed based on the {@code GameSession} scores
 * associated with each user. 
 * 
 * @author Clankers
 */
public class Leaderboard {

/*  private HashMap<UUID, Integer> scoreSet = new HashMap<>();

    public void addScore(UUID id, Integer score) {
        this.scoreSet.put(id, score);
    }

    public Integer getScore(UUID id)
    {
        return this.scoreSet.get(id);
    }

    public void getScores()
    {
        Collection<Integer> scores = scoreSet.values();
        for(Integer score : scores)
        {
            System.out.println(score);
        }

    }

    public void sortScores()
    {
        SortedSet<Integer> ranking = new TreeSet<>(scoreSet.values());
        for(Integer ranks: ranking ){
            System.out.println(ranks);
        }
    */   //}//Might need to change how this works

    /**
     * Singleton of {@link UserList} with all of the registered users.
     */
    private UserList userList; // singleton

    /**
     * Constructs a {@code Leaderboard} object.
     * Retrieves the most recent user data from the json file
     * and loads it into the current leaderboard. 
     */
    public Leaderboard() 
    {
        this.userList = UserList.getInstance();
        reloadUsers(); 
    }

    /**
     * Reloads all users from the json file to ensure that the leaderboard
     * shows the most recent information from the user.
     * 
     * For existing users the {@code UserList} are cleared and replaced with the latest
     * data form the {@link DataLoader}.
     */
    public void reloadUsers() 
    {
        ArrayList<User> loadedUsers = DataLoader.getUsers();
        userList.getUsers().clear();
        userList.getUsers().addAll(loadedUsers);
    }

    /**
     * Adds a new {@link User} to the leaderboard and saves the updated
     * list to the json file.
     * @param user the {@code User} object to add
     */
    public void addUser(User user) 
    {
        userList.getUsers().add(user);
        DataWriter.saveUsers(); 
    }

    /**
     * Calculates the total score for a single user by summing all the scores 
     * of the {@link GameSession} objects.
     * @param user the {@code User} whose total score is calculated
     * @return the total score across all game sessions and returns 0 if the user has no sessions
     */
    private int calculateTotalScore(User user) 
    {
        int totalScore = 0;

        ArrayList<GameSession> sessions = user.getAllSessions();
        if (sessions != null && !sessions.isEmpty()) 
        {
            for (GameSession session : sessions) 
            {
                totalScore += session.getScore();
            }
        }

        return totalScore;
    }

    /**
     * Sorts all users in the leaderboard by their scores in descending 
     * order. The total score for each user is calculated through the {@link calculateTotalScore} method.
     * This method will not modify user data, it will rearrange the list
     * within the {@link UserList}.
     */
    public void sortByScore() 
    {
        java.util.Comparator<User> scoreComparator = new java.util.Comparator<User>() 
        {
            @Override
            public int compare(User u1, User u2) 
            {
                int total1 = calculateTotalScore(u1);
                int total2 = calculateTotalScore(u2);
                return Integer.compare(total2, total1);
            }
        };
        userList.getUsers().sort(scoreComparator);
    }
    

    /**
     * Displays the leaderboard with highest to lowest without modifying user data.
     */
    public void displayLeaderBoard() {
        reloadUsers();  
        sortByScore(); 

        System.out.println("------ Leaderboard ------");
        int rank = 1;
        for (User user : userList.getUsers()) {
            int totalScore = calculateTotalScore(user);
            System.out.println(rank++ + ". " 
                + user.getFirstName() + " " + user.getLastName() 
                + " - " + totalScore);
        }
    }


    /**
     * Returns the list of users currently stored in the leaderboard.
     * 
     * The list will reflect the most recent state {@link UserList}.
     * @return an {@code ArrayList<User>} containg all users in the leaderboard.
     */
    public ArrayList<User> getUsers() 
    {
        return userList.getUsers();
    }

    public static void main(String[] args) 
    {
        Leaderboard leaderboard = new Leaderboard();

        // display leaderboard 
        leaderboard.displayLeaderBoard();

        // example add new players, delete from json later
        User newPlayer = new User("Jada", "Young", "Jada@email.com", "12345", 0, UUID.randomUUID());
        leaderboard.addUser(newPlayer);

        System.out.println("For testing: Jada was added!");

        leaderboard.displayLeaderBoard();
    }
}


