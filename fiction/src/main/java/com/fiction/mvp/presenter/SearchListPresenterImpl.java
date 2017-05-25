package com.fiction.mvp.presenter;


import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupSearchManager;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.view.ViewManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public class SearchListPresenterImpl extends PresenterImplCompat<List<FictionModel>, ViewManager.SearchListView> implements PresenterManager.SearchListPresenter {


    public SearchListPresenterImpl(ViewManager.SearchListView view) {
        super(view);
    }

    @Override
    public void startSearch(String fictionName, int page) {
        netWork(ApiConfig.SEARCH_URL + fictionName + "&p=" + page + ApiConfig.SEARCH_SUFFIX);
    }


    @Override
    public List<FictionModel> getT(Document document) {
        return JsoupSearchManager.get(document).get81List();
    }
}
