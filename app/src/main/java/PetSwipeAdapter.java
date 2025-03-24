package com.example.test_pawmatch.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test_pawmatch.R;
import com.example.test_pawmatch.models.Pet;

import java.util.ArrayList;
import java.util.List;

public class PetSwipeAdapter extends RecyclerView.Adapter<PetSwipeAdapter.PetViewHolder> {
    private List<Pet> pets;
    private OnPetClickListener listener;

    public interface OnPetClickListener {
        void onPetClick(Pet pet);
        void onPetLike(Pet pet);
        void onPetDislike(Pet pet);
    }

    public PetSwipeAdapter(OnPetClickListener listener) {
        this.pets = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pet_swipe, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = pets.get(position);
        holder.bind(pet);
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    public void updatePets(List<Pet> newPets) {
        this.pets = newPets;
        notifyDataSetChanged();
    }

    public void addPet(Pet pet) {
        pets.add(pet);
        notifyItemInserted(pets.size() - 1);
    }

    public void removePet(int position) {
        if (position >= 0 && position < pets.size()) {
            pets.remove(position);
            notifyItemRemoved(position);
        }
    }

    class PetViewHolder extends RecyclerView.ViewHolder {
        private ImageView petImageView;
        private TextView nameTextView;
        private TextView typeTextView;
        private TextView breedTextView;
        private TextView ageTextView;
        private TextView sizeTextView;
        private TextView genderTextView;
        private TextView energyLevelTextView;
        private TextView sociabilityTextView;
        private TextView trainingLevelTextView;
        private TextView specialNeedsTextView;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            initializeViews(itemView);
            setupClickListeners();
        }

        private void initializeViews(View itemView) {
            petImageView = itemView.findViewById(R.id.petImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            breedTextView = itemView.findViewById(R.id.breedTextView);
            ageTextView = itemView.findViewById(R.id.ageTextView);
            sizeTextView = itemView.findViewById(R.id.sizeTextView);
            genderTextView = itemView.findViewById(R.id.genderTextView);
            energyLevelTextView = itemView.findViewById(R.id.energyLevelTextView);
            sociabilityTextView = itemView.findViewById(R.id.sociabilityTextView);
            trainingLevelTextView = itemView.findViewById(R.id.trainingLevelTextView);
            specialNeedsTextView = itemView.findViewById(R.id.specialNeedsTextView);
        }

        private void setupClickListeners() {
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onPetClick(pets.get(position));
                }
            });
        }

        public void bind(Pet pet) {
            nameTextView.setText(pet.getName());
            typeTextView.setText(pet.getType());
            breedTextView.setText(pet.getBreed());
            ageTextView.setText(pet.getAgeRange());
            sizeTextView.setText(pet.getSize());
            genderTextView.setText(pet.getGender());
            energyLevelTextView.setText(String.format("Energy Level: %d/5", pet.getEnergyLevel()));
            sociabilityTextView.setText(String.format("Sociability: %d/5", pet.getSociability()));
            trainingLevelTextView.setText(String.format("Training Level: %d/5", pet.getTrainingLevel()));

            if (pet.isHasSpecialNeeds()) {
                specialNeedsTextView.setVisibility(View.VISIBLE);
                specialNeedsTextView.setText(pet.getSpecialNeedsDescription());
            } else {
                specialNeedsTextView.setVisibility(View.GONE);
            }

            if (pet.getImageUrl() != null && !pet.getImageUrl().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(pet.getImageUrl())
                        .placeholder(R.drawable.placeholder_pet)
                        .error(R.drawable.error_pet)
                        .into(petImageView);
            } else {
                petImageView.setImageResource(R.drawable.placeholder_pet);
            }
        }
    }
} 