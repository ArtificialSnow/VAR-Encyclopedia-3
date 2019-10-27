package main.java.app;

import java.io.File;

/**
 * An enum which represents folders created by the application. Used to obtain the path to these files.
 */
public enum ApplicationFolder {

    Main("VAR-Encyclopedia"),
    Creations("VAR-Encyclopedia/Creations"),
    RegularCreations("VAR-Encyclopedia/Creations/RegularCreations"),
    RedactedCreations("VAR-Encyclopedia/Creations/RedactedCreations"),
    AudioChunks("VAR-Encyclopedia/AudioChunks"),
    Temp("VAR-Encyclopedia/.temp"),
    TempImages("VAR-Encyclopedia/.temp/Images");


    private String _path;

    private ApplicationFolder(String path) {
        _path = path.replaceAll("/", File.separator);
    }

    public String getPath() {
        return _path;
    }
}
