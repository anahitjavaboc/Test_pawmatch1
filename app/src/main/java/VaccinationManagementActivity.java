package com.example.pawmatch.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawmatch.R;
import com.example.pawmatch.adapters.VaccinationAdapter;
import com.example.pawmatch.models.Pet;
import com.example.pawmatch.utils.FirebaseManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VaccinationManagementActivity extends AppCompatActivity {
    private RecyclerView vaccinationsRecyclerView;
    private MaterialButton addVaccinationButton;
    private MaterialCardView addVaccinationCard;
    private TextInputEditText vaccinationNameEditText, vaccinationDateEditText, nextDueDateEditText,
            veterinarianEditText, notesEditText;
    private MaterialButton saveVaccinationButton, cancelVaccinationButton;
    private ProgressBar progressBar;
    private VaccinationAdapter vaccinationAdapter;
    private List<Pet.Vaccination> vaccinations;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String petId;
    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_management);

        firebaseManager = FirebaseManager.getInstance();
        petId = getIntent().getStringExtra("petId");
        if (petId == null) {
            Toast.makeText(this, "Error: Pet ID not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        initializeViews();
        setupDatePickers();
        setupRecyclerView();
        setupClickListeners();
        loadVaccinations();
    }

    private void initializeViews() {
        vaccinationsRecyclerView = findViewById(R.id.vaccinationsRecyclerView);
        addVaccinationButton = findViewById(R.id.addVaccinationButton);
        addVaccinationCard = findViewById(R.id.addVaccinationCard);
        vaccinationNameEditText = findViewById(R.id.vaccinationNameEditText);
        vaccinationDateEditText = findViewById(R.id.vaccinationDateEditText);
        nextDueDateEditText = findViewById(R.id.nextDueDateEditText);
        veterinarianEditText = findViewById(R.id.veterinarianEditText);
        notesEditText = findViewById(R.id.notesEditText);
        saveVaccinationButton = findViewById(R.id.saveVaccinationButton);
        cancelVaccinationButton = findViewById(R.id.cancelVaccinationButton);
        progressBar = findViewById(R.id.progressBar);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        vaccinations = new ArrayList<>();
    }

    private void setupDatePickers() {
        vaccinationDateEditText.setOnClickListener(v -> showDatePicker(vaccinationDateEditText));
        nextDueDateEditText.setOnClickListener(v -> showDatePicker(nextDueDateEditText));
    }

    private void showDatePicker(TextInputEditText editText) {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            editText.setText(dateFormat.format(calendar.getTime()));
        };

        new DatePickerDialog(this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setupRecyclerView() {
        vaccinationAdapter = new VaccinationAdapter(vaccinations, this::editVaccination, this::deleteVaccination);
        vaccinationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vaccinationsRecyclerView.setAdapter(vaccinationAdapter);
    }

    private void setupClickListeners() {
        addVaccinationButton.setOnClickListener(v -> {
            addVaccinationCard.setVisibility(View.VISIBLE);
            clearForm();
        });

        saveVaccinationButton.setOnClickListener(v -> saveVaccination());

        cancelVaccinationButton.setOnClickListener(v -> {
            addVaccinationCard.setVisibility(View.GONE);
            clearForm();
        });
    }

    private void loadVaccinations() {
        progressBar.setVisibility(View.VISIBLE);
        firebaseManager.getPetProfile(petId, new FirebaseManager.FirebaseCallback() {
            @Override
            public void onSuccess(Object data) {
                Pet pet = (Pet) data;
                vaccinations.clear();
                vaccinations.addAll(pet.getVaccinations());
                vaccinationAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(VaccinationManagementActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveVaccination() {
        if (!validateInputs()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        Pet.Vaccination vaccination = new Pet.Vaccination(
                vaccinationNameEditText.getText().toString(),
                parseDate(vaccinationDateEditText.getText().toString()),
                parseDate(nextDueDateEditText.getText().toString()),
                veterinarianEditText.getText().toString(),
                notesEditText.getText().toString()
        );

        firebaseManager.addVaccination(petId, vaccination, new FirebaseManager.FirebaseCallback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(VaccinationManagementActivity.this, "Vaccination saved successfully", Toast.LENGTH_SHORT).show();
                addVaccinationCard.setVisibility(View.GONE);
                clearForm();
                loadVaccinations();
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(VaccinationManagementActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editVaccination(Pet.Vaccination vaccination) {
        vaccinationNameEditText.setText(vaccination.getName());
        vaccinationDateEditText.setText(dateFormat.format(vaccination.getDateAdministered()));
        nextDueDateEditText.setText(dateFormat.format(vaccination.getNextDueDate()));
        veterinarianEditText.setText(vaccination.getVeterinarian());
        notesEditText.setText(vaccination.getNotes());
        addVaccinationCard.setVisibility(View.VISIBLE);
    }

    private void deleteVaccination(Pet.Vaccination vaccination) {
        progressBar.setVisibility(View.VISIBLE);
        firebaseManager.deleteVaccination(petId, vaccination, new FirebaseManager.FirebaseCallback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(VaccinationManagementActivity.this, "Vaccination deleted successfully", Toast.LENGTH_SHORT).show();
                loadVaccinations();
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(VaccinationManagementActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInputs() {
        if (vaccinationNameEditText.getText().toString().trim().isEmpty()) {
            vaccinationNameEditText.setError("Please enter vaccination name");
            return false;
        }
        if (vaccinationDateEditText.getText().toString().trim().isEmpty()) {
            vaccinationDateEditText.setError("Please select date administered");
            return false;
        }
        if (nextDueDateEditText.getText().toString().trim().isEmpty()) {
            nextDueDateEditText.setError("Please select next due date");
            return false;
        }
        return true;
    }

    private void clearForm() {
        vaccinationNameEditText.setText("");
        vaccinationDateEditText.setText("");
        nextDueDateEditText.setText("");
        veterinarianEditText.setText("");
        notesEditText.setText("");
    }

    private Date parseDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            return new Date();
        }
    }
}