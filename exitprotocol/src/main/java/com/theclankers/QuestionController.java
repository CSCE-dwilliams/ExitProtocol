package com.theclankers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.EscapeManager;
import com.model.Game;
import com.model.User;
import com.model.Challenge;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class QuestionController implements Initializable {

    @FXML
    Button hintBtn;
    @FXML
    Text txtHint1;
    @FXML
    Text txtHint2;
    @FXML
    Text txtHint3;
    @FXML
    Text questionNum;
    @FXML
    Text questionText;
    @FXML
    TextField answerField;
    @FXML
    Button btnSubmit;

    private EscapeManager manager;
    private User user;
    private Game game;
    private Challenge curChallenge;

    private Text[] hints;
    private int hintIndex;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = EscapeManager.getInstance();
        user = manager.getCurrentUser();
        game = manager.getCurrentGame();
        int playerIndex = game.getPlayerIndex();
        hintIndex = 0;
        curChallenge = game.getChallenges().get(playerIndex);

        String questionIndex = Integer.toString((game.getPlayerIndex() + 1));
        questionNum.setText(questionIndex);

        String question = curChallenge.getQuestion();
        questionText.setText(question);

        hints = new Text[] { txtHint1, txtHint2, txtHint3 };
        for (int i = 0; i < curChallenge.getHints().size(); i++) {
            hints[i].setText(curChallenge.getHints().get(i));
        }

    }

    public void getHintBtn(MouseEvent event) throws IOException {
        hints[hintIndex].setVisible(true);
        game.decScore(25);
        hintIndex++;
    }
}