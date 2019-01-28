package com.hc.sp;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by liu_lei on 2017/11/20.
 *
 */

public class SharedPrefUtil {

    private static final String SP_NAME = "my_sp";

    private static SharedPrefUtil spu;
    private SharedPreferences sp;
    private Context context;

    private SharedPrefUtil(Context context) {
        this.context = context;
    }

    public static SharedPrefUtil getInstance() {
        if (spu == null) {
            synchronized (SharedPrefUtil.class) {
                if (spu == null) {
                    spu = new SharedPrefUtil(MyApplication.getInstance().getApplicationContext());
                }
            }
        }
        return spu;
    }

    private SharedPreferences getSp() {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        return sp;
    }

    /**
     * 写入数据
     */
    public <T> void put(String key, T data) {
        if (data instanceof String) {
            getSp().edit().putString(key, (String) data).apply();
        } else if (data instanceof Integer) {
            getSp().edit().putInt(key, (Integer) data).apply();
        } else if (data instanceof Long) {
            getSp().edit().putLong(key, (Long) data).apply();
        } else if (data instanceof Float) {
            getSp().edit().putFloat(key, (Float) data).apply();
        } else if (data instanceof Boolean) {
            getSp().edit().putBoolean(key, (Boolean) data).apply();
        }
    }

    /**
     * 读取数据
     */
    public <T> T get(String key, T defValue) {
        if (defValue instanceof String) {
            return (T) getSp().getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return (T) (Integer) getSp().getInt(key, (Integer) defValue);
        } else if (defValue instanceof Long) {
            return (T) (Long) getSp().getLong(key, (Long) defValue);
        } else if (defValue instanceof Float) {
            return (T) (Float) getSp().getFloat(key, (Float) defValue);
        } else if (defValue instanceof Boolean) {
            return (T) (Boolean) getSp().getBoolean(key, (Boolean) defValue);
        }

        return defValue;
    }

}
