package main.java.app;

public class FileDirectory {

    private final String pathToBash;
    private final String checkIfMainFolderExists;
    private final String makeMainFolder;
    private final String makeAudioChunksFolder;
    private final String makeCreationsFolder;
    private final String makeTempFolder;
    private final String makeRegularCreationsFolder;
    private final String makeRedactedCreationsFolder;
    private final String makeCurrentCreationsTextFile;

    public FileDirectory() {

        pathToBash = "/bin/bash";
        checkIfMainFolderExists = "[ ! -d \"VAR-Encyclopedia\" ]";
        makeMainFolder = "mkdir VAR-Encyclopedia; ";
        makeAudioChunksFolder = "mkdir -p VAR-Encyclopedia/AudioChunks; ";
        makeCreationsFolder = "mkdir -p VAR-Encyclopedia/Creations; ";
        makeRegularCreationsFolder = "mkdir -p VAR-Encyclopedia/Creations/RegularCreations; ";
        makeRedactedCreationsFolder = "mkdir -p VAR-Encyclopedia/Creations/RedactedCreations; ";
        makeCurrentCreationsTextFile = "touch VAR-Encyclopedia/Creations/CurrentCreations.txt; ";
        makeTempFolder = "mkdir -p VAR-Encyclopedia/.temp; ";
    }

    //Checks if the required files to store media exist in the current directory and creates them if they do not.
    public void create() {
        String[] checkFilesCommands = { pathToBash, "-c", checkIfMainFolderExists };
        ProcessBuilder checkFilesBuilder = new ProcessBuilder(checkFilesCommands);

        try {
            Process checkFilesProcess = checkFilesBuilder.start();
            int exitStatus = checkFilesProcess.waitFor();

            if (exitStatus == 0) {
                String[] createFilesCommands = { pathToBash, "-c", makeMainFolder + makeCreationsFolder + makeRegularCreationsFolder + makeRedactedCreationsFolder +
                        makeCurrentCreationsTextFile + makeAudioChunksFolder + makeTempFolder };
                ProcessBuilder createFilesBuilder = new ProcessBuilder(createFilesCommands);

                Process createFilesProcess = createFilesBuilder.start();
                createFilesProcess.waitFor();
            }

        } catch (Exception e) {
            System.out.println("Error - Unable to ensure File Directory Exists");
        }
    }

    public void deleteAllEmptyDirectories(String directory) {
        String[] deleteEmptyDirectoriesCommands = { "sh", "-c", "./src/main/resources/shellscripts/deleteEmptyDirectories.sh" + " \"" + directory + "\"" };
        ProcessBuilder deleteEmptyDirectoriesBuilder = new ProcessBuilder(deleteEmptyDirectoriesCommands);

        try {
            Process deleteEmptyDirectoriesProcess = deleteEmptyDirectoriesBuilder.start();
            deleteEmptyDirectoriesProcess.waitFor();
        } catch (Exception e) {
            System.out.println("Error - Unable to delete empty directories");
        }
    }
}
