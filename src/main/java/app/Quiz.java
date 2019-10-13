package main.java.app;

import javafx.scene.media.Media;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public void generateQuiz() throws IOException {
        final int[] indices = new Random().ints(0, _numberOfCreations).distinct().limit(_numberOfQuestions).toArray();

        for (int index : indices) {
            String[] creationInformation = Files.readAllLines(Paths.get(ApplicationFolder.Creations.getPath() + File.separator + "CurrentCreations.txt")).get(index).split(":");

            _redactedCreations.add(creationInformation[0]);
            _quizAnswers.add(creationInformation[1]);
        }
    }

    public List<String> getMedia() {
        return _redactedCreations;
    }

    public List<String> getAnswers() {
        return _quizAnswers;
    }
}
