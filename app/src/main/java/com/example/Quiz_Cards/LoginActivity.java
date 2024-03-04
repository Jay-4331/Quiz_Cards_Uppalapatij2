package com.example.Quiz_Cards;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.register_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText usernameEditText = findViewById(R.id.username_data);
                EditText passwordEditText = findViewById(R.id.password_data);
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // ERROR MESSAGES
                if (TextUtils.isEmpty(username)) {
                    usernameEditText.setError("Username cannot be empty");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError("Password cannot be empty");
                    return;
                }

                // Retrieve stored credentials from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                String storedUsername = sharedPreferences.getString(KEY_USERNAME, "");
                String storedPassword = sharedPreferences.getString(KEY_PASSWORD, "");

                // Check if entered credentials match stored credentials
                if (username.equals(storedUsername) && password.equals(storedPassword)) {
                    // Login successful, navigate to success screen
                    Intent intent = new Intent(LoginActivity.this, GameRulesActivity.class);
                    startActivity(intent);
                    finish(); // Finish LoginActivity so user can't go back to it
                } else {
                    //if login failed then error message
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Switch to Register Screen upon clicking the Register Button

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

