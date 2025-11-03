package com.model;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * Test suite for the EscapeManager class.
 * Tests Singleton pattern implementation and game management functionality.
 */
public class EscapeManagerTest {
    
    @Before
    public void setUp() {
        // Reset the singleton instance before each test
        resetSingleton();
    }

    @After
    public void tearDown() {
        // Clean up after each test
        resetSingleton();
    }

    /**
     * Helper method to reset the singleton instance using reflection
     */
    private void resetSingleton() {
        try {
            Field instance = EscapeManager.class.getDeclaredField("escapeManager");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            fail("Failed to reset singleton: " + e.getMessage());
        }
    }

    @Test
    public void testSingletonPattern() {
        EscapeManager instance1 = EscapeManager.getInstance();
        EscapeManager instance2 = EscapeManager.getInstance();
        
        assertNotNull("getInstance should not return null", instance1);
        assertSame("Multiple getInstance calls should return same instance", 
            instance1, instance2);
    }

    @Test
    public void testPrivateConstructor() {
        Constructor<?>[] constructors = EscapeManager.class.getDeclaredConstructors();
        assertEquals("Should have exactly one constructor", 1, constructors.length);
        
        Constructor<?> constructor = constructors[0];
        assertTrue("Constructor should be private", 
            Modifier.isPrivate(constructor.getModifiers()));
        
        // Try to access the private constructor
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
        } catch (Exception e) {
            fail("Should be able to create instance with private constructor");
        }
    }

    @Test
    public void testGetInstanceThreadSafety() throws InterruptedException {
        final int threadCount = 100;
        Thread[] threads = new Thread[threadCount];
        final EscapeManager[] instances = new EscapeManager[threadCount];
        
        // Create multiple threads that try to get the instance simultaneously
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                instances[index] = EscapeManager.getInstance();
            });
            threads[i].start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }
        
        // Verify all instances are the same
        EscapeManager firstInstance = instances[0];
        for (EscapeManager instance : instances) {
            assertSame("All instances should be the same across threads", 
                firstInstance, instance);
        }
    }

    @Test
    public void testLogInMethodExists() {
        EscapeManager manager = EscapeManager.getInstance();
        try {
            manager.getClass().getMethod("logIn");
        } catch (NoSuchMethodException e) {
            fail("logIn method should exist");
        }
    }

    @Test
    public void testLeaderboardInitialization() {
        EscapeManager manager = EscapeManager.getInstance();
        try {
            Field leaderboardField = EscapeManager.class.getDeclaredField("leaderboard");
            leaderboardField.setAccessible(true);
            assertNotNull("Leaderboard field should be declared", leaderboardField);
            assertEquals("Leaderboard field should be of type Leaderboard", 
                Leaderboard.class, leaderboardField.getType());
        } catch (NoSuchFieldException e) {
            fail("Leaderboard field should exist");
        }
    }

    @Test
    public void testUserFieldInitialization() {
        EscapeManager manager = EscapeManager.getInstance();
        try {
            Field userField = EscapeManager.class.getDeclaredField("user");
            userField.setAccessible(true);
            assertNotNull("User field should be declared", userField);
            assertEquals("User field should be of type User", 
                User.class, userField.getType());
        } catch (NoSuchFieldException e) {
            fail("User field should exist");
        }
    }

    @Test
    public void testSingletonFieldModifiers() {
        try {
            Field instanceField = EscapeManager.class.getDeclaredField("escapeManager");
            int modifiers = instanceField.getModifiers();
            assertTrue("Singleton instance should be private", 
                Modifier.isPrivate(modifiers));
            assertTrue("Singleton instance should be static", 
                Modifier.isStatic(modifiers));
        } catch (NoSuchFieldException e) {
            fail("escapeManager field should exist");
        }
    }

    @Test
    public void testInitialState() {
        EscapeManager manager = EscapeManager.getInstance();
        try {
            Field leaderboardField = EscapeManager.class.getDeclaredField("leaderboard");
            Field userField = EscapeManager.class.getDeclaredField("user");
            leaderboardField.setAccessible(true);
            userField.setAccessible(true);
            
            // Initially these should be null as they're not initialized in constructor
            assertNull("Leaderboard should be initially null", 
                leaderboardField.get(manager));
            assertNull("User should be initially null", 
                userField.get(manager));
        } catch (Exception e) {
            fail("Failed to access fields: " + e.getMessage());
        }
    }

    @Test
    public void testClassStructure() {
        Class<EscapeManager> clazz = EscapeManager.class;
        
        // Verify class is not abstract
        assertFalse("EscapeManager should not be abstract", 
            Modifier.isAbstract(clazz.getModifiers()));
        
        // Verify field declarations
        Field[] fields = clazz.getDeclaredFields();
        assertTrue("Should have at least 3 fields", fields.length >= 3);
        
        // Verify method declarations
        assertTrue("Should have getInstance method", 
            hasMethod(clazz, "getInstance"));
        assertTrue("Should have logIn method", 
            hasMethod(clazz, "logIn"));
    }

    private boolean hasMethod(Class<?> clazz, String methodName) {
        try {
            clazz.getDeclaredMethod(methodName);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}
