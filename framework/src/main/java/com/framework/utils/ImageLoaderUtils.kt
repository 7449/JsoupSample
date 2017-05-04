package com.framework.utils


import android.widget.ImageView

import com.bumptech.glide.Glide
import com.framework.R

/**
 * by y on 2016/7/26.
 */
object ImageLoaderUtils {

    fun display(imageView: ImageView, url: String) {
        Glide.with(imageView.context).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).crossFade().into(imageView)
    }

}
