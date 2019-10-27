package main.java.scenes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class CreationsViewer {

    @FXML private MediaView _mediaView;
    @FXML private Label _currentTime;
    @FXML private Slider _timeBar;
    @FXML private Button _playPause;
    @FXML private Button _skipForward;
    @FXML private Button _skipBackward;

    private MediaPlayer _mediaPlayer;

    public void setMedia(Media media) {
        _mediaPlayer = new MediaPlayer(media);
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
        _mediaPlayer.setAutoPlay(true);
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
        } else if (! _mediaPlayer.getCurrentTime().add(Duration.millis(100)).lessThan(_mediaPlayer.getTotalDuration())) {
            //If it is 0.1 seconds from the end, do nothing
        } else {
            _mediaPlayer.seek(_mediaPlayer.getTotalDuration().add(Duration.millis(-100)));
        }
    }

    public void skipBackButtonHandler() {
        if (_mediaPlayer.getCurrentTime().add(Duration.seconds(-10)).greaterThan(Duration.ZERO)) {
            _mediaPlayer.seek(_mediaPlayer.getCurrentTime().add(Duration.seconds(-10)));
        } else {
            _mediaPlayer.seek(Duration.ZERO);
        }
    }

    public void stopMedia() {
        if (_mediaPlayer != null) {
            _mediaPlayer.stop();
        }
    }
}
