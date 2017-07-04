package com.fiction.mvp.presenter;

import com.fiction.manager.JsoupFictionListManager;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.view.ViewManager;
import com.framework.base.mvp.BasePresenterImpl;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/1/8.
 */

public class FictionDetailPresenterImpl extends BasePresenterImpl<FictionModel, ViewManager.FictionDetailView> implements PresenterManager.FictionDetailPresenter {


    public FictionDetailPresenterImpl(ViewManager.FictionDetailView view) {
        super(view);
    }

    @Override
    public void startDetail(String url) {
        netWork(url);
    }

    @Override
    public FictionModel getT(Document document) {
        if (view == null) {
            return new FictionModel();
        }
        return JsoupFictionListManager.get(document).getDetail(view.getType());
    }
}
