package com.theclankers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.EscapeManager;
import com.model.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SessionCreateController implements Initializable {

    @FXML
    TextField teamName;
    @FXML
    Button startBtn;

    private EscapeManager manager;
    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = EscapeManager.getInstance();
        user = manager.getCurrentUser();
    }

    public void btnCreateGame(MouseEvent event) throws IOException {
        String tmName = teamName.getText();
        manager.createGame(tmName, "Historical", 2, 2);
        System.out.println(manager.createGame(tmName, "Historical", 2, 2));
        System.out.println(manager.getCurrentGame().getIntro());
        App.setRoot("GameIntro");
    }
}