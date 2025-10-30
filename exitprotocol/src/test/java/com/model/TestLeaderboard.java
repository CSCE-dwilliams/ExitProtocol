package com.model;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.UUID;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Comprehensive JUnit test suite for the Leaderboard class.
 * Tests all public methods, edge cases, and integration with UserList, DataLoader, and DataWriter.
 *
 * @author Test Suite
 */
public class TestLeaderboard {

    private Leaderboard leaderboard;
    private UserList userList;
    private static final String TEST_USER_FILE = "json/test_users.json";
    private static final String BACKUP_USER_FILE = "json/users_backup.json";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    /**
     * Set up test environment before all tests run.
     * Creates backup of original user data and sets up test data.
     */
    @BeforeClass
    public static void setUpClass() {
        // Create backup of original users.json if it exists
        File originalFile = new File("json/users.json");
        File backupFile = new File(BACKUP_USER_FILE);

        if (originalFile.exists()) {
            originalFile.renameTo(backupFile);
        }

        // Create test directory if it doesn't exist
        File jsonDir = new File("json");
        if (!jsonDir.exists()) {
            jsonDir.mkdirs();
        }
    }

    /**
     * Restore original user data after all tests complete.
     */
    @AfterClass
    public static void tearDownClass() {
        // Clean up test file
        File testFile = new File("json/users.json");
        if (testFile.exists()) {
            testFile.delete();
        }

        // Restore backup if it exists
        File backupFile = new File(BACKUP_USER_FILE);
        File originalFile = new File("json/users.json");

        if (backupFile.exists()) {
            backupFile.renameTo(originalFile);
        }
    }

    /**
     * Set up test environment before each test.
     * Creates fresh test data and initializes leaderboard.
     */
    @Before
    public void setUp() {
        // Redirect System.out for testing display methods
        System.setOut(new PrintStream(outContent));

        // Create test JSON data
        createTestUserData();

        // Reset UserList singleton state
        userList = UserList.getInstance();
        userList.getUsers().clear();

        // Create fresh leaderboard instance
        leaderboard = new Leaderboard();
    }

    /**
     * Clean up after each test.
     */
    @After
    public void tearDown() {
        // Restore System.out
        System.setOut(originalOut);

        // Clear output stream
        outContent.reset();

        // Clean up UserList
        if (userList != null) {
            userList.getUsers().clear();
        }
    }

    /**
     * Creates test user data in JSON format for testing.
     */
    private void createTestUserData() {
        String testJson = """
        [
            {
                "firstname": "Alice",
                "lastname": "Johnson",
                "id": "550e8400-e29b-41d4-a716-446655440001",
                "email": "alice@test.com",
                "password": "password123",
                "avatar": 1,
                "sessions": [
                    {
                        "id": "550e8400-e29b-41d4-a716-446655440011",
                        "teamname": "AliceTeam",
                        "sessionName": "Session1",
                        "theme": "Horror",
                        "difficulty": 2,
                        "playercount": 1,
                        "score": 150,
                        "currentChallengeIndex": 3,
                        "state": "ACTIVE",
                        "hintsUsed": 2
                    },
                    {
                        "id": "550e8400-e29b-41d4-a716-446655440012",
                        "teamname": "AliceTeam2",
                        "sessionName": "Session2",
                        "theme": "Fantasy",
                        "difficulty": 3,
                        "playercount": 2,
                        "score": 200,
                        "currentChallengeIndex": 5,
                        "state": "COMPLETED",
                        "hintsUsed": 1
                    }
                ]
            },
            {
                "firstname": "Bob",
                "lastname": "Smith",
                "id": "550e8400-e29b-41d4-a716-446655440002",
                "email": "bob@test.com",
                "password": "password456",
                "avatar": 2,
                "sessions": [
                    {
                        "id": "550e8400-e29b-41d4-a716-446655440021",
                        "teamname": "BobTeam",
                        "sessionName": "BobSession",
                        "theme": "Mystery",
                        "difficulty": 1,
                        "playercount": 1,
                        "score": 100,
                        "currentChallengeIndex": 2,
                        "state": "ACTIVE",
                        "hintsUsed": 0
                    }
                ]
            },
            {
                "firstname": "Charlie",
                "lastname": "Brown",
                "id": "550e8400-e29b-41d4-a716-446655440003",
                "email": "charlie@test.com",
                "password": "password789",
                "avatar": 3,
                "sessions": [
                    {
                        "id": "550e8400-e29b-41d4-a716-446655440031",
                        "teamname": "CharlieTeam",
                        "sessionName": "CharlieSession",
                        "theme": "Historical",
                        "difficulty": 3,
                        "playercount": 4,
                        "score": 500,
                        "currentChallengeIndex": 8,
                        "state": "COMPLETED",
                        "hintsUsed": 3
                    }
                ]
            },
            {
                "firstname": "Diana",
                "lastname": "Prince",
                "id": "550e8400-e29b-41d4-a716-446655440004",
                "email": "diana@test.com",
                "password": "password000",
                "avatar": 4,
                "sessions": []
            }
        ]
        """;

        try (FileWriter writer = new FileWriter("json/users.json")) {
            writer.write(testJson);
        } catch (IOException e) {
            fail("Failed to create test user data: " + e.getMessage());
        }
    }

    // ============ CONSTRUCTOR TESTS ============

    /**
     * Test that Leaderboard constructor properly initializes and loads user data.
     */
    @Test
    public void testConstructor() {
        assertNotNull("Leaderboard should be created", leaderboard);
        assertNotNull("UserList should be initialized", leaderboard.getUsers());
        assertTrue("Should load test users", leaderboard.getUsers().size() > 0);
        assertEquals("Should load exactly 4 test users", 4, leaderboard.getUsers().size());
    }

    /**
     * Test constructor with empty user data.
     */
    @Test
    public void testConstructorWithEmptyData() {
        // Create empty JSON file
        try (FileWriter writer = new FileWriter("json/users.json")) {
            writer.write("[]");
        } catch (IOException e) {
            fail("Failed to create empty test data");
        }

        Leaderboard emptyLeaderboard = new Leaderboard();
        assertNotNull("Leaderboard should be created even with empty data", emptyLeaderboard);
        assertEquals("Should have no users", 0, emptyLeaderboard.getUsers().size());
    }

    // ============ RELOAD USERS TESTS ============

    /**
     * Test reloadUsers method functionality.
     */
    @Test
    public void testReloadUsers() {
        int initialCount = leaderboard.getUsers().size();
        assertEquals("Should have 4 initial users", 4, initialCount);

        // Manually clear users to simulate stale data
        userList.getUsers().clear();
        assertEquals("Users should be cleared", 0, userList.getUsers().size());

        // Reload users
        leaderboard.reloadUsers();
        assertEquals("Should reload all users", 4, leaderboard.getUsers().size());
    }

    /**
     * Test reloadUsers with updated data.
     */
    @Test
    public void testReloadUsersWithUpdatedData() {
        assertEquals("Should start with 4 users", 4, leaderboard.getUsers().size());

        // Modify the JSON file to add another user
        String updatedJson = """
        [
            {
                "firstname": "Alice",
                "lastname": "Johnson",
                "id": "550e8400-e29b-41d4-a716-446655440001",
                "email": "alice@test.com",
                "password": "password123",
                "avatar": 1,
                "sessions": []
            },
            {
                "firstname": "Eve",
                "lastname": "Wilson",
                "id": "550e8400-e29b-41d4-a716-446655440005",
                "email": "eve@test.com",
                "password": "password111",
                "avatar": 5,
                "sessions": []
            }
        ]
        """;

        try (FileWriter writer = new FileWriter("json/users.json")) {
            writer.write(updatedJson);
        } catch (IOException e) {
            fail("Failed to create updated test data");
        }

        leaderboard.reloadUsers();
        assertEquals("Should reload with updated data", 2, leaderboard.getUsers().size());

        // Verify the new user was loaded
        boolean foundEve = false;
        for (User user : leaderboard.getUsers()) {
            if ("Eve".equals(user.getFirstName())) {
                foundEve = true;
                break;
            }
        }
        assertTrue("Should find newly added user Eve", foundEve);
    }

    // ============ ADD USER TESTS ============

    /**
     * Test adding a valid user to the leaderboard.
     */
    @Test
    public void testAddUser() {
        int initialCount = leaderboard.getUsers().size();

        User newUser = new User("John", "Doe", "john@test.com", "password", 1, UUID.randomUUID());
        leaderboard.addUser(newUser);

        assertEquals("User count should increase by 1", initialCount + 1, leaderboard.getUsers().size());
        assertTrue("New user should be in the list", leaderboard.getUsers().contains(newUser));
    }

    /**
     * Test adding multiple users.
     */
    @Test
    public void testAddMultipleUsers() {
        int initialCount = leaderboard.getUsers().size();

        User user1 = new User("User", "One", "user1@test.com", "pass1", 1, UUID.randomUUID());
        User user2 = new User("User", "Two", "user2@test.com", "pass2", 2, UUID.randomUUID());

        leaderboard.addUser(user1);
        leaderboard.addUser(user2);

        assertEquals("Should add both users", initialCount + 2, leaderboard.getUsers().size());
        assertTrue("Should contain first user", leaderboard.getUsers().contains(user1));
        assertTrue("Should contain second user", leaderboard.getUsers().contains(user2));
    }

    /**
     * Test adding user with null values (edge case).
     */
    @Test
    public void testAddUserWithNullFields() {
        int initialCount = leaderboard.getUsers().size();

        // This tests the robustness of the User constructor and data handling
        User userWithNulls = new User(null, null, null, null, 0, UUID.randomUUID());
        leaderboard.addUser(userWithNulls);

        assertEquals("Should still add user even with null fields", initialCount + 1, leaderboard.getUsers().size());
        assertTrue("User with null fields should be in list", leaderboard.getUsers().contains(userWithNulls));
    }

    // ============ SORT BY SCORE TESTS ============

    /**
     * Test sorting users by score in descending order.
     */
    @Test
    public void testSortByScore() {
        leaderboard.sortByScore();
        ArrayList<User> users = leaderboard.getUsers();

        // Verify users are sorted by total score (descending)
        // Expected order: Charlie (500), Alice (350), Bob (100), Diana (0)
        assertEquals("First user should be Charlie with highest score", "Charlie", users.get(0).getFirstName());
        assertEquals("Second user should be Alice", "Alice", users.get(1).getFirstName());
        assertEquals("Third user should be Bob", "Bob", users.get(2).getFirstName());
        assertEquals("Fourth user should be Diana with no sessions", "Diana", users.get(3).getFirstName());
    }

    /**
     * Test sorting with users having equal scores.
     */
    @Test
    public void testSortByScoreWithEqualScores() {
        // Clear existing users and add users with same scores
        userList.getUsers().clear();

        User user1 = new User("Equal1", "Score", "equal1@test.com", "pass", 1, UUID.randomUUID());
        User user2 = new User("Equal2", "Score", "equal2@test.com", "pass", 2, UUID.randomUUID());

        // Create sessions with equal total scores
        GameSession session1 = new GameSession(user1.getUUID(), "Team1", "Session1", "Horror", 1, 1);
        session1.setScore(100);
        user1.storeGameSession(session1);

        GameSession session2 = new GameSession(user2.getUUID(), "Team2", "Session2", "Fantasy", 1, 1);
        session2.setScore(100);
        user2.storeGameSession(session2);

        userList.getUsers().add(user1);
        userList.getUsers().add(user2);

        leaderboard.sortByScore();

        // Both users should still be in the list
        assertEquals("Should have 2 users", 2, leaderboard.getUsers().size());

        // Order might vary for equal scores, but both should be present
        boolean foundUser1 = false, foundUser2 = false;
        for (User user : leaderboard.getUsers()) {
            if ("Equal1".equals(user.getFirstName())) foundUser1 = true;
            if ("Equal2".equals(user.getFirstName())) foundUser2 = true;
        }
        assertTrue("Should find first user", foundUser1);
        assertTrue("Should find second user", foundUser2);
    }

    /**
     * Test sorting with empty user list.
     */
    @Test
    public void testSortByScoreEmptyList() {
        userList.getUsers().clear();

        // Should not throw exception
        leaderboard.sortByScore();
        assertEquals("Empty list should remain empty", 0, leaderboard.getUsers().size());
    }

    /**
     * Test sorting with single user.
     */
    @Test
    public void testSortByScoreSingleUser() {
        userList.getUsers().clear();

        User singleUser = new User("Solo", "Player", "solo@test.com", "pass", 1, UUID.randomUUID());
        userList.getUsers().add(singleUser);

        leaderboard.sortByScore();

        assertEquals("Should have 1 user", 1, leaderboard.getUsers().size());
        assertEquals("Should be the same user", "Solo", leaderboard.getUsers().get(0).getFirstName());
    }

    // ============ DISPLAY LEADERBOARD TESTS ============

    /**
     * Test displayLeaderBoard output format and content.
     */
    @Test
    public void testDisplayLeaderBoard() {
        leaderboard.displayLeaderBoard();
        String output = outContent.toString();

        assertNotNull("Output should not be null", output);
        assertTrue("Should contain leaderboard header", output.contains("------ Leaderboard ------"));

        // Check that all users are displayed
        assertTrue("Should display Alice", output.contains("Alice Johnson"));
        assertTrue("Should display Bob", output.contains("Bob Smith"));
        assertTrue("Should display Charlie", output.contains("Charlie Brown"));
        assertTrue("Should display Diana", output.contains("Diana Prince"));

        // Check ranking numbers
        assertTrue("Should show ranking numbers", output.contains("1."));
        assertTrue("Should show ranking numbers", output.contains("2."));
        assertTrue("Should show ranking numbers", output.contains("3."));
        assertTrue("Should show ranking numbers", output.contains("4."));
    }

    /**
     * Test displayLeaderBoard with correct score calculations and ordering.
     */
    @Test
    public void testDisplayLeaderBoardScoreOrdering() {
        leaderboard.displayLeaderBoard();
        String output = outContent.toString();

        // Charlie should be first with 500 points
        assertTrue("Charlie should be ranked first", output.indexOf("Charlie Brown - 500") < output.indexOf("Alice Johnson"));

        // Alice should be second with 350 points (150 + 200)
        assertTrue("Alice should be ranked second", output.indexOf("Alice Johnson - 350") < output.indexOf("Bob Smith"));

        // Bob should be third with 100 points
        assertTrue("Bob should be ranked third", output.indexOf("Bob Smith - 100") < output.indexOf("Diana Prince"));

        // Diana should be last with 0 points (no sessions)
        assertTrue("Diana should be ranked last", output.contains("Diana Prince - 0"));
    }

    /**
     * Test displayLeaderBoard with empty user list.
     */
    @Test
    public void testDisplayLeaderBoardEmpty() {
        // Create empty JSON
        try (FileWriter writer = new FileWriter("json/users.json")) {
            writer.write("[]");
        } catch (IOException e) {
            fail("Failed to create empty test data");
        }

        Leaderboard emptyLeaderboard = new Leaderboard();
        emptyLeaderboard.displayLeaderBoard();

        String output = outContent.toString();
        assertTrue("Should display header even when empty", output.contains("------ Leaderboard ------"));

        // Should not contain any user names or scores
        assertFalse("Should not contain user data", output.contains("Alice"));
        assertFalse("Should not contain user data", output.contains("Bob"));
    }

    // ============ GET USERS TESTS ============

    /**
     * Test getUsers returns correct user list.
     */
    @Test
    public void testGetUsers() {
        ArrayList<User> users = leaderboard.getUsers();

        assertNotNull("Users list should not be null", users);
        assertEquals("Should return all loaded users", 4, users.size());

        // Verify it's the same reference as UserList
        assertSame("Should return reference to UserList users", userList.getUsers(), users);
    }

    /**
     * Test getUsers reflects changes to UserList.
     */
    @Test
    public void testGetUsersReflectsChanges() {
        ArrayList<User> users = leaderboard.getUsers();
        int initialSize = users.size();

        // Add user directly to UserList
        User newUser = new User("Direct", "Add", "direct@test.com", "pass", 1, UUID.randomUUID());
        userList.getUsers().add(newUser);

        // getUsers should reflect this change
        assertEquals("getUsers should reflect UserList changes", initialSize + 1, leaderboard.getUsers().size());
        assertTrue("Should contain directly added user", leaderboard.getUsers().contains(newUser));
    }

    // ============ SCORE CALCULATION TESTS (PRIVATE METHOD TESTING VIA PUBLIC METHODS) ============

    /**
     * Test score calculation through sorting behavior.
     */
    @Test
    public void testScoreCalculationThroughSorting() {
        // Create users with known session scores for testing
        userList.getUsers().clear();

        User userA = new User("UserA", "Test", "a@test.com", "pass", 1, UUID.randomUUID());
        User userB = new User("UserB", "Test", "b@test.com", "pass", 2, UUID.randomUUID());

        // UserA: 2 sessions with scores 50 and 75 = 125 total
        GameSession sessionA1 = new GameSession(userA.getUUID(), "TeamA1", "SessionA1", "Horror", 1, 1);
        sessionA1.setScore(50);
        GameSession sessionA2 = new GameSession(userA.getUUID(), "TeamA2", "SessionA2", "Fantasy", 2, 1);
        sessionA2.setScore(75);
        userA.storeGameSession(sessionA1);
        userA.storeGameSession(sessionA2);

        // UserB: 1 session with score 200 = 200 total
        GameSession sessionB1 = new GameSession(userB.getUUID(), "TeamB1", "SessionB1", "Mystery", 3, 1);
        sessionB1.setScore(200);
        userB.storeGameSession(sessionB1);

        userList.getUsers().add(userA);
        userList.getUsers().add(userB);

        leaderboard.sortByScore();

        // UserB should be first (200 > 125)
        assertEquals("UserB should be first with higher score", "UserB", leaderboard.getUsers().get(0).getFirstName());
        assertEquals("UserA should be second", "UserA", leaderboard.getUsers().get(1).getFirstName());
    }

    /**
     * Test score calculation with null sessions.
     */
    @Test
    public void testScoreCalculationWithNullSessions() {
        userList.getUsers().clear();

        User userWithNoSessions = new User("NoSessions", "User", "nosessions@test.com", "pass", 1, UUID.randomUUID());
        userList.getUsers().add(userWithNoSessions);

        // Should not throw exception when calculating score
        leaderboard.sortByScore();
        assertEquals("User with no sessions should still be in list", 1, leaderboard.getUsers().size());

        // Display should show 0 score
        leaderboard.displayLeaderBoard();
        String output = outContent.toString();
        assertTrue("Should display 0 score for user with no sessions", output.contains("NoSessions User - 0"));
    }

    // ============ INTEGRATION TESTS ============

    /**
     * Test integration between Leaderboard and DataLoader/DataWriter.
     */
    @Test
    public void testDataIntegration() {
        // Add a new user
        User newUser = new User("Integration", "Test", "integration@test.com", "pass", 1, UUID.randomUUID());
        GameSession session = new GameSession(newUser.getUUID(), "IntegrationTeam", "IntegrationSession", "Horror", 2, 1);
        session.setScore(300);
        newUser.storeGameSession(session);

        leaderboard.addUser(newUser);

        // Create new leaderboard instance to test data persistence
        Leaderboard newLeaderboard = new Leaderboard();

        // Should load the added user
        boolean foundIntegrationUser = false;
        for (User user : newLeaderboard.getUsers()) {
            if ("Integration".equals(user.getFirstName())) {
                foundIntegrationUser = true;
                assertEquals("Should have correct last name", "Test", user.getLastName());
                assertEquals("Should have correct email", "integration@test.com", user.getEmail());
                assertFalse("Should have sessions", user.getAllSessions().isEmpty());
                break;
            }
        }
        assertTrue("Should find integration test user after reload", foundIntegrationUser);
    }

    /**
     * Test leaderboard behavior with corrupted data.
     */
    @Test
    public void testCorruptedDataHandling() {
        // Create corrupted JSON
        try (FileWriter writer = new FileWriter("json/users.json")) {
            writer.write("{ invalid json }");
        } catch (IOException e) {
            fail("Failed to create corrupted test data");
        }

        // Should handle gracefully (DataLoader should catch the exception)
        try {
            Leaderboard corruptedLeaderboard = new Leaderboard();
            assertNotNull("Should create leaderboard even with corrupted data", corruptedLeaderboard);
            // The actual behavior depends on DataLoader implementation
        } catch (Exception e) {
            // If DataLoader throws exception, that's also acceptable behavior
            assertNotNull("Exception should have a message", e.getMessage());
        }
    }

    // ============ EDGE CASE TESTS ============

    /**
     * Test leaderboard with users having negative scores.
     */
    @Test
    public void testNegativeScores() {
        userList.getUsers().clear();

        User userPositive = new User("Positive", "Score", "pos@test.com", "pass", 1, UUID.randomUUID());
        User userNegative = new User("Negative", "Score", "neg@test.com", "pass", 2, UUID.randomUUID());

        GameSession positiveSession = new GameSession(userPositive.getUUID(), "PosTeam", "PosSession", "Horror", 1, 1);
        positiveSession.setScore(100);
        userPositive.storeGameSession(positiveSession);

        GameSession negativeSession = new GameSession(userNegative.getUUID(), "NegTeam", "NegSession", "Fantasy", 1, 1);
        negativeSession.setScore(-50);
        userNegative.storeGameSession(negativeSession);

        userList.getUsers().add(userPositive);
        userList.getUsers().add(userNegative);

        leaderboard.sortByScore();

        // Positive score should come first
        assertEquals("Positive score should be ranked higher", "Positive", leaderboard.getUsers().get(0).getFirstName());
        assertEquals("Negative score should be ranked lower", "Negative", leaderboard.getUsers().get(1).getFirstName());

        leaderboard.displayLeaderBoard();
        String output = outContent.toString();
        assertTrue("Should display negative score", output.contains("Negative Score - -50"));
    }

    /**
     * Test leaderboard with very large scores.
     */
    @Test
    public void testLargeScores() {
        userList.getUsers().clear();

        User userLarge = new User("Large", "Score", "large@test.com", "pass", 1, UUID.randomUUID());
        GameSession largeSession = new GameSession(userLarge.getUUID(), "LargeTeam", "LargeSession", "Horror", 3, 1);
        largeSession.setScore(Integer.MAX_VALUE);
        userLarge.storeGameSession(largeSession);

        userList.getUsers().add(userLarge);

        leaderboard.sortByScore();
        leaderboard.displayLeaderBoard();

        String output = outContent.toString();
        assertTrue("Should handle large scores", output.contains("Large Score - " + Integer.MAX_VALUE));
    }

    /**
     * Test concurrent access simulation (basic thread safety consideration).
     */
    @Test
    public void testConcurrentAccess() {
        // This is a basic test - real thread safety would require more complex testing
        final int initialUserCount = leaderboard.getUsers().size();

        // Simulate multiple operations
        for (int i = 0; i < 10; i++) {
            User user = new User("Concurrent" + i, "User", "concurrent" + i + "@test.com", "pass", i, UUID.randomUUID());
            leaderboard.addUser(user);
        }

        assertEquals("Should add all concurrent users", initialUserCount + 10, leaderboard.getUsers().size());

        // Sort multiple times
        for (int i = 0; i < 5; i++) {
            leaderboard.sortByScore();
        }

        // Should still have all users
        assertEquals("Should maintain user count after multiple sorts", initialUserCount + 10, leaderboard.getUsers().size());
    }
}
