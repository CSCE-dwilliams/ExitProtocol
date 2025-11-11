package com.theclankers;

import java.io.IOException;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void switchToSignInScreen() throws IOException {
        System.exit(0);
    }

    @FXML
    private void switchToCreateAccountScreen() throws IOException {
        System.exit(0);
    }
}
