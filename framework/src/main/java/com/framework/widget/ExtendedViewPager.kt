package com.framework.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View


class ExtendedViewPager : ViewPager {


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        setPageTransformer(true, ZoomOutPageTransformer())
    }

    override fun canScroll(v: View, checkV: Boolean, dx: Int, x: Int, y: Int): Boolean {
        if (v is TouchImageView) {
            return v.canScrollHorizontallyFroyo(-dx)
        } else {
            return super.canScroll(v, checkV, dx, x, y)
        }
    }

}
