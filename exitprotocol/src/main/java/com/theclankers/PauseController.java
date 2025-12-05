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
import javafx.scene.input.MouseEvent;

public class PauseController implements Initializable {

    @FXML
    Button btnResume;
    @FXML
    Button btnQuit;

    private EscapeManager manager;
    private User user;
    private Game game;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = EscapeManager.getInstance();
        user = manager.getCurrentUser();
        game = manager.getCurrentGame();
    }

    public void backToGameBtn(MouseEvent event) throws IOException {
        App.setRoot("baseGameState");
    }

    public void saveGameBtn(MouseEvent event) throws IOException {

    }

}