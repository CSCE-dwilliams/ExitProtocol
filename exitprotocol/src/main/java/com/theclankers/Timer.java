package com.theclankers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Timer {
    private static Scene scene;
    private static int timeLimit =50;
    private static Timeline timeline;

    public static void play(){
        timeline.play();
    }
    public static void pause(){
        timeline.pause();
    }

    public static void resetTimer(){
        timeline.stop();
    }
    public static void timerStart(Stage stage) {

        Label timerLabel = new Label("Time: " + timeLimit);
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    timeLimit--;
                    timerLabel.setText("Time: " + timeLimit);
                    System.out.println("Seconds remaining: " + timeLimit);
                    if (timeLimit <= 0) {
                        System.out.println("Time's up!");
                    }
                }));

        timeline.setCycleCount(timeLimit); // run until 0
        timeline.play();
        
        scene = new Scene(new StackPane(timerLabel),200,100);
        stage.setScene(scene);
        stage.show();
    }

    public static void reduceTimeRemaining(int demount){
        timeLimit -= demount;
    }    



}


