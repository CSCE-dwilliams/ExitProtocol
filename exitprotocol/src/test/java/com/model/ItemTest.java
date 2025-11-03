package com.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemTest {
    private Item item;

    @Before
    public void setUp() {
        // create a sample item before each test
        item = new Item("Sword", "A sharp blade used for combat.", "Used to attack enemies");
    }

    @Test
    public void testConstructorAndGetters() {
        assertNotNull("Item should not be null after construction", item);
        assertEquals("Item name should match", "Sword", item.getName());
        assertEquals("Item description should match", "A sharp blade used for combat.", item.getDescription());
        assertEquals("Item use case should match", "Used to attack enemies", item.getUseCase());
    }

    @Test
    public void testDifferentValues() {
        Item potion = new Item("Potion", "Restores health points.", "Used to heal player");
        assertEquals("Potion", potion.getName());
        assertEquals("Restores health points.", potion.getDescription());
        assertEquals("Used to heal player", potion.getUseCase());
    }

    @Test
    public void testEmptyStringsAllowed() {
        Item blankItem = new Item("", "", "");
        assertEquals("", blankItem.getName());
        assertEquals("", blankItem.getDescription());
        assertEquals("", blankItem.getUseCase());
    }
}
