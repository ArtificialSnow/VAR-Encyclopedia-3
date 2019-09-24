package main.java.app;

import main.java.scenes.CreateAudioChunksScene;

public enum SceneType {

    MainMenuScene("/main/resources/scenes/MainMenuScene.fxml"),
    GetCreationTextScene("/main/resources/scenes/GetCreationTextScene.fxml"),
    CreateAudioChunksScene("/main/resources/scenes/CreateAudioChunksScene.fxml"),
    ViewExistingCreationsScene("/main/resources/scenes/ViewExistingCreationsScene.fxml");


    private String _filePath;

    SceneType(String filePath) {
        _filePath = filePath;
    }

    public String getPath() {
        return _filePath;
    }
}
