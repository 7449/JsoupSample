package com.framework.base;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.framework.R;
import com.framework.base.mvp.BasePresenterImpl;
import com.framework.widget.Constant;
import com.framework.widget.StatusLayout;
import com.socks.library.KLog;

/**
 * by y on 2016/7/26.
 */
public abstract class BaseActivity<P extends BasePresenterImpl> extends AppCompatActivity {


    protected P mPresenter;
    protected StatusLayout mStatusView;
    protected int state = Constant.TYPE_NO_FINISH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.i(getClass().getSimpleName());
        setContentView(initContentView());
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

    /**
     * 最外面套一层 {@link CoordinatorLayout} 是为了 解锁 {@link android.support.design.widget.Snackbar} 更多姿势，例如 滑动删除
     * <p>
     * {@link StatusLayout} : 多种状态下的rootView
     * <p>
     * 这里取巧了一下，因为 {@link org.jsoup.Jsoup} 抓取的数据都可以用一个 {@link  android.support.v7.widget.RecyclerView} 来显示
     * <p>
     * {@link StatusLayout} SuccessView 并没有隐藏或者显示，
     * 因为{@link FrameLayout} 填充 View 的时候是依次重叠的，这个时候只要最先填充 SuccessView
     * 只要去控制 errorView 或者 EmptyView 隐藏或者显示就行了，会自行覆盖掉 SuccessView，真正的项目中不应该这样做，因为要处理的布局远远比这个复杂
     *
     * @return activity 根布局
     */
    private View initContentView() {
        CoordinatorLayout coordinatorLayout = new CoordinatorLayout(this);
        coordinatorLayout.setId(R.id.activityRootView);
        mStatusView = new StatusLayout(this);
        mStatusView.setSuccessView(getLayoutId(), new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mStatusView.setEmptyView(R.layout.layout_empty_view);
        mStatusView.setErrorView(R.layout.layout_network_error);
        mStatusView.getEmptyView().setOnClickListener(v -> clickNetWork());
        mStatusView.getErrorView().setOnClickListener(v -> clickNetWork());
        setStatusViewStatus(StatusLayout.SUCCESS);
        coordinatorLayout.addView(mStatusView, new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return coordinatorLayout;
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
        if (mStatusView != null) {
            mStatusView.onDestroyView();
        }
        if (mPresenter != null) {
            mPresenter.onDestroy(state);
            mPresenter = null;
        }
    }

    public void setStatusViewStatus(int status) {
        if (mStatusView != null) {
            mStatusView.setStatus(status);
        }
    }
}
