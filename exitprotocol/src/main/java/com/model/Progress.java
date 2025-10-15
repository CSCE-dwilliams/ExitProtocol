package com.model;

import java.util.UUID;
public class Progress {
    
    private Progress gameProgress;
    private int skippedChallenges;
    private int challengesCompleted;
    private int hintsUsed;
    private int IncorrectAnswers;
    private int currentScore;
    private UUID id;
    private String teamName;

    public Progress()
    {
        this.skippedChallenges = skippedChallenges;
        this.challengesCompleted = challengesCompleted;
        this.hintsUsed = hintsUsed;
        this.IncorrectAnswers = IncorrectAnswers;
        this.currentScore = currentScore;
        this.id = id;
        this.teamName = teamName;
    }

    public void getProgress()
    {

    }

}
