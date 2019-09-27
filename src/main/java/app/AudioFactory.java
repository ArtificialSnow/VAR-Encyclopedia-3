package main.java.app;

import java.io.File;
import java.util.List;

public class AudioFactory {

    public void previewAudioChunk(String audioChunk, String voiceSynthesizerType) {
        String[] previewAudioCommands = { "sh", "-c", "./src/main/resources/shellscripts/previewAudioChunk.sh" + " \"" + voiceSynthesizerType + "\" \"" + audioChunk +"\"" };
        ProcessBuilder previewAudioBuilder = new ProcessBuilder(previewAudioCommands);

        try {
            Process previewAudioProcess = previewAudioBuilder.start();
            previewAudioProcess.waitFor();
        } catch (Exception e) {
            System.out.print("Error previewing audio chunk");
        }
    }

    public void saveAudioChunk(String audioChunk, String voiceSynthesizerType, String searchTerm, String chunkName) {
        String[] saveAudioCommands = { "sh", "-c", "./src/main/resources/shellscripts/saveAudioChunk.sh"
                + " \"" + chunkName + "\" \"" + searchTerm + "\" \"" + voiceSynthesizerType + "\" \"" + audioChunk +"\"" };
        ProcessBuilder saveAudioBuilder = new ProcessBuilder(saveAudioCommands);

        try {
            Process saveAudioProcess = saveAudioBuilder.start();
            saveAudioProcess.waitFor();
        } catch (Exception e) {
            System.out.print("Error saving audio chunk");
        }
    }

    public boolean chunkAlreadyExists(String searchTerm, String chunkName) {
        boolean directoryFound = false;
        File[] fileList = new File("VAR-Encyclopedia/AudioChunks").listFiles();

        for (File file : fileList) {
            if (file.getName().equals(searchTerm)) {
                directoryFound = true;
            }
        }

        if (directoryFound) {
            File[] chunkList = new File("VAR-Encyclopedia/AudioChunks/" + searchTerm).listFiles();

            for (File chunk : chunkList) {
                if (chunk.getName().equals(chunkName + ".wav")) {
                    return true;
                }
            }
        }

        return false;
    }

    public void combineAudioChunks(List<String> audioChunksList) {
        //to do
    }

}
