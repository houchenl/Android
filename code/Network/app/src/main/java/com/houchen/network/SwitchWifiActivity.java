package com.houchen.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SwitchWifiActivity extends AppCompatActivity {

    private static final String TAG = "houchen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_wifi);

        registerConnectionReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterConnectionReceiver();
    }

    private void registerConnectionReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(mNetworkChangeListener, filter);
    }

    private void unregisterConnectionReceiver() {
        unregisterReceiver(mNetworkChangeListener);
    }

    private BroadcastReceiver mNetworkChangeListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "onReceive: action " + action + ", " + System.currentTimeMillis());

            // 监听wifi的打开和关闭，与wifi的连接无关
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                Log.e(TAG, "onReceive: wifiState " + wifiState + ", " + System.currentTimeMillis());

                switch (wifiState) {
                    case WifiManager.WIFI_STATE_DISABLED:
                        Log.d(TAG, "onReceive: WifiManager.WIFI_STATE_DISABLED" + ", " + System.currentTimeMillis());
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        Log.d(TAG, "onReceive: WifiManager.WIFI_STATE_DISABLING" + ", " + System.currentTimeMillis());
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        Log.d(TAG, "onReceive: WifiManager.WIFI_STATE_ENABLING" + ", " + System.currentTimeMillis());
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        Log.d(TAG, "onReceive: WifiManager.WIFI_STATE_ENABLED" + ", " + System.currentTimeMillis());
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        Log.d(TAG, "onReceive: WifiManager.WIFI_STATE_UNKNOWN" + ", " + System.currentTimeMillis());
                        break;
                    default:
                        break;
                }
            }

            /*
            * 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager
            * .WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
            * 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，
            * 当然刚打开wifi肯定还没有连接到有效的无线
            * */
            if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (null != parcelableExtra) {
                    NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                    NetworkInfo.State state = networkInfo.getState();
                    boolean isConnected = state == NetworkInfo.State.CONNECTED;// 当然，这边可以更精确的确定状态
                    Log.e(TAG, "onReceive: isConnected " + isConnected + ", " + System.currentTimeMillis());
                }
            }

            /*
            * 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。
            * 最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见log
            * 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适
            * */
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                Log.d(TAG, "onReceive: ConnectivityManager.CONNECTIVITY_ACTION" + ", " + System.currentTimeMillis());

                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

                if (activeNetwork != null) { // connected to the internet
                    if (activeNetwork.isConnected()) {
                        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                            // connected to wifi
                            Log.e(TAG, "当前WiFi连接可用 ");
                        } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                            // connected to the mobile provider's data plan
                            Log.e(TAG, "当前移动网络连接可用 ");
                        }
                    } else {
                        Log.e(TAG, "当前没有网络连接，请确保你已经打开网络 ");
                    }

                    Log.e(TAG, "info.getTypeName()" + activeNetwork.getTypeName());
                    Log.e(TAG, "getSubtypeName()" + activeNetwork.getSubtypeName());
                    Log.e(TAG, "getState()" + activeNetwork.getState());
                    Log.e(TAG, "getDetailedState()" + activeNetwork.getDetailedState().name());
                    Log.e(TAG, "getDetailedState()" + activeNetwork.getExtraInfo());
                    Log.e(TAG, "getType()" + activeNetwork.getType());
                } else {   // not connected to the internet
                    Log.e(TAG, "当前没有网络连接，请确保你已经打开网络 ");
                }
            }

        }
    };

}
