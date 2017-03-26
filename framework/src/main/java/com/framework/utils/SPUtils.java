package com.framework.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * by y on 2017/3/26.
 */

public class SPUtils {
    private static final String SHAREDPREFERENCES_NAME = "image";
    private static SharedPreferences sharedPreferences;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private static void initSharePreferences(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        initSharePreferences(context.getApplicationContext());
    }

    public static boolean isNull() {
        return sharedPreferences == null;
    }

    public static Boolean getBoolean(String key, boolean defaultValue) {
        if (isNull()) {
            return defaultValue;
        }
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void setBoolean(String key, boolean value) {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static String getString(String key, String defaultValue) {
        if (isNull()) {
            return defaultValue;
        }
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void setString(String key, String value) {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static void clearAll() {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().clear().apply();
    }

}
