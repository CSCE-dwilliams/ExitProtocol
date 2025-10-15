package com.model;

import java.util.ArrayList;
import javafx.scene.image.Image;

public class CipherChallenge {
    private String question;
    private String answer;
    private ArrayList<String> hints;
    private Image clue;

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
        return answer;
    }

    public void setAnswer(String theAnswer)
    {
        this.answer = theAnswer;
    }

    public ArrayList<String> getHints()
    {
        return hints;
    }

    public void addHint(String hint)
    {
        hints.add(hint);
    }

    public Image getClue()
    {
        return clue;
    }

    public void setClue(Image theClue)
    {
        this.clue = theClue;
    }
}
