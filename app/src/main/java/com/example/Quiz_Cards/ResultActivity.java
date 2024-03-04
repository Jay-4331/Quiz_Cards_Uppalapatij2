package com.example.Quiz_Cards;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ResultActivity extends AppCompatActivity {

    private TextView textViewScore;
    private Button buttonStartAgain;

    private Button buttonScoreHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewScore = findViewById(R.id.text_view_display_score);
        buttonStartAgain = findViewById(R.id.button_start_again);

        Button buttonScoreHistory = findViewById(R.id.button_score_history);
        buttonScoreHistory.setVisibility(View.VISIBLE);

        // Get the score from the intent
        int score = getIntent().getIntExtra("SCORE", 0);

        // Display the score
        textViewScore.setText(String.valueOf(score));

        saveScoreHistory(score);

        // Set click listener for the "Start Again" button
        buttonStartAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the QuizActivity again
                Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
                startActivity(intent);
                // Finish this activity to remove it from the back stack
                finish();
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