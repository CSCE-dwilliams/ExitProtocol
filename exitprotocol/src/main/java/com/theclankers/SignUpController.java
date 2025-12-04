/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package com.theclankers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.EscapeManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author portia
 */
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
        String email = newEmail.getText();
        String firstName = newFirstName.getText();
        String lastName = newLastName.getText();
        int avatarChoice = avatarSelect.getValue();
        System.out.println(avatarChoice);

        EscapeManager manager = EscapeManager.getInstance();
        if (manager.emailAlreadyExists(email)) {
            lbl_error.setVisible(true);
            return;
        }
        manager.createAccount(firstName, firstName, email, lastName, avatarChoice);

        App.setRoot("themeselection");
    }

    @FXML
    private void back(MouseEvent event) throws IOException {
        App.setRoot("home");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        avatarSelect.getItems().addAll(1, 2, 3, 4);

    }

}