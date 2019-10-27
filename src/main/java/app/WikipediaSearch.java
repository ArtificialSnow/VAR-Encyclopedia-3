package main.java.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class uses ProcessBuilders and the wikit command-line command to search terms on wikipedia and obtain the search results.
 */
public class WikipediaSearch {

    private String _pathToBash;
    private List<String> _wikipediaContent;
    private List<String> _formattedWikipediaContent;


    public WikipediaSearch(String searchTerm) {

        _pathToBash = "/bin/bash";
        _wikipediaContent = search(searchTerm);
        _formattedWikipediaContent = splitByLine(_wikipediaContent);
    }


    //Returns a copy of the List of Lines associated with a particular wikipedia search.
    public List<String> getContent() {
        List<String> copiedArrayList = new ArrayList<String>();

        for (String line : _formattedWikipediaContent) {
            copiedArrayList.add(line);
        }

        return copiedArrayList;
    }

    //Checks whether the content exists on wikipedia.
    public boolean contentDoesNotExist() {
        return (_wikipediaContent.size() == 0 || _wikipediaContent.get(0).contains(":^("));
    }


    //Searches for a particular search on wikipedia using the wikit bash command.
    private List<String> search(String searchTerm) {
        List<String> content = new ArrayList<String>();
        BufferedReader stdout = null;

        try {

            ProcessBuilder getContentBuilder = new ProcessBuilder(_pathToBash, "-c", "wikit " + searchTerm);
            Process getContentProcess = getContentBuilder.start();
            int exitStatus = getContentProcess.waitFor();

            stdout = new BufferedReader(new InputStreamReader(getContentProcess.getInputStream()));


            if (exitStatus == 0) {

                String line;
                while ((line = stdout.readLine()) != null) {

                    content.add(line);
                }
            } else {

                System.out.println("Error due to wikit command exiting with exit status 1.");
            }

            stdout.close();

        } catch (Exception e) {

            System.out.println("Error getting the wikipedia content.");

            if (stdout != null) {
                try {

                    stdout.close();
                } catch (IOException IOexe) {

                    System.out.println("Error closing input stream.");
                }
            }
        }

        return content;
    }


    //Takes the output from wikit and creates an ArrayList containing the lines.
    private List<String> splitByLine(List<String> content) {

        ArrayList<String> formattedContent = new ArrayList<String>();

        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.ENGLISH);
        iterator.setText(content.get(0));
        int start = iterator.first();

        for (int end = iterator.next();end != BreakIterator.DONE; start = end, end = iterator.next()) {

            formattedContent.add(content.get(0).substring(start,end).trim());
        }

        return formattedContent;
    }
}

