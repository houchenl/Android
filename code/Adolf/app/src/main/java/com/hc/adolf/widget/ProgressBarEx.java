package com.hc.adolf.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by liulei0905 on 2016/8/4.
 *
 */
public class ProgressBarEx extends ProgressBar {

    public ProgressBarEx(Context context) {
        super(context);
    }

    public ProgressBarEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressBarEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    public void hide() {
        setVisibility(View.GONE);
    }

}
