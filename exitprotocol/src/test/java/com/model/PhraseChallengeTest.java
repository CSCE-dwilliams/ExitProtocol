package com.model;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class PhraseChallengeTest {
    private PhraseChallenge challenge;
    private ArrayList<String> hints;

    @Before
    public void setUp() {
        hints = new ArrayList<>();
        hints.add("The first Hint");
        hints.add("The second Hint");

        challenge = new PhraseChallenge("Doot doot da loot doot?", "beep boop bap boop", hints, null);
    }

    @Test
    public void testQuestionSetsFieldsCorrectly() {
        assertEquals("Doot doot da loot doot?", challenge.getQuestion());
    }

    @Test
    public void testAnswerSetsFieldsCorrectly() {
        assertEquals("beep boop bap boop", challenge.getAnswer());
    }

    @Test
    public void testQuestionHandlesNullInputs() {
        PhraseChallenge nullChallenge = new PhraseChallenge(null, null, null, null);
        assertNull(nullChallenge.getQuestion());
    }

    @Test
    public void testAnswerHandlesNullInputs() {
        PhraseChallenge nullChallenge = new PhraseChallenge(null, null, null, null);
        assertNull(nullChallenge.getAnswer());
    }

    @Test
    public void testQuestionHandlesEmptyStrings() {
        PhraseChallenge emptyChallenge = new PhraseChallenge("", "", hints, null);
        assertEquals("", emptyChallenge.getQuestion());
    }

    @Test
    public void testAnswerHandlesEmptyStrings() {
        PhraseChallenge emptyChallenge = new PhraseChallenge("", "", hints, null);
        assertEquals("", emptyChallenge.getAnswer());
    }

    @Test
    public void testQuestion() {
        challenge.setQuestion("What language are we testing?");
        assertEquals("What language are we testing?", challenge.getQuestion());
    }

    @Test
    public void testAnswer() {
        challenge.setAnswer("Java");
        assertEquals("Java", challenge.getAnswer());
    }

    @Test
    public void testQuestionInitializesFields() {
        assertEquals("Doot doot da loot doot?", challenge.getQuestion());
    }

    @Test
    public void testAnswerInitializesFields() {
        assertEquals("beep boop bap boop", challenge.getAnswer());
    }

    @Test
    public void testQuestionNullInputs() {
        PhraseChallenge nullChallenge = null;
        try {
            nullChallenge = new PhraseChallenge(null, null, null, null);
        } catch (Exception e) {
            fail("Constructor should not throw exception for null inputs, but threw: " + e);
        }
        assertNull(nullChallenge.getQuestion());
    }

    @Test
    public void testAnswerNullInputs() {
        PhraseChallenge nullChallenge = null;
        try {
            nullChallenge = new PhraseChallenge(null, null, null, null);
        } catch (Exception e) {
            fail("Constructor should not throw exception for null inputs, but threw: " + e);
        }
        assertNull(nullChallenge.getAnswer());
    }

    @Test
    public void testQuestionEmptyStrings() {
        PhraseChallenge emptyChallenge = null;
        try {
            emptyChallenge = new PhraseChallenge("", "", hints, null);
        } catch (Exception e) {
            fail("Constructor should not throw exception for empty strings, but threw: " + e);
        }
        assertEquals("", emptyChallenge.getQuestion());
    }

    @Test
    public void testAnswerEmptyStrings() {
        PhraseChallenge emptyChallenge = null;
        try {
            emptyChallenge = new PhraseChallenge("", "", hints, null);
        } catch (Exception e) {
            fail("Constructor should not throw exception for empty strings, but threw: " + e);
        }
        assertEquals("", emptyChallenge.getAnswer());
    }

    @Test
    public void testGetQuestionReturnsExpectedValue() {
        assertEquals("Doot doot da loot doot?", challenge.getQuestion());
    }

    @Test
    public void testSetQuestionUpdatesValue() {
        challenge.setQuestion("What is polymorphism?");
        assertEquals("What is polymorphism?", challenge.getQuestion());
    }

    @Test
    public void testSetQuestionWithNullDoesNotThrow() {
        try {
            challenge.setQuestion(null);
        } catch (Exception e) {
            fail("setQuestion(null) should not throw, but threw: " + e);
        }
        assertNull(challenge.getQuestion());
    }

    @Test
    public void testSetQuestionWithEmptyString() {
        try {
            challenge.setQuestion("");
        } catch (Exception e) {
            fail("setQuestion(\"\") should not throw, but threw: " + e);
        }
        assertEquals("", challenge.getQuestion());
    }


    @Test
    public void testGetAnswerReturnsExpectedValue() {
        assertEquals("beep boop bap boop", challenge.getAnswer());
    }

    @Test
    public void testSetAnswerUpdatesValue() {
        challenge.setAnswer("A beverage");
        assertEquals("A beverage", challenge.getAnswer());
    }

    @Test
    public void testSetAnswerWithEmptyString() {
        try {
            challenge.setAnswer("");
        } catch (Exception e) {
            fail("setAnswer(\"\") should not throw, but threw: " + e);
        }
        assertEquals("", challenge.getAnswer());
    }

    @Test
    public void testSetQuestion() {
        challenge.setQuestion("What language are we testing?");
        assertEquals("What language are we testing?", challenge.getQuestion());
    }

    @Test
    public void testSetAnswer() {
        challenge.setAnswer("Java");
        assertEquals("Java", challenge.getAnswer());
    }

    @Test
    public void testSetAnswerWithNullDoesNotThrow() {
        try {
            challenge.setAnswer(null);
        } catch (Exception e) {
            fail("setAnswer(null) should not throw, but threw: " + e);
        }
        assertNull(challenge.getAnswer());
    }

    @Test
    public void testQuestionUpdatesDoNotThrow() {
        try {
            challenge.setQuestion("First");
            challenge.setQuestion("Second");
        } catch (Exception e) {
            fail("Repeated setQuestion/setAnswer should not throw, but threw: " + e);
        }
        assertEquals("Second", challenge.getQuestion());
    }

    @Test
    public void testAnswerUpdatesDoNotThrow() {
        try {
            challenge.setAnswer("First answer");
            challenge.setAnswer("Second answer");
        } catch (Exception e) {
            fail("Repeated setQuestion/setAnswer should not throw, but threw: " + e);
        }
        assertEquals("Second answer", challenge.getAnswer());
    }
}