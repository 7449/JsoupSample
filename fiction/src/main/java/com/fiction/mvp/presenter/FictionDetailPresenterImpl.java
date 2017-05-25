package com.fiction.mvp.presenter;

import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupFictionListManager;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.view.ViewManager;
import com.framework.base.mvp.BasePresenterImpl;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/1/8.
 */

public class FictionDetailPresenterImpl extends BasePresenterImpl<FictionModel, ViewManager.FictionDetailView> implements PresenterManager.FictionDetailPresenter {

    private String type = ApiConfig.Type.ZW_81;

    public FictionDetailPresenterImpl(ViewManager.FictionDetailView view) {
        super(view);
    }

    @Override
    public void startDetail(String url, String type) {
        this.type = type;
        netWork(url);
    }

    @Override
    public FictionModel getT(Document document) {
        return JsoupFictionListManager.get(document).getDetail(type);
    }
}
