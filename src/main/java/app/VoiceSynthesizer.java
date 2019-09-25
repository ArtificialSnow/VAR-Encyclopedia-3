package main.java.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VoiceSynthesizer {

    private List<String> _voicesUnformatted;
    private List<String> _voicesList;

    public VoiceSynthesizer() {
        runGetSynthVoicesBashScript();
    }

    public List<String> getVoices() {
        _voicesList = new ArrayList<String>();

        for (String voice : _voicesUnformatted.get(0).split(" ")){
            _voicesList.add(voice);
        }

        return _voicesList;
    }

    public void setVoice(String voice) {

    }

    private void runGetSynthVoicesBashScript() {
        _voicesUnformatted = new ArrayList<String>();

        BufferedReader stdout = null;

        try {

            ProcessBuilder getVoicesBuilder = new ProcessBuilder("sh", "-c", "./src/main/resources/shellscripts/getSynthesizerVoices.sh ");
            Process getVoicesProcess = getVoicesBuilder.start();
            int exitStatus = getVoicesProcess.waitFor();

            stdout = new BufferedReader(new InputStreamReader(getVoicesProcess.getInputStream()));

            if (exitStatus == 0) {
                String line;
                while ((line = stdout.readLine()) != null) {

                    _voicesUnformatted.add(line);
                }
            } else {

                System.out.println("Error due to getSynthesizerVoices.sh exiting with status 1.");
            }
            stdout.close();

        } catch (Exception e) {
            System.out.println("Error getting the voices list.");

            if (stdout != null) {
                try {
                    stdout.close();
                } catch (IOException IOexe) {
                    System.out.println("Error closing input stream.");
                }
            }
        }
    }
}
