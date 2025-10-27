package com.model;
/**
 * TFChallenge is a subclass of Challenge that represents a True/False type challenge.
 * It contains a question, the correct answer, and inherits hints and clues from the Challenge class.
 * @author The Clankers
 */
import java.util.ArrayList;
import javafx.scene.image.Image;

public class TFChallenge extends Challenge{

    public String question;
    public String answer;
    public int tf;
    /**
     * Constructor for TFChallenge that initializes the question, answer, hints, and clue.
     * @param theQuestion the question for the True/False challenge
     * @param theAnswer the correct answer ("True" or "False")
     * @param hints a list of hints for the challenge
     * @param clue an image clue associated with the challenge
     */
    public TFChallenge(String theQuestion, String theAnswer, ArrayList<String> hints, Image clue)
    {
        super(hints, clue);
        setQuestion(theQuestion);
        setAnswer(theAnswer);
    }

    /**
     * Returns the question of the True/False challenge
     * @return the question string 
     */
    public String getQuestion()
    {
        return question;
    }
    /**
     * Sets the question of the True/False challenge
     * @param theQuestion the question string 
     */
    public void setQuestion(String theQuestion)
    {
        this.question = theQuestion;
    }
    /**
     * Returns the answer of the True/False challenge
     * @return the answer as an integer 
     */
    public int getAnswer()
    {
        return this.tf;
    }
    /**
     * Sets the answer based on string input
     * @param the answer as a string 
     */
    public void setAnswer(String theAnswer)
    {
        if (theAnswer.equalsIgnoreCase("True") || theAnswer.equalsIgnoreCase("t"))
        {
            tf = 1;
        }



        else
        {
            tf = 0;
        }
    }
}



