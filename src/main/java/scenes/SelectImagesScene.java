package main.java.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.java.app.SceneType;

import java.io.IOException;

public class SelectImagesScene extends ApplicationScene {

    @FXML private Button _homeButton;
    @FXML private Button _quitButton;
    @FXML private Button _shiftImageUpButton;
    @FXML private Button get_shiftImageDownButton;

    private String _searchTerm;

    public void setSearchTerm(String searchTerm) {
        _searchTerm = searchTerm;
        System.out.println(_searchTerm);
    }

    public void addImageButtonHandler() {

    }

    public void removeImageButtonHandler() {

    }

    public void shiftImageUpButtonHandler() {

    }

    public void shiftImageDownButtonHandler() {

    }

    public void homeButtonHandler(ActionEvent event) throws IOException {
        changeScene(SceneType.MainMenuScene, event);
    }

    public void quitButtonHandler() {
        System.exit(0);
    }
}
