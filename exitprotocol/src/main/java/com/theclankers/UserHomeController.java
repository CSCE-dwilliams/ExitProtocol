package com.theclankers;

import java.net.URL;
import java.util.ResourceBundle;

import com.model.EscapeManager;
import com.model.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 *
 * @author portia
 */
public class UserHomeController implements Initializable {

    @FXML
    private Button medievalResume;
    @FXML
    private Button mysteryResume;
    @FXML
    private Button historicalResume;

    private EscapeManager manager;
    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = EscapeManager.getInstance();
        user = manager.getCurrentUser();
        actionStatus();

        System.out.println("testing");
        System.out.println(manager.selectExistingGame("Historical"));
        if (manager.selectExistingGame("Historical")) {
            historicalResume.setVisible(true);
        }
        if (manager.getCurrentUser().getSession("Mystery") != null) {
            if (manager.selectExistingGame("Mystery")) {
                mysteryResume.setVisible(true);

            }
        }

    }

    private void actionStatus() {
        System.out.println(manager.getCurrentUser());

    }

}