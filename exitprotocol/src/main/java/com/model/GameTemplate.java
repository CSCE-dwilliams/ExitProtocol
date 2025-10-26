package com.model;

import java.util.ArrayList;

public class GameTemplate {
    private ArrayList<Challenge> challenges = new ArrayList<>();
    private String gameTheme;
    private String intro;

    public GameTemplate(String theme, String intro){
        this.gameTheme = theme;
        this.intro = intro;
    }

    public String getTheme(){
        return gameTheme;
    }
    public void getIntro(){
        String introFull = "\n[Introduction].....\n****************************************\n" + intro 
        + "\n****************************************\n";
        System.out.println(introFull);
        // TTSUtils.speak(intro);
    }
    public ArrayList<Challenge> getChallenges(){
        return challenges;
    }
    public void addChallenge(Challenge addChallenge){
        challenges.add(addChallenge);
    }

}