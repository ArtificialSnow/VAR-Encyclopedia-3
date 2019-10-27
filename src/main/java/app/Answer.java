package main.java.app;

/**
 * This class represents the information of a Quiz Answer.
 */
public class Answer {

    String _correctAnswer;
    String _enteredAnswer;
    boolean _correct;

    public Answer(String correctAnswer, String enteredAnswer) {
        _correctAnswer = correctAnswer;
        _enteredAnswer = enteredAnswer;
        _correct = correctAnswer.equalsIgnoreCase(enteredAnswer);
    }

    public String getCorrectAnswer() {
        return _correctAnswer;
    }

    public String getEnteredAnswer() {
        return _enteredAnswer;
    }

    public String getCorrect() {
        if (_correct) {
            return "Correct";
        } else {
            return "Incorrect";
        }
    }
}
