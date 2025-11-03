package com.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;

/**
 * Test suite for the GameSession class.
 * Tests all functionality including session management, scoring, and state transitions.
 */
public class GameSessionTest {
    private GameSession gameSession;
    private static final String TEAM_NAME = "Test Team";
    private static final String SESSION_NAME = "Test Session";
    private static final String THEME = "Mystery";
    private static final int DIFFICULTY = 2;
    private static final int PLAYER_COUNT = 4;
    private UUID userId;

    @Before
    public void setUp() {
        userId = UUID.randomUUID();
        gameSession = new GameSession(userId, TEAM_NAME, SESSION_NAME, THEME, DIFFICULTY, PLAYER_COUNT);
    }

    @Test
    public void testConstructor() {
        assertNotNull("Session ID should not be null", gameSession.getSessionID());
        assertEquals("Team name should match", TEAM_NAME, gameSession.getTeamName());
        assertEquals("Session name should match", SESSION_NAME, gameSession.getSessionName());
        assertEquals("Theme should match", THEME, gameSession.getSessionTheme());
        assertEquals("Difficulty should match", DIFFICULTY, gameSession.getDifficulty());
        assertEquals("Player count should match", PLAYER_COUNT, gameSession.getPlayerCount());
        assertEquals("Initial challenge index should be 0", 0, gameSession.getChallengeIndex());
        assertEquals("Initial hints used should be 0", 0, gameSession.getHintsUsed());
        assertEquals("Initial state should be ACTIVE", SessionState.ACTIVE, gameSession.getState());
        assertNotNull("Progress should not be null", gameSession.getProgress());
    }

    @Test
    public void testSessionIdUniqueness() {
        GameSession anotherSession = new GameSession(userId, TEAM_NAME, SESSION_NAME, THEME, DIFFICULTY, PLAYER_COUNT);
        assertNotEquals("Session IDs should be unique", 
            gameSession.getSessionID(), anotherSession.getSessionID());
    }

    @Test
    public void testHintManagement() {
        assertEquals("Initial hints used should be 0", 0, gameSession.getHintsUsed());
        
        gameSession.addHintUsed();
        assertEquals("Hints used should increment by 1", 1, gameSession.getHintsUsed());
        
        gameSession.setHintsUsed(5);
        assertEquals("Hints used should be settable", 5, gameSession.getHintsUsed());
    }

    @Test
    public void testScoreManagement() {
        assertEquals("Initial score should be 0", 0, gameSession.getScore());
        
        gameSession.setScore(100);
        assertEquals("Score should be settable", 100, gameSession.getScore());
        
        gameSession.setScore(50);
        assertEquals("Score should be updatable", 50, gameSession.getScore());
    }

    @Test
    public void testChallengeIndexManagement() {
        assertEquals("Initial challenge index should be 0", 0, gameSession.getChallengeIndex());
        
        gameSession.setChallengeIndex(3);
        assertEquals("Challenge index should be settable", 3, gameSession.getChallengeIndex());
        
        gameSession.advancePuzzle();
        assertEquals("Challenge index should increment", 4, gameSession.getChallengeIndex());
    }

    @Test
    public void testStateTransitions() {
        assertEquals("Initial state should be ACTIVE", SessionState.ACTIVE, gameSession.getState());
        
        gameSession.pause();
        assertEquals("State should be PAUSED after pause", SessionState.PAUSED, gameSession.getState());
        
        gameSession.resume();
        assertEquals("State should be ACTIVE after resume", SessionState.ACTIVE, gameSession.getState());
        
        gameSession.complete();
        assertEquals("State should be COMPLETED after complete", SessionState.COMPLETED, gameSession.getState());
    }

    @Test
    public void testPercentCalculation() {
        // The percent calculation should return a value between 0 and 100
        int percent = gameSession.getPercent();
        assertTrue("Percent should be between 0 and 100", percent >= 0 && percent <= 100);
        
        // Test with different challenge indices
        gameSession.setChallengeIndex(1);
        int newPercent = gameSession.getPercent();
        assertTrue("Percent should increase with higher challenge index", 
            newPercent > 0);
    }

    @Test
    public void testToString() {
        String sessionString = gameSession.toString();
        assertTrue("ToString should contain session name", 
            sessionString.contains(SESSION_NAME));
        assertTrue("ToString should contain team name", 
            sessionString.contains(TEAM_NAME));
        assertTrue("ToString should contain theme", 
            sessionString.contains(THEME));
        assertTrue("ToString should contain difficulty", 
            sessionString.contains(String.valueOf(DIFFICULTY)));
        assertTrue("ToString should contain player count", 
            sessionString.contains(String.valueOf(PLAYER_COUNT)));
    }

    @Test
    public void testProgressManagement() {
        ChallengeProgress progress = gameSession.getProgress();
        assertNotNull("Progress should not be null", progress);
    }

    @Test
    public void testStateValidTransitions() {
        // Test valid state transitions
        gameSession.pause();
        assertEquals(SessionState.PAUSED, gameSession.getState());
        
        gameSession.resume();
        assertEquals(SessionState.ACTIVE, gameSession.getState());
        
        gameSession.complete();
        assertEquals(SessionState.COMPLETED, gameSession.getState());
        
        // Test transitions from completed state
        gameSession.pause(); // Should remain completed
        assertEquals("Completed state should be final", 
            SessionState.COMPLETED, gameSession.getState());
    }

    @Test
    public void testEdgeCases() {
        // Test with extreme values
        gameSession.setChallengeIndex(Integer.MAX_VALUE);
        assertTrue("Should handle maximum challenge index", 
            gameSession.getChallengeIndex() == Integer.MAX_VALUE);
        
        gameSession.setScore(Integer.MAX_VALUE);
        assertTrue("Should handle maximum score", 
            gameSession.getScore() == Integer.MAX_VALUE);
        
        gameSession.setHintsUsed(Integer.MAX_VALUE);
        assertTrue("Should handle maximum hints used", 
            gameSession.getHintsUsed() == Integer.MAX_VALUE);
    }
}
