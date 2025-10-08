package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

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

    }

    public void sortScore()
    {

    }
}
