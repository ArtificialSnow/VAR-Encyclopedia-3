package main.java.app;

import main.java.scenes.CreateAudioChunksScene;
import main.java.scenes.CreationsViewer;

import java.io.File;

public enum SceneType {

    MainMenuScene("/main/resources/scenes/MainMenuScene.fxml"),
    CreateAudioChunksScene("/main/resources/scenes/CreateAudioChunksScene.fxml"),
    ViewExistingCreationsScene("/main/resources/scenes/ViewExistingCreationsScene.fxml"),
    ViewAudioChunksScene("/main/resources/scenes/ViewAudioChunksScene.fxml"),
    CombineAudioChunksScene("/main/resources/scenes/CombineAudioChunksScene.fxml"),
    SelectImagesScene("/main/resources/scenes/SelectImagesScene.fxml"),
    CreationsViewer("/main/resources/scenes/CreationsViewer.fxml"),
    SetUpQuizScene("/main/resources/scenes/SetUpQuizScene.fxml"),
    PlayQuizScene("/main/resources/scenes/PlayQuizScene.fxml"),
    DisplayQuizResultsScene("/main/resources/scenes/DisplayQuizResultsScene.fxml");


    private String _filePath;

    SceneType(String filePath) {
        _filePath = filePath.replaceAll("/", File.separator);
    }

    public String getPath() {
        return _filePath;
    }
}
