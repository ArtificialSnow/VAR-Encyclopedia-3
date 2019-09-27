package main.java.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import main.java.app.SceneType;

import java.io.IOException;

public class MainMenuScene extends ApplicationScene {

    @FXML private Button _quitButton;
    @FXML private Button _createCreationButton;
    @FXML private Button _createAudioChunksButton;
    @FXML private Button _viewCreationsButton;
    @FXML private Button _viewAudioChunksButton;

    public void quitButtonHandler() {
        System.exit(0);
    }

    public void createCreationButtonHandler(ActionEvent event) throws IOException {
        changeScene(SceneType.CombineAudioChunksScene, event);
    }

    public void createAudioChunkButtonHandler(ActionEvent event) throws IOException {
        changeScene(SceneType.CreateAudioChunksScene, event);
    }

    public void viewCreationsButtonHandler(ActionEvent event) throws IOException {
        changeScene(SceneType.ViewExistingCreationsScene, event);
    }

    public void viewAudioChunksButtonHandler(ActionEvent event) throws IOException {
        changeScene(SceneType.ViewAudioChunksScene, event);
    }
}
