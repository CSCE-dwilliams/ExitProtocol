package com.model;

public class EscapeManager {
    private Leaderboard leaderboard;
    //private Timer timer;
    // private Avatar avatar;
    // private Theme theme;
    // private GameSession gameSession;
    private User user;
    private static EscapeManager escapeManager;
    private EscapeManager() {}


    public static EscapeManager getInstance(){
        if(escapeManager ==null){
            escapeManager = new EscapeManager();
        }
        return escapeManager;
    }
    
    public void logIn(){
        Driver.signInStart();
    }
    

}
