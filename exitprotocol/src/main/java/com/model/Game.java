package com.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Game{
    private String theme;
    private int difficulty;
    private int playerCount;
    private UUID gameID;
    private int score =0;
    // public ArrayList<Challenge> gameset = new ArrayList<Challenge>();


    // thinking solution to item problem is to have another loop that checks if 
    // ArrayList<String> questions = new ArrayList<>();
    // ArrayList<String> answers = new ArrayList<>();
    // ArrayList<String> postQuestions = new ArrayList<>();
    // ArrayList<Item> items = new ArrayList<>();
    // ArrayList<ArrayList<String>> hints = new ArrayList<>();
    ArrayList<Challenge> challenges = new ArrayList<>();

    private GameTemplate gameSet;

    public Game(GameSession session) {
        this.theme = session.getSessionTheme();
        this.difficulty = session.getDifficulty();
        this.playerCount = session.getPlayerCount();
    }

    public String getIntro() {
        return "";
    }

    public String getTheme() {
        return this.theme;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public int getPlayerCount() {
        return this.playerCount;
    }

    public void setGameSet(GameTemplate aGameTemplate) {
        this.gameSet = aGameTemplate;
    }

    public void initializeGame(GameTemplate template, int startingIndex){
        //
    }

    public void challengeStart(int startingIndex) {
        gameSet.getIntro();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // questions = gameSet.getQuestions();
        // answers = gameSet.getAnswers();
        // postQuestions = gameSet.getPostQuestions();
        // hints = gameSet.getHints();
        // items = gameSet.getItems();
        challenges = gameSet.getChallenges();

        for (int i = 0; i < challenges.size(); i++) {
            int challengeNo = 1;
            challengeNo += startingIndex;
            System.out.println("ATTEMPT CHALLENGE " + challengeNo + ":\n");
            attemptQuestion(startingIndex);
            startingIndex++;
        }

    }

    public void attemptQuestion(int startingIndex) {
        Scanner u = new Scanner(System.in);
        boolean gameValid = true;
        Challenge currentChallenge = challenges.get(startingIndex);

        ArrayList<String> hintList = challenges.get(startingIndex).getHints();
        ArrayList<Item> itemList = challenges.get(startingIndex).getItems();

        String questionString = currentChallenge.getQuestion();
        String questionAnswer = currentChallenge.getAnswer();
        String questionPost = currentChallenge.getPostQuestion();

        int hintIndex = 0;

        System.out.println(questionString);
        // System.out.println(clues.get(startingIndex)); No clue for now
        while (gameValid) {
            /**
             * 
             * LOOP THAT RUNS IF ITEMS ARE PRESENT 
             * Will present 
             * [Game options]
             * [Press x button to see items]
             *              ->[Press to view x item]
             * 
             */
            if(itemList.size() >0){
                 System.out.println("Press 1. To attempt question\nPress 2. To get hint\nPress 3. To exit and quit\nPress 4. to View Items");
            int choice = u.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("\nAttempt question:");
                    u.nextLine();
                    String attempt = u.nextLine();
                    if (attempt.equalsIgnoreCase(questionAnswer)) {
                        System.out.println("\nCorrect | + 100points \n");
                        System.out.println(questionPost+"\n________________");
                        score += 100;
                        gameValid = false;
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }else{
                        System.out.println("Incorrect, try again");
                        break;
                    }
                    break;
                case 2:
                    if (hintList.size() > hintIndex) {
                        String hint = hintList.get(hintIndex);
                        if (hint != null) {
                            System.out.println(hint + "| -50 points");
                            score -= 50;
                        }
                    } else {
                        System.out.println("No more hints remaining");
                    }
                    hintIndex++;
                    break;
                case 3:
                    System.out.println("Here are your items:");
                    int i =0;
                    for(Item item : itemList){
                        i++;
                        System.out.println(i+"."+item.getName());
                    }
                    System.out.println("Press X to go back ");

                    break;
            }
            }
/**
 * 
 * DEFAULT CASE WHERE THERE IS NO ITEM
 * Probably can break these 2 loops into methods fr fr 
 * 
 */
            System.out.println("Press 1. To attempt question\nPress 2. To get hint");
            int choice = u.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("\nAttempt question:");
                    u.nextLine();
                    String attempt = u.nextLine();
                    if (attempt.equalsIgnoreCase(questionAnswer)) {
                        System.out.println("\nCorrect | + 100points \n");
                        System.out.println(questionPost+"\n________________");
                        score += 100;
                        gameValid = false;
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }else{
                        System.out.println("Incorrect, try again");
                        break;
                    }
                    break;
                case 2:
                    if (hintList.size() > hintIndex) {
                        String hint = hintList.get(hintIndex);
                        if (hint != null) {
                            System.out.println(hint + "| -50 points");
                            score -= 50;
                        }
                    } else {
                        System.out.println("No more hints remaining");
                    }
                    hintIndex++;
                    break;
            }
        }
    }



    public UUID getID() {
        return gameID;
    }
    // working on challenge implementation
    /*
     * public ArrayList<Challenge> getQuestions(){
     * return
     * }
     * public Challenge getquestion(){
     * 
     * }
     */

    public void getClues() {

    }

    public void getAnswer() {

    }

    public void skipPuzzle() {

    }

    public void questionValidity() {

    }

    public void puzzleCompleted() {

    }

    /*
     * public void updateScore(Integer currentScore){
     * currentScore = Challenge.getScore(gameID);
     * 
     * }
     * Need to think about the implementation of Challenge it is the same as the
     * leaderboard right now
     */
    public void calculateScore(int currentScore) {

    }

    public void attemptQuestion(String userAttempt) {
        /*
         * if (userAttempt == gameset.get())
         * {
         * 
         * }
         * else
         * {
         * System.out.println("Incorrect Answer");
         * }
         */
    }

}
