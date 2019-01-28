package com.hc.animation.flip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hc.animation.R;

public class CardFlipActivity extends AppCompatActivity {

    private boolean mIsShowBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_flip);

        // display the front of card by default.
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CardFrontFragment())
                    .commit();
        }

        findViewById(R.id.container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCard();
            }
        });
    }

    private void flipCard() {
        if (mIsShowBack) {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_left_in,
                            R.animator.card_flip_left_out,
                            R.animator.card_flip_right_in,
                            R.animator.card_flip_right_out)
                    .replace(R.id.container, new CardFrontFragment())
                    .commit();

            mIsShowBack = false;
            return;
        }

        // flip to the back.
        mIsShowBack = true;

        /*
        * Create and commit a new fragment transaction that adds the fragment for
        * the back of the card, uses custom animations, and is part of the fragment
        * manager's back stack.
        * */
        getFragmentManager().beginTransaction()

                /*
                * Replace the default fragment animations with animator resources
                * representing rotations when switching to the back of the card, as
                * well as animator resources representing rotations when flipping
                * back to the front (e.g. when the system Back button is pressed).
                * */
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)

                .replace(R.id.container, new CardBackFragment())

                .commit();
    }

}
