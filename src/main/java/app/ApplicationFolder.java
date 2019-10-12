package main.java.app;

public enum ApplicationFolder {

    Main("VAR-Encyclopedia"),
    Creations("VAR-Encyclopedia/Creations"),
    RegularCreation("VAR-Encyclopedia/Creations/RegularCreations"),
    RedactedCreation("VAR-Encyclopedia/Creations/RedactedCreations"),
    AudioChunks("VAR-Encyclopedia/AudioChunks"),
    Temp("VAR-Encyclopedia/.temp"),
    TempImages("VAR-Encyclopedia/.temp/Images");


    private String _path;

    private ApplicationFolder(String path) {
        _path = path;
    }
}
