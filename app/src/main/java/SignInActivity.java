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

public class SignInActivity extends AppCompatActivity {
    private TextInputEditText emailEditText, passwordEditText;
    private MaterialButton signInButton, signUpButton, resetPasswordButton;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();
        initializeViews();
        setupClickListeners();

        // Check if user is already signed in and email is verified
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null && currentUser.isEmailVerified()) {
            navigateToMainActivity();
        }
    }

    private void initializeViews() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupClickListeners() {
        signInButton.setOnClickListener(v -> signIn());
        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
        resetPasswordButton.setOnClickListener(v -> resetPassword());
    }

    private void signIn() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (!validateInputs(email, password)) {
            return;
        }

        showProgress(true);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    showProgress(false);
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            navigateToMainActivity();
                        } else {
                            Toast.makeText(SignInActivity.this,
                                    "Please verify your email address",
                                    Toast.LENGTH_LONG).show();
                            // Send verification email
                            sendVerificationEmail();
                        }
                    } else {
                        Toast.makeText(SignInActivity.this,
                                "Authentication failed: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void resetPassword() {
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty()) {
            emailEditText.setError("Please enter your email address");
            return;
        }

        showProgress(true);
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    showProgress(false);
                    if (task.isSuccessful()) {
                        Toast.makeText(SignInActivity.this,
                                "Password reset email sent. Please check your inbox.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SignInActivity.this,
                                "Failed to send reset email: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendVerificationEmail() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            showProgress(true);
            user.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        showProgress(false);
                        if (task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this,
                                    "Verification email sent. Please check your inbox.",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignInActivity.this,
                                    "Failed to send verification email: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty()) {
            emailEditText.setError("Please enter your email address");
            return false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Please enter your password");
            return false;
        }
        return true;
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        signInButton.setEnabled(!show);
        signUpButton.setEnabled(!show);
        resetPasswordButton.setEnabled(!show);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}