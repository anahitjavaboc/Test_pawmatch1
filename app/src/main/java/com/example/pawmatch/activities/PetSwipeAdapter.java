public class PetSwipeAdapter extends RecyclerView.Adapter<PetSwipeAdapter.PetViewHolder> {
    private List<Pet> pets;
    private OnPetClickListener listener;

    public interface OnPetClickListener {
        void onPetClick(Pet pet);
    }

    public PetSwipeAdapter(List<Pet> pets, OnPetClickListener listener) {
        this.pets = pets;
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
        holder.bind(pets.get(position));
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    class PetViewHolder extends RecyclerView.ViewHolder {
        private ImageView petImageView;
        private TextView nameTextView, typeTextView, breedTextView;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            petImageView = itemView.findViewById(R.id.petImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            breedTextView = itemView.findViewById(R.id.breedTextView);

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

            Glide.with(itemView.getContext())
                    .load(pet.getImageUrl())
                    .placeholder(R.drawable.placeholder_pet)
                    .into(petImageView);
        }
    }
}
