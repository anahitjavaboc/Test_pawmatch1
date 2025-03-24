package com.example.test_pawmatch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test_pawmatch.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private TextInputEditText emailEditText, passwordEditText, confirmPasswordEditText;
    private MaterialButton signUpButton, backToSignInButton;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        backToSignInButton = findViewById(R.id.backToSignInButton);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupClickListeners() {
        signUpButton.setOnClickListener(v -> signUp());
        backToSignInButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void signUp() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (!validateInputs(email, password, confirmPassword)) {
            return;
        }

        showProgress(true);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            sendVerificationEmail(user);
                        }
                    } else {
                        showProgress(false);
                        Toast.makeText(SignUpActivity.this,
                                "Registration failed: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendVerificationEmail(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    showProgress(false);
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this,
                                "Registration successful! Please check your email to verify your account.",
                                Toast.LENGTH_LONG).show();
                        // Navigate back to sign in screen
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUpActivity.this,
                                "Failed to send verification email: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validateInputs(String email, String password, String confirmPassword) {
        if (email.isEmpty()) {
            emailEditText.setError("Please enter your email address");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please enter a valid email address");
            return false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Please enter a password");
            return false;
        }
        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters long");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        signUpButton.setEnabled(!show);
        backToSignInButton.setEnabled(!show);
    }
}