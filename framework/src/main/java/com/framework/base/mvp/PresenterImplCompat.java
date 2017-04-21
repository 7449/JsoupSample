package com.framework.base.mvp;

import com.rxjsoupnetwork.manager.RxJsoupDisposeManager;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;
import com.rxjsoupnetwork.manager.RxJsoupNetWorkListener;
import com.socks.library.KLog;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * by y on 2017/3/23
 */

public abstract class PresenterImplCompat<M, V extends BaseView<M>>
        implements RxJsoupNetWorkListener<M>, RxJsoupNetWork.DocumentCallback<M> {

    protected final V view;
    private DisposableObserver<M> api;

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
        if (api != null) {
            RxJsoupDisposeManager.getInstance().unDispose(api);
        }
    }

    @Override
    public void onNetWorkComplete() {
        view.hideProgress();
        if (api != null) {
            RxJsoupDisposeManager.getInstance().unDispose(api);
        }
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
        api = RxJsoupNetWork
                .getInstance()
                .getApi(
                        RxJsoupNetWork.onSubscribe(url, this),
                        this);
    }
}
