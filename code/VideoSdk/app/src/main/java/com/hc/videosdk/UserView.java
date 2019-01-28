package com.hc.videosdk;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yongxiang on 2016/6/2.
 * <p>
 * for bind video view
 * <p>
 * we use imageView as default view , but default view can also be viewGroupt that contains head , name, bg...
 */
public class UserView
        extends FrameLayout
{


    public UserView( Context context )
    {
        super( context );
        // 视频黑边
        setBackgroundResource( R.drawable.tb_muilty_view_default_bg );
        mVideoGesture = new GestureDetector( context, new MySimpleOnGestureListener() );

        // default head view
        mDefaultView = new ImageView( getContext() ); // default view can alseo be viewGroupt that contains head , name, bg...
        mDefaultView.setImageResource( R.drawable.ghw_default_head );
        mDefaultView.setBackgroundColor( getResources().getColor( R.color.tb_yellow ) );
        mDefaultView.setVisibility( View.INVISIBLE );
        addView( mDefaultView );
        // displayName show
        mtvDisplayName = new TextView( context );
        mtvDisplayName.setTextColor( getResources().getColor( R.color.tb_red ) );
        addView( mtvDisplayName );

        // auser is speaking icon
        mivSpeaking = new ImageView( context );
        mivSpeaking.setImageResource( R.drawable.icon_speaking );
        mivSpeaking.setVisibility( View.INVISIBLE );
        addView( mivSpeaking );

    }

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec )
    {
        super.onMeasure( widthMeasureSpec, heightMeasureSpec );

        int width = MeasureSpec.getSize( widthMeasureSpec );
        int height = MeasureSpec.getSize( heightMeasureSpec );

        mivSpeaking.measure( width / 6 + MeasureSpec.EXACTLY, height / 6 + MeasureSpec.EXACTLY );
    }

    @Override
    protected void onLayout( boolean changed, int left, int top, int right, int bottom )
    {
        super.onLayout( changed, left, top, right, bottom );
        int width = right - left;
        int height = bottom - top;
        mivSpeaking.layout( width - mivSpeaking.getMeasuredWidth(), height - mivSpeaking.getMeasuredHeight(), width, height );
    }


    @Override
    public void addView( View child )
    {
        super.addView( child );
        bringChildToFront( mDefaultView );
        bringChildToFront( mtvDisplayName );
        bringChildToFront( mivSpeaking );
    }

    @Override
    public boolean onTouchEvent( MotionEvent event )
    {
        return mVideoGesture.onTouchEvent( event );
    }

    public void clear()
    {
        destoryDefault();
        mIsBind = false;
        mUid = -1;
        mChannelId = 1;
//        mDisplayName = null;
        mtvDisplayName.setText( null );
    }

    public void showDefault()
    {
        if ( mDefaultView.getVisibility() != View.VISIBLE )
            mDefaultView.setVisibility( View.VISIBLE );
    }

    public void hideDefault()
    {
        if ( mDefaultView.getVisibility() == View.VISIBLE )
            mDefaultView.setVisibility( View.INVISIBLE );
    }

    public void setDisplayName( String mDisplayName )
    {
//        this.mDisplayName = mDisplayName;
        mtvDisplayName.setText( mDisplayName );
    }

    public void setIsBind( boolean mIsBind )
    {
        this.mIsBind = mIsBind;
    }

    public void setUid( short mUid )
    {
        this.mUid = mUid;
    }

    public void setChannelId( int mChannelId )
    {
        this.mChannelId = mChannelId;
    }

    public boolean isBind()
    {
        return mIsBind;
    }

    public short getUid()
    {
        return mUid;
    }

    public int getChannelId()
    {
        return mChannelId;
    }

//    public String getDisplayName()
//    {
//        return mDisplayName;
//    }

    public void setGestureCallback( GestureCallback mGestureCallback )
    {
        this.mGestureCallback = mGestureCallback;
    }

    public void setIsSpeaking( boolean isSpeaking )
    {
        if ( isSpeaking )
        {
            if ( mivSpeaking.getVisibility() != View.VISIBLE )
                mivSpeaking.setVisibility( View.VISIBLE );
        }
        else if ( mivSpeaking.getVisibility() == View.VISIBLE )
            mivSpeaking.setVisibility( View.INVISIBLE );
    }

    private void destoryDefault()
    {
        if ( null != mDefaultView )
            removeView( mDefaultView );
        mDefaultView = null;
    }


    public class MySimpleOnGestureListener
            extends GestureDetector.SimpleOnGestureListener
    {

        @Override
        public boolean onDoubleTap( MotionEvent e )
        {
            if ( null != mGestureCallback )
                mGestureCallback.onDoubleTap( e, UserView.this );
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed( MotionEvent e )
        {
            if ( null != mGestureCallback )
                mGestureCallback.onSingleTapConfirmed( e, UserView.this );
            return true;
        }

        @Override
        public boolean onDown( MotionEvent e )
        {
            return true;
        }

        @Override
        public boolean onFling( MotionEvent e1, MotionEvent e2, float velocityX, float velocityY )
        {
            return super.onFling( e1, e2, velocityX, velocityY );
        }
    }


    public interface GestureCallback
    {

        public boolean onDoubleTap(MotionEvent e, View v);

        public boolean onSingleTapConfirmed(MotionEvent e, View v);

    }

    private ImageView       mDefaultView     = null;
    private boolean         mIsBind          = false;
    private short           mUid             = 0;
    private int             mChannelId       = 0;
    //    private String          mDisplayName     = null;
    private GestureDetector mVideoGesture    = null;
    private GestureCallback mGestureCallback = null;
    private TextView        mtvDisplayName   = null;
    private ImageView       mivSpeaking      = null;

}
