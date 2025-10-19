package com.model;

import java.util.Scanner;
import java.util.UUID;

public class TestMain {

    public static void main(String[] args) {


        System.out.println("Welcome to **Exit Protocol**\n---------------");


        Scanner userInput = new Scanner(System.in);

        UserList userList = UserList.getInstance();
        userList.loadUsers();        
        System.out.println("Input:\n1. for Returning User\n2. For New User");
        int choice = userInput.nextInt();

        if(choice == 1){
            System.out.println("Enter email address associated with account: ");
            String emailtry = userInput.nextLine();
            System.out.println("enter pass:");
            String passWord = userInput.nextLine();

        } else if(choice ==2){
            System.out.println("work cult");
        }


        GameList gameList = GameList.getInstance();

        User testUser = userList.getUser("jodoe@email.sc.edu", "1234");
        Game createGame = new Game("null", 0, 0, "null", UUID.randomUUID());
        gameList.loadGames();
        gameList.addgame(createGame);

        GameSession session = testUser.createAndAddSession(createGame);
        
        

    }
}