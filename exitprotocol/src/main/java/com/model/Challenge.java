package com.model;

import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * This class manages challenge questions, answers associated with challenges, 
 * and post questions that will be asked to the user.
 * 
 * This class also manages hints and items that the user will be able to use
 * during the game to help with their progression through the challenge.
 * @author Clankers
 */
public class Challenge {

    /**
     * The question associated with the challenge 
     */
    private String question;

    /** 
     * The answer associated with the challenges question
     */
    private String answer;

    /** 
     * A list of hints that the user will be able to access when completing
     * a challenge.
     */
    private ArrayList<String> hints = new ArrayList<>();

    /**
     * This will show a post answer to the user after 
     * showing the initial question??.
     */
    private String postQuestion;

    /**
     * This a list of items that will hold the users inventory.
     * Users will be able to use these items to help them through challenges.
     */
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * Constructs a new {@code Challenge} with a specified question, answer,
     * and post question
     * @param question main question for the challenge
     * @param answer the correct answer associated with the problem 
     * @param postQuestion follow up question shown after the main question
     */
    public Challenge(String question, String answer, String postQuestion) {
        this.question = question;
        this.answer = answer;
        this.postQuestion = postQuestion;
    }

    /**
     * Returns the main question for the challenge
     * @return the challenge question
     */
    public String getQuestion(){
        return question;
    }
    /**
     * Returns the answer associated with the challenge
     * @return the correct answer 
     */
    public String getAnswer(){
        return answer;
    }
    /**
     * Retunrs post question associated with the challenge
     * @return the post question
     */
    public String getPostQuestion(){
        return postQuestion;
    }
    /**
     * Returns an array list of hints that the user will be able to use 
     * to help them get through the challenge.
     * @return array list of hints
     */
    public ArrayList<String> getHints(){
        return hints;
    }
    /**
     * Returns an array list of items that the user will be able to 
     * use during the challenge.
     * @return array list of items
     */
    public ArrayList<Item> getItems(){
        return items;
    }
    /**
     * Adds a new hint to the list of available hints.
     * @param hint the hint string to add
     */
    public void addHint(String hint){
        hints.add(hint);
    }
    /**
     * Will add a new item to the list of items.
     * @param item the item to add
     */
    public void addItem(Item item){
        items.add(item);
    }

}