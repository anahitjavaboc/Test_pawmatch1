package com.example.pawmatch.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yakaido.android.cardstackview.SwipeableMethod;
import com.example.test_pawmatch.R;

public class CardStackActivity extends AppCompatActivity implements CardStackListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        // Initialize CardStackView
        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        CardStackLayoutManager layoutManager = new CardStackLayoutManager(this, this);
        layoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        layoutManager.setDirections(Direction.HORIZONTAL);
        layoutManager.setSwipeThreshold(0.3f);

        // Set layout manager and adapter to CardStackView
        cardStackView.setLayoutManager(layoutManager);
        cardStackView.setAdapter(new CardStackAdapter());
    }

    @Override
    public void onCardSwiped(Direction direction) {
        if (direction == Direction.Right) {
            Toast.makeText(CardStackActivity.this, "Matched!", Toast.LENGTH_SHORT).show();
        } else if (direction == Direction.Left) {
            Toast.makeText(CardStackActivity.this, "Skipped!", Toast.LENGTH_SHORT).show();
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
}