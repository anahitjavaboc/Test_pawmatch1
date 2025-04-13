package com.example.pawmatch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.SwipeableMethod;
import com.example.test_pawmatch.R;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements CardStackListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        MaterialButton signInButton = findViewById(R.id.signInButton);
        MaterialButton signUpButton = findViewById(R.id.signUpButton);

        // Sign In button click
        signInButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });

        // Sign Up button click
        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        // Initialize CardStackView
        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        CardStackLayoutManager layoutManager = new CardStackLayoutManager(this, this);
        layoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        layoutManager.setDirections(Direction.HORIZONTAL);
        layoutManager.setSwipeThreshold(0.3f);
        cardStackView.setLayoutManager(layoutManager);
        cardStackView.setAdapter(new CardStackAdapter());
    }

    @Override
    public void onCardSwiped(Direction direction) {
        if (direction == Direction.Right) {
            Toast.makeText(MainActivity.this, "Matched!", Toast.LENGTH_SHORT).show();
        } else if (direction == Direction.Left) {
            Toast.makeText(MainActivity.this, "Skipped!", Toast.LENGTH_SHORT).show();
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