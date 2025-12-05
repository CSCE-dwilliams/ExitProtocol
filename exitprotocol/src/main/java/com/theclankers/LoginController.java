package com.theclankers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.EscapeManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class LoginController implements Initializable {
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_password;
    @FXML
    private Text lbl_error;

    @FXML
    public void onBtnClicked(MouseEvent event) throws IOException {
        System.out.println(">>> Button clicked!");
        String email = txt_email.getText();
        String password = txt_password.getText();

        EscapeManager manager = EscapeManager.getInstance();
        System.out.println(manager.signIn(email, password));
        System.out.println("email: " + email + " pass: " + password);
        if (!manager.signIn(email, password)) {
            lbl_error.setText("Invalid login credentials");
            lbl_error.setVisible(true); // Make it visible
            return;
        }
        App.setRoot("themeSelection");
    }

    public void onBackBtn(MouseEvent event) throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}

// can debate about whether or not to include 'back' button
