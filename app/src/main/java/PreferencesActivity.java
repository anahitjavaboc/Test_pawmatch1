package com.example.test_pawmatch.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test_pawmatch.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class PreferencesActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "PawMatchPrefs";
    private static final String KEY_PET_TYPE = "pet_type";
    private static final String KEY_PET_SIZE = "pet_size";
    private static final String KEY_PET_AGE = "pet_age";
    private static final String KEY_PET_GENDER = "pet_gender";
    private static final String KEY_MAX_DISTANCE = "max_distance";
    private static final String KEY_NOTIFICATIONS = "notifications_enabled";
    private static final String KEY_EMAIL_UPDATES = "email_updates_enabled";

    private TextInputLayout petTypeLayout, petSizeLayout, petAgeLayout, petGenderLayout, maxDistanceLayout;
    private TextInputEditText petTypeInput, petSizeInput, petAgeInput, petGenderInput, maxDistanceInput;
    private SwitchMaterial notificationsSwitch, emailUpdatesSwitch;
    private MaterialButton saveButton, cancelButton;
    private View progressBar;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        initializeViews();
        setupClickListeners();
        loadPreferences();
    }

    private void initializeViews() {
        petTypeLayout = findViewById(R.id.petTypeLayout);
        petSizeLayout = findViewById(R.id.petSizeLayout);
        petAgeLayout = findViewById(R.id.petAgeLayout);
        petGenderLayout = findViewById(R.id.petGenderLayout);
        maxDistanceLayout = findViewById(R.id.maxDistanceLayout);

        petTypeInput = findViewById(R.id.petTypeInput);
        petSizeInput = findViewById(R.id.petSizeInput);
        petAgeInput = findViewById(R.id.petAgeInput);
        petGenderInput = findViewById(R.id.petGenderInput);
        maxDistanceInput = findViewById(R.id.maxDistanceInput);

        notificationsSwitch = findViewById(R.id.notificationsSwitch);
        emailUpdatesSwitch = findViewById(R.id.emailUpdatesSwitch);

        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupClickListeners() {
        saveButton.setOnClickListener(v -> savePreferences());
        cancelButton.setOnClickListener(v -> finish());
    }

    private void loadPreferences() {
        petTypeInput.setText(prefs.getString(KEY_PET_TYPE, ""));
        petSizeInput.setText(prefs.getString(KEY_PET_SIZE, ""));
        petAgeInput.setText(prefs.getString(KEY_PET_AGE, ""));
        petGenderInput.setText(prefs.getString(KEY_PET_GENDER, ""));
        maxDistanceInput.setText(prefs.getString(KEY_MAX_DISTANCE, "50"));
        notificationsSwitch.setChecked(prefs.getBoolean(KEY_NOTIFICATIONS, true));
        emailUpdatesSwitch.setChecked(prefs.getBoolean(KEY_EMAIL_UPDATES, true));
    }

    private void savePreferences() {
        if (!validateInputs()) {
            return;
        }

        showProgress(true);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_PET_TYPE, petTypeInput.getText().toString().trim());
        editor.putString(KEY_PET_SIZE, petSizeInput.getText().toString().trim());
        editor.putString(KEY_PET_AGE, petAgeInput.getText().toString().trim());
        editor.putString(KEY_PET_GENDER, petGenderInput.getText().toString().trim());
        editor.putString(KEY_MAX_DISTANCE, maxDistanceInput.getText().toString().trim());
        editor.putBoolean(KEY_NOTIFICATIONS, notificationsSwitch.isChecked());
        editor.putBoolean(KEY_EMAIL_UPDATES, emailUpdatesSwitch.isChecked());

        editor.apply();

        showProgress(false);
        Toast.makeText(this, R.string.preferences_saved, Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (petTypeInput.getText().toString().trim().isEmpty()) {
            petTypeLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            petTypeLayout.setError(null);
        }

        if (petSizeInput.getText().toString().trim().isEmpty()) {
            petSizeLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            petSizeLayout.setError(null);
        }

        if (petAgeInput.getText().toString().trim().isEmpty()) {
            petAgeLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            petAgeLayout.setError(null);
        }

        if (petGenderInput.getText().toString().trim().isEmpty()) {
            petGenderLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            petGenderLayout.setError(null);
        }

        String maxDistance = maxDistanceInput.getText().toString().trim();
        if (maxDistance.isEmpty()) {
            maxDistanceLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            try {
                int distance = Integer.parseInt(maxDistance);
                if (distance <= 0 || distance > 100) {
                    maxDistanceLayout.setError(getString(R.string.error_invalid_distance));
                    isValid = false;
                } else {
                    maxDistanceLayout.setError(null);
                }
            } catch (NumberFormatException e) {
                maxDistanceLayout.setError(getString(R.string.error_invalid_number));
                isValid = false;
            }
        }

        return isValid;
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        saveButton.setEnabled(!show);
        cancelButton.setEnabled(!show);
    }
}