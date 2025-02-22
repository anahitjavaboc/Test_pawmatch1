package com.example.pawmatch;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import com.yuyakaido.android.cardstackview.*;

import java.util.ArrayList;
import java.util.List;

public class SwipeActivity extends AppCompatActivity {
    private CardStackView cardStackView;
    private CardStackLayoutManager manager;
    private PetSwipeAdapter adapter;
    private List<Pet> petList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        cardStackView = findViewById(R.id.cardStackView);
        petList = loadPetData();

        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardSwiped(Direction direction) {
                if (direction == Direction.Right) {
                    Toast.makeText(SwipeActivity.this, "Matched!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCardDragging(Direction direction, float ratio) {}
            @Override
            public void onCardRewound() {}
            @Override
            public void onCardCanceled() {}
            @Override
            public void onCardAppeared(View view, int position) {}
            @Override
            public void onCardDisappeared(View view, int position) {}
        });

        manager.setStackFrom(StackFrom.Top);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20);

        adapter = new PetSwipeAdapter(this, petList);
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    private List<Pet> loadPetData() {
        List<Pet> pets = new ArrayList<>();
        pets.add(new Pet("Buddy", "A friendly golden retriever", "https://example.com/buddy.jpg"));
        pets.add(new Pet("Whiskers", "A playful kitten", "https://example.com/whiskers.jpg"));
        pets.add(new Pet("Max", "Loyal and energetic", "https://example.com/max.jpg"));
        return pets;
    }
}
