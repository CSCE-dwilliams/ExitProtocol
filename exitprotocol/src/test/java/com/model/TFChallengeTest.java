package com.model;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;



public class TFChallengeTest {
    
    private TFChallenge falseChallenge;
    private TFChallenge trueChallenge;
    private ArrayList<String> hints;

    @Test
    public void testTesting(){
        assertTrue(true);
    }

    @Before
    public void setUp(){
        hints = new ArrayList<>();
        hints.add("The first hint");
        hints.add("The second hint");
        trueChallenge = new TFChallenge("Do torches light up the dark?" ,"True",hints,null);
        falseChallenge = new TFChallenge("Do these pants make my butt look fat?","False",hints,null);
    }

    @Test
    void testTrueQuestion() {
        assertEquals("Is the sky blue?", trueChallenge.getQuestion());
    }

    @Test
    void testFalseQuestion() {
        assertEquals("Is fire cold?", falseChallenge.getQuestion());
    }

    @Test
    void testTrueQuestionUpdateValue() {
        trueChallenge.setQuestion("New Question");
        assertEquals("New Question", trueChallenge.getQuestion());
        trueChallenge.setQuestion("Do torches light up the dark?");
    }

    @Test
    void testFalseQuestionUpdateValue() {
        falseChallenge.setQuestion("New Question");
        assertEquals("New Question", falseChallenge.getQuestion());
        falseChallenge.setQuestion("Do these pants make my butt look fat?");
    }

    @Test
    void testTrueQuestionWithEmptyString() {
        trueChallenge.setQuestion("");
        assertEquals("", trueChallenge.getQuestion(), "Empty question should be stored as-is");
        trueChallenge.setQuestion("Do torches light up the dark?");
    }

    @Test
    void testFalseQuestionWithEmptyString() {
        falseChallenge.setQuestion("");
        assertEquals("", falseChallenge.getQuestion(), "Empty question should be stored as-is");
        falseChallenge.setQuestion("Do these pants make my butt look fat?");
    }

    @Test
    void testAnswerIsTrueWhenStringIsTrue() {
        assertEquals(1, trueChallenge.getAnswer());
    }

    @Test
    void testAnswerIsFalseWhenStringIsFalse() {
        assertEquals(0, falseChallenge.getAnswer());
    }

    @Test
    void testSetAnswerHandlesCaseInsensitivity() {
        assertEquals(1, trueChallenge.getAnswer());
        trueChallenge.setAnswer("fAlSe");
        assertEquals(0, trueChallenge.getAnswer());
        trueChallenge.setAnswer("True");
    }

    @Test
    void testSetAnswerWithInvalidString() {
        TFChallenge challenge = new TFChallenge("Weird input", "maybe", hints, null);
        assertEquals(0, challenge.getAnswer());
        System.out.println("Invalid input should default to 0 (false)");
    }

     @Test
    void testTrueGetQuestionReturnsCorrectValue() {
        assertEquals("Is the sky blue?", trueChallenge.getQuestion(), "True challenge question should match expected value");
    }
    @Test
    void testFalseGetQuestionReturnsCorrectValue() {
        assertEquals("Is fire cold?", falseChallenge.getQuestion(), "False challenge question should match expected value");
    }

    @Test
    void testSetAnswerWithEmptyString() {
        TFChallenge challenge = new TFChallenge("Empty input", "", hints, null);
        assertEquals(0, challenge.getAnswer());
        //Empty input should default to 0 (false)
        
    }

    @Test
    void testSetAnswerWithNullDoesNotThrow() {
        TFChallenge challenge = new TFChallenge("Null test", "True", hints, null);
        try {
            challenge.setAnswer(null);
        }catch (Exception e) {
            fail("setAnswer(null) should not throw an exception, but threw: "+ e);
        }
        assertEquals(0, challenge.getAnswer());
        //Null input should safely default to 0 (false)
    }

    @Test
    void testConstructorWithValidInputs() {
        assertEquals("Is the sky blue?", trueChallenge.getQuestion());
        assertEquals(1, trueChallenge.getAnswer());
    }

    @Test
    void testConstructorHandlesNullQuestion() {
        try{
            new TFChallenge(null, null, hints, null);
        } catch(Exception e)
        {
            fail("Constructor should not throw when given null inputs, but threw: " + e);
        }
        TFChallenge challenge = new TFChallenge(null, null, hints,null);
        assertNull(challenge.getQuestion());

    }

    @Test
    void testConstructorHandlesNullAnswer() {
        try{
            new TFChallenge(null, null, hints, null);
        } catch(Exception e)
        {
            fail("Constructor should not throw when given null inputs, but threw: " + e);
        }
        TFChallenge challenge = new TFChallenge(null, null, hints,null);
        assertEquals(0, challenge.getAnswer());
    }

    @Test
    void testAnswerHandlesEmptyStrings() {
        TFChallenge challenge = new TFChallenge("", "", hints,null);
        assertEquals(0, challenge.getAnswer());
    }

    @Test
    void testQuestionHandlesEmptyStrings() {
        TFChallenge challenge = new TFChallenge("", "", hints,null);
        assertEquals("", challenge.getQuestion());
    }

    @Test
    void testQuestionHandlesNullHints() {
        try {
            new TFChallenge("Q?", "True", null, null);
        } catch (Exception e) {
            fail("Constructor should not throw when given null hints, but threw: "+ e);
        }
        TFChallenge challenge = new TFChallenge("Q?", "True", null, null);
        assertEquals("Q?", challenge.getQuestion());
    }

    @Test
    void testAnswerHandlesNullHints() {
        try {
            new TFChallenge("Q?", "True", null, null);
        } catch (Exception e) {
            fail("Constructor should not throw when given null hints, but threw: "+ e);
        }
        TFChallenge challenge = new TFChallenge("Q?", "True", null, null);
        assertEquals(1, challenge.getAnswer());
    }

    
}
