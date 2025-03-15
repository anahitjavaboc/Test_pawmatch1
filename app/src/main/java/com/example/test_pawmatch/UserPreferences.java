package com.example.pawmatch.models;

import com.google.firebase.firestore.Exclude;
import java.util.List;

public class UserPreferences {
    private String id;
    private String userId;
    private List<String> preferredBreeds;
    private int minAge;
    private int maxAge;
    private List<String> preferredSizes;
    private int minEnergyLevel;
    private int maxEnergyLevel;
    private int minSociability;
    private int maxSociability;
    private int minTrainingLevel;
    private int maxTrainingLevel;
    private List<String> preferredSpecialNeeds;
    private String preferredLocation;
    private int maxDistance; // in kilometers

    // Required empty constructor for Firestore
    public UserPreferences() {}

    public UserPreferences(String userId, List<String> preferredBreeds,
                           int minAge, int maxAge, List<String> preferredSizes,
                           int minEnergyLevel, int maxEnergyLevel,
                           int minSociability, int maxSociability,
                           int minTrainingLevel, int maxTrainingLevel,
                           List<String> preferredSpecialNeeds,
                           String preferredLocation, int maxDistance) {
        this.userId = userId;
        this.preferredBreeds = preferredBreeds;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.preferredSizes = preferredSizes;
        this.minEnergyLevel = minEnergyLevel;
        this.maxEnergyLevel = maxEnergyLevel;
        this.minSociability = minSociability;
        this.maxSociability = maxSociability;
        this.minTrainingLevel = minTrainingLevel;
        this.maxTrainingLevel = maxTrainingLevel;
        this.preferredSpecialNeeds = preferredSpecialNeeds;
        this.preferredLocation = preferredLocation;
        this.maxDistance = maxDistance;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getPreferredBreeds() {
        return preferredBreeds;
    }

    public void setPreferredBreeds(List<String> preferredBreeds) {
        this.preferredBreeds = preferredBreeds;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public List<String> getPreferredSizes() {
        return preferredSizes;
    }

    public void setPreferredSizes(List<String> preferredSizes) {
        this.preferredSizes = preferredSizes;
    }

    public int getMinEnergyLevel() {
        return minEnergyLevel;
    }

    public void setMinEnergyLevel(int minEnergyLevel) {
        this.minEnergyLevel = minEnergyLevel;
    }

    public int getMaxEnergyLevel() {
        return maxEnergyLevel;
    }

    public void setMaxEnergyLevel(int maxEnergyLevel) {
        this.maxEnergyLevel = maxEnergyLevel;
    }

    public int getMinSociability() {
        return minSociability;
    }

    public void setMinSociability(int minSociability) {
        this.minSociability = minSociability;
    }

    public int getMaxSociability() {
        return maxSociability;
    }

    public void setMaxSociability(int maxSociability) {
        this.maxSociability = maxSociability;
    }

    public int getMinTrainingLevel() {
        return minTrainingLevel;
    }

    public void setMinTrainingLevel(int minTrainingLevel) {
        this.minTrainingLevel = minTrainingLevel;
    }

    public int getMaxTrainingLevel() {
        return maxTrainingLevel;
    }

    public void setMaxTrainingLevel(int maxTrainingLevel) {
        this.maxTrainingLevel = maxTrainingLevel;
    }

    public List<String> getPreferredSpecialNeeds() {
        return preferredSpecialNeeds;
    }

    public void setPreferredSpecialNeeds(List<String> preferredSpecialNeeds) {
        this.preferredSpecialNeeds = preferredSpecialNeeds;
    }

    public String getPreferredLocation() {
        return preferredLocation;
    }

    public void setPreferredLocation(String preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }
}