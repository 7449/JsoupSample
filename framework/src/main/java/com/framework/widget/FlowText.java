package com.framework.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.framework.R;

import org.apmem.tools.layouts.FlowLayout;

/**
 * by y on 2017/1/9.
 */

public class FlowText extends AppCompatTextView {
    public FlowText(Context context) {
        super(context);
        init();
    }

    public FlowText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlowText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setPadding(18, 10, 18, 10);
        FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 10;
        params.bottomMargin = 10;
        setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        setLayoutParams(params);
        setBackgroundResource(R.drawable.shape_search_name);
    }

}
