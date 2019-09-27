package main.java.scenes;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import main.java.app.AudioFactory;
import main.java.app.FileDirectory;
import main.java.app.SceneType;

import java.io.File;
import java.io.IOException;

public class ViewAudioChunksScene extends ApplicationScene {

    @FXML private Button _homeButton;
    @FXML private Button _quitButton;
    @FXML private Button _playButton;
    @FXML private Button _deleteButton;
    @FXML private ListView<String> _searchTermList;
    @FXML private ListView<String> _audioChunksList;

    AudioFactory _audioFactory;
    FileDirectory _fileDirectory;

    @FXML
    public void initialize() {
        _audioFactory = new AudioFactory();
        _fileDirectory = new FileDirectory();

        _fileDirectory.deleteAllEmptyDirectories("./VAR-Encyclopedia/AudioChunks");
        updateSearchTermList();
    }

    public void updateSearchTermList() {
        _searchTermList.getItems().clear();

        File[] searchTermList = new File("./VAR-Encyclopedia/AudioChunks").listFiles();
        for (File searchTermDirectory : searchTermList) {
            _searchTermList.getItems().add(searchTermDirectory.getName());
        }
    }

    public void updateAudioChunksList() {
        _audioChunksList.getItems().clear();

        String searchTermDirectory = _searchTermList.getSelectionModel().getSelectedItem();
        if (searchTermDirectory != null) {
            File[] audioChunksList = new File("./VAR-Encyclopedia/AudioChunks/" + searchTermDirectory).listFiles();

            if ( audioChunksList.length == 0) {
                _fileDirectory.deleteAllEmptyDirectories("./VAR-Encyclopedia/AudioChunks");
                updateSearchTermList();

            } else {
                for (File audioChunk : audioChunksList) {
                    String chunkName = audioChunk.getName();
                    _audioChunksList.getItems().add(chunkName.substring(0,chunkName.length() - 4));
                }
            }
        }
    }

    public void homeButtonHandler(ActionEvent event) throws IOException {
        changeScene(SceneType.MainMenuScene, event);
    }

    public void quitButtonHandler() {
        System.exit(0);
    }

    public void playButtonHandler() {
        String searchTerm = _searchTermList.getSelectionModel().getSelectedItem();
        String audioChunk = _audioChunksList.getSelectionModel().getSelectedItem();

        if (audioChunk == null || searchTerm == null) {
            createInformationAlert("No Audio Chunk Selected", "Please select an audio chunk");

        } else {
            new Thread( new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    _audioFactory.playAudioChunk(searchTerm, audioChunk);
                    return null;
                }
            }).start();
        }
    }

    public void deleteButtonHandler() {
        String searchTerm = _searchTermList.getSelectionModel().getSelectedItem();
        String audioChunk = _audioChunksList.getSelectionModel().getSelectedItem();

        if (searchTerm == null || audioChunk == null) {
            createInformationAlert("No Audio Chunk Selected", "Please select an audio chunk");

        } else {
            _audioFactory.deleteAudioChunk(searchTerm, audioChunk);
            updateAudioChunksList();
        }
    }
}
