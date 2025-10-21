package com.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;
public class Game {
    private String theme;
    private int difficulty;
    private int playerCount;
    private UUID gameID;
    public ArrayList<Challenge> gameset = new ArrayList<Challenge>();

    public Game(String theme, int difficulty, int playerCount, String teamName) {
        this.theme = theme;
        this.difficulty = difficulty;
        this.playerCount = playerCount;
        this.gameset = gameset;
    }

    public String getIntro(){
        return "";
    }

    public String getTheme(){
        return this.theme;
    }
    public int getDifficulty(){
        return this.difficulty;
    }
    public int getPlayerCount(){
        return this.playerCount;
    }

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
/*  public void updateScore(Integer currentScore){
        currentScore = Challenge.getScore(gameID);
        
    }
        Need to think about the implementation of Challenge it is the same as the leaderboard right now*/
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
