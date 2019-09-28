package main.java.app;

import java.io.File;
import java.util.List;

public class AudioFactory {

    public void playAudioChunk(String searchTerm, String audioChunkName) {
        String[] playAudioChunkCommands = { "sh", "-c", "./src/main/resources/shellscripts/playAudioChunk.sh" + " \"" + searchTerm + "\" \"" + audioChunkName +"\"" };
        ProcessBuilder playAudioChunkBuilder = new ProcessBuilder(playAudioChunkCommands);

        try {
            Process playAudioChunkProcess = playAudioChunkBuilder.start();
            playAudioChunkProcess.waitFor();
        } catch (Exception e) {
            System.out.print("Error playing audio chunk");
        }
    }

    public void deleteAudioChunk(String searchTerm, String audioChunkName) {
        String[] deleteAudioChunkCommands = { "sh", "-c", "./src/main/resources/shellscripts/deleteAudioChunk.sh" + " \"" + searchTerm + "\" \"" + audioChunkName +"\"" };
        ProcessBuilder deleteAudioChunkBuilder = new ProcessBuilder(deleteAudioChunkCommands);

        try {
            Process deleteAudioChunkProcess = deleteAudioChunkBuilder.start();
            deleteAudioChunkProcess.waitFor();
        } catch (Exception e) {
            System.out.print("Error deleting audio chunk");
        }
    }

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
