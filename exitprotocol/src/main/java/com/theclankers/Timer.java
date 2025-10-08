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
    private static int secondsRemaining =10;

    public static void timerStart(Stage stage) {

        Label timerLabel = new Label("Time: " + secondsRemaining);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    secondsRemaining--;
                    timerLabel.setText("Time: " + secondsRemaining);
                    System.out.println("Seconds remaining: " + secondsRemaining);
                    if (secondsRemaining <= 0) {
                        System.out.println("Time's up!");
                    }
                }));

        timeline.setCycleCount(secondsRemaining); // run until 0
        timeline.play();
        
        scene = new Scene(new StackPane(timerLabel),200,100);
        stage.setScene(scene);
        stage.show();
    }
}


