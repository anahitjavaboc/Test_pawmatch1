package com.example.Test_pawmatch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class PreferencesActivity extends AppCompatActivity {
    private Spinner petTypeSpinner, sizeSpinner, sociabilitySpinner, trainingSpinner;
    private EditText breedEditText, locationEditText;
    private SeekBar ageSeekBar, energySeekBar;
    private TextView ageTextView, energyTextView;
    private RadioGroup genderGroup;
    private CheckBox specialNeedsCheckbox;
    private Switch readyToConceiveSwitch;
    private Button savePreferencesButton;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        petTypeSpinner = findViewById(R.id.petTypeSpinner);
        breedEditText = findViewById(R.id.breedEditText);
        ageSeekBar = findViewById(R.id.ageSeekBar);
        ageTextView = findViewById(R.id.ageTextView);
        sizeSpinner = findViewById(R.id.sizeSpinner);
        genderGroup = findViewById(R.id.genderGroup);
        locationEditText = findViewById(R.id.locationEditText);
        energySeekBar = findViewById(R.id.energySeekBar);
        energyTextView = findViewById(R.id.energyTextView);
        sociabilitySpinner = findViewById(R.id.sociabilitySpinner);
        trainingSpinner = findViewById(R.id.trainingSpinner);
        specialNeedsCheckbox = findViewById(R.id.specialNeedsCheckbox);
        readyToConceiveSwitch = findViewById(R.id.readyToConceiveSwitch);
        savePreferencesButton = findViewById(R.id.savePreferencesButton);

        preferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);

        // SeekBar Listeners
        ageSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ageTextView.setText("Age: " + progress + " years");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        energySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                energyTextView.setText("Energy Level: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        savePreferencesButton.setOnClickListener(v -> savePreferences());
    }

    private void savePreferences() {
        String petType = petTypeSpinner.getSelectedItem().toString();
        String breed = breedEditText.getText().toString();
        int age = ageSeekBar.getProgress();
        String size = sizeSpinner.getSelectedItem().toString();
        String gender = ((RadioButton) findViewById(genderGroup.getCheckedRadioButtonId())).getText().toString();
        String location = locationEditText.getText().toString();
        int energyLevel = energySeekBar.getProgress();
        String sociability = sociabilitySpinner.getSelectedItem().toString();
        String trainingLevel = trainingSpinner.getSelectedItem().toString();
        boolean specialNeeds = specialNeedsCheckbox.isChecked();
        boolean readyToConceive = readyToConceiveSwitch.isChecked();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("petType", petType);
        editor.putString("breed", breed);
        editor.putInt("age", age);
        editor.putString("size", size);
        editor.putString("gender", gender);
        editor.putString("location", location);
        editor.putInt("energyLevel", energyLevel);
        editor.putString("sociability", sociability);
        editor.putString("trainingLevel", trainingLevel);
        editor.putBoolean("specialNeeds", specialNeeds);
        editor.putBoolean("readyToConceive", readyToConceive);
        editor.apply();

        Toast.makeText(this, "Preferences saved!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ProfileCreationActivity.class));
        finish();
    }
}
