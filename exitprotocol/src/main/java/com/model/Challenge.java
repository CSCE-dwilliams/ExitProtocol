package com.model;

import java.util.HashMap;
import java.util.UUID;

public class Challenge {
    private String question;
    private String answer;
    private ArrayList<String> hints;
    private Image clue;

    HashMap<UUID, Integer> scoreSet = new HashMap<UUID, Integer>();

    private void addScore()
    {

    }

    private Integer getScore(UUID id)
    {
        return null;
    }

    private void getScores()
    {

    }

    private void sortScores()
    {
        
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
