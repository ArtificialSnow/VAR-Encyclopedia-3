package main.java.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CreationFactory {

    public void combineImagesToVideo(String imageNames, int numberOfImages) {
        String getDurationCommand = "soxi -D ./VAR-Encyclopedia/.temp/tempCombinedChunks.wav";
        ProcessBuilder getDuration = new ProcessBuilder("bash", "-c", getDurationCommand);

        try {
            Process getDurationProcess = getDuration.start();
            BufferedReader stdout = new BufferedReader(new InputStreamReader(getDurationProcess.getInputStream()));
            getDurationProcess.waitFor();

            double duration = Double.parseDouble(stdout.readLine());

            String combineImageFile = "cat " + imageNames + " | " + "ffmpeg -y -framerate "+ numberOfImages/duration + " -i -"+ " -c:v libx264 -vf fps=25 -pix_fmt yuv420p ./VAR-Encyclopedia/.temp/combinedImages.mp4";
            ProcessBuilder combineImagesToVideoBuilder = new ProcessBuilder("bash", "-c", combineImageFile);

            Process combineImagesToVideoProcess = combineImagesToVideoBuilder.start();
            combineImagesToVideoProcess.waitFor();

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error combing image to video");
        }

    }

    public void combineVideoAndText(String searchTerm) {
        String addTermToFile = "ffmpeg -y -i \"./VAR-Encyclopedia/.temp/combinedImages.mp4\" -vf drawtext=\"fontfile=/path/to/font.ttf: text="+searchTerm+": fontcolor=white: fontsize=24: box=1: boxcolor=black@0.5: boxborderw=5: x=(w-text_w)/2: y=(h-text_h)/2\" -codec:a copy ./VAR-Encyclopedia/.temp/combinedVideo.mp4" ;
        ProcessBuilder combineVideoAndTextBuilder = new ProcessBuilder("bash", "-c", addTermToFile);

        try {
            Process combineVideoAndTextProcess = combineVideoAndTextBuilder.start();
            combineVideoAndTextProcess.waitFor();
        } catch (Exception e) {
            System.out.println("Error combing Video and Text");
        }

    }

    public void addBGMToVideo(String nameOfMusic){
        String getDurationCommand = "soxi -D ./VAR-Encyclopedia/.temp/tempCombinedChunks.wav";
        ProcessBuilder getDuration = new ProcessBuilder("bash", "-c", getDurationCommand);

        try {
            Process getDurationProcess = getDuration.start();
            BufferedReader stdout = new BufferedReader(new InputStreamReader(getDurationProcess.getInputStream()));
            getDurationProcess.waitFor();

            double duration = Double.parseDouble(stdout.readLine());

            String trimBGMCommand = "ffmpeg -i ./VAR-Encyclopedia/.temp/BGM/"+nameOfMusic+".mp3 -ss 0 -to "+duration+" -c copy ./VAR-Encyclopedia/.temp/"+nameOfMusic+"_trim.mp3";
            ProcessBuilder trimBGMBuilder = new ProcessBuilder("bash","-c",trimBGMCommand);
            Process trimBGMProcess = trimBGMBuilder.start();
            trimBGMProcess.waitFor();
        } catch (Exception e) {
            System.out.println("Error when trim background music");
        }


//        String addBGMToVideoCommand = "ffmpeg -y -i ./VAR-Encyclopedia/.temp/"+nameOfMusic+"_trim.mp3 -i ./VAR-Encyclopedia/.temp/combinedVideo.mp4 -filter_complex \"[0:a][1:a]amerge,pan=stereo|c0<c0+c2|c1<c1+c3[out]\" -map 1:v -map \"[out]\" -c:v copy -shortest ./VAR-Encyclopedia/.temp/BackgroundVideo.mp4";
        String addBGMToVideoCommand = "ffmpeg -y -i ./VAR-Encyclopedia/.temp/"+nameOfMusic+"_trim.mp3 -i ./VAR-Encyclopedia/.temp/combinedVideo.mp4 -c:a aac -strict experimental ./VAR-Encyclopedia/.temp/BackgroundVideo.mp4";
        ProcessBuilder addBGMToVideoBuilder = new ProcessBuilder("bash","-c",addBGMToVideoCommand);
        try {
            Process addBGMToVideoProcess = addBGMToVideoBuilder.start();
            addBGMToVideoProcess.waitFor();
        } catch (Exception e) {
            System.out.println("Error adding background music");
        }
    }

    public void combineVideoAndAudio(String nameOfCreation) {
//        String combineVideoAndAudioCommand = "ffmpeg -y -i ./VAR-Encyclopedia/.temp/combinedVideo.mp4 -i ./VAR-Encyclopedia/.temp/tempCombinedChunks.wav -c:a aac -strict experimental ./VAR-Encyclopedia/Creations/"+nameOfCreation+".mp4";
        String combineVideoAndAudioCommand = "ffmpeg -y -i ./VAR-Encyclopedia/.temp/BackgroundVideo.mp4 -i ./VAR-Encyclopedia/.temp/tempCombinedChunks.wav -c:a aac -strict experimental ./VAR-Encyclopedia/Creations/"+nameOfCreation+".mp4";
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
