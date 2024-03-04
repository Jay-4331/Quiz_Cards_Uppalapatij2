package com.example.Quiz_Cards;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class GameRulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_rules_activity);

        Button startGameButton = findViewById(R.id.button_start_game);

        // Set click listener for the start game button
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start QuizActivity when the button is clicked
                Intent intent = new Intent(GameRulesActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });

    }
}
