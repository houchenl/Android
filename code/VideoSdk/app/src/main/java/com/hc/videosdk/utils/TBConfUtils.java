package com.hc.videosdk.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import tbsdk.core.confcontrol.confcotrlmacro.ITBConfMarcs;
import tbsdk.core.video.videomacro.TBUIVideoMacros;
import tbsdk.sdk.TBSDK;
import tbsdk.sdk.listener.ITBUIConfModuleListener;

public class TBConfUtils
{
    public static final String TAG = "test";


    /**
     * 加会
     *
     * @param context
     * @param confExportListener
     * @param
     * @param
     * @param
     * @param
     * @param
     * @param
     * @param
     * @param
     * @param
     * @param
     */
    public static int preJoinConf(Context context, ITBUIConfModuleListener confExportListener, String cmdLine_join )
    {

        // 加会的回调监听
        TBSDK.getInstance().getConfUIModuleKit().setConfUIListener( confExportListener );
        //加会
        return TBSDK.getInstance().getConfUIModuleKit().preJoinConf( cmdLine_join );

    }

    /**
     * 创会
     *
     * @param context
     * @param confExportListener
     * @param
     * @param
     * @param
     * @param
     * @param
     * @param
     * @param
     * @param
     * @param
     */
    public static void createConf(Context context, ITBUIConfModuleListener confExportListener, String cmdLine, String cmdLine_init )
    {

        TBSDK.getInstance().getConfUIModuleKit().setConfUIListener( confExportListener );
        //加会
        TBSDK.getInstance().getConfUIModuleKit().createConf( cmdLine );

    }

    /**
     * @param node_support_muiltVideos boolean
     * @param node_monitor_oriention   boolean
     * @return
     */
    public static String toJsonForVideoConfigure( String node_support_muiltVideos, String node_monitor_oriention, String node_video_encode_max, String node_auto_adjust_birate_level )
    {

        String cmdVideoConfig = "";
        try
        {
            // 构建一个JSONObject对象
            JSONObject jsonObject = new JSONObject();
            jsonObject.put( TBUIVideoMacros.NODE_SUPPORTMUILTVIDEOS, node_support_muiltVideos );
            jsonObject.put( TBUIVideoMacros.NODE_MONITORORIENTION, node_monitor_oriention );
            jsonObject.put( TBUIVideoMacros.NODE_VIDEOENCODEMAX, node_video_encode_max );
            jsonObject.put( TBUIVideoMacros.NODE_AUTOADJUSTBITRATELEVEL, node_auto_adjust_birate_level );
            // 生成字符串
            cmdVideoConfig = jsonObject.toString();
        }
        // 捕获异常
        catch ( JSONException e )
        {
            e.printStackTrace();
            return "";
        }
        return cmdVideoConfig;
    }

    public static String toJsonForConfCmdLineInit( String node_sitename, boolean hasHost, int moduleType, int conferenceMode )
    {
        // 初始化一个JSON字符串,用来存放站点信息
        String cmdLine_init = "";
        try
        {
            // 构建一个JSONObject对象
            JSONObject jsonObject = new JSONObject();
            // 键值对，存放站点信息
            jsonObject.put( ITBConfMarcs.NODE_SITENAME, node_sitename );
            // 是否有主持人
            jsonObject.put( ITBConfMarcs.NODE_HAS_HOST, hasHost );
            // 要初始化的模块
            jsonObject.put( ITBConfMarcs.NODE_MODULE, moduleType );
            // 会议模式（互动模式/直播模式(CDN模式)）
            jsonObject.put( ITBConfMarcs.NODE_CONFERENCE_MODE, conferenceMode );
            // 生成字符串
            cmdLine_init = jsonObject.toString();
        }
        // 捕获异常
        catch ( JSONException e )
        {
            e.printStackTrace();
            return "";
        }
        return cmdLine_init;
    }

    public static String toJsonForJoinConfCmdLine( String node_meetingid, String node_meetingpwd, String node_displayname, String node_username, String node_meetinghostpwd, String node_meetingtopic, String node_createconfdisplayname )
    {
        // 初始化一个JSON字符串,用来存放加会信息
        String cmdLine = "";
        try
        {
            // 构建一个JSONObject对象
            JSONObject jsonObject = new JSONObject();
            // 键值对，存放会议ID
            jsonObject.put( ITBConfMarcs.NODE_MEETINGID, node_meetingid );
            // 键值对，存会议密码
            jsonObject.put( ITBConfMarcs.NODE_MEETINGPWD, node_meetingpwd );
            // 键值对，存放用户的显示名
            jsonObject.put( ITBConfMarcs.NODE_DISPLAYNAME, node_displayname );
            // 键值对，存放用户名(通常为帐号)
            jsonObject.put( ITBConfMarcs.NODE_USERNAME, node_username );
            // 键值对，存放UI布局(没有额外说明，默认填 1)
//            jsonObject.put(ITBConfMarcs.NODE_UILAYOUT, node_uilayout);
            /******************* 选填参数 *****************/
            // 键值对，存放用户的主持人密码
            jsonObject.put( ITBConfMarcs.NODE_MEETINGHOSTPWD, node_meetinghostpwd );
            // 键值对，存放会议主题
            jsonObject.put( ITBConfMarcs.NODE_MEETINGTOPIC, node_meetingtopic );
            // 创建者显示名
            jsonObject.put( ITBConfMarcs.NODE_CREATECONFDISPLAYNAME, node_createconfdisplayname );
            // 生成字符串
            cmdLine = jsonObject.toString();
        }
        // 捕获异常
        catch ( JSONException e )
        {
            e.printStackTrace();
            return "";
        }
        return cmdLine;
    }

    public static String toJsonForCreateConfCmdLine( String node_meetingpwd, String node_displayname, String node_username, String node_meetinghostpwd, String node_meetingtopic )
    {
        // 初始化一个JSON字符串,用来存放创会信息
        String cmdLine = "";
        try
        {
            // 构建一个JSONObject对象
            JSONObject jsonObject = new JSONObject();
            // 键值对，存放会议密码
            jsonObject.put( ITBConfMarcs.NODE_MEETINGPWD, node_meetingpwd );
            // 键值对，存放用户的显示名
            jsonObject.put( ITBConfMarcs.NODE_DISPLAYNAME, node_displayname );
            // 键值对，存放用户的用户名
            jsonObject.put( ITBConfMarcs.NODE_USERNAME, node_username );
            // 键值对，存放UI布局(没有额外说明，默认填 1)
//            jsonObject.put(ITBConfMarcs.NODE_UILAYOUT, node_uilayout);
            /******************* 选填参数 *****************/
            // 键值对，存放会议主题
            jsonObject.put( ITBConfMarcs.NODE_MEETINGTOPIC, node_meetingtopic );
            // 键值对，存放用户的主持人密码
            jsonObject.put( ITBConfMarcs.NODE_MEETINGHOSTPWD, node_meetinghostpwd );
            // 创建会议后，是否立即加入会议

            // 生成字符串
            cmdLine = jsonObject.toString();
        }
        // 捕获异常
        catch ( JSONException e )
        {
            e.printStackTrace();
            return "";
        }
        return cmdLine;
    }


    public static String toJsonForSetOption( String node_talk_mode,String node_log_wsdl )
    {
        // 初始化一个JSON字符串,用来存放加会信息
        String cmdLine = "";
        try
        {
            // 构建一个JSONObject对象
            JSONObject jsonObject = new JSONObject();

            // 会议模式：语音模式
            jsonObject.put( ITBConfMarcs.NODE_TALK_MODE, node_talk_mode );
            //日志服务器
            jsonObject.put(ITBConfMarcs.NODE_LOG_WSDL,node_log_wsdl);
            // 生成字符串
            cmdLine = jsonObject.toString();
        }
        // 捕获异常
        catch ( JSONException e )
        {
            e.printStackTrace();
            return "";
        }
        return cmdLine;
    }

}
