package com.theclankers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.EscapeManager;
import com.model.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class IntroController implements Initializable {

    @FXML
    Text introText;

    @FXML
    Button startGame;
    private EscapeManager manager;
    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = EscapeManager.getInstance();
        user = manager.getCurrentUser();
        String text = manager.getCurrentGame().getIntro();

        introText.setText(text);
    }

    public void btnStart(MouseEvent event) throws IOException {
        App.setRoot("baseGameState");
    }
}