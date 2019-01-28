package com.hc.animation.property;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.hc.animation.R;

import java.util.ArrayList;
import java.util.List;

public class ArcMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean mIsMenuOpen;

    private int[] mImgIds = {R.id.img_button, R.id.img_camera, R.id.img_music
            , R.id.img_place, R.id.img_sleep, R.id.img_thought, R.id.img_with};
    private List<ImageView> mImages = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_menu);

        for (int imgId : mImgIds) {
            ImageView image = (ImageView) findViewById(imgId);
            image.setOnClickListener(this);
            mImages.add(image);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_button:
                if (mIsMenuOpen) {
                    closeMenuAnim();
                    mIsMenuOpen = false;
                } else {
                    openMenuAnim();
                    mIsMenuOpen = true;
                }
                break;
            default:
                Toast.makeText(this, "click " + view.getId(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 展开菜单动画
     * */
    private void openMenuAnim() {
        // 几个子菜单一起展开
//        for (int i = 1; i < mImgIds.length; i++) {
//            ObjectAnimator animator = ObjectAnimator.ofFloat(mImages.get(i), "translationY", 0f, 150f * i);
//            animator.setDuration(500).start();
//        }

        // 几个子菜单依次展开
        for (int i = 1; i < mImgIds.length; i++) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(mImages.get(i), "translationY", 0f, 150f * i);
            animator.setStartDelay(300 * i);
            animator.setInterpolator(new BounceInterpolator());
            animator.setDuration(500).start();
        }
    }

    /**
     * 关闭菜单动画
     * */
    private void closeMenuAnim() {
        // 几个子菜单依次关闭
        for (int i = 1; i < mImgIds.length; i++) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(mImages.get(i), "translationY", 150f * i, 0f);
            animator.setStartDelay(300 * i);
            animator.setInterpolator(new BounceInterpolator());
            animator.setDuration(500).start();
        }
    }

}
