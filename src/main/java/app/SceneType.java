package main.java.app;

import main.java.scenes.CreateAudioChunksScene;

public enum SceneType {

    MainMenuScene("/main/resources/scenes/MainMenuScene.fxml"),
    CreateAudioChunksScene("/main/resources/scenes/CreateAudioChunksScene.fxml"),
    ViewExistingCreationsScene("/main/resources/scenes/ViewExistingCreationsScene.fxml"),
    ViewAudioChunksScene("/main/resources/scenes/ViewAudioChunksScene.fxml"),
    CombineAudioChunksScene("/main/resources/scenes/CombineAudioChunksScene.fxml"),
    SelectImagesScene("/main/resources/scenes/SelectImagesScene.fxml");


    private String _filePath;

    SceneType(String filePath) {
        _filePath = filePath;
    }

    public String getPath() {
        return _filePath;
    }
}
