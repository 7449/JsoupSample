package com.framework.base.mvp;

import com.framework.widget.Constant;
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


    public BasePresenterImpl(V view) {
        this.view = view;
    }

    @Override
    public void onNetWorkStart() {
        if (view != null) {
            view.showProgress();
        }
    }

    @Override
    public void onNetWorkError(Throwable e) {
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
        if (view == null) {
            return;
        }
        if (data instanceof List) {
            if (view instanceof BaseListView) {
                if (((List) data).isEmpty()) {
                    KLog.i("data  isEmpty == true, view instanceof BaseListView");
                    ((BaseListView) view).noMore();
                } else {
                    KLog.i("view instanceof BaseListView,Success");
                    view.netWorkSuccess(data);
                }
                return;
            }
            if (((List) data).isEmpty()) {
                KLog.i("data  isEmpty == true, view instanceof BaseView");
                view.netWorkError();
            } else {
                KLog.i("view instanceof BaseView,Success");
                view.netWorkSuccess(data);
            }
        } else {
            view.netWorkSuccess(data);
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
