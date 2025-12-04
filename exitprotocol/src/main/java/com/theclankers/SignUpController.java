package com.theclankers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.EscapeManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SignUpController implements Initializable {
    @FXML
    private TextField newEmail;
    @FXML
    private TextField newFirstName;
    @FXML
    private TextField newLastName;
    @FXML
    private TextField newPass;
    @FXML
    private ChoiceBox<Integer> avatarSelect;
    @FXML
    private Label lbl_error;

    @FXML
    private void btnSignupClicked(MouseEvent event) throws IOException {
        String firstName = newFirstName.getText();
        String lastName = newLastName.getText();
        String email = newEmail.getText();
        String password = newPass.getText();
        int avatarChoice = avatarSelect.getValue();
        System.out.println(avatarChoice);

        EscapeManager manager = EscapeManager.getInstance();
        if (manager.emailAlreadyExists(email)) {
            lbl_error.setVisible(true);
            return;
        }
        manager.createAccount(firstName, lastName, email, password, avatarChoice);

        App.setRoot("themeselection");
    }

    @FXML
    private void onBackBtn(MouseEvent event) throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        avatarSelect.getItems().addAll(1, 2, 3, 4);

    }

}