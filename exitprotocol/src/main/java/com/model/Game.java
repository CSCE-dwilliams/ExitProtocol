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
    private int score = 0;
    private int itemIndex;
    private int playerIndex;
    private int hintIndex;
    private int hintPenalty = 25;
    private int correctPoints = 100;
    private boolean quit = false;
    // public ArrayList<Challenge> gameset = new ArrayList<Challenge>();

    // thinking solution to item problem is to have another loop that checks if
    // ArrayList<String> questions = new ArrayList<>();
    // ArrayList<String> answers = new ArrayList<>();
    // ArrayList<String> postQuestions = new ArrayList<>();
    // ArrayList<Item> items = new ArrayList<>();
    // ArrayList<ArrayList<String>> hints = new ArrayList<>();
    ArrayList<Challenge> challenges = new ArrayList<>();
    ArrayList<Item> sessionItems = new ArrayList();
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

    public int getIndex(){
        return playerIndex;
    }
    public void setGameSet(GameTemplate aGameTemplate) {
        this.gameSet = aGameTemplate;
    }

    public void challengeStart(int startingIndex) {
        // printing intro flavor text
        gameSet.getIntro();
        // try {
        // Thread.sleep(10000);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // instantiating all arraylists and pointing player index
        playerIndex = startingIndex;
        challenges = gameSet.getChallenges();
    }

    public void runGame() {
        while(!quit){
        for (int i = 0; i < challenges.size(); i++) {
            int challengeNo = 1 + playerIndex;
            System.out.println("ATTEMPT CHALLENGE " + challengeNo + ":\n");
            System.out.println("Correct answers +100 points\nUsing a hint: -25 points\nSkipping: -75 points");
            System.out.println("--------------------------");


            questionLoop(playerIndex);
            if(quit){
                break;
            }
            playerIndex++;
        }
        break;
    }

    }

    public void questionLoop(int startingIndex) {
        Scanner u = new Scanner(System.in);
        hintIndex = 0;
        itemIndex = 0;
        boolean gameValid = true;
        while (gameValid && !quit) {
            if (hasItems()) {
                if (attemptQuestionWithItems(u)) {
                    gameValid = false;
                }
            } else {
                if (attemptQuestionNoItems(u)) {
                    gameValid = false;
                }
            }

            if(quit){
                gameValid = false;
            }
        }
    }

    public boolean attemptQuestionNoItems(Scanner u) {
        Challenge currentChallenge = challenges.get(playerIndex);
        String questionString = currentChallenge.getQuestion();
        String questionAnswer = currentChallenge.getAnswer();
        String questionPost = currentChallenge.getPostQuestion();
        ArrayList<String> hintList = currentChallenge.getHints();

        System.out.println(questionString);
        System.out.println("----------");

        boolean questionCorrect = false;

        // Menu loop so user can try, ask for hints, or quit without leaving the
        // question
        while (!questionCorrect) {
            System.out.println("Press 1. To attempt question");
            System.out.println("Press 2. To get hint");
            System.out.println("Press 4. To save and quit");
            System.out.print("> ");

            String line = u.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid choice (1, 2 or 4).");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\nAttempt question:");
                    String attempt = u.nextLine();
                    if (attempt.equalsIgnoreCase(questionAnswer)) {
                        System.out.println("\nCorrect | +100 points\n");
                        System.out.println(questionPost + "\n________________");
                        score += correctPoints;
                        questionCorrect = true;

                        // If the challenge unexpectedly has items, add them safely
                        if (currentChallenge.getItems() != null && currentChallenge.getItems().size() > 0) {
                            Item challengeItem = currentChallenge.getItems().get(itemIndex);
                            sessionItems.add(challengeItem);
                            itemIndex++;
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ignored) {
                        }
                    } else {
                        System.out.println("Incorrect, try again");
                    }
                    break; // prevent fall-through

                case 2:
                    if (hintList.size() > hintIndex) {
                        String hint = hintList.get(hintIndex);
                        if (hint != null) {
                            System.out.println("\n" + hint + " | -25 points" + "\n");
                            score -= hintPenalty;
                        }
                        hintIndex++;
                    } else {
                        System.out.println("\nNo more hints remaining\n");
                    }
                    break;

                case 4:
                    // handle save & quit: adjust to your save implementation
                    // saveGame();
                    System.out.println("Game saved. Exiting question...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    quit = true;
                    return false; // not correct, outer loop can handle overall quit

                default:
                    System.out.println("Invalid choice. Enter 1, 2 or 4.");
                    break;
            }
        }

        return questionCorrect;
    }

    public boolean attemptQuestionWithItems(Scanner u) {

        // instantiating variables 4 logic
        Challenge currentChallenge = challenges.get(playerIndex);
        String questionString = currentChallenge.getQuestion();
        String questionAnswer = currentChallenge.getAnswer();
        String questionPost = currentChallenge.getPostQuestion();
        ArrayList<String> hintList = currentChallenge.getHints();

        boolean questionCorrect = false;

        System.out.println(questionString);
        System.out.println("----------");
        // Menu loop for this single question so user can press 1/2/3 multiple times
        // until they answer or quit
        while (!questionCorrect) {
            System.out.println("Press 1. To attempt question");
            System.out.println("Press 2. To get hint");
            System.out.println("Press 3. To look at items");
            System.out.println("Press 4. To save and quit");
            System.out.print("> ");

            String line = u.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (1-4).");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\nAttempt question:");
                    String attempt = u.nextLine();
                    if (attempt.equalsIgnoreCase(questionAnswer)) {
                        System.out.println("\nCorrect | +100 points\n");
                        System.out.println(questionPost + "\n________________");
                        score += correctPoints;
                        if (currentChallenge.getItems().size() > 0) {
                            // only add if there are items in the challenge itself
                            Item challengeItem = currentChallenge.getItems().get(itemIndex);
                            sessionItems.add(challengeItem);
                            itemIndex++;
                        }
                        questionCorrect = true;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ignored) {
                        }
                    } else {
                        System.out.println("Incorrect, try again");
                    }
                    break;
                case 2:
                    if (hintList.size() > hintIndex) {
                        String hint = hintList.get(hintIndex);
                        if (hint != null) {
                            System.out.println("\n" + hint + " | -25 points" + "\n");
                            score -= hintPenalty;
                        }
                        hintIndex++;
                    } else {
                        System.out.println("\nNo more hints remaining\n");
                    }
                    break;
                case 3:
                    System.out.println("\nHere are your items:\n");
                    if (sessionItems.isEmpty()) {
                        System.out.println("You have no items.\n");
                    } else {
                        int i = 0;
                        for (Item item : sessionItems) {
                            i++;
                            System.out.println(i + ". " + item.getName());
                        }
                        // Optionally print descriptions for all items (safely)
                        System.out.println("\nDescriptions:");
                        for (int j = 0; j < sessionItems.size(); j++) {
                            Item it = sessionItems.get(j);
                            System.out.println((j + 1) + ". " + it.getDescription() + "\n");
                        }
                    }
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException ignored) {
                    }
                    System.out.println(questionString);
                    System.out.println("----------");

                    break;
                case 4:
                    // implement save/quit behavior here; for now return false so outer loop can
                    // handle quitting
                    // saveGame();
                    System.out.println("Game saved. Exiting...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    quit = true;
                    // Decide: should quitting count as "questionCorrect"? probably not,
                    // so return false or handle game state elsewhere. Here we return false.
                    return false;
                default:
                    System.out.println("Invalid choice. Enter 1-4.");
                    break;
            }
        }

        // when questionCorrect == true we return true to signal the outer loop to
        // advance
        return questionCorrect;
    }

    public boolean hasItems() {
        if (sessionItems.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public UUID getID() {
        return gameID;
    }

   public void puzzleCompleted() {

    }

    public int getScore(){
        return score;
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
