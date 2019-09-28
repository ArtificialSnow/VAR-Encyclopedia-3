package main.java.scenes;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.app.AudioFactory;
import main.java.app.CreationFactory;
import main.java.app.FileDirectory;
import main.java.app.SceneType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ViewExistingCreationsScene extends ApplicationScene {

    @FXML private Button _homeButton;
    @FXML private Button _quitButton;
    @FXML private Button _playButton;
    @FXML private Button _deleteButton;
    @FXML private ListView<String> _creationsList;

    AudioFactory _audioFactory;
    FileDirectory _fileDirectory;
    CreationFactory _creationFactory;

    @FXML
    public void initialize() {
        _audioFactory = new AudioFactory();
        _fileDirectory = new FileDirectory();
        _creationFactory = new CreationFactory();

        updateCreationsList();
    }

    public void updateCreationsList() {
        _creationsList.getItems().clear();

        File[] creationsList = new File("./VAR-Encyclopedia/Creations").listFiles();
        for (File creation : creationsList) {
            String creationName = creation.getName();
            _creationsList.getItems().add(creationName.substring(0, creationName.length() - 4));
        }
    }

    public void homeButtonHandler(ActionEvent event) throws IOException {
        changeScene(SceneType.MainMenuScene, event);
    }

    public void quitButtonHandler() {
        System.exit(0);
    }

    public void playButtonHandler() throws IOException {
        String creation = _creationsList.getSelectionModel().getSelectedItem();

        if (creation == null) {
            createInformationAlert("No Creation Selected", "Please select a Creation");

        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/scenes/CreationsViewer.fxml"));
            Parent parentScene = loader.load();
            CreationsViewer viewer = loader.getController();

            Media audioChunkMedia = new Media(Paths.get("./VAR-Encyclopedia/Creations/" + creation + ".mp4").toUri().toString());
            viewer.setMedia(audioChunkMedia);

            Stage mediaPlayerStage = new Stage();
            mediaPlayerStage.setScene(new Scene(parentScene));
            mediaPlayerStage.setResizable(false);
            mediaPlayerStage.setTitle(creation);
            mediaPlayerStage.show();
        }
    }

    public void deleteButtonHandler() {
        String creation = _creationsList.getSelectionModel().getSelectedItem();

        if (creation == null) {
            createInformationAlert("No Creation Selected", "Please select a Creation");

        } else {
            Alert deleteConfirmation = createConfirmationAlert("Are you sure you want to delete " + creation + "?");
            if (deleteConfirmation.getResult() == ButtonType.YES) {
                _creationFactory.deleteCreation(creation);

                updateCreationsList();
            }
        }
    }
}
