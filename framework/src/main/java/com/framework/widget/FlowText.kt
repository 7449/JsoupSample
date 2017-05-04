package com.framework.widget

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.ViewGroup

import com.framework.R
import com.google.android.flexbox.FlexboxLayout

/**
 * by y on 2017/1/9.
 */

class FlowText : AppCompatTextView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        setPadding(18, 10, 18, 10)
        val params = FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.leftMargin = 10
        params.bottomMargin = 10
        setTextColor(ContextCompat.getColor(context, R.color.white))
        layoutParams = params
        setBackgroundResource(R.drawable.shape_search_name)
    }

}
