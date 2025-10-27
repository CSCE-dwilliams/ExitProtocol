package com.model;
/**
 * Game class helps run the game based off of the Game session parameters.
 * It holds the theme, difficulty, and player count for the game session.
 * @author The Clankers
 */
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
public class Game {
    private String theme;
    private int difficulty;
    private int playerCount;
    private UUID gameID;
    // public ArrayList<Challenge> gameset = new ArrayList<Challenge>();
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> answers = new ArrayList<>();
    ArrayList<String> clues = new ArrayList<>();

    private GameTemplate gameSet;
    /**
     * Creates  a game session based on the theme, difficulty, and player count
     * @param theme is used in game generation
     * @param difficulty is used to set the level of challenge
     * @param playerCount is used to set how many players are in the game
     */
    public Game(GameSession session) {
        this.theme = session.getSessionTheme();
        this.difficulty = session.getDifficulty();
        this.playerCount = session.getPlayerCount();
    }
    /**
     * Returns intro of the game
     * @return a string intro per theme
     */
    public String getIntro(){
        return "";
    }
    /**
     * Returns theme of the game
     * @return one of 4 themes
     */
    public String getTheme(){
        return this.theme;
    }
    /**
     * Returns difficulty of the game
     * @return integer of difficulty level
     */
    public int getDifficulty(){
        return this.difficulty;
    }
    /**
     * Returns the amount of players in the game
     * @return returns an integer of player count
     */
    public int getPlayerCount(){
        return this.playerCount;
    }
   /**
    * Sets the game template based on the theme and difficulty
    * @param aGameTemplate is used to set a game template
    */
    public void setGameSet(GameTemplate aGameTemplate){
        this.gameSet = aGameTemplate;
    }
    /**
     * Start the challenge
     * @param startingIndex is used to start the challenge from a specific index
     */
    public void challengeStart(int startingIndex){
        System.out.println(gameSet.getIntro());
        questions = gameSet.getQuestions();
        answers = gameSet.getAnswers();
        clues = gameSet.getClues();

        for(int i =0; i < questions.size();i++){
            
            int challengeNo = 1;
            challengeNo += startingIndex;
            System.out.println("ATTEMPT CHALLENGE "+ challengeNo + ":\n");
            attemptQuestion(startingIndex);
            startingIndex++;
        }

    }
    /**
     * Creates how many attempts a user has to answer a question and chacks if the answer is correct
     * @param startingIndex is used to start the challenge from a specific index
     */
    public void attemptQuestion(int startingIndex){
        Scanner u = new Scanner(System.in);
        boolean gameValid = true;
        System.out.println(questions.get(startingIndex));
        System.out.println(clues.get(startingIndex));
        while(gameValid){
        System.out.println("Press 1. To attempt question\nPress 2. To get hint");
        int choice = u.nextInt();
        switch(choice){
            case 1:
                System.out.println("\nAttempt question:");
                u.nextLine();
                String attempt = u.nextLine();
                if(attempt.equalsIgnoreCase(answers.get(startingIndex))){
                    System.out.println("Correct");
                    gameValid = false;
                }
                break;
            case 2:
                break;
        }
    }
    }
    /**
     * Returnes the unique ID of the game
     * @return UUID of the game
     */
    public UUID getID()
    {
        return gameID;
    }
    //working on challenge implementation 
   /*  public ArrayList<Challenge> getQuestions(){
        return 
    }
    public Challenge getquestion(){
    
    }
    */

    public void getClues(){

    }

    public void getAnswer(){

    }    
    public void skipPuzzle(){

    }
    public void questionValidity(){

    }
    public void puzzleCompleted(){

    }
    public void calculateScore(int currentScore){
        
    }

    public void attemptQuestion(String userAttempt)
    {
        /*
        if (userAttempt == gameset.get())
        {
            
        }
        else
        {
            System.out.println("Incorrect Answer");
        }
        */
    } 

}
