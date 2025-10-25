package com.model;

public class Item {
    private String itemName;
    private String itemDesc;

    public Item(String name, String description) {
        this.itemName = name;
        this.itemDesc = description;
    }
    public String getName(){
        return itemName;
    }
    public String getDescription(){
        return itemDesc;
    }
}
