package com.model;

import java.util.ArrayList;
import javafx.scene.image.Image;

public class PhraseChallenge extends Challenge{

    private String question;
    private String answer;
    
    public PhraseChallenge(String theQuestion, String theAnswer, ArrayList<String> hints, Image clue)
    {
        super(hints,clue);
        setAnswer(theAnswer);
        setQuestion(theQuestion);

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