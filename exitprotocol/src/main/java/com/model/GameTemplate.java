package com.model;

import java.util.ArrayList;

public class GameTemplate {
    private ArrayList<String> questionSet = new ArrayList<>();
    private ArrayList<String> answerSet = new ArrayList<>();
    private ArrayList<ArrayList<String>> hintSet = new ArrayList<>();
    private ArrayList<String> clueSet = new ArrayList<>();
    private String gameTheme;
    private String intro;
    /**
     * Creates a game template that takes in theme and intro
     * @param theme is used to set the game theme
     * @param intro is used to introduce the games theme
     */
    public GameTemplate(String theme, String intro){
        this.gameTheme = theme;
        this.intro = intro;
    }
    /**
     * Return theme of the game
     * @return a string of the games theme
     */
    public String getTheme(){
        return gameTheme;
    }
    /**
     * Return intro of the game
     * @return a string of the intro
     */
    public String getIntro(){
        return intro;
    }
    /**
     * Return questions of the game
     * @return an arraylist of questions
     */
    public ArrayList<String> getQuestions() {
        return questionSet;
    }
    /**
     * Add questions to the game
     * @param question
     */
    public void addQuestions(String question){
        questionSet.add(question);
    }
    /**
     * Returns clues of the game
     * @return an arraylist of clues
     */
    public ArrayList<String> getClues() {
        return clueSet;
    }
    /**
     * Add clues to the game
     * @param clue 
     */
    public void addClues(String clue){
        clueSet.add(clue);
    }
    /**
     * Returns answers of the game
     * @return an arraylist of answers
     */
    public ArrayList<String> getAnswers(){
        return answerSet;
    }
    /**
     * Add answers to the game
     * @param answer 
     */
    public void addAnswers(String answer){
        answerSet.add(answer);
    }
    // public ArrayList<String> getHints() {
    //     return hintSet;
    // }
    // public void addHints(String hints){
    //     hintSet.add(hints);
    // }

}