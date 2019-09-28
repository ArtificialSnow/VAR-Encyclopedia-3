package main.java.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.java.app.AudioFactory;
import main.java.app.FileDirectory;
import main.java.app.SceneType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ViewAudioChunksScene extends ApplicationScene {

    @FXML private Button _homeButton;
    @FXML private Button _quitButton;
    @FXML private Button _playButton;
    @FXML private Button _deleteButton;
    @FXML private ListView<String> _searchTermList;
    @FXML private ListView<String> _audioChunksList;

    AudioFactory _audioFactory;
    FileDirectory _fileDirectory;
    MediaPlayer _mediaPlayer;

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
            if (_mediaPlayer == null) {
                _playButton.setText("Stop");
                _deleteButton.setDisable(true);

                Media audioChunkMedia = new Media(Paths.get("./VAR-Encyclopedia/AudioChunks/" + searchTerm + "/" + audioChunk + ".wav").toUri().toString());
                _mediaPlayer = new MediaPlayer(audioChunkMedia);

                _mediaPlayer.setOnEndOfMedia( () -> {
                    _mediaPlayer = null;

                    _playButton.setText("Play");
                    _deleteButton.setDisable(false);
                });

                _mediaPlayer.play();

            } else {
                _mediaPlayer.stop();
                _mediaPlayer = null;

                _playButton.setText("Play");
                _deleteButton.setDisable(false);
            }
        }
    }

    public void deleteButtonHandler() {
        String searchTerm = _searchTermList.getSelectionModel().getSelectedItem();
        String audioChunk = _audioChunksList.getSelectionModel().getSelectedItem();

        if (searchTerm == null || audioChunk == null) {
            createInformationAlert("No Audio Chunk Selected", "Please select an audio chunk");

        } else {
            Alert deleteConfirmation = createConfirmationAlert("Are you sure you want to delete " + audioChunk + "?");
            if (deleteConfirmation.getResult() == ButtonType.YES) {
                _audioFactory.deleteAudioChunk(searchTerm, audioChunk);
                updateAudioChunksList();
            }
        }
    }
}
