package com.model;

import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;
import javafx.scene.image.Image;
public abstract class Challenge {
    private HashMap<UUID, Integer> scoreSet;
    private String hint;
    private Item requiredItem;
    private Item rewardedItem;
    private Image clue;
    private ArrayList<String> hints;

    public Challenge(ArrayList<String> hints, Image clue) {
        this.scoreSet = new HashMap<>();
        this.hints = hints;
        this.clue = clue;
        this.requiredItem = null;
        this.rewardedItem = null;
    }

    public boolean requiresItem() {
        return this.requiredItem != null;
    }

    public Item getRequiredItem() {
        return this.requiredItem;
    }

    public boolean rewardsItem() {
        return this.rewardedItem != null;
    }

    public Item getRewardedItem() {
        return this.rewardedItem;
    }

    public void setRewardedItem(Item rewardedItem) {
        this.rewardedItem = rewardedItem;
    }

    public void addScore(UUID id, Integer score)
    {
        scoreSet.put(id,score);
    }

    public Integer getScore(UUID id)
    {
        return scoreSet.get(id);
    }

    public ArrayList<String> getHints()
    {
        return this.hints;
    }

    public void addHint(String hint)
    {
        hints.add(hint);
    }

    public String getHint()
    {
        return this.hint;
    }

    public void setClue(Image theClue)
    {
        this.clue = theClue;
    }

    public Image getClue()
    {
        return this.clue;
    }
}
