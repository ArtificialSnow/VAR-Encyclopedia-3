package main.java.scenes;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.app.*;

import java.io.IOException;

public class CreateAudioChunksScene extends ApplicationScene {

    @FXML private Button _homeButton;
    @FXML private Button _quitButton;
    @FXML private TextField _searchBar;
    @FXML private Button _searchButton;
    @FXML private ListView<String> _wikipediaText;
    @FXML private Button _addTextToEditorButton;
    @FXML private TextArea _editor;
    @FXML private ChoiceBox<String> _voiceSynthesizerSelection;
    @FXML private Label _wordLimit;
    @FXML private Button _previewAudioChunk;
    @FXML private TextField _fileNameTextArea;
    @FXML private Button _saveButton;

    private static CreateAudioChunksScene CACS;

    public static CreateAudioChunksScene getInstance(){
        if (CACS == null){
            CACS = new CreateAudioChunksScene();
        }
        return CACS;
    }

    public ObservableList<String> get_listofSearchTerm() {
        return _listofSearchTerm;
    }

    private String _searchTerm;
    private ObservableList<String> _listofSearchTerm;
    private WikipediaSearch _wikipediaSearch;
    private AudioFactory _audioFactory;
    private VoiceSynthesizer _voiceSynthesizer;

    @FXML
    public void initialize() {
        _audioFactory = new AudioFactory();
        _editor.setWrapText(true);
        _listofSearchTerm = FXCollections.observableArrayList();

        _voiceSynthesizer = new VoiceSynthesizer();
        for (String voice : _voiceSynthesizer.getVoices()){
            _voiceSynthesizerSelection.getItems().add(voice);
        }
        _voiceSynthesizerSelection.getSelectionModel().selectFirst();
        CACS = this;
    }

    public void homeButtonHandler(ActionEvent event) throws IOException {
        if (_editor.getText().length() != 0) {
            Alert searchAlert = createConfirmationAlert("Are you sure you wish to go back to the main menu? You will lose your existing progress.");

            if (searchAlert.getResult() == ButtonType.YES) {
                changeScene(SceneType.MainMenuScene, event);
            }
        } else {
            changeScene(SceneType.MainMenuScene, event);
        }
    }

    public void quitButtonHandler() {
        if (_editor.getText().length() != 0) {
            createQuitAlert();
        } else {
            System.exit(0);
        }
    }

    public void searchButtonHandler() {
        _searchTerm = _searchBar.getText();
        _listofSearchTerm.add(_searchTerm);
        if (_searchTerm == null || _searchTerm.length() == 0) {
            createInformationAlert("No Search Term", "Please enter a Search Term");
        } else {
            if (_editor.getText().length() != 0) {
                Alert searchAlert = createConfirmationAlert("Are you sure you wish to search? You will lose all existing progress.");

                if (searchAlert.getResult() == ButtonType.YES) {
                    searchWikipedia();
                }
            } else {
                searchWikipedia();
            }
        }
    }

    private void searchWikipedia() {
        new Thread(new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                _wikipediaSearch = new WikipediaSearch(_searchTerm);
                return null;
            }

            @Override
            protected void done() {
                Platform.runLater(() -> {
                    if (_wikipediaSearch.contentDoesNotExist()) {
                        createInformationAlert("Content does not exist", "No content on wikipedia for the search term " + _searchTerm);
                    } else {
                        _editor.clear();
                        _wikipediaText.getItems().clear();

                        _wikipediaText.getItems().addAll(_wikipediaSearch.getContent());
                    }
                });
            }
        }).start();
    }

    public void addTextToEditorButtonHandler() {
        String wikipediaLine = _wikipediaText.getSelectionModel().getSelectedItem();

        if (wikipediaLine == null) {
            createInformationAlert("No line selected", "Please select a line to add before adding the the editor");
        } else {
            _editor.appendText(wikipediaLine + "\n");
        }

        checkTextLength();
    }

    public void checkTextLength() {
        Platform.runLater( () -> {
            if (_editor.getText().split("\\s").length >= 40) {
                _wordLimit.setStyle("-fx-font-weight: bold; -fx-text-fill: red; -fx-background-color: silver;");
            } else {
                _wordLimit.setStyle("-fx-font-weight: bold; -fx-text-fill: #4c7450; -fx-background-color: silver;");
            }

            if (_editor.getText().matches("\\s+")) {
                _editor.clear();
            }
        });
    }

    public void previewAudioChunkButtonHandler() {
        if (validAudioChunk()) {
            _previewAudioChunk.setDisable(true);

            new Thread( new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    _audioFactory.previewAudioChunk(_editor.getText(), _voiceSynthesizerSelection.getSelectionModel().getSelectedItem());
                    return null;
                }

                @Override
                protected void done() {
                    Platform.runLater( () -> {
                        _previewAudioChunk.setDisable(false);
                    });
                }
            }).start();
        }
    }

    public void saveButtonHandler() {
        String chunkName = _fileNameTextArea.getText();
        if (validAudioChunk()) {
            String audioChunkText = _editor.getText();

            if (_audioFactory.chunkAlreadyExists(_searchTerm, chunkName)) {
                Alert overrideAlert = createConfirmationAlert("Audio Chunk " + chunkName + " already exists. Would you like to override?");

                if (overrideAlert.getResult() == ButtonType.YES) {
                    saveAudioChunk(chunkName, audioChunkText);
                }
            } else {
                saveAudioChunk(chunkName, audioChunkText);
            }
        }
    }

    private void saveAudioChunk(String chunkName, String audioChunkText) {
        _saveButton.setDisable(true);

        new Thread( new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                _audioFactory.saveAudioChunk(audioChunkText, _voiceSynthesizerSelection.getSelectionModel().getSelectedItem(), _searchTerm, chunkName);
                return null;
            }

            @Override
            protected void done() {
                Platform.runLater( () -> {
                    _editor.clear();
                    _fileNameTextArea.clear();
                    _saveButton.setDisable(false);

                    createInformationAlert("New Audio Chunk Created", "Audio Chunk " + chunkName + " has been created.");
                });
            }
        }).start();
    }

    private boolean validAudioChunk() {
        if (_editor.getText().split("\\s").length >= 40) {
            createInformationAlert("Audio chunk too long", "Maximum if 40 words per audio chunk");
            return false;
        } else if (_editor.getText().length() == 0) {
            createInformationAlert("No content in Audio Chunk", "Please add the text for the audio chunk into the editor");
            return false;
        }
        return true;
    }
}