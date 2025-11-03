package com.model;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class UITest {
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStream;

    @Before
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        
    }

    @After
    public void tearDown() {
        // Restore normal System.out and SecurityManager
        System.setOut(originalOut);
    }

    public static class EscapeManager {
        private static final EscapeManager instance = new EscapeManager();
        public static EscapeManager getInstance() { return instance; }
        public void logIn() {
            System.out.println("[Mock] logIn() called");
        }
    }

    public static class UserList {
        private static final UserList instance = new UserList();
        public static UserList getInstance() { return instance; }
    }

    @Test
    public void testMainLoopPrintsExpectedTextAndCallsExit() {
        UI ui = new UI();

        boolean exitCalled = false;
        try {
            ui.mainLoop();
        } catch (SecurityException e) {
            exitCalled = e.getMessage().contains("System.exit(0)");
        } catch (Exception e) {
            fail("mainLoop() threw unexpected exception: " + e);
        }

        String output = outputStream.toString();

        assertTrue("Expected welcome message in output",
                output.contains("WELCOME TO EXIT PROTOCOL"));
        assertTrue("Expected ASCII art or intro section",
                output.contains("An Escape Room Bonanza"));
        assertTrue("Expected mock login message",
                output.contains("[Mock] logIn() called"));
        assertTrue("Expected System.exit(0) call",
                exitCalled);
    }

    @Test
    public void testSignInLoopDoesNotThrow() {
        UI ui = new UI();
        try {
            ui.signInLoop();
        } catch (Exception e) {
            fail("signInLoop() should not throw an exception, but threw: " + e);
        }
    }

    @Test
    public void testMainMethodRunsWithoutCrashing() {
        boolean exitCalled = false;
        try {
            UI.main(new String[]{});
        } catch (SecurityException e) {
            exitCalled = e.getMessage().contains("System.exit(0)");
        } catch (Exception e) {
            fail("main() threw unexpected exception: " + e);
        }
        assertTrue("System.exit(0) should have been called", exitCalled);
    }
}
