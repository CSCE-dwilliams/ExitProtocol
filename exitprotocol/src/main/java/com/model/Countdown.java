package com.model;

import javafx.animation.PauseTransition;
import java.util.Timer;
import java.util.TimerTask;
/**
 * maybe this class could be singleton? i could see a lot of methods being static
 */
public class Countdown {
    private Timer timer;
    
    private boolean isPaused = false;
    private int timeLimit = 900;
    private int timeRemaining;
    private static int initialTime = 1800;

    public Countdown(){
        this.timeRemaining = initialTime;
        timer = new Timer();
    }
    
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

    public void pause(){
        isPaused = true;
    }

    public void resume(){
        isPaused = false;
    }

    public void player(){
        //Not sure what this is needed for will leave it just in case
    }
    public void resetTimer(){
        timeRemaining = timeLimit;
    }
    public void reduceTimeRemaining(){
        timeRemaining--;
    }
    public void reduceTimeRemaining(int difficulty){
        timeRemaining = (timeRemaining - difficulty);
    }
}
