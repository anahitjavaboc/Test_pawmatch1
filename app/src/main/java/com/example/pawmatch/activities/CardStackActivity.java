package com.example.test_pawmatch;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;

public class CardStackActivity extends AppCompatActivity {
    private CardStackView cardStackView;
    private CardStackLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.CardStackView); // Connect to your XML layout

        // Initialize CardStackView
        cardStackView = findViewById(R.id.card_stack_view);

        // Set up the CardStackLayoutManager
        layoutManager = new CardStackLayoutManager(this, new CardStackLayoutManager.CardStackListener() {
            @Override
            public void onCardSwiped(Direction direction) {
                // Handle card swipe direction (e.g., left, right, etc.)
            }

            @Override
            public void onCardRewound() {
                // Handle card rewind (optional)
            }

            @Override
            public void onCardCanceled() {
                // Handle card cancel action
            }

            @Override
            public void onCardAppeared(View view, int position) {
                // Handle card appearance
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                // Handle card disappearance
            }
        });

        // Set layout manager and adapter to CardStackView
        cardStackView.setLayoutManager(layoutManager);
        cardStackView.setAdapter(new YourCardStackAdapter()); // Replace with your adapter
    }
}
