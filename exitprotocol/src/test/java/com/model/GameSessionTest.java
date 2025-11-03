package com.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;
import java.util.HashSet;

public class GameSessionTest {
    private GameSession session;
    private final String TEAM_NAME = "Test Team";
    private final String SESSION_NAME = "Test Session";
    private final String THEME = "Mystery";
    private final    int DIFFICULTY = 2;
    private final int PLAYER_COUNT = 4;
    private UUID userId;
    
    // Edge cases and boundary values
    private final String EMPTY_STRING = "";
    private final String VERY_LONG_STRING = "a".repeat(1000);
    private final String SPECIAL_CHARS = "!@#$%^&*()\n\t\\\"';:,.<>/?";
    private final int MAX_INT = Integer.MAX_VALUE;
    private final int MIN_INT = Integer.MIN_VALUE;

    @Before
    public void setUp() {
        userId = UUID.randomUUID();
        session = new GameSession(userId, TEAM_NAME, SESSION_NAME, THEME, DIFFICULTY, PLAYER_COUNT);
    }

    @Test
    public void testConstructor() {
        assertNotNull("Session ID should not be null", session.getSessionID());
        assertEquals("Team name should match", TEAM_NAME, session.getTeamName());
        assertEquals("Session name should match", SESSION_NAME, session.getSessionName());
        assertEquals("Theme should match", THEME, session.getSessionTheme());
        assertEquals("Difficulty should match", DIFFICULTY, session.getDifficulty());
        assertEquals("Player count should match", PLAYER_COUNT, session.getPlayerCount());
        assertEquals("Initial challenge index should be 0", 0, session.getChallengeIndex());
        assertEquals("Initial hints used should be 0", 0, session.getHintsUsed());
        assertEquals("Initial state should be ACTIVE", SessionState.ACTIVE, session.getState());
        assertNotNull("Progress should not be null", session.getProgress());
    }

    @Test
    public void testConstructorWithEdgeCases() {
        // Test with empty strings
        GameSession emptySession = new GameSession(UUID.randomUUID(), "", "", "", 0, 0);
        assertEquals("Empty team name should be preserved", "", emptySession.getTeamName());
        assertEquals("Empty session name should be preserved", "", emptySession.getSessionName());
        assertEquals("Empty theme should be preserved", "", emptySession.getSessionTheme());
        
        // Test with very long strings (1000 chars)
        String longString = "a".repeat(1000);
        GameSession longSession = new GameSession(UUID.randomUUID(), longString, longString, longString, DIFFICULTY, PLAYER_COUNT);
        assertEquals("Long team name should be preserved", longString, longSession.getTeamName());
        assertEquals("Long session name should be preserved", longString, longSession.getSessionName());
        
        // Test with special characters
        String specialString = "!@#$%^&*()\n\t\\\"';:,.<>/?";
        GameSession specialSession = new GameSession(UUID.randomUUID(), specialString, specialString, specialString, DIFFICULTY, PLAYER_COUNT);
        assertEquals("Special characters in team name should be preserved", specialString, specialSession.getTeamName());
        
        // Test with extreme integer values
        GameSession extremeSession = new GameSession(UUID.randomUUID(), TEAM_NAME, SESSION_NAME, THEME, Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals("Max difficulty should be preserved", Integer.MAX_VALUE, extremeSession.getDifficulty());
        assertEquals("Max player count should be preserved", Integer.MAX_VALUE, extremeSession.getPlayerCount());
        
        GameSession negativeSession = new GameSession(UUID.randomUUID(), TEAM_NAME, SESSION_NAME, THEME, Integer.MIN_VALUE, Integer.MIN_VALUE);
        assertEquals("Negative difficulty should be preserved", Integer.MIN_VALUE, negativeSession.getDifficulty());
        assertEquals("Negative player count should be preserved", Integer.MIN_VALUE, negativeSession.getPlayerCount());
    }

    @Test
    public void testSessionIDUniqueness() {
        // Create multiple sessions and verify IDs are unique
        HashSet<UUID> sessionIds = new HashSet<>();
        for(int i = 0; i < 1000; i++) {
            GameSession newSession = new GameSession(UUID.randomUUID(), TEAM_NAME, SESSION_NAME, THEME, DIFFICULTY, PLAYER_COUNT);
            UUID id = newSession.getSessionID();
            assertFalse("Session ID should be unique", sessionIds.contains(id));
            sessionIds.add(id);
        }
    }

    @Test
    public void testHintManagement() {
        assertEquals("Initial hints should be 0", 0, session.getHintsUsed());
        
        // Test normal increment
        for(int i = 0; i < 100; i++) {
            int beforeHints = session.getHintsUsed();
            session.addHintUsed();
            assertEquals("Hints should increment by exactly 1", beforeHints + 1, session.getHintsUsed());
        }
        
        // Test boundary values for setHintsUsed
        session.setHintsUsed(0);
        assertEquals("Should allow setting hints to 0", 0, session.getHintsUsed());
        
        session.setHintsUsed(Integer.MAX_VALUE);
        assertEquals("Should handle maximum integer value", Integer.MAX_VALUE, session.getHintsUsed());
        
        session.setHintsUsed(Integer.MIN_VALUE);
        assertEquals("Should handle negative values", Integer.MIN_VALUE, session.getHintsUsed());
        
        // Test rapid hint additions
        for(int i = 0; i < 1000; i++) {
            session.addHintUsed();
        }
        assertEquals("Should handle many rapid hint additions", 1000, session.getHintsUsed());
    }

    @Test
    public void testScoreManagement() {
        assertEquals("Initial score should be 0", 0, session.getScore());
        
        // Test normal score updates
        session.setScore(100);
        assertEquals("Score should be updated to positive value", 100, session.getScore());
        
        session.setScore(0);
        assertEquals("Score should be updated to zero", 0, session.getScore());
        
        session.setScore(-50);
        assertEquals("Score should allow negative values", -50, session.getScore());
        
        // Test boundary values
        session.setScore(Integer.MAX_VALUE);
        assertEquals("Should handle maximum integer score", Integer.MAX_VALUE, session.getScore());
        
        session.setScore(Integer.MIN_VALUE);
        assertEquals("Should handle minimum integer score", Integer.MIN_VALUE, session.getScore());
        
        // Test rapid score changes
        for(int i = -1000; i < 1000; i++) {
            session.setScore(i);
            assertEquals("Score should be updated correctly during rapid changes", i, session.getScore());
        }
    }

    @Test
    public void testChallengeIndexManagement() {
        assertEquals("Initial challenge index should be 0", 0, session.getChallengeIndex());
        
        // Test normal progression
        for(int i = 0; i < 100; i++) {
            session.advancePuzzle();
            assertEquals("Challenge index should increment correctly", i + 1, session.getChallengeIndex());
        }
        
        // Test setting specific indices including boundary cases
        int[] testIndices = {
            0,                  // Start
            -1,                // Negative
            Integer.MIN_VALUE,  // Minimum possible
            Integer.MAX_VALUE,  // Maximum possible
            1000000,           // Large number
            -1000000           // Large negative
        };
        
        for(int index : testIndices) {
            session.setChallengeIndex(index);
            assertEquals("Challenge index should be set correctly", index, session.getChallengeIndex());
            session.advancePuzzle();
            assertEquals("Should increment correctly from any value", index + 1, session.getChallengeIndex());
        }
        
        // Test rapid advancement
        session.setChallengeIndex(0);
        for(int i = 0; i < 10000; i++) {
            session.advancePuzzle();
        }
        assertEquals("Should handle rapid puzzle advancement", 10000, session.getChallengeIndex());
    }

    @Test
    public void testSessionStateTransitions() {
        assertEquals("Initial state should be ACTIVE", SessionState.ACTIVE, session.getState());
        
        // Test all possible state transitions
        session.pause();
        assertEquals("State should be PAUSED after pause", SessionState.PAUSED, session.getState());
        
        session.resume();
        assertEquals("State should be ACTIVE after resume", SessionState.ACTIVE, session.getState());
        
        session.complete();
        assertEquals("State should be COMPLETED after complete", SessionState.COMPLETED, session.getState());
        
        // Test state transitions from COMPLETED
        session.pause();  // Should still be COMPLETED
        assertEquals("COMPLETED state should be final", SessionState.COMPLETED, session.getState());
        
        session.resume(); // Should still be COMPLETED
        assertEquals("COMPLETED state should be final", SessionState.COMPLETED, session.getState());
        
        // Test multiple pause/resume cycles
        session = new GameSession(userId, TEAM_NAME, SESSION_NAME, THEME, DIFFICULTY, PLAYER_COUNT);
        for(int i = 0; i < 100; i++) {
            session.pause();
            assertEquals("Should be PAUSED", SessionState.PAUSED, session.getState());
            session.resume();
            assertEquals("Should be ACTIVE", SessionState.ACTIVE, session.getState());
        }
        
        // Test state transitions during challenge progression
        session.pause();
        session.advancePuzzle();
        assertEquals("Should maintain PAUSED state during challenge advancement", SessionState.PAUSED, session.getState());
        
        session.resume();
        session.advancePuzzle();
        assertEquals("Should maintain ACTIVE state during challenge advancement", SessionState.ACTIVE, session.getState());
    }

    @Test
    public void testToString() {
        // Test normal case
        String expected = "Session Name: " + SESSION_NAME + 
                         "\nSession Team Name: " + TEAM_NAME +
                         "\nSession Theme: " + THEME + 
                         "\nDifficulty: " + DIFFICULTY + 
                         "\nPlayer Count: " + PLAYER_COUNT + 
                         "\nCurrent Score: " + session.getScore();
        assertEquals("toString should match expected format", expected, session.toString());
        
        // Test with empty strings
        GameSession emptySession = new GameSession(UUID.randomUUID(), "", "", "", 0, 0);
        String emptyExpected = "Session Name: " + 
                             "\nSession Team Name: " +
                             "\nSession Theme: " + 
                             "\nDifficulty: " + 0 + 
                             "\nPlayer Count: " + 0 + 
                             "\nCurrent Score: " + 0;
        assertEquals("toString should handle empty strings", emptyExpected, emptySession.toString());
        
        // Test with special characters
        String specialChars = "!@#$%^&*()\n\t\\\"';:,.<>/?";
        GameSession specialSession = new GameSession(UUID.randomUUID(), specialChars, specialChars, specialChars, -1, -1);
        String specialExpected = "Session Name: " + specialChars + 
                               "\nSession Team Name: " + specialChars +
                               "\nSession Theme: " + specialChars + 
                               "\nDifficulty: " + (-1) + 
                               "\nPlayer Count: " + (-1) + 
                               "\nCurrent Score: " + 0;
        assertEquals("toString should handle special characters", specialExpected, specialSession.toString());
        
        // Test with extreme values
        GameSession extremeSession = new GameSession(UUID.randomUUID(), TEAM_NAME, SESSION_NAME, THEME, Integer.MAX_VALUE, Integer.MIN_VALUE);
        extremeSession.setScore(Integer.MAX_VALUE);
        String extremeExpected = "Session Name: " + SESSION_NAME + 
                               "\nSession Team Name: " + TEAM_NAME +
                               "\nSession Theme: " + THEME + 
                               "\nDifficulty: " + Integer.MAX_VALUE + 
                               "\nPlayer Count: " + Integer.MIN_VALUE + 
                               "\nCurrent Score: " + Integer.MAX_VALUE;
        assertEquals("toString should handle extreme values", extremeExpected, extremeSession.toString());
    }

    @Test
    public void testGetPercent() {
        // Test initial state
        int percent = session.getPercent();
        assertTrue("Initial percent should be between 0 and 100", percent >= 0 && percent <= 100);
        assertEquals("With no progress, percent should be 0", 0, percent);
        
        // Test different challenge indices
        int[] testIndices = {0, 1, 5, 10, 100, -1, Integer.MAX_VALUE};
        for(int index : testIndices) {
            session.setChallengeIndex(index);
            int newPercent = session.getPercent();
            assertTrue("Percent should always be between 0 and 100", newPercent >= 0 && newPercent <= 100);
        }
        
        // Test percentage calculation accuracy
        // Note: This assumes the game has a fixed number of challenges
        GameList gameList = GameList.getInstance();
        gameList.loadGames();
        Game testGame = new Game(session);
        gameList.getGameData(testGame);
        testGame.assignChallenges();
        
        int totalChallenges = testGame.getChallenges().size();
        if(totalChallenges > 0) {
            session.setChallengeIndex(totalChallenges / 2);
            assertEquals("Halfway through should be ~50%", 50, session.getPercent());
            
            session.setChallengeIndex(totalChallenges);
            assertEquals("Complete should be 100%", 100, session.getPercent());
        }
    }

    @Test
    public void testProgress() {
        // Test initial progress
        ChallengeProgress progress = session.getProgress();
        assertNotNull("Progress object should not be null", progress);
        
        // Test progress persistence
        ChallengeProgress initialProgress = session.getProgress();
        session.advancePuzzle();
        ChallengeProgress afterAdvanceProgress = session.getProgress();
        assertSame("Progress object should remain the same instance", initialProgress, afterAdvanceProgress);
        
        // Test progress with state changes
        session.pause();
        ChallengeProgress pausedProgress = session.getProgress();
        assertSame("Progress should persist through state changes", initialProgress, pausedProgress);
        
        session.complete();
        ChallengeProgress completedProgress = session.getProgress();
        assertSame("Progress should persist after completion", initialProgress, completedProgress);
    }
}
