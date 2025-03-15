package com.example.pawmatch.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pawmatch.R;
import com.example.pawmatch.models.Pet;
import com.example.pawmatch.utils.FirebaseManager;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PetProfileCreationActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private ImageView profileImageView;
    private TextInputEditText nameEditText, ageEditText, locationEditText, descriptionEditText;
    private Spinner petTypeSpinner, breedSpinner, ageRangeSpinner, sizeSpinner, genderSpinner;
    private RatingBar energyLevelRatingBar, sociabilityRatingBar, trainingLevelRatingBar;
    private ChipGroup specialNeedsChipGroup;
    private Button uploadImageButton, saveButton;
    private ProgressBar progressBar;
    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile_creation);

        firebaseManager = FirebaseManager.getInstance();
        initializeViews();
        setupSpinners();
        setupSpecialNeedsChips();
        setupClickListeners();
    }

    private void initializeViews() {
        profileImageView = findViewById(R.id.profileImageView);
        nameEditText = findViewById(R.id.nameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        locationEditText = findViewById(R.id.locationEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        petTypeSpinner = findViewById(R.id.petTypeSpinner);
        breedSpinner = findViewById(R.id.breedSpinner);
        ageRangeSpinner = findViewById(R.id.ageRangeSpinner);
        sizeSpinner = findViewById(R.id.sizeSpinner);
        genderSpinner = findViewById(R.id.genderSpinner);
        energyLevelRatingBar = findViewById(R.id.energyLevelRatingBar);
        sociabilityRatingBar = findViewById(R.id.sociabilityRatingBar);
        trainingLevelRatingBar = findViewById(R.id.trainingLevelRatingBar);
        specialNeedsChipGroup = findViewById(R.id.specialNeedsChipGroup);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        saveButton = findViewById(R.id.saveButton);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupSpinners() {
        // Pet Type Spinner
        List<String> petTypes = Arrays.asList("Dog", "Cat", "Bird", "Fish", "Other");
        ArrayAdapter<String> petTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, petTypes);
        petTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petTypeSpinner.setAdapter(petTypeAdapter);

        // Age Range Spinner
        List<String> ageRanges = Arrays.asList("Puppy/Kitten", "Young", "Adult", "Senior");
        ArrayAdapter<String> ageRangeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ageRanges);
        ageRangeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageRangeSpinner.setAdapter(ageRangeAdapter);

        // Size Spinner
        List<String> sizes = Arrays.asList("Small", "Medium", "Large", "Extra Large");
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sizes);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);

        // Gender Spinner
        List<String> genders = Arrays.asList("Male", "Female", "Neutered", "Spayed");
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
    }

    private void setupSpecialNeedsChips() {
        List<String> specialNeeds = Arrays.asList(
                "Requires Medication",
                "Special Diet",
                "Physical Limitations",
                "Behavioral Training",
                "Regular Vet Visits",
                "Allergies"
        );

        for (String need : specialNeeds) {
            Chip chip = new Chip(this);
            chip.setText(need);
            chip.setCheckable(true);
            specialNeedsChipGroup.addView(chip);
        }
    }

    private void setupClickListeners() {
        uploadImageButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        saveButton.setOnClickListener(v -> savePetProfile());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            profileImageView.setImageURI(selectedImageUri);
        }
    }

    private void savePetProfile() {
        if (!validateInputs()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        saveButton.setEnabled(false);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Pet pet = createPetObject(userId);

        if (selectedImageUri != null) {
            // Upload image first
            String imagePath = "pet_images/" + userId + "/" + System.currentTimeMillis() + ".jpg";
            StorageReference imageRef = FirebaseStorage.getInstance().getReference().child(imagePath);

            imageRef.putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            pet.setImageUrl(uri.toString());
                            savePetToFirebase(pet);
                        });
                    })
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.GONE);
                        saveButton.setEnabled(true);
                        Toast.makeText(this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            savePetToFirebase(pet);
        }
    }

    private Pet createPetObject(String userId) {
        List<String> selectedSpecialNeeds = new ArrayList<>();
        for (int i = 0; i < specialNeedsChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) specialNeedsChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                selectedSpecialNeeds.add(chip.getText().toString());
            }
        }

        return new Pet(
                userId,
                nameEditText.getText().toString(),
                petTypeSpinner.getSelectedItem().toString(),
                breedSpinner.getSelectedItem().toString(),
                Double.parseDouble(ageEditText.getText().toString()),
                ageRangeSpinner.getSelectedItem().toString(),
                sizeSpinner.getSelectedItem().toString(),
                genderSpinner.getSelectedItem().toString(),
                locationEditText.getText().toString(),
                (int) energyLevelRatingBar.getRating(),
                (int) sociabilityRatingBar.getRating(),
                (int) trainingLevelRatingBar.getRating(),
                selectedSpecialNeeds,
                descriptionEditText.getText().toString()
        );
    }

    private void savePetToFirebase(Pet pet) {
        firebaseManager.createPetProfile(pet, new FirebaseManager.FirebaseCallback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PetProfileCreationActivity.this, "Pet profile created successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(View.GONE);
                saveButton.setEnabled(true);
                Toast.makeText(PetProfileCreationActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInputs() {
        if (nameEditText.getText().toString().trim().isEmpty()) {
            nameEditText.setError("Please enter pet's name");
            return false;
        }
        if (ageEditText.getText().toString().trim().isEmpty()) {
            ageEditText.setError("Please enter pet's age");
            return false;
        }
        if (locationEditText.getText().toString().trim().isEmpty()) {
            locationEditText.setError("Please enter location");
            return false;
        }
        if (descriptionEditText.getText().toString().trim().isEmpty()) {
            descriptionEditText.setError("Please enter a description");
            return false;
        }
        return true;
    }
}