package com.model;

public class Item {
    private String itemName;
    private String itemDesc;
    private String itemUseCase;

    public Item(String name, String description, String itemUseCase) {
        this.itemName = name;
        this.itemDesc = description;
        this.itemUseCase = itemUseCase;
    }
    public String getName(){
        return itemName;
    }
    public String getDescription(){
        return itemDesc;
    }
    public String getUseCase(){
        return itemUseCase;
    }
    
}
