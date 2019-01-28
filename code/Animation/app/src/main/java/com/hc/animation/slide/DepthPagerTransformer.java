package com.hc.animation.slide;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * This page transformer uses the default slide animation for sliding pages to the left,
 * while using a "depth" animation for sliding pages to the right.
 * This depth animation fades the page out, and scales it down linearly.
 * */
class DepthPagerTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75f;

    /*
    * The position parameter indicates where a given page is located relative to the center of the screen.
    * It is a dynamic property that changes as the user scrolls through the pages. When a page fills the screen,
    * its position value is 0. When a page is drawn just off the right side of the screen, its position value is 1.
    * If the user scrolls halfway between pages one and two, page one has a position of -0.5
    * and page two has a position of 0.5.
    * */
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            view.setAlpha(1 - position);

            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }

}
