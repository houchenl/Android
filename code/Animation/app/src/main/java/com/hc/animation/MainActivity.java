package com.hc.animation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hc.animation.dialogact.MyDialogActivity;
import com.hc.animation.fade.CrossFadeActivity;
import com.hc.animation.flip.CardFlipActivity;
import com.hc.animation.frame.FrameAnimationActivity;
import com.hc.animation.layout.ListAnimationActivity;
import com.hc.animation.layout.LayoutChangeActivity;
import com.hc.animation.property.ArcMenuActivity;
import com.hc.animation.property.PropertyAnimationActivity;
import com.hc.animation.slide.ScreenSlideActivity;
import com.hc.animation.soup.PullToMakeSoupActivity;
import com.hc.animation.test.TestActivity;
import com.hc.animation.tween.TweenAnimationActivity;
import com.hc.animation.zoom.ZoomActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_test_library).setOnClickListener(this);
        findViewById(R.id.btn_tween).setOnClickListener(this);
        findViewById(R.id.btn_frame).setOnClickListener(this);
        findViewById(R.id.btn_layout).setOnClickListener(this);
        findViewById(R.id.btn_property).setOnClickListener(this);
        findViewById(R.id.btn_arc_menu).setOnClickListener(this);
        findViewById(R.id.btn_cross_fade).setOnClickListener(this);
        findViewById(R.id.btn_slide).setOnClickListener(this);
        findViewById(R.id.btn_card_flip).setOnClickListener(this);
        findViewById(R.id.btn_zoom).setOnClickListener(this);
        findViewById(R.id.btn_layout_change).setOnClickListener(this);
        findViewById(R.id.btn_make_soup).setOnClickListener(this);
        findViewById(R.id.btn_dialog_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test_library:
                start(TestActivity.class);
                break;
            case R.id.btn_tween:
                start(TweenAnimationActivity.class);
                break;
            case R.id.btn_frame:
                start(FrameAnimationActivity.class);
                break;
            case R.id.btn_layout:
                start(ListAnimationActivity.class);
                break;
            case R.id.btn_property:
                start(PropertyAnimationActivity.class);
                break;
            case R.id.btn_arc_menu:
                start(ArcMenuActivity.class);
                break;
            case R.id.btn_cross_fade:
                start(CrossFadeActivity.class);
                break;
            case R.id.btn_slide:
                start(ScreenSlideActivity.class);
                break;
            case R.id.btn_card_flip:
                start(CardFlipActivity.class);
                break;
            case R.id.btn_zoom:
                start(ZoomActivity.class);
                break;
            case R.id.btn_layout_change:
                start(LayoutChangeActivity.class);
                break;
            case R.id.btn_make_soup:
                start(PullToMakeSoupActivity.class);
                break;
            case R.id.btn_dialog_activity:
                start(MyDialogActivity.class);
                break;
        }
    }

    private void start(Class clz) {
        startActivity(new Intent(this, clz));
        // 第一个参数是跳转的目的activity进入的动画，每二个参数是当前activity的退出动画
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

}
