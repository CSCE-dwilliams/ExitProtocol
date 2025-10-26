package com.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the progress of a user in completing challenges.
 * 
 * This class also tracks whether a challenge has been solved,
 * the number of hints used by the user, any items obtained during the challenge,
 * and maintains progress for related sub-challenges.
 * @author Clankers
 */
public class ChallengeProgress{
    /**
     * Stores progress for sub-challenges related to this challenge.
     *  Maps each {@link Challenge} to its corresponding {@link ChallengeProgress}.
     * Useful for tracking progress in multi-part challenges.
     */
    private HashMap<Challenge, ChallengeProgress> challengeProgress;

    /**
     * Indicates whether this challenge has been solved by the player.
     */
    private boolean solved;

    /**
     * A list of items collected while working on this challenge.
     */
    private ArrayList<Item> items;
    /**
     * The number of hints that the players used for a challenge.
     */
    private int numHintsUsed;
    

    

}
