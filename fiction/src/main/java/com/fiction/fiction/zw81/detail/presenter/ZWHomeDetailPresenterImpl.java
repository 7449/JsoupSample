package com.fiction.fiction.zw81.detail.presenter;

import com.fiction.fiction.zw81.detail.model.ZWHomeDetailModel;
import com.fiction.fiction.zw81.detail.view.ZWHomeDetailView;
import com.fiction.manager.JsoupZwHomeManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/1/8.
 */

public class ZWHomeDetailPresenterImpl extends PresenterImplCompat<ZWHomeDetailModel, ZWHomeDetailView> implements ZWHomeDetailPresenter {

    public ZWHomeDetailPresenterImpl(ZWHomeDetailView view) {
        super(view);
    }

    @Override
    public void startDetail(String url) {
        netWork(url);
    }

    @Override
    public ZWHomeDetailModel getT(Document document) {
        return JsoupZwHomeManager.get(document).getZwHomeDetail();
    }
}
