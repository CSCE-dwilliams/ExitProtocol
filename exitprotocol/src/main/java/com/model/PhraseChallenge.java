package com.model;

import java.util.ArrayList;
import javafx.scene.image.Image;

public class PhraseChallenge extends Challenge{

    private String question;
    private String answer;
    
    public PhraseChallenge(ArrayList<String> hints, Image clue)
    {
        
        this.hints = hints;
        this.clue = clue;
    }
    
    public String getQuestion()
    {
        return question;
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
}