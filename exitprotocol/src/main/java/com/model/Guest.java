package com.model;

import java.util.UUID;

public class Guest{
    private String guestAlias;
    private Integer score;

    public void checkCredentials(){
        
    }

    public UUID getSessionData(){
        UUID sessionID = new UUID(5,5);
        return sessionID;
    }

    
}