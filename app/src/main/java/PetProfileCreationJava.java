package com.example.test_pawmatch.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test_pawmatch.R;
import com.example.test_pawmatch.models.Pet;
import com.example.test_pawmatch.utils.FirebaseManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.StorageReference;

public class PetProfileCreationActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private ImageView petImageView;
    private TextInputLayout nameLayout, typeLayout, breedLayout, ageLayout, sizeLayout, genderLayout;
    private TextInputEditText nameInput, typeInput, breedInput, ageInput, sizeInput, genderInput;
    private MaterialButton uploadImageButton, saveButton, cancelButton;
    private View progressBar;
    private FirebaseManager firebaseManager;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        petImageView.setImageURI(selectedImageUri);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile_creation);

        firebaseManager = FirebaseManager.getInstance();
        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        petImageView = findViewById(R.id.petImageView);
        nameLayout = findViewById(R.id.nameLayout);
        typeLayout = findViewById(R.id.typeLayout);
        breedLayout = findViewById(R.id.breedLayout);
        ageLayout = findViewById(R.id.ageLayout);
        sizeLayout = findViewById(R.id.sizeLayout);
        genderLayout = findViewById(R.id.genderLayout);

        nameInput = findViewById(R.id.nameInput);
        typeInput = findViewById(R.id.typeInput);
        breedInput = findViewById(R.id.breedInput);
        ageInput = findViewById(R.id.ageInput);
        sizeInput = findViewById(R.id.sizeInput);
        genderInput = findViewById(R.id.genderInput);

        uploadImageButton = findViewById(R.id.uploadImageButton);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupClickListeners() {
        uploadImageButton.setOnClickListener(v -> openImagePicker());
        saveButton.setOnClickListener(v -> savePetProfile());
        cancelButton.setOnClickListener(v -> finish());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void savePetProfile() {
        if (!validateInputs()) {
            return;
        }

        showProgress(true);

        Pet pet = new Pet();
        pet.setName(nameInput.getText().toString().trim());
        pet.setType(typeInput.getText().toString().trim());
        pet.setBreed(breedInput.getText().toString().trim());
        pet.setAgeRange(ageInput.getText().toString().trim());
        pet.setSize(sizeInput.getText().toString().trim());
        pet.setGender(genderInput.getText().toString().trim());

        if (selectedImageUri != null) {
            // Upload image first
            firebaseManager.uploadPetImage(selectedImageUri, pet.getId(), new FirebaseManager.FirebaseCallback() {
                @Override
                public void onSuccess() {
                    // After image upload, save pet profile
                    savePetToFirebase(pet);
                }

                @Override
                public void onError(String error) {
                    showProgress(false);
                    Toast.makeText(PetProfileCreationActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Save pet profile without image
            savePetToFirebase(pet);
        }
    }

    private void savePetToFirebase(Pet pet) {
        firebaseManager.addPet(pet, new FirebaseManager.FirebaseCallback() {
            @Override
            public void onSuccess() {
                showProgress(false);
                Toast.makeText(PetProfileCreationActivity.this,
                        R.string.pet_profile_created, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String error) {
                showProgress(false);
                Toast.makeText(PetProfileCreationActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (nameInput.getText().toString().trim().isEmpty()) {
            nameLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            nameLayout.setError(null);
        }

        if (typeInput.getText().toString().trim().isEmpty()) {
            typeLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            typeLayout.setError(null);
        }

        if (breedInput.getText().toString().trim().isEmpty()) {
            breedLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            breedLayout.setError(null);
        }

        if (ageInput.getText().toString().trim().isEmpty()) {
            ageLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            ageLayout.setError(null);
        }

        if (sizeInput.getText().toString().trim().isEmpty()) {
            sizeLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            sizeLayout.setError(null);
        }

        if (genderInput.getText().toString().trim().isEmpty()) {
            genderLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            genderLayout.setError(null);
        }

        return isValid;
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        uploadImageButton.setEnabled(!show);
        saveButton.setEnabled(!show);
        cancelButton.setEnabled(!show);
    }
}