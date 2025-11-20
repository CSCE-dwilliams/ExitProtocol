/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package com.theclankers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import com.model.UserList;
import com.theclankers.App;

/**
 *
 * @author portia
 */
public class SignUpController implements Initializable {
    @FXML
    private TextField txt_username;
    @FXML
    private TextField txt_password;
    @FXML
    private TextField txt_first_name;
    @FXML
    private TextField txt_last_name;
    @FXML
    private TextField txt_age;
    @FXML
    private TextField txt_phone_number;
    @FXML
    private Label lbl_error;

    @FXML
    private void btnSignupClicked(MouseEvent event) throws IOException {
        String username = txt_username.getText();
        String password = txt_password.getText();
        String firstName = txt_first_name.getText();
        String lastName = txt_last_name.getText();
        int age;
        String phoneNumber = txt_phone_number.getText();

        try {
            age = Integer.parseInt(txt_age.getText());
        } catch (Exception e) {
            lbl_error.setText("Sorry, you must enter a valid age.");
            return;
        }

        // check for empty fields
        if (username.equals("") || password.equals("") || firstName.equals("") || lastName.equals("")
                || phoneNumber.equals("")) {
            lbl_error.setText("Sorry, You cannot leave blank fields");
            return;
        }

        Library library = Library.getInstance();

        if (!library.createAccount(username, firstName, lastName, age, phoneNumber)) {
            lbl_error.setText("Sorry, this user couldn't be created.");
            return;
        }

        library.login(username);
        User user = library.getCurrentUser();
        App.setRoot("user_home");
    }

    @FXML
    private void back(MouseEvent event) throws IOException {
        App.setRoot("home");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}