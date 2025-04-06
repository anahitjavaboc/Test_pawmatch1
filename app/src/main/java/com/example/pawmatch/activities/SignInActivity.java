package com.example.pawmatch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test_pawmatch.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    private TextInputLayout emailLayout, passwordLayout;
    private TextInputEditText emailInput, passwordInput;
    private MaterialButton signInButton, signUpButton, resetPasswordButton;
    private View progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        initializeViews();
        setupClickListeners();

        // Check if user is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && currentUser.isEmailVerified()) {
            navigateToMainActivity();
            return;
        }

        // Restore saved state if available
        if (savedInstanceState != null) {
            emailInput.setText(savedInstanceState.getString(KEY_EMAIL, ""));
            passwordInput.setText(savedInstanceState.getString(KEY_PASSWORD, ""));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_EMAIL, Objects.requireNonNull(emailInput.getText()).toString());
        outState.putString(KEY_PASSWORD, Objects.requireNonNull(passwordInput.getText()).toString());
    }

    private void initializeViews() {
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
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
        if (!validateInputs()) {
            return;
        }

        String email = Objects.requireNonNull(emailInput.getText()).toString().trim();
        String password = Objects.requireNonNull(passwordInput.getText()).toString().trim();

        showProgress(true);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    showProgress(false);
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            navigateToMainActivity();
                        } else {
                            sendVerificationEmail();
                        }
                    } else {
                        handleSignInError(task.getException());
                    }
                });
    }

    private void handleSignInError(Exception exception) {
        String errorMessage = exception != null ? exception.getMessage() : "Authentication failed";

        if (errorMessage.contains("no user record")) {
            emailLayout.setError(getString(R.string.error_email_not_found));
        } else if (errorMessage.contains("password is invalid")) {
            passwordLayout.setError(getString(R.string.error_invalid_password));
        } else if (errorMessage.contains("badly formatted")) {
            emailLayout.setError(getString(R.string.error_invalid_email));
        } else {
            Toast.makeText(SignInActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private void resetPassword() {
        String email = Objects.requireNonNull(emailInput.getText()).toString().trim();
        if (email.isEmpty()) {
            emailLayout.setError(getString(R.string.error_field_required));
            return;
        }

        showProgress(true);

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    showProgress(false);
                    if (task.isSuccessful()) {
                        Toast.makeText(SignInActivity.this,
                                R.string.password_reset_email_sent, Toast.LENGTH_SHORT).show();
                    } else {
                        handlePasswordResetError(task.getException());
                    }
                });
    }

    private void handlePasswordResetError(Exception exception) {
        String errorMessage = exception != null ? exception.getMessage() : "Password reset failed";
        assert errorMessage != null;
        if (errorMessage.contains("badly formatted")) {
            emailLayout.setError(getString(R.string.error_invalid_email));
        } else {
            Toast.makeText(SignInActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private void sendVerificationEmail() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this,
                                    R.string.verification_email_sent, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignInActivity.this,
                                    R.string.error_sending_verification_email, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private boolean validateInputs() {
        boolean isValid = true;
        String email = Objects.requireNonNull(emailInput.getText()).toString().trim();
        String password = Objects.requireNonNull(passwordInput.getText()).toString().trim();

        if (email.isEmpty()) {
            emailLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError(getString(R.string.error_invalid_email));
            isValid = false;
        } else {
            emailLayout.setError(null);
        }

        if (password.isEmpty()) {
            passwordLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            passwordLayout.setError(null);
        }

        return isValid;
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        signInButton.setEnabled(!show);
        signUpButton.setEnabled(!show);
        resetPasswordButton.setEnabled(!show);
    }
}