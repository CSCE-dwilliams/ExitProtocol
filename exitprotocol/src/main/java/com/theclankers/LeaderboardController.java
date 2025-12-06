package com.theclankers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.Leaderboard;
import com.model.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class LeaderboardController implements Initializable {
    @FXML
    Text teamName1;
    @FXML
    Text teamName2;
    @FXML
    Text teamName3;
    @FXML
    Text teamName4;
    @FXML
    Text teamName5;
    @FXML
    Text score1;
    @FXML
    Text score2;
    @FXML
    Text score3;
    @FXML
    Text score4;
    @FXML
    Text score5;
    @FXML
    Button backButton;

    private Text[] teamNames;
    private Text[] teamScores;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teamNames = new Text[] { teamName1, teamName2, teamName3, teamName4, teamName5 };
        teamScores = new Text[] { score1, score2, score3, score4, score5 };

        // Load and display leaderboard
        displayLeaderboard();
    }

    private void displayLeaderboard() {
        // Get Leaderboard instance and reload user data
        Leaderboard leaderboard = new Leaderboard();

        // Sort users by total score
        leaderboard.sortByScore();

        // Get sorted users and display top 5
        var users = leaderboard.getUsers();
        int displayCount = Math.min(5, users.size());

        for (int i = 0; i < 5; i++) {
            if (i < displayCount) {
                User user = users.get(i);
                int totalScore = leaderboard.calculateTotalScore(user);
                String teamName = user.getFirstName() + " " + user.getLastName();

                teamNames[i].setText(teamName);
                teamScores[i].setText(String.valueOf(totalScore));
                teamNames[i].setVisible(true);
                teamScores[i].setVisible(true);
            } else {
                // Hide unused slots
                teamNames[i].setVisible(false);
                teamScores[i].setVisible(false);
            }
        }
    }

    public void backBtn(MouseEvent event) throws IOException {
        App.setRoot("themeSelection");
    }
}