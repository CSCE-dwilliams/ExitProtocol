package com.model;

import java.util.ArrayList;

public class GameTemplate {
    public ArrayList<String> questionSet = new ArrayList<>();
    public ArrayList<String> answerSet = new ArrayList<>();
    public ArrayList<ArrayList<String>> hintSet = new ArrayList<>();
    public ArrayList<String> clueSet = new ArrayList<>();
    public String gameTheme;
    public String intro;

    public GameTemplate(String theme, String intro){
        this.gameTheme = theme;
        this.intro = intro;
    }

    public ArrayList<String> getQuestions() {
        return questionSet;
    }
    public void addQuestions(String question){
        questionSet.add(question);
    }
    public ArrayList<String> getClues() {
        return clueSet;
    }
    public void addClues(String clue){
        clueSet.add(clue);
    }
    // public ArrayList<String> getHints() {
    //     return hintSet;
    // }
    // public void addHints(String hints){
    //     hintSet.add(hints);
    // }

}