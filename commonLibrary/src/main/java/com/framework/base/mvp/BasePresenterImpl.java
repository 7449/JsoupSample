package com.framework.base.mvp;

import com.framework.widget.Constant;
import com.framework.widget.StatusLayout;
import com.socks.library.KLog;

import java.util.List;

import io.reactivex.jsoup.network.manager.RxJsoupNetWork;
import io.reactivex.jsoup.network.manager.RxJsoupNetWorkListener;

/**
 * by y on 2017/3/23
 */

public abstract class BasePresenterImpl<M, V extends BaseView<M>>
        implements RxJsoupNetWorkListener<M> {

    public static final String KK_URL_TAG = "kk_url";
    public static final String KK_DATA_TAG = "kk_data";

    protected V view;
    private Object tag;

    private StatusLayout mStatusView;

    public BasePresenterImpl(V view) {
        this.view = view;
    }

    @Override
    public void onNetWorkStart() {
        if (mStatusView != null) {
            mStatusView.setStatus(StatusLayout.SUCCESS);
        }
        if (view != null) {
            view.showProgress();
        }
    }

    @Override
    public void onNetWorkError(Throwable e) {
        if (mStatusView != null) {
            mStatusView.setStatus(StatusLayout.ERROR);
        }
        KLog.i(e.toString());
        if (view != null) {
            view.hideProgress();
            view.netWorkError();
        }
    }

    @Override
    public void onNetWorkComplete() {
        if (view != null) {
            view.hideProgress();
        }
    }

    @Override
    public void onNetWorkSuccess(M data) {
        if (view != null) {
            if (data instanceof List && view instanceof BaseListView && ((List) data).isEmpty()) {
                if (mStatusView != null) {
                    mStatusView.setStatus(StatusLayout.EMPTY);
                }
                ((BaseListView) view).noMore();
            } else {
                if (mStatusView != null) {
                    mStatusView.setStatus(StatusLayout.SUCCESS);
                }
                view.netWorkSuccess(data);
            }
        }
    }

    protected void netWork(String url) {
        KLog.i(url);
        RxJsoupNetWork.getInstance().cancel(tag);
        RxJsoupNetWork
                .getInstance()
                .getApi(tag, url, this);
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public void setRootView(StatusLayout statusLayout) {
        if (mStatusView == null) {
            this.mStatusView = statusLayout;
        }
    }

    public void onDestroy(int state) {
        switch (state) {
            case Constant.TYPE_NO_FINISH:
                RxJsoupNetWork.getInstance().cancel(tag);
                break;
        }
        RxJsoupNetWork.getInstance().cancel(KK_URL_TAG);
        RxJsoupNetWork.getInstance().cancel(KK_DATA_TAG);
        if (view != null)
            view = null;
    }
}
