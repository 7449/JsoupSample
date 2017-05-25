package com.framework.utils;

import android.content.Intent;

/**
 * by y on 2017/3/23.
 */

public class ApkUtils {

    private static final String XL = "com.xunlei.downloadprovider";

    private ApkUtils() {
    }

    public static Intent getXLIntent() {
        return UIUtils.getContext().getPackageManager().getLaunchIntentForPackage(ApkUtils.XL);
    }
}
