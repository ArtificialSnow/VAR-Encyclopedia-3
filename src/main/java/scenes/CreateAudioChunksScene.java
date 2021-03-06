package main.java.scenes;

import com.sun.javaws.progress.Progress;
import javafx.application.Platform;
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
    @FXML private ProgressIndicator _progressIndicator;

    private String _searchTerm;
    private WikipediaSearch _wikipediaSearch;
    private AudioFactory _audioFactory;
    private VoiceSynthesizer _voiceSynthesizer;

    //Sets up the scene: adds all available voice synthesizer voices to the drop down menu
    @FXML
    public void initialize() {
        _audioFactory = new AudioFactory();
        _editor.setWrapText(true);

        _voiceSynthesizer = new VoiceSynthesizer();
        for (String voice : _voiceSynthesizer.getVoices()){
            _voiceSynthesizerSelection.getItems().add(voice);
        }

        if (_voiceSynthesizerSelection.getItems().contains("kal_diphone")) {
            _voiceSynthesizerSelection.getSelectionModel().select("kal_diphone");
        } else {
            _voiceSynthesizerSelection.getSelectionModel().selectFirst();
        }
    }

    //Disables all buttons
    public void setDisableAllButtons(Boolean disable) {
        _homeButton.setDisable(disable);
        _searchButton.setDisable(disable);
        _quitButton.setDisable(disable);
        _addTextToEditorButton.setDisable(disable);
        _previewAudioChunk.setDisable(disable);
        _saveButton.setDisable(disable);
    }

    //Changes the scene to the main menu
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

    //Gets the Wikipedia Content for a wikipedia search
    public void searchButtonHandler() {
        _searchTerm = _searchBar.getText().replaceAll("(^\\s+)|(\\s+$)", "");

        if (_searchTerm == null || _searchTerm.length() == 0) {
            createInformationAlert("No Search Term", "Please enter a Search Term");
        } else {
            if (_editor.getText().length() != 0) {
                Alert searchAlert = createConfirmationAlert("Are you sure you wish to search? You will lose all existing progress.");

                if (searchAlert.getResult() == ButtonType.YES) {
                    _editor.clear();
                    _fileNameTextArea.clear();
                    searchWikipedia();
                }
            } else {
                _editor.clear();
                _fileNameTextArea.clear();
                searchWikipedia();
            }
        }
    }

    public void searchBarOnEnter() {
        if (! _searchButton.isDisabled()) {
            searchButtonHandler();
        }
    }

    //Helper method for getting wikipedia Content
    private void searchWikipedia() {
        setDisableAllButtons(true);
        _progressIndicator.setVisible(true);

        new Thread(new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                _wikipediaSearch = new WikipediaSearch(_searchTerm);
                return null;
            }

            @Override
            protected void done() {
                Platform.runLater(() -> {
                    _editor.clear();
                    _wikipediaText.getItems().clear();

                    if (_wikipediaSearch.contentDoesNotExist()) {
                        createInformationAlert("Content does not exist", "No content on wikipedia for the search term " + _searchTerm);
                    } else {
                        _wikipediaText.getItems().addAll(_wikipediaSearch.getContent());
                    }

                    _progressIndicator.setVisible(false);
                    setDisableAllButtons(false);
                });
            }
        }).start();
    }

    //Adds the selected text in the list view to the text editor
    public void addTextToEditorButtonHandler() {
        String wikipediaLine = _wikipediaText.getSelectionModel().getSelectedItem();

        if (wikipediaLine == null) {
            createInformationAlert("No line selected", "Please select a line to add before adding the the editor");
        } else {
            _editor.appendText(wikipediaLine + "\n");
        }

        checkTextLength();
    }

    //Checks whether the text in the editor is more than 40 words
    public void checkTextLength() {
        Platform.runLater( () -> {
            if (_editor.getText().split("\\s").length > 40) {
                _wordLimit.setStyle("-fx-font-weight: bold; -fx-text-fill: red; -fx-background-color: silver;");
            } else {
                _wordLimit.setStyle("-fx-font-weight: bold; -fx-text-fill: #4c7450; -fx-background-color: silver;");
            }

            if (_editor.getText().matches("\\s+")) {
                _editor.clear();
            }
        });
    }

    //Previews the audio of the text in the editor
    public void previewAudioChunkButtonHandler() {
        if (validAudioChunk()) {
            setDisableAllButtons(true);

            new Thread( new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    _audioFactory.previewAudioChunk(_editor.getText(), _voiceSynthesizerSelection.getSelectionModel().getSelectedItem());
                    return null;
                }

                @Override
                protected void done() {
                    Platform.runLater( () -> {
                        setDisableAllButtons(false);
                    });
                }
            }).start();
        }
    }

    //Saves an audio chunk to be used to create creations
    public void saveButtonHandler() {
        String chunkName = _fileNameTextArea.getText().replaceAll("(^\\s+)|(\\s+$)", "");;

        if (chunkName == null || chunkName.length() == 0) {
            createInformationAlert("No Chunk Name specified", "Please enter a name for the Audio Chunk");
        } else {
            if (validAudioChunk()) {
                String audioChunkText = _editor.getText().replaceAll("\\s$", "");
                audioChunkText = audioChunkText.replaceAll("\n", " ");

                if (_audioFactory.chunkAlreadyExists(_searchTerm, chunkName)) {
                    Alert overrideAlert = createConfirmationAlert("Audio Chunk " + chunkName + " already exists. Would you like to override?");

                    if (overrideAlert.getResult() == ButtonType.YES) {
                            _audioFactory.deleteAudioChunk(_searchTerm,chunkName);
                        saveAudioChunk(chunkName, audioChunkText);
                    }
                } else {
                    saveAudioChunk(chunkName, audioChunkText);
                }
            }
        }
    }

    public void saveButtonOnEnter() {
        if (! _saveButton.isDisabled()) {
            saveButtonHandler();
        }
    }

    //Helper method for saving audio chunks
    private void saveAudioChunk(String chunkName, String audioChunkText) {
        setDisableAllButtons(true);

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
                    setDisableAllButtons(false);

                    //createInformationAlert("New Audio Chunk Created", "Audio Chunk " + chunkName + " has been created.");
                });
            }
        }).start();
    }

    //Checks whether an audio chunk is valid or not
    private boolean validAudioChunk() {
        if (_editor.getText().split("\\s").length > 40) {
            createInformationAlert("Audio chunk too long", "Maximum if 40 words per audio chunk");
            return false;
        } else if (_editor.getText().length() == 0) {
            createInformationAlert("No content in Audio Chunk", "Please add the text for the audio chunk into the editor");
            return false;
        } else if (_searchTerm == null) {
            createInformationAlert("No search term for Audio Chunk", "Please enter a search term for the Audio Chunk");
            return false;
        } else if (_wikipediaSearch.contentDoesNotExist()) {
            createInformationAlert("This is not a valid Search Term", "No content exists on Wikipedia for this search term");
            return false;
        }
        return true;
    }
}