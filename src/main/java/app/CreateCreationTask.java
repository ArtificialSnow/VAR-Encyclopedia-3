package main.java.app;

import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CreateCreationTask extends Task<Void> {
    private int _duration;
    private int _numberOfImages;
    private String _searchTerm;
    private String _nameOfCreation;

    public CreateCreationTask(int _numberOfImages, String _searchTerm, String _nameOfCreation) {
        this._numberOfImages = _numberOfImages;
        this._searchTerm = _searchTerm;
        this._nameOfCreation = _nameOfCreation;
    }

    @Override
    protected Void call() throws Exception {
        try {


            String addTermToFile = "./addTermToFile.sh " + _searchTerm;
            String combine = "./combine.sh " + _nameOfCreation;

            String command = "soxi -D combinedAudio.wav";
            ProcessBuilder getDuration = new ProcessBuilder("bash", "-c", command);
            Process process = getDuration.start();

            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));

            process.waitFor();
            _duration = Integer.parseInt(stdout.readLine());

            System.out.println("find the duration of audio:"+_duration);

            String combineAudioFile = "./combineAudioFile.sh " + _nameOfCreation;
            ProcessBuilder combineAudio = new ProcessBuilder("bash", "-c", combineAudioFile);
            Process CA = combineAudio.start();
            CA.waitFor();

            System.out.println("Finished to combine Audio");

            String combineImageFile = "./combineImageFile.sh " + _duration + " " + _numberOfImages;
            ProcessBuilder combineImage = new ProcessBuilder("bash", "-c", combineImageFile);
            Process CI = combineImage.start();
            CI.waitFor();

            System.out.println("Finished to combine Images");

            ProcessBuilder addTerm = new ProcessBuilder("bash", "-c", addTermToFile);
            ProcessBuilder combineAudioVideo = new ProcessBuilder("bash", "-c", combine);
            ProcessBuilder finalCreate = new ProcessBuilder("bash", "-c", finalCreation);


        }catch(IOException e){
            e.printStackTrace();

        }
        return null;
    }
}
