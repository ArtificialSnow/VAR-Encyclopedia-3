package main.java.app;

import java.util.List;

public class AudioFactory {

    public void previewAudioChunk(String audioChunk, String voiceSynthesizerType) {
        String[] createAudioCommands = { "sh", "-c", "./src/main/resources/shellscripts/previewAudioChunk.sh" + " \"" + voiceSynthesizerType + "\" \"" + audioChunk +"\"" };
        ProcessBuilder createAudioBuilder = new ProcessBuilder(createAudioCommands);

        try {
            Process createAudioProcess = createAudioBuilder.start();
            int exitStatus = createAudioProcess.waitFor();
            System.out.println(exitStatus);

        } catch (Exception e) {
            System.out.print("Error previewing audio chunk");
        }
    }

    public void saveAudioChunk(String audioChunk, String voiceSynthesizerType, String searchTerm, String chunkName) {

    }

    public void combineAudioChunks(List<String> audioChunksList) {
        //to do
    }

}
