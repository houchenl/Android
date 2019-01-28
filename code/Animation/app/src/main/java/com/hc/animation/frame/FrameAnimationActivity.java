package com.hc.animation.frame;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.hc.animation.R;

/**
 * Drawable animation lets you load a series of Drawable resources one after another to create an animation.
 * This is a traditional animation in the sense that it is created with a sequence of different images,
 * played in order, like a roll of film. The AnimationDrawable class is the basis for Drawable animations.
 * */
public class FrameAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        findViewById(R.id.btn_frame).setOnClickListener(this);
        mImage = (ImageView) findViewById(R.id.img);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_frame:
                frameAnimation();
                break;
        }
    }

    /**
     * 逐帧动画
     * */
    private void frameAnimation() {
        /*mImage.setImageResource(R.drawable.anim_frame);
        AnimationDrawable animationDrawable = (AnimationDrawable) mImage.getDrawable();
        animationDrawable.start();*/

        mImage.setBackgroundResource(R.drawable.anim_frame);
        AnimationDrawable animationDrawable = (AnimationDrawable) mImage.getBackground();
        animationDrawable.start();

        /*
        * It's important to note that the start() method called on the AnimationDrawable cannot be called
        * during the onCreate() method of your Activity, because the AnimationDrawable is not yet
        * fully attached to the window. If you want to play the animation immediately,
        * without requiring interaction, then you might want to call it from the onWindowFocusChanged()
        * method in your Activity, which will get called when Android brings your window into focus.
        * */
    }

    /**
     * called after onResume() and before onPause()
     * */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

}
