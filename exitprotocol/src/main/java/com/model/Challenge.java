package com.model;

import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;
import javafx.scene.image.Image;
public class Challenge {

    private String question;
    private String answer;
    private ArrayList<String> hints = new ArrayList<>();
    private String postQuestion;
    private ArrayList<Item> items = new ArrayList<>();

    public Challenge(String question, String answer, String postQuestion) {
        this.question = question;
        this.answer = answer;
        this.postQuestion = postQuestion;
    }

    public String getQuestion(){
        return question;
    }
    public String getAnswer(){
        return answer;
    }
    public String getPostQuestion(){
        return postQuestion;
    }
    public ArrayList<String> getHints(){
        return hints;
    }
    public ArrayList<Item> getItems(){
        return items;
    }
    public void addHint(String hint){
        hints.add(hint);
    }
    public void addItem(Item item){
        items.add(item);
    }


    
}