package com.example.pawmatch.models;

import com.google.firebase.firestore.Exclude;
import java.util.List;

public class Pet {
    private String id;
    private String ownerId;
    private String name;
    private String breed;
    private int age;
    private String size; // Small, Medium, Large
    private int energyLevel; // 1-5
    private int sociability; // 1-5
    private int trainingLevel; // 1-5
    private List<String> specialNeeds;
    private String location;
    private String imageUrl;
    private String description;
    private List<String> interests;
    private boolean isActive;

    // Required empty constructor for Firestore
    public Pet() {}

    public Pet(String ownerId, String name, String breed, int age, String size,
               int energyLevel, int sociability, int trainingLevel,
               List<String> specialNeeds, String location, String imageUrl,
               String description, List<String> interests) {
        this.ownerId = ownerId;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.size = size;
        this.energyLevel = energyLevel;
        this.sociability = sociability;
        this.trainingLevel = trainingLevel;
        this.specialNeeds = specialNeeds;
        this.location = location;
        this.imageUrl = imageUrl;
        this.description = description;
        this.interests = interests;
        this.isActive = true;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public void setEnergyLevel(int energyLevel) {
        this.energyLevel = energyLevel;
    }

    public int getSociability() {
        return sociability;
    }

    public void setSociability(int sociability) {
        this.sociability = sociability;
    }

    public int getTrainingLevel() {
        return trainingLevel;
    }

    public void setTrainingLevel(int trainingLevel) {
        this.trainingLevel = trainingLevel;
    }

    public List<String> getSpecialNeeds() {
        return specialNeeds;
    }

    public void setSpecialNeeds(List<String> specialNeeds) {
        this.specialNeeds = specialNeeds;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}