package com.example.pawmatch.activities;

import android.graphics.Path;

public abstract class CardStackListener {
    public abstract void onCardSwiped(Direction direction);

    public abstract void onCardSwiped(Path.Direction direction);
}
@Override
public void onCardSwiped(Path.Direction direction) {
    if (direction == Direction.Right) {
        Toast.makeText(SwipeActivity.this, "Matched!", Toast.LENGTH_SHORT).show();
    }
}

