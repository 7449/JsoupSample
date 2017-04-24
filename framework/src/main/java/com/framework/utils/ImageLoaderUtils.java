package com.framework.utils;


import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.framework.R;

/**
 * by y on 2016/7/26.
 */
public class ImageLoaderUtils {

    public static void display(@NonNull ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).crossFade().into(imageView);
    }

}
