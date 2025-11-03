package com.model;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.UUID;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class UserTest {

    /** Test user instance under test. */
    private User user;

    /** Designate a UUID for consistent user identity across all tests. */
    private static UUID staticUserId;

    /** Path to the temporary users JSON file used in tests. */
    private static final String TEST_USERS_PATH = "json/users.json";

    /** Backup of the original user file, restored after tests. */
    private static final String BACKUP_USER_FILE = "json/users_backup.json";

    /**
     * Prepares the test environment before any tests run.
     */
    @BeforeClass
    public static void setUpClass() {
        staticUserId = UUID.randomUUID();

        File originalFile = new File(TEST_USERS_PATH);
        File backupFile = new File(BACKUP_USER_FILE);

        if (originalFile.exists()) {
            originalFile.renameTo(backupFile);
        }

        File jsonDir = new File("json");
        if (!jsonDir.exists()) {
            jsonDir.mkdirs();
        }
    }

    /**
     * Cleans up after all tests have completed.
     * 
     * Deletes the test JSON file if it exists.
     * Restores the original users file from backup if present.
     */
    @AfterClass
    public static void tearDownClass() {
        File testFile = new File(TEST_USERS_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }

        File backupFile = new File(BACKUP_USER_FILE);
        File originalFile = new File(TEST_USERS_PATH);

        if (backupFile.exists()) {
            backupFile.renameTo(originalFile);
        }
    }

    /**
     * Initializes a fresh User object before each test and writes deterministic
     * test data to json/users.json.
     */
    @Before
    public void setUp() {
        createTestUserData();
        user = new User("Alice", "Smith", "alice@example.com", "pw123", 2, staticUserId);
    }

    /**
     * Cleans up after each test by removing all sessions associated with the user.
     */
    @After
    public void tearDown() {
        for (GameSession s : new ArrayList<>(user.getAllSessions())) {
            user.removeSession(s.getSessionID());
        }
    }

    /**
     * Writes a deterministic JSON dataset for user-related tests.
     * 
     * <p>The dataset includes one user ("Alice Smith") and one sample session
     * with fixed IDs and parameters. Used to ensure consistent test behavior.
     */
    private void createTestUserData() {
        String testJson = """
        [
          {
            "firstname": "Alice",
            "lastname": "Smith",
            "id": "550e8400-e29b-41d4-a716-446655440010",
            "email": "alice@example.com",
            "password": "pw123",
            "avatar": 2,
            "sessions": [
              {
                "id": "550e8400-e29b-41d4-a716-446655440011",
                "teamname": "TeamA",
                "sessionName": "SessionA",
                "theme": "Horror",
                "difficulty": 1,
                "playercount": 1,
                "score": 150,
                "currentChallengeIndex": 3,
                "state": "ACTIVE",
                "hintsUsed": 0
              }
            ]
          }
        ]
        """;

        try (FileWriter writer = new FileWriter(TEST_USERS_PATH)) {
            writer.write(testJson);
        } catch (IOException e) {
            fail("Failed to create test user data: " + e.getMessage());
        }
    }

    // ====================== TESTS ======================

    /**
     * Tests that the User constructor and getter methods work correctly.
     */
    @Test
    public void testConstructorAndGetters() {
        assertNotNull("User should have been created", user);
        assertEquals("First name should match", "Alice", user.getFirstName());
        assertEquals("Last name should match", "Smith", user.getLastName());
        assertEquals("Email should match", "alice@example.com", user.getEmail());
        assertEquals("Password should match", "pw123", user.getPassword());
        assertEquals("Avatar should match", 2, user.getAvatar());
        assertEquals("UUID should match", staticUserId, user.getID());
        assertEquals("getUUID should match getID", user.getID(), user.getUUID());
    }

    /**
     * Tests that User setScore and User getScore work as expected.
     */
    @Test
    public void testSetAndGetScore() {
        user.setScore(77);
        assertEquals("Score should return value set", 77, user.getScore());
    }

    /**
     * Tests storing and retrieving GameSession instances by UUID,
     * and verifying that User getAllSessions returns them correctly.
     */
    @Test
    public void testStoreAndGetSession_byUUID_and_getAllSessions() {
        UUID sessionId = UUID.randomUUID();
        GameSession gs = new GameSession(user.getUUID(), "TeamA", "SessionA", "Horror", 1, 1);
        UUID actualSessionId = gs.getSessionID();

        user.storeGameSession(gs);

        assertSame("Stored session should be retrievable by its ID", gs, user.getSession(actualSessionId));

        ArrayList<GameSession> sessions = user.getAllSessions();
        assertEquals("Should have one stored session", 1, sessions.size());
        assertSame("The stored session should be the same instance", gs, sessions.get(0));
    }

    /**
     * Tests that User getSessionIDS returns session IDs as strings.
     */
    @Test
    public void testGetSessionIDS_returnsStringIds() {
        GameSession gs = new GameSession(user.getUUID(), "TeamB", "SessionB", "Fantasy", 2, 2);
        UUID id = gs.getSessionID();

        user.storeGameSession(gs);

        ArrayList<String> ids = user.getSessionIDS();
        assertEquals("Should contain one id", 1, ids.size());
        assertEquals("Stringified id should match session id", id.toString(), ids.get(0));
    }

    /**
     * Tests selecting sessions by name using User chooseSession,
     * including case-insensitivity and nonexistent names.
     */
    @Test
    public void testChooseSession_byName_caseInsensitive_and_nonexistent() {
        GameSession gs1 = new GameSession(user.getUUID(), "TeamOne", "TeamOneSession", "Mystery", 1, 1);
        GameSession gs2 = new GameSession(user.getUUID(), "TeamTwo", "OtherSession", "SciFi", 2, 2);

        user.storeGameSession(gs1);
        user.storeGameSession(gs2);

        GameSession chosen = user.chooseSession("teamonesession");
        assertSame("Should find session by name ignoring case", gs1, chosen);

        assertNull("Nonexistent session should return null", user.chooseSession("noSuchSession"));
    }

    /**
     * Tests that removing a session correctly deletes it from storage.
     */
    @Test
    public void testRemoveSession_removesCorrectly() {
        GameSession gs = new GameSession(user.getUUID(), "TeamC", "SessionC", "Historical", 3, 4);
        UUID id = gs.getSessionID();

        user.storeGameSession(gs);
        assertSame("Should be retrievable pre-removal", gs, user.getSession(id));

        user.removeSession(id);
        assertNull("Session should be removed and return null", user.getSession(id));
        assertTrue("getAllSessions should be empty after removal", user.getAllSessions().isEmpty());
    }

    /**
     * Tests that storing a null GameSession does nothing and causes no exceptions.
     */
    @Test
    public void testStoreNullSession_doesNothing() {
        user.storeGameSession(null);
        assertTrue("No sessions should exist after storing null", user.getAllSessions().isEmpty());
    }

    /**
     * Tests that User toString includes basic identifying information.
     */
    @Test
    public void testToString_containsBasicInfo() {
        String s = user.toString();
        assertTrue("toString should contain first name", s.contains("First Name: Alice"));
        assertTrue("toString should contain last name", s.contains("Last Name: Smith"));
        assertTrue("toString should contain email", s.contains("Email: alice@example.com"));
        assertTrue("toString should contain avatar selection", s.contains("Avatar Selection No.:2"));
    }
}
