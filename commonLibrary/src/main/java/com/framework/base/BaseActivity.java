package com.framework.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.framework.R;
import com.framework.base.mvp.BasePresenterImpl;
import com.framework.widget.StatusLayout;

/**
 * by y on 2016/7/26.
 */
public abstract class BaseActivity<P extends BasePresenterImpl> extends AppCompatActivity {


    protected P mPresenter;
    protected StatusLayout mStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStatusView = new StatusLayout(this);
        mStatusView.setSuccessView(getLayoutId(), new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mStatusView.setEmptyView(R.layout.layout_empty_view);
        mStatusView.setErrorView(R.layout.layout_network_error);
        mStatusView.setStatus(StatusLayout.SUCCESS);
        mStatusView.getEmptyView().setOnClickListener(v -> clickNetWork());
        mStatusView.getErrorView().setOnClickListener(v -> clickNetWork());
        setContentView(mStatusView);
        initById();
        mPresenter = initPresenterImpl();
        if (mPresenter != null) {
            mPresenter.setTag(getClass().getSimpleName());
        }
        initCreate(savedInstanceState);
        if (getSupportActionBar() != null && !TextUtils.equals(getClass().getSimpleName(), "MainActivity")) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void clickNetWork() {
    }

    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }


    protected abstract void initCreate(Bundle savedInstanceState);

    protected abstract void initById();

    protected abstract P initPresenterImpl();

    protected abstract int getLayoutId();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }
}
