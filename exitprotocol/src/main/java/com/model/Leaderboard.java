package com.model;
import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

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

    // jadas idea for the leaderboard
    //private ArrayList<User> users;
    private UserList userList; // singleton

    public Leaderboard() 
    {
        // load from the most recent version from JSON file
        //this.users = DataLoader.getUsers();
        this.userList = UserList.getInstance();
        reloadUsers();
    }

    // refresh from json to grab new users and include them on the leaderboard
    public void reloadUsers() 
    {
        ArrayList<User> loadedUsers = DataLoader.getUsers();
        userList.getUsers().clear();
        userList.getUsers().addAll(DataLoader.getUsers());
        //this.users = DataLoader.getUsers();
    }

    // add a new user to the leaderboard and save to file
    public void addUser(User user) 
    {
        userList.getUsers().add(user);
        DataWriter.saveUsers(); // write to json
    }

    // sort users by total score starting with highest
    public void sortByScore() 
    {
        userList.getUsers().sort(Comparator.comparingInt(User::getScore).reversed());

    }

    public void displayLeaderBoard() 
    {
        reloadUsers();
        sortByScore();
        System.out.println("------LeaderBoard------");
        int rank = 1;
        for (User user : userList.getUsers()) {
            System.out.println(rank++ + ". " + user.getFirstName() + " " + user.getLastName()
                    + " - " + user.getScore());
        }
    }

    // my use this instaed to get all users, ideal
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
        newPlayer.setScore(0); // in order for this to work 
                                // get score and set score in the user class returns 0.
                                // there is also no setter for score
        leaderboard.addUser(newPlayer);

        System.out.println("For testing: Jada was added!");

        //leaderboard.reloadUsers();
        leaderboard.displayLeaderBoard();
    }
}


