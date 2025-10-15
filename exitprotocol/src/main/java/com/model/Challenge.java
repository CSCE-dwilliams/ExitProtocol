package com.model;

import java.util.HashMap;
import java.util.UUID;

public class Challenge {
    HashMap<String, Integer> scoreSet = new HashMap<>();

    public void addScore(UUID id, Integer score)
    {
        String idString = id.toString();
        scoreSet.put(idString,score);
    }   

    public Integer getScore(UUID id)
    {
        String idString = id.toString();
        Integer Score = scoreSet.get(idString);
        
        return Score;
    }
}
