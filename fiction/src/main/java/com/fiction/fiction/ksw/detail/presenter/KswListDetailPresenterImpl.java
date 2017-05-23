package com.fiction.fiction.ksw.detail.presenter;

import com.fiction.fiction.ksw.detail.model.KswListDetailModel;
import com.fiction.fiction.ksw.detail.view.KswListDetailView;
import com.fiction.manager.JsoupKswListManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/1/8.
 */

public class KswListDetailPresenterImpl extends PresenterImplCompat<KswListDetailModel, KswListDetailView> implements KswListDetailPresenter {

    public KswListDetailPresenterImpl(KswListDetailView view) {
        super(view);
    }

    @Override
    public void startDetail(String url) {
        netWork(url);
    }

    @Override
    public KswListDetailModel getT(Document document) {
        return JsoupKswListManager.get(document).getKswListDetail();
    }
}
