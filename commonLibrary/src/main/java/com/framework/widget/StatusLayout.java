package com.framework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.framework.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/5/19
 */

public class StatusLayout extends FrameLayout {
    public static final int SUCCESS = 1;
    public static final int ERROR = 2;
    public static final int EMPTY = 3;
    private static final int NO_LAYOUT = 0X00;
    private int mStatus;

    private View errorView;
    private View emptyView;
    private View successView;

    public StatusLayout(Context context) {
        super(context);
        init(null);
    }

    public StatusLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public View getViewLayout(@LayoutRes int id) {
        return LayoutInflater.from(getContext()).inflate(id, this, false);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StatusLayout);

        int errorViewId = typedArray.getResourceId(R.styleable.StatusLayout_status_error_layout, NO_LAYOUT);
        int emptyViewId = typedArray.getResourceId(R.styleable.StatusLayout_status_empty_layout, NO_LAYOUT);
        int successViewId = typedArray.getResourceId(R.styleable.StatusLayout_status_success_layout, NO_LAYOUT);

        if (errorViewId != NO_LAYOUT) {
            setErrorView(errorViewId);
        }
        if (emptyViewId != NO_LAYOUT) {
            setEmptyView(emptyViewId);
        }
        if (successViewId != NO_LAYOUT) {
            setSuccessView(successViewId);
        }

        typedArray.recycle();
    }

    public void onDestroyView() {
//        for (int i = 0; i < getChildCount(); i++) {
//            View view = getChildAt(i);
//            KLog.i(view.getClass().getSimpleName());
//        }
        List<View> allChildViews = getAllChildViews(this);
        int size = allChildViews.size();
        for (int i = 0; i < size; i++) {
            View view = allChildViews.get(i);
            if (view != null) {
                view = null;
            }
        }
    }

    private List<View> getAllChildViews(View view) {
        List<View> listView = new ArrayList<>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                listView.add(viewchild);
                listView.addAll(getAllChildViews(viewchild));
            }
        }
        return listView;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(@StatusMode int status) {
        if (mStatus == status || errorView == null || emptyView == null || successView == null) {
            return;
        }
        mStatus = status;
        emptyView.setVisibility(status == EMPTY ? VISIBLE : GONE);
        errorView.setVisibility(status == ERROR ? VISIBLE : GONE);
    }

    public void setErrorView(@NonNull View errorView) {
        setErrorView(errorView, getParams());
    }

    public void setErrorView(@LayoutRes int errorViewId, @NonNull LayoutParams params) {
        setErrorView(getViewLayout(errorViewId), params);
    }

    public void setErrorView(@NonNull View errorView, @NonNull LayoutParams params) {
        this.errorView = errorView;
        errorView.setVisibility(GONE);
        addView(errorView, params);
    }

    public void setEmptyView(@NonNull View emptyView) {
        setEmptyView(emptyView, getParams());
    }

    public void setEmptyView(@LayoutRes int emptyViewId, @NonNull LayoutParams params) {
        setEmptyView(getViewLayout(emptyViewId), params);
    }

    public void setEmptyView(@NonNull View emptyView, @NonNull LayoutParams params) {
        this.emptyView = emptyView;
        emptyView.setVisibility(GONE);
        addView(emptyView, params);
    }

    public void setSuccessView(@NonNull View successView) {
        setSuccessView(successView, getParams());
    }

    public void setSuccessView(@LayoutRes int successViewId, @NonNull LayoutParams params) {
        setSuccessView(getViewLayout(successViewId), params);
    }

    public void setSuccessView(@NonNull View successView, @NonNull LayoutParams params) {
        this.successView = successView;
        addView(successView, params);
    }

    public View getSuccessView() {
        return successView;
    }

    public void setSuccessView(@LayoutRes int successViewId) {
        setSuccessView(getViewLayout(successViewId), getParams());
    }

    public View getErrorView() {
        return errorView;
    }

    public void setErrorView(@LayoutRes int errorViewId) {
        setErrorView(getViewLayout(errorViewId), getParams());
    }

    public View getEmptyView() {
        return emptyView;
    }

    public void setEmptyView(@LayoutRes int emptyViewId) {
        setEmptyView(getViewLayout(emptyViewId), getParams());
    }

    private LayoutParams getParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
    }

    @IntDef({
            StatusLayout.SUCCESS,
            StatusLayout.EMPTY,
            StatusLayout.ERROR})
    @Retention(RetentionPolicy.SOURCE)
    @interface StatusMode {
    }
}