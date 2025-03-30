package com.example.pawmatch.activities;

import java.util.ArrayList;
import java.util.List;

public class Pet {
    private String id;
    private String name;
    private String type; // dog, cat, etc.
    private String breed;
    private String ageRange;
    private String size;
    private String gender;
    private int energyLevel;
    private int sociability;
    private int trainingLevel;
    private boolean hasSpecialNeeds;
    private String specialNeedsDescription;
    private String imageUrl;
    private List<Vaccination> vaccinations;
    private List<MedicalCondition> medicalConditions;
    private List<Medication> medications;
    private List<VetAppointment> vetAppointments;

    public Pet() {
        vaccinations = new ArrayList<>();
        medicalConditions = new ArrayList<>();
        medications = new ArrayList<>();
        vetAppointments = new ArrayList<>();
    }

    public Pet(String whiskers, String cat, String siamese, String s, String small, String female, int i, int i1, int i2, boolean b, String s1) {
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public String getAgeRange() { return ageRange; }
    public void setAgeRange(String ageRange) { this.ageRange = ageRange; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getEnergyLevel() { return energyLevel; }
    public void setEnergyLevel(int energyLevel) { this.energyLevel = energyLevel; }

    public int getSociability() { return sociability; }
    public void setSociability(int sociability) { this.sociability = sociability; }

    public int getTrainingLevel() { return trainingLevel; }
    public void setTrainingLevel(int trainingLevel) { this.trainingLevel = trainingLevel; }

    public boolean isHasSpecialNeeds() { return hasSpecialNeeds; }
    public void setHasSpecialNeeds(boolean hasSpecialNeeds) { this.hasSpecialNeeds = hasSpecialNeeds; }

    public String getSpecialNeedsDescription() { return specialNeedsDescription; }
    public void setSpecialNeedsDescription(String specialNeedsDescription) { this.specialNeedsDescription = specialNeedsDescription; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<Vaccination> getVaccinations() { return vaccinations; }
    public void setVaccinations(List<Vaccination> vaccinations) { this.vaccinations = vaccinations; }

    public List<MedicalCondition> getMedicalConditions() { return medicalConditions; }
    public void setMedicalConditions(List<MedicalCondition> medicalConditions) { this.medicalConditions = medicalConditions; }

    public List<Medication> getMedications() { return medications; }
    public void setMedications(List<Medication> medications) { this.medications = medications; }

    public List<VetAppointment> getVetAppointments() { return vetAppointments; }
    public void setVetAppointments(List<VetAppointment> vetAppointments) { this.vetAppointments = vetAppointments; }

    // Inner classes for different types of records
    public static class Vaccination {
        private String name;
        private String date;
        private String nextDueDate;
        private String notes;

        public Vaccination() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }

        public String getNextDueDate() { return nextDueDate; }
        public void setNextDueDate(String nextDueDate) { this.nextDueDate = nextDueDate; }

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vaccination that = (Vaccination) o;
            return name.equals(that.name) && date.equals(that.date);
        }

        @Override
        public int hashCode() {
            return name.hashCode() + date.hashCode();
        }
    }

    public static class MedicalCondition {
        private String name;
        private String diagnosisDate;
        private String status;
        private String notes;

        public MedicalCondition() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDiagnosisDate() { return diagnosisDate; }
        public void setDiagnosisDate(String diagnosisDate) { this.diagnosisDate = diagnosisDate; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MedicalCondition that = (MedicalCondition) o;
            return name.equals(that.name) && diagnosisDate.equals(that.diagnosisDate);
        }

        @Override
        public int hashCode() {
            return name.hashCode() + diagnosisDate.hashCode();
        }
    }

    public static class Medication {
        private String name;
        private String dosage;
        private String frequency;
        private String startDate;
        private String endDate;
        private String notes;

        public Medication() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDosage() { return dosage; }
        public void setDosage(String dosage) { this.dosage = dosage; }

        public String getFrequency() { return frequency; }
        public void setFrequency(String frequency) { this.frequency = frequency; }

        public String getStartDate() { return startDate; }
        public void setStartDate(String startDate) { this.startDate = startDate; }

        public String getEndDate() { return endDate; }
        public void setEndDate(String endDate) { this.endDate = endDate; }

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Medication that = (Medication) o;
            return name.equals(that.name) && startDate.equals(that.startDate);
        }

        @Override
        public int hashCode() {
            return name.hashCode() + startDate.hashCode();
        }
    }

    public static class VetAppointment {
        private String date;
        private String time;
        private String vetName;
        private String clinicName;
        private String purpose;
        private String notes;

        public VetAppointment() {}

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }

        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }

        public String getVetName() { return vetName; }
        public void setVetName(String vetName) { this.vetName = vetName; }

        public String getClinicName() { return clinicName; }
        public void setClinicName(String clinicName) { this.clinicName = clinicName; }

        public String getPurpose() { return purpose; }
        public void setPurpose(String purpose) { this.purpose = purpose; }

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VetAppointment that = (VetAppointment) o;
            return date.equals(that.date) && time.equals(that.time);
        }

        @Override
        public int hashCode() {
            return date.hashCode() + time.hashCode();
        }
    }
}