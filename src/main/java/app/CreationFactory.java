package main.java.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class CreationFactory {

    public void combineImagesToVideo(String imageNames, int numberOfImages) {
        String getDurationCommand = "soxi -D " + ApplicationFolder.Temp.getPath() + "/tempCombinedChunks.wav";
        ProcessBuilder getDuration = new ProcessBuilder("bash", "-c", getDurationCommand);

        try {
            Process getDurationProcess = getDuration.start();
            BufferedReader stdout = new BufferedReader(new InputStreamReader(getDurationProcess.getInputStream()));
            getDurationProcess.waitFor();

            double duration = Double.parseDouble(stdout.readLine());

            String combineImageFile = "cat " + imageNames + " | " + "ffmpeg -y -framerate "+ numberOfImages/duration + " -i -"+ " -c:v libx264 -vf fps=25 -pix_fmt yuv420p " + ApplicationFolder.Temp.getPath() + "/combinedImages.mp4";
            ProcessBuilder combineImagesToVideoBuilder = new ProcessBuilder("bash", "-c", combineImageFile);

            Process combineImagesToVideoProcess = combineImagesToVideoBuilder.start();
            combineImagesToVideoProcess.waitFor();

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error combing image to video");
        }

    }

    public void combineVideoAndText(String searchTerm) {
        String addTermToFile = "ffmpeg -y -i \"" + ApplicationFolder.Temp.getPath() + "/combinedImages.mp4\" -vf drawtext=\"fontfile=/path/to/font.ttf: text="+searchTerm+": fontcolor=white: fontsize=24: box=1: boxcolor=black@0.5: boxborderw=5: x=(w-text_w)/2: y=(h-text_h)/2\" -codec:a copy " + ApplicationFolder.Temp.getPath() + "/combinedVideo.mp4" ;
        ProcessBuilder combineVideoAndTextBuilder = new ProcessBuilder("bash", "-c", addTermToFile);

        try {
            Process combineVideoAndTextProcess = combineVideoAndTextBuilder.start();
            combineVideoAndTextProcess.waitFor();
        } catch (Exception e) {
            System.out.println("Error combing Video and Text");
        }

    }

    public void combineVideoAndAudio(String nameOfCreation) {
        String combineVideoAndAudioCommand = "ffmpeg -y -i " + ApplicationFolder.Temp.getPath() + "/combinedVideo.mp4 -i " + ApplicationFolder.Temp.getPath() + "/tempCombinedChunks.wav -c:a aac -strict experimental " + ApplicationFolder.RegularCreations.getPath() + File.separator + nameOfCreation+ ".mp4";
        String createRedactedVideoCommand = "ffmpeg -y -i " + ApplicationFolder.Temp.getPath() + "/combinedImages.mp4 -i " + ApplicationFolder.Temp.getPath() + "/tempRedactedCombinedChunks.wav -c:a aac -strict experimental " + ApplicationFolder.RedactedCreations.getPath() + File.separator + nameOfCreation+ ".mp4";
        ProcessBuilder combineVideoAndAudioBuilder = new ProcessBuilder("bash", "-c", combineVideoAndAudioCommand + "; " + createRedactedVideoCommand);
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

    public void writeToCreationsFile(String creationName, String searchTerm) {
        String[] writeToCreationsFileCommands = { "sh", "-c", "./src/main/resources/shellscripts/writeToCreationsFile.sh" + " \"" + creationName +"\"" + " \"" + searchTerm +"\""};
        ProcessBuilder writeToCreationsFileBuilder = new ProcessBuilder(writeToCreationsFileCommands);

        try {
            Process writeToCreationsFileProcess = writeToCreationsFileBuilder.start();
            writeToCreationsFileProcess.waitFor();
        } catch (Exception e) {
            System.out.print("Error saving creation to file");
        }
    }

    public void deleteFromCreationsFile(String creationName, String searchTerm) {
        String[] deleteFromCreationsFileCommands = { "sh", "-c", "./src/main/resources/shellscripts/deleteFromCreationsFile.sh" + " \"" + creationName +"\"" + " \"" + searchTerm +"\"" };
        ProcessBuilder deleteFromCreationsFileBuilder = new ProcessBuilder(deleteFromCreationsFileCommands);

        try {
            Process deleteFromCreationsFileProcess = deleteFromCreationsFileBuilder.start();
            deleteFromCreationsFileProcess.waitFor();
        } catch (Exception e) {
            System.out.print("Error deleting from creation file");
        }
    }
}
