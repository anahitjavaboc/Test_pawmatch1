package com.example.pawmatch.models;

import com.google.firebase.firestore.Exclude;
import java.util.List;
import java.util.Date;

public class Pet {
    private String id;
    private String ownerId;
    private String name;
    private String petType; // Dog, Cat, Rabbit, Bird, etc.
    private String breed;
    private String ageRange; // Puppy/Kitten, Young, Adult, Senior
    private int age;
    private String size; // Small, Medium, Large
    private String gender; // Male, Female
    private int energyLevel; // 1-5
    private int sociability; // 1-5
    private int trainingLevel; // 1-5
    private List<String> specialNeeds;
    private String location;
    private String imageUrl;
    private String description;
    private List<String> interests;
    private boolean isActive;

    // Health Profile
    private List<Vaccination> vaccinations;
    private List<MedicalCondition> medicalConditions;
    private List<Medication> medications;
    private List<VetAppointment> vetAppointments;

    // Required empty constructor for Firestore
    public Pet() {}

    public Pet(String ownerId, String name, String petType, String breed, String ageRange, int age,
               String size, String gender, int energyLevel, int sociability, int trainingLevel,
               List<String> specialNeeds, String location, String imageUrl, String description,
               List<String> interests) {
        this.ownerId = ownerId;
        this.name = name;
        this.petType = petType;
        this.breed = breed;
        this.ageRange = ageRange;
        this.age = age;
        this.size = size;
        this.gender = gender;
        this.energyLevel = energyLevel;
        this.sociability = sociability;
        this.trainingLevel = trainingLevel;
        this.specialNeeds = specialNeeds;
        this.location = location;
        this.imageUrl = imageUrl;
        this.description = description;
        this.interests = interests;
        this.isActive = true;
        this.vaccinations = new ArrayList<>();
        this.medicalConditions = new ArrayList<>();
        this.medications = new ArrayList<>();
        this.vetAppointments = new ArrayList<>();
    }

    // Existing getters and setters...

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Vaccination> getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(List<Vaccination> vaccinations) {
        this.vaccinations = vaccinations;
    }

    public List<MedicalCondition> getMedicalConditions() {
        return medicalConditions;
    }

    public void setMedicalConditions(List<MedicalCondition> medicalConditions) {
        this.medicalConditions = medicalConditions;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public List<VetAppointment> getVetAppointments() {
        return vetAppointments;
    }

    public void setVetAppointments(List<VetAppointment> vetAppointments) {
        this.vetAppointments = vetAppointments;
    }

    // Inner classes for health profile
    public static class Vaccination {
        private String name;
        private Date dateGiven;
        private Date nextDueDate;
        private String notes;

        public Vaccination() {}

        public Vaccination(String name, Date dateGiven, Date nextDueDate, String notes) {
            this.name = name;
            this.dateGiven = dateGiven;
            this.nextDueDate = nextDueDate;
            this.notes = notes;
        }

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Date getDateGiven() { return dateGiven; }
        public void setDateGiven(Date dateGiven) { this.dateGiven = dateGiven; }
        public Date getNextDueDate() { return nextDueDate; }
        public void setNextDueDate(Date nextDueDate) { this.nextDueDate = nextDueDate; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class MedicalCondition {
        private String name;
        private String description;
        private Date diagnosedDate;
        private boolean isActive;
        private String treatment;

        public MedicalCondition() {}

        public MedicalCondition(String name, String description, Date diagnosedDate, boolean isActive, String treatment) {
            this.name = name;
            this.description = description;
            this.diagnosedDate = diagnosedDate;
            this.isActive = isActive;
            this.treatment = treatment;
        }

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public Date getDiagnosedDate() { return diagnosedDate; }
        public void setDiagnosedDate(Date diagnosedDate) { this.diagnosedDate = diagnosedDate; }
        public boolean isActive() { return isActive; }
        public void setActive(boolean active) { isActive = active; }
        public String getTreatment() { return treatment; }
        public void setTreatment(String treatment) { this.treatment = treatment; }
    }

    public static class Medication {
        private String name;
        private String dosage;
        private String frequency;
        private Date startDate;
        private Date endDate;
        private String notes;

        public Medication() {}

        public Medication(String name, String dosage, String frequency, Date startDate, Date endDate, String notes) {
            this.name = name;
            this.dosage = dosage;
            this.frequency = frequency;
            this.startDate = startDate;
            this.endDate = endDate;
            this.notes = notes;
        }

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDosage() { return dosage; }
        public void setDosage(String dosage) { this.dosage = dosage; }
        public String getFrequency() { return frequency; }
        public void setFrequency(String frequency) { this.frequency = frequency; }
        public Date getStartDate() { return startDate; }
        public void setStartDate(Date startDate) { this.startDate = startDate; }
        public Date getEndDate() { return endDate; }
        public void setEndDate(Date endDate) { this.endDate = endDate; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class VetAppointment {
        private Date appointmentDate;
        private String vetName;
        private String clinicName;
        private String purpose;
        private String notes;
        private boolean isCompleted;

        public VetAppointment() {}

        public VetAppointment(Date appointmentDate, String vetName, String clinicName, String purpose, String notes) {
            this.appointmentDate = appointmentDate;
            this.vetName = vetName;
            this.clinicName = clinicName;
            this.purpose = purpose;
            this.notes = notes;
            this.isCompleted = false;
        }

        // Getters and setters
        public Date getAppointmentDate() { return appointmentDate; }
        public void setAppointmentDate(Date appointmentDate) { this.appointmentDate = appointmentDate; }
        public String getVetName() { return vetName; }
        public void setVetName(String vetName) { this.vetName = vetName; }
        public String getClinicName() { return clinicName; }
        public void setClinicName(String clinicName) { this.clinicName = clinicName; }
        public String getPurpose() { return purpose; }
        public void setPurpose(String purpose) { this.purpose = purpose; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
        public boolean isCompleted() { return isCompleted; }
        public void setCompleted(boolean completed) { isCompleted = completed; }
    }
}