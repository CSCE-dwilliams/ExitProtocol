package com.model;
import javafx.animation.PauseTransition;
/**
 * maybe this class could be singleton? i could see a lot of methods being static
 */
public class Timer {
    private int timeRemaining;
    private boolean paused;

    public Timer(){
        this.timeRemaining = 300;
        this.paused = false;
    }

    public boolean ispaused(){
        return this.paused;
    }

    public void pause(){
        this.paused = true;
    }

    public void getPaused(boolean paused){
        this.paused = paused;


    }
    public int getTimeRemaining(){
        return this.timeRemaining;

    }
    public void unpause(){
        this.paused = false;
    }
    public void decrementTime(){
        this.timeRemaining -= 1;
        }
    }

