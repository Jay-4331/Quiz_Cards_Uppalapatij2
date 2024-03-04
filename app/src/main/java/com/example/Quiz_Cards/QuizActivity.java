package com.example.Quiz_Cards;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class QuizActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private RadioGroup radioGroupOptions;
    private Button buttonConfirm;
    private Button buttonNext;
    private List<Question> questions;

    private Button buttonScoreHistory;
    private int currentQuestionIndex = 0;
    private SparseArray<Integer> userAnswers = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);

        // Initialize views
        textViewQuestion = findViewById(R.id.text_view_question);
        radioGroupOptions = findViewById(R.id.radio_group_options);
        buttonNext = findViewById(R.id.button_next);
        buttonConfirm = findViewById(R.id.button_confirm);
        buttonScoreHistory = findViewById(R.id.button_score_history);
        buttonScoreHistory.setVisibility(View.GONE);

        // Populate sample questions
        populateQuestions();

        // Display first question
        displayQuestion(currentQuestionIndex);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a confirmation dialog
                showConfirmationDialog();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the next question or show result
                if (currentQuestionIndex < questions.size() - 1) {
                    currentQuestionIndex++;
                    displayQuestion(currentQuestionIndex);
                } else {
                    showResult(); // Call showResult() when the user has answered all questions
                }
            }
        });

        buttonScoreHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show score history dialog
                showScoreHistory();
            }
        });

    }

    private void populateQuestions() {
        questions = new ArrayList<>();

        // Add your questions here
        // Sample Question 1
        List<String> options1 = new ArrayList<>();
        options1.add("2000");
        options1.add("2007");
        options1.add("2011");
        List<Integer> correctAnswers1 = new ArrayList<>();
        correctAnswers1.add(1);
        questions.add(new Question("1.What year was Android released?", options1, correctAnswers1));

        // Sample Question 2
        List<String> options2 = new ArrayList<>();
        options2.add("Right");
        options2.add("Left");
        options2.add("Center");
        List<Integer> correctAnswers2 = new ArrayList<>();
        correctAnswers2.add(2);
        questions.add(new Question("2.What text-alignment property is used to put the content in the center of the screen?", options2, correctAnswers2));

        // Sample Question 3
        List<String> options3 = new ArrayList<>();
        options3.add("Flash Drive");
        options3.add("Label");
        options3.add("Image");
        List<Integer> correctAnswers3 = new ArrayList<>();
        correctAnswers3.add(0);
        questions.add(new Question("3.Which of the following is not an element you can add to your screen in App Lab?", options3, correctAnswers3));

        // Sample Question 4
        List<String> options4 = new ArrayList<>();
        options4.add("To generate an event");
        options4.add("Select 1 value at a time");
        options4.add("To create radio buttons");
        List<Integer> correctAnswers4 = new ArrayList<>();
        correctAnswers4.add(1);
        questions.add(new Question("4.What are radio buttons used for?", options4, correctAnswers4));

        // Sample Question 5
        List<String> options5 = new ArrayList<>();
        options5.add("Storing Value in it");
        options5.add("Select single value");
        options5.add("Select more than one value");
        List<Integer> correctAnswers5 = new ArrayList<>();
        correctAnswers5.add(2);
        questions.add(new Question("5.What are checkboxes used for?", options5, correctAnswers5));

        // Add more questions as needed
    }

    private void displayQuestion(int index) {
        Question question = questions.get(index);
        textViewQuestion.setText(question.getQuestionText());
        radioGroupOptions.removeAllViews();
        for (int i = 0; i < question.getOptions().size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(question.getOptions().get(i));
            radioGroupOptions.addView(radioButton);
        }
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to confirm your answer?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Record the user's answer and check if it's correct
                recordAnswerAndCheck();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void recordAnswerAndCheck() {
        int selectedOptionIndex = getSelectedOptionIndex();
        userAnswers.put(currentQuestionIndex, selectedOptionIndex);

        // Check if the answer is correct
        boolean isCorrect = checkAnswer(currentQuestionIndex, selectedOptionIndex);
        if (isCorrect) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
        }

        // Move to the next question or show result
        moveToNextQuestion();
    }

    private void moveToNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            displayQuestion(currentQuestionIndex);
        } else {
            showResult();
        }
    }

    private boolean checkAnswer(int questionIndex, int selectedOptionIndex) {
        Question question = questions.get(questionIndex);
        List<Integer> correctAnswers = question.getCorrectAnswers();
        return correctAnswers.contains(selectedOptionIndex);
    }

    private int getSelectedOptionIndex() {
        int selectedOptionIndex = -1;
        int selectedRadioButtonId = radioGroupOptions.getCheckedRadioButtonId();

        if (selectedRadioButtonId != -1) {
            // Find the index of the selected radio button
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            String selectedOptionText = selectedRadioButton.getText().toString();

            // Find the index of the selected option text in the question's options list
            Question question = questions.get(currentQuestionIndex);
            selectedOptionIndex = question.getOptions().indexOf(selectedOptionText);
        }

        return selectedOptionIndex;
    }

    private int calculateScore() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            int selectedOptionIndex = userAnswers.get(i, -1);
            if (selectedOptionIndex != -1 && checkAnswer(i, selectedOptionIndex)) {
                score++;
            }
        }
        return score;
    }

    private void showResult() {
        // Calculate the score here
        int score = calculateScore();

        saveScoreHistory(score);

        // Start ResultActivity to display the result
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("SCORE", score);
        startActivity(intent);
    }

    private void saveScoreHistory(int score) {
        SharedPreferences preferences = getSharedPreferences("QuizHistory", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Retrieve existing score history
        Set<String> scoreHistory = preferences.getStringSet("scoreHistory", new HashSet<String>());

        // Add the current score to the history
        scoreHistory.add("Score: " + score + ", Date: " + getCurrentDateTime());

        // Save the updated score history
        editor.putStringSet("scoreHistory", scoreHistory);
        editor.apply();
    }

    // Helper method to get current date and time
    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date());
    }


    private void showScoreHistory() {
        // Retrieve score history from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("QuizHistory", MODE_PRIVATE);
        Set<String> scoreHistorySet = preferences.getStringSet("scoreHistory", new HashSet<String>());
        List<String> scoreHistoryList = new ArrayList<>(scoreHistorySet);

        // Display the score history in a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Score History");

        // Convert score history list to array
        String[] scoreHistoryArray = scoreHistoryList.toArray(new String[0]);
        builder.setItems(scoreHistoryArray, null);

        builder.setPositiveButton("OK", null);
        builder.show();
    }


}





