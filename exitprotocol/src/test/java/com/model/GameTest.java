// package com.model;

// import org.junit.Before;
// import org.junit.After;
// import org.junit.Test;
// import static org.junit.Assert.*;

// import java.io.ByteArrayOutputStream;
// import java.io.File;
// import java.io.PrintStream;
// import java.util.ArrayList;
// import java.util.UUID;

// /**
// * Comprehensive test suite for the Game class.
// * Tests all methods and edge cases to identify potential bugs.
// *
// * @author Clankers
// */
// public class GameTest {

// private Game game;
// private GameSession session;
// private GameTemplate gameTemplate;
// private Challenge challenge1;
// private Challenge challenge2;
// private Challenge challenge3;
// private Item item1;
// private Item item2;
// private UUID testUserID;

// private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
// private final PrintStream originalOut = System.out;

// /**
// * Sets up test fixtures before each test method.
// */
// @Before
// public void setUp() {
// // Create test user ID
// testUserID = UUID.randomUUID();

// // Create a game session
// session = new GameSession(
// testUserID,
// "Test Team",
// "Test Session",
// "Mystery",
// 2,
// 4
// );

// // Create game instance
// game = new Game(session);

// // Create game template
// gameTemplate = new GameTemplate("Mystery", "Welcome to the mystery game!");

// // Create test items
// item1 = new Item("Flashlight", "A bright LED flashlight", "Illuminates dark
// areas");
// item2 = new Item("Key", "An old rusty key", "Opens locked doors");

// // Create test challenges with hints and items
// challenge1 = new Challenge(
// "What is 2 + 2?",
// "4",
// "Correct! Moving on..."
// );
// challenge1.addHint("Think about basic addition");
// challenge1.addHint("The answer is less than 5");
// challenge1.addItem(item1);

// challenge2 = new Challenge(
// "What color is the sky?",
// "blue",
// "Great job!"
// );
// challenge2.addHint("Look up on a clear day");

// challenge3 = new Challenge(
// "What is the capital of France?",
// "Paris",
// "Excellent!"
// );
// challenge3.addHint("It's known as the City of Light");
// challenge3.addItem(item2);

// // Add challenges to template
// gameTemplate.addChallenge(challenge1);
// gameTemplate.addChallenge(challenge2);
// gameTemplate.addChallenge(challenge3);

// // Set game template
// game.setGameSet(gameTemplate);
// }

// /**
// * Cleanup after each test.
// */
// @After
// public void cleanup() {
// System.setOut(originalOut);
// // Clean up certificate file if created
// File certFile = new File("Certificate.txt");
// if (certFile.exists()) {
// certFile.delete();
// }
// }

// // ========== Constructor Tests ==========

// /**
// * Tests that Game constructor properly initializes with valid GameSession.
// */
// @Test
// public void testConstructor_ValidSession() {
// GameSession testSession = new GameSession(
// UUID.randomUUID(),
// "Team Alpha",
// "Session 1",
// "Horror",
// 1,
// 2
// );
// Game testGame = new Game(testSession);

// assertEquals("Theme should match session theme", "Horror",
// testGame.getTheme());
// assertEquals("Difficulty should match session difficulty", 1,
// testGame.getDifficulty());
// assertEquals("Player count should match session player count", 2,
// testGame.getPlayerCount());
// assertEquals("Score should match session score", 0, testGame.getScore());
// assertEquals("Index should be 0 initially", 0, testGame.getIndex());
// }

// /**
// * Tests constructor with different difficulty levels.
// */
// @Test
// public void testConstructor_DifferentDifficulties() {
// // Test Easy (1)
// GameSession easySession = new GameSession(testUserID, "Team", "Session",
// "Theme", 1, 1);
// Game easyGame = new Game(easySession);
// assertEquals("Easy difficulty should be 1", 1, easyGame.getDifficulty());

// // Test Medium (2)
// GameSession mediumSession = new GameSession(testUserID, "Team", "Session",
// "Theme", 2, 1);
// Game mediumGame = new Game(mediumSession);
// assertEquals("Medium difficulty should be 2", 2, mediumGame.getDifficulty());

// // Test Hard (3)
// GameSession hardSession = new GameSession(testUserID, "Team", "Session",
// "Theme", 3, 1);
// Game hardGame = new Game(hardSession);
// assertEquals("Hard difficulty should be 3", 3, hardGame.getDifficulty());
// }

// /**
// * Tests constructor with various player counts.
// */
// @Test
// public void testConstructor_VariousPlayerCounts() {
// for (int i = 1; i <= 10; i++) {
// GameSession testSession = new GameSession(testUserID, "Team", "Session",
// "Theme", 1, i);
// Game testGame = new Game(testSession);
// assertEquals("Player count should be " + i, i, testGame.getPlayerCount());
// }
// }

// /**
// * Tests constructor with null theme (edge case).
// */
// @Test
// public void testConstructor_NullTheme() {
// GameSession nullThemeSession = new GameSession(testUserID, "Team", "Session",
// null, 1, 1);
// Game testGame = new Game(nullThemeSession);
// assertNull("Theme should be null", testGame.getTheme());
// }

// /**
// * Tests constructor with empty theme string.
// */
// @Test
// public void testConstructor_EmptyTheme() {
// GameSession emptyThemeSession = new GameSession(testUserID, "Team",
// "Session", "", 1, 1);
// Game testGame = new Game(emptyThemeSession);
// assertEquals("Theme should be empty string", "", testGame.getTheme());
// }

// // ========== getIntro() Tests ==========

// /**
// * Tests getIntro returns an empty string.
// * Based on the implementation, it should return an empty string.
// */
// @Test
// public void testGetIntro_ReturnsEmptyString() {
// String intro = game.getIntro();
// assertNotNull("Intro should not be null", intro);
// assertEquals("Intro should be empty string", "", intro);
// }

// // ========== getTheme() Tests ==========

// /**
// * Tests getTheme returns correct theme.
// */
// @Test
// public void testGetTheme_ValidTheme() {
// assertEquals("Theme should be Mystery", "Mystery", game.getTheme());
// }

// /**
// * Tests getTheme with different themes.
// */
// @Test
// public void testGetTheme_DifferentThemes() {
// String[] themes = {"Horror", "Fantasy", "Sci-Fi", "Historical", "Adventure"};

// for (String theme : themes) {
// GameSession testSession = new GameSession(testUserID, "Team", "Session",
// theme, 1, 1);
// Game testGame = new Game(testSession);
// assertEquals("Theme should match: " + theme, theme, testGame.getTheme());
// }
// }

// /**
// * Tests getTheme returns the same reference.
// */
// @Test
// public void testGetTheme_ReturnsSameReference() {
// String theme1 = game.getTheme();
// String theme2 = game.getTheme();
// assertSame("Should return same reference", theme1, theme2);
// }

// // ========== getDifficulty() Tests ==========

// /**
// * Tests getDifficulty returns correct difficulty.
// */
// @Test
// public void testGetDifficulty_ValidDifficulty() {
// assertEquals("Difficulty should be 2", 2, game.getDifficulty());
// }

// /**
// * Tests getDifficulty with boundary values.
// */
// @Test
// public void testGetDifficulty_BoundaryValues() {
// // Test minimum difficulty
// GameSession minSession = new GameSession(testUserID, "Team", "Session",
// "Theme", 1, 1);
// Game minGame = new Game(minSession);
// assertEquals("Minimum difficulty should be 1", 1, minGame.getDifficulty());

// // Test maximum difficulty
// GameSession maxSession = new GameSession(testUserID, "Team", "Session",
// "Theme", 3, 1);
// Game maxGame = new Game(maxSession);
// assertEquals("Maximum difficulty should be 3", 3, maxGame.getDifficulty());
// }

// /**
// * Tests getDifficulty with invalid values (edge case).
// */
// @Test
// public void testGetDifficulty_InvalidValues() {
// // Test zero difficulty
// GameSession zeroSession = new GameSession(testUserID, "Team", "Session",
// "Theme", 0, 1);
// Game zeroGame = new Game(zeroSession);
// assertEquals("Zero difficulty should be stored", 0,
// zeroGame.getDifficulty());

// // Test negative difficulty
// GameSession negSession = new GameSession(testUserID, "Team", "Session",
// "Theme", -1, 1);
// Game negGame = new Game(negSession);
// assertEquals("Negative difficulty should be stored", -1,
// negGame.getDifficulty());
// }

// // ========== getPlayerCount() Tests ==========

// /**
// * Tests getPlayerCount returns correct count.
// */
// @Test
// public void testGetPlayerCount_ValidCount() {
// assertEquals("Player count should be 4", 4, game.getPlayerCount());
// }

// /**
// * Tests getPlayerCount with single player.
// */
// @Test
// public void testGetPlayerCount_SinglePlayer() {
// GameSession singleSession = new GameSession(testUserID, "Team", "Session",
// "Theme", 1, 1);
// Game singleGame = new Game(singleSession);
// assertEquals("Single player count should be 1", 1,
// singleGame.getPlayerCount());
// }

// /**
// * Tests getPlayerCount with many players.
// */
// @Test
// public void testGetPlayerCount_ManyPlayers() {
// GameSession manySession = new GameSession(testUserID, "Team", "Session",
// "Theme", 1, 100);
// Game manyGame = new Game(manySession);
// assertEquals("Many players count should be 100", 100,
// manyGame.getPlayerCount());
// }

// /**
// * Tests getPlayerCount with zero players (edge case).
// */
// @Test
// public void testGetPlayerCount_ZeroPlayers() {
// GameSession zeroSession = new GameSession(testUserID, "Team", "Session",
// "Theme", 1, 0);
// Game zeroGame = new Game(zeroSession);
// assertEquals("Zero players should be stored", 0, zeroGame.getPlayerCount());
// }

// // ========== getIndex() Tests ==========

// /**
// * Tests getIndex returns 0 initially.
// */
// @Test
// public void testGetIndex_InitiallyZero() {
// assertEquals("Index should be 0 initially", 0, game.getIndex());
// }

// /**
// * Tests getIndex after challengeStart.
// */
// @Test
// public void testGetIndex_AfterChallengeStart() {
// game.challengeStart(0);
// assertEquals("Index should be 0 after challengeStart(0)", 0,
// game.getIndex());

// game.challengeStart(2);
// assertEquals("Index should be 2 after challengeStart(2)", 2,
// game.getIndex());
// }

// /**
// * Tests getIndex with negative starting index.
// */
// @Test
// public void testGetIndex_NegativeStartingIndex() {
// game.challengeStart(-1);
// assertEquals("Index should be -1 after challengeStart(-1)", -1,
// game.getIndex());
// }

// // ========== getChallenges() Tests ==========

// /**
// * Tests getChallenges returns non-null list initially.
// */
// @Test
// public void testGetChallenges_InitiallyNotNull() {
// assertNotNull("Challenges list should not be null", game.getChallenges());
// }

// /**
// * Tests getChallenges initially empty.
// */
// @Test
// public void testGetChallenges_InitiallyEmpty() {
// assertTrue("Challenges list should be empty initially",
// game.getChallenges().isEmpty());
// }

// /**
// * Tests getChallenges after assignChallenges.
// */
// @Test
// public void testGetChallenges_AfterAssignChallenges() {
// game.assignChallenges();

// ArrayList<Challenge> challenges = game.getChallenges();
// assertNotNull("Challenges should not be null", challenges);
// assertEquals("Should have 3 challenges", 3, challenges.size());
// assertEquals("First challenge should match", challenge1, challenges.get(0));
// assertEquals("Second challenge should match", challenge2, challenges.get(1));
// assertEquals("Third challenge should match", challenge3, challenges.get(2));
// }

// /**
// * Tests getChallenges returns same reference (encapsulation check).
// */
// @Test
// public void testGetChallenges_ReturnsSameReference() {
// game.assignChallenges();
// ArrayList<Challenge> challenges1 = game.getChallenges();
// ArrayList<Challenge> challenges2 = game.getChallenges();
// assertSame("Should return same list reference", challenges1, challenges2);
// }

// /**
// * Tests external modification of challenges list.
// */
// @Test
// public void testGetChallenges_ExternalModification() {
// game.assignChallenges();
// ArrayList<Challenge> challenges = game.getChallenges();

// Challenge newChallenge = new Challenge("New?", "New", "New post");
// challenges.add(newChallenge);

// assertEquals("External modification should affect game challenges",
// 4, game.getChallenges().size());
// }

// // ========== setGameSet() Tests ==========

// /**
// * Tests setGameSet with valid GameTemplate.
// */
// @Test
// public void testSetGameSet_ValidTemplate() {
// GameTemplate newTemplate = new GameTemplate("Fantasy", "Fantasy intro");
// game.setGameSet(newTemplate);

// // Verify by assigning challenges and checking
// game.assignChallenges();
// assertTrue("Challenges should be empty for new template",
// game.getChallenges().isEmpty());
// }

// /**
// * Tests setGameSet with null template.
// */
// @Test
// public void testSetGameSet_NullTemplate() {
// game.setGameSet(null);
// // Should not throw exception
// // Subsequent operations may fail, but setter should work
// }

// /**
// * Tests setGameSet replacing existing template.
// */
// @Test
// public void testSetGameSet_ReplaceTemplate() {
// // First template already set in setUp
// game.assignChallenges();
// assertEquals("Should have 3 challenges", 3, game.getChallenges().size());

// // Replace with new template
// GameTemplate newTemplate = new GameTemplate("Horror", "Horror intro");
// Challenge horrorChallenge = new Challenge("Scary?", "Yes", "Boo!");
// newTemplate.addChallenge(horrorChallenge);

// game.setGameSet(newTemplate);
// game.assignChallenges();

// assertEquals("Should have 1 challenge after replacement", 1,
// game.getChallenges().size());
// assertEquals("Challenge should be horror challenge", horrorChallenge,
// game.getChallenges().get(0));
// }

// // ========== challengeStart() Tests ==========

// /**
// * Tests challengeStart with index 0.
// */
// @Test
// public void testChallengeStart_IndexZero() {
// game.challengeStart(0);

// assertEquals("Player index should be 0", 0, game.getIndex());
// assertEquals("Should have 3 challenges", 3, game.getChallenges().size());
// }

// /**
// * Tests challengeStart with positive index.
// */
// @Test
// public void testChallengeStart_PositiveIndex() {
// game.challengeStart(2);

// assertEquals("Player index should be 2", 2, game.getIndex());
// assertEquals("Should have 3 challenges", 3, game.getChallenges().size());
// }

// /**
// * Tests challengeStart with negative index (should print intro).
// */
// @Test
// public void testChallengeStart_NegativeIndex() {
// game.challengeStart(-1);

// assertEquals("Player index should be -1", -1, game.getIndex());
// // Intro should be called, but we can't easily verify console output here
// }

// /**
// * Tests challengeStart with index beyond challenges size.
// */
// @Test
// public void testChallengeStart_IndexBeyondSize() {
// game.challengeStart(10);

// assertEquals("Player index should be 10", 10, game.getIndex());
// assertEquals("Should still have 3 challenges", 3,
// game.getChallenges().size());
// }

// /**
// * Tests challengeStart multiple times.
// */
// @Test
// public void testChallengeStart_MultipleTimes() {
// game.challengeStart(0);
// assertEquals("Index should be 0", 0, game.getIndex());

// game.challengeStart(1);
// assertEquals("Index should be 1 after second call", 1, game.getIndex());

// game.challengeStart(0);
// assertEquals("Index should be 0 after third call", 0, game.getIndex());
// }

// // ========== assignChallenges() Tests ==========

// /**
// * Tests assignChallenges properly assigns from template.
// */
// @Test
// public void testAssignChallenges_ProperlyAssigns() {
// game.assignChallenges();

// ArrayList<Challenge> challenges = game.getChallenges();
// assertEquals("Should have 3 challenges", 3, challenges.size());
// assertEquals("First challenge should match", challenge1, challenges.get(0));
// assertEquals("Second challenge should match", challenge2, challenges.get(1));
// assertEquals("Third challenge should match", challenge3, challenges.get(2));
// }

// /**
// * Tests assignChallenges with empty template.
// */
// @Test
// public void testAssignChallenges_EmptyTemplate() {
// GameTemplate emptyTemplate = new GameTemplate("Empty", "Empty intro");
// game.setGameSet(emptyTemplate);
// game.assignChallenges();

// assertTrue("Challenges should be empty", game.getChallenges().isEmpty());
// }

// /**
// * Tests assignChallenges multiple times.
// */
// @Test
// public void testAssignChallenges_MultipleTimes() {
// game.assignChallenges();
// assertEquals("Should have 3 challenges first time", 3,
// game.getChallenges().size());

// game.assignChallenges();
// assertEquals("Should still have 3 challenges second time", 3,
// game.getChallenges().size());
// }

// /**
// * Tests assignChallenges with null gameSet (edge case).
// */
// @Test(expected = NullPointerException.class)
// public void testAssignChallenges_NullGameSet() {
// Game newGame = new Game(session);
// newGame.setGameSet(null);
// newGame.assignChallenges(); // Should throw NullPointerException
// }

// // ========== hasItems() Tests ==========

// /**
// * Tests hasItems returns false initially.
// */
// @Test
// public void testHasItems_InitiallyFalse() {
// assertFalse("Should have no items initially", game.hasItems());
// }

// /**
// * Tests hasItems returns true after items added.
// */
// @Test
// public void testHasItems_AfterAddingItems() {
// // Manually add items to gameItems list
// game.getChallenges(); // Get reference to internal list
// // Since we can't directly access gameItems, we'll test indirectly

// // Start challenge and simulate item collection
// game.challengeStart(0);
// // Items are added through question answering, which we can't easily simulate
// }

// /**
// * Tests hasItems with empty gameItems list.
// */
// @Test
// public void testHasItems_EmptyList() {
// assertFalse("Should return false for empty items list", game.hasItems());
// }

// // ========== getID() Tests ==========

// /**
// * Tests getID returns null initially (gameID not set in constructor).
// */
// @Test
// public void testGetID_InitiallyNull() {
// UUID id = game.getID();
// assertNull("Game ID should be null initially", id);
// }

// // ========== puzzleCompleted() Tests ==========

// /**
// * Tests puzzleCompleted does nothing (empty method).
// */
// @Test
// public void testPuzzleCompleted_DoesNothing() {
// // Should not throw exception
// game.puzzleCompleted();
// game.puzzleCompleted();
// }

// // ========== getScore() Tests ==========

// /**
// * Tests getScore returns initial score from session.
// */
// @Test
// public void testGetScore_InitialScore() {
// assertEquals("Initial score should be 0", 0, game.getScore());
// }

// /**
// * Tests getScore with non-zero initial score.
// */
// @Test
// public void testGetScore_NonZeroInitialScore() {
// GameSession scoredSession = new GameSession(testUserID, "Team", "Session",
// "Theme", 1, 1);
// scoredSession.setScore(500);
// Game scoredGame = new Game(scoredSession);

// assertEquals("Initial score should be 500", 500, scoredGame.getScore());
// }

// /**
// * Tests getScore with negative score (edge case).
// */
// @Test
// public void testGetScore_NegativeScore() {
// GameSession negSession = new GameSession(testUserID, "Team", "Session",
// "Theme", 1, 1);
// negSession.setScore(-100);
// Game negGame = new Game(negSession);

// assertEquals("Negative score should be stored", -100, negGame.getScore());
// }

// /**
// * Tests getScore with very large score.
// */
// @Test
// public void testGetScore_LargeScore() {
// GameSession largeSession = new GameSession(testUserID, "Team", "Session",
// "Theme", 1, 1);
// largeSession.setScore(1000000);
// Game largeGame = new Game(largeSession);

// assertEquals("Large score should be stored", 1000000, largeGame.getScore());
// }

// // ========== Integration Tests ==========

// /**
// * Tests complete game setup and initialization.
// */
// @Test
// public void testIntegration_CompleteSetup() {
// GameSession testSession = new GameSession(
// testUserID,
// "Integration Team",
// "Integration Session",
// "Adventure",
// 2,
// 3
// );

// Game integrationGame = new Game(testSession);
// GameTemplate template = new GameTemplate("Adventure", "Welcome to
// adventure!");

// Challenge c1 = new Challenge("Q1", "A1", "P1");
// Challenge c2 = new Challenge("Q2", "A2", "P2");
// template.addChallenge(c1);
// template.addChallenge(c2);

// integrationGame.setGameSet(template);
// integrationGame.assignChallenges();
// integrationGame.challengeStart(0);

// assertEquals("Theme should be Adventure", "Adventure",
// integrationGame.getTheme());
// assertEquals("Difficulty should be 2", 2, integrationGame.getDifficulty());
// assertEquals("Player count should be 3", 3,
// integrationGame.getPlayerCount());
// assertEquals("Should have 2 challenges", 2,
// integrationGame.getChallenges().size());
// assertEquals("Index should be 0", 0, integrationGame.getIndex());
// assertEquals("Score should be 0", 0, integrationGame.getScore());
// }

// /**
// * Tests that multiple Game instances are independent.
// */
// @Test
// public void testIntegration_MultipleGamesIndependent() {
// GameSession session1 = new GameSession(testUserID, "Team1", "Session1",
// "Theme1", 1, 2);
// GameSession session2 = new GameSession(testUserID, "Team2", "Session2",
// "Theme2", 3, 4);

// Game game1 = new Game(session1);
// Game game2 = new Game(session2);

// GameTemplate template1 = new GameTemplate("Theme1", "Intro1");
// GameTemplate template2 = new GameTemplate("Theme2", "Intro2");

// game1.setGameSet(template1);
// game2.setGameSet(template2);

// game1.assignChallenges();
// game2.assignChallenges();

// // Verify independence
// assertEquals("Game1 theme should be Theme1", "Theme1", game1.getTheme());
// assertEquals("Game2 theme should be Theme2", "Theme2", game2.getTheme());
// assertEquals("Game1 difficulty should be 1", 1, game1.getDifficulty());
// assertEquals("Game2 difficulty should be 3", 3, game2.getDifficulty());
// assertNotSame("Games should have different challenge lists",
// game1.getChallenges(), game2.getChallenges());
// }

// /**
// * Tests challengeStart with various indices and challenge sizes.
// */
// @Test
// public void testIntegration_ChallengeStartVariousIndices() {
// game.assignChallenges();

// // Start at beginning
// game.challengeStart(0);
// assertEquals("Index should be 0", 0, game.getIndex());

// // Start at middle
// game.challengeStart(1);
// assertEquals("Index should be 1", 1, game.getIndex());

// // Start at end
// game.challengeStart(2);
// assertEquals("Index should be 2", 2, game.getIndex());

// // Start beyond end
// game.challengeStart(5);
// assertEquals("Index should be 5", 5, game.getIndex());
// }

// /**
// * Tests Game with minimal configuration.
// */
// @Test
// public void testIntegration_MinimalConfiguration() {
// GameSession minSession = new GameSession(testUserID, "", "", "", 1, 1);
// Game minGame = new Game(minSession);

// GameTemplate minTemplate = new GameTemplate("", "");
// minGame.setGameSet(minTemplate);
// minGame.assignChallenges();
// minGame.challengeStart(0);

// assertEquals("Theme should be empty", "", minGame.getTheme());
// assertEquals("Difficulty should be 1", 1, minGame.getDifficulty());
// assertEquals("Player count should be 1", 1, minGame.getPlayerCount());
// assertTrue("Challenges should be empty", minGame.getChallenges().isEmpty());
// }

// /**
// * Tests Game with maximum realistic configuration.
// */
// @Test
// public void testIntegration_MaximumConfiguration() {
// GameSession maxSession = new GameSession(
// testUserID,
// "Very Long Team Name That Could Be Used",
// "Very Long Session Name That Could Be Used",
// "Very Long Theme Name That Could Be Used",
// 3,
// 100
// );
// maxSession.setScore(999999);

// Game maxGame = new Game(maxSession);
// GameTemplate maxTemplate = new GameTemplate(
// "Very Long Theme Name That Could Be Used",
// "Very long intro text that could be used in a real game scenario"
// );

// // Add many challenges
// for (int i = 0; i < 50; i++) {
// Challenge c = new Challenge("Question " + i, "Answer " + i, "Post " + i);
// for (int j = 0; j < 5; j++) {
// c.addHint("Hint " + j + " for challenge " + i);
// }
// maxTemplate.addChallenge(c);
// }

// maxGame.setGameSet(maxTemplate);
// maxGame.assignChallenges();
// maxGame.challengeStart(0);

// assertEquals("Player count should be 100", 100, maxGame.getPlayerCount());
// assertEquals("Should have 50 challenges", 50,
// maxGame.getChallenges().size());
// assertEquals("Score should be 999999", 999999, maxGame.getScore());
// }

// /**
// * Tests Game state consistency after multiple operations.
// */
// @Test
// public void testIntegration_StateConsistency() {
// // Initial state
// assertEquals("Initial index should be 0", 0, game.getIndex());
// assertTrue("Initial challenges should be empty",
// game.getChallenges().isEmpty());

// // Assign challenges
// game.assignChallenges();
// assertEquals("Index should still be 0", 0, game.getIndex());
// assertEquals("Should have 3 challenges", 3, game.getChallenges().size());

// // Start challenge
// game.challengeStart(1);
// assertEquals("Index should be 1", 1, game.getIndex());
// assertEquals("Should still have 3 challenges", 3,
// game.getChallenges().size());

// // Reassign challenges
// game.assignChallenges();
// assertEquals("Index should still be 1", 1, game.getIndex());
// assertEquals("Should still have 3 challenges", 3,
// game.getChallenges().size());
// }

// /**
// * Tests Game with challenges containing items and hints.
// */
// @Test
// public void testIntegration_ChallengesWithItemsAndHints() {
// game.assignChallenges();

// ArrayList<Challenge> challenges = game.getChallenges();

// // Verify challenge 1 has hints and items
// assertEquals("Challenge 1 should have 2 hints", 2,
// challenges.get(0).getHints().size());
// assertEquals("Challenge 1 should have 1 item", 1,
// challenges.get(0).getItems().size());

// // Verify challenge 2 has hints but no items
// assertEquals("Challenge 2 should have 1 hint", 1,
// challenges.get(1).getHints().size());
// assertEquals("Challenge 2 should have 0 items", 0,
// challenges.get(1).getItems().size());

// // Verify challenge 3 has hints and items
// assertEquals("Challenge 3 should have 1 hint", 1,
// challenges.get(2).getHints().size());
// assertEquals("Challenge 3 should have 1 item", 1,
// challenges.get(2).getItems().size());
// }

// /**
// * Tests Game behavior with null challenges in template (edge case).
// */
// @Test
// public void testIntegration_NullChallengeInTemplate() {
// GameTemplate nullTemplate = new GameTemplate("Theme", "Intro");
// nullTemplate.getChallenges().add(null);
// nullTemplate.addChallenge(challenge1);

// game.setGameSet(nullTemplate);
// game.assignChallenges();

// assertEquals("Should have 2 elements (including null)", 2,
// game.getChallenges().size());
// assertNull("First element should be null", game.getChallenges().get(0));
// assertEquals("Second element should be challenge1", challenge1,
// game.getChallenges().get(1));
// }

// /**
// * Tests Game with session that has hints already used.
// */
// @Test
// public void testIntegration_SessionWithHintsUsed() {
// GameSession hintSession = new GameSession(testUserID, "Team", "Session",
// "Theme", 2, 2);
// hintSession.setHintsUsed(5);

// Game hintGame = new Game(hintSession);
// GameTemplate template = new GameTemplate("Theme", "Intro");
// hintGame.setGameSet(template);

// assertEquals("Session should have 5 hints used", 5,
// hintSession.getHintsUsed());
// }

// /**
// * Tests toString-like behavior through getters.
// */
// @Test
// public void testIntegration_GameStateSummary() {
// game.assignChallenges();
// game.challengeStart(1);

// String summary = String.format(
// "Theme: %s, Difficulty: %d, Players: %d, Index: %d, Score: %d, Challenges:
// %d",
// game.getTheme(),
// game.getDifficulty(),
// game.getPlayerCount(),
// game.getIndex(),
// game.getScore(),
// game.getChallenges().size()
// );

// assertEquals("Summary should match expected format",
// "Theme: Mystery, Difficulty: 2, Players: 4, Index: 1, Score: 0, Challenges:
// 3",
// summary);
// }
// }
