package com.framework.base.mvp;

import com.socks.library.KLog;

import java.util.List;

import io.reactivex.jsoup.network.manager.RxJsoupNetWork;
import io.reactivex.jsoup.network.manager.RxJsoupNetWorkListener;

/**
 * by y on 2017/3/23
 */

public abstract class BasePresenterImpl<M, V extends BaseView<M>>
        implements RxJsoupNetWorkListener<M> {

    protected V view;
    private Object tag;

    public BasePresenterImpl(V view) {
        this.view = view;
    }

    @Override
    public void onNetWorkStart() {
        if (view == null) {
            return;
        }
        view.showProgress();
    }

    @Override
    public void onNetWorkError(Throwable e) {
        if (view == null) {
            return;
        }
        KLog.i(e.toString());
        view.hideProgress();
        view.netWorkError();
    }

    @Override
    public void onNetWorkComplete() {
        if (view == null) {
            return;
        }
        view.hideProgress();
    }

    @Override
    public void onNetWorkSuccess(M data) {
        if (view == null) {
            return;
        }
        if (data instanceof List && view instanceof BaseListView && ((List) data).isEmpty()) {
            ((BaseListView) view).noMore();
        } else {
            view.netWorkSuccess(data);
        }
    }

    protected void netWork(String url) {
        RxJsoupNetWork.getInstance().cancel(tag);
        RxJsoupNetWork
                .getInstance()
                .getApi(tag, url, this);
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public void onDestroy() {
        if (view != null) {
            KLog.i("cancel------:" + tag);
            KLog.i(getClass().getSimpleName(), "onDestroyView:" + getClass().getSimpleName());
            RxJsoupNetWork.getInstance().cancel(tag);
            view = null;
        }
    }
}
