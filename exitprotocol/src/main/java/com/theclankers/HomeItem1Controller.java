package com.theclankers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class HomeItem1Controller implements Initializable {

    @FXML
    Button backButton;

    public void backBtn(MouseEvent event) throws IOException {
        App.setRoot("baseGameState");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}