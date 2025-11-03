package com.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Test suite for the Countdown class.
 * Tests timer functionality, pausing/resuming, and time reduction features.
 */
public class CountdownTest {
    private Countdown countdown;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        countdown = new Countdown();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testConstructorInitialization() {
        assertNotNull("Countdown should be initialized", countdown);
        assertFalse("Timer should not be paused initially", countdown.isPaused());
    }

    @Test
    public void testStartTimer() {
        countdown.startTimer();
        // Let the timer run for a short period
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            fail("Test interrupted");
        }
        assertTrue("Time should decrease after starting timer", 
            countdown.getTimeRemaining() < 900);
    }

    @Test
    public void testPauseAndResume() {
        countdown.startTimer();
        int timeBeforePause = countdown.getTimeRemaining();
        countdown.pause();
        assertTrue("Timer should be paused", countdown.isPaused());
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            fail("Test interrupted");
        }
        
        assertEquals("Time should not decrease while paused", 
            timeBeforePause, countdown.getTimeRemaining());
        
        countdown.resume();
        assertFalse("Timer should not be paused after resume", countdown.isPaused());
    }

    @Test
    public void testResetTimer() {
        countdown.startTimer();
        countdown.reduceTimeRemaining(100); // Reduce some time
        countdown.resetTimer();
        assertEquals("Timer should reset to time limit (900 seconds)", 
            900, countdown.getTimeRemaining());
    }

    @Test
    public void testReduceTimeRemaining() {
        int initialTime = countdown.getTimeRemaining();
        countdown.reduceTimeRemaining();
        assertEquals("Time should decrease by 1 second", 
            initialTime - 1, countdown.getTimeRemaining());
    }

    @Test
    public void testReduceTimeRemainingWithDifficulty() {
        int initialTime = countdown.getTimeRemaining();
        int difficulty = 30;
        countdown.reduceTimeRemaining(difficulty);
        assertEquals("Time should decrease by difficulty value", 
            initialTime - difficulty, countdown.getTimeRemaining());
    }

    @Test
    public void testTimeThresholdNotifications() {
        countdown.resetTimer();
        countdown.reduceTimeRemaining(300); // Reduce to 5 minutes remaining
        outContent.reset();
        countdown.startTimer();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            fail("Test interrupted");
        }
        assertTrue("Should show 5-minute warning", 
            outContent.toString().contains("5 Minutes Remaining!"));
    }

    @Test
    public void testTimerCompletion() {
        countdown.resetTimer();
        countdown.reduceTimeRemaining(900); // Reduce to 0
        outContent.reset();
        countdown.startTimer();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            fail("Test interrupted");
        }
        assertTrue("Should show time's up message", 
            outContent.toString().contains("Time's Up!"));
    }

    @Test
    public void testNegativeTimeHandling() {
        countdown.resetTimer();
        countdown.reduceTimeRemaining(1000); // Reduce beyond 0
        assertTrue("Time should not go below 0", 
            countdown.getTimeRemaining() <= 0);
    }

    @Test
    public void testConcurrentOperations() {
        countdown.startTimer();
        countdown.pause();
        countdown.reduceTimeRemaining(50);
        countdown.resume();
        assertFalse("Timer should remain unpaused after concurrent operations", 
            countdown.isPaused());
    }

    @Test
    public void testMultipleResets() {
        countdown.startTimer();
        countdown.resetTimer();
        int timeAfterFirstReset = countdown.getTimeRemaining();
        countdown.resetTimer();
        assertEquals("Multiple resets should result in the same time", 
            timeAfterFirstReset, countdown.getTimeRemaining());
    }
}
