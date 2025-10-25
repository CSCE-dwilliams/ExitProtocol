package com.model;

import java.util.ArrayList;

public class GameTemplate {
    private ArrayList<String> questionSet = new ArrayList<>();
    private ArrayList<String> answerSet = new ArrayList<>();
    private ArrayList<ArrayList<String>> hintSet = new ArrayList<>();
    private ArrayList<String> postQuestionSet = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private String gameTheme;
    private String intro;

    public GameTemplate(String theme, String intro){
        this.gameTheme = theme;
        this.intro = intro;
    }

    public String getTheme(){
        return gameTheme;
    }
    public void getIntro(){
        String introFull = "\n[Introduction].....\n****************************************\n" + intro 
        + "\n****************************************\n";
        System.out.println(introFull);
        TTSUtils.speak(intro);
    }
    public ArrayList<String> getQuestions() {
        return questionSet;
    }
    public void addQuestions(String question){
        questionSet.add(question);
    }
    public ArrayList<String> getPostQuestions() {
        return postQuestionSet;
    }
    public void addClues(String clue){
        postQuestionSet.add(clue);
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
    public ArrayList<Item> getItems(){
        return items;
    }
    public void addItem(Item item){
        items.add(item);
    }
}