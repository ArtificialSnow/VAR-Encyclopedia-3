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

        //Non-case sensitive, also does not differentiate between answers with an s or es on the end, to try and make as many plurals correct as possible.
        _correct = (correctAnswer.equalsIgnoreCase(enteredAnswer) || (correctAnswer + "s").equalsIgnoreCase(enteredAnswer) || correctAnswer.equalsIgnoreCase(enteredAnswer + "s")
                || (correctAnswer + "es").equalsIgnoreCase(enteredAnswer) || correctAnswer.equalsIgnoreCase(enteredAnswer + "es"));
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
