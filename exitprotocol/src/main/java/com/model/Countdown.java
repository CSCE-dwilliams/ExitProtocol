package com.model;

import javafx.animation.PauseTransition;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Represents a countdown timer with support for pausing, resuming, and alerts
 * at specific time intervals.
 * 
 * The timer prints console messages when certain thresholds are reached.
 * maybe this class could be singleton? i could see a lot of methods being static
 * 
 * @author Clankers
 */
public class Countdown {
    private Timer timer;
    
    private boolean isPaused = false;
    private int timeLimit = 900;
    private int timeRemaining;
    private static int initialTime = 1800;

    /**
     * Constructs a new Countdown timer.
     * Initializes the remaining time to the initialTime value and prepares the timer.
     */
    public Countdown(){
        this.timeRemaining = initialTime;
        timer = new Timer();
    }
    
    /**
     * Starts the countdown timer.
     * The timer updates every second and prints a notification at crutial intervals:
     * 10 min, 5 min, 3 min, 2 min, and when 1 minute is remaining.
     */
    public void startTimer(){
        resetTimer();
        timer.scheduleAtFixedRate(new TimerTask() {
            
            @Override
            public void run() {
                
                if(isPaused = false){
                    reduceTimeRemaining();

                    if(timeRemaining == 600)
                {
                    System.out.println("10 Minutes Remaining!");
                }
                
                if(timeRemaining == 300)
                {
                    System.out.println("5 Minutes Remaining!");
                }

                if(timeRemaining == 180)
                {
                    System.out.println("3 Minutes Remaining!");
                }

                if(timeRemaining == 120)
                {
                    System.out.println("2 Minutes Remaining!");
                }

                if(timeRemaining == 60)
                {
                    System.out.println("1 Minute Remaining, Time's Almost Up!");
                }

                if(timeRemaining <= 0) {
                    timer.cancel();
                    System.out.println("Time's Up!");
                }
                }
            }
            }, 0, 1000);
    }

    /**
     * Pauses the count down timer.
     */
    public void pause(){
        isPaused = true;
    }

    /**
     * Resumes the countdown timer if paused.
     */
    public void resume(){
        isPaused = false;
    }

    public void player(){
        //Not sure what this is needed for will leave it just in case
    }

    /**
     * Resets timer once limit is reached.
     */
    public void resetTimer(){
        timeRemaining = timeLimit;
    }

    /**
     * Reduces time when player answers a question incorrectly.
     */
    public void reduceTimeRemaining(){
        timeRemaining--;
    }
    public void reduceTimeRemaining(int difficulty){
        timeRemaining = (timeRemaining - difficulty);
    }
}
