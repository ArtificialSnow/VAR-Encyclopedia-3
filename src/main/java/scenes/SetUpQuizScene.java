package main.java.scenes;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import main.java.app.ApplicationFolder;
import main.java.app.Quiz;
import main.java.app.SceneType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SetUpQuizScene extends ApplicationScene {

    @FXML Button _quitButton;
    @FXML Button _homeButton;
    @FXML Button _startQuizButton;
    @FXML ChoiceBox<Integer> _numberOfQuestions;

    private int _numberOfCreations;

    @FXML
    public void initialize() throws IOException {

        ArrayList<String> creations = new ArrayList<>(Files.readAllLines(Paths.get(ApplicationFolder.Creations.getPath() + File.separator + "CurrentCreations.txt")));
        _numberOfCreations = creations.size();

        if (_numberOfCreations == 0 ){
            _numberOfQuestions.setDisable(true);
            _startQuizButton.setDisable(true);
        } else {
            for (int i = 1; i <= creations.size(); i++) {
                _numberOfQuestions.getItems().add(i);
            }

            _numberOfQuestions.getSelectionModel().selectFirst();
        }
    }

    public void homeButtonHandler(ActionEvent event) throws IOException {
        changeScene(SceneType.MainMenuScene, event);
    }

    public void quitButtonHandler() {
        System.exit(0);
    }

    public void startQuizButtonHandler(ActionEvent event) throws IOException {
        int numberOfQuestions = _numberOfQuestions.getSelectionModel().getSelectedItem();

        ApplicationScene playQuizSceneController = changeScene(SceneType.PlayQuizScene, event);
        ((PlayQuizScene) playQuizSceneController).setQuiz(new Quiz(numberOfQuestions, _numberOfCreations));
    }
}
