package com.model;

import java.util.ArrayList;
import javafx.scene.image.Image;

public class InteractiveChallenge {
    private String answer;
    private ArrayList<String> hints;
    private Image clue;
    /* 
    public String getQuestion()
    {
        return null;
    }

    public void setQuestion(String newQuestion)
    {
        this.question = newQuestion;
    }
*/
    public String getAnswer()
    {
        return this.answer;
    }

    public void setAnswer(String theanswer)
    {
        this.answer = theanswer;
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
