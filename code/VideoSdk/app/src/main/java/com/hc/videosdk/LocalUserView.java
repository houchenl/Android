package com.hc.videosdk;

import android.content.Context;
import android.view.View;

import tbsdk.core.video.view.LocalVideoView;

/**
 * Created by yongxiang on 2016/6/28.
 */
public class LocalUserView
        extends UserView
{
    public LocalUserView( Context context )
    {
        super( context );
    }



    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec )
    {
        super.onMeasure( widthMeasureSpec, heightMeasureSpec );

        int viewWidthSize = MeasureSpec.getSize( widthMeasureSpec );
        int viewHeightSize = MeasureSpec.getSize( heightMeasureSpec );

        if ( viewHeightSize == 0 || viewWidthSize == 0 )
            return;
        for ( int i = 0; i < getChildCount(); i++ )
        {
            View child = getChildAt( i );
            if ( LocalVideoView.class.isInstance( child ) )
            {
                if ( ( ( ( float ) viewWidthSize ) / ( ( float ) viewHeightSize ) ) > ( 480.0f / 640.0f ) )
                {
                    int newHeight = viewWidthSize * 640 / 480;
                    newHeight = newHeight == 0 ? 1 : newHeight;
                    child.measure( viewWidthSize + MeasureSpec.EXACTLY, newHeight + MeasureSpec.EXACTLY );
                }else
                {
                    int newWidth = viewHeightSize * 480 / 640;
                    newWidth = newWidth == 0 ? 1 : newWidth;
                    child.measure( newWidth + MeasureSpec.EXACTLY, viewHeightSize + MeasureSpec.EXACTLY );
                }

            }
        }
    }

    @Override
    protected void onLayout( boolean changed, int left, int top, int right, int bottom )
    {
        super.onLayout( changed, left, top, right, bottom );

        for ( int i = 0; i < getChildCount(); i++ )
        {
            View child = getChildAt( i );
            if ( LocalVideoView.class.isInstance( child ) )
            {
                int width = right - left;
                int height = bottom - top;
                int measuredHeight = child.getMeasuredHeight();
                int measuredWidth = child.getMeasuredWidth();
                child.layout( ( width - measuredWidth ) / 2, ( height - measuredHeight ) / 2,//
                        ( width + measuredWidth ) / 2, ( height + measuredHeight ) / 2 );
            }
        }
    }

}
