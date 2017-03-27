package com.framework.utils;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.framework.App;

import java.util.List;

/**
 * by y on 2017/3/23.
 */

public class ApkUtils {

    public static final String XL = "com.xunlei.downloadprovider";
    public static final String ADM_PRO = "com.dv.adm.pay";
    public static final String ADM = "com.dv.adm";

    private ApkUtils() {
    }

    public static Intent getXLIntent() {
        return UIUtils.getContext().getPackageManager().getLaunchIntentForPackage(ApkUtils.XL);
    }

    public static boolean isApk(String packageName) {
        final PackageManager packageManager = App.getInstance().getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (!pinfo.isEmpty()) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
