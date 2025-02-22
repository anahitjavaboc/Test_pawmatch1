package com.example.pawmatch;

public class Pet {
    private String name;
    private String bio;
    private String imageUrl;

    public Pet(String name, String bio, String imageUrl) {
        this.name = name;
        this.bio = bio;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }
    public String getBio() { return bio; }
    public String getImageUrl() { return imageUrl; }
}
