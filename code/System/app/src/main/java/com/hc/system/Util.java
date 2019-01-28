package com.hc.system;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by liulei0905 on 2016/9/9.
 */
public class Util {

    public static int getAppVersionCode(Context context, String packageName) {
        int version = 0;

        if (context == null || TextUtils.isEmpty(packageName)) return version;

        List<PackageInfo> listPackageInfos = context.getPackageManager().getInstalledPackages(0);
        if (listPackageInfos != null && listPackageInfos.size() > 0) {
            for (PackageInfo info : listPackageInfos) {
                String name = info.packageName;
                if (!TextUtils.isEmpty(name) && name.equals(packageName)) {
                    version = info.versionCode;
                }
            }
        }

        return version;
    }

}
