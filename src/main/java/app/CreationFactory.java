package main.java.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CreationFactory {

    public void combineImagesToVideo(int numberOfImages) {
        String getDurationCommand = "soxi -D combinedAudio.wav";
        ProcessBuilder getDuration = new ProcessBuilder("bash", "-c", getDurationCommand);

        try{
            Process getDurationProcess = getDuration.start();
            BufferedReader stdout = new BufferedReader(new InputStreamReader(getDurationProcess.getInputStream()));
            getDurationProcess.waitFor();

            int duration = Integer.parseInt(stdout.readLine());

            String combineImageFile = "./combineImageFile.sh " + duration + " " + numberOfImages;
            ProcessBuilder combineImagesToVideoBuilder = new ProcessBuilder("bash", "-c", combineImageFile);

            Process combineImagesToVideoProcess = combineImagesToVideoBuilder.start();
            combineImagesToVideoProcess.waitFor();

        } catch(Exception e) {

        }

    }

    public void combineVideoAndText(String searchTerm) {
        String addTermToFile = "./addTermToFile.sh " + searchTerm;
        ProcessBuilder combineVideoAndTextBuilder = new ProcessBuilder("bash", "-c", addTermToFile);
        try{
            Process combineVideoAndTextProcess = combineVideoAndTextBuilder.start();
            combineVideoAndTextProcess.waitFor();
        } catch (Exception e) {
            System.out.println("Error combing Video and Text");
        }

    }

    public void combineVideoAndAudio(String nameOfCreation) {
        String combineVideoAndAudioCommand = "./combine.sh " + nameOfCreation;
        ProcessBuilder combineVideoAndAudioBuilder = new ProcessBuilder("bash", "-c", combineVideoAndAudioCommand);
        try{
            Process combineVideoAndAudioProcess = combineVideoAndAudioBuilder.start();
            combineVideoAndAudioProcess.waitFor();
        } catch (Exception e) {
            System.out.print("Error combing Video and Audio");
        }

    }
    public void deleteCreation(String creationName) {
        String[] deleteCreationCommands = { "sh", "-c", "./src/main/resources/shellscripts/deleteCreation.sh" + " \"" + creationName +"\"" };
        ProcessBuilder deleteCreationBuilder = new ProcessBuilder(deleteCreationCommands);

        try {
            Process deleteCreationProcess = deleteCreationBuilder.start();
            deleteCreationProcess.waitFor();
        } catch (Exception e) {
            System.out.print("Error playing audio chunk");
        }
    }
}
