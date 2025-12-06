package com.theclankers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.EscapeManager;
import com.model.Game;
import com.model.Challenge;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class QuestionCorrectController implements Initializable {
    @FXML
    Button continueBtn;
    @FXML
    Text postQuestionText;

    private EscapeManager manager;
    private Game game;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = EscapeManager.getInstance();
        game = manager.getCurrentGame();
        // Get the challenge that was just answered (playerIndex was incremented after
        // correct answer)
        int justAnsweredIndex = game.getPlayerIndex() - 1;
        Challenge justAnsweredChallenge = game.getChallenges().get(justAnsweredIndex);

        String postText = justAnsweredChallenge.getPostQuestion();
        postQuestionText.setText(postText);
    }

    public void continueGame(MouseEvent event) throws IOException {
        if (game.getPlayerIndex() >= 5) {
            App.setRoot("sessionCompletion");
        }
        App.setRoot("baseGameState");
    }

}