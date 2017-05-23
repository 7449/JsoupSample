package com.framework.base.mvp;

import com.socks.library.KLog;

import java.util.List;

import io.reactivex.jsoup.network.manager.RxJsoupNetWork;
import io.reactivex.jsoup.network.manager.RxJsoupNetWorkListener;

/**
 * by y on 2017/3/23
 */

public abstract class PresenterImplCompat<M, V extends BaseView<M>>
        implements RxJsoupNetWorkListener<M> {

    protected final V view;

    public PresenterImplCompat(V view) {
        this.view = view;
    }

    @Override
    public void onNetWorkStart() {
        view.showProgress();
    }

    @Override
    public void onNetWorkError(Throwable e) {
        KLog.i(e.toString());
        view.hideProgress();
        view.netWorkError();
    }

    @Override
    public void onNetWorkComplete() {
        view.hideProgress();
    }

    @Override
    public void onNetWorkSuccess(M data) {
        if (data instanceof List && view instanceof BaseListView && ((List) data).isEmpty()) {
            ((BaseListView) view).noMore();
        } else {
            view.netWorkSuccess(data);
        }
    }

    protected void netWork(String url) {
        KLog.i(url);
        RxJsoupNetWork
                .getInstance()
                .getApi(url,
                        this);
    }
}
