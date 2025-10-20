package com.model;

import java.util.ArrayList;
import javafx.scene.image.Image;

public class TFChallenge extends Challenge{

    public String question;
    public String answer;
    public int tf;

    
    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String theQuestion)
    {
        this.question = theQuestion;
    }

    public int getAnswer()
    {
        return this.tf;
    }

    public void setAnswer(String theAnswer)
    {
        if (theAnswer.equalsIgnoreCase("True"))
        {
            tf = 1;
        }

        else
        {
            tf = 0;
        }
    }
}



