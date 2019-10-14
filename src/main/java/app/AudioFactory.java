package main.java.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
        File[] fileList = new File(ApplicationFolder.AudioChunks.getPath()).listFiles();

        for (File file : fileList) {
            if (file.getName().equals(searchTerm)) {
                directoryFound = true;
            }
        }

        if (directoryFound) {
            try {
                ArrayList<String> creations = new ArrayList<>(Files.readAllLines(Paths.get(ApplicationFolder.AudioChunks.getPath() + File.separator + searchTerm + File.separator + "AudioChunks.txt")));

                for (String creation : creations) {
                    System.out.println(creation);
                    System.out.println(chunkName);
                    if (creation.matches("^" + chunkName + ":.*")){
                        return true;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error opening currentAudioChunks.txt");
            }
        }

        return false;
    }

    public void combineAudioChunks(String searchTerm, String audioChunksString) {
        String[] combineAudioChunkCommands = { "sh", "-c", "./src/main/resources/shellscripts/combineAudioChunks.sh" + " \"" + searchTerm + "\" \"" + audioChunksString +"\"" };
        ProcessBuilder combineAudioChunkBuilder = new ProcessBuilder(combineAudioChunkCommands);

        try {
            Process combineAudioChunkProcess = combineAudioChunkBuilder.start();
            combineAudioChunkProcess.waitFor();
        } catch (Exception e) {
            System.out.print("Error combining audio chunks");
        }
    }
}
