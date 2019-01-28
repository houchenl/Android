package com.hc.videosdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.hc.videosdk.utils.TBConfUtils;
import com.tb.conf.api.struct.video.EAutoAdjustBirateLevel;

import tbsdk.core.confcontrol.base.TBConfModuleMacros;
import tbsdk.core.confcontrol.confcotrlmacro.ITBConfMarcs;
import tbsdk.core.video.videomacro.TBUIVideoBitrateType;
import tbsdk.core.video.videomacro.TBUIVideoMacros;
import tbsdk.core.video.videomacro.TBUIVideoResolutionType;
import tbsdk.core.video.videomacro.TBUIVideoSplitType;
import tbsdk.sdk.TBSDK;
import tbsdk.sdk.interfacekit.ETBCONFERENCEMODE;
import tbsdk.sdk.interfacekit.ITBUIVideoModuleKit;
import tbsdk.sdk.interfacekit.TBUIConfMacro;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "houchen-MainActivity";

    // json 配置
    private String mszCmdLineInit;

    private int mnBitrateType = TBUIVideoBitrateType.BitrateType_512;

    private boolean mbIsConfInitModule;

    private FrameLayout mVideoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVideoLayout = findViewById(R.id.framelayout);

        initConfModule();

        ITBUIVideoModuleKit moduleKit = TBSDK.getInstance().getVideoModuleKit();
        Log.d(TAG, "onCreate: moduleKit is " + moduleKit);

        configVideoModule();

        setVideoSplitMode(0);
        UserViewsContainer userViewsContainer = new UserViewsContainer(this);
        mVideoLayout.addView(userViewsContainer);
        int nRet = TBSDK.getInstance().getVideoModuleKit().bindLocalVideoView(videoView);
        Log.d(TAG, "onCreate: bind ret " + nRet);
        if (TBUIVideoMacros.EVIDEORETURNCODE_OK == nRet) {

        }
    }

    // 初始化会议模块
    private boolean initConfModule() {
        TBSDK.getInstance().getConfUIModuleKit().unInitModule();

        // 初始化参数
        String node_sitename = "https://lx.techbridge-inc.com";
        // 是否有主持人
        boolean hasHost = false;
        //要初始化的模块
        int moduleType = TBConfModuleMacros.module_whiteboard | TBConfModuleMacros.module_video | TBConfModuleMacros.module_audio;

        // 会议模式（互动模式/ 直播模式(CDN模式)）
        int conferenceMode = ETBCONFERENCEMODE.TBCONFERENCEMODE_INTERACT;

        mszCmdLineInit = TBConfUtils.toJsonForConfCmdLineInit(node_sitename, hasHost, moduleType, conferenceMode);

        // init conf moudle
        int initResult = TBSDK.getInstance().getConfUIModuleKit().initModule(mszCmdLineInit);
        if (TBUIConfMacro.ERETURNCODE_STATUS_ERROR == initResult) {
            // initModule, has init module
            mbIsConfInitModule = true;
        } else if (TBUIConfMacro.ERETURNCODE_PARMS_INVALID == initResult) {
            // mszCmdLineInit is invalid:appkey is null or json parse error
            mbIsConfInitModule = false;
        } else if (TBUIConfMacro.ERETURNCODE_OK == initResult) {
            // init sucessfully
            mbIsConfInitModule = true;
        }
//        String option = TBConfUtils.toJsonForSetOption( null, "https://gh-log.techbridge-inc.com/webService/service.php" );
//        TBSDK.getInstance().getConfUIModuleKit().setOption( option );
        return mbIsConfInitModule;
    }

    /**
     * 配置视频模块
     */
    private void configVideoModule() {
        // 是否支持多路视频
        boolean node_support_muiltVideos = true;
        // 设置码流调整的上行上调速度
        int autoAdjustBirateLevel = EAutoAdjustBirateLevel.AutoAdjustBirateLevel_HIGHT;
        String jsonVideoConfig = TBConfUtils.toJsonForVideoConfigure(node_support_muiltVideos + "", null, String.valueOf(mnBitrateType), autoAdjustBirateLevel + "");
        ITBUIVideoModuleKit videoModule = TBSDK.getInstance().getVideoModuleKit();
        if (null != videoModule) {
            // 配置视频的一些参数：方向旋转，动态码流调整速度,etc.
            videoModule.setVideoModuleConfig(jsonVideoConfig);
            // 设置本地视频上传时的采集参数 小于256的时候设置ResolutionType_640x480不起作用，会被调整到ResolutionType_320x240
            videoModule.setVideoCaptureConfig(TBUIVideoResolutionType.ResolutionType_640x480, mnBitrateType);
            // 设置摄像头设备的分辨率
            videoModule.setVideoSrcConfig(TBUIVideoResolutionType.ResolutionType_640x480);

//            if ( null != mVideoViewController )
//            {
//                // 视频模块的回调
//                videoModule.setTBUIVideoListener( mVideoViewController );
//            }
        }
    }

    private void setVideoSplitMode(int mnUserCount) {

        int type = 0;
        // 1. calculate VideoSplitType
        if (mnUserCount == 0) {
            type = TBUIVideoSplitType.VideoSplitType_ONE;
        } else if (mnUserCount == 1) {
            type = TBUIVideoSplitType.VideoSplitType_TWO;
        } else if (mnUserCount == 2 || mnUserCount == 3) {
            type = TBUIVideoSplitType.VideoSplitType_FOUR;
        } else if (mnUserCount > 3) {
            type = TBUIVideoSplitType.VideoSplitType_NINE;
        }

        // 2._setVideoSplitMode for adjust bitrate
        TBSDK.getInstance().getVideoModuleKit().setVideoSplitMode(type);
    }

}
