package com.theclankers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.EscapeManager;
import com.model.User;
import com.model.Game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class GameHomeController implements Initializable {

    private EscapeManager manager;
    private User user;
    private Game game;

    @FXML
    Label teamNameBox;

    @FXML
    Label scoreBox;

    @FXML
    Circle btnQ1;
    @FXML
    Circle btnQ2;
    @FXML
    Circle btnQ3;
    @FXML
    Circle btnQ4;
    @FXML
    Circle btnQ5;
    @FXML
    Circle btnQ6;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = EscapeManager.getInstance();
        user = manager.getCurrentUser();
        game = manager.getCurrentGame();

        String currentScore = Integer.toString(game.getScore());
        String teamName = manager.getCurrentSession().getTeamName();

        teamNameBox.setText(teamName);
        scoreBox.setText(currentScore);

        // visibility of buttons AND items is determined here

    }

    public void buttonVisibility() {
        // for (int i = 0; i < game.getPlayerIndex(); i++) {

        // }
        // switch (game.getPlayerIndex()) {
        // case 0:
        // btnQ1.setVisible(true);
        // break;
        // case 1:

        // }

    }

    // public void primaryGameLoop() {

    // while (game.getPlayerIndex() >= 0) {

    // if (game.getItems().size() > 0) {
    // // logic for populating items here
    // }

    // }

    // }

}