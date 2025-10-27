package com.model;
/**
 * Class to represent the progress of a user in a challenge.
 * It tracks whether the challenge is solved, items collected, and amount of hints used.
 * @author The Clankers
 */
import java.util.ArrayList;
import java.util.HashMap;
/**
 * hashmap of challenge to challenge progress
 * solved questions is a boolean
 * arraylist of items collected
 * number of hints used is an integer
 */
public class ChallengeProgress{
    private HashMap<Challenge, ChallengeProgress> challengeProgress;
    private boolean solved;
    private ArrayList<Item> items;
    private int numHintsUsed;
    

    

}
