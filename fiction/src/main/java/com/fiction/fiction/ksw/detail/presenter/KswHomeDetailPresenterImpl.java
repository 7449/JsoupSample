package com.fiction.fiction.ksw.detail.presenter;

import com.fiction.fiction.ksw.detail.model.KswHomeDetailModel;
import com.fiction.fiction.ksw.detail.view.KswHomeDetailView;
import com.fiction.manager.JsoupKswHomeManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/1/8.
 */

public class KswHomeDetailPresenterImpl extends PresenterImplCompat<KswHomeDetailModel, KswHomeDetailView> implements KswHomeDetailPresenter {

    public KswHomeDetailPresenterImpl(KswHomeDetailView view) {
        super(view);
    }

    @Override
    public void startDetail(String url) {
        netWork(url);
    }

    @Override
    public KswHomeDetailModel getT(Document document) {
        return JsoupKswHomeManager.get(document).getKswHomeDetail();
    }
}
