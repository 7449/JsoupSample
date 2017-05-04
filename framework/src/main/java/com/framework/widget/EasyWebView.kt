package com.framework.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.AbsoluteLayout
import android.widget.ProgressBar

import com.framework.utils.HtmlUtils

/**
 * by y on 2017/4/18
 */

class EasyWebView : WebView {

    private var progressbar: ProgressBar? = null
    private var loadListener: WebViewLoadListener? = null

    fun setLoadListener(loadListener: WebViewLoadListener) {
        this.loadListener = loadListener
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {

        progressbar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
        progressbar!!.layoutParams = AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.MATCH_PARENT, 20, 0, 0)
        addView(progressbar)

        val settings = settings
        settings.javaScriptEnabled = true
        settings.builtInZoomControls = false
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.domStorageEnabled = true
        settings.setAppCacheEnabled(false)

        setWebChromeClient(
                object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        newProgressBar(newProgress)
                    }
                })
    }

    private fun newProgressBar(newProgress: Int) {
        if (newProgress == 100) {
            if (loadListener != null) {
                loadListener!!.loadingSuccess()
            }
            progressbar!!.visibility = View.GONE
        } else {
            if (progressbar!!.visibility == View.GONE) {
                progressbar!!.visibility = View.VISIBLE
            }
            progressbar!!.progress = newProgress
        }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        if (progressbar != null) {
            val lp = progressbar!!.layoutParams as AbsoluteLayout.LayoutParams
            lp.x = l
            lp.y = t
            progressbar!!.layoutParams = lp
        }
        super.onScrollChanged(l, t, oldl, oldt)
    }

    fun loadDataUrl(url: String) {
        loadDataWithBaseURL(null, HtmlUtils.getHtml(url), HtmlUtils.mimeType, HtmlUtils.coding, null)
    }

    interface WebViewLoadListener {
        fun loadingSuccess()
    }

}
