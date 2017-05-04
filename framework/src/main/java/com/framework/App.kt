package com.framework

import android.app.Application
import android.content.Context
import com.framework.utils.SPUtils
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher


/**
 * by y on 2016/7/26.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        context = applicationContext
        SPUtils.init(context!!)
        watcher = LeakCanary.install(this)
    }


    companion object {

        private var context: Context? = null
        private var watcher: RefWatcher? = null

        val instance: App
            get() = (context as App?)!!

        val refWatcher: RefWatcher?
            get() = watcher
    }
}
