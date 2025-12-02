package com.theclankers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController implements Initializable {
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_password;
    @FXML
    private Label lbl_error;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    private void onBtnClicked(MouseEvent event) throws IOException {
        String email = txt_email.getText();
        String password = txt_password.getText();

    }

}

// can debate about whether or not to include 'back' button
