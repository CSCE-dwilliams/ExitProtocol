package com.model;
/**
 * Class to represent the progress of a user in a challenge.
 * It tracks whether the challenge is solved, items collected, and amount of hints used.
 * @author The Clankers
 */
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Represents a user's progress in completing challenges.
 * This class tracks whether a challenge has been solved, the number of hints used,
 * items obtained, and progress in related sub-challenges. It is useful for
 * representing complex or multi-part challenge structures.
 * @author Clankers
/**
 * hashmap of challenge to challenge progress
 * solved questions is a boolean
 * arraylist of items collected
 * number of hints used is an integer

 */
public class ChallengeProgress{
    /**
     * Stores progress for sub-challenges related to this challenge.
     * Maps each Challenge to its corresponding ChallengeProgress.
     * Useful for tracking progress in multi-part challenges.
     */
    private HashMap<Challenge, ChallengeProgress> challengeProgress;

    /**
     * Indicates whether this challenge has been solved by the player.
     */
    private boolean solved;
    private int numHintsUsed;
    private ArrayList<String> questionSet = new ArrayList<>();
    private ArrayList<String> answerSet = new ArrayList<>();
    private ArrayList<ArrayList<String>> hintSet = new ArrayList<>();
    private ArrayList<String> postQuestionSet = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    

    

}
