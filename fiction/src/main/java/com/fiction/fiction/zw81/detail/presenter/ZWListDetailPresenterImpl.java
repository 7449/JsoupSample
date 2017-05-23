package com.fiction.fiction.zw81.detail.presenter;

import com.fiction.fiction.zw81.detail.model.ZWListDetailModel;
import com.fiction.fiction.zw81.detail.view.ZWListDetailView;
import com.fiction.manager.JsoupZwListManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/1/8.
 */

public class ZWListDetailPresenterImpl extends PresenterImplCompat<ZWListDetailModel, ZWListDetailView> implements ZWListDetailPresenter {

    public ZWListDetailPresenterImpl(ZWListDetailView view) {
        super(view);
    }

    @Override
    public void startDetail(String url) {
        netWork(url);
    }

    @Override
    public ZWListDetailModel getT(Document document) {
        return JsoupZwListManager.get(document).getZwListDetail();
    }
}
