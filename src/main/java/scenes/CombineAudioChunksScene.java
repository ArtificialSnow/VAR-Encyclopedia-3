package main.java.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import main.java.app.SceneType;

import java.io.IOException;

public class CombineAudioChunksScene extends ApplicationScene {

    @FXML private Button _homeButton;
    @FXML private Button _nextSceneButton;
    @FXML private Button _quitButton;
    @FXML private ListView<String> _searchTermsListView;
    @FXML private ListView<String> _audioChunksListView;
    @FXML private ListView<String> _selectedAudioChunksListView;

    private CreateAudioChunksScene CAC;

    @FXML
    public void initialize() {
//        _searchTermsListView = new ListView<String>();
//        _audioChunksListView = new ListView<String>();
//        _selectedAudioChunksListView = new ListView<String>();
        listSearchTerm();
    }

    public void homeButtonHandler(ActionEvent event) throws IOException {
        if (_selectedAudioChunksListView.getItems().size() != 0) {
            Alert returnToMenuAlert = createConfirmationAlert("Are you sure you want to quit to the main menu? All progress will be lost.");

            if (returnToMenuAlert.getResult() == ButtonType.YES) {
                changeScene(SceneType.MainMenuScene, event);
            }
        } else {
            changeScene(SceneType.MainMenuScene, event);
        }
    }

    public void quitButtonHandler() {
        if (_selectedAudioChunksListView.getItems().size() != 0) {
            createQuitAlert();
        } else {
            System.exit(0);
        }
    }

    public void nextSceneButtonHandler(ActionEvent event) throws IOException {
        changeScene(SceneType.SelectImagesScene, event);
    }

    public void listSearchTerm() {

//        _searchTermsListView.getItems().add(term);
        _searchTermsListView.getItems().setAll(CreateAudioChunksScene.getInstance().get_listofSearchTerm());
    }

    public void listAudioChunk(String chunck) {
        _audioChunksListView.getItems().add(chunck);
    }
}
