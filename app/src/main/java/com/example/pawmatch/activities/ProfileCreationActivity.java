package com.example.pawmatch.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pawmatch.MainActivity;
import com.example.test_pawmatch.R;

import java.io.IOException;

public class ProfileCreationActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView petImageView;
    private EditText petNameEditText, petBioEditText;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);

        petImageView = findViewById(R.id.petImageView);
        Button uploadImageButton = findViewById(R.id.uploadImageButton);
        petNameEditText = findViewById(R.id.petNameEditText);
        petBioEditText = findViewById(R.id.petBioEditText);
        Button saveProfileButton = findViewById(R.id.saveProfileButton);

        preferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);

        // Upload Image
        uploadImageButton.setOnClickListener(v -> openGallery());

        // Save Profile
        saveProfileButton.setOnClickListener(v -> saveProfile());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                petImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveProfile() {
        String petName = petNameEditText.getText().toString().trim();
        String petBio = petBioEditText.getText().toString().trim();

        if (petName.isEmpty()) {
            petNameEditText.setError("Pet name is required!");
            return;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("petName", petName);
        editor.putString("petBio", petBio);
        editor.apply();

        Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
