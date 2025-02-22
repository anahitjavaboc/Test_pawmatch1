package com.example.test_pawmatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class PetSwipeAdapter extends RecyclerView.Adapter<PetSwipeAdapter.ViewHolder> {
    private Context context;
    private List<Pet> petList;

    public PetSwipeAdapter(Context context, List<Pet> petList) {
        this.context = context;
        this.petList = petList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pet_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pet pet = petList.get(position);
        holder.petNameText.setText(pet.getName());
        holder.petBioText.setText(pet.getBio());

        // Load pet image using Glide
        Glide.with(context)
                .load(pet.getImageUrl())
                .placeholder(R.drawable.ic_pet_placeholder)
                .into(holder.petImageView);
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView petImageView;
        TextView petNameText, petBioText;

        public ViewHolder(View itemView) {
            super(itemView);
            petImageView = itemView.findViewById(R.id.petImageView);
            petNameText = itemView.findViewById(R.id.petNameText);
            petBioText = itemView.findViewById(R.id.petBioText);
        }
    }
}
