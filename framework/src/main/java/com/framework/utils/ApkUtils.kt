package com.framework.utils

import android.content.Intent

/**
 * by y on 2017/3/23.
 */

object ApkUtils {

    val XL = "com.xunlei.downloadprovider"
    val ADM_PRO = "com.dv.adm.pay"
    val ADM = "com.dv.adm"


    fun getXLIntent(): Intent? {
        return UIUtils.context.packageManager.getLaunchIntentForPackage(ApkUtils.XL)
    }
}
