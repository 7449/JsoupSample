package com.movie.movie.xiaopian.detail.presenter;

import com.framework.base.PresenterImplCompat;
import com.movie.manager.XiaoPianJsoupManager;
import com.movie.movie.xiaopian.detail.model.XiaoPianDetailModel;
import com.movie.movie.xiaopian.detail.view.XiaoPianDetailView;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/3/24.
 */

public class XiaoPianDetailPresenterImpl extends PresenterImplCompat<XiaoPianDetailModel, XiaoPianDetailView> implements XiaoPianDetailPresenter {

    public XiaoPianDetailPresenterImpl(XiaoPianDetailView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String url) {
        RxJsoupNetWork
                .getInstance()
                .getApi(
                        RxJsoupNetWork.onSubscribe(url, this),
                        this);
    }

    @Override
    public XiaoPianDetailModel getT(Document document) {
        return XiaoPianJsoupManager.get(document).getXiaoPianDetail();
    }
}
