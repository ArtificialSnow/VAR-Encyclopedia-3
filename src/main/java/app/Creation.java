package main.java.app;

/**
 * Encapsulates the information which represents a creation
 */
public class Creation {

    String _name;
    String _searchTerm;

    public Creation(String name, String searchTerm) {
        _name = name;
        _searchTerm = searchTerm;
    }

    public String getName() {
        return _name;
    }

    public String getSearchTerm() {
        return _searchTerm;
    }
}
