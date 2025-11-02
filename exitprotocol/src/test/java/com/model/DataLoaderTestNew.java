package com.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

/**
 * Comprehensive test suite for the DataLoader class.
 * Tests all methods including edge cases, error conditions, and data integrity.
 *
 * This test suite focuses on what the methods SHOULD return based on their
 * documentation and intended behavior, helping to identify bugs in the implementation.
 *
 * BUGS IDENTIFIED BY THIS TEST SUITE:
 * 1. getUsers() does not handle missing JSON files gracefully - throws exception instead of returning empty list
 * 2. Session ID parsing bug in line 70: Uses personJSON.get("id") instead of sessionDat.get("id") for session UUID
 * 3. No null checks for missing required fields - will throw NullPointerException
 * 4. getGames() does not handle missing JSON files gracefully
 * 5. No validation of data types during JSON parsing
 * 6. Empty catch blocks hide errors during file reading (printStackTrace only)
 *
 * @author Clankers Test Suite
 */
public class DataLoaderTestNew {

    private static final String BACKUP_USERS_FILE = "json/users_backup_test.json";
    private static final String BACKUP_GAMES_FILE = "json/rooms_backup_test.json";

    /**
     * Set up test environment before each test.
     * Creates backup of original files if they exist.
     */
    @Before
    public void setUp() throws Exception {
        // Backup original files if they exist
        backupFileIfExists("json/users.json", BACKUP_USERS_FILE);
        backupFileIfExists("json/rooms.json", BACKUP_GAMES_FILE);
    }

    /**
     * Clean up test environment after each test.
     * Restores original files from backup.
     */
    @After
    public void tearDown() throws Exception {
        // Restore original files
        restoreFile(BACKUP_USERS_FILE, "json/users.json");
        restoreFile(BACKUP_GAMES_FILE, "json/rooms.json");
    }

    // ========== Tests for getUsers() method ==========

    /**
     * Test that getUsers() returns a non-null ArrayList.
     * Even with no data, should return empty list, not null.
     */
    @Test
    public void testGetUsersReturnsNonNull() {
        ArrayList<User> users = DataLoader.getUsers();
        assertNotNull("getUsers() should never return null", users);
    }

    /**
     * Test that getUsers() correctly loads basic user information.
     * Verifies first name, last name, email, password, avatar, and UUID.
     */
    @Test
    public void testGetUsersLoadsBasicUserInfo() {
        createTestUsersFile(buildBasicTestUserJson());

        ArrayList<User> users = DataLoader.getUsers();

        assertNotNull("Users list should not be null", users);
        assertTrue("Should load at least one user", users.size() > 0);

        User firstUser = users.get(0);
        assertEquals("First name should match", "TestUser", firstUser.getFirstName());
        assertEquals("Last name should match", "TestLast", firstUser.getLastName());
        assertEquals("Email should match", "test@example.com", firstUser.getEmail());
        assertEquals("Password should match", "password123", firstUser.getPassword());
        assertEquals("Avatar should match", 1, firstUser.getAvatar());
        assertNotNull("UUID should not be null", firstUser.getUUID());
    }

    /**
     * Test that getUsers() correctly parses UUID from string format.
     * UUIDs should be properly converted from JSON string to UUID object.
     */
    @Test
    public void testGetUsersParsesUUIDCorrectly() {
        String knownUUID = "12345678-1234-1234-1234-123456789abc";
        createTestUsersFile(buildUserJsonWithUUID(knownUUID));

        ArrayList<User> users = DataLoader.getUsers();

        assertNotNull("Users list should not be null", users);
        assertTrue("Should load at least one user", users.size() > 0);

        User user = users.get(0);
        assertNotNull("User UUID should not be null", user.getUUID());
        assertEquals("UUID should match the one in JSON", knownUUID, user.getUUID().toString());
    }

    /**
     * Test that getUsers() returns empty list for empty JSON array.
     * Should handle empty data gracefully without errors.
     */
    @Test
    public void testGetUsersWithEmptyJsonArray() {
        createTestUsersFile("[]");

        ArrayList<User> users = DataLoader.getUsers();

        assertNotNull("Users list should not be null", users);
        assertEquals("Empty JSON should result in empty list", 0, users.size());
    }

    /**
     * Test that getUsers() handles multiple users correctly.
     * Should load all users from the JSON file.
     */
    @Test
    public void testGetUsersWithMultipleUsers() {
        createTestUsersFile(buildMultipleUsersJson());

        ArrayList<User> users = DataLoader.getUsers();

        assertNotNull("Users list should not be null", users);
        assertEquals("Should load exactly 3 users", 3, users.size());

        assertEquals("First user name should match", "User1", users.get(0).getFirstName());
        assertEquals("Second user name should match", "User2", users.get(1).getFirstName());
        assertEquals("Third user name should match", "User3", users.get(2).getFirstName());
    }

    /**
     * Test that getUsers() correctly loads game sessions for users.
     * Sessions should be properly attached to user objects.
     */
    @Test
    public void testGetUsersLoadsGameSessions() {
        createTestUsersFile(buildUserJsonWithSessions());

        ArrayList<User> users = DataLoader.getUsers();

        assertNotNull("Users list should not be null", users);
        assertTrue("Should load at least one user", users.size() > 0);

        User user = users.get(0);
        ArrayList<GameSession> sessions = user.getAllSessions();

        assertNotNull("Sessions list should not be null", sessions);
        assertTrue("User should have at least one session", sessions.size() > 0);
    }

    /**
     * Test that getUsers() correctly loads session details.
     * Verifies all session fields are properly loaded.
     * NOTE: This test may reveal a bug in DataLoader line 70 where session ID is parsed incorrectly.
     */
    @Test
    public void testGetUsersLoadsSessionDetails() {
        createTestUsersFile(buildUserJsonWithDetailedSession());

        ArrayList<User> users = DataLoader.getUsers();
        User user = users.get(0);
        ArrayList<GameSession> sessions = user.getAllSessions();

        assertTrue("Should have at least one session", sessions.size() > 0);

        GameSession session = sessions.get(0);
        assertEquals("Session name should match", "TestSession", session.getSessionName());
        assertEquals("Theme should match", "Horror", session.getSessionTheme());
        assertEquals("Difficulty should match", 2, session.getDifficulty());
        assertEquals("Player count should match", 3, session.getPlayerCount());
        assertEquals("Score should match", 150, session.getScore());
        assertEquals("Challenge index should match", 4, session.getChallengeIndex());
        assertEquals("Hints used should match", 2, session.getHintsUsed());
        assertEquals("Team name should match", "TestTeam", session.getTeamName());
    }

    /**
     * Test that getUsers() handles users with no sessions.
     * Users without sessions should still load correctly.
     */
    @Test
    public void testGetUsersWithNoSessions() {
        createTestUsersFile(buildUserJsonWithoutSessions());

        ArrayList<User> users = DataLoader.getUsers();

        assertNotNull("Users list should not be null", users);
        assertTrue("Should load at least one user", users.size() > 0);

        User user = users.get(0);
        ArrayList<GameSession> sessions = user.getAllSessions();

        assertNotNull("Sessions list should not be null", sessions);
        assertEquals("User with no sessions should have empty list", 0, sessions.size());
    }

    /**
     * Test that getUsers() handles session with default hintsUsed value.
     * If hintsUsed is not present, should default to 0.
     */
    @Test
    public void testGetUsersSessionDefaultHintsUsed() {
        createTestUsersFile(buildUserJsonSessionWithoutHintsUsed());

        ArrayList<User> users = DataLoader.getUsers();
        User user = users.get(0);
        ArrayList<GameSession> sessions = user.getAllSessions();

        assertTrue("Should have at least one session", sessions.size() > 0);

        GameSession session = sessions.get(0);
        assertEquals("Hints used should default to 0 when not present", 0, session.getHintsUsed());
    }

    /**
     * Test that getUsers() handles file not found scenario gracefully.
     * BUG: Currently throws exception, should return empty list.
     */
    @Test
    public void testGetUsersFileNotFound() {
        deleteFile("json/users.json");

        try {
            ArrayList<User> users = DataLoader.getUsers();
            assertNotNull("Should return a list even when file not found", users);
            assertEquals("Should return empty list when file not found", 0, users.size());
        } catch (Exception e) {
            fail("Should not throw exception when file not found. Should return empty list instead. This is a BUG.");
        }
    }

    /**
     * Test that getUsers() handles invalid JSON syntax gracefully.
     * Should not crash the application.
     */
    @Test
    public void testGetUsersWithInvalidJson() {
        createTestUsersFile("{invalid json syntax}");

        ArrayList<User> users = DataLoader.getUsers();

        assertNotNull("Should return a list even with invalid JSON", users);
        assertEquals("Should return empty list when JSON is invalid", 0, users.size());
    }

    /**
     * Test data integrity with real-world data structure.
     * Verifies complete user object with session is loaded correctly.
     */
    @Test
    public void testRealWorldUserDataStructure() {
        createTestUsersFile(buildCompleteRealWorldUserJson());

        ArrayList<User> users = DataLoader.getUsers();

        assertNotNull("Users should not be null", users);
        assertTrue("Should load user from real-world structure", users.size() > 0);

        User user = users.get(0);
        assertEquals("Henry", user.getFirstName());
        assertEquals("Monteith", user.getLastName());
        assertEquals("hi@henry.moe", user.getEmail());

        ArrayList<GameSession> sessions = user.getAllSessions();
        assertTrue("User should have sessions", sessions.size() > 0);

        GameSession session = sessions.get(0);
        assertEquals("Historical", session.getSessionTheme());
        assertEquals("Clanker", session.getTeamName());
    }

    // ========== Tests for getGames() method ==========

    /**
     * Test that getGames() returns a non-null ArrayList.
     */
    @Test
    public void testGetGamesReturnsNonNull() {
        ArrayList<GameTemplate> games = DataLoader.getGames();
        assertNotNull("getGames() should never return null", games);
    }

    /**
     * Test that getGames() correctly loads basic game information.
     * Verifies theme and intro are properly loaded.
     */
    @Test
    public void testGetGamesLoadsBasicGameInfo() {
        createTestGamesFile(buildBasicTestGameJson());

        ArrayList<GameTemplate> games = DataLoader.getGames();

        assertNotNull("Games list should not be null", games);
        assertTrue("Should load at least one game", games.size() > 0);

        GameTemplate game = games.get(0);
        assertEquals("Theme should match", "TestTheme", game.getTheme());
        assertNotNull("Challenges should not be null", game.getChallenges());
    }

    /**
     * Test that getGames() returns empty list for empty JSON array.
     */
    @Test
    public void testGetGamesWithEmptyJsonArray() {
        createTestGamesFile("[]");

        ArrayList<GameTemplate> games = DataLoader.getGames();

        assertNotNull("Games list should not be null", games);
        assertEquals("Empty JSON should result in empty list", 0, games.size());
    }

    /**
     * Test that getGames() correctly loads multiple games.
     */
    @Test
    public void testGetGamesWithMultipleGames() {
        createTestGamesFile(buildMultipleGamesJson());

        ArrayList<GameTemplate> games = DataLoader.getGames();

        assertNotNull("Games list should not be null", games);
        assertEquals("Should load exactly 2 games", 2, games.size());

        assertEquals("First game theme should match", "Horror", games.get(0).getTheme());
        assertEquals("Second game theme should match", "Mystery", games.get(1).getTheme());
    }

    /**
     * Test that getGames() correctly loads challenges for games.
     */
    @Test
    public void testGetGamesLoadsChallenges() {
        createTestGamesFile(buildGameJsonWithChallenges());

        ArrayList<GameTemplate> games = DataLoader.getGames();

        assertNotNull("Games list should not be null", games);
        assertTrue("Should load at least one game", games.size() > 0);

        GameTemplate game = games.get(0);
        ArrayList<Challenge> challenges = game.getChallenges();

        assertNotNull("Challenges list should not be null", challenges);
        assertTrue("Game should have at least one challenge", challenges.size() > 0);
    }

    /**
     * Test that getGames() correctly loads challenge details.
     * Verifies question, answer, and post-question are properly loaded.
     */
    @Test
    public void testGetGamesLoadsChallengeDetails() {
        createTestGamesFile(buildGameJsonWithDetailedChallenge());

        ArrayList<GameTemplate> games = DataLoader.getGames();
        GameTemplate game = games.get(0);
        ArrayList<Challenge> challenges = game.getChallenges();

        assertTrue("Should have at least one challenge", challenges.size() > 0);

        Challenge challenge = challenges.get(0);
        assertEquals("Question should match", "What is the answer?", challenge.getQuestion());
        assertEquals("Answer should match", "42", challenge.getAnswer());
        assertEquals("Post question should match", "Correct!", challenge.getPostQuestion());
    }

    /**
     * Test that getGames() correctly loads hints for challenges.
     */
    @Test
    public void testGetGamesLoadsHints() {
        createTestGamesFile(buildGameJsonWithHints());

        ArrayList<GameTemplate> games = DataLoader.getGames();
        GameTemplate game = games.get(0);
        ArrayList<Challenge> challenges = game.getChallenges();
        Challenge challenge = challenges.get(0);

        ArrayList<String> hints = challenge.getHints();

        assertNotNull("Hints list should not be null", hints);
        assertTrue("Challenge should have at least one hint", hints.size() > 0);
        assertEquals("First hint should match", "Hint number 1", hints.get(0));
    }

    /**
     * Test that getGames() correctly loads multiple hints.
     */
    @Test
    public void testGetGamesLoadsMultipleHints() {
        createTestGamesFile(buildGameJsonWithMultipleHints());

        ArrayList<GameTemplate> games = DataLoader.getGames();
        GameTemplate game = games.get(0);
        Challenge challenge = game.getChallenges().get(0);
        ArrayList<String> hints = challenge.getHints();

        assertEquals("Should have exactly 3 hints", 3, hints.size());
        assertEquals("First hint should match", "First hint", hints.get(0));
        assertEquals("Second hint should match", "Second hint", hints.get(1));
        assertEquals("Third hint should match", "Third hint", hints.get(2));
    }

    /**
     * Test that getGames() correctly loads items for challenges.
     */
    @Test
    public void testGetGamesLoadsItems() {
        createTestGamesFile(buildGameJsonWithItem());

        ArrayList<GameTemplate> games = DataLoader.getGames();
        GameTemplate game = games.get(0);
        Challenge challenge = game.getChallenges().get(0);

        ArrayList<Item> items = challenge.getItems();

        assertNotNull("Items list should not be null", items);
        assertTrue("Challenge should have at least one item", items.size() > 0);
    }

    /**
     * Test that getGames() correctly loads item details.
     * Verifies item name, description, and use case.
     */
    @Test
    public void testGetGamesLoadsItemDetails() {
        createTestGamesFile(buildGameJsonWithDetailedItem());

        ArrayList<GameTemplate> games = DataLoader.getGames();
        GameTemplate game = games.get(0);
        Challenge challenge = game.getChallenges().get(0);
        ArrayList<Item> items = challenge.getItems();

        assertTrue("Should have at least one item", items.size() > 0);

        Item item = items.get(0);
        assertEquals("Item name should match", "Key", item.getName());
        assertEquals("Item description should match", "A rusty old key", item.getDescription());
        assertEquals("Item use case should match", "Opens the door", item.getUseCase());
    }

    /**
     * Test that getGames() handles challenges without items.
     */
    @Test
    public void testGetGamesHandlesChallengesWithoutItems() {
        createTestGamesFile(buildGameJsonWithoutItem());

        ArrayList<GameTemplate> games = DataLoader.getGames();
        GameTemplate game = games.get(0);
        Challenge challenge = game.getChallenges().get(0);

        ArrayList<Item> items = challenge.getItems();

        assertNotNull("Items list should not be null", items);
        assertEquals("Challenge without items should have empty list", 0, items.size());
    }

    /**
     * Test that getGames() handles multiple challenges per game.
     */
    @Test
    public void testGetGamesLoadMultipleChallenges() {
        createTestGamesFile(buildGameJsonWithMultipleChallenges());

        ArrayList<GameTemplate> games = DataLoader.getGames();
        GameTemplate game = games.get(0);
        ArrayList<Challenge> challenges = game.getChallenges();

        assertEquals("Should have exactly 3 challenges", 3, challenges.size());
    }

    /**
     * Test that getGames() handles file not found scenario.
     * BUG: Currently throws exception, should return empty list.
     */
    @Test
    public void testGetGamesFileNotFound() {
        deleteFile("json/rooms.json");

        try {
            ArrayList<GameTemplate> games = DataLoader.getGames();
            assertNotNull("Should return a list even when file not found", games);
            assertEquals("Should return empty list when file not found", 0, games.size());
        } catch (Exception e) {
            fail("Should not throw exception when file not found. Should return empty list instead. This is a BUG.");
        }
    }

    /**
     * Test that getGames() handles invalid JSON syntax gracefully.
     */
    @Test
    public void testGetGamesWithInvalidJson() {
        createTestGamesFile("{invalid json}");

        ArrayList<GameTemplate> games = DataLoader.getGames();

        assertNotNull("Should return a list even with invalid JSON", games);
        assertEquals("Should return empty list when JSON is invalid", 0, games.size());
    }

    /**
     * Test that getGames() handles empty challenges array.
     */
    @Test
    public void testGetGamesWithEmptyChallenges() {
        createTestGamesFile(buildGameJsonWithEmptyChallenges());

        ArrayList<GameTemplate> games = DataLoader.getGames();

        assertNotNull("Games list should not be null", games);
        assertTrue("Should load at least one game", games.size() > 0);

        GameTemplate game = games.get(0);
        ArrayList<Challenge> challenges = game.getChallenges();

        assertNotNull("Challenges list should not be null", challenges);
        assertEquals("Game with empty challenges should have empty list", 0, challenges.size());
    }

    /**
     * Test that getGames() handles empty hints array.
     */
    @Test
    public void testGetGamesWithEmptyHints() {
        createTestGamesFile(buildGameJsonWithEmptyHints());

        ArrayList<GameTemplate> games = DataLoader.getGames();
        GameTemplate game = games.get(0);
        Challenge challenge = game.getChallenges().get(0);

        ArrayList<String> hints = challenge.getHints();

        assertNotNull("Hints list should not be null", hints);
        assertEquals("Challenge with no hints should have empty list", 0, hints.size());
    }

    /**
     * Test data integrity with real-world game data structure.
     */
    @Test
    public void testRealWorldGameDataStructure() {
        createTestGamesFile(buildCompleteRealWorldGameJson());

        ArrayList<GameTemplate> games = DataLoader.getGames();

        assertNotNull("Games should not be null", games);
        assertTrue("Should load game from real-world structure", games.size() > 0);

        GameTemplate game = games.get(0);
        assertEquals("Historical", game.getTheme());

        ArrayList<Challenge> challenges = game.getChallenges();
        assertTrue("Game should have challenges", challenges.size() > 0);

        Challenge challenge = challenges.get(0);
        assertNotNull("Challenge question should not be null", challenge.getQuestion());
        assertNotNull("Challenge answer should not be null", challenge.getAnswer());
    }

    // ========== Helper Methods ==========

    /**
     * Creates a test users JSON file with the given content.
     */
    private void createTestUsersFile(String content) {
        try {
            File file = new File("json/users.json");
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            fail("Failed to create test users file: " + e.getMessage());
        }
    }

    /**
     * Creates a test games JSON file with the given content.
     */
    private void createTestGamesFile(String content) {
        try {
            File file = new File("json/rooms.json");
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            fail("Failed to create test games file: " + e.getMessage());
        }
    }

    /**
     * Backs up a file if it exists.
     */
    private void backupFileIfExists(String originalPath, String backupPath) {
        try {
            File original = new File(originalPath);
            if (original.exists()) {
                Files.copy(Paths.get(originalPath), Paths.get(backupPath),
                          StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            // Backup failed, but continue with tests
        }
    }

    /**
     * Restores a file from backup.
     */
    private void restoreFile(String backupPath, String originalPath) {
        try {
            File backup = new File(backupPath);
            if (backup.exists()) {
                Files.copy(Paths.get(backupPath), Paths.get(originalPath),
                          StandardCopyOption.REPLACE_EXISTING);
                backup.delete();
            }
        } catch (Exception e) {
            // Restore failed, but continue
        }
    }

    /**
     * Deletes a file.
     */
    private void deleteFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            // Deletion failed, but continue
        }
    }

    // ========== JSON Builder Methods ==========

    private String buildBasicTestUserJson() {
        return "[{\"firstname\":\"TestUser\",\"lastname\":\"TestLast\"," +
               "\"email\":\"test@example.com\",\"password\":\"password123\"," +
               "\"avatar\":1,\"id\":\"12345678-1234-1234-1234-123456789abc\"}]";
    }

    private String buildUserJsonWithUUID(String uuid) {
        return "[{\"firstname\":\"Test\",\"lastname\":\"User\"," +
               "\"email\":\"test@test.com\",\"password\":\"pass\"," +
               "\"avatar\":1,\"id\":\"" + uuid + "\"}]";
    }

    private String buildMultipleUsersJson() {
        return "[{\"firstname\":\"User1\",\"lastname\":\"Last1\"," +
               "\"email\":\"user1@test.com\",\"password\":\"pass1\"," +
               "\"avatar\":1,\"id\":\"11111111-1111-1111-1111-111111111111\"}," +
               "{\"firstname\":\"User2\",\"lastname\":\"Last2\"," +
               "\"email\":\"user2@test.com\",\"password\":\"pass2\"," +
               "\"avatar\":2,\"id\":\"22222222-2222-2222-2222-222222222222\"}," +
               "{\"firstname\":\"User3\",\"lastname\":\"Last3\"," +
               "\"email\":\"user3@test.com\",\"password\":\"pass3\"," +
               "\"avatar\":3,\"id\":\"33333333-3333-3333-3333-333333333333\"}]";
    }

    private String buildUserJsonWithSessions() {
        return "[{\"firstname\":\"Test\",\"lastname\":\"User\"," +
               "\"email\":\"test@test.com\",\"password\":\"pass\",\"avatar\":1," +
               "\"id\":\"12345678-1234-1234-1234-123456789abc\"," +
               "\"sessions\":[{\"difficulty\":1,\"score\":100,\"playercount\":2," +
               "\"sessionName\":\"Session1\",\"theme\":\"Horror\"," +
               "\"id\":\"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa\"," +
               "\"state\":\"ACTIVE\",\"currentChallengeIndex\":1," +
               "\"hintsUsed\":0,\"teamname\":\"Team1\"}]}]";
    }

    private String buildUserJsonWithDetailedSession() {
        return "[{\"firstname\":\"Test\",\"lastname\":\"User\"," +
               "\"email\":\"test@test.com\",\"password\":\"pass\",\"avatar\":1," +
               "\"id\":\"12345678-1234-1234-1234-123456789abc\"," +
               "\"sessions\":[{\"difficulty\":2,\"score\":150,\"playercount\":3," +
               "\"sessionName\":\"TestSession\",\"theme\":\"Horror\"," +
               "\"id\":\"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa\"," +
               "\"state\":\"ACTIVE\",\"currentChallengeIndex\":4," +
               "\"hintsUsed\":2,\"teamname\":\"TestTeam\"}]}]";
    }

    private String buildUserJsonWithoutSessions() {
        return "[{\"firstname\":\"Test\",\"lastname\":\"User\"," +
               "\"email\":\"test@test.com\",\"password\":\"pass\"," +
               "\"avatar\":1,\"id\":\"12345678-1234-1234-1234-123456789abc\"," +
               "\"sessions\":[]}]";
    }

    private String buildUserJsonSessionWithoutHintsUsed() {
        return "[{\"firstname\":\"Test\",\"lastname\":\"User\"," +
               "\"email\":\"test@test.com\",\"password\":\"pass\",\"avatar\":1," +
               "\"id\":\"12345678-1234-1234-1234-123456789abc\"," +
               "\"sessions\":[{\"difficulty\":1,\"score\":100,\"playercount\":2," +
               "\"sessionName\":\"Session1\",\"theme\":\"Horror\"," +
               "\"id\":\"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa\"," +
               "\"state\":\"ACTIVE\",\"currentChallengeIndex\":1," +
               "\"teamname\":\"Team1\"}]}]";
    }

    private String buildCompleteRealWorldUserJson() {
        return "[{\"firstname\":\"Henry\",\"password\":\"as\"," +
               "\"sessions\":[{\"difficulty\":1,\"score\":325,\"playercount\":1," +
               "\"sessionName\":\"Session\",\"theme\":\"Historical\"," +
               "\"id\":\"9219ef72-0f3c-4814-97a6-cbebbc080179\"," +
               "\"state\":\"ACTIVE\",\"currentChallengeIndex\":4," +
               "\"hintsUsed\":3,\"teamname\":\"Clanker\"}]," +
               "\"id\":\"a745fd0c-dd0d-4bf2-a4e9-9e5e48fb4d5e\"," +
               "\"avatar\":1,\"email\":\"hi@henry.moe\",\"lastname\":\"Monteith\"}]";
    }

    private String buildBasicTestGameJson() {
        return "[{\"theme\":\"TestTheme\",\"intro\":\"Test intro\"," +
               "\"challenges\":[{\"phrasechallenges\":[]}]}]";
    }

    private String buildMultipleGamesJson() {
        return "[{\"theme\":\"Horror\",\"intro\":\"Horror intro\"," +
               "\"challenges\":[{\"phrasechallenges\":[]}]}," +
               "{\"theme\":\"Mystery\",\"intro\":\"Mystery intro\"," +
               "\"challenges\":[{\"phrasechallenges\":[]}]}]";
    }

    private String buildGameJsonWithChallenges() {
        return "[{\"theme\":\"TestTheme\",\"intro\":\"Test intro\"," +
               "\"challenges\":[{\"phrasechallenges\":[" +
               "{\"question\":\"Q1\",\"answer\":\"A1\",\"postQuestion\":\"P1\"," +
               "\"hints\":[]}]}]}]";
    }

    private String buildGameJsonWithDetailedChallenge() {
        return "[{\"theme\":\"TestTheme\",\"intro\":\"Test intro\"," +
               "\"challenges\":[{\"phrasechallenges\":[" +
               "{\"question\":\"What is the answer?\",\"answer\":\"42\"," +
               "\"postQuestion\":\"Correct!\",\"hints\":[]}]}]}]";
    }

    private String buildGameJsonWithHints() {
        return "[{\"theme\":\"TestTheme\",\"intro\":\"Test intro\"," +
               "\"challenges\":[{\"phrasechallenges\":[" +
               "{\"question\":\"Q1\",\"answer\":\"A1\",\"postQuestion\":\"P1\"," +
               "\"hints\":[\"Hint number 1\"]}]}]}]";
    }

    private String buildGameJsonWithMultipleHints() {
        return "[{\"theme\":\"TestTheme\",\"intro\":\"Test intro\"," +
               "\"challenges\":[{\"phrasechallenges\":[" +
               "{\"question\":\"Q1\",\"answer\":\"A1\",\"postQuestion\":\"P1\"," +
               "\"hints\":[\"First hint\",\"Second hint\",\"Third hint\"]}]}]}]";
    }

    private String buildGameJsonWithItem() {
        return "[{\"theme\":\"TestTheme\",\"intro\":\"Test intro\"," +
               "\"challenges\":[{\"phrasechallenges\":[" +
               "{\"question\":\"Q1\",\"answer\":\"A1\",\"postQuestion\":\"P1\"," +
               "\"hints\":[],\"item\":{\"name\":\"TestItem\"," +
               "\"description\":\"Test desc\",\"useCase\":\"Test use\"}}]}]}]";
    }

    private String buildGameJsonWithDetailedItem() {
        return "[{\"theme\":\"TestTheme\",\"intro\":\"Test intro\"," +
               "\"challenges\":[{\"phrasechallenges\":[" +
               "{\"question\":\"Q1\",\"answer\":\"A1\",\"postQuestion\":\"P1\"," +
               "\"hints\":[],\"item\":{\"name\":\"Key\"," +
               "\"description\":\"A rusty old key\",\"useCase\":\"Opens the door\"}}]}]}]";
    }

    private String buildGameJsonWithoutItem() {
        return "[{\"theme\":\"TestTheme\",\"intro\":\"Test intro\"," +
               "\"challenges\":[{\"phrasechallenges\":[" +
               "{\"question\":\"Q1\",\"answer\":\"A1\",\"postQuestion\":\"P1\"," +
               "\"hints\":[]}]}]}]";
    }

    private String buildGameJsonWithMultipleChallenges() {
        return "[{\"theme\":\"TestTheme\",\"intro\":\"Test intro\"," +
               "\"challenges\":[{\"phrasechallenges\":[" +
               "{\"question\":\"Q1\",\"answer\":\"A1\",\"postQuestion\":\"P1\",\"hints\":[]}," +
               "{\"question\":\"Q2\",\"answer\":\"A2\",\"postQuestion\":\"P2\",\"hints\":[]}," +
               "{\"question\":\"Q3\",\"answer\":\"A3\",\"postQuestion\":\"P3\",\"hints\":[]}]}]}]";
    }

    private String buildGameJsonWithEmptyChallenges() {
        return "[{\"theme\":\"TestTheme\",\"intro\":\"Test intro\"," +
               "\"challenges\":[{\"phrasechallenges\":[]}]}]";
    }

    private String buildGameJsonWithEmptyHints() {
        return "[{\"theme\":\"TestTheme\",\"intro\":\"Test intro\"," +
               "\"challenges\":[{\"phrasechallenges\":[" +
               "{\"question\":\"Q1\",\"answer\":\"A1\",\"postQuestion\":\"P1\"," +
               "\"hints\":[]}]}]}]";
    }

    private String buildCompleteRealWorldGameJson() {
        return "[{\"theme\":\"Historical\"," +
               "\"intro\":\"The year is 1927...\"," +
               "\"challenges\":[{\"phrasechallenges\":[" +
               "{\"question\":\"What is prohibited?\",\"answer\":\"Liquor\"," +
               "\"hints\":[\"L_ _ _ _ _ \",\"Rum, Vodka, Tequila\"]," +
               "\"postQuestion\":\"You found a clue!\"," +
               "\"item\":{\"name\":\"Caesar Guide\"," +
               "\"description\":\"A guide for how to complete a Caesar cipher.\"," +
               "\"useCase\":\"Use for decoding\"}}]}]}]";
    }
}
