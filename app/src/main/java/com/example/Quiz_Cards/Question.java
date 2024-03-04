package com.example.Quiz_Cards;

import java.util.List;

public class Question {

    private String questionText;
    private List<String> options;
    private List<Integer> correctAnswers;

    public Question(String questionText, List<String> options, List<Integer> correctAnswers) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswers = correctAnswers;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public List<Integer> getCorrectAnswers() {
        return correctAnswers;
    }
}
