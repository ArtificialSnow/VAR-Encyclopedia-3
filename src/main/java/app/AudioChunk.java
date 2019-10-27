package main.java.app;

/**
 * Encapsulates the information representing an Audio Chunk.
 */
public class AudioChunk {

    String _name;
    String _searchTerm;
    String _voice;
    String _text;

    public AudioChunk(String name, String searchTerm, String voice, String text) {
        _name = name;
        _searchTerm = searchTerm;
        _voice = voice;
        _text = text;
    }

    public String getName() {
        return _name;
    }

    public String getSearchTerm() {
        return _searchTerm;
    }

    public String getVoice() {
        return _voice;
    }

    public String getText() {
        return _text;
    }

}
