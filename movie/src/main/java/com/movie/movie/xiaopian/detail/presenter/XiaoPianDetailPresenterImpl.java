package com.movie.movie.xiaopian.detail.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.movie.manager.XiaoPianJsoupManager;
import com.movie.movie.xiaopian.detail.model.XiaoPianDetailModel;
import com.movie.movie.xiaopian.detail.view.XiaoPianDetailView;

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
        netWork(url);
    }

    @Override
    public XiaoPianDetailModel getT(Document document) {
        return XiaoPianJsoupManager.get(document).getXiaoPianDetail();
    }
}
