package com.model;

import java.util.HashMap;
import java.util.UUID;
import java.util.Comparator;
import java.util.Collection;

public class Leaderboard {

    private HashMap<UUID, Integer> scoreSet = new HashMap<>();

    public void addScore(UUID id, Integer score) {
        this.scoreSet.put(id, score);
    }

    public Integer getScore(UUID id)
    {
        return this.scoreSet.get(id);
    }

    public void getScores()
    {
        Collection<Integer> scores = scoreSet.values();
        for(Integer score : scores)
        {
            System.out.println(score);
        }

    }

    public void sortScore()
    {
        
    }
}
