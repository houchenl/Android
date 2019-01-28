package com.hc.customview.slide;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.nfc.tech.NfcA;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hc.customview.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.startX;

/**
 * Created by liu_lei on 2017/7/24.
 */

public class SlideLayout extends LinearLayout {

    private int[] colors = {
            Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor("#0000ff"),
            Color.parseColor("#ffff00"), Color.parseColor("#00ffff"), Color.parseColor("#00ff00")
    };

    private Context context;

    private List<View> dividers = new ArrayList<>();

    private int index = 0;
    private float lastX;
    private float lastY;
    private int startScrollX;
    private boolean ignore;
    private boolean hasJudged;

    private int currentIndex = 0;

    public SlideLayout(Context context) {
        super(context);
        init(context);
    }

    public SlideLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlideLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        this.context = context;
    }

    public void addItem(String name) {
//        int bgColor = colors[index % 6];
        int bgColor = Color.parseColor("#3532ff");

        View view = LayoutInflater.from(context).inflate(R.layout.item_text, null, false);

        view.setBackgroundColor(bgColor);
        TextView tv = (TextView) view.findViewById(R.id.tv_msg);
        View divider = view.findViewById(R.id.bottom_line);
        tv.setText(name);
        dividers.add(divider);
        addView(view);

        final int position = index;
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onItemClick(position, v);
            }
        });

        index++;
    }

    public void setIndex(int index) {
        currentIndex = index;
        for (View view : dividers) {
            view.setVisibility(View.INVISIBLE);
        }
        dividers.get(currentIndex).setVisibility(View.VISIBLE);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_POINTER_DOWN:
//                lastX = event.getX();
//                lastY = event.getY();
//                startScrollX = getScrollX();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (ignore) {
//                    ignore = false;
//                    break;
//                }
//                float curX = event.getX();
//                float dX = curX - lastX;
//                lastX = curX;
////                if (hasJudged) {
//                    int targetScrollX = getScrollX() + (int)(-dX);
////                    if (targetScrollX > width_right) {
////                        scrollTo(width_right, 0);
////                    } else if (targetScrollX < 0) {
////                        scrollTo(0, 0);
////                    } else {
//                if (targetScrollX >= 0) {
//                        scrollTo(targetScrollX, 0);
//                    requestLayout();
//                }
////                    }
////                }
//                break;
//            case MotionEvent.ACTION_UP:
//                float finalX = event.getX();
//                break;
//            default:
//                break;
//        }
//
//        return true;
//    }
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

}
