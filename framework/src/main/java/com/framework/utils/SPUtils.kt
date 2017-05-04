package com.framework.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build

/**
 * by y on 2017/3/26.
 */

object SPUtils {
    private val SHAREDPREFERENCES_NAME = "image"
    private var sharedPreferences: SharedPreferences? = null

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun initSharePreferences(context: Context) {
        sharedPreferences = context.applicationContext.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun init(context: Context) {
        initSharePreferences(context.applicationContext)
    }

    val isNull: Boolean
        get() = sharedPreferences == null

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        if (isNull) {
            return defaultValue
        }
        return sharedPreferences!!.getBoolean(key, defaultValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        if (isNull) {
            return
        }
        sharedPreferences!!.edit().putBoolean(key, value).apply()
    }

    fun getString(key: String, defaultValue: String): String {
        if (isNull) {
            return defaultValue
        }
        return sharedPreferences!!.getString(key, defaultValue)
    }

    fun setString(key: String, value: String) {
        if (isNull) {
            return
        }
        sharedPreferences!!.edit().putString(key, value).apply()
    }

    fun clearAll() {
        if (isNull) {
            return
        }
        sharedPreferences!!.edit().clear().apply()
    }

}
