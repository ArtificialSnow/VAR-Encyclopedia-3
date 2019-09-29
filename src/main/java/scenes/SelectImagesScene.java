package main.java.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import main.java.app.CreationFactory;
import main.java.app.SceneType;

import java.io.IOException;

public class SelectImagesScene extends ApplicationScene {

    @FXML private Button _homeButton;
    @FXML private Button _quitButton;
    @FXML private Button _addImageButton;
    @FXML private Button _removeImageButton;
    @FXML private Button _shiftImageUpButton;
    @FXML private Button _shiftImageDownButton;
    @FXML private Button _createCreationButton;
    @FXML private TextField _creationName;

    private String _searchTerm;
    private CreationFactory _creationFactory;

    //"./VAR-Encyclopedia/.temp/tempCombinedChunks.wav"
    @FXML
    public void initialize() {
        _creationFactory = new CreationFactory();
    }

    public void setSearchTerm(String searchTerm) {
        _searchTerm = searchTerm;
    }

    public void addImageButtonHandler() {

    }

    public void removeImageButtonHandler() {

    }

    public void shiftImageUpButtonHandler() {

    }

    public void shiftImageDownButtonHandler() {

    }

    public void createCreationButtonHandler() {
       // _creationFactory.combineImagesToVideo();
       // _creationFactory.combineVideoAndText();
       // _creationFactory.combineVideoAndAudio();
    }

    public void homeButtonHandler(ActionEvent event) throws IOException {
        Alert homeAlert = createConfirmationAlert("Are you sure you want to go back to the Main Menu? You will lose all existing progress");
        if (homeAlert.getResult() == ButtonType.YES) {
            changeScene(SceneType.MainMenuScene, event);
        }
    }

    public void quitButtonHandler() {
        createQuitAlert();
    }
}
