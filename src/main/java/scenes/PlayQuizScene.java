package main.java.scenes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import main.java.app.Answer;
import main.java.app.ApplicationFolder;
import main.java.app.Quiz;
import main.java.app.SceneType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PlayQuizScene extends ApplicationScene {

    @FXML private MediaView _mediaView;
    @FXML private Label _currentTime;
    @FXML private Slider _timeBar;
    @FXML private Button _playPause;
    @FXML private Button _skipForward;
    @FXML private Button _skipBackward;

    @FXML private TextField _searchTermField;
    @FXML private Button _submitAnswerButton;

    private MediaPlayer _mediaPlayer;

    private int index;

    private List<String> _selectedCreations;
    private List<String> _correctAnswers;
    private List<Answer> _answerList;

    @FXML
    public void initialize() {
        _answerList = new ArrayList<>();
    }

    public void setQuiz(Quiz quiz) {
        _selectedCreations = quiz.getMedia();
        _correctAnswers = quiz.getAnswers();
        index = 0;

        _mediaPlayer = new MediaPlayer(new Media(Paths.get(ApplicationFolder.RedactedCreations.getPath() + File.separator + _selectedCreations.get(index) + ".mp4").toUri().toString()));
        setUpNewMediaPlayer();
    }

    public void setUpNewMediaPlayer() {
        _mediaPlayer.setAutoPlay(true);
        _mediaPlayer.setOnReady( () -> {
            _timeBar.setMin(0);
            _timeBar.setMax(_mediaPlayer.getTotalDuration().toMillis());

            _timeBar.valueProperty().addListener( observable -> {
                if (_timeBar.isPressed()) {
                    _mediaPlayer.seek(new Duration(_timeBar.getValue()));
                }
            });
        });

        _mediaPlayer.setOnEndOfMedia( () -> {
            _mediaPlayer.seek(Duration.ZERO);
            _timeBar.adjustValue(0);
            _mediaPlayer.pause();
            _playPause.setText("Play");
        });

        _mediaPlayer.currentTimeProperty().addListener( new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                String time = "";
                time += String.format("%02d", ((int)newValue.toMinutes()));;
                time += ":";
                time += String.format("%02d", ((int)newValue.toSeconds() % 60));
                _currentTime.setText(time);

                _timeBar.adjustValue(newValue.toMillis());
            }
        });

        _mediaView.setMediaPlayer(_mediaPlayer);
    }

    public void pausePlayMethodHandler() {
        if (_mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            _mediaPlayer.pause();
            _playPause.setText("Play");
        } else {
            _mediaPlayer.play();
            _playPause.setText("Pause");
        }
    }

    public void skipForwardButtonHandler() {
        if (_mediaPlayer.getCurrentTime().add(Duration.seconds(10)).lessThan(_mediaPlayer.getTotalDuration())) {
            _mediaPlayer.seek(_mediaPlayer.getCurrentTime().add(Duration.seconds(10)));
        } else {
            _mediaPlayer.seek(_mediaPlayer.getTotalDuration());
        }
    }

    public void skipBackButtonHandler() {
        if (_mediaPlayer.getCurrentTime().add(Duration.seconds(10)).lessThan(_mediaPlayer.getTotalDuration())) {
            _mediaPlayer.seek(_mediaPlayer.getCurrentTime().add(Duration.seconds(10)));
        } else if (! _mediaPlayer.getCurrentTime().add(Duration.millis(100)).lessThan(_mediaPlayer.getTotalDuration())) {
            //If it is 0.1 seconds from the end, do nothing
        } else {
            _mediaPlayer.seek(_mediaPlayer.getTotalDuration().add(Duration.millis(-100)));
        }
    }

    public void submitAnswerButtonHandler(ActionEvent event) throws IOException {
        String playerAnswer = _searchTermField.getText();
        _answerList.add(new Answer(_correctAnswers.get(index), playerAnswer));

        _mediaPlayer.stop();
        _searchTermField.clear();

        if (++index < _selectedCreations.size()) {
            Media creationMedia = new Media(Paths.get(ApplicationFolder.RedactedCreations.getPath() + File.separator + _selectedCreations.get(index) + ".mp4").toUri().toString());
            _mediaPlayer = new MediaPlayer(creationMedia);
            setUpNewMediaPlayer();
        } else {
            ApplicationScene displayQuizResultsScene = changeScene(SceneType.DisplayQuizResultsScene, event);
            ((DisplayQuizResultsScene) displayQuizResultsScene).setResults(_answerList);
        }
    }

    public void homeButtonHandler(ActionEvent event) throws IOException {
        Alert homeAlert = createConfirmationAlert("Are you sure you want to exit the quiz? Your progress will be lost.");
        if (homeAlert.getResult() == ButtonType.YES) {
            changeScene(SceneType.MainMenuScene, event);
        }
    }

    public void quitButtonHandler() {
        System.exit(0);
    }
}
