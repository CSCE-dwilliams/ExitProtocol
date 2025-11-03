package com.model;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class ChallengeTest {

	@Test
	public void testConstructorAndGetters() {
		String q = "What is 2+2?";
		String a = "4";
		String post = "Proceed to next room";

		Challenge c = new Challenge(q, a, post);

		assertEquals("Question should match", q, c.getQuestion());
		assertEquals("Answer should match", a, c.getAnswer());
		assertEquals("Post question should match", post, c.getPostQuestion());

		// hints and items should start empty
		assertNotNull("Hints list should not be null", c.getHints());
		assertTrue("Hints should initially be empty", c.getHints().isEmpty());
		assertNotNull("Items list should not be null", c.getItems());
		assertTrue("Items should initially be empty", c.getItems().isEmpty());
	}

	@Test
	public void testAddHintAndGetHints() {
		Challenge c = new Challenge("q", "a", "p");

		c.addHint("first hint");
		ArrayList<String> hints = c.getHints();

		assertEquals("Hints size after one add", 1, hints.size());
		assertEquals("Hint content matches", "first hint", hints.get(0));

		// adding another hint via addHint
		c.addHint("second hint");
		assertEquals(2, c.getHints().size());
		assertEquals("second hint", c.getHints().get(1));
	}

	@Test
	public void testGetHintsReturnsLiveList() {
		Challenge c = new Challenge("q", "a", "p");
		c.addHint("h1");

		// mutate the returned list directly
		ArrayList<String> hints = c.getHints();
		hints.add("h2");

		// since getHints returns the internal list, changes should be visible
		assertEquals("Direct mutation should affect internal list", 2, c.getHints().size());
		assertEquals("h2", c.getHints().get(1));
	}

	@Test
	public void testAddItemAndGetItems() {
		Challenge c = new Challenge("q", "a", "p");

		Item it = new Item("Key", "Opens door", "use on door");
		c.addItem(it);

		assertEquals("Items size after add", 1, c.getItems().size());
		assertSame("Stored item should be the same instance", it, c.getItems().get(0));

		// verify we can add another and order is preserved
		Item it2 = new Item("Map", "Shows layout", "view map");
		c.addItem(it2);
		assertEquals(2, c.getItems().size());
		assertSame(it2, c.getItems().get(1));
	}

	@Test
	public void testAddNullHintAllowed() {
		Challenge c = new Challenge("q", "a", "p");
		c.addHint(null);
		assertEquals("Adding null hint increases size", 1, c.getHints().size());
		assertNull("Stored hint is null", c.getHints().get(0));
	}

}
