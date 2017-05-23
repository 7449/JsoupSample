package com.fiction.fiction.biquge.detail.presenter;

import com.fiction.fiction.biquge.detail.model.BiQuGeHomeDetailModel;
import com.fiction.fiction.biquge.detail.view.BiQuGeHomeDetailView;
import com.fiction.manager.JsoupBiQuGeHomeManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/1/8.
 */

public class BiQuGeHomeDetailPresenterImpl extends PresenterImplCompat<BiQuGeHomeDetailModel, BiQuGeHomeDetailView> implements BiQuGeHomeDetailPresenter {

    public BiQuGeHomeDetailPresenterImpl(BiQuGeHomeDetailView view) {
        super(view);
    }

    @Override
    public void startDetail(String url) {
        netWork(url);
    }

    @Override
    public BiQuGeHomeDetailModel getT(Document document) {
        return JsoupBiQuGeHomeManager.get(document).getBiQuGeHomeDetail();
    }
}
