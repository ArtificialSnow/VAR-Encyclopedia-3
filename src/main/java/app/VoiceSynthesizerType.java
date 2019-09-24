package main.java.app;

public enum VoiceSynthesizerType {
    Default("", "Default");

    private String _flag;
    private String _name;

    private VoiceSynthesizerType(String flag, String name) {
        _flag = flag;
        _name = name;
    }

    public String getFlag() {
        return _flag;
    }
}
