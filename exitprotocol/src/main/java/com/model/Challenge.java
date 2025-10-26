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
/**
 * Represents a challenge that includes a set of hints, clues,
 * and a score system for players identified by UUIDS.
 * 
 * This class manages player scores, challenge hints, and an image clue
 * that can be used to help solve a challenge.
 * 
 * @author Clankers
 */
public class Challenge {
    /**Stores the score of each participant, mapped by their UUID. */
    HashMap<UUID, Integer> scoreSet = new HashMap<>();
    /**The current hint being displayed or used.*/
    public String hint;
    /**The image clue associated with the challenge. */
    public Image clue;
    /**A list of all hints available for this challenge. */
    public ArrayList<String> hints;

    /**
     * Constructs a new {@code Challenge} with the specified list of hints and clue image.
     * @param hints a list of hint strings to assist in solvinf the challenge
     * @param clue an image representing the visual clue for the challenge
     */
    public Challenge(ArrayList<String> hints, Image clue){

    }

    /**
     * Adds or updates a players score in the challenge.
     * @param id the identifier of a player
     * @param score the score associated with that player
     */
    public void addScore(UUID id, Integer score)
    {
        scoreSet.put(id,score);
    }

    /**
     * Retrives the score for a specific player.
     * @param id the identifier of a player
     * @return the players score, or {@code null} if no score is recorded
     */
    public Integer getScore(UUID id)
    {
        return scoreSet.get(id);
    }

    /**
     * Returns the list of all hints associated with this challenge.
     * @return an {@code ArrayList<String>} of hints
     */
    public ArrayList<String> getHints()
    {
        return this.hints;
    }

    /**
     * Adds a new hint to the list of available hints.
     * @param hint the hint string to add
     */
    public void addHint(String hint)
    {
        hints.add(hint);
    }

    /**
     * Retrives the current or last hint used.
     * @return the current hint string
     */
    public String getHint()
    {
        return this.hint;
    }

    /**
     * Sets the image clue for this challenge.
     * @param theClue the {@code Image} to be used as the clue
     */
    public void setClue(Image theClue)
    {
        this.clue = theClue;
    }

    /**
     * Returns the image clue associated with the appropriate challenge.
     * @return the {@code Image} clue
     */
    public Image getClue()
    {
        return this.clue;
    }
}
