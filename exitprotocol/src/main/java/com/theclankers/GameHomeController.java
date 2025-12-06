package com.theclankers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.EscapeManager;
import com.model.User;
import com.model.Game;
import com.model.Item;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GameHomeController implements Initializable {

    @FXML
    Label teamNameBox;
    @FXML
    Label scoreBox;
    @FXML
    Circle pauseBtn;
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

    @FXML
    Button item1;
    @FXML
    Button item2;
    @FXML
    Button item3;

    private Circle[] buttons;
    private EscapeManager manager;
    private User user;
    private Game game;
    private Button[] items;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = EscapeManager.getInstance();
        user = manager.getCurrentUser();
        game = manager.getCurrentGame();
        buttons = new Circle[] { btnQ1, btnQ2, btnQ3, btnQ4, btnQ5, btnQ6 };
        items = new Button[] { item1, item2, item3 };
        String currentScore = Integer.toString(game.getScore());
        String teamName = manager.getCurrentSession().getTeamName();

        teamNameBox.setText(teamName);
        scoreBox.setText(currentScore);
        buttonVisibility();
        displayAvailableItems();
        // item visibility

    }

    public void switchPause(MouseEvent event) throws IOException {
        App.setRoot("pauseScreen");
    }

    private void displayAvailableItems() {
        // Only display earned items (persistent across questions)
        ArrayList<Item> earnedItems = manager.getEarnedItems();

        // Show buttons for available items
        for (int i = 0; i < items.length; i++) {
            if (i < earnedItems.size()) {
                Item item = earnedItems.get(i);
                items[i].setText(item.getName());
                items[i].setVisible(true);
            } else {
                items[i].setVisible(false);
            }
        }
    }

    private void buttonVisibility() {
        int currentIndex = game.getPlayerIndex();

        // Make all buttons up to current index visible and blue, except the current one
        for (int i = 0; i <= currentIndex; i++) {
            buttons[i].setVisible(true);
            if (i < currentIndex) {
                buttons[i].setFill(Color.BLACK);
            }
            // Current button keeps its default color
        }

        // Hide all buttons after current index
        for (int i = currentIndex + 1; i < buttons.length; i++) {
            buttons[i].setVisible(false);
        }
    }

    public void handleBtn(MouseEvent event) throws IOException {
        Circle clickedButton = (Circle) event.getSource();
        for (int i = 0; i < buttons.length; i++) {
            if (clickedButton == buttons[i]) {
                game.setPlayerIndex(i);
                App.setRoot("questiontemplate");
            }
        }
    }

    public void switchItemBtn(MouseEvent event) throws IOException {
        App.setRoot("itemSelect");
    }

    public void primaryGameLoop() {

        while (game.getPlayerIndex() >= 0) {

            if (game.getItems().size() > 0) {
                // logic for populating items here
            }

        }

    }

}