package com.model;

import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;
import javafx.scene.image.Image;
public class Challenge {
    HashMap<UUID, Integer> scoreSet = new HashMap<>();
    public String hint;
    public Image clue;
    public ArrayList<String> hints;

    public Challenge(ArrayList<String> hints, Image clue){
    public Challenge(ArrayList<String> hints, Image clue){

    }

    public void addScore(UUID id, Integer score)
    {
        scoreSet.put(id,score);
    }

    public Integer getScore(UUID id)
    {
        return scoreSet.get(id);
    }

    public String getQuestion()
    {
        return this.question;
    }

    public void setQuestion(String theQuestion)
    {
        this.question = theQuestion;
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

    public String getHint()
    {
        return this.hint;
    }

    public void setClue(Image theClue)
    {
        this.clue = theClue;
    }

    public Image getClue()
    {
        return this.clue;
    }
}
