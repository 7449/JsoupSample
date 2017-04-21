package com.fiction.fiction.biquge.detail.presenter;

import com.fiction.fiction.biquge.detail.model.BiQuGeListDetailModel;
import com.fiction.fiction.biquge.detail.view.BiQuGeListDetailView;
import com.fiction.manager.JsoupBiQuGeListManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/1/8.
 */

public class BiQuGeListDetailPresenterImpl extends PresenterImplCompat<BiQuGeListDetailModel, BiQuGeListDetailView> implements BiQuGeListDetailPresenter {

    public BiQuGeListDetailPresenterImpl(BiQuGeListDetailView view) {
        super(view);
    }

    @Override
    public void startDetail(String url) {
        netWork(url);
    }

    @Override
    public BiQuGeListDetailModel getT(Document document) {
        return JsoupBiQuGeListManager.get(document).getBiqugeListDetail();
    }
}
