// package com.model;

// import static org.junit.Assert.*;
// import org.junit.Before;
// import org.junit.Test;
// import java.util.UUID;
// import java.util.HashSet;
// import java.util.concurrent.CountDownLatch;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
// import java.util.concurrent.TimeUnit;

// /**
// * Comprehensive test suite for the GameSession class.
// * Tests all functionality including session management, scoring, state
// transitions,
// * edge cases, concurrent modifications, and stress testing.
// */
// public class GameSessionTest {
// private GameSession gameSession;
// private static final String TEAM_NAME = "Test Team";
// private static final String SESSION_NAME = "Test Session";
// private static final String THEME = "Mystery";
// private static final int DIFFICULTY = 2;
// private static final int PLAYER_COUNT = 4;
// private UUID userId;

// // Edge case test data
// private static final String EMPTY_STRING = "";
// private static final String VERY_LONG_STRING = "a".repeat(10000);
// private static final String SPECIAL_CHARS =
// "!@#$%^&*()\n\t\r\b\f\\\"';:,.<>/?–—\uD83D\uDE00\u0000";
// private static final String NULL_CHAR_STRING = "Test\u0000With\u0000Null";
// private static final int NEGATIVE_ONE = -1;
// private static final int MAX_INT = Integer.MAX_VALUE;
// private static final int MIN_INT = Integer.MIN_VALUE;

// @Before
// public void setUp() {
// userId = UUID.randomUUID();
// gameSession = new GameSession(userId, TEAM_NAME, SESSION_NAME, THEME,
// DIFFICULTY, PLAYER_COUNT);
// }

// @Test
// public void testConstructorNormalCase() {
// assertNotNull("Session ID should not be null", gameSession.getSessionID());
// assertEquals("Team name should match", TEAM_NAME, gameSession.getTeamName());
// assertEquals("Session name should match", SESSION_NAME,
// gameSession.getSessionName());
// assertEquals("Theme should match", THEME, gameSession.getSessionTheme());
// assertEquals("Difficulty should match", DIFFICULTY,
// gameSession.getDifficulty());
// assertEquals("Player count should match", PLAYER_COUNT,
// gameSession.getPlayerCount());
// assertEquals("Initial challenge index should be 0", 0,
// gameSession.getChallengeIndex());
// assertEquals("Initial hints used should be 0", 0,
// gameSession.getHintsUsed());
// assertEquals("Initial state should be ACTIVE", SessionState.ACTIVE,
// gameSession.getState());
// assertNotNull("Progress should not be null", gameSession.getProgress());
// }

// @Test
// public void testConstructorEdgeCases() {
// // Test with empty strings
// GameSession emptySession = new GameSession(UUID.randomUUID(), "", "", "", 0,
// 0);
// assertEquals("Empty team name should be preserved", "",
// emptySession.getTeamName());
// assertEquals("Empty session name should be preserved", "",
// emptySession.getSessionName());
// assertEquals("Empty theme should be preserved", "",
// emptySession.getSessionTheme());

// // Test with very long strings
// String longString = "a".repeat(10000);
// GameSession longSession = new GameSession(UUID.randomUUID(), longString,
// longString, longString, DIFFICULTY, PLAYER_COUNT);
// assertEquals("Long team name should be preserved", longString,
// longSession.getTeamName());
// assertEquals("Long session name should be preserved", longString,
// longSession.getSessionName());

// // Test with special characters including null chars and emoji
// String specialString =
// "!@#$%^&*()\n\t\r\b\f\\\"';:,.<>/?–—\uD83D\uDE00\u0000";
// GameSession specialSession = new GameSession(UUID.randomUUID(),
// specialString, specialString, specialString, DIFFICULTY, PLAYER_COUNT);
// assertEquals("Special characters should be preserved", specialString,
// specialSession.getTeamName());

// // Test with extreme integer values
// GameSession extremeSession = new GameSession(
// UUID.randomUUID(), TEAM_NAME, SESSION_NAME, THEME,
// Integer.MAX_VALUE, Integer.MIN_VALUE
// );
// assertEquals("Max difficulty should be preserved", Integer.MAX_VALUE,
// extremeSession.getDifficulty());
// assertEquals("Min player count should be preserved", Integer.MIN_VALUE,
// extremeSession.getPlayerCount());
// }

// @Test
// public void testSessionIdUniqueness() {
// HashSet<UUID> sessionIds = new HashSet<>();

// // Generate 1000 sessions and verify all IDs are unique
// for (int i = 0; i < 1000; i++) {
// GameSession newSession = new GameSession(
// UUID.randomUUID(), TEAM_NAME, SESSION_NAME, THEME, DIFFICULTY, PLAYER_COUNT
// );
// UUID sessionId = newSession.getSessionID();
// assertFalse("Session ID should be unique: " + sessionId,
// sessionIds.contains(sessionId));
// sessionIds.add(sessionId);
// }
// }

// @Test
// public void testConcurrentSessionCreation() throws InterruptedException {
// final int THREAD_COUNT = 100;
// final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
// final HashSet<UUID> sessionIds = new HashSet<>();
// final Object lock = new Object();

// ExecutorService executor = Executors.newFixedThreadPool(10);

// // Create sessions concurrently
// for (int i = 0; i < THREAD_COUNT; i++) {
// executor.submit(() -> {
// try {
// GameSession session = new GameSession(
// UUID.randomUUID(), TEAM_NAME, SESSION_NAME, THEME, DIFFICULTY, PLAYER_COUNT
// );
// synchronized (lock) {
// assertFalse("Session ID should be unique even under concurrency",
// sessionIds.contains(session.getSessionID()));
// sessionIds.add(session.getSessionID());
// }
// } finally {
// latch.countDown();
// }
// });
// }

// assertTrue("All threads should complete", latch.await(10, TimeUnit.SECONDS));
// executor.shutdown();
// assertEquals("All sessions should be created", THREAD_COUNT,
// sessionIds.size());
// }

// @Test
// public void testHintManagement() {
// assertEquals("Initial hints used should be 0", 0,
// gameSession.getHintsUsed());

// // Test normal increment behavior
// for (int i = 0; i < 100; i++) {
// int beforeHints = gameSession.getHintsUsed();
// gameSession.addHintUsed();
// assertEquals("Hints used should increment by exactly 1", beforeHints + 1,
// gameSession.getHintsUsed());
// }

// // Test boundary values
// gameSession.setHintsUsed(0);
// assertEquals("Should allow setting hints to 0", 0,
// gameSession.getHintsUsed());

// gameSession.setHintsUsed(Integer.MAX_VALUE);
// assertEquals("Should handle maximum integer value", Integer.MAX_VALUE,
// gameSession.getHintsUsed());
// gameSession.addHintUsed(); // Test overflow
// assertEquals("Should handle overflow gracefully", Integer.MIN_VALUE,
// gameSession.getHintsUsed());

// gameSession.setHintsUsed(Integer.MIN_VALUE);
// assertEquals("Should handle minimum integer value", Integer.MIN_VALUE,
// gameSession.getHintsUsed());

// // Stress test - rapid hint additions
// for (int i = 0; i < 10000; i++) {
// gameSession.addHintUsed();
// }
// assertEquals("Should handle many rapid hint additions", 10000 +
// Integer.MIN_VALUE, gameSession.getHintsUsed());
// }

// @Test
// public void testConcurrentHintModification() throws InterruptedException {
// final int THREAD_COUNT = 100;
// final int ITERATIONS_PER_THREAD = 1000;
// final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

// ExecutorService executor = Executors.newFixedThreadPool(10);

// // Modify hints concurrently
// for (int i = 0; i < THREAD_COUNT; i++) {
// executor.submit(() -> {
// try {
// for (int j = 0; j < ITERATIONS_PER_THREAD; j++) {
// gameSession.addHintUsed();
// }
// } finally {
// latch.countDown();
// }
// });
// }

// assertTrue("All threads should complete", latch.await(10, TimeUnit.SECONDS));
// executor.shutdown();
// assertEquals("Final hint count should match total operations",
// THREAD_COUNT * ITERATIONS_PER_THREAD, gameSession.getHintsUsed());
// }

// @Test
// public void testScoreManagement() {
// assertEquals("Initial score should be 0", 0, gameSession.getScore());

// // Test normal score updates
// int[] testScores = {1, -1, 100, -100, 0, Integer.MAX_VALUE,
// Integer.MIN_VALUE};
// for (int score : testScores) {
// gameSession.setScore(score);
// assertEquals("Score should be set correctly", score, gameSession.getScore());
// }

// // Test rapid score changes
// for (int i = -1000; i < 1000; i++) {
// gameSession.setScore(i);
// assertEquals("Score should be updated correctly during rapid changes", i,
// gameSession.getScore());
// }

// // Test extreme value transitions
// gameSession.setScore(Integer.MAX_VALUE);
// assertEquals("Should handle maximum score", Integer.MAX_VALUE,
// gameSession.getScore());

// gameSession.setScore(Integer.MIN_VALUE);
// assertEquals("Should handle minimum score", Integer.MIN_VALUE,
// gameSession.getScore());

// // Test score cycling
// for (int i = 0; i < 1000; i++) {
// gameSession.setScore(Integer.MAX_VALUE);
// gameSession.setScore(Integer.MIN_VALUE);
// gameSession.setScore(0);
// }
// assertEquals("Score should be stable after cycling", 0,
// gameSession.getScore());
// }

// @Test
// public void testChallengeIndexManagement() {
// assertEquals("Initial challenge index should be 0", 0,
// gameSession.getChallengeIndex());

// // Test normal progression with bounds
// int[] testIndices = {
// 0, 1, -1, 100, -100,
// Integer.MAX_VALUE, Integer.MIN_VALUE,
// Integer.MAX_VALUE - 1, Integer.MIN_VALUE + 1
// };

// for (int index : testIndices) {
// gameSession.setChallengeIndex(index);
// assertEquals("Challenge index should be set correctly", index,
// gameSession.getChallengeIndex());

// // Test advancement from each position
// if (index < Integer.MAX_VALUE) {
// gameSession.advancePuzzle();
// assertEquals("Should increment by 1", index + 1,
// gameSession.getChallengeIndex());
// }
// }

// // Test overflow behavior
// gameSession.setChallengeIndex(Integer.MAX_VALUE);
// gameSession.advancePuzzle(); // Should overflow to MIN_VALUE
// assertEquals("Should handle index overflow", Integer.MIN_VALUE,
// gameSession.getChallengeIndex());

// // Stress test - rapid advancement
// gameSession.setChallengeIndex(0);
// for (int i = 0; i < 10000; i++) {
// int expected = gameSession.getChallengeIndex() + 1;
// gameSession.advancePuzzle();
// assertEquals("Should increment correctly during rapid advancement",
// expected, gameSession.getChallengeIndex());
// }
// }

// @Test
// public void testConcurrentChallengeProgression() throws InterruptedException
// {
// final int THREAD_COUNT = 100;
// final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
// gameSession.setChallengeIndex(0);

// ExecutorService executor = Executors.newFixedThreadPool(10);

// // Advance puzzle concurrently
// for (int i = 0; i < THREAD_COUNT; i++) {
// executor.submit(() -> {
// try {
// gameSession.advancePuzzle();
// } finally {
// latch.countDown();
// }
// });
// }

// assertTrue("All threads should complete", latch.await(10, TimeUnit.SECONDS));
// executor.shutdown();
// assertEquals("Final index should match thread count",
// THREAD_COUNT, gameSession.getChallengeIndex());
// }

// @Test
// public void testStateTransitionsComprehensive() {
// assertEquals("Initial state should be ACTIVE", SessionState.ACTIVE,
// gameSession.getState());

// // Test all possible state transitions
// SessionState[][] transitions = {
// {SessionState.ACTIVE, SessionState.PAUSED}, // active -> pause
// {SessionState.PAUSED, SessionState.ACTIVE}, // paused -> resume
// {SessionState.ACTIVE, SessionState.COMPLETED}, // active -> complete
// {SessionState.COMPLETED, SessionState.COMPLETED}, // complete -> any action
// {SessionState.PAUSED, SessionState.COMPLETED}, // paused -> complete
// };

// for (SessionState[] transition : transitions) {
// GameSession testSession = new GameSession(
// UUID.randomUUID(), TEAM_NAME, SESSION_NAME, THEME, DIFFICULTY, PLAYER_COUNT
// );

// // Set initial state
// switch (transition[0]) {
// case ACTIVE: break; // default state
// case PAUSED: testSession.pause(); break;
// case COMPLETED: testSession.complete(); break;
// }
// assertEquals("Initial state should be set", transition[0],
// testSession.getState());

// // Try to transition
// switch (transition[1]) {
// case ACTIVE: testSession.resume(); break;
// case PAUSED: testSession.pause(); break;
// case COMPLETED: testSession.complete(); break;
// }
// assertEquals("State should transition correctly", transition[1],
// testSession.getState());
// }
// }

// @Test
// public void testStateTransitionsDuringOperations() {
// // Test state consistency during various operations
// gameSession.pause();
// int currentIndex = gameSession.getChallengeIndex();
// gameSession.advancePuzzle();
// assertEquals("Puzzle should advance while paused", currentIndex + 1,
// gameSession.getChallengeIndex());
// assertEquals("State should remain paused after operation",
// SessionState.PAUSED, gameSession.getState());

// gameSession.complete();
// currentIndex = gameSession.getChallengeIndex();
// gameSession.advancePuzzle();
// assertEquals("Puzzle should advance while completed", currentIndex + 1,
// gameSession.getChallengeIndex());
// assertEquals("State should remain completed after operation",
// SessionState.COMPLETED, gameSession.getState());

// // Test rapid state transitions
// for (int i = 0; i < 1000; i++) {
// gameSession.pause();
// assertEquals(SessionState.PAUSED, gameSession.getState());
// gameSession.resume();
// assertEquals(SessionState.ACTIVE, gameSession.getState());
// }
// }

// @Test
// public void testPercentCalculation() {
// // Test initial state
// int percent = gameSession.getPercent();
// assertTrue("Initial percent should be between 0 and 100", percent >= 0 &&
// percent <= 100);
// assertEquals("With no progress, percent should be 0", 0, percent);

// // Test with various challenge indices
// int[] testIndices = {0, 1, -1, 100, -100, Integer.MAX_VALUE,
// Integer.MIN_VALUE};
// for (int index : testIndices) {
// gameSession.setChallengeIndex(index);
// int newPercent = gameSession.getPercent();
// assertTrue("Percent should always be between 0 and 100", newPercent >= 0 &&
// newPercent <= 100);
// }

// // Test percentage calculation accuracy
// GameList gameList = GameList.getInstance();
// gameList.loadGames();
// Game testGame = new Game(gameSession);
// gameList.getGameData(testGame);
// testGame.assignChallenges();

// int totalChallenges = testGame.getChallenges().size();
// if (totalChallenges > 0) {
// // Test key percentages
// gameSession.setChallengeIndex(0);
// assertEquals("Start should be 0%", 0, gameSession.getPercent());

// gameSession.setChallengeIndex(totalChallenges / 2);
// assertEquals("Halfway should be ~50%", 50, gameSession.getPercent());

// gameSession.setChallengeIndex(totalChallenges);
// assertEquals("Complete should be 100%", 100, gameSession.getPercent());

// // Test each step
// for (int i = 0; i <= totalChallenges; i++) {
// gameSession.setChallengeIndex(i);
// float expectedPercent = ((float)i / totalChallenges) * 100;
// assertEquals("Percentage should be calculated correctly",
// Math.round(expectedPercent), gameSession.getPercent());
// }
// }
// }

// @Test
// public void testToStringComprehensive() {
// // Test normal case
// String sessionString = gameSession.toString();
// assertTrue("ToString should contain session name",
// sessionString.contains(SESSION_NAME));
// assertTrue("ToString should contain team name",
// sessionString.contains(TEAM_NAME));
// assertTrue("ToString should contain theme", sessionString.contains(THEME));
// assertTrue("ToString should contain difficulty",
// sessionString.contains(String.valueOf(DIFFICULTY)));
// assertTrue("ToString should contain player count",
// sessionString.contains(String.valueOf(PLAYER_COUNT)));

// // Test with empty strings
// GameSession emptySession = new GameSession(UUID.randomUUID(), "", "", "", 0,
// 0);
// String emptyString = emptySession.toString();
// assertTrue("Should handle empty strings", emptyString.contains("Session Name:
// "));
// assertTrue("Should handle empty strings", emptyString.contains("Session Team
// Name: "));

// // Test with special characters
// String specialChars =
// "!@#$%^&*()\n\t\r\b\f\\\"';:,.<>/?–—\uD83D\uDE00\u0000";
// GameSession specialSession = new GameSession(
// UUID.randomUUID(), specialChars, specialChars, specialChars,
// Integer.MIN_VALUE, Integer.MAX_VALUE
// );
// String specialString = specialSession.toString();
// assertTrue("Should handle special characters",
// specialString.contains(specialChars));

// // Test with very long strings
// String longString = "a".repeat(10000);
// GameSession longSession = new GameSession(
// UUID.randomUUID(), longString, longString, longString,
// DIFFICULTY, PLAYER_COUNT
// );
// String longResult = longSession.toString();
// assertTrue("Should handle very long strings",
// longResult.contains(longString));

// // Test with extreme scores
// gameSession.setScore(Integer.MAX_VALUE);
// assertTrue("Should handle maximum score in toString",
// gameSession.toString().contains(String.valueOf(Integer.MAX_VALUE)));

// gameSession.setScore(Integer.MIN_VALUE);
// assertTrue("Should handle minimum score in toString",
// gameSession.toString().contains(String.valueOf(Integer.MIN_VALUE)));
// }

// @Test
// public void testProgressManagementComprehensive() {
// // Test initial state
// ChallengeProgress progress = gameSession.getProgress();
// assertNotNull("Progress should not be null", progress);

// // Test progress persistence
// ChallengeProgress initialProgress = gameSession.getProgress();
// for (int i = 0; i < 100; i++) {
// gameSession.advancePuzzle();
// assertSame("Progress object should remain the same instance",
// initialProgress, gameSession.getProgress());
// }

// // Test progress with state changes
// gameSession.pause();
// assertSame("Progress should persist through pause",
// initialProgress, gameSession.getProgress());

// gameSession.resume();
// assertSame("Progress should persist through resume",
// initialProgress, gameSession.getProgress());

// gameSession.complete();
// assertSame("Progress should persist through completion",
// initialProgress, gameSession.getProgress());

// // Test concurrent access to progress
// final int THREAD_COUNT = 100;
// final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
// ExecutorService executor = Executors.newFixedThreadPool(10);

// for (int i = 0; i < THREAD_COUNT; i++) {
// executor.submit(() -> {
// try {
// assertSame("Progress should be thread-safe",
// initialProgress, gameSession.getProgress());
// } finally {
// latch.countDown();
// }
// });
// }

// try {
// assertTrue("All threads should complete", latch.await(10, TimeUnit.SECONDS));
// } catch (InterruptedException e) {
// fail("Concurrent progress access test interrupted");
// }
// executor.shutdown();
// }
// }
