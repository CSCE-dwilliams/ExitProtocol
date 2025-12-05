package com.theclankers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.EscapeManager;
import com.model.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class UserThemeSelect implements Initializable {

    @FXML
    private Button medievalResume;
    @FXML
    private Button mysteryResume;
    @FXML
    private Button historicalResume;
    @FXML
    private Button btnPlayHistorical;
    @FXML
    private Button btnPlayMystery;
    @FXML
    private Button btnPlayMedieval;

    private EscapeManager manager;
    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = EscapeManager.getInstance();
        user = manager.getCurrentUser();

        if (checkSession("Historical")) {
            historicalResume.setVisible(true);
        }
        if (checkSession("Mystery")) {
            mysteryResume.setVisible(true);
        }
        if (checkSession("Medieval")) {
            medievalResume.setVisible(true);
        }

    }

    public void btnHistoricalResume(MouseEvent event) throws IOException {
        manager.selectExistingGame("Historical");
        App.setRoot("baseGameState");
    }

    public void btnMysteryResume(MouseEvent event) throws IOException {

    }

    public void btnMedievalResume(MouseEvent event) throws IOException {

    }

    public void btnNewMystery(MouseEvent event) throws IOException {

    }

    public void btnNewMedieval(MouseEvent event) throws IOException {

    }

    public void btnNewHistorical(MouseEvent event) throws IOException {

        App.setRoot("createSession");
    }

    private boolean checkSession(String theme) {
        if (theme == null) {
            return false;
        }
        return manager.selectExistingGame(theme);
    }

}