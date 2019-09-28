package main.java.scenes;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class CreationsViewer {

    @FXML private MediaView _mediaView;

    private MediaPlayer _mediaPlayer;



    public void setMedia(Media media) {
        _mediaPlayer = new MediaPlayer(media);
        _mediaPlayer.setAutoPlay(true);
        _mediaView.setMediaPlayer(_mediaPlayer);
    }










}
