package com.model;

import java.util.ArrayList;

public class GameTemplate{
    private String theme;
    ArrayList<ArrayList<GameTemplate>> templates = new ArrayList();
    public ArrayList<String> questionSet = new ArrayList<>();
    public ArrayList<String> hints = new ArrayList<>();
    public ArrayList<String> clues = new ArrayList<>();

    public GameTemplate(String theme){
        

    }


    public ArrayList<String> getQuestions(){
        return questionSet;
    }
    public ArrayList<String> getClues(){
        return clues;
    }
    public ArrayList<String> getHints(){
        return hints;
    }
    
}