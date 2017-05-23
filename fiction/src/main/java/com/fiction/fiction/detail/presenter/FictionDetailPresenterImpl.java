package com.fiction.fiction.detail.presenter;

import com.fiction.fiction.detail.model.FictionDetailModel;
import com.fiction.fiction.detail.view.FictionDetailView;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupFictionListManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/1/8.
 */

public class FictionDetailPresenterImpl extends PresenterImplCompat<FictionDetailModel, FictionDetailView> implements FictionDetailPresenter {

    private String type = ApiConfig.Type.ZW_81;

    public FictionDetailPresenterImpl(FictionDetailView view) {
        super(view);
    }

    @Override
    public void startDetail(String url, String type) {
        this.type = type;
        netWork(url);
    }

    @Override
    public FictionDetailModel getT(Document document) {
        return JsoupFictionListManager.get(document).getDetail(type);
    }
}
