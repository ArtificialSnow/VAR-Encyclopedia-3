package main.java.app;

import javafx.scene.media.Media;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a Quiz which is the active learning component of the application.
 */
public class Quiz {

    private int _numberOfQuestions;
    private int _numberOfCreations;
    private List<String> _redactedCreations;
    private List<String> _quizAnswers;

    public Quiz(int numberOfQuestions, int numberOfCreations) {
        _numberOfQuestions = numberOfQuestions;
        _numberOfCreations = numberOfCreations;

        _redactedCreations = new ArrayList<>();
        _quizAnswers = new ArrayList<>();

        try {
            generateQuiz();
        } catch (IOException e) {
            System.out.println("Error generating quiz");
        }
    }

    /**
     * Selects a specified number of random numbers (_numberOfQuestions) random numbers, within the range of 0 to the current number of creations (_numberOfCreations).
     * Each of these random numbers are used to select a creation for the quiz.
     * The creation name is added to _redactedCreations, and the search term for the creation is added to _quizAnswers.
     * @throws IOException
     */
    public void generateQuiz() throws IOException {
        final int[] indices = new Random().ints(0, _numberOfCreations).distinct().limit(_numberOfQuestions).toArray();

        for (int index : indices) {
            String[] creationInformation = Files.readAllLines(Paths.get(ApplicationFolder.Creations.getPath() + File.separator + "CurrentCreations.txt")).get(index).split(":");

            _redactedCreations.add(creationInformation[0]);
            _quizAnswers.add(creationInformation[1]);
        }
    }

    /**
     *
     * @return A list of all the creation names to be in the quiz.
     */
    public List<String> getMedia() {
        return _redactedCreations;
    }

    /**
     *
     * @return A list of all the search terms of the creations (which will be the answers to the quiz).
     */
    public List<String> getAnswers() {
        return _quizAnswers;
    }
}
