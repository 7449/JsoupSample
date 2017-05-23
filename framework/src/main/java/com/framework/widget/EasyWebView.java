package com.framework.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.framework.utils.HtmlUtils;

/**
 * by y on 2017/4/18
 */

public class EasyWebView extends WebView {

    private ProgressBar progressbar;
    private WebViewLoadListener loadListener;

    public void setLoadListener(WebViewLoadListener loadListener) {
        this.loadListener = loadListener;
    }

    public EasyWebView(Context context) {
        super(context);
        init();
    }

    public EasyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EasyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {

        progressbar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 20, 0, 0));
        addView(progressbar);

        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(false);

        setWebChromeClient(
                new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        super.onProgressChanged(view, newProgress);
                        newProgressBar(newProgress);
                    }
                });
    }

    private void newProgressBar(int newProgress) {
        if (newProgress == 100) {
            if (loadListener != null) {
                loadListener.loadingSuccess();
            }
            progressbar.setVisibility(GONE);
        } else {
            if (progressbar.getVisibility() == GONE) {
                progressbar.setVisibility(VISIBLE);
            }
            progressbar.setProgress(newProgress);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (progressbar != null) {
            LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
            lp.x = l;
            lp.y = t;
            progressbar.setLayoutParams(lp);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void loadDataUrl(String url) {
        loadDataWithBaseURL(null, HtmlUtils.getHtml(url), HtmlUtils.getMimeType(), HtmlUtils.getCoding(), null);
    }

    public interface WebViewLoadListener {
        void loadingSuccess();
    }

}
