package com.example.pawmatch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pawmatch.activities.SignInActivity;
import com.example.test_pawmatch.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private static final String ERROR_FILL_FIELDS = "Please fill in all fields";
    private static final String ERROR_PASSWORDS_MISMATCH = "Passwords do not match";
    private static final String ERROR_PASSWORD_TOO_SHORT = "Password must be at least 6 characters long";
    private static final String SUCCESS_ACCOUNT_CREATED = "Account created successfully!";

    private TextInputEditText emailEditText, passwordEditText, confirmPasswordEditText;
    private MaterialButton signUpButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        MaterialButton backToSignInButton = findViewById(R.id.backToSignInButton);
        progressBar = findViewById(R.id.progressBar);

        // Set up listeners
        signUpButton.setOnClickListener(v -> registerUser());
        backToSignInButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {
        String email = Objects.requireNonNull(emailEditText.getText()).toString().trim();
        String password = Objects.requireNonNull(passwordEditText.getText()).toString().trim();
        String confirmPassword = Objects.requireNonNull(confirmPasswordEditText.getText()).toString().trim();

        // Validate inputs
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, ERROR_FILL_FIELDS, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, ERROR_PASSWORDS_MISMATCH, Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, ERROR_PASSWORD_TOO_SHORT, Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress
        progressBar.setVisibility(View.VISIBLE);
        signUpButton.setEnabled(false);

        // Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    // Hide progress
                    progressBar.setVisibility(View.GONE);
                    signUpButton.setEnabled(true);

                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, SUCCESS_ACCOUNT_CREATED, Toast.LENGTH_SHORT).show();

                        // Navigate to the main activity
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class); // Replace MainActivity with your target activity
                        startActivity(intent);
                        finish();
                    } else {
                        // Handle errors
                        if (task.getException() != null) {
                            String errorMessage = task.getException().getMessage();
                            if (errorMessage != null && errorMessage.contains("email")) {
                                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
                            } else if (errorMessage.contains("already in use")) {
                                Toast.makeText(this, "This email is already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
