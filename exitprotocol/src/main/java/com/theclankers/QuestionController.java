package com.theclankers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.EscapeManager;
import com.model.Game;
import com.model.User;
import com.model.Challenge;

import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class QuestionController implements Initializable {

    @FXML
    Button hintBtn;
    @FXML
    Circle pauseBtn;
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
    @FXML
    Text wrongAns;
    @FXML
    Text noHintsLeft;

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
        curChallenge = game.getCurrentChallenge();

        String questionIndex = Integer.toString((game.getPlayerIndex() + 1));
        questionNum.setText(questionIndex);

        String question = curChallenge.getQuestion();
        questionText.setText(question);

        hints = new Text[] { txtHint1, txtHint2, txtHint3 };
        for (int i = 0; i < curChallenge.getHints().size(); i++) {
            hints[i].setText(curChallenge.getHints().get(i));
        }

    }

    public void switchItemBtn(MouseEvent event) throws IOException {
        App.setRoot("itemSelect");
    }

    public void getHintBtn(MouseEvent event) throws IOException {
        if (hintIndex < 3) {
            hints[hintIndex].setVisible(true);
            manager.hintPenalty();
            hintIndex++;
        } else {
            showHideText(noHintsLeft);
        }

    }

    public void switchPause(MouseEvent event) throws IOException {
        App.setRoot("pauseScreenQuestion");
    }

    public void attemptQuestionButton(MouseEvent event) throws IOException {
        String attempt = answerField.getText();
        if (!manager.questionCorrect(attempt)) {
            showHideText(wrongAns);
            manager.wrongAnswerPenalty();
            return;
        }
        manager.correctAnswer();
        // Store items earned from this question to EscapeManager
        manager.addItemsFromCurrentChallenge();
        if (game.getPlayerIndex() > 5) {
            App.setRoot("sessionCompletion");
        }
        manager.nextQuestion();
        App.setRoot("questionCorrect");
    }

    private void showHideText(Text someText) {
        someText.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1.3));
        pause.setOnFinished(event -> {
            someText.setVisible(false);
        });
        pause.play();
    }
}