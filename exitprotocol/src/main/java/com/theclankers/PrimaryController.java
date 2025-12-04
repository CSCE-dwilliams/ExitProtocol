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
        App.setRoot("login");
    }

    @FXML
    private void switchToCreateAccountScreen() throws IOException {
        App.setRoot("createAccount");
    }

    // USE THIS TO DEMO ANY DESIRED SCREEN
    @FXML
    private void testScreen() throws IOException{
        App.setRoot("Create session");
    }
    @FXML
    private void testBtnClick() throws IOException {
        App.setRoot("");
    }
}
