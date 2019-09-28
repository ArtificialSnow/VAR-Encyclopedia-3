package main.java.scenes;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import main.java.app.AudioFactory;
import main.java.app.FileDirectory;
import main.java.app.SceneType;

import java.io.File;
import java.io.IOException;

public class ViewExistingCreationsScene extends ApplicationScene {

    @FXML private Button _homeButton;
    @FXML private Button _quitButton;
    @FXML private Button _playButton;
    @FXML private Button _deleteButton;
    @FXML private ListView<String> _creationsList;

    AudioFactory _audioFactory;
    FileDirectory _fileDirectory;

    @FXML
    public void initialize() {
        _audioFactory = new AudioFactory();
        _fileDirectory = new FileDirectory();

        updateCreationsList();
    }

    public void updateCreationsList() {
        _creationsList.getItems().clear();

        File[] creationsList = new File("./VAR-Encyclopedia/Creations").listFiles();
        for (File creation : creationsList) {
            _creationsList.getItems().add(creation.getName());
        }
    }

    public void homeButtonHandler(ActionEvent event) throws IOException {
        changeScene(SceneType.MainMenuScene, event);
    }

    public void quitButtonHandler() {
        System.exit(0);
    }

    public void playButtonHandler() {
        String creation = _creationsList.getSelectionModel().getSelectedItem();

        if (creation == null) {
            createInformationAlert("No Creation Selected", "Please select a Creation");

        } else {
            new Thread( new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    //play mediaPlayer
                    return null;
                }
            }).start();
        }
    }

    public void deleteButtonHandler() {
        String creation = _creationsList.getSelectionModel().getSelectedItem();

        if (creation == null) {
            createInformationAlert("No Creation Selected", "Please select a Creation");

        } else {
            Alert deleteConfirmation = createConfirmationAlert("Are you sure you want to delete " + creation + "?");
            if (deleteConfirmation.getResult() == ButtonType.YES) {
                //call to deleteCreation.sh
            }
        }
    }
}
