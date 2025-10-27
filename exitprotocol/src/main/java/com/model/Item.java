package com.model;

/**
 * The Item class represents an item with a name and description.
 * It provides methods to retrieve these details.
 *
 * This class can be used to model items in an inventory, catalog, or similar system.
 *
 * @author Clankers
 */
public class Item {
    private String itemName;
    private String itemDesc;
    private String itemUseCase;

    /**
     * Constructs a new Item with the specified name and description.
     *
     * @param name the name of the item
     * @param description the description of the item
     */
    public Item(String name, String description, String itemUseCase) {
        this.itemName = name;
        this.itemDesc = description;
        this.itemUseCase = itemUseCase;
    }
    /**
     * Return the name of the item
     * @return the item name
     */
    public String getName(){
        return itemName;
    }
    /**
     * Returns the description of item
     * @return the item description
     */
    public String getDescription(){
        return itemDesc;
    }
    public String getUseCase(){
        return itemUseCase;
    }
    
}
