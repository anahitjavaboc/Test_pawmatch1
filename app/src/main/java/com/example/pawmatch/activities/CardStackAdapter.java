package com.example.pawmatch.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.test_pawmatch.R;

import java.util.ArrayList;
import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private final List<Pet> petList;

    public CardStackAdapter() {
        petList = new ArrayList<>();
        petList.add(new Pet("https://images.unsplash.com/photo-1605462436930-2b25828f08b3", "Buddy", "2 years old • Labrador", "Friendly and energetic dog who loves to play fetch."));
        petList.add(new Pet("https://images.unsplash.com/photo-1586671267727-6403930797b6", "Max", "3 years old • Beagle", "Loves to cuddle and explore the park."));
        petList.add(new Pet("https://images.unsplash.com/photo-1592194996308-7b43878e84a6", "Luna", "1 year old • Husky", "Playful and curious, great with kids."));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pet_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pet pet = petList.get(position);
        holder.bind(pet);
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView petImage;
        private final TextView petName;
        private final TextView petInfo;
        private final TextView petDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            petImage = itemView.findViewById(R.id.petImage);
            petName = itemView.findViewById(R.id.petName);
            petInfo = itemView.findViewById(R.id.petInfo);
            petDescription = itemView.findViewById(R.id.petDescription);
        }

        void bind(Pet pet) {
            petName.setText(pet.getName());
            petInfo.setText(pet.getInfo());
            petDescription.setText(pet.getDescription());

            // Load the image using Glide
            Glide.with(itemView.getContext())
                    .load(pet.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(petImage);
        }
    }
}