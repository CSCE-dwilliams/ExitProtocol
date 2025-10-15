package com.model;

import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;
public class Challenge {
    HashMap<String, Integer> scoreSet = new HashMap<>();

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
        return null;
    }

    public void setAnswer(String answer)
    {

    }

    public ArrayList<String> getHints()
    {
        return null;
    }

    public void addHint(String hint)
    {

    }
}
