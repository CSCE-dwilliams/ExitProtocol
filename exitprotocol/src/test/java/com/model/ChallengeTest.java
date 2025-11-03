package com.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Comprehensive test suite for the Challenge class.
 * Tests all methods and edge cases to identify potential bugs.
 *
 * @author Clankers
 */
public class ChallengeTest {

    private Challenge challenge;
    private Challenge challengeWithNulls;
    private Challenge challengeWithEmptyStrings;
    private Item testItem1;
    private Item testItem2;

    /**
     * Sets up test fixtures before each test method.
     * Creates various Challenge instances for testing different scenarios.
     */
    @Before
    public void setUp() {
        // Standard challenge for most tests
        challenge = new Challenge(
            "What is the capital of France?",
            "Paris",
            "Great! Now let's move to the next challenge."
        );

        // Challenge with null values to test edge cases
        challengeWithNulls = new Challenge(null, null, null);

        // Challenge with empty strings to test edge cases
        challengeWithEmptyStrings = new Challenge("", "", "");

        // Create test items
        testItem1 = new Item("Map", "A detailed map of the city", "Navigation");
        testItem2 = new Item("Key", "An old rusty key", "Unlock doors");
    }

    // ========== Constructor Tests ==========

    /**
     * Tests that Challenge constructor properly initializes all fields.
     */
    @Test
    public void testConstructor_ValidParameters() {
        Challenge c = new Challenge("Question", "Answer", "PostQuestion");
        assertEquals("Question should be set correctly", "Question", c.getQuestion());
        assertEquals("Answer should be set correctly", "Answer", c.getAnswer());
        assertEquals("PostQuestion should be set correctly", "PostQuestion", c.getPostQuestion());
        assertNotNull("Hints list should be initialized", c.getHints());
        assertNotNull("Items list should be initialized", c.getItems());
        assertTrue("Hints list should be empty initially", c.getHints().isEmpty());
        assertTrue("Items list should be empty initially", c.getItems().isEmpty());
    }

    /**
     * Tests constructor with null parameters.
     */
    @Test
    public void testConstructor_NullParameters() {
        Challenge c = new Challenge(null, null, null);
        assertNull("Question should be null", c.getQuestion());
        assertNull("Answer should be null", c.getAnswer());
        assertNull("PostQuestion should be null", c.getPostQuestion());
        assertNotNull("Hints list should still be initialized", c.getHints());
        assertNotNull("Items list should still be initialized", c.getItems());
    }

    /**
     * Tests constructor with empty string parameters.
     */
    @Test
    public void testConstructor_EmptyStrings() {
        Challenge c = new Challenge("", "", "");
        assertEquals("Question should be empty string", "", c.getQuestion());
        assertEquals("Answer should be empty string", "", c.getAnswer());
        assertEquals("PostQuestion should be empty string", "", c.getPostQuestion());
    }

    /**
     * Tests constructor with very long strings.
     */
    @Test
    public void testConstructor_LongStrings() {
        String longString = "A".repeat(10000);
        Challenge c = new Challenge(longString, longString, longString);
        assertEquals("Long question should be stored correctly", longString, c.getQuestion());
        assertEquals("Long answer should be stored correctly", longString, c.getAnswer());
        assertEquals("Long post question should be stored correctly", longString, c.getPostQuestion());
    }

    /**
     * Tests constructor with special characters.
     */
    @Test
    public void testConstructor_SpecialCharacters() {
        String specialChars = "!@#$%^&*()_+-=[]{}|;':\",./<>?`~\n\t\\";
        Challenge c = new Challenge(specialChars, specialChars, specialChars);
        assertEquals("Question with special characters should be stored", specialChars, c.getQuestion());
        assertEquals("Answer with special characters should be stored", specialChars, c.getAnswer());
        assertEquals("PostQuestion with special characters should be stored", specialChars, c.getPostQuestion());
    }

    // ========== getQuestion() Tests ==========

    /**
     * Tests getQuestion returns the correct question.
     */
    @Test
    public void testGetQuestion_ValidQuestion() {
        assertEquals("Should return the correct question",
            "What is the capital of France?",
            challenge.getQuestion());
    }

    /**
     * Tests getQuestion with null question.
     */
    @Test
    public void testGetQuestion_NullQuestion() {
        assertNull("Should return null for null question", challengeWithNulls.getQuestion());
    }

    /**
     * Tests getQuestion with empty string.
     */
    @Test
    public void testGetQuestion_EmptyString() {
        assertEquals("Should return empty string", "", challengeWithEmptyStrings.getQuestion());
    }

    /**
     * Tests that getQuestion returns the same reference (immutability check).
     */
    @Test
    public void testGetQuestion_ReturnsSameReference() {
        String question1 = challenge.getQuestion();
        String question2 = challenge.getQuestion();
        assertSame("Should return the same reference", question1, question2);
    }

    // ========== getAnswer() Tests ==========

    /**
     * Tests getAnswer returns the correct answer.
     */
    @Test
    public void testGetAnswer_ValidAnswer() {
        assertEquals("Should return the correct answer", "Paris", challenge.getAnswer());
    }

    /**
     * Tests getAnswer with null answer.
     */
    @Test
    public void testGetAnswer_NullAnswer() {
        assertNull("Should return null for null answer", challengeWithNulls.getAnswer());
    }

    /**
     * Tests getAnswer with empty string.
     */
    @Test
    public void testGetAnswer_EmptyString() {
        assertEquals("Should return empty string", "", challengeWithEmptyStrings.getAnswer());
    }

    /**
     * Tests that getAnswer returns the same reference.
     */
    @Test
    public void testGetAnswer_ReturnsSameReference() {
        String answer1 = challenge.getAnswer();
        String answer2 = challenge.getAnswer();
        assertSame("Should return the same reference", answer1, answer2);
    }

    /**
     * Tests case sensitivity of answer.
     */
    @Test
    public void testGetAnswer_CaseSensitivity() {
        Challenge c = new Challenge("Question", "Paris", "Post");
        assertEquals("Answer should maintain original case", "Paris", c.getAnswer());
        assertNotEquals("Answer should be case sensitive", "paris", c.getAnswer());
        assertNotEquals("Answer should be case sensitive", "PARIS", c.getAnswer());
    }

    // ========== getPostQuestion() Tests ==========

    /**
     * Tests getPostQuestion returns the correct post question.
     */
    @Test
    public void testGetPostQuestion_ValidPostQuestion() {
        assertEquals("Should return the correct post question",
            "Great! Now let's move to the next challenge.",
            challenge.getPostQuestion());
    }

    /**
     * Tests getPostQuestion with null post question.
     */
    @Test
    public void testGetPostQuestion_NullPostQuestion() {
        assertNull("Should return null for null post question",
            challengeWithNulls.getPostQuestion());
    }

    /**
     * Tests getPostQuestion with empty string.
     */
    @Test
    public void testGetPostQuestion_EmptyString() {
        assertEquals("Should return empty string", "",
            challengeWithEmptyStrings.getPostQuestion());
    }

    /**
     * Tests that getPostQuestion returns the same reference.
     */
    @Test
    public void testGetPostQuestion_ReturnsSameReference() {
        String post1 = challenge.getPostQuestion();
        String post2 = challenge.getPostQuestion();
        assertSame("Should return the same reference", post1, post2);
    }

    // ========== getHints() Tests ==========

    /**
     * Tests that getHints returns a non-null list initially.
     */
    @Test
    public void testGetHints_InitiallyNotNull() {
        assertNotNull("Hints list should not be null", challenge.getHints());
    }

    /**
     * Tests that getHints returns an empty list initially.
     */
    @Test
    public void testGetHints_InitiallyEmpty() {
        assertTrue("Hints list should be empty initially", challenge.getHints().isEmpty());
        assertEquals("Hints list size should be 0", 0, challenge.getHints().size());
    }

    /**
     * Tests that getHints returns the correct list after adding hints.
     */
    @Test
    public void testGetHints_AfterAddingHints() {
        challenge.addHint("Hint 1");
        challenge.addHint("Hint 2");

        ArrayList<String> hints = challenge.getHints();
        assertNotNull("Hints list should not be null", hints);
        assertEquals("Hints list should contain 2 hints", 2, hints.size());
        assertEquals("First hint should match", "Hint 1", hints.get(0));
        assertEquals("Second hint should match", "Hint 2", hints.get(1));
    }

    /**
     * Tests that getHints returns the same list reference (potential mutability issue).
     */
    @Test
    public void testGetHints_ReturnsSameReference() {
        ArrayList<String> hints1 = challenge.getHints();
        ArrayList<String> hints2 = challenge.getHints();
        assertSame("Should return the same list reference", hints1, hints2);
    }

    /**
     * Tests that external modification of hints list affects the challenge.
     * This tests potential encapsulation issues.
     */
    @Test
    public void testGetHints_ExternalModification() {
        ArrayList<String> hints = challenge.getHints();
        hints.add("External Hint");

        // This should show whether the list is properly encapsulated
        assertEquals("External modification should affect challenge hints",
            1, challenge.getHints().size());
        assertTrue("External hint should be in the list",
            challenge.getHints().contains("External Hint"));
    }

    // ========== getItems() Tests ==========

    /**
     * Tests that getItems returns a non-null list initially.
     */
    @Test
    public void testGetItems_InitiallyNotNull() {
        assertNotNull("Items list should not be null", challenge.getItems());
    }

    /**
     * Tests that getItems returns an empty list initially.
     */
    @Test
    public void testGetItems_InitiallyEmpty() {
        assertTrue("Items list should be empty initially", challenge.getItems().isEmpty());
        assertEquals("Items list size should be 0", 0, challenge.getItems().size());
    }

    /**
     * Tests that getItems returns the correct list after adding items.
     */
    @Test
    public void testGetItems_AfterAddingItems() {
        challenge.addItem(testItem1);
        challenge.addItem(testItem2);

        ArrayList<Item> items = challenge.getItems();
        assertNotNull("Items list should not be null", items);
        assertEquals("Items list should contain 2 items", 2, items.size());
        assertEquals("First item should match", testItem1, items.get(0));
        assertEquals("Second item should match", testItem2, items.get(1));
    }

    /**
     * Tests that getItems returns the same list reference.
     */
    @Test
    public void testGetItems_ReturnsSameReference() {
        ArrayList<Item> items1 = challenge.getItems();
        ArrayList<Item> items2 = challenge.getItems();
        assertSame("Should return the same list reference", items1, items2);
    }

    /**
     * Tests that external modification of items list affects the challenge.
     */
    @Test
    public void testGetItems_ExternalModification() {
        ArrayList<Item> items = challenge.getItems();
        items.add(testItem1);

        assertEquals("External modification should affect challenge items",
            1, challenge.getItems().size());
        assertTrue("External item should be in the list",
            challenge.getItems().contains(testItem1));
    }

    // ========== addHint() Tests ==========

    /**
     * Tests adding a single valid hint.
     */
    @Test
    public void testAddHint_SingleHint() {
        challenge.addHint("This is a hint");

        assertEquals("Should have 1 hint", 1, challenge.getHints().size());
        assertEquals("Hint should match", "This is a hint", challenge.getHints().get(0));
    }

    /**
     * Tests adding multiple hints.
     */
    @Test
    public void testAddHint_MultipleHints() {
        challenge.addHint("Hint 1");
        challenge.addHint("Hint 2");
        challenge.addHint("Hint 3");

        ArrayList<String> hints = challenge.getHints();
        assertEquals("Should have 3 hints", 3, hints.size());
        assertEquals("First hint should match", "Hint 1", hints.get(0));
        assertEquals("Second hint should match", "Hint 2", hints.get(1));
        assertEquals("Third hint should match", "Hint 3", hints.get(2));
    }

    /**
     * Tests adding a null hint.
     */
    @Test
    public void testAddHint_NullHint() {
        challenge.addHint(null);

        assertEquals("Should have 1 hint (null)", 1, challenge.getHints().size());
        assertNull("Hint should be null", challenge.getHints().get(0));
    }

    /**
     * Tests adding an empty string hint.
     */
    @Test
    public void testAddHint_EmptyString() {
        challenge.addHint("");

        assertEquals("Should have 1 hint", 1, challenge.getHints().size());
        assertEquals("Hint should be empty string", "", challenge.getHints().get(0));
    }

    /**
     * Tests adding duplicate hints.
     */
    @Test
    public void testAddHint_DuplicateHints() {
        challenge.addHint("Same hint");
        challenge.addHint("Same hint");

        assertEquals("Should have 2 hints (duplicates allowed)", 2, challenge.getHints().size());
        assertEquals("First hint should match", "Same hint", challenge.getHints().get(0));
        assertEquals("Second hint should match", "Same hint", challenge.getHints().get(1));
    }

    /**
     * Tests adding a very long hint.
     */
    @Test
    public void testAddHint_LongHint() {
        String longHint = "A".repeat(10000);
        challenge.addHint(longHint);

        assertEquals("Should have 1 hint", 1, challenge.getHints().size());
        assertEquals("Long hint should be stored correctly", longHint, challenge.getHints().get(0));
    }

    /**
     * Tests adding hints with special characters.
     */
    @Test
    public void testAddHint_SpecialCharacters() {
        String specialHint = "Hint with \n newlines \t tabs and \"quotes\"";
        challenge.addHint(specialHint);

        assertEquals("Should have 1 hint", 1, challenge.getHints().size());
        assertEquals("Special hint should be stored correctly", specialHint, challenge.getHints().get(0));
    }

    /**
     * Tests the order of hints is maintained.
     */
    @Test
    public void testAddHint_OrderMaintained() {
        challenge.addHint("First");
        challenge.addHint("Second");
        challenge.addHint("Third");

        ArrayList<String> hints = challenge.getHints();
        assertEquals("First hint should be at index 0", "First", hints.get(0));
        assertEquals("Second hint should be at index 1", "Second", hints.get(1));
        assertEquals("Third hint should be at index 2", "Third", hints.get(2));
    }

    /**
     * Tests adding many hints (stress test).
     */
    @Test
    public void testAddHint_ManyHints() {
        int numHints = 1000;
        for (int i = 0; i < numHints; i++) {
            challenge.addHint("Hint " + i);
        }

        assertEquals("Should have " + numHints + " hints", numHints, challenge.getHints().size());
        assertEquals("First hint should match", "Hint 0", challenge.getHints().get(0));
        assertEquals("Last hint should match", "Hint " + (numHints - 1),
            challenge.getHints().get(numHints - 1));
    }

    // ========== addItem() Tests ==========

    /**
     * Tests adding a single valid item.
     */
    @Test
    public void testAddItem_SingleItem() {
        challenge.addItem(testItem1);

        assertEquals("Should have 1 item", 1, challenge.getItems().size());
        assertEquals("Item should match", testItem1, challenge.getItems().get(0));
    }

    /**
     * Tests adding multiple items.
     */
    @Test
    public void testAddItem_MultipleItems() {
        challenge.addItem(testItem1);
        challenge.addItem(testItem2);

        ArrayList<Item> items = challenge.getItems();
        assertEquals("Should have 2 items", 2, items.size());
        assertEquals("First item should match", testItem1, items.get(0));
        assertEquals("Second item should match", testItem2, items.get(1));
    }

    /**
     * Tests adding a null item.
     */
    @Test
    public void testAddItem_NullItem() {
        challenge.addItem(null);

        assertEquals("Should have 1 item (null)", 1, challenge.getItems().size());
        assertNull("Item should be null", challenge.getItems().get(0));
    }

    /**
     * Tests adding duplicate items (same object reference).
     */
    @Test
    public void testAddItem_DuplicateItems() {
        challenge.addItem(testItem1);
        challenge.addItem(testItem1);

        assertEquals("Should have 2 items (duplicates allowed)", 2, challenge.getItems().size());
        assertSame("Both items should be the same reference",
            challenge.getItems().get(0), challenge.getItems().get(1));
    }

    /**
     * Tests the order of items is maintained.
     */
    @Test
    public void testAddItem_OrderMaintained() {
        Item item1 = new Item("First", "First item", "Use1");
        Item item2 = new Item("Second", "Second item", "Use2");
        Item item3 = new Item("Third", "Third item", "Use3");

        challenge.addItem(item1);
        challenge.addItem(item2);
        challenge.addItem(item3);

        ArrayList<Item> items = challenge.getItems();
        assertEquals("First item should be at index 0", item1, items.get(0));
        assertEquals("Second item should be at index 1", item2, items.get(1));
        assertEquals("Third item should be at index 2", item3, items.get(2));
    }

    /**
     * Tests adding many items (stress test).
     */
    @Test
    public void testAddItem_ManyItems() {
        int numItems = 1000;
        ArrayList<Item> addedItems = new ArrayList<>();

        for (int i = 0; i < numItems; i++) {
            Item item = new Item("Item " + i, "Description " + i, "Use " + i);
            addedItems.add(item);
            challenge.addItem(item);
        }

        assertEquals("Should have " + numItems + " items", numItems, challenge.getItems().size());
        assertEquals("First item should match", addedItems.get(0), challenge.getItems().get(0));
        assertEquals("Last item should match", addedItems.get(numItems - 1),
            challenge.getItems().get(numItems - 1));
    }

    /**
     * Tests adding items with various properties.
     */
    @Test
    public void testAddItem_VariousProperties() {
        Item emptyItem = new Item("", "", "");
        Item nullPropertiesItem = new Item(null, null, null);
        Item normalItem = new Item("Normal", "Normal item", "Normal use");

        challenge.addItem(emptyItem);
        challenge.addItem(nullPropertiesItem);
        challenge.addItem(normalItem);

        assertEquals("Should have 3 items", 3, challenge.getItems().size());
        assertEquals("Empty item should be stored", emptyItem, challenge.getItems().get(0));
        assertEquals("Null properties item should be stored", nullPropertiesItem, challenge.getItems().get(1));
        assertEquals("Normal item should be stored", normalItem, challenge.getItems().get(2));
    }

    // ========== Integration Tests ==========

    /**
     * Tests a complete scenario with all operations.
     */
    @Test
    public void testIntegration_CompleteScenario() {
        Challenge c = new Challenge(
            "Find the hidden key",
            "Behind the painting",
            "You found the key! Now open the door."
        );

        // Add hints
        c.addHint("Look at the walls");
        c.addHint("Check behind decorations");
        c.addHint("The painting looks suspicious");

        // Add items
        Item flashlight = new Item("Flashlight", "A bright LED flashlight", "Illuminate");
        Item magnifier = new Item("Magnifying Glass", "A detective's tool", "Examine");
        c.addItem(flashlight);
        c.addItem(magnifier);

        // Verify all components
        assertEquals("Question should be set", "Find the hidden key", c.getQuestion());
        assertEquals("Answer should be set", "Behind the painting", c.getAnswer());
        assertEquals("Post question should be set", "You found the key! Now open the door.", c.getPostQuestion());
        assertEquals("Should have 3 hints", 3, c.getHints().size());
        assertEquals("Should have 2 items", 2, c.getItems().size());
        assertTrue("Should contain flashlight", c.getItems().contains(flashlight));
        assertTrue("Should contain magnifier", c.getItems().contains(magnifier));
    }

    /**
     * Tests that multiple challenges are independent.
     */
    @Test
    public void testIntegration_MultipleChallengesIndependent() {
        Challenge c1 = new Challenge("Question 1", "Answer 1", "Post 1");
        Challenge c2 = new Challenge("Question 2", "Answer 2", "Post 2");

        c1.addHint("Hint for c1");
        c2.addHint("Hint for c2");

        Item item1 = new Item("Item1", "Desc1", "Use1");
        Item item2 = new Item("Item2", "Desc2", "Use2");
        c1.addItem(item1);
        c2.addItem(item2);

        // Verify independence
        assertEquals("c1 should have 1 hint", 1, c1.getHints().size());
        assertEquals("c2 should have 1 hint", 1, c2.getHints().size());
        assertEquals("c1 should have 1 item", 1, c1.getItems().size());
        assertEquals("c2 should have 1 item", 1, c2.getItems().size());

        assertNotEquals("c1 and c2 should have different hints",
            c1.getHints().get(0), c2.getHints().get(0));
        assertNotEquals("c1 and c2 should have different items",
            c1.getItems().get(0), c2.getItems().get(0));
    }

    /**
     * Tests challenge with mixed null and valid values.
     */
    @Test
    public void testIntegration_MixedNullAndValidValues() {
        Challenge c = new Challenge("Valid Question", null, "Valid Post");
        c.addHint("Valid Hint");
        c.addHint(null);
        c.addItem(testItem1);
        c.addItem(null);

        assertEquals("Question should be valid", "Valid Question", c.getQuestion());
        assertNull("Answer should be null", c.getAnswer());
        assertEquals("Post question should be valid", "Valid Post", c.getPostQuestion());
        assertEquals("Should have 2 hints", 2, c.getHints().size());
        assertEquals("Should have 2 items", 2, c.getItems().size());
        assertEquals("First hint should be valid", "Valid Hint", c.getHints().get(0));
        assertNull("Second hint should be null", c.getHints().get(1));
        assertEquals("First item should be valid", testItem1, c.getItems().get(0));
        assertNull("Second item should be null", c.getItems().get(1));
    }
}
