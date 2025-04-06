//package com.example.pawmatch.activities;
//
//import android.os.Bundle;
//
//import com.example.test_pawmatch.R;
//import com.yuyakaido.android.cardstackview.CardStackView;
//import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
//import com.yuyakaido.android.cardstackview.CardStackListener;
//import com.yuyakaido.android.cardstackview.Direction;
//import com.yuyakaido.android.cardstackview.StackFrom;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SwipeActivity extends AppCompatActivity {
//    private List<Pet> petList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_swipe);
//
//        // Ensure the correct ID matches the XML file
//        CardStackView cardStackView = findViewById(R.id.cardStackView);
//        petList = loadPetData();
//
//        CardStackLayoutManager manager = new CardStackLayoutManager(this, new CardStackListener() {
//            @Override
//            public void onCardSwiped(com.yuyakaido.android.cardstackview.Direction direction) {
//                if (direction == com.example.android.cardstackview.Direction.Right) {
//                    Toast.makeText(SwipeActivity.this, "Matched!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCardDragging(Direction direction, float ratio) {
//            }
//
//            @Override
//            public void onCardRewound() {
//            }
//
//            @Override
//            public void onCardCanceled() {
//            }
//
//            @Override
//            public void onCardAppeared(View view, int position) {
//            }
//
//            @Override
//            public void onCardDisappeared(View view, int position) {
//            }
//        });
//
//        // Card Stack Manager settings
//        manager.setStackFrom(StackFrom.Top);
//        manager.setVisibleCount(3);
//        manager.setTranslationInterval(8.0f);
//        manager.setScaleInterval(0.95f);
//        manager.setSwipeThreshold(0.3f);
//        manager.setMaxDegree(20);
//
//        // Setting up Adapter
//        PetSwipeAdapter adapter = new PetSwipeAdapter(this, petList);
//        cardStackView.setLayoutManager(manager);
//        cardStackView.setAdapter(adapter);
//        cardStackView.setItemAnimator(new DefaultItemAnimator());
//    }
//
//    private List<Pet> loadPetData() {
//        List<Pet> pets = new ArrayList<>();
//        pets.add(new Pet("Buddy", "A friendly golden retriever", "https://example.com/buddy.jpg"));
//        pets.add(new Pet("Whiskers", "A playful kitten", "https://example.com/whiskers.jpg"));
//        pets.add(new Pet("Max", "Loyal and energetic", "https://example.com/max.jpg"));
//        return pets;
//    }
//}
