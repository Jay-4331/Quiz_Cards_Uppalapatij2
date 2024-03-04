package com.example.Quiz_Cards;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameEditText = findViewById(R.id.username_data);
                EditText firstNameEditText = findViewById(R.id.first_name_data);
                EditText familyNameEditText = findViewById(R.id.family_name_data);
                EditText dobEditText = findViewById(R.id.date_of_birth_data);
                EditText emailEditText = findViewById(R.id.email_data);
                EditText passwordEditText = findViewById(R.id.password_data);
                String firstName = firstNameEditText.getText().toString();
                String familyName = familyNameEditText.getText().toString();
                String dob = dobEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    usernameEditText.setError("Username cannot be empty");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError("Password cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(firstName)) {
                    firstNameEditText.setError("First name cannot be empty");
                    return;
                    // It will check if last name is empty or not between 3 and 30 characters
                } else if (firstName.length() < 3 || firstName.length() > 30) {
                    firstNameEditText.setError("First name should be between 3 and 30 characters");
                    return;
                }

                if (TextUtils.isEmpty(familyName)) {
                    familyNameEditText.setError("Family name cannot be empty");
                    return;
                } else if (familyName.length() < 3 || familyName.length() > 30) {
                    familyNameEditText.setError("Family name should be between 3 and 30 characters");
                    return;
                }
                if (TextUtils.isEmpty(dob)) {
                    dobEditText.setError("Date of birth cannot be empty");
                    return;
                }
                // This will check if date of birth is in the format MM/DD/YYYY
                if (!isValidDateFormat(dob)) {
                    dobEditText.setError("Invalid date of birth format. Please use MM/DD/YYYY");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError("email cannot be empty");
                    return;
                }

                if (!isValidEmail(email)) {
                    emailEditText.setError("Invalid email address");
                    return;
                }

                // Save the registered credentials to SharedPreferences
                saveCredentials(username, password);

                Toast.makeText(RegisterActivity.this, "Account Has Been Created", Toast.LENGTH_SHORT).show();

                // Navigate back to the login screen
                finish();
            }
        });
    }

    private void saveCredentials(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Method to validate date of birth format
    private boolean isValidDateFormat(String dob) {
        // Regular expression pattern for MM/DD/YYYY format
        String regex = "(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01])/\\d{4}";
        return Pattern.matches(regex, dob);
    }
}
