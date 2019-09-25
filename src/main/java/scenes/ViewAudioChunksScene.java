package main.java.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.java.app.SceneType;

import java.io.IOException;

public class ViewAudioChunksScene extends ApplicationScene {

    @FXML private Button _homeButton;
    @FXML private Button _quitButton;

    public void homeButtonHandler(ActionEvent event) throws IOException {
        changeScene(SceneType.MainMenuScene, event);
    }

    public void quitButtonHandler() {
        System.exit(0);
    }
}
