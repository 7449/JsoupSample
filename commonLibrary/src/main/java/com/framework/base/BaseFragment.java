package com.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.framework.R;
import com.framework.base.mvp.BasePresenterImpl;
import com.framework.widget.Constant;
import com.framework.widget.StatusLayout;

/**
 * by y on 2016/7/26.
 */
public abstract class BaseFragment<P extends BasePresenterImpl> extends Fragment {
    protected static final String FRAGMENT_INDEX = "fragment_index";
    protected static final String FRAGMENT_TYPE = "fragment_type";
    protected boolean isLoad;
    protected boolean isPrepared;
    protected boolean isVisible;
    protected int tabPosition = 0;
    protected String type = null;
    protected Bundle bundle;
    protected P mPresenter;
    protected StatusLayout mStatusView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if (bundle != null) {
            initBundle();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mStatusView == null) {
            mStatusView = new StatusLayout(container.getContext());
            mStatusView.setSuccessView(getLayoutId(), new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mStatusView.setEmptyView(R.layout.layout_empty_view);
            mStatusView.setErrorView(R.layout.layout_network_error);
            setStatusViewStatus(StatusLayout.SUCCESS);
            isPrepared = true;
        }
        initById();
        return mStatusView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.setTag(TextUtils.concat(type, String.valueOf(tabPosition)));
        }
        initActivityCreated();
        mStatusView.getEmptyView().setOnClickListener(v -> clickNetWork());
        mStatusView.getErrorView().setOnClickListener(v -> clickNetWork());
    }

    protected void clickNetWork() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }

    protected <T extends View> T getView(int id) {
        //noinspection unchecked
        return (T) mStatusView.findViewById(id);
    }

    private void onVisible() {
        initActivityCreated();
    }

    protected void initBundle() {
    }

    protected abstract void initById();

    protected abstract P initPresenter();

    protected abstract void initActivityCreated();

    protected abstract int getLayoutId();

    protected void setLoad() {
        isLoad = true;
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.onDestroy(Constant.TYPE_NO_FINISH);
            mPresenter = null;
        }
        super.onDestroyView();
        if (mStatusView != null) {
            mStatusView.onDestroyView();
        }
    }

    public void setStatusViewStatus(int status) {
        if (mStatusView != null) {
            mStatusView.setStatus(status);
        }
    }
}

