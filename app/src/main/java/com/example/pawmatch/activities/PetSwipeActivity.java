//package com.example.pawmatch.activities;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
//import com.yuyakaido.android.cardstackview.CardStackListener;
//import com.yuyakaido.android.cardstackview.CardStackView;
//import com.yuyakaido.android.cardstackview.Direction;
//import com.yuyakaido.android.cardstackview.StackFrom;
//import com.example.pawmatch.R;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PetSwipeActivity extends AppCompatActivity implements CardStackListener {
//
//    private CardStackView cardStackView;
//    private CardStackLayoutManager manager;
//    private PetSwipeAdapter adapter;
//    private List<Pet> petList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pet_swipe);
//
//        // Initialize CardStackView
//        cardStackView = findViewById(R.id.card_stack_view);
//        manager = new CardStackLayoutManager(this, this);
//        manager.setStackFrom(StackFrom.Top);
//        manager.setVisibleCount(3);
//        manager.setTranslationInterval(8.0f);
//        manager.setScaleInterval(0.95f);
//        manager.setSwipeThreshold(0.3f);
//        manager.setMaxDegree(20.0f);
//        manager.setDirections(Direction.HORIZONTAL);
//        manager.setCanScrollVertical(false);
//
//        // Set up adapter
//        petList = getDummyPets(); // Replace with Firebase data
//        adapter = new PetSwipeAdapter(petList, pet -> Toast.makeText(this, "Clicked " + pet.getName(), Toast.LENGTH_SHORT).show());
//
//        cardStackView.setLayoutManager(manager);
//        cardStackView.setAdapter(adapter);
//        cardStackView.setItemAnimator(new DefaultItemAnimator());
//    }
//
//    private List<Pet> getDummyPets() {
//        List<Pet> pets = new ArrayList<>();
//        pets.add(new Pet("Buddy", "Dog", "Golden Retriever", "3 years", "Large", "Male", 4, 5, 3, false, ""));
//        pets.add(new Pet("Whiskers", "Cat", "Siamese", "2 years", "Small", "Female", 3, 4, 2, false, ""));
//        return pets;
//    }
//
//    @Override
//    public void onCardSwiped(@NonNull Direction direction) {
//        Object Direction;
//        if (direction == Direction.Right) {
//            Toast.makeText(this, "Liked!", Toast.LENGTH_SHORT).show();
//        } else if (direction == Direction.Left) {
//            Toast.makeText(this, "Disliked!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public <Direction> void onCardDragging(Direction direction, float ratio) {}
//    @Override
//    public void onCardRewound() {}
//    @Override
//    public void onCardCanceled() {}
//    @Override
//    public void onCardAppeared(View view, int position) {}
//    @Override
//    public void onCardDisappeared(View view, int position) {}
//}
