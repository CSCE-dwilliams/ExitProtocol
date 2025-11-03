package com.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;
import static org.junit.Assert.*;

public class UserListTest {
    private UserList list;

    @Before
    public void setUp() throws Exception {
        // Ensure the singleton is reset before each test
        resetSingleton();

        // Obtain a fresh instance and ensure internal users list is empty
        list = UserList.getInstance();
        list.getUsers().clear();
    }

    @After
    public void tearDown() throws Exception {
        // clear and reset singleton after each test to avoid cross-test contamination
        if (list != null) {
            list.getUsers().clear();
        }
        resetSingleton();
    }

    /**
     * Uses reflection to set the private static userList field to null,
     * allowing tests to re-create the singleton instance.
     */
    private void resetSingleton() throws Exception {
        java.lang.reflect.Field f = UserList.class.getDeclaredField("userList");
        f.setAccessible(true);
        f.set(null, null);
    }

    @Test
    public void testSingletonInstanceIsSame() {
        UserList a = UserList.getInstance();
        UserList b = UserList.getInstance();
        assertSame("getInstance should return same singleton instance", a, b);
    }

    @Test
    public void testCreateAccountAddsUserAndTestEmailSignIn() {
        int before = list.getUsers().size();

        UUID id = UUID.randomUUID();
        String email = "newuser@example.com";
        list.createAccount("New", "User", email, "secret", 1, id);

        // size increased by 1
        assertEquals(before + 1, list.getUsers().size());

        // email lookup (case-insensitive) should succeed
        assertTrue("Email should be found by testEmailSignIn", list.testEmailSignIn(email.toUpperCase()));

        // getUser with exact email/password should return the created user
        User retrieved = list.getUser(email, "secret");
        assertNotNull("getUser should return the newly created user", retrieved);
        assertEquals("Emails should match", email, retrieved.getEmail());
        assertEquals("IDs should match", id, retrieved.getID());
    }

    @Test
    public void testTestEmailSignInCaseInsensitive() {
        UUID id = UUID.randomUUID();
        User u = new User("Alice", "Smith", "Alice@Example.com", "pw", 0, id);
        list.getUsers().add(u);

        assertTrue("Lowercase lookup should find mixed-case email", list.testEmailSignIn("alice@example.com"));
        assertTrue("Uppercase lookup should find mixed-case email", list.testEmailSignIn("ALICE@EXAMPLE.COM"));
    }

    @Test
    public void testGetUserPasswordMatching() {
        UUID id = UUID.randomUUID();
        User u = new User("Bob", "Jones", "bob@example.com", "mypw", 0, id);
        list.getUsers().add(u);

        // correct credentials
        User ok = list.getUser("bob@example.com", "mypw");
        assertNotNull(ok);
        assertEquals(id, ok.getID());

        // wrong password
        User wrongPw = list.getUser("bob@example.com", "badpw");
        assertNull(wrongPw);

        // wrong email
        User wrongEmail = list.getUser("nobody@example.com", "mypw");
        assertNull(wrongEmail);
    }

    @Test
    public void testUpdateUserReplacesExistingById() {
        UUID id = UUID.randomUUID();
        User original = new User("First", "Last", "x@example.com", "pw1", 1, id);
        list.getUsers().add(original);

        // Create a replacement user with same ID but different fields
        User updated = new User("NewFirst", "NewLast", "x@example.com", "pw2", 2, id);

        list.updateUser(updated);

        // find user in list by id and confirm it matches updated object
        boolean foundUpdated = false;
        for (User u : list.getUsers()) {
            if (u.getID().equals(id)) {
                assertEquals("First name should be updated", "NewFirst", u.getFirstName());
                assertEquals("Password should be updated", "pw2", u.getPassword());
                assertEquals("Avatar should be updated", 2, u.getAvatar());
                foundUpdated = true;
            }
        }
        assertTrue("Updated user should be present in list", foundUpdated);
    }

    @Test
    public void testSeeSessionsReturnsNullWhenNoSessions() {
        UUID id = UUID.randomUUID();
        User u = new User("Sess", "Less", "sessless@example.com", "pw", 0, id);
        list.getUsers().add(u);

        // seeSessions returns null when user has no sessions per implementation
        ArrayList<GameSession> sessions = list.seeSessions(u);
        assertNull("seeSessions should return null if user has no in-progress sessions", sessions);
    }

    @Test
    public void testSeeSessionsReturnsListWhenNonEmpty() {
        UUID id = UUID.randomUUID();
        User u = new User("Has", "Sessions", "has@example.com", "pw", 0, id);
        list.getUsers().add(u);

        // create a minimal GameSession and add to user (assumes GameSession constructor available)
        GameSession gs = new GameSession(u.getUUID(), "TeamX", "SessionX", "Theme", 1, 1);
        u.storeGameSession(gs);

        ArrayList<GameSession> result = list.seeSessions(u);
        assertNotNull("seeSessions should return non-null when sessions exist", result);
        assertEquals(1, result.size());
        assertSame("Returned session should be the same instance stored", gs, result.get(0));
    }
}
