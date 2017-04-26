package com.framework;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.framework.utils.SPUtils;
import com.squareup.leakcanary.LeakCanary;

/**
 * by y on 2016/7/26.
 */
public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static App getInstance() {
        return (App) context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        context = getApplicationContext();
        SPUtils.init(context);
        LeakCanary.install(this);
    }
}
