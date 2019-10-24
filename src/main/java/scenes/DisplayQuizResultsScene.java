package main.java.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.app.Answer;
import main.java.app.SceneType;

import java.io.IOException;
import java.util.List;

public class DisplayQuizResultsScene extends ApplicationScene {

    @FXML Button _homeButton;
    @FXML Button _anotherQuizButton;
    @FXML Label _scoreLabel;

    @FXML TableView _resultsTableView;
    @FXML TableColumn _correctAnswerColumn;
    @FXML TableColumn _theirAnswerColumn;
    @FXML TableColumn _resultColumn;

    @FXML
    public void initialize() {

        _correctAnswerColumn.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));
        _theirAnswerColumn.setCellValueFactory(new PropertyValueFactory<>("enteredAnswer"));
        _resultColumn.setCellValueFactory(new PropertyValueFactory<>("correct"));
    }

    public void setResults(List<Answer> answerList) {
        int numberCorrect = 0;

        for (Answer answer : answerList) {
            _resultsTableView.getItems().add(answer);

            if (answer.getCorrect().equals("Correct")) {
                numberCorrect++;
            }
        }

        _scoreLabel.setText("Total Correct: " + numberCorrect + "/" + answerList.size());
    }

    public void homeButtonHandler(ActionEvent event) throws IOException {
        changeScene(SceneType.MainMenuScene, event);
    }

    public void anotherQuizButtonHandler(ActionEvent event) throws IOException  {
        changeScene(SceneType.SetUpQuizScene, event);
    }
}
