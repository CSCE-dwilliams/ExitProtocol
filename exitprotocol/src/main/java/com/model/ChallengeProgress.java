package com.model;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Represents a user's progress in completing challenges.
 * This class tracks whether a challenge has been solved, the number of hints used,
 * items obtained, and progress in related sub-challenges. It is useful for
 * representing complex or multi-part challenge structures.
 * @author Clankers
 */
public class ChallengeProgress{
    /**
     * Stores progress for sub-challenges related to this challenge.
     * Maps each Challenge to its corresponding ChallengeProgress.
     * Useful for tracking progress in multi-part challenges.
     */
    private HashMap<Challenge, ChallengeProgress> challengeProgress;
    private boolean solved;
    private int numHintsUsed;
    private ArrayList<String> questionSet = new ArrayList<>();
    private ArrayList<String> answerSet = new ArrayList<>();
    private ArrayList<ArrayList<String>> hintSet = new ArrayList<>();
    private ArrayList<String> postQuestionSet = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    

    

}
