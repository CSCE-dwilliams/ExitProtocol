package com.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;
public class Game {
    private String theme;
    private int difficulty;
    private int playerCount;
    private String teamName;
    private UUID gameID;
    public ArrayList<Challenge> gameset = new ArrayList<Challenge>();

    

    public Game(String theme, int difficulty, int playerCount, String teamName, ArrayList<Challenge> gameset) {
        this.theme = theme;
        this.difficulty = difficulty;
        this.playerCount = playerCount;
        this.teamName = teamName;
        this.gameset = gameset;
    }

    public String getIntro(){
        return "";
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

}
