package com.example.pawmatch.activities; // Make sure this matches your actual package name

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pawmatch.activities.SignInActivity;
import com.example.pawmatch.activities.SignUpActivity;
import com.example.test_pawmatch.R;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Make sure activity_main.xml exists

        // Initialize buttons
        MaterialButton signInButton = findViewById(R.id.signInButton);
        MaterialButton signUpButton = findViewById(R.id.signUpButton);

        // Sign In button click
        signInButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
            finish(); // Close MainActivity
        });

        // Sign Up button click
        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish(); // Close MainActivity
        });

        // Optional: Delay navigation to MainActivity (you probably don't need this if you're already in MainActivity)

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class); // Replace with another activity if needed
            startActivity(intent);
            finish();
        }, 3000); // 3-second delay

    }
}
