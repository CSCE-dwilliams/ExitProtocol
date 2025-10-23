package com.model;

import java.util.ArrayList;

public class GameTemplate {
    private ArrayList<String> questionSet = new ArrayList<>();
    private ArrayList<String> answerSet = new ArrayList<>();
    private ArrayList<ArrayList<String>> hintSet = new ArrayList<>();
    private ArrayList<String> clueSet = new ArrayList<>();
    private String gameTheme;
    private String intro;

    public GameTemplate(String theme, String intro){
        this.gameTheme = theme;
        this.intro = intro;
    }

    public String getTheme(){
        return gameTheme;
    }
    public String getIntro(){
        return intro;
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

    public ArrayList<String> getAnswers(){
        return answerSet;
    }
    public void addAnswers(String answer){
        answerSet.add(answer);
    }
    public ArrayList<ArrayList<String>> getHints() {
        return hintSet;
    }
    public void addHints(int index, String hint){
        ArrayList<String> nuSet = new ArrayList<String>();
        hintSet.add(nuSet);
        hintSet.get(index).add(hint);
    }

}