package main.java.app;

public class Answer {

    String _correctAnswer;
    String _enteredAnswer;
    boolean _correct;

    public Answer(String correctAnswer, String enteredAnswer) {
        _correctAnswer = correctAnswer;
        _enteredAnswer = enteredAnswer;
        _correct = correctAnswer.equals(enteredAnswer);
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
