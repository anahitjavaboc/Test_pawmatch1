package com.example.pawmatch.utils;

import android.net.Uri;
import com.example.pawmatch.models.Match;
import com.example.pawmatch.models.Message;
import com.example.pawmatch.models.Pet;
import com.example.pawmatch.models.UserPreferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FirebaseManager {
    private static FirebaseManager instance;
    private final FirebaseAuth auth;
    private final FirebaseFirestore db;
    private final FirebaseStorage storage;

    private FirebaseManager() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public static FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

    // Pet Profile Operations
    public void createPetProfile(Pet pet, OnCompleteListener<String> listener) {
        db.collection("pets")
                .add(pet)
                .addOnSuccessListener(documentReference -> {
                    pet.setId(documentReference.getId());
                    listener.onSuccess(documentReference.getId());
                })
                .addOnFailureListener(listener::onError);
    }

    public void updatePetProfile(Pet pet, OnCompleteListener<Void> listener) {
        db.collection("pets")
                .document(pet.getId())
                .set(pet)
                .addOnSuccessListener(aVoid -> listener.onSuccess(null))
                .addOnFailureListener(listener::onError);
    }

    public void getPetProfile(String petId, OnCompleteListener<Pet> listener) {
        db.collection("pets")
                .document(petId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Pet pet = documentSnapshot.toObject(Pet.class);
                    if (pet != null) {
                        pet.setId(documentSnapshot.getId());
                    }
                    listener.onSuccess(pet);
                })
                .addOnFailureListener(listener::onError);
    }

    public void uploadPetImage(String petId, Uri imageUri, OnCompleteListener<String> listener) {
        StorageReference storageRef = storage.getReference()
                .child("pet_images")
                .child(petId)
                .child("profile.jpg");

        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl()
                        .addOnSuccessListener(uri -> listener.onSuccess(uri.toString()))
                        .addOnFailureListener(listener::onError))
                .addOnFailureListener(listener::onError);
    }

    // User Preferences Operations
    public void saveUserPreferences(UserPreferences preferences, OnCompleteListener<String> listener) {
        db.collection("user_preferences")
                .add(preferences)
                .addOnSuccessListener(documentReference -> {
                    preferences.setId(documentReference.getId());
                    listener.onSuccess(documentReference.getId());
                })
                .addOnFailureListener(listener::onError);
    }

    public void updateUserPreferences(UserPreferences preferences, OnCompleteListener<Void> listener) {
        db.collection("user_preferences")
                .document(preferences.getId())
                .set(preferences)
                .addOnSuccessListener(aVoid -> listener.onSuccess(null))
                .addOnFailureListener(listener::onError);
    }

    public void getUserPreferences(String userId, OnCompleteListener<UserPreferences> listener) {
        db.collection("user_preferences")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        UserPreferences preferences = queryDocumentSnapshots.getDocuments().get(0).toObject(UserPreferences.class);
                        if (preferences != null) {
                            preferences.setId(queryDocumentSnapshots.getDocuments().get(0).getId());
                        }
                        listener.onSuccess(preferences);
                    } else {
                        listener.onSuccess(null);
                    }
                })
                .addOnFailureListener(listener::onError);
    }

    // Matching Operations
    public void createMatch(Pet pet1, Pet pet2, OnCompleteListener<String> listener) {
        Match match = new Match(pet1.getId(), pet2.getId(), pet1.getOwnerId(), pet2.getOwnerId());
        db.collection("matches")
                .add(match)
                .addOnSuccessListener(documentReference -> {
                    match.setId(documentReference.getId());
                    listener.onSuccess(documentReference.getId());
                })
                .addOnFailureListener(listener::onError);
    }

    public void getMatches(String petId, OnCompleteListener<List<Match>> listener) {
        db.collection("matches")
                .whereEqualTo("isActive", true)
                .whereEqualTo("pet1Id", petId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Match> matches = new ArrayList<>();
                    for (var doc : queryDocumentSnapshots) {
                        Match match = doc.toObject(Match.class);
                        match.setId(doc.getId());
                        matches.add(match);
                    }
                    listener.onSuccess(matches);
                })
                .addOnFailureListener(listener::onError);
    }

    // Messaging Operations
    public void sendMessage(Message message, OnCompleteListener<String> listener) {
        db.collection("messages")
                .add(message)
                .addOnSuccessListener(documentReference -> {
                    message.setId(documentReference.getId());
                    // Update match's last message
                    updateMatchLastMessage(message.getMatchId(), message.getContent(), message.getTimestamp());
                    listener.onSuccess(documentReference.getId());
                })
                .addOnFailureListener(listener::onError);
    }

    private void updateMatchLastMessage(String matchId, String lastMessage, java.util.Date lastMessageDate) {
        db.collection("matches")
                .document(matchId)
                .update("lastMessage", lastMessage, "lastMessageDate", lastMessageDate)
                .addOnFailureListener(e -> e.printStackTrace());
    }

    public void getMessages(String matchId, OnCompleteListener<List<Message>> listener) {
        db.collection("messages")
                .whereEqualTo("matchId", matchId)
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Message> messages = new ArrayList<>();
                    for (var doc : queryDocumentSnapshots) {
                        Message message = doc.toObject(Message.class);
                        message.setId(doc.getId());
                        messages.add(message);
                    }
                    listener.onSuccess(messages);
                })
                .addOnFailureListener(listener::onError);
    }

    // Callback interface
    public interface OnCompleteListener<T> {
        void onSuccess(T result);
        void onError(Exception e);
    }
}