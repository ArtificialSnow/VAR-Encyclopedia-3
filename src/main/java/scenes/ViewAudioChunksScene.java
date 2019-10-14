package main.java.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.java.app.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ViewAudioChunksScene extends ApplicationScene {

    @FXML private Button _homeButton;
    @FXML private Button _quitButton;
    @FXML private Button _playButton;
    @FXML private Button _deleteButton;
    @FXML private ListView<String> _searchTermList;
    @FXML private TableView _audioChunksTableView;

    @FXML private TableColumn<String, AudioChunk> _nameColumn;
    @FXML private TableColumn<String, AudioChunk> _voiceColumn;
    @FXML private TableColumn<String, AudioChunk> _textColumn;

    AudioFactory _audioFactory;
    FileDirectory _fileDirectory;
    MediaPlayer _mediaPlayer;

    @FXML
    public void initialize() {
        _audioFactory = new AudioFactory();
        _fileDirectory = new FileDirectory();

        _nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        _voiceColumn.setCellValueFactory(new PropertyValueFactory<>("voice"));
        _textColumn.setCellValueFactory(new PropertyValueFactory<>("text"));

        _fileDirectory.deleteAllEmptyDirectories(ApplicationFolder.AudioChunks.getPath());
        updateSearchTermList();
    }

    public void updateSearchTermList() {
        _searchTermList.getItems().clear();

        File[] searchTermList = new File(ApplicationFolder.AudioChunks.getPath()).listFiles();
        for (File searchTermDirectory : searchTermList) {
            _searchTermList.getItems().add(searchTermDirectory.getName());
        }
    }

    public void updateAudioChunksList() {
        _audioChunksTableView.getItems().clear();

        String searchTermDirectory = _searchTermList.getSelectionModel().getSelectedItem();

        try {
            ArrayList<String> audioChunks = new ArrayList<>(Files.readAllLines(Paths.get(ApplicationFolder.AudioChunks.getPath() + File.separator + searchTermDirectory + File.separator+ "AudioChunks.txt")));
            for (String audioChunk : audioChunks){
                String[] audioChunkInformation = audioChunk.split(":");

                _audioChunksTableView.getItems().add(new AudioChunk(audioChunkInformation[0], audioChunkInformation[1], audioChunkInformation[2], audioChunkInformation[3]));
            }
        } catch (IOException e) {
            System.out.println("Error updating audio chunks list");
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
        AudioChunk audioChunk = (AudioChunk) _audioChunksTableView.getSelectionModel().getSelectedItem();

        if (audioChunk == null || searchTerm == null) {
            createInformationAlert("No Audio Chunk Selected", "Please select an audio chunk");

        } else {
            if (_mediaPlayer == null) {
                _playButton.setText("Stop");
                _deleteButton.setDisable(true);

                Media audioChunkMedia = new Media(Paths.get(ApplicationFolder.AudioChunks.getPath() + File.separator + searchTerm + File.separator + "RegularAudioChunks" + File.separator + audioChunk.getName() + ".wav").toUri().toString());
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
        AudioChunk audioChunk = (AudioChunk) _audioChunksTableView.getSelectionModel().getSelectedItem();

        if (searchTerm == null || audioChunk == null) {
            createInformationAlert("No Audio Chunk Selected", "Please select an audio chunk");

        } else {
            Alert deleteConfirmation = createConfirmationAlert("Are you sure you want to delete " + audioChunk.getName() + "?");
            if (deleteConfirmation.getResult() == ButtonType.YES) {
                _audioFactory.deleteAudioChunk(searchTerm, audioChunk.getName());
                updateAudioChunksList();
            }
        }
    }
}
