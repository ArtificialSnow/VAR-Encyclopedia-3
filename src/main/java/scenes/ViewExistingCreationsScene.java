package main.java.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import main.java.app.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ViewExistingCreationsScene extends ApplicationScene {

    @FXML private Button _homeButton;
    @FXML private Button _quitButton;
    @FXML private Button _playButton;
    @FXML private Button _deleteButton;
    @FXML private TableView _creationsTableView;
    @FXML private TableColumn<String, Creation> _nameColumn;
    @FXML private TableColumn<String, Creation> _searchTermColumn;

    AudioFactory _audioFactory;
    FileDirectory _fileDirectory;
    CreationFactory _creationFactory;

    @FXML
    public void initialize() {
        _audioFactory = new AudioFactory();
        _fileDirectory = new FileDirectory();
        _creationFactory = new CreationFactory();

        _nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        _searchTermColumn.setCellValueFactory(new PropertyValueFactory<>("searchTerm"));

        updateCreationsList();
    }

    public void updateCreationsList() {
        _creationsTableView.getItems().clear();

        try {
            ArrayList<String> creations = new ArrayList<>(Files.readAllLines(Paths.get(ApplicationFolder.Creations.getPath() + File.separator + "CurrentCreations.txt")));

            for (String creation : creations) {
                String[] creationData = creation.split(" ");

                _creationsTableView.getItems().add(new Creation(creationData[0], creationData[1]));
            }
        } catch (IOException e) {
            System.out.println("Unable to open currentCreations.txt");
        }
    }

    public void homeButtonHandler(ActionEvent event) throws IOException {
        changeScene(SceneType.MainMenuScene, event);
    }

    public void quitButtonHandler() {
        System.exit(0);
    }

    public void playButtonHandler() throws IOException {
        Creation creation = (Creation) _creationsTableView.getSelectionModel().getSelectedItem();

        if (creation == null) {
            createInformationAlert("No Creation Selected", "Please select a Creation");

        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneType.CreationsViewer.getPath()));
            Parent parentScene = loader.load();
            CreationsViewer viewer = loader.getController();

            Media audioChunkMedia = new Media(Paths.get(ApplicationFolder.RegularCreations.getPath() + File.separator + creation.getName() + ".mp4").toUri().toString());
            viewer.setMedia(audioChunkMedia);

            Stage mediaPlayerStage = new Stage();
            mediaPlayerStage.setScene(new Scene(parentScene));
            mediaPlayerStage.setResizable(false);
            mediaPlayerStage.setTitle(creation.getName());
            mediaPlayerStage.setOnCloseRequest( closeStage -> {
                viewer.stopMedia();
            });
            mediaPlayerStage.show();
        }
    }

    public void deleteButtonHandler() {
        Creation creation = (Creation) _creationsTableView.getSelectionModel().getSelectedItem();

        if (creation == null) {
            createInformationAlert("No Creation Selected", "Please select a Creation");

        } else {
            Alert deleteConfirmation = createConfirmationAlert("Are you sure you want to delete " + creation.getName() + "?");
            if (deleteConfirmation.getResult() == ButtonType.YES) {
                _creationFactory.deleteCreation(creation.getName());
                _creationFactory.deleteFromCreationsFile(creation.getName(), creation.getSearchTerm());

                updateCreationsList();
            }
        }
    }
}
