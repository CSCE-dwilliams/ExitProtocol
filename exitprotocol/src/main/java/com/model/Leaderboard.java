package com.model;
import java.util.HashMap;
import java.util.UUID;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

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

    public void sortScores()
    {
        SortedSet<Integer> ranking = new TreeSet<>(scoreSet.values());
        for(Integer ranks: ranking ){
            System.out.println(ranks);
        }
    }//Might need to change how this works
}
