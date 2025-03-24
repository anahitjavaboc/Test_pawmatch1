package com.example.test_pawmatch.utils;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.test_pawmatch.models.Pet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FirebaseManager {
    private static final String TAG = "FirebaseManager";
    private static FirebaseManager instance;
    private final FirebaseFirestore db;
    private final FirebaseStorage storage;
    private final FirebaseAuth auth;

    private FirebaseManager() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public static synchronized FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

    public interface FirebaseCallback {
        void onSuccess();
        void onError(String error);
    }

    public void addPet(Pet pet, FirebaseCallback callback) {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            callback.onError("User not authenticated");
            return;
        }

        db.collection("users")
                .document(user.getUid())
                .collection("pets")
                .add(pet)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "Pet added with ID: " + documentReference.getId());
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding pet", e);
                    callback.onError("Failed to add pet: " + e.getMessage());
                });
    }

    public void updatePet(String petId, Pet pet, FirebaseCallback callback) {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            callback.onError("User not authenticated");
            return;
        }

        db.collection("users")
                .document(user.getUid())
                .collection("pets")
                .document(petId)
                .set(pet)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Pet updated successfully");
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error updating pet", e);
                    callback.onError("Failed to update pet: " + e.getMessage());
                });
    }

    public void deletePet(String petId, FirebaseCallback callback) {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            callback.onError("User not authenticated");
            return;
        }

        db.collection("users")
                .document(user.getUid())
                .collection("pets")
                .document(petId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Pet deleted successfully");
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error deleting pet", e);
                    callback.onError("Failed to delete pet: " + e.getMessage());
                });
    }

    public void getPets(FirebaseCallback callback) {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            callback.onError("User not authenticated");
            return;
        }

        db.collection("users")
                .document(user.getUid())
                .collection("pets")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Pet> pets = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Pet pet = document.toObject(Pet.class);
                        if (pet != null) {
                            pet.setId(document.getId());
                            pets.add(pet);
                        }
                    }
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting pets", e);
                    callback.onError("Failed to get pets: " + e.getMessage());
                });
    }

    public void uploadPetImage(Uri imageUri, String petId, FirebaseCallback callback) {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            callback.onError("User not authenticated");
            return;
        }

        String fileName = "pet_" + petId + "_" + UUID.randomUUID().toString() + ".jpg";
        StorageReference storageRef = storage.getReference()
                .child("users")
                .child(user.getUid())
                .child("pets")
                .child(petId)
                .child(fileName);

        UploadTask uploadTask = storageRef.putFile(imageUri);
        uploadTask.addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    Log.d(TAG, "Upload progress: " + progress + "%");
                })
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        Log.d(TAG, "Image uploaded successfully");
                        callback.onSuccess();
                    });
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error uploading image", e);
                    callback.onError("Failed to upload image: " + e.getMessage());
                });
    }

    public void addVaccination(String petId, Pet.Vaccination vaccination, FirebaseCallback callback) {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            callback.onError("User not authenticated");
            return;
        }

        DocumentReference petRef = db.collection("users")
                .document(user.getUid())
                .collection("pets")
                .document(petId);

        petRef.get().addOnSuccessListener(documentSnapshot -> {
            Pet pet = documentSnapshot.toObject(Pet.class);
            if (pet != null) {
                pet.getVaccinations().add(vaccination);
                petRef.set(pet)
                        .addOnSuccessListener(aVoid -> {
                            Log.d(TAG, "Vaccination added successfully");
                            callback.onSuccess();
                        })
                        .addOnFailureListener(e -> {
                            Log.e(TAG, "Error adding vaccination", e);
                            callback.onError("Failed to add vaccination: " + e.getMessage());
                        });
            } else {
                callback.onError("Pet not found");
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error getting pet", e);
            callback.onError("Failed to get pet: " + e.getMessage());
        });
    }

    public void updateVaccination(String petId, Pet.Vaccination oldVaccination, Pet.Vaccination newVaccination, FirebaseCallback callback) {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            callback.onError("User not authenticated");
            return;
        }

        DocumentReference petRef = db.collection("users")
                .document(user.getUid())
                .collection("pets")
                .document(petId);

        petRef.get().addOnSuccessListener(documentSnapshot -> {
            Pet pet = documentSnapshot.toObject(Pet.class);
            if (pet != null) {
                List<Pet.Vaccination> vaccinations = pet.getVaccinations();
                int index = vaccinations.indexOf(oldVaccination);
                if (index != -1) {
                    vaccinations.set(index, newVaccination);
                    petRef.set(pet)
                            .addOnSuccessListener(aVoid -> {
                                Log.d(TAG, "Vaccination updated successfully");
                                callback.onSuccess();
                            })
                            .addOnFailureListener(e -> {
                                Log.e(TAG, "Error updating vaccination", e);
                                callback.onError("Failed to update vaccination: " + e.getMessage());
                            });
                } else {
                    callback.onError("Vaccination not found");
                }
            } else {
                callback.onError("Pet not found");
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error getting pet", e);
            callback.onError("Failed to get pet: " + e.getMessage());
        });
    }

    public void deleteVaccination(String petId, Pet.Vaccination vaccination, FirebaseCallback callback) {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            callback.onError("User not authenticated");
            return;
        }

        DocumentReference petRef = db.collection("users")
                .document(user.getUid())
                .collection("pets")
                .document(petId);

        petRef.get().addOnSuccessListener(documentSnapshot -> {
            Pet pet = documentSnapshot.toObject(Pet.class);
            if (pet != null) {
                pet.getVaccinations().remove(vaccination);
                petRef.set(pet)
                        .addOnSuccessListener(aVoid -> {
                            Log.d(TAG, "Vaccination deleted successfully");
                            callback.onSuccess();
                        })
                        .addOnFailureListener(e -> {
                            Log.e(TAG, "Error deleting vaccination", e);
                            callback.onError("Failed to delete vaccination: " + e.getMessage());
                        });
            } else {
                callback.onError("Pet not found");
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error getting pet", e);
            callback.onError("Failed to get pet: " + e.getMessage());
        });
    }
} 