package com.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;
public class Game {
    private String theme;
    private int difficulty;
    private int playerCount;
    private String teamName;
    private UUID id;
    private ArrayList<Challenge> gameset = new ArrayList<Challenge>();
    

    public Game(String theme, int difficulty, int playerCount, String teamName, UUID id) {
        this.theme = theme;
        this.difficulty = difficulty;
        this.playerCount = playerCount;
        this.teamName = teamName;
        this.id = id;
    }

    public String getIntro(){
        return "";
    }

    public UUID getGameID(){
        return this.id;
    }
    //working on challenge implementation 
    //public ArrayList<Challenge> getQuestions(){}
    //public Challenge getquestion(){ }
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
    public void updateScore(int currentScore){
        //Progress.currentScore = currentScore;
    }
    public void calculateScore(int currentScore){

    }

}
