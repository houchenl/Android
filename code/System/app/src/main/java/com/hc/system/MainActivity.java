package com.hc.system;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import cn.emoney.acg.module.SecurityHome;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "houchen";
    public static SimpleDateFormat mFormatDay = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat FormatFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static SimpleDateFormat FormatFull_short = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat mFormatHHmmss = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat mFormatDayFull = new SimpleDateFormat("MM月dd日 HH:mm:ss");
    public static SimpleDateFormat mFormatDayFull_1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    public static final TimeZone BEIJI_TIMEZONE = TimeZone.getTimeZone("GMT+8");
    public static final long ORG_TIMESTAMP = 1262275200;// 2010-01-01 0:0:0.000
    private String time = "2016-09-21 22:48:21.887";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get version code
        TextView tvVersionCode = (TextView) findViewById(R.id.tv_version_code);
        tvVersionCode.setText(Util.getAppVersionCode(this, "lthj.exchangestock.caopanshou") + "");

        Intent intent = new Intent(this, SecurityHome.class);
        intent.putExtra("extra_key_push_msg_type", "notify");
        intent.putExtra("extra_key_push_msg_extend_info", "http://www.baidu.com");
        String uriString = intent.toUri(Intent.URI_INTENT_SCHEME);
        Log.d(TAG, "onCreate: " + uriString);
        // intent:#Intent;component=com.hc.system/cn.emoney.acg.module.SecurityHome;S.extra_key_push_msg_type=notify;S.extra_key_push_msg_extend_info=http%3A%2F%2Fwww.baidu.com;end
        TextView tvIntentUri = (TextView) findViewById(R.id.tv_intent_uri);
        tvIntentUri.setText(uriString);

        // list json
        JSONArray array = new JSONArray();
        array.put("hello");
        array.put(33);
        array.put(true);
        List<JSONArray> list = new ArrayList<>();
        list.add(array);
        list.add(array);
        list.add(array);
        tvIntentUri.setText(list.toString());

        // today
        String today = mFormatDay.format(new Date());
        tvIntentUri.setText(today);

        // get url
        tvIntentUri.setText(getUrl());

        tvIntentUri.setText(formatNewsPushTime(time));

        ss();
    }

    private String getUrl() {
        String msg = "{\n" +
                "    \"data\": {\n" +
                "        \"sound\": \"default\",\n" +
                "        \"badge\": 1,\n" +
                "        \"_alert_title\": \"系统消息\",\n" +
                "        \"_alert_content\": \"巨额解禁汹涌减持：A股再临变数\",\n" +
                "        \"_alert_in_front\": 0,\n" +
                "        \"extra_args\": {\n" +
                "            \"args\": {\n" +
                "                \"id\": \"12\",\n" +
                "                \"url\": \"http://www.baidu.com\",\n" +
                "                \"type\": \"notify\"\n" +
                "            },\n" +
                "            \"id\": \"dfdasfadsfdsfasdf\"\n" +
                "        },\n" +
                "        \"title\": \"巨额解禁汹涌减持：A股再临变数\"\n" +
                "    },\n" +
                "    \"type\": \"notify\"\n" +
                "}";

        Log.d(TAG, "getUrl: 0");
        try {
            JSONObject objectMsg = new JSONObject(msg);
            Log.d(TAG, "getUrl: 1");
            if (objectMsg.has("data")) {
                Log.d(TAG, "getUrl: 3");
                JSONObject jsonData = objectMsg.getJSONObject("data");
                Log.d(TAG, "getUrl: 4");
                if (jsonData.has("extra_args")) {
                    Log.d(TAG, "getUrl: 5");
                    JSONObject jsonArgs = jsonData.getJSONObject("extra_args");
                    Log.d(TAG, "getUrl: 6");
                    if (jsonArgs != null && jsonArgs.has("args")) {
                        Log.d(TAG, "getUrl: 7");
                        JSONObject jsonArg = jsonArgs.getJSONObject("args");
                        Log.d(TAG, "getUrl: 8");
                        if (jsonArg != null && jsonArg.has("url")) {
                            Log.d(TAG, "getUrl: 9");
                            return jsonArg.getString("url");
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "getUrl: 2");
        }

        return "null";
    }

    public static String formatNewsPushTime(String fullTime) {
        String sRet = "";
        Date date = null;
        try {
            if (fullTime != null && fullTime.length() > 20) {
                // 2014-10-14 14:29:39.000
                date = FormatFull.parse(fullTime);
            } else {
                // 2014-10-14 14:29:39e
                date = FormatFull_short.parse(fullTime);
            }
        } catch (Exception e) {
        }
        if (date == null) {
            return sRet;
        }

        // 判断是不是今天
        // GregorianCalendar orginCalendar = new GregorianCalendar(2000, 1, 1);
        // Date orginDate = orginCalendar.getTime();
        long diff1 = date.getTime() / 1000 - ORG_TIMESTAMP;
        long diff2 = getTimestampFixed() / 1000 - ORG_TIMESTAMP;
        long t_days1 = diff1 / (3600 * 24);
        long t_days2 = diff2 / (3600 * 24);
        long days = t_days2 - t_days1; // 计算出参数的日期与当前日期的差距天数, 0为当天, 1为昨天, 2为前天 .....
        Log.d("houchen", "days: " + days);
        if (days == 0) {
            // 是今天
            sRet = mFormatHHmmss.format(date);
        } else {
            GregorianCalendar tGregorianCalendar = new GregorianCalendar();
            tGregorianCalendar.setTimeZone(BEIJI_TIMEZONE);
            tGregorianCalendar.setTime(date);
            int year = tGregorianCalendar.get(Calendar.YEAR);
            Calendar tCalendar = Calendar.getInstance();
            tCalendar.setTimeZone(BEIJI_TIMEZONE);
            int curYear = Calendar.getInstance().get(Calendar.YEAR);
            if (year < curYear) {
                sRet = mFormatDayFull_1.format(tGregorianCalendar.getTime());
            } else {
                sRet = mFormatDayFull.format(tGregorianCalendar.getTime());
            }
        }

        return sRet;
    }

    public static long getTimestampFixed() {
        return System.currentTimeMillis() + 0 * 1000;
    }

    private void ss() {
//        String extraValues = extras.getString("extra_args");
        // {"args":"{\"id\":\"60\",\"type\":\"notify\",\"url\":\"http:\\/\\/drdata.emoney.cn\\/temdata\\/small\\/201609\\/201692616231746181.html\"}","id":"60157957"}
        String extraValues = "{\"args\":\"{\\\"id\\\":\\\"60\\\",\\\"type\\\":\\\"notify\\\",\\\"url\\\":\\\"http:\\\\/\\\\/drdata.emoney.cn\\\\/temdata\\\\/small\\\\/201609\\\\/201692616231746181.html\\\"}\",\"id\":\"60157957\"}";
//        LogUtil.easylog(LogUtil.TAG_PUSH, "extra_args: " + extraValues);
        JSONObject jsonExtras = null;
        String type = "", extroInfo = "";
        try {
            jsonExtras = new JSONObject(extraValues);
            if (jsonExtras != null && jsonExtras.has("args")) {
                String argsValue = jsonExtras.getString("args");
                JSONObject jsonArgs = new JSONObject(argsValue);
                if (jsonArgs != null) {
                    if (jsonArgs.has("type")) {
                        type = jsonArgs.getString("type");
                    }
                    if (jsonArgs.has("url")) {
                        extroInfo = jsonArgs.getString("url");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "fail for: " + e.getMessage());
        }

        Log.d(TAG, "type: " + type + ", info: " + extroInfo);
    }

}
