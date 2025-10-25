package com.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Game {
    private String theme;
    private int difficulty;
    private int playerCount;
    private UUID gameID;
    private int score =0;
    // public ArrayList<Challenge> gameset = new ArrayList<Challenge>();


    // thinking solution to item problem is to have another loop that checks if 
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> answers = new ArrayList<>();
    ArrayList<String> postQuestions = new ArrayList<>();
    ArrayList<Item> items = new ArrayList<>();
    ArrayList<ArrayList<String>> hints = new ArrayList<>();

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

    public void challengeStart(int startingIndex) {
        gameSet.getIntro();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        questions = gameSet.getQuestions();
        answers = gameSet.getAnswers();
        postQuestions = gameSet.getPostQuestions();
        hints = gameSet.getHints();
        items = gameSet.getItems();

        for (int i = 0; i < questions.size(); i++) {
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
        System.out.println(questions.get(startingIndex));
        // System.out.println(clues.get(startingIndex)); No clue for now
        int hintIndex = 0;
        while (gameValid) {
            if(items.size() >0){
                 System.out.println("Press 1. To attempt question\nPress 2. To get hint");
            int choice = u.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("\nAttempt question:");
                    u.nextLine();
                    String attempt = u.nextLine();
                    if (attempt.equalsIgnoreCase(answers.get(startingIndex))) {
                        System.out.println("\nCorrect | + 100points \n");
                        System.out.println(postQuestions.get(startingIndex)+"\n________________");
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
                    if (hints.get(startingIndex).size() > hintIndex) {
                        String hint = hints.get(startingIndex).get(hintIndex);
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
            System.out.println("Press 1. To attempt question\nPress 2. To get hint");
            int choice = u.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("\nAttempt question:");
                    u.nextLine();
                    String attempt = u.nextLine();
                    if (attempt.equalsIgnoreCase(answers.get(startingIndex))) {
                        System.out.println("\nCorrect | + 100points \n");
                        System.out.println(postQuestions.get(startingIndex)+"\n________________");
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
                    if (hints.get(startingIndex).size() > hintIndex) {
                        String hint = hints.get(startingIndex).get(hintIndex);
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
