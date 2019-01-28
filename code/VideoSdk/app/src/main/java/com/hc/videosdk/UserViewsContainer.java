package com.hc.videosdk;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import tb.base.utils.TBVideoSize.Size;
import tbsdk.core.video.videomacro.TBUIVideoSplitType;

/**
 * for show user view,  and chang split.
 */
public class UserViewsContainer
        extends FrameLayout
{
    public UserViewsContainer(Context context )
    {
        super( context );
        mContext = context;
//        setBackgroundResource( tb.tbconfsdk.R.drawable.tb_muilty_view_default_bg );
    }

    public void clearRes()
    {
        removeAllViews();
        mlistVideoViewParent.clear();
        mFullScreenView = null;
        mVideoSplitType = TBUIVideoSplitType.VideoSplitType_UNKNOW;
        msizeLocalVideoSmall = null;
        mlocalVideoView = null;
        mbSmallWindowShow = false;
    }

    @Override
    public boolean dispatchTouchEvent( MotionEvent ev )
    {
        if ( null != mContainerGesture )
            mContainerGesture.onTouchEvent( ev );
        return super.dispatchTouchEvent( ev );
    }

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec )
    {
        super.onMeasure( widthMeasureSpec, heightMeasureSpec );
        int viewWidthSize = MeasureSpec.getSize( widthMeasureSpec );
        int viewHeightSize = MeasureSpec.getSize( heightMeasureSpec );
        int childCount = getChildCount();
        //调整为小窗口显示状态 ： 画中画显示，不能双击放大缩小
        if ( mbSmallWindowShow )
        {
            View viewLocal = findViewById( R.id.video_local_view_id );
            if ( mlistVideoViewParent.size() > 0 )  // 画中画
            {
                View firstVideo = mlistVideoViewParent.get( 0 );
                firstVideo.measure( widthMeasureSpec, heightMeasureSpec );

                _initSize( viewWidthSize, viewHeightSize );
                if ( null != viewLocal )
                    viewLocal.measure( msizeLocalVideoSmall.width + MeasureSpec.EXACTLY, msizeLocalVideoSmall.height + MeasureSpec.EXACTLY );

                //其他视频隐藏
                for ( int i = 0; i < childCount; i++ )
                {
                    View view = getChildAt( i );
                    if ( view != firstVideo && view != viewLocal )
                        view.measure( 1 + MeasureSpec.EXACTLY, 1 + MeasureSpec.EXACTLY );  // hide other view
                }
            }
            else
            {
                // 本地视频全屏
                if ( null != viewLocal )
                    viewLocal.measure( widthMeasureSpec, heightMeasureSpec );
            }
            if ( null != viewLocal )
                bringChildToFront( viewLocal );
            return;
        }
        //全屏显示
        if ( null != mFullScreenView )
        {
            View viewLocal = findViewById( R.id.video_local_view_id );

            mFullScreenView.measure( widthMeasureSpec, heightMeasureSpec );  // show full screen view first
            for ( int i = 0; i < childCount; i++ )
            {
                View view = getChildAt( i );
                if ( view != mFullScreenView && view != viewLocal ) // do not change local video size, or maybe stop camera preview
                    view.measure( 1 + MeasureSpec.EXACTLY, 1 + MeasureSpec.EXACTLY );  // hide other view
            }

            bringChildToFront( mFullScreenView );
            return;
        }
        // 分屏显示
        View viewLocal = findViewById( R.id.video_local_view_id );
        if ( null != viewLocal )
            bringChildToFront( viewLocal );
        switch ( mVideoSplitType )
        {
            case TBUIVideoSplitType.VideoSplitType_UNKNOW:
                break;
            case TBUIVideoSplitType.VideoSplitType_ONE:
                // 本地视频全屏
                if ( null != viewLocal )
                    viewLocal.measure( widthMeasureSpec, heightMeasureSpec );
                break;
            case TBUIVideoSplitType.VideoSplitType_TWO:
                if ( mlistVideoViewParent.size() > 0 )  // 画中画
                {
                    View firstVideo = mlistVideoViewParent.get( 0 );
                    firstVideo.measure( widthMeasureSpec, heightMeasureSpec );

                    _initSize( viewWidthSize, viewHeightSize );
                    if ( null != viewLocal )
                        viewLocal.measure( msizeLocalVideoSmall.width + MeasureSpec.EXACTLY, msizeLocalVideoSmall.height + MeasureSpec.EXACTLY );
                }
                break;
            case TBUIVideoSplitType.VideoSplitType_FOUR:
                _measureSplitScreen( viewWidthSize, viewWidthSize, childCount, 2, 2 );
//                _measureSplitScreen( viewWidthSize, viewHeightSize, childCount, 2, 2 );
                break;
            case TBUIVideoSplitType.VideoSplitType_NINE:
                _measureSplitScreen( viewWidthSize, viewWidthSize, childCount, 3, 3 );
//                _measureSplitScreen( viewWidthSize, viewHeightSize, childCount, 3, 3 );
                break;
        }

    }

    @Override
    protected void onLayout( boolean changed, int left, int top, int right, int bottom )
    {
        int width = right - left;
        int height = bottom - top;
        int childCount = getChildCount();

        //调整为小窗口显示状态 ： 画中画显示，不能双击放大缩小
        if ( mbSmallWindowShow )
        {
            View viewLocal = findViewById( R.id.video_local_view_id );
            if ( mlistVideoViewParent.size() > 0 )  // 画中画
            {
                View firstVideo = mlistVideoViewParent.get( 0 );
                firstVideo.layout( ( width - firstVideo.getMeasuredWidth() ) / 2, ( height - firstVideo.getMeasuredHeight() ) / 2,//
                        ( width + firstVideo.getMeasuredWidth() ) / 2, ( height + firstVideo.getMeasuredHeight() ) / 2 );

                if ( null != viewLocal )
                    viewLocal.layout( width - msizeLocalVideoSmall.width, 0, width, msizeLocalVideoSmall.height );


                //其他视频隐藏
                for ( int i = 0; i < childCount; i++ )
                {
                    View view = getChildAt( i );
                    if ( view != firstVideo && view != viewLocal )
                        view.layout( view.getLeft(), view.getTop(), view.getLeft() + 1, view.getTop() + 1 ); // hide other view
                }
            }
            else
            {
                // 本地视频全屏
                if ( null != viewLocal )
                    viewLocal.layout( ( width - viewLocal.getMeasuredWidth() ) / 2, ( height - viewLocal.getMeasuredHeight() ) / 2,//
                            ( width + viewLocal.getMeasuredWidth() ) / 2, ( height + viewLocal.getMeasuredHeight() ) / 2 );
            }
            return;
        }

        //全屏显示
        if ( null != mFullScreenView )
        {
            View viewLocal = findViewById( R.id.video_local_view_id );
            mFullScreenView.layout( 0, 0, width, height );
            for ( int i = 0; i < childCount; i++ )
            {
                View view = getChildAt( i );
                if ( view != mFullScreenView && view != viewLocal )
                    view.layout( view.getLeft(), view.getTop(), view.getLeft() + 1, view.getTop() + 1 ); // hide other view
            }
            return;
        }

        // 分屏显示

        View viewLocal = findViewById( R.id.video_local_view_id );
        switch ( mVideoSplitType )
        {
            case TBUIVideoSplitType.VideoSplitType_UNKNOW:
                break;
            case TBUIVideoSplitType.VideoSplitType_ONE:
                // 本地视频全屏
                if ( null != viewLocal )
                    viewLocal.layout( ( width - viewLocal.getMeasuredWidth() ) / 2, ( height - viewLocal.getMeasuredHeight() ) / 2,//
                            ( width + viewLocal.getMeasuredWidth() ) / 2, ( height + viewLocal.getMeasuredHeight() ) / 2 );
                break;
            case TBUIVideoSplitType.VideoSplitType_TWO:

                if ( mlistVideoViewParent.size() > 0 )  // 画中画
                {
                    View firstVideo = mlistVideoViewParent.get( 0 );
                    firstVideo.layout( ( width - firstVideo.getMeasuredWidth() ) / 2, ( height - firstVideo.getMeasuredHeight() ) / 2,//
                            ( width + firstVideo.getMeasuredWidth() ) / 2, ( height + firstVideo.getMeasuredHeight() ) / 2 );

                    if ( null != viewLocal )
                        viewLocal.layout( width - msizeLocalVideoSmall.width, 0, width, msizeLocalVideoSmall.height );
                }
                break;
            case TBUIVideoSplitType.VideoSplitType_FOUR:
                _layoutSplitScreen( width, width, childCount, 2, 2 );
//                _layoutSplitScreen( width, height, childCount, 2, 2 );
                break;
            case TBUIVideoSplitType.VideoSplitType_NINE:
                _layoutSplitScreen( width, width, childCount, 3, 3 );
//                _layoutSplitScreen( width, height, childCount, 3, 3 );
                break;
            default:
                break;
        }
    }

    private void _layoutSplitScreen( int width, int height, int childCount, int column, int row )
    {
        if ( column <= 0 || row <= 0 )
        {
            return;
        }

        int index = 0;
        boolean bIsLocalVideoAdd = false;
        int muiltyVideoSize = mlistVideoViewParent.size();
        for ( int i = 0; i < childCount; i++ )
        {
            int l = ( i % column ) * width / column;
            int t = ( ( int ) ( i / column ) ) * height / row;
            int r = ( i % column + 1 ) * width / column;
            int b = ( ( int ) ( i / column + 1 ) ) * height / row;

            if ( bIsLocalVideoAdd || ( index < muiltyVideoSize && mlistVideoViewParent.get( index ).isBind() ) )
            {
                mlistVideoViewParent.get( index ).layout( l, t, r, b );
                index++;
            }
            else if ( !bIsLocalVideoAdd && null != mlocalVideoView )
            {
                mlocalVideoView.layout( l, t, r, b );
                bIsLocalVideoAdd = true;
            }
        }
    }

    private void _measureSplitScreen( int viewWidthSize, int viewHeightSize, int childCount, int column, int row )
    {
        if ( column <= 0 || row <= 0 )
        {
            return;
        }
        for ( int i = 0; i < childCount; i++ )
        {
            int columnWidth = viewWidthSize / column + MeasureSpec.EXACTLY;
            int rowHeight = viewHeightSize / row + MeasureSpec.EXACTLY;
            getChildAt( i ).measure( columnWidth, rowHeight );
//			LOG.debug( "_measureSplitScreen, columnWidth, rowHeight:" + columnWidth + ", " + rowHeight );
        }
    }

    public boolean isSmallWindowShow()
    {
        return mbSmallWindowShow;
    }

    public void OnDoubleTap( View v, boolean isFullScreen )
    {
        if ( -1 == indexOfChild( v ) )
            return;
        if ( isFullScreen )
        {
            mFullScreenView = v;
        }
        else
        {
            mFullScreenView = null;
        }
        requestLayout();
    }

    public void changeToSmallWindowShow( boolean isShow )
    {
        mbSmallWindowShow = isShow;
        requestLayout();
    }


    public boolean isFullScreenShow()
    {
        return null != mFullScreenView;
    }

    public View getFullScreenView()
    {
        return mFullScreenView;
    }

    public void clearFullScreenView()
    {
        if ( null != mFullScreenView )
        {
            mFullScreenView = null;
            requestLayout();
        }
    }


    public UserView addUserView( final short uid, final String dn, UserView.GestureCallback gestureCallback )
    {
        UserView videoViewParent = new UserView( mContext );
        videoViewParent.setIsBind( true );
        videoViewParent.setUid( uid );
        videoViewParent.setDisplayName( dn );
        videoViewParent.setGestureCallback( gestureCallback );
        addView( videoViewParent );
        bringChildToFront( mlocalVideoView );
        mlistVideoViewParent.add( videoViewParent );

        return videoViewParent;
    }

    public UserView addLocalVideoView( UserView.GestureCallback gestureCallback )
    {
        if ( null == mlocalVideoView )
        {
            LocalUserView videoViewParent = new LocalUserView( mContext );
            videoViewParent.setGestureCallback( gestureCallback );
            videoViewParent.setId( R.id.video_local_view_id );

            mlocalVideoView = videoViewParent;
        }
        if ( -1 == indexOfChild( mlocalVideoView ) )
            addView( mlocalVideoView );
        bringChildToFront( mlocalVideoView );

        return mlocalVideoView;
    }

    public void removeLocalVideoView()
    {
        if ( null == mlocalVideoView )
            return;
        removeView( mlocalVideoView );
        mlocalVideoView = null;
    }

    public boolean hideLocalVideoView( boolean isHide )
    {
        if ( null == mlocalVideoView )
            return false;
        if ( isHide )
        {
            removeView( mlocalVideoView );

        }
        else
        {
            if ( -1 == indexOfChild( mlocalVideoView ) )
                addView( mlocalVideoView );
            bringChildToFront( mlocalVideoView );
        }
        return true;
    }


    public UserView getLocalVideoView()
    {
        return mlocalVideoView;
    }

    public boolean removeUserView( short uid )
    {
        UserView videoViewParent = findVideoByUid( uid );
        if ( null == videoViewParent )
            return false;
        mlistVideoViewParent.remove( videoViewParent );
        removeView( videoViewParent );
        return true;
    }

    public UserView findVideoByUid( short uid )
    {
        for ( UserView videoView : mlistVideoViewParent )
        {
            if ( videoView.getUid() == uid )
            {
                return videoView;
            }
        }
        return null;
    }

    public void setSimpleOnGestureListener( GestureDetector.SimpleOnGestureListener simpleOnGestureListener )
    {
        this.mContainerGesture = new GestureDetector( getContext(), simpleOnGestureListener );
    }

    /**
     * @param containerWidth
     * @param containerHeight
     */
    private void _initSize( int containerWidth, int containerHeight )
    {
        if ( null == msizeLocalVideoSmall )
        {
            msizeLocalVideoSmall = new Size();
        }

        msizeLocalVideoSmall.width = containerWidth / 4;
        if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH )
        {
            msizeLocalVideoSmall.height = ( int ) ( msizeLocalVideoSmall.width * 640 / 480 );
        }
        else
        {
            DisplayMetrics dispalyMetrics = getContext().getResources().getDisplayMetrics();
            msizeLocalVideoSmall.height = ( int ) ( msizeLocalVideoSmall.width * dispalyMetrics.heightPixels / dispalyMetrics.widthPixels );
        }

        // if preview view size is too small, camera preview maybe stop
        if ( msizeLocalVideoSmall.height < 5 )
            msizeLocalVideoSmall.height = 5;
        if ( msizeLocalVideoSmall.width < 5 )
            msizeLocalVideoSmall.width = 5;
    }


    private ArrayList<UserView> mlistVideoViewParent = new ArrayList<UserView>();  // not contains your user view

    private Context mContext = null;

    public int mVideoSplitType = TBUIVideoSplitType.VideoSplitType_UNKNOW;

    private View mFullScreenView = null;

    private LocalUserView mlocalVideoView = null;

    private Size msizeLocalVideoSmall = null;

    /**
     * 是否是处于悬浮窗口显示状态：画中画显示，此时不能双击放大缩小
     */
    private boolean mbSmallWindowShow = false;

    // onSingleTapConfirmed
    private GestureDetector mContainerGesture = null;
}
