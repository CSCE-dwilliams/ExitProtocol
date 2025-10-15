package com.model;

import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;
import javafx.scene.image.Image;
public class Challenge {
    HashMap<String, Integer> scoreSet = new HashMap<>();
    private String answer;
    private String question;
    private String hint;
    private Image clue;
    private ArrayList<String> hints;

    public Challenge(String answer, String question, ArrayList<String> hints, Image clue){

    }

    public void addScore(UUID id, Integer score)
    {
        String idString = id.toString();
        scoreSet.put(idString,score);
    }   

    public Integer getScore(UUID id)
    {
        String idString = id.toString();
        Integer Score = scoreSet.get(idString);
        
        return Score;
    }

    public String getQuestion()
    {
        return null;
    }

    public void setQuestion(String question)
    {

    }

    public String getAnswer()
    {
        return this.answer;
    }

    public void setAnswer(String theAnswer)
    {
        this.answer = theAnswer;
    }

    public ArrayList<String> getHints()
    {
        return this.hints;
    }

    public void addHint(String hint)
    {
        hints.add(hint);
    }

    public void setClue(Image theClue)
    {
        this.clue = theClue;
    }

    public Image getClue()
    {
        return clue;
    }
}
