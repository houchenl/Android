package com.hc.hencoder.hello;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liu_lei on 2018/1/23.
 *
 */

public class HelloView extends View {

    public HelloView(Context context) {
        super(context);
    }

    public HelloView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HelloView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
    }

}
